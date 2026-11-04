package com.lookation.dto

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

@Schema(description = "공간 문의 한 건")
data class QnaDto(
    @field:Schema(description = "문의 식별자입니다.", example = "1")
    val id: Long,
    @field:Schema(description = "문의가 달린 공간의 식별자입니다.", example = "42")
    val spaceId: Long,
    @field:Schema(description = "작성자 닉네임입니다.", example = "공간지기")
    val nickname: String,
    @field:Schema(description = "문의 제목입니다.", example = "주차 공간이 있나요")
    val title: String,
    @field:Schema(description = "문의 본문입니다.", example = "차량 2대를 가져갈 예정인데 주차가 가능할까요?")
    val content: String,
    @field:Schema(description = "문의 상태. `OPEN` 또는 `ANSWERED`입니다.", example = "OPEN")
    val status: String,
    @field:Schema(description = "호스트 답변. 아직 답변이 없으면 null입니다.", example = "건물 지하 1층에 2대까지 주차할 수 있습니다.")
    val reply: String?,
    @field:Schema(description = "작성 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val createdAt: OffsetDateTime,
)

@Schema(description = "공간 문의 커서 페이징 응답")
data class QnaListDto(
    @field:ArraySchema(arraySchema = Schema(description = "문의 목록. 최신순입니다."))
    val items: List<QnaDto>,
    @field:Schema(description = "다음 페이지 커서. 마지막 페이지면 null입니다.", example = "102")
    val nextCursor: Long?,
)
