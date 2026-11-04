package com.lookation.reservation.infrastructure

import com.lookation.jooq.tables.ReservationEvents.RESERVATION_EVENTS
import com.lookation.jooq.tables.Accounts.ACCOUNTS
import com.lookation.jooq.tables.Reservations.RESERVATIONS
import com.lookation.jooq.tables.Spaces.SPACES
import com.lookation.jooq.tables.Settlements.SETTLEMENTS
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

data class HostReservationRow(
    val id: Long,
    val spaceId: Long,
    val spaceName: String,
    val memberNickname: String,
    val memberName: String?,
    val memberPhone: String?,
    val startAt: OffsetDateTime,
    val endAt: OffsetDateTime,
    val guestCount: Int,
    val price: Long,
    val status: String,
)

data class ReservationRow(
    val id: Long,
    val memberId: Long,
    val hostId: Long,
    val spaceId: Long,
    val startAt: OffsetDateTime,
    val endAt: OffsetDateTime,
    val price: Long,
    val status: String,
    val paymentDueAt: OffsetDateTime?,
)

@Repository
class ReservationRepository(private val dsl: DSLContext) {

    fun insert(
        memberId: Long,
        hostId: Long,
        spaceId: Long,
        startAt: OffsetDateTime,
        endAt: OffsetDateTime,
        guestCount: Int,
        price: Long,
        status: String,
        requestMessage: String?,
    ): Long =
        dsl.insertInto(RESERVATIONS)
            .set(RESERVATIONS.MEMBER_ID, memberId)
            .set(RESERVATIONS.HOST_ID, hostId)
            .set(RESERVATIONS.SPACE_ID, spaceId)
            .set(RESERVATIONS.START_AT, startAt)
            .set(RESERVATIONS.END_AT, endAt)
            .set(RESERVATIONS.GUEST_COUNT, guestCount.toShort())
            .set(RESERVATIONS.PRICE, price)
            .set(RESERVATIONS.STATUS, status)
            .set(RESERVATIONS.REQUEST_MESSAGE, requestMessage)
            .returningResult(RESERVATIONS.ID)
            .fetchOne()!!
            .value1()

    fun findById(id: Long): ReservationRow? =
        dsl.selectFrom(RESERVATIONS)
            .where(RESERVATIONS.ID.eq(id))
            .fetchOne()
            ?.toRow()

    fun updateStatus(
        id: Long,
        expectedStatus: String,
        status: String,
        cancelledAt: OffsetDateTime? = null,
        approvedAt: OffsetDateTime? = null,
        paymentDueAt: OffsetDateTime? = null,
        confirmedAt: OffsetDateTime? = null,
        completedAt: OffsetDateTime? = null,
    ): Int {
        var update = dsl.update(RESERVATIONS)
            .set(RESERVATIONS.STATUS, status)
            .set(RESERVATIONS.UPDATED_AT, OffsetDateTime.now())
        if (cancelledAt != null) update = update.set(RESERVATIONS.CANCELLED_AT, cancelledAt)
        if (approvedAt != null) update = update.set(RESERVATIONS.APPROVED_AT, approvedAt)
        if (paymentDueAt != null) update = update.set(RESERVATIONS.PAYMENT_DUE_AT, paymentDueAt)
        if (confirmedAt != null) update = update.set(RESERVATIONS.CONFIRMED_AT, confirmedAt)
        if (completedAt != null) update = update.set(RESERVATIONS.COMPLETED_AT, completedAt)
        return update
            .where(RESERVATIONS.ID.eq(id))
            .and(RESERVATIONS.STATUS.eq(expectedStatus))
            .execute()
    }

    fun addEvent(
        reservationId: Long,
        fromStatus: String?,
        toStatus: String,
        actorType: String,
        actorId: Long?,
        reason: String? = null,
    ) {
        dsl.insertInto(RESERVATION_EVENTS)
            .set(RESERVATION_EVENTS.RESERVATION_ID, reservationId)
            .set(RESERVATION_EVENTS.FROM_STATUS, fromStatus)
            .set(RESERVATION_EVENTS.TO_STATUS, toStatus)
            .set(RESERVATION_EVENTS.ACTOR_TYPE, actorType)
            .set(RESERVATION_EVENTS.ACTOR_ID, actorId)
            .set(RESERVATION_EVENTS.REASON, reason)
            .execute()
    }

    fun findPastEnd(startedBefore: OffsetDateTime, status: String): List<ReservationRow> =
        dsl.selectFrom(RESERVATIONS)
            .where(RESERVATIONS.STATUS.eq(status))
            .and(RESERVATIONS.END_AT.le(startedBefore))
            .fetch()
            .map { it.toRow() }

    fun findPendingApprovalCreatedBefore(threshold: OffsetDateTime): List<ReservationRow> =
        dsl.selectFrom(RESERVATIONS)
            .where(RESERVATIONS.STATUS.eq("PENDING_APPROVAL"))
            .and(RESERVATIONS.CREATED_AT.le(threshold))
            .fetch()
            .map { it.toRow() }

    fun findPaymentOverdue(now: OffsetDateTime): List<ReservationRow> =
        dsl.selectFrom(RESERVATIONS)
            .where(RESERVATIONS.STATUS.eq("PENDING_PAYMENT"))
            .and(RESERVATIONS.PAYMENT_DUE_AT.le(now))
            .fetch()
            .map { it.toRow() }

    fun findSettleable(endBefore: OffsetDateTime): List<ReservationRow> =
        dsl.selectFrom(RESERVATIONS)
            .where(RESERVATIONS.STATUS.eq("COMPLETED"))
            .and(RESERVATIONS.END_AT.lt(endBefore))
            .andNotExists(
                DSL.selectOne().from(SETTLEMENTS).where(SETTLEMENTS.RESERVATION_ID.eq(RESERVATIONS.ID)),
            )
            .fetch()
            .map { it.toRow() }

    fun findByHost(hostId: Long, status: String?, cursor: Long?, limit: Int): List<HostReservationRow> {
        var condition = RESERVATIONS.HOST_ID.eq(hostId)
        if (status != null) condition = condition.and(RESERVATIONS.STATUS.eq(status))
        if (cursor != null) condition = condition.and(RESERVATIONS.ID.lt(cursor))
        return dsl.select(
            RESERVATIONS.ID,
            RESERVATIONS.SPACE_ID,
            SPACES.NAME,
            ACCOUNTS.NICKNAME,
            ACCOUNTS.NAME,
            ACCOUNTS.PHONE,
            RESERVATIONS.START_AT,
            RESERVATIONS.END_AT,
            RESERVATIONS.GUEST_COUNT,
            RESERVATIONS.PRICE,
            RESERVATIONS.STATUS,
        )
            .from(RESERVATIONS)
            .join(SPACES).on(SPACES.ID.eq(RESERVATIONS.SPACE_ID))
            .join(ACCOUNTS).on(ACCOUNTS.ID.eq(RESERVATIONS.MEMBER_ID))
            .where(condition)
            .orderBy(RESERVATIONS.ID.desc())
            .limit(limit)
            .fetch()
            .map {
                HostReservationRow(
                    id = it.value1(),
                    spaceId = it.value2(),
                    spaceName = it.value3(),
                    memberNickname = it.value4(),
                    memberName = it.value5(),
                    memberPhone = it.value6(),
                    startAt = it.value7(),
                    endAt = it.value8(),
                    guestCount = it.value9().toInt(),
                    price = it.value10(),
                    status = it.value11(),
                )
            }
    }

    fun findByMember(memberId: Long): List<ReservationRow> =
        dsl.selectFrom(RESERVATIONS)
            .where(RESERVATIONS.MEMBER_ID.eq(memberId))
            .orderBy(RESERVATIONS.START_AT.desc())
            .fetch()
            .map { it.toRow() }

    private fun org.jooq.Record.toRow(): ReservationRow = ReservationRow(
        id = get(RESERVATIONS.ID),
        memberId = get(RESERVATIONS.MEMBER_ID),
        hostId = get(RESERVATIONS.HOST_ID),
        spaceId = get(RESERVATIONS.SPACE_ID),
        startAt = get(RESERVATIONS.START_AT),
        endAt = get(RESERVATIONS.END_AT),
        price = get(RESERVATIONS.PRICE),
        status = get(RESERVATIONS.STATUS),
        paymentDueAt = get(RESERVATIONS.PAYMENT_DUE_AT),
    )
}
