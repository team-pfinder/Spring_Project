package com.lookation.dto

import com.lookation.domain.ChargeStatus
import com.lookation.domain.WithdrawalStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "입금 신청 한 건")
data class ChargeDto(
    @field:Schema(description = "입금 신청 식별자입니다.", example = "1")
    val id: Long,

    @field:Schema(description = "충전 금액. 원 단위입니다.", example = "120000")
    val amount: Long,

    @field:Schema(description = "입금자명입니다.", example = "김하늘")
    val depositorName: String,

    @field:Schema(description = "처리 상태. `REQUESTED`, `APPROVED`, `REJECTED` 중 하나입니다.", example = "REQUESTED")
    val status: ChargeStatus,
)

@Schema(description = "출금 신청 한 건")
data class WithdrawalDto(
    @field:Schema(description = "출금 신청 식별자입니다.", example = "1")
    val id: Long,

    @field:Schema(description = "출금 금액. 원 단위입니다.", example = "120000")
    val amount: Long,

    @field:Schema(description = "처리 상태. `REQUESTED`, `APPROVED`, `PAID`, `REJECTED` 중 하나입니다.", example = "REQUESTED")
    val status: WithdrawalStatus,
)
