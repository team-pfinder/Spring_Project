package com.lookation.controller.wallet

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



data class WalletDecisionForm(
    val approved: Boolean,
)
