package com.lookation.repository

import com.lookation.domain.Account
import com.lookation.domain.AccountStatus
import com.lookation.jooq.tables.AccountRoles.ACCOUNT_ROLES
import com.lookation.jooq.tables.Accounts.ACCOUNTS
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class AccountRepository(private val dsl: DSLContext) {

    fun existsByEmail(email: String): Boolean =
        dsl.fetchCount(ACCOUNTS, ACCOUNTS.EMAIL.equalIgnoreCase(email)) > 0

    fun insert(email: String, passwordHash: String, name: String, nickname: String): Long =
        dsl.insertInto(ACCOUNTS)
            .set(ACCOUNTS.EMAIL, email)
            .set(ACCOUNTS.PASSWORD_HASH, passwordHash)
            .set(ACCOUNTS.NAME, name)
            .set(ACCOUNTS.NICKNAME, nickname)
            .returningResult(ACCOUNTS.ID)
            .fetchOne()!!
            .value1()

    fun addRole(accountId: Long, role: String) {
        dsl.insertInto(ACCOUNT_ROLES)
            .set(ACCOUNT_ROLES.ACCOUNT_ID, accountId)
            .set(ACCOUNT_ROLES.ROLE, role)
            .execute()
    }

    fun findByEmail(email: String): Account? =
        dsl.selectFrom(ACCOUNTS)
            .where(ACCOUNTS.EMAIL.equalIgnoreCase(email))
            .fetchOne()
            ?.toAccount()

    fun findById(id: Long): Account? =
        dsl.selectFrom(ACCOUNTS)
            .where(ACCOUNTS.ID.eq(id))
            .fetchOne()
            ?.toAccount()

    fun updatePassword(accountId: Long, passwordHash: String) {
        dsl.update(ACCOUNTS)
            .set(ACCOUNTS.PASSWORD_HASH, passwordHash)
            .set(ACCOUNTS.UPDATED_AT, OffsetDateTime.now())
            .where(ACCOUNTS.ID.eq(accountId))
            .execute()
    }

    fun roles(accountId: Long): List<String> =
        dsl.select(ACCOUNT_ROLES.ROLE)
            .from(ACCOUNT_ROLES)
            .where(ACCOUNT_ROLES.ACCOUNT_ID.eq(accountId))
            .fetch(ACCOUNT_ROLES.ROLE)

    private fun Record.toAccount(): Account = Account(
        id = get(ACCOUNTS.ID),
        email = get(ACCOUNTS.EMAIL),
        passwordHash = get(ACCOUNTS.PASSWORD_HASH),
        name = get(ACCOUNTS.NAME),
        nickname = get(ACCOUNTS.NICKNAME),
        status = AccountStatus.valueOf(get(ACCOUNTS.STATUS)),
    )
}
