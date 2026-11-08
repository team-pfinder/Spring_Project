package com.lookation.controller

import com.lookation.dto.PagedDto
import com.lookation.dto.QnaDto
import com.lookation.global.error.UnauthorizedException
import com.lookation.service.QnaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
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
import com.lookation.controller.form.QnaAnswerForm
import com.lookation.controller.form.AskQnaForm

@Tag(
    name = "공간 문의",
    description = "공간별 질문과 호스트 답변을 다룹니다. 목록·상세 조회는 공개이고, 질문 작성은 로그인, 답변은 해당 공간의 `HOST`, 삭제는 질문 작성자 본인만 가능합니다.",
)
@RestController
@RequestMapping("/api/v1")
class QnaController(private val qnaService: QnaService) {

    @Operation(
        summary = "공간 문의 목록",
        description = "특정 공간에 달린 질문을 최신순으로 커서 페이징해 돌려줍니다. 인증이 필요 없고, 작성자는 닉네임(`nickname`)으로만 노출됩니다. `limit`이 1~50을 벗어나면 400입니다.",
    )
    @GetMapping("/spaces/{spaceId}/qnas")
    fun list(
        @Parameter(description = "문의를 조회할 공간 id", example = "1")
        @PathVariable spaceId: Long,
        @Parameter(description = "다음 페이지를 조회할 때 직전 응답의 `nextCursor`를 그대로 넣습니다. 첫 페이지는 생략합니다.", example = "42")
        @RequestParam(required = false) cursor: Long?,
        @Parameter(description = "한 페이지에 담을 항목 수입니다. 1~50 사이여야 합니다.", example = "20")
        @RequestParam(defaultValue = "20") limit: Int,
    ): PagedDto<QnaDto> = qnaService.list(spaceId, cursor, limit)

    @Operation(
        summary = "공간 문의 작성",
        description = "로그인한 사용자가 공간에 질문을 남깁니다. 토큰이 없으면 401이고, 제재 중인 계정이면 403입니다. 없는 공간이면 404, `PUBLISHED` 상태가 아닌 공간이면 409입니다. 성공하면 200과 함께 `OPEN` 상태의 질문을 돌려줍니다.",
    )
    @PostMapping("/spaces/{spaceId}/qnas")
    fun ask(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "질문을 남길 공간 id", example = "1")
        @PathVariable spaceId: Long,
        @Valid @RequestBody form: AskQnaForm,
    ): QnaDto = qnaService.ask(jwt.accountId(), spaceId, form)

    @Operation(
        summary = "공간 문의 상세 조회",
        description = "질문 한 건과 호스트 답변을 조회합니다. 인증이 필요 없고, 작성자는 닉네임으로만 노출됩니다. `status`는 `OPEN` 또는 `ANSWERED`이며, 없는 id면 404입니다.",
    )
    @GetMapping("/qnas/{id}")
    fun get(
        @Parameter(description = "질문 id", example = "1")
        @PathVariable id: Long,
    ): QnaDto = qnaService.get(id)

    @Operation(
        summary = "공간 문의 답변",
        description = "해당 공간을 소유한 `HOST`가 질문에 답변합니다. `HOST` 역할이 아니면 403이고, 다른 호스트의 공간이면 403입니다. 없는 질문·공간이면 404, 이미 답변된 질문이면 409입니다. 성공하면 200과 함께 `ANSWERED` 상태의 질문을 돌려줍니다.",
    )
    @PostMapping("/qnas/{id}/answer")
    @PreAuthorize("hasRole('HOST')")
    fun answer(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "답변할 질문 id", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody form: QnaAnswerForm,
    ): QnaDto = qnaService.answer(jwt.accountId(), id, form)

    @Operation(
        summary = "공간 문의 삭제",
        description = "질문 작성자 본인만 질문을 삭제할 수 있습니다. 토큰이 없으면 401, 작성자가 아니면 403입니다. 없는 질문이면 404이고, 이미 답변이 달린 질문은 삭제할 수 없어 409입니다. 성공하면 본문 없이 204입니다.",
    )
    @DeleteMapping("/qnas/{id}")
    fun delete(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "삭제할 질문 id", example = "1")
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        qnaService.delete(jwt.accountId(), id)
        return ResponseEntity.noContent().build()
    }

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")
}
