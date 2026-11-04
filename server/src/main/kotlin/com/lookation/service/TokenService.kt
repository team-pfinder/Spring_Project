package com.lookation.service

import com.lookation.repository.AccountRepository
import com.lookation.repository.RefreshTokenRepository
import com.lookation.global.error.UnauthorizedException
import com.lookation.global.security.JwtProperties
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom
import java.time.Instant
import java.time.OffsetDateTime
import java.util.Base64
import java.util.UUID

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
)

@Service
class TokenService(
    private val jwtEncoder: JwtEncoder,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val accountRepository: AccountRepository,
    private val revocations: RefreshTokenRevocationService,
) {

    fun issue(accountId: Long, roles: List<String>): TokenPair = create(accountId, roles, UUID.randomUUID())

    @Transactional
    fun rotate(rawRefreshToken: String): TokenPair {
        val token = refreshTokenRepository.findByHash(hash(rawRefreshToken))
            ?: throw UnauthorizedException("유효하지 않은 refresh token입니다")
        if (token.revokedAt != null) {
            revocations.revokeFamily(token.familyId)
            throw UnauthorizedException("이미 사용된 refresh token입니다")
        }
        if (token.expiresAt.isBefore(OffsetDateTime.now())) {
            throw UnauthorizedException("만료된 refresh token입니다")
        }
        val account = accountRepository.findById(token.accountId)
        if (account == null || account.status != com.lookation.domain.AccountStatus.ACTIVE) {
            revocations.revokeFamily(token.familyId)
            throw UnauthorizedException("사용할 수 없는 계정입니다")
        }
        if (refreshTokenRepository.revoke(token.id) != 1) {
            revocations.revokeFamily(token.familyId)
            throw UnauthorizedException("이미 사용된 refresh token입니다")
        }
        return create(token.accountId, accountRepository.roles(token.accountId), token.familyId)
    }

    fun revokeAllForAccount(accountId: Long) {
        refreshTokenRepository.revokeAllForAccount(accountId)
    }

    fun revoke(rawRefreshToken: String) {
        refreshTokenRepository.findByHash(hash(rawRefreshToken))
            ?.let { refreshTokenRepository.revoke(it.id) }
    }

    private fun create(accountId: Long, roles: List<String>, familyId: UUID): TokenPair {
        val rawRefreshToken = generateRefreshToken()
        refreshTokenRepository.save(
            accountId = accountId,
            tokenHash = hash(rawRefreshToken),
            familyId = familyId,
            expiresAt = OffsetDateTime.now().plus(jwtProperties.refreshTtl),
        )
        return TokenPair(
            accessToken = issueAccessToken(accountId, roles),
            refreshToken = rawRefreshToken,
            expiresIn = jwtProperties.accessTtl.seconds,
        )
    }

    private fun issueAccessToken(accountId: Long, roles: List<String>): String {
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer(jwtProperties.issuer)
            .subject(accountId.toString())
            .issuedAt(now)
            .expiresAt(now.plus(jwtProperties.accessTtl))
            .claim("roles", roles)
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    private fun generateRefreshToken(): String {
        val bytes = ByteArray(32)
        SecureRandom().nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    private fun hash(value: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(value.toByteArray(StandardCharsets.UTF_8))
            .joinToString("") { "%02x".format(it.toInt() and 0xff) }
}
