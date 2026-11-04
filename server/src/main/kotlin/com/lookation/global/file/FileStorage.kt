package com.lookation.global.file

import com.lookation.global.error.BadRequestException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID

data class StoredFile(
    val key: String,
    val originalName: String,
    val contentType: String,
    val size: Long,
)

@Service
class FileStorage(@Value("\${lookation.upload-dir}") uploadDir: String) {

    private val directory: Path = Paths.get(uploadDir).toAbsolutePath().normalize()

    init {
        Files.createDirectories(directory)
    }

    fun store(file: MultipartFile): StoredFile {
        if (file.isEmpty) throw BadRequestException("빈 파일입니다")
        if (file.size > MAX_BYTES) throw BadRequestException("파일은 10MB 이하만 가능합니다")
        val contentType = file.contentType
        val extension = EXTENSIONS[contentType]
            ?: throw BadRequestException("jpg, png, webp만 업로드할 수 있습니다")
        val key = UUID.randomUUID().toString() + extension
        val target = directory.resolve(key).normalize()
        if (target.parent != directory) throw BadRequestException("잘못된 파일 경로입니다")
        file.inputStream.use { Files.copy(it, target) }
        return StoredFile(key, file.originalFilename ?: key, requireNotNull(contentType), file.size)
    }

    companion object {
        private const val MAX_BYTES = 10L * 1024 * 1024
        private val EXTENSIONS = mapOf(
            "image/jpeg" to ".jpg",
            "image/png" to ".png",
            "image/webp" to ".webp",
        )
    }
}
