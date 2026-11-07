package com.lookation.global.security

import com.lookation.domain.AccountRole
import com.lookation.repository.AccountRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Order(AdminSeeder.ORDER)
class AdminSeeder(
    private val properties: AdminProperties,
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        if (properties.email.isBlank() || properties.password.isBlank()) return
        if (accountRepository.existsByEmail(properties.email)) return

        val accountId = accountRepository.insert(
            email = properties.email,
            passwordHash = requireNotNull(passwordEncoder.encode(properties.password)),
            name = "Admin",
            nickname = "admin",
        )
        accountRepository.addRole(accountId, AccountRole.ADMIN)
    }

    companion object {
        /** Runs before other startup seeders ([com.lookation.global.seed.InitializeService]). */
        const val ORDER = 0
    }
}
