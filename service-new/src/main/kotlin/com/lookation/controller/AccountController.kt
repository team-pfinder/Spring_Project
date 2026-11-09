package com.lookation.controller

import com.lookation.repository.AccountRepository
import com.lookation.global.error.NotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import com.lookation.global.error.UnauthorizedException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.lookation.dto.AccountDto


@Tag(
    name = "계정",
    description = "로그인한 사용자 본인의 계정 정보를 조회합니다. 모든 엔드포인트에 accessToken이 필요합니다.",
)
@RestController
@RequestMapping("/api/v1")
class AccountController(private val accountRepository: AccountRepository) {

    @Operation(
        summary = "내 계정 정보 조회",
        description = "accessToken의 주체(subject)로 본인 계정을 찾아 이메일·이름·닉네임·역할 목록을 반환합니다. 토큰이 없거나 유효하지 않으면 401이고, 토큰 주체에 해당하는 계정이 없으면 404입니다.",
    )
    @GetMapping("/me")
    fun me(@AuthenticationPrincipal jwt: Jwt): AccountDto {
        val accountId = jwt.subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
        val account = accountRepository.findById(accountId)
            ?: throw NotFoundException("계정을 찾을 수 없습니다")
        return AccountDto(
            id = account.id,
            email = account.email,
            name = account.name,
            nickname = account.nickname,
            roles = accountRepository.roles(accountId),
        )
    }
}
