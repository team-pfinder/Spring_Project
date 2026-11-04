package com.lookation.space.api

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalTime

data class SpaceForm(
    @field:NotBlank @field:Size(max = 100) val name: String,
    @field:Size(max = 300) val shortIntro: String? = null,
    val description: String? = null,
    @field:NotBlank @field:Size(max = 200) val address: String,
    @field:Size(max = 200) val addressDetail: String? = null,
    @field:NotBlank val spaceType: String,
    @field:Min(1) val minCapacity: Int,
    @field:Min(1) val maxCapacity: Int,
    @field:Size(max = 300) val useHours: String? = null,
    @field:Size(max = 450) val caution: String? = null,
    @field:Email val contactEmail: String? = null,
    @field:Size(max = 20) val contactPhone: String? = null,
    @field:Size(max = 2000) val websiteUrl: String? = null,
    val facilities: List<String> = emptyList(),
    val approvalRequired: Boolean = false,
)

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

data class PriceRuleForm(
    val dayOfWeek: Int,
    val unit: String,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val price: Long,
)

data class PriceRuleResponse(
    val id: Long,
    val dayOfWeek: Int,
    val unit: String,
    val startTime: LocalTime?,
    val endTime: LocalTime?,
    val price: Long,
)

data class InspectionDecisionForm(
    val approved: Boolean,
    @field:Size(max = 500) val reason: String? = null,
)

data class PendingInspectionResponse(
    val spaceId: Long,
    val spaceName: String,
    val hostId: Long,
    val requestedAt: java.time.OffsetDateTime,
)
