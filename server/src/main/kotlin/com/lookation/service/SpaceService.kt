package com.lookation.service

import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ConflictException
import com.lookation.global.error.ForbiddenException
import com.lookation.global.error.NotFoundException
import com.lookation.repository.HostProfileRepository
import com.lookation.dto.PendingInspectionResponse
import com.lookation.controller.SpaceForm
import com.lookation.dto.SpaceSearchResponse
import com.lookation.dto.SpaceSummaryResponse
import com.lookation.dto.SpaceResponse
import com.lookation.domain.SpaceType
import com.lookation.repository.SpaceRepository
import com.lookation.repository.SpaceRow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpaceService(
    private val spaceRepository: SpaceRepository,
    private val hostProfileRepository: HostProfileRepository,
    private val priceRuleRepository: com.lookation.repository.PriceRuleRepository,
) {

    @Transactional
    fun create(hostId: Long, form: SpaceForm): SpaceResponse {
        validate(form)
        val spaceId = spaceRepository.insert(
            hostId = hostId,
            name = form.name,
            shortIntro = form.shortIntro,
            description = form.description,
            address = form.address,
            addressDetail = form.addressDetail,
            spaceType = form.spaceType,
            minCapacity = form.minCapacity,
            maxCapacity = form.maxCapacity,
            useHours = form.useHours,
            caution = form.caution,
            contactEmail = form.contactEmail,
            contactPhone = form.contactPhone,
            websiteUrl = form.websiteUrl,
            approvalRequired = form.approvalRequired,
        )
        spaceRepository.replaceFacilities(spaceId, form.facilities.toSet())
        return response(spaceId)
    }

    @Transactional
    fun update(accountId: Long, spaceId: Long, form: SpaceForm): SpaceResponse {
        val space = owned(accountId, spaceId)
        if (space.status !in EDITABLE_STATUSES) {
            throw ConflictException("검수 중이거나 게시된 공간은 수정할 수 없습니다")
        }
        validate(form)
        spaceRepository.update(
            id = spaceId,
            name = form.name,
            shortIntro = form.shortIntro,
            description = form.description,
            address = form.address,
            addressDetail = form.addressDetail,
            spaceType = form.spaceType,
            minCapacity = form.minCapacity,
            maxCapacity = form.maxCapacity,
            useHours = form.useHours,
            caution = form.caution,
            contactEmail = form.contactEmail,
            contactPhone = form.contactPhone,
            websiteUrl = form.websiteUrl,
            approvalRequired = form.approvalRequired,
        )
        spaceRepository.replaceFacilities(spaceId, form.facilities.toSet())
        return response(spaceId)
    }

    @Transactional(readOnly = true)
    fun get(accountId: Long?, spaceId: Long): SpaceResponse {
        val space = spaceRepository.findById(spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.status != "PUBLISHED" && space.hostId != accountId) {
            throw NotFoundException("공간을 찾을 수 없습니다")
        }
        return response(space)
    }

    @Transactional
    fun close(accountId: Long, spaceId: Long): SpaceResponse {
        val space = owned(accountId, spaceId)
        if (space.status != "PUBLISHED") {
            throw ConflictException("게시 중인 공간만 중단할 수 있습니다")
        }
        spaceRepository.updateStatus(spaceId, "PUBLISHED", "CLOSED")
        return response(spaceId)
    }

    @Transactional
    fun reopen(accountId: Long, spaceId: Long): SpaceResponse {
        val space = owned(accountId, spaceId)
        if (space.status != "CLOSED") {
            throw ConflictException("중단된 공간만 재개할 수 있습니다")
        }
        spaceRepository.updateStatus(spaceId, "CLOSED", "DRAFT")
        return response(spaceId)
    }

    @Transactional
    fun delete(accountId: Long, spaceId: Long) {
        owned(accountId, spaceId)
        if (spaceRepository.hasActiveReservations(spaceId)) {
            throw ConflictException("진행 중인 예약이 있어 삭제할 수 없습니다")
        }
        spaceRepository.softDelete(spaceId)
    }

    @Transactional
    fun submitInspection(accountId: Long, spaceId: Long): SpaceResponse {
        val space = owned(accountId, spaceId)
        if (space.status !in EDITABLE_STATUSES) {
            throw ConflictException("검수 신청할 수 없는 상태입니다")
        }
        if (!hostProfileRepository.exists(accountId)) {
            throw ConflictException("호스트 프로필을 먼저 등록해야 합니다")
        }
        if (spaceRepository.countImages(spaceId) < 1) {
            throw ConflictException("검수 신청에는 이미지가 최소 1장 필요합니다")
        }
        if (priceRuleRepository.count(spaceId) < 1) {
            throw ConflictException("검수 신청에는 가격 규칙이 최소 1개 필요합니다")
        }
        spaceRepository.updateStatus(spaceId, space.status, "PENDING_INSPECTION")
        spaceRepository.insertInspection(spaceId, "PENDING_INSPECTION", null, null)
        return response(spaceId)
    }

    @Transactional(readOnly = true)
    fun search(
        type: String?,
        keyword: String?,
        guests: Int?,
        minPrice: Long?,
        maxPrice: Long?,
        from: java.time.OffsetDateTime?,
        to: java.time.OffsetDateTime?,
        cursor: Long?,
        limit: Int,
    ): SpaceSearchResponse {
        if (limit !in 1..50) throw BadRequestException("limit은 1~50입니다")
        if ((from == null) != (to == null)) throw BadRequestException("from과 to는 함께 지정해야 합니다")
        if (from != null && to != null && !to.isAfter(from)) {
            throw BadRequestException("to는 from 이후여야 합니다")
        }
        val rows = spaceRepository.search(
            type = type,
            keyword = keyword,
            guests = guests,
            minPrice = minPrice,
            maxPrice = maxPrice,
            from = from,
            to = to,
            cursor = cursor,
            limit = limit,
        )
        return SpaceSearchResponse(
            items = rows.map {
                SpaceSummaryResponse(
                    id = it.id,
                    name = it.name,
                    shortIntro = it.shortIntro,
                    spaceType = it.spaceType,
                    address = it.address,
                    minCapacity = it.minCapacity,
                    maxCapacity = it.maxCapacity,
                    minPrice = it.minPrice,
                )
            },
            nextCursor = if (rows.size == limit) rows.last().id else null,
        )
    }

    @Transactional(readOnly = true)
    fun pendingInspections(): List<PendingInspectionResponse> =
        spaceRepository.pendingInspections().map {
            val space = spaceRepository.findById(it.spaceId)
                ?: throw NotFoundException("공간을 찾을 수 없습니다")
            PendingInspectionResponse(
                spaceId = it.spaceId,
                spaceName = space.name,
                hostId = space.hostId,
                requestedAt = it.requestedAt,
            )
        }

    @Transactional
    fun decide(adminId: Long, spaceId: Long, approved: Boolean, reason: String?): SpaceResponse {
        val space = spaceRepository.findById(spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.status != "PENDING_INSPECTION") {
            throw ConflictException("검수 대기 상태가 아닙니다")
        }
        if (!approved && reason.isNullOrBlank()) {
            throw BadRequestException("반려 사유가 필요합니다")
        }
        val nextStatus = if (approved) "PUBLISHED" else "INSPECTION_REJECTED"
        spaceRepository.updateStatus(spaceId, "PENDING_INSPECTION", nextStatus)
        spaceRepository.insertInspection(spaceId, nextStatus, reason, adminId)
        return response(spaceId)
    }

    private fun owned(accountId: Long, spaceId: Long): SpaceRow {
        val space = spaceRepository.findById(spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.hostId != accountId) {
            throw ForbiddenException("공간 소유자만 접근할 수 있습니다")
        }
        return space
    }

    private fun validate(form: SpaceForm) {
        if (SpaceType.entries.none { it.name == form.spaceType }) {
            throw BadRequestException("알 수 없는 공간유형입니다")
        }
        if (form.minCapacity < 1 || form.maxCapacity < form.minCapacity) {
            throw BadRequestException("최소/최대 인원이 올바르지 않습니다")
        }
    }

    private fun response(spaceId: Long): SpaceResponse =
        response(requireNotNull(spaceRepository.findById(spaceId)))

    private fun response(space: SpaceRow) = SpaceResponse(
        id = space.id,
        name = space.name,
        shortIntro = space.shortIntro,
        description = space.description,
        address = space.address,
        addressDetail = space.addressDetail,
        spaceType = space.spaceType,
        minCapacity = space.minCapacity,
        maxCapacity = space.maxCapacity,
        useHours = space.useHours,
        caution = space.caution,
        contactEmail = space.contactEmail,
        contactPhone = space.contactPhone,
        websiteUrl = space.websiteUrl,
        status = space.status,
        facilities = spaceRepository.facilities(space.id),
        images = spaceRepository.imageKeys(space.id).map { "/uploads/$it" },
        approvalRequired = space.approvalRequired,
    )

    companion object {
        private val EDITABLE_STATUSES = setOf("DRAFT", "INSPECTION_REJECTED")
    }
}
