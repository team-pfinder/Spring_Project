package com.lookation.global.seed

/**
 * One repeatable block of local seed data.
 *
 * 실행 범위: [InitializeService]는 `local` 프로필 + `lookation.seed.enabled=true` 때만 등록되므로
 * 운영 배포에서는 절대 실행되지 않는다. 각 단계 클래스도 같은 패키지의 테스트 픽스처로만 쓰이며
 * (통합 테스트가 `InitializeService(steps)`를 직접 호출), 본 코드는 로컬 개발·테스트 전용이다.
 *
 * Rows are insert-if-absent: an existing row (matched on its fixed natural key) is skipped and
 * never deleted or recreated, so a second `bootRun` leaves the database untouched.
 *
 * One repair path is allowed: files owned by the seed (`seed/...`) are recopied when missing or
 * when their size no longer matches the source, and the matching `size_bytes` is re-synced. This
 * keeps the `attachments` row and the file on disk consistent after a truncated copy or a
 * deleted upload directory.
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
