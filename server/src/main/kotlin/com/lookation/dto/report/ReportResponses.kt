package com.lookation.dto.report

import java.time.OffsetDateTime

data class ReportResponse(
    val id: Long,
    val reporterId: Long,
    val targetType: String,
    val targetId: Long,
    val reason: String,
    val status: String,
    val createdAt: OffsetDateTime,
)

data class ReportListResponse(val items: List<ReportResponse>, val nextCursor: Long?)
