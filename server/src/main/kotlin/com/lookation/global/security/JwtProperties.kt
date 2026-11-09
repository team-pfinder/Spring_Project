package com.lookation.global.security

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import java.time.Duration

@Validated
@ConfigurationProperties("lookation.jwt")
data class JwtProperties(
    @field:NotBlank @field:Size(min = 32)
    val secret: String,

    val issuer: String = "lookation",

    val accessTtl: Duration = Duration.ofMinutes(15),

    val refreshTtl: Duration = Duration.ofDays(14),
)
