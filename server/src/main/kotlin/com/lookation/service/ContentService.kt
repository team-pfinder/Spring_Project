package com.lookation.service

import com.lookation.controller.form.HelpForm
import com.lookation.controller.form.NoticeForm
import com.lookation.dto.HelpDto
import com.lookation.dto.NoticeDto
import com.lookation.dto.PagedDto
import com.lookation.global.error.BadRequestException
import com.lookation.global.error.NotFoundException
import com.lookation.repository.ContentRepository
import com.lookation.repository.HelpRow
import com.lookation.repository.NoticeRow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContentService(private val contentRepository: ContentRepository) {

    @Transactional(readOnly = true)
    fun notices(cursor: Long?, limit: Int): PagedDto<NoticeDto> {
        validateLimit(limit)
        val rows = contentRepository.listNotices(cursor, limit)
        return PagedDto(rows.map { it.toResponse() }, if (rows.size == limit) rows.last().id else null)
    }

    @Transactional(readOnly = true)
    fun notice(id: Long): NoticeDto =
        (contentRepository.findNotice(id) ?: throw NotFoundException("공지사항을 찾을 수 없습니다")).toResponse()

    @Transactional
    fun createNotice(form: NoticeForm): NoticeDto =
        contentRepository.insertNotice(form.title, form.content, form.important)
            .let { requireNotNull(contentRepository.findNotice(it)).toResponse() }

    @Transactional
    fun updateNotice(id: Long, form: NoticeForm): NoticeDto {
        contentRepository.findNotice(id) ?: throw NotFoundException("공지사항을 찾을 수 없습니다")
        contentRepository.updateNotice(id, form.title, form.content, form.important)
        return requireNotNull(contentRepository.findNotice(id)).toResponse()
    }

    @Transactional
    fun deleteNotice(id: Long) {
        if (contentRepository.deleteNotice(id) != 1) throw NotFoundException("공지사항을 찾을 수 없습니다")
    }

    @Transactional(readOnly = true)
    fun helps(category: String?, cursor: Long?, limit: Int): PagedDto<HelpDto> {
        validateLimit(limit)
        val rows = contentRepository.listHelps(category, cursor, limit)
        return PagedDto(rows.map { it.toResponse() }, if (rows.size == limit) rows.last().id else null)
    }

    @Transactional(readOnly = true)
    fun help(id: Long): HelpDto =
        (contentRepository.findHelp(id) ?: throw NotFoundException("도움말을 찾을 수 없습니다")).toResponse()

    @Transactional
    fun createHelp(form: HelpForm): HelpDto =
        contentRepository.insertHelp(form.category, form.title, form.content)
            .let { requireNotNull(contentRepository.findHelp(it)).toResponse() }

    @Transactional
    fun updateHelp(id: Long, form: HelpForm): HelpDto {
        contentRepository.findHelp(id) ?: throw NotFoundException("도움말을 찾을 수 없습니다")
        contentRepository.updateHelp(id, form.category, form.title, form.content)
        return requireNotNull(contentRepository.findHelp(id)).toResponse()
    }

    @Transactional
    fun deleteHelp(id: Long) {
        if (contentRepository.deleteHelp(id) != 1) throw NotFoundException("도움말을 찾을 수 없습니다")
    }

    private fun validateLimit(limit: Int) {
        if (limit !in 1..50) throw BadRequestException("limit은 1~50입니다")
    }

    private fun NoticeRow.toResponse() = NoticeDto(id, title, content, important, createdAt, updatedAt)
    private fun HelpRow.toResponse() = HelpDto(id, category, title, content, createdAt, updatedAt)
}
