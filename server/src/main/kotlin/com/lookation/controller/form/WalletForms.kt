package com.lookation.controller.form

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

@Schema(description = "입금 신청 요청")
data class ChargeRequestForm(
    @field:Schema(description = "충전 금액. 1 이상이며 원 단위입니다.", example = "120000")
    @field:Positive val amount: Long,
    @field:Schema(description = "입금자명. 최대 50자입니다.", example = "김하늘")
    @field:NotBlank @field:Size(max = 50) val depositorName: String,
)

@Schema(description = "출금 신청 요청")
data class WithdrawalRequestForm(
    @field:Schema(description = "출금 금액. 1 이상이며 원 단위입니다.", example = "120000")
    @field:Positive val amount: Long,
)



@Schema(description = "입출금 심사 결정 요청")
data class WalletDecisionForm(
    @field:Schema(description = "승인 여부. `true`이면 승인, `false`이면 거절로 처리합니다.", example = "true")
    val approved: Boolean,
)
