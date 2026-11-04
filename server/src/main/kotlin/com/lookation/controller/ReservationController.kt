package com.lookation.controller

import com.lookation.global.error.UnauthorizedException
import com.lookation.service.ReservationService
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

@RestController
@RequestMapping("/api/v1/reservations")
class ReservationController(private val reservationService: ReservationService) {

    @PostMapping
    fun create(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: CreateReservationForm,
    ): ReservationDto = reservationService.create(jwt.accountId(), form)

    @GetMapping
    fun listMine(@AuthenticationPrincipal jwt: Jwt): List<ReservationDto> =
        reservationService.listMine(jwt.accountId())

    @GetMapping("/{id}")
    fun get(@AuthenticationPrincipal jwt: Jwt, @PathVariable id: Long): ReservationDto =
        reservationService.get(jwt.accountId(), id)

    @PostMapping("/{id}/approve")
    fun approve(@AuthenticationPrincipal jwt: Jwt, @PathVariable id: Long): ReservationDto =
        reservationService.approve(jwt.accountId(), id)

    @PostMapping("/{id}/reject")
    fun reject(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @RequestBody(required = false) form: RejectReservationForm?,
    ): ReservationDto = reservationService.reject(jwt.accountId(), id, form?.reason)

    @PostMapping("/{id}/pay")
    fun pay(@AuthenticationPrincipal jwt: Jwt, @PathVariable id: Long): ReservationDto =
        reservationService.pay(jwt.accountId(), id)

    @PostMapping("/{id}/cancel")
    fun cancel(@AuthenticationPrincipal jwt: Jwt, @PathVariable id: Long): ReservationDto =
        reservationService.cancel(jwt.accountId(), id)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
