package com.lookation.service

import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ConflictException
import com.lookation.global.error.ForbiddenException
import com.lookation.global.error.NotFoundException
import com.lookation.repository.HostProfileRepository
import com.lookation.dto.PendingInspectionDto
import com.lookation.controller.form.SpaceForm
import com.lookation.dto.SpaceSearchDto
import com.lookation.dto.SpaceSummaryDto
import com.lookation.dto.SpaceDto
import com.lookation.domain.SpaceStatus
import com.lookation.domain.SpaceType
import com.lookation.domain.SpaceSort
import com.lookation.repository.SpaceCursor
import com.lookation.repository.SpaceRepository
import com.lookation.repository.SpaceRow
import com.lookation.repository.SpaceSummary
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets
import java.util.Base64

@Service
class SpaceService(
    private val spaceRepository: SpaceRepository,
    private val hostProfileRepository: HostProfileRepository,
    private val priceRuleRepository: com.lookation.repository.PriceRuleRepository,
) {

    @Transactional
    fun create(hostId: Long, form: SpaceForm): SpaceDto {
        validate(form)
        val spaceId = spaceRepository.insert(
            hostId = hostId,
            name = form.name,
            shortIntro = form.shortIntro,
            description = form.description,
            address = form.address,
            addressDetail = form.addressDetail,
            sido = form.sido,
            sigungu = form.sigungu,
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
    fun update(accountId: Long, spaceId: Long, form: SpaceForm): SpaceDto {
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
            sido = form.sido,
            sigungu = form.sigungu,
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
    fun get(accountId: Long?, spaceId: Long): SpaceDto {
        val space = spaceRepository.findById(spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.status != SpaceStatus.PUBLISHED && space.hostId != accountId) {
            throw NotFoundException("공간을 찾을 수 없습니다")
        }
        return response(space)
    }

    @Transactional
    fun close(accountId: Long, spaceId: Long): SpaceDto {
        val space = owned(accountId, spaceId)
        if (space.status != SpaceStatus.PUBLISHED) {
            throw ConflictException("게시 중인 공간만 중단할 수 있습니다")
        }
        spaceRepository.updateStatus(spaceId, SpaceStatus.PUBLISHED, SpaceStatus.CLOSED)
        return response(spaceId)
    }

    @Transactional
    fun reopen(accountId: Long, spaceId: Long): SpaceDto {
        val space = owned(accountId, spaceId)
        if (space.status != SpaceStatus.CLOSED) {
            throw ConflictException("중단된 공간만 재개할 수 있습니다")
        }
        spaceRepository.updateStatus(spaceId, SpaceStatus.CLOSED, SpaceStatus.DRAFT)
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
    fun submitInspection(accountId: Long, spaceId: Long): SpaceDto {
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
        spaceRepository.updateStatus(spaceId, space.status, SpaceStatus.PENDING_INSPECTION)
        spaceRepository.insertInspection(spaceId, SpaceStatus.PENDING_INSPECTION, null, null)
        return response(spaceId)
    }

    @Transactional(readOnly = true)
    fun search(
        type: String?,
        keyword: String?,
        sido: String?,
        sigungu: String?,
        guests: Int?,
        minPrice: Long?,
        maxPrice: Long?,
        from: java.time.OffsetDateTime?,
        to: java.time.OffsetDateTime?,
        sort: String?,
        cursor: String?,
        limit: Int,
    ): SpaceSearchDto {
        if (limit !in 1..50) throw BadRequestException("limit은 1~50입니다")
        if ((from == null) != (to == null)) throw BadRequestException("from과 to는 함께 지정해야 합니다")
        if (from != null && to != null && !to.isAfter(from)) {
            throw BadRequestException("to는 from 이후여야 합니다")
        }
        val normalizedSort = try {
            SpaceSort.parse(sort)
        } catch (_: IllegalArgumentException) {
            throw BadRequestException("sort는 latest, price_asc, price_desc, rating 중 하나입니다")
        }
        val rows = spaceRepository.search(
            type = type,
            keyword = keyword,
            sido = sido,
            sigungu = sigungu,
            guests = guests,
            minPrice = minPrice,
            maxPrice = maxPrice,
            from = from,
            to = to,
            sort = normalizedSort,
            cursor = decodeCursor(cursor, normalizedSort),
            limit = limit,
        )
        return SpaceSearchDto(
            items = rows.map {
                SpaceSummaryDto(
                    id = it.id,
                    name = it.name,
                    shortIntro = it.shortIntro,
                    spaceType = SpaceType.valueOf(it.spaceType),
                    address = it.address,
                    sido = it.sido,
                    sigungu = it.sigungu,
                    minCapacity = it.minCapacity,
                    maxCapacity = it.maxCapacity,
                    minPrice = it.minPrice,
                    thumbnailUrl = it.thumbnailUrl,
                    rating = it.rating,
                    reviewCount = it.reviewCount,
                )
            },
            nextCursor = if (rows.size == limit) encodeCursor(normalizedSort, rows.last()) else null,
        )
    }

    private fun encodeCursor(sort: SpaceSort, row: SpaceSummary): String {
        val value = when (sort) {
            SpaceSort.PRICE_ASC, SpaceSort.PRICE_DESC -> row.minPrice?.toString().orEmpty()
            SpaceSort.RATING -> row.rating?.toString().orEmpty()
            SpaceSort.LATEST -> ""
        }
        val raw = "${sort.key}|$value|${row.id}"
        return Base64.getUrlEncoder().withoutPadding()
            .encodeToString(raw.toByteArray(StandardCharsets.UTF_8))
    }

    private fun decodeCursor(raw: String?, sort: SpaceSort): SpaceCursor? {
        if (raw.isNullOrBlank()) return null
        // 기존 숫자 커서(id)도 최신순에서는 계속 받는다.
        raw.toLongOrNull()?.let {
            if (sort != SpaceSort.LATEST) throw BadRequestException("커서가 올바르지 않습니다")
            return SpaceCursor(sort, null, it)
        }
        val decoded = try {
            String(Base64.getUrlDecoder().decode(raw), StandardCharsets.UTF_8)
        } catch (_: IllegalArgumentException) {
            throw BadRequestException("커서가 올바르지 않습니다")
        }
        val parts = decoded.split("|")
        if (parts.size != 3 || parts[0] != sort.key) {
            throw BadRequestException("커서가 올바르지 않습니다")
        }
        val id = parts[2].toLongOrNull() ?: throw BadRequestException("커서가 올바르지 않습니다")
        return SpaceCursor(sort, parts[1].ifBlank { null }, id)
    }

    @Transactional(readOnly = true)
    fun pendingInspections(): List<PendingInspectionDto> =
        spaceRepository.pendingInspections().map {
            val space = spaceRepository.findById(it.spaceId)
                ?: throw NotFoundException("공간을 찾을 수 없습니다")
            PendingInspectionDto(
                spaceId = it.spaceId,
                spaceName = space.name,
                hostId = space.hostId,
                requestedAt = it.requestedAt,
            )
        }

    @Transactional
    fun decide(adminId: Long, spaceId: Long, approved: Boolean, reason: String?): SpaceDto {
        val space = spaceRepository.findById(spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.status != SpaceStatus.PENDING_INSPECTION) {
            throw ConflictException("검수 대기 상태가 아닙니다")
        }
        if (!approved && reason.isNullOrBlank()) {
            throw BadRequestException("반려 사유가 필요합니다")
        }
        val nextStatus = if (approved) SpaceStatus.PUBLISHED else SpaceStatus.INSPECTION_REJECTED
        spaceRepository.updateStatus(spaceId, SpaceStatus.PENDING_INSPECTION, nextStatus)
        spaceRepository.insertInspection(
            spaceId,
            if (approved) SpaceStatus.PUBLISHED else SpaceStatus.INSPECTION_REJECTED,
            reason,
            adminId,
        )
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

    private fun response(spaceId: Long): SpaceDto =
        response(requireNotNull(spaceRepository.findById(spaceId)))

    private fun response(space: SpaceRow): SpaceDto {
        val rating = spaceRepository.ratingSummary(space.id)
        return SpaceDto(
        id = space.id,
        name = space.name,
        shortIntro = space.shortIntro,
        description = space.description,
        address = space.address,
        addressDetail = space.addressDetail,
        sido = space.sido,
        sigungu = space.sigungu,
        spaceType = SpaceType.valueOf(space.spaceType),
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
        rating = rating.rating,
        reviewCount = rating.reviewCount,
        )
    }

    companion object {
        private val EDITABLE_STATUSES = setOf(SpaceStatus.DRAFT, SpaceStatus.INSPECTION_REJECTED)
    }
}
