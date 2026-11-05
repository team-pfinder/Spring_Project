package com.lookation.integration

import com.lookation.global.seed.InitializeService
import com.lookation.global.seed.SeedStep
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@SpringBootTest(
    properties = [
        "lookation.jwt.secret=0123456789abcdef0123456789abcdef",
        "lookation.encryption.key=AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQE=",
        "lookation.upload-dir=build/test-uploads",
        "lookation.admin.email=ops@lookation.local",
        "lookation.admin.password=lookation-admin1!",
        // Unique property keeps the Spring context cache from reusing another container context.
        "lookation.test.suite=seed-admin-config",
    ],
)
@Testcontainers
class SeedAdminConfigTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:18-alpine"))
    }

    @Autowired
    private lateinit var steps: List<SeedStep>

    @Autowired
    private lateinit var dsl: DSLContext

    @Test
    fun `seed follows the configured admin email instead of the default address`() {
        InitializeService(steps).seed()

        val admins = dsl.resultQuery(
            """
            select a.email
            from accounts a join account_roles r on r.account_id = a.id
            where r.role = 'ADMIN'
            """.trimIndent(),
        ).fetch(0, String::class.java)

        assertThat(admins).containsExactly("ops@lookation.local")
        assertThat(
            dsl.resultQuery("select count(*) from accounts where email = 'admin@lookation.local'")
                .fetchOne(0, Int::class.java),
        ).isZero()
    }
}
