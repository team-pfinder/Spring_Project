package com.lookation.controller.form

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class CreateReviewForm(
    @field:Positive val reservationId: Long,
    @field:Min(1) @field:Max(5) val rating: Int,
    @field:NotBlank @field:Size(max = 2000) val content: String,
)

data class ReviewReplyForm(
    @field:NotBlank @field:Size(max = 2000) val content: String,
)
