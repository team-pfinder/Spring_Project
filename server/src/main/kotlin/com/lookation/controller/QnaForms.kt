package com.lookation.controller

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AskQnaForm(
    @field:NotBlank @field:Size(max = 200) val title: String,
    @field:NotBlank @field:Size(max = 2000) val content: String,
)

data class QnaAnswerForm(
    @field:NotBlank @field:Size(max = 2000) val content: String,
)
