package com.lookation.controller.form

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Schema(description = "호스트 신청 요청")
data class HostApplyForm(
    @field:Schema(description = "사업자 상호명. 최대 100자입니다.", example = "한강파티룸")
    @field:NotBlank @field:Size(max = 100)
    val businessName: String,

    @field:Schema(description = "10자리 사업자등록번호입니다.", example = "1234567890")
    @field:NotBlank @field:Pattern(regexp = "\\d{10}")
    val businessNumber: String,

    @field:Schema(description = "대표자 이름. 최대 50자입니다.", example = "김하늘")
    @field:NotBlank @field:Size(max = 50)
    val representativeName: String,

    @field:Schema(description = "정산 계좌 은행명. 선택 항목이며 최대 50자입니다.", example = "국민은행")
    @field:Size(max = 50)
    val bankName: String? = null,

    @field:Schema(description = "정산 계좌 번호. 선택 항목이며 10~20자리 숫자입니다.", example = "110123456789")
    @field:Pattern(regexp = "\\d{10,20}")
    val bankAccountNumber: String? = null,

    @field:Schema(description = "예금주 이름. 선택 항목이며 최대 50자입니다.", example = "김하늘")
    @field:Size(max = 50)
    val bankAccountHolder: String? = null,
)
