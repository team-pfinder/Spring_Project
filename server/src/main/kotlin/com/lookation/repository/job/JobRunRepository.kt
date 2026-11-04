package com.lookation.repository.job

import com.lookation.jooq.tables.JobRuns.JOB_RUNS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class JobRunRepository(private val dsl: DSLContext) {

    fun start(jobName: String): Long =
        dsl.insertInto(JOB_RUNS)
            .set(JOB_RUNS.JOB_NAME, jobName)
            .returningResult(JOB_RUNS.ID)
            .fetchOne()!!
            .value1()

    fun finish(id: Long, status: String, processed: Int, message: String? = null) {
        dsl.update(JOB_RUNS)
            .set(JOB_RUNS.STATUS, status)
            .set(JOB_RUNS.PROCESSED_COUNT, processed)
            .set(JOB_RUNS.MESSAGE, message?.take(500))
            .set(JOB_RUNS.FINISHED_AT, OffsetDateTime.now())
            .where(JOB_RUNS.ID.eq(id))
            .execute()
    }
}
