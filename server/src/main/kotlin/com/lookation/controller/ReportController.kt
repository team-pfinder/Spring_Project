package com.lookation.controller

import com.lookation.dto.PagedDto
import com.lookation.dto.ReportDto
import com.lookation.global.error.UnauthorizedException
import com.lookation.service.ReportService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
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

@Tag(
    name = "신고",
    description = "공간·예약·리뷰·문의·계정에 대한 신고 접수와 관리자 심사. 접수는 로그인한 회원이, 목록 조회와 심사는 관리자만 호출할 수 있습니다.",
)
@RestController
@RequestMapping("/api/v1")
class ReportController(private val reportService: ReportService) {

    @Operation(
        summary = "신고 접수",
        description = "대상 종류(`targetType`)는 `SPACE`, `RESERVATION`, `REVIEW`, `QNA`, `ACCOUNT` 중 하나이고, 사유는 최대 500자입니다. 접수 직후 상태는 `RECEIVED`입니다. 대상이 존재하지 않으면 404, 같은 대상에 이미 신고한 경우는 409, 제재 중인 계정은 403입니다. 허용되지 않은 `targetType` 값은 400입니다. 본인 계정이나 본인 자원을 신고하는 경우를 막는 검사는 없어 그대로 접수됩니다.",
    )
    @PostMapping("/reports")
    fun create(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: CreateReportForm,
    ): ReportDto = reportService.create(jwt.accountId(), form)

    @Operation(
        summary = "신고 목록 조회 (관리자)",
        description = "관리자가 전체 신고를 최신순(ID 내림차순)으로 조회합니다. `status`를 주면 해당 상태만 걸러내고, 생략하면 전체 상태를 반환합니다. `status`가 `RECEIVED`·`ACCEPTED`·`REJECTED`가 아니면 400이며, `limit`이 1~100을 벗어나도 400입니다. 응답의 `nextCursor`가 null이면 마지막 페이지입니다.",
    )
    @GetMapping("/admin/reports")
    @PreAuthorize("hasRole('ADMIN')")
    fun list(
        @Parameter(description = "신고 상태 필터: `RECEIVED`, `ACCEPTED`, `REJECTED`. 생략하면 전체", example = "RECEIVED")
        @RequestParam(required = false) status: String?,
        @Parameter(description = "이전 페이지 마지막 신고 ID. 생략하면 첫 페이지", example = "42")
        @RequestParam(required = false) cursor: Long?,
        @Parameter(description = "페이지 크기 (1~100)", example = "20")
        @RequestParam(defaultValue = "20") limit: Int,
    ): PagedDto<ReportDto> = reportService.list(status, cursor, limit)

    @Operation(
        summary = "신고 심사",
        description = "관리자가 신고 1건을 인정(`accepted` = true, 상태 `ACCEPTED`) 또는 기각(`accepted` = false, 상태 `REJECTED`)으로 처리합니다. 심사는 신고 상태만 바꾸며 제재를 자동으로 적용하지 않습니다. 없는 신고는 404, 이미 심사가 끝난 신고는 409입니다.",
    )
    @PostMapping("/admin/reports/{id}/decision")
    @PreAuthorize("hasRole('ADMIN')")
    fun decide(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "심사할 신고 ID", example = "42")
        @PathVariable id: Long,
        @Valid @RequestBody form: ReportDecisionForm,
    ): ReportDto = reportService.decide(jwt.accountId(), id, form.accepted)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
