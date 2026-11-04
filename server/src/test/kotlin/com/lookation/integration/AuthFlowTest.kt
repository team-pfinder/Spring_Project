package com.lookation.integration

import org.assertj.core.api.Assertions.assertThat
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
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientResponseException
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

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
class AuthFlowTest {

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
    private lateinit var mailSender: CapturingMailSender

    private val client by lazy { RestClient.create("http://localhost:$port") }

    private data class ApiResponse(val status: HttpStatusCode, val body: Map<String, Any>?, val raw: String? = null)

    @Test
    fun `signup issues tokens and me returns the account`() {
        val signup = post("/api/v1/auth/signup", signupPayload("flow1@lookation.test", nickname = "flow1"))
        assertThat(signup.status.value()).isEqualTo(HttpStatus.OK.value())
        val accessToken = signup.body!!["accessToken"] as String
        assertThat(accessToken).isNotBlank()

        val me = client.get()
            .uri("/api/v1/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .retrieve()
            .toEntity(mapType)

        assertThat(me.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(me.body!!["email"]).isEqualTo("flow1@lookation.test")
        assertThat(me.body!!["roles"] as List<*>).contains("MEMBER")
    }

    @Test
    fun `login with the correct password issues tokens`() {
        post("/api/v1/auth/signup", signupPayload("flow5@lookation.test", nickname = "flow5"))
        val login = post(
            "/api/v1/auth/login",
            mapOf("email" to "flow5@lookation.test", "password" to "password123"),
        )
        assertThat(login.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(login.body!!["accessToken"]).isNotNull()
    }

    @Test
    fun `duplicate email is rejected with conflict`() {
        val form = signupPayload("flow2@lookation.test", nickname = "flow2")
        assertThat(post("/api/v1/auth/signup", form).status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(post("/api/v1/auth/signup", form).status.value()).isEqualTo(HttpStatus.CONFLICT.value())
    }

    @Test
    fun `wrong password is rejected with unauthorized`() {
        post("/api/v1/auth/signup", signupPayload("flow3@lookation.test", nickname = "flow3"))
        val result = post(
            "/api/v1/auth/login",
            mapOf("email" to "flow3@lookation.test", "password" to "wrong-password"),
        )
        assertThat(result.status.value()).isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

    @Test
    fun `refresh rotates the token and rejects reuse`() {
        val signup = post("/api/v1/auth/signup", signupPayload("flow4@lookation.test", nickname = "flow4"))
        val refreshToken = signup.body!!["refreshToken"] as String

        val refreshed = post("/api/v1/auth/refresh", mapOf("refreshToken" to refreshToken))
        assertThat(refreshed.status.value()).isEqualTo(HttpStatus.OK.value())
        val rotated = refreshed.body!!["refreshToken"] as String
        assertThat(rotated).isNotEqualTo(refreshToken)

        assertThat(post("/api/v1/auth/refresh", mapOf("refreshToken" to refreshToken)).status.value())
            .isEqualTo(HttpStatus.UNAUTHORIZED.value())
        assertThat(post("/api/v1/auth/refresh", mapOf("refreshToken" to rotated)).status.value())
            .isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

    @Test
    fun `signup without the verification code is rejected`() {
        val result = post(
            "/api/v1/auth/signup",
            mapOf(
                "email" to "nocode@lookation.test",
                "password" to "password123",
                "name" to "NoCode",
                "nickname" to "nocode",
            ),
        )
        assertThat(result.status.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `wrong verification code is rejected`() {
        val email = "wrongcode@lookation.test"
        assertThat(post("/api/v1/auth/email-verifications", mapOf("email" to email, "purpose" to "SIGNUP")).status.value())
            .isEqualTo(HttpStatus.NO_CONTENT.value())
        val code = mailSender.codes.getValue(email)
        val wrong = if (code == "000000") "111111" else "000000"

        val result = post(
            "/api/v1/auth/signup",
            mapOf(
                "email" to email,
                "password" to "password123",
                "name" to "Wrong",
                "nickname" to "wrong",
                "code" to wrong,
            ),
        )
        assertThat(result.status.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `password reset changes the password`() {
        val email = "reset@lookation.test"
        post("/api/v1/auth/signup", signupPayload(email, nickname = "reset"))

        assertThat(
            post("/api/v1/auth/password-reset/request", mapOf("email" to email)).status.value(),
        ).isEqualTo(HttpStatus.NO_CONTENT.value())
        val code = mailSender.codes.getValue(email)

        assertThat(
            post(
                "/api/v1/auth/password-reset/confirm",
                mapOf("email" to email, "code" to code, "newPassword" to "newpassword123"),
            ).status.value(),
        ).isEqualTo(HttpStatus.NO_CONTENT.value())

        assertThat(
            post("/api/v1/auth/login", mapOf("email" to email, "password" to "password123")).status.value(),
        ).isEqualTo(HttpStatus.UNAUTHORIZED.value())
        assertThat(
            post("/api/v1/auth/login", mapOf("email" to email, "password" to "newpassword123")).status.value(),
        ).isEqualTo(HttpStatus.OK.value())
    }

    private fun signupPayload(email: String, nickname: String): Map<String, Any> {
        post("/api/v1/auth/email-verifications", mapOf("email" to email, "purpose" to "SIGNUP"))
        val code = mailSender.codes.getValue(email)
        return mapOf(
            "email" to email,
            "password" to "password123",
            "name" to "Flow",
            "nickname" to nickname,
            "code" to code,
        )
    }

    private fun post(path: String, body: Map<String, Any>): ApiResponse =
        try {
            val entity = client.post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }
}
