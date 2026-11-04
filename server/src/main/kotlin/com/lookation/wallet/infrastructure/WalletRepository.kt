package com.lookation.wallet.infrastructure

import com.lookation.jooq.tables.WalletEntries.WALLET_ENTRIES
import com.lookation.jooq.tables.Wallets.WALLETS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class WalletRepository(private val dsl: DSLContext) {

    fun ensureWallet(accountId: Long) {
        dsl.insertInto(WALLETS)
            .set(WALLETS.ACCOUNT_ID, accountId)
            .set(WALLETS.BALANCE, 0L)
            .onConflictDoNothing()
            .execute()
    }

    fun hold(accountId: Long, amount: Long): Boolean =
        dsl.update(WALLETS)
            .set(WALLETS.BALANCE, WALLETS.BALANCE.minus(amount))
            .set(WALLETS.UPDATED_AT, OffsetDateTime.now())
            .where(WALLETS.ACCOUNT_ID.eq(accountId))
            .and(WALLETS.BALANCE.ge(amount))
            .execute() == 1

    fun deposit(accountId: Long, amount: Long) {
        dsl.update(WALLETS)
            .set(WALLETS.BALANCE, WALLETS.BALANCE.plus(amount))
            .set(WALLETS.UPDATED_AT, OffsetDateTime.now())
            .where(WALLETS.ACCOUNT_ID.eq(accountId))
            .execute()
    }

    fun addEntry(accountId: Long, type: String, amount: Long, refType: String?, refId: Long?) {
        dsl.insertInto(WALLET_ENTRIES)
            .set(WALLET_ENTRIES.ACCOUNT_ID, accountId)
            .set(WALLET_ENTRIES.ENTRY_TYPE, type)
            .set(WALLET_ENTRIES.AMOUNT, amount)
            .set(WALLET_ENTRIES.REF_TYPE, refType)
            .set(WALLET_ENTRIES.REF_ID, refId)
            .execute()
    }

    fun balance(accountId: Long): Long =
        dsl.select(WALLETS.BALANCE)
            .from(WALLETS)
            .where(WALLETS.ACCOUNT_ID.eq(accountId))
            .fetchOne(WALLETS.BALANCE) ?: 0L
}
