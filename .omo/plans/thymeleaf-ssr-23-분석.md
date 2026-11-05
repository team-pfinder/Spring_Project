# Thymeleaf SSR 이용자 5화면 — 분석·구현 계획 (#23)

분석 기준: `main` @ `1b9ecd9`, 2026-11-05. `cd server && ./gradlew compileKotlin` 통과 확인.
이 문서는 분석·계획만 담는다. 구현 착수는 승인 후.

## 결론

SSR 코드는 **0줄**이다. `server/`에 Thymeleaf 의존성, `templates/`, `static/`, `@Controller`, 쿠키·CSRF 처리, MockMvc가 전부 없다. 에픽의 4개 축(인증 체인 / 레이아웃·자산 / API 보강 / 5화면)이 모두 신규 작업이다.

- 기반 3건(#29·#30·#31)은 서로 독립 → 병렬 가능.
- 화면 4건은 기반에 의존. #35(예약)가 최대 난이도.
- 규모(에이전트 기준): 기반 2.5일 + 화면 5.5일 ≈ **8일**. 사람 수동 브라우저 검증 시간 별도.

## 확인된 사실

|항목|사실|근거|
|---|---|---|
|보안 체인|1개, CSRF off, STATELESS, formLogin/logout off, 헤더 JWT만|`global/security/SecurityConfig.kt:20-24,46-48`|
|공개 API|`/spaces` 계열 GET, 인증 API, `/uploads/**`만 permitAll|`SecurityConfig.kt:26-43`|
|가격 규칙 API|`GET /api/v1/spaces/{id}/prices`는 permitAll 아님. 메서드 문서·서비스 계약(“비PUBLISHED여도 소유자 아니면 404”)과 불일치|`SecurityConfig.kt:33-36` vs `SpaceController.kt:194-204`, `PriceRuleService.kt:29-31`|
|토큰|access 15분 HS256, `sub`=accountId, `roles` 클레임. refresh는 32바이트 난수, SHA-256 저장, family 단위|`service/TokenService.kt:35-58,84-105`|
|재사용 탐지|폐기된 refresh 재사용 시 family 전체 폐기. 동시 갱신 경쟁에서 진 쪽도 family 폐기|`TokenService.kt:41-44,53-56`|
|쿠키|코드베이스에 쿠키 쓰기·읽기 0건|`grep ResponseCookie|Cookie|addCookie` → 0|
|예외 처리|전역 `@RestControllerAdvice`가 모든 컨트롤러 예외를 ProblemDetail JSON으로 반환|`global/error/ApiExceptionHandler.kt:12`|
|예약 가격|시간제는 `(요일, 시작시각, 종료시각)` 정확 일치 규칙 가격(시간 곱 없음). 종일제는 00:00→다음날 00:00만 허용|`service/ReservationService.kt:55-79`, `repository/SpaceRepository.kt:298-316`|
|점유 판정|DB exclusion constraint: `PENDING_APPROVAL, PENDING_PAYMENT, CONFIRMED, COMPLETED`만 점유. `[start, end)`|`db/migration/V3__constraints.sql:79-81`|
|예약 목록|`GET /api/v1/reservations`는 커서·상태 필터·총같은 제한 없음|`ReservationController.kt:43-45`, `ReservationService.kt:281`|
|회원 예약 DTO|`spaceName`, `guestCount`, `paymentDueAt`, `canCancel`, `requestMessage`, `createdAt` 없음|`dto/ReservationDtos.kt:36-54`|
|환불액 복원|취소 응답에만 `refundedAmount` 존재. 원장에 `ref_type='RESERVATION'`, `ref_id`로 남으므로 재조회 가능|`ReservationService.kt:213-236`, `V2__baseline.sql` wallet_entries|
|목록 DTO|`SpaceSummaryDto`에 썸네일·평점·리뷰수·시도/시군구 없음. 정렬은 `id DESC` 고정|`dto/SpaceDtos.kt:66-85`, `SpaceRepository.kt:175,246`|
|공개 공간|시드 6개 중 `PUBLISHED`는 “시드 파티룸” 1개|`global/seed/SpaceSeed.kt:146` vs `:164-236`|
|기존 테스트|MockMvc 0건. 전부 `RANDOM_PORT` + `RestClient`, 시드는 테스트에서 수동 실행|`test/kotlin/com/lookation/integration/*`|
|시드 계정|`seed-member-1@lookation.local` / `lookation1234!` (MEMBER), `seed-host-1@lookation.local` 동일 비번 (MEMBER+HOST)|`global/seed/AccountSeed.kt:60-67`|
|레거시 마크업|5화면 JSP 전부 UTF-8·LF, `WEB-INF/views/` 아래 145~962줄|직접 확인|
|정적 자산|`css` 1.7MB + `js` 704KB + `images` 2.0MB = 4.4MB (에픽 수치와 일치). `fonts` 2.9MB, `vendor` 22MB는 별도|`du -sh Lookation/WebContent/*`|
|커밋된 비밀|카카오맵 JS 키 3곳 하드코딩|`search.jsp:453`, `locationDetail.jsp:286`, `locationDetailHost.jsp:252`|

## 차단 요인 (우선순위)

1. **전역 REST 예외 어드바이스**: HTML 컨트롤러를 `com.lookation.controller`에 두면 400·404·409가 JSON으로 나간다. `@ControllerAdvice` 선택자는 “`@Controller`이면서 `@RestController` 아님”을 표현할 수 없어 패키지 분리가 필요하다.
2. **예약 입력 모델**: 자유 `datetime-local` 입력은 가격 규칙과 맞지 않아 대부분 400이다. 슬롯(가격 규칙) 선택 UI가 필요하고, 이는 #35의 폼 계약을 바꾼다.
3. **마이페이지 데이터 부족**: DTO 6개 필드 누락 + 무제한 목록. 조회 경로를 넓히지 않으면 화면이 `spaceId`만 표시한다.
4. **시드 커버리지**: PUBLISHED 1개로는 검색 필터·정렬·커서·빈 결과를 검증할 수 없다. 결제 화면용 `PENDING_PAYMENT`(미래 `payment_due_at`)도 없다.
5. **refresh 경쟁**: 현재 구현은 탭 2개 동시 갱신에서 family를 폐기해 #29 완료 조건을 만족할 수 없다.

## 결정 필요

### D1. 뷰 계층 위치 — 권장: `com.lookation.view` 신설

- HTML 컨트롤러·뷰 폼·HTML 예외 어드바이스를 `com.lookation.view`에 둔다.
- `ApiExceptionHandler`를 `@RestControllerAdvice(basePackages = ["com.lookation.controller"])`로 좁힌다. 기존 API 동작 변화 없음(모든 REST 컨트롤러가 해당 패키지).
- ADR 0009의 레이어 목록에 `view` 추가를 ADR 0010에서 명시.
- 대안: 같은 패키지 + HTML 컨트롤러마다 로컬 `@ExceptionHandler` 중복. 기각.

### D2. refresh 재사용 유예 — 권장: 10초 유예 재발급 (HTML 체인 한정)

- `TokenService.rotate(raw, grace)`로 유예를 호출자가 정한다. HTML 필터만 `lookation.html.reuse-grace`(기본 10초)를 넘기고, API의 `/api/v1/auth/refresh`는 `grace=0`이라 기존 재사용 탐지 계약이 그대로다.
- 폐기된 토큰이 유예 안이고 family에 활성 토큰이 남아 있으면 family를 죽이지 않고 같은 family로 새 쌍을 발급.
- 스키마 변경 없음. 대가: 유예 중 family에 활성 refresh 토큰이 2개 생길 수 있고, 탈취 토큰 재전송도 10초간 통과.
- 대안: 새 refresh 토큰을 암호화해 유예 동안 저장 → 엄격 단일 활성. 스키마+암호화 비용. 기각(현 단계).
- 로그아웃은 family 단위 폐기라 유예로 되살아나지 않는다.

### D3. 예약 입력 — 권장: 날짜 + 가격규칙 슬롯 선택

- 상세/예약 폼은 `date`와 `priceRuleId`를 받고, 서버가 KST 기준 `startAt`/`endAt`을 계산해 `ReservationService.create`에 넘긴다.
- 슬롯 목록 = 해당 날짜 요일의 가격 규칙 − 점유 구간(4개 상태).
- 대안: 자유 시작·종료 입력. 가격 규칙 정확 일치 요구와 충돌. 기각.
- 폼 hidden 값은 서버가 렌더링하며, 변조돼도 서비스가 재검증해 400이므로 안전.

### D4. 시드 확장 — 권장: PUBLISHED 3~4개 + 결제 대기 1건 추가

- 지역·유형·가격이 다른 PUBLISHED 공간을 추가해 필터·정렬·커서를 검증.
- `seed-member-1` 소유의 `PENDING_PAYMENT` 예약 1건을 `payment_due_at = now+30m`으로 추가(기존 시드는 과거).
- 기존 계약 테스트 수치(`SeedDatasetTest`, `SeedContractTest`)를 갱신해야 한다.

### D5. #32·#36 순환 의존 — 권장: `/mypage` 셸을 #32가 소유

- #32: `/mypage` GET → `/mypage/reservations`로 302하는 셸만 추가. 로그인/비로그인 리다이렉트 검증에 사용.
- #36: `/mypage/reservations` 목록·상세·취소를 채운다.
- 이렇게 하면 #32의 완료 조건 “로그인 후 `/mypage/reservations` 접근 가능”이 #36 없이 성립한다.

## 라우트 표 (#30 산출물)

|메서드|경로|인증|템플릿|
|---|---|---|---|
|GET|`/`|공개|302 → `/spaces`|
|GET/POST|`/login`|공개|`auth/login.html`|
|POST|`/logout`|필수|302 → `/spaces`|
|GET/POST|`/signup`|공개|`auth/signup.html`|
|POST|`/signup/email-verification`|공개|같은 화면 재렌더|
|GET/POST|`/password-reset`|공개|`auth/password-reset.html`|
|POST|`/password-reset/confirm`|공개|같은 화면|
|GET|`/spaces`|공개|`space/list.html`|
|GET|`/spaces/{id}`|공개|`space/detail.html`|
|GET|`/spaces/{id}/reservations/new`|필수|`reservation/form.html`|
|POST|`/spaces/{id}/reservations`|필수|302 → 결제 또는 마이페이지|
|GET/POST|`/reservations/{id}/pay`|필수|`reservation/pay.html`|
|GET|`/mypage`|필수|302 → `/mypage/reservations`|
|GET|`/mypage/reservations`|필수|`mypage/reservations.html`|
|GET|`/mypage/reservations/{id}`|필수|`mypage/reservation.html`|
|POST|`/mypage/reservations/{id}/cancel`|필수|302 → 상세|

정적: `/css/**`, `/js/**`, `/images/**`, `/fonts/**`, `/uploads/**`, `/favicon.ico` permitAll.
에러: `templates/error.html`(Boot 기본 에러 뷰) + 상태별 문구.

## 인증 체인 설계 (#29)

두 개의 `SecurityFilterChain`:

1. **API 체인** `@Order(1)`, `securityMatcher("/api/**", "/actuator/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")`
   - CSRF off, STATELESS, `HeaderBearerTokenResolver(HttpHeaders.AUTHORIZATION)` 명시.
   - 기존 authorize 규칙 그대로. 응답 계약 불변.
2. **HTML 체인** `@Order(2)`, `securityMatcher("/**")`
   - `CookieCsrfTokenRepository` + 기본 XOR 핸들러. CSRF on.
   - `oauth2ResourceServer { bearerTokenResolver = CookieBearerTokenResolver() }` — 쿠키 `access_token`만 읽는다.
   - permitAll: 로그인·가입·비밀번호 재설정·정적·`/error`.
   - `anyRequest authenticated`, 커스텀 `AuthenticationEntryPoint` → `/login?next=<상대경로>`.
   - STATELESS 유지. `RequestCache`는 쓰지 않고 진입점이 직접 `next`를 만든다.

쿠키: `access_token`(HttpOnly, SameSite=Lax, Path=/), `refresh_token`(HttpOnly, SameSite=Lax, Path=/, 14일). `Secure`는 프로퍼티 `lookation.html.secure-cookie`로 제어(로컬 false).

투명 갱신: HTML 체인에 `OncePerRequestFilter`를 추가해 access 쿠키가 없거나 `exp - now < 30s`일 때 refresh 쿠키로 `TokenService.rotate` 호출 → 새 쿠키 2개 세팅 → 요청 속성에 새 access를 넣고 쿠키 리졸버가 그 속성을 우선 읽는다. 정적 경로는 건너뛴다. rotate 실패 시 쿠키 삭제 후 `/login?expired=1`.

로그아웃: refresh 쿠키로 `TokenService.revoke`, 두 쿠키 `Max-Age=0`.

## 티켓 그래프

```
Wave 0 (병렬): #29 인증 체인 · #30 레이아웃/자산 · #31 API 보강
Wave 1:        #32 로그인/가입 (←#29,#30) · #33 검색 (←#30,#31) · #34 상세 (←#30,#31)
Wave 2:        #35 예약·결제 (←#34)
Wave 3:        #36 마이페이지 (←#35, #32 셸)
```

#31은 계약 변경(추가 필드·정렬·availability)이므로 #33·#34 착수 전 머지가 안전하다.

## 티켓별 스케치

### #29 인증 체인 + ADR 0010 (0.5~1일)

1. `spring-boot-starter-thymeleaf` 추가(Boot 4.1.2 → `thymeleaf-spring6` 3.1.5).
2. `SecurityConfig`를 두 체인으로 분리. API 체인은 현 규칙을 그대로 옮긴다.
3. `CookieBearerTokenResolver`, `LoginEntryPoint`, `AccessRefreshFilter`, `CookieWriter`/`CookieReader`를 `global/security`에 추가.
4. `TokenService.rotate(raw, grace)` 유예 로직 + 테스트(동시 rotate 2회 → 둘 다 성공, family 유지). API는 `grace=0` 유지.
5. ADR 0010: 0008 개정 범위(SSR 뷰 허용, 세션 제거·헤더 API 유지, 쿠키 JWT는 stateless). `AGENTS.md` 스택 표에 Thymeleaf 행 추가.
6. 검증: MockMvc 403(CSRF 없음), 기존 통합 테스트 10개 통과.

### #30 레이아웃·정적 자산 (0.5일)

1. `templates/layout.html` + `templates/fragments/{header,footer}.html`. Thymeleaf 3.1 fragment 파라미터(`~{::main}`) 방식. 레이아웃 다이얼렉트 의존성 없음.
2. `static/{css,js,images,fonts}`에 5화면에 필요한 것만 복사. `style.css`가 `../fonts`, `../images`를 상대 참조하므로 디렉터리 구조를 유지.
3. Bootstrap 4 CSS에 맞춰 **BS4 bundle JS**를 쓴다(레거시는 BS3 JS 혼용).
4. jQuery 1개 버전만. 페이지별 인라인 스크립트는 이식하지 않고 새로 쓴다.
5. 카카오맵 키는 이관하지 않는다. 지도는 플레이스홀더.
6. 검증: MockMvc 레이아웃 렌더 + `/css/style.css` 200.

### #31 API 보강 (0.75~1일)

1. `SpaceSummaryDto`에 `thumbnailUrl`, `rating`, `reviewCount`, `sido`, `sigungu` 추가. `SpaceService`/`SpaceRepository`에서 첫 이미지(정렬 `sort_order,id`)·리뷰 집계 서브쿼리.
2. `sort` 파라미터(`latest`, `price_asc`, `price_desc`, `rating`) — repository orderBy 분기. `created_at`을 `SpaceRow`에 추가.
3. `GET /api/v1/spaces/{id}/availability?from&to` — 점유 구간만(예약자 정보 제외), 4개 상태, `[start,end)`.
4. `/api/v1/spaces/*/prices`를 permitAll에 추가(문서 계약과 일치).
5. 인덱스: `spaces(status, id desc) where deleted_at is null`는 기본 목록 읽기 경로가 생기므로 함께 검토. 남용 금지 — 쿼리 계획 확인 후.
6. 검증: `SpaceFlowTest` 확장.

### #32 로그인·가입·비밀번호 재설정 (1일)

1. `view/AuthViewController` + `view/form`의 `LoginViewForm`, `SignupViewForm`, `PasswordResetViewForm`.
2. 로그인 POST: `AuthService.login` → 쿠키 발급 → `next`(상대 경로만)로 302. 실패는 같은 화면 재렌더 + 메시지.
3. 가입: 2단계(코드 발송 POST → 코드·비번 입력 POST). JS 없이 동작.
4. 비밀번호 재설정: 요청/확인 2단계.
5. `view/HtmlExceptionHandler` + `error.html`.
6. 검증: MockMvc 3건(로그인 성공 쿠키, 실패 메시지, 비로그인 리다이렉트).

### #33 공간 검색 (1일)

1. `view/SpaceViewController.list` — `SpaceService.search` 직접 호출.
2. 필터: `sido`(고정 17개 목록), `sigungu`(텍스트), `type`, `guests`, `minPrice`/`maxPrice`, `keyword`, `sort`, `cursor`.
3. 카드: 썸네일·이름·지역·유형·가격·평점. 결과 없음 상태.
4. 다음 페이지는 `<a href>` 링크(JS 없이 동작).
5. 검증: 필터 조합 3개, 커서 2페이지.

### #34 공간 상세 (1~1.5일)

1. `view/SpaceViewController.detail` — `SpaceService.get`, `ReviewService.list`, `QnaService.list`, `PriceRuleService.list`, `AvailabilityService`.
2. 캐러셀은 `sort_order` 순서 그대로. 비공개·삭제 404.
3. 리뷰·Q&A는 첫 페이지만 SSR, 더보기는 링크.
4. 검증: 이미지 순서, 점유 구간 표시, 404.

### #35 예약·결제 (2일, 최대 난이도)

1. 예약 폼: 날짜 선택 → 슬롯 목록(가격 규칙 − 점유) → 인원·요청 메시지.
2. POST: 서버가 슬롯에서 `startAt`/`endAt` 계산 → `ReservationService.create`.
3. 승인형이면 생성 후 `/mypage/reservations/{id}`로, 즉시형이면 결제가 이미 끝났으므로 같은 곳으로.
4. 결제 화면: 금액·잔액·마감 시각. `GET /api/v1/wallet`(또는 서비스 직접 호출)로 잔액 노출.
5. 오류: 잔액 부족·시간 중복(409)·마감 초과를 폼에 매핑.
6. 검증: 승인형은 호스트 승인이 화면에 없으므로 API로 승인 후 결제. 이 한계를 이슈에 명시.

### #36 마이페이지 예약 (1일)

1. 회원 목록에 `spaceName`, `guestCount`, `paymentDueAt`, `canCancel`, `requestMessage` 추가. `findByMember`에서 `SPACES` 조인 + `canCancel` 계산.
2. 커서·상태 필터 추가(응답 형태는 유지하거나 `{items,nextCursor}`로 확장 — API 계약 변경 명시).
3. 환불액은 `wallet_entries`에서 조회해 채운다.
4. 목록·상세·취소. 취소는 POST + CSRF.
5. 검증: 타인 예약 403, 취소 후 상태·환불 표시.

## 함정

- **springdoc**: `@Controller`도 문서화 대상이다(`AbstractOpenApiResource`는 `@Hidden`만 제외). HTML 컨트롤러에 `@Hidden`을 붙이지 않으면 `/v3/api-docs`가 오염된다.
- **CSRF 자동 주입**: `th:action`이 `_csrf` hidden을 넣는 것은 `RequestDataValueProcessor` 덕분이다. 첫 MockMvc 테스트에서 `name="_csrf"`를 단언하고, 실패하면 `@ModelAttribute("_csrf")`로 보강.
- **시간대**: `OffsetDateTime.now()`는 JVM 기본 존, 가격 규칙은 KST(`ReservationService.kt:35,55`). HTML 경계에서 KST로 고정 변환하고 `+09:00` 문자열을 hidden에 넣는다.
- **Kotlin 경고 = 에러**: `allWarningsAsErrors = true`. 사용하지 않는 파라미터·deprecation도 빌드를 깨뜨린다.
- **정적 자산 경로**: `style.css`의 `../fonts/...`, `../images/...` 상대 참조를 유지하려면 `static/css`, `static/fonts`, `static/images`를 같은 깊이로 둔다.
- **환불 표시**: `REFUNDED` 상태는 코드가 쓰지 않는다. 상태가 아니라 `wallet_entries`로 환불액을 복원한다.
- **availability 술어**: DB 제약과 같은 4개 상태를 써야 한다. 다르면 화면이 가능하다고 한 슬롯이 409로 실패한다.
- **시드 계정**: `seed-member-1`, `seed-host-1` / `lookation1234!`. 통합 테스트는 시드를 수동 실행해야 한다.

## 검증 전략

- MockMvc: 현재 0건. `@SpringBootTest` + `@AutoConfigureMockMvc` + `@Testcontainers` + `@ServiceConnection` + `InitializeService(steps).seed()` 패턴을 새로 만든다(클래스마다 고유 `lookation.test.suite` 프로퍼티로 컨텍스트 캐시 분리).
- 기존 API 회귀: `AuthFlowTest`, `SpaceFlowTest`, `ReservationFlowTest`, `WalletFlowTest`, `AdminFlowTest`, `ReviewQnaFlowTest` 통과.
- 브라우저 스모크: `docker compose up -d --wait` → `cd server && SPRING_PROFILES_ACTIVE=local ./gradlew bootRun` → 5화면. 시드 계정으로 로그인.
- 에픽 완료 조건의 “수동 브라우저 스모크”는 에이전트 브라우저 도구로 실제 렌더·쿠키·CSRF를 확인한다.
