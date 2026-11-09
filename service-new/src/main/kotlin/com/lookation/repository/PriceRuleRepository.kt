package com.lookation.repository

import com.lookation.domain.PriceUnit
import com.lookation.jooq.tables.PriceRules.PRICE_RULES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalTime

data class PriceRuleRow(
    val id: Long,
    val dayOfWeek: Int,
    val unit: PriceUnit,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val price: Long,
)

data class PriceLookup(
    val id: Long,
    val price: Long,
)

data class NewPriceRule(
    val dayOfWeek: Int,
    val unit: PriceUnit,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val price: Long,
)

@Repository
class PriceRuleRepository(private val dsl: DSLContext) {

    fun list(spaceId: Long): List<PriceRuleRow> =
        dsl.selectFrom(PRICE_RULES)
            .where(PRICE_RULES.SPACE_ID.eq(spaceId))
            .orderBy(PRICE_RULES.DAY_OF_WEEK, PRICE_RULES.START_TIME)
            .fetch()
            .map {
                PriceRuleRow(
                    id = it.get(PRICE_RULES.ID),
                    dayOfWeek = it.get(PRICE_RULES.DAY_OF_WEEK).toInt(),
                    unit = PriceUnit.valueOf(it.get(PRICE_RULES.UNIT)),
                    startTime = it.get(PRICE_RULES.START_TIME),
                    endTime = it.get(PRICE_RULES.END_TIME),
                    price = it.get(PRICE_RULES.PRICE),
                )
            }

    fun count(spaceId: Long): Int =
        dsl.fetchCount(PRICE_RULES, PRICE_RULES.SPACE_ID.eq(spaceId))

    fun findHourRule(spaceId: Long, dayOfWeek: Int, startTime: LocalTime, endTime: LocalTime): PriceLookup? =
        dsl.select(PRICE_RULES.ID, PRICE_RULES.PRICE)
            .from(PRICE_RULES)
            .where(PRICE_RULES.SPACE_ID.eq(spaceId))
            .and(PRICE_RULES.UNIT.eq(PriceUnit.HOUR.name))
            .and(PRICE_RULES.DAY_OF_WEEK.eq(dayOfWeek.toShort()))
            .and(PRICE_RULES.START_TIME.eq(startTime))
            .and(PRICE_RULES.END_TIME.eq(endTime))
            .fetchOne()
            ?.let { PriceLookup(it.value1(), it.value2()) }

    fun findDayRule(spaceId: Long, dayOfWeek: Int): PriceLookup? =
        dsl.select(PRICE_RULES.ID, PRICE_RULES.PRICE)
            .from(PRICE_RULES)
            .where(PRICE_RULES.SPACE_ID.eq(spaceId))
            .and(PRICE_RULES.UNIT.eq(PriceUnit.DAY.name))
            .and(PRICE_RULES.DAY_OF_WEEK.eq(dayOfWeek.toShort()))
            .fetchOne()
            ?.let { PriceLookup(it.value1(), it.value2()) }

    fun replace(spaceId: Long, rules: List<NewPriceRule>) {
        dsl.deleteFrom(PRICE_RULES).where(PRICE_RULES.SPACE_ID.eq(spaceId)).execute()
        if (rules.isEmpty()) return
        dsl.batch(
            rules.map { rule ->
                dsl.insertInto(PRICE_RULES)
                    .set(PRICE_RULES.SPACE_ID, spaceId)
                    .set(PRICE_RULES.DAY_OF_WEEK, rule.dayOfWeek.toShort())
                    .set(PRICE_RULES.UNIT, rule.unit.name)
                    .set(PRICE_RULES.START_TIME, rule.startTime)
                    .set(PRICE_RULES.END_TIME, rule.endTime)
                    .set(PRICE_RULES.PRICE, rule.price)
            },
        ).execute()
    }
}
