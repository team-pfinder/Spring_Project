package com.lookation.view

import com.lookation.domain.ReservationStatus
import com.lookation.dto.OccupiedIntervalDto
import com.lookation.dto.PriceRuleDto
import com.lookation.global.error.UnauthorizedException
import org.springframework.security.oauth2.jwt.Jwt
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId

val KST: ZoneId = ZoneId.of("Asia/Seoul")

data class Viewer(
    val id: Long,
    val nickname: String,
    val roles: List<String>,
)

/**
 * 예약 폼·상세 화면에 보여줄 슬롯. 가격 규칙과 점유 구간을 합쳐 만든다.
 *
 * [available]이 false여도 서버는 생성 시 다시 검증하므로, 변조된 요청은 409/400으로 막힌다.
 */
data class BookingSlot(
    val priceRuleId: Long,
    val label: String,
    val startAt: OffsetDateTime,
    val endAt: OffsetDateTime,
    val price: Long,
    val available: Boolean,
)

fun Jwt.accountIdOrNull(): Long? = subject?.toLongOrNull()

fun Jwt.requireAccountId(): Long =
    accountIdOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")

/** 로그인 후 이동할 경로. 열린 리다이렉트를 막기 위해 사이트 내부 상대 경로만 허용한다. */
fun safeNext(next: String?): String =
    next?.takeIf {
        it.startsWith("/") &&
            !it.startsWith("//") &&
            '\\' !in it &&
            it.none { char -> char.code < 0x20 }
    } ?: "/spaces"

fun urlEncode(value: String): String = URLEncoder.encode(value, StandardCharsets.UTF_8)

fun bookingSlots(
    date: LocalDate,
    rules: List<PriceRuleDto>,
    occupied: List<OccupiedIntervalDto>,
    now: OffsetDateTime = OffsetDateTime.now(),
): List<BookingSlot> {
    val dayOfWeek = date.dayOfWeek.value
    return rules
        .filter { it.dayOfWeek == dayOfWeek }
        .map { rule ->
            val startAt: OffsetDateTime
            val endAt: OffsetDateTime
            val label: String
            if (rule.unit == "DAY") {
                startAt = date.atStartOfDay(KST).toOffsetDateTime()
                endAt = date.plusDays(1).atStartOfDay(KST).toOffsetDateTime()
                label = "종일 (00:00 ~ 다음 날 00:00)"
            } else {
                val startTime = requireNotNull(rule.startTime) { "HOUR 가격 규칙에 시작 시각이 없습니다" }
                val endTime = requireNotNull(rule.endTime) { "HOUR 가격 규칙에 종료 시각이 없습니다" }
                startAt = date.atTime(startTime).atZone(KST).toOffsetDateTime()
                endAt = date.atTime(endTime).atZone(KST).toOffsetDateTime()
                label = "$startTime ~ $endTime"
            }
            val overlaps = occupied.any { it.startAt < endAt && startAt < it.endAt }
            BookingSlot(
                priceRuleId = rule.id,
                label = label,
                startAt = startAt,
                endAt = endAt,
                price = rule.price,
                available = !overlaps && startAt.isAfter(now),
            )
        }
        .sortedBy { it.startAt }
}

fun ReservationStatus.canCancelNow(startAt: OffsetDateTime): Boolean =
    this in setOf(
        ReservationStatus.PENDING_APPROVAL,
        ReservationStatus.PENDING_PAYMENT,
        ReservationStatus.CONFIRMED,
    ) && OffsetDateTime.now().isBefore(startAt)
