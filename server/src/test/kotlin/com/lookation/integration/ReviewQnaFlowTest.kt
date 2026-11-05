package com.lookation.integration

import com.lookation.jooq.tables.Accounts.ACCOUNTS
import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import com.lookation.jooq.tables.Reservations.RESERVATIONS
import com.lookation.jooq.tables.Spaces.SPACES
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientResponseException
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.time.Instant
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
class ReviewQnaFlowTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:18-alpine"))

        private val mapType = object : ParameterizedTypeReference<Map<String, Any>>() {}
    }

    @Value("\${local.server.port}")
    private var port: Int = 0

    @Autowired private lateinit var dsl: DSLContext
    @Autowired private lateinit var jwtEncoder: JwtEncoder
    @Autowired private lateinit var mailSender: CapturingMailSender

    private val client by lazy { RestClient.create("http://localhost:$port") }
    private data class ApiResponse(val status: HttpStatusCode, val body: Map<String, Any>?, val raw: String? = null)

    @Test
    fun `review lifecycle with host reply and admin blind`() {
        val hostId = account("host")
        val spaceId = publishedSpace(hostId)
        val member = signup()
        val reservationId = reservation(member.id, hostId, spaceId, "COMPLETED", OffsetDateTime.now().minusDays(1))

        val created = postMap("/api/v1/reviews", mapOf("reservationId" to reservationId, "rating" to 5, "content" to "좋았습니다"), member.access)
        assertThat(created.status.value()).isEqualTo(HttpStatus.OK.value())
        val reviewId = (created.body!!["id"] as Number).toLong()

        assertThat(postMap("/api/v1/reviews", mapOf("reservationId" to reservationId, "rating" to 4, "content" to "또"), member.access).status.value())
            .isEqualTo(HttpStatus.CONFLICT.value())

        val listed = getMap("/api/v1/spaces/$spaceId/reviews", null).body!!["items"] as List<*>
        assertThat(listed).hasSize(1)

        val hostToken = tokenFor(hostId, "HOST")
        assertThat(postMap("/api/v1/reviews/$reviewId/reply", mapOf("content" to "감사합니다"), hostToken).body!!["reply"])
            .isEqualTo("감사합니다")
        assertThat(postMap("/api/v1/reviews/$reviewId/reply", mapOf("content" to "또"), hostToken).status.value())
            .isEqualTo(HttpStatus.CONFLICT.value())

        val adminId = account("admin")
        assertThat(postMap("/api/v1/admin/reviews/$reviewId/blind", emptyMap(), tokenFor(adminId, "ADMIN")).status.value())
            .isEqualTo(HttpStatus.OK.value())
        assertThat(getMap("/api/v1/spaces/$spaceId/reviews", null).body!!["items"] as List<*>).isEmpty()
    }

    @Test
    fun `review requires a completed reservation`() {
        val hostId = account("host")
        val spaceId = publishedSpace(hostId)
        val member = signup()
        val reservationId = reservation(member.id, hostId, spaceId, "CONFIRMED", OffsetDateTime.now().plusDays(3))

        val result = postMap("/api/v1/reviews", mapOf("reservationId" to reservationId, "rating" to 5, "content" to "좋아요"), member.access)
        assertThat(result.status.value()).isEqualTo(HttpStatus.CONFLICT.value())
    }

    @Test
    fun `qna can be answered once and deleted only before an answer`() {
        val hostId = account("host")
        val spaceId = publishedSpace(hostId)
        val member = signup()
        val hostToken = tokenFor(hostId, "HOST")

        val asked = postMap("/api/v1/spaces/$spaceId/qnas", mapOf("title" to "주차 되나요?", "content" to "주차 공간 문의"), member.access)
        assertThat(asked.status.value()).isEqualTo(HttpStatus.OK.value())
        val qnaId = (asked.body!!["id"] as Number).toLong()

        val answered = postMap("/api/v1/qnas/$qnaId/answer", mapOf("content" to "가능합니다"), hostToken)
        assertThat(answered.body!!["status"]).isEqualTo("ANSWERED")
        assertThat(postMap("/api/v1/qnas/$qnaId/answer", mapOf("content" to "또"), hostToken).status.value())
            .isEqualTo(HttpStatus.CONFLICT.value())
        assertThat(delete("/api/v1/qnas/$qnaId", member.access).status.value())
            .isEqualTo(HttpStatus.CONFLICT.value())

        val second = postMap("/api/v1/spaces/$spaceId/qnas", mapOf("title" to "질문2", "content" to "내용"), member.access)
        val secondId = (second.body!!["id"] as Number).toLong()
        assertThat(delete("/api/v1/qnas/$secondId", member.access).status.value())
            .isEqualTo(HttpStatus.NO_CONTENT.value())
        assertThat(getMap("/api/v1/spaces/$spaceId/qnas", null).body!!["items"] as List<*>).hasSize(1)
    }

    private data class TestUser(val id: Long, val access: String, val refresh: String)

    @Test
    fun `review images keep upload order`() {
        val hostId = account("host")
        val spaceId = publishedSpace(hostId)
        val member = signup()
        val reservationId = reservation(member.id, hostId, spaceId, "COMPLETED", OffsetDateTime.now().minusDays(1))
        val created = postMap(
            "/api/v1/reviews",
            mapOf("reservationId" to reservationId, "rating" to 5, "content" to "좋았습니다"),
            member.access,
        )
        val reviewId = (created.body!!["id"] as Number).toLong()

        val uploaded = (1..2).map {
            val response = uploadReviewImage(reviewId, member.access)
            assertThat(response.status.value()).isEqualTo(HttpStatus.OK.value())
            response.body!!["key"] as String
        }

        dsl.update(ATTACHMENTS)
            .set(ATTACHMENTS.SORT_ORDER, 99.toShort())
            .where(ATTACHMENTS.STORAGE_KEY.eq(uploaded.first().removePrefix("/uploads/")))
            .execute()

        val listed = getMap("/api/v1/spaces/$spaceId/reviews", null).body!!["items"] as List<*>
        assertThat((listed.first() as Map<*, *>)["images"]).isEqualTo(listOf(uploaded[1], uploaded[0]))
    }

    private fun uploadReviewImage(reviewId: Long, token: String): ApiResponse {
        val builder = MultipartBodyBuilder()
        builder.part("file", ByteArrayResource(ByteArray(32) { 1 }))
            .filename("test.png")
            .contentType(MediaType.IMAGE_PNG)
        return try {
            val entity = client.post()
                .uri("/api/v1/reviews/$reviewId/images")
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

    private fun signup(): TestUser {
        val email = "review-${UUID.randomUUID()}@lookation.test"
        postMap("/api/v1/auth/email-verifications", mapOf("email" to email, "purpose" to "SIGNUP"), null)
        val code = mailSender.codes.getValue(email)
        val signup = postMap(
            "/api/v1/auth/signup",
            mapOf("email" to email, "password" to "password123", "name" to "Member", "nickname" to "member", "code" to code),
            null,
        )
        val access = signup.body!!["accessToken"] as String
        val refresh = signup.body["refreshToken"] as String
        val me = getMap("/api/v1/me", access)
        return TestUser((me.body!!["id"] as Number).toLong(), access, refresh)
    }

    private fun account(nickname: String): Long =
        dsl.insertInto(ACCOUNTS)
            .set(ACCOUNTS.EMAIL, "fixture-${UUID.randomUUID()}@lookation.test")
            .set(ACCOUNTS.PASSWORD_HASH, "unused")
            .set(ACCOUNTS.NAME, nickname)
            .set(ACCOUNTS.NICKNAME, nickname)
            .returningResult(ACCOUNTS.ID)
            .fetchOne()!!
            .value1()

    private fun publishedSpace(hostId: Long): Long =
        dsl.insertInto(SPACES)
            .set(SPACES.HOST_ID, hostId)
            .set(SPACES.NAME, "리뷰 공간")
            .set(SPACES.ADDRESS, "서울")
            .set(SPACES.SPACE_TYPE, "PARTY_ROOM")
            .set(SPACES.MIN_CAPACITY, 1.toShort())
            .set(SPACES.MAX_CAPACITY, 10.toShort())
            .set(SPACES.STATUS, "PUBLISHED")
            .returningResult(SPACES.ID)
            .fetchOne()!!
            .value1()

    private fun reservation(memberId: Long, hostId: Long, spaceId: Long, status: String, endAt: OffsetDateTime): Long =
        dsl.insertInto(RESERVATIONS)
            .set(RESERVATIONS.MEMBER_ID, memberId)
            .set(RESERVATIONS.HOST_ID, hostId)
            .set(RESERVATIONS.SPACE_ID, spaceId)
            .set(RESERVATIONS.START_AT, endAt.minusHours(2))
            .set(RESERVATIONS.END_AT, endAt)
            .set(RESERVATIONS.GUEST_COUNT, 2.toShort())
            .set(RESERVATIONS.PRICE, 100_000)
            .set(RESERVATIONS.STATUS, status)
            .returningResult(RESERVATIONS.ID)
            .fetchOne()!!
            .value1()

    private fun tokenFor(accountId: Long, vararg roles: String): String {
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer("lookation")
            .subject(accountId.toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(300))
            .claim("roles", roles.toList())
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    private fun postMap(path: String, body: Map<String, Any>, token: String?): ApiResponse =
        try {
            var request = client.post().uri(path).contentType(MediaType.APPLICATION_JSON)
            if (token != null) request = request.header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            val entity = request.body(body).retrieve().toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }

    private fun getMap(path: String, token: String?): ApiResponse =
        try {
            var request = client.get().uri(path)
            if (token != null) request = request.header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            val entity = request.retrieve().toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }

    private fun delete(path: String, token: String): ApiResponse =
        try {
            val entity = client.delete().uri(path).header(HttpHeaders.AUTHORIZATION, "Bearer $token").retrieve().toBodilessEntity()
            ApiResponse(entity.statusCode, null)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }
}
