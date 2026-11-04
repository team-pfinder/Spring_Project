package com.lookation.service

import com.lookation.domain.AccountStatus
import com.lookation.domain.VerificationPurpose
import com.lookation.repository.AccountRepository
import com.lookation.controller.form.LoginForm
import com.lookation.controller.form.PasswordResetConfirmForm
import com.lookation.controller.form.SignupForm
import com.lookation.repository.VerificationRepository
import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ConflictException
import com.lookation.global.error.TooManyRequestsException
import com.lookation.global.error.UnauthorizedException
import com.lookation.global.mail.MailSender
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom
import java.time.OffsetDateTime

@Service
class AuthService(
    private val accountRepository: AccountRepository,
    private val verificationRepository: VerificationRepository,
    private val mailSender: MailSender,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService,
) {

    @Transactional
    fun signup(form: SignupForm): TokenPair {
        if (accountRepository.existsByEmail(form.email)) {
            throw ConflictException("이미 가입된 이메일입니다")
        }
        consumeCode(form.email, VerificationPurpose.SIGNUP, form.code)
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
        if (account.status != AccountStatus.ACTIVE) {
            throw UnauthorizedException("사용할 수 없는 계정입니다")
        }
        if (!passwordEncoder.matches(form.password, account.passwordHash)) {
            throw UnauthorizedException("이메일 또는 비밀번호가 올바르지 않습니다")
        }
        return tokenService.issue(account.id, accountRepository.roles(account.id))
    }

    @Transactional
    fun requestVerification(email: String, purpose: String) {
        val purposeType = try {
            VerificationPurpose.valueOf(purpose)
        } catch (_: IllegalArgumentException) {
            throw BadRequestException("알 수 없는 인증 목적입니다")
        }
        requestVerification(email, purposeType)
    }

    private fun requestVerification(email: String, purpose: VerificationPurpose) {
        val now = OffsetDateTime.now()
        val latest = verificationRepository.findLatest(email, purpose)
        if (latest != null && latest.createdAt.plusSeconds(RESEND_COOLDOWN_SECONDS).isAfter(now)) {
            throw TooManyRequestsException("인증 코드는 ${RESEND_COOLDOWN_SECONDS}초 후에 다시 요청할 수 있습니다")
        }
        if (purpose == VerificationPurpose.PASSWORD_RESET && !accountRepository.existsByEmail(email)) {
            return
        }
        val code = String.format("%06d", SecureRandom().nextInt(1_000_000))
        verificationRepository.insert(email, hash(code), purpose, now.plusMinutes(CODE_TTL_MINUTES))
        mailSender.sendVerificationCode(email, code, purpose.name)
    }

    @Transactional
    fun resetPassword(form: PasswordResetConfirmForm) {
        consumeCode(form.email, VerificationPurpose.PASSWORD_RESET, form.code)
        val account = accountRepository.findByEmail(form.email)
            ?: throw BadRequestException("인증 코드가 올바르지 않습니다")
        accountRepository.updatePassword(account.id, requireNotNull(passwordEncoder.encode(form.newPassword)))
        tokenService.revokeAllForAccount(account.id)
    }

    private fun consumeCode(email: String, purpose: VerificationPurpose, code: String) {
        val row = verificationRepository.findLatest(email, purpose)
            ?: throw BadRequestException("인증 코드를 먼저 요청해 주세요")
        if (row.usedAt != null) throw BadRequestException("이미 사용된 인증 코드입니다")
        if (row.expiresAt.isBefore(OffsetDateTime.now())) throw BadRequestException("인증 코드가 만료되었습니다")
        if (row.attempts >= MAX_ATTEMPTS) throw BadRequestException("인증 시도 횟수를 초과했습니다")
        if (row.codeHash != hash(code)) {
            verificationRepository.incrementAttempts(row.id)
            throw BadRequestException("인증 코드가 올바르지 않습니다")
        }
        verificationRepository.markUsed(row.id)
    }

    private fun hash(value: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(value.toByteArray(StandardCharsets.UTF_8))
            .joinToString("") { "%02x".format(it.toInt() and 0xff) }

    fun refresh(rawRefreshToken: String): TokenPair = tokenService.rotate(rawRefreshToken)

    fun logout(rawRefreshToken: String) = tokenService.revoke(rawRefreshToken)

    companion object {
        private const val CODE_TTL_MINUTES = 10L
        private const val RESEND_COOLDOWN_SECONDS = 60L
        private const val MAX_ATTEMPTS = 5
    }
}
