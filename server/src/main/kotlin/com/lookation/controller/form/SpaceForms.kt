package com.lookation.controller.form

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import com.lookation.domain.FacilityCode
import com.lookation.domain.PriceUnit
import java.time.LocalTime

@Schema(description = "공간 등록·수정 요청")
data class SpaceForm(
    @field:Schema(description = "공간 이름. 최대 100자입니다.", example = "루프탑 파티룸 한강")
    @field:NotBlank @field:Size(max = 100) val name: String,
    @field:Schema(description = "목록에 노출될 한 줄 소개. 선택 항목이며 최대 300자입니다.", example = "한강이 보이는 루프탑 단독 파티룸")
    @field:Size(max = 300) val shortIntro: String? = null,
    @field:Schema(description = "상세 설명. 선택 항목입니다.", example = "단독으로 사용하는 루프탑 공간으로 최대 20명까지 이용할 수 있습니다.")
    val description: String? = null,
    @field:Schema(description = "기본 주소. 최대 200자입니다.", example = "서울 마포구 양화로 45")
    @field:NotBlank @field:Size(max = 200) val address: String,
    @field:Schema(description = "상세 주소. 선택 항목이며 최대 200자입니다.", example = "3층")
    @field:Size(max = 200) val addressDetail: String? = null,
    @field:Schema(description = "시/도 지역. 선택 항목이며 최대 20자입니다. 검색의 `sido` 필터와 같은 값을 씁니다.", example = "서울")
    @field:Size(max = 20) val sido: String? = null,
    @field:Schema(description = "시/군/구 지역. 선택 항목이며 최대 30자입니다. 검색의 `sigungu` 필터와 같은 값을 씁니다.", example = "마포구")
    @field:Size(max = 30) val sigungu: String? = null,
    @field:Schema(
        description = "공간 유형. `PARTY_ROOM`, `EMPTY_SPACE`, `ROOFTOP`, `BRIDAL_SHOWER`, `CLUB`, `WORKSHOP` 중 하나입니다.",
        example = "PARTY_ROOM",
    )
    @field:NotBlank val spaceType: String,
    @field:Schema(description = "최소 수용 인원. 1 이상입니다.", example = "4")
    @field:Min(1) val minCapacity: Int,
    @field:Schema(description = "최대 수용 인원. 최소 수용 인원 이상이어야 합니다.", example = "20")
    @field:Min(1) val maxCapacity: Int,
    @field:Schema(description = "이용 가능 시간 안내. 선택 항목이며 최대 300자입니다.", example = "10:00~22:00")
    @field:Size(max = 300) val useHours: String? = null,
    @field:Schema(description = "주의 사항. 선택 항목이며 최대 450자입니다.", example = "실내 흡연은 금지입니다.")
    @field:Size(max = 450) val caution: String? = null,
    @field:Schema(description = "문의용 이메일. 선택 항목입니다.", example = "host@lookation.local")
    @field:Email val contactEmail: String? = null,
    @field:Schema(description = "문의용 전화번호. 선택 항목이며 최대 20자입니다.", example = "02-1234-5678")
    @field:Size(max = 20) val contactPhone: String? = null,
    @field:Schema(description = "공간 소개 웹사이트 주소. 선택 항목이며 최대 2000자입니다.", example = "https://lookation.local/spaces/42")
    @field:Size(max = 2000) val websiteUrl: String? = null,
    @field:ArraySchema(
        schema = Schema(example = "WIFI"),
        arraySchema = Schema(description = "제공 시설 코드 목록. `PARKING`, `WIFI`, `PROJECTOR`, `SOUND`, `KITCHEN`, `REFRIGERATOR`, `MICROWAVE`, `AIR_CONDITIONER`, `TABLE`, `CHAIR`, `BATHROOM`, `ELEVATOR` 중에서 고릅니다."),
    )
    val facilities: List<FacilityCode> = emptyList(),
    @field:Schema(description = "예약 승인 필요 여부. `true`이면 예약이 호스트 승인 후 결제 대기로 진행됩니다.", example = "false")
    val approvalRequired: Boolean = false,
)




@Schema(description = "가격 규칙 항목")
data class PriceRuleForm(
    @field:Schema(description = "요일. 1=월요일부터 7=일요일까지입니다.", example = "5")
    val dayOfWeek: Int,
    @field:Schema(description = "가격 단위. `HOUR` 또는 `DAY`입니다.", example = "HOUR")
    val unit: PriceUnit,
    @field:Schema(description = "시작 시각. `HOUR` 단위일 때 필수이며 `DAY` 단위에서는 지정할 수 없습니다.", example = "18:00:00")
    val startTime: LocalTime? = null,
    @field:Schema(description = "종료 시각. `HOUR` 단위일 때 필수이며 시작 시각보다 뒤여야 합니다.", example = "22:00:00")
    val endTime: LocalTime? = null,
    @field:Schema(description = "해당 규칙의 가격. 0 이상이며 원 단위입니다.", example = "120000")
    val price: Long,
)


@Schema(description = "공간 검수 결정 요청")
data class InspectionDecisionForm(
    @field:Schema(description = "승인 여부. `true`이면 `PUBLISHED`, `false`이면 `INSPECTION_REJECTED`로 전환합니다.", example = "true")
    val approved: Boolean,
    @field:Schema(description = "거절 사유. 선택 항목이며 최대 500자입니다.", example = "공간 사진에 실제와 다른 시설이 포함되어 있습니다.")
    @field:Size(max = 500) val reason: String? = null,
)
