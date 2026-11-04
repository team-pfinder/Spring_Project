package com.lookation.reservation.api

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

data class HostReservationResponse(
    val id: Long,
    val spaceId: Long,
    val spaceName: String,
    val memberNickname: String,
    val memberName: String?,
    val memberPhone: String?,
    val startAt: OffsetDateTime,
    val endAt: OffsetDateTime,
    val guestCount: Int,
    val price: Long,
    val status: String,
)

data class ReservationResponse(
    val id: Long,
    val spaceId: Long,
    val startAt: OffsetDateTime,
    val endAt: OffsetDateTime,
    val price: Long,
    val status: String,
    val refundedAmount: Long?,
)
