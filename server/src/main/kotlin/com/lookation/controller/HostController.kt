package com.lookation.controller

import com.lookation.global.crypto.CryptoService
import com.lookation.repository.HostProfileRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.lookation.global.error.UnauthorizedException
import com.lookation.dto.HostProfileDto
import com.lookation.controller.form.HostApplyForm



@RestController
@RequestMapping("/api/v1")
class HostController(
    private val hostProfileRepository: HostProfileRepository,
    private val cryptoService: CryptoService,
    private val accountRepository: com.lookation.repository.AccountRepository,
) {

    @PostMapping("/hosts/apply")
    fun apply(@AuthenticationPrincipal jwt: Jwt, @Valid @RequestBody form: HostApplyForm): HostProfileDto {
        val accountId = jwt.accountId()
        if (hostProfileRepository.exists(accountId)) {
            throw com.lookation.global.error.ConflictException("이미 호스트 신청이 완료된 계정입니다")
        }
        hostProfileRepository.insert(
            accountId = accountId,
            businessName = form.businessName,
            businessNumber = form.businessNumber,
            representativeName = form.representativeName,
            bankName = form.bankName,
            bankAccountNumberEnc = form.bankAccountNumber?.let(cryptoService::encrypt),
            bankAccountHolderEnc = form.bankAccountHolder?.let(cryptoService::encrypt),
        )
        if (!accountRepository.roles(accountId).contains("HOST")) {
            accountRepository.addRole(accountId, "HOST")
        }
        return requireNotNull(hostProfileRepository.findByAccountId(accountId)).toResponse()
    }

    @GetMapping("/me/host-profile")
    fun me(@AuthenticationPrincipal jwt: Jwt): HostProfileDto {
        val profile = hostProfileRepository.findByAccountId(jwt.accountId())
            ?: throw com.lookation.global.error.NotFoundException("호스트 프로필이 없습니다")
        return profile.toResponse()
    }

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")

    private fun com.lookation.repository.HostProfileRow.toResponse() = HostProfileDto(
        businessName = businessName,
        businessNumber = businessNumber,
        representativeName = representativeName,
        bankName = bankName,
        bankAccountNumberMasked = bankAccountNumberEnc?.let { mask(cryptoService.decrypt(it)) },
        status = status,
    )

    private fun mask(accountNumber: String): String =
        if (accountNumber.length <= 4) "****" else "****" + accountNumber.takeLast(4)
}
