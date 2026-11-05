package com.lookation.global.seed

import com.lookation.jooq.tables.Accounts.ACCOUNTS
import com.lookation.jooq.tables.Reservations.RESERVATIONS
import com.lookation.jooq.tables.Spaces.SPACES
import org.jooq.DSLContext

internal fun DSLContext.accountId(email: String): Long =
    select(ACCOUNTS.ID)
        .from(ACCOUNTS)
        .where(ACCOUNTS.EMAIL.equalIgnoreCase(email))
        .fetchOne(ACCOUNTS.ID)
        ?: error("시드 계정이 없습니다: $email")

internal fun DSLContext.spaceId(name: String): Long =
    select(SPACES.ID)
        .from(SPACES)
        .where(SPACES.NAME.eq(name))
        .fetchOne(SPACES.ID)
        ?: error("시드 공간이 없습니다: $name")

internal fun DSLContext.spaceHostId(name: String): Long =
    select(SPACES.HOST_ID)
        .from(SPACES)
        .where(SPACES.NAME.eq(name))
        .fetchOne(SPACES.HOST_ID)
        ?: error("시드 공간이 없습니다: $name")

internal fun DSLContext.reservationId(requestMessage: String): Long? =
    select(RESERVATIONS.ID)
        .from(RESERVATIONS)
        .where(RESERVATIONS.REQUEST_MESSAGE.eq(requestMessage))
        .fetchOne(RESERVATIONS.ID)
