package com.lookation.view

import com.lookation.domain.ReservationStatus
import com.lookation.global.error.ApiException
import com.lookation.repository.WalletRepository
import com.lookation.service.ReservationService
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Hidden
@Controller
class ReservationViewController(
    private val reservationService: ReservationService,
    private val walletRepository: WalletRepository,
) {

    @GetMapping("/mypage")
    fun mypage(): String = "redirect:/mypage/reservations"

    @GetMapping("/mypage/reservations")
    fun reservations(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestParam(required = false) status: String?,
        model: Model,
    ): String {
        val memberId = jwt.requireAccountId()
        model.addAttribute("reservations", reservationService.listMine(memberId, status))
        model.addAttribute("statusFilter", status.orEmpty())
        model.addAttribute("statuses", ReservationStatus.entries.map { it.name })
        return "mypage/reservations"
    }

    @GetMapping("/mypage/reservations/{id}")
    fun reservation(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @RequestParam(required = false) created: String?,
        @RequestParam(required = false) paid: String?,
        @RequestParam(required = false) cancelled: String?,
        model: Model,
    ): String {
        val memberId = jwt.requireAccountId()
        model.addAttribute("reservation", reservationService.getMine(memberId, id))
        if (created != null) model.addAttribute("notice", "예약이 접수되었습니다.")
        if (paid != null) model.addAttribute("notice", "결제가 완료되었습니다.")
        if (cancelled != null) model.addAttribute("notice", "예약이 취소되었습니다.")
        return "mypage/reservation"
    }

    @PostMapping("/mypage/reservations/{id}/cancel")
    fun cancel(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        model: Model,
    ): String {
        val memberId = jwt.requireAccountId()
        return try {
            reservationService.cancelMine(memberId, id)
            "redirect:/mypage/reservations/$id?cancelled=1"
        } catch (exception: ApiException) {
            model.addAttribute("reservation", reservationService.getMine(memberId, id))
            model.addAttribute("error", exception.message)
            model.addAttribute("renderedErrorLocally", true)
            "mypage/reservation"
        }
    }

    @GetMapping("/reservations/{id}/pay")
    fun payPage(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        model: Model,
    ): String {
        val memberId = jwt.requireAccountId()
        model.addAttribute("reservation", reservationService.getMine(memberId, id))
        model.addAttribute("balance", walletRepository.balance(memberId))
        return "reservation/pay"
    }

    @PostMapping("/reservations/{id}/pay")
    fun pay(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        model: Model,
    ): String {
        val memberId = jwt.requireAccountId()
        return try {
            reservationService.pay(memberId, id)
            "redirect:/mypage/reservations/$id?paid=1"
        } catch (exception: ApiException) {
            model.addAttribute("reservation", reservationService.getMine(memberId, id))
            model.addAttribute("balance", walletRepository.balance(memberId))
            model.addAttribute("error", exception.message)
            model.addAttribute("renderedErrorLocally", true)
            "reservation/pay"
        }
    }
}
