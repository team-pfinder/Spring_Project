package com.lookation.dto

import com.lookation.domain.ReservationStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

@Schema(description = "호스트가 조회하는 예약 한 건")
data class HostReservationDto(
    @field:Schema(description = "예약 식별자입니다.", example = "1")
    val id: Long,
    @field:Schema(description = "예약된 공간의 식별자입니다.", example = "42")
    val spaceId: Long,
    @field:Schema(description = "공간 이름입니다.", example = "루프탑 파티룸 한강")
    val spaceName: String,
    @field:Schema(description = "예약자 닉네임입니다.", example = "공간지기")
    val memberNickname: String,
    @field:Schema(description = "예약자 실명. 예약이 `CONFIRMED` 또는 `COMPLETED`일 때만 값이 있고 그 외에는 null입니다.", example = "김하늘")
    val memberName: String?,
    @field:Schema(description = "예약자 연락처. 예약이 `CONFIRMED` 또는 `COMPLETED`일 때만 값이 있고 그 외에는 null입니다.", example = "010-1234-5678")
    val memberPhone: String?,
    @field:Schema(description = "이용 시작 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val startAt: OffsetDateTime,
    @field:Schema(description = "이용 종료 일시입니다.", example = "2026-11-20T22:00:00+09:00")
    val endAt: OffsetDateTime,
    @field:Schema(description = "이용 인원입니다.", example = "8")
    val guestCount: Int,
    @field:Schema(description = "결제 금액. 원 단위입니다.", example = "120000")
    val price: Long,
    @field:Schema(
        description = "예약 상태. `PENDING_APPROVAL`, `PENDING_PAYMENT`, `CONFIRMED`, `COMPLETED`, `CANCELLED`, `REFUNDED`, `REJECTED`, `EXPIRED` 중 하나입니다.",
        example = "CONFIRMED",
    )
    val status: ReservationStatus,
)

@Schema(description = "회원이 조회하는 예약 한 건")
data class ReservationDto(
    @field:Schema(description = "예약 식별자입니다.", example = "1")
    val id: Long,
    @field:Schema(description = "예약된 공간의 식별자입니다.", example = "42")
    val spaceId: Long,
    @field:Schema(description = "공간 이름입니다.", example = "루프탑 파티룸 한강")
    val spaceName: String,
    @field:Schema(description = "이용 시작 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val startAt: OffsetDateTime,
    @field:Schema(description = "이용 종료 일시입니다.", example = "2026-11-20T22:00:00+09:00")
    val endAt: OffsetDateTime,
    @field:Schema(description = "이용 인원입니다.", example = "8")
    val guestCount: Int,
    @field:Schema(description = "결제 금액. 원 단위입니다.", example = "120000")
    val price: Long,
    @field:Schema(
        description = "예약 상태. `PENDING_APPROVAL`, `PENDING_PAYMENT`, `CONFIRMED`, `COMPLETED`, `CANCELLED`, `REFUNDED`, `REJECTED`, `EXPIRED` 중 하나입니다.",
        example = "CONFIRMED",
    )
    val status: ReservationStatus,
    @field:Schema(description = "호스트에게 전달한 요청 사항입니다.", example = "늦게 도착합니다.")
    val requestMessage: String?,
    @field:Schema(description = "결제 시한. 결제 대기 상태가 아니면 null입니다.", example = "2026-11-20T18:00:00+09:00")
    val paymentDueAt: OffsetDateTime?,
    @field:Schema(description = "예약 생성 일시입니다.", example = "2026-11-18T12:00:00+09:00")
    val createdAt: OffsetDateTime,
    @field:Schema(description = "지금 취소할 수 있는지 여부입니다. 이미 시작했거나 취소 불가 상태면 false입니다.", example = "true")
    val canCancel: Boolean,
    @field:Schema(description = "환불된 금액. 환불이 발생하지 않았으면 null입니다.", example = "120000")
    val refundedAmount: Long?,
)
