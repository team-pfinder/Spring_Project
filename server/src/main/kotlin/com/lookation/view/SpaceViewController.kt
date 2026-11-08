package com.lookation.view

import com.lookation.controller.form.CreateReservationForm
import com.lookation.domain.ReservationStatus
import com.lookation.domain.SpaceType
import com.lookation.global.error.ApiException
import com.lookation.service.AvailabilityService
import com.lookation.service.PriceRuleService
import com.lookation.service.QnaService
import com.lookation.service.ReservationService
import com.lookation.service.ReviewService
import com.lookation.service.SpaceService
import com.lookation.view.form.ReservationViewForm
import io.swagger.v3.oas.annotations.Hidden
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDate

@Hidden
@Controller
class SpaceViewController(
    private val spaceService: SpaceService,
    private val priceRuleService: PriceRuleService,
    private val availabilityService: AvailabilityService,
    private val reviewService: ReviewService,
    private val qnaService: QnaService,
    private val reservationService: ReservationService,
) {

    @GetMapping("/")
    fun root(): String = "redirect:/spaces"

    @GetMapping("/spaces")
    fun list(
        @RequestParam(required = false) keyword: String?,
        @RequestParam(required = false) sido: String?,
        @RequestParam(required = false) sigungu: String?,
        @RequestParam(required = false) type: String?,
        @RequestParam(required = false) guests: Int?,
        @RequestParam(required = false) minPrice: Long?,
        @RequestParam(required = false) maxPrice: Long?,
        @RequestParam(required = false) date: LocalDate?,
        @RequestParam(required = false) sort: String?,
        @RequestParam(required = false) cursor: String?,
        model: Model,
    ): String {
        val from = date?.atStartOfDay(KST)?.toOffsetDateTime()
        val to = date?.plusDays(1)?.atStartOfDay(KST)?.toOffsetDateTime()
        val result = spaceService.search(
            type = type?.takeIf { it.isNotBlank() },
            keyword = keyword?.takeIf { it.isNotBlank() },
            sido = sido?.takeIf { it.isNotBlank() },
            sigungu = sigungu?.takeIf { it.isNotBlank() },
            guests = guests,
            minPrice = minPrice,
            maxPrice = maxPrice,
            from = from,
            to = to,
            sort = sort,
            cursor = cursor,
            limit = PAGE_SIZE,
        )
        val filters = linkedMapOf(
            "keyword" to keyword,
            "sido" to sido,
            "sigungu" to sigungu,
            "type" to type,
            "guests" to guests?.toString(),
            "minPrice" to minPrice?.toString(),
            "maxPrice" to maxPrice?.toString(),
            "date" to date?.toString(),
            "sort" to sort,
        )
        model.addAttribute("spaces", result.items)
        model.addAttribute("nextUrl", result.nextCursor?.let { searchUrl(filters, it) })
        model.addAttribute("filters", filters)
        model.addAttribute("sidoOptions", SIDO_OPTIONS)
        model.addAttribute("spaceTypes", SpaceType.entries.map { it.name })
        model.addAttribute("resultCount", result.items.size)
        return "space/list"
    }

    @GetMapping("/spaces/{id}")
    fun detail(
        @AuthenticationPrincipal jwt: Jwt?,
        @PathVariable id: Long,
        @RequestParam(required = false) date: LocalDate?,
        model: Model,
    ): String {
        val accountId = jwt?.accountIdOrNull()
        val space = spaceService.get(accountId, id)
        val selectedDate = date ?: LocalDate.now(KST).plusDays(1)
        val rules = priceRuleService.list(accountId, id)
        val occupied = availabilityService.occupiedForDate(id, selectedDate)
        model.addAttribute("space", space)
        model.addAttribute("date", selectedDate)
        model.addAttribute("minDate", LocalDate.now(KST))
        model.addAttribute("slots", bookingSlots(selectedDate, rules, occupied))
        model.addAttribute("reviews", reviewService.list(id, null, 5))
        model.addAttribute("qnas", qnaService.list(id, null, 5))
        return "space/detail"
    }

    @GetMapping("/spaces/{id}/reservations/new")
    fun reservationForm(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @RequestParam(required = false) date: LocalDate?,
        @RequestParam(required = false) priceRuleId: Long?,
        model: Model,
    ): String {
        val accountId = jwt.requireAccountId()
        val selectedDate = date ?: LocalDate.now(KST).plusDays(1)
        renderReservationForm(
            accountId = accountId,
            spaceId = id,
            date = selectedDate,
            selectedPriceRuleId = priceRuleId,
            guestCount = null,
            requestMessage = null,
            model = model,
        )
        return "reservation/form"
    }

    @PostMapping("/spaces/{id}/reservations")
    fun createReservation(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable id: Long,
        @Valid @ModelAttribute("form") form: ReservationViewForm,
        bindingResult: BindingResult,
        model: Model,
    ): String {
        val accountId = jwt.requireAccountId()
        if (bindingResult.hasErrors()) {
            renderReservationForm(
                accountId = accountId,
                spaceId = id,
                date = form.date ?: LocalDate.now(KST).plusDays(1),
                selectedPriceRuleId = form.priceRuleId,
                guestCount = form.guestCount,
                requestMessage = form.requestMessage,
                model = model,
            )
            return "reservation/form"
        }

        val date = requireNotNull(form.date)
        try {
            val rules = priceRuleService.list(accountId, id)
            val occupied = availabilityService.occupiedForDate(id, date)
            val slots = bookingSlots(date, rules, occupied)
            val slot = slots.firstOrNull { it.priceRuleId == form.priceRuleId }
                ?: throw com.lookation.global.error.BadRequestException("선택한 이용 시간을 찾을 수 없습니다")
            val reservation = reservationService.create(
                accountId,
                CreateReservationForm(
                    spaceId = id,
                    startAt = slot.startAt,
                    endAt = slot.endAt,
                    guestCount = requireNotNull(form.guestCount),
                    requestMessage = form.requestMessage?.takeIf { it.isNotBlank() },
                ),
            )
            val destination = if (reservation.status == ReservationStatus.PENDING_PAYMENT) {
                "/reservations/${reservation.id}/pay"
            } else {
                "/mypage/reservations/${reservation.id}?created=1"
            }
            return "redirect:$destination"
        } catch (exception: ApiException) {
            model.addAttribute("error", exception.message)
            model.addAttribute("renderedErrorLocally", true)
            renderReservationForm(
                accountId = accountId,
                spaceId = id,
                date = date,
                selectedPriceRuleId = form.priceRuleId,
                guestCount = form.guestCount,
                requestMessage = form.requestMessage,
                model = model,
            )
            return "reservation/form"
        }
    }

    private fun renderReservationForm(
        accountId: Long,
        spaceId: Long,
        date: LocalDate,
        selectedPriceRuleId: Long?,
        guestCount: Int?,
        requestMessage: String?,
        model: Model,
    ) {
        val space = spaceService.get(accountId, spaceId)
        val rules = priceRuleService.list(accountId, spaceId)
        val occupied = availabilityService.occupiedForDate(spaceId, date)
        model.addAttribute("space", space)
        model.addAttribute("date", date)
        model.addAttribute("minDate", LocalDate.now(KST))
        model.addAttribute("slots", bookingSlots(date, rules, occupied))
        model.addAttribute("selectedPriceRuleId", selectedPriceRuleId)
        model.addAttribute("selectedGuestCount", guestCount ?: space.minCapacity)
        model.addAttribute("selectedRequestMessage", requestMessage)
        if (!model.containsAttribute("form")) {
            model.addAttribute(
                "form",
                ReservationViewForm(
                    date = date,
                    priceRuleId = selectedPriceRuleId,
                    guestCount = guestCount ?: space.minCapacity,
                    requestMessage = requestMessage,
                ),
            )
        }
    }

    private fun searchUrl(filters: Map<String, String?>, cursor: String): String {
        val builder = UriComponentsBuilder.fromPath("/spaces")
        filters.forEach { (name, value) ->
            if (!value.isNullOrBlank()) builder.queryParam(name, value)
        }
        builder.queryParam("cursor", cursor)
        return builder.build().encode().toUriString()
    }

    private companion object {
        const val PAGE_SIZE = 12
        val SIDO_OPTIONS = listOf(
            "서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종",
            "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주",
        )
    }
}
