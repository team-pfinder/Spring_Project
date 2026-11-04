package com.lookation.domain.reservation

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

object CancellationPolicy {

    private val FULL = BigDecimal("1.00")
    private val SEVENTY_PERCENT = BigDecimal("0.70")
    private val FIFTY_PERCENT = BigDecimal("0.50")

    private val KST = ZoneId.of("Asia/Seoul")

    fun refundRate(startAt: OffsetDateTime, now: OffsetDateTime): BigDecimal {
        val startDate = startAt.atZoneSameInstant(KST).toLocalDate()
        val nowDate = now.atZoneSameInstant(KST).toLocalDate()
        val days = ChronoUnit.DAYS.between(nowDate, startDate)
        return when {
            days >= 7 -> FULL
            days >= 3 -> SEVENTY_PERCENT
            days >= 1 -> FIFTY_PERCENT
            else -> BigDecimal.ZERO
        }
    }

    fun refundAmount(price: Long, rate: BigDecimal): Long =
        BigDecimal.valueOf(price).multiply(rate).setScale(0, RoundingMode.DOWN).toLong()
}
