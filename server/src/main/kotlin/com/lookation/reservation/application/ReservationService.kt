package com.lookation.reservation.application

import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ConflictException
import com.lookation.global.error.ForbiddenException
import com.lookation.global.error.NotFoundException
import com.lookation.reservation.api.CreateReservationForm
import com.lookation.reservation.api.ReservationResponse
import com.lookation.reservation.domain.CancellationPolicy
import com.lookation.reservation.infrastructure.ReservationRepository
import com.lookation.reservation.infrastructure.ReservationRow
import com.lookation.space.infrastructure.SpaceRepository
import com.lookation.wallet.infrastructure.WalletRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.LocalTime
import java.time.ZoneId

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val spaceRepository: SpaceRepository,
    private val walletRepository: WalletRepository,
) {

    @Transactional
    fun create(memberId: Long, form: CreateReservationForm): ReservationResponse {
        val now = OffsetDateTime.now()
        if (!form.endAt.isAfter(form.startAt)) {
            throw BadRequestException("종료 시각은 시작 시각 이후여야 합니다")
        }
        if (form.startAt.isBefore(now)) {
            throw BadRequestException("과거 시각은 예약할 수 없습니다")
        }
        if (form.startAt.isAfter(now.plusDays(MAX_ADVANCE_DAYS))) {
            throw BadRequestException("예약 가능 기간은 ${MAX_ADVANCE_DAYS}일입니다")
        }

        val space = spaceRepository.findById(form.spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.status != "PUBLISHED") {
            throw ConflictException("예약할 수 없는 공간입니다")
        }
        if (form.guestCount < space.minCapacity || form.guestCount > space.maxCapacity) {
            throw ConflictException("예약 인원은 ${space.minCapacity}~${space.maxCapacity}명이어야 합니다")
        }

        val kstStart = form.startAt.atZoneSameInstant(KST)
        val kstEnd = form.endAt.atZoneSameInstant(KST)
        val hourRule = spaceRepository.findHourRule(
            spaceId = form.spaceId,
            dayOfWeek = kstStart.dayOfWeek.value,
            startTime = kstStart.toLocalTime(),
            endTime = kstEnd.toLocalTime(),
        )
        val price: Long
        if (hourRule != null) {
            if (kstStart.toLocalDate() != kstEnd.toLocalDate()) {
                throw BadRequestException("시간 단위 예약은 같은 날짜 안에서만 가능합니다")
            }
            price = hourRule.price
        } else {
            val dayRule = spaceRepository.findDayRule(form.spaceId, kstStart.dayOfWeek.value)
                ?: throw BadRequestException("해당 시간대의 가격 규칙이 없습니다")
            val isFullDay = kstStart.toLocalTime() == LocalTime.MIDNIGHT &&
                kstEnd.toLocalTime() == LocalTime.MIDNIGHT &&
                kstEnd.toLocalDate() == kstStart.toLocalDate().plusDays(1)
            if (!isFullDay) {
                throw BadRequestException("일 단위 예약은 00:00부터 다음 날 00:00까지만 가능합니다")
            }
            price = dayRule.price
        }

        val requiresApproval = space.approvalRequired
        if (!requiresApproval) {
            walletRepository.ensureWallet(memberId)
            if (!walletRepository.hold(memberId, price)) {
                throw ConflictException("마일리지 잔액이 부족합니다")
            }
        }

        val initialStatus = if (requiresApproval) "PENDING_APPROVAL" else "CONFIRMED"
        val reservationId = try {
            reservationRepository.insert(
                memberId = memberId,
                hostId = space.hostId,
                spaceId = form.spaceId,
                startAt = form.startAt,
                endAt = form.endAt,
                guestCount = form.guestCount,
                price = price,
                status = initialStatus,
                requestMessage = form.requestMessage,
            )
        } catch (_: DataIntegrityViolationException) {
            throw ConflictException("이미 예약된 시간대입니다")
        }

        if (!requiresApproval) {
            walletRepository.addEntry(memberId, "HOLD", -price, "RESERVATION", reservationId)
        }
        reservationRepository.addEvent(reservationId, null, initialStatus, "MEMBER", memberId)
        return requireNotNull(reservationRepository.findById(reservationId)).toResponse()
    }

    @Transactional
    fun approve(hostId: Long, reservationId: Long): ReservationResponse {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        if (reservation.hostId != hostId) {
            throw ForbiddenException("호스트만 승인할 수 있습니다")
        }
        val now = OffsetDateTime.now()
        if (reservationRepository.updateStatus(
                id = reservationId,
                expectedStatus = "PENDING_APPROVAL",
                status = "PENDING_PAYMENT",
                approvedAt = now,
                paymentDueAt = now.plusHours(PAYMENT_WINDOW_HOURS),
            ) != 1
        ) {
            throw ConflictException("승인할 수 없는 상태입니다")
        }
        reservationRepository.addEvent(reservationId, "PENDING_APPROVAL", "PENDING_PAYMENT", "HOST", hostId)
        return requireNotNull(reservationRepository.findById(reservationId)).toResponse()
    }

    @Transactional
    fun reject(hostId: Long, reservationId: Long, reason: String?): ReservationResponse {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        if (reservation.hostId != hostId) {
            throw ForbiddenException("호스트만 거절할 수 있습니다")
        }
        if (reservationRepository.updateStatus(reservationId, "PENDING_APPROVAL", "REJECTED") != 1) {
            throw ConflictException("거절할 수 없는 상태입니다")
        }
        reservationRepository.addEvent(reservationId, "PENDING_APPROVAL", "REJECTED", "HOST", hostId, reason)
        return requireNotNull(reservationRepository.findById(reservationId)).toResponse()
    }

    @Transactional
    fun pay(memberId: Long, reservationId: Long): ReservationResponse {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        if (reservation.memberId != memberId) {
            throw ForbiddenException("예약자만 결제할 수 있습니다")
        }
        if (reservation.status != "PENDING_PAYMENT") {
            throw ConflictException("결제할 수 없는 상태입니다")
        }
        if (reservation.paymentDueAt?.isBefore(OffsetDateTime.now()) == true) {
            throw ConflictException("결제 시한이 지났습니다")
        }
        walletRepository.ensureWallet(memberId)
        if (!walletRepository.hold(memberId, reservation.price)) {
            throw ConflictException("마일리지 잔액이 부족합니다")
        }
        if (reservationRepository.updateStatus(
                id = reservationId,
                expectedStatus = "PENDING_PAYMENT",
                status = "CONFIRMED",
                confirmedAt = OffsetDateTime.now(),
            ) != 1
        ) {
            throw ConflictException("이미 처리된 예약입니다")
        }
        walletRepository.addEntry(memberId, "HOLD", -reservation.price, "RESERVATION", reservationId)
        reservationRepository.addEvent(reservationId, "PENDING_PAYMENT", "CONFIRMED", "MEMBER", memberId)
        return requireNotNull(reservationRepository.findById(reservationId)).toResponse()
    }

    @Transactional
    fun cancel(accountId: Long, reservationId: Long): ReservationResponse {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        val isHost = reservation.hostId == accountId
        val isMember = reservation.memberId == accountId
        if (!isHost && !isMember) {
            throw ForbiddenException("예약 당사자만 취소할 수 있습니다")
        }
        if (reservation.status !in CANCELLABLE_STATUSES) {
            throw ConflictException("취소할 수 없는 상태입니다")
        }
        if (!OffsetDateTime.now().isBefore(reservation.startAt)) {
            throw BadRequestException("이용 시작 후에는 취소할 수 없습니다")
        }
        if (reservation.status != "CONFIRMED") {
            if (reservationRepository.updateStatus(reservationId, reservation.status, "CANCELLED", cancelledAt = OffsetDateTime.now()) != 1) {
                throw ConflictException("이미 처리된 예약입니다")
            }
            reservationRepository.addEvent(
                reservationId,
                reservation.status,
                "CANCELLED",
                if (reservation.hostId == accountId) "HOST" else "MEMBER",
                accountId,
            )
            return requireNotNull(reservationRepository.findById(reservationId)).toResponse()
        }

        val rate = if (isHost) {
            BigDecimal.ONE
        } else {
            CancellationPolicy.refundRate(reservation.startAt, OffsetDateTime.now())
        }
        val refund = CancellationPolicy.refundAmount(reservation.price, rate)

        if (reservationRepository.updateStatus(reservation.id, "CONFIRMED", "CANCELLED", OffsetDateTime.now()) != 1) {
            throw ConflictException("이미 처리된 예약입니다")
        }
        if (refund > 0) {
            walletRepository.ensureWallet(reservation.memberId)
            walletRepository.deposit(reservation.memberId, refund)
            walletRepository.addEntry(reservation.memberId, "REFUND", refund, "RESERVATION", reservation.id)
        }
        reservationRepository.addEvent(
            reservationId = reservation.id,
            fromStatus = "CONFIRMED",
            toStatus = "CANCELLED",
            actorType = if (isHost) "HOST" else "MEMBER",
            actorId = accountId,
        )

        return requireNotNull(reservationRepository.findById(reservation.id))
            .toResponse(refundedAmount = refund)
    }

    @Transactional(readOnly = true)
    fun get(accountId: Long, reservationId: Long): ReservationResponse {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        if (reservation.memberId != accountId && reservation.hostId != accountId) {
            throw ForbiddenException("예약 당사자만 조회할 수 있습니다")
        }
        return reservation.toResponse()
    }

    @Transactional(readOnly = true)
    fun listMine(memberId: Long): List<ReservationResponse> =
        reservationRepository.findByMember(memberId).map { it.toResponse() }

    private fun ReservationRow.toResponse(refundedAmount: Long? = null) = ReservationResponse(
        id = id,
        spaceId = spaceId,
        startAt = startAt,
        endAt = endAt,
        price = price,
        status = status,
        refundedAmount = refundedAmount,
    )

    companion object {
        private const val MAX_ADVANCE_DAYS = 90L
        private const val PAYMENT_WINDOW_HOURS = 1L
        private val CANCELLABLE_STATUSES = setOf("PENDING_APPROVAL", "PENDING_PAYMENT", "CONFIRMED")
        private val KST = ZoneId.of("Asia/Seoul")
    }
}
