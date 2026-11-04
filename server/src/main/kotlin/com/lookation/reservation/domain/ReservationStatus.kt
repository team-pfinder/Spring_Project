package com.lookation.reservation.domain

enum class ReservationStatus {
    PENDING_APPROVAL,
    PENDING_PAYMENT,
    CONFIRMED,
    COMPLETED,
    CANCELLED,
    REFUNDED,
    REJECTED,
    EXPIRED,
}
