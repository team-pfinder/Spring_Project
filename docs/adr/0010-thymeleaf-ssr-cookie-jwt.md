# HTML 화면은 Thymeleaf SSR, 인증은 쿠키 JWT로 한다

ADR 0008은 “REST API만 제공하고 세션 기반 뷰를 없앤다”고 정했다. 실제 이용자 여정 5화면(로그인·가입, 공간 검색, 공간 상세, 예약, 마이페이지 예약)은 서버 렌더링이 필요하다. 이 결정은 0008을 **부분 개정**한다: 세션은 계속 쓰지 않고, 헤더 JWT API 계약도 그대로 두되, 브라우저용 HTML 체인을 추가한다.

## 0008에서 바뀌는 것

- **바뀐다**: “뷰 없음” → 이용자 여정 화면은 Thymeleaf SSR로 제공한다. 레거시 JSP 120개는 실행하지 않고, CSS·JS·이미지와 마크업만 재사용한다. JS는 화면별로 다시 쓴다.
- **바뀌지 않는다**: 세션을 인증 수단으로 쓰지 않는다. `HttpSession`을 만들지 않고 두 체인 모두 `STATELESS`다. refresh 토큰은 계속 DB에 두고 회전한다.
- **바뀌지 않는다**: `/api/**`는 헤더 JWT 전용이다. 쿠키를 보내도 API는 인증하지 않는다. CSRF도 API에는 걸지 않는다. 기존 응답·오류(`ProblemDetail`) 계약은 그대로다.
- **새로 생긴다**: 같은 앱에 HTML 체인. 쿠키 JWT + CSRF, access 만료 시 투명 갱신. URL은 `/login`, `/spaces`, `/spaces/{id}`, `/spaces/{id}/reservations/new`, `/reservations/{id}/pay`, `/mypage/reservations`처럼 새 경로를 쓴다. 레거시 `.action` 리다이렉트는 만들지 않는다.

## 체인 두 개

`securityMatcher`로 나눈다. 순서는 `@Order(1)` API, `@Order(2)` HTML이다.

|항목|API 체인|HTML 체인|
|---|---|---|
|대상|`/api/**`, `/actuator/**`, `/v3/api-docs/**`, `/swagger-ui/**`|나머지 전부(`/**`)|
|인증 수단|`Authorization: Bearer` 헤더만|`access_token` 쿠키만|
|CSRF|끔|켬(`CookieCsrfTokenRepository`)|
|세션|`STATELESS`|`STATELESS`|
|401 처리|기존 `WWW-Authenticate` JSON|`/login?next=<상대경로>` 302|
|오류 본문|`ProblemDetail`|HTML 오류 화면|

HTML 컨트롤러는 `com.lookation.view`에 두고, `ApiExceptionHandler`는 `@RestControllerAdvice(basePackages = ["com.lookation.controller"])`로 API 컨트롤러에만 적용한다. 한 어드바이스가 HTML 예외까지 JSON으로 바꾸는 것을 막기 위해서다.

## 쿠키

|쿠키|값|수명|속성|
|---|---|---|---|
|`access_token`|HS256 JWT(기존 access와 동일)|15분|`HttpOnly`, `SameSite=Lax`, `Path=/`, `Secure`는 설정값|
|`refresh_token`|불투명 난수(기존 refresh와 동일)|14일|`HttpOnly`, `SameSite=Lax`, `Path=/`, `Secure`는 설정값|

- `Secure`는 `lookation.html.secure-cookie`(환경변수 `LOOKATION_SECURE_COOKIE`)로 제어한다. 로컬 HTTP는 false, 배포 HTTPS는 true다.
- JS는 두 쿠키를 읽지 않는다. CSRF 토큰만 `XSRF-TOKEN` 쿠키로 노출한다.

## 투명 갱신과 재사용 유예

- HTML 체인의 pre-auth 필터가 access 쿠키를 검사한다. 없거나 30초 내 만료면 refresh 쿠키로 `TokenService.rotate`를 호출하고 새 쿠키 두 개를 응답에 쓴다. 같은 요청의 bearer 리졸버는 요청 속성에 실린 새 access를 먼저 읽는다.
- refresh도 만료·폐기면 쿠키를 지우고 `/login?expired=1`로 보낸다. `/login`·`/signup`·`/password-reset`·정적 경로는 필터를 건너뛰어 리다이렉트 루프를 막는다.
- **재사용 유예**: 탭 두 개가 같은 refresh로 동시에 갱신하면 한쪽은 이미 폐기된 토큰을 들고 온다. `TokenService.rotate(raw, grace)`는 유예 시간(기본 10초) 안이고 family에 유효한 토큰이 남아 있으면 family를 폐기하지 않고 같은 family로 새 쌍을 발급한다. HTML 필터만 유예를 넘기고, API의 `/api/v1/auth/refresh`는 `grace=0`으로 기존처럼 즉시 재사용을 거부한다.
- 유예의 대가: 유예 중에는 활성 refresh 토큰이 family에 2개 존재할 수 있고, 탈취한 refresh를 10초 안에 재전송하면 새 쌍을 받는다. 유예를 넘기면 기존대로 family 전체를 폐기한다. 로그아웃은 family 단위로 폐기하므로 유예로 되살아나지 않는다.

## 로그아웃

`POST /logout`은 CSRF를 요구하고, refresh 쿠키의 family를 전부 폐기한 뒤 두 쿠키를 `Max-Age=0`으로 지운다. access 토큰은 `jti`가 없어 만료(최대 15분) 전까지 유효하다. 이 한계는 0008과 같다. 즉시 무효화가 필요해지면 `jti` + 거부 목록을 추가한다.

## 대안

- **세션 유지 + CSRF**: 같은 출처 SSR에는 더 단순하지만, 0008이 “프론트를 나중에 분리한다”는 이유로 세션을 기각했고 이번 결정도 그 전제를 유지한다.
- **HTML도 헤더 JWT**: 브라우저 폼은 헤더를 붙일 수 없다. JS로 붙이면 SSR의 이점이 사라진다.
- **BFF/별도 프론트**: 지금 필요한 5화면에 비해 운영 대상이 늘어난다.
- **refresh 유예 없이 탭 하나만 유지**: 두 탭을 쓰는 정상 사용자가 로그아웃된다. #29 완료 조건과 충돌한다.

## 결과

- HTML 화면은 서비스 계층을 직접 호출한다. API를 HTTP로 다시 부르지 않는다.
- 권한 검사는 기존 서비스·`@PreAuthorize`를 그대로 쓴다. HTML 컨트롤러가 인증 주체(`Jwt`)를 받아 서비스에 넘긴다.
- 뷰 계층이 하나 늘어난다: `com.lookation.view`. ADR 0009의 레이어 목록에 추가한다.
- springdoc은 `@Controller`도 문서화하므로 HTML 컨트롤러에는 `@Hidden`을 붙인다.
