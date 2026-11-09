package com.lookation.service

import com.lookation.domain.PriceUnit
import com.lookation.domain.isPubliclyVisible
import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ForbiddenException
import com.lookation.global.error.NotFoundException
import com.lookation.controller.form.PriceRuleForm
import com.lookation.dto.PriceRuleDto
import com.lookation.repository.NewPriceRule
import com.lookation.repository.PriceRuleRepository
import com.lookation.repository.SpaceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId

@Service
class PriceRuleService(
    private val priceRuleRepository: PriceRuleRepository,
    private val spaceRepository: SpaceRepository,
) {

    @Transactional(readOnly = true)
    fun resolvePrice(spaceId: Long, startAt: OffsetDateTime, endAt: OffsetDateTime): Long {
        val kstStart = startAt.atZoneSameInstant(KST)
        val kstEnd = endAt.atZoneSameInstant(KST)
        val hourRule = priceRuleRepository.findHourRule(
            spaceId = spaceId,
            dayOfWeek = kstStart.dayOfWeek.value,
            startTime = kstStart.toLocalTime(),
            endTime = kstEnd.toLocalTime(),
        )
        if (hourRule != null) {
            if (kstStart.toLocalDate() != kstEnd.toLocalDate()) {
                throw BadRequestException("시간 단위 예약은 같은 날짜 안에서만 가능합니다")
            }
            return hourRule.price
        }
        val dayRule = priceRuleRepository.findDayRule(spaceId, kstStart.dayOfWeek.value)
            ?: throw BadRequestException("해당 시간대의 가격 규칙이 없습니다")
        val isFullDay = kstStart.toLocalTime() == LocalTime.MIDNIGHT &&
            kstEnd.toLocalTime() == LocalTime.MIDNIGHT &&
            kstEnd.toLocalDate() == kstStart.toLocalDate().plusDays(1)
        if (!isFullDay) {
            throw BadRequestException("일 단위 예약은 00:00부터 다음 날 00:00까지만 가능합니다")
        }
        return dayRule.price
    }

    @Transactional(readOnly = true)
    fun list(accountId: Long?, spaceId: Long): List<PriceRuleDto> {
        val space = spaceRepository.findById(spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (!space.status.isPubliclyVisible && space.hostId != accountId) {
            throw NotFoundException("공간을 찾을 수 없습니다")
        }
        return priceRuleRepository.list(spaceId).map { it.toResponse() }
    }

    @Transactional
    fun replace(accountId: Long, spaceId: Long, rules: List<PriceRuleForm>): List<PriceRuleDto> {
        val space = spaceRepository.findById(spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.hostId != accountId) {
            throw ForbiddenException("공간 소유자만 가격을 설정할 수 있습니다")
        }
        validate(rules)
        priceRuleRepository.replace(
            spaceId,
            rules.map {
                NewPriceRule(
                    dayOfWeek = it.dayOfWeek,
                    unit = it.unit,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    price = it.price,
                )
            },
        )
        return priceRuleRepository.list(spaceId).map { it.toResponse() }
    }

    private fun validate(rules: List<PriceRuleForm>) {
        if (rules.size > MAX_RULES) {
            throw BadRequestException("가격 규칙은 ${MAX_RULES}개까지 등록할 수 있습니다")
        }
        rules.forEach { rule ->
            if (rule.dayOfWeek !in 1..7) throw BadRequestException("요일은 1~7이어야 합니다")
            if (rule.price < 0) throw BadRequestException("가격은 0 이상이어야 합니다")
            when (rule.unit) {
                PriceUnit.HOUR -> {
                    val start = rule.startTime ?: throw BadRequestException("시간 단위 규칙에는 시작 시각이 필요합니다")
                    val end = rule.endTime ?: throw BadRequestException("시간 단위 규칙에는 종료 시각이 필요합니다")
                    if (!end.isAfter(start)) throw BadRequestException("종료 시각은 시작 시각 이후여야 합니다")
                }
                PriceUnit.DAY -> if (rule.startTime != null || rule.endTime != null) {
                    throw BadRequestException("일 단위 규칙에는 시각을 지정할 수 없습니다")
                }
            }
        }
        rules.filter { it.unit == PriceUnit.HOUR }
            .groupBy { it.dayOfWeek }
            .forEach { (_, dayRules) ->
                val sorted = dayRules.sortedBy { it.startTime }
                sorted.zipWithNext().forEach { (previous, next) ->
                    if (previous.endTime!! > next.startTime!!) {
                        throw BadRequestException("같은 요일의 시간 블록이 겹칩니다")
                    }
                }
            }
    }

    private fun com.lookation.repository.PriceRuleRow.toResponse() = PriceRuleDto(
        id = id,
        dayOfWeek = dayOfWeek,
        unit = unit,
        startTime = startTime,
        endTime = endTime,
        price = price,
    )

    companion object {
        private const val MAX_RULES = 50
        private val KST = ZoneId.of("Asia/Seoul")
    }
}
