package com.lookation.integration

import com.lookation.jooq.tables.AccountRoles.ACCOUNT_ROLES
import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import com.lookation.jooq.tables.Accounts.ACCOUNTS
import com.lookation.jooq.tables.HostProfiles.HOST_PROFILES
import com.lookation.jooq.tables.Reservations.RESERVATIONS
import com.lookation.jooq.tables.Spaces.SPACES
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientResponseException
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import org.springframework.web.util.UriComponentsBuilder
import java.time.OffsetDateTime
import java.util.UUID

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "lookation.jwt.secret=0123456789abcdef0123456789abcdef",
        "lookation.encryption.key=AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQE=",
        "lookation.upload-dir=build/test-uploads",
    ],
)
@Import(TestMailConfig::class)
@Testcontainers
class SpaceFlowTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:18-alpine"))

        private val mapType = object : ParameterizedTypeReference<Map<String, Any>>() {}
    }

    @Value("\${local.server.port}")
    private var port: Int = 0

    @Autowired
    private lateinit var dsl: DSLContext

    @Autowired
    private lateinit var mailSender: CapturingMailSender

    private val client by lazy { RestClient.create("http://localhost:$port") }

    private data class ApiResponse(val status: HttpStatusCode, val body: Map<String, Any>?, val raw: String? = null)

    private data class TestUser(val id: Long, val access: String, val refresh: String)

    @Test
    fun `host application grants the role and masks the account number`() {
        val user = signup()

        val applied = post("/api/v1/hosts/apply", hostForm(), user.access)
        assertThat(applied.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(applied.body!!["status"]).isEqualTo("PUBLISHED")

        val hostToken = refresh(user)
        val me = get("/api/v1/me", hostToken)
        assertThat(me.body!!["roles"] as List<*>).contains("HOST")

        val profile = get("/api/v1/me/host-profile", hostToken)
        assertThat(profile.body!!["bankAccountNumberMasked"]).isEqualTo("****1234")

        val cipher = dsl.select(HOST_PROFILES.BANK_ACCOUNT_NUMBER_ENC)
            .from(HOST_PROFILES)
            .where(HOST_PROFILES.ACCOUNT_ID.eq(user.id))
            .fetchOne(HOST_PROFILES.BANK_ACCOUNT_NUMBER_ENC)
        assertThat(cipher).isNotNull()
        assertThat(String(cipher!!, Charsets.UTF_8)).doesNotContain("12345678901234")
    }

    @Test
    fun `space needs an image before inspection and admin approval publishes it`() {
        val host = signup()
        assertThat(post("/api/v1/hosts/apply", hostForm(), host.access).status.value())
            .isEqualTo(HttpStatus.OK.value())
        val hostToken = refresh(host)

        val created = post("/api/v1/spaces", spaceForm(), hostToken)
        assertThat(created.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(created.body!!["status"]).isEqualTo("DRAFT")
        val spaceId = (created.body["id"] as Number).toLong()

        assertThat(post("/api/v1/spaces/$spaceId/inspection", emptyMap(), hostToken).status.value())
            .isEqualTo(HttpStatus.CONFLICT.value())

        val image = upload(spaceId, hostToken)
        assertThat(image.status.value()).isEqualTo(HttpStatus.OK.value())

        assertThat(post("/api/v1/spaces/$spaceId/inspection", emptyMap(), hostToken).status.value())
            .isEqualTo(HttpStatus.CONFLICT.value())

        assertThat(putPrices(spaceId, hostToken).status.value())
            .isEqualTo(HttpStatus.OK.value())

        val submitted = post("/api/v1/spaces/$spaceId/inspection", emptyMap(), hostToken)
        assertThat(submitted.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(submitted.body!!["status"]).isEqualTo("PENDING_INSPECTION")

        val adminToken = adminToken()
        val approved = post(
            "/api/v1/admin/inspections/$spaceId/decision",
            mapOf("approved" to true),
            adminToken,
        )
        assertThat(approved.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(approved.body!!["status"]).isEqualTo("PUBLISHED")

        val publicRead = get("/api/v1/spaces/$spaceId", null)
        assertThat(publicRead.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(publicRead.body!!["facilities"] as List<*>).contains("WIFI", "PARKING")
    }

    @Test
    fun `rejection requires a reason and the host can resubmit`() {
        val host = signup()
        post("/api/v1/hosts/apply", hostForm(), host.access)
        val hostToken = refresh(host)
        val spaceId = (post("/api/v1/spaces", spaceForm(), hostToken).body!!["id"] as Number).toLong()
        upload(spaceId, hostToken)
        putPrices(spaceId, hostToken)
        post("/api/v1/spaces/$spaceId/inspection", emptyMap(), hostToken)

        val adminToken = adminToken()
        assertThat(
            post("/api/v1/admin/inspections/$spaceId/decision", mapOf("approved" to false), adminToken)
                .status.value(),
        ).isEqualTo(HttpStatus.BAD_REQUEST.value())

        val rejected = post(
            "/api/v1/admin/inspections/$spaceId/decision",
            mapOf("approved" to false, "reason" to "사진이 부족합니다"),
            adminToken,
        )
        assertThat(rejected.body!!["status"]).isEqualTo("INSPECTION_REJECTED")

        val resubmitted = post("/api/v1/spaces/$spaceId/inspection", emptyMap(), hostToken)
        assertThat(resubmitted.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(resubmitted.body!!["status"]).isEqualTo("PENDING_INSPECTION")
    }

    @Test
    fun `other accounts cannot update a space`() {
        val host = signup()
        val other = signup()
        post("/api/v1/hosts/apply", hostForm(), host.access)
        val hostToken = refresh(host)
        val otherToken = refresh(other)
        val spaceId = (post("/api/v1/spaces", spaceForm(), hostToken).body!!["id"] as Number).toLong()

        val result = put("/api/v1/spaces/$spaceId", spaceForm(), otherToken)
        assertThat(result.status.value()).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    private fun putPrices(spaceId: Long, token: String): ApiResponse =
        try {
            val entity = client.put()
                .uri("/api/v1/spaces/$spaceId/prices")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(priceRules())
                .retrieve()
                .toEntity(object : ParameterizedTypeReference<List<Any>>() {})
            ApiResponse(entity.statusCode, null)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }

    @Test
    fun `closing hides the space, reopening requires inspection, and deletion is blocked by active reservations`() {
        val host = signup()
        post("/api/v1/hosts/apply", hostForm(), host.access)
        val hostToken = refresh(host)
        val name = "space-${UUID.randomUUID()}"
        val created = post("/api/v1/spaces", spaceForm() + ("name" to name), hostToken)
        val spaceId = (created.body!!["id"] as Number).toLong()
        upload(spaceId, hostToken)
        putPrices(spaceId, hostToken)
        post("/api/v1/spaces/$spaceId/inspection", emptyMap(), hostToken)
        val adminToken = adminToken()
        post("/api/v1/admin/inspections/$spaceId/decision", mapOf("approved" to true), adminToken)

        val closed = post("/api/v1/spaces/$spaceId/close", emptyMap(), hostToken)
        assertThat(closed.body!!["status"]).isEqualTo("CLOSED")
        assertThat(get("/api/v1/spaces/$spaceId").status.value()).isEqualTo(HttpStatus.NOT_FOUND.value())
        assertThat(searchNames(mapOf("keyword" to name))).doesNotContain(name)

        val reopened = post("/api/v1/spaces/$spaceId/reopen", emptyMap(), hostToken)
        assertThat(reopened.body!!["status"]).isEqualTo("DRAFT")
        assertThat(post("/api/v1/spaces/$spaceId/inspection", emptyMap(), hostToken).body!!["status"])
            .isEqualTo("PENDING_INSPECTION")
        post("/api/v1/admin/inspections/$spaceId/decision", mapOf("approved" to true), adminToken)

        val memberId = dsl.insertInto(ACCOUNTS)
            .set(ACCOUNTS.EMAIL, "guest-${UUID.randomUUID()}@lookation.test")
            .set(ACCOUNTS.PASSWORD_HASH, "unused")
            .set(ACCOUNTS.NAME, "Guest")
            .set(ACCOUNTS.NICKNAME, "guest")
            .returningResult(ACCOUNTS.ID)
            .fetchOne()!!
            .value1()
        val hostId = dsl.select(SPACES.HOST_ID).from(SPACES)
            .where(SPACES.ID.eq(spaceId)).fetchOne(SPACES.HOST_ID)!!
        val reservationId = dsl.insertInto(RESERVATIONS)
            .set(RESERVATIONS.MEMBER_ID, memberId)
            .set(RESERVATIONS.HOST_ID, hostId)
            .set(RESERVATIONS.SPACE_ID, spaceId)
            .set(RESERVATIONS.START_AT, OffsetDateTime.now().plusDays(5))
            .set(RESERVATIONS.END_AT, OffsetDateTime.now().plusDays(5).plusHours(2))
            .set(RESERVATIONS.GUEST_COUNT, 2.toShort())
            .set(RESERVATIONS.PRICE, 100_000)
            .set(RESERVATIONS.STATUS, "CONFIRMED")
            .returningResult(RESERVATIONS.ID)
            .fetchOne()!!
            .value1()

        assertThat(delete("/api/v1/spaces/$spaceId", hostToken).status.value())
            .isEqualTo(HttpStatus.CONFLICT.value())

        dsl.update(RESERVATIONS).set(RESERVATIONS.STATUS, "CANCELLED")
            .where(RESERVATIONS.ID.eq(reservationId)).execute()
        assertThat(delete("/api/v1/spaces/$spaceId", hostToken).status.value())
            .isEqualTo(HttpStatus.NO_CONTENT.value())
        assertThat(get("/api/v1/spaces/$spaceId", hostToken).status.value())
            .isEqualTo(HttpStatus.NOT_FOUND.value())
        assertThat(searchNames(mapOf("keyword" to name))).doesNotContain(name)
    }

    @Test
    fun `region filters narrow the public search`() {
        val hostId = dsl.insertInto(ACCOUNTS)
            .set(ACCOUNTS.EMAIL, "region-${UUID.randomUUID()}@lookation.test")
            .set(ACCOUNTS.PASSWORD_HASH, "unused")
            .set(ACCOUNTS.NAME, "Region Host")
            .set(ACCOUNTS.NICKNAME, "region")
            .returningResult(ACCOUNTS.ID)
            .fetchOne()!!
            .value1()
        val prefix = "region-${UUID.randomUUID()}"
        fun insertSpace(name: String, sido: String?, sigungu: String?) {
            dsl.insertInto(SPACES)
                .set(SPACES.HOST_ID, hostId)
                .set(SPACES.NAME, name)
                .set(SPACES.ADDRESS, "서울 마포구 양화로 45")
                .set(SPACES.SPACE_TYPE, "PARTY_ROOM")
                .set(SPACES.MIN_CAPACITY, 2.toShort())
                .set(SPACES.MAX_CAPACITY, 10.toShort())
                .set(SPACES.SIDO, sido)
                .set(SPACES.SIGUNGU, sigungu)
                .set(SPACES.STATUS, "PUBLISHED")
                .execute()
        }
        insertSpace("$prefix-seoul", "서울", "마포구")
        insertSpace("$prefix-seoul-2", "서울", "강남구")
        insertSpace("$prefix-busan", "부산", "해운대구")
        insertSpace("$prefix-unknown", null, null)

        assertThat(searchNames(mapOf("keyword" to prefix, "sido" to "서울")))
            .containsExactlyInAnyOrder("$prefix-seoul", "$prefix-seoul-2")
        assertThat(searchNames(mapOf("keyword" to prefix, "sido" to "서울", "sigungu" to "마포구")))
            .containsExactly("$prefix-seoul")
        assertThat(searchNames(mapOf("keyword" to prefix, "sigungu" to "해운대구")))
            .containsExactly("$prefix-busan")
        assertThat(searchNames(mapOf("keyword" to prefix)))
            .containsExactlyInAnyOrder("$prefix-seoul", "$prefix-seoul-2", "$prefix-busan", "$prefix-unknown")
    }

    @Test
    fun `space created with a region is returned by the region filter`() {
        val host = signup()
        post("/api/v1/hosts/apply", hostForm(), host.access)
        val hostToken = refresh(host)
        val name = "region-api-${UUID.randomUUID()}"
        val created = post(
            "/api/v1/spaces",
            spaceForm() + mapOf("name" to name, "sido" to "제주", "sigungu" to "서귀포시"),
            hostToken,
        )
        assertThat(created.body!!["sido"]).isEqualTo("제주")
        val spaceId = (created.body["id"] as Number).toLong()

        upload(spaceId, hostToken)
        putPrices(spaceId, hostToken)
        post("/api/v1/spaces/$spaceId/inspection", emptyMap(), hostToken)
        post("/api/v1/admin/inspections/$spaceId/decision", mapOf("approved" to true), adminToken())

        assertThat(searchNames(mapOf("keyword" to name, "sido" to "제주", "sigungu" to "서귀포시")))
            .containsExactly(name)
        assertThat(searchNames(mapOf("keyword" to name, "sido" to "부산"))).isEmpty()
    }

    @Test
    fun `space images keep upload order`() {
        val host = signup()
        post("/api/v1/hosts/apply", hostForm(), host.access)
        val hostToken = refresh(host)
        val spaceId = (post("/api/v1/spaces", spaceForm(), hostToken).body!!["id"] as Number).toLong()

        val uploaded = (1..3).map {
            val response = upload(spaceId, hostToken)
            assertThat(response.status.value()).isEqualTo(HttpStatus.OK.value())
            response.body!!["key"] as String
        }

        dsl.update(ATTACHMENTS)
            .set(ATTACHMENTS.SORT_ORDER, 99.toShort())
            .where(ATTACHMENTS.STORAGE_KEY.eq(uploaded.first().removePrefix("/uploads/")))
            .execute()

        assertThat(getList("/api/v1/spaces/$spaceId/images"))
            .isEqualTo(listOf(uploaded[1], uploaded[2], uploaded[0]))
    }

    private fun getList(path: String): List<String> {
        val type = object : ParameterizedTypeReference<List<String>>() {}
        return client.get().uri(path).retrieve().toEntity(type).body!!
    }

    private fun searchNames(params: Map<String, String>): List<Any?> {
        var builder = UriComponentsBuilder.fromPath("/api/v1/spaces")
        params.forEach { (name, value) -> builder = builder.queryParam(name, value) }
        val result = getUri(builder.build().encode().toUri())
        val items = result.body!!["items"] as List<*>
        return items.map { (it as Map<*, *>)["name"] }
    }

    private fun getUri(uri: java.net.URI): ApiResponse =
        try {
            val entity = client.get().uri(uri).retrieve().toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }

    private fun delete(path: String, token: String): ApiResponse =
        try {
            val entity = client.delete()
                .uri(path)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .retrieve()
                .toBodilessEntity()
            ApiResponse(entity.statusCode, null)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }

    private fun hostForm() = mapOf(
        "businessName" to "테스트 주식회사",
        "businessNumber" to (1_000_000_000L + (Math.random() * 8_999_999_999L).toLong()).toString(),
        "representativeName" to "홍길동",
        "bankName" to "국민은행",
        "bankAccountNumber" to "12345678901234",
        "bankAccountHolder" to "홍길동",
    )

    private fun priceRules() = listOf(
        mapOf("dayOfWeek" to 1, "unit" to "HOUR", "startTime" to "18:00", "endTime" to "20:00", "price" to 100_000),
    )

    private fun spaceForm() = mapOf(
        "name" to "루프탑 파티룸",
        "shortIntro" to "야경이 좋은 공간",
        "address" to "서울시 중구 세종대로 1",
        "spaceType" to "ROOFTOP",
        "minCapacity" to 2,
        "maxCapacity" to 10,
        "useHours" to "18:00-22:00",
        "caution" to "22시 이후 소음 금지",
        "facilities" to listOf("WIFI", "PARKING"),
    )

    private fun signup(): TestUser {
        val email = "user-${UUID.randomUUID()}@lookation.test"
        post("/api/v1/auth/email-verifications", mapOf("email" to email, "purpose" to "SIGNUP"))
        val code = mailSender.codes.getValue(email)
        val signup = post(
            "/api/v1/auth/signup",
            mapOf(
                "email" to email,
                "password" to "password123",
                "name" to "User",
                "nickname" to "user",
                "code" to code,
            ),
        )
        val access = signup.body!!["accessToken"] as String
        val refreshToken = signup.body["refreshToken"] as String
        val me = get("/api/v1/me", access)
        return TestUser((me.body!!["id"] as Number).toLong(), access, refreshToken)
    }

    private fun refresh(user: TestUser): String {
        val refreshed = post("/api/v1/auth/refresh", mapOf("refreshToken" to user.refresh))
        return refreshed.body!!["accessToken"] as String
    }

    private fun adminToken(): String {
        val user = signup()
        dsl.insertInto(ACCOUNT_ROLES)
            .set(ACCOUNT_ROLES.ACCOUNT_ID, user.id)
            .set(ACCOUNT_ROLES.ROLE, "ADMIN")
            .execute()
        val email = dsl.select(com.lookation.jooq.tables.Accounts.ACCOUNTS.EMAIL)
            .from(com.lookation.jooq.tables.Accounts.ACCOUNTS)
            .where(com.lookation.jooq.tables.Accounts.ACCOUNTS.ID.eq(user.id))
            .fetchOne(com.lookation.jooq.tables.Accounts.ACCOUNTS.EMAIL)!!
        val login = post("/api/v1/auth/login", mapOf("email" to email, "password" to "password123"))
        return login.body!!["accessToken"] as String
    }

    private fun upload(spaceId: Long, token: String): ApiResponse {
        val builder = MultipartBodyBuilder()
        builder.part("file", ByteArrayResource(ByteArray(32) { 1 }))
            .filename("test.png")
            .contentType(MediaType.IMAGE_PNG)
        return try {
            val entity = client.post()
                .uri("/api/v1/spaces/$spaceId/images")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(builder.build())
                .retrieve()
                .toEntity(String::class.java)
            ApiResponse(entity.statusCode, mapOf("key" to (entity.body ?: "")))
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }
    }

    private fun get(path: String, token: String? = null): ApiResponse =
        try {
            var request = client.get().uri(path)
            if (token != null) request = request.header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            val entity = request.retrieve().toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }

    private fun post(path: String, body: Map<String, Any?>, token: String? = null): ApiResponse =
        try {
            var request = client.post().uri(path).contentType(MediaType.APPLICATION_JSON)
            if (token != null) request = request.header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            val entity = request.body(body).retrieve().toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }

    private fun put(path: String, body: Any, token: String): ApiResponse =
        try {
            val entity = client.put()
                .uri(path)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }
}
