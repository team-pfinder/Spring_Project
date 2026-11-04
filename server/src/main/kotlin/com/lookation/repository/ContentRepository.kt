package com.lookation.repository

import com.lookation.jooq.tables.Helps.HELPS
import com.lookation.jooq.tables.Notices.NOTICES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

data class NoticeRow(
    val id: Long,
    val title: String,
    val content: String,
    val important: Boolean,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
)

data class HelpRow(
    val id: Long,
    val category: String,
    val title: String,
    val content: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
)

@Repository
class ContentRepository(private val dsl: DSLContext) {

    fun insertNotice(title: String, content: String, important: Boolean): Long =
        dsl.insertInto(NOTICES)
            .set(NOTICES.TITLE, title)
            .set(NOTICES.CONTENT, content)
            .set(NOTICES.IMPORTANT, important)
            .returningResult(NOTICES.ID)
            .fetchOne()!!
            .value1()

    fun findNotice(id: Long): NoticeRow? =
        dsl.selectFrom(NOTICES).where(NOTICES.ID.eq(id)).fetchOne()?.let {
            NoticeRow(it.get(NOTICES.ID), it.get(NOTICES.TITLE), it.get(NOTICES.CONTENT), it.get(NOTICES.IMPORTANT), it.get(NOTICES.CREATED_AT), it.get(NOTICES.UPDATED_AT))
        }

    fun listNotices(cursor: Long?, limit: Int): List<NoticeRow> {
        var condition: org.jooq.Condition = DSL.trueCondition()
        if (cursor != null) condition = NOTICES.ID.lt(cursor)
        return dsl.selectFrom(NOTICES)
            .where(condition)
            .orderBy(NOTICES.ID.desc())
            .limit(limit)
            .fetch()
            .map { NoticeRow(it.get(NOTICES.ID), it.get(NOTICES.TITLE), it.get(NOTICES.CONTENT), it.get(NOTICES.IMPORTANT), it.get(NOTICES.CREATED_AT), it.get(NOTICES.UPDATED_AT)) }
    }

    fun updateNotice(id: Long, title: String, content: String, important: Boolean): Int =
        dsl.update(NOTICES)
            .set(NOTICES.TITLE, title)
            .set(NOTICES.CONTENT, content)
            .set(NOTICES.IMPORTANT, important)
            .set(NOTICES.UPDATED_AT, OffsetDateTime.now())
            .where(NOTICES.ID.eq(id))
            .execute()

    fun deleteNotice(id: Long): Int =
        dsl.deleteFrom(NOTICES).where(NOTICES.ID.eq(id)).execute()

    fun insertHelp(category: String, title: String, content: String): Long =
        dsl.insertInto(HELPS)
            .set(HELPS.CATEGORY, category)
            .set(HELPS.TITLE, title)
            .set(HELPS.CONTENT, content)
            .returningResult(HELPS.ID)
            .fetchOne()!!
            .value1()

    fun findHelp(id: Long): HelpRow? =
        dsl.selectFrom(HELPS).where(HELPS.ID.eq(id)).fetchOne()?.let {
            HelpRow(it.get(HELPS.ID), it.get(HELPS.CATEGORY), it.get(HELPS.TITLE), it.get(HELPS.CONTENT), it.get(HELPS.CREATED_AT), it.get(HELPS.UPDATED_AT))
        }

    fun listHelps(category: String?, cursor: Long?, limit: Int): List<HelpRow> {
        var condition: org.jooq.Condition = DSL.trueCondition()
        if (category != null) condition = condition.and(HELPS.CATEGORY.eq(category))
        if (cursor != null) condition = condition.and(HELPS.ID.lt(cursor))
        return dsl.selectFrom(HELPS)
            .where(condition)
            .orderBy(HELPS.ID.desc())
            .limit(limit)
            .fetch()
            .map { HelpRow(it.get(HELPS.ID), it.get(HELPS.CATEGORY), it.get(HELPS.TITLE), it.get(HELPS.CONTENT), it.get(HELPS.CREATED_AT), it.get(HELPS.UPDATED_AT)) }
    }

    fun updateHelp(id: Long, category: String, title: String, content: String): Int =
        dsl.update(HELPS)
            .set(HELPS.CATEGORY, category)
            .set(HELPS.TITLE, title)
            .set(HELPS.CONTENT, content)
            .set(HELPS.UPDATED_AT, OffsetDateTime.now())
            .where(HELPS.ID.eq(id))
            .execute()

    fun deleteHelp(id: Long): Int =
        dsl.deleteFrom(HELPS).where(HELPS.ID.eq(id)).execute()
}
