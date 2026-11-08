package com.lookation.controller

import com.lookation.dto.PagedDto
import com.lookation.dto.ReviewDto
import com.lookation.global.error.UnauthorizedException
import com.lookation.service.ReviewService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import com.lookation.controller.form.ReviewReplyForm
import com.lookation.controller.form.CreateReviewForm

@Tag(
    name = "리뷰",
    description = "공간 리뷰 목록 조회, 리뷰 작성, 이미지 첨부, 호스트 답글과 관리자 블라인드 처리. 목록 조회는 공개이고 나머지는 로그인이 필요합니다.",
)
@RestController
@RequestMapping("/api/v1")
class ReviewController(private val reviewService: ReviewService) {

    @Operation(
        summary = "공간 리뷰 목록 조회",
        description = "공간의 리뷰를 최신순(ID 내림차순)으로 조회합니다. 블라인드 처리된 리뷰는 결과에서 제외되며, 인증 없이 호출할 수 있습니다. `limit`이 1~50을 벗어나면 400이고, 응답의 `nextCursor`가 null이면 마지막 페이지입니다.",
    )
    @GetMapping("/spaces/{spaceId}/reviews")
    fun list(
        @Parameter(description = "리뷰를 조회할 공간 ID", example = "1")
        @PathVariable spaceId: Long,
        @Parameter(description = "이전 페이지 마지막 리뷰 ID. 생략하면 첫 페이지", example = "42")
        @RequestParam(required = false) cursor: Long?,
        @Parameter(description = "페이지 크기 (1~50)", example = "20")
        @RequestParam(defaultValue = "20") limit: Int,
    ): PagedDto<ReviewDto> = reviewService.list(spaceId, cursor, limit)

    @Operation(
        summary = "리뷰 작성",
        description = "이용을 마친 본인 예약에 리뷰를 작성합니다. 예약 상태가 `COMPLETED`여야 하고, 이용 종료 후 30일이 지나면 작성할 수 없습니다. 예약 1건당 1개만 작성할 수 있으며, 평점은 1~5, 내용은 최대 2000자입니다. 없는 예약은 404, 다른 회원의 예약은 403, 완료되지 않은 예약이거나 이미 작성한 경우는 409, 기한이 지난 경우는 400, 제재 중인 계정은 403입니다.",
    )
    @PostMapping("/reviews")
    fun create(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: CreateReviewForm,
    ): ReviewDto = reviewService.create(jwt.accountId(), form)

    @Operation(
        summary = "리뷰 이미지 업로드",
        description = "리뷰에 이미지를 1장씩 첨부하고 `/uploads/{key}` 경로를 문자열로 돌려줍니다. multipart 필드 이름은 `file`이며 jpg·png·webp만, 10MB 이하만 허용합니다(위반 시 400). 리뷰당 최대 5장이고, 없는 리뷰는 404, 작성자가 아니면 403, 블라인드된 리뷰이거나 5장을 넘기면 409입니다.",
    )
    @PostMapping("/reviews/{id}/images")
    fun uploadImage(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "이미지를 첨부할 리뷰 ID", example = "42")
        @PathVariable id: Long,
        @Parameter(description = "업로드할 이미지 파일 (jpg, png, webp / 10MB 이하)")
        @RequestParam("file") file: MultipartFile,
    ): String = reviewService.uploadImage(jwt.accountId(), id, file)

    @Operation(
        summary = "호스트 답글 작성",
        description = "리뷰가 달린 공간의 호스트가 답글을 1회 작성합니다. `HOST` 역할이면서 해당 공간의 소유자여야 하고, 다른 호스트이거나 일반 회원이면 403입니다. 없는 리뷰는 404, 블라인드된 리뷰이거나 이미 답글이 있으면 409입니다. 내용은 최대 2000자입니다.",
    )
    @PostMapping("/reviews/{id}/reply")
    @PreAuthorize("hasRole('HOST')")
    fun reply(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "답글을 달 리뷰 ID", example = "42")
        @PathVariable id: Long,
        @Valid @RequestBody form: ReviewReplyForm,
    ): ReviewDto = reviewService.reply(jwt.accountId(), id, form)

    @Operation(
        summary = "리뷰 블라인드 처리",
        description = "관리자가 리뷰를 블라인드 처리합니다. 처리된 리뷰는 공간 리뷰 목록에서 빠지고, 답글이나 이미지 첨부도 409로 거부됩니다. 없는 리뷰는 404, 이미 블라인드된 리뷰는 409입니다. 성공 시 본문 없이 200을 돌려줍니다.",
    )
    @PostMapping("/admin/reviews/{id}/blind")
    @PreAuthorize("hasRole('ADMIN')")
    fun blind(
        @Parameter(description = "블라인드 처리할 리뷰 ID", example = "42")
        @PathVariable id: Long,
    ) = reviewService.blind(id)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
