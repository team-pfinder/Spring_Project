package com.lookation.dto

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "커서 페이징 응답")
data class PagedDto<T>(
    @field:ArraySchema(arraySchema = Schema(description = "항목 목록입니다."))
    val items: List<T>,
    @field:Schema(description = "다음 페이지 커서. 마지막 페이지면 null입니다.", example = "102")
    val nextCursor: Long?,
)
