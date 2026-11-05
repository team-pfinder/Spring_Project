package com.lookation.view

import com.lookation.global.security.AuthCookies
import com.lookation.service.TokenService
import io.swagger.v3.oas.annotations.Hidden
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping

/**
 * HTML 체인의 인증 화면 중 로그아웃만 담당한다.
 *
 * 로그인·가입·비밀번호 재설정 화면은 #32에서 추가한다. 이 컨트롤러가 `controller`가 아니라
 * `view` 패키지에 있는 이유는 `ApiExceptionHandler`가 HTML 예외를 JSON으로 바꾸지 않게 하기 위해서다.
 */
@Hidden
@Controller
class HtmlAuthController(
    private val tokenService: TokenService,
    private val authCookies: AuthCookies,
) {

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): String {
        authCookies.readRefresh(request)?.let { tokenService.revokeFamily(it) }
        authCookies.clear(response)
        return "redirect:/login?loggedOut=1"
    }
}
