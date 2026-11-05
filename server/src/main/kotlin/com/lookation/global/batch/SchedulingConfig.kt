package com.lookation.global.batch

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class SchedulingConfig

@Configuration
@Profile("!test")
class ReservationScheduler(private val reservationBatchService: com.lookation.service.ReservationBatchService) {

    @Scheduled(cron = "0 5 0 * * *", zone = "Asia/Seoul")
    fun completeReservations() {
        reservationBatchService.completeReservations()
    }

    @Scheduled(cron = "0 10 0 * * *", zone = "Asia/Seoul")
    fun settle() {
        reservationBatchService.settle()
    }

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    fun expireReservations() {
        reservationBatchService.expireReservations()
    }
}
