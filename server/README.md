# Lookation server

레거시 Lookation(Spring MVC 3 + JSP + Oracle)을 대체하는 백엔드. 설계 결정은 `../docs/adr/`에 있다.

## 요구 사항

- JDK 25 (Gradle toolchain이 25를 찾는다)
- Docker (통합 테스트용 Testcontainers PostgreSQL)

## 명령

```bash
./gradlew build          # jOOQ 코드 생성 + 컴파일 + 테스트
./gradlew generateJooq   # 스키마 변경 후 코드 재생성
./gradlew bootRun        # 로컬 실행 (아래 환경변수 필요)
```

## 환경변수

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
| `V1__extensions.sql` | `btree_gist` 확장 |
| `V2__baseline.sql` | 테이블 구조. **jOOQ 코드 생성 입력** |
| `V3__constraints.sql` | CHECK·부분 유니크 인덱스·중복 예약 배제 제약 |

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
| GET | `/api/v1/spaces?type=&keyword=&guests=&minPrice=&maxPrice=&from=&to=&cursor=&limit=` | 공개 |
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

`com.lookation.<layer>.<feature>` — `controller`, `service`, `repository`, `domain`, `dto`, 공통은 `global`. 요청 Form은 `controller/<feature>`, 응답은 `dto/<feature>`.
