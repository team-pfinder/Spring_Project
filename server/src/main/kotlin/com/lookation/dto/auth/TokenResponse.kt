package com.lookation.dto.auth

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
)
