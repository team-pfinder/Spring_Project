package com.lookation.service

import com.lookation.domain.SpaceStatus
import com.lookation.dto.OccupiedIntervalDto
import com.lookation.dto.SpaceAvailabilityDto
import com.lookation.global.error.BadRequestException
import com.lookation.global.error.NotFoundException
import com.lookation.repository.ReservationRepository
import com.lookation.repository.SpaceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId

/**
 * 공간의 점유 구간을 조회한다.
 *
 * 예약자 정보는 절대 밖으로 내보내지 않고 `[startAt, endAt)` 구간만 반환한다. 점유 상태 집합은
 * DB exclusion constraint와 동일한 [ReservationRepository.OCCUPYING_STATUSES]를 쓴다.
 */
@Service
class AvailabilityService(
    private val spaceRepository: SpaceRepository,
    private val reservationRepository: ReservationRepository,
) {

    @Transactional(readOnly = true)
    fun availability(
        accountId: Long?,
        spaceId: Long,
        from: OffsetDateTime,
        to: OffsetDateTime,
    ): SpaceAvailabilityDto {
        if (!to.isAfter(from)) {
            throw BadRequestException("종료 일시는 시작 일시 이후여야 합니다")
        }
        if (Duration.between(from, to) > MAX_RANGE) {
            throw BadRequestException("조회 기간은 최대 ${MAX_RANGE.toDays()}일입니다")
        }
        val space = spaceRepository.findById(spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.status != SpaceStatus.PUBLISHED && space.hostId != accountId) {
            throw NotFoundException("공간을 찾을 수 없습니다")
        }
        return SpaceAvailabilityDto(
            spaceId = spaceId,
            from = from,
            to = to,
            occupied = reservationRepository.findOccupied(spaceId, from, to).map {
                OccupiedIntervalDto(it.startAt, it.endAt)
            },
        )
    }

    @Transactional(readOnly = true)
    fun occupiedForDate(spaceId: Long, date: LocalDate): List<OccupiedIntervalDto> {
        val from = date.atStartOfDay(KST).toOffsetDateTime()
        val to = date.plusDays(1).atStartOfDay(KST).toOffsetDateTime()
        return reservationRepository.findOccupied(spaceId, from, to).map {
            OccupiedIntervalDto(it.startAt, it.endAt)
        }
    }

    companion object {
        private val MAX_RANGE: Duration = Duration.ofDays(92)
        private val KST: ZoneId = ZoneId.of("Asia/Seoul")
    }
}
