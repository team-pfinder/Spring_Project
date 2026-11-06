package com.lookation.view

import com.lookation.controller.form.LoginForm
import com.lookation.controller.form.PasswordResetConfirmForm
import com.lookation.controller.form.SignupForm
import com.lookation.global.error.ApiException
import com.lookation.global.security.AuthCookies
import com.lookation.service.AuthService
import com.lookation.service.TokenService
import com.lookation.view.form.LoginViewForm
import com.lookation.view.form.PasswordResetRequestViewForm
import com.lookation.view.form.PasswordResetViewForm
import com.lookation.view.form.SignupRequestViewForm
import com.lookation.view.form.SignupViewForm
import io.swagger.v3.oas.annotations.Hidden
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * HTML 인증 화면. 로그인·가입·비밀번호 재설정·로그아웃을 담당한다.
 *
 * 성공하면 access/refresh 쿠키를 발급하고, 실패하면 같은 화면을 메시지와 함께 다시 렌더한다.
 * `@Controller`가 아니라 view 패키지에 있는 이유는 `ApiExceptionHandler`가 HTML 예외를 JSON으로
 * 바꾸지 않게 하기 위해서다.
 */
@Hidden
@Controller
class HtmlAuthController(
    private val authService: AuthService,
    private val tokenService: TokenService,
    private val authCookies: AuthCookies,
) {

    @GetMapping("/login")
    fun loginPage(
        @RequestParam(required = false) next: String?,
        @RequestParam(required = false) expired: String?,
        @RequestParam(required = false) loggedOut: String?,
        @RequestParam(required = false) reset: String?,
        model: Model,
    ): String {
        model.addAttribute("form", LoginViewForm(next = safeNext(next)))
        if (next != null && next != safeNext(next)) {
            model.addAttribute("error", "이동할 수 없는 경로입니다")
        }
        if (expired != null) model.addAttribute("notice", "로그인이 만료되었습니다. 다시 로그인해 주세요.")
        if (loggedOut != null) model.addAttribute("notice", "로그아웃되었습니다.")
        if (reset != null) model.addAttribute("notice", "비밀번호가 변경되었습니다. 새 비밀번호로 로그인해 주세요.")
        return "auth/login"
    }

    @PostMapping("/login")
    fun login(
        @Valid @ModelAttribute("form") form: LoginViewForm,
        bindingResult: BindingResult,
        response: HttpServletResponse,
        model: Model,
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("form", form.copy(password = ""))
            return "auth/login"
        }
        return try {
            val pair = authService.login(LoginForm(email = form.email, password = form.password))
            authCookies.write(response, pair.accessToken, pair.refreshToken)
            "redirect:${safeNext(form.next)}"
        } catch (exception: ApiException) {
            model.addAttribute("error", exception.message)
            model.addAttribute("form", form.copy(password = ""))
            "auth/login"
        }
    }

    @GetMapping("/signup")
    fun signupPage(
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false) sent: String?,
        model: Model,
    ): String {
        val normalizedEmail = email.orEmpty()
        model.addAttribute("form", SignupViewForm(email = normalizedEmail))
        model.addAttribute("emailForm", SignupRequestViewForm(email = normalizedEmail))
        if (sent != null) model.addAttribute("notice", "인증 코드를 보냈습니다. 메일함을 확인해 주세요.")
        return "auth/signup"
    }

    @PostMapping("/signup/email-verification")
    fun requestSignupCode(
        @Valid @ModelAttribute("emailForm") emailForm: SignupRequestViewForm,
        bindingResult: BindingResult,
        model: Model,
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("form", SignupViewForm(email = emailForm.email))
            return "auth/signup"
        }
        return try {
            authService.requestVerification(emailForm.email, "SIGNUP")
            "redirect:/signup?email=${urlEncode(emailForm.email)}&sent=1"
        } catch (exception: ApiException) {
            model.addAttribute("error", exception.message)
            model.addAttribute("form", SignupViewForm(email = emailForm.email))
            "auth/signup"
        }
    }

    @PostMapping("/signup")
    fun signup(
        @Valid @ModelAttribute("form") form: SignupViewForm,
        bindingResult: BindingResult,
        response: HttpServletResponse,
        model: Model,
    ): String {
        model.addAttribute("emailForm", SignupRequestViewForm(email = form.email))
        if (bindingResult.hasErrors()) {
            return "auth/signup"
        }
        return try {
            val pair = authService.signup(
                SignupForm(
                    email = form.email,
                    password = form.password,
                    name = form.name,
                    nickname = form.nickname,
                    code = form.code,
                ),
            )
            authCookies.write(response, pair.accessToken, pair.refreshToken)
            "redirect:/spaces?welcome=1"
        } catch (exception: ApiException) {
            model.addAttribute("error", exception.message)
            model.addAttribute("form", form.copy(password = ""))
            "auth/signup"
        }
    }

    @GetMapping("/password-reset")
    fun passwordResetPage(
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false) sent: String?,
        model: Model,
    ): String {
        val normalizedEmail = email.orEmpty()
        model.addAttribute("emailForm", PasswordResetRequestViewForm(email = normalizedEmail))
        model.addAttribute("form", PasswordResetViewForm(email = normalizedEmail))
        if (sent != null) model.addAttribute("notice", "재설정 코드를 보냈습니다. 메일함을 확인해 주세요.")
        return "auth/password-reset"
    }

    @PostMapping("/password-reset")
    fun requestPasswordReset(
        @Valid @ModelAttribute("emailForm") emailForm: PasswordResetRequestViewForm,
        bindingResult: BindingResult,
        model: Model,
    ): String {
        if (bindingResult.hasErrors()) {
            model.addAttribute("form", PasswordResetViewForm(email = emailForm.email))
            return "auth/password-reset"
        }
        return try {
            authService.requestVerification(emailForm.email, "PASSWORD_RESET")
            "redirect:/password-reset?email=${urlEncode(emailForm.email)}&sent=1"
        } catch (exception: ApiException) {
            model.addAttribute("error", exception.message)
            model.addAttribute("form", PasswordResetViewForm(email = emailForm.email))
            "auth/password-reset"
        }
    }

    @PostMapping("/password-reset/confirm")
    fun confirmPasswordReset(
        @Valid @ModelAttribute("form") form: PasswordResetViewForm,
        bindingResult: BindingResult,
        model: Model,
    ): String {
        model.addAttribute("emailForm", PasswordResetRequestViewForm(email = form.email))
        if (bindingResult.hasErrors()) {
            return "auth/password-reset"
        }
        return try {
            authService.resetPassword(
                PasswordResetConfirmForm(
                    email = form.email,
                    code = form.code,
                    newPassword = form.newPassword,
                ),
            )
            "redirect:/login?reset=1"
        } catch (exception: ApiException) {
            model.addAttribute("error", exception.message)
            model.addAttribute("form", form.copy(newPassword = ""))
            "auth/password-reset"
        }
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): String {
        authCookies.readRefresh(request)?.let { tokenService.revokeFamily(it) }
        authCookies.clear(response)
        return "redirect:/login?loggedOut=1"
    }
}
