# 프로세스 정의

> 문서 지도: [index](index.md) · [glossary](glossary.md) · [domain](domain.md) · [processes](processes.md) · [flows](flows.md) · [validation](validation.md) · [source](source/source.md) · [source.plain](source/source.plain.md)

## 읽는 법

- 모든 프로세스는 TO-BE다. 원문에 AS-IS 절차가 따로 없고, "정책 고려사항" 메모만 있다 `[정책 §정책 고려]`.
- `출처`는 요구 PDF 쪽 번호 `[p.N]`을 우선 적고, 정책·기술문서 근거를 덧붙였다.
- 각 프로세스는 같은 ID의 플로우와 1:1로 대응한다.

## P-AUTH-LOGIN-01

**로그인·로그아웃·세션 유지 (이용자·호스트·관리자)**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-AUTH-LOGIN-01 |
| 이름 | 로그인·로그아웃·세션 유지 |
| 구분 | TO-BE |
| 액터 | 이용자, 호스트, 관리자, 시스템 |
| 트리거 | 로그인 페이지에서 이메일·비밀번호 제출, 또는 로그아웃 버튼 클릭 |
| 사전 조건 | 가입·이메일 인증 완료(회원), 관리자는 DB에 계정 존재 |
| 입력 | 이메일, 비밀번호 |
| 단계 | 1. 자격 증명 검증 2. 계정 상태 확인(정상·블랙리스트·탈퇴) 3. 역할 판별 후 세션 발급 4. 역할별 메뉴 노출 |
| 결정 | 자격 증명 불일치 / 블랙리스트 / 탈퇴 회원 / 30분 무요청 |
| 예외 | 블랙리스트면 안내 팝업 후 로그인 거부 또는 제한 로그인(문서 상충). 세션 만료 시 로그인 페이지로 이동 |
| 사후 조건 | 세션에 회원코드와 역할 구분자가 저장되고, 역할별 메뉴가 활성화된다 |
| 관련 용어 | [회원고유코드](glossary.md#용어), [블랙리스트](glossary.md#용어) |
| 출처 | `[p.2]`, `[p.6]`, `[p.12]`, `[정책 §이용자 1-2]`, `[기술 p.31]` |
| 관련 플로우 | [F-AUTH-LOGIN-01](flows.md#f-auth-login-01) |
| 관련 finding | [V-AUTH-BLACKLIST-01](validation.md#v-auth-blacklist-01), [V-AUTH-SESSION-01](validation.md#v-auth-session-01) |

## P-AUTH-PASSWORD-RESET-01

**비밀번호 찾기·재설정**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-AUTH-PASSWORD-RESET-01 |
| 이름 | 비밀번호 찾기·재설정 |
| 구분 | TO-BE |
| 액터 | 이용자, 호스트, 시스템 |
| 트리거 | 로그인 화면의 "비밀번호 찾기" 링크 클릭 |
| 사전 조건 | 가입된 이메일 보유 |
| 입력 | 이메일, 새 비밀번호, 새 비밀번호 확인 |
| 단계 | 1. 이메일 입력 2. 재설정 링크 메일 발송 3. 링크로 재설정 화면 진입 4. 새 비밀번호 검증·저장 5. 로그인 화면 복귀 |
| 결정 | 미가입 이메일 / 링크 만료 / 비밀번호 규칙 위반 / 확인 불일치 |
| 예외 | 메일 발송 실패 시 안내. 링크 재사용 불가 |
| 사후 조건 | 비밀번호가 갱신되고 기존 세션은 무효화된다(무효화 정책은 미기재) |
| 관련 용어 | [Password](domain.md#회원) |
| 출처 | `[p.6]`, `[p.12]`, `[기술 p.28]` |
| 관련 플로우 | [F-AUTH-PASSWORD-RESET-01](flows.md#f-auth-password-reset-01) |
| 관련 finding | [V-SEC-PRIVACY-01](validation.md#v-sec-privacy-01) |

## P-ACCOUNT-SIGNUP-01

**회원가입·이메일 인증**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-ACCOUNT-SIGNUP-01 |
| 이름 | 회원가입·이메일 인증 |
| 구분 | TO-BE |
| 액터 | 이용자, 호스트, 시스템 |
| 트리거 | 회원가입 버튼 클릭 후 양식 제출 |
| 사전 조건 | 없음(비회원) |
| 입력 | 이메일, 비밀번호·확인, 닉네임, 연락처, (호스트) 이름, 동의 체크 |
| 단계 | 1. 필수 항목·정규식 검증 2. 이메일·닉네임 중복 검사 3. 인증 메일 발송 4. 메일 링크 확인 5. 계정 생성·회원고유코드 부여 6. 자동 로그인 |
| 결정 | 중복 이메일 / 중복 닉네임 / 형식 위반 / 인증 미완료 / 동의 미체크 |
| 예외 | 인증 메일 미확인 시 가입 미완료. 형식 위반 시 가입 완료 페이지 진입 불가 |
| 사후 조건 | 계정과 가입일자가 생성되고 세션이 발급된다 |
| 관련 용어 | [이용자](glossary.md#용어), [호스트](glossary.md#용어) |
| 출처 | `[p.6]`, `[p.12]`, `[정책 §이용자 1-1]`, `[기술 p.26]` |
| 관련 플로우 | [F-ACCOUNT-SIGNUP-01](flows.md#f-account-signup-01) |
| 관련 finding | [V-ACCOUNT-SIGNUP-01](validation.md#v-account-signup-01) |

## P-ACCOUNT-WITHDRAW-01

**회원 탈퇴·데이터 분리보관**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-ACCOUNT-WITHDRAW-01 |
| 이름 | 회원 탈퇴·데이터 분리보관 |
| 구분 | TO-BE |
| 액터 | 이용자, 호스트, 시스템 |
| 트리거 | 마이페이지의 회원 탈퇴 버튼 클릭 |
| 사전 조건 | 로그인, 비밀번호 재확인 |
| 입력 | 비밀번호, 탈퇴 의사 확인 |
| 단계 | 1. 탈퇴 가능 조건 검사 2. 비밀번호 재확인 3. 탈퇴 의사 재확인 4. 식별 정보 삭제·분리보관 5. 게시물 비노출 처리 6. 세션 종료 |
| 결정 | 예약 보유 / 잔여 마일리지 존재 / 비밀번호 불일치 / 의사 철회 |
| 예외 | 잔여 마일리지가 있으면 환전 안내 후 중단. 예약 보유 시 탈퇴 불가(정책)이나 요구 PDF는 호스트만 제한한다 |
| 사후 조건 | 탈퇴회원으로 분리 보관되고, 작성 글은 "알 수 없는 회원"으로 표시되며 예약 기록은 유지된다 |
| 관련 용어 | [탈퇴회원](glossary.md#용어) |
| 출처 | `[p.10]`, `[p.13]`, `[정책 §이용자 1-4]`, `[기술 p.69]` |
| 관련 플로우 | [F-ACCOUNT-WITHDRAW-01](flows.md#f-account-withdraw-01) |
| 관련 finding | [V-ACCOUNT-WITHDRAW-01](validation.md#v-account-withdraw-01), [V-DATA-RETENTION-01](validation.md#v-data-retention-01) |

## P-ACCOUNT-BLACKLIST-01

**블랙리스트 지정·해제**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-ACCOUNT-BLACKLIST-01 |
| 이름 | 블랙리스트 지정·해제 |
| 구분 | TO-BE |
| 액터 | 관리자, 시스템, 회원 |
| 트리거 | 신고 인정 누적 또는 관리자의 직접 지정 판단 |
| 사전 조건 | 신고 처리 이력 또는 관리자 판단 |
| 입력 | 대상 회원, 지정 사유, 제재 기간(정책), 처리 결과 |
| 단계 | 1. 신고·경고 이력 확인 2. 제재 여부 판단 3. 블랙리스트 등록·기간 설정 4. 마일리지 자동환불 5. 세션 종료·로그인 제한 6. 팝업 안내(해제일·사유) |
| 결정 | 경고 3회 도달 / 예약 잔여 여부 / 기간 설정 / 복구 요청 |
| 예외 | 예약이 남아 있으면 이용 종료까지 제한 로그인 허용. 계좌가 없으면 자동환불 불가 → 계좌 등록 유도 |
| 사후 조건 | 계정이 블랙리스트 상태가 되고, 작성 글 닉네임이 익명화되며, 복구 시 정상 상태로 돌아간다 |
| 관련 용어 | [블랙리스트](glossary.md#용어), [경고](glossary.md#용어) |
| 출처 | `[p.3]`, `[p.11]`, `[p.20]`, `[정책 §관리자 3-2]`, `[기술 p.11]` |
| 관련 플로우 | [F-ACCOUNT-BLACKLIST-01](flows.md#f-account-blacklist-01) |
| 관련 finding | [V-ACCOUNT-BLACKLIST-01](validation.md#v-account-blacklist-01), [V-PERM-BLACKLIST-01](validation.md#v-perm-blacklist-01), [V-DOMAIN-MILEAGE-REFUND-01](validation.md#v-domain-mileage-refund-01) |

## P-DOMAIN-SPACE-REGISTER-01

**공간 등록**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-SPACE-REGISTER-01 |
| 이름 | 공간 등록 |
| 구분 | TO-BE |
| 액터 | 호스트, 시스템 |
| 트리거 | 호스트센터의 공간 등록 버튼 클릭 |
| 사전 조건 | 호스트 로그인, 사업자 정보 준비, 계좌 1개 이상 |
| 입력 | 기본 정보(유형·이름·소개·시설·주의사항·대표이미지·주소), 연락처, 사업자 정보, 상세 정보(이미지·수용인원·웹사이트), 이용 안내, 패키지 |
| 단계 | 1. 기본 정보 입력 2. 연락처 입력 3. 사업자 정보 입력 4. 상세 정보 입력 5. 이용 안내 입력 6. 패키지 1건 이상 등록 7. 검수 신청 |
| 결정 | 필수 항목 누락 / 이미지 규격 위반 / 패키지 미등록 / 주소 API 실패 |
| 예외 | 패키지가 없으면 등록 완료 불가 안내 후 패키지 등록 페이지로 이동. 이미지 용량·해상도 초과 시 재첨부 |
| 사후 조건 | 공간이 검수 대기 상태로 저장되고 호스트 화면에서만 조회된다 |
| 관련 용어 | [공간](glossary.md#용어), [패키지](glossary.md#용어) |
| 출처 | `[p.14]`, `[p.16]`, `[정책 §호스트 3-1]`, `[기술 p.40]` |
| 관련 플로우 | [F-DOMAIN-SPACE-REGISTER-01](flows.md#f-domain-space-register-01) |
| 관련 finding | [V-DOMAIN-SPACE-02](validation.md#v-domain-space-02), [V-DOMAIN-PACKAGE-01](validation.md#v-domain-package-01) |

## P-PROC-INSPECT-01

**검수 신청·승인·반려**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-PROC-INSPECT-01 |
| 이름 | 검수 신청·승인·반려 |
| 구분 | TO-BE |
| 액터 | 호스트, 관리자, 시스템 |
| 트리거 | 호스트의 검수 신청, 관리자의 검수 목록 조회 |
| 사전 조건 | 공간 필수 정보 입력 완료, 호스트 로그인, 관리자 로그인 |
| 입력 | 검수 신청 건, 처리 결과(승인·반려), 반려 사유 |
| 단계 | 1. 신청 접수(검수 대기) 2. 관리자 검수 목록 확인 3. 상세 검토 4. 승인 또는 반려 5. 결과 저장 6. 게시·메일 발송 |
| 결정 | 필수 정보 충족 / 사업자 정보 적법 / 반려 사유 입력 여부 / 기각 후 재신청 허용 여부 |
| 예외 | 반려 시 재신청 불가(요구 PDF) 또는 가능(정책). 검수 대기 중 호스트 수정·삭제 요청은 거부 |
| 사후 조건 | 승인 시 공간이 게시되어 검색에 노출되고 호스트에게 알림 메일이 발송된다. 반려 시 삭제만 가능 |
| 관련 용어 | [검수](glossary.md#용어), [검수 대기](glossary.md#용어) |
| 출처 | `[p.3]`, `[p.4]`, `[p.14]`, `[p.15]`, `[기술 p.57]`, `[기술 p.58]` |
| 관련 플로우 | [F-PROC-INSPECT-01](flows.md#f-proc-inspect-01) |
| 관련 finding | [V-STATUS-INSPECT-01](validation.md#v-status-inspect-01), [V-STATUS-INSPECT-02](validation.md#v-status-inspect-02) |

## P-DOMAIN-SPACE-MANAGE-01

**공간 조회·수정·삭제**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-SPACE-MANAGE-01 |
| 이름 | 공간 조회·수정·삭제 |
| 구분 | TO-BE |
| 액터 | 호스트, 시스템 |
| 트리거 | 공간 관리 페이지 진입, 수정·삭제 버튼 클릭 |
| 사전 조건 | 호스트 로그인, 본인 소유 공간 |
| 입력 | 수정할 항목, 삭제 확인 |
| 단계 | 1. 등록 공간 목록 조회(검수상태·등록일·공간명·대표이미지) 2. 수정 화면 진입 3. 수정 가능 항목만 편집 4. 저장 5. 삭제 시 이후 예약 확인 6. 삭제 처리 |
| 결정 | 검수 대기 여부 / 수정 불가 항목 / 이후 예약 존재 / 삭제 확인 |
| 예외 | 검수 대기 중에는 수정·삭제 불가. 이후 예약이 있으면 삭제 거부. 기각 상태는 삭제만 허용 |
| 사후 조건 | 수정 내용이 저장되거나(검수 완료 공간은 재검수 없음) 공간과 종속 데이터가 삭제되어 검색에서 사라진다 |
| 관련 용어 | [공간](glossary.md#용어) |
| 출처 | `[p.15]`, `[p.3]`, `[정책 §호스트 4-1]`, `[기술 p.39]` |
| 관련 플로우 | [F-DOMAIN-SPACE-MANAGE-01](flows.md#f-domain-space-manage-01) |
| 관련 finding | [V-DOMAIN-SPACE-01](validation.md#v-domain-space-01) |

## P-DOMAIN-PACKAGE-01

**패키지 등록·적용**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-PACKAGE-01 |
| 이름 | 패키지 등록·적용 |
| 구분 | TO-BE |
| 액터 | 호스트, 시스템 |
| 트리거 | 패키지 등록 또는 예약 등록(패키지 적용) 버튼 클릭 |
| 사전 조건 | 검수 승인된 공간, 호스트 로그인 |
| 입력 | 패키지명, 시작·종료시간, 가격, 적용 날짜 |
| 단계 | 1. 패키지 정보 입력 2. 시간·가격 검증 3. 패키지 저장 4. 달력에서 적용 날짜 선택 5. 드래그 앤 드롭으로 배치 6. 겹침 검사 후 저장 |
| 결정 | 3시간 미만·34시간 초과 / 가격 범위 위반 / 같은 날짜 겹침 / 검수 미승인 |
| 예외 | 검수 승인 전이면 등록·적용 불가. 겹치는 시간대는 저장 거부. 적용 없는 날짜는 휴무 처리 |
| 사후 조건 | 적용된 날짜에만 예약이 가능하고, 패키지가 적용된 공간만 검색에 노출된다 |
| 관련 용어 | [패키지](glossary.md#용어), [패키지 적용](glossary.md#용어) |
| 출처 | `[p.16]`, `[정책 §호스트 3-1-3]`, `[기술 p.47]`, `[기술 p.49]` |
| 관련 플로우 | [F-DOMAIN-PACKAGE-01](flows.md#f-domain-package-01) |
| 관련 finding | [V-DOMAIN-PACKAGE-01](validation.md#v-domain-package-01) |

## P-DOMAIN-RESERVATION-01

**예약 생성·마일리지 결제**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-RESERVATION-01 |
| 이름 | 예약 생성·마일리지 결제 |
| 구분 | TO-BE |
| 액터 | 이용자, 시스템 |
| 트리거 | 공간 상세에서 날짜·패키지 선택 후 예약 버튼 클릭 |
| 사전 조건 | 이용자 로그인, 패키지 적용 날짜, 3개월 이내 |
| 입력 | 예약자명, 연락처, 예약일자, 인원수, 요청사항, 마일리지 결제 |
| 단계 | 1. 날짜·패키지 선택 2. 예약 정보 입력 3. 마일리지 잔액 확인 4. 결제 5. 예약 확정 6. 예약 목록에 표시 |
| 결정 | 잔액 부족 / 동시 예약 선점 / 3개월 초과 / 정원 초과 / 당일 예약 시한 초과 |
| 예외 | 잔액 부족 시 충전 페이지로 이동. 선점 패배 시 "이미 예약된 패키지" 안내 후 상세로 복귀. 결제 실패 시 예약 미생성 |
| 사후 조건 | 예약완료 상태의 예약 1건과 결제 1건이 생성되고 해당 패키지 날짜는 재예약 불가 |
| 관련 용어 | [예약](glossary.md#용어), [예약완료](glossary.md#용어), [에스크로](glossary.md#용어) |
| 출처 | `[p.9]`, `[p.16]`, `[정책 §이용자 5-1]`, `[기술 p.76]` |
| 관련 플로우 | [F-DOMAIN-RESERVATION-01](flows.md#f-domain-reservation-01) |
| 관련 finding | [V-DOMAIN-RESERVATION-02](validation.md#v-domain-reservation-02), [V-INT-PAYMENT-01](validation.md#v-int-payment-01) |

## P-DOMAIN-RESERVATION-CANCEL-01

**예약 취소·환불**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-RESERVATION-CANCEL-01 |
| 이름 | 예약 취소·환불 |
| 구분 | TO-BE |
| 액터 | 이용자, 호스트, 시스템 |
| 트리거 | 예약 목록에서 취소 버튼 클릭 |
| 사전 조건 | 예약완료 상태, 취소 가능 시한 이내 |
| 입력 | 취소 사유(100자 이내), 취소 주체 |
| 단계 | 1. 취소 가능 여부 확인 2. 취소 사유 입력 3. 환불 비율 계산 4. 마일리지 환불 5. 예약 상태 변경 6. 상대방·관리자 화면에 사유 노출 |
| 결정 | 이용 전날 23:59 경과 / 당일 취소 / 호스트 취소 / 환불 비율 구간 |
| 예외 | 시한 경과 시 취소 불가. 당일 취소는 이용자 불가·호스트는 100% 환불(정책)로 상충 |
| 사후 조건 | 예약이 취소 상태가 되고 환불 내역이 생성되며, 해당 날짜가 다시 예약 가능해진다 |
| 관련 용어 | [예약취소](glossary.md#용어), [환불](glossary.md#용어) |
| 출처 | `[p.9]`, `[p.18]`, `[정책 §호스트 5-1]` |
| 관련 플로우 | [F-DOMAIN-RESERVATION-CANCEL-01](flows.md#f-domain-reservation-cancel-01) |
| 관련 finding | [V-DOMAIN-RESERVATION-01](validation.md#v-domain-reservation-01), [V-DOMAIN-PAYMENT-01](validation.md#v-domain-payment-01) |

## P-DOMAIN-MILEAGE-CHARGE-01

**마일리지 충전 신청·승인**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-MILEAGE-CHARGE-01 |
| 이름 | 마일리지 충전 신청·승인 |
| 구분 | TO-BE |
| 액터 | 이용자, 관리자, 시스템 |
| 트리거 | 마일리지 메뉴 또는 결제 중 잔액 부족 안내에서 충전 선택 |
| 사전 조건 | 로그인, 계좌 1개 이상 등록, 진행 중인 충전 신청 없음 |
| 입력 | 충전 금액(정책: 최소 10,000원, 최대 1,000,000원, 1만원 단위), 입금 계좌 |
| 단계 | 1. 금액 선택 2. 계좌 확인 3. 신청 생성 4. 이용자가 계좌 이체 5. 관리자가 예금주·금액 확인 6. 승인 또는 실패 처리 7. 마일리지 반영 |
| 결정 | 계좌 없음 / 중복 신청 / 대기 시간 초과 / 입금액 불일치 |
| 예외 | 대기 시간이 지나면 승인 버튼 비활성. 금액 불일치 시 충전실패. 실패 후 입금분 환불은 사이트에 기록되지 않는다 |
| 사후 조건 | 승인 시 마일리지 잔액과 충전 내역이 증가하고 신청이 완료 상태가 된다 |
| 관련 용어 | [충전](glossary.md#용어), [마일리지](glossary.md#용어) |
| 출처 | `[p.5]`, `[p.10]`, `[정책 §이용자 9]`, `[기술 p.104]` |
| 관련 플로우 | [F-DOMAIN-MILEAGE-CHARGE-01](flows.md#f-domain-mileage-charge-01) |
| 관련 finding | [V-DOMAIN-MILEAGE-CHARGE-01](validation.md#v-domain-mileage-charge-01), [V-DOMAIN-MILEAGE-CHARGE-02](validation.md#v-domain-mileage-charge-02), [V-STATUS-MILEAGE-01](validation.md#v-status-mileage-01) |

## P-DOMAIN-MILEAGE-EXCHANGE-01

**마일리지 환전**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-MILEAGE-EXCHANGE-01 |
| 이름 | 마일리지 환전 |
| 구분 | TO-BE |
| 액터 | 이용자, 호스트, 관리자, 시스템 |
| 트리거 | 마일리지 화면에서 환전 신청 |
| 사전 조건 | 로그인, 계좌 등록 완료, 보유 마일리지 ≥ 신청 금액 |
| 입력 | 환전 금액(정책: 최소 1,000원), 수령 계좌 |
| 단계 | 1. 계좌 선택 2. 금액 입력 3. 신청 저장 4. 관리자가 계좌 입금 5. 완료 처리 6. 내역 리스트 반영 |
| 결정 | 계좌 없음 / 잔액 초과 / 최소 금액 미달 |
| 예외 | 계좌가 없으면 계좌 등록 페이지로 이동. 관리자 입금 전에는 완료로 표시되지 않는다 |
| 사후 조건 | 마일리지가 차감되고 환전 내역이 남는다(상태 컬럼은 관리하지 않는다는 서술과 상충) |
| 관련 용어 | [환전](glossary.md#용어), [계좌정보](glossary.md#용어) |
| 출처 | `[p.5]`, `[p.19]`, `[정책 §호스트 8]` |
| 관련 플로우 | [F-DOMAIN-MILEAGE-EXCHANGE-01](flows.md#f-domain-mileage-exchange-01) |
| 관련 finding | [V-STATUS-MILEAGE-01](validation.md#v-status-mileage-01), [V-DOMAIN-MILEAGE-REFUND-01](validation.md#v-domain-mileage-refund-01) |

## P-DOMAIN-MILEAGE-SETTLE-01

**정산**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-MILEAGE-SETTLE-01 |
| 이름 | 정산 |
| 구분 | TO-BE |
| 액터 | 시스템, 호스트, 관리자 |
| 트리거 | 예약이 이용완료로 전이 |
| 사전 조건 | 예약완료 상태였고 체크아웃 시각 경과, 취소되지 않음 |
| 입력 | 예약 금액, 수수료율 |
| 단계 | 1. 이용완료 판정 2. 수수료 계산 3. 호스트 마일리지 증가 4. 정산 내역 기록 5. (정책) 호스트가 정산 버튼 클릭 |
| 결정 | 자동 정산 vs 호스트 버튼 / 수수료율 / 취소 후 정산 |
| 예외 | 취소된 예약은 정산 대상이 아니다. 정산 후 취소가 발생하면 회수 규칙이 없다 |
| 사후 조건 | 호스트 잔액과 정산 내역이 증가하고 예약에 정산일자가 기록된다 |
| 관련 용어 | [정산](glossary.md#용어), [수수료](glossary.md#용어) |
| 출처 | `[p.18]`, `[p.19]`, `[정책 §호스트 8]`, `[최종 §5-1]` |
| 관련 플로우 | [F-DOMAIN-MILEAGE-SETTLE-01](flows.md#f-domain-mileage-settle-01) |
| 관련 finding | [V-PROC-SETTLE-01](validation.md#v-proc-settle-01), [V-DOMAIN-MILEAGE-SETTLE-01](validation.md#v-domain-mileage-settle-01) |

## P-DOMAIN-REVIEW-01

**리뷰 작성·답변·신고**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-REVIEW-01 |
| 이름 | 리뷰 작성·답변·신고 |
| 구분 | TO-BE |
| 액터 | 이용자, 호스트, 관리자, 시스템 |
| 트리거 | 공간 상세의 리뷰 작성 버튼, 호스트의 답글·신고 버튼 |
| 사전 조건 | 로그인, (추론) 해당 공간 이용완료 |
| 입력 | 별점 1~5, 내용, 사진 여러 장, 답글 내용, 신고 사유 |
| 단계 | 1. 리뷰 작성 2. 저장·노출 3. 호스트 답글 1개 등록 4. 신고 접수 5. 신고 3건 누적 시 블라인드 6. 관리자 처리 |
| 결정 | 작성 자격 / 사진 개수·용량 / 신고 누적 수 / 이미 신고한 사용자 |
| 예외 | 신고된 글은 수정·삭제 비활성. 작성자가 탈퇴·블랙리스트면 닉네임 익명화, 내용은 유지 |
| 사후 조건 | 리뷰·답글이 노출되거나 블라인드되고, 평균 별점과 리뷰 수가 갱신된다 |
| 관련 용어 | [리뷰](glossary.md#용어), [별점](glossary.md#용어), [답글](glossary.md#용어) |
| 출처 | `[p.8]`, `[p.17]`, `[정책 §이용자 4-3]`, `[기술 p.90]` |
| 관련 플로우 | [F-DOMAIN-REVIEW-01](flows.md#f-domain-review-01) |
| 관련 finding | [V-DOMAIN-REVIEW-01](validation.md#v-domain-review-01), [V-DOMAIN-REVIEW-02](validation.md#v-domain-review-02) |

## P-DOMAIN-QNA-01

**Q&A 작성·답변**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-QNA-01 |
| 이름 | Q&A 작성·답변 |
| 구분 | TO-BE |
| 액터 | 이용자, 호스트, 시스템 |
| 트리거 | 공간 상세의 Q&A 작성 버튼 |
| 사전 조건 | 로그인(이용자) |
| 입력 | 문의 내용 |
| 단계 | 1. 문의 작성 2. 저장·노출 3. 호스트 확인 4. 답글 1개 등록 5. 이용자에게 노출 |
| 결정 | 로그인 여부 / 신고된 글 여부 / 답글 중복 |
| 예외 | 신고 접수된 글은 수정·삭제 비활성. 작성자가 탈퇴·블랙리스트면 익명 표시 |
| 사후 조건 | Q&A와 답글이 공간 상세와 마이페이지 목록에 반영된다 |
| 관련 용어 | [Q&A](glossary.md#용어), [답글](glossary.md#용어) |
| 출처 | `[p.8]`, `[p.17]`, `[정책 §이용자 4-3]`, `[기술 p.87]` |
| 관련 플로우 | [F-DOMAIN-QNA-01](flows.md#f-domain-qna-01) |
| 관련 finding | [V-DOMAIN-QNA-01](validation.md#v-domain-qna-01) |

## P-PROC-REPORT-01

**신고 접수·처리**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-PROC-REPORT-01 |
| 이름 | 신고 접수·처리 |
| 구분 | TO-BE |
| 액터 | 이용자, 호스트, 관리자, 시스템 |
| 트리거 | 공간 상세·예약 목록·메시지 화면의 신고 버튼 |
| 사전 조건 | 로그인, 신고 대상과의 관계(공간 이용·예약·대화) |
| 입력 | 신고 유형, 신고 사유, 첨부 사진(캡처) |
| 단계 | 1. 신고 양식 작성 2. 접수 3. 관리자 목록 확인 4. 게시물·내용 검토 5. 인정 또는 반려 6. 처리 사유 입력 7. 신고자·피신고자 메일 발송 8. 게시물·계정 제재 반영 |
| 결정 | 신고 유형 선택 여부 / 사유 작성 여부 / 위반 판단 / 경고 누적 수 |
| 예외 | 허위 신고는 반려되고 경고가 누적될 수 있다. 게시물 1건 처리 시 관련 신고 전체가 함께 처리된다 |
| 사후 조건 | 처리 결과가 저장되고, 인정 시 블라인드·경고·블랙리스트·공간 이용 제한이 파생된다 |
| 관련 용어 | [신고](glossary.md#용어), [신고 처리](glossary.md#용어), [블라인드](glossary.md#용어) |
| 출처 | `[p.2]`, `[p.11]`, `[p.20]`, `[정책 §관리자 2-1]`, `[기술 p.5]` |
| 관련 플로우 | [F-PROC-REPORT-01](flows.md#f-proc-report-01) |
| 관련 finding | [V-PROC-REPORT-01](validation.md#v-proc-report-01) |

## P-DOMAIN-MESSAGE-01

**PIRI 1:1 문의**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-MESSAGE-01 |
| 이름 | PIRI 1:1 문의 |
| 구분 | TO-BE |
| 액터 | 이용자, 호스트, 시스템 |
| 트리거 | 예약 목록의 메신저 버튼 클릭 |
| 사전 조건 | 예약완료 상태, 이용완료 전 |
| 입력 | 텍스트 메시지, 이미지 첨부 |
| 단계 | 1. 대화방 진입 2. 메시지 전송 3. 상대 확인·답변 4. 대화 목록에 최종 시각 갱신 5. 이용완료 시 전송 비활성 |
| 결정 | 예약 상태 / 이용완료 여부 / 신고 필요 여부 |
| 예외 | 이용완료 이후 전송 불가. 호스트 탈퇴 시 대화 내역 비노출. 부적절 메시지는 캡처 첨부 후 신고 |
| 사후 조건 | 대화 내역이 저장되고, 예약 단위로 문의 이력이 남는다 |
| 관련 용어 | [PIRI](glossary.md#용어) |
| 출처 | `[p.10]`, `[p.18]`, `[p.19]`, `[정책 §호스트 7]` |
| 관련 플로우 | [F-DOMAIN-MESSAGE-01](flows.md#f-domain-message-01) |
| 관련 finding | [V-TERM-MESSENGER-01](validation.md#v-term-messenger-01) |

## P-DOMAIN-CONTENT-01

**공지사항·도움말 운영**

| 항목 | 내용 |
| --- | --- |
| 프로세스 ID | P-DOMAIN-CONTENT-01 |
| 이름 | 공지사항·도움말 운영 |
| 구분 | TO-BE |
| 액터 | 관리자, 이용자, 호스트 |
| 트리거 | 관리자의 게시물 등록·수정·삭제, 회원의 목록·검색 |
| 사전 조건 | 관리자 로그인(작성), 회원 로그인 불필요(조회) |
| 입력 | 제목, 내용, 유형·카테고리, 첨부파일·이미지, 상단 고정 여부 |
| 단계 | 1. 게시물 작성 2. 고정·카테고리 지정 3. 목록 노출(10건/페이지) 4. 검색(제목·내용·제목+내용) 5. 수정 시 수정일 갱신 6. 삭제 |
| 결정 | 고정 여부 / 카테고리 선택 / 페이지 범위 |
| 예외 | 첨부 다운로드 실패, 고정 글은 1페이지에만 노출 |
| 사후 조건 | 게시물이 등록·갱신되고 회원 화면에 노출된다 |
| 관련 용어 | [공지사항](glossary.md#용어), [도움말](glossary.md#용어) |
| 출처 | `[p.4]`, `[p.18]`, `[정책 §관리자 5-1]` |
| 관련 플로우 | [F-DOMAIN-CONTENT-01](flows.md#f-domain-content-01) |
| 관련 finding | [V-DOMAIN-NOTICE-01](validation.md#v-domain-notice-01), [V-UI-PAGING-01](validation.md#v-ui-paging-01) |

## 프로세스-플로우 대응

| 프로세스 | 플로우 | 주요 finding |
| --- | --- | --- |
| [P-AUTH-LOGIN-01](processes.md#p-auth-login-01) | [F-AUTH-LOGIN-01](flows.md#f-auth-login-01) | [V-AUTH-BLACKLIST-01](validation.md#v-auth-blacklist-01) |
| [P-AUTH-PASSWORD-RESET-01](processes.md#p-auth-password-reset-01) | [F-AUTH-PASSWORD-RESET-01](flows.md#f-auth-password-reset-01) | [V-SEC-PRIVACY-01](validation.md#v-sec-privacy-01) |
| [P-ACCOUNT-SIGNUP-01](processes.md#p-account-signup-01) | [F-ACCOUNT-SIGNUP-01](flows.md#f-account-signup-01) | [V-ACCOUNT-SIGNUP-01](validation.md#v-account-signup-01) |
| [P-ACCOUNT-WITHDRAW-01](processes.md#p-account-withdraw-01) | [F-ACCOUNT-WITHDRAW-01](flows.md#f-account-withdraw-01) | [V-ACCOUNT-WITHDRAW-01](validation.md#v-account-withdraw-01) |
| [P-ACCOUNT-BLACKLIST-01](processes.md#p-account-blacklist-01) | [F-ACCOUNT-BLACKLIST-01](flows.md#f-account-blacklist-01) | [V-ACCOUNT-BLACKLIST-01](validation.md#v-account-blacklist-01) |
| [P-DOMAIN-SPACE-REGISTER-01](processes.md#p-domain-space-register-01) | [F-DOMAIN-SPACE-REGISTER-01](flows.md#f-domain-space-register-01) | [V-DOMAIN-SPACE-02](validation.md#v-domain-space-02) |
| [P-PROC-INSPECT-01](processes.md#p-proc-inspect-01) | [F-PROC-INSPECT-01](flows.md#f-proc-inspect-01) | [V-STATUS-INSPECT-01](validation.md#v-status-inspect-01) |
| [P-DOMAIN-SPACE-MANAGE-01](processes.md#p-domain-space-manage-01) | [F-DOMAIN-SPACE-MANAGE-01](flows.md#f-domain-space-manage-01) | [V-DOMAIN-SPACE-01](validation.md#v-domain-space-01) |
| [P-DOMAIN-PACKAGE-01](processes.md#p-domain-package-01) | [F-DOMAIN-PACKAGE-01](flows.md#f-domain-package-01) | [V-DOMAIN-PACKAGE-01](validation.md#v-domain-package-01) |
| [P-DOMAIN-RESERVATION-01](processes.md#p-domain-reservation-01) | [F-DOMAIN-RESERVATION-01](flows.md#f-domain-reservation-01) | [V-DOMAIN-RESERVATION-02](validation.md#v-domain-reservation-02) |
| [P-DOMAIN-RESERVATION-CANCEL-01](processes.md#p-domain-reservation-cancel-01) | [F-DOMAIN-RESERVATION-CANCEL-01](flows.md#f-domain-reservation-cancel-01) | [V-DOMAIN-PAYMENT-01](validation.md#v-domain-payment-01) |
| [P-DOMAIN-MILEAGE-CHARGE-01](processes.md#p-domain-mileage-charge-01) | [F-DOMAIN-MILEAGE-CHARGE-01](flows.md#f-domain-mileage-charge-01) | [V-DOMAIN-MILEAGE-CHARGE-01](validation.md#v-domain-mileage-charge-01) |
| [P-DOMAIN-MILEAGE-EXCHANGE-01](processes.md#p-domain-mileage-exchange-01) | [F-DOMAIN-MILEAGE-EXCHANGE-01](flows.md#f-domain-mileage-exchange-01) | [V-STATUS-MILEAGE-01](validation.md#v-status-mileage-01) |
| [P-DOMAIN-MILEAGE-SETTLE-01](processes.md#p-domain-mileage-settle-01) | [F-DOMAIN-MILEAGE-SETTLE-01](flows.md#f-domain-mileage-settle-01) | [V-PROC-SETTLE-01](validation.md#v-proc-settle-01) |
| [P-DOMAIN-REVIEW-01](processes.md#p-domain-review-01) | [F-DOMAIN-REVIEW-01](flows.md#f-domain-review-01) | [V-DOMAIN-REVIEW-01](validation.md#v-domain-review-01) |
| [P-DOMAIN-QNA-01](processes.md#p-domain-qna-01) | [F-DOMAIN-QNA-01](flows.md#f-domain-qna-01) | [V-DOMAIN-QNA-01](validation.md#v-domain-qna-01) |
| [P-PROC-REPORT-01](processes.md#p-proc-report-01) | [F-PROC-REPORT-01](flows.md#f-proc-report-01) | [V-PROC-REPORT-01](validation.md#v-proc-report-01) |
| [P-DOMAIN-MESSAGE-01](processes.md#p-domain-message-01) | [F-DOMAIN-MESSAGE-01](flows.md#f-domain-message-01) | [V-TERM-MESSENGER-01](validation.md#v-term-messenger-01) |
| [P-DOMAIN-CONTENT-01](processes.md#p-domain-content-01) | [F-DOMAIN-CONTENT-01](flows.md#f-domain-content-01) | [V-DOMAIN-NOTICE-01](validation.md#v-domain-notice-01) |

## 프로세스 미정의 항목

| 항목 | 상태 | 근거 |
| --- | --- | --- |
| 운영 보고서 조회 | 프로세스 미정의 | 요구 PDF 관리자 장에 요구가 없고 정책 문서에만 있다 `[정책 §관리자 7-1]`. 지표 정의가 없어 흐름을 그리지 않았다 → [V-REPORT-OPERATION-01](validation.md#v-report-operation-01) |
| 관리자 계정 생성 | 프로세스 미정의 | "DB에 직접 입력"만 서술되고 화면·권한 부여 절차가 없다 `[p.2]` |
| 공지·도움말의 첨부파일 저장 | 단계 미정의 | 등록·조회만 있고 저장소·용량 규칙이 없다 `[p.4]` |
