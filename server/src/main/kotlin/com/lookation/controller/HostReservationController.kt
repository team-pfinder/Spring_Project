package com.lookation.controller

import com.lookation.global.error.UnauthorizedException
import com.lookation.service.ReservationService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import com.lookation.dto.HostReservationDto

@RestController
@RequestMapping("/api/v1/hosts/reservations")
@PreAuthorize("hasRole('HOST')")
class HostReservationController(private val reservationService: ReservationService) {

    @GetMapping
    fun list(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestParam(required = false) status: String?,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "20") limit: Int,
    ): List<HostReservationDto> =
        reservationService.listForHost(jwt.accountId(), status, cursor, limit)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
