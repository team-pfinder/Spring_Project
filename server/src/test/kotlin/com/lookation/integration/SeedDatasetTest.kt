package com.lookation.integration

import com.lookation.domain.AccountStatus
import com.lookation.domain.AttachmentOwnerType
import com.lookation.domain.ChargeStatus
import com.lookation.domain.InspectionStatus
import com.lookation.domain.JobStatus
import com.lookation.domain.QnaStatus
import com.lookation.domain.ReportStatus
import com.lookation.domain.ReservationStatus
import com.lookation.domain.SpaceStatus
import com.lookation.domain.SpaceType
import com.lookation.domain.VerificationPurpose
import com.lookation.domain.WithdrawalStatus
import com.lookation.global.seed.AttachmentSeed
import com.lookation.global.seed.InitializeService
import com.lookation.global.seed.SeedStep
import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "lookation.jwt.secret=0123456789abcdef0123456789abcdef",
        "lookation.encryption.key=AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQE=",
        "lookation.upload-dir=build/test-uploads",
        "lookation.admin.email=admin@lookation.local",
        "lookation.admin.password=lookation-admin1!",
        // Unique property keeps the Spring context cache from reusing another container context.
        "lookation.test.suite=seed-dataset",
    ],
)
@Testcontainers
class SeedDatasetTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:18-alpine"))

        private const val SEED_PASSWORD = "lookation1234!"
        private const val ADMIN_PASSWORD = "lookation-admin1!"
        private val mapType = object : ParameterizedTypeReference<Map<String, Any>>() {}
        private val listType = object : ParameterizedTypeReference<List<Map<String, Any>>>() {}
        private val stringListType = object : ParameterizedTypeReference<List<String>>() {}

        private val TABLES = listOf(
            "accounts", "account_roles", "host_profiles", "sanctions", "spaces", "space_facilities",
            "attachments", "price_rules", "reservations", "reservation_events", "wallets", "wallet_entries",
            "charges", "settlements", "withdrawals", "inspections", "job_runs", "messages",
            "reports", "reviews", "review_replies", "qnas", "qna_replies", "notices", "helps",
            "email_verifications",
        )
    }

    @Autowired
    private lateinit var steps: List<SeedStep>

    @Autowired
    private lateinit var dsl: DSLContext

    @Value("\${local.server.port}")
    private var port: Int = 0

    @Value("\${lookation.upload-dir}")
    private lateinit var uploadDir: String

    private val client by lazy { RestClient.create("http://localhost:$port") }

    @Test
    fun `seed inserts the dataset once and a second run changes nothing`() {
        val service = InitializeService(steps)
        service.seed()

        val counts = tableCounts()
        assertThat(counts["accounts"]).isEqualTo(6)
        assertThat(counts["spaces"]).isEqualTo(6)
        assertThat(counts["reservations"]).isEqualTo(10)
        assertThat(counts["attachments"]).isEqualTo(8)

        assertThat(service.seed().inserted).isZero()
        assertThat(tableCounts()).isEqualTo(counts)
    }

    @Test
    fun `dataset covers every domain enum`() {
        InitializeService(steps).seed()

        assertThat(distinct("accounts", "status"))
            .containsExactlyInAnyOrderElementsOf(AccountStatus.entries.map { it.name })
        assertThat(distinct("spaces", "space_type"))
            .containsExactlyInAnyOrderElementsOf(SpaceType.entries.map { it.name })
        assertThat(distinct("spaces", "status"))
            .containsExactlyInAnyOrderElementsOf(SpaceStatus.entries.map { it.name })
        assertThat(distinct("inspections", "status"))
            .containsExactlyInAnyOrderElementsOf(InspectionStatus.entries.map { it.name })
        assertThat(distinct("reservations", "status"))
            .containsExactlyInAnyOrderElementsOf(ReservationStatus.entries.map { it.name })
        assertThat(distinct("qnas", "status"))
            .containsExactlyInAnyOrderElementsOf(QnaStatus.entries.map { it.name })
        assertThat(distinct("reports", "status"))
            .containsExactlyInAnyOrderElementsOf(ReportStatus.entries.map { it.name })
        assertThat(distinct("charges", "status"))
            .containsExactlyInAnyOrderElementsOf(ChargeStatus.entries.map { it.name })
        assertThat(distinct("withdrawals", "status"))
            .containsExactlyInAnyOrderElementsOf(WithdrawalStatus.entries.map { it.name })
        assertThat(distinct("job_runs", "status"))
            .containsExactlyInAnyOrderElementsOf(JobStatus.entries.map { it.name })
        assertThat(distinct("email_verifications", "purpose"))
            .containsExactlyInAnyOrderElementsOf(VerificationPurpose.entries.map { it.name })
        assertThat(distinct("reservation_events", "actor_type"))
            .containsExactlyInAnyOrder("MEMBER", "HOST", "ADMIN", "SYSTEM")
        assertThat(distinct("attachments", "owner_type"))
            .containsExactlyInAnyOrderElementsOf(AttachmentOwnerType.entries.map { it.name })
    }

    @Test
    fun `attachment rows point at files that exist on disk`() {
        InitializeService(steps).seed()

        val rows = dsl.selectFrom(ATTACHMENTS).fetch()
        assertThat(rows).isNotEmpty

        val root = Paths.get(uploadDir).toAbsolutePath().normalize()
        rows.forEach { row ->
            val path = root.resolve(row.get(ATTACHMENTS.STORAGE_KEY))
            assertThat(path).describedAs(row.get(ATTACHMENTS.STORAGE_KEY)).exists()
            assertThat(Files.size(path)).isEqualTo(row.get(ATTACHMENTS.SIZE_BYTES))
            assertThat(row.get(ATTACHMENTS.CONTENT_TYPE)).startsWith("image/")
        }

        val spaceOwners = rows.filter { it.get(ATTACHMENTS.OWNER_TYPE) == "SPACE" }
            .map { it.get(ATTACHMENTS.OWNER_ID) }
            .toSet()
        assertThat(spaceOwners).hasSize(6)

        rows.groupBy { it.get(ATTACHMENTS.OWNER_TYPE) to it.get(ATTACHMENTS.OWNER_ID) }
            .forEach { (owner, ownerRows) ->
                assertThat(ownerRows.map { it.get(ATTACHMENTS.SORT_ORDER).toInt() }.sorted())
                    .describedAs("sort order for $owner")
                    .containsExactlyElementsOf((0 until ownerRows.size).toList())
            }

        val filesUnderSeed = Files.list(root.resolve("seed")).use { paths ->
            paths.filter { Files.isRegularFile(it) }.map { root.relativize(it).toString() }.toList()
        }
        assertThat(filesUnderSeed)
            .containsExactlyInAnyOrderElementsOf(rows.map { it.get(ATTACHMENTS.STORAGE_KEY) })
    }

    @Test
    fun `missing legacy image source skips attachments without failing`() {
        InitializeService(steps).seed()
        dsl.deleteFrom(ATTACHMENTS).execute()

        val missing = AttachmentSeed(dsl, uploadDir, "build/no-such-legacy-images")
        val result = InitializeService(listOf(missing)).seed()

        assertThat(result.inserted).isZero()
        assertThat(result.skipped).isEqualTo(8)
        assertThat(dsl.fetchCount(ATTACHMENTS)).isZero()

        InitializeService(steps).seed()
        assertThat(dsl.fetchCount(ATTACHMENTS)).isEqualTo(8)
    }

    @Test
    fun `wallet balances equal their ledger entries and completed reservations are settled`() {
        InitializeService(steps).seed()

        assertWalletLedgerConsistent(dsl)

        val completed = reservations(ReservationStatus.COMPLETED)
        assertThat(completed).isNotEmpty

        val settlements = dsl.resultQuery(
            """
            select s.reservation_id, s.gross_amount, s.fee_amount, s.net_amount
            from settlements s
            """.trimIndent(),
        ).fetch().associateBy { it.get("reservation_id", Long::class.java) }

        assertThat(settlements).hasSize(completed.size)
        completed.forEach { reservation ->
            val settlement = settlements[reservation.get("id", Long::class.java)]
            assertThat(settlement).describedAs("settlement for reservation ${reservation.get("id")}").isNotNull
            val price = reservation.get("price", Long::class.java)
            val expectedFee = BigDecimal.valueOf(price).multiply(BigDecimal("0.10"))
                .setScale(0, RoundingMode.DOWN).toLong()
            assertThat(settlement!!.get("gross_amount", Long::class.java)).isEqualTo(price)
            assertThat(settlement.get("fee_amount", Long::class.java)).isEqualTo(expectedFee)
            assertThat(settlement.get("net_amount", Long::class.java)).isEqualTo(price - expectedFee)
        }
    }

    @Test
    fun `seeded data drives the five screens without extra fixtures`() {
        InitializeService(steps).seed()

        val memberToken = login("seed-member-1@lookation.local", SEED_PASSWORD)
        val hostToken = login("seed-host-1@lookation.local", SEED_PASSWORD)
        val adminToken = login("admin@lookation.local", ADMIN_PASSWORD)

        // 1. 공간 검색·상세
        val spaceItems = get("/api/v1/spaces", null)["items"] as List<*>
        assertThat(spaceItems).isNotEmpty
        val searchHit = spaceItems.first() as Map<*, *>
        val spaceId = (searchHit["id"] as Number).toLong()
        val detail = get("/api/v1/spaces/$spaceId", null)
        assertThat(detail["name"]).isNotNull
        assertThat(detail["status"]).isEqualTo("PUBLISHED")

        val images = getStrings("/api/v1/spaces/$spaceId/images", null)
        assertThat(images).isNotEmpty
        val image = client.get().uri(images.first()).retrieve().toEntity(ByteArray::class.java)
        assertThat(image.statusCode.value()).isEqualTo(200)
        assertThat(image.headers.contentType.toString()).startsWith("image/")
        assertThat(image.body!!.size).isGreaterThan(0)

        // 2. 회원 예약 목록
        val memberReservations = getList("/api/v1/reservations", memberToken)
        assertThat(memberReservations.map { it["status"] })
            .contains("COMPLETED", "CONFIRMED", "CANCELLED")

        // 3. 호스트 예약 목록
        val hostReservations = getList("/api/v1/hosts/reservations", hostToken)
        assertThat(hostReservations).isNotEmpty
        val hostProfile = get("/api/v1/me/host-profile", hostToken)
        assertThat(hostProfile["bankAccountNumberMasked"]).isEqualTo("****1234")

        // 4. 관리자 검수 목록
        val inspections = getList("/api/v1/admin/inspections", adminToken)
        assertThat(inspections).isNotEmpty

        // 5. 공지·리뷰·문의
        assertThat(get("/api/v1/notices", null)["items"] as List<*>).isNotEmpty
        val reviews = get("/api/v1/spaces/$spaceId/reviews", null)["items"] as List<*>
        assertThat(reviews).isNotEmpty
        val reviewImages = (reviews.first() as Map<*, *>)["images"] as List<*>
        assertThat(reviewImages).isNotEmpty
        val reviewImage = client.get().uri(reviewImages.first() as String).retrieve().toEntity(ByteArray::class.java)
        assertThat(reviewImage.statusCode.value()).isEqualTo(200)
        assertThat(reviewImage.body!!.size).isGreaterThan(0)
        assertThat(get("/api/v1/spaces/$spaceId/qnas", null)["items"] as List<*>).isNotEmpty
    }

    private fun login(email: String, password: String): String {
        val response = client.post()
            .uri("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapOf("email" to email, "password" to password))
            .retrieve()
            .toEntity(mapType)
        return response.body!!["accessToken"] as String
    }

    private fun get(path: String, token: String?): Map<String, Any> =
        client.get()
            .uri(path)
            .apply { if (token != null) header(HttpHeaders.AUTHORIZATION, "Bearer $token") }
            .retrieve()
            .toEntity(mapType)
            .body!!

    private fun getList(path: String, token: String?): List<Map<String, Any>> =
        client.get()
            .uri(path)
            .apply { if (token != null) header(HttpHeaders.AUTHORIZATION, "Bearer $token") }
            .retrieve()
            .toEntity(listType)
            .body!!

    private fun getStrings(path: String, token: String?): List<String> =
        client.get()
            .uri(path)
            .apply { if (token != null) header(HttpHeaders.AUTHORIZATION, "Bearer $token") }
            .retrieve()
            .toEntity(stringListType)
            .body!!

    private fun distinct(table: String, column: String): List<String> =
        dsl.resultQuery("select distinct $column from $table").fetch(0, String::class.java)

    private fun reservations(status: ReservationStatus) =
        dsl.resultQuery(
            "select id, price from reservations where status = ?",
            status.name,
        ).fetch()

    private fun tableCounts(): Map<String, Int> =
        TABLES.associateWith { dsl.fetchCount(DSL.table(it)) }
}
