package com.lookation.dto

import java.time.OffsetDateTime

data class NoticeDto(
    val id: Long,
    val title: String,
    val content: String,
    val important: Boolean,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
)

data class NoticeListDto(val items: List<NoticeDto>, val nextCursor: Long?)

data class HelpDto(
    val id: Long,
    val category: String,
    val title: String,
    val content: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
)

data class HelpListDto(val items: List<HelpDto>, val nextCursor: Long?)
