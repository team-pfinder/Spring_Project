package com.lookation.global.security

import com.lookation.account.infrastructure.AccountRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
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
        accountRepository.addRole(accountId, "ADMIN")
    }
}
