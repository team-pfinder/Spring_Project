package com.lookation.service

import com.lookation.controller.form.ApplySanctionForm
import com.lookation.dto.SanctionResponse
import com.lookation.global.error.ConflictException
import com.lookation.global.error.ForbiddenException
import com.lookation.global.error.NotFoundException
import com.lookation.repository.SanctionRepository
import com.lookation.repository.SanctionRow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
class SanctionService(private val sanctionRepository: SanctionRepository) {

    @Transactional(readOnly = true)
    fun checkWritable(accountId: Long) {
        val active = sanctionRepository.findActive(accountId, OffsetDateTime.now())
        if (active.isNotEmpty()) {
            val until = active.mapNotNull { it.endsAt }.minOrNull()
            val message = if (until == null) "이용이 제한된 계정입니다" else "이용이 제한된 계정입니다. 해제 예정: $until"
            throw ForbiddenException(message)
        }
    }

    @Transactional
    fun apply(adminId: Long, form: ApplySanctionForm): SanctionResponse {
        val id = sanctionRepository.insert(
            accountId = form.accountId,
            reason = form.reason,
            level = form.level,
            endsAt = form.endsAt,
            adminId = adminId,
        )
        return requireNotNull(sanctionRepository.findById(id)).toResponse()
    }

    @Transactional
    fun revoke(id: Long) {
        sanctionRepository.findById(id) ?: throw NotFoundException("제재를 찾을 수 없습니다")
        if (sanctionRepository.revoke(id) != 1) {
            throw ConflictException("이미 해제된 제재입니다")
        }
    }

    private fun SanctionRow.toResponse() = SanctionResponse(
        id = id,
        accountId = accountId,
        reason = reason,
        level = level,
        startsAt = startsAt,
        endsAt = endsAt,
    )
}
