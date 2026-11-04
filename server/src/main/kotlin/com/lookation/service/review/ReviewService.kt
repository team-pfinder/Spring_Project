package com.lookation.service.review

import com.lookation.controller.review.CreateReviewForm
import com.lookation.controller.review.ReviewReplyForm
import com.lookation.dto.review.ReviewListResponse
import com.lookation.dto.review.ReviewResponse
import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ConflictException
import com.lookation.global.error.ForbiddenException
import com.lookation.global.error.NotFoundException
import com.lookation.global.file.FileStorage
import com.lookation.repository.reservation.ReservationRepository
import com.lookation.repository.review.ReviewRepository
import com.lookation.repository.review.ReviewRow
import com.lookation.repository.space.SpaceRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.OffsetDateTime

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val reservationRepository: ReservationRepository,
    private val spaceRepository: SpaceRepository,
    private val fileStorage: FileStorage,
    private val sanctionService: com.lookation.service.sanction.SanctionService,
) {

    @Transactional
    fun create(memberId: Long, form: CreateReviewForm): ReviewResponse {
        sanctionService.checkWritable(memberId)
        val reservation = reservationRepository.findById(form.reservationId)
            ?: throw NotFoundException("예약을 찾을 수 없습니다")
        if (reservation.memberId != memberId) {
            throw ForbiddenException("본인 예약에만 리뷰를 작성할 수 있습니다")
        }
        if (reservation.status != "COMPLETED") {
            throw ConflictException("이용완료된 예약만 리뷰를 작성할 수 있습니다")
        }
        if (reservation.endAt.plusDays(REVIEW_WINDOW_DAYS).isBefore(OffsetDateTime.now())) {
            throw BadRequestException("리뷰 작성 기한(${REVIEW_WINDOW_DAYS}일)이 지났습니다")
        }
        if (reviewRepository.existsByReservation(reservation.id)) {
            throw ConflictException("이미 리뷰를 작성했습니다")
        }
        val reviewId = try {
            reviewRepository.insert(reservation.id, reservation.spaceId, memberId, form.rating, form.content)
        } catch (_: DataIntegrityViolationException) {
            throw ConflictException("이미 리뷰를 작성했습니다")
        }
        return response(requireNotNull(reviewRepository.findById(reviewId)))
    }

    @Transactional(readOnly = true)
    fun list(spaceId: Long, cursor: Long?, limit: Int): ReviewListResponse {
        if (limit !in 1..50) throw BadRequestException("limit은 1~50입니다")
        val rows = reviewRepository.listBySpace(spaceId, cursor, limit)
        val images = reviewRepository.imageKeys(rows.map { it.id })
        return ReviewListResponse(
            items = rows.map { it.toResponse(images[it.id].orEmpty()) },
            nextCursor = if (rows.size == limit) rows.last().id else null,
        )
    }

    @Transactional
    fun reply(hostId: Long, reviewId: Long, form: ReviewReplyForm): ReviewResponse {
        val review = reviewRepository.findById(reviewId)
            ?: throw NotFoundException("리뷰를 찾을 수 없습니다")
        if (review.blindedAt != null) {
            throw ConflictException("블라인드된 리뷰에는 답글을 달 수 없습니다")
        }
        val space = spaceRepository.findById(review.spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.hostId != hostId) {
            throw ForbiddenException("공간 호스트만 답글을 달 수 있습니다")
        }
        if (review.reply != null) {
            throw ConflictException("이미 답글이 있습니다")
        }
        try {
            reviewRepository.insertReply(reviewId, hostId, form.content)
        } catch (_: DataIntegrityViolationException) {
            throw ConflictException("이미 답글이 있습니다")
        }
        return response(requireNotNull(reviewRepository.findById(reviewId)))
    }

    @Transactional
    fun blind(reviewId: Long) {
        reviewRepository.findById(reviewId) ?: throw NotFoundException("리뷰를 찾을 수 없습니다")
        if (reviewRepository.blind(reviewId) != 1) {
            throw ConflictException("이미 블라인드된 리뷰입니다")
        }
    }

    @Transactional
    fun uploadImage(accountId: Long, reviewId: Long, file: MultipartFile): String {
        val review = reviewRepository.findById(reviewId)
            ?: throw NotFoundException("리뷰를 찾을 수 없습니다")
        if (review.memberId != accountId) {
            throw ForbiddenException("작성자만 이미지를 올릴 수 있습니다")
        }
        if (review.blindedAt != null) {
            throw ConflictException("블라인드된 리뷰에는 이미지를 올릴 수 없습니다")
        }
        if (reviewRepository.countImages(reviewId) >= MAX_IMAGES) {
            throw ConflictException("리뷰 이미지는 ${MAX_IMAGES}장까지 가능합니다")
        }
        val stored = fileStorage.store(file)
        reviewRepository.addImage(reviewId, stored.key, stored.originalName, stored.contentType, stored.size)
        return "/uploads/${stored.key}"
    }

    private fun response(review: ReviewRow): ReviewResponse =
        review.toResponse(
            reviewRepository.imageKeys(listOf(review.id))[review.id].orEmpty(),
        )

    private fun ReviewRow.toResponse(images: List<String>) = ReviewResponse(
        id = id,
        spaceId = spaceId,
        nickname = nickname,
        rating = rating,
        content = content,
        reply = reply,
        images = images.map { "/uploads/$it" },
        createdAt = createdAt,
    )

    companion object {
        private const val MAX_IMAGES = 5
        private const val REVIEW_WINDOW_DAYS = 30L
    }
}
