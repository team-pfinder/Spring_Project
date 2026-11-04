package com.lookation.dto

data class ChargeDto(
    val id: Long,
    val amount: Long,
    val depositorName: String,
    val status: String,
)

data class WithdrawalDto(
    val id: Long,
    val amount: Long,
    val status: String,
)
