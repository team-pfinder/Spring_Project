package com.lookation.controller.sanction

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.OffsetDateTime

data class ApplySanctionForm(
    @field:Positive val accountId: Long,
    @field:Min(1) @field:Max(3) val level: Int,
    @field:NotBlank @field:Size(max = 300) val reason: String,
    val endsAt: OffsetDateTime? = null,
)
