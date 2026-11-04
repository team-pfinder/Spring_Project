package com.lookation.auth.application

import com.lookation.account.infrastructure.AccountRepository
import com.lookation.auth.api.LoginForm
import com.lookation.auth.api.SignupForm
import com.lookation.global.error.ConflictException
import com.lookation.global.error.UnauthorizedException
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService,
) {

    @Transactional
    fun signup(form: SignupForm): TokenPair {
        if (accountRepository.existsByEmail(form.email)) {
            throw ConflictException("이미 가입된 이메일입니다")
        }
        val accountId = try {
            accountRepository.insert(
                email = form.email,
                passwordHash = requireNotNull(passwordEncoder.encode(form.password)),
                name = form.name,
                nickname = form.nickname,
            )
        } catch (_: DuplicateKeyException) {
            throw ConflictException("이미 가입된 이메일입니다")
        }
        accountRepository.addRole(accountId, "MEMBER")
        return tokenService.issue(accountId, listOf("MEMBER"))
    }

    @Transactional
    fun login(form: LoginForm): TokenPair {
        val account = accountRepository.findByEmail(form.email)
            ?: throw UnauthorizedException("이메일 또는 비밀번호가 올바르지 않습니다")
        if (account.status != "ACTIVE") {
            throw UnauthorizedException("사용할 수 없는 계정입니다")
        }
        if (!passwordEncoder.matches(form.password, account.passwordHash)) {
            throw UnauthorizedException("이메일 또는 비밀번호가 올바르지 않습니다")
        }
        return tokenService.issue(account.id, accountRepository.roles(account.id))
    }

    fun refresh(rawRefreshToken: String): TokenPair = tokenService.rotate(rawRefreshToken)

    fun logout(rawRefreshToken: String) = tokenService.revoke(rawRefreshToken)
}
