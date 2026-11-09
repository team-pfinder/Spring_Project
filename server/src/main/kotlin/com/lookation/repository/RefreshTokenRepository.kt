package com.lookation.repository

import com.lookation.jooq.tables.RefreshTokens.REFRESH_TOKENS
import org.jooq.DSLContext
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.UUID

data class RefreshTokenRow(
    val id: Long,
    val accountId: Long,
    val familyId: UUID,
    val expiresAt: OffsetDateTime,
    val revokedAt: OffsetDateTime?,
)

@Repository
class RefreshTokenRepository(private val dsl: DSLContext) {

    fun save(accountId: Long, tokenHash: String, familyId: UUID, expiresAt: OffsetDateTime) {
        dsl.insertInto(REFRESH_TOKENS)
            .set(REFRESH_TOKENS.ACCOUNT_ID, accountId)
            .set(REFRESH_TOKENS.TOKEN_HASH, tokenHash)
            .set(REFRESH_TOKENS.FAMILY_ID, familyId)
            .set(REFRESH_TOKENS.EXPIRES_AT, expiresAt)
            .execute()
    }

    fun findByHash(tokenHash: String): RefreshTokenRow? =
        dsl.selectFrom(REFRESH_TOKENS)
            .where(REFRESH_TOKENS.TOKEN_HASH.eq(tokenHash))
            .fetchOne()
            ?.let {
                RefreshTokenRow(
                    id = it.get(REFRESH_TOKENS.ID),
                    accountId = it.get(REFRESH_TOKENS.ACCOUNT_ID),
                    familyId = it.get(REFRESH_TOKENS.FAMILY_ID),
                    expiresAt = it.get(REFRESH_TOKENS.EXPIRES_AT),
                    revokedAt = it.get(REFRESH_TOKENS.REVOKED_AT),
                )
            }

    fun revoke(id: Long): Int =
        dsl.update(REFRESH_TOKENS)
            .set(REFRESH_TOKENS.REVOKED_AT, OffsetDateTime.now())
            .where(REFRESH_TOKENS.ID.eq(id))
            .and(REFRESH_TOKENS.REVOKED_AT.isNull)
            .execute()

    fun revokeAllForAccount(accountId: Long) {
        dsl.update(REFRESH_TOKENS)
            .set(REFRESH_TOKENS.REVOKED_AT, OffsetDateTime.now())
            .where(REFRESH_TOKENS.ACCOUNT_ID.eq(accountId))
            .and(REFRESH_TOKENS.REVOKED_AT.isNull)
            .execute()
    }

    /**
     * 계열 폐기는 재사용 탐지 시 401 응답과 함께 커밋돼야 하므로 바깥 트랜잭션과 무관하게 별도 트랜잭션으로 실행한다.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun revokeFamily(familyId: UUID) {
        dsl.update(REFRESH_TOKENS)
            .set(REFRESH_TOKENS.REVOKED_AT, OffsetDateTime.now())
            .where(REFRESH_TOKENS.FAMILY_ID.eq(familyId))
            .and(REFRESH_TOKENS.REVOKED_AT.isNull)
            .execute()
    }

    fun hasActiveInFamily(familyId: UUID): Boolean =
        dsl.fetchExists(
            dsl.selectOne()
                .from(REFRESH_TOKENS)
                .where(REFRESH_TOKENS.FAMILY_ID.eq(familyId))
                .and(REFRESH_TOKENS.REVOKED_AT.isNull),
        )
}
