package com.lookation.space.infrastructure

import com.lookation.jooq.tables.PriceRules.PRICE_RULES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalTime

data class PriceRuleRow(
    val id: Long,
    val dayOfWeek: Int,
    val unit: String,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val price: Long,
)

data class NewPriceRule(
    val dayOfWeek: Int,
    val unit: String,
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
                    unit = it.get(PRICE_RULES.UNIT),
                    startTime = it.get(PRICE_RULES.START_TIME),
                    endTime = it.get(PRICE_RULES.END_TIME),
                    price = it.get(PRICE_RULES.PRICE),
                )
            }

    fun count(spaceId: Long): Int =
        dsl.fetchCount(PRICE_RULES, PRICE_RULES.SPACE_ID.eq(spaceId))

    fun replace(spaceId: Long, rules: List<NewPriceRule>) {
        dsl.deleteFrom(PRICE_RULES).where(PRICE_RULES.SPACE_ID.eq(spaceId)).execute()
        if (rules.isEmpty()) return
        dsl.batch(
            rules.map { rule ->
                dsl.insertInto(PRICE_RULES)
                    .set(PRICE_RULES.SPACE_ID, spaceId)
                    .set(PRICE_RULES.DAY_OF_WEEK, rule.dayOfWeek.toShort())
                    .set(PRICE_RULES.UNIT, rule.unit)
                    .set(PRICE_RULES.START_TIME, rule.startTime)
                    .set(PRICE_RULES.END_TIME, rule.endTime)
                    .set(PRICE_RULES.PRICE, rule.price)
            },
        ).execute()
    }
}
