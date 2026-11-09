package com.lookation.controller.form

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

@Schema(description = "리뷰 작성 요청")
data class CreateReviewForm(
    @field:Schema(description = "리뷰를 남길 완료된 예약의 식별자입니다.", example = "42")
    @field:Positive
    val reservationId: Long,

    @field:Schema(description = "평점. 1~5 사이의 정수입니다.", example = "5")
    @field:Min(1) @field:Max(5)
    val rating: Int,

    @field:Schema(description = "리뷰 본문. 최대 2000자입니다.", example = "조명과 음향 시설이 좋아서 모임하기 편했습니다.")
    @field:NotBlank @field:Size(max = 2000)
    val content: String,
)

@Schema(description = "호스트 리뷰 답변 요청")
data class ReviewReplyForm(
    @field:Schema(description = "답변 본문. 최대 2000자입니다.", example = "이용해 주셔서 감사합니다. 다음에도 좋은 공간으로 준비하겠습니다.")
    @field:NotBlank @field:Size(max = 2000)
    val content: String,
)
