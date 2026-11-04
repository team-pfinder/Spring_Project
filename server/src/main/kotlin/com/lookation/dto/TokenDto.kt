package com.lookation.dto

data class TokenDto(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
)
