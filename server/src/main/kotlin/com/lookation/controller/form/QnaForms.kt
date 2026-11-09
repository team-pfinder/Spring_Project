package com.lookation.controller.form

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "공간 문의 등록 요청")
data class AskQnaForm(
    @field:Schema(description = "문의 제목. 최대 200자입니다.", example = "주차 공간이 있나요")
    @field:NotBlank @field:Size(max = 200)
    val title: String,

    @field:Schema(description = "문의 본문. 최대 2000자입니다.", example = "차량 2대를 가져갈 예정인데 주차가 가능할까요?")
    @field:NotBlank @field:Size(max = 2000)
    val content: String,
)

@Schema(description = "공간 문의 답변 등록 요청")
data class QnaAnswerForm(
    @field:Schema(description = "답변 본문. 최대 2000자입니다.", example = "건물 지하 1층에 2대까지 주차할 수 있습니다.")
    @field:NotBlank @field:Size(max = 2000)
    val content: String,
)
