package com.lookation.integration

import com.lookation.jooq.tables.Accounts.ACCOUNTS
import com.lookation.jooq.tables.PriceRules.PRICE_RULES
import com.lookation.jooq.tables.Spaces.SPACES
import com.lookation.jooq.tables.Reservations.RESERVATIONS
import com.lookation.jooq.tables.Settlements.SETTLEMENTS
import com.lookation.jooq.tables.Wallets.WALLETS
import org.assertj.core.api.Assertions.assertThat
import com.lookation.service.reservation.ReservationBatchService
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.context.annotation.Import
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientResponseException
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.time.LocalDate
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.UUID

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "lookation.jwt.secret=0123456789abcdef0123456789abcdef",
        "lookation.encryption.key=AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQE=",
        "lookation.upload-dir=build/test-uploads",
    ],
)
@Import(TestMailConfig::class)
@Testcontainers
class ReservationFlowTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:18-alpine"))

        private val mapType = object : ParameterizedTypeReference<Map<String, Any>>() {}
        private val kst = ZoneId.of("Asia/Seoul")
    }

    @Value("\${local.server.port}")
    private var port: Int = 0

    @Autowired
    private lateinit var dsl: DSLContext

    @Autowired
    private lateinit var mailSender: CapturingMailSender

    @Autowired
    private lateinit var batch: ReservationBatchService

    @Autowired
    private lateinit var jwtEncoder: JwtEncoder

    private val client by lazy { RestClient.create("http://localhost:$port") }

    private data class ApiResponse(val status: HttpStatusCode, val body: Map<String, Any>?)

    @Test
    fun `creating a reservation holds the price and blocks overlapping bookings`() {
        val (memberId, token) = signup()
        topUp(memberId, 300_000)
        val slot = slot(daysFromNow = 10)
        val spaceId = publishedSpace(price = 100_000, slot = slot)

        val created = post(
            "/api/v1/reservations",
            mapOf(
                "spaceId" to spaceId,
                "startAt" to slot.start.toString(),
                "endAt" to slot.end.toString(),
                "guestCount" to 4,
            ),
            token,
        )
        assertThat(created.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(created.body!!["status"]).isEqualTo("CONFIRMED")
        assertThat(created.body["price"]).isEqualTo(100_000)
        assertThat(balance(memberId)).isEqualTo(200_000)

        val overlap = post(
            "/api/v1/reservations",
            mapOf(
                "spaceId" to spaceId,
                "startAt" to slot.start.toString(),
                "endAt" to slot.end.toString(),
                "guestCount" to 2,
            ),
            token,
        )
        assertThat(overlap.status.value()).isEqualTo(HttpStatus.CONFLICT.value())
        assertThat(balance(memberId)).isEqualTo(200_000)
    }

    @Test
    fun `cancelling ten days ahead refunds the full price`() {
        val (memberId, token) = signup()
        topUp(memberId, 100_000)
        val slot = slot(daysFromNow = 10)
        val spaceId = publishedSpace(price = 100_000, slot = slot)
        val reservationId = createReservation(token, spaceId, slot)

        val cancelled = post("/api/v1/reservations/$reservationId/cancel", emptyMap(), token)

        assertThat(cancelled.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(cancelled.body!!["refundedAmount"]).isEqualTo(100_000)
        assertThat(cancelled.body["status"]).isEqualTo("CANCELLED")
        assertThat(balance(memberId)).isEqualTo(100_000)
    }

    @Test
    fun `cancelling two days ahead refunds half the price`() {
        val (memberId, token) = signup()
        topUp(memberId, 100_000)
        val slot = slot(daysFromNow = 2)
        val spaceId = publishedSpace(price = 100_000, slot = slot)
        val reservationId = createReservation(token, spaceId, slot)

        val cancelled = post("/api/v1/reservations/$reservationId/cancel", emptyMap(), token)

        assertThat(cancelled.body!!["refundedAmount"]).isEqualTo(50_000)
        assertThat(balance(memberId)).isEqualTo(50_000)
    }

    @Test
    fun `reservation fails when the wallet balance is too low`() {
        val (memberId, token) = signup()
        topUp(memberId, 10_000)
        val slot = slot(daysFromNow = 10)
        val spaceId = publishedSpace(price = 100_000, slot = slot)

        val result = post(
            "/api/v1/reservations",
            mapOf(
                "spaceId" to spaceId,
                "startAt" to slot.start.toString(),
                "endAt" to slot.end.toString(),
                "guestCount" to 2,
            ),
            token,
        )

        assertThat(result.status.value()).isEqualTo(HttpStatus.CONFLICT.value())
        assertThat(balance(memberId)).isEqualTo(10_000)
    }

    @Test
    fun `cancelling twice refunds only once`() {
        val (memberId, token) = signup()
        topUp(memberId, 100_000)
        val slot = slot(daysFromNow = 10)
        val spaceId = publishedSpace(price = 100_000, slot = slot)
        val reservationId = createReservation(token, spaceId, slot)

        assertThat(post("/api/v1/reservations/$reservationId/cancel", emptyMap(), token).status.value())
            .isEqualTo(HttpStatus.OK.value())
        assertThat(post("/api/v1/reservations/$reservationId/cancel", emptyMap(), token).status.value())
            .isEqualTo(HttpStatus.CONFLICT.value())
        assertThat(balance(memberId)).isEqualTo(100_000)
    }

    @Test
    fun `guest count above capacity is rejected`() {
        val (memberId, token) = signup()
        topUp(memberId, 100_000)
        val slot = slot(daysFromNow = 10)
        val spaceId = publishedSpace(price = 100_000, slot = slot, capacity = 4)

        val result = post(
            "/api/v1/reservations",
            mapOf(
                "spaceId" to spaceId,
                "startAt" to slot.start.toString(),
                "endAt" to slot.end.toString(),
                "guestCount" to 5,
            ),
            token,
        )

        assertThat(result.status.value()).isEqualTo(HttpStatus.CONFLICT.value())
        assertThat(balance(memberId)).isEqualTo(100_000)
    }

    @Test
    fun `day rule allows a full day booking`() {
        val (memberId, token) = signup()
        topUp(memberId, 200_000)
        val date = LocalDate.now(kst).plusDays(10)
        val spaceId = publishedSpaceWithDayRule(price = 150_000, date = date)

        val result = post(
            "/api/v1/reservations",
            mapOf(
                "spaceId" to spaceId,
                "startAt" to date.atStartOfDay(kst).toOffsetDateTime().toString(),
                "endAt" to date.plusDays(1).atStartOfDay(kst).toOffsetDateTime().toString(),
                "guestCount" to 2,
            ),
            token,
        )

        assertThat(result.status.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(result.body!!["price"]).isEqualTo(150_000)
        assertThat(balance(memberId)).isEqualTo(50_000)
    }

    @Test
    fun `approval reservation is confirmed only after approval and payment`() {
        val host = signup()
        val (memberId, token) = signup()
        topUp(memberId, 200_000)
        val slot = slot(daysFromNow = 10)
        val spaceId = publishedSpace(
            price = 100_000,
            slot = slot,
            hostId = host.first,
            approvalRequired = true,
        )

        val created = post(
            "/api/v1/reservations",
            mapOf(
                "spaceId" to spaceId,
                "startAt" to slot.start.toString(),
                "endAt" to slot.end.toString(),
                "guestCount" to 2,
            ),
            token,
        )
        assertThat(created.body!!["status"]).isEqualTo("PENDING_APPROVAL")
        assertThat(balance(memberId)).isEqualTo(200_000)
        val reservationId = (created.body["id"] as Number).toLong()

        val approved = post("/api/v1/reservations/$reservationId/approve", emptyMap(), host.second)
        assertThat(approved.body!!["status"]).isEqualTo("PENDING_PAYMENT")
        assertThat(balance(memberId)).isEqualTo(200_000)

        val paid = post("/api/v1/reservations/$reservationId/pay", emptyMap(), token)
        assertThat(paid.body!!["status"]).isEqualTo("CONFIRMED")
        assertThat(balance(memberId)).isEqualTo(100_000)
    }

    @Test
    fun `batch completes, settles and expires reservations`() {
        val host = signup()
        val (memberId, token) = signup()
        topUp(memberId, 100_000)
        val slot = slot(daysFromNow = 10)
        val spaceId = publishedSpace(price = 100_000, slot = slot, hostId = host.first)
        val reservationId = createReservation(token, spaceId, slot)

        dsl.update(RESERVATIONS)
            .set(RESERVATIONS.START_AT, OffsetDateTime.now().minusDays(2))
            .set(RESERVATIONS.END_AT, OffsetDateTime.now().minusDays(1))
            .where(RESERVATIONS.ID.eq(reservationId))
            .execute()

        assertThat(batch.completeReservations()).isEqualTo(1)
        assertThat(
            dsl.select(RESERVATIONS.STATUS).from(RESERVATIONS)
                .where(RESERVATIONS.ID.eq(reservationId)).fetchOne(RESERVATIONS.STATUS),
        ).isEqualTo("COMPLETED")

        assertThat(batch.settle()).isEqualTo(1)
        assertThat(hostBalance(host.first)).isEqualTo(90_000)
        assertThat(
            dsl.select(SETTLEMENTS.NET_AMOUNT).from(SETTLEMENTS)
                .where(SETTLEMENTS.RESERVATION_ID.eq(reservationId))
                .fetchOne(SETTLEMENTS.NET_AMOUNT),
        ).isEqualTo(90_000)
    }

    @Test
    fun `expired approval reservations are expired by the batch`() {
        val host = signup()
        val (_, token) = signup()
        val slot = slot(daysFromNow = 10)
        val spaceId = publishedSpace(
            price = 100_000,
            slot = slot,
            hostId = host.first,
            approvalRequired = true,
        )
        val created = post(
            "/api/v1/reservations",
            mapOf(
                "spaceId" to spaceId,
                "startAt" to slot.start.toString(),
                "endAt" to slot.end.toString(),
                "guestCount" to 2,
            ),
            token,
        )
        val reservationId = (created.body!!["id"] as Number).toLong()
        dsl.update(RESERVATIONS)
            .set(RESERVATIONS.CREATED_AT, OffsetDateTime.now().minusHours(25))
            .where(RESERVATIONS.ID.eq(reservationId))
            .execute()

        assertThat(batch.expireReservations()).isEqualTo(1)
        assertThat(
            dsl.select(RESERVATIONS.STATUS).from(RESERVATIONS)
                .where(RESERVATIONS.ID.eq(reservationId)).fetchOne(RESERVATIONS.STATUS),
        ).isEqualTo("EXPIRED")
    }

    @Test
    fun `host reservation list exposes contact only after confirmation`() {
        val host = signup()
        val (memberId, memberToken) = signup()
        topUp(memberId, 200_000)
        val slot = slot(daysFromNow = 10)
        val spaceId = publishedSpace(
            price = 100_000,
            slot = slot,
            hostId = host.first,
            approvalRequired = true,
        )
        val created = post(
            "/api/v1/reservations",
            mapOf(
                "spaceId" to spaceId,
                "startAt" to slot.start.toString(),
                "endAt" to slot.end.toString(),
                "guestCount" to 2,
            ),
            memberToken,
        )
        val reservationId = (created.body!!["id"] as Number).toLong()
        val hostToken = tokenFor(host.first, "HOST")

        val pending = getList("/api/v1/hosts/reservations", hostToken)
        assertThat(pending).hasSize(1)
        assertThat((pending.first() as Map<*, *>)["memberName"]).isNull()

        post("/api/v1/reservations/$reservationId/approve", emptyMap(), hostToken)
        post("/api/v1/reservations/$reservationId/pay", emptyMap(), memberToken)

        val confirmed = getList("/api/v1/hosts/reservations?status=CONFIRMED", hostToken)
        val first = confirmed.first() as Map<*, *>
        assertThat(first["memberName"]).isEqualTo("Member")
        assertThat(first["status"]).isEqualTo("CONFIRMED")
    }

    private fun tokenFor(accountId: Long, vararg roles: String): String {
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer("lookation")
            .subject(accountId.toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(300))
            .claim("roles", roles.toList())
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    private data class Slot(val start: OffsetDateTime, val end: OffsetDateTime)

    private fun slot(daysFromNow: Long): Slot {
        val date = LocalDate.now(kst).plusDays(daysFromNow)
        return Slot(
            start = date.atTime(18, 0).atZone(kst).toOffsetDateTime(),
            end = date.atTime(20, 0).atZone(kst).toOffsetDateTime(),
        )
    }

    private fun signup(): Pair<Long, String> {
        val email = "member-${UUID.randomUUID()}@lookation.test"
        post("/api/v1/auth/email-verifications", mapOf("email" to email, "purpose" to "SIGNUP"))
        val code = mailSender.codes.getValue(email)
        val signup = post(
            "/api/v1/auth/signup",
            mapOf(
                "email" to email,
                "password" to "password123",
                "name" to "Member",
                "nickname" to "member",
                "code" to code,
            ),
        )
        val token = signup.body!!["accessToken"] as String
        val me = client.get()
            .uri("/api/v1/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .retrieve()
            .toEntity(mapType)
        return (me.body!!["id"] as Number).toLong() to token
    }

    private fun topUp(accountId: Long, amount: Long) {
        dsl.insertInto(WALLETS)
            .set(WALLETS.ACCOUNT_ID, accountId)
            .set(WALLETS.BALANCE, amount)
            .execute()
    }

    private fun hostBalance(accountId: Long): Long =
        dsl.select(WALLETS.BALANCE).from(WALLETS)
            .where(WALLETS.ACCOUNT_ID.eq(accountId))
            .fetchOne(WALLETS.BALANCE) ?: 0L

    private fun balance(accountId: Long): Long =
        dsl.select(WALLETS.BALANCE)
            .from(WALLETS)
            .where(WALLETS.ACCOUNT_ID.eq(accountId))
            .fetchOne(WALLETS.BALANCE) ?: 0L

    private fun publishedSpace(
        price: Long,
        slot: Slot,
        capacity: Int = 10,
        hostId: Long? = null,
        approvalRequired: Boolean = false,
    ): Long {
        val resolvedHostId = hostId ?: dsl.insertInto(ACCOUNTS)
            .set(ACCOUNTS.EMAIL, "host-${UUID.randomUUID()}@lookation.test")
            .set(ACCOUNTS.PASSWORD_HASH, "unused")
            .set(ACCOUNTS.NAME, "Host")
            .set(ACCOUNTS.NICKNAME, "host")
            .returningResult(ACCOUNTS.ID)
            .fetchOne()!!
            .value1()

        val spaceId = dsl.insertInto(SPACES)
            .set(SPACES.HOST_ID, resolvedHostId)
            .set(SPACES.NAME, "테스트 공간")
            .set(SPACES.ADDRESS, "서울시 중구")
            .set(SPACES.SPACE_TYPE, "PARTY_ROOM")
            .set(SPACES.MIN_CAPACITY, 1.toShort())
            .set(SPACES.MAX_CAPACITY, capacity.toShort())
            .set(SPACES.STATUS, "PUBLISHED")
            .set(SPACES.APPROVAL_REQUIRED, approvalRequired)
            .returningResult(SPACES.ID)
            .fetchOne()!!
            .value1()

        val kstStart = slot.start.atZoneSameInstant(kst)
        val kstEnd = slot.end.atZoneSameInstant(kst)
        dsl.insertInto(PRICE_RULES)
            .set(PRICE_RULES.SPACE_ID, spaceId)
            .set(PRICE_RULES.DAY_OF_WEEK, kstStart.dayOfWeek.value.toShort())
            .set(PRICE_RULES.UNIT, "HOUR")
            .set(PRICE_RULES.START_TIME, kstStart.toLocalTime())
            .set(PRICE_RULES.END_TIME, kstEnd.toLocalTime())
            .set(PRICE_RULES.PRICE, price)
            .execute()

        return spaceId
    }

    private fun publishedSpaceWithDayRule(price: Long, date: LocalDate): Long {
        val hostId = dsl.insertInto(ACCOUNTS)
            .set(ACCOUNTS.EMAIL, "dayhost-${UUID.randomUUID()}@lookation.test")
            .set(ACCOUNTS.PASSWORD_HASH, "unused")
            .set(ACCOUNTS.NAME, "Host")
            .set(ACCOUNTS.NICKNAME, "host")
            .returningResult(ACCOUNTS.ID)
            .fetchOne()!!
            .value1()

        val spaceId = dsl.insertInto(SPACES)
            .set(SPACES.HOST_ID, hostId)
            .set(SPACES.NAME, "일 단위 공간")
            .set(SPACES.ADDRESS, "서울시 중구")
            .set(SPACES.SPACE_TYPE, "PARTY_ROOM")
            .set(SPACES.MIN_CAPACITY, 1.toShort())
            .set(SPACES.MAX_CAPACITY, 10.toShort())
            .set(SPACES.STATUS, "PUBLISHED")
            .returningResult(SPACES.ID)
            .fetchOne()!!
            .value1()

        dsl.insertInto(PRICE_RULES)
            .set(PRICE_RULES.SPACE_ID, spaceId)
            .set(PRICE_RULES.DAY_OF_WEEK, date.dayOfWeek.value.toShort())
            .set(PRICE_RULES.UNIT, "DAY")
            .set(PRICE_RULES.PRICE, price)
            .execute()

        return spaceId
    }

    private fun createReservation(token: String, spaceId: Long, slot: Slot): Long {
        val response = post(
            "/api/v1/reservations",
            mapOf(
                "spaceId" to spaceId,
                "startAt" to slot.start.toString(),
                "endAt" to slot.end.toString(),
                "guestCount" to 2,
            ),
            token,
        )
        return (response.body!!["id"] as Number).toLong()
    }

    private fun getList(path: String, token: String): List<Any> =
        client.get()
            .uri(path)
            .header("Authorization", "Bearer $token")
            .retrieve()
            .toEntity(object : org.springframework.core.ParameterizedTypeReference<List<Any>>() {})
            .body ?: emptyList()

    private fun post(path: String, body: Map<String, Any>, token: String? = null): ApiResponse =
        try {
            var request = client.post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
            if (token != null) {
                request = request.header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }
            val entity = request.body(body).retrieve().toEntity(mapType)
            ApiResponse(entity.statusCode, entity.body)
        } catch (exception: RestClientResponseException) {
            ApiResponse(exception.statusCode, null)
        }
}
