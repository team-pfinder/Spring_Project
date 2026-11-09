package com.lookation.global.security

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

/**
 * HTML(Thymeleaf SSR) 체인의 쿠키 설정.
 *
 * 로컬은 HTTP라 `secure-cookie=false`, 배포는 HTTPS라 `LOOKATION_SECURE_COOKIE=true`를 준다.
 */
@ConfigurationProperties("lookation.html")
data class HtmlProperties(
    val secureCookie: Boolean = false,
    val reuseGrace: Duration = Duration.ofSeconds(10),
)
