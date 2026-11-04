package com.lookation.service.report

import com.lookation.controller.report.CreateReportForm
import com.lookation.dto.report.ReportListResponse
import com.lookation.dto.report.ReportResponse
import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ConflictException
import com.lookation.global.error.NotFoundException
import com.lookation.repository.account.AccountRepository
import com.lookation.repository.qna.QnaRepository
import com.lookation.repository.report.ReportRepository
import com.lookation.repository.report.ReportRow
import com.lookation.repository.reservation.ReservationRepository
import com.lookation.repository.review.ReviewRepository
import com.lookation.repository.space.SpaceRepository
import com.lookation.service.sanction.SanctionService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportService(
    private val reportRepository: ReportRepository,
    private val spaceRepository: SpaceRepository,
    private val reservationRepository: ReservationRepository,
    private val reviewRepository: ReviewRepository,
    private val qnaRepository: QnaRepository,
    private val accountRepository: AccountRepository,
    private val sanctionService: SanctionService,
) {

    @Transactional
    fun create(reporterId: Long, form: CreateReportForm): ReportResponse {
        sanctionService.checkWritable(reporterId)
        if (!targetExists(form.targetType, form.targetId)) {
            throw NotFoundException("신고 대상을 찾을 수 없습니다")
        }
        val id = try {
            reportRepository.insert(reporterId, form.targetType, form.targetId, form.reason)
        } catch (_: DataIntegrityViolationException) {
            throw ConflictException("이미 신고한 대상입니다")
        }
        return requireNotNull(reportRepository.findById(id)).toResponse()
    }

    @Transactional(readOnly = true)
    fun list(status: String?, cursor: Long?, limit: Int): ReportListResponse {
        if (limit !in 1..100) throw BadRequestException("limit은 1~100입니다")
        val rows = reportRepository.list(status, cursor, limit)
        return ReportListResponse(rows.map { it.toResponse() }, if (rows.size == limit) rows.last().id else null)
    }

    @Transactional
    fun decide(adminId: Long, reportId: Long, accepted: Boolean): ReportResponse {
        reportRepository.findById(reportId) ?: throw NotFoundException("신고를 찾을 수 없습니다")
        if (reportRepository.decide(reportId, accepted, adminId) != 1) {
            throw ConflictException("이미 처리된 신고입니다")
        }
        return requireNotNull(reportRepository.findById(reportId)).toResponse()
    }

    private fun targetExists(type: String, id: Long): Boolean = when (type) {
        "SPACE" -> spaceRepository.findById(id) != null
        "RESERVATION" -> reservationRepository.findById(id) != null
        "REVIEW" -> reviewRepository.findById(id) != null
        "QNA" -> qnaRepository.findById(id) != null
        "ACCOUNT" -> accountRepository.findById(id) != null
        else -> false
    }

    private fun ReportRow.toResponse() = ReportResponse(
        id = id,
        reporterId = reporterId,
        targetType = targetType,
        targetId = targetId,
        reason = reason,
        status = status,
        createdAt = createdAt,
    )
}
