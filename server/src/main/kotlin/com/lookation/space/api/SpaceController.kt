package com.lookation.space.api

import com.lookation.global.error.UnauthorizedException
import com.lookation.space.application.SpaceImageService
import com.lookation.space.application.SpaceService
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/spaces")
class SpaceController(
    private val spaceService: SpaceService,
    private val imageService: SpaceImageService,
    private val priceRuleService: com.lookation.space.application.PriceRuleService,
) {

    @GetMapping
    fun search(
        @RequestParam(required = false) type: String?,
        @RequestParam(required = false) keyword: String?,
        @RequestParam(required = false) guests: Int?,
        @RequestParam(required = false) minPrice: Long?,
        @RequestParam(required = false) maxPrice: Long?,
        @RequestParam(required = false) from: java.time.OffsetDateTime?,
        @RequestParam(required = false) to: java.time.OffsetDateTime?,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "20") limit: Int,
    ): SpaceSearchResponse = spaceService.search(
        type = type,
        keyword = keyword,
        guests = guests,
        minPrice = minPrice,
        maxPrice = maxPrice,
        from = from,
        to = to,
        cursor = cursor,
        limit = limit,
    )

    @PostMapping
    @PreAuthorize("hasRole('HOST')")
    fun create(@AuthenticationPrincipal jwt: Jwt, @Valid @RequestBody form: SpaceForm): SpaceResponse =
        spaceService.create(jwt.accountId(), form)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HOST')")
    fun update(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @Valid @RequestBody form: SpaceForm,
    ): SpaceResponse = spaceService.update(jwt.accountId(), id, form)

    @GetMapping("/{id}")
    fun get(@AuthenticationPrincipal jwt: Jwt?, @PathVariable id: Long): SpaceResponse =
        spaceService.get(jwt?.accountIdOrNull(), id)

    @PostMapping("/{id}/close")
    @PreAuthorize("hasRole('HOST')")
    fun close(@AuthenticationPrincipal jwt: Jwt, @PathVariable id: Long): SpaceResponse =
        spaceService.close(jwt.accountId(), id)

    @PostMapping("/{id}/reopen")
    @PreAuthorize("hasRole('HOST')")
    fun reopen(@AuthenticationPrincipal jwt: Jwt, @PathVariable id: Long): SpaceResponse =
        spaceService.reopen(jwt.accountId(), id)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HOST')")
    fun delete(@AuthenticationPrincipal jwt: Jwt, @PathVariable id: Long): ResponseEntity<Void> {
        spaceService.delete(jwt.accountId(), id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/inspection")
    @PreAuthorize("hasRole('HOST')")
    fun submitInspection(@AuthenticationPrincipal jwt: Jwt, @PathVariable id: Long): SpaceResponse =
        spaceService.submitInspection(jwt.accountId(), id)

    @PostMapping("/{id}/images")
    @PreAuthorize("hasRole('HOST')")
    fun uploadImage(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @RequestParam("file") file: MultipartFile,
    ): String = imageService.upload(jwt.accountId(), id, file)

    @GetMapping("/{id}/images")
    fun images(@PathVariable id: Long): List<String> = imageService.list(id)

    @GetMapping("/{id}/prices")
    fun prices(@AuthenticationPrincipal jwt: Jwt?, @PathVariable id: Long): List<PriceRuleResponse> =
        priceRuleService.list(jwt?.accountIdOrNull(), id)

    @PutMapping("/{id}/prices")
    @PreAuthorize("hasRole('HOST')")
    fun replacePrices(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @Valid @RequestBody rules: List<PriceRuleForm>,
    ): List<PriceRuleResponse> = priceRuleService.replace(jwt.accountId(), id, rules)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")

    private fun Jwt.accountIdOrNull(): Long? = subject?.toLongOrNull()
}
