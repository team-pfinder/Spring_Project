package com.lookation.space.infrastructure

import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import com.lookation.jooq.tables.Inspections.INSPECTIONS
import com.lookation.jooq.tables.PriceRules.PRICE_RULES
import com.lookation.jooq.tables.Reservations.RESERVATIONS
import com.lookation.jooq.tables.SpaceFacilities.SPACE_FACILITIES
import com.lookation.jooq.tables.Spaces.SPACES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.time.LocalTime
import java.time.OffsetDateTime

data class SpaceRow(
    val id: Long,
    val hostId: Long,
    val name: String,
    val shortIntro: String?,
    val description: String?,
    val address: String,
    val addressDetail: String?,
    val spaceType: String,
    val minCapacity: Int,
    val maxCapacity: Int,
    val useHours: String?,
    val caution: String?,
    val contactEmail: String?,
    val contactPhone: String?,
    val websiteUrl: String?,
    val approvalRequired: Boolean,
    val status: String,
)

data class PriceLookup(
    val id: Long,
    val price: Long,
)

data class SpaceSummary(
    val id: Long,
    val name: String,
    val shortIntro: String?,
    val spaceType: String,
    val address: String,
    val minCapacity: Int,
    val maxCapacity: Int,
    val minPrice: Long?,
)

data class InspectionRow(
    val id: Long,
    val spaceId: Long,
    val status: String,
    val reason: String?,
    val requestedAt: OffsetDateTime,
)

@Repository
class SpaceRepository(private val dsl: DSLContext) {

    fun insert(
        hostId: Long,
        name: String,
        shortIntro: String?,
        description: String?,
        address: String,
        addressDetail: String?,
        spaceType: String,
        minCapacity: Int,
        maxCapacity: Int,
        useHours: String?,
        caution: String?,
        contactEmail: String?,
        contactPhone: String?,
        websiteUrl: String?,
        approvalRequired: Boolean,
    ): Long =
        dsl.insertInto(SPACES)
            .set(SPACES.HOST_ID, hostId)
            .set(SPACES.NAME, name)
            .set(SPACES.SHORT_INTRO, shortIntro)
            .set(SPACES.DESCRIPTION, description)
            .set(SPACES.ADDRESS, address)
            .set(SPACES.ADDRESS_DETAIL, addressDetail)
            .set(SPACES.SPACE_TYPE, spaceType)
            .set(SPACES.MIN_CAPACITY, minCapacity.toShort())
            .set(SPACES.MAX_CAPACITY, maxCapacity.toShort())
            .set(SPACES.USE_HOURS, useHours)
            .set(SPACES.CAUTION, caution)
            .set(SPACES.CONTACT_EMAIL, contactEmail)
            .set(SPACES.CONTACT_PHONE, contactPhone)
            .set(SPACES.WEBSITE_URL, websiteUrl)
            .set(SPACES.APPROVAL_REQUIRED, approvalRequired)
            .returningResult(SPACES.ID)
            .fetchOne()!!
            .value1()

    fun update(
        id: Long,
        name: String,
        shortIntro: String?,
        description: String?,
        address: String,
        addressDetail: String?,
        spaceType: String,
        minCapacity: Int,
        maxCapacity: Int,
        useHours: String?,
        caution: String?,
        contactEmail: String?,
        contactPhone: String?,
        websiteUrl: String?,
        approvalRequired: Boolean,
    ) {
        dsl.update(SPACES)
            .set(SPACES.NAME, name)
            .set(SPACES.SHORT_INTRO, shortIntro)
            .set(SPACES.DESCRIPTION, description)
            .set(SPACES.ADDRESS, address)
            .set(SPACES.ADDRESS_DETAIL, addressDetail)
            .set(SPACES.SPACE_TYPE, spaceType)
            .set(SPACES.MIN_CAPACITY, minCapacity.toShort())
            .set(SPACES.MAX_CAPACITY, maxCapacity.toShort())
            .set(SPACES.USE_HOURS, useHours)
            .set(SPACES.CAUTION, caution)
            .set(SPACES.CONTACT_EMAIL, contactEmail)
            .set(SPACES.CONTACT_PHONE, contactPhone)
            .set(SPACES.WEBSITE_URL, websiteUrl)
            .set(SPACES.APPROVAL_REQUIRED, approvalRequired)
            .set(SPACES.UPDATED_AT, OffsetDateTime.now())
            .where(SPACES.ID.eq(id))
            .execute()
    }

    fun updateStatus(id: Long, expected: String, status: String) {
        dsl.update(SPACES)
            .set(SPACES.STATUS, status)
            .set(SPACES.UPDATED_AT, OffsetDateTime.now())
            .where(SPACES.ID.eq(id))
            .and(SPACES.STATUS.eq(expected))
            .execute()
    }

    fun findByHost(hostId: Long): List<SpaceRow> =
        findAllBy(SPACES.HOST_ID.eq(hostId).and(SPACES.DELETED_AT.isNull))

    fun search(
        type: String?,
        keyword: String?,
        guests: Int?,
        minPrice: Long?,
        maxPrice: Long?,
        from: OffsetDateTime?,
        to: OffsetDateTime?,
        cursor: Long?,
        limit: Int,
    ): List<SpaceSummary> {
        var condition = SPACES.STATUS.eq("PUBLISHED").and(SPACES.DELETED_AT.isNull)
        if (type != null) {
            condition = condition.and(SPACES.SPACE_TYPE.eq(type))
        }
        if (keyword != null) {
            condition = condition.and(
                SPACES.NAME.containsIgnoreCase(keyword).or(SPACES.ADDRESS.containsIgnoreCase(keyword)),
            )
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
                    .and(RESERVATIONS.STATUS.`in`("PENDING_APPROVAL", "PENDING_PAYMENT", "CONFIRMED"))
                    .and(
                        DSL.condition(
                            "tstzrange(reservations.start_at, reservations.end_at, '[)') && tstzrange({0}, {1}, '[)')",
                            from,
                            to,
                        ),
                    ),
            )
        }
        if (cursor != null) {
            condition = condition.and(SPACES.ID.lt(cursor))
        }

        val minPriceField = DSL.field(
            DSL.select(DSL.min(PRICE_RULES.PRICE)).from(PRICE_RULES).where(PRICE_RULES.SPACE_ID.eq(SPACES.ID)),
        )

        return dsl.select(
            SPACES.ID,
            SPACES.NAME,
            SPACES.SHORT_INTRO,
            SPACES.SPACE_TYPE,
            SPACES.ADDRESS,
            SPACES.MIN_CAPACITY,
            SPACES.MAX_CAPACITY,
            minPriceField,
        )
            .from(SPACES)
            .where(condition)
            .orderBy(SPACES.ID.desc())
            .limit(limit)
            .fetch()
            .map {
                SpaceSummary(
                    id = it.value1(),
                    name = it.value2(),
                    shortIntro = it.value3(),
                    spaceType = it.value4(),
                    address = it.value5(),
                    minCapacity = it.value6().toInt(),
                    maxCapacity = it.value7().toInt(),
                    minPrice = it.value8(),
                )
            }
    }

    fun replaceFacilities(spaceId: Long, facilities: Set<String>) {
        dsl.deleteFrom(SPACE_FACILITIES).where(SPACE_FACILITIES.SPACE_ID.eq(spaceId)).execute()
        if (facilities.isEmpty()) return
        dsl.batch(
            facilities.map { code ->
                dsl.insertInto(SPACE_FACILITIES)
                    .set(SPACE_FACILITIES.SPACE_ID, spaceId)
                    .set(SPACE_FACILITIES.FACILITY_CODE, code)
            },
        ).execute()
    }

    fun facilities(spaceId: Long): List<String> =
        dsl.select(SPACE_FACILITIES.FACILITY_CODE)
            .from(SPACE_FACILITIES)
            .where(SPACE_FACILITIES.SPACE_ID.eq(spaceId))
            .fetch(SPACE_FACILITIES.FACILITY_CODE)

    fun imageKeys(spaceId: Long): List<String> =
        dsl.select(ATTACHMENTS.STORAGE_KEY)
            .from(ATTACHMENTS)
            .where(ATTACHMENTS.OWNER_TYPE.eq("SPACE"))
            .and(ATTACHMENTS.OWNER_ID.eq(spaceId))
            .and(ATTACHMENTS.CONTENT_TYPE.startsWith("image/"))
            .orderBy(ATTACHMENTS.ID)
            .fetch(ATTACHMENTS.STORAGE_KEY)

    fun countImages(spaceId: Long): Int =
        dsl.fetchCount(
            ATTACHMENTS,
            ATTACHMENTS.OWNER_TYPE.eq("SPACE")
                .and(ATTACHMENTS.OWNER_ID.eq(spaceId))
                .and(ATTACHMENTS.CONTENT_TYPE.startsWith("image/")),
        )

    fun findHourRule(spaceId: Long, dayOfWeek: Int, startTime: LocalTime, endTime: LocalTime): PriceLookup? =
        dsl.select(PRICE_RULES.ID, PRICE_RULES.PRICE)
            .from(PRICE_RULES)
            .where(PRICE_RULES.SPACE_ID.eq(spaceId))
            .and(PRICE_RULES.UNIT.eq("HOUR"))
            .and(PRICE_RULES.DAY_OF_WEEK.eq(dayOfWeek.toShort()))
            .and(PRICE_RULES.START_TIME.eq(startTime))
            .and(PRICE_RULES.END_TIME.eq(endTime))
            .fetchOne()
            ?.let { PriceLookup(it.value1(), it.value2()) }

    fun findDayRule(spaceId: Long, dayOfWeek: Int): PriceLookup? =
        dsl.select(PRICE_RULES.ID, PRICE_RULES.PRICE)
            .from(PRICE_RULES)
            .where(PRICE_RULES.SPACE_ID.eq(spaceId))
            .and(PRICE_RULES.UNIT.eq("DAY"))
            .and(PRICE_RULES.DAY_OF_WEEK.eq(dayOfWeek.toShort()))
            .fetchOne()
            ?.let { PriceLookup(it.value1(), it.value2()) }

    fun findById(id: Long): SpaceRow? = findBy(SPACES.ID.eq(id).and(SPACES.DELETED_AT.isNull))

    fun hasActiveReservations(spaceId: Long): Boolean =
        dsl.fetchCount(
            RESERVATIONS,
            RESERVATIONS.SPACE_ID.eq(spaceId)
                .and(RESERVATIONS.STATUS.`in`("PENDING_APPROVAL", "PENDING_PAYMENT", "CONFIRMED")),
        ) > 0

    fun softDelete(id: Long) {
        dsl.update(SPACES)
            .set(SPACES.STATUS, "CLOSED")
            .set(SPACES.DELETED_AT, OffsetDateTime.now())
            .set(SPACES.UPDATED_AT, OffsetDateTime.now())
            .where(SPACES.ID.eq(id))
            .and(SPACES.DELETED_AT.isNull)
            .execute()
    }

    fun insertInspection(spaceId: Long, status: String, reason: String?, adminId: Long?) {
        dsl.insertInto(INSPECTIONS)
            .set(INSPECTIONS.SPACE_ID, spaceId)
            .set(INSPECTIONS.STATUS, status)
            .set(INSPECTIONS.REASON, reason)
            .set(INSPECTIONS.ADMIN_ID, adminId)
            .set(INSPECTIONS.PROCESSED_AT, if (adminId == null) null else OffsetDateTime.now())
            .execute()
    }

    fun pendingInspections(): List<InspectionRow> =
        dsl.selectFrom(INSPECTIONS)
            .where(INSPECTIONS.STATUS.eq("PENDING_INSPECTION"))
            .orderBy(INSPECTIONS.REQUESTED_AT)
            .fetch()
            .map {
                InspectionRow(
                    id = it.get(INSPECTIONS.ID),
                    spaceId = it.get(INSPECTIONS.SPACE_ID),
                    status = it.get(INSPECTIONS.STATUS),
                    reason = it.get(INSPECTIONS.REASON),
                    requestedAt = it.get(INSPECTIONS.REQUESTED_AT),
                )
            }

    private fun findBy(condition: org.jooq.Condition): SpaceRow? =
        dsl.selectFrom(SPACES).where(condition).fetchOne()?.toRow()

    private fun findAllBy(condition: org.jooq.Condition): List<SpaceRow> =
        dsl.selectFrom(SPACES).where(condition).fetch().map { it.toRow() }

    private fun org.jooq.Record.toRow(): SpaceRow = SpaceRow(
        id = get(SPACES.ID),
        hostId = get(SPACES.HOST_ID),
        name = get(SPACES.NAME),
        shortIntro = get(SPACES.SHORT_INTRO),
        description = get(SPACES.DESCRIPTION),
        address = get(SPACES.ADDRESS),
        addressDetail = get(SPACES.ADDRESS_DETAIL),
        spaceType = get(SPACES.SPACE_TYPE),
        minCapacity = get(SPACES.MIN_CAPACITY).toInt(),
        maxCapacity = get(SPACES.MAX_CAPACITY).toInt(),
        useHours = get(SPACES.USE_HOURS),
        caution = get(SPACES.CAUTION),
        contactEmail = get(SPACES.CONTACT_EMAIL),
        contactPhone = get(SPACES.CONTACT_PHONE),
        websiteUrl = get(SPACES.WEBSITE_URL),
        approvalRequired = get(SPACES.APPROVAL_REQUIRED),
        status = get(SPACES.STATUS),
    )
}

private val KST = java.time.ZoneId.of("Asia/Seoul")
