package com.lookation.dto

import java.time.OffsetDateTime

data class ReportDto(
    val id: Long,
    val reporterId: Long,
    val targetType: String,
    val targetId: Long,
    val reason: String,
    val status: String,
    val createdAt: OffsetDateTime,
)

data class ReportListDto(val items: List<ReportDto>, val nextCursor: Long?)
