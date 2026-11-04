package com.lookation.controller

import com.lookation.dto.HelpListDto
import com.lookation.dto.HelpDto
import com.lookation.dto.NoticeListDto
import com.lookation.dto.NoticeDto
import com.lookation.service.ContentService
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

@RestController
@RequestMapping("/api/v1")
class ContentController(private val contentService: ContentService) {

    @GetMapping("/notices")
    fun notices(@RequestParam(required = false) cursor: Long?, @RequestParam(defaultValue = "20") limit: Int): NoticeListDto =
        contentService.notices(cursor, limit)

    @GetMapping("/notices/{id}")
    fun notice(@PathVariable id: Long): NoticeDto = contentService.notice(id)

    @GetMapping("/helps")
    fun helps(
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "20") limit: Int,
    ): HelpListDto = contentService.helps(category, cursor, limit)

    @GetMapping("/helps/{id}")
    fun help(@PathVariable id: Long): HelpDto = contentService.help(id)

    @PostMapping("/admin/notices")
    @PreAuthorize("hasRole('ADMIN')")
    fun createNotice(@Valid @RequestBody form: NoticeForm): NoticeDto = contentService.createNotice(form)

    @PutMapping("/admin/notices/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateNotice(@PathVariable id: Long, @Valid @RequestBody form: NoticeForm): NoticeDto =
        contentService.updateNotice(id, form)

    @DeleteMapping("/admin/notices/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteNotice(@PathVariable id: Long): ResponseEntity<Void> {
        contentService.deleteNotice(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/admin/helps")
    @PreAuthorize("hasRole('ADMIN')")
    fun createHelp(@Valid @RequestBody form: HelpForm): HelpDto = contentService.createHelp(form)

    @PutMapping("/admin/helps/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateHelp(@PathVariable id: Long, @Valid @RequestBody form: HelpForm): HelpDto =
        contentService.updateHelp(id, form)

    @DeleteMapping("/admin/helps/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteHelp(@PathVariable id: Long): ResponseEntity<Void> {
        contentService.deleteHelp(id)
        return ResponseEntity.noContent().build()
    }
}
