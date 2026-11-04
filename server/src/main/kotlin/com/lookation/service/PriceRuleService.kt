package com.lookation.service

import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ForbiddenException
import com.lookation.global.error.NotFoundException
import com.lookation.controller.form.PriceRuleForm
import com.lookation.dto.PriceRuleResponse
import com.lookation.repository.NewPriceRule
import com.lookation.repository.PriceRuleRepository
import com.lookation.repository.SpaceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime

@Service
class PriceRuleService(
    private val priceRuleRepository: PriceRuleRepository,
    private val spaceRepository: SpaceRepository,
) {

    @Transactional(readOnly = true)
    fun list(accountId: Long?, spaceId: Long): List<PriceRuleResponse> {
        val space = spaceRepository.findById(spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.status != "PUBLISHED" && space.hostId != accountId) {
            throw NotFoundException("공간을 찾을 수 없습니다")
        }
        return priceRuleRepository.list(spaceId).map { it.toResponse() }
    }

    @Transactional
    fun replace(accountId: Long, spaceId: Long, rules: List<PriceRuleForm>): List<PriceRuleResponse> {
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
                "HOUR" -> {
                    val start = rule.startTime ?: throw BadRequestException("시간 단위 규칙에는 시작 시각이 필요합니다")
                    val end = rule.endTime ?: throw BadRequestException("시간 단위 규칙에는 종료 시각이 필요합니다")
                    if (!end.isAfter(start)) throw BadRequestException("종료 시각은 시작 시각 이후여야 합니다")
                }
                "DAY" -> if (rule.startTime != null || rule.endTime != null) {
                    throw BadRequestException("일 단위 규칙에는 시각을 지정할 수 없습니다")
                }
                else -> throw BadRequestException("알 수 없는 가격 단위입니다")
            }
        }
        rules.filter { it.unit == "HOUR" }
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

    private fun com.lookation.repository.PriceRuleRow.toResponse() = PriceRuleResponse(
        id = id,
        dayOfWeek = dayOfWeek,
        unit = unit,
        startTime = startTime,
        endTime = endTime,
        price = price,
    )

    companion object {
        private const val MAX_RULES = 50
    }
}
