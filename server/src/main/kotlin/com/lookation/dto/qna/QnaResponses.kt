package com.lookation.dto.qna

import java.time.OffsetDateTime

data class QnaResponse(
    val id: Long,
    val spaceId: Long,
    val nickname: String,
    val title: String,
    val content: String,
    val status: String,
    val reply: String?,
    val createdAt: OffsetDateTime,
)

data class QnaListResponse(
    val items: List<QnaResponse>,
    val nextCursor: Long?,
)
