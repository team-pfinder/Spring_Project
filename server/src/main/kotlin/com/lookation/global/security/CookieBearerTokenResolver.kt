package com.lookation.global.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver
import org.springframework.stereotype.Component

/**
 * HTML 체인 전용 bearer 리졸버. `Authorization` 헤더는 보지 않고 쿠키만 읽는다.
 * API 체인은 [org.springframework.security.oauth2.server.resource.web.HeaderBearerTokenResolver]를 쓴다.
 */
@Component
class CookieBearerTokenResolver(private val authCookies: AuthCookies) : BearerTokenResolver {

    override fun resolve(request: HttpServletRequest): String? = authCookies.readAccess(request)
}
