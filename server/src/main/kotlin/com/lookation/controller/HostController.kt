package com.lookation.controller

import com.lookation.global.crypto.CryptoService
import com.lookation.repository.HostProfileRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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



@Tag(
    name = "호스트",
    description = "호스트 프로필 등록과 본인 프로필 조회입니다. 모든 엔드포인트에 accessToken이 필요합니다.",
)
@RestController
@RequestMapping("/api/v1")
class HostController(
    private val hostProfileRepository: HostProfileRepository,
    private val cryptoService: CryptoService,
    private val accountRepository: com.lookation.repository.AccountRepository,
) {

    @Operation(
        summary = "호스트 신청",
        description = "로그인한 사용자가 사업자 정보와 정산 계좌를 등록하고 즉시 `HOST` 역할을 부여받습니다. 계좌번호와 예금주는 암호화되어 저장됩니다. 이미 호스트 프로필이 있으면 409이고, 토큰이 없거나 유효하지 않으면 401입니다. 필수 값이 비었거나 형식이 틀리면 400입니다.",
    )
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

    @Operation(
        summary = "내 호스트 프로필 조회",
        description = "로그인한 사용자의 호스트 프로필을 반환합니다. 계좌번호는 마스킹되어 `****1234` 형태의 `bankAccountNumberMasked`로 내려가고, 전체 번호는 응답에 포함되지 않습니다. 호스트 프로필이 없으면 404이고, 토큰이 없거나 유효하지 않으면 401입니다.",
    )
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
