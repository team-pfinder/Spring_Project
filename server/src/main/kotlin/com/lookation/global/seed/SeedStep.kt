package com.lookation.global.seed

/**
 * One repeatable block of local seed data.
 *
 * Implementations MUST be insert-if-absent: an existing row (matched on its fixed natural key)
 * is skipped, never updated or deleted, so a second `bootRun` leaves the data untouched.
 *
 * Steps run in `@Order` order; annotate a step explicitly when it depends on another one's rows.
 */
interface SeedStep {

    /** Human-readable label used in the summary log. */
    val name: String

    fun seed(): SeedResult
}

data class SeedResult(val inserted: Int, val skipped: Int) {
    operator fun plus(other: SeedResult): SeedResult =
        SeedResult(inserted + other.inserted, skipped + other.skipped)
}
