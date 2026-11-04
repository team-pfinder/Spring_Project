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
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(
    name = "공간 검수 (관리자)",
    description = "호스트가 검수 신청한 공간을 조회하고 게시 승인 또는 반려를 결정합니다. 모든 엔드포인트는 `ADMIN` 권한 전용입니다.",
)
@RestController
@RequestMapping("/api/v1/admin/inspections")
@PreAuthorize("hasRole('ADMIN')")
class AdminInspectionController(private val spaceService: SpaceService) {

    @Operation(
        summary = "검수 대기 공간 목록",
        description = "검수 이력이 `PENDING_INSPECTION`인 건을 신청 시각 오름차순으로 모두 반환합니다. 페이징은 없습니다. `ADMIN` 권한이 없으면 403입니다.",
    )
    @GetMapping
    fun pending(): List<PendingInspectionDto> = spaceService.pendingInspections()

    @Operation(
        summary = "검수 승인 또는 반려",
        description = "`PENDING_INSPECTION` 공간을 승인하면 `PUBLISHED`, 반려하면 `INSPECTION_REJECTED`로 바꾸고 검수 이력에 결정자와 사유를 남깁니다. 반려할 때 `reason`이 비어 있으면 400이고, 검수 대기 상태가 아니면 409, 없는 공간이면 404입니다. 처리 후 공간 상세를 반환합니다.",
    )
    @PostMapping("/{spaceId}/decision")
    fun decide(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "검수 대상 공간 ID입니다.", example = "1")
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
