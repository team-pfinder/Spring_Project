package com.lookation.controller

import com.lookation.dto.ReportListResponse
import com.lookation.dto.ReportResponse
import com.lookation.global.error.UnauthorizedException
import com.lookation.service.ReportService
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import com.lookation.controller.form.ReportDecisionForm
import com.lookation.controller.form.CreateReportForm

@RestController
@RequestMapping("/api/v1")
class ReportController(private val reportService: ReportService) {

    @PostMapping("/reports")
    fun create(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: CreateReportForm,
    ): ReportResponse = reportService.create(jwt.accountId(), form)

    @GetMapping("/admin/reports")
    @PreAuthorize("hasRole('ADMIN')")
    fun list(
        @RequestParam(required = false) status: String?,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "20") limit: Int,
    ): ReportListResponse = reportService.list(status, cursor, limit)

    @PostMapping("/admin/reports/{id}/decision")
    @PreAuthorize("hasRole('ADMIN')")
    fun decide(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @Valid @RequestBody form: ReportDecisionForm,
    ): ReportResponse = reportService.decide(jwt.accountId(), id, form.accepted)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
