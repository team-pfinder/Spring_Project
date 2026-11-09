package com.lookation.global.seed

import com.lookation.global.security.AdminSeeder
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

/**
 * Runs every [SeedStep] on local startup when `lookation.seed.enabled=true`.
 *
 * Only registered in the `local` profile; `test` and deployment profiles never seed.
 * Ordered after [AdminSeeder] so the configured admin account exists before the account
 * step checks its fixed email.
 */
@Component
@Profile("local")
@ConditionalOnProperty(prefix = "lookation.seed", name = ["enabled"], havingValue = "true")
@Order(InitializeService.ORDER)
class InitializeService(private val steps: List<SeedStep>) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        seed()
    }

    /** Runs all steps and returns their combined counts. Safe to call repeatedly. */
    fun seed(): SeedResult {
        val total = steps.fold(SeedResult(inserted = 0, skipped = 0)) { total, step ->
            val result = step.seed()
            log.info("시드 '{}': 삽입 {}건, 스킵 {}건", step.name, result.inserted, result.skipped)
            total + result
        }
        log.info("시드 완료: 삽입 {}건, 스킵 {}건", total.inserted, total.skipped)
        return total
    }

    companion object {
        const val ORDER = AdminSeeder.ORDER + 1

        private val log = LoggerFactory.getLogger(InitializeService::class.java)
    }
}
