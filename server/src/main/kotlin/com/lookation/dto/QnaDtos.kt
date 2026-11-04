package com.lookation.dto

import java.time.OffsetDateTime

data class QnaDto(
    val id: Long,
    val spaceId: Long,
    val nickname: String,
    val title: String,
    val content: String,
    val status: String,
    val reply: String?,
    val createdAt: OffsetDateTime,
)

data class QnaListDto(
    val items: List<QnaDto>,
    val nextCursor: Long?,
)
