package com.lookation.global.seed

import com.lookation.domain.ChargeStatus
import com.lookation.domain.WithdrawalStatus
import com.lookation.global.crypto.CryptoService
import com.lookation.jooq.tables.Charges.CHARGES
import com.lookation.jooq.tables.Settlements.SETTLEMENTS
import com.lookation.jooq.tables.WalletEntries.WALLET_ENTRIES
import com.lookation.jooq.tables.Wallets.WALLETS
import com.lookation.jooq.tables.Withdrawals.WITHDRAWALS
import org.jooq.DSLContext
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

/**
 * Seeds wallets with a ledger that sums to the stored balance, charge/withdrawal requests in
 * every status, and the settlement for the seeded completed reservation.
 *
 * Ledger entries are only written for money that moved: a `REQUESTED` or `REJECTED` charge has
 * no `CHARGE` entry, and a rejected withdrawal has its hold restored.
 */
@Component
@Order(WalletSeed.ORDER)
class WalletSeed(
    private val dsl: DSLContext,
    private val cryptoService: CryptoService,
) : SeedStep {

    override val name = "지갑·원장·충전·정산·출금"

    @Transactional
    override fun seed(): SeedResult {
        val now = OffsetDateTime.now()
        var inserted = 0
        var skipped = 0

        val chargeIds = CHARGES_SEED.associate { seed ->
            val accountId = dsl.accountId(seed.email)
            val existing = dsl.select(CHARGES.ID)
                .from(CHARGES)
                .where(CHARGES.ACCOUNT_ID.eq(accountId).and(CHARGES.DEPOSITOR_NAME.eq(seed.depositorName)))
                .fetchOne(CHARGES.ID)
            if (existing != null) {
                skipped++
                seed.depositorName to existing
            } else {
                val id = dsl.insertInto(CHARGES)
                    .set(CHARGES.ACCOUNT_ID, accountId)
                    .set(CHARGES.AMOUNT, seed.amount)
                    .set(CHARGES.DEPOSITOR_NAME, seed.depositorName)
                    .set(CHARGES.STATUS, seed.status.name)
                    .set(CHARGES.ADMIN_ID, if (seed.status == ChargeStatus.REQUESTED) null else dsl.accountId("admin@lookation.local"))
                    .set(CHARGES.CREATED_AT, now.minusDays(3))
                    .set(CHARGES.PROCESSED_AT, if (seed.status == ChargeStatus.REQUESTED) null else now.minusDays(2))
                    .returningResult(CHARGES.ID)
                    .fetchOne()!!
                    .value1()
                inserted++
                seed.depositorName to id
            }
        }

        val withdrawalIds = WITHDRAWALS_SEED.associate { seed ->
            val accountId = dsl.accountId(seed.email)
            val existing = dsl.select(WITHDRAWALS.ID)
                .from(WITHDRAWALS)
                .where(WITHDRAWALS.ACCOUNT_ID.eq(accountId).and(WITHDRAWALS.AMOUNT.eq(seed.amount)))
                .fetchOne(WITHDRAWALS.ID)
            if (existing != null) {
                skipped++
                "${seed.email}:${seed.amount}" to existing
            } else {
                val id = dsl.insertInto(WITHDRAWALS)
                    .set(WITHDRAWALS.ACCOUNT_ID, accountId)
                    .set(WITHDRAWALS.AMOUNT, seed.amount)
                    .set(WITHDRAWALS.BANK_NAME, seed.bankName)
                    .set(WITHDRAWALS.ACCOUNT_NUMBER_ENC, cryptoService.encrypt("12345678901234"))
                    .set(WITHDRAWALS.ACCOUNT_HOLDER_ENC, cryptoService.encrypt("시드예금주"))
                    .set(WITHDRAWALS.STATUS, seed.status.name)
                    .set(WITHDRAWALS.ADMIN_ID, if (seed.status == WithdrawalStatus.REQUESTED) null else dsl.accountId("admin@lookation.local"))
                    .set(WITHDRAWALS.CREATED_AT, now.minusDays(3))
                    .set(WITHDRAWALS.PROCESSED_AT, if (seed.status == WithdrawalStatus.REQUESTED) null else now.minusDays(2))
                    .returningResult(WITHDRAWALS.ID)
                    .fetchOne()!!
                    .value1()
                inserted++
                "${seed.email}:${seed.amount}" to id
            }
        }

        WALLETS_SEED.forEach { seed ->
            val accountId = dsl.accountId(seed.email)
            if (dsl.fetchCount(WALLETS, WALLETS.ACCOUNT_ID.eq(accountId)) > 0) {
                skipped++
                return@forEach
            }
            dsl.insertInto(WALLETS)
                .set(WALLETS.ACCOUNT_ID, accountId)
                .set(WALLETS.BALANCE, seed.entries.sumOf { it.amount })
                .execute()
            seed.entries.forEach { entry ->
                val refId = when (entry.refType) {
                    "CHARGE" -> chargeIds[entry.refKey]
                    "WITHDRAWAL" -> withdrawalIds[entry.refKey]
                    "RESERVATION" -> dsl.reservationId(entry.refKey!!)
                    null -> null
                    else -> error("시드 원장 참조 유형이 잘못됐습니다: ${entry.refType}")
                }
                dsl.insertInto(WALLET_ENTRIES)
                    .set(WALLET_ENTRIES.ACCOUNT_ID, accountId)
                    .set(WALLET_ENTRIES.ENTRY_TYPE, entry.type)
                    .set(WALLET_ENTRIES.AMOUNT, entry.amount)
                    .set(WALLET_ENTRIES.REF_TYPE, entry.refType)
                    .set(WALLET_ENTRIES.REF_ID, refId)
                    .execute()
            }
            inserted++
        }

        val completedId = dsl.reservationId("seed:reservation:completed")!!
        if (dsl.fetchCount(SETTLEMENTS, SETTLEMENTS.RESERVATION_ID.eq(completedId)) == 0) {
            dsl.insertInto(SETTLEMENTS)
                .set(SETTLEMENTS.RESERVATION_ID, completedId)
                .set(SETTLEMENTS.HOST_ID, dsl.accountId("seed-host-1@lookation.local"))
                .set(SETTLEMENTS.GROSS_AMOUNT, 40000)
                .set(SETTLEMENTS.FEE_AMOUNT, 4000)
                .set(SETTLEMENTS.NET_AMOUNT, 36000)
                .set(SETTLEMENTS.SETTLED_AT, now.minusDays(9))
                .execute()
            inserted++
        } else {
            skipped++
        }

        return SeedResult(inserted = inserted, skipped = skipped)
    }

    private data class SeedCharge(
        val email: String,
        val amount: Long,
        val depositorName: String,
        val status: ChargeStatus,
    )

    private data class SeedWithdrawal(
        val email: String,
        val amount: Long,
        val bankName: String,
        val status: WithdrawalStatus,
    )

    private data class SeedEntry(
        val type: String,
        val amount: Long,
        val refType: String?,
        val refKey: String? = null,
    )

    private data class SeedWallet(
        val email: String,
        val entries: List<SeedEntry>,
    )

    private companion object {
        const val ORDER = 50

        val CHARGES_SEED = listOf(
            SeedCharge("seed-member-1@lookation.local", 200000, "시드충전회원1", ChargeStatus.APPROVED),
            SeedCharge("seed-member-2@lookation.local", 10000, "시드충전회원2", ChargeStatus.REQUESTED),
            SeedCharge("seed-host-2@lookation.local", 50000, "시드충전호스트2", ChargeStatus.APPROVED),
            SeedCharge("seed-member-3@lookation.local", 5000, "시드충전회원3", ChargeStatus.REJECTED),
        )

        val WITHDRAWALS_SEED = listOf(
            SeedWithdrawal("seed-host-1@lookation.local", 20000, "국민은행", WithdrawalStatus.PAID),
            SeedWithdrawal("seed-host-1@lookation.local", 5000, "국민은행", WithdrawalStatus.REJECTED),
            SeedWithdrawal("seed-host-2@lookation.local", 30000, "신한은행", WithdrawalStatus.REQUESTED),
            SeedWithdrawal("seed-host-2@lookation.local", 10000, "신한은행", WithdrawalStatus.APPROVED),
        )

        val WALLETS_SEED = listOf(
            SeedWallet(
                email = "seed-member-1@lookation.local",
                entries = listOf(
                    SeedEntry("CHARGE", 200000, "CHARGE", "시드충전회원1"),
                    SeedEntry("HOLD", -50000, "RESERVATION", "seed:reservation:confirmed"),
                    SeedEntry("HOLD", -40000, "RESERVATION", "seed:reservation:completed"),
                    SeedEntry("HOLD", -30000, "RESERVATION", "seed:reservation:cancelled"),
                    SeedEntry("REFUND", 30000, "RESERVATION", "seed:reservation:cancelled"),
                    SeedEntry("HOLD", -20000, "RESERVATION", "seed:reservation:refunded"),
                    SeedEntry("REFUND", 20000, "RESERVATION", "seed:reservation:refunded"),
                    SeedEntry("HOLD", -30005, "RESERVATION", "seed:batch:complete"),
                ),
            ),
            SeedWallet(
                email = "seed-host-1@lookation.local",
                entries = listOf(
                    SeedEntry("SETTLE", 36000, "RESERVATION", "seed:reservation:completed"),
                    SeedEntry("WITHDRAWAL", -20000, "WITHDRAWAL", "seed-host-1@lookation.local:20000"),
                    SeedEntry("WITHDRAWAL", -5000, "WITHDRAWAL", "seed-host-1@lookation.local:5000"),
                    SeedEntry("WITHDRAWAL", 5000, "WITHDRAWAL", "seed-host-1@lookation.local:5000"),
                ),
            ),
            SeedWallet(
                email = "seed-host-2@lookation.local",
                entries = listOf(
                    SeedEntry("CHARGE", 50000, "CHARGE", "시드충전호스트2"),
                    SeedEntry("WITHDRAWAL", -30000, "WITHDRAWAL", "seed-host-2@lookation.local:30000"),
                    SeedEntry("WITHDRAWAL", -10000, "WITHDRAWAL", "seed-host-2@lookation.local:10000"),
                ),
            ),
        )
    }
}
