package com.lookation.domain

import java.math.BigDecimal
import java.math.RoundingMode

/** Settlement split: the platform keeps 10% of the reservation price, rounded down. */
object SettlementPolicy {

    private val FEE_RATE = BigDecimal("0.10")

    fun fee(price: Long): Long =
        BigDecimal.valueOf(price).multiply(FEE_RATE).setScale(0, RoundingMode.DOWN).toLong()

    fun net(price: Long): Long = price - fee(price)
}
