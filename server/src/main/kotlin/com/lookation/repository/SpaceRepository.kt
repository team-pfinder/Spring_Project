package com.lookation.repository

import com.lookation.domain.AttachmentOwnerType
import com.lookation.domain.FacilityCode
import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import com.lookation.jooq.tables.Inspections.INSPECTIONS
import com.lookation.jooq.tables.PriceRules.PRICE_RULES
import com.lookation.jooq.tables.Reservations.RESERVATIONS
import com.lookation.jooq.tables.Reviews.REVIEWS
import com.lookation.jooq.tables.SpaceFacilities.SPACE_FACILITIES
import com.lookation.domain.PriceUnit
import com.lookation.domain.SpaceSort
import com.lookation.domain.ReservationStatus
import com.lookation.domain.SpaceStatus
import com.lookation.jooq.tables.Spaces.SPACES
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.math.RoundingMode
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
    val sido: String?,
    val sigungu: String?,
    val spaceType: String,
    val minCapacity: Int,
    val maxCapacity: Int,
    val useHours: String?,
    val caution: String?,
    val contactEmail: String?,
    val contactPhone: String?,
    val websiteUrl: String?,
    val approvalRequired: Boolean,
    val status: SpaceStatus,
)

data class SpaceSummary(
    val id: Long,
    val name: String,
    val shortIntro: String?,
    val spaceType: String,
    val address: String,
    val sido: String?,
    val sigungu: String?,
    val minCapacity: Int,
    val maxCapacity: Int,
    val minPrice: Long?,
    val thumbnailUrl: String?,
    val rating: Double?,
    val reviewCount: Int,
)

/**
 * 검색 커서. [sort]에 따라 [value]의 의미가 달라진다.
 *
 * - `latest`: value 없음, id만 사용
 * - `price_asc`/`price_desc`: value는 마지막 항목의 최소 가격
 * - `rating`: value는 마지막 항목의 평균 평점
 */
data class SpaceCursor(
    val sort: SpaceSort,
    val value: String?,
    val id: Long,
)

data class RatingSummary(
    val rating: Double?,
    val reviewCount: Int,
)

data class InspectionRow(
    val id: Long,
    val spaceId: Long,
    val status: SpaceStatus,
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
        sido: String?,
        sigungu: String?,
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
            .set(SPACES.SIDO, sido)
            .set(SPACES.SIGUNGU, sigungu)
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
        sido: String?,
        sigungu: String?,
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
            .set(SPACES.SIDO, sido)
            .set(SPACES.SIGUNGU, sigungu)
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

    fun updateStatus(id: Long, expected: SpaceStatus, status: SpaceStatus) {
        dsl.update(SPACES)
            .set(SPACES.STATUS, status.name)
            .set(SPACES.UPDATED_AT, OffsetDateTime.now())
            .where(SPACES.ID.eq(id))
            .and(SPACES.STATUS.eq(expected.name))
            .execute()
    }

    fun findByHost(hostId: Long): List<SpaceRow> =
        findAllBy(SPACES.HOST_ID.eq(hostId).and(SPACES.DELETED_AT.isNull))

    fun ratingSummary(spaceId: Long): RatingSummary {
        val row = dsl.select(
            DSL.avg(REVIEWS.RATING),
            DSL.count(),
        )
            .from(REVIEWS)
            .where(REVIEWS.SPACE_ID.eq(spaceId))
            .and(REVIEWS.BLINDED_AT.isNull)
            .fetchOne()
        val rating = row?.value1()
        return RatingSummary(
            rating = rating?.setScale(1, RoundingMode.HALF_UP)?.toDouble(),
            reviewCount = row?.value2() ?: 0,
        )
    }

    fun thumbnailKey(spaceId: Long): String? =
        dsl.select(ATTACHMENTS.STORAGE_KEY)
            .from(ATTACHMENTS)
            .where(ATTACHMENTS.OWNER_TYPE.eq(AttachmentOwnerType.SPACE.name))
            .and(ATTACHMENTS.OWNER_ID.eq(spaceId))
            .and(ATTACHMENTS.CONTENT_TYPE.startsWith("image/"))
            .orderBy(ATTACHMENTS.SORT_ORDER, ATTACHMENTS.ID)
            .limit(1)
            .fetchOne(ATTACHMENTS.STORAGE_KEY)

    fun replaceFacilities(spaceId: Long, facilities: Set<FacilityCode>) {
        dsl.deleteFrom(SPACE_FACILITIES).where(SPACE_FACILITIES.SPACE_ID.eq(spaceId)).execute()
        if (facilities.isEmpty()) return
        dsl.batch(
            facilities.map { code ->
                dsl.insertInto(SPACE_FACILITIES)
                    .set(SPACE_FACILITIES.SPACE_ID, spaceId)
                    .set(SPACE_FACILITIES.FACILITY_CODE, code.name)
            },
        ).execute()
    }

    fun facilities(spaceId: Long): List<FacilityCode> =
        dsl.select(SPACE_FACILITIES.FACILITY_CODE)
            .from(SPACE_FACILITIES)
            .where(SPACE_FACILITIES.SPACE_ID.eq(spaceId))
            .fetch(SPACE_FACILITIES.FACILITY_CODE)
            .map { FacilityCode.valueOf(it) }

    fun imageKeys(spaceId: Long): List<String> =
        dsl.select(ATTACHMENTS.STORAGE_KEY)
            .from(ATTACHMENTS)
            .where(ATTACHMENTS.OWNER_TYPE.eq(AttachmentOwnerType.SPACE.name))
            .and(ATTACHMENTS.OWNER_ID.eq(spaceId))
            .and(ATTACHMENTS.CONTENT_TYPE.startsWith("image/"))
            .orderBy(ATTACHMENTS.SORT_ORDER, ATTACHMENTS.ID)
            .fetch(ATTACHMENTS.STORAGE_KEY)

    fun countImages(spaceId: Long): Int =
        dsl.fetchCount(
            ATTACHMENTS,
            ATTACHMENTS.OWNER_TYPE.eq(AttachmentOwnerType.SPACE.name)
                .and(ATTACHMENTS.OWNER_ID.eq(spaceId))
                .and(ATTACHMENTS.CONTENT_TYPE.startsWith("image/")),
        )

    fun findById(id: Long): SpaceRow? = findBy(SPACES.ID.eq(id).and(SPACES.DELETED_AT.isNull))

    fun hasActiveReservations(spaceId: Long): Boolean =
        dsl.fetchCount(
            RESERVATIONS,
            RESERVATIONS.SPACE_ID.eq(spaceId)
                .and(RESERVATIONS.STATUS.`in`(
                    ReservationStatus.PENDING_APPROVAL.name,
                    ReservationStatus.PENDING_PAYMENT.name,
                    ReservationStatus.CONFIRMED.name,
                )),
        ) > 0

    fun softDelete(id: Long) {
        dsl.update(SPACES)
            .set(SPACES.STATUS, SpaceStatus.CLOSED.name)
            .set(SPACES.DELETED_AT, OffsetDateTime.now())
            .set(SPACES.UPDATED_AT, OffsetDateTime.now())
            .where(SPACES.ID.eq(id))
            .and(SPACES.DELETED_AT.isNull)
            .execute()
    }

    fun insertInspection(spaceId: Long, status: SpaceStatus, reason: String?, adminId: Long?) {
        dsl.insertInto(INSPECTIONS)
            .set(INSPECTIONS.SPACE_ID, spaceId)
            .set(INSPECTIONS.STATUS, status.name)
            .set(INSPECTIONS.REASON, reason)
            .set(INSPECTIONS.ADMIN_ID, adminId)
            .set(INSPECTIONS.PROCESSED_AT, if (adminId == null) null else OffsetDateTime.now())
            .execute()
    }

    fun pendingInspections(): List<InspectionRow> =
        dsl.selectFrom(INSPECTIONS)
            .where(INSPECTIONS.STATUS.eq(SpaceStatus.PENDING_INSPECTION.name))
            .orderBy(INSPECTIONS.REQUESTED_AT)
            .fetch()
            .map {
                InspectionRow(
                    id = it.get(INSPECTIONS.ID),
                    spaceId = it.get(INSPECTIONS.SPACE_ID),
                    status = SpaceStatus.valueOf(it.get(INSPECTIONS.STATUS)),
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
        sido = get(SPACES.SIDO),
        sigungu = get(SPACES.SIGUNGU),
        spaceType = get(SPACES.SPACE_TYPE),
        minCapacity = get(SPACES.MIN_CAPACITY).toInt(),
        maxCapacity = get(SPACES.MAX_CAPACITY).toInt(),
        useHours = get(SPACES.USE_HOURS),
        caution = get(SPACES.CAUTION),
        contactEmail = get(SPACES.CONTACT_EMAIL),
        contactPhone = get(SPACES.CONTACT_PHONE),
        websiteUrl = get(SPACES.WEBSITE_URL),
        approvalRequired = get(SPACES.APPROVAL_REQUIRED),
        status = SpaceStatus.valueOf(get(SPACES.STATUS)),
    )
}

private val KST = java.time.ZoneId.of("Asia/Seoul")
