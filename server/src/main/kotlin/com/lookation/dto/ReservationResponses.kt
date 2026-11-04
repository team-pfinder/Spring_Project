package com.lookation.dto

import java.time.OffsetDateTime

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
