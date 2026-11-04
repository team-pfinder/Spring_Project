package com.lookation.dto

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

@Schema(description = "신고 한 건")
data class ReportDto(
    @field:Schema(description = "신고 식별자입니다.", example = "1")
    val id: Long,
    @field:Schema(description = "신고한 계정의 식별자입니다.", example = "42")
    val reporterId: Long,
    @field:Schema(
        description = "신고 대상 종류. `SPACE`, `RESERVATION`, `REVIEW`, `QNA`, `ACCOUNT` 중 하나입니다.",
        example = "REVIEW",
    )
    val targetType: String,
    @field:Schema(description = "신고 대상의 식별자입니다.", example = "42")
    val targetId: Long,
    @field:Schema(description = "신고 사유입니다.", example = "예약과 무관한 광고성 내용이 포함되어 있습니다.")
    val reason: String,
    @field:Schema(description = "처리 상태. `RECEIVED`, `ACCEPTED`, `REJECTED` 중 하나입니다.", example = "RECEIVED")
    val status: String,
    @field:Schema(description = "접수 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val createdAt: OffsetDateTime,
)

@Schema(description = "신고 커서 페이징 응답")
data class ReportListDto(
    @field:ArraySchema(arraySchema = Schema(description = "신고 목록. 최신순입니다."))
    val items: List<ReportDto>,
    @field:Schema(description = "다음 페이지 커서. 마지막 페이지면 null입니다.", example = "102")
    val nextCursor: Long?,
)
