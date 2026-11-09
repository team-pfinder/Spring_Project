package com.lookation.repository

import com.lookation.jooq.tables.Sanctions.SANCTIONS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

data class SanctionRow(
    val id: Long,
    val accountId: Long,
    val reason: String,
    val level: Int,
    val startsAt: OffsetDateTime,
    val endsAt: OffsetDateTime?,
    val revokedAt: OffsetDateTime?,
)

@Repository
class SanctionRepository(private val dsl: DSLContext) {

    fun findActive(accountId: Long, now: OffsetDateTime): List<SanctionRow> =
        dsl.selectFrom(SANCTIONS)
            .where(SANCTIONS.ACCOUNT_ID.eq(accountId))
            .and(SANCTIONS.REVOKED_AT.isNull)
            .and(SANCTIONS.STARTS_AT.le(now))
            .and(SANCTIONS.ENDS_AT.isNull.or(SANCTIONS.ENDS_AT.gt(now)))
            .fetch()
            .map { it.toRow() }

    fun insert(accountId: Long, reason: String, level: Int, endsAt: OffsetDateTime?, adminId: Long): Long =
        dsl.insertInto(SANCTIONS)
            .set(SANCTIONS.ACCOUNT_ID, accountId)
            .set(SANCTIONS.REASON, reason)
            .set(SANCTIONS.LEVEL, level.toShort())
            .set(SANCTIONS.ENDS_AT, endsAt)
            .set(SANCTIONS.ADMIN_ID, adminId)
            .returningResult(SANCTIONS.ID)
            .fetchOne()!!
            .value1()

    fun findById(id: Long): SanctionRow? =
        dsl.selectFrom(SANCTIONS).where(SANCTIONS.ID.eq(id)).fetchOne()?.toRow()

    fun revoke(id: Long): Int =
        dsl.update(SANCTIONS)
            .set(SANCTIONS.REVOKED_AT, OffsetDateTime.now())
            .where(SANCTIONS.ID.eq(id))
            .and(SANCTIONS.REVOKED_AT.isNull)
            .execute()

    private fun org.jooq.Record.toRow() = SanctionRow(
        id = get(SANCTIONS.ID),
        accountId = get(SANCTIONS.ACCOUNT_ID),
        reason = get(SANCTIONS.REASON),
        level = get(SANCTIONS.LEVEL).toInt(),
        startsAt = get(SANCTIONS.STARTS_AT),
        endsAt = get(SANCTIONS.ENDS_AT),
        revokedAt = get(SANCTIONS.REVOKED_AT),
    )
}
