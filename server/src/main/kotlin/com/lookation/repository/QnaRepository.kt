package com.lookation.repository

import com.lookation.domain.QnaStatus
import com.lookation.jooq.tables.Accounts.ACCOUNTS
import com.lookation.jooq.tables.QnaReplies.QNA_REPLIES
import com.lookation.jooq.tables.Qnas.QNAS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

data class QnaRow(
    val id: Long,
    val spaceId: Long,
    val memberId: Long,
    val nickname: String,
    val title: String,
    val content: String,
    val status: QnaStatus,
    val createdAt: OffsetDateTime,
    val reply: String?,
)

@Repository
class QnaRepository(private val dsl: DSLContext) {

    fun insert(spaceId: Long, memberId: Long, title: String, content: String): Long =
        dsl.insertInto(QNAS)
            .set(QNAS.SPACE_ID, spaceId)
            .set(QNAS.MEMBER_ID, memberId)
            .set(QNAS.TITLE, title)
            .set(QNAS.CONTENT, content)
            .returningResult(QNAS.ID)
            .fetchOne()!!
            .value1()

    fun findById(id: Long): QnaRow? = baseQuery().where(QNAS.ID.eq(id)).fetchOne()?.toRow()

    fun listBySpace(spaceId: Long, cursor: Long?, limit: Int): List<QnaRow> {
        var condition = QNAS.SPACE_ID.eq(spaceId)
        if (cursor != null) condition = condition.and(QNAS.ID.lt(cursor))
        return baseQuery()
            .where(condition)
            .orderBy(QNAS.ID.desc())
            .limit(limit)
            .fetch()
            .map { it.toRow() }
    }

    fun markAnswered(id: Long): Int =
        dsl.update(QNAS)
            .set(QNAS.STATUS, QnaStatus.ANSWERED.name)
            .where(QNAS.ID.eq(id))
            .and(QNAS.STATUS.eq(QnaStatus.OPEN.name))
            .execute()

    fun insertReply(qnaId: Long, hostId: Long, content: String) {
        dsl.insertInto(QNA_REPLIES)
            .set(QNA_REPLIES.QNA_ID, qnaId)
            .set(QNA_REPLIES.HOST_ID, hostId)
            .set(QNA_REPLIES.CONTENT, content)
            .execute()
    }

    fun deleteOpen(id: Long, memberId: Long): Int =
        dsl.deleteFrom(QNAS)
            .where(QNAS.ID.eq(id))
            .and(QNAS.MEMBER_ID.eq(memberId))
            .and(QNAS.STATUS.eq(QnaStatus.OPEN.name))
            .execute()

    private fun baseQuery() =
        dsl.select(
            QNAS.ID,
            QNAS.SPACE_ID,
            QNAS.MEMBER_ID,
            ACCOUNTS.NICKNAME,
            QNAS.TITLE,
            QNAS.CONTENT,
            QNAS.STATUS,
            QNAS.CREATED_AT,
            QNA_REPLIES.CONTENT,
        )
            .from(QNAS)
            .join(ACCOUNTS).on(ACCOUNTS.ID.eq(QNAS.MEMBER_ID))
            .leftJoin(QNA_REPLIES).on(QNA_REPLIES.QNA_ID.eq(QNAS.ID))

    private fun org.jooq.Record9<Long, Long, Long, String, String, String, String, OffsetDateTime, String?>.toRow() =
        QnaRow(
            id = value1(),
            spaceId = value2(),
            memberId = value3(),
            nickname = value4(),
            title = value5(),
            content = value6(),
            status = QnaStatus.valueOf(value7()),
            createdAt = value8(),
            reply = value9(),
        )
}
