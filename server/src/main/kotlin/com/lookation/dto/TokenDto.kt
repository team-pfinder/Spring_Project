package com.lookation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "발급된 토큰 쌍")
data class TokenDto(
    @field:Schema(description = "API 호출에 사용할 액세스 토큰입니다.", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.abc123")
    val accessToken: String,
    @field:Schema(description = "액세스 토큰 재발급에 사용할 리프레시 토큰입니다.", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.abc123")
    val refreshToken: String,
    @field:Schema(description = "액세스 토큰 만료까지 남은 시간. 초 단위입니다.", example = "900")
    val expiresIn: Long,
)
