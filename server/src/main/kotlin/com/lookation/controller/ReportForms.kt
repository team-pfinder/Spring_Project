package com.lookation.controller

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class CreateReportForm(
    @field:NotBlank @field:Pattern(regexp = "SPACE|RESERVATION|REVIEW|QNA|ACCOUNT") val targetType: String,
    @field:Positive val targetId: Long,
    @field:NotBlank @field:Size(max = 500) val reason: String,
)

data class ReportDecisionForm(
    val accepted: Boolean,
)
