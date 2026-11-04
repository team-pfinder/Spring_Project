package com.lookation.integration

import com.lookation.jooq.tables.Accounts.ACCOUNTS
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
class AdminFlowTest {

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
    private data class TestUser(val id: Long, val access: String)

    @Test
    fun `sanction blocks writes but allows login and can be revoked`() {
        val member = signup()
        val adminToken = tokenFor(account("admin"), "ADMIN")

        val applied = post(
            "/api/v1/admin/sanctions",
            mapOf(
                "accountId" to member.id,
                "level" to 1,
                "reason" to "스팸",
                "endsAt" to OffsetDateTime.now().plusDays(7).toString(),
            ),
            adminToken,
        )
        assertThat(applied.status.value()).isEqualTo(HttpStatus.OK.value())
        val sanctionId = (applied.body!!["id"] as Number).toLong()

        assertThat(get("/api/v1/me", member.access).status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(
            post("/api/v1/wallet/charges", mapOf("amount" to 10_000, "depositorName" to "홍길동"), member.access).status.value(),
        ).isEqualTo(HttpStatus.FORBIDDEN.value())

        assertThat(delete("/api/v1/admin/sanctions/$sanctionId", adminToken).status.value())
            .isEqualTo(HttpStatus.NO_CONTENT.value())
        assertThat(
            post("/api/v1/wallet/charges", mapOf("amount" to 10_000, "depositorName" to "홍길동"), member.access).status.value(),
        ).isEqualTo(HttpStatus.OK.value())
    }

    @Test
    fun `admin manages notices and helps`() {
        val adminToken = tokenFor(account("admin"), "ADMIN")
        val notice = post(
            "/api/v1/admin/notices",
            mapOf("title" to "점검 안내", "content" to "새벽 점검", "important" to true),
            adminToken,
        )
        assertThat(notice.status.value()).isEqualTo(HttpStatus.OK.value())
        val noticeId = (notice.body!!["id"] as Number).toLong()

        val notices = get("/api/v1/notices", null).body!!["items"] as List<*>
        assertThat(notices.map { (it as Map<*, *>)["title"] }).contains("점검 안내")
        assertThat(get("/api/v1/notices/$noticeId", null).body!!["important"]).isEqualTo(true)

        assertThat(put("/api/v1/admin/notices/$noticeId", mapOf("title" to "점검 완료", "content" to "완료", "important" to false), adminToken).status.value())
            .isEqualTo(HttpStatus.OK.value())

        val help = post("/api/v1/admin/helps", mapOf("category" to "결제", "title" to "환불 규정", "content" to "7일 전"), adminToken)
        assertThat(help.status.value()).isEqualTo(HttpStatus.OK.value())
        val helps = get("/api/v1/helps?category=결제", null).body!!["items"] as List<*>
        assertThat(helps).hasSize(1)

        assertThat(delete("/api/v1/admin/notices/$noticeId", adminToken).status.value()).isEqualTo(HttpStatus.NO_CONTENT.value())
    }

    @Test
    fun `report is recorded once and decided by an admin`() {
        val member = signup()
        val spaceId = publishedSpace(account("host"))
        val adminToken = tokenFor(account("admin"), "ADMIN")

        val report = post("/api/v1/reports", mapOf("targetType" to "SPACE", "targetId" to spaceId, "reason" to "허위 정보"), member.access)
        assertThat(report.status.value()).isEqualTo(HttpStatus.OK.value())
        val reportId = (report.body!!["id"] as Number).toLong()

        assertThat(
            post("/api/v1/reports", mapOf("targetType" to "SPACE", "targetId" to spaceId, "reason" to "또"), member.access).status.value(),
        ).isEqualTo(HttpStatus.CONFLICT.value())

        val pending = get("/api/v1/admin/reports?status=RECEIVED", adminToken).body!!["items"] as List<*>
        assertThat(pending).hasSize(1)

        val decided = post("/api/v1/admin/reports/$reportId/decision", mapOf("accepted" to true), adminToken)
        assertThat(decided.body!!["status"]).isEqualTo("ACCEPTED")
    }

    private fun signup(): TestUser {
        val email = "admin-${UUID.randomUUID()}@lookation.test"
        post("/api/v1/auth/email-verifications", mapOf("email" to email, "purpose" to "SIGNUP"), null)
        val code = mailSender.codes.getValue(email)
        val signup = post(
            "/api/v1/auth/signup",
            mapOf("email" to email, "password" to "password123", "name" to "Member", "nickname" to "member", "code" to code),
            null,
        )
        val access = signup.body!!["accessToken"] as String
        val me = get("/api/v1/me", access)
        return TestUser((me.body!!["id"] as Number).toLong(), access)
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
            .set(SPACES.NAME, "신고 공간")
            .set(SPACES.ADDRESS, "서울")
            .set(SPACES.SPACE_TYPE, "PARTY_ROOM")
            .set(SPACES.MIN_CAPACITY, 1.toShort())
            .set(SPACES.MAX_CAPACITY, 10.toShort())
            .set(SPACES.STATUS, "PUBLISHED")
            .returningResult(SPACES.ID)
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

    private fun post(path: String, body: Map<String, Any>, token: String?): ApiResponse =
        try {
            var request = client.post().uri(path).contentType(MediaType.APPLICATION_JSON)
            if (token != null) request = request.header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            val entity = request.body(body).retrieve().toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }

    private fun put(path: String, body: Map<String, Any>, token: String): ApiResponse =
        try {
            val entity = client.put().uri(path).header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON).body(body).retrieve().toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }

    private fun get(path: String, token: String?): ApiResponse =
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
