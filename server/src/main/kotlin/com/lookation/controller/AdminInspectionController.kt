package com.lookation.controller

import com.lookation.global.error.UnauthorizedException
import com.lookation.service.SpaceService
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.lookation.dto.PendingInspectionDto
import com.lookation.dto.SpaceDto
import com.lookation.controller.form.InspectionDecisionForm

@RestController
@RequestMapping("/api/v1/admin/inspections")
@PreAuthorize("hasRole('ADMIN')")
class AdminInspectionController(private val spaceService: SpaceService) {

    @GetMapping
    fun pending(): List<PendingInspectionDto> = spaceService.pendingInspections()

    @PostMapping("/{spaceId}/decision")
    fun decide(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable spaceId: Long,
        @Valid @RequestBody form: InspectionDecisionForm,
    ): SpaceDto = spaceService.decide(
        adminId = jwt.accountId(),
        spaceId = spaceId,
        approved = form.approved,
        reason = form.reason,
    )

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
