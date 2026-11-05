package com.lookation.global.seed

import com.lookation.domain.QnaStatus
import com.lookation.domain.ReportStatus
import com.lookation.jooq.tables.Helps.HELPS
import com.lookation.jooq.tables.Messages.MESSAGES
import com.lookation.jooq.tables.Notices.NOTICES
import com.lookation.jooq.tables.QnaReplies.QNA_REPLIES
import com.lookation.jooq.tables.Qnas.QNAS
import com.lookation.jooq.tables.Reports.REPORTS
import com.lookation.jooq.tables.ReviewReplies.REVIEW_REPLIES
import com.lookation.jooq.tables.Reviews.REVIEWS
import org.jooq.DSLContext
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

/** Seeds reviews, Q&As, messages, reports, notices and helps. */
@Component
@Order(ContentSeed.ORDER)
class ContentSeed(private val dsl: DSLContext) : SeedStep {

    override val name = "리뷰·문의·메시지·신고·공지·도움말"

    @Transactional
    override fun seed(): SeedResult {
        val now = OffsetDateTime.now()
        var inserted = 0
        var skipped = 0

        val completedId = dsl.reservationId("seed:reservation:completed")!!
        val spaceId = dsl.spaceId("시드 파티룸")
        val memberId = dsl.accountId("seed-member-1@lookation.local")
        val hostId = dsl.accountId("seed-host-1@lookation.local")
        val adminId = dsl.accountId("admin@lookation.local")

        val reviewId = dsl.select(REVIEWS.ID)
            .from(REVIEWS)
            .where(REVIEWS.RESERVATION_ID.eq(completedId))
            .fetchOne(REVIEWS.ID)
        val review = if (reviewId == null) {
            inserted++
            dsl.insertInto(REVIEWS)
                .set(REVIEWS.RESERVATION_ID, completedId)
                .set(REVIEWS.SPACE_ID, spaceId)
                .set(REVIEWS.MEMBER_ID, memberId)
                .set(REVIEWS.RATING, 5.toShort())
                .set(REVIEWS.CONTENT, "시드 리뷰입니다. 시설이 좋았습니다.")
                .set(REVIEWS.CREATED_AT, now.minusDays(9))
                .returningResult(REVIEWS.ID)
                .fetchOne()!!
                .value1()
        } else {
            skipped++
            reviewId
        }
        if (dsl.fetchCount(REVIEW_REPLIES, REVIEW_REPLIES.REVIEW_ID.eq(review)) == 0) {
            dsl.insertInto(REVIEW_REPLIES)
                .set(REVIEW_REPLIES.REVIEW_ID, review)
                .set(REVIEW_REPLIES.HOST_ID, hostId)
                .set(REVIEW_REPLIES.CONTENT, "시드 리뷰 답글입니다. 감사합니다.")
                .execute()
            inserted++
        } else {
            skipped++
        }

        val openQna = ensureQna("시드 문의(미답변)", QnaStatus.OPEN, spaceId, memberId, now.minusDays(3))
        val answeredQna = ensureQna(
            "시드 문의(답변)",
            QnaStatus.ANSWERED,
            spaceId,
            dsl.accountId("seed-member-2@lookation.local"),
            now.minusDays(4),
        )
        listOf(openQna, answeredQna).forEach { if (it.inserted) inserted++ else skipped++ }
        val answeredQnaId = answeredQna.id
        if (dsl.fetchCount(QNA_REPLIES, QNA_REPLIES.QNA_ID.eq(answeredQnaId)) == 0) {
            dsl.insertInto(QNA_REPLIES)
                .set(QNA_REPLIES.QNA_ID, answeredQnaId)
                .set(QNA_REPLIES.HOST_ID, hostId)
                .set(QNA_REPLIES.CONTENT, "시드 문의 답변입니다.")
                .execute()
            inserted++
        } else {
            skipped++
        }

        val confirmedId = dsl.reservationId("seed:reservation:confirmed")!!
        listOf(
            Triple(confirmedId, memberId, "시드 메시지: 예약 문의드립니다."),
            Triple(confirmedId, hostId, "시드 메시지: 네, 확인했습니다."),
        ).forEach { (reservationId, senderId, content) ->
            val exists = dsl.fetchCount(
                MESSAGES,
                MESSAGES.RESERVATION_ID.eq(reservationId)
                    .and(MESSAGES.SENDER_ID.eq(senderId))
                    .and(MESSAGES.CONTENT.eq(content)),
            ) > 0
            if (exists) {
                skipped++
                return@forEach
            }
            dsl.insertInto(MESSAGES)
                .set(MESSAGES.RESERVATION_ID, reservationId)
                .set(MESSAGES.SENDER_ID, senderId)
                .set(MESSAGES.CONTENT, content)
                .execute()
            inserted++
        }

        listOf(
            SeedReport("seed-member-2@lookation.local", "SPACE", spaceId, ReportStatus.RECEIVED),
            SeedReport("seed-member-1@lookation.local", "REVIEW", review, ReportStatus.ACCEPTED),
            SeedReport("seed-host-2@lookation.local", "QNA", answeredQnaId, ReportStatus.REJECTED),
        ).forEach { report ->
            val reporterId = dsl.accountId(report.reporterEmail)
            val exists = dsl.fetchCount(
                REPORTS,
                REPORTS.REPORTER_ID.eq(reporterId)
                    .and(REPORTS.TARGET_TYPE.eq(report.targetType))
                    .and(REPORTS.TARGET_ID.eq(report.targetId)),
            ) > 0
            if (exists) {
                skipped++
                return@forEach
            }
            dsl.insertInto(REPORTS)
                .set(REPORTS.REPORTER_ID, reporterId)
                .set(REPORTS.TARGET_TYPE, report.targetType)
                .set(REPORTS.TARGET_ID, report.targetId)
                .set(REPORTS.REASON, "시드 신고 사유(${report.status.name})")
                .set(REPORTS.STATUS, report.status.name)
                .set(REPORTS.ADMIN_ID, if (report.status == ReportStatus.RECEIVED) null else adminId)
                .set(REPORTS.CREATED_AT, now.minusDays(3))
                .set(REPORTS.PROCESSED_AT, if (report.status == ReportStatus.RECEIVED) null else now.minusDays(2))
                .execute()
            inserted++
        }

        NOTICES_SEED.forEach { notice ->
            if (dsl.fetchCount(NOTICES, NOTICES.TITLE.eq(notice.first)) > 0) {
                skipped++
                return@forEach
            }
            dsl.insertInto(NOTICES)
                .set(NOTICES.TITLE, notice.first)
                .set(NOTICES.CONTENT, notice.second)
                .set(NOTICES.IMPORTANT, notice.third)
                .execute()
            inserted++
        }

        HELPS_SEED.forEach { help ->
            if (dsl.fetchCount(HELPS, HELPS.TITLE.eq(help.first)) > 0) {
                skipped++
                return@forEach
            }
            dsl.insertInto(HELPS)
                .set(HELPS.CATEGORY, help.second)
                .set(HELPS.TITLE, help.first)
                .set(HELPS.CONTENT, help.third)
                .execute()
            inserted++
        }

        return SeedResult(inserted = inserted, skipped = skipped)
    }

    private fun ensureQna(
        title: String,
        status: QnaStatus,
        spaceId: Long,
        memberId: Long,
        createdAt: OffsetDateTime,
    ): SeedQna {
        dsl.select(QNAS.ID).from(QNAS).where(QNAS.TITLE.eq(title)).fetchOne(QNAS.ID)
            ?.let { return SeedQna(it, inserted = false) }
        val id = dsl.insertInto(QNAS)
            .set(QNAS.SPACE_ID, spaceId)
            .set(QNAS.MEMBER_ID, memberId)
            .set(QNAS.TITLE, title)
            .set(QNAS.CONTENT, "시드 문의 내용입니다.")
            .set(QNAS.STATUS, status.name)
            .set(QNAS.CREATED_AT, createdAt)
            .returningResult(QNAS.ID)
            .fetchOne()!!
            .value1()
        return SeedQna(id, inserted = true)
    }

    private data class SeedQna(val id: Long, val inserted: Boolean)

    private data class SeedReport(
        val reporterEmail: String,
        val targetType: String,
        val targetId: Long,
        val status: ReportStatus,
    )

    private companion object {
        const val ORDER = 60

        val NOTICES_SEED = listOf(
            Triple("[시드] 서비스 점검 안내", "시드 공지 내용입니다.", true),
            Triple("[시드] 신규 기능 안내", "시드 공지 내용입니다.", false),
        )

        val HELPS_SEED = listOf(
            Triple("예약을 취소하려면?", "예약", "마이페이지에서 예약을 취소할 수 있습니다."),
            Triple("정산은 언제 되나요?", "정산", "이용 완료 후 다음 날 정산됩니다."),
        )
    }
}
