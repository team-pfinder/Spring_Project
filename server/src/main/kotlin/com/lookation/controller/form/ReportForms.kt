package com.lookation.controller.form

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

@Schema(description = "신고 접수 요청")
data class CreateReportForm(
    @field:Schema(
        description = "신고 대상 종류. `SPACE`, `RESERVATION`, `REVIEW`, `QNA`, `ACCOUNT` 중 하나입니다.",
        example = "REVIEW",
    )
    @field:NotBlank @field:Pattern(regexp = "SPACE|RESERVATION|REVIEW|QNA|ACCOUNT") val targetType: String,
    @field:Schema(description = "신고 대상의 식별자입니다.", example = "42")
    @field:Positive val targetId: Long,
    @field:Schema(description = "신고 사유. 최대 500자입니다.", example = "예약과 무관한 광고성 내용이 포함되어 있습니다.")
    @field:NotBlank @field:Size(max = 500) val reason: String,
)

@Schema(description = "신고 처리 결정 요청")
data class ReportDecisionForm(
    @field:Schema(description = "승인 여부. `true`이면 신고를 인정해 `ACCEPTED`, `false`이면 `REJECTED`로 처리합니다.", example = "true")
    val accepted: Boolean,
)
