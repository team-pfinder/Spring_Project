package com.lookation.global.seed

import com.lookation.domain.AttachmentOwnerType
import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import com.lookation.jooq.tables.Reviews.REVIEWS
import org.jooq.DSLContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * Copies legacy images from `Lookation/WebContent/images` into the upload directory under
 * deterministic `seed/{ownerType}-{ownerId}-{sortOrder}.{ext}` keys and inserts the matching
 * `attachments` rows.
 *
 * A missing source file is logged and skipped, never fatal, so the seed also runs on a checkout
 * that does not ship the legacy images.
 */
@Component
@Order(AttachmentSeed.ORDER)
class AttachmentSeed(
    private val dsl: DSLContext,
    @Value("\${lookation.upload-dir}") uploadDir: String,
    @Value("\${lookation.seed.image-source:../Lookation/WebContent/images}") imageSource: String,
) : SeedStep {

    private val uploadRoot: Path = Paths.get(uploadDir).toAbsolutePath().normalize()
    private val configuredSource: Path = Paths.get(imageSource)

    override val name = "첨부 이미지"

    @Transactional
    override fun seed(): SeedResult {
        var inserted = 0
        var skipped = 0

        seedAttachments().forEach { attachment ->
            val source = sourceRoot().resolve(attachment.sourceName).normalize()
            if (!Files.isRegularFile(source)) {
                log.warn("시드 이미지 원본이 없어 건너뜁니다: {}", source)
                skipped++
                return@forEach
            }
            val contentType = CONTENT_TYPES[extensionOf(attachment.sourceName)]
            if (contentType == null) {
                log.warn("지원하지 않는 시드 이미지 형식이라 건너뜁니다: {}", source)
                skipped++
                return@forEach
            }

            val key = "seed/${attachment.ownerType.name.lowercase()}-${attachment.ownerId}-${attachment.sortOrder}.${extensionOf(attachment.sourceName)}"
            val target = uploadRoot.resolve(key).normalize()
            val sourceSize = Files.size(source)

            val existing = dsl.select(ATTACHMENTS.STORAGE_KEY, ATTACHMENTS.SIZE_BYTES)
                .from(ATTACHMENTS)
                .where(
                    ATTACHMENTS.OWNER_TYPE.eq(attachment.ownerType.name)
                        .and(ATTACHMENTS.OWNER_ID.eq(attachment.ownerId))
                        .and(ATTACHMENTS.SORT_ORDER.eq(attachment.sortOrder)),
                )
                .fetchOne()
            if (existing != null) {
                if (existing.value1() != key) {
                    log.warn("같은 순서에 다른 첨부가 이미 있어 건너뜁니다: {}", key)
                    skipped++
                    return@forEach
                }
                copyIfNeeded(source, target)
                if (existing.value2() != sourceSize) {
                    dsl.update(ATTACHMENTS)
                        .set(ATTACHMENTS.SIZE_BYTES, sourceSize)
                        .where(
                            ATTACHMENTS.OWNER_TYPE.eq(attachment.ownerType.name)
                                .and(ATTACHMENTS.OWNER_ID.eq(attachment.ownerId))
                                .and(ATTACHMENTS.SORT_ORDER.eq(attachment.sortOrder)),
                        )
                        .execute()
                }
                skipped++
                return@forEach
            }

            copyIfNeeded(source, target)
            dsl.insertInto(ATTACHMENTS)
                .set(ATTACHMENTS.OWNER_TYPE, attachment.ownerType.name)
                .set(ATTACHMENTS.OWNER_ID, attachment.ownerId)
                .set(ATTACHMENTS.STORAGE_KEY, key)
                .set(ATTACHMENTS.ORIGINAL_NAME, attachment.sourceName)
                .set(ATTACHMENTS.CONTENT_TYPE, contentType)
                .set(ATTACHMENTS.SIZE_BYTES, sourceSize)
                .set(ATTACHMENTS.SORT_ORDER, attachment.sortOrder)
                .execute()
            inserted++
        }

        return SeedResult(inserted = inserted, skipped = skipped)
    }

    private fun seedAttachments(): List<SeedAttachment> = buildList {
        SPACE_IMAGES.forEach { (spaceName, files) ->
            val spaceId = dsl.spaceId(spaceName)
            files.forEachIndexed { index, file ->
                add(SeedAttachment(AttachmentOwnerType.SPACE, spaceId, index.toShort(), file))
            }
        }
        dsl.reviewIdOfCompletedReservation()?.let { reviewId ->
            add(SeedAttachment(AttachmentOwnerType.REVIEW, reviewId, 0, REVIEW_IMAGE))
        }
    }

    private fun DSLContext.reviewIdOfCompletedReservation(): Long? =
        reservationId("seed:reservation:completed")?.let { reservationId ->
            select(REVIEWS.ID).from(REVIEWS).where(REVIEWS.RESERVATION_ID.eq(reservationId)).fetchOne(REVIEWS.ID)
        }

    /**
     * 원본 이미지 폴더를 찾는다. 기본값 `../Lookation/...`은 작업 디렉토리가 `server/`일 때만 맞고,
     * IntelliJ처럼 레포 루트에서 실행하면 `./Lookation/...`이 맞으므로, 존재하는 쪽을 쓴다.
     */
    private fun sourceRoot(): Path {
        if (Files.isDirectory(configuredSource)) return configuredSource
        val fromRoot = configuredSource
            .takeIf { it.startsWith("..") }
            ?.let { it.subpath(1, it.nameCount) }
            ?: Paths.get("Lookation", "WebContent", "images")
        return if (Files.isDirectory(fromRoot)) fromRoot else configuredSource
    }

    private fun copyIfNeeded(source: Path, target: Path) {
        if (Files.isRegularFile(target) && Files.size(target) == Files.size(source)) return
        Files.createDirectories(target.parent)
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
    }

    private fun extensionOf(fileName: String): String =
        fileName.substringAfterLast('.', missingDelimiterValue = "").lowercase()

    private data class SeedAttachment(
        val ownerType: AttachmentOwnerType,
        val ownerId: Long,
        val sortOrder: Short,
        val sourceName: String,
    )

    private companion object {
        const val ORDER = 80
        const val REVIEW_IMAGE = "main-3.jpg"

        val CONTENT_TYPES = mapOf(
            "jpg" to "image/jpeg",
            "jpeg" to "image/jpeg",
            "png" to "image/png",
            "webp" to "image/webp",
        )

        val SPACE_IMAGES = linkedMapOf(
            "시드 파티룸" to listOf("main-1.jpg", "main-2.jpg"),
            "시드 한강 루프탑" to listOf("main-2.jpg"),
            "시드 부산 파티룸" to listOf("main-host-1.jpg"),
            "시드 판교 워크샵" to listOf("bg_3.jpg"),
            "시드 빈 공간" to listOf("main-3.jpg"),
            "시드 루프탑" to listOf("main-host-1.jpg"),
            "시드 브라이덜샤워" to listOf("main-host-2.jpg"),
            "시드 클럽" to listOf("main-host-3.jpg"),
            "시드 워크샵" to listOf("bg_3.jpg"),
        )

        val log = LoggerFactory.getLogger(AttachmentSeed::class.java)
    }
}
