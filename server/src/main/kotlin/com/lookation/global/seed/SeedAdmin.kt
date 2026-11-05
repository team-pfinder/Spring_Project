package com.lookation.global.seed

import com.lookation.global.security.AdminProperties
import com.lookation.jooq.tables.Accounts.ACCOUNTS
import org.jooq.DSLContext
import org.springframework.stereotype.Component

/**
 * Resolves the admin account the seed should attach to inspections, sanctions, charges and
 * reports. Uses the configured `lookation.admin.email` when present so an overridden admin
 * address does not produce a second `ADMIN` account.
 */
@Component
class SeedAdmin(
    private val dsl: DSLContext,
    properties: AdminProperties,
) {

    val email: String = properties.email.ifBlank { DEFAULT_EMAIL }

    fun id(): Long =
        dsl.select(ACCOUNTS.ID)
            .from(ACCOUNTS)
            .where(ACCOUNTS.EMAIL.equalIgnoreCase(email))
            .fetchOne(ACCOUNTS.ID)
            ?: error("시드 관리자 계정이 없습니다: $email")

    companion object {
        const val DEFAULT_EMAIL = "admin@lookation.local"
    }
}
