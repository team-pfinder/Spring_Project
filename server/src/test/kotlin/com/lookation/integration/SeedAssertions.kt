package com.lookation.integration

import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext

/** Every wallet's stored balance must equal the sum of its ledger entries. */
internal fun assertWalletLedgerConsistent(dsl: DSLContext) {
    val wallets = dsl.resultQuery(
        """
        select w.account_id, w.balance, coalesce(sum(e.amount), 0) as ledger
        from wallets w left join wallet_entries e on e.account_id = w.account_id
        group by w.account_id, w.balance
        """.trimIndent(),
    ).fetch()
    assertThat(wallets).isNotEmpty
    wallets.forEach { row ->
        assertThat(row.get("balance", Long::class.java))
            .describedAs("account ${row.get("account_id", Long::class.java)}")
            .isEqualTo(row.get("ledger", Long::class.java))
    }
}
