package com.lookation.dto.wallet

data class ChargeResponse(
    val id: Long,
    val amount: Long,
    val depositorName: String,
    val status: String,
)

data class WithdrawalResponse(
    val id: Long,
    val amount: Long,
    val status: String,
)
