package com.lookation.controller

import com.lookation.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.lookation.dto.TokenDto
import com.lookation.controller.form.PasswordResetConfirmForm
import com.lookation.controller.form.PasswordResetRequestForm
import com.lookation.controller.form.EmailVerificationForm
import com.lookation.controller.form.RefreshForm
import com.lookation.controller.form.LoginForm
import com.lookation.controller.form.SignupForm

@Tag(
    name = "인증",
    description = "회원가입·로그인·토큰 재발급·비밀번호 재설정. 여기서 받은 `accessToken`을 우측 상단 Authorize에 넣으면 보호된 API를 호출할 수 있습니다.",
)
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @Operation(
        summary = "회원가입",
        description = "이메일 인증을 마친 뒤 계정을 만들고 토큰을 즉시 발급합니다. 인증 코드는 `/email-verifications`로 먼저 요청해야 하며, 코드가 틀리거나 만료되면 400입니다. 이미 가입된 이메일이면 409입니다.",
    )
    @PostMapping("/signup")
    fun signup(@Valid @RequestBody form: SignupForm): TokenDto =
        authService.signup(form).toResponse()

    @Operation(
        summary = "로그인",
        description = "이메일·비밀번호로 `accessToken`(15분)과 `refreshToken`(14일)을 발급합니다. 자격 증명이 틀리면 401이고, 탈퇴한 계정도 401입니다.",
    )
    @PostMapping("/login")
    fun login(@Valid @RequestBody form: LoginForm): TokenDto =
        authService.login(form).toResponse()

    @Operation(
        summary = "토큰 재발급",
        description = "`refreshToken`으로 새 토큰 쌍을 발급합니다. 만료·폐기된 토큰이면 401이며, 재발급하면 이전 refreshToken은 더 이상 쓸 수 없습니다.",
    )
    @PostMapping("/refresh")
    fun refresh(@Valid @RequestBody form: RefreshForm): TokenDto =
        authService.refresh(form.refreshToken).toResponse()

    @Operation(
        summary = "이메일 인증 코드 발송",
        description = "6자리 인증 코드를 메일로 보냅니다. `purpose`는 `SIGNUP` 또는 `PASSWORD_RESET`입니다. 성공 시 204를 돌려주고, 응답 본문은 없습니다. 로컬에서는 Mailpit(`http://localhost:28025`)에서 확인합니다.",
    )
    @PostMapping("/email-verifications")
    fun requestVerification(@Valid @RequestBody form: EmailVerificationForm): ResponseEntity<Void> {
        authService.requestVerification(form.email, form.purpose)
        return ResponseEntity.noContent().build()
    }

    @Operation(
        summary = "비밀번호 재설정 코드 요청",
        description = "`PASSWORD_RESET` 목적의 인증 코드를 이메일로 보냅니다. 성공 시 204입니다.",
    )
    @PostMapping("/password-reset/request")
    fun requestPasswordReset(@Valid @RequestBody form: PasswordResetRequestForm): ResponseEntity<Void> {
        authService.requestVerification(form.email, "PASSWORD_RESET")
        return ResponseEntity.noContent().build()
    }

    @Operation(
        summary = "비밀번호 재설정 확정",
        description = "인증 코드와 새 비밀번호로 비밀번호를 바꿉니다. 코드가 틀리거나 만료되면 400입니다. 성공 시 204이며 기존 refreshToken은 모두 폐기됩니다.",
    )
    @PostMapping("/password-reset/confirm")
    fun confirmPasswordReset(@Valid @RequestBody form: PasswordResetConfirmForm): ResponseEntity<Void> {
        authService.resetPassword(form)
        return ResponseEntity.noContent().build()
    }

    @Operation(
        summary = "로그아웃",
        description = "`refreshToken`을 폐기합니다. accessToken은 만료 전까지 유효하므로 즉시 무효화가 필요하면 토큰 수명(15분)을 기다립니다. 성공 시 204입니다.",
    )
    @PostMapping("/logout")
    fun logout(@Valid @RequestBody form: RefreshForm): ResponseEntity<Void> {
        authService.logout(form.refreshToken)
        return ResponseEntity.noContent().build()
    }

    private fun com.lookation.service.TokenPair.toResponse() =
        TokenDto(accessToken, refreshToken, expiresIn)
}
