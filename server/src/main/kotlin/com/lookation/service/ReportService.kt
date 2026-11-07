package com.lookation.service

import com.lookation.controller.form.CreateReportForm
import com.lookation.dto.ReportListDto
import com.lookation.dto.ReportDto
import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ConflictException
import com.lookation.global.error.NotFoundException
import com.lookation.repository.AccountRepository
import com.lookation.repository.QnaRepository
import com.lookation.domain.ReportStatus
import com.lookation.domain.ReportTarget
import com.lookation.repository.ReportRepository
import com.lookation.repository.ReportRow
import com.lookation.repository.ReservationRepository
import com.lookation.repository.ReviewRepository
import com.lookation.repository.SpaceRepository
import com.lookation.service.SanctionService
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
    fun create(reporterId: Long, form: CreateReportForm): ReportDto {
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
    fun list(status: String?, cursor: Long?, limit: Int): ReportListDto {
        if (limit !in 1..100) throw BadRequestException("limit은 1~100입니다")
        val statusFilter = status?.let {
            try {
                ReportStatus.valueOf(it)
            } catch (_: IllegalArgumentException) {
                throw BadRequestException("알 수 없는 신고 상태입니다")
            }
        }
        val rows = reportRepository.list(statusFilter, cursor, limit)
        return ReportListDto(rows.map { it.toResponse() }, if (rows.size == limit) rows.last().id else null)
    }

    @Transactional
    fun decide(adminId: Long, reportId: Long, accepted: Boolean): ReportDto {
        reportRepository.findById(reportId) ?: throw NotFoundException("신고를 찾을 수 없습니다")
        if (reportRepository.decide(reportId, accepted, adminId) != 1) {
            throw ConflictException("이미 처리된 신고입니다")
        }
        return requireNotNull(reportRepository.findById(reportId)).toResponse()
    }

    private fun targetExists(type: ReportTarget, id: Long): Boolean = when (type) {
        ReportTarget.SPACE -> spaceRepository.findById(id) != null
        ReportTarget.RESERVATION -> reservationRepository.findById(id) != null
        ReportTarget.REVIEW -> reviewRepository.findById(id) != null
        ReportTarget.QNA -> qnaRepository.findById(id) != null
        ReportTarget.ACCOUNT -> accountRepository.findById(id) != null
    }

    private fun ReportRow.toResponse() = ReportDto(
        id = id,
        reporterId = reporterId,
        targetType = targetType,
        targetId = targetId,
        reason = reason,
        status = status,
        createdAt = createdAt,
    )
}
