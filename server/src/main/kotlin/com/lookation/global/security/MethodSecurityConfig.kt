package com.lookation.global.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties(AdminProperties::class)
class MethodSecurityConfig

@ConfigurationProperties("lookation.admin")
data class AdminProperties(
    val email: String = "",
    val password: String = "",
)
