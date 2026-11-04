package com.lookation.wallet.api

import com.lookation.global.error.UnauthorizedException
import com.lookation.wallet.application.WalletService
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
class AdminWalletController(private val walletService: WalletService) {

    @PostMapping("/charges/{id}/decision")
    fun decideCharge(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @Valid @RequestBody form: WalletDecisionForm,
    ): ChargeResponse = walletService.decideCharge(jwt.accountId(), id, form.approved)

    @PostMapping("/withdrawals/{id}/decision")
    fun decideWithdrawal(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @Valid @RequestBody form: WalletDecisionForm,
    ): WithdrawalResponse = walletService.decideWithdrawal(jwt.accountId(), id, form.approved)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
