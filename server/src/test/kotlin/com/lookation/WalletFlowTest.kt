package com.lookation

import com.lookation.jooq.tables.AccountRoles.ACCOUNT_ROLES
import com.lookation.jooq.tables.Accounts.ACCOUNTS
import com.lookation.jooq.tables.Wallets.WALLETS
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
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
class WalletFlowTest {

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
    fun `admin approval credits the requested charge`() {
        val user = signup()
        val charge = post(
            "/api/v1/wallet/charges",
            mapOf("amount" to 50_000, "depositorName" to "홍길동"),
            user.access,
        )
        assertThat(charge.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(charge.body!!["status"]).isEqualTo("REQUESTED")
        val chargeId = (charge.body["id"] as Number).toLong()

        val approved = post(
            "/api/v1/admin/charges/$chargeId/decision",
            mapOf("approved" to true),
            adminToken(),
        )
        assertThat(approved.body!!["status"]).isEqualTo("APPROVED")
        assertThat(balance(user.id)).isEqualTo(50_000)
    }

    @Test
    fun `rejected withdrawal returns the held amount`() {
        val host = signup()
        post("/api/v1/hosts/apply", hostForm(), host.access)
        val hostToken = refresh(host)
        topUp(host.id, 100_000)

        val withdrawal = post("/api/v1/withdrawals", mapOf("amount" to 40_000), hostToken)
        assertThat(withdrawal.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(withdrawal.body!!["status"]).isEqualTo("REQUESTED")
        assertThat(balance(host.id)).isEqualTo(60_000)
        val withdrawalId = (withdrawal.body["id"] as Number).toLong()

        val rejected = post(
            "/api/v1/admin/withdrawals/$withdrawalId/decision",
            mapOf("approved" to false),
            adminToken(),
        )
        assertThat(rejected.body!!["status"]).isEqualTo("REJECTED")
        assertThat(balance(host.id)).isEqualTo(100_000)
    }

    @Test
    fun `withdrawal without a host profile is rejected`() {
        val user = signup()
        topUp(user.id, 100_000)
        val result = post("/api/v1/withdrawals", mapOf("amount" to 10_000), user.access)
        assertThat(result.status.value()).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    private fun hostForm() = mapOf(
        "businessName" to "테스트 주식회사",
        "businessNumber" to (1_000_000_000L + (Math.random() * 8_999_999_999L).toLong()).toString(),
        "representativeName" to "홍길동",
        "bankName" to "국민은행",
        "bankAccountNumber" to "12345678901234",
        "bankAccountHolder" to "홍길동",
    )

    private fun signup(): TestUser {
        val email = "wallet-${UUID.randomUUID()}@lookation.test"
        val signup = post(
            "/api/v1/auth/signup",
            mapOf(
                "email" to email,
                "password" to "password123",
                "name" to "Wallet",
                "nickname" to "wallet",
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
        val email = dsl.select(ACCOUNTS.EMAIL).from(ACCOUNTS)
            .where(ACCOUNTS.ID.eq(user.id)).fetchOne(ACCOUNTS.EMAIL)!!
        return post("/api/v1/auth/login", mapOf("email" to email, "password" to "password123"))
            .body!!["accessToken"] as String
    }

    private fun topUp(accountId: Long, amount: Long) {
        dsl.insertInto(WALLETS)
            .set(WALLETS.ACCOUNT_ID, accountId)
            .set(WALLETS.BALANCE, amount)
            .execute()
    }

    private fun balance(accountId: Long): Long =
        dsl.select(WALLETS.BALANCE).from(WALLETS)
            .where(WALLETS.ACCOUNT_ID.eq(accountId))
            .fetchOne(WALLETS.BALANCE) ?: 0L

    private fun get(path: String, token: String?): ApiResponse =
        try {
            var request = client.get().uri(path)
            if (token != null) request = request.header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            val entity = request.retrieve().toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }

    private fun post(path: String, body: Map<String, Any>, token: String? = null): ApiResponse =
        try {
            var request = client.post().uri(path).contentType(MediaType.APPLICATION_JSON)
            if (token != null) request = request.header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            val entity = request.body(body).retrieve().toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null, exception.responseBodyAsString)
        }
}
