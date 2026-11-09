package com.lookation.repository

import com.lookation.domain.AttachmentOwnerType
import com.lookation.domain.ReservationStatus
import com.lookation.domain.SpaceSort
import com.lookation.domain.SpaceStatus
import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import com.lookation.jooq.tables.PriceRules.PRICE_RULES
import com.lookation.jooq.tables.Reservations.RESERVATIONS
import com.lookation.jooq.tables.Reviews.REVIEWS
import com.lookation.jooq.tables.Spaces.SPACES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.OffsetDateTime
import java.time.ZoneId

/**
 * 공간 검색·커서 전용 repository. [SpaceRepository]는 삽입·수정·이미지·시설을 담당하고,
 * 이 클래스는 검색 조건·정렬·커서와 가격/리뷰/썸네일 필드를 함께 조립한다.
 */
@Repository
class SpaceSearchRepository(private val dsl: DSLContext) {

    fun search(
        type: String?,
        keyword: String?,
        sido: String?,
        sigungu: String?,
        guests: Int?,
        minPrice: Long?,
        maxPrice: Long?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        sort: SpaceSort,
        cursor: SpaceCursor?,
        limit: Int,
    ): List<SpaceSummary> {
        var condition = SPACES.STATUS.eq(SpaceStatus.PUBLISHED.name).and(SPACES.DELETED_AT.isNull)
        if (type != null) {
            condition = condition.and(SPACES.SPACE_TYPE.eq(type))
        }
        if (keyword != null) {
            condition = condition.and(
                SPACES.NAME.containsIgnoreCase(keyword).or(SPACES.ADDRESS.containsIgnoreCase(keyword)),
            )
        }
        if (sido != null) {
            condition = condition.and(SPACES.SIDO.eq(sido))
        }
        if (sigungu != null) {
            condition = condition.and(SPACES.SIGUNGU.eq(sigungu))
        }
        if (guests != null) {
            condition = condition
                .and(SPACES.MIN_CAPACITY.le(guests.toShort()))
                .and(SPACES.MAX_CAPACITY.ge(guests.toShort()))
        }
        if (minPrice != null || maxPrice != null) {
            var priceCondition = PRICE_RULES.SPACE_ID.eq(SPACES.ID)
            if (minPrice != null) priceCondition = priceCondition.and(PRICE_RULES.PRICE.ge(minPrice))
            if (maxPrice != null) priceCondition = priceCondition.and(PRICE_RULES.PRICE.le(maxPrice))
            condition = condition.andExists(DSL.selectOne().from(PRICE_RULES).where(priceCondition))
        }
        if (from != null && to != null) {
            condition = condition.andExists(
                DSL.selectOne()
                    .from(PRICE_RULES)
                    .where(PRICE_RULES.SPACE_ID.eq(SPACES.ID))
                    .and(PRICE_RULES.DAY_OF_WEEK.eq(from.atZoneSameInstant(KST).dayOfWeek.value.toShort())),
            )
            condition = condition.andNotExists(
                DSL.selectOne()
                    .from(RESERVATIONS)
                    .where(RESERVATIONS.SPACE_ID.eq(SPACES.ID))
                    .and(RESERVATIONS.STATUS.`in`(
                        ReservationStatus.PENDING_APPROVAL.name,
                        ReservationStatus.PENDING_PAYMENT.name,
                        ReservationStatus.CONFIRMED.name,
                        ReservationStatus.COMPLETED.name,
                    ))
                    .and(
                        DSL.condition(
                            "tstzrange(reservations.start_at, reservations.end_at, '[)') && tstzrange({0}, {1}, '[)')",
                            from,
                            to,
                        ),
                    ),
            )
        }

        val minPriceField = DSL.field(
            DSL.select(DSL.min(PRICE_RULES.PRICE)).from(PRICE_RULES).where(PRICE_RULES.SPACE_ID.eq(SPACES.ID)),
        )
        val ratingField = DSL.field(
            DSL.select(DSL.avg(REVIEWS.RATING))
                .from(REVIEWS)
                .where(REVIEWS.SPACE_ID.eq(SPACES.ID))
                .and(REVIEWS.BLINDED_AT.isNull),
        )
        val reviewCountField = DSL.field(
            DSL.select(DSL.count())
                .from(REVIEWS)
                .where(REVIEWS.SPACE_ID.eq(SPACES.ID))
                .and(REVIEWS.BLINDED_AT.isNull),
        )
        val thumbnailField = DSL.field(
            DSL.select(ATTACHMENTS.STORAGE_KEY)
                .from(ATTACHMENTS)
                .where(ATTACHMENTS.OWNER_TYPE.eq(AttachmentOwnerType.SPACE.name))
                .and(ATTACHMENTS.OWNER_ID.eq(SPACES.ID))
                .and(ATTACHMENTS.CONTENT_TYPE.startsWith("image/"))
                .orderBy(ATTACHMENTS.SORT_ORDER, ATTACHMENTS.ID)
                .limit(1),
        )

        if (cursor != null) {
            condition = condition.and(cursorCondition(sort, cursor, minPriceField, ratingField))
        }

        val orderFields: List<org.jooq.OrderField<*>> = when (sort) {
            SpaceSort.PRICE_ASC -> listOf(minPriceField.asc().nullsLast(), SPACES.ID.desc())
            SpaceSort.PRICE_DESC -> listOf(minPriceField.desc().nullsLast(), SPACES.ID.desc())
            SpaceSort.RATING -> listOf(ratingField.desc().nullsLast(), SPACES.ID.desc())
            SpaceSort.LATEST -> listOf(SPACES.ID.desc())
        }

        return dsl.select(
            SPACES.ID,
            SPACES.NAME,
            SPACES.SHORT_INTRO,
            SPACES.SPACE_TYPE,
            SPACES.ADDRESS,
            SPACES.SIDO,
            SPACES.SIGUNGU,
            SPACES.MIN_CAPACITY,
            SPACES.MAX_CAPACITY,
            minPriceField,
            thumbnailField,
            ratingField,
            reviewCountField,
        )
            .from(SPACES)
            .where(condition)
            .orderBy(orderFields)
            .limit(limit)
            .fetch()
            .map {
                val thumbnailKey = it.value11()
                val rating = it.value12()
                SpaceSummary(
                    id = it.value1(),
                    name = it.value2(),
                    shortIntro = it.value3(),
                    spaceType = it.value4(),
                    address = it.value5(),
                    sido = it.value6(),
                    sigungu = it.value7(),
                    minCapacity = it.value8().toInt(),
                    maxCapacity = it.value9().toInt(),
                    minPrice = it.value10(),
                    thumbnailUrl = thumbnailKey?.let { key -> "/uploads/$key" },
                    rating = rating?.setScale(1, RoundingMode.HALF_UP)?.toDouble(),
                    reviewCount = it.value13() ?: 0,
                )
            }
    }

    private fun cursorCondition(
        sort: SpaceSort,
        cursor: SpaceCursor,
        minPriceField: org.jooq.Field<Long?>,
        ratingField: org.jooq.Field<BigDecimal?>,
    ): org.jooq.Condition = when (sort) {
        SpaceSort.PRICE_ASC -> {
            val price = cursor.value?.toLongOrNull()
            if (price == null) {
                minPriceField.isNull.and(SPACES.ID.lt(cursor.id))
            } else {
                minPriceField.gt(price)
                    .or(minPriceField.isNull)
                    .or(minPriceField.eq(price).and(SPACES.ID.lt(cursor.id)))
            }
        }
        SpaceSort.PRICE_DESC -> {
            val price = cursor.value?.toLongOrNull()
            if (price == null) {
                minPriceField.isNull.and(SPACES.ID.lt(cursor.id))
            } else {
                minPriceField.lt(price)
                    .or(minPriceField.isNull)
                    .or(minPriceField.eq(price).and(SPACES.ID.lt(cursor.id)))
            }
        }
        SpaceSort.RATING -> {
            val rating = cursor.value?.let(::BigDecimal)
            if (rating == null) {
                ratingField.isNull.and(SPACES.ID.lt(cursor.id))
            } else {
                ratingField.lt(rating)
                    .or(ratingField.isNull)
                    .or(ratingField.eq(rating).and(SPACES.ID.lt(cursor.id)))
            }
        }
        SpaceSort.LATEST -> SPACES.ID.lt(cursor.id)
    }
}

private val KST = ZoneId.of("Asia/Seoul")
