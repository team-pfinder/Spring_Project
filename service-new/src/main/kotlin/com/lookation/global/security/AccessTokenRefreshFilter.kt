package com.lookation.global.security

import com.lookation.global.error.UnauthorizedException
import com.lookation.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.web.filter.OncePerRequestFilter
import java.time.Instant

/**
 * HTML 체인의 투명 갱신 필터.
 *
 * access 쿠키가 없거나 만료(또는 30초 내 만료)면 refresh 쿠키로 새 쌍을 발급하고 응답 쿠키를 갱신한다.
 * 새 access 토큰은 [AuthCookies.REFRESHED_ACCESS_ATTRIBUTE]에 실어, 같은 요청의 bearer 리졸버가 읽게 한다.
 *
 * refresh도 만료·폐기면 쿠키를 지우고 로그인 화면으로 보낸다. `/login` 등 인증 화면은 건너뛰어
 * 리다이렉트 루프를 만들지 않는다.
 */
class AccessTokenRefreshFilter(
    private val tokenService: TokenService,
    private val jwtDecoder: JwtDecoder,
    private val authCookies: AuthCookies,
    private val htmlProperties: HtmlProperties,
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return SKIP_PREFIXES.any { path == it || path.startsWith("$it/") }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val accessToken = authCookies.readAccess(request)
        if (accessToken != null && !isExpiringSoon(accessToken)) {
            filterChain.doFilter(request, response)
            return
        }

        val refreshToken = authCookies.readRefresh(request)
        if (refreshToken == null) {
            filterChain.doFilter(request, response)
            return
        }

        val pair = try {
            tokenService.rotate(refreshToken, htmlProperties.reuseGrace)
        } catch (exception: UnauthorizedException) {
            authCookies.clear(response)
            // 공개 화면은 쿠키가 깨졌어도 그대로 보여 준다. 보호 화면만 로그인으로 보낸다.
            if (isPublicPage(request)) {
                filterChain.doFilter(request, response)
                return
            }
            response.sendRedirect("/login?expired=1")
            return
        }

        authCookies.write(response, pair.accessToken, pair.refreshToken)
        request.setAttribute(AuthCookies.REFRESHED_ACCESS_ATTRIBUTE, pair.accessToken)
        filterChain.doFilter(request, response)
    }

    private fun isExpiringSoon(token: String): Boolean =
        try {
            val expiresAt = jwtDecoder.decode(token).expiresAt
            expiresAt == null || expiresAt.isBefore(Instant.now().plus(EXPIRY_SKEW))
        } catch (exception: JwtException) {
            true
        }

    private fun isPublicPage(request: HttpServletRequest): Boolean {
        if (request.method != "GET") return false
        val path = request.requestURI
        return path == "/" || path == "/spaces" || PUBLIC_SPACE_PATH.matches(path)
    }

    companion object {
        private val EXPIRY_SKEW = java.time.Duration.ofSeconds(30)
        private val PUBLIC_SPACE_PATH = Regex("^/spaces/\\d+$")

        /** 정적 자산과 인증 화면은 갱신 대상이 아니다. */
        private val SKIP_PREFIXES = listOf(
            "/css",
            "/js",
            "/images",
            "/fonts",
            "/uploads",
            "/favicon.ico",
            "/error",
            "/login",
            "/signup",
            "/password-reset",
            "/actuator",
        )
    }
}
