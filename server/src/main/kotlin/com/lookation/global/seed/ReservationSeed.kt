package com.lookation.global.seed

import com.lookation.domain.ReservationStatus
import com.lookation.jooq.tables.ReservationEvents.RESERVATION_EVENTS
import com.lookation.jooq.tables.Reservations.RESERVATIONS
import org.jooq.DSLContext
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

/**
 * Seeds ten reservations: one per [ReservationStatus], plus a stale pending-approval row and a
 * past confirmed row so the expire and complete/settle batches have work to do.
 *
 * The fixed `request_message` marker (`seed:...`) is the idempotency key.
 */
@Component
@Order(ReservationSeed.ORDER)
class ReservationSeed(private val dsl: DSLContext) : SeedStep {

    override val name = "예약·상태 이력"

    @Transactional
    override fun seed(): SeedResult {
        val now = OffsetDateTime.now().truncatedTo(ChronoUnit.HOURS)
        var inserted = 0
        var skipped = 0

        SEED_RESERVATIONS.forEach { seed ->
            if (dsl.reservationId(seed.marker) != null) {
                skipped++
                return@forEach
            }
            val memberId = dsl.accountId(seed.memberEmail)
            val spaceHostId = dsl.spaceHostId(seed.spaceName)
            val reservationId = dsl.insertInto(RESERVATIONS)
                .set(RESERVATIONS.MEMBER_ID, memberId)
                .set(RESERVATIONS.HOST_ID, spaceHostId)
                .set(RESERVATIONS.SPACE_ID, dsl.spaceId(seed.spaceName))
                .set(RESERVATIONS.START_AT, now.plus(seed.startOffsetHours, ChronoUnit.HOURS))
                .set(RESERVATIONS.END_AT, now.plus(seed.endOffsetHours, ChronoUnit.HOURS))
                .set(RESERVATIONS.GUEST_COUNT, seed.guestCount.toShort())
                .set(RESERVATIONS.PRICE, seed.price)
                .set(RESERVATIONS.STATUS, seed.status.name)
                .set(RESERVATIONS.REQUEST_MESSAGE, seed.marker)
                .set(RESERVATIONS.CREATED_AT, now.minus(seed.createdBeforeHours, ChronoUnit.HOURS))
                .set(RESERVATIONS.APPROVED_AT, seed.approvedBeforeHours?.let { now.minus(it, ChronoUnit.HOURS) })
                .set(RESERVATIONS.PAYMENT_DUE_AT, seed.paymentDueOffsetHours?.let { now.plus(it, ChronoUnit.HOURS) })
                .set(RESERVATIONS.CONFIRMED_AT, seed.confirmedBeforeHours?.let { now.minus(it, ChronoUnit.HOURS) })
                .set(RESERVATIONS.COMPLETED_AT, seed.completedBeforeHours?.let { now.minus(it, ChronoUnit.HOURS) })
                .set(RESERVATIONS.CANCELLED_AT, seed.cancelledBeforeHours?.let { now.minus(it, ChronoUnit.HOURS) })
                .returningResult(RESERVATIONS.ID)
                .fetchOne()!!
                .value1()

            seed.events.forEach { event ->
                dsl.insertInto(RESERVATION_EVENTS)
                    .set(RESERVATION_EVENTS.RESERVATION_ID, reservationId)
                    .set(RESERVATION_EVENTS.FROM_STATUS, event.from?.name)
                    .set(RESERVATION_EVENTS.TO_STATUS, event.to.name)
                    .set(RESERVATION_EVENTS.ACTOR_TYPE, event.actorType)
                    .set(
                        RESERVATION_EVENTS.ACTOR_ID,
                        when (event.actorType) {
                            "MEMBER" -> memberId
                            "HOST" -> spaceHostId
                            "ADMIN" -> dsl.accountId("admin@lookation.local")
                            else -> null
                        },
                    )
                    .set(RESERVATION_EVENTS.REASON, event.reason)
                    .execute()
            }
            inserted++
        }

        return SeedResult(inserted = inserted, skipped = skipped)
    }

    private data class SeedEvent(
        val from: ReservationStatus?,
        val to: ReservationStatus,
        val actorType: String,
        val reason: String? = null,
    )

    private data class SeedReservation(
        val marker: String,
        val memberEmail: String,
        val spaceName: String,
        val startOffsetHours: Long,
        val endOffsetHours: Long,
        val guestCount: Int,
        val price: Long,
        val status: ReservationStatus,
        val createdBeforeHours: Long,
        val approvedBeforeHours: Long? = null,
        val paymentDueOffsetHours: Long? = null,
        val confirmedBeforeHours: Long? = null,
        val completedBeforeHours: Long? = null,
        val cancelledBeforeHours: Long? = null,
        val events: List<SeedEvent>,
    )

    private companion object {
        const val ORDER = 40
        const val DAY_HOURS = 24L

        val SEED_RESERVATIONS = listOf(
            SeedReservation(
                marker = "seed:reservation:confirmed",
                memberEmail = "seed-member-1@lookation.local",
                spaceName = "시드 파티룸",
                startOffsetHours = 5 * DAY_HOURS,
                endOffsetHours = 5 * DAY_HOURS + 2,
                guestCount = 12,
                price = 50000,
                status = ReservationStatus.CONFIRMED,
                createdBeforeHours = 6 * DAY_HOURS,
                confirmedBeforeHours = DAY_HOURS,
                events = listOf(
                    SeedEvent(null, ReservationStatus.CONFIRMED, "MEMBER"),
                ),
            ),
            SeedReservation(
                marker = "seed:reservation:completed",
                memberEmail = "seed-member-1@lookation.local",
                spaceName = "시드 파티룸",
                startOffsetHours = -10 * DAY_HOURS,
                endOffsetHours = -9 * DAY_HOURS,
                guestCount = 10,
                price = 40000,
                status = ReservationStatus.COMPLETED,
                createdBeforeHours = 12 * DAY_HOURS,
                confirmedBeforeHours = 11 * DAY_HOURS,
                completedBeforeHours = 9 * DAY_HOURS,
                events = listOf(
                    SeedEvent(null, ReservationStatus.CONFIRMED, "MEMBER"),
                    SeedEvent(ReservationStatus.CONFIRMED, ReservationStatus.COMPLETED, "SYSTEM"),
                ),
            ),
            SeedReservation(
                marker = "seed:reservation:pending-approval",
                memberEmail = "seed-member-1@lookation.local",
                spaceName = "시드 루프탑",
                startOffsetHours = 3 * DAY_HOURS,
                endOffsetHours = 3 * DAY_HOURS + 2,
                guestCount = 8,
                price = 60000,
                status = ReservationStatus.PENDING_APPROVAL,
                createdBeforeHours = 1,
                events = listOf(
                    SeedEvent(null, ReservationStatus.PENDING_APPROVAL, "MEMBER"),
                ),
            ),
            SeedReservation(
                marker = "seed:reservation:pending-payment",
                memberEmail = "seed-member-1@lookation.local",
                spaceName = "시드 루프탑",
                startOffsetHours = 4 * DAY_HOURS,
                endOffsetHours = 4 * DAY_HOURS + 2,
                guestCount = 6,
                price = 60000,
                status = ReservationStatus.PENDING_PAYMENT,
                createdBeforeHours = 3,
                approvedBeforeHours = 2,
                paymentDueOffsetHours = 22,
                events = listOf(
                    SeedEvent(null, ReservationStatus.PENDING_APPROVAL, "MEMBER"),
                    SeedEvent(ReservationStatus.PENDING_APPROVAL, ReservationStatus.PENDING_PAYMENT, "HOST"),
                ),
            ),
            SeedReservation(
                marker = "seed:reservation:cancelled",
                memberEmail = "seed-member-1@lookation.local",
                spaceName = "시드 워크샵",
                startOffsetHours = 6 * DAY_HOURS,
                endOffsetHours = 6 * DAY_HOURS + 2,
                guestCount = 6,
                price = 30000,
                status = ReservationStatus.CANCELLED,
                createdBeforeHours = 8 * DAY_HOURS,
                confirmedBeforeHours = 7 * DAY_HOURS,
                cancelledBeforeHours = DAY_HOURS,
                events = listOf(
                    SeedEvent(null, ReservationStatus.CONFIRMED, "MEMBER"),
                    SeedEvent(ReservationStatus.CONFIRMED, ReservationStatus.CANCELLED, "HOST"),
                ),
            ),
            SeedReservation(
                marker = "seed:reservation:refunded",
                memberEmail = "seed-member-1@lookation.local",
                spaceName = "시드 워크샵",
                startOffsetHours = -20 * DAY_HOURS,
                endOffsetHours = -19 * DAY_HOURS,
                guestCount = 4,
                price = 20000,
                status = ReservationStatus.REFUNDED,
                createdBeforeHours = 24 * DAY_HOURS,
                confirmedBeforeHours = 23 * DAY_HOURS,
                cancelledBeforeHours = 21 * DAY_HOURS,
                events = listOf(
                    SeedEvent(null, ReservationStatus.CONFIRMED, "MEMBER"),
                    SeedEvent(ReservationStatus.CONFIRMED, ReservationStatus.REFUNDED, "ADMIN", "시드 환불 처리"),
                ),
            ),
            SeedReservation(
                marker = "seed:reservation:rejected",
                memberEmail = "seed-member-2@lookation.local",
                spaceName = "시드 루프탑",
                startOffsetHours = 7 * DAY_HOURS,
                endOffsetHours = 7 * DAY_HOURS + 2,
                guestCount = 5,
                price = 60000,
                status = ReservationStatus.REJECTED,
                createdBeforeHours = 3 * DAY_HOURS,
                events = listOf(
                    SeedEvent(null, ReservationStatus.PENDING_APPROVAL, "MEMBER"),
                    SeedEvent(ReservationStatus.PENDING_APPROVAL, ReservationStatus.REJECTED, "HOST", "시드 거절 사유"),
                ),
            ),
            SeedReservation(
                marker = "seed:reservation:expired",
                memberEmail = "seed-member-1@lookation.local",
                spaceName = "시드 클럽",
                startOffsetHours = 8 * DAY_HOURS,
                endOffsetHours = 8 * DAY_HOURS + 3,
                guestCount = 40,
                price = 45000,
                status = ReservationStatus.EXPIRED,
                createdBeforeHours = 5 * DAY_HOURS,
                paymentDueOffsetHours = -1,
                events = listOf(
                    SeedEvent(null, ReservationStatus.PENDING_APPROVAL, "MEMBER"),
                    SeedEvent(ReservationStatus.PENDING_APPROVAL, ReservationStatus.EXPIRED, "SYSTEM"),
                ),
            ),
            SeedReservation(
                marker = "seed:batch:expire",
                memberEmail = "seed-member-1@lookation.local",
                spaceName = "시드 루프탑",
                startOffsetHours = 10 * DAY_HOURS,
                endOffsetHours = 10 * DAY_HOURS + 2,
                guestCount = 7,
                price = 60000,
                status = ReservationStatus.PENDING_APPROVAL,
                createdBeforeHours = 2 * DAY_HOURS,
                events = listOf(
                    SeedEvent(null, ReservationStatus.PENDING_APPROVAL, "MEMBER"),
                ),
            ),
            SeedReservation(
                marker = "seed:batch:complete",
                memberEmail = "seed-member-1@lookation.local",
                spaceName = "시드 워크샵",
                startOffsetHours = -2 * DAY_HOURS,
                endOffsetHours = -DAY_HOURS,
                guestCount = 6,
                price = 30005,
                status = ReservationStatus.CONFIRMED,
                createdBeforeHours = 3 * DAY_HOURS,
                confirmedBeforeHours = 2 * DAY_HOURS,
                events = listOf(
                    SeedEvent(null, ReservationStatus.CONFIRMED, "MEMBER"),
                ),
            ),
        )
    }
}
