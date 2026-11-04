package com.lookation.controller

import com.lookation.dto.ReviewListResponse
import com.lookation.dto.ReviewResponse
import com.lookation.global.error.UnauthorizedException
import com.lookation.service.ReviewService
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

@RestController
@RequestMapping("/api/v1")
class ReviewController(private val reviewService: ReviewService) {

    @GetMapping("/spaces/{spaceId}/reviews")
    fun list(
        @PathVariable spaceId: Long,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "20") limit: Int,
    ): ReviewListResponse = reviewService.list(spaceId, cursor, limit)

    @PostMapping("/reviews")
    fun create(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: CreateReviewForm,
    ): ReviewResponse = reviewService.create(jwt.accountId(), form)

    @PostMapping("/reviews/{id}/images")
    fun uploadImage(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @RequestParam("file") file: MultipartFile,
    ): String = reviewService.uploadImage(jwt.accountId(), id, file)

    @PostMapping("/reviews/{id}/reply")
    @PreAuthorize("hasRole('HOST')")
    fun reply(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @Valid @RequestBody form: ReviewReplyForm,
    ): ReviewResponse = reviewService.reply(jwt.accountId(), id, form)

    @PostMapping("/admin/reviews/{id}/blind")
    @PreAuthorize("hasRole('ADMIN')")
    fun blind(@PathVariable id: Long) = reviewService.blind(id)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
