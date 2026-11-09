package com.lookation.dto

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

@Schema(description = "리뷰 한 건")
data class ReviewDto(
    @field:Schema(description = "리뷰 식별자입니다.", example = "1")
    val id: Long,

    @field:Schema(description = "리뷰가 달린 공간의 식별자입니다.", example = "42")
    val spaceId: Long,

    @field:Schema(description = "작성자 닉네임입니다.", example = "공간지기")
    val nickname: String,

    @field:Schema(description = "평점. 1~5 사이입니다.", example = "5")
    val rating: Int,

    @field:Schema(description = "리뷰 본문입니다.", example = "조명과 음향 시설이 좋아서 모임하기 편했습니다.")
    val content: String,

    @field:Schema(description = "호스트 답변. 아직 답변이 없으면 null입니다.", example = "이용해 주셔서 감사합니다.")
    val reply: String?,

    @field:ArraySchema(
        schema = Schema(example = "/uploads/reviews/1.jpg"),
        arraySchema = Schema(description = "첨부 이미지 경로 목록입니다."),
    )
    val images: List<String>,

    @field:Schema(description = "작성 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val createdAt: OffsetDateTime,
)
