package com.lookation.repository

import com.lookation.domain.WithdrawalStatus
import com.lookation.jooq.tables.Withdrawals.WITHDRAWALS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

data class WithdrawalRow(
    val id: Long,
    val accountId: Long,
    val amount: Long,
    val status: WithdrawalStatus,
)

@Repository
class WithdrawalRepository(private val dsl: DSLContext) {

    fun insert(
        accountId: Long,
        amount: Long,
        bankName: String,
        accountNumberEnc: ByteArray,
        accountHolderEnc: ByteArray,
    ): Long =
        dsl.insertInto(WITHDRAWALS)
            .set(WITHDRAWALS.ACCOUNT_ID, accountId)
            .set(WITHDRAWALS.AMOUNT, amount)
            .set(WITHDRAWALS.BANK_NAME, bankName)
            .set(WITHDRAWALS.ACCOUNT_NUMBER_ENC, accountNumberEnc)
            .set(WITHDRAWALS.ACCOUNT_HOLDER_ENC, accountHolderEnc)
            .returningResult(WITHDRAWALS.ID)
            .fetchOne()!!
            .value1()

    fun findById(id: Long): WithdrawalRow? =
        dsl.selectFrom(WITHDRAWALS).where(WITHDRAWALS.ID.eq(id)).fetchOne()?.let {
            WithdrawalRow(
                id = it.get(WITHDRAWALS.ID),
                accountId = it.get(WITHDRAWALS.ACCOUNT_ID),
                amount = it.get(WITHDRAWALS.AMOUNT),
                status = WithdrawalStatus.valueOf(it.get(WITHDRAWALS.STATUS)),
            )
        }

    fun findByAccount(accountId: Long): List<WithdrawalRow> =
        dsl.selectFrom(WITHDRAWALS)
            .where(WITHDRAWALS.ACCOUNT_ID.eq(accountId))
            .orderBy(WITHDRAWALS.ID.desc())
            .fetch()
            .map {
                WithdrawalRow(
                    id = it.get(WITHDRAWALS.ID),
                    accountId = it.get(WITHDRAWALS.ACCOUNT_ID),
                    amount = it.get(WITHDRAWALS.AMOUNT),
                    status = WithdrawalStatus.valueOf(it.get(WITHDRAWALS.STATUS)),
                )
            }

    fun updateStatus(id: Long, expected: WithdrawalStatus, status: WithdrawalStatus, adminId: Long): Int =
        dsl.update(WITHDRAWALS)
            .set(WITHDRAWALS.STATUS, status.name)
            .set(WITHDRAWALS.ADMIN_ID, adminId)
            .set(WITHDRAWALS.PROCESSED_AT, OffsetDateTime.now())
            .where(WITHDRAWALS.ID.eq(id))
            .and(WITHDRAWALS.STATUS.eq(expected.name))
            .execute()
}
