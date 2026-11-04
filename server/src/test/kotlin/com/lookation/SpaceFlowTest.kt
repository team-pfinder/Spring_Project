package com.lookation

import com.lookation.jooq.tables.AccountRoles.ACCOUNT_ROLES
import com.lookation.jooq.tables.HostProfiles.HOST_PROFILES
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
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
import java.util.UUID

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "lookation.jwt.secret=0123456789abcdef0123456789abcdef",
        "lookation.encryption.key=AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQE=",
        "lookation.upload-dir=build/test-uploads",
    ],
)
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
        val signup = post(
            "/api/v1/auth/signup",
            mapOf(
                "email" to email,
                "password" to "password123",
                "name" to "User",
                "nickname" to "user",
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
