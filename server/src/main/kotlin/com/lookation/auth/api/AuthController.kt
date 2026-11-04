package com.lookation.auth.api

import com.lookation.auth.application.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

    @PostMapping("/logout")
    fun logout(@Valid @RequestBody form: RefreshForm): ResponseEntity<Void> {
        authService.logout(form.refreshToken)
        return ResponseEntity.noContent().build()
    }

    private fun com.lookation.auth.application.TokenPair.toResponse() =
        TokenResponse(accessToken, refreshToken, expiresIn)
}
