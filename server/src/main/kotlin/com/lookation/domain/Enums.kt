package com.lookation.domain

enum class AccountStatus {
    ACTIVE,
    WITHDRAWN,
}

enum class AccountRole {
    MEMBER,
    HOST,
    ADMIN,
}

enum class ActorType {
    MEMBER,
    HOST,
    ADMIN,
    SYSTEM,
}

enum class WalletEntryType {
    CHARGE,
    HOLD,
    RELEASE,
    REFUND,
    SETTLE,
    FEE,
    WITHDRAWAL,
}

enum class PriceUnit {
    HOUR,
    DAY,
}

enum class ReportTarget {
    SPACE,
    RESERVATION,
    REVIEW,
    QNA,
    ACCOUNT,
}

enum class SpaceStatus {
    DRAFT,
    PENDING_INSPECTION,
    INSPECTION_REJECTED,
    PUBLISHED,
    SUSPENDED,
    CLOSED,
}

enum class InspectionStatus {
    PENDING_INSPECTION,
    INSPECTION_REJECTED,
    PUBLISHED,
    SUSPENDED,
}

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

enum class QnaStatus {
    OPEN,
    ANSWERED,
}

enum class ReportStatus {
    RECEIVED,
    ACCEPTED,
    REJECTED,
}

enum class ChargeStatus {
    REQUESTED,
    APPROVED,
    REJECTED,
}

enum class WithdrawalStatus {
    REQUESTED,
    APPROVED,
    PAID,
    REJECTED,
}

enum class JobStatus {
    RUNNING,
    SUCCESS,
    FAILED,
}

enum class VerificationPurpose {
    SIGNUP,
    PASSWORD_RESET,
}

enum class SpaceType {
    PARTY_ROOM,
    EMPTY_SPACE,
    ROOFTOP,
    BRIDAL_SHOWER,
    CLUB,
    WORKSHOP,
}

enum class AttachmentOwnerType {
    SPACE,
    REVIEW,
}
