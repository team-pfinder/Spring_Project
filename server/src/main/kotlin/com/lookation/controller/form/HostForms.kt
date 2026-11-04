package com.lookation.controller.form

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class HostApplyForm(
    @field:NotBlank @field:Size(max = 100) val businessName: String,
    @field:NotBlank @field:Pattern(regexp = "\\d{10}") val businessNumber: String,
    @field:NotBlank @field:Size(max = 50) val representativeName: String,
    @field:Size(max = 50) val bankName: String? = null,
    @field:Pattern(regexp = "\\d{10,20}") val bankAccountNumber: String? = null,
    @field:Size(max = 50) val bankAccountHolder: String? = null,
)
