package com.lookation.dto

import java.time.OffsetDateTime

data class ReviewDto(
    val id: Long,
    val spaceId: Long,
    val nickname: String,
    val rating: Int,
    val content: String,
    val reply: String?,
    val images: List<String>,
    val createdAt: OffsetDateTime,
)

data class ReviewListDto(
    val items: List<ReviewDto>,
    val nextCursor: Long?,
)
