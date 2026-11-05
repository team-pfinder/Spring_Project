package com.lookation.global.security

import com.lookation.service.TokenService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.savedrequest.NullRequestCache

/**
 * 체인 두 개로 나눈다.
 *
 * - API 체인(`/api` 이하): 기존 계약 그대로. `Authorization: Bearer` 헤더만 읽고 CSRF를 끈다.
 * - HTML 체인(나머지): 쿠키 JWT + CSRF. 뷰가 서비스를 직접 호출하는 SSR 화면용이다.
 *
 * 두 체인 모두 `STATELESS`다. HTML 인증은 세션이 아니라 쿠키로 유지한다.
 */
@Configuration
@EnableConfigurationProperties(HtmlProperties::class)
class SecurityConfig(
    private val authCookies: AuthCookies,
    private val cookieBearerTokenResolver: CookieBearerTokenResolver,
    private val tokenService: TokenService,
    private val jwtDecoder: JwtDecoder,
    private val htmlProperties: HtmlProperties,
) {

    @Bean
    @Order(1)
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher(
                "/api/**",
                "/actuator/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
            )
            csrf { disable() }
            httpBasic { disable() }
            formLogin { disable() }
            logout { disable() }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            requestCache { requestCache = NullRequestCache() }
            authorizeHttpRequests {
                authorize(HttpMethod.GET, "/actuator/health", permitAll)
                authorize("/v3/api-docs/**", permitAll)
                // springdoc은 /swagger-ui.html을 /swagger-ui/index.html로 리다이렉트한다.
                authorize("/swagger-ui.html", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize(HttpMethod.GET, "/api/v1/spaces", permitAll)
                authorize(HttpMethod.GET, "/api/v1/spaces/*", permitAll)
                authorize(HttpMethod.GET, "/api/v1/spaces/*/images", permitAll)
                authorize(HttpMethod.GET, "/api/v1/spaces/*/reviews", permitAll)
                authorize(HttpMethod.GET, "/api/v1/spaces/*/qnas", permitAll)
                authorize(HttpMethod.GET, "/api/v1/qnas/*", permitAll)
                authorize(HttpMethod.GET, "/api/v1/notices", permitAll)
                authorize(HttpMethod.GET, "/api/v1/notices/*", permitAll)
                authorize(HttpMethod.GET, "/api/v1/helps", permitAll)
                authorize(HttpMethod.GET, "/api/v1/helps/*", permitAll)
                authorize("/api/v1/auth/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                bearerTokenResolver = DefaultBearerTokenResolver()
                jwt { jwtAuthenticationConverter = claimRolesConverter() }
            }
        }
        return http.build()
    }

    @Bean
    @Order(2)
    fun htmlSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/**")
            csrf { csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse() }
            httpBasic { disable() }
            formLogin { disable() }
            logout { disable() }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            requestCache { requestCache = NullRequestCache() }
            authorizeHttpRequests {
                authorize(HttpMethod.GET, "/login", permitAll)
                authorize(HttpMethod.POST, "/login", permitAll)
                authorize(HttpMethod.GET, "/signup", permitAll)
                authorize(HttpMethod.POST, "/signup", permitAll)
                authorize(HttpMethod.POST, "/signup/email-verification", permitAll)
                authorize(HttpMethod.GET, "/password-reset", permitAll)
                authorize(HttpMethod.POST, "/password-reset", permitAll)
                authorize(HttpMethod.POST, "/password-reset/confirm", permitAll)
                authorize("/css/**", permitAll)
                authorize("/js/**", permitAll)
                authorize("/images/**", permitAll)
                authorize("/fonts/**", permitAll)
                authorize("/uploads/**", permitAll)
                authorize("/favicon.ico", permitAll)
                authorize("/error", permitAll)
                authorize(anyRequest, authenticated)
            }
            exceptionHandling { authenticationEntryPoint = LoginRedirectEntryPoint() }
            oauth2ResourceServer {
                bearerTokenResolver = cookieBearerTokenResolver
                jwt { jwtAuthenticationConverter = claimRolesConverter() }
            }
            addFilterBefore<BearerTokenAuthenticationFilter>(
                AccessTokenRefreshFilter(tokenService, jwtDecoder, authCookies, htmlProperties),
            )
        }
        return http.build()
    }

    private fun claimRolesConverter(): JwtAuthenticationConverter {
        val rolesConverter = JwtGrantedAuthoritiesConverter()
        rolesConverter.setAuthoritiesClaimName("roles")
        rolesConverter.setAuthorityPrefix("ROLE_")
        return JwtAuthenticationConverter().apply {
            setJwtGrantedAuthoritiesConverter(rolesConverter)
        }
    }
}
