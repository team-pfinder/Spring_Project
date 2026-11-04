package com.lookation.host.infrastructure

import com.lookation.jooq.tables.HostProfiles.HOST_PROFILES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

data class HostProfileRow(
    val accountId: Long,
    val businessName: String,
    val businessNumber: String,
    val representativeName: String,
    val bankName: String?,
    val bankAccountNumberEnc: ByteArray?,
    val bankAccountHolderEnc: ByteArray?,
    val status: String,
)

@Repository
class HostProfileRepository(private val dsl: DSLContext) {

    fun exists(accountId: Long): Boolean =
        dsl.fetchCount(HOST_PROFILES, HOST_PROFILES.ACCOUNT_ID.eq(accountId)) > 0

    fun insert(
        accountId: Long,
        businessName: String,
        businessNumber: String,
        representativeName: String,
        bankName: String?,
        bankAccountNumberEnc: ByteArray?,
        bankAccountHolderEnc: ByteArray?,
    ) {
        dsl.insertInto(HOST_PROFILES)
            .set(HOST_PROFILES.ACCOUNT_ID, accountId)
            .set(HOST_PROFILES.BUSINESS_NAME, businessName)
            .set(HOST_PROFILES.BUSINESS_NUMBER, businessNumber)
            .set(HOST_PROFILES.REPRESENTATIVE_NAME, representativeName)
            .set(HOST_PROFILES.BANK_NAME, bankName)
            .set(HOST_PROFILES.BANK_ACCOUNT_NUMBER_ENC, bankAccountNumberEnc)
            .set(HOST_PROFILES.BANK_ACCOUNT_HOLDER_ENC, bankAccountHolderEnc)
            .execute()
    }

    fun findByAccountId(accountId: Long): HostProfileRow? =
        dsl.selectFrom(HOST_PROFILES)
            .where(HOST_PROFILES.ACCOUNT_ID.eq(accountId))
            .fetchOne()
            ?.let {
                HostProfileRow(
                    accountId = it.get(HOST_PROFILES.ACCOUNT_ID),
                    businessName = it.get(HOST_PROFILES.BUSINESS_NAME),
                    businessNumber = it.get(HOST_PROFILES.BUSINESS_NUMBER),
                    representativeName = it.get(HOST_PROFILES.REPRESENTATIVE_NAME),
                    bankName = it.get(HOST_PROFILES.BANK_NAME),
                    bankAccountNumberEnc = it.get(HOST_PROFILES.BANK_ACCOUNT_NUMBER_ENC),
                    bankAccountHolderEnc = it.get(HOST_PROFILES.BANK_ACCOUNT_HOLDER_ENC),
                    status = it.get(HOST_PROFILES.STATUS),
                )
            }
}
