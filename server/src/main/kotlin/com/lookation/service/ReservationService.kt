package com.lookation.service

import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ConflictException
import com.lookation.global.error.ForbiddenException
import com.lookation.global.error.NotFoundException
import com.lookation.controller.form.CreateReservationForm
import com.lookation.dto.ReservationDto
import com.lookation.domain.CancellationPolicy
import com.lookation.domain.ActorType
import com.lookation.domain.ReservationStatus
import com.lookation.domain.isPubliclyVisible
import com.lookation.domain.WalletEntryType
import com.lookation.repository.ReservationRepository
import com.lookation.repository.ReservationRow
import com.lookation.repository.SpaceRepository
import com.lookation.repository.WalletRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.LocalTime
import java.time.ZoneId
import com.lookation.dto.HostReservationDto

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val spaceRepository: SpaceRepository,
    private val walletRepository: WalletRepository,
    private val sanctionService: com.lookation.service.SanctionService,
) {

    @Transactional
    fun create(memberId: Long, form: CreateReservationForm): ReservationDto {
        sanctionService.checkWritable(memberId)
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
        if (!space.status.isPubliclyVisible) {
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

        // 즉시 예약도 create->pay->confirm 2단계(스펙 #35): 생성은 PENDING_PAYMENT, 홀드는 pay 시점.
        val requiresApproval = space.approvalRequired
        val initialStatus = if (requiresApproval) ReservationStatus.PENDING_APPROVAL else ReservationStatus.PENDING_PAYMENT
        val paymentDueAt = if (requiresApproval) null else now.plusHours(PAYMENT_WINDOW_HOURS)
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
                paymentDueAt = paymentDueAt,
            )
        } catch (_: DataIntegrityViolationException) {
            throw ConflictException("이미 예약된 시간대입니다")
        }
        reservationRepository.addEvent(reservationId, null, initialStatus, ActorType.MEMBER, memberId)
        return response(reservationId)
    }

    @Transactional
    fun approve(hostId: Long, reservationId: Long): ReservationDto {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        if (reservation.hostId != hostId) {
            throw ForbiddenException("호스트만 승인할 수 있습니다")
        }
        val now = OffsetDateTime.now()
        if (reservationRepository.updateStatus(
                id = reservationId,
                expectedStatus = ReservationStatus.PENDING_APPROVAL,
                status = ReservationStatus.PENDING_PAYMENT,
                approvedAt = now,
                paymentDueAt = now.plusHours(PAYMENT_WINDOW_HOURS),
            ) != 1
        ) {
            throw ConflictException("승인할 수 없는 상태입니다")
        }
        reservationRepository.addEvent(reservationId, ReservationStatus.PENDING_APPROVAL, ReservationStatus.PENDING_PAYMENT, ActorType.HOST, hostId)
        return response(reservationId)
    }

    @Transactional
    fun reject(hostId: Long, reservationId: Long, reason: String?): ReservationDto {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        if (reservation.hostId != hostId) {
            throw ForbiddenException("호스트만 거절할 수 있습니다")
        }
        if (reservationRepository.updateStatus(reservationId, ReservationStatus.PENDING_APPROVAL, ReservationStatus.REJECTED) != 1) {
            throw ConflictException("거절할 수 없는 상태입니다")
        }
        reservationRepository.addEvent(reservationId, ReservationStatus.PENDING_APPROVAL, ReservationStatus.REJECTED, ActorType.HOST, hostId, reason)
        return response(reservationId)
    }

    @Transactional
    fun pay(memberId: Long, reservationId: Long): ReservationDto {
        sanctionService.checkWritable(memberId)
        val reservation = reservationRepository.findById(reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        if (reservation.memberId != memberId) {
            throw ForbiddenException("예약자만 결제할 수 있습니다")
        }
        if (reservation.status != ReservationStatus.PENDING_PAYMENT) {
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
                expectedStatus = ReservationStatus.PENDING_PAYMENT,
                status = ReservationStatus.CONFIRMED,
                confirmedAt = OffsetDateTime.now(),
            ) != 1
        ) {
            throw ConflictException("이미 처리된 예약입니다")
        }
        walletRepository.addEntry(memberId, WalletEntryType.HOLD, -reservation.price, "RESERVATION", reservationId)
        reservationRepository.addEvent(reservationId, ReservationStatus.PENDING_PAYMENT, ReservationStatus.CONFIRMED, ActorType.MEMBER, memberId)
        return response(reservationId)
    }

    @Transactional
    fun cancel(accountId: Long, reservationId: Long): ReservationDto {
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
        if (reservation.status != ReservationStatus.CONFIRMED) {
            if (reservationRepository.updateStatus(reservationId, reservation.status, ReservationStatus.CANCELLED, cancelledAt = OffsetDateTime.now()) != 1) {
                throw ConflictException("이미 처리된 예약입니다")
            }
            reservationRepository.addEvent(
                reservationId,
                reservation.status,
                ReservationStatus.CANCELLED,
                if (reservation.hostId == accountId) ActorType.HOST else ActorType.MEMBER,
                accountId,
            )
            return response(reservationId, walletRepository.refundedAmount(reservationId))
        }

        val rate = if (isHost) {
            BigDecimal.ONE
        } else {
            CancellationPolicy.refundRate(reservation.startAt, OffsetDateTime.now())
        }
        val refund = CancellationPolicy.refundAmount(reservation.price, rate)

        if (reservationRepository.updateStatus(reservation.id, ReservationStatus.CONFIRMED, ReservationStatus.CANCELLED, OffsetDateTime.now()) != 1) {
            throw ConflictException("이미 처리된 예약입니다")
        }
        if (refund > 0) {
            walletRepository.ensureWallet(reservation.memberId)
            walletRepository.deposit(reservation.memberId, refund)
            walletRepository.addEntry(reservation.memberId, WalletEntryType.REFUND, refund, "RESERVATION", reservation.id)
        }
        reservationRepository.addEvent(
            reservationId = reservation.id,
            fromStatus = ReservationStatus.CONFIRMED,
            toStatus = ReservationStatus.CANCELLED,
            actorType = if (isHost) ActorType.HOST else ActorType.MEMBER,
            actorId = accountId,
        )

        return response(reservation.id, refund)
    }

    @Transactional(readOnly = true)
    fun get(accountId: Long, reservationId: Long): ReservationDto {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        if (reservation.memberId != accountId && reservation.hostId != accountId) {
            throw ForbiddenException("예약 당사자만 조회할 수 있습니다")
        }
        return response(reservation)
    }

    @Transactional(readOnly = true)
    fun getMine(memberId: Long, reservationId: Long): ReservationDto {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        if (reservation.memberId != memberId) {
            throw ForbiddenException("본인 예약만 조회할 수 있습니다")
        }
        return response(reservation)
    }

    @Transactional
    fun cancelMine(memberId: Long, reservationId: Long): ReservationDto {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        if (reservation.memberId != memberId) {
            throw ForbiddenException("본인 예약만 취소할 수 있습니다")
        }
        return cancel(memberId, reservationId)
    }

    @Transactional(readOnly = true)
    fun listForHost(
        hostId: Long,
        status: String?,
        cursor: Long?,
        limit: Int,
    ): List<com.lookation.dto.HostReservationDto> {
        if (limit !in 1..100) throw BadRequestException("limit은 1~100입니다")
        val statusFilter = status?.let {
            try {
                ReservationStatus.valueOf(it)
            } catch (_: IllegalArgumentException) {
                throw BadRequestException("알 수 없는 예약 상태입니다")
            }
        }
        return reservationRepository.findByHost(hostId, statusFilter, cursor, limit).map { row ->
            val contactVisible = row.status == ReservationStatus.CONFIRMED || row.status == ReservationStatus.COMPLETED
            com.lookation.dto.HostReservationDto(
                id = row.id,
                spaceId = row.spaceId,
                spaceName = row.spaceName,
                memberNickname = row.memberNickname,
                memberName = if (contactVisible) row.memberName else null,
                memberPhone = if (contactVisible) row.memberPhone else null,
                startAt = row.startAt,
                endAt = row.endAt,
                guestCount = row.guestCount,
                price = row.price,
                status = row.status,
            )
        }
    }

    @Transactional(readOnly = true)
    fun listMine(memberId: Long, status: String? = null): List<ReservationDto> {
        val statusFilter = status?.takeIf { it.isNotBlank() }?.let {
            try {
                ReservationStatus.valueOf(it)
            } catch (_: IllegalArgumentException) {
                throw BadRequestException("알 수 없는 예약 상태입니다")
            }
        }
        return reservationRepository.findByMember(memberId, statusFilter).map {
            ReservationDto(
                id = it.id,
                spaceId = it.spaceId,
                spaceName = it.spaceName,
                startAt = it.startAt,
                endAt = it.endAt,
                guestCount = it.guestCount,
                price = it.price,
                status = it.status,
                requestMessage = it.requestMessage,
                paymentDueAt = it.paymentDueAt,
                createdAt = it.createdAt,
                canCancel = canCancel(it.status, it.startAt),
                refundedAmount = walletRepository.refundedAmount(it.id),
            )
        }
    }

    private fun response(reservationId: Long, refundedAmount: Long? = null): ReservationDto =
        response(requireNotNull(reservationRepository.findById(reservationId)), refundedAmount)

    private fun response(reservation: ReservationRow, refundedAmount: Long? = null): ReservationDto {
        val spaceName = spaceRepository.findById(reservation.spaceId)?.name ?: ""
        return ReservationDto(
            id = reservation.id,
            spaceId = reservation.spaceId,
            spaceName = spaceName,
            startAt = reservation.startAt,
            endAt = reservation.endAt,
            guestCount = reservation.guestCount,
            price = reservation.price,
            status = reservation.status,
            requestMessage = reservation.requestMessage,
            paymentDueAt = reservation.paymentDueAt,
            createdAt = reservation.createdAt,
            canCancel = canCancel(reservation.status, reservation.startAt),
            refundedAmount = refundedAmount ?: walletRepository.refundedAmount(reservation.id),
        )
    }

    private fun canCancel(status: ReservationStatus, startAt: OffsetDateTime): Boolean =
        status in CANCELLABLE_STATUSES && OffsetDateTime.now().isBefore(startAt)

    companion object {
        private const val MAX_ADVANCE_DAYS = 90L
        private const val PAYMENT_WINDOW_HOURS = 1L
        private val CANCELLABLE_STATUSES = setOf(
            ReservationStatus.PENDING_APPROVAL,
            ReservationStatus.PENDING_PAYMENT,
            ReservationStatus.CONFIRMED,
        )
        private val KST = ZoneId.of("Asia/Seoul")
    }
}
