package com.lookation.controller

import com.lookation.dto.QnaListResponse
import com.lookation.dto.QnaResponse
import com.lookation.global.error.UnauthorizedException
import com.lookation.service.QnaService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class QnaController(private val qnaService: QnaService) {

    @GetMapping("/spaces/{spaceId}/qnas")
    fun list(
        @PathVariable spaceId: Long,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "20") limit: Int,
    ): QnaListResponse = qnaService.list(spaceId, cursor, limit)

    @PostMapping("/spaces/{spaceId}/qnas")
    fun ask(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable spaceId: Long,
        @Valid @RequestBody form: AskQnaForm,
    ): QnaResponse = qnaService.ask(jwt.accountId(), spaceId, form)

    @GetMapping("/qnas/{id}")
    fun get(@PathVariable id: Long): QnaResponse = qnaService.get(id)

    @PostMapping("/qnas/{id}/answer")
    @PreAuthorize("hasRole('HOST')")
    fun answer(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @Valid @RequestBody form: QnaAnswerForm,
    ): QnaResponse = qnaService.answer(jwt.accountId(), id, form)

    @DeleteMapping("/qnas/{id}")
    fun delete(@AuthenticationPrincipal jwt: Jwt, @PathVariable id: Long): ResponseEntity<Void> {
        qnaService.delete(jwt.accountId(), id)
        return ResponseEntity.noContent().build()
    }

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
