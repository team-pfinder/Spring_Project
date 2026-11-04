package com.lookation.repository

import com.lookation.domain.ChargeStatus
import com.lookation.jooq.tables.Charges.CHARGES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

data class ChargeRow(
    val id: Long,
    val accountId: Long,
    val amount: Long,
    val depositorName: String,
    val status: ChargeStatus,
)

@Repository
class ChargeRepository(private val dsl: DSLContext) {

    fun insert(accountId: Long, amount: Long, depositorName: String): Long =
        dsl.insertInto(CHARGES)
            .set(CHARGES.ACCOUNT_ID, accountId)
            .set(CHARGES.AMOUNT, amount)
            .set(CHARGES.DEPOSITOR_NAME, depositorName)
            .returningResult(CHARGES.ID)
            .fetchOne()!!
            .value1()

    fun findById(id: Long): ChargeRow? =
        dsl.selectFrom(CHARGES).where(CHARGES.ID.eq(id)).fetchOne()?.let {
            ChargeRow(
                id = it.get(CHARGES.ID),
                accountId = it.get(CHARGES.ACCOUNT_ID),
                amount = it.get(CHARGES.AMOUNT),
                depositorName = it.get(CHARGES.DEPOSITOR_NAME),
                status = ChargeStatus.valueOf(it.get(CHARGES.STATUS)),
            )
        }

    fun findByAccount(accountId: Long): List<ChargeRow> =
        dsl.selectFrom(CHARGES)
            .where(CHARGES.ACCOUNT_ID.eq(accountId))
            .orderBy(CHARGES.ID.desc())
            .fetch()
            .map {
                ChargeRow(
                    id = it.get(CHARGES.ID),
                    accountId = it.get(CHARGES.ACCOUNT_ID),
                    amount = it.get(CHARGES.AMOUNT),
                    depositorName = it.get(CHARGES.DEPOSITOR_NAME),
                    status = ChargeStatus.valueOf(it.get(CHARGES.STATUS)),
                )
            }

    fun updateStatus(id: Long, expected: ChargeStatus, status: ChargeStatus, adminId: Long): Int =
        dsl.update(CHARGES)
            .set(CHARGES.STATUS, status.name)
            .set(CHARGES.ADMIN_ID, adminId)
            .set(CHARGES.PROCESSED_AT, java.time.OffsetDateTime.now())
            .where(CHARGES.ID.eq(id))
            .and(CHARGES.STATUS.eq(expected.name))
            .execute()
}
