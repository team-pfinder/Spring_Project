package com.lookation.global.security

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class JwtConfig {

    @Bean
    fun jwtEncoder(properties: JwtProperties): JwtEncoder =
        NimbusJwtEncoder.withSecretKey(secretKey(properties)).build()

    @Bean
    fun jwtDecoder(properties: JwtProperties): JwtDecoder {
        val decoder = NimbusJwtDecoder.withSecretKey(secretKey(properties)).build()
        decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(properties.issuer))
        return decoder
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()

    private fun secretKey(properties: JwtProperties): SecretKey =
        SecretKeySpec(properties.secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
}
