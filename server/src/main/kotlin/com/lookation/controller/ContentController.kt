package com.lookation.controller

import com.lookation.dto.HelpDto
import com.lookation.dto.NoticeDto
import com.lookation.dto.PagedDto
import com.lookation.service.ContentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import com.lookation.controller.form.HelpForm
import com.lookation.controller.form.NoticeForm

@Tag(
    name = "고객센터",
    description = "공지사항과 도움말을 조회·관리합니다. 조회는 누구나 가능하고, 작성·수정·삭제는 `ADMIN` 역할만 가능합니다.",
)
@RestController
@RequestMapping("/api/v1")
class ContentController(private val contentService: ContentService) {

    @Operation(
        summary = "공지사항 목록",
        description = "공지사항을 최신순으로 커서 페이징해 돌려줍니다. 인증이 필요 없습니다. `important`가 `true`인 항목은 상단 고정 공지입니다. `limit`이 1~50을 벗어나면 400입니다.",
    )
    @GetMapping("/notices")
    fun notices(
        @Parameter(description = "다음 페이지를 조회할 때 직전 응답의 `nextCursor`를 그대로 넣습니다. 첫 페이지는 생략합니다.", example = "42")
        @RequestParam(required = false) cursor: Long?,
        @Parameter(description = "한 페이지에 담을 항목 수입니다. 1~50 사이여야 합니다.", example = "20")
        @RequestParam(defaultValue = "20") limit: Int,
    ): PagedDto<NoticeDto> =
        contentService.notices(cursor, limit)

    @Operation(
        summary = "공지사항 상세 조회",
        description = "공지사항 한 건을 조회합니다. 인증이 필요 없습니다. 없는 id면 404입니다.",
    )
    @GetMapping("/notices/{id}")
    fun notice(
        @Parameter(description = "공지사항 id", example = "1")
        @PathVariable id: Long,
    ): NoticeDto = contentService.notice(id)

    @Operation(
        summary = "도움말 목록",
        description = "도움말을 최신순으로 커서 페이징해 돌려줍니다. 인증이 필요 없습니다. `category`를 주면 해당 카테고리만 필터링하고, 생략하면 전체를 조회합니다. `limit`이 1~50을 벗어나면 400입니다.",
    )
    @GetMapping("/helps")
    fun helps(
        @Parameter(description = "도움말 카테고리 필터입니다. 생략하면 전체 카테고리를 조회합니다.", example = "예약")
        @RequestParam(required = false) category: String?,
        @Parameter(description = "다음 페이지를 조회할 때 직전 응답의 `nextCursor`를 그대로 넣습니다. 첫 페이지는 생략합니다.", example = "42")
        @RequestParam(required = false) cursor: Long?,
        @Parameter(description = "한 페이지에 담을 항목 수입니다. 1~50 사이여야 합니다.", example = "20")
        @RequestParam(defaultValue = "20") limit: Int,
    ): PagedDto<HelpDto> = contentService.helps(category, cursor, limit)

    @Operation(
        summary = "도움말 상세 조회",
        description = "도움말 한 건을 조회합니다. 인증이 필요 없습니다. 없는 id면 404입니다.",
    )
    @GetMapping("/helps/{id}")
    fun help(
        @Parameter(description = "도움말 id", example = "1")
        @PathVariable id: Long,
    ): HelpDto = contentService.help(id)

    @Operation(
        summary = "공지사항 작성",
        description = "새 공지사항을 만듭니다. `ADMIN` 역할이 필요하며, 토큰이 없거나 권한이 부족하면 401 또는 403입니다. `important`를 생략하면 `false`로 저장됩니다. 성공하면 200과 함께 생성된 공지사항을 돌려줍니다.",
    )
    @PostMapping("/admin/notices")
    @PreAuthorize("hasRole('ADMIN')")
    fun createNotice(@Valid @RequestBody form: NoticeForm): NoticeDto = contentService.createNotice(form)

    @Operation(
        summary = "공지사항 수정",
        description = "기존 공지사항을 폼 값으로 전부 교체합니다. `ADMIN` 역할이 필요합니다. 없는 id면 404이고, 성공하면 200과 함께 수정된 공지사항을 돌려줍니다.",
    )
    @PutMapping("/admin/notices/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateNotice(
        @Parameter(description = "공지사항 id", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody form: NoticeForm,
    ): NoticeDto =
        contentService.updateNotice(id, form)

    @Operation(
        summary = "공지사항 삭제",
        description = "공지사항을 삭제합니다. `ADMIN` 역할이 필요합니다. 성공하면 본문 없이 204이고, 없는 id면 404입니다.",
    )
    @DeleteMapping("/admin/notices/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteNotice(
        @Parameter(description = "공지사항 id", example = "1")
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        contentService.deleteNotice(id)
        return ResponseEntity.noContent().build()
    }

    @Operation(
        summary = "도움말 작성",
        description = "새 도움말을 만듭니다. `ADMIN` 역할이 필요하며, 토큰이 없거나 권한이 부족하면 401 또는 403입니다. 성공하면 200과 함께 생성된 도움말을 돌려줍니다.",
    )
    @PostMapping("/admin/helps")
    @PreAuthorize("hasRole('ADMIN')")
    fun createHelp(@Valid @RequestBody form: HelpForm): HelpDto = contentService.createHelp(form)

    @Operation(
        summary = "도움말 수정",
        description = "기존 도움말을 폼 값으로 전부 교체합니다. `ADMIN` 역할이 필요합니다. 없는 id면 404이고, 성공하면 200과 함께 수정된 도움말을 돌려줍니다.",
    )
    @PutMapping("/admin/helps/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateHelp(
        @Parameter(description = "도움말 id", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody form: HelpForm,
    ): HelpDto =
        contentService.updateHelp(id, form)

    @Operation(
        summary = "도움말 삭제",
        description = "도움말을 삭제합니다. `ADMIN` 역할이 필요합니다. 성공하면 본문 없이 204이고, 없는 id면 404입니다.",
    )
    @DeleteMapping("/admin/helps/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteHelp(
        @Parameter(description = "도움말 id", example = "1")
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        contentService.deleteHelp(id)
        return ResponseEntity.noContent().build()
    }
}
