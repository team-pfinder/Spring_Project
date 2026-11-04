package com.lookation.controller

import com.lookation.global.error.UnauthorizedException
import com.lookation.service.ReservationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import com.lookation.dto.HostReservationDto

@Tag(
    name = "호스트 예약 관리",
    description = "호스트가 자신의 공간에 들어온 예약을 상태별로 조회합니다. `HOST` 역할만 호출할 수 있습니다.",
)
@RestController
@RequestMapping("/api/v1/hosts/reservations")
@PreAuthorize("hasRole('HOST')")
class HostReservationController(private val reservationService: ReservationService) {

    @Operation(
        summary = "호스트 예약 목록 조회",
        description = "내 공간에 대한 예약을 ID 내림차순 커서 페이지네이션으로 조회합니다. `status`를 생략하면 모든 상태를 반환하며, `CONFIRMED`·`COMPLETED` 상태에서만 예약자 실명(`memberName`)과 연락처(`memberPhone`)가 채워지고 그 외 상태에서는 `null`입니다. `HOST` 역할이 아니면 403, `limit`이 1~100 밖이거나 알 수 없는 `status`이면 400입니다.",
    )
    @GetMapping
    fun list(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(
            description = "예약 상태 필터. `PENDING_APPROVAL`, `PENDING_PAYMENT`, `CONFIRMED`, `COMPLETED`, `CANCELLED`, `REFUNDED`, `REJECTED`, `EXPIRED` 중 하나이며, 생략하면 전체를 조회합니다.",
            example = "CONFIRMED",
        )
        @RequestParam(required = false) status: String?,
        @Parameter(
            description = "커서. 직전 응답에서 받은 마지막 예약 ID를 넣으면 그보다 작은 ID부터 다음 페이지를 조회합니다.",
            example = "42",
        )
        @RequestParam(required = false) cursor: Long?,
        @Parameter(description = "한 번에 가져올 개수. 1~100이며 기본값은 20입니다.", example = "20")
        @RequestParam(defaultValue = "20") limit: Int,
    ): List<HostReservationDto> =
        reservationService.listForHost(jwt.accountId(), status, cursor, limit)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
