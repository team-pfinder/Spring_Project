# Lookation server

레거시 Lookation(Spring MVC 3 + JSP + Oracle)을 대체하는 백엔드. 설계 결정은 `../docs/adr/`에 있다.

## 요구 사항

- JDK 25 (Gradle toolchain이 25를 찾는다)
- Docker (통합 테스트용 Testcontainers PostgreSQL)

## 명령

```bash
./gradlew build          # jOOQ 코드 생성 + 컴파일 + 테스트
./gradlew generateJooq   # 스키마 변경 후 코드 재생성
./gradlew bootRun        # 로컬 실행 (local 프로필이 기본값)
```

## 로컬 실행

레포 루트에서 인프라를 띄운다(Postgres·Mailpit·MiniStack, 모두 `127.0.0.1` 바인딩).

```bash
docker compose up -d --wait
cd server && ./gradlew bootRun
```

| 서비스 | 호스트 포트 | 용도 |
|---|---|---|
| PostgreSQL 18 | `25432` | DB `lookation` (사용자·비밀번호 `lookation`) |
| Mailpit | `21025` / `28025` | SMTP / 메일 확인 UI (`http://localhost:28025`) |
| MiniStack | `24566` | Secrets Manager 에뮬레이터 (AWS API `http://localhost:24566`) |

- `local` 프로필은 `src/main/resources/application-local.yml`에 있고, `application.yml`의 `spring.profiles.default: local` 덕분에 프로필을 지정하지 않으면 자동으로 적용된다. 민감 값은 MiniStack의 시크릿 `lookation/local`에서 기동 시 주입된다(`SecretsEnvironmentPostProcessor`). `deploy/ministack/ready.d/01-lookation-secrets.sh`가 시드한다.
- 배포 환경은 `SPRING_PROFILES_ACTIVE`를 **반드시** 지정한다. 빠뜨리면 기본값 `local`이 적용되어 `localhost:24566`(MiniStack) 연결을 시도하고 기동에 실패한다.
- 기본 관리자: `admin@lookation.local` / `lookation-admin1!` (시크릿의 `adminPassword`).
- 시드 데이터: `local` 프로필에서 `lookation.seed.enabled=true`(`application-local.yml` 기본값)면 기동 시 `InitializeService`가 아래 데이터를 insert-if-absent로 넣는다. 재기동해도 중복·갱신이 없다. 고정 비밀번호는 `lookation1234!`다.
  - 계정: 이용자 `seed-member-1`~`3`(`member-3`은 탈퇴 상태), 호스트 `seed-host-1`·`seed-host-2`, 관리자 `admin@lookation.local` — 모두 `@lookation.local` 도메인이다.
  - 데이터: 호스트 프로필 2·제재 3, 공간 6(유형·상태 각 6종)·시설·시간/일 가격 규칙·검수 4상태, 예약 10(8상태 + 배치용 2)·상태 이력, 지갑·원장·충전 3상태·출금 4상태·정산, 리뷰·문의·메시지·신고 3상태·공지·도움말, 배치 이력 3상태·인증 코드 2목적.
  - 관리자는 `lookation.admin.email`(기본 `admin@lookation.local`) 기준이다. `AdminSeeder`가 먼저 만들면 그 비밀번호(`lookation-admin1!`)를 유지하고 시드는 건너뛴다.
  - 첨부: 레거시 `Lookation/WebContent/images`의 이미지를 `lookation.upload-dir` 아래 `seed/...` 결정적 키로 복사하고 `attachments` 행을 넣는다. 기존 행은 삭제·갱신하지 않는다. 단, 시드가 만든 `seed/...` 파일이 없거나 크기가 바뀌면 다시 복사하고 `size_bytes`만 맞춘다. 원본이 없으면 경고 후 건너뛴다(`lookation.seed.image-source`로 경로 변경).
- **`SPRING_PROFILES_ACTIVE=local`을 셸에 export하지 않는다.** 이 프로필은 환경변수가 설정 파일보다 우선하므로 테스트에서도 `test` 프로필을 덮어쓴다. DB는 여전히 Testcontainers를 쓰지만(`@ServiceConnection`), `SecretsEnvironmentPostProcessor`가 MiniStack을 요구해 MiniStack이 없는 CI에서 테스트가 실패한다.
- 통합 테스트는 `src/test/resources/application.properties`의 `spring.profiles.active=test`로 기본값 `local`을 끈다.
- `test` 프로필에서는 `ReservationScheduler`를 등록하지 않는다. 스케줄러가 시드 데이터를 배치로 바꾸면 테스트가 시간에 따라 흔들리기 때문이다.
- 데이터 리셋: `docker compose down -v`.

## API 문서 (Swagger UI)

앱을 띄운 뒤 `http://localhost:8080/swagger-ui/index.html`에서 전체 API를 한국어 설명·요청/응답 예시와 함께 볼 수 있다. OpenAPI JSON은 `/v3/api-docs`다.

1. `POST /api/v1/auth/login`을 실행해 `accessToken`을 복사한다(시드 관리자는 `admin@lookation.local`).
2. 오른쪽 위 **Authorize**에 `accessToken`만 붙여넣는다(`Bearer ` 접두사 없이). 토큰은 새로고침해도 유지된다.
3. 자물쇠가 닫힌 API는 토큰이 필요하고, 열린 API는 공개 엔드포인트다. 이 구분은 `global/config/OpenApiConfig.kt`의 목록과 `SecurityConfig`의 `permitAll` 매처를 맞춘 것이다.

## 환경변수

아래 값은 `local` 프로필을 쓰지 않을 때 필요하다. `local` 프로필에서는 `LOOKATION_DB_PASSWORD`·`LOOKATION_JWT_SECRET`·`LOOKATION_ENCRYPTION_KEY`·`LOOKATION_ADMIN_PASSWORD`를 MiniStack에서 받으므로 지정하지 않는다.

| 이름 | 용도 |
|---|---|
| `LOOKATION_DB_URL` | JDBC URL (예: `jdbc:postgresql://localhost:5432/lookation`) |
| `LOOKATION_DB_USERNAME` | DB 사용자 |
| `LOOKATION_DB_PASSWORD` | DB 비밀번호 |
| `LOOKATION_JWT_SECRET` | HS256 서명 키 (32바이트 이상) |
| `LOOKATION_ENCRYPTION_KEY` | 정산 계좌 AES-GCM 키 (base64, 32바이트) |
| `SPRING_MAIL_HOST` 등 | SMTP 설정. 없으면 인증 코드를 로그로 출력 |
| `LOOKATION_UPLOAD_DIR` | 업로드 저장 경로 (기본 `./uploads`) |
| `LOOKATION_ADMIN_EMAIL` / `LOOKATION_ADMIN_PASSWORD` | 초기 관리자 시드 (선택, 둘 다 있을 때만 생성) |

## 스키마

Flyway 마이그레이션은 `src/main/resources/db/migration`에 있다.

| 파일 | 내용 |
|---|---|
| `V1__extensions.sql` | `btree_gist`, `pg_trgm` 확장 |
| `V2__baseline.sql` | 테이블 구조. **jOOQ 코드 생성 입력** |
| `V3__constraints.sql` | CHECK·부분 유니크 인덱스·중복 예약 배제 제약·조회 인덱스 |

jOOQ OSS 파서가 CHECK 제약을 읽지 못하므로 구조(V2)와 제약(V3)을 분리했다. **최초 배포 전에는** 테이블 추가 시 V2에 구조를, V3에 제약을 넣는다. 배포 후에는 기존 파일을 수정하지 않고 V4 이상으로 추가해야 하며, jOOQ 코드 생성 입력을 DB 기반으로 전환해야 한다(현재 미구현).

## 주요 API

| 메서드 | 경로 | 권한 |
|---|---|---|
| POST | `/api/v1/auth/signup`, `/login`, `/refresh`, `/logout` | 공개 (signup은 이메일 인증 코드 필수) |
| POST | `/api/v1/auth/email-verifications` | 공개 |
| POST | `/api/v1/auth/password-reset/request`, `/password-reset/confirm` | 공개 |
| GET | `/api/v1/me` | 인증 |
| POST | `/api/v1/hosts/apply` | 인증 |
| GET | `/api/v1/me/host-profile` | 인증 |
| POST/PUT | `/api/v1/spaces`, `/api/v1/spaces/{id}` | HOST |
| GET | `/api/v1/spaces/{id}` | 공개(게시된 공간) |
| POST | `/api/v1/spaces/{id}/inspection` | HOST |
| POST | `/api/v1/spaces/{id}/close`, `/reopen` | HOST |
| DELETE | `/api/v1/spaces/{id}` | HOST (진행 중 예약 있으면 409, 소프트 삭제) |
| POST | `/api/v1/spaces/{id}/images` | HOST |
| GET | `/api/v1/admin/inspections` | ADMIN |
| POST | `/api/v1/admin/inspections/{spaceId}/decision` | ADMIN |
| GET/PUT | `/api/v1/spaces/{id}/prices` | 공개 조회 / HOST 수정 |
| GET | `/api/v1/hosts/reservations` | HOST (CONFIRMED 이후 예약자 연락처 노출) |
| GET | `/api/v1/spaces?type=&keyword=&sido=&sigungu=&guests=&minPrice=&maxPrice=&from=&to=&cursor=&limit=` | 공개 |
| POST | `/api/v1/reservations` | 인증 |
| POST | `/api/v1/reservations/{id}/approve`, `/reject`, `/pay` | HOST / HOST / 예약자 |
| POST | `/api/v1/reservations/{id}/cancel` | 당사자 |
| POST/GET | `/api/v1/wallet/charges` | 인증 |
| POST/GET | `/api/v1/withdrawals` | HOST |
| POST | `/api/v1/admin/charges/{id}/decision`, `/api/v1/admin/withdrawals/{id}/decision` | ADMIN |
| GET | `/api/v1/spaces/{id}/reviews` | 공개 |
| POST | `/api/v1/reviews`, `/api/v1/reviews/{id}/images` | 인증 (COMPLETED 예약 1건당 1개, 이미지 5장) |
| POST | `/api/v1/reviews/{id}/reply` | HOST |
| POST | `/api/v1/admin/reviews/{id}/blind` | ADMIN |
| GET/POST | `/api/v1/spaces/{id}/qnas`, `/api/v1/qnas/{id}` | 공개 조회 / 인증 질문 |
| POST | `/api/v1/qnas/{id}/answer` | HOST |
| DELETE | `/api/v1/qnas/{id}` | 작성자 (답변 전만) |
| GET | `/api/v1/notices`, `/api/v1/helps` | 공개 |
| POST/PUT/DELETE | `/api/v1/admin/notices`, `/api/v1/admin/helps` | ADMIN |
| POST | `/api/v1/reports` | 인증 |
| GET/POST | `/api/v1/admin/reports`, `/api/v1/admin/reports/{id}/decision` | ADMIN |
| POST/DELETE | `/api/v1/admin/sanctions`, `/api/v1/admin/sanctions/{id}` | ADMIN |

## 제재

활성 제재가 있는 계정은 로그인·조회·예약 취소·출금은 가능하지만, 예약 생성·결제·리뷰·질문·신고·충전·출금 신청은 403으로 차단된다. 제재는 관리자 API로 부여·해제한다.

## 패키지

`com.lookation.<layer>` — `controller`, `service`, `repository`, `domain`, `dto`, 공통은 `global`. 기능별 하위 패키지는 두지 않는다. 요청 Form은 `controller/form`, 응답은 `dto`, 상태 enum은 `domain/Enums.kt`.
