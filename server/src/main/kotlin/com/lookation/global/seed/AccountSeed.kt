package com.lookation.global.seed

import com.lookation.domain.AccountRole
import com.lookation.domain.AccountStatus
import com.lookation.repository.AccountRepository
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * Seeds the fixed local accounts: three members (one withdrawn), two hosts and the admin email
 * shared with `AdminSeeder`. Existing rows are skipped as-is, so the admin keeps its credential.
 *
 * Every seeded account uses the fixed password [SEED_PASSWORD] (documented in `server/README.md`).
 */
@Component
@Order(AccountSeed.ORDER)
class AccountSeed(
    private val accounts: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val seedAdmin: SeedAdmin,
) : SeedStep {

    override val name = "계정"

    @Transactional
    override fun seed(): SeedResult {
        var inserted = 0
        var skipped = 0

        (SEED_ACCOUNTS + SeedAccount(seedAdmin.email, "Admin", "admin", AccountStatus.ACTIVE, listOf("ADMIN"))).forEach { seed ->
            if (accounts.existsByEmail(seed.email)) {
                skipped++
                return@forEach
            }
            val accountId = accounts.insert(
                email = seed.email,
                passwordHash = requireNotNull(passwordEncoder.encode(SEED_PASSWORD)),
                name = seed.name,
                nickname = seed.nickname,
                status = seed.status,
            )
            seed.roles.forEach { accounts.addRole(accountId, AccountRole.valueOf(it)) }
            inserted++
        }

        return SeedResult(inserted = inserted, skipped = skipped)
    }

    private data class SeedAccount(
        val email: String,
        val name: String,
        val nickname: String,
        val status: AccountStatus,
        val roles: List<String>,
    )

    companion object {
        const val ORDER = 10
        const val SEED_PASSWORD = "lookation1234!"

        private val SEED_ACCOUNTS = listOf(
            SeedAccount("seed-member-1@lookation.local", "시드회원1", "seed-member-1", AccountStatus.ACTIVE, listOf("MEMBER")),
            SeedAccount("seed-member-2@lookation.local", "시드회원2", "seed-member-2", AccountStatus.ACTIVE, listOf("MEMBER")),
            SeedAccount("seed-member-3@lookation.local", "시드회원3", "seed-member-3", AccountStatus.WITHDRAWN, listOf("MEMBER")),
            SeedAccount("seed-host-1@lookation.local", "시드호스트1", "seed-host-1", AccountStatus.ACTIVE, listOf("MEMBER", "HOST")),
            SeedAccount("seed-host-2@lookation.local", "시드호스트2", "seed-host-2", AccountStatus.ACTIVE, listOf("MEMBER", "HOST")),
        )
    }
}
