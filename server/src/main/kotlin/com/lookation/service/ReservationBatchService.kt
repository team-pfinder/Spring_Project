package com.lookation.service

import com.lookation.repository.JobRunRepository
import com.lookation.domain.JobStatus
import com.lookation.domain.ActorType
import com.lookation.domain.ReservationStatus
import com.lookation.domain.WalletEntryType
import com.lookation.repository.ReservationRepository
import com.lookation.domain.SettlementPolicy
import com.lookation.repository.SettlementRepository
import com.lookation.repository.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.time.ZoneId

@Service
class ReservationBatchService(
    private val reservationRepository: ReservationRepository,
    private val walletRepository: WalletRepository,
    private val settlementRepository: SettlementRepository,
    private val jobRunRepository: JobRunRepository,
) {

    @Transactional
    fun completeReservations(now: OffsetDateTime = OffsetDateTime.now()): Int {
        val runId = jobRunRepository.start("COMPLETE_RESERVATIONS")
        return try {
            val processed = reservationRepository.findPastEnd(now, ReservationStatus.CONFIRMED).count { reservation ->
                if (reservationRepository.updateStatus(
                        reservation.id,
                        ReservationStatus.CONFIRMED,
                        ReservationStatus.COMPLETED,
                        completedAt = now,
                    ) == 1
                ) {
                    reservationRepository.addEvent(reservation.id, ReservationStatus.CONFIRMED, ReservationStatus.COMPLETED, ActorType.SYSTEM, null)
                    true
                } else {
                    false
                }
            }
            jobRunRepository.finish(runId, JobStatus.SUCCESS, processed)
            processed
        } catch (exception: Exception) {
            jobRunRepository.finish(runId, JobStatus.FAILED, 0, exception.message)
            throw exception
        }
    }

    @Transactional
    fun expireReservations(now: OffsetDateTime = OffsetDateTime.now()): Int {
        val runId = jobRunRepository.start("EXPIRE_RESERVATIONS")
        return try {
            var processed = 0
            reservationRepository.findPendingApprovalCreatedBefore(now.minusHours(APPROVAL_WINDOW_HOURS))
                .forEach { reservation ->
                    if (reservationRepository.updateStatus(reservation.id, ReservationStatus.PENDING_APPROVAL, ReservationStatus.EXPIRED) == 1) {
                        reservationRepository.addEvent(reservation.id, ReservationStatus.PENDING_APPROVAL, ReservationStatus.EXPIRED, ActorType.SYSTEM, null)
                        processed++
                    }
                }
            reservationRepository.findPaymentOverdue(now).forEach { reservation ->
                if (reservationRepository.updateStatus(reservation.id, ReservationStatus.PENDING_PAYMENT, ReservationStatus.EXPIRED) == 1) {
                    reservationRepository.addEvent(reservation.id, ReservationStatus.PENDING_PAYMENT, ReservationStatus.EXPIRED, ActorType.SYSTEM, null)
                    processed++
                }
            }
            jobRunRepository.finish(runId, JobStatus.SUCCESS, processed)
            processed
        } catch (exception: Exception) {
            jobRunRepository.finish(runId, JobStatus.FAILED, 0, exception.message)
            throw exception
        }
    }

    @Transactional
    fun settle(now: OffsetDateTime = OffsetDateTime.now()): Int {
        val runId = jobRunRepository.start("SETTLE_RESERVATIONS")
        return try {
            val cutoff = now.atZoneSameInstant(KST).toLocalDate().atStartOfDay(KST).toOffsetDateTime()
            var processed = 0
            reservationRepository.findSettleable(cutoff).forEach { reservation ->
                if (settlementRepository.existsByReservation(reservation.id)) return@forEach
                val fee = SettlementPolicy.fee(reservation.price)
                val net = SettlementPolicy.net(reservation.price)
                settlementRepository.insert(reservation.id, reservation.hostId, reservation.price, fee, net)
                walletRepository.ensureWallet(reservation.hostId)
                walletRepository.deposit(reservation.hostId, net)
                walletRepository.addEntry(reservation.hostId, WalletEntryType.SETTLE, net, "RESERVATION", reservation.id)
                processed++
            }
            jobRunRepository.finish(runId, JobStatus.SUCCESS, processed)
            processed
        } catch (exception: Exception) {
            jobRunRepository.finish(runId, JobStatus.FAILED, 0, exception.message)
            throw exception
        }
    }

    companion object {
        private const val APPROVAL_WINDOW_HOURS = 24L
        private val KST = ZoneId.of("Asia/Seoul")
    }
}
