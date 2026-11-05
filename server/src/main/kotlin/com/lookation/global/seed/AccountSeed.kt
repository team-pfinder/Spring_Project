package com.lookation.global.seed

import com.lookation.repository.AccountRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * Seeds the fixed local accounts needed to exercise the seed contract: one member and the
 * admin email shared with `AdminSeeder`. The full state dataset (more accounts, spaces,
 * reservations) is added by later steps implementing [SeedStep].
 *
 * The natural key is the fixed email. An existing account is skipped as-is — roles and
 * password are not re-synced — so the admin created by `AdminSeeder` keeps its credential.
 * Every seeded account uses the fixed password [SEED_PASSWORD] (documented in `server/README.md`).
 */
@Component
class AccountSeed(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
) : SeedStep {

    override val name = "계정"

    @Transactional
    override fun seed(): SeedResult {
        var inserted = 0
        var skipped = 0

        ACCOUNTS.forEach { seed ->
            if (accountRepository.existsByEmail(seed.email)) {
                skipped++
                return@forEach
            }
            val accountId = accountRepository.insert(
                email = seed.email,
                passwordHash = requireNotNull(passwordEncoder.encode(SEED_PASSWORD)),
                name = seed.name,
                nickname = seed.nickname,
            )
            seed.roles.forEach { accountRepository.addRole(accountId, it) }
            inserted++
        }

        return SeedResult(inserted = inserted, skipped = skipped)
    }

    private data class SeedAccount(
        val email: String,
        val name: String,
        val nickname: String,
        val roles: List<String>,
    )

    private companion object {
        const val SEED_PASSWORD = "lookation1234!"

        val ACCOUNTS = listOf(
            SeedAccount("seed-member-1@lookation.local", "시드회원1", "seed-member-1", listOf("MEMBER")),
            SeedAccount("admin@lookation.local", "Admin", "admin", listOf("ADMIN")),
        )
    }
}
