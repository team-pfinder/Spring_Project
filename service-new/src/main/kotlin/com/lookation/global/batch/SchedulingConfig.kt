package com.lookation.global.batch

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class SchedulingConfig

// 테스트에서는 스케줄러를 끈다. 매분 도는 expire 작업이 시드 데이터를 바꿔 테스트를 흔들지 않게 한다.
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
