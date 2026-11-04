package com.lookation.domain

data class Account(
    val id: Long,
    val email: String,
    val passwordHash: String,
    val name: String,
    val nickname: String,
    val status: String,
)
