# Lookation 기획서 분석 인덱스

> 문서 지도: [index](index.md) · [glossary](glossary.md) · [domain](domain.md) · [processes](processes.md) · [flows](flows.md) · [validation](validation.md) · [source](source/source.md) · [source.plain](source/source.plain.md)

## 문서 범위

| 항목 | 내용 |
| --- | --- |
| 프로젝트 | Lookation — 공간 대여·예약 사이트 (Team PFINDER) |
| 주 기획서 | `최종문서/04.PFINDER_요구조건분석서.pdf` (요구 분석, `[p.N]` 표기 기준) |
| 보조 문서 | `공용공간/요구사항 및 정책/정책.docx`, `공용공간/요구사항 및 정책/요구조건분석서(최종).docx`, `최종문서/07.PFINDER_기술문서.pdf` |
| 제외 | 화면 시안(스토리보드)은 화면 구조 분석 대상이 아니므로 원문 변환만 하고 분석 문서에는 인용만 했다 |
| 원문 보존 | both — `source/source.md`(추적용), `source/source.plain.md`(단순 변환) |
| ID·링크 수준 | full — 의미 기반 ID, 문서 지도, Index, 상호 링크, 링크 검사 수행 |
| 작성일 | 2026-11-02 |

## 핵심 요약

- 액터는 관리자·호스트·이용자 3갈래이고, 호스트와 이용자는 같은 계정에서 역할로 갈린다. 관리자는 회원가입 경로가 없고 DB에 직접 입력한다.
- 핵심 거래 축은 `공간 → 검수 → 패키지 → 패키지 적용 → 예약 → 마일리지 결제(에스크로) → 이용완료 → 정산`이다.
- 자금 축은 마일리지 잔액과 거래 이력으로 분리되고, 충전·환전·정산은 모두 관리자 수동 승인 절차를 거친다.
- 제재 축은 `신고 → 인정/반려 → 경고 누적 → 블랙리스트`이고, 블라인드는 신고와 별개로 게시물에 걸리는 노출 제어다.
- 가장 큰 미확정 지점은 블랙리스트 정책, 예약 상태 집합, 검수 상태 코드, 정산 주체, 결제 방식(승인예약)이다. 5건 모두 blocker로 올렸다.

## 산출물 목록

| 파일 | 내용 | 상태 |
| --- | --- | --- |
| [index.md](index.md) | 문서 범위, 요약, ID 규칙, Index, 결정 필요 항목 | 완료 |
| [glossary.md](glossary.md) | 용어 46개, 동의어·충돌·미정의 표시, 정책 문서 전용 용어 분리 | 완료 |
| [domain.md](domain.md) | 엔티티·값 객체·관계, Mermaid 클래스 다이어그램, 상태 전이 7개, 불변식 14개 | 완료 |
| [processes.md](processes.md) | TO-BE 프로세스 19개, 액터·트리거·단계·예외·사후 조건 | 완료 |
| [flows.md](flows.md) | Mermaid flowchart 19개 (정상·대안·예외 경로) | 완료 |
| [validation.md](validation.md) | finding 35건, 추적성, 링크 검사, 미해결 질문 | 완료 |
| [source/source.md](source/source.md) | 추적용 원문 변환 (`[p.N]` 표시, 표 번호, warnings) | 완료 |
| [source/source.plain.md](source/source.plain.md) | 단순 원문 변환 (frontmatter·주석 없음) | 완료 |

## ID 규칙

| 접두사 | 의미 | 형식 |
| --- | --- | --- |
| `V-` | 검증 finding | `V-{AREA}-{TOPIC}-{NN}` |
| `P-` | 프로세스 | `P-{AREA}-{TOPIC}-{NN}` |
| `F-` | 플로우 | `F-{AREA}-{TOPIC}-{NN}` (프로세스와 1:1) |

사용한 AREA 코드와 개수:

| AREA | 의미 | finding 수 | 프로세스·플로우 수 |
| --- | --- | --- | --- |
| `AUTH` | 인증 | 2 | 2 |
| `ACCOUNT` | 계정 | 3 | 3 |
| `PERM` | 권한 | 1 | 0 |
| `DOMAIN` | 핵심 도메인 | 14 | 12 |
| `PROC` | 프로세스 | 2 | 2 |
| `STATUS` | 상태 | 4 | 0 |
| `DATA` | 데이터 | 1 | 0 |
| `INT` | 연동 | 2 | 0 |
| `SEC` | 보안 | 1 | 0 |
| `TERM` | 용어 | 2 | 0 |
| `UI` | 화면 | 2 | 0 |
| `REPORT` | 리포트 | 1 | 0 |

추가 AREA 코드는 만들지 않았다. `MISC`·`ETC`·`OTHER`는 쓰지 않는다.

## Finding Index

| ID | 심각도 | 요약 | 앵커 |
| --- | --- | --- | --- |
| V-ACCOUNT-BLACKLIST-01 | blocker | 블랙리스트 영구 vs 차수별 기간제 | [보기](validation.md#v-account-blacklist-01) |
| V-PERM-BLACKLIST-01 | blocker | 블랙리스트 회원 허용 기능 목록 상충 | [보기](validation.md#v-perm-blacklist-01) |
| V-STATUS-RESERVATION-01 | blocker | 예약 3단계 vs 4단계, 이용완료 판정 시점 | [보기](validation.md#v-status-reservation-01) |
| V-STATUS-INSPECT-01 | blocker | 검수 상태 이름·단계 수 상충 | [보기](validation.md#v-status-inspect-01) |
| V-PROC-SETTLE-01 | blocker | 정산 자동 vs 호스트 버튼 | [보기](validation.md#v-proc-settle-01) |
| V-INT-PAYMENT-01 | blocker | 승인예약 vs 바로결제 단일 서술 | [보기](validation.md#v-int-payment-01) |
| V-AUTH-BLACKLIST-01 | major | 블랙리스트 회원 로그인 허용/거부 | [보기](validation.md#v-auth-blacklist-01) |
| V-AUTH-SESSION-01 | major | 세션 만료 기준·역할별 분리 | [보기](validation.md#v-auth-session-01) |
| V-ACCOUNT-SIGNUP-01 | major | 탈퇴 이메일 재가입 규칙 | [보기](validation.md#v-account-signup-01) |
| V-ACCOUNT-WITHDRAW-01 | major | 탈퇴 제한 대상(이용자 포함 여부) | [보기](validation.md#v-account-withdraw-01) |
| V-DATA-RETENTION-01 | major | 탈퇴 데이터 보존·삭제 범위 | [보기](validation.md#v-data-retention-01) |
| V-DOMAIN-MILEAGE-REFUND-01 | major | 블랙리스트 전환 시 잔액 처리 | [보기](validation.md#v-domain-mileage-refund-01) |
| V-DOMAIN-MILEAGE-CHARGE-01 | major | 충전 대기 초과 시 입금액 처리 | [보기](validation.md#v-domain-mileage-charge-01) |
| V-DOMAIN-MILEAGE-CHARGE-02 | major | 충전 실패 이력 미기록 | [보기](validation.md#v-domain-mileage-charge-02) |
| V-STATUS-MILEAGE-01 | major | 환전 신청 상태 저장 여부 | [보기](validation.md#v-status-mileage-01) |
| V-DOMAIN-MILEAGE-SETTLE-01 | major | 수수료율·계산 규칙 미정의 | [보기](validation.md#v-domain-mileage-settle-01) |
| V-DOMAIN-SPACE-01 | major | 예약 보유 공간 삭제 처리 | [보기](validation.md#v-domain-space-01) |
| V-STATUS-INSPECT-02 | major | 검수 반려 후 재신청 | [보기](validation.md#v-status-inspect-02) |
| V-DOMAIN-PACKAGE-01 | major | 패키지 겹침 판정 단위 | [보기](validation.md#v-domain-package-01) |
| V-DOMAIN-RESERVATION-01 | major | 취소 주체별 시한·환불 비율 | [보기](validation.md#v-domain-reservation-01) |
| V-DOMAIN-RESERVATION-02 | major | 동시 예약 1건 보장 방식 | [보기](validation.md#v-domain-reservation-02) |
| V-DOMAIN-PAYMENT-01 | major | 에스크로 해제·부분 환불 | [보기](validation.md#v-domain-payment-01) |
| V-DOMAIN-REVIEW-01 | major | 평균 별점 소수 자리 | [보기](validation.md#v-domain-review-01) |
| V-DOMAIN-REVIEW-02 | major | 리뷰 작성 자격 | [보기](validation.md#v-domain-review-02) |
| V-DOMAIN-QNA-01 | major | 신고 접수 후 수정·삭제 | [보기](validation.md#v-domain-qna-01) |
| V-DOMAIN-NOTICE-01 | major | 공지 상단 고정 규칙 | [보기](validation.md#v-domain-notice-01) |
| V-PROC-REPORT-01 | major | 자동 블라인드 카운트 기준 | [보기](validation.md#v-proc-report-01) |
| V-INT-EMAIL-01 | major | 메일 실패·재시도 | [보기](validation.md#v-int-email-01) |
| V-SEC-PRIVACY-01 | major | 비밀번호 해시·세션 무효화 | [보기](validation.md#v-sec-privacy-01) |
| V-TERM-MILEAGE-01 | minor | "환전" 용어 혼용 | [보기](validation.md#v-term-mileage-01) |
| V-TERM-MESSENGER-01 | minor | PIRI·DM·메신저 명칭 혼용 | [보기](validation.md#v-term-messenger-01) |
| V-UI-SEARCH-01 | minor | 검색 정렬 기준 불일치 | [보기](validation.md#v-ui-search-01) |
| V-UI-PAGING-01 | minor | 페이지 규칙 적용 범위 | [보기](validation.md#v-ui-paging-01) |
| V-DOMAIN-SPACE-02 | question | 공간유형 전체 목록 | [보기](validation.md#v-domain-space-02) |
| V-REPORT-OPERATION-01 | question | 운영 보고서 범위 포함 여부 | [보기](validation.md#v-report-operation-01) |

## Process Index

| ID | 이름 | 플로우 | 주요 finding |
| --- | --- | --- | --- |
| P-AUTH-LOGIN-01 | 로그인·로그아웃·세션 유지 | [F-AUTH-LOGIN-01](flows.md#f-auth-login-01) | [V-AUTH-BLACKLIST-01](validation.md#v-auth-blacklist-01) |
| P-AUTH-PASSWORD-RESET-01 | 비밀번호 찾기·재설정 | [F-AUTH-PASSWORD-RESET-01](flows.md#f-auth-password-reset-01) | [V-SEC-PRIVACY-01](validation.md#v-sec-privacy-01) |
| P-ACCOUNT-SIGNUP-01 | 회원가입·이메일 인증 | [F-ACCOUNT-SIGNUP-01](flows.md#f-account-signup-01) | [V-ACCOUNT-SIGNUP-01](validation.md#v-account-signup-01) |
| P-ACCOUNT-WITHDRAW-01 | 회원 탈퇴·데이터 분리보관 | [F-ACCOUNT-WITHDRAW-01](flows.md#f-account-withdraw-01) | [V-DATA-RETENTION-01](validation.md#v-data-retention-01) |
| P-ACCOUNT-BLACKLIST-01 | 블랙리스트 지정·해제 | [F-ACCOUNT-BLACKLIST-01](flows.md#f-account-blacklist-01) | [V-ACCOUNT-BLACKLIST-01](validation.md#v-account-blacklist-01) |
| P-DOMAIN-SPACE-REGISTER-01 | 공간 등록 | [F-DOMAIN-SPACE-REGISTER-01](flows.md#f-domain-space-register-01) | [V-DOMAIN-SPACE-02](validation.md#v-domain-space-02) |
| P-PROC-INSPECT-01 | 검수 신청·승인·반려 | [F-PROC-INSPECT-01](flows.md#f-proc-inspect-01) | [V-STATUS-INSPECT-01](validation.md#v-status-inspect-01) |
| P-DOMAIN-SPACE-MANAGE-01 | 공간 조회·수정·삭제 | [F-DOMAIN-SPACE-MANAGE-01](flows.md#f-domain-space-manage-01) | [V-DOMAIN-SPACE-01](validation.md#v-domain-space-01) |
| P-DOMAIN-PACKAGE-01 | 패키지 등록·적용 | [F-DOMAIN-PACKAGE-01](flows.md#f-domain-package-01) | [V-DOMAIN-PACKAGE-01](validation.md#v-domain-package-01) |
| P-DOMAIN-RESERVATION-01 | 예약 생성·마일리지 결제 | [F-DOMAIN-RESERVATION-01](flows.md#f-domain-reservation-01) | [V-DOMAIN-RESERVATION-02](validation.md#v-domain-reservation-02) |
| P-DOMAIN-RESERVATION-CANCEL-01 | 예약 취소·환불 | [F-DOMAIN-RESERVATION-CANCEL-01](flows.md#f-domain-reservation-cancel-01) | [V-DOMAIN-RESERVATION-01](validation.md#v-domain-reservation-01) |
| P-DOMAIN-MILEAGE-CHARGE-01 | 마일리지 충전 신청·승인 | [F-DOMAIN-MILEAGE-CHARGE-01](flows.md#f-domain-mileage-charge-01) | [V-DOMAIN-MILEAGE-CHARGE-01](validation.md#v-domain-mileage-charge-01) |
| P-DOMAIN-MILEAGE-EXCHANGE-01 | 마일리지 환전 | [F-DOMAIN-MILEAGE-EXCHANGE-01](flows.md#f-domain-mileage-exchange-01) | [V-STATUS-MILEAGE-01](validation.md#v-status-mileage-01) |
| P-DOMAIN-MILEAGE-SETTLE-01 | 정산 | [F-DOMAIN-MILEAGE-SETTLE-01](flows.md#f-domain-mileage-settle-01) | [V-PROC-SETTLE-01](validation.md#v-proc-settle-01) |
| P-DOMAIN-REVIEW-01 | 리뷰 작성·답변·신고 | [F-DOMAIN-REVIEW-01](flows.md#f-domain-review-01) | [V-DOMAIN-REVIEW-01](validation.md#v-domain-review-01) |
| P-DOMAIN-QNA-01 | Q&A 작성·답변 | [F-DOMAIN-QNA-01](flows.md#f-domain-qna-01) | [V-DOMAIN-QNA-01](validation.md#v-domain-qna-01) |
| P-PROC-REPORT-01 | 신고 접수·처리 | [F-PROC-REPORT-01](flows.md#f-proc-report-01) | [V-PROC-REPORT-01](validation.md#v-proc-report-01) |
| P-DOMAIN-MESSAGE-01 | PIRI 1:1 문의 | [F-DOMAIN-MESSAGE-01](flows.md#f-domain-message-01) | [V-TERM-MESSENGER-01](validation.md#v-term-messenger-01) |
| P-DOMAIN-CONTENT-01 | 공지사항·도움말 운영 | [F-DOMAIN-CONTENT-01](flows.md#f-domain-content-01) | [V-DOMAIN-NOTICE-01](validation.md#v-domain-notice-01) |

## Flow Index

| ID | 프로세스 | 비고 |
| --- | --- | --- |
| F-AUTH-LOGIN-01 | [P-AUTH-LOGIN-01](processes.md#p-auth-login-01) | 역할별 분기 포함 |
| F-AUTH-PASSWORD-RESET-01 | [P-AUTH-PASSWORD-RESET-01](processes.md#p-auth-password-reset-01) | 링크 만료·재사용 불가 경로 |
| F-ACCOUNT-SIGNUP-01 | [P-ACCOUNT-SIGNUP-01](processes.md#p-account-signup-01) | 이메일 인증·중복 검사 |
| F-ACCOUNT-WITHDRAW-01 | [P-ACCOUNT-WITHDRAW-01](processes.md#p-account-withdraw-01) | 잔액·예약 보유 차단 |
| F-ACCOUNT-BLACKLIST-01 | [P-ACCOUNT-BLACKLIST-01](processes.md#p-account-blacklist-01) | 지정·해제 |
| F-DOMAIN-SPACE-REGISTER-01 | [P-DOMAIN-SPACE-REGISTER-01](processes.md#p-domain-space-register-01) | 단계별 입력 |
| F-PROC-INSPECT-01 | [P-PROC-INSPECT-01](processes.md#p-proc-inspect-01) | 승인·반려 |
| F-DOMAIN-SPACE-MANAGE-01 | [P-DOMAIN-SPACE-MANAGE-01](processes.md#p-domain-space-manage-01) | 예약 보유 삭제 차단 |
| F-DOMAIN-PACKAGE-01 | [P-DOMAIN-PACKAGE-01](processes.md#p-domain-package-01) | 검증·겹침 |
| F-DOMAIN-RESERVATION-01 | [P-DOMAIN-RESERVATION-01](processes.md#p-domain-reservation-01) | 결제 실패·선점 패배 |
| F-DOMAIN-RESERVATION-CANCEL-01 | [P-DOMAIN-RESERVATION-CANCEL-01](processes.md#p-domain-reservation-cancel-01) | 주체별 환불 |
| F-DOMAIN-MILEAGE-CHARGE-01 | [P-DOMAIN-MILEAGE-CHARGE-01](processes.md#p-domain-mileage-charge-01) | 관리자 승인 |
| F-DOMAIN-MILEAGE-EXCHANGE-01 | [P-DOMAIN-MILEAGE-EXCHANGE-01](processes.md#p-domain-mileage-exchange-01) | 계좌 없음 분기 |
| F-DOMAIN-MILEAGE-SETTLE-01 | [P-DOMAIN-MILEAGE-SETTLE-01](processes.md#p-domain-mileage-settle-01) | 정산 |
| F-DOMAIN-REVIEW-01 | [P-DOMAIN-REVIEW-01](processes.md#p-domain-review-01) | 신고 누적 블라인드 |
| F-DOMAIN-QNA-01 | [P-DOMAIN-QNA-01](processes.md#p-domain-qna-01) | 답글 1개 |
| F-PROC-REPORT-01 | [P-PROC-REPORT-01](processes.md#p-proc-report-01) | 인정·반려 |
| F-DOMAIN-MESSAGE-01 | [P-DOMAIN-MESSAGE-01](processes.md#p-domain-message-01) | 1:1 대화 |
| F-DOMAIN-CONTENT-01 | [P-DOMAIN-CONTENT-01](processes.md#p-domain-content-01) | 공지·도움말 |

## 결정 필요 항목

| 번호 | 결정 | 관련 finding | 담당 |
| --- | --- | --- | --- |
| 1 | 블랙리스트는 영구인가, 차수별 기간제인가 | [V-ACCOUNT-BLACKLIST-01](validation.md#v-account-blacklist-01) | 제품 |
| 2 | 블랙리스트 회원의 로그인·허용 기능 범위 | [V-PERM-BLACKLIST-01](validation.md#v-perm-blacklist-01), [V-AUTH-BLACKLIST-01](validation.md#v-auth-blacklist-01) | 제품 |
| 3 | 예약 상태 집합과 이용완료 판정 시점 | [V-STATUS-RESERVATION-01](validation.md#v-status-reservation-01) | 제품·개발 |
| 4 | 검수 상태 코드표 확정 | [V-STATUS-INSPECT-01](validation.md#v-status-inspect-01), [V-STATUS-INSPECT-02](validation.md#v-status-inspect-02) | 제품 |
| 5 | 정산 주체(자동/호스트 신청)와 수수료율 | [V-PROC-SETTLE-01](validation.md#v-proc-settle-01), [V-DOMAIN-MILEAGE-SETTLE-01](validation.md#v-domain-mileage-settle-01) | 제품 |
| 6 | 승인예약 포함 여부 | [V-INT-PAYMENT-01](validation.md#v-int-payment-01) | 제품 |
| 7 | 탈퇴 데이터 보존·삭제 범위 | [V-DATA-RETENTION-01](validation.md#v-data-retention-01) | 제품 |
| 8 | 블랙리스트 전환 시 잔여 마일리지 처리 | [V-DOMAIN-MILEAGE-REFUND-01](validation.md#v-domain-mileage-refund-01) | 제품 |
| 9 | 운영 보고서 범위 포함 여부 | [V-REPORT-OPERATION-01](validation.md#v-report-operation-01) | 제품 |
| 10 | 공간유형 전체 목록 | [V-DOMAIN-SPACE-02](validation.md#v-domain-space-02) | 제품 |

## 준비 상태

| 항목 | 상태 |
| --- | --- |
| 원문 변환 | 완료 (`source/source.md`, `source/source.plain.md`) |
| 용어 사전 | 완료 — 구현 전 확정 필요 8개(모호·충돌·미정의) |
| 도메인 모델 | 완료 — 구현 전 확정 필요 2개(블랙리스트 모델, 탈퇴회원 모델) |
| 프로세스·플로우 | 완료 — 19쌍 |
| 검증 | 완료 — blocker 6, major 23, minor 4, question 2 |
| 구현 착수 가능 여부 | **보류** — blocker 6건 결정 전에는 예약·정산·검수·결제 구현을 시작하지 않는다 |
