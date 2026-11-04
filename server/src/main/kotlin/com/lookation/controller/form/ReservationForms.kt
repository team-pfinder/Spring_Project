package com.lookation.controller.form

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.OffsetDateTime

@Schema(description = "예약 생성 요청")
data class CreateReservationForm(
    @field:Schema(description = "예약할 공간의 식별자입니다.", example = "42")
    @field:Positive val spaceId: Long,
    @field:Schema(description = "이용 시작 일시입니다. 호스트가 승인제를 걸어둔 공간이면 승인 후 결제 대기로 넘어갑니다.", example = "2026-11-20T18:00:00+09:00")
    val startAt: OffsetDateTime,
    @field:Schema(description = "이용 종료 일시입니다. 시작 일시보다 뒤여야 합니다.", example = "2026-11-20T22:00:00+09:00")
    val endAt: OffsetDateTime,
    @field:Schema(description = "이용 인원. 1 이상 32767 이하입니다.", example = "8")
    @field:Positive @field:Max(32767) val guestCount: Int,
    @field:Schema(description = "호스트에게 전달할 요청 사항. 선택 항목이며 최대 300자입니다.", example = "늦게 도착합니다. 조명 세팅 부탁드려요.")
    @field:Size(max = 300) val requestMessage: String? = null,
)

@Schema(description = "예약 거절 요청")
data class RejectReservationForm(
    @field:Schema(description = "거절 사유. 선택 항목이며 최대 500자입니다.", example = "해당 시간대는 이미 다른 예약이 확정되었습니다.")
    @field:Size(max = 500) val reason: String? = null,
)

