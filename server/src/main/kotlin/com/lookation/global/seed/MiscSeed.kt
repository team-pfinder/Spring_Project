package com.lookation.global.seed

import com.lookation.domain.JobStatus
import com.lookation.domain.VerificationPurpose
import com.lookation.jooq.tables.EmailVerifications.EMAIL_VERIFICATIONS
import com.lookation.jooq.tables.JobRuns.JOB_RUNS
import org.jooq.DSLContext
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

/** Seeds the remaining enum coverage: job-run statuses and email-verification purposes. */
@Component
@Order(MiscSeed.ORDER)
class MiscSeed(private val dsl: DSLContext) : SeedStep {

    override val name = "배치 이력·인증 코드"

    @Transactional
    override fun seed(): SeedResult {
        val now = OffsetDateTime.now()
        var inserted = 0
        var skipped = 0

        JOB_RUNS_SEED.forEach { seed ->
            if (dsl.fetchCount(JOB_RUNS, JOB_RUNS.JOB_NAME.eq(seed.name)) > 0) {
                skipped++
                return@forEach
            }
            dsl.insertInto(JOB_RUNS)
                .set(JOB_RUNS.JOB_NAME, seed.name)
                .set(JOB_RUNS.STATUS, seed.status.name)
                .set(JOB_RUNS.PROCESSED_COUNT, seed.processed)
                .set(JOB_RUNS.MESSAGE, seed.message)
                .set(JOB_RUNS.STARTED_AT, now.minusDays(1))
                .set(JOB_RUNS.FINISHED_AT, if (seed.status == JobStatus.RUNNING) null else now.minusDays(1).plusMinutes(1))
                .execute()
            inserted++
        }

        VERIFICATIONS_SEED.forEach { seed ->
            val exists = dsl.fetchCount(
                EMAIL_VERIFICATIONS,
                EMAIL_VERIFICATIONS.EMAIL.eq(seed.email).and(EMAIL_VERIFICATIONS.PURPOSE.eq(seed.purpose.name)),
            ) > 0
            if (exists) {
                skipped++
                return@forEach
            }
            dsl.insertInto(EMAIL_VERIFICATIONS)
                .set(EMAIL_VERIFICATIONS.EMAIL, seed.email)
                .set(EMAIL_VERIFICATIONS.CODE_HASH, "0".repeat(64))
                .set(EMAIL_VERIFICATIONS.PURPOSE, seed.purpose.name)
                .set(EMAIL_VERIFICATIONS.ATTEMPTS, 0)
                .set(EMAIL_VERIFICATIONS.EXPIRES_AT, now.plusDays(1))
                .execute()
            inserted++
        }

        return SeedResult(inserted = inserted, skipped = skipped)
    }

    private data class SeedJobRun(
        val name: String,
        val status: JobStatus,
        val processed: Int,
        val message: String?,
    )

    private data class SeedVerification(
        val email: String,
        val purpose: VerificationPurpose,
    )

    private companion object {
        const val ORDER = 70

        val JOB_RUNS_SEED = listOf(
            SeedJobRun("SEED_SAMPLE_COMPLETE", JobStatus.SUCCESS, 1, null),
            SeedJobRun("SEED_SAMPLE_SETTLE", JobStatus.FAILED, 0, "시드 실패 예시"),
            SeedJobRun("SEED_SAMPLE_EXPIRE", JobStatus.RUNNING, 0, null),
        )

        val VERIFICATIONS_SEED = listOf(
            SeedVerification("seed-member-1@lookation.local", VerificationPurpose.SIGNUP),
            SeedVerification("seed-member-1@lookation.local", VerificationPurpose.PASSWORD_RESET),
        )
    }
}
