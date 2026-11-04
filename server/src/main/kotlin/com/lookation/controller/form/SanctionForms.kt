package com.lookation.controller.form

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.OffsetDateTime

@Schema(description = "제재 적용 요청")
data class ApplySanctionForm(
    @field:Schema(description = "제재할 계정의 식별자입니다.", example = "42")
    @field:Positive val accountId: Long,
    @field:Schema(description = "제재 단계. 1~3 사이의 정수입니다.", example = "2")
    @field:Min(1) @field:Max(3) val level: Int,
    @field:Schema(description = "제재 사유. 최대 300자입니다.", example = "반복적인 예약 취소로 호스트에게 피해를 주었습니다.")
    @field:NotBlank @field:Size(max = 300) val reason: String,
    @field:Schema(description = "제재 종료 일시. 선택 항목이며 생략하면 기간 제한 없이 적용됩니다.", example = "2026-11-20T18:00:00+09:00")
    val endsAt: OffsetDateTime? = null,
)
