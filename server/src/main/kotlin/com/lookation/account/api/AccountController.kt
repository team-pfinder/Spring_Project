package com.lookation.account.api

import com.lookation.account.infrastructure.AccountRepository
import com.lookation.global.error.NotFoundException
import com.lookation.global.error.UnauthorizedException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class AccountResponse(
    val id: Long,
    val email: String,
    val name: String,
    val nickname: String,
    val roles: List<String>,
)

@RestController
@RequestMapping("/api/v1")
class AccountController(private val accountRepository: AccountRepository) {

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal jwt: Jwt): AccountResponse {
        val accountId = jwt.subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
        val account = accountRepository.findById(accountId)
            ?: throw NotFoundException("계정을 찾을 수 없습니다")
        return AccountResponse(
            id = account.id,
            email = account.email,
            name = account.name,
            nickname = account.nickname,
            roles = accountRepository.roles(accountId),
        )
    }
}
