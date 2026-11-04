package com.lookation.controller

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class NoticeForm(
    @field:NotBlank @field:Size(max = 200) val title: String,
    @field:NotBlank val content: String,
    val important: Boolean = false,
)

data class HelpForm(
    @field:NotBlank @field:Size(max = 50) val category: String,
    @field:NotBlank @field:Size(max = 200) val title: String,
    @field:NotBlank val content: String,
)
