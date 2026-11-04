package com.lookation.dto.host

data class HostProfileResponse(
    val businessName: String,
    val businessNumber: String,
    val representativeName: String,
    val bankName: String?,
    val bankAccountNumberMasked: String?,
    val status: String,
)
