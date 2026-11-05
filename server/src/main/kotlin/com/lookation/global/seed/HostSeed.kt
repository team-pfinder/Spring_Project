package com.lookation.global.seed

import com.lookation.global.crypto.CryptoService
import com.lookation.jooq.tables.HostProfiles.HOST_PROFILES
import com.lookation.jooq.tables.Sanctions.SANCTIONS
import org.jooq.DSLContext
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

/** Seeds the two host profiles and the sanctions that exercise the periodic/permanent/revoked cases. */
@Component
@Order(HostSeed.ORDER)
class HostSeed(
    private val dsl: DSLContext,
    private val cryptoService: CryptoService,
    private val seedAdmin: SeedAdmin,
) : SeedStep {

    override val name = "호스트 프로필·제재"

    @Transactional
    override fun seed(): SeedResult {
        var inserted = 0
        var skipped = 0
        val now = OffsetDateTime.now()

        PROFILES.forEach { profile ->
            val accountId = dsl.accountId(profile.email)
            if (dsl.fetchCount(HOST_PROFILES, HOST_PROFILES.ACCOUNT_ID.eq(accountId)) > 0) {
                skipped++
                return@forEach
            }
            dsl.insertInto(HOST_PROFILES)
                .set(HOST_PROFILES.ACCOUNT_ID, accountId)
                .set(HOST_PROFILES.BUSINESS_NAME, profile.businessName)
                .set(HOST_PROFILES.BUSINESS_NUMBER, profile.businessNumber)
                .set(HOST_PROFILES.REPRESENTATIVE_NAME, profile.representativeName)
                .set(HOST_PROFILES.BANK_NAME, profile.bankName)
                .set(HOST_PROFILES.BANK_ACCOUNT_NUMBER_ENC, cryptoService.encrypt("12345678901234"))
                .set(HOST_PROFILES.BANK_ACCOUNT_HOLDER_ENC, cryptoService.encrypt(profile.representativeName))
                .set(HOST_PROFILES.STATUS, profile.status)
                .execute()
            inserted++
        }

        SANCTION_SEEDS.forEach { penalty ->
            val accountId = dsl.accountId(penalty.email)
            if (dsl.fetchCount(SANCTIONS, SANCTIONS.ACCOUNT_ID.eq(accountId).and(SANCTIONS.REASON.eq(penalty.reason))) > 0) {
                skipped++
                return@forEach
            }
            dsl.insertInto(SANCTIONS)
                .set(SANCTIONS.ACCOUNT_ID, accountId)
                .set(SANCTIONS.REASON, penalty.reason)
                .set(SANCTIONS.LEVEL, penalty.level.toShort())
                .set(SANCTIONS.STARTS_AT, now.minusDays(30))
                .set(SANCTIONS.ENDS_AT, penalty.endsAt?.let { now.plusDays(it) })
                .set(SANCTIONS.REVOKED_AT, if (penalty.revoked) now.minusDays(1) else null)
                .set(SANCTIONS.ADMIN_ID, seedAdmin.id())
                .execute()
            inserted++
        }

        return SeedResult(inserted = inserted, skipped = skipped)
    }

    private data class SeedProfile(
        val email: String,
        val businessName: String,
        val businessNumber: String,
        val representativeName: String,
        val bankName: String,
        val status: String,
    )

    private data class SeedSanction(
        val email: String,
        val reason: String,
        val level: Int,
        val endsAt: Long?,
        val revoked: Boolean,
    )

    private companion object {
        const val ORDER = 20

        val PROFILES = listOf(
            SeedProfile(
                email = "seed-host-1@lookation.local",
                businessName = "루카파티",
                businessNumber = "123-45-67890",
                representativeName = "김호스트",
                bankName = "국민은행",
                status = "PUBLISHED",
            ),
            SeedProfile(
                email = "seed-host-2@lookation.local",
                businessName = "루프탑스튜디오",
                businessNumber = "234-56-78901",
                representativeName = "이호스트",
                bankName = "신한은행",
                status = "SUSPENDED",
            ),
        )

        val SANCTION_SEEDS = listOf(
            SeedSanction("seed-member-2@lookation.local", "시드 기간제 제재", 1, 7, revoked = false),
            SeedSanction("seed-member-3@lookation.local", "시드 영구 제재", 3, null, revoked = false),
            SeedSanction("seed-host-2@lookation.local", "시드 해제된 제재", 2, 30, revoked = true),
        )
    }
}
