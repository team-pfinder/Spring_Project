package com.lookation.dto

import java.time.OffsetDateTime

data class NoticeResponse(
    val id: Long,
    val title: String,
    val content: String,
    val important: Boolean,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
)

data class NoticeListResponse(val items: List<NoticeResponse>, val nextCursor: Long?)

data class HelpResponse(
    val id: Long,
    val category: String,
    val title: String,
    val content: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
)

data class HelpListResponse(val items: List<HelpResponse>, val nextCursor: Long?)
