package com.lookation.controller

import com.lookation.global.error.UnauthorizedException
import com.lookation.service.WalletService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.lookation.dto.WithdrawalDto
import com.lookation.dto.ChargeDto
import com.lookation.controller.form.WalletDecisionForm

@Tag(
    name = "관리자 - 입출금 심사",
    description = "관리자가 회원의 충전 신청과 호스트의 출금 신청을 승인하거나 거절합니다. `ADMIN`만 호출할 수 있습니다.",
)
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
class AdminWalletController(private val walletService: WalletService) {

    @Operation(
        summary = "충전 신청 승인·거절",
        description = "`approved`가 `true`이면 상태가 `APPROVED`로 바뀌고 신청 금액이 회원 지갑 잔액에 더해지며 원장에 기록됩니다. `false`이면 상태만 `REJECTED`로 바뀌고 잔액은 변하지 않습니다. 없는 신청이면 404, 이미 처리된 신청이면 409입니다.",
    )
    @PostMapping("/charges/{id}/decision")
    fun decideCharge(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "충전 신청 ID", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody form: WalletDecisionForm,
    ): ChargeDto = walletService.decideCharge(jwt.accountId(), id, form.approved)

    @Operation(
        summary = "출금 신청 승인·거절",
        description = "`approved`가 `true`이면 상태가 `PAID`가 됩니다. 신청 시점에 홀드된 금액이 그대로 지급 처리되므로 잔액을 추가로 차감하지 않습니다. `false`이면 상태가 `REJECTED`가 되고 홀드된 금액이 지갑 잔액으로 되돌아옵니다. 없는 신청이면 404, 이미 처리된 신청이면 409입니다.",
    )
    @PostMapping("/withdrawals/{id}/decision")
    fun decideWithdrawal(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "출금 신청 ID", example = "42")
        @PathVariable id: Long,
        @Valid @RequestBody form: WalletDecisionForm,
    ): WithdrawalDto = walletService.decideWithdrawal(jwt.accountId(), id, form.approved)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
