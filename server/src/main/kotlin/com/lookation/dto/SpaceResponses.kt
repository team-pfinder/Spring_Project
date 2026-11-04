package com.lookation.dto

import java.time.LocalTime
import java.time.OffsetDateTime

data class SpaceResponse(
    val id: Long,
    val name: String,
    val shortIntro: String?,
    val description: String?,
    val address: String,
    val addressDetail: String?,
    val spaceType: String,
    val minCapacity: Int,
    val maxCapacity: Int,
    val useHours: String?,
    val caution: String?,
    val contactEmail: String?,
    val contactPhone: String?,
    val websiteUrl: String?,
    val status: String,
    val facilities: List<String>,
    val images: List<String>,
    val approvalRequired: Boolean,
)

data class SpaceSummaryResponse(
    val id: Long,
    val name: String,
    val shortIntro: String?,
    val spaceType: String,
    val address: String,
    val minCapacity: Int,
    val maxCapacity: Int,
    val minPrice: Long?,
)

data class SpaceSearchResponse(
    val items: List<SpaceSummaryResponse>,
    val nextCursor: Long?,
)

data class PriceRuleResponse(
    val id: Long,
    val dayOfWeek: Int,
    val unit: String,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val price: Long,
)

data class PendingInspectionResponse(
    val spaceId: Long,
    val spaceName: String,
    val hostId: Long,
    val requestedAt: OffsetDateTime,
)
