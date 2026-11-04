package com.lookation.dto

data class AccountDto(
    val id: Long,
    val email: String,
    val name: String,
    val nickname: String,
    val roles: List<String>,
)
