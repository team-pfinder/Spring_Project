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
