package com.lookation.dto

data class HostProfileDto(
    val businessName: String,
    val businessNumber: String,
    val representativeName: String,
    val bankName: String?,
    val bankAccountNumberMasked: String?,
    val status: String,
)
