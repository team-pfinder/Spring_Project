package com.lookation.repository

import com.lookation.jooq.tables.ReservationEvents.RESERVATION_EVENTS
import com.lookation.domain.ActorType
import com.lookation.domain.ReservationStatus
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
    val status: ReservationStatus,
)

data class ReservationRow(
    val id: Long,
    val memberId: Long,
    val hostId: Long,
    val spaceId: Long,
    val startAt: OffsetDateTime,
    val endAt: OffsetDateTime,
    val guestCount: Int,
    val price: Long,
    val status: ReservationStatus,
    val requestMessage: String?,
    val paymentDueAt: OffsetDateTime?,
    val createdAt: OffsetDateTime,
)

data class MemberReservationRow(
    val id: Long,
    val spaceId: Long,
    val spaceName: String,
    val startAt: OffsetDateTime,
    val endAt: OffsetDateTime,
    val guestCount: Int,
    val price: Long,
    val status: ReservationStatus,
    val requestMessage: String?,
    val paymentDueAt: OffsetDateTime?,
    val createdAt: OffsetDateTime,
)

data class OccupiedIntervalRow(
    val startAt: OffsetDateTime,
    val endAt: OffsetDateTime,
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
        status: ReservationStatus,
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
            .set(RESERVATIONS.STATUS, status.name)
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
        expectedStatus: ReservationStatus,
        status: ReservationStatus,
        cancelledAt: OffsetDateTime? = null,
        approvedAt: OffsetDateTime? = null,
        paymentDueAt: OffsetDateTime? = null,
        confirmedAt: OffsetDateTime? = null,
        completedAt: OffsetDateTime? = null,
    ): Int {
        var update = dsl.update(RESERVATIONS)
            .set(RESERVATIONS.STATUS, status.name)
            .set(RESERVATIONS.UPDATED_AT, OffsetDateTime.now())
        if (cancelledAt != null) update = update.set(RESERVATIONS.CANCELLED_AT, cancelledAt)
        if (approvedAt != null) update = update.set(RESERVATIONS.APPROVED_AT, approvedAt)
        if (paymentDueAt != null) update = update.set(RESERVATIONS.PAYMENT_DUE_AT, paymentDueAt)
        if (confirmedAt != null) update = update.set(RESERVATIONS.CONFIRMED_AT, confirmedAt)
        if (completedAt != null) update = update.set(RESERVATIONS.COMPLETED_AT, completedAt)
        return update
            .where(RESERVATIONS.ID.eq(id))
            .and(RESERVATIONS.STATUS.eq(expectedStatus.name))
            .execute()
    }

    fun addEvent(
        reservationId: Long,
        fromStatus: ReservationStatus?,
        toStatus: ReservationStatus,
        actorType: ActorType,
        actorId: Long?,
        reason: String? = null,
    ) {
        dsl.insertInto(RESERVATION_EVENTS)
            .set(RESERVATION_EVENTS.RESERVATION_ID, reservationId)
            .set(RESERVATION_EVENTS.FROM_STATUS, fromStatus?.name)
            .set(RESERVATION_EVENTS.TO_STATUS, toStatus.name)
            .set(RESERVATION_EVENTS.ACTOR_TYPE, actorType.name)
            .set(RESERVATION_EVENTS.ACTOR_ID, actorId)
            .set(RESERVATION_EVENTS.REASON, reason)
            .execute()
    }

    fun findPastEnd(startedBefore: OffsetDateTime, status: ReservationStatus): List<ReservationRow> =
        dsl.selectFrom(RESERVATIONS)
            .where(RESERVATIONS.STATUS.eq(status.name))
            .and(RESERVATIONS.END_AT.le(startedBefore))
            .fetch()
            .map { it.toRow() }

    fun findPendingApprovalCreatedBefore(threshold: OffsetDateTime): List<ReservationRow> =
        dsl.selectFrom(RESERVATIONS)
            .where(RESERVATIONS.STATUS.eq(ReservationStatus.PENDING_APPROVAL.name))
            .and(RESERVATIONS.CREATED_AT.le(threshold))
            .fetch()
            .map { it.toRow() }

    fun findPaymentOverdue(now: OffsetDateTime): List<ReservationRow> =
        dsl.selectFrom(RESERVATIONS)
            .where(RESERVATIONS.STATUS.eq(ReservationStatus.PENDING_PAYMENT.name))
            .and(RESERVATIONS.PAYMENT_DUE_AT.le(now))
            .fetch()
            .map { it.toRow() }

    fun findSettleable(endBefore: OffsetDateTime): List<ReservationRow> =
        dsl.selectFrom(RESERVATIONS)
            .where(RESERVATIONS.STATUS.eq(ReservationStatus.COMPLETED.name))
            .and(RESERVATIONS.END_AT.lt(endBefore))
            .andNotExists(
                DSL.selectOne().from(SETTLEMENTS).where(SETTLEMENTS.RESERVATION_ID.eq(RESERVATIONS.ID)),
            )
            .fetch()
            .map { it.toRow() }

    fun findByHost(hostId: Long, status: ReservationStatus?, cursor: Long?, limit: Int): List<HostReservationRow> {
        var condition = RESERVATIONS.HOST_ID.eq(hostId)
        if (status != null) condition = condition.and(RESERVATIONS.STATUS.eq(status.name))
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
                    status = ReservationStatus.valueOf(it.value11()),
                )
            }
    }

    fun findByMember(memberId: Long, status: ReservationStatus? = null): List<MemberReservationRow> {
        var condition = RESERVATIONS.MEMBER_ID.eq(memberId)
        if (status != null) condition = condition.and(RESERVATIONS.STATUS.eq(status.name))
        return dsl.select(
            RESERVATIONS.ID,
            RESERVATIONS.SPACE_ID,
            SPACES.NAME,
            RESERVATIONS.START_AT,
            RESERVATIONS.END_AT,
            RESERVATIONS.GUEST_COUNT,
            RESERVATIONS.PRICE,
            RESERVATIONS.STATUS,
            RESERVATIONS.REQUEST_MESSAGE,
            RESERVATIONS.PAYMENT_DUE_AT,
            RESERVATIONS.CREATED_AT,
        )
            .from(RESERVATIONS)
            .join(SPACES).on(SPACES.ID.eq(RESERVATIONS.SPACE_ID))
            .where(condition)
            .orderBy(RESERVATIONS.START_AT.desc())
            .fetch()
            .map {
                MemberReservationRow(
                    id = it.value1(),
                    spaceId = it.value2(),
                    spaceName = it.value3(),
                    startAt = it.value4(),
                    endAt = it.value5(),
                    guestCount = it.value6().toInt(),
                    price = it.value7(),
                    status = ReservationStatus.valueOf(it.value8()),
                    requestMessage = it.value9(),
                    paymentDueAt = it.value10(),
                    createdAt = it.value11(),
                )
            }
    }

    fun findOccupied(spaceId: Long, from: OffsetDateTime, to: OffsetDateTime): List<OccupiedIntervalRow> =
        dsl.select(RESERVATIONS.START_AT, RESERVATIONS.END_AT)
            .from(RESERVATIONS)
            .where(RESERVATIONS.SPACE_ID.eq(spaceId))
            .and(RESERVATIONS.STATUS.`in`(OCCUPYING_STATUSES.map { it.name }))
            .and(
                DSL.condition(
                    "tstzrange(reservations.start_at, reservations.end_at, '[)') && tstzrange({0}, {1}, '[)')",
                    from,
                    to,
                ),
            )
            .orderBy(RESERVATIONS.START_AT)
            .fetch()
            .map { OccupiedIntervalRow(it.value1(), it.value2()) }

    private fun org.jooq.Record.toRow(): ReservationRow = ReservationRow(
        id = get(RESERVATIONS.ID),
        memberId = get(RESERVATIONS.MEMBER_ID),
        hostId = get(RESERVATIONS.HOST_ID),
        spaceId = get(RESERVATIONS.SPACE_ID),
        startAt = get(RESERVATIONS.START_AT),
        endAt = get(RESERVATIONS.END_AT),
        guestCount = get(RESERVATIONS.GUEST_COUNT).toInt(),
        price = get(RESERVATIONS.PRICE),
        status = ReservationStatus.valueOf(get(RESERVATIONS.STATUS)),
        requestMessage = get(RESERVATIONS.REQUEST_MESSAGE),
        paymentDueAt = get(RESERVATIONS.PAYMENT_DUE_AT),
        createdAt = get(RESERVATIONS.CREATED_AT),
    )

    companion object {
        /**
         * DB exclusion constraint와 같은 점유 상태 집합. 목록에 이 상태가 빠지면 화면이
         * 예약 가능하다고 표시한 구간이 409로 실패한다.
         */
        val OCCUPYING_STATUSES = listOf(
            ReservationStatus.PENDING_APPROVAL,
            ReservationStatus.PENDING_PAYMENT,
            ReservationStatus.CONFIRMED,
            ReservationStatus.COMPLETED,
        )
    }
}
