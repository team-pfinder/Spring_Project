package com.lookation.controller

import com.lookation.global.error.UnauthorizedException
import com.lookation.service.WalletService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.lookation.dto.WithdrawalDto
import com.lookation.dto.ChargeDto
import com.lookation.controller.form.WithdrawalRequestForm
import com.lookation.controller.form.ChargeRequestForm

@Tag(
    name = "지갑",
    description = "마일리지 충전 신청·내역 조회와 호스트 출금 신청·내역 조회. 모든 API에 로그인이 필요하고 출금 관련 API는 `HOST`만 호출할 수 있습니다.",
)
@RestController
@RequestMapping("/api/v1")
class WalletController(private val walletService: WalletService) {

    @Operation(
        summary = "충전 신청",
        description = "입금자명(`depositorName`)과 금액(`amount`)으로 충전을 신청하고 상태 `REQUESTED`인 내역을 반환합니다. 이 호출만으로는 잔액이 늘지 않고 관리자 승인 시 반영됩니다. 활성 제재가 있으면 403, 금액이 0 이하이거나 입금자명이 비어 있거나 50자를 넘으면 400입니다.",
    )
    @PostMapping("/wallet/charges")
    fun requestCharge(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: ChargeRequestForm,
    ): ChargeDto = walletService.requestCharge(jwt.accountId(), form)

    @Operation(
        summary = "내 충전 내역 조회",
        description = "내가 신청한 충전 내역을 최신순으로 반환합니다. `status`는 `REQUESTED`, `APPROVED`, `REJECTED` 중 하나입니다.",
    )
    @GetMapping("/wallet/charges")
    fun charges(@AuthenticationPrincipal jwt: Jwt): List<ChargeDto> =
        walletService.charges(jwt.accountId())

    @Operation(
        summary = "출금 신청",
        description = "호스트가 정산 계좌로 출금을 신청합니다. 신청 금액만큼 지갑 잔액이 홀드되고 상태 `REQUESTED`인 내역이 만들어집니다. 호스트 전용이라 그 외 역할은 403이고, 활성 제재가 있어도 403입니다. 호스트 프로필이나 등록된 정산 계좌가 없으면 400, 출금 가능한 잔액이 부족하면 409입니다.",
    )
    @PostMapping("/withdrawals")
    @PreAuthorize("hasRole('HOST')")
    fun requestWithdrawal(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: WithdrawalRequestForm,
    ): WithdrawalDto = walletService.requestWithdrawal(jwt.accountId(), form)

    @Operation(
        summary = "내 출금 내역 조회",
        description = "호스트 본인의 출금 신청 내역을 최신순으로 반환합니다. `status`는 `REQUESTED`, `APPROVED`, `PAID`, `REJECTED` 중 하나이며, 호스트 전용이라 그 외 역할은 403입니다.",
    )
    @GetMapping("/withdrawals")
    @PreAuthorize("hasRole('HOST')")
    fun withdrawals(@AuthenticationPrincipal jwt: Jwt): List<WithdrawalDto> =
        walletService.withdrawals(jwt.accountId())

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
