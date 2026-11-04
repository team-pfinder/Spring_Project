package com.lookation.controller.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignupForm(
    @field:Email @field:NotBlank val email: String,
    @field:NotBlank @field:Size(min = 8, max = 72) val password: String,
    @field:NotBlank @field:Size(max = 50) val name: String,
    @field:NotBlank @field:Size(max = 30) val nickname: String,
    @field:NotBlank @field:Pattern(regexp = "\\d{6}") val code: String,
)

data class LoginForm(
    @field:Email @field:NotBlank val email: String,
    @field:NotBlank val password: String,
)

data class RefreshForm(
    @field:NotBlank val refreshToken: String,
)


data class EmailVerificationForm(
    @field:Email @field:NotBlank val email: String,
    @field:NotBlank @field:Pattern(regexp = "SIGNUP|PASSWORD_RESET") val purpose: String,
)

data class PasswordResetRequestForm(
    @field:Email @field:NotBlank val email: String,
)

data class PasswordResetConfirmForm(
    @field:Email @field:NotBlank val email: String,
    @field:NotBlank @field:Pattern(regexp = "\\d{6}") val code: String,
    @field:NotBlank @field:Size(min = 8, max = 72) val newPassword: String,
)
