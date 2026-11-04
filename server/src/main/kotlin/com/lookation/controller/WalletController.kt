package com.lookation.controller

import com.lookation.global.error.UnauthorizedException
import com.lookation.service.WalletService
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.lookation.dto.WithdrawalResponse
import com.lookation.dto.ChargeResponse
import com.lookation.controller.form.WithdrawalRequestForm
import com.lookation.controller.form.ChargeRequestForm

@RestController
@RequestMapping("/api/v1")
class WalletController(private val walletService: WalletService) {

    @PostMapping("/wallet/charges")
    fun requestCharge(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: ChargeRequestForm,
    ): ChargeResponse = walletService.requestCharge(jwt.accountId(), form)

    @GetMapping("/wallet/charges")
    fun charges(@AuthenticationPrincipal jwt: Jwt): List<ChargeResponse> =
        walletService.charges(jwt.accountId())

    @PostMapping("/withdrawals")
    @PreAuthorize("hasRole('HOST')")
    fun requestWithdrawal(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: WithdrawalRequestForm,
    ): WithdrawalResponse = walletService.requestWithdrawal(jwt.accountId(), form)

    @GetMapping("/withdrawals")
    @PreAuthorize("hasRole('HOST')")
    fun withdrawals(@AuthenticationPrincipal jwt: Jwt): List<WithdrawalResponse> =
        walletService.withdrawals(jwt.accountId())

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
