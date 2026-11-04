package com.lookation.controller.form

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "공지사항 등록·수정 요청")
data class NoticeForm(
    @field:Schema(description = "공지 제목. 최대 200자입니다.", example = "추석 연휴 고객센터 운영 안내")
    @field:NotBlank @field:Size(max = 200) val title: String,
    @field:Schema(description = "공지 본문입니다.", example = "추석 연휴 기간에는 전화 상담이 중단됩니다.")
    @field:NotBlank val content: String,
    @field:Schema(description = "중요 공지 여부. `true`이면 목록 상단에 고정됩니다.", example = "false")
    val important: Boolean = false,
)

@Schema(description = "도움말 등록·수정 요청")
data class HelpForm(
    @field:Schema(description = "도움말 분류명. 최대 50자이며 자유 입력입니다.", example = "예약")
    @field:NotBlank @field:Size(max = 50) val category: String,
    @field:Schema(description = "도움말 제목. 최대 200자입니다.", example = "예약 취소는 언제까지 가능한가요")
    @field:NotBlank @field:Size(max = 200) val title: String,
    @field:Schema(description = "도움말 본문입니다.", example = "이용 시작 24시간 전까지 무료로 취소할 수 있습니다.")
    @field:NotBlank val content: String,
)
