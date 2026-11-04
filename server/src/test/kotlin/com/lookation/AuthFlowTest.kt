package com.lookation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
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

    private val client by lazy { RestClient.create("http://localhost:$port") }

    private data class ApiResponse(val status: HttpStatusCode, val body: Map<String, Any>?, val rawBody: String? = null)

    @Test
    fun `signup issues tokens and me returns the account`() {
        val signup = post(
            "/api/v1/auth/signup",
            mapOf(
                "email" to "flow1@lookation.test",
                "password" to "password123",
                "name" to "Flow",
                "nickname" to "flow1",
            ),
        )
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
    fun `duplicate email is rejected with conflict`() {
        val form = mapOf(
            "email" to "flow2@lookation.test",
            "password" to "password123",
            "name" to "Flow",
            "nickname" to "flow2",
        )
        assertThat(post("/api/v1/auth/signup", form).status.value())
            .isEqualTo(HttpStatus.OK.value())
        assertThat(post("/api/v1/auth/signup", form).status.value())
            .isEqualTo(HttpStatus.CONFLICT.value())
    }

    @Test
    fun `login with the correct password issues tokens`() {
        post(
            "/api/v1/auth/signup",
            mapOf(
                "email" to "flow5@lookation.test",
                "password" to "password123",
                "name" to "Flow",
                "nickname" to "flow5",
            ),
        )

        val login = post(
            "/api/v1/auth/login",
            mapOf("email" to "flow5@lookation.test", "password" to "password123"),
        )

        assertThat(login.status.value())
            .withFailMessage("login status=%s body=%s", login.status, login.rawBody)
            .isEqualTo(HttpStatus.OK.value())
        assertThat(login.body!!["accessToken"]).isNotNull()
    }

    @Test
    fun `wrong password is rejected with unauthorized`() {
        post(
            "/api/v1/auth/signup",
            mapOf(
                "email" to "flow3@lookation.test",
                "password" to "password123",
                "name" to "Flow",
                "nickname" to "flow3",
            ),
        )

        val result = post(
            "/api/v1/auth/login",
            mapOf("email" to "flow3@lookation.test", "password" to "wrong-password"),
        )
        assertThat(result.status.value()).isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

    @Test
    fun `refresh rotates the token and rejects reuse`() {
        val signup = post(
            "/api/v1/auth/signup",
            mapOf(
                "email" to "flow4@lookation.test",
                "password" to "password123",
                "name" to "Flow",
                "nickname" to "flow4",
            ),
        )
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
