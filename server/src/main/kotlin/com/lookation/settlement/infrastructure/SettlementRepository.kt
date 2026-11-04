package com.lookation.settlement.infrastructure

import com.lookation.jooq.tables.Settlements.SETTLEMENTS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class SettlementRepository(private val dsl: DSLContext) {

    fun existsByReservation(reservationId: Long): Boolean =
        dsl.fetchCount(SETTLEMENTS, SETTLEMENTS.RESERVATION_ID.eq(reservationId)) > 0

    fun insert(reservationId: Long, hostId: Long, gross: Long, fee: Long, net: Long) {
        dsl.insertInto(SETTLEMENTS)
            .set(SETTLEMENTS.RESERVATION_ID, reservationId)
            .set(SETTLEMENTS.HOST_ID, hostId)
            .set(SETTLEMENTS.GROSS_AMOUNT, gross)
            .set(SETTLEMENTS.FEE_AMOUNT, fee)
            .set(SETTLEMENTS.NET_AMOUNT, net)
            .execute()
    }
}
