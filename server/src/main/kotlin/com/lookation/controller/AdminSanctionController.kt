package com.lookation.controller

import com.lookation.dto.SanctionDto
import com.lookation.global.error.UnauthorizedException
import com.lookation.service.SanctionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.lookation.controller.form.ApplySanctionForm

@Tag(
    name = "관리자 - 제재",
    description = "관리자가 계정에 이용 제재를 부여하거나 해제합니다. `ADMIN`만 호출할 수 있으며, 활성 제재가 있는 계정은 예약 생성·결제, 리뷰 작성, 문의·신고 등록, 충전·출금 신청이 403으로 차단되고 조회와 예약 취소는 계속 허용됩니다.",
)
@RestController
@RequestMapping("/api/v1/admin/sanctions")
@PreAuthorize("hasRole('ADMIN')")
class AdminSanctionController(private val sanctionService: SanctionService) {

    @Operation(
        summary = "제재 부여",
        description = "`accountId` 계정에 `level` 1~3, `reason` 필수로 제재를 추가합니다. `endsAt`을 생략하면 종료 시각이 없는 영구 제재가 되고, 값을 주면 그 시각까지 적용됩니다. 성공 시 200으로 생성된 제재를 반환하며 영구 제재의 `endsAt`은 null입니다. 값이 비었거나 범위를 벗어나면 400입니다.",
    )
    @PostMapping
    fun apply(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: ApplySanctionForm,
    ): SanctionDto = sanctionService.apply(jwt.accountId(), form)

    @Operation(
        summary = "제재 해제",
        description = "지정한 제재를 해제해 즉시 효력을 없앱니다. 성공 시 응답 본문 없이 204를 반환하고, 없는 제재면 404, 이미 해제된 제재면 409입니다.",
    )
    @DeleteMapping("/{id}")
    fun revoke(
        @Parameter(description = "제재 ID", example = "1")
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        sanctionService.revoke(id)
        return ResponseEntity.noContent().build()
    }

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
