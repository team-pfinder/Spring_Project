package com.lookation.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

@Schema(description = "공지사항 한 건")
data class NoticeDto(
    @field:Schema(description = "공지사항 식별자입니다.", example = "1")
    val id: Long,

    @field:Schema(description = "공지 제목입니다.", example = "추석 연휴 고객센터 운영 안내")
    val title: String,

    @field:Schema(description = "공지 본문입니다.", example = "추석 연휴 기간에는 전화 상담이 중단됩니다.")
    val content: String,

    @field:Schema(description = "중요 공지 여부입니다.", example = "false")
    val important: Boolean,

    @field:Schema(description = "등록 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val createdAt: OffsetDateTime,

    @field:Schema(description = "마지막 수정 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val updatedAt: OffsetDateTime,
)


@Schema(description = "도움말 한 건")
data class HelpDto(
    @field:Schema(description = "도움말 식별자입니다.", example = "1")
    val id: Long,

    @field:Schema(description = "도움말 분류명입니다.", example = "예약")
    val category: String,

    @field:Schema(description = "도움말 제목입니다.", example = "예약 취소는 언제까지 가능한가요")
    val title: String,

    @field:Schema(description = "도움말 본문입니다.", example = "이용 시작 24시간 전까지 무료로 취소할 수 있습니다.")
    val content: String,

    @field:Schema(description = "등록 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val createdAt: OffsetDateTime,

    @field:Schema(description = "마지막 수정 일시입니다.", example = "2026-11-20T18:00:00+09:00")
    val updatedAt: OffsetDateTime,
)
