package com.lookation.repository

import com.lookation.jooq.tables.EmailVerifications.EMAIL_VERIFICATIONS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

data class VerificationRow(
    val id: Long,
    val email: String,
    val codeHash: String,
    val purpose: String,
    val attempts: Int,
    val expiresAt: OffsetDateTime,
    val usedAt: OffsetDateTime?,
    val createdAt: OffsetDateTime,
)

@Repository
class VerificationRepository(private val dsl: DSLContext) {

    fun insert(email: String, codeHash: String, purpose: String, expiresAt: OffsetDateTime) {
        dsl.insertInto(EMAIL_VERIFICATIONS)
            .set(EMAIL_VERIFICATIONS.EMAIL, email)
            .set(EMAIL_VERIFICATIONS.CODE_HASH, codeHash)
            .set(EMAIL_VERIFICATIONS.PURPOSE, purpose)
            .set(EMAIL_VERIFICATIONS.EXPIRES_AT, expiresAt)
            .execute()
    }

    fun findLatest(email: String, purpose: String): VerificationRow? =
        dsl.selectFrom(EMAIL_VERIFICATIONS)
            .where(EMAIL_VERIFICATIONS.EMAIL.equalIgnoreCase(email))
            .and(EMAIL_VERIFICATIONS.PURPOSE.eq(purpose))
            .orderBy(EMAIL_VERIFICATIONS.ID.desc())
            .limit(1)
            .fetchOne()
            ?.let {
                VerificationRow(
                    id = it.get(EMAIL_VERIFICATIONS.ID),
                    email = it.get(EMAIL_VERIFICATIONS.EMAIL),
                    codeHash = it.get(EMAIL_VERIFICATIONS.CODE_HASH),
                    purpose = it.get(EMAIL_VERIFICATIONS.PURPOSE),
                    attempts = it.get(EMAIL_VERIFICATIONS.ATTEMPTS),
                    expiresAt = it.get(EMAIL_VERIFICATIONS.EXPIRES_AT),
                    usedAt = it.get(EMAIL_VERIFICATIONS.USED_AT),
                    createdAt = it.get(EMAIL_VERIFICATIONS.CREATED_AT),
                )
            }

    fun incrementAttempts(id: Long) {
        dsl.update(EMAIL_VERIFICATIONS)
            .set(EMAIL_VERIFICATIONS.ATTEMPTS, EMAIL_VERIFICATIONS.ATTEMPTS.plus(1))
            .where(EMAIL_VERIFICATIONS.ID.eq(id))
            .execute()
    }

    fun markUsed(id: Long) {
        dsl.update(EMAIL_VERIFICATIONS)
            .set(EMAIL_VERIFICATIONS.USED_AT, OffsetDateTime.now())
            .where(EMAIL_VERIFICATIONS.ID.eq(id))
            .execute()
    }
}
