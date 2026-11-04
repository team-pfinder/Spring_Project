package com.lookation.dto.sanction

import java.time.OffsetDateTime

data class SanctionResponse(
    val id: Long,
    val accountId: Long,
    val reason: String,
    val level: Int,
    val startsAt: OffsetDateTime,
    val endsAt: OffsetDateTime?,
)
