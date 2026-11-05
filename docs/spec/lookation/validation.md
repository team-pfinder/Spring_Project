# 검증

> 문서 지도: [index](index.md) · [glossary](glossary.md) · [domain](domain.md) · [processes](processes.md) · [flows](flows.md) · [validation](validation.md) · [source](source/source.md) · [source.plain](source/source.plain.md)

## 검증 요약

| 항목 | 결과 |
| --- | --- |
| 검증 범위 | 요구 PDF(`04.PFINDER_요구조건분석서.pdf`), 정책 문서(`정책.docx`), 최종 요구분석서(`요구조건분석서(최종).docx`), 기술문서(`07.PFINDER_기술문서.pdf`) |
| 검증 영역 | 용어·도메인·프로세스·요구사항·일관성·추적성 |
| finding 합계 | 35 |
| Blocker | 6 |
| Major | 23 |
| Minor | 4 |
| Question | 2 |
| 링크 검사 | `check_links.py docs/spec/lookation` — 결과는 아래 [링크 검사](#링크-검사) |
| 미해결 질문 | [미해결 질문](#미해결-질문) |

검증 원칙: 모든 finding은 원문 위치(`[p.N]`, `[정책 §...]`, `[최종 §...]`, `[기술 p.N]`)를 근거로 한다. 원문에 없는 내용은 `추론`으로 표시했고, 제품 결정이 필요한 항목은 `Question`으로 분리했다. AS-IS/TO-BE 충돌은 어느 쪽이 최신인지 임의로 정하지 않았다.

## Findings

### Blocker

#### V-ACCOUNT-BLACKLIST-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | blocker |
| 영역 | 계정·제재 |
| 발견 내용 | 블랙리스트 기간이 문서마다 다르다. 요구 PDF는 "영구적으로 서비스 이용을 제한"이라 쓰고 `[p.13]`, 정책 문서는 누적 차수에 따라 기간이 늘어나는 기간제를 서술한다 `[정책 §관리자 3-2]`. 해제 예정일 컬럼 유무와 자동 해제 주체가 정해지지 않아 계정 상태 모델을 확정할 수 없다. |
| 근거·출처 | `[p.3]`, `[p.13]`, `[정책 §관리자 3-2]` |
| 권장 조치 | 영구/기간제 중 하나를 제품이 결정하고, 기간제라면 차수별 기간표와 자동 해제 배치 여부를 명시한다. |
| 담당 | 제품 |
| 상태 | resolved — 기간제 누적(1차 7일, 2차 30일, 3차 영구), 자동 해제 배치. [ADR 0005](../../adr/0005-unified-account-model.md) |
| 관련 | [P-ACCOUNT-BLACKLIST-01](processes.md#p-account-blacklist-01), [F-ACCOUNT-BLACKLIST-01](flows.md#f-account-blacklist-01) |

#### V-PERM-BLACKLIST-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | blocker |
| 영역 | 권한 |
| 발견 내용 | 블랙리스트 회원의 허용 기능이 상충한다. 요구 PDF는 "환전(이용자: 충전, 호스트: 정산), 회원 탈퇴, 예약 확인(관리) 외 나머지 기능은 사용할 수 없다"고 쓰지만 `[p.3]`, 정책 문서는 로그인 후 모든 기능 제한으로 읽힌다. 로그인 허용 여부·예약 조회 범위·이용 중 예약의 접속 허용이 함께 걸린다. |
| 근거·출처 | `[p.3]`, `[p.11]`, `[정책 §관리자 3-2]` |
| 권장 조치 | 허용 기능 목록을 표로 확정하고, 로그인·조회·충전·환전·탈퇴 각각의 허용/차단을 명시한다. |
| 담당 | 제품 |
| 상태 | resolved — 로그인·예약 조회·환전·탈퇴만 허용, 쓰기 차단. [ADR 0005](../../adr/0005-unified-account-model.md) |
| 관련 | [P-ACCOUNT-BLACKLIST-01](processes.md#p-account-blacklist-01), [P-AUTH-LOGIN-01](processes.md#p-auth-login-01) |

#### V-STATUS-RESERVATION-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | blocker |
| 영역 | 상태 |
| 발견 내용 | 예약 상태 집합이 3단계(예약완료·이용완료·예약취소) `[p.17]`와 4단계(환불신청·환불완료 포함) `[정책 §이용자 5-3]`로 갈린다. 이용완료 전이 시점(체크아웃 시각 판정 주체·배치 여부)도 정의되지 않았다. 상태 머신과 정산 트리거가 이 결정에 종속된다. |
| 근거·출처 | `[p.9]`, `[p.17]`, `[정책 §이용자 5-3]` |
| 권장 조치 | 상태 집합을 확정하고, 이용완료 전이를 배치/실시간 중 무엇으로 판정하는지 명시한다. |
| 담당 | 제품·개발 |
| 상태 | resolved — 8상태(PENDING_APPROVAL/PENDING_PAYMENT/CONFIRMED/COMPLETED/CANCELLED/REFUNDED/REJECTED/EXPIRED), 이용완료는 00:05 배치. [ADR 0006](../../adr/0006-reservation-state-machine.md) |
| 관련 | [P-DOMAIN-RESERVATION-01](processes.md#p-domain-reservation-01), [P-DOMAIN-RESERVATION-CANCEL-01](processes.md#p-domain-reservation-cancel-01) |

#### V-STATUS-INSPECT-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | blocker |
| 영역 | 상태·검수 |
| 발견 내용 | 검수 상태의 이름과 단계 수가 문서마다 다르다. 관리자 화면은 "인증 대기" `[p.3]`, 최종 요구분석서는 "등록 대기" `[최종 §4-1]`, 정책 문서는 4단계 상태를 쓴다. 승인 상태도 "검수 승인"과 "검수 완료"로 갈려 상태 코드·화면 문구를 확정할 수 없다. |
| 근거·출처 | `[p.3]`, `[p.14]`, `[p.15]`, `[최종 §4-1]`, `[정책 §호스트 3-2]` |
| 권장 조치 | 상태 코드표(코드·표시명·전이 조건)를 하나로 확정하고 화면 문구를 코드에서 파생시킨다. |
| 담당 | 제품 |
| 상태 | resolved — DRAFT/PENDING_INSPECTION/INSPECTION_REJECTED/PUBLISHED/SUSPENDED/CLOSED. 반려 후 재신청은 V-STATUS-INSPECT-02에서 계속 |
| 관련 | [P-PROC-INSPECT-01](processes.md#p-proc-inspect-01), [F-PROC-INSPECT-01](flows.md#f-proc-inspect-01) |

#### V-PROC-SETTLE-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | blocker |
| 영역 | 프로세스·정산 |
| 발견 내용 | 정산 시점과 주체가 상충한다. 요구 PDF는 이용완료 시 자동 정산으로 읽히고 `[p.18]`, 최종 요구분석서는 호스트가 정산 버튼을 눌러 신청하는 흐름을 둔다 `[최종 §5-1]`. 정산 후 취소가 발생했을 때 회수 규칙도 없다. |
| 근거·출처 | `[p.18]`, `[p.19]`, `[최종 §5-1]`, `[정책 §호스트 8]` |
| 권장 조치 | 자동/수동 중 하나를 정하고, 정산 후 취소·환불 시 회수 또는 차감 규칙을 정의한다. |
| 담당 | 제품·개발 |
| 상태 | resolved — 정산 자동(T+1), 출금만 호스트 신청, 정산 후 취소는 음수 원장. [ADR 0007](../../adr/0007-wallet-ledger-settlement.md) |
| 관련 | [P-DOMAIN-MILEAGE-SETTLE-01](processes.md#p-domain-mileage-settle-01), [F-DOMAIN-MILEAGE-SETTLE-01](flows.md#f-domain-mileage-settle-01) |

#### V-INT-PAYMENT-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | blocker |
| 영역 | 연동·결제 |
| 발견 내용 | 결제 방식이 "바로결제(마일리지·에스크로)" 하나로 서술되어 있으나 `[p.9]`, `[p.16]`, 정책 문서에는 호스트 승인 후 결제하는 "승인예약"이 있다 `[정책 §이용자 3-3]`. 승인 대기 상태·승인 시한·승인 거절 시 예약 상태가 정의되지 않아 결제 흐름을 확정할 수 없다. |
| 근거·출처 | `[p.9]`, `[p.16]`, `[정책 §이용자 3-3]`, `[기술 p.76]` |
| 권장 조치 | 승인예약을 요구 범위에 포함할지 결정한다. 포함하면 승인 대기 상태와 시한·거절 처리를 정의한다. |
| 담당 | 제품 |
| 상태 | resolved — 승인예약 포함. 승인 24시간, 결제 1시간 시한, 초과 시 EXPIRED. [ADR 0006](../../adr/0006-reservation-state-machine.md) |
| 관련 | [P-DOMAIN-RESERVATION-01](processes.md#p-domain-reservation-01), [F-DOMAIN-RESERVATION-01](flows.md#f-domain-reservation-01) |

### Major

#### V-AUTH-BLACKLIST-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 인증·권한 |
| 발견 내용 | 블랙리스트 회원의 로그인 처리가 "안내 팝업 후 로그인 거부"와 "제한 로그인 허용"으로 갈린다 `[p.3]`, `[p.11]`. 로그인 자체를 막으면 허용 기능(충전·환전·탈퇴·예약 확인)에 접근할 수 없어 권한 모델과 충돌한다. |
| 근거·출처 | `[p.3]`, `[p.11]`, `[정책 §관리자 3-2]` |
| 권장 조치 | 제한 로그인으로 확정하고 허용 메뉴를 서버에서 강제하는 방식으로 정리한다. |
| 담당 | 제품·개발 |
| 상태 | open |
| 관련 | [P-AUTH-LOGIN-01](processes.md#p-auth-login-01), [F-AUTH-LOGIN-01](flows.md#f-auth-login-01) |

#### V-AUTH-SESSION-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 인증·세션 |
| 발견 내용 | "30분 무요청 시 세션 만료"만 있고 `[p.2]`, 만료 기준(마지막 요청 시각 갱신 여부), 역할별 세션 분리(이용자/호스트/관리자), 중복 로그인 정책이 없다. 관리자 세션의 타임아웃이 별도인지도 불명이다. |
| 근거·출처 | `[p.2]`, `[p.6]`, `[기술 p.31]` |
| 권장 조치 | 세션 타임아웃·갱신 방식·역할별 분리·중복 로그인 허용 여부를 명시한다. |
| 담당 | 개발 |
| 상태 | open |
| 관련 | [P-AUTH-LOGIN-01](processes.md#p-auth-login-01), [F-AUTH-LOGIN-01](flows.md#f-auth-login-01) |

#### V-ACCOUNT-SIGNUP-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 계정 |
| 발견 내용 | "이메일은 호스트·이용자·관리자 전체에서 중복될 수 없다"는 불변식만 있고 `[p.12]`, 탈퇴회원이 쓴 이메일의 재가입 허용 여부와 재가입 시 기존 게시물 연결 방식이 없다. 관리자 계정은 DB 직접 입력이라 이 불변식의 적용 대상인지도 불명하다. |
| 근거·출처 | `[p.12]`, `[p.13]` |
| 권장 조치 | 탈퇴 이메일 재사용 규칙과 재가입 시 데이터 연결 정책을 확정한다. |
| 담당 | 제품 |
| 상태 | resolved — 탈퇴 후 30일 유예 동안 재가입 차단, 이후 같은 이메일로 새 계정 생성. 기존 게시물·예약은 익명 유지, 연결 없음 |
| 관련 | [P-ACCOUNT-SIGNUP-01](processes.md#p-account-signup-01), [F-ACCOUNT-SIGNUP-01](flows.md#f-account-signup-01) |

#### V-ACCOUNT-WITHDRAW-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 계정·예약 |
| 발견 내용 | 탈퇴 제한 조건이 갈린다. 요구 PDF는 "호스트는 예약 내역이 있으면 탈퇴 불가"로 읽히고 `[p.13]`, 정책 문서는 이용자도 예약 보유 시 탈퇴 불가로 읽힌다 `[정책 §이용자 1-4]`. 잔여 마일리지 0 조건과 예약 보유 조건의 우선순위도 없다. |
| 근거·출처 | `[p.10]`, `[p.13]`, `[정책 §이용자 1-4]` |
| 권장 조치 | 역할별 탈퇴 제한 조건과 검사 순서를 표로 확정한다. |
| 담당 | 제품 |
| 상태 | open |
| 관련 | [P-ACCOUNT-WITHDRAW-01](processes.md#p-account-withdraw-01), [F-ACCOUNT-WITHDRAW-01](flows.md#f-account-withdraw-01) |

#### V-DATA-RETENTION-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 데이터·보안 |
| 발견 내용 | 탈퇴 시 "식별 정보는 삭제, 글은 알 수 없는 회원으로 유지, 예약 기록은 보존"만 있고 `[p.13]`, 보존 기간·분리보관 테이블 속성·탈퇴회원의 계좌/사업자 정보 삭제 시점이 정의되지 않았다. 기술문서도 테이블 존재만 언급한다 `[기술 p.69]`. |
| 근거·출처 | `[p.13]`, `[기술 p.69]` |
| 권장 조치 | 항목별 삭제/보존/보존 기간을 표로 확정하고 분리보관 스키마를 정의한다. |
| 담당 | 제품·개발 |
| 상태 | resolved — 탈퇴 즉시 로그인 차단, 30일 유예 후 개인정보 익명화, 예약·결제·정산 기록 5년 보존, 리뷰는 작성자만 익명화 |
| 관련 | [P-ACCOUNT-WITHDRAW-01](processes.md#p-account-withdraw-01) |

#### V-DOMAIN-MILEAGE-REFUND-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 마일리지 |
| 발견 내용 | 블랙리스트 전환 시 잔여 마일리지 처리가 "자동 환불" `[p.18]`과 "환전 신청만 가능" `[p.3]`으로 상충한다. 자동 환불의 대상 계좌(대표계좌) 지정 규칙도 없다 `[정책 §관리자 3-2]`. |
| 근거·출처 | `[p.3]`, `[p.18]`, `[정책 §관리자 3-2]` |
| 권장 조치 | 자동 환불 여부와 대상 계좌 결정 규칙을 확정한다. |
| 담당 | 제품 |
| 상태 | open |
| 관련 | [P-ACCOUNT-BLACKLIST-01](processes.md#p-account-blacklist-01), [P-DOMAIN-MILEAGE-EXCHANGE-01](processes.md#p-domain-mileage-exchange-01) |

#### V-DOMAIN-MILEAGE-CHARGE-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 마일리지·충전 |
| 발견 내용 | 충전 신청 대기 시간이 지나 관리자 승인 버튼이 비활성화될 때, 이미 입금된 금액의 처리(자동 환불·수동 처리·이력 미기록)가 정의되지 않았다 `[p.5]`, `[p.10]`. |
| 근거·출처 | `[p.5]`, `[p.10]`, `[정책 §이용자 9]` |
| 권장 조치 | 대기 시간 초과 시 입금액 처리와 사용자 안내 문구를 확정한다. |
| 담당 | 제품·운영 |
| 상태 | open |
| 관련 | [P-DOMAIN-MILEAGE-CHARGE-01](processes.md#p-domain-mileage-charge-01), [F-DOMAIN-MILEAGE-CHARGE-01](flows.md#f-domain-mileage-charge-01) |

#### V-DOMAIN-MILEAGE-CHARGE-02

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 마일리지·충전 |
| 발견 내용 | 입금액 불일치로 충전이 실패한 뒤의 입금분 환불이 "사이트에 기록되지 않는다"고 서술되어 `[p.10]`, 회계·분쟁 대응을 위한 실패 이력 저장이 불가능하다. |
| 근거·출처 | `[p.10]`, `[p.5]` |
| 권장 조치 | 실패 건도 신청 이력으로 남기고, 환불 처리 상태를 기록할지 결정한다. |
| 담당 | 제품·개발 |
| 상태 | open |
| 관련 | [P-DOMAIN-MILEAGE-CHARGE-01](processes.md#p-domain-mileage-charge-01) |

#### V-STATUS-MILEAGE-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 마일리지·상태 |
| 발견 내용 | 환전이 "완료 건만 이력으로 남는다" `[p.5]`고 하면서 관리자 입금 전 상태가 화면에 표시되어야 한다 `[p.19]`. 상태 컬럼을 관리하지 않는다는 서술과 신청/완료 구분이 충돌한다. |
| 근거·출처 | `[p.5]`, `[p.19]`, `[정책 §호스트 8]` |
| 권장 조치 | 신청·처리중·완료·실패 상태를 저장할지 확정한다. |
| 담당 | 개발 |
| 상태 | resolved — 출금 상태를 `withdrawals_status_check`의 `REQUESTED`/`APPROVED`/`PAID`/`REJECTED`로 저장한다. `GLOSSARY.md` 상태 값 표에 반영 |
| 관련 | [P-DOMAIN-MILEAGE-EXCHANGE-01](processes.md#p-domain-mileage-exchange-01), [F-DOMAIN-MILEAGE-EXCHANGE-01](flows.md#f-domain-mileage-exchange-01) |

#### V-DOMAIN-MILEAGE-SETTLE-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 마일리지·정산 |
| 발견 내용 | 수수료율이 요구 PDF에 없고 정책 문서에도 "수수료를 뺀다"만 있다 `[정책 §호스트 8]`, `[최종 §5-1]`. 요율·반올림·부가세 포함 여부가 없어 정산 금액을 계산할 수 없다. |
| 근거·출처 | `[p.18]`, `[정책 §호스트 8]`, `[최종 §5-1]` |
| 권장 조치 | 수수료율과 계산·반올림 규칙을 확정한다. |
| 담당 | 제품 |
| 상태 | resolved — 10%, 원 단위 내림, 부가세는 수수료에 포함. `fee = floor(price × 0.10)`, `net = price - fee` |
| 관련 | [P-DOMAIN-MILEAGE-SETTLE-01](processes.md#p-domain-mileage-settle-01) |

#### V-DOMAIN-SPACE-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 공간 |
| 발견 내용 | "이후 예약이 남아 있으면 공간을 삭제할 수 없다"만 있고 `[p.15]`, 남은 예약의 자동 취소·호스트 안내·삭제 요청 보류 상태가 정의되지 않았다. 블라인드된 공간의 삭제 가능 여부도 불명이다. |
| 근거·출처 | `[p.15]`, `[p.3]` |
| 권장 조치 | 예약 보유 공간의 삭제 차단과 대안(비노출·예약 종료 후 삭제)을 확정한다. |
| 담당 | 제품 |
| 상태 | resolved — 미종료 예약이 있으면 삭제 불가. `CLOSED`로 전환해 신규 예약만 차단하고 마지막 예약 종료 후 삭제. 블라인드 공간도 동일 |
| 관련 | [P-DOMAIN-SPACE-MANAGE-01](processes.md#p-domain-space-manage-01), [F-DOMAIN-SPACE-MANAGE-01](flows.md#f-domain-space-manage-01) |

#### V-STATUS-INSPECT-02

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 검수 |
| 발견 내용 | 검수 반려 후 재신청 가능 여부가 상충한다. 요구 PDF는 재신청 불가·삭제만 허용으로 읽히고 `[p.15]`, 정책 문서는 재신청을 허용한다 `[정책 §호스트 3-2]`. 반려 사유 수정 후 재검수 흐름이 화면에 없다. |
| 근거·출처 | `[p.15]`, `[정책 §호스트 3-2]`, `[기술 p.58]` |
| 권장 조치 | 재신청 허용 여부와 재신청 시 수정 가능 범위를 확정한다. |
| 담당 | 제품 |
| 상태 | resolved — 재신청 허용. 반려 사유 확인 후 공간 정보 전체 수정 가능, 재신청마다 `inspections` 새 행, 횟수 제한 없음 |
| 관련 | [P-PROC-INSPECT-01](processes.md#p-proc-inspect-01), [F-PROC-INSPECT-01](flows.md#f-proc-inspect-01) |

#### V-DOMAIN-PACKAGE-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 패키지 |
| 발견 내용 | "같은 날짜 겹침"의 판정 단위가 날짜인지 시간 구간인지 불명하고 `[p.16]`, 적용 날짜를 드래그 앤 드롭으로 배치할 때 중복 배치·과거 날짜 배치 규칙이 없다. |
| 근거·출처 | `[p.16]`, `[기술 p.47]`, `[기술 p.49]` |
| 권장 조치 | 겹침 판정 기준과 적용 가능 날짜 범위를 확정한다. |
| 담당 | 제품·개발 |
| 상태 | resolved — 겹침은 시간 구간으로 판정(DB exclusion constraint). 가격은 요일 + 시간대 규칙, 예약 가능 범위는 오늘~90일. 날짜별 예외 가격 없음 |
| 관련 | [P-DOMAIN-PACKAGE-01](processes.md#p-domain-package-01), [F-DOMAIN-PACKAGE-01](flows.md#f-domain-package-01) |

#### V-DOMAIN-RESERVATION-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 예약·환불 |
| 발견 내용 | 이용자 취소 시한은 "이용 전날 23:59까지, 당일 취소 불가"인데 `[p.9]`, 정책 문서는 호스트 취소에 100% 환불을 둔다 `[정책 §호스트 5-1]`. 호스트 취소의 시한·환불 비율·이용자 귀책과 호스트 귀책의 구분이 없다. |
| 근거·출처 | `[p.9]`, `[p.18]`, `[정책 §호스트 5-1]` |
| 권장 조치 | 취소 주체별 시한과 환불 비율표를 확정한다. |
| 담당 | 제품 |
| 상태 | resolved — 이용자: 7일 전 100%, 3일 전 70%, 1일 전 50%, 당일 0%. 호스트: 시한 없이 100% 환불, 취소 3회 누적 시 관리자 검토 |
| 관련 | [P-DOMAIN-RESERVATION-CANCEL-01](processes.md#p-domain-reservation-cancel-01), [F-DOMAIN-RESERVATION-CANCEL-01](flows.md#f-domain-reservation-cancel-01) |

#### V-DOMAIN-RESERVATION-02

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 예약·동시성 |
| 발견 내용 | "같은 패키지 날짜에는 결제 성공 예약 1건"이라는 불변식만 있고 `[p.9]`, 동시 결제 시 선점 실패를 어떤 방식(유니크 제약·비관적 락·조건부 UPDATE)으로 보장하는지, 실패 시 결제 롤백 순서가 정의되지 않았다. |
| 근거·출처 | `[p.9]`, `[기술 p.76]` |
| 권장 조치 | 동시성 제어 방식과 결제·예약 트랜잭션 경계를 확정한다. |
| 담당 | 개발 |
| 상태 | resolved — 단일 트랜잭션: 잔액 조건부 UPDATE → `HOLD` 원장 → 예약 INSERT. 겹침은 exclusion constraint가 최종 방어, 패배 요청 409 |
| 관련 | [P-DOMAIN-RESERVATION-01](processes.md#p-domain-reservation-01), [F-DOMAIN-RESERVATION-01](flows.md#f-domain-reservation-01) |

#### V-DOMAIN-PAYMENT-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 결제·환불 |
| 발견 내용 | 에스크로 보관금의 해제 조건(이용완료·취소·노쇼)과 부분 환불·수수료 차감 순서가 없다 `[p.16]`, `[p.18]`. 취소 시 에스크로 해제와 마일리지 환불이 어느 트랜잭션에서 일어나는지도 불명이다. |
| 근거·출처 | `[p.16]`, `[p.18]`, `[p.19]` |
| 권장 조치 | 에스크로 상태(보관·해제·반환)와 환불 계산식을 확정한다. |
| 담당 | 제품·개발 |
| 상태 | resolved — 결제 시 `HOLD`, 이용완료 시 `RELEASE`+`SETTLE`, 취소 시 계산된 금액만 `REFUND`. PG 승인은 트랜잭션 밖, 실패 시 보상 |
| 관련 | [P-DOMAIN-RESERVATION-CANCEL-01](processes.md#p-domain-reservation-cancel-01), [F-DOMAIN-RESERVATION-CANCEL-01](flows.md#f-domain-reservation-cancel-01) |

#### V-DOMAIN-REVIEW-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 리뷰 |
| 발견 내용 | 평균 별점 소수 자리가 첫째 자리 `[p.17]`와 둘째 자리 `[정책 §호스트 4-2]`로 갈린다. 반올림 규칙이 없어 목록·상세의 표시값이 달라질 수 있다. |
| 근거·출처 | `[p.17]`, `[정책 §호스트 4-2]` |
| 권장 조치 | 표시 자릿수와 반올림 규칙을 확정한다. |
| 담당 | 제품 |
| 상태 | open |
| 관련 | [P-DOMAIN-REVIEW-01](processes.md#p-domain-review-01), [F-DOMAIN-REVIEW-01](flows.md#f-domain-review-01) |

#### V-DOMAIN-REVIEW-02

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 리뷰 |
| 발견 내용 | 리뷰 작성 자격이 명시되지 않았다. "공간을 이용한 이용자"로만 쓰여 `[p.8]`, `[p.17]`, 예약완료만으로 가능한지 이용완료 후에만 가능한지, 예약 1건당 1개 제한이 있는지 알 수 없다. |
| 근거·출처 | `[p.8]`, `[p.17]` |
| 권장 조치 | 작성 자격과 예약당 리뷰 수 제한을 확정한다. |
| 담당 | 제품 |
| 상태 | resolved — COMPLETED 예약 1건당 1리뷰, 이용완료 후 30일 이내, 수정 불가, 삭제는 관리자 블라인드만 |
| 관련 | [P-DOMAIN-REVIEW-01](processes.md#p-domain-review-01) |

#### V-DOMAIN-QNA-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | Q&A |
| 발견 내용 | "신고 접수된 글은 수정·삭제 비활성" `[정책 §이용자 4-3]`과 "삭제 버튼으로 게시물 삭제" `[p.8]`가 충돌한다. 신고 처리 전 작성자가 삭제할 수 있는지, 삭제 시 답글이 남는지 불명이다. |
| 근거·출처 | `[p.8]`, `[p.17]`, `[정책 §이용자 4-3]` |
| 권장 조치 | 신고 접수 후 작성자 권한과 삭제 시 답글 처리 규칙을 확정한다. |
| 담당 | 제품 |
| 상태 | open |
| 관련 | [P-DOMAIN-QNA-01](processes.md#p-domain-qna-01), [F-DOMAIN-QNA-01](flows.md#f-domain-qna-01) |

#### V-DOMAIN-NOTICE-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 콘텐츠 |
| 발견 내용 | 공지사항 상단 고정의 최대 개수·고정 기간·정렬 우선순위가 없다 `[p.4]`. 고정 글이 1페이지에만 노출된다는 서술만 있어 페이지네이션과의 관계가 불명확하다. |
| 근거·출처 | `[p.4]`, `[p.18]` |
| 권장 조치 | 고정 개수·기간·정렬 규칙을 확정한다. |
| 담당 | 제품 |
| 상태 | open |
| 관련 | [P-DOMAIN-CONTENT-01](processes.md#p-domain-content-01), [F-DOMAIN-CONTENT-01](flows.md#f-domain-content-01) |

#### V-PROC-REPORT-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 신고 |
| 발견 내용 | "신고 3건 누적 시 블라인드"의 카운트 기준이 게시물 단위인지 신고자 단위인지, 기간 제한이 있는지, 반려된 신고가 카운트에서 빠지는지 없다 `[정책 §이용자 4-3]`. 신고 1건 처리 시 같은 게시물의 모든 신고를 함께 처리한다는 규칙과도 상호작용이 정의되지 않았다. |
| 근거·출처 | `[p.2]`, `[p.11]`, `[정책 §이용자 4-3]`, `[정책 §관리자 2-1]` |
| 권장 조치 | 자동 블라인드 임계값·카운트 단위·기간·반려 제외 여부를 확정한다. |
| 담당 | 제품 |
| 상태 | resolved — 자동 블라인드 없음. 신고는 관리자 판단으로만 처리하고, 관리자 화면에 대상별 신고 누적 수를 표시한다 |
| 관련 | [P-PROC-REPORT-01](processes.md#p-proc-report-01), [F-PROC-REPORT-01](flows.md#f-proc-report-01) |

#### V-INT-EMAIL-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 연동·메일 |
| 발견 내용 | 인증·비밀번호 재설정·검수 결과·신고 처리 결과 메일의 실패 처리와 재시도, 발송 이력 저장이 정의되지 않았다 `[기술 p.24]`. 메일 실패가 원 프로세스를 실패시키는지도 불명이다. |
| 근거·출처 | `[p.2]`, `[p.15]`, `[기술 p.24]` |
| 권장 조치 | 메일 발송을 비동기로 분리할지, 실패 시 재시도·이력 정책을 확정한다. |
| 담당 | 개발 |
| 상태 | open |
| 관련 | [P-ACCOUNT-SIGNUP-01](processes.md#p-account-signup-01), [P-PROC-INSPECT-01](processes.md#p-proc-inspect-01) |

#### V-SEC-PRIVACY-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | major |
| 영역 | 보안 |
| 발견 내용 | 비밀번호 규칙은 8~12자·대소문자·특수문자 4종으로 확정되어 있으나 `[p.6]`, `[p.12]`, 해시 알고리즘·솔트, 재설정 후 기존 세션 무효화 여부가 없다. 관리자 비밀번호에 같은 규칙을 적용하는지도 불명이다. |
| 근거·출처 | `[p.6]`, `[p.12]`, `[기술 p.28]` |
| 권장 조치 | 해시 방식과 세션 무효화 규칙을 구현 표준으로 정한다. |
| 담당 | 개발 |
| 상태 | open |
| 관련 | [P-AUTH-PASSWORD-RESET-01](processes.md#p-auth-password-reset-01), [F-AUTH-PASSWORD-RESET-01](flows.md#f-auth-password-reset-01) |

### Minor

#### V-TERM-MILEAGE-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | minor |
| 영역 | 용어 |
| 발견 내용 | "환전"이 좁은 의미(마일리지→계좌 지급)와 넓은 의미(이용자 충전, 호스트 정산까지 포함)로 혼용된다 `[p.3]`, `[p.5]`. 화면 문구·테이블명에서 의미가 갈릴 수 있다. |
| 근거·출처 | `[p.3]`, `[p.5]`, `[p.19]` |
| 권장 조치 | "환전"은 계좌 지급에만 쓰고 충전·정산은 별도 용어로 고정한다. |
| 담당 | 제품·문서 |
| 상태 | open |
| 관련 | [P-DOMAIN-MILEAGE-EXCHANGE-01](processes.md#p-domain-mileage-exchange-01) |

#### V-TERM-MESSENGER-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | minor |
| 영역 | 용어 |
| 발견 내용 | 같은 기능을 PIRI·DM·메신저·1:1문의로 부른다 `[p.10]`, `[p.19]`. 화면·DB·API 이름이 갈릴 수 있다. |
| 근거·출처 | `[p.10]`, `[p.19]` |
| 권장 조치 | 표시명은 PIRI, 코드명은 message로 고정한다. |
| 담당 | 제품·문서 |
| 상태 | resolved — 표시명 PIRI, 코드·테이블·API는 `message`/`messages` |
| 관련 | [P-DOMAIN-MESSAGE-01](processes.md#p-domain-message-01) |

#### V-UI-SEARCH-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | minor |
| 영역 | 화면·검색 |
| 발견 내용 | 검색 정렬 기준이 요구 PDF의 "평균별점 순" `[p.8]`과 정책 문서의 "베스트 순" `[정책 §이용자 3-5]`으로 갈린다. 정렬 키와 동률 처리 규칙이 없다. |
| 근거·출처 | `[p.8]`, `[정책 §이용자 3-5]` |
| 권장 조치 | 정렬 키·순서·동률 규칙을 확정하고 화면 라벨을 하나로 통일한다. |
| 담당 | 제품 |
| 상태 | open |
| 관련 | [P-DOMAIN-SPACE-MANAGE-01](processes.md#p-domain-space-manage-01) |

#### V-UI-PAGING-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | minor |
| 영역 | 화면 |
| 발견 내용 | "페이지당 10건, 페이지 번호 최대 5개"가 공지사항에만 명시되어 있고 `[p.4]`, 검색·리뷰·Q&A·신고 목록에도 같은 규칙을 적용하는지 불명이다. |
| 근거·출처 | `[p.4]`, `[p.8]`, `[p.17]` |
| 권장 조치 | 화면별 페이지 크기와 페이지 번호 개수를 표로 확정한다. |
| 담당 | 제품 |
| 상태 | open |
| 관련 | [P-DOMAIN-CONTENT-01](processes.md#p-domain-content-01) |

### Question

#### V-DOMAIN-SPACE-02

| 항목 | 내용 |
| --- | --- |
| 심각도 | question |
| 영역 | 공간·용어 |
| 발견 내용 | 공간유형 목록이 "파티룸·루프탑·클럽·브라이덜샤워 등"으로 닫히지 않아 `[p.7]`, `[p.16]`, 코드성 데이터의 전체 목록을 확정할 수 없다. |
| 근거·출처 | `[p.7]`, `[p.16]`, `[정책 §이용자 3-2]` |
| 권장 조치 | 유형 전체 목록과 추가·변경 권한을 확정한다. |
| 담당 | 제품 |
| 상태 | resolved — 6종 고정(PARTY_ROOM·EMPTY_SPACE·ROOFTOP·BRIDAL_SHOWER·CLUB·WORKSHOP), 추가는 마이그레이션으로만 |
| 관련 | [P-DOMAIN-SPACE-REGISTER-01](processes.md#p-domain-space-register-01), [F-DOMAIN-SPACE-REGISTER-01](flows.md#f-domain-space-register-01) |

#### V-REPORT-OPERATION-01

| 항목 | 내용 |
| --- | --- |
| 심각도 | question |
| 영역 | 리포트·범위 |
| 발견 내용 | 운영 보고서(거래액·예약·회원·게시물 집계)가 정책 문서에만 있고 `[정책 §관리자 7-1]`, 요구 PDF 관리자 장에는 요구가 없다 `[p.2]`. 지표 정의·집계 기간·화면이 없어 범위 포함 여부를 먼저 정해야 한다. |
| 근거·출처 | `[p.2]`, `[정책 §관리자 7-1]` |
| 권장 조치 | 이번 범위에 포함할지 결정하고, 포함하면 지표 정의서를 별도로 만든다. |
| 담당 | 제품 |
| 상태 | open |
| 관련 | [index](index.md) |

## 추적성

| 문서 | 추적 단위 | 원문 근거 |
| --- | --- | --- |
| [glossary.md](glossary.md) | 용어 46개 (주 표 42, 정책 문서 전용 4) | 각 행에 `[p.N]`·`[정책 §...]`·`[최종 §...]`·`[기술 p.N]` 병기 |
| [domain.md](domain.md) | 엔티티·값 객체·상태 전이·불변식 14개 | 모든 행에 `[p.N]` |
| [processes.md](processes.md) | 프로세스 19개, 미정의 항목 1개 | 각 프로세스 `출처` 행 |
| [flows.md](flows.md) | 플로우 19개 | 프로세스 ID 1:1 대응 |

## 링크 검사

- 검사 명령: `python3 ~/.agents/skills/gorani-spec-analysis/scripts/check_links.py docs/spec/lookation`
- 검사 대상: 이 디렉터리와 `source/` 하위 markdown의 모든 상대 링크·앵커
- 결과: [링크 검사 결과](#링크-검사-결과)

### 링크 검사 결과

`check_links.py` 실행 결과 깨진 링크 0건.

## 미해결 질문

| 번호 | 질문 | 관련 finding | 결정권자 |
| --- | --- | --- | --- |
| 1 | 블랙리스트는 영구인가, 차수별 기간제인가? | [V-ACCOUNT-BLACKLIST-01](validation.md#v-account-blacklist-01) | 제품 |
| 2 | 블랙리스트 회원이 로그인해 쓸 수 있는 기능은 정확히 무엇인가? | [V-PERM-BLACKLIST-01](validation.md#v-perm-blacklist-01), [V-AUTH-BLACKLIST-01](validation.md#v-auth-blacklist-01) | 제품 |
| 3 | 예약 상태는 3단계인가 4단계인가? 이용완료는 언제 판정하는가? | [V-STATUS-RESERVATION-01](validation.md#v-status-reservation-01) | 제품·개발 |
| 4 | 검수 상태 코드표를 하나로 확정할 수 있는가? | [V-STATUS-INSPECT-01](validation.md#v-status-inspect-01) | 제품 |
| 5 | 정산은 자동인가 호스트 신청인가? 수수료율은 얼마인가? | [V-PROC-SETTLE-01](validation.md#v-proc-settle-01), [V-DOMAIN-MILEAGE-SETTLE-01](validation.md#v-domain-mileage-settle-01) | 제품 |
| 6 | 승인예약(호스트 승인 후 결제)을 이번 범위에 포함하는가? | [V-INT-PAYMENT-01](validation.md#v-int-payment-01) | 제품 |
| 7 | 탈퇴 시 보존·삭제 항목과 보존 기간은? | [V-DATA-RETENTION-01](validation.md#v-data-retention-01) | 제품 |
| 8 | 블랙리스트 전환 시 잔여 마일리지는 자동 환불인가? | [V-DOMAIN-MILEAGE-REFUND-01](validation.md#v-domain-mileage-refund-01) | 제품 |
| 9 | 운영 보고서를 이번 범위에 포함하는가? | [V-REPORT-OPERATION-01](validation.md#v-report-operation-01) | 제품 |
| 10 | 공간유형 전체 목록은? | [V-DOMAIN-SPACE-02](validation.md#v-domain-space-02) | 제품 |
