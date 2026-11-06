package com.lookation.controller

import com.lookation.dto.SpaceAvailabilityDto
import com.lookation.global.error.UnauthorizedException
import com.lookation.service.AvailabilityService
import com.lookation.service.SpaceImageService
import com.lookation.service.SpaceService
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
import com.lookation.dto.PriceRuleDto
import com.lookation.dto.SpaceSearchDto
import com.lookation.dto.SpaceDto
import com.lookation.controller.form.PriceRuleForm
import com.lookation.controller.form.SpaceForm
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(
    name = "공간",
    description = "공간 검색·상세 조회와 호스트의 공간 등록·수정·운영(마감/재개/삭제)·검수 신청·이미지·가격 규칙 관리. 검색·상세·이미지·가격 조회는 공개이고, 그 외 등록·수정 계열은 `HOST` 권한이면서 본인 소유 공간에만 허용됩니다.",
)
@RestController
@RequestMapping("/api/v1/spaces")
class SpaceController(
    private val spaceService: SpaceService,
    private val imageService: SpaceImageService,
    private val priceRuleService: com.lookation.service.PriceRuleService,
    private val availabilityService: AvailabilityService,
) {

    @Operation(
        summary = "공간 검색",
        description = "게시(`PUBLISHED`)되었고 삭제되지 않은 공간만 커서 페이징으로 조회합니다. 인증은 필요 없습니다. `sido`/`sigungu`를 주면 해당 지역 공간만 남고, `minPrice`/`maxPrice`는 가격 규칙에 해당 범위의 규칙이 하나라도 있는 공간을 찾고, `from`/`to`를 주면 그 요일의 가격 규칙이 있으면서 해당 시간대에 겹치는 예약(신청·결제 대기·확정)이 없는 공간만 남습니다. 응답의 `nextCursor`를 다음 요청의 `cursor`로 넘기며, `nextCursor`는 이번 응답이 `limit`만큼 찼을 때만 채워집니다. `sort`는 `latest`(기본, 최신 등록순), `price_asc`, `price_desc`, `rating` 중 하나이고, 동률이면 공간 ID 내림차순입니다. `limit`이 1~50을 벗어나거나 `sort`가 목록에 없거나 `from`/`to`를 하나만 주거나 `to`가 `from` 이후가 아니면 400입니다.",
    )
    @GetMapping
    fun search(
        @Parameter(description = "공간 유형입니다. `SpaceType` 값 중 하나를 그대로 넣습니다.", example = "PARTY_ROOM")
        @RequestParam(required = false) type: String?,
        @Parameter(description = "공간명 또는 주소에 대한 부분 일치 검색어입니다.", example = "루프탑")
        @RequestParam(required = false) keyword: String?,
        @Parameter(description = "시/도 지역입니다. 지정하면 해당 지역 공간만 조회합니다.", example = "서울")
        @RequestParam(required = false) sido: String?,
        @Parameter(description = "시/군/구 지역입니다. 지정하면 해당 지역 공간만 조회합니다.", example = "마포구")
        @RequestParam(required = false) sigungu: String?,
        @Parameter(description = "이용 인원입니다. 최소 인원 이상·최대 인원 이하인 공간만 조회합니다.", example = "8")
        @RequestParam(required = false) guests: Int?,
        @Parameter(description = "가격 규칙 금액의 하한입니다(KRW). 이 금액 이상인 규칙이 하나라도 있는 공간을 조회합니다.", example = "120000")
        @RequestParam(required = false) minPrice: Long?,
        @Parameter(description = "가격 규칙 금액의 상한입니다(KRW). 이 금액 이하인 규칙이 하나라도 있는 공간을 조회합니다.", example = "120000")
        @RequestParam(required = false) maxPrice: Long?,
        @Parameter(description = "이용 시작 일시입니다. `to`와 함께 지정해야 합니다.", example = "2026-11-20T18:00:00+09:00")
        @RequestParam(required = false) from: java.time.OffsetDateTime?,
        @Parameter(description = "이용 종료 일시입니다. `from`보다 뒤여야 하고, 이 구간과 겹치는 활성 예약이 있는 공간은 제외됩니다.", example = "2026-11-20T22:00:00+09:00")
        @RequestParam(required = false) to: java.time.OffsetDateTime?,
        @Parameter(description = "정렬 기준입니다. `latest`(기본), `price_asc`, `price_desc`, `rating` 중 하나입니다.", example = "price_asc")
        @RequestParam(required = false) sort: String?,
        @Parameter(description = "다음 페이지 조회용 커서입니다. 직전 응답의 `nextCursor`를 그대로 넣습니다.", example = "bGF0ZXN0fHwxMDI")
        @RequestParam(required = false) cursor: String?,
        @Parameter(description = "페이지 크기입니다. 1~50만 허용하며 기본값은 20입니다.", example = "20")
        @RequestParam(defaultValue = "20") limit: Int,
    ): SpaceSearchDto = spaceService.search(
        type = type,
        keyword = keyword,
        sido = sido,
        sigungu = sigungu,
        guests = guests,
        minPrice = minPrice,
        maxPrice = maxPrice,
        from = from,
        to = to,
        sort = sort,
        cursor = cursor,
        limit = limit,
    )

    @Operation(
        summary = "공간 등록",
        description = "`HOST` 권한이 필요합니다. 새 공간을 `DRAFT` 상태로 만들고 시설 목록을 함께 저장합니다. `spaceType`은 `SpaceType` 값이어야 하고, `minCapacity`는 1 이상이며 `maxCapacity`는 `minCapacity` 이상이어야 하며, 어기면 400입니다. 생성 직후에는 게시되지 않으므로 검수 신청과 이미지·가격 등록을 거쳐야 사용자에게 노출됩니다.",
    )
    @PostMapping
    @PreAuthorize("hasRole('HOST')")
    fun create(@AuthenticationPrincipal jwt: Jwt, @Valid @RequestBody form: SpaceForm): SpaceDto =
        spaceService.create(jwt.accountId(), form)

    @Operation(
        summary = "공간 수정",
        description = "`HOST` 권한과 본인 소유 공간이어야 합니다. 다른 사람의 공간이면 403, 없는 공간이면 404입니다. `DRAFT` 또는 `INSPECTION_REJECTED` 상태에서만 수정할 수 있고 그 외에는 409이며, 수정해도 상태는 바뀌지 않습니다. 시설 목록은 요청 값으로 전부 교체되고, `spaceType`·인원 검증 실패는 400입니다.",
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HOST')")
    fun update(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "공간 ID입니다.", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody form: SpaceForm,
    ): SpaceDto = spaceService.update(jwt.accountId(), id, form)

    @Operation(
        summary = "공간 상세 조회",
        description = "인증 없이 호출할 수 있고, `PUBLISHED` 공간은 누구나 조회됩니다. 게시되지 않은 공간은 소유자 토큰을 함께 보낼 때만 조회되고, 그 외에는 404입니다. 응답에는 상태·시설·이미지 URL이 포함됩니다.",
    )
    @GetMapping("/{id}")
    fun get(
        @AuthenticationPrincipal jwt: Jwt?,
        @Parameter(description = "공간 ID입니다.", example = "1")
        @PathVariable id: Long,
    ): SpaceDto = spaceService.get(jwt?.accountIdOrNull(), id)

    @Operation(
        summary = "공간 예약 점유 구간 조회",
        description = "인증 없이 호출할 수 있습니다. `from`~`to`와 겹치는 예약 점유 구간만 반환하며 예약자 정보는 포함하지 않습니다. 점유 상태는 `PENDING_APPROVAL`, `PENDING_PAYMENT`, `CONFIRMED`, `COMPLETED`이고 구간은 `[start, end)`입니다. 게시되지 않은 공간은 소유자만 조회할 수 있고, 그 외에는 404입니다. `to`가 `from` 이전이거나 조회 기간이 92일을 넘으면 400입니다.",
    )
    @GetMapping("/{id}/availability")
    fun availability(
        @AuthenticationPrincipal jwt: Jwt?,
        @Parameter(description = "공간 ID입니다.", example = "1")
        @PathVariable id: Long,
        @Parameter(description = "조회 시작 일시입니다.", example = "2026-11-20T00:00:00+09:00")
        @RequestParam from: java.time.OffsetDateTime,
        @Parameter(description = "조회 종료 일시입니다.", example = "2026-11-21T00:00:00+09:00")
        @RequestParam to: java.time.OffsetDateTime,
    ): SpaceAvailabilityDto = availabilityService.availability(jwt?.accountIdOrNull(), id, from, to)

    @Operation(
        summary = "공간 운영 중단",
        description = "`HOST` 권한과 본인 소유 공간이어야 합니다. `PUBLISHED` 공간을 `CLOSED`로 바꿔 검색·상세에서 노출되지 않게 합니다. 게시 중이 아니면 409, 소유자가 아니면 403, 없는 공간이면 404입니다.",
    )
    @PostMapping("/{id}/close")
    @PreAuthorize("hasRole('HOST')")
    fun close(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "공간 ID입니다.", example = "1")
        @PathVariable id: Long,
    ): SpaceDto = spaceService.close(jwt.accountId(), id)

    @Operation(
        summary = "공간 운영 재개",
        description = "`HOST` 권한과 본인 소유 공간이어야 합니다. `CLOSED` 공간을 `DRAFT`로 되돌립니다. 바로 게시되는 것이 아니라 다시 검수 신청과 승인이 필요합니다. `CLOSED`가 아니면 409, 소유자가 아니면 403, 없는 공간이면 404입니다.",
    )
    @PostMapping("/{id}/reopen")
    @PreAuthorize("hasRole('HOST')")
    fun reopen(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "공간 ID입니다.", example = "1")
        @PathVariable id: Long,
    ): SpaceDto = spaceService.reopen(jwt.accountId(), id)

    @Operation(
        summary = "공간 삭제",
        description = "`HOST` 권한과 본인 소유 공간이어야 합니다. 실제 행을 지우지 않고 소프트 삭제하므로 검색과 상세에서만 빠집니다. 진행 중인 예약이 있으면 409, 소유자가 아니면 403, 없는 공간이면 404입니다. 성공 시 204이고 응답 본문은 없습니다.",
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('HOST')")
    fun delete(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "공간 ID입니다.", example = "1")
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        spaceService.delete(jwt.accountId(), id)
        return ResponseEntity.noContent().build()
    }

    @Operation(
        summary = "검수 신청",
        description = "`HOST` 권한과 본인 소유 공간이어야 합니다. `DRAFT` 또는 `INSPECTION_REJECTED` 공간을 `PENDING_INSPECTION`으로 바꾸고 검수 이력을 남깁니다. 호스트 프로필이 없거나, 이미지가 0장이거나, 가격 규칙이 0개면 409입니다. 상태가 맞지 않아도 409, 소유자가 아니면 403, 없는 공간이면 404입니다.",
    )
    @PostMapping("/{id}/inspection")
    @PreAuthorize("hasRole('HOST')")
    fun submitInspection(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "공간 ID입니다.", example = "1")
        @PathVariable id: Long,
    ): SpaceDto = spaceService.submitInspection(jwt.accountId(), id)

    @Operation(
        summary = "공간 이미지 업로드",
        description = "`HOST` 권한과 본인 소유 공간이어야 합니다. `DRAFT` 또는 `INSPECTION_REJECTED` 상태에서만 업로드할 수 있고, 그 외 상태는 409입니다. 공간당 10장까지만 저장되며 초과하면 409, 소유자가 아니면 403, 없는 공간이면 404입니다. 성공 시 저장된 이미지의 URL 문자열(예: `/uploads/...`)을 그대로 반환합니다.",
    )
    @PostMapping("/{id}/images")
    @PreAuthorize("hasRole('HOST')")
    fun uploadImage(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "공간 ID입니다.", example = "1")
        @PathVariable id: Long,
        @Parameter(description = "업로드할 이미지 파일입니다.", example = "space.jpg")
        @RequestParam("file") file: MultipartFile,
    ): String = imageService.upload(jwt.accountId(), id, file)

    @Operation(
        summary = "공간 이미지 목록 조회",
        description = "인증 없이 호출할 수 있습니다. 해당 공간에 등록된 이미지 URL 목록을 반환하며, 공간 상태와 무관하게 조회됩니다. 없는 공간이면 404입니다.",
    )
    @GetMapping("/{id}/images")
    fun images(
        @Parameter(description = "공간 ID입니다.", example = "1")
        @PathVariable id: Long,
    ): List<String> = imageService.list(id)

    @Operation(
        summary = "가격 규칙 조회",
        description = "인증 없이 호출할 수 있고, `PUBLISHED` 공간의 가격 규칙은 누구나 조회됩니다. 게시되지 않은 공간은 소유자 토큰을 함께 보낼 때만 조회되며, 그 외에는 404입니다.",
    )
    @GetMapping("/{id}/prices")
    fun prices(
        @AuthenticationPrincipal jwt: Jwt?,
        @Parameter(description = "공간 ID입니다.", example = "1")
        @PathVariable id: Long,
    ): List<PriceRuleDto> = priceRuleService.list(jwt?.accountIdOrNull(), id)

    @Operation(
        summary = "가격 규칙 전체 교체",
        description = "`HOST` 권한과 본인 소유 공간이어야 합니다. 기존 규칙을 모두 지우고 요청 목록으로 교체하므로, 일부만 보내면 나머지는 삭제됩니다. 규칙은 50개까지이며 `dayOfWeek`는 1~7, `price`는 0 이상이어야 합니다. `unit`이 `HOUR`이면 `startTime`·`endTime`이 모두 필요하고 종료가 시작 이후여야 하며, `DAY`이면 시각을 지정할 수 없고, 같은 요일의 시간 블록이 겹치면 위반은 모두 400입니다. 소유자가 아니면 403, 없는 공간이면 404입니다.",
    )
    @PutMapping("/{id}/prices")
    @PreAuthorize("hasRole('HOST')")
    fun replacePrices(
        @AuthenticationPrincipal jwt: Jwt,
        @Parameter(description = "공간 ID입니다.", example = "1")
        @PathVariable id: Long,
        @Valid @RequestBody rules: List<PriceRuleForm>,
    ): List<PriceRuleDto> = priceRuleService.replace(jwt.accountId(), id, rules)

    private fun Jwt.accountId(): Long =
        subject?.toLongOrNull() ?: throw UnauthorizedException("토큰 주체가 없습니다")

    private fun Jwt.accountIdOrNull(): Long? = subject?.toLongOrNull()
}
