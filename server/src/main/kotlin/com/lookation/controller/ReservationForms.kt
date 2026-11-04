package com.lookation.controller

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.OffsetDateTime

data class CreateReservationForm(
    @field:Positive val spaceId: Long,
    val startAt: OffsetDateTime,
    val endAt: OffsetDateTime,
    @field:Positive @field:Max(32767) val guestCount: Int,
    @field:Size(max = 300) val requestMessage: String? = null,
)

data class RejectReservationForm(
    @field:Size(max = 500) val reason: String? = null,
)


