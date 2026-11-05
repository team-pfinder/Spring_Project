package com.lookation.global.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * HTML 체인의 인증 진입점. API의 401 JSON 대신 로그인 화면으로 보낸다.
 *
 * GET 요청은 원래 경로를 `next`로 넘겨 로그인 후 되돌아올 수 있게 한다.
 * 열린 리다이렉트를 막기 위해 `/`로 시작하고 `//`로 시작하지 않는 경로만 허용한다.
 */
class LoginRedirectEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        val next = nextPath(request)
        val location = if (next == null) "/login" else "/login?next=" + encode(next)
        response.sendRedirect(location)
    }

    private fun nextPath(request: HttpServletRequest): String? {
        if (request.method != "GET") return null
        val uri = request.requestURI ?: return null
        if (!uri.startsWith("/") || uri.startsWith("//")) return null
        if (uri == "/login") return null
        val query = request.queryString
        return if (query.isNullOrBlank()) uri else "$uri?$query"
    }

    private fun encode(value: String): String =
        URLEncoder.encode(value, StandardCharsets.UTF_8)
}
