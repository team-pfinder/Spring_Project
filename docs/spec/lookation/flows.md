# 플로우차트

> 문서 지도: [index](index.md) · [glossary](glossary.md) · [domain](domain.md) · [processes](processes.md) · [flows](flows.md) · [validation](validation.md) · [source](source/source.md) · [source.plain](source/source.plain.md)

## 읽는 법

- 각 플로우는 같은 ID의 프로세스와 1:1 대응한다([processes.md](processes.md)).
- 노드 ID는 `{TOPIC}{NN}_{NODE}` 규칙을 따르고 `START`·`END`는 예약어다.
- 원문에 없는 분기는 도표 아래에 `추론`으로 표시했다. 렌더링 도구(mmdc)가 없어 Mermaid 소스로만 보관한다.

## F-AUTH-LOGIN-01

**로그인·로그아웃·세션 유지**

```mermaid
flowchart TD
    LOGIN01_START([시작: 로그인 화면]) --> LOGIN01_INPUT["이메일·비밀번호 입력"]
    LOGIN01_INPUT --> LOGIN01_VALIDATE{"자격 증명 일치?"}
    LOGIN01_VALIDATE -->|아니오| LOGIN01_FAIL["오류 안내 후 재입력"]
    LOGIN01_FAIL --> LOGIN01_INPUT
    LOGIN01_VALIDATE -->|예| LOGIN01_STATUS{"계정 상태"}
    LOGIN01_STATUS -->|탈퇴| LOGIN01_DENY["로그인 거부"]
    LOGIN01_STATUS -->|블랙리스트| LOGIN01_BLACK{"잔여 예약 있음?"}
    LOGIN01_BLACK -->|예| LOGIN01_LIMITED["제한 로그인: 예약 확인만 허용"]
    LOGIN01_BLACK -->|아니오| LOGIN01_POPUP["해제일·사유 팝업 후 로그아웃"]
    LOGIN01_STATUS -->|정상| LOGIN01_SESSION["세션 발급: 회원코드·역할 저장"]
    LOGIN01_LIMITED --> LOGIN01_SESSION
    LOGIN01_SESSION --> LOGIN01_MENU["역할별 메뉴 노출"]
    LOGIN01_MENU --> LOGIN01_IDLE{"30분 무요청?"}
    LOGIN01_IDLE -->|예| LOGIN01_EXPIRE["세션 만료·로그인 화면 이동"]
    LOGIN01_IDLE -->|아니오| LOGIN01_LOGOUT{"로그아웃 클릭?"}
    LOGIN01_LOGOUT -->|예| LOGIN01_END([종료: 세션 삭제])
    LOGIN01_LOGOUT -->|아니오| LOGIN01_MENU
    LOGIN01_EXPIRE --> LOGIN01_END
    LOGIN01_DENY --> LOGIN01_END
    LOGIN01_POPUP --> LOGIN01_END
```

- 대응 프로세스: [P-AUTH-LOGIN-01](processes.md#p-auth-login-01)
- 관련 finding: [V-AUTH-BLACKLIST-01](validation.md#v-auth-blacklist-01), [V-AUTH-SESSION-01](validation.md#v-auth-session-01)
- 추론: 블랙리스트 로그인을 "거부"와 "제한 허용"으로 나눈 분기. 원문은 두 규칙을 함께 적어 판단이 갈린다.

## F-AUTH-PASSWORD-RESET-01

**비밀번호 찾기·재설정**

```mermaid
flowchart TD
    RESET01_START([시작: 비밀번호 찾기]) --> RESET01_EMAIL["이메일 입력"]
    RESET01_EMAIL --> RESET01_EXISTS{"가입된 이메일?"}
    RESET01_EXISTS -->|아니오| RESET01_HINT["가입 여부 안내(계정 존재 여부 비노출)"]
    RESET01_HINT --> RESET01_EMAIL
    RESET01_EXISTS -->|예| RESET01_SEND["재설정 링크 메일 발송"]
    RESET01_SEND --> RESET01_MAIL{"메일 발송 성공?"}
    RESET01_MAIL -->|아니오| RESET01_RETRY["재발송 안내"]
    RESET01_RETRY --> RESET01_SEND
    RESET01_MAIL -->|예| RESET01_LINK["링크 클릭: 재설정 화면"]
    RESET01_LINK --> RESET01_EXPIRED{"링크 유효?"}
    RESET01_EXPIRED -->|아니오| RESET01_EXPIRED_MSG["만료 안내 후 재요청"]
    RESET01_EXPIRED_MSG --> RESET01_EMAIL
    RESET01_EXPIRED -->|예| RESET01_NEW["새 비밀번호·확인 입력"]
    RESET01_NEW --> RESET01_RULE{"8~12자·특수문자 충족? 확인 일치?"}
    RESET01_RULE -->|아니오| RESET01_RULE_MSG["규칙 안내"]
    RESET01_RULE_MSG --> RESET01_NEW
    RESET01_RULE -->|예| RESET01_SAVE["비밀번호 갱신"]
    RESET01_SAVE --> RESET01_END([종료: 로그인 화면])
```

- 대응 프로세스: [P-AUTH-PASSWORD-RESET-01](processes.md#p-auth-password-reset-01)
- 관련 finding: [V-SEC-PRIVACY-01](validation.md#v-sec-privacy-01)
- 추론: 링크 만료 시간과 기존 세션 무효화 규칙은 원문에 없어 일반 관행으로 분기를 두었다.

## F-ACCOUNT-SIGNUP-01

**회원가입·이메일 인증**

```mermaid
flowchart TD
    SIGNUP01_START([시작: 회원가입 화면]) --> SIGNUP01_ROLE{"가입 유형"}
    SIGNUP01_ROLE -->|이용자| SIGNUP01_FORM_U["이메일·비밀번호·닉네임·연락처 입력"]
    SIGNUP01_ROLE -->|호스트| SIGNUP01_FORM_H["이메일·닉네임·이름·비밀번호·연락처 입력"]
    SIGNUP01_FORM_U --> SIGNUP01_VALIDATE{"필수·정규식 통과?"}
    SIGNUP01_FORM_H --> SIGNUP01_VALIDATE
    SIGNUP01_VALIDATE -->|아니오| SIGNUP01_ERR["항목별 오류 표시"]
    SIGNUP01_ERR --> SIGNUP01_ROLE
    SIGNUP01_VALIDATE -->|예| SIGNUP01_DUP{"이메일·닉네임 중복?"}
    SIGNUP01_DUP -->|예| SIGNUP01_DUP_MSG["중복 안내"]
    SIGNUP01_DUP_MSG --> SIGNUP01_ROLE
    SIGNUP01_DUP -->|아니오| SIGNUP01_AGREE{"이용정책 동의?"}
    SIGNUP01_AGREE -->|아니오| SIGNUP01_AGREE_MSG["동의 필요 안내"]
    SIGNUP01_AGREE_MSG --> SIGNUP01_ROLE
    SIGNUP01_AGREE -->|예| SIGNUP01_MAIL["인증 메일 발송"]
    SIGNUP01_MAIL --> SIGNUP01_CONFIRM{"메일 링크 확인?"}
    SIGNUP01_CONFIRM -->|미확인| SIGNUP01_PENDING["가입 미완료 상태 유지"]
    SIGNUP01_PENDING --> SIGNUP01_MAIL
    SIGNUP01_CONFIRM -->|확인| SIGNUP01_CREATE["계정 생성·회원고유코드 부여·가입일자 기록"]
    SIGNUP01_CREATE --> SIGNUP01_LOGIN["자동 로그인"]
    SIGNUP01_LOGIN --> SIGNUP01_END([종료: 메인 화면])
```

- 대응 프로세스: [P-ACCOUNT-SIGNUP-01](processes.md#p-account-signup-01)
- 관련 finding: [V-ACCOUNT-SIGNUP-01](validation.md#v-account-signup-01)
- 추론: 이용자 가입에 이름 항목이 없고 호스트 가입에만 있는 것은 원문 그대로다. 두 양식을 한 플로우에 합친 것은 추론이다.

## F-ACCOUNT-WITHDRAW-01

**회원 탈퇴·데이터 분리보관**

```mermaid
flowchart TD
    WITHDRAW01_START([시작: 마이페이지]) --> WITHDRAW01_CHECK{"탈퇴 가능 조건"}
    WITHDRAW01_CHECK -->|잔여 마일리지 있음| WITHDRAW01_MILEAGE["환전 안내: 환전 페이지로 이동"]
    WITHDRAW01_MILEAGE --> WITHDRAW01_END([종료: 탈퇴 중단])
    WITHDRAW01_CHECK -->|예약 보유| WITHDRAW01_BOOK["탈퇴 불가 안내(정책)"]
    WITHDRAW01_BOOK --> WITHDRAW01_END
    WITHDRAW01_CHECK -->|조건 충족| WITHDRAW01_PW["비밀번호 재입력"]
    WITHDRAW01_PW --> WITHDRAW01_PWOK{"비밀번호 일치?"}
    WITHDRAW01_PWOK -->|아니오| WITHDRAW01_PWERR["불일치 안내"]
    WITHDRAW01_PWERR --> WITHDRAW01_PW
    WITHDRAW01_PWOK -->|예| WITHDRAW01_CONFIRM{"탈퇴 의사 재확인"}
    WITHDRAW01_CONFIRM -->|취소| WITHDRAW01_END
    WITHDRAW01_CONFIRM -->|확인| WITHDRAW01_DELETE["식별정보 삭제·탈퇴회원 분리보관"]
    WITHDRAW01_DELETE --> WITHDRAW01_HIDE["게시물 비노출·닉네임 익명화"]
    WITHDRAW01_HIDE --> WITHDRAW01_KEEP["예약·거래 기록 유지"]
    WITHDRAW01_KEEP --> WITHDRAW01_LOGOUT["세션 종료"]
    WITHDRAW01_LOGOUT --> WITHDRAW01_END
```

- 대응 프로세스: [P-ACCOUNT-WITHDRAW-01](processes.md#p-account-withdraw-01)
- 관련 finding: [V-ACCOUNT-WITHDRAW-01](validation.md#v-account-withdraw-01), [V-DATA-RETENTION-01](validation.md#v-data-retention-01)
- 추론: "예약 보유 시 탈퇴 불가"는 정책 문서 규칙이라 요구 PDF와 어긋난다. 플로우는 정책 쪽을 따랐다.

## F-ACCOUNT-BLACKLIST-01

**블랙리스트 지정·해제**

```mermaid
flowchart TD
    BLACK01_START([시작: 신고 처리 결과 또는 관리자 판단]) --> BLACK01_REVIEW["신고·경고 이력 확인"]
    BLACK01_REVIEW --> BLACK01_DECIDE{"제재 대상?"}
    BLACK01_DECIDE -->|아니오| BLACK01_NONE["제재 없음: 종료"]
    BLACK01_NONE --> BLACK01_END([종료])
    BLACK01_DECIDE -->|예| BLACK01_PERIOD["제재 기간 결정(정책: 3·6·9·12개월 / 요구 PDF: 영구)"]
    BLACK01_PERIOD --> BLACK01_REG["블랙리스트 등록·사유 저장"]
    BLACK01_REG --> BLACK01_BAL{"보유 마일리지 > 0?"}
    BLACK01_BAL -->|예| BLACK01_ACCOUNT{"등록 계좌 있음?"}
    BLACK01_ACCOUNT -->|예| BLACK01_REFUND["대표계좌로 자동환불·거래 기록"]
    BLACK01_ACCOUNT -->|아니오| BLACK01_ASK["계좌 등록 페이지로 이동"]
    BLACK01_ASK --> BLACK01_REFUND
    BLACK01_BAL -->|아니오| BLACK01_ANON["작성 글 닉네임 익명화"]
    BLACK01_REFUND --> BLACK01_ANON
    BLACK01_ANON --> BLACK01_KICK{"현재 로그인 중?"}
    BLACK01_KICK -->|예| BLACK01_LOGOUT["안내 알림 후 강제 로그아웃"]
    BLACK01_KICK -->|아니오| BLACK01_LATER["다음 로그인 시 팝업 안내"]
    BLACK01_LOGOUT --> BLACK01_END
    BLACK01_LATER --> BLACK01_END
```

- 대응 프로세스: [P-ACCOUNT-BLACKLIST-01](processes.md#p-account-blacklist-01)
- 관련 finding: [V-ACCOUNT-BLACKLIST-01](validation.md#v-account-blacklist-01), [V-PERM-BLACKLIST-01](validation.md#v-perm-blacklist-01), [V-DOMAIN-MILEAGE-REFUND-01](validation.md#v-domain-mileage-refund-01)
- 추론: 잔여 예약이 있는 경우의 제한 로그인 분기는 로그인 플로우에 두었다.

## F-DOMAIN-SPACE-REGISTER-01

**공간 등록**

```mermaid
flowchart TD
    REG01_START([시작: 공간 등록]) --> REG01_BASIC["기본 정보 입력: 유형·이름·소개·시설·주의사항·대표이미지·주소"]
    REG01_BASIC --> REG01_CONTACT["연락처 정보 입력: 이메일·휴대폰·대표전화"]
    REG01_CONTACT --> REG01_BIZ["사업자 정보 입력: 상호·대표자·등록번호·등록증·유형·업태·종목·주소"]
    REG01_BIZ --> REG01_DETAIL["상세 정보 입력: 이미지·최소/최대 인원·웹사이트"]
    REG01_DETAIL --> REG01_GUIDE["이용 안내 입력: 이용시간·정기/지정 휴무"]
    REG01_GUIDE --> REG01_PACKAGE{"패키지 1건 이상 등록?"}
    REG01_PACKAGE -->|아니오| REG01_PKG_MOVE["패키지 등록 페이지로 이동"]
    REG01_PKG_MOVE --> REG01_PACKAGE
    REG01_PACKAGE -->|예| REG01_VALIDATE{"필수 항목·이미지 규격 통과?"}
    REG01_VALIDATE -->|아니오| REG01_ERR["항목 오류 표시"]
    REG01_ERR --> REG01_BASIC
    REG01_VALIDATE -->|예| REG01_SAVE["검수 대기 상태로 저장"]
    REG01_SAVE --> REG01_INSPECT["검수 신청"]
    REG01_INSPECT --> REG01_END([종료: 공간관리 목록에 검수 대기 표시])
```

- 대응 프로세스: [P-DOMAIN-SPACE-REGISTER-01](processes.md#p-domain-space-register-01)
- 관련 finding: [V-DOMAIN-SPACE-02](validation.md#v-domain-space-02), [V-DOMAIN-PACKAGE-01](validation.md#v-domain-package-01)
- 추론: 패키지 등록을 공간 등록의 선행 조건으로 배치했다. 원문은 "패키지가 없으면 등록 불가 안내"만 서술한다 `[정책 §호스트 3-1]`.

## F-PROC-INSPECT-01

**검수 신청·승인·반려**

```mermaid
flowchart TD
    INSPECT01_START([시작: 검수 신청 접수]) --> INSPECT01_WAIT["공간 상태: 검수 대기"]
    INSPECT01_WAIT --> INSPECT01_LOCK{"호스트 수정·삭제 요청?"}
    INSPECT01_LOCK -->|있음| INSPECT01_DENY["처리 거부: 검수 중 변경 불가"]
    INSPECT01_DENY --> INSPECT01_WAIT
    INSPECT01_LOCK -->|없음| INSPECT01_ADMIN["관리자 검수 목록 조회"]
    INSPECT01_ADMIN --> INSPECT01_DETAIL["공간 상세·사업자 정보·이미지 검토"]
    INSPECT01_DETAIL --> INSPECT01_JUDGE{"적법·필수 정보 충족?"}
    INSPECT01_JUDGE -->|예| INSPECT01_APPROVE["검수 승인 저장"]
    INSPECT01_APPROVE --> INSPECT01_PUBLISH["공간 게시: 검색 노출"]
    INSPECT01_PUBLISH --> INSPECT01_MAIL["호스트에게 게시 알림 메일"]
    INSPECT01_MAIL --> INSPECT01_END([종료])
    INSPECT01_JUDGE -->|아니오| INSPECT01_REASON["반려 사유 입력"]
    INSPECT01_REASON --> INSPECT01_REJECT["검수 반려 저장"]
    INSPECT01_REJECT --> INSPECT01_RETRY{"재신청 허용?"}
    INSPECT01_RETRY -->|불가: 요구 PDF| INSPECT01_DELETE_ONLY["호스트는 삭제만 가능"]
    INSPECT01_DELETE_ONLY --> INSPECT01_END
    INSPECT01_RETRY -->|가능: 정책 문서| INSPECT01_FIX["호스트가 정보 수정 후 재신청"]
    INSPECT01_FIX --> INSPECT01_WAIT
```

- 대응 프로세스: [P-PROC-INSPECT-01](processes.md#p-proc-inspect-01)
- 관련 finding: [V-STATUS-INSPECT-01](validation.md#v-status-inspect-01), [V-STATUS-INSPECT-02](validation.md#v-status-inspect-02)
- 추론: 없음. 재신청 분기는 문서 충돌을 그대로 두 갈래로 그린 것이다.

## F-DOMAIN-SPACE-MANAGE-01

**공간 조회·수정·삭제**

```mermaid
flowchart TD
    MANAGE01_START([시작: 공간 관리 페이지]) --> MANAGE01_LIST["등록 공간 목록: 검수상태·등록일·공간명·대표이미지"]
    MANAGE01_LIST --> MANAGE01_ACTION{"선택"}
    MANAGE01_ACTION -->|수정| MANAGE01_EDIT_LOCK{"검수 대기 중?"}
    MANAGE01_EDIT_LOCK -->|예| MANAGE01_BLOCK["수정 불가 안내"]
    MANAGE01_BLOCK --> MANAGE01_LIST
    MANAGE01_EDIT_LOCK -->|아니오| MANAGE01_EDIT["수정 화면: 불가 항목 disabled"]
    MANAGE01_EDIT --> MANAGE01_SAVE["저장"]
    MANAGE01_SAVE --> MANAGE01_REINSPECT{"검수 완료 공간 수정?"}
    MANAGE01_REINSPECT -->|예| MANAGE01_NO_REINSPECT["재검수 없이 유지"]
    MANAGE01_REINSPECT -->|아니오| MANAGE01_STAY["검수 상태 유지"]
    MANAGE01_NO_REINSPECT --> MANAGE01_LIST
    MANAGE01_STAY --> MANAGE01_LIST
    MANAGE01_ACTION -->|삭제| MANAGE01_DEL_LOCK{"검수 대기 중?"}
    MANAGE01_DEL_LOCK -->|예| MANAGE01_BLOCK
    MANAGE01_DEL_LOCK -->|아니오| MANAGE01_FUTURE{"이후 예약 존재?"}
    MANAGE01_FUTURE -->|예| MANAGE01_DEL_BLOCK["삭제 거부 안내"]
    MANAGE01_DEL_BLOCK --> MANAGE01_LIST
    MANAGE01_FUTURE -->|아니오| MANAGE01_CONFIRM["삭제 확인 경고창"]
    MANAGE01_CONFIRM --> MANAGE01_CASCADE["종속 데이터 삭제·검색 비노출"]
    MANAGE01_CASCADE --> MANAGE01_END([종료])
```

- 대응 프로세스: [P-DOMAIN-SPACE-MANAGE-01](processes.md#p-domain-space-manage-01)
- 관련 finding: [V-DOMAIN-SPACE-01](validation.md#v-domain-space-01)
- 추론: 삭제 시 리뷰·Q&A·예약을 함께 지우는지 여부는 문서가 갈린다. 플로우는 정책 문서의 연쇄 삭제를 따랐다.

## F-DOMAIN-PACKAGE-01

**패키지 등록·적용**

```mermaid
flowchart TD
    PKG01_START([시작: 패키지 관리]) --> PKG01_INSPECT{"공간 검수 승인?"}
    PKG01_INSPECT -->|아니오| PKG01_DENY["등록·적용 불가 안내"]
    PKG01_DENY --> PKG01_END([종료])
    PKG01_INSPECT -->|예| PKG01_INPUT["패키지명·시작시간·종료시간·가격 입력"]
    PKG01_INPUT --> PKG01_RULE{"3시간 이상 34시간 이하?"}
    PKG01_RULE -->|아니오| PKG01_RULE_ERR["시간 규칙 위반 안내"]
    PKG01_RULE_ERR --> PKG01_INPUT
    PKG01_RULE -->|예| PKG01_PRICE{"가격 1,000~1,000,000원?"}
    PKG01_PRICE -->|아니오| PKG01_PRICE_ERR["가격 범위 안내"]
    PKG01_PRICE_ERR --> PKG01_INPUT
    PKG01_PRICE -->|예| PKG01_SAVE["패키지 저장"]
    PKG01_SAVE --> PKG01_APPLY["달력에서 적용 날짜 선택"]
    PKG01_APPLY --> PKG01_OVERLAP{"같은 날짜 시간 겹침?"}
    PKG01_OVERLAP -->|예| PKG01_OVERLAP_ERR["겹침 안내: 다른 시간대 선택"]
    PKG01_OVERLAP_ERR --> PKG01_APPLY
    PKG01_OVERLAP -->|아니오| PKG01_APPLIED["패키지 적용 저장"]
    PKG01_APPLIED --> PKG01_HOLIDAY["적용 없는 날짜는 휴무 처리"]
    PKG01_HOLIDAY --> PKG01_END
```

- 대응 프로세스: [P-DOMAIN-PACKAGE-01](processes.md#p-domain-package-01)
- 관련 finding: [V-DOMAIN-PACKAGE-01](validation.md#v-domain-package-01)
- 추론: 검수 승인 여부를 패키지 관리 진입 조건으로 배치했다. 원문은 "검수 승인된 공간만 패키지 등록·적용 가능"이라고만 적는다 `[p.16]`.

## F-DOMAIN-RESERVATION-01

**예약 생성·마일리지 결제**

```mermaid
flowchart TD
    RES01_START([시작: 공간 상세]) --> RES01_AUTH{"로그인?"}
    RES01_AUTH -->|아니오| RES01_LOGIN["로그인 페이지로 이동"]
    RES01_LOGIN --> RES01_END([종료])
    RES01_AUTH -->|예| RES01_DATE["날짜·패키지 선택"]
    RES01_DATE --> RES01_RANGE{"3개월 이내?"}
    RES01_RANGE -->|아니오| RES01_RANGE_ERR["선택 불가 안내"]
    RES01_RANGE_ERR --> RES01_DATE
    RES01_RANGE -->|예| RES01_FORM["예약자명·연락처·인원·요청사항 입력"]
    RES01_FORM --> RES01_VALID{"입력 규칙 통과?"}
    RES01_VALID -->|아니오| RES01_VALID_ERR["항목 오류 안내"]
    RES01_VALID_ERR --> RES01_FORM
    RES01_VALID -->|예| RES01_BAL{"마일리지 잔액 충분?"}
    RES01_BAL -->|아니오| RES01_CHARGE["충전 페이지로 이동"]
    RES01_CHARGE --> RES01_BAL
    RES01_BAL -->|예| RES01_PAY["마일리지 결제 요청"]
    RES01_PAY --> RES01_RACE{"다른 결제가 먼저 성공?"}
    RES01_RACE -->|예| RES01_SOLD["이미 예약된 패키지 안내 후 상세 복귀"]
    RES01_SOLD --> RES01_END
    RES01_RACE -->|아니오| RES01_CONFIRM["예약완료·결제 기록·에스크로 보관"]
    RES01_CONFIRM --> RES01_LIST["예약 목록에 표시"]
    RES01_LIST --> RES01_END
```

- 대응 프로세스: [P-DOMAIN-RESERVATION-01](processes.md#p-domain-reservation-01)
- 관련 finding: [V-DOMAIN-RESERVATION-02](validation.md#v-domain-reservation-02), [V-INT-PAYMENT-01](validation.md#v-int-payment-01)
- 추론: 패키지 날짜 잠금 시점을 결제 시점으로 그렸다. 원문은 "결제가 먼저 성공하는 사람이 우선"만 서술한다 `[p.9]`.

## F-DOMAIN-RESERVATION-CANCEL-01

**예약 취소·환불**

```mermaid
flowchart TD
    CANCEL01_START([시작: 예약 목록]) --> CANCEL01_STATE{"예약 상태"}
    CANCEL01_STATE -->|예약완료 아님| CANCEL01_DENY["취소 불가 안내"]
    CANCEL01_DENY --> CANCEL01_END([종료])
    CANCEL01_STATE -->|예약완료| CANCEL01_WHO{"취소 주체"}
    CANCEL01_WHO -->|이용자| CANCEL01_DEADLINE{"이용 전날 23:59 이전?"}
    CANCEL01_DEADLINE -->|아니오| CANCEL01_TODAY["당일 취소 불가 안내"]
    CANCEL01_TODAY --> CANCEL01_END
    CANCEL01_DEADLINE -->|예| CANCEL01_REASON["취소 사유 입력"]
    CANCEL01_WHO -->|호스트| CANCEL01_HOST_REASON["취소 사유 입력: 당일 취소 100% 환불(정책)"]
    CANCEL01_REASON --> CANCEL01_RATIO["환불 비율 산정: 7일 전 100%, 6~1일 전 50%"]
    CANCEL01_HOST_REASON --> CANCEL01_RATIO
    CANCEL01_RATIO --> CANCEL01_REFUND["마일리지 환불·거래 기록"]
    CANCEL01_REFUND --> CANCEL01_STATE_UP["예약 상태: 취소/환불완료"]
    CANCEL01_STATE_UP --> CANCEL01_NOTIFY["상대방·관리자에게 사유 노출"]
    CANCEL01_NOTIFY --> CANCEL01_SLOT["해당 패키지 날짜 재예약 가능"]
    CANCEL01_SLOT --> CANCEL01_END
```

- 대응 프로세스: [P-DOMAIN-RESERVATION-CANCEL-01](processes.md#p-domain-reservation-cancel-01)
- 관련 finding: [V-DOMAIN-RESERVATION-01](validation.md#v-domain-reservation-01), [V-DOMAIN-PAYMENT-01](validation.md#v-domain-payment-01)
- 추론: 호스트 취소의 환불 비율은 정책 문서의 100% 규칙을 따랐다. 요구 PDF는 "호스트나 이용자가 취소"만 서술한다 `[p.17]`.

## F-DOMAIN-MILEAGE-CHARGE-01

**마일리지 충전 신청·승인**

```mermaid
flowchart TD
    CHARGE01_START([시작: 마일리지 충전]) --> CHARGE01_ACCOUNT{"등록 계좌 있음?"}
    CHARGE01_ACCOUNT -->|아니오| CHARGE01_ADD["계좌 등록 페이지로 이동"]
    CHARGE01_ADD --> CHARGE01_ACCOUNT
    CHARGE01_ACCOUNT -->|예| CHARGE01_PENDING{"진행 중 신청 있음?"}
    CHARGE01_PENDING -->|예| CHARGE01_BLOCK["중복 신청 불가 안내"]
    CHARGE01_BLOCK --> CHARGE01_END([종료])
    CHARGE01_PENDING -->|아니오| CHARGE01_AMOUNT["금액 선택(정책: 1만원 단위, 1만~100만원)"]
    CHARGE01_AMOUNT --> CHARGE01_RANGE{"금액 범위 충족?"}
    CHARGE01_RANGE -->|아니오| CHARGE01_RANGE_ERR["범위 안내"]
    CHARGE01_RANGE_ERR --> CHARGE01_AMOUNT
    CHARGE01_RANGE -->|예| CHARGE01_CREATE["충전 신청 생성: 대기"]
    CHARGE01_CREATE --> CHARGE01_TRANSFER["이용자 계좌 이체"]
    CHARGE01_TRANSFER --> CHARGE01_ADMIN["관리자: 예금주·금액 확인"]
    CHARGE01_ADMIN --> CHARGE01_TIMEOUT{"대기 시간 경과?"}
    CHARGE01_TIMEOUT -->|예| CHARGE01_EXPIRE["승인 버튼 비활성·실패 처리"]
    CHARGE01_EXPIRE --> CHARGE01_END
    CHARGE01_TIMEOUT -->|아니오| CHARGE01_MATCH{"입금액 일치?"}
    CHARGE01_MATCH -->|아니오| CHARGE01_FAIL["충전실패 처리(사이트 외 환불)"]
    CHARGE01_FAIL --> CHARGE01_END
    CHARGE01_MATCH -->|예| CHARGE01_APPROVE["승인: 마일리지 증가·완료 처리"]
    CHARGE01_APPROVE --> CHARGE01_END
```

- 대응 프로세스: [P-DOMAIN-MILEAGE-CHARGE-01](processes.md#p-domain-mileage-charge-01)
- 관련 finding: [V-DOMAIN-MILEAGE-CHARGE-01](validation.md#v-domain-mileage-charge-01), [V-DOMAIN-MILEAGE-CHARGE-02](validation.md#v-domain-mileage-charge-02), [V-STATUS-MILEAGE-01](validation.md#v-status-mileage-01)
- 추론: 대기 시간 경과 판정을 관리자 화면에서 수행하는 것으로 그렸다. 배치 여부는 원문에 없다.

## F-DOMAIN-MILEAGE-EXCHANGE-01

**마일리지 환전**

```mermaid
flowchart TD
    EXCH01_START([시작: 환전 신청]) --> EXCH01_ACCOUNT{"등록 계좌 있음?"}
    EXCH01_ACCOUNT -->|아니오| EXCH01_ADD["계좌 등록 페이지로 이동"]
    EXCH01_ADD --> EXCH01_ACCOUNT
    EXCH01_ACCOUNT -->|예| EXCH01_BAL{"보유 마일리지 ≥ 신청 금액?"}
    EXCH01_BAL -->|아니오| EXCH01_BAL_ERR["잔액 부족 안내"]
    EXCH01_BAL_ERR --> EXCH01_END([종료])
    EXCH01_BAL -->|예| EXCH01_MIN{"최소 1,000원 이상?"}
    EXCH01_MIN -->|아니오| EXCH01_MIN_ERR["최소 금액 안내"]
    EXCH01_MIN_ERR --> EXCH01_END
    EXCH01_MIN -->|예| EXCH01_SUBMIT["환전 신청 저장·마일리지 차감"]
    EXCH01_SUBMIT --> EXCH01_ADMIN["관리자: 신청 내역 확인"]
    EXCH01_ADMIN --> EXCH01_DEPOSIT["회원 계좌로 입금"]
    EXCH01_DEPOSIT --> EXCH01_DONE["환전 완료 처리·버튼 비활성"]
    EXCH01_DONE --> EXCH01_HISTORY["환전 내역 리스트 반영"]
    EXCH01_HISTORY --> EXCH01_END
```

- 대응 프로세스: [P-DOMAIN-MILEAGE-EXCHANGE-01](processes.md#p-domain-mileage-exchange-01)
- 관련 finding: [V-STATUS-MILEAGE-01](validation.md#v-status-mileage-01), [V-DOMAIN-MILEAGE-REFUND-01](validation.md#v-domain-mileage-refund-01)
- 추론: 신청 시점의 마일리지 차감을 그렸다. 원문은 "환전과 동시에 입금·차감"과 "관리자 완료 처리"를 함께 적어 시점이 불명확하다 `[p.5]`.

## F-DOMAIN-MILEAGE-SETTLE-01

**정산**

```mermaid
flowchart TD
    SETTLE01_START([시작: 예약 상태 점검]) --> SETTLE01_USED{"이용완료 전이?"}
    SETTLE01_USED -->|아니오| SETTLE01_WAIT["대기"]
    SETTLE01_WAIT --> SETTLE01_END([종료])
    SETTLE01_USED -->|예| SETTLE01_CANCELED{"취소된 예약?"}
    SETTLE01_CANCELED -->|예| SETTLE01_SKIP["정산 제외"]
    SETTLE01_SKIP --> SETTLE01_END
    SETTLE01_CANCELED -->|아니오| SETTLE01_FEE["수수료 계산(정책: 10%)"]
    SETTLE01_FEE --> SETTLE01_MODE{"정산 방식"}
    SETTLE01_MODE -->|자동: 요구 PDF| SETTLE01_AUTO["호스트 마일리지 자동 증가"]
    SETTLE01_MODE -->|수동: 정책 문서| SETTLE01_MANUAL["정산 버튼 활성화"]
    SETTLE01_MANUAL --> SETTLE01_CLICK{"호스트 클릭?"}
    SETTLE01_CLICK -->|아니오| SETTLE01_PENDING["정산 대기 유지"]
    SETTLE01_PENDING --> SETTLE01_END
    SETTLE01_CLICK -->|예| SETTLE01_CREDIT["호스트 마일리지 증가"]
    SETTLE01_AUTO --> SETTLE01_RECORD["정산 내역 기록: 마일리지·수수료·일자"]
    SETTLE01_CREDIT --> SETTLE01_RECORD
    SETTLE01_RECORD --> SETTLE01_END
```

- 대응 프로세스: [P-DOMAIN-MILEAGE-SETTLE-01](processes.md#p-domain-mileage-settle-01)
- 관련 finding: [V-PROC-SETTLE-01](validation.md#v-proc-settle-01), [V-DOMAIN-MILEAGE-SETTLE-01](validation.md#v-domain-mileage-settle-01)
- 추론: 없음. 자동·수동 두 갈래는 문서 충돌을 그대로 반영한 것이다.

## F-DOMAIN-REVIEW-01

**리뷰 작성·답변·신고**

```mermaid
flowchart TD
    REVIEW01_START([시작: 공간 상세 리뷰 영역]) --> REVIEW01_AUTH{"로그인?"}
    REVIEW01_AUTH -->|아니오| REVIEW01_LOGIN["로그인 페이지로 이동"]
    REVIEW01_LOGIN --> REVIEW01_END([종료])
    REVIEW01_AUTH -->|예| REVIEW01_WRITE["별점·내용·사진 입력"]
    REVIEW01_WRITE --> REVIEW01_VALID{"필수·사진 규격 통과?"}
    REVIEW01_VALID -->|아니오| REVIEW01_ERR["오류 안내"]
    REVIEW01_ERR --> REVIEW01_WRITE
    REVIEW01_VALID -->|예| REVIEW01_SAVE["리뷰 저장·노출"]
    REVIEW01_SAVE --> REVIEW01_REPLY["호스트 답글 1개 등록"]
    REVIEW01_REPLY --> REVIEW01_REPORT{"신고 접수?"}
    REVIEW01_REPORT -->|아니오| REVIEW01_END
    REVIEW01_REPORT -->|예| REVIEW01_COUNT{"누적 신고 3건 이상?"}
    REVIEW01_COUNT -->|예| REVIEW01_BLIND["블라인드: 검토중 안내문 표시"]
    REVIEW01_COUNT -->|아니오| REVIEW01_SHOW["노출 유지"]
    REVIEW01_BLIND --> REVIEW01_ADMIN["관리자 검토"]
    REVIEW01_ADMIN --> REVIEW01_JUDGE{"운영정책 위반?"}
    REVIEW01_JUDGE -->|예| REVIEW01_HIDE["숨김: 삭제된 리뷰 안내"]
    REVIEW01_JUDGE -->|아니오| REVIEW01_RESTORE["신고 반려·재노출"]
    REVIEW01_SHOW --> REVIEW01_END
    REVIEW01_HIDE --> REVIEW01_END
    REVIEW01_RESTORE --> REVIEW01_END
```

- 대응 프로세스: [P-DOMAIN-REVIEW-01](processes.md#p-domain-review-01)
- 관련 finding: [V-DOMAIN-REVIEW-01](validation.md#v-domain-review-01), [V-DOMAIN-REVIEW-02](validation.md#v-domain-review-02)
- 추론: 작성 자격 검사를 "로그인"만으로 두었다. 이용완료 여부를 요구 PDF가 명시하지 않는다.

## F-DOMAIN-QNA-01

**Q&A 작성·답변**

```mermaid
flowchart TD
    QNA01_START([시작: 공간 상세 Q&A]) --> QNA01_AUTH{"로그인?"}
    QNA01_AUTH -->|아니오| QNA01_LOGIN["로그인 페이지로 이동"]
    QNA01_LOGIN --> QNA01_END([종료])
    QNA01_AUTH -->|예| QNA01_WRITE["문의 내용 작성"]
    QNA01_WRITE --> QNA01_VALID{"내용 입력?"}
    QNA01_VALID -->|아니오| QNA01_ERR["내용 필요 안내"]
    QNA01_ERR --> QNA01_WRITE
    QNA01_VALID -->|예| QNA01_SAVE["Q&A 저장·노출"]
    QNA01_SAVE --> QNA01_HOST["호스트 확인"]
    QNA01_HOST --> QNA01_REPLY{"답글 작성?"}
    QNA01_REPLY -->|아니오| QNA01_PENDING["답변 대기"]
    QNA01_PENDING --> QNA01_END
    QNA01_REPLY -->|예| QNA01_REPLY_SAVE["답글 1개 저장"]
    QNA01_REPLY_SAVE --> QNA01_CHECK{"신고된 글?"}
    QNA01_CHECK -->|예| QNA01_LOCK["수정·삭제 비활성"]
    QNA01_CHECK -->|아니오| QNA01_EDIT["작성자 수정·삭제 가능"]
    QNA01_LOCK --> QNA01_END
    QNA01_EDIT --> QNA01_END
```

- 대응 프로세스: [P-DOMAIN-QNA-01](processes.md#p-domain-qna-01)
- 관련 finding: [V-DOMAIN-QNA-01](validation.md#v-domain-qna-01)
- 추론: 신고된 글의 수정·삭제 차단은 정책 문서 규칙만 있다 `[정책 §이용자 4-3]`.

## F-PROC-REPORT-01

**신고 접수·처리**

```mermaid
flowchart TD
    REPORT01_START([시작: 신고 버튼]) --> REPORT01_FORM["신고 유형·사유·첨부 입력"]
    REPORT01_FORM --> REPORT01_VALID{"유형·사유 입력?"}
    REPORT01_VALID -->|아니오| REPORT01_ERR["입력 필요 안내"]
    REPORT01_ERR --> REPORT01_FORM
    REPORT01_VALID -->|예| REPORT01_SAVE["신고 접수"]
    REPORT01_SAVE --> REPORT01_LIST["관리자 신고 목록: 링크·신고자·사유·처리현황"]
    REPORT01_LIST --> REPORT01_OPEN["신고 대상 게시물·내용 확인"]
    REPORT01_OPEN --> REPORT01_JUDGE{"운영정책 위반?"}
    REPORT01_JUDGE -->|아니오| REPORT01_REJECT["반려 처리·사유 입력"]
    REPORT01_JUDGE -->|예| REPORT01_ACCEPT["인정 처리·사유 입력"]
    REPORT01_ACCEPT --> REPORT01_BULK["해당 게시물의 모든 신고 일괄 처리"]
    REPORT01_REJECT --> REPORT01_BULK
    REPORT01_BULK --> REPORT01_MAIL["신고자·피신고자에게 결과 메일"]
    REPORT01_MAIL --> REPORT01_EFFECT{"파생 제재"}
    REPORT01_EFFECT -->|게시물| REPORT01_BLIND["블라인드 또는 영구 블라인드"]
    REPORT01_EFFECT -->|계정| REPORT01_WARN["경고 누적"]
    REPORT01_WARN --> REPORT01_BLACK["기준 초과 시 블랙리스트"]
    REPORT01_EFFECT -->|관계| REPORT01_PENALTY["신고자 공간 예약·이용 제한"]
    REPORT01_BLIND --> REPORT01_END([종료])
    REPORT01_BLACK --> REPORT01_END
    REPORT01_PENALTY --> REPORT01_END
```

- 대응 프로세스: [P-PROC-REPORT-01](processes.md#p-proc-report-01)
- 관련 finding: [V-PROC-REPORT-01](validation.md#v-proc-report-01)
- 추론: 경고 누적과 블랙리스트 전환을 신고 처리의 파생 단계로 배치했다. 요구 PDF는 관리자 주관 판단만 서술한다 `[p.3]`.

## F-DOMAIN-MESSAGE-01

**PIRI 1:1 문의**

```mermaid
flowchart TD
    MSG01_START([시작: 예약 목록]) --> MSG01_STATE{"예약완료?"}
    MSG01_STATE -->|아니오| MSG01_DENY["메신저 버튼 비활성"]
    MSG01_DENY --> MSG01_END([종료])
    MSG01_STATE -->|예| MSG01_THREAD["대화방 호출"]
    MSG01_THREAD --> MSG01_INPUT["텍스트·이미지 입력"]
    MSG01_INPUT --> MSG01_USED{"이용완료 시각 경과?"}
    MSG01_USED -->|예| MSG01_LOCK["전송 버튼 비활성·기록만 조회"]
    MSG01_LOCK --> MSG01_END
    MSG01_USED -->|아니오| MSG01_SEND["메시지 전송·목록 최종 시각 갱신"]
    MSG01_SEND --> MSG01_REPLY["상대 확인·답변"]
    MSG01_REPLY --> MSG01_LEAVE{"호스트 탈퇴?"}
    MSG01_LEAVE -->|예| MSG01_HIDE["대화 내역 비노출"]
    MSG01_HIDE --> MSG01_END
    MSG01_LEAVE -->|아니오| MSG01_ABUSE{"부적절 메시지?"}
    MSG01_ABUSE -->|예| MSG01_REPORT["캡처 첨부 후 신고"]
    MSG01_ABUSE -->|아니오| MSG01_INPUT
    MSG01_REPORT --> MSG01_END
```

- 대응 프로세스: [P-DOMAIN-MESSAGE-01](processes.md#p-domain-message-01)
- 관련 finding: [V-TERM-MESSENGER-01](validation.md#v-term-messenger-01)
- 추론: 이미지 첨부와 신고를 같은 대화방에서 처리하는 것으로 그렸다. 원문은 "예약내역 화면으로 접속하여 신고"라고 적는다 `[p.18]`.

## F-DOMAIN-CONTENT-01

**공지사항·도움말 운영**

```mermaid
flowchart TD
    CONTENT01_START([시작: 관리자 게시물 관리]) --> CONTENT01_KIND{"게시물 종류"}
    CONTENT01_KIND -->|공지사항| CONTENT01_N_FORM["제목·내용·유형·첨부 입력"]
    CONTENT01_KIND -->|도움말| CONTENT01_H_FORM["제목·내용·카테고리·이미지 입력"]
    CONTENT01_N_FORM --> CONTENT01_N_PIN{"상단 고정?"}
    CONTENT01_N_PIN -->|예| CONTENT01_N_TOP["1페이지 상단 고정·날짜순 정렬"]
    CONTENT01_N_PIN -->|아니오| CONTENT01_SAVE["게시물 저장"]
    CONTENT01_N_TOP --> CONTENT01_SAVE
    CONTENT01_H_FORM --> CONTENT01_SAVE
    CONTENT01_SAVE --> CONTENT01_LIST["목록 노출: 페이지당 10건·페이지 번호 5개"]
    CONTENT01_LIST --> CONTENT01_SEARCH["검색: 제목·내용·제목+내용, 도움말은 카테고리"]
    CONTENT01_SEARCH --> CONTENT01_EDIT{"수정 요청?"}
    CONTENT01_EDIT -->|예| CONTENT01_UPDATE["수정일 갱신 후 저장"]
    CONTENT01_EDIT -->|아니오| CONTENT01_DELETE{"삭제 요청?"}
    CONTENT01_UPDATE --> CONTENT01_LIST
    CONTENT01_DELETE -->|예| CONTENT01_REMOVE["게시물 삭제"]
    CONTENT01_DELETE -->|아니오| CONTENT01_END([종료])
    CONTENT01_REMOVE --> CONTENT01_END
```

- 대응 프로세스: [P-DOMAIN-CONTENT-01](processes.md#p-domain-content-01)
- 관련 finding: [V-DOMAIN-NOTICE-01](validation.md#v-domain-notice-01), [V-UI-PAGING-01](validation.md#v-ui-paging-01)
- 추론: 두 게시물을 한 플로우에 묶은 것은 편집 편의를 위한 것이며, 프로세스 표에서도 하나로 다룬다.
