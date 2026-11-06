package com.lookation.view

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/** Thymeleaf 템플릿에서 쓰는 표시 형식. 뷰마다 같은 포맷을 반복하지 않도록 모은다. */
@Component("fmt")
class ViewFormat {

    fun won(value: Long?): String =
        if (value == null) "-" else "%,d원".format(value)

    fun won(value: Number?): String =
        if (value == null) "-" else "%,d원".format(value.toLong())

    fun dateTime(value: OffsetDateTime?): String =
        value?.atZoneSameInstant(KST)?.format(DATE_TIME) ?: "-"

    fun date(value: LocalDate?): String =
        value?.format(DATE) ?: "-"

    fun time(value: LocalTime?): String =
        value?.format(TIME) ?: "-"

    fun rating(value: Double?): String =
        value?.let { "%.1f".format(it) } ?: "신규"

    fun status(value: String?): String =
        when (value) {
            "PENDING_APPROVAL" -> "승인 대기"
            "PENDING_PAYMENT" -> "결제 대기"
            "CONFIRMED" -> "예약 확정"
            "COMPLETED" -> "이용 완료"
            "CANCELLED" -> "취소됨"
            "REFUNDED" -> "환불됨"
            "REJECTED" -> "거절됨"
            "EXPIRED" -> "만료됨"
            else -> value ?: "-"
        }

    fun spaceType(value: String?): String =
        when (value) {
            "PARTY_ROOM" -> "파티룸"
            "EMPTY_SPACE" -> "빈 공간"
            "ROOFTOP" -> "루프탑"
            "BRIDAL_SHOWER" -> "브라이덜샤워"
            "CLUB" -> "클럽"
            "WORKSHOP" -> "워크샵"
            else -> value ?: "-"
        }

    fun facility(value: String?): String =
        when (value) {
            "PARKING" -> "주차"
            "WIFI" -> "와이파이"
            "PROJECTOR" -> "프로젝터"
            "SOUND" -> "음향"
            "KITCHEN" -> "주방"
            "REFRIGERATOR" -> "냉장고"
            "MICROWAVE" -> "전자레인지"
            "AIR_CONDITIONER" -> "에어컨"
            "TABLE" -> "테이블"
            "CHAIR" -> "의자"
            "BATHROOM" -> "화장실"
            "ELEVATOR" -> "엘리베이터"
            else -> value ?: "-"
        }

    fun badgeClass(status: String?): String =
        when (status) {
            "CONFIRMED", "COMPLETED", "PUBLISHED" -> "badge-success"
            "PENDING_APPROVAL", "PENDING_PAYMENT" -> "badge-warning"
            "CANCELLED", "REJECTED", "EXPIRED", "REFUNDED" -> "badge-secondary"
            else -> "badge-light"
        }

    private companion object {
        val DATE: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val DATE_TIME: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
        val TIME: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    }
}
