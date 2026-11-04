package com.lookation.controller

import com.lookation.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.lookation.dto.TokenResponse
import com.lookation.controller.form.PasswordResetConfirmForm
import com.lookation.controller.form.PasswordResetRequestForm
import com.lookation.controller.form.EmailVerificationForm
import com.lookation.controller.form.RefreshForm
import com.lookation.controller.form.LoginForm
import com.lookation.controller.form.SignupForm

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody form: SignupForm): TokenResponse =
        authService.signup(form).toResponse()

    @PostMapping("/login")
    fun login(@Valid @RequestBody form: LoginForm): TokenResponse =
        authService.login(form).toResponse()

    @PostMapping("/refresh")
    fun refresh(@Valid @RequestBody form: RefreshForm): TokenResponse =
        authService.refresh(form.refreshToken).toResponse()

    @PostMapping("/email-verifications")
    fun requestVerification(@Valid @RequestBody form: EmailVerificationForm): ResponseEntity<Void> {
        authService.requestVerification(form.email, form.purpose)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/password-reset/request")
    fun requestPasswordReset(@Valid @RequestBody form: PasswordResetRequestForm): ResponseEntity<Void> {
        authService.requestVerification(form.email, "PASSWORD_RESET")
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/password-reset/confirm")
    fun confirmPasswordReset(@Valid @RequestBody form: PasswordResetConfirmForm): ResponseEntity<Void> {
        authService.resetPassword(form)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/logout")
    fun logout(@Valid @RequestBody form: RefreshForm): ResponseEntity<Void> {
        authService.logout(form.refreshToken)
        return ResponseEntity.noContent().build()
    }

    private fun com.lookation.service.TokenPair.toResponse() =
        TokenResponse(accessToken, refreshToken, expiresIn)
}
