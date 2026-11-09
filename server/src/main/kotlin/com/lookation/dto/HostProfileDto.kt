package com.lookation.dto

import com.lookation.domain.SpaceStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "호스트 프로필")
data class HostProfileDto(
    @field:Schema(description = "사업자 상호명입니다.", example = "한강파티룸")
    val businessName: String,

    @field:Schema(description = "사업자등록번호입니다.", example = "1234567890")
    val businessNumber: String,

    @field:Schema(description = "대표자 이름입니다.", example = "김하늘")
    val representativeName: String,

    @field:Schema(description = "정산 계좌 은행명. 등록하지 않았으면 null입니다.", example = "국민은행")
    val bankName: String?,

    @field:Schema(description = "끝 4자리만 남기고 마스킹한 정산 계좌 번호. 등록하지 않았으면 null입니다.", example = "****6789")
    val bankAccountNumberMasked: String?,

    @field:Schema(
        description = "호스트 인증 상태. `PENDING_INSPECTION`, `INSPECTION_REJECTED`, `PUBLISHED`, `SUSPENDED` 중 하나입니다.",
        example = "PUBLISHED",
    )
    val status: SpaceStatus,
)
