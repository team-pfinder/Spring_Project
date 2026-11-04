package com.lookation.service

import com.lookation.controller.form.AskQnaForm
import com.lookation.controller.form.QnaAnswerForm
import com.lookation.dto.QnaListResponse
import com.lookation.dto.QnaResponse
import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ConflictException
import com.lookation.global.error.ForbiddenException
import com.lookation.global.error.NotFoundException
import com.lookation.repository.QnaRepository
import com.lookation.repository.QnaRow
import com.lookation.repository.SpaceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QnaService(
    private val qnaRepository: QnaRepository,
    private val spaceRepository: SpaceRepository,
    private val sanctionService: com.lookation.service.SanctionService,
) {

    @Transactional
    fun ask(memberId: Long, spaceId: Long, form: AskQnaForm): QnaResponse {
        sanctionService.checkWritable(memberId)
        val space = spaceRepository.findById(spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.status != "PUBLISHED") {
            throw ConflictException("게시된 공간에만 질문할 수 있습니다")
        }
        val qnaId = qnaRepository.insert(spaceId, memberId, form.title, form.content)
        return response(requireNotNull(qnaRepository.findById(qnaId)))
    }

    @Transactional(readOnly = true)
    fun list(spaceId: Long, cursor: Long?, limit: Int): QnaListResponse {
        if (limit !in 1..50) throw BadRequestException("limit은 1~50입니다")
        val rows = qnaRepository.listBySpace(spaceId, cursor, limit)
        return QnaListResponse(
            items = rows.map { it.toResponse() },
            nextCursor = if (rows.size == limit) rows.last().id else null,
        )
    }

    @Transactional(readOnly = true)
    fun get(id: Long): QnaResponse {
        val qna = qnaRepository.findById(id) ?: throw NotFoundException("질문을 찾을 수 없습니다")
        return qna.toResponse()
    }

    @Transactional
    fun answer(hostId: Long, qnaId: Long, form: QnaAnswerForm): QnaResponse {
        val qna = qnaRepository.findById(qnaId)
            ?: throw NotFoundException("질문을 찾을 수 없습니다")
        val space = spaceRepository.findById(qna.spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.hostId != hostId) {
            throw ForbiddenException("공간 호스트만 답변할 수 있습니다")
        }
        if (qna.status != "OPEN") {
            throw ConflictException("이미 답변한 질문입니다")
        }
        if (qnaRepository.markAnswered(qnaId) != 1) {
            throw ConflictException("이미 답변한 질문입니다")
        }
        qnaRepository.insertReply(qnaId, hostId, form.content)
        return response(requireNotNull(qnaRepository.findById(qnaId)))
    }

    @Transactional
    fun delete(memberId: Long, qnaId: Long) {
        val qna = qnaRepository.findById(qnaId)
            ?: throw NotFoundException("질문을 찾을 수 없습니다")
        if (qna.memberId != memberId) {
            throw ForbiddenException("작성자만 삭제할 수 있습니다")
        }
        if (qnaRepository.deleteOpen(qnaId, memberId) != 1) {
            throw ConflictException("답변이 달린 질문은 삭제할 수 없습니다")
        }
    }

    private fun response(qna: QnaRow): QnaResponse = qna.toResponse()

    private fun QnaRow.toResponse() = QnaResponse(
        id = id,
        spaceId = spaceId,
        nickname = nickname,
        title = title,
        content = content,
        status = status,
        reply = reply,
        createdAt = createdAt,
    )
}
