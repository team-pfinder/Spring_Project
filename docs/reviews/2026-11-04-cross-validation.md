# 2026-11-04 교차검증 — 신규 백엔드 1차 슬라이스

대상: `server/` (auth·reservation·wallet·schema)와 `docs/adr/0001~0008`.
범위: 읽기 전용 리뷰. 검증자는 코드·문서를 수정하지 않았고, 결과는 `/tmp/lookation-review-<agent>.md`에 캡처했다.

## 실행

| 검증자 | 명령 | 모델 | 지적 수 |
|---|---|---|---|
| Gemini | Orca 터미널에서 `gemini -p "$(cat brief)"` | `gemini-3.8-flash` (CLI 설정) | 18 (P0 4 / P1 8 / P2 6) |
| Codex | Orca 터미널에서 `codex exec -m gpt-6.1-sol -c model_reasoning_effort="high"` | gpt-6.1-sol, high | 20 (P0 1 / P1 15 / P2 4) |
| OpenCode | Orca 터미널에서 `opencode run --auto -m opencode-go/muse-spark-1.3-contributor#xhigh` | muse-spark-1.3-contributor, xhigh | 10 (P0 6 / P1 4) |

공통 브리프: `/tmp/lookation-review-brief.md`. 세 검증자에게 같은 설계 결정·구현 범위·점검 항목을 전달했다.

## 실측으로 확인한 것

| 주장 | 확인 방법 | 결과 |
|---|---|---|
| 정상 로그인이 readOnly 트랜잭션에서 refresh 토큰 INSERT 실패 | 성공 로그인 통합 테스트 추가 후 실행 | **재현**. 401로 위장됨(원인: INSERT 실패 → `/error` 재디스패치가 보안에 차단) |
| 취소가 조건부 UPDATE가 아니어서 중복 환불 가능 | 코드 경로 확인(`updateStatus`가 `WHERE id`만 사용) | 확인. 상태 조건부 UPDATE로 수정 |
| refresh rotation이 비원자적 | `rotate`에 트랜잭션 없음, `revoke`가 영향 행 수 미확인 | 확인. 수정. 계열 폐기는 `REQUIRES_NEW`로 분리해 401과 함께 커밋 |
| `unit='DAY'` 가격 규칙 조회 불가 | `START_TIME.eq(null)` 비교 + CHECK 제약 확인 | 확인. DAY 전용 조회·전일 검증 추가 |
| 예약 인원이 공간 정원을 초과해도 통과 | `SpaceRow`에 capacity 없음 확인 | 확인. 정원 검사 추가 |

## 반영한 수정 (P0·P1)

| # | 지적 | 조치 |
|---|---|---|
| 1 | 동시 취소 중복 환불 | `updateStatus(id, expected, next, ...)` 조건부 UPDATE + 영향 행 0이면 409. 회귀 테스트 `cancelling twice refunds only once` |
| 2 | 로그인 readOnly INSERT 실패 | `AuthService.login`을 쓰기 트랜잭션으로 변경. 성공 로그인 테스트 추가 |
| 3 | refresh rotation 경쟁 | `@Transactional` + 조건부 `revoke` + `REFRESH_NEW` 계열 폐기. 기존 재사용 테스트 통과 |
| 4 | 탈퇴·비활성 계정이 refresh로 갱신 | rotation에서 계정 상태 확인 후 계열 폐기 |
| 5 | DAY 가격 규칙 조회 불가 | `findHourRule`/`findDayRule` 분리, DAY는 00:00~다음 날 00:00만 허용. 테스트 추가 |
| 6 | DAY 가격 중복 허용(NULL unique) | 부분 유니크 인덱스 `price_rules_day_unique` |
| 7 | 정원 초과 예약 | `spaces.capacity` 조회·검사, 409. 테스트 추가 |
| 8 | `guestCount` short 축소 | `@Max(32767)` 검증 추가 |
| 9 | 이용 시작 후 취소 가능 | 취소 시 `now < startAt` 검증 |
| 10 | `COMPLETED` 예약이 겹침 제약에서 제외 | exclusion constraint 조건에 `COMPLETED` 추가 |
| 11 | JWT 비밀키 최소 길이 미검증 | `@Validated` + `@Size(min = 32)` |
| 12 | 검증 오류가 문자열, 500 핸들러 없음 | `ProblemDetail.errors` 구조화 + `Exception` 핸들러(내부 메시지 은닉) |
| 13 | 인증 실패가 ProblemDetail 계약과 다름 | `/error` 허용으로 원 오류 노출 차단 해제. 인증 진입점 JSON화는 이월 |
| 14 | `jooq-codegen.xml`이 Gradle 입력이 아님 | `inputs.file` 추가 |

테스트: 14개 통과 (스키마 2, auth 5, reservation 7).

## 이월 (범위 또는 설계 결정)

| 지적 | 이유 | 다음 조치 |
|---|---|---|
| 승인예약(`PENDING_APPROVAL → PENDING_PAYMENT`) 미구현 | 이번 슬라이스는 즉시결제만 | 예약 방식 필드 + 승인/거절/결제 API |
| 이용완료·에스크로 해제·T+1 정산 배치 없음 | 배치 슬라이스 미착수 | `@Scheduled` + `settlements`/`RELEASE`/`SETTLE` |
| 제재(`sanctions`) 런타임 미강제 | 제재 도메인 미착수 | 로그인·refresh·예약 생성에 유효 제재 검사 |
| 가입 이메일 인증 미구현 | `email_verifications` 테이블만 존재 | 코드 발송·확인 후 계정 활성화 |
| 공간·호스트·검수·지갑·정산·리뷰·Q&A·공지 API 없음 | 슬라이스 범위 | 패리티 순서대로 구현 |
| 취소 페널티 수취 계정 없음 | 플랫폼 수익 계정 미모델링 | 정산 슬라이스에서 플랫폼 계정·원장 항목 추가 |
| jOOQ 코드 생성이 `V2`만 읽음 | OSS 파서가 CHECK를 못 읽어 구조/제약을 분리 | 배포 전 DB 기반 코드 생성으로 전환 |
| 목록 무제한 조회 | keyset 페이징 미적용 | `(start_at, id)` 커서 |
| 호스트가 받은 예약 목록 없음 | API 미구현 | `GET /api/v1/hosts/reservations` |
| `password_hash` 전 컬럼 조회 | 기능 결함 아님, 노출 면적 | 필요한 컬럼만 select |
| 관측성(원장 대사·정산 지연·재사용 탐지) | 운영 단계 | 메트릭·대사 잡 |

## 검증자 간 불일치와 판단

- **"리뷰·Q&A·공지 테이블 누락"**: Gemini는 P0, OpenCode는 P0-6, Codex는 P1-13으로 분류. 사실은 같고, 이번 슬라이스 범위 밖이라 **P0 아님**으로 판정했다. 패리티 체크리스트에 미구현으로 남아 있다.
- **"HOUR 가격 정확일치가 버그"**: OpenCode/Gemini 지적. 현재 모델은 "가격 블록 = 예약 단위"라 정확일치가 의도다. 다만 임의 구간 예약을 허용하지 않는다는 점을 문서화해야 한다. DAY는 실제 버그였고 수정했다.
- **"취소 페널티 자금 유실"**: Gemini 지적. 회원 원장은 `HOLD(-price) + REFUND(+refund)`로 순지출이 맞지만, 페널티 수취 계정이 없어 플랫폼 쪽 원장이 비어 있다. **부분 인정**, 정산 슬라이스에서 플랫폼 계정으로 해소한다.
- **Codex "정상 로그인 실패"**: 실측으로 확인된 유일한 P0급 결함. 다른 검증자는 놓쳤다.

## 참고

- 검증자 원문: `/tmp/lookation-review-{gemini,codex,opencode}.md`
- 이번 검증은 기동·동시성 재현을 포함하지 않는다. 동시 취소·동시 refresh는 코드 경로와 조건부 UPDATE로 방어했지만, 실제 경쟁 재현 테스트는 아직 없다.

---

# docker-compose 로컬 인프라 계획 교차검증

대상: 로컬 개발 인프라 계획(`docker compose` + MiniStack Secrets Manager, 앱은 호스트 `bootRun`). 검증자는 저장소를 수정하지 않았다.

| 검증자 | 실행 | 결과 |
| --- | --- | --- |
| Gemini (`gemini-3.8-flash`) | `orca agent launch --agent gemini` | must 4, should 3 |
| Codex (`gpt-6.1-sol`, high) | `orca agent launch --agent codex --model gpt-6.1-sol --effort high` | must 5, should 5 |
| OpenCode (`muse-spark-1.3-contributor`, xhigh) | `opencode run --auto -m opencode-go/muse-spark-1.3-contributor#xhigh` | must 6, should 5 |

## 반영한 지적

| 지적 | 판정 | 반영 |
| --- | --- | --- |
| EPP 등록은 `META-INF/spring.factories` + `org.springframework.boot.EnvironmentPostProcessor` 키 | 수용 | `server/src/main/resources/META-INF/spring.factories` |
| EPP는 `ConfigDataEnvironmentPostProcessor.ORDER` 이후 실행 | 수용 | `getOrder() = ORDER + 1` |
| 시크릿은 `application.yml`이 참조하는 `LOOKATION_*` 이름으로 주입 | 수용 | `SecretsEnvironmentPostProcessor.SECRET_FIELDS` |
| `lookation.secrets.enabled` 기본 false, local에서만 true | 수용 | `application-local.yml` |
| `put-secret-value`는 upsert가 아님 | 수용 | 시드 스크립트 `describe → create|put` |
| 정적 자격증명·region·endpoint override·timeout 필요 | 수용 | `application-local.yml` + EPP builder |
| AWS SDK BOM·HTTP client 미지정 시 빌드 불가 | 수용 | BOM `2.46.7` + `url-connection-client` |
| `/ready` 200은 시드 성공을 보장하지 않음 | 수용 | healthcheck를 `describe-secret`으로 |
| PostgreSQL 18 볼륨 경로는 `/var/lib/postgresql` | 수용 | `docker-compose.yml` (`PGDATA=/var/lib/postgresql/18/docker` 실측) |
| 포트 loopback 바인딩 | 수용 | 전 포트 `127.0.0.1:` |
| 이미지 태그 고정 | 수용 | `mailpit:v1.33.0`, `ministack:1.5.36` |
| 프로필 export 시 통합 테스트가 로컬 DB를 봄 | 수용 | `server/README.md`, `AGENTS.md` 경고 |
| 관리자 권한은 공개 공지 조회로 검증 불가 | 수용 | 스모크를 `GET /api/v1/admin/inspections`로 |

## 기각한 지적

| 지적 | 기각 이유 |
| --- | --- |
| EPP 등록에 `.imports` 파일이 필요하다(Gemini) | `spring-boot-4.1.2.jar`에 `SpringFactoriesEnvironmentPostProcessorsFactory`가 있고 상수 풀에 신·구 키가 모두 존재. `.imports` 참조 없음 |
| `SERVICES`는 LocalStack 전용이라 무시된다(OpenCode) | 실측: `SERVICES=secretsmanager` → "1 services", 미설정 → "91 services" |
| `init.d`(부팅 전)가 시드에 적합하다(OpenCode) | 실측: 부팅 전 스크립트는 게이트웨이 수신 전이라 `Could not connect`. `ready.d`만 성공 |
| mailpit `v1.21` 고정(Gemini) | Docker Hub 실제 태그는 `v1.33.0` |
| `/docker-entrypoint-initaws.d` VOLUME 선언 때문에 ro 바인드 불가(OpenCode) | 실측으로 ro 바인드 성공 |

## 계획 검토에서 나오지 않았고 구현 중 발견한 결함

| 결함 | 원인 | 수정 |
| --- | --- | --- |
| `spring.mail.host` 설정 시 기동 실패(`BeanDefinitionOverrideException: mailSender`) | 앱 `MailConfig.mailSender` 빈 이름이 Boot의 `JavaMailSender` 빈과 충돌. 테스트는 `spring.mail.host`를 설정하지 않아 드러나지 않았다 | 빈 메서드를 `lookationMailSender`로 개명 |
