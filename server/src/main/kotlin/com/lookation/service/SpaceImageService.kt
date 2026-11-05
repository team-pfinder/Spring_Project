package com.lookation.service

import com.lookation.global.error.ConflictException
import com.lookation.global.error.ForbiddenException
import com.lookation.global.error.NotFoundException
import com.lookation.domain.SpaceStatus
import com.lookation.global.file.FileStorage
import com.lookation.repository.AttachmentRepository
import com.lookation.repository.SpaceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class SpaceImageService(
    private val spaceRepository: SpaceRepository,
    private val attachmentRepository: AttachmentRepository,
    private val fileStorage: FileStorage,
) {

    @Transactional
    fun upload(accountId: Long, spaceId: Long, file: MultipartFile): String {
        val space = spaceRepository.findById(spaceId)
            ?: throw NotFoundException("공간을 찾을 수 없습니다")
        if (space.hostId != accountId) {
            throw ForbiddenException("공간 소유자만 이미지를 올릴 수 있습니다")
        }
        if (space.status !in EDITABLE_STATUSES) {
            throw ConflictException("검수 중이거나 게시된 공간에는 이미지를 올릴 수 없습니다")
        }
        if (spaceRepository.countImages(spaceId) >= MAX_IMAGES) {
            throw ConflictException("이미지는 공간당 ${MAX_IMAGES}장까지 가능합니다")
        }
        val stored = fileStorage.store(file)
        attachmentRepository.insert(
            ownerType = "SPACE",
            ownerId = spaceId,
            storageKey = stored.key,
            originalName = stored.originalName,
            contentType = stored.contentType,
            size = stored.size,
        )

        return "/uploads/${stored.key}"
    }

    @Transactional(readOnly = true)
    fun list(spaceId: Long): List<String> {
        spaceRepository.findById(spaceId) ?: throw NotFoundException("공간을 찾을 수 없습니다")
        return spaceRepository.imageKeys(spaceId).map { "/uploads/$it" }
    }

    companion object {
        private const val MAX_IMAGES = 10
        private val EDITABLE_STATUSES = setOf(SpaceStatus.DRAFT, SpaceStatus.INSPECTION_REJECTED)
    }
}
