package com.lookation.repository

import com.lookation.jooq.tables.Accounts.ACCOUNTS
import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import com.lookation.jooq.tables.ReviewReplies.REVIEW_REPLIES
import com.lookation.jooq.tables.Reviews.REVIEWS
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

data class ReviewRow(
    val id: Long,
    val reservationId: Long,
    val spaceId: Long,
    val memberId: Long,
    val nickname: String,
    val rating: Int,
    val content: String,
    val blindedAt: OffsetDateTime?,
    val createdAt: OffsetDateTime,
    val reply: String?,
)

@Repository
class ReviewRepository(private val dsl: DSLContext) {

    fun insert(reservationId: Long, spaceId: Long, memberId: Long, rating: Int, content: String): Long =
        dsl.insertInto(REVIEWS)
            .set(REVIEWS.RESERVATION_ID, reservationId)
            .set(REVIEWS.SPACE_ID, spaceId)
            .set(REVIEWS.MEMBER_ID, memberId)
            .set(REVIEWS.RATING, rating.toShort())
            .set(REVIEWS.CONTENT, content)
            .returningResult(REVIEWS.ID)
            .fetchOne()!!
            .value1()

    fun existsByReservation(reservationId: Long): Boolean =
        dsl.fetchCount(REVIEWS, REVIEWS.RESERVATION_ID.eq(reservationId)) > 0

    fun findById(id: Long): ReviewRow? = baseQuery()
        .where(REVIEWS.ID.eq(id))
        .fetchOne()
        ?.toRow()

    fun listBySpace(spaceId: Long, cursor: Long?, limit: Int): List<ReviewRow> {
        var condition = REVIEWS.SPACE_ID.eq(spaceId).and(REVIEWS.BLINDED_AT.isNull)
        if (cursor != null) condition = condition.and(REVIEWS.ID.lt(cursor))
        return baseQuery()
            .where(condition)
            .orderBy(REVIEWS.ID.desc())
            .limit(limit)
            .fetch()
            .map { it.toRow() }
    }

    fun blind(id: Long): Int =
        dsl.update(REVIEWS)
            .set(REVIEWS.BLINDED_AT, OffsetDateTime.now())
            .where(REVIEWS.ID.eq(id))
            .and(REVIEWS.BLINDED_AT.isNull)
            .execute()

    fun insertReply(reviewId: Long, hostId: Long, content: String) {
        dsl.insertInto(REVIEW_REPLIES)
            .set(REVIEW_REPLIES.REVIEW_ID, reviewId)
            .set(REVIEW_REPLIES.HOST_ID, hostId)
            .set(REVIEW_REPLIES.CONTENT, content)
            .execute()
    }

    fun countImages(reviewId: Long): Int =
        dsl.fetchCount(
            ATTACHMENTS,
            ATTACHMENTS.OWNER_TYPE.eq("REVIEW").and(ATTACHMENTS.OWNER_ID.eq(reviewId)),
        )

    fun imageKeys(reviewIds: List<Long>): Map<Long, List<String>> {
        if (reviewIds.isEmpty()) return emptyMap()
        return dsl.select(ATTACHMENTS.OWNER_ID, ATTACHMENTS.STORAGE_KEY)
            .from(ATTACHMENTS)
            .where(ATTACHMENTS.OWNER_TYPE.eq("REVIEW"))
            .and(ATTACHMENTS.OWNER_ID.`in`(reviewIds))
            .orderBy(ATTACHMENTS.ID)
            .fetch()
            .groupBy({ it.value1() }, { it.value2() })
    }

    fun addImage(reviewId: Long, key: String, originalName: String, contentType: String, size: Long) {
        dsl.insertInto(ATTACHMENTS)
            .set(ATTACHMENTS.OWNER_TYPE, "REVIEW")
            .set(ATTACHMENTS.OWNER_ID, reviewId)
            .set(ATTACHMENTS.STORAGE_KEY, key)
            .set(ATTACHMENTS.ORIGINAL_NAME, originalName)
            .set(ATTACHMENTS.CONTENT_TYPE, contentType)
            .set(ATTACHMENTS.SIZE_BYTES, size)
            .execute()
    }

    private fun baseQuery() =
        dsl.select(
            REVIEWS.ID,
            REVIEWS.RESERVATION_ID,
            REVIEWS.SPACE_ID,
            REVIEWS.MEMBER_ID,
            ACCOUNTS.NICKNAME,
            REVIEWS.RATING,
            REVIEWS.CONTENT,
            REVIEWS.BLINDED_AT,
            REVIEWS.CREATED_AT,
            REVIEW_REPLIES.CONTENT,
        )
            .from(REVIEWS)
            .join(ACCOUNTS).on(ACCOUNTS.ID.eq(REVIEWS.MEMBER_ID))
            .leftJoin(REVIEW_REPLIES).on(REVIEW_REPLIES.REVIEW_ID.eq(REVIEWS.ID))

    private fun org.jooq.Record10<Long, Long, Long, Long, String, Short, String, OffsetDateTime?, OffsetDateTime, String?>.toRow() =
        ReviewRow(
            id = value1(),
            reservationId = value2(),
            spaceId = value3(),
            memberId = value4(),
            nickname = value5(),
            rating = value6().toInt(),
            content = value7(),
            blindedAt = value8(),
            createdAt = value9(),
            reply = value10(),
        )
}
