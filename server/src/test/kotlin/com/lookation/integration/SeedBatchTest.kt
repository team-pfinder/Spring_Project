package com.lookation.integration

import com.lookation.domain.ReservationStatus
import com.lookation.global.seed.InitializeService
import com.lookation.global.seed.SeedStep
import com.lookation.service.ReservationBatchService
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.time.OffsetDateTime

@SpringBootTest(
    properties = [
        "lookation.jwt.secret=0123456789abcdef0123456789abcdef",
        "lookation.encryption.key=AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQE=",
        "lookation.upload-dir=build/test-uploads",
        "lookation.admin.email=admin@lookation.local",
        "lookation.admin.password=lookation-admin1!",
        // Unique property keeps the Spring context cache from reusing another container context.
        "lookation.test.suite=seed-batch",
    ],
)
@Testcontainers
class SeedBatchTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:18-alpine"))
    }

    @Autowired
    private lateinit var steps: List<SeedStep>

    @Autowired
    private lateinit var batchService: ReservationBatchService

    @Autowired
    private lateinit var dsl: DSLContext

    @Test
    fun `the three scheduled batches run on seeded data only`() {
        InitializeService(steps).seed()
        val now = OffsetDateTime.now()

        assertThat(batchService.completeReservations(now)).isEqualTo(1)
        assertThat(batchService.expireReservations(now)).isEqualTo(1)
        assertThat(batchService.settle(now)).isEqualTo(1)

        assertThat(statusOf("seed:batch:complete")).isEqualTo(ReservationStatus.COMPLETED)
        assertThat(statusOf("seed:batch:expire")).isEqualTo(ReservationStatus.EXPIRED)

        val completed = dsl.resultQuery(
            "select id, price from reservations where status = 'COMPLETED'",
        ).fetch()
        assertThat(completed).hasSize(2)

        val settlements = dsl.resultQuery(
            "select reservation_id, gross_amount, fee_amount, net_amount from settlements",
        ).fetch()
        assertThat(settlements).hasSize(2)
        assertThat(settlements.map { it.get("reservation_id", Long::class.java) })
            .containsExactlyInAnyOrderElementsOf(completed.map { it.get("id", Long::class.java) })
        settlements.forEach {
            assertThat(it.get("fee_amount", Long::class.java))
                .isEqualTo((it.get("gross_amount", Long::class.java) * 10) / 100)
            assertThat(it.get("net_amount", Long::class.java))
                .isEqualTo(it.get("gross_amount", Long::class.java) - it.get("fee_amount", Long::class.java))
        }

        assertWalletLedgerConsistent(dsl)

        assertThat(jobRun("COMPLETE_RESERVATIONS").get("status", String::class.java)).isEqualTo("SUCCESS")
        assertThat(jobRun("EXPIRE_RESERVATIONS").get("status", String::class.java)).isEqualTo("SUCCESS")
        assertThat(jobRun("SETTLE_RESERVATIONS").get("status", String::class.java)).isEqualTo("SUCCESS")
    }

    private fun statusOf(requestMessage: String): ReservationStatus? =
        dsl.resultQuery(
            "select status from reservations where request_message = ?",
            requestMessage,
        ).fetchOne(0, String::class.java)?.let(ReservationStatus::valueOf)

    private fun jobRun(jobName: String) =
        dsl.resultQuery(
            "select status from job_runs where job_name = ? order by id desc limit 1",
            jobName,
        ).fetchOne()!!
}
