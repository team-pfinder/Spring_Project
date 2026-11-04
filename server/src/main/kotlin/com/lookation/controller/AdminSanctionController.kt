package com.lookation.controller

import com.lookation.dto.SanctionDto
import com.lookation.global.error.UnauthorizedException
import com.lookation.service.SanctionService
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

@RestController
@RequestMapping("/api/v1/admin/sanctions")
@PreAuthorize("hasRole('ADMIN')")
class AdminSanctionController(private val sanctionService: SanctionService) {

    @PostMapping
    fun apply(
        @AuthenticationPrincipal jwt: Jwt,
        @Valid @RequestBody form: ApplySanctionForm,
    ): SanctionDto = sanctionService.apply(jwt.accountId(), form)

    @DeleteMapping("/{id}")
    fun revoke(@PathVariable id: Long): ResponseEntity<Void> {
        sanctionService.revoke(id)
        return ResponseEntity.noContent().build()
    }

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
