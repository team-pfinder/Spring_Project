package com.lookation.global.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import java.time.Duration

/**
 * HTML 체인의 access/refresh 쿠키를 읽고 쓴다.
 *
 * access 쿠키는 `BearerTokenResolver`가, refresh 쿠키는 [AccessTokenRefreshFilter]와 로그아웃이 읽는다.
 * refresh는 JS에서 읽을 필요가 없으므로 두 쿠키 모두 `HttpOnly`다.
 */
@Component
class AuthCookies(
    private val htmlProperties: HtmlProperties,
    private val jwtProperties: JwtProperties,
) {

    fun readAccess(request: HttpServletRequest): String? =
        request.getAttribute(REFRESHED_ACCESS_ATTRIBUTE) as String?
            ?: read(request, ACCESS_COOKIE)

    fun readRefresh(request: HttpServletRequest): String? = read(request, REFRESH_COOKIE)

    fun write(response: HttpServletResponse, accessToken: String, refreshToken: String) {
        add(response, ACCESS_COOKIE, accessToken, jwtProperties.accessTtl)
        add(response, REFRESH_COOKIE, refreshToken, jwtProperties.refreshTtl)
    }

    fun clear(response: HttpServletResponse) {
        add(response, ACCESS_COOKIE, "", Duration.ZERO)
        add(response, REFRESH_COOKIE, "", Duration.ZERO)
    }

    private fun read(request: HttpServletRequest, name: String): String? =
        request.cookies
            ?.firstOrNull { it.name == name }
            ?.value
            ?.takeIf { it.isNotBlank() }

    private fun add(response: HttpServletResponse, name: String, value: String, maxAge: Duration) {
        val cookie = ResponseCookie.from(name, value)
            .httpOnly(true)
            .secure(htmlProperties.secureCookie)
            .sameSite("Lax")
            .path("/")
            .maxAge(maxAge)
            .build()
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
    }

    companion object {
        const val ACCESS_COOKIE = "access_token"
        const val REFRESH_COOKIE = "refresh_token"

        /** 갱신 필터가 새 access 토큰을 요청에 실어 보낼 때 쓰는 속성 이름. */
        const val REFRESHED_ACCESS_ATTRIBUTE = "lookation.refreshedAccessToken"
    }
}
