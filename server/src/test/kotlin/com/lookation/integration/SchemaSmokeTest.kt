package com.lookation.integration

import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.dao.DataAccessException
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(
    properties = [
        "lookation.jwt.secret=0123456789abcdef0123456789abcdef",
        "lookation.encryption.key=AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQE=",
        "lookation.upload-dir=build/test-uploads",
    ],
)
@Testcontainers
class SchemaSmokeTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:18-alpine"))
    }

    @Autowired
    lateinit var dsl: DSLContext

    @Test
    fun `flyway applies baseline and constraints`() {
        val tables = dsl.resultQuery(
            """
            select table_name from information_schema.tables
            where table_schema = 'public' and table_name <> 'flyway_schema_history'
            """.trimIndent()
        ).fetch(0, String::class.java)

        assertThat(tables).contains("accounts", "reservations", "wallet_entries", "inspections")
        assertThat(tables).hasSize(27)

        val constraints = dsl.resultQuery(
            "select conname from pg_constraint where conname = 'reservations_no_overlap'"
        ).fetch(0, String::class.java)
        assertThat(constraints).containsExactly("reservations_no_overlap")
    }

    @Test
    fun `attachments reject unknown owner types and duplicate sort order`() {
        fun insert(ownerType: String, ownerId: Long, sortOrder: Short) {
            dsl.insertInto(ATTACHMENTS)
                .set(ATTACHMENTS.OWNER_TYPE, ownerType)
                .set(ATTACHMENTS.OWNER_ID, ownerId)
                .set(ATTACHMENTS.STORAGE_KEY, "smoke/$ownerId-$sortOrder.png")
                .set(ATTACHMENTS.ORIGINAL_NAME, "smoke.png")
                .set(ATTACHMENTS.CONTENT_TYPE, "image/png")
                .set(ATTACHMENTS.SIZE_BYTES, 10L)
                .set(ATTACHMENTS.SORT_ORDER, sortOrder)
                .execute()
        }

        val ownerId = System.nanoTime()
        insert("SPACE", ownerId, 0)

        assertThatThrownBy { insert("SPACE", ownerId, 0) }
            .isInstanceOf(DataAccessException::class.java)
        assertThatThrownBy { insert("QNA", ownerId, 1) }
            .isInstanceOf(DataAccessException::class.java)
    }

    @Test
    fun `generated jOOQ classes insert and read an account`() {
        val id = dsl.insertInto(DSL.table("accounts"))
            .set(DSL.field("email"), "smoke@lookation.test")
            .set(DSL.field("password_hash"), "hash")
            .set(DSL.field("name"), "Smoke")
            .set(DSL.field("nickname"), "smoke")
            .returning(DSL.field("id"))
            .fetchOne()!!.get(0, Long::class.java)

        val email = dsl.resultQuery("select email from accounts where id = ?", id)
            .fetchOne(0, String::class.java)

        assertThat(email).isEqualTo("smoke@lookation.test")
    }
}
