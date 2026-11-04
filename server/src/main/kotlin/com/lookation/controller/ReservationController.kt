package com.lookation.controller

import com.lookation.global.error.UnauthorizedException
import com.lookation.service.ReservationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.lookation.dto.ReservationDto
import com.lookation.controller.form.RejectReservationForm
import com.lookation.controller.form.CreateReservationForm

@Tag(
    name = "예약",
    description = "예약 생성부터 호스트 승인·결제·취소까지 다룹니다. 생성과 결제·취소는 예약자가, 승인과 거절은 공간 소유 호스트가 호출합니다.",
)
@RestController
@RequestMapping("/api/v1/reservations")
class ReservationController(private val reservationService: ReservationService) {

    @Operation(
        summary = "예약 생성",
        description = "`spaceId` 공간을 `startAt`~`endAt`로 예약합니다. 공간의 `approvalRequired`가 참이면 `PENDING_APPROVAL`로 생성되어 호스트 승인을 기다리고, 거짓이면 마일리지를 즉시 홀드하면서 `CONFIRMED`로 생성됩니다. 종료 시각이 시작 이전이거나 과거이거나 90일을 넘거나 가격 규칙이 없으면 400, 공간이 `PUBLISHED`가 아니거나 인원이 수용 범위를 벗어나거나 잔액이 부족하거나 이미 예약된 시간대면 409, 공간이 없으면 404, 이용이 제한된 계정이면 403입니다.",
    )
    @PostMapping
    fun create(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: CreateReservationForm,
    ): ReservationDto = reservationService.create(jwt.accountId(), form)

    @Operation(
        summary = "내 예약 목록 조회",
        description = "내가 예약자인 예약을 이용 시작 시각 내림차순으로 모두 반환합니다. 이 응답의 `refundedAmount`는 항상 `null`이며, 취소로 실제 환불된 금액은 취소 응답에서만 확인할 수 있습니다.",
    )
    @GetMapping
    fun listMine(@AuthenticationPrincipal jwt: Jwt): List<ReservationDto> =
        reservationService.listMine(jwt.accountId())

    @Operation(
        summary = "예약 단건 조회",
        description = "예약자 또는 그 공간의 호스트만 조회할 수 있습니다. 당사자가 아니면 403, 없는 예약이면 404입니다.",
    )
    @GetMapping("/{id}")
    fun get(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "예약 ID", example = "42") @PathVariable id: Long,
    ): ReservationDto =
        reservationService.get(jwt.accountId(), id)

    @Operation(
        summary = "예약 승인 (호스트)",
        description = "`PENDING_APPROVAL` 예약을 `PENDING_PAYMENT`로 전이하고 결제 시한을 승인 시점 1시간 뒤로 설정합니다. 공간 소유 호스트가 아니면 403, 없는 예약이면 404, 상태가 `PENDING_APPROVAL`이 아니면 409입니다.",
    )
    @PostMapping("/{id}/approve")
    fun approve(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "예약 ID", example = "42") @PathVariable id: Long,
    ): ReservationDto =
        reservationService.approve(jwt.accountId(), id)

    @Operation(
        summary = "예약 거절 (호스트)",
        description = "`PENDING_APPROVAL` 예약을 `REJECTED`로 전이합니다. 요청 본문은 선택이며 `reason`(최대 500자)을 보내면 예약 이력에 남습니다. 공간 소유 호스트가 아니면 403, 없는 예약이면 404, 상태가 `PENDING_APPROVAL`이 아니면 409입니다.",
    )
    @PostMapping("/{id}/reject")
    fun reject(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "예약 ID", example = "42") @PathVariable id: Long,
        @RequestBody(required = false) form: RejectReservationForm?,
    ): ReservationDto = reservationService.reject(jwt.accountId(), id, form?.reason)

    @Operation(
        summary = "예약 결제",
        description = "`PENDING_PAYMENT` 예약 대금을 마일리지로 결제하고 `CONFIRMED`로 전이합니다. 예약자가 아니면 403, 없는 예약이면 404이며, 상태가 `PENDING_PAYMENT`가 아니거나 결제 시한(승인 후 1시간)이 지났거나 잔액이 부족하면 409입니다. 이용이 제한된 계정이면 403입니다.",
    )
    @PostMapping("/{id}/pay")
    fun pay(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "예약 ID", example = "42") @PathVariable id: Long,
    ): ReservationDto =
        reservationService.pay(jwt.accountId(), id)

    @Operation(
        summary = "예약 취소",
        description = "예약자 또는 호스트가 취소합니다. `PENDING_APPROVAL`·`PENDING_PAYMENT`는 환불 없이 `CANCELLED`가 되어 `refundedAmount`가 `null`이고, `CONFIRMED`는 이용 시작일까지 남은 일수로 환불률을 계산해(7일 이상 100%, 3일 이상 70%, 1일 이상 50%, 그 미만 0%) 마일리지로 돌려주며 `refundedAmount`에 금액을 담습니다. 호스트가 취소하면 100% 환불입니다. 당사자가 아니면 403, 없는 예약이면 404, 취소 가능 상태가 아니면 409, 이용 시작 시각이 이미 지났으면 400입니다.",
    )
    @PostMapping("/{id}/cancel")
    fun cancel(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "예약 ID", example = "42") @PathVariable id: Long,
    ): ReservationDto =
        reservationService.cancel(jwt.accountId(), id)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
