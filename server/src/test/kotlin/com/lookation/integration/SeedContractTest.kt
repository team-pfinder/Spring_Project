package com.lookation.integration

import com.lookation.domain.AccountStatus
import com.lookation.global.seed.AccountSeed
import com.lookation.global.seed.InitializeService
import com.lookation.global.seed.SeedResult
import com.lookation.jooq.tables.Accounts.ACCOUNTS
import com.lookation.repository.AccountRepository
import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.security.crypto.password.PasswordEncoder
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@SpringBootTest(
    properties = [
        "lookation.jwt.secret=0123456789abcdef0123456789abcdef",
        "lookation.encryption.key=AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQE=",
        "lookation.upload-dir=build/test-uploads",
        "lookation.admin.email=admin@lookation.local",
        "lookation.admin.password=lookation-admin1!",
    ],
)
@Testcontainers
class SeedContractTest {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:18-alpine"))

        private const val SEED_PASSWORD = "lookation1234!"

        private const val MEMBER_EMAIL = "seed-member-1@lookation.local"
        private const val ADMIN_EMAIL = "admin@lookation.local"
    }

    @Autowired
    private lateinit var accountSeed: AccountSeed

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var dsl: DSLContext

    @Test
    fun `seeding twice keeps one row per fixed email and defers to the admin seeder`() {
        val service = InitializeService(listOf(accountSeed))

        assertThat(accountCount(ADMIN_EMAIL)).isEqualTo(1)

        val first = service.seed()
        val second = service.seed()

        assertThat(first).isEqualTo(SeedResult(inserted = 5, skipped = 1))
        assertThat(second).isEqualTo(SeedResult(inserted = 0, skipped = 6))

        listOf(ADMIN_EMAIL, MEMBER_EMAIL).forEach { email ->
            assertThat(accountCount(email)).describedAs(email).isEqualTo(1)
        }

        assertThat(accountRepository.roles(accountRepository.findByEmail(ADMIN_EMAIL)!!.id))
            .containsExactly("ADMIN")
        assertThat(accountRepository.roles(accountRepository.findByEmail(MEMBER_EMAIL)!!.id))
            .containsExactly("MEMBER")

        val member = accountRepository.findByEmail(MEMBER_EMAIL)!!
        assertThat(member.status).isEqualTo(AccountStatus.ACTIVE)
        assertThat(passwordEncoder.matches(SEED_PASSWORD, member.passwordHash)).isTrue()
    }

    private fun accountCount(email: String): Int =
        dsl.fetchCount(ACCOUNTS, ACCOUNTS.EMAIL.equalIgnoreCase(email))
}
