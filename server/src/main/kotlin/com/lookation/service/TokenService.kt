package com.lookation.service

import com.lookation.domain.AccountRole
import com.lookation.domain.AccountStatus
import com.lookation.repository.AccountRepository
import com.lookation.repository.RefreshTokenRepository
import com.lookation.repository.RefreshTokenRow
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
import java.time.Duration
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
) {

    fun issue(accountId: Long, roles: List<AccountRole>): TokenPair = create(accountId, roles, UUID.randomUUID())

    @Transactional
    fun rotate(rawRefreshToken: String, grace: Duration = Duration.ZERO): TokenPair {
        val token = refreshTokenRepository.findByHash(hash(rawRefreshToken))
            ?: throw UnauthorizedException("유효하지 않은 refresh token입니다")
        if (token.expiresAt.isBefore(OffsetDateTime.now())) {
            throw UnauthorizedException("만료된 refresh token입니다")
        }
        if (token.revokedAt != null) {
            // 두 탭이 동시에 갱신하면 한쪽은 방금 폐기된 토큰을 들고 온다.
            // 유예 시간 안이고 family에 아직 유효한 토큰이 있으면 회전 경쟁으로 보고 계속 발급한다.
            return rotateWithinGrace(token, grace)
                ?: revokeFamilyAndFail(token, "이미 사용된 refresh token입니다")
        }
        activeAccount(token.accountId)
            ?: revokeFamilyAndFail(token, "사용할 수 없는 계정입니다")
        if (refreshTokenRepository.revoke(token.id) != 1) {
            // 조건부 폐기에서 진 쪽도 회전 경쟁이다.
            return rotateWithinGrace(token, grace)
                ?: revokeFamilyAndFail(token, "이미 사용된 refresh token입니다")
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

    /** HTML 로그아웃은 브라우저 탭 전체를 로그아웃시켜야 하므로 family 단위로 폐기한다. */
    fun revokeFamily(rawRefreshToken: String) {
        refreshTokenRepository.findByHash(hash(rawRefreshToken))
            ?.let { refreshTokenRepository.revokeFamily(it.familyId) }
    }

    private fun rotateWithinGrace(token: RefreshTokenRow, grace: Duration): TokenPair? {
        if (grace.isZero) return null
        val revokedAt = token.revokedAt
        if (revokedAt != null && revokedAt.isBefore(OffsetDateTime.now().minus(grace))) {
            return null
        }
        if (!refreshTokenRepository.hasActiveInFamily(token.familyId)) return null
        activeAccount(token.accountId) ?: return null
        return create(token.accountId, accountRepository.roles(token.accountId), token.familyId)
    }

    private fun activeAccount(accountId: Long) =
        accountRepository.findById(accountId)?.takeIf { it.status == AccountStatus.ACTIVE }

    private fun revokeFamilyAndFail(token: RefreshTokenRow, message: String): Nothing {
        refreshTokenRepository.revokeFamily(token.familyId)
        throw UnauthorizedException(message)
    }

    private fun create(accountId: Long, roles: List<AccountRole>, familyId: UUID): TokenPair {
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

    private fun issueAccessToken(accountId: Long, roles: List<AccountRole>): String {
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer(jwtProperties.issuer)
            .subject(accountId.toString())
            .issuedAt(now)
            .expiresAt(now.plus(jwtProperties.accessTtl))
            .claim("roles", roles.map { it.name })
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
