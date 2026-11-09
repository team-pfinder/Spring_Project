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

/**
 * 공간 공개 범위. [PUBLISHED]만 검색·상세에 공개되고, 나머지는 소유한 호스트 본인에게만 보인다.
 * [SUSPENDED]/[CLOSED]는 게시 중단을 뜻해 비공개이며, 신규 상태가 추가돼도 공개 집합에
 * 명시적으로 넣지 않는 한 비공개로 둔다.
 */
val SpaceStatus.isPubliclyVisible: Boolean
    get() = this == SpaceStatus.PUBLISHED

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

enum class FacilityCode {
    PARKING,
    WIFI,
    PROJECTOR,
    SOUND,
    KITCHEN,
    REFRIGERATOR,
    MICROWAVE,
    AIR_CONDITIONER,
    TABLE,
    CHAIR,
    BATHROOM,
    ELEVATOR,
}

enum class SpaceSort(val key: String) {
    LATEST("latest"),
    PRICE_ASC("price_asc"),
    PRICE_DESC("price_desc"),
    RATING("rating"),
    ;

    companion object {
        fun parse(raw: String?): SpaceSort {
            if (raw.isNullOrBlank()) return LATEST
            return entries.firstOrNull { it.key == raw }
                ?: throw IllegalArgumentException("sort는 latest, price_asc, price_desc, rating 중 하나입니다")
        }
    }
}
