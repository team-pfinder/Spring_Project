package com.lookation.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

@Schema(description = "계정 제재 한 건")
data class SanctionDto(
    @field:Schema(description = "제재 식별자입니다.", example = "1")
    val id: Long,
    @field:Schema(description = "제재 대상 계정의 식별자입니다.", example = "42")
    val accountId: Long,
    @field:Schema(description = "제재 사유입니다.", example = "반복적인 예약 취소로 호스트에게 피해를 주었습니다.")
    val reason: String,
    @field:Schema(description = "제재 단계. 1~3 사이입니다.", example = "2")
    val level: Int,
    @field:Schema(description = "제재 시작 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val startsAt: OffsetDateTime,
    @field:Schema(description = "제재 종료 일시. 기간 제한이 없으면 null입니다.", example = "2026-12-20T18:00:00+09:00")
    val endsAt: OffsetDateTime?,
)
