package com.lookation.repository

import com.lookation.jooq.tables.Reports.REPORTS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

data class ReportRow(
    val id: Long,
    val reporterId: Long,
    val targetType: String,
    val targetId: Long,
    val reason: String,
    val status: String,
    val createdAt: OffsetDateTime,
)

@Repository
class ReportRepository(private val dsl: DSLContext) {

    fun insert(reporterId: Long, targetType: String, targetId: Long, reason: String): Long =
        dsl.insertInto(REPORTS)
            .set(REPORTS.REPORTER_ID, reporterId)
            .set(REPORTS.TARGET_TYPE, targetType)
            .set(REPORTS.TARGET_ID, targetId)
            .set(REPORTS.REASON, reason)
            .returningResult(REPORTS.ID)
            .fetchOne()!!
            .value1()

    fun findById(id: Long): ReportRow? =
        dsl.selectFrom(REPORTS).where(REPORTS.ID.eq(id)).fetchOne()?.let {
            ReportRow(
                it.get(REPORTS.ID), it.get(REPORTS.REPORTER_ID), it.get(REPORTS.TARGET_TYPE),
                it.get(REPORTS.TARGET_ID), it.get(REPORTS.REASON), it.get(REPORTS.STATUS), it.get(REPORTS.CREATED_AT),
            )
        }

    fun list(status: String?, cursor: Long?, limit: Int): List<ReportRow> {
        var condition: org.jooq.Condition = org.jooq.impl.DSL.trueCondition()
        if (status != null) condition = condition.and(REPORTS.STATUS.eq(status))
        if (cursor != null) condition = condition.and(REPORTS.ID.lt(cursor))
        return dsl.selectFrom(REPORTS)
            .where(condition)
            .orderBy(REPORTS.ID.desc())
            .limit(limit)
            .fetch()
            .map {
                ReportRow(
                    it.get(REPORTS.ID), it.get(REPORTS.REPORTER_ID), it.get(REPORTS.TARGET_TYPE),
                    it.get(REPORTS.TARGET_ID), it.get(REPORTS.REASON), it.get(REPORTS.STATUS), it.get(REPORTS.CREATED_AT),
                )
            }
    }

    fun decide(id: Long, accepted: Boolean, adminId: Long): Int =
        dsl.update(REPORTS)
            .set(REPORTS.STATUS, if (accepted) "ACCEPTED" else "REJECTED")
            .set(REPORTS.ADMIN_ID, adminId)
            .set(REPORTS.PROCESSED_AT, OffsetDateTime.now())
            .where(REPORTS.ID.eq(id))
            .and(REPORTS.STATUS.eq("RECEIVED"))
            .execute()
}
