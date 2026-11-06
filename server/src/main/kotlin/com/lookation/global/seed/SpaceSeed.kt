package com.lookation.global.seed

import com.lookation.domain.InspectionStatus
import com.lookation.domain.SpaceStatus
import com.lookation.domain.SpaceType
import com.lookation.jooq.tables.Inspections.INSPECTIONS
import com.lookation.jooq.tables.PriceRules.PRICE_RULES
import com.lookation.jooq.tables.SpaceFacilities.SPACE_FACILITIES
import com.lookation.jooq.tables.Spaces.SPACES
import org.jooq.DSLContext
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime
import java.time.OffsetDateTime

/**
 * Seeds six spaces, one per [SpaceType] and one per [SpaceStatus], plus their facilities,
 * hourly/daily price rules and inspection history (all four [InspectionStatus] values).
 * Images are owned by the follow-up attachment seed.
 */
@Component
@Order(SpaceSeed.ORDER)
class SpaceSeed(
    private val dsl: DSLContext,
    private val seedAdmin: SeedAdmin,
) : SeedStep {

    override val name = "공간·편의시설·가격·검수"

    @Transactional
    override fun seed(): SeedResult {
        var inserted = 0
        var skipped = 0

        SPACES_SEED.forEach { seed ->
            val hostId = dsl.accountId(seed.hostEmail)
            val existing = dsl.fetchCount(SPACES, SPACES.NAME.eq(seed.name)) > 0
            if (existing) {
                skipped++
                return@forEach
            }

            val spaceId = dsl.insertInto(SPACES)
                .set(SPACES.HOST_ID, hostId)
                .set(SPACES.NAME, seed.name)
                .set(SPACES.SHORT_INTRO, seed.shortIntro)
                .set(SPACES.DESCRIPTION, seed.description)
                .set(SPACES.ADDRESS, seed.address)
                .set(SPACES.ADDRESS_DETAIL, seed.addressDetail)
                .set(SPACES.SIDO, seed.sido)
                .set(SPACES.SIGUNGU, seed.sigungu)
                .set(SPACES.SPACE_TYPE, seed.type.name)
                .set(SPACES.MIN_CAPACITY, seed.minCapacity.toShort())
                .set(SPACES.MAX_CAPACITY, seed.maxCapacity.toShort())
                .set(SPACES.USE_HOURS, USE_HOURS)
                .set(SPACES.CAUTION, "시드 데이터입니다.")
                .set(SPACES.CONTACT_EMAIL, seed.hostEmail)
                .set(SPACES.CONTACT_PHONE, "010-0000-0000")
                .set(SPACES.APPROVAL_REQUIRED, seed.approvalRequired)
                .set(SPACES.STATUS, seed.status.name)
                .returningResult(SPACES.ID)
                .fetchOne()!!
                .value1()

            seed.facilities.forEach { code ->
                dsl.insertInto(SPACE_FACILITIES)
                    .set(SPACE_FACILITIES.SPACE_ID, spaceId)
                    .set(SPACE_FACILITIES.FACILITY_CODE, code)
                    .execute()
            }

            (1..7).forEach { day ->
                dsl.insertInto(PRICE_RULES)
                    .set(PRICE_RULES.SPACE_ID, spaceId)
                    .set(PRICE_RULES.DAY_OF_WEEK, day.toShort())
                    .set(PRICE_RULES.UNIT, "HOUR")
                    .set(PRICE_RULES.START_TIME, HOUR_START)
                    .set(PRICE_RULES.END_TIME, HOUR_END)
                    .set(PRICE_RULES.PRICE, seed.hourPrice)
                    .execute()
                dsl.insertInto(PRICE_RULES)
                    .set(PRICE_RULES.SPACE_ID, spaceId)
                    .set(PRICE_RULES.DAY_OF_WEEK, day.toShort())
                    .set(PRICE_RULES.UNIT, "DAY")
                    .set(PRICE_RULES.PRICE, seed.hourPrice * 4)
                    .execute()
            }

            seed.inspection?.let { inspection ->
                dsl.insertInto(INSPECTIONS)
                    .set(INSPECTIONS.SPACE_ID, spaceId)
                    .set(INSPECTIONS.STATUS, inspection.name)
                    .set(INSPECTIONS.REASON, if (inspection == InspectionStatus.INSPECTION_REJECTED) "시드 반려 사유" else null)
                    .set(INSPECTIONS.ADMIN_ID, seedAdmin.id())
                    .set(INSPECTIONS.REQUESTED_AT, OffsetDateTime.now().minusDays(30))
                    .set(
                        INSPECTIONS.PROCESSED_AT,
                        if (inspection == InspectionStatus.PENDING_INSPECTION) null else OffsetDateTime.now().minusDays(29),
                    )
                    .execute()
            }

            inserted++
        }

        return SeedResult(inserted = inserted, skipped = skipped)
    }

    private data class SeedSpace(
        val name: String,
        val shortIntro: String,
        val description: String,
        val address: String,
        val addressDetail: String,
        val sido: String,
        val sigungu: String,
        val hostEmail: String,
        val type: SpaceType,
        val status: SpaceStatus,
        val minCapacity: Int,
        val maxCapacity: Int,
        val approvalRequired: Boolean,
        val hourPrice: Long,
        val facilities: List<String>,
        val inspection: InspectionStatus?,
    )

    private companion object {
        const val ORDER = 30
        const val USE_HOURS = "18:00~22:00"
        val HOUR_START: LocalTime = LocalTime.of(18, 0)
        val HOUR_END: LocalTime = LocalTime.of(22, 0)

        val SPACES_SEED = listOf(
            SeedSpace(
                name = "시드 파티룸",
                shortIntro = "강남 한복판 파티룸",
                description = "시드 데이터용 파티룸입니다.",
                address = "서울특별시 강남구 테헤란로 1",
                addressDetail = "1층",
                sido = "서울",
                sigungu = "강남구",
                hostEmail = "seed-host-1@lookation.local",
                type = SpaceType.PARTY_ROOM,
                status = SpaceStatus.PUBLISHED,
                minCapacity = 10,
                maxCapacity = 30,
                approvalRequired = false,
                hourPrice = 60000,
                facilities = listOf("PARKING", "WIFI", "PROJECTOR", "AIR_CONDITIONER"),
                inspection = InspectionStatus.PUBLISHED,
            ),
            SeedSpace(
                name = "시드 한강 루프탑",
                shortIntro = "여의도 한강이 보이는 루프탑",
                description = "검색 필터와 정렬을 검증하기 위한 두 번째 게시 공간입니다.",
                address = "서울특별시 영등포구 여의대로 10",
                addressDetail = "옥상",
                sido = "서울",
                sigungu = "영등포구",
                hostEmail = "seed-host-1@lookation.local",
                type = SpaceType.ROOFTOP,
                status = SpaceStatus.PUBLISHED,
                minCapacity = 4,
                maxCapacity = 12,
                approvalRequired = false,
                hourPrice = 45000,
                facilities = listOf("WIFI", "SOUND", "TABLE"),
                inspection = InspectionStatus.PUBLISHED,
            ),
            SeedSpace(
                name = "시드 부산 파티룸",
                shortIntro = "해운대 바다 전망 파티룸",
                description = "승인제 예약을 검증하기 위한 게시 공간입니다.",
                address = "부산광역시 해운대구 해운대로 20",
                addressDetail = "12층",
                sido = "부산",
                sigungu = "해운대구",
                hostEmail = "seed-host-2@lookation.local",
                type = SpaceType.PARTY_ROOM,
                status = SpaceStatus.PUBLISHED,
                minCapacity = 6,
                maxCapacity = 24,
                approvalRequired = true,
                hourPrice = 70000,
                facilities = listOf("PARKING", "WIFI", "PROJECTOR", "REFRIGERATOR"),
                inspection = InspectionStatus.PUBLISHED,
            ),
            SeedSpace(
                name = "시드 판교 워크샵",
                shortIntro = "판교 스타트업 워크샵 공간",
                description = "낮은 가격대와 워크샵 유형 필터를 검증하는 게시 공간입니다.",
                address = "경기도 성남시 분당구 판교역로 30",
                addressDetail = "5층",
                sido = "경기",
                sigungu = "성남시",
                hostEmail = "seed-host-2@lookation.local",
                type = SpaceType.WORKSHOP,
                status = SpaceStatus.PUBLISHED,
                minCapacity = 2,
                maxCapacity = 10,
                approvalRequired = false,
                hourPrice = 25000,
                facilities = listOf("WIFI", "PROJECTOR", "KITCHEN", "CHAIR"),
                inspection = InspectionStatus.PUBLISHED,
            ),
            SeedSpace(
                name = "시드 빈 공간",
                shortIntro = "마포 빈 공간",
                description = "시드 데이터용 빈 공간입니다.",
                address = "서울특별시 마포구 양화로 2",
                addressDetail = "2층",
                sido = "서울",
                sigungu = "마포구",
                hostEmail = "seed-host-1@lookation.local",
                type = SpaceType.EMPTY_SPACE,
                status = SpaceStatus.DRAFT,
                minCapacity = 1,
                maxCapacity = 10,
                approvalRequired = false,
                hourPrice = 30000,
                facilities = listOf("WIFI", "TABLE", "CHAIR"),
                inspection = null,
            ),
            SeedSpace(
                name = "시드 루프탑",
                shortIntro = "용산 루프탑",
                description = "시드 데이터용 루프탑입니다.",
                address = "서울특별시 용산구 한남대로 3",
                addressDetail = "옥상",
                sido = "서울",
                sigungu = "용산구",
                hostEmail = "seed-host-2@lookation.local",
                type = SpaceType.ROOFTOP,
                status = SpaceStatus.PENDING_INSPECTION,
                minCapacity = 5,
                maxCapacity = 20,
                approvalRequired = true,
                hourPrice = 80000,
                facilities = listOf("PARKING", "SOUND", "BATHROOM"),
                inspection = InspectionStatus.PENDING_INSPECTION,
            ),
            SeedSpace(
                name = "시드 브라이덜샤워",
                shortIntro = "송파 브라이덜샤워",
                description = "시드 데이터용 브라이덜샤워 공간입니다.",
                address = "서울특별시 송파구 올림픽로 4",
                addressDetail = "3층",
                sido = "서울",
                sigungu = "송파구",
                hostEmail = "seed-host-2@lookation.local",
                type = SpaceType.BRIDAL_SHOWER,
                status = SpaceStatus.INSPECTION_REJECTED,
                minCapacity = 2,
                maxCapacity = 8,
                approvalRequired = false,
                hourPrice = 40000,
                facilities = listOf("AIR_CONDITIONER", "REFRIGERATOR", "MICROWAVE"),
                inspection = InspectionStatus.INSPECTION_REJECTED,
            ),
            SeedSpace(
                name = "시드 클럽",
                shortIntro = "마포 클럽",
                description = "시드 데이터용 클럽입니다.",
                address = "서울특별시 마포구 어울마당로 5",
                addressDetail = "지하 1층",
                sido = "서울",
                sigungu = "마포구",
                hostEmail = "seed-host-1@lookation.local",
                type = SpaceType.CLUB,
                status = SpaceStatus.SUSPENDED,
                minCapacity = 20,
                maxCapacity = 80,
                approvalRequired = true,
                hourPrice = 90000,
                facilities = listOf("SOUND", "PROJECTOR", "ELEVATOR"),
                inspection = InspectionStatus.SUSPENDED,
            ),
            SeedSpace(
                name = "시드 워크샵",
                shortIntro = "성남 워크샵",
                description = "시드 데이터용 워크샵입니다.",
                address = "경기도 성남시 분당구 판교로 6",
                addressDetail = "4층",
                sido = "경기",
                sigungu = "성남시",
                hostEmail = "seed-host-2@lookation.local",
                type = SpaceType.WORKSHOP,
                status = SpaceStatus.CLOSED,
                minCapacity = 4,
                maxCapacity = 16,
                approvalRequired = false,
                hourPrice = 35000,
                facilities = listOf("WIFI", "KITCHEN", "TABLE"),
                inspection = null,
            ),
        )
    }
}
