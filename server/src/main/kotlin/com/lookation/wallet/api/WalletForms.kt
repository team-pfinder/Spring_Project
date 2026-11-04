package com.lookation.wallet.api

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class ChargeRequestForm(
    @field:Positive val amount: Long,
    @field:NotBlank @field:Size(max = 50) val depositorName: String,
)

data class WithdrawalRequestForm(
    @field:Positive val amount: Long,
)

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

data class WalletDecisionForm(
    val approved: Boolean,
)
