package com.lookation.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * OpenAPI 문서 설정.
 *
 * JWT bearer 인증을 보안 스킴으로 등록하고, 엔드포인트별로 자물쇠를 켠다. 공개 엔드포인트 목록은
 * [SecurityConfig]의 `permitAll` 매처와 같아야 하며, 여기서 어긋나면 문서가 실제 권한과 달라진다.
 * `@Operation(security = [])`는 springdoc이 빈 배열을 전역 설정으로 되돌려 무시하므로 커스터마이저로 지정한다.
 */
@Configuration
class OpenApiConfig {

    @Bean
    fun lookationOpenApi(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("Lookation API")
                .version("v1")
                .description(
                    """
                    공간 대여·예약 서비스 Lookation의 백엔드 API입니다.

                    ## 사용 순서
                    1. `POST /api/v1/auth/email-verifications`로 인증 코드를 요청하고 메일함(로컬은 Mailpit `http://localhost:18025`)에서 6자리 코드를 확인합니다.
                    2. `POST /api/v1/auth/signup`으로 가입하거나 `POST /api/v1/auth/login`으로 로그인해 `accessToken`을 받습니다.
                    3. 오른쪽 위 **Authorize** 버튼을 누르고 `accessToken`만 입력합니다. `Bearer ` 접두사는 붙이지 않습니다.
                    4. 토큰 수명은 15분입니다. 만료되면 `POST /api/v1/auth/refresh`로 재발급합니다.

                    ## 권한
                    - 공개: 공간 조회, 공지사항·도움말, 공간별 리뷰·문의 목록
                    - 로그인: 내 정보, 예약, 리뷰·문의 작성, 지갑 충전, 신고
                    - `HOST`: 공간 등록·수정, 호스트 예약 관리, 출금 신청
                    - `ADMIN`: 검수·입출금·신고·제재 심사, 공지·도움말 관리

                    ## 오류 응답
                    실패는 RFC 9457 `ProblemDetail` 형식입니다(`type`, `title`, `status`, `detail`, `instance`).
                    검증 실패는 400, 토큰 없음·만료는 401, 권한 부족은 403, 없는 자원은 404, 상태 충돌은 409입니다.
                    """.trimIndent(),
                ),
        )
        .components(
            Components().addSecuritySchemes(
                BEARER,
                SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("`POST /api/v1/auth/login` 응답의 `accessToken` 값을 그대로 붙여넣습니다."),
            ),
        )

    /** 인증이 필요한 오퍼레이션에만 `bearerAuth` 요구 사항을 붙인다. */
    @Bean
    fun perEndpointSecurity(): OpenApiCustomizer = OpenApiCustomizer { openApi ->
        openApi.paths.orEmpty().forEach { (path, item) ->
            item.readOperationsMap().forEach { (method, operation) ->
                operation.security =
                    if (isPublic(method, path)) emptyList()
                    else listOf(SecurityRequirement().addList(BEARER))
            }
        }
    }

    /** [SecurityConfig]의 `permitAll` 매처와 동일한 공개 엔드포인트. 경로 변수 이름이 달라도 매칭되도록 `{}`로 정규화한다. */
    private fun isPublic(method: PathItem.HttpMethod, path: String): Boolean {
        if (path.startsWith("/api/v1/auth/")) return true
        return method == PathItem.HttpMethod.GET && normalize(path) in PUBLIC_GET_PATHS
    }

    private fun normalize(path: String): String = PATH_VARIABLE.replace(path, "{}")

    companion object {
        const val BEARER = "bearerAuth"

        private val PATH_VARIABLE = Regex("\\{[^/}]+}")

        private val PUBLIC_GET_PATHS = setOf(
            "/api/v1/spaces",
            "/api/v1/spaces/{}",
            "/api/v1/spaces/{}/images",
            "/api/v1/spaces/{}/reviews",
            "/api/v1/spaces/{}/qnas",
            "/api/v1/qnas/{}",
            "/api/v1/notices",
            "/api/v1/notices/{}",
            "/api/v1/helps",
            "/api/v1/helps/{}",
        )
    }
}
