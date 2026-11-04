package com.lookation.space.application

import com.lookation.global.error.BadRequestException
import com.lookation.global.error.ConflictException
import com.lookation.global.error.ForbiddenException
import com.lookation.global.error.NotFoundException
import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import com.lookation.space.infrastructure.SpaceRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID

@Service
class SpaceImageService(
    private val spaceRepository: SpaceRepository,
    private val dsl: DSLContext,
    @Value("\${lookation.upload-dir}") uploadDir: String,
) {

    private val directory: Path = Paths.get(uploadDir).toAbsolutePath().normalize()

    init {
        Files.createDirectories(directory)
    }

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
        if (file.isEmpty) {
            throw BadRequestException("빈 파일입니다")
        }
        if (file.size > MAX_BYTES) {
            throw BadRequestException("이미지는 10MB 이하만 가능합니다")
        }
        val contentType = file.contentType
        val extension = EXTENSIONS[contentType]
            ?: throw BadRequestException("jpg, png, webp만 업로드할 수 있습니다")
        if (spaceRepository.countImages(spaceId) >= MAX_IMAGES) {
            throw ConflictException("이미지는 공간당 ${MAX_IMAGES}장까지 가능합니다")
        }

        val storageKey = UUID.randomUUID().toString() + extension
        val target = directory.resolve(storageKey).normalize()
        if (target.parent != directory) {
            throw BadRequestException("잘못된 파일 경로입니다")
        }
        file.inputStream.use { Files.copy(it, target) }

        dsl.insertInto(ATTACHMENTS)
            .set(ATTACHMENTS.OWNER_TYPE, "SPACE")
            .set(ATTACHMENTS.OWNER_ID, spaceId)
            .set(ATTACHMENTS.STORAGE_KEY, storageKey)
            .set(ATTACHMENTS.ORIGINAL_NAME, file.originalFilename ?: storageKey)
            .set(ATTACHMENTS.CONTENT_TYPE, requireNotNull(contentType))
            .set(ATTACHMENTS.SIZE_BYTES, file.size)
            .execute()

        return "/uploads/$storageKey"
    }

    @Transactional(readOnly = true)
    fun list(spaceId: Long): List<String> {
        spaceRepository.findById(spaceId) ?: throw NotFoundException("공간을 찾을 수 없습니다")
        return spaceRepository.imageKeys(spaceId).map { "/uploads/$it" }
    }

    companion object {
        private const val MAX_BYTES = 10L * 1024 * 1024
        private const val MAX_IMAGES = 10
        private val EDITABLE_STATUSES = setOf("DRAFT", "INSPECTION_REJECTED")
        private val EXTENSIONS = mapOf(
            "image/jpeg" to ".jpg",
            "image/png" to ".png",
            "image/webp" to ".webp",
        )
    }
}
