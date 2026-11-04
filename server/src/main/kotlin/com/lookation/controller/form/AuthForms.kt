package com.lookation.controller.form

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Schema(description = "회원가입 요청")
data class SignupForm(
    @field:Schema(description = "로그인에 사용할 이메일. `/email-verifications`로 인증을 마친 주소여야 합니다.", example = "host@lookation.local")
    @field:Email @field:NotBlank val email: String,
    @field:Schema(description = "비밀번호. 8~72자입니다.", example = "lookation1!")
    @field:NotBlank @field:Size(min = 8, max = 72) val password: String,
    @field:Schema(description = "실명. 최대 50자입니다.", example = "김하늘")
    @field:NotBlank @field:Size(max = 50) val name: String,
    @field:Schema(description = "서비스에서 사용할 닉네임. 최대 30자입니다.", example = "공간지기")
    @field:NotBlank @field:Size(max = 30) val nickname: String,
    @field:Schema(description = "메일로 받은 6자리 인증 코드입니다.", example = "123456")
    @field:NotBlank @field:Pattern(regexp = "\\d{6}") val code: String,
)

@Schema(description = "로그인 요청")
data class LoginForm(
    @field:Schema(description = "가입한 이메일입니다.", example = "host@lookation.local")
    @field:Email @field:NotBlank val email: String,
    @field:Schema(description = "비밀번호입니다.", example = "lookation1!")
    @field:NotBlank val password: String,
)

@Schema(description = "토큰 재발급·로그아웃 요청")
data class RefreshForm(
    @field:Schema(description = "재발급 또는 폐기할 리프레시 토큰입니다.", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.abc123")
    @field:NotBlank val refreshToken: String,
)


@Schema(description = "이메일 인증 코드 발송 요청")
data class EmailVerificationForm(
    @field:Schema(description = "인증 코드를 받을 이메일입니다.", example = "host@lookation.local")
    @field:Email @field:NotBlank val email: String,
    @field:Schema(description = "인증 목적. `SIGNUP` 또는 `PASSWORD_RESET`입니다.", example = "SIGNUP")
    @field:NotBlank @field:Pattern(regexp = "SIGNUP|PASSWORD_RESET") val purpose: String,
)

@Schema(description = "비밀번호 재설정 코드 요청")
data class PasswordResetRequestForm(
    @field:Schema(description = "재설정 코드를 받을 이메일입니다.", example = "host@lookation.local")
    @field:Email @field:NotBlank val email: String,
)

@Schema(description = "비밀번호 재설정 확정 요청")
data class PasswordResetConfirmForm(
    @field:Schema(description = "가입한 이메일입니다.", example = "host@lookation.local")
    @field:Email @field:NotBlank val email: String,
    @field:Schema(description = "메일로 받은 6자리 인증 코드입니다.", example = "123456")
    @field:NotBlank @field:Pattern(regexp = "\\d{6}") val code: String,
    @field:Schema(description = "새 비밀번호. 8~72자입니다.", example = "lookation1!")
    @field:NotBlank @field:Size(min = 8, max = 72) val newPassword: String,
)
