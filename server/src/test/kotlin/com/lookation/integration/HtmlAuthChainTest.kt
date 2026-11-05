package com.lookation.integration

import com.lookation.global.error.UnauthorizedException
import com.lookation.global.seed.InitializeService
import com.lookation.global.seed.SeedStep
import com.lookation.global.security.AuthCookies
import com.lookation.repository.AccountRepository
import com.lookation.service.TokenService
import jakarta.servlet.http.Cookie
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.time.Duration
import java.time.Instant

@SpringBootTest(
    properties = [
        "lookation.jwt.secret=0123456789abcdef0123456789abcdef",
        "lookation.encryption.key=AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQE=",
        "lookation.upload-dir=build/test-uploads",
        "lookation.test.suite=html-auth-chain",
    ],
)
@AutoConfigureMockMvc
@Import(TestMailConfig::class)
@Testcontainers
class HtmlAuthChainTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:18-alpine"))
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var steps: List<SeedStep>

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var jwtEncoder: JwtEncoder

    @BeforeEach
    fun seed() {
        InitializeService(steps).seed()
    }

    @Test
    fun `비로그인 HTML 요청은 로그인으로 리다이렉트된다`() {
        val result = mockMvc.perform(get("/"))
            .andExpect(status().isFound)
            .andExpect(header().string("Location", "/login?next=%2F"))
            .andReturn()

        assertThat(result.response.getCookie("JSESSIONID")).isNull()
    }

    @Test
    fun `API 체인은 쿠키 토큰을 인정하지 않는다`() {
        val pair = tokenService.issue(memberId(), listOf("MEMBER"))

        mockMvc.perform(get("/api/v1/me").cookie(Cookie(AuthCookies.ACCESS_COOKIE, pair.accessToken)))
            .andExpect(status().isUnauthorized)

        mockMvc.perform(
            get("/api/v1/me").header("Authorization", "Bearer ${pair.accessToken}"),
        ).andExpect(status().isOk)
    }

    @Test
    fun `HTML 체인은 쿠키 토큰을 인정한다`() {
        val pair = tokenService.issue(memberId(), listOf("MEMBER"))

        mockMvc.perform(get("/").cookie(Cookie(AuthCookies.ACCESS_COOKIE, pair.accessToken)))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `CSRF 토큰이 없는 HTML POST는 403이다`() {
        val pair = tokenService.issue(memberId(), listOf("MEMBER"))

        mockMvc.perform(
            post("/logout").cookie(Cookie(AuthCookies.REFRESH_COOKIE, pair.refreshToken)),
        ).andExpect(status().isForbidden)
    }

    @Test
    fun `CSRF 토큰이 있으면 로그아웃되고 refresh family가 폐기된다`() {
        val pair = tokenService.issue(memberId(), listOf("MEMBER"))

        val result = mockMvc.perform(
            post("/logout")
                .with(csrf())
                .cookie(Cookie(AuthCookies.REFRESH_COOKIE, pair.refreshToken)),
        )
            .andExpect(status().isFound)
            .andExpect(header().string("Location", "/login?loggedOut=1"))
            .andReturn()

        assertThat(result.response.getHeaders("Set-Cookie"))
            .anyMatch { it.startsWith("${AuthCookies.ACCESS_COOKIE}=;") && it.contains("Max-Age=0") }
        assertThat(result.response.getHeaders("Set-Cookie"))
            .anyMatch { it.startsWith("${AuthCookies.REFRESH_COOKIE}=;") && it.contains("Max-Age=0") }

        assertThatThrownBy { tokenService.rotate(pair.refreshToken) }
            .isInstanceOf(UnauthorizedException::class.java)
    }

    @Test
    fun `access가 만료되면 refresh 쿠키로 새 쿠키를 발급한다`() {
        val pair = tokenService.issue(memberId(), listOf("MEMBER"))
        val expiredAccess = expiredAccessToken()

        val result = mockMvc.perform(
            get("/")
                .cookie(
                    Cookie(AuthCookies.ACCESS_COOKIE, expiredAccess),
                    Cookie(AuthCookies.REFRESH_COOKIE, pair.refreshToken),
                ),
        )
            .andExpect(status().isNotFound)
            .andReturn()

        assertThat(result.response.getHeaders("Set-Cookie"))
            .anyMatch { it.startsWith("${AuthCookies.ACCESS_COOKIE}=") && !it.contains("Max-Age=0") }
    }

    @Test
    fun `두 탭이 같은 refresh로 동시에 갱신해도 family가 유지된다`() {
        val first = tokenService.issue(memberId(), listOf("MEMBER"))
        val second = tokenService.rotate(first.refreshToken)
        val third = tokenService.rotate(first.refreshToken, Duration.ofSeconds(10))

        assertThat(third.refreshToken).isNotEqualTo(second.refreshToken)
        assertThat(tokenService.rotate(second.refreshToken).accessToken).isNotBlank()
    }

    @Test
    fun `로그아웃으로 폐기된 refresh는 유예 없이 실패한다`() {
        val pair = tokenService.issue(memberId(), listOf("MEMBER"))
        tokenService.revokeFamily(pair.refreshToken)

        assertThatThrownBy { tokenService.rotate(pair.refreshToken) }
            .isInstanceOf(UnauthorizedException::class.java)
    }

    private fun memberId(): Long =
        requireNotNull(accountRepository.findByEmail("seed-member-1@lookation.local")).id

    private fun expiredAccessToken(): String {
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer("lookation")
            .subject(memberId().toString())
            .issuedAt(now.minusSeconds(3600))
            .expiresAt(now.minusSeconds(60))
            .claim("roles", listOf("MEMBER"))
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}
