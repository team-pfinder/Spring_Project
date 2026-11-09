package com.lookation.dto

import com.lookation.domain.FacilityCode
import com.lookation.domain.PriceUnit
import com.lookation.domain.SpaceStatus
import com.lookation.domain.SpaceType
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalTime
import java.time.OffsetDateTime

@Schema(description = "공간 상세")
data class SpaceDto(
    @field:Schema(description = "공간 식별자입니다.", example = "42")
    val id: Long,

    @field:Schema(description = "공간 이름입니다.", example = "루프탑 파티룸 한강")
    val name: String,

    @field:Schema(description = "한 줄 소개. 등록하지 않았으면 null입니다.", example = "한강이 보이는 루프탑 단독 파티룸")
    val shortIntro: String?,

    @field:Schema(description = "상세 설명. 등록하지 않았으면 null입니다.", example = "단독으로 사용하는 루프탑 공간으로 최대 20명까지 이용할 수 있습니다.")
    val description: String?,

    @field:Schema(description = "기본 주소입니다.", example = "서울 마포구 양화로 45")
    val address: String,

    @field:Schema(description = "상세 주소. 등록하지 않았으면 null입니다.", example = "3층")
    val addressDetail: String?,

    @field:Schema(description = "시/도 지역. 등록하지 않았으면 null입니다.", example = "서울")
    val sido: String?,

    @field:Schema(description = "시/군/구 지역. 등록하지 않았으면 null입니다.", example = "마포구")
    val sigungu: String?,

    @field:Schema(
        description = "공간 유형. `PARTY_ROOM`, `EMPTY_SPACE`, `ROOFTOP`, `BRIDAL_SHOWER`, `CLUB`, `WORKSHOP` 중 하나입니다.",
        example = "PARTY_ROOM",
    )
    val spaceType: SpaceType,

    @field:Schema(description = "최소 수용 인원입니다.", example = "4")
    val minCapacity: Int,

    @field:Schema(description = "최대 수용 인원입니다.", example = "20")
    val maxCapacity: Int,

    @field:Schema(description = "이용 가능 시간 안내. 등록하지 않았으면 null입니다.", example = "10:00~22:00")
    val useHours: String?,

    @field:Schema(description = "주의 사항. 등록하지 않았으면 null입니다.", example = "실내 흡연은 금지입니다.")
    val caution: String?,

    @field:Schema(description = "문의용 이메일. 등록하지 않았으면 null입니다.", example = "host@lookation.local")
    val contactEmail: String?,

    @field:Schema(description = "문의용 전화번호. 등록하지 않았으면 null입니다.", example = "02-1234-5678")
    val contactPhone: String?,

    @field:Schema(description = "공간 소개 웹사이트 주소. 등록하지 않았으면 null입니다.", example = "https://lookation.local/spaces/42")
    val websiteUrl: String?,

    @field:Schema(
        description = "공간 상태. `DRAFT`, `PENDING_INSPECTION`, `INSPECTION_REJECTED`, `PUBLISHED`, `SUSPENDED`, `CLOSED` 중 하나입니다.",
        example = "PUBLISHED",
    )
    val status: SpaceStatus,

    @field:ArraySchema(
        schema = Schema(example = "WIFI"),
        arraySchema = Schema(description = "제공 시설 코드 목록입니다."),
    )
    val facilities: List<FacilityCode>,

    @field:ArraySchema(
        schema = Schema(example = "/uploads/spaces/1.jpg"),
        arraySchema = Schema(description = "공간 이미지 경로 목록입니다."),
    )
    val images: List<String>,

    @field:Schema(description = "예약 승인 필요 여부입니다.", example = "false")
    val approvalRequired: Boolean,

    @field:Schema(description = "공개 리뷰 평점 평균. 리뷰가 없으면 null입니다.", example = "4.5")
    val rating: Double?,

    @field:Schema(description = "공개 리뷰 수입니다.", example = "12")
    val reviewCount: Int,
)

@Schema(description = "공간 검색 결과 한 건")
data class SpaceSummaryDto(
    @field:Schema(description = "공간 식별자입니다.", example = "42")
    val id: Long,

    @field:Schema(description = "공간 이름입니다.", example = "루프탑 파티룸 한강")
    val name: String,

    @field:Schema(description = "한 줄 소개. 등록하지 않았으면 null입니다.", example = "한강이 보이는 루프탑 단독 파티룸")
    val shortIntro: String?,

    @field:Schema(
        description = "공간 유형. `PARTY_ROOM`, `EMPTY_SPACE`, `ROOFTOP`, `BRIDAL_SHOWER`, `CLUB`, `WORKSHOP` 중 하나입니다.",
        example = "PARTY_ROOM",
    )
    val spaceType: SpaceType,

    @field:Schema(description = "기본 주소입니다.", example = "서울 마포구 양화로 45")
    val address: String,

    @field:Schema(description = "시/도 지역. 등록하지 않았으면 null입니다.", example = "서울")
    val sido: String?,

    @field:Schema(description = "시/군/구 지역. 등록하지 않았으면 null입니다.", example = "마포구")
    val sigungu: String?,

    @field:Schema(description = "최소 수용 인원입니다.", example = "4")
    val minCapacity: Int,

    @field:Schema(description = "최대 수용 인원입니다.", example = "20")
    val maxCapacity: Int,

    @field:Schema(description = "가장 낮은 가격 규칙의 금액. 가격 규칙이 없으면 null입니다.", example = "120000")
    val minPrice: Long?,

    @field:Schema(description = "첫 이미지 URL. 이미지가 없으면 null입니다.", example = "/uploads/seed/space-1-0.jpg")
    val thumbnailUrl: String?,

    @field:Schema(description = "공개 리뷰 평점 평균. 리뷰가 없으면 null입니다.", example = "4.5")
    val rating: Double?,

    @field:Schema(description = "공개 리뷰 수입니다.", example = "12")
    val reviewCount: Int,
)

@Schema(description = "공간 검색 커서 페이징 응답")
data class SpaceSearchDto(
    @field:ArraySchema(arraySchema = Schema(description = "공간 요약 목록입니다."))
    val items: List<SpaceSummaryDto>,

    @field:Schema(
        description = "다음 페이지 커서. 마지막 페이지면 null입니다. 정렬 기준과 마지막 항목을 함께 담은 불투명 문자열입니다.",
        example = "bGF0ZXN0fDEwMnwxMDI",
    )
    val nextCursor: String?,
)

@Schema(description = "예약 점유 구간 한 건. 예약자 정보는 포함하지 않습니다.")
data class OccupiedIntervalDto(
    @field:Schema(description = "점유 시작 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val startAt: OffsetDateTime,

    @field:Schema(description = "점유 종료 일시입니다.", example = "2026-11-20T22:00:00+09:00")
    val endAt: OffsetDateTime,
)

@Schema(description = "공간의 예약 점유 구간 조회 결과")
data class SpaceAvailabilityDto(
    @field:Schema(description = "공간 식별자입니다.", example = "42")
    val spaceId: Long,

    @field:Schema(description = "조회 시작 일시입니다.", example = "2026-11-20T00:00:00+09:00")
    val from: OffsetDateTime,

    @field:Schema(description = "조회 종료 일시입니다.", example = "2026-11-21T00:00:00+09:00")
    val to: OffsetDateTime,

    @field:ArraySchema(arraySchema = Schema(description = "해당 구간과 겹치는 점유 구간입니다. 비어 있으면 예약 가능합니다."))
    val occupied: List<OccupiedIntervalDto>,
)

@Schema(description = "가격 규칙 한 건")
data class PriceRuleDto(
    @field:Schema(description = "가격 규칙 식별자입니다.", example = "1")
    val id: Long,

    @field:Schema(description = "요일. 1=월요일부터 7=일요일까지입니다.", example = "5")
    val dayOfWeek: Int,

    @field:Schema(description = "가격 단위. `HOUR` 또는 `DAY`입니다.", example = "HOUR")
    val unit: PriceUnit,

    @field:Schema(description = "시작 시각. `DAY` 단위 규칙이면 null입니다.", example = "18:00:00")
    val startTime: LocalTime?,

    @field:Schema(description = "종료 시각. `DAY` 단위 규칙이면 null입니다.", example = "22:00:00")
    val endTime: LocalTime?,

    @field:Schema(description = "해당 규칙의 가격. 원 단위입니다.", example = "120000")
    val price: Long,
)

@Schema(description = "검수 대기 중인 공간 한 건")
data class PendingInspectionDto(
    @field:Schema(description = "공간 식별자입니다.", example = "42")
    val spaceId: Long,

    @field:Schema(description = "공간 이름입니다.", example = "루프탑 파티룸 한강")
    val spaceName: String,

    @field:Schema(description = "공간을 등록한 호스트 계정의 식별자입니다.", example = "1")
    val hostId: Long,

    @field:Schema(description = "검수 요청 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val requestedAt: OffsetDateTime,
)
