package com.lookation.service.reservation

import com.lookation.repository.job.JobRunRepository
import com.lookation.repository.reservation.ReservationRepository
import com.lookation.repository.settlement.SettlementRepository
import com.lookation.repository.wallet.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
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
            val processed = reservationRepository.findPastEnd(now, "CONFIRMED").count { reservation ->
                if (reservationRepository.updateStatus(
                        reservation.id,
                        "CONFIRMED",
                        "COMPLETED",
                        completedAt = now,
                    ) == 1
                ) {
                    reservationRepository.addEvent(reservation.id, "CONFIRMED", "COMPLETED", "SYSTEM", null)
                    true
                } else {
                    false
                }
            }
            jobRunRepository.finish(runId, "SUCCESS", processed)
            processed
        } catch (exception: Exception) {
            jobRunRepository.finish(runId, "FAILED", 0, exception.message)
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
                    if (reservationRepository.updateStatus(reservation.id, "PENDING_APPROVAL", "EXPIRED") == 1) {
                        reservationRepository.addEvent(reservation.id, "PENDING_APPROVAL", "EXPIRED", "SYSTEM", null)
                        processed++
                    }
                }
            reservationRepository.findPaymentOverdue(now).forEach { reservation ->
                if (reservationRepository.updateStatus(reservation.id, "PENDING_PAYMENT", "EXPIRED") == 1) {
                    reservationRepository.addEvent(reservation.id, "PENDING_PAYMENT", "EXPIRED", "SYSTEM", null)
                    processed++
                }
            }
            jobRunRepository.finish(runId, "SUCCESS", processed)
            processed
        } catch (exception: Exception) {
            jobRunRepository.finish(runId, "FAILED", 0, exception.message)
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
                val fee = BigDecimal.valueOf(reservation.price)
                    .multiply(FEE_RATE)
                    .setScale(0, RoundingMode.DOWN)
                    .toLong()
                val net = reservation.price - fee
                settlementRepository.insert(reservation.id, reservation.hostId, reservation.price, fee, net)
                walletRepository.ensureWallet(reservation.hostId)
                walletRepository.deposit(reservation.hostId, net)
                walletRepository.addEntry(reservation.hostId, "SETTLE", net, "RESERVATION", reservation.id)
                processed++
            }
            jobRunRepository.finish(runId, "SUCCESS", processed)
            processed
        } catch (exception: Exception) {
            jobRunRepository.finish(runId, "FAILED", 0, exception.message)
            throw exception
        }
    }

    companion object {
        private const val APPROVAL_WINDOW_HOURS = 24L
        private val FEE_RATE = BigDecimal("0.10")
        private val KST = ZoneId.of("Asia/Seoul")
    }
}
