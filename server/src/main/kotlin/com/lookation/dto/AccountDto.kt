package com.lookation.dto

import com.lookation.domain.AccountRole
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "내 계정 정보")
data class AccountDto(
    @field:Schema(description = "계정 식별자입니다.", example = "1")
    val id: Long,

    @field:Schema(description = "가입 이메일입니다.", example = "host@lookation.local")
    val email: String,

    @field:Schema(description = "실명입니다.", example = "김하늘")
    val name: String,

    @field:Schema(description = "닉네임입니다.", example = "공간지기")
    val nickname: String,

    @field:ArraySchema(
        schema = Schema(example = "MEMBER"),
        arraySchema = Schema(description = "계정 역할 목록. `MEMBER`, `HOST`, `ADMIN` 중 하나 이상입니다."),
    )
    val roles: List<AccountRole>,
)
