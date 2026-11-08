package com.lookation.integration

import com.lookation.global.seed.InitializeService
import com.lookation.global.seed.SeedStep
import com.lookation.jooq.tables.Accounts.ACCOUNTS
import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import com.lookation.jooq.tables.PriceRules.PRICE_RULES
import com.lookation.jooq.tables.Reservations.RESERVATIONS
import com.lookation.jooq.tables.Spaces.SPACES
import com.lookation.repository.WalletRepository
import com.lookation.service.ReservationService
import jakarta.servlet.http.Cookie
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.time.LocalDate
import java.time.ZoneId

@SpringBootTest(
    properties = [
        "lookation.jwt.secret=0123456789abcdef0123456789abcdef",
        "lookation.encryption.key=AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQE=",
        "lookation.upload-dir=build/test-uploads",
        "lookation.test.suite=thymeleaf-view",
    ],
)
@AutoConfigureMockMvc
@Import(TestMailConfig::class)
@Testcontainers
class ThymeleafViewFlowTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:18-alpine"))

        private const val PASSWORD = "lookation1234!"
        private const val MEMBER_EMAIL = "seed-member-1@lookation.local"
        private val KST = ZoneId.of("Asia/Seoul")
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var steps: List<SeedStep>

    @Autowired
    private lateinit var dsl: DSLContext

    @Autowired
    private lateinit var reservationService: ReservationService

    @Autowired
    private lateinit var walletRepository: WalletRepository

    @Autowired
    private lateinit var mailSender: CapturingMailSender

    @BeforeEach
    fun seed() {
        InitializeService(steps).seed()
    }

    @Test
    fun `공개 검색 화면은 카드와 썸네일을 렌더한다`() {
        mockMvc.perform(get("/"))
            .andExpect(status().isFound)
            .andExpect(header().string("Location", "/spaces"))
        mockMvc.perform(get("/css/app.css")).andExpect(status().isOk)
        mockMvc.perform(get("/images/main-1.jpg")).andExpect(status().isOk)

        mockMvc.perform(get("/spaces"))
            .andExpect(status().isOk)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("시드 파티룸")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("/uploads/seed/space-")))
    }

    @Test
    fun `검색 화면은 로딩 상태를 렌더한다`() {
        mockMvc.perform(get("/spaces"))
            .andExpect(status().isOk)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("로딩 중")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("loading-state")))
    }

    @Test
    fun `잔액 부족 예약 실패는 오류 메시지를 렌더한다`() {
        val cookies = login(MEMBER_EMAIL)
        // 시드 잔액을 0으로 만든 뒤 즉시예약을 시도하면 ConflictException이 form에 렌더돼야 한다.
        val memberId = accountId(MEMBER_EMAIL)
        dsl.update(com.lookation.jooq.tables.Wallets.WALLETS)
            .set(com.lookation.jooq.tables.Wallets.WALLETS.BALANCE, 0L)
            .where(com.lookation.jooq.tables.Wallets.WALLETS.ACCOUNT_ID.eq(memberId))
            .execute()
        val spaceId = spaceId("시드 파티룸")
        val date = LocalDate.now(KST).plusDays(10)
        val ruleId = priceRuleId(spaceId, date.dayOfWeek.value)

        mockMvc.perform(
            post("/spaces/$spaceId/reservations")
                .with(csrf())
                .cookie(*cookies)
                .param("date", date.toString())
                .param("priceRuleId", ruleId.toString())
                .param("guestCount", "10")
                .param("requestMessage", "잔액 부족 테스트"),
        )
            .andExpect(status().isOk)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("마일리지 잔액이 부족합니다")))
    }

    @Test
    fun `상세 화면은 이미지 순서와 예약 슬롯을 렌더한다`() {
        val spaceId = spaceId("시드 파티룸")
        val keys = dsl.select(ATTACHMENTS.STORAGE_KEY)
            .from(ATTACHMENTS)
            .where(ATTACHMENTS.OWNER_TYPE.eq("SPACE"))
            .and(ATTACHMENTS.OWNER_ID.eq(spaceId))
            .orderBy(ATTACHMENTS.SORT_ORDER, ATTACHMENTS.ID)
            .fetch(ATTACHMENTS.STORAGE_KEY)

        val html = mockMvc.perform(get("/spaces/$spaceId"))
            .andExpect(status().isOk)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("시드 파티룸")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("18:00 ~ 22:00")))
            .andReturn().response.contentAsString

        assertThat(html.indexOf("/uploads/${keys[0]}")).isLessThan(html.indexOf("/uploads/${keys[1]}"))

        val draftId = spaceId("시드 빈 공간")
        mockMvc.perform(get("/spaces/$draftId"))
            .andExpect(status().isNotFound)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("페이지를 찾을 수 없습니다")))
    }

    @Test
    fun `로그인하면 쿠키가 발급되고 마이페이지 예약 목록에 접근한다`() {
        mockMvc.perform(get("/mypage/reservations"))
            .andExpect(status().isFound)
            .andExpect(header().string("Location", "/login?next=%2Fmypage%2Freservations"))

        val cookies = login(MEMBER_EMAIL)

        mockMvc.perform(get("/mypage/reservations").cookie(*cookies))
            .andExpect(status().isOk)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("시드 파티룸")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("예약 확정")))

        mockMvc.perform(
            post("/login").with(csrf()).param("email", MEMBER_EMAIL).param("password", "wrong-password"),
        )
            .andExpect(status().isOk)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("이메일 또는 비밀번호가 올바르지 않습니다")))
    }

    @Test
    fun `가입 인증 코드와 비밀번호 재설정이 메일 코드로 동작한다`() {
        val email = "view-${java.util.UUID.randomUUID()}@lookation.test"

        mockMvc.perform(get("/signup"))
            .andExpect(status().isOk)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("이메일 인증")))

        mockMvc.perform(
            post("/signup/email-verification").with(csrf()).param("email", email),
        )
            .andExpect(status().isFound)

        val signupCode = requireNotNull(mailSender.codes[email])
        mockMvc.perform(
            post("/signup")
                .with(csrf())
                .param("email", email)
                .param("password", "password123")
                .param("name", "뷰테스트")
                .param("nickname", "viewtest")
                .param("code", signupCode),
        )
            .andExpect(status().isFound)
            .andExpect(header().string("Location", "/spaces?welcome=1"))

        mockMvc.perform(post("/password-reset").with(csrf()).param("email", email))
            .andExpect(status().isFound)

        val resetCode = requireNotNull(mailSender.codes[email])
        mockMvc.perform(
            post("/password-reset/confirm")
                .with(csrf())
                .param("email", email)
                .param("code", resetCode)
                .param("newPassword", "newpassword123"),
        )
            .andExpect(status().isFound)
            .andExpect(header().string("Location", "/login?reset=1"))

        mockMvc.perform(
            post("/login").with(csrf()).param("email", email).param("password", "newpassword123"),
        )
            .andExpect(status().isFound)
            .andExpect(header().string("Location", "/spaces"))
    }

    @Test
    fun `즉시 예약은 생성 직후 확정되고 상세 화면에서 취소할 수 있다`() {
        val cookies = login(MEMBER_EMAIL)
        deposit(200_000)
        val spaceId = spaceId("시드 파티룸")
        val date = LocalDate.now(KST).plusDays(10)
        val ruleId = priceRuleId(spaceId, date.dayOfWeek.value)

        mockMvc.perform(
            get("/spaces/$spaceId/reservations/new")
                .cookie(*cookies)
                .param("date", date.toString())
                .param("priceRuleId", ruleId.toString()),
        )
            .andExpect(status().isOk)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("예약 신청하기")))

        val created = mockMvc.perform(
            post("/spaces/$spaceId/reservations")
                .with(csrf())
                .cookie(*cookies)
                .param("date", date.toString())
                .param("priceRuleId", ruleId.toString())
                .param("guestCount", "10")
                .param("requestMessage", "테스트 예약"),
        )
            .andExpect(status().isFound)
            .andReturn()

        val reservationId = reservationIdFrom(created.response.getRedirectedUrl())
        val status = dsl.select(RESERVATIONS.STATUS).from(RESERVATIONS)
            .where(RESERVATIONS.ID.eq(reservationId)).fetchOne(RESERVATIONS.STATUS)
        assertThat(status).isEqualTo("CONFIRMED")

        mockMvc.perform(get("/mypage/reservations/$reservationId").cookie(*cookies))
            .andExpect(status().isOk)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("예약 확정")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("예약 취소")))

        mockMvc.perform(post("/mypage/reservations/$reservationId/cancel").with(csrf()).cookie(*cookies))
            .andExpect(status().isFound)

        val cancelled = dsl.select(RESERVATIONS.STATUS).from(RESERVATIONS)
            .where(RESERVATIONS.ID.eq(reservationId)).fetchOne(RESERVATIONS.STATUS)
        assertThat(cancelled).isEqualTo("CANCELLED")
    }

    @Test
    fun `승인형 예약은 호스트 승인 뒤 결제 화면에서 결제된다`() {
        val cookies = login(MEMBER_EMAIL)
        deposit(200_000)
        val spaceId = spaceId("시드 부산 파티룸")
        val date = LocalDate.now(KST).plusDays(11)
        val ruleId = priceRuleId(spaceId, date.dayOfWeek.value)

        val created = mockMvc.perform(
            post("/spaces/$spaceId/reservations")
                .with(csrf())
                .cookie(*cookies)
                .param("date", date.toString())
                .param("priceRuleId", ruleId.toString())
                .param("guestCount", "6")
                .param("requestMessage", "승인 부탁드려요"),
        )
            .andExpect(status().isFound)
            .andReturn()

        val reservationId = reservationIdFrom(created.response.getRedirectedUrl())
        assertThat(statusOf(reservationId)).isEqualTo("PENDING_APPROVAL")

        mockMvc.perform(
            post("/spaces/$spaceId/reservations")
                .with(csrf())
                .cookie(*cookies)
                .param("date", date.toString())
                .param("priceRuleId", ruleId.toString())
                .param("guestCount", "6"),
        )
            .andExpect(status().isOk)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("이미 예약된 시간대입니다")))

        val hostId = dsl.select(SPACES.HOST_ID).from(SPACES)
            .where(SPACES.ID.eq(spaceId)).fetchOne(SPACES.HOST_ID)!!
        reservationService.approve(hostId, reservationId)

        mockMvc.perform(get("/reservations/$reservationId/pay").cookie(*cookies))
            .andExpect(status().isOk)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("결제하기")))

        mockMvc.perform(post("/reservations/$reservationId/pay").with(csrf()).cookie(*cookies))
            .andExpect(status().isFound)

        assertThat(statusOf(reservationId)).isEqualTo("CONFIRMED")
    }

    @Test
    fun `다른 회원의 예약 상세는 403이다`() {
        val ownerCookies = login(MEMBER_EMAIL)
        val otherCookies = login("seed-member-2@lookation.local")
        val reservationId = dsl.select(RESERVATIONS.ID).from(RESERVATIONS)
            .where(RESERVATIONS.MEMBER_ID.eq(accountId(MEMBER_EMAIL)))
            .orderBy(RESERVATIONS.ID.desc())
            .limit(1)
            .fetchOne(RESERVATIONS.ID)!!

        mockMvc.perform(get("/mypage/reservations/$reservationId").cookie(*otherCookies))
            .andExpect(status().isForbidden)
            .andExpect(content().string(org.hamcrest.Matchers.containsString("접근 권한이 없습니다")))
    }

    private fun login(email: String): Array<Cookie> {
        val result = mockMvc.perform(
            post("/login")
                .with(csrf())
                .param("email", email)
                .param("password", PASSWORD),
        )
            .andExpect(status().isFound)
            .andExpect(header().string("Location", "/spaces"))
            .andReturn()

        val access = result.response.getCookie("access_token")
        val refresh = result.response.getCookie("refresh_token")
        assertThat(access).describedAs("access cookie").isNotNull
        assertThat(refresh).describedAs("refresh cookie").isNotNull
        return arrayOf(access!!, refresh!!)
    }

    private fun deposit(amount: Long) {
        val memberId = accountId(MEMBER_EMAIL)
        walletRepository.ensureWallet(memberId)
        walletRepository.deposit(memberId, amount)
        walletRepository.addEntry(memberId, com.lookation.domain.WalletEntryType.CHARGE, amount, "CHARGE", null)
    }

    private fun accountId(email: String): Long =
        dsl.select(ACCOUNTS.ID).from(ACCOUNTS).where(ACCOUNTS.EMAIL.eq(email)).fetchOne(ACCOUNTS.ID)!!

    private fun spaceId(name: String): Long =
        dsl.select(SPACES.ID).from(SPACES).where(SPACES.NAME.eq(name)).fetchOne(SPACES.ID)!!

    private fun priceRuleId(spaceId: Long, dayOfWeek: Int): Long =
        dsl.select(PRICE_RULES.ID).from(PRICE_RULES)
            .where(PRICE_RULES.SPACE_ID.eq(spaceId))
            .and(PRICE_RULES.DAY_OF_WEEK.eq(dayOfWeek.toShort()))
            .and(PRICE_RULES.UNIT.eq("HOUR"))
            .fetchOne(PRICE_RULES.ID)!!

    private fun reservationIdFrom(url: String?): Long =
        requireNotNull(url).substringAfterLast("/").substringBefore("?").toLong()

    private fun statusOf(reservationId: Long): String? =
        dsl.select(RESERVATIONS.STATUS).from(RESERVATIONS)
            .where(RESERVATIONS.ID.eq(reservationId)).fetchOne(RESERVATIONS.STATUS)
}
