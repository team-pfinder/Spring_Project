# GLOSSARY

Lookation의 업무 용어와 코드 표기를 하나로 고정한다. 이슈·ADR·리팩터링 제안·테스트 이름에서 도메인 개념을 부를 때는 아래 표기를 쓴다.

전체 분석(정의·동의어·충돌·출처)은 [`docs/spec/lookation/glossary.md`](docs/spec/lookation/glossary.md)에 있다. 이 파일은 그중 구현에 바로 쓰는 핵심만 추린다.

## 표기 원칙

1. 화면·기획서 용어와 코드·DB 이름이 다르면 둘 다 적는다. 코드에서는 `코드 표기` 열을 쓴다.
2. 같은 대상을 다른 이름으로 부르지 않는다. 아래 `쓰지 않는 말`은 코드·문서에서 피한다.
3. 충돌이 확정되지 않은 용어는 `쟁점` 열에 finding ID를 적는다. 구현 전에 결정한다.

## 회원

| 용어 | 코드 표기 | 정의 | 쓰지 않는 말 |
| --- | --- | --- | --- |
| 이용자 | `member`, `MEMBER` | 공간을 검색·예약·이용하고 리뷰·Q&A를 쓰는 회원 | 유저, 사용자 |
| 호스트 | `host`, `HOST` | 공간을 등록·관리하고 예약을 받는 회원 | 판매자, 업주 |
| 관리자 | `admin` | 관리자 전용 사이트 계정. 회원가입 불가, DB 직접 입력 | 운영자 |
| 회원 | `account` | 이용자와 호스트를 합친 상위 개념. 코드에서는 역할별 테이블로 갈린다 | 계정(로그인 계정과 혼동) |
| 회원고유코드 | `accountCode`, `member_code` / `host_code` | 계정과 소유 데이터를 식별하는 코드 | 회원번호 |
| 블랙리스트 | `MEMBER_BLACKLIST` | 관리자 판단으로 서비스 이용을 제한하는 상태 | 블랙 |
| 경고 | `warning` | 신고 인정 시 누적되는 제재 카운트 | 벌점 |
| 탈퇴회원 | `MEMBER_WITHDRAW`, `HOST_WITHDRAW` | 탈퇴 후 식별정보를 분리 보관하는 회원. 게시물에는 "알 수 없는 회원"으로 표시 | 탈퇴자 |

쟁점: 블랙리스트 기간·허용 기능 [V-ACCOUNT-BLACKLIST-01](docs/spec/lookation/validation.md#v-account-blacklist-01), [V-PERM-BLACKLIST-01](docs/spec/lookation/validation.md#v-perm-blacklist-01). 탈퇴 데이터 보존 [V-DATA-RETENTION-01](docs/spec/lookation/validation.md#v-data-retention-01).

## 공간

| 용어 | 코드 표기 | 정의 | 쓰지 않는 말 |
| --- | --- | --- | --- |
| 공간 | `loc`, `LOC` | 호스트가 등록해 대여하는 장소 단위 | 매물, 룸, 공간 게시물 |
| 공간유형 | `LOC_TYPE` | 파티룸·루프탑 등 공간 분류 | 카테고리 |
| 검수 | `INSPECT_REG_LIST`, `INSPECT_TYPE` | 관리자가 공간 등록의 적법성·필수 정보를 확인하는 절차 | 심사, 승인 검토 |
| 검수 대기 | `inspect` 상태 | 검수 신청 후 관리자 처리 전 | 인증 대기, 등록 대기 |
| 검수 승인 | `inspect` 상태 | 관리자가 공간 게시를 승인한 상태 | 검수 완료 |
| 검수 반려 | `inspect` 상태 | 관리자가 등록을 거부한 상태 | 검수 기각 |
| 블라인드 | `blind`, `loc_blind_code` | 게시물을 목록·상세에서 숨기되 데이터는 남기는 처리 | 숨김, 비공개 |
| 패키지 | `PACKAGE` | 공간에서 판매하는 시간 단위 상품 | 상품, 요금제 |
| 패키지 적용 | `APPLY_PACKAGE` | 패키지를 특정 날짜에 배치해 예약 가능하게 하는 행위 | 예약 등록 |

쟁점: 검수 상태 이름·단계 [V-STATUS-INSPECT-01](docs/spec/lookation/validation.md#v-status-inspect-01), 반려 후 재신청 [V-STATUS-INSPECT-02](docs/spec/lookation/validation.md#v-status-inspect-02), 공간유형 목록 [V-DOMAIN-SPACE-02](docs/spec/lookation/validation.md#v-domain-space-02).

## 예약·결제

| 용어 | 코드 표기 | 정의 | 쓰지 않는 말 |
| --- | --- | --- | --- |
| 예약 | `book`, `BOOK_LIST` | 이용자가 특정 날짜의 패키지를 확보하는 거래 | booking, 주문 |
| 예약완료 | `book` 상태 | 결제까지 끝나 확정된 예약 | 결제완료 |
| 이용완료 | `book` 상태 | 체크아웃 이후 이용이 끝난 상태 | 사용완료 |
| 예약취소 | `book` 상태 | 취소되어 환불까지 끝난 상태 | 환불완료 |
| 에스크로 | `escrow` | 이용완료 전까지 결제대금을 사이트가 보관하는 방식 | 예치 |
| 바로결제 | `direct payment` | 예약 시점에 마일리지로 즉시 결제 | 즉시결제 |
| 마일리지 | `mileage` | 사이트 안에서만 쓰는 결제 수단. 잔액과 거래 이력을 가진다 | 포인트, 캐시 |
| 충전 | `charge`, `LOAD_REG` | 계좌 입금을 관리자가 확인해 마일리지를 늘리는 처리 | 입금, 데포짓 |
| 환전 | `exchange`, `*_EXCHANGE_LIST` | 보유 마일리지를 회원 계좌로 지급하는 처리 | 출금, 정산 |
| 정산 | `settlement` | 이용완료 예약 금액에서 수수료를 뺀 몫을 호스트에게 지급 | 환전(호스트) |
| 수수료 | `fee` | 정산 시 차감하는 비율 | 중개료 |

쟁점: 예약 상태 집합 [V-STATUS-RESERVATION-01](docs/spec/lookation/validation.md#v-status-reservation-01), 정산 주체 [V-PROC-SETTLE-01](docs/spec/lookation/validation.md#v-proc-settle-01), 수수료율 [V-DOMAIN-MILEAGE-SETTLE-01](docs/spec/lookation/validation.md#v-domain-mileage-settle-01), 승인예약 [V-INT-PAYMENT-01](docs/spec/lookation/validation.md#v-int-payment-01).

## 게시물·소통

| 용어 | 코드 표기 | 정의 | 쓰지 않는 말 |
| --- | --- | --- | --- |
| 리뷰 | `REVIEW` | 이용완료 후 남기는 별점·내용·사진 게시물 | 후기 |
| 답글 | `REVIEW_REPLY`, `QNA_REPLY` | 호스트가 리뷰·Q&A에 남기는 공개 답변 | 댓글 |
| Q&A | `QNA` | 공간에 대한 이용자 질문과 호스트 답글 | 문의 |
| 신고 | `LOC_REPORT`, `BOOK_REPORT` | 운영정책 위반을 관리자에게 알리는 행위 | 제보 |
| 신고 처리 | `REPORT_PROC_TYPE` | 관리자가 신고를 인정 또는 반려로 판정 | 신고 심사 |
| PIRI | `MSG`, `MEMBER_MSG_INFO`, `HOST_MSG_INFO` | 예약한 이용자와 호스트의 1:1 메신저 | DM, 메신저, 1:1문의 |
| 공지사항 | `NOTICE` | 관리자가 등록하는 서비스 공지. 상단 고정 가능 | 공지 |
| 도움말 | `HELP` | 카테고리별 서비스 안내 문서 | FAQ |

쟁점: PIRI 명칭 [V-TERM-MESSENGER-01](docs/spec/lookation/validation.md#v-term-messenger-01), 신고 자동 블라인드 기준 [V-PROC-REPORT-01](docs/spec/lookation/validation.md#v-proc-report-01), 리뷰 자격 [V-DOMAIN-REVIEW-02](docs/spec/lookation/validation.md#v-domain-review-02).

## 상태 값

상태 값의 최종 집합은 아직 확정되지 않았다. 구현할 때는 [`docs/spec/lookation/domain.md#상태-전이`](docs/spec/lookation/domain.md#상태-전이)를 기준으로 하고, 임의로 새 값을 만들지 않는다.

| 대상 | 문서에 나온 값 | 쟁점 |
| --- | --- | --- |
| 계정 | 정상 / 블랙리스트 / 탈퇴 | 기간제 여부 |
| 검수 | 대기 / 승인 / 반려 | 단계 수·이름 |
| 예약 | 예약완료 / 이용완료 / 예약취소 (+환불신청·환불완료) | 3단계 vs 4단계 |
| 충전 신청 | 대기 / 완료 / 실패 | 실패 이력 저장 |
| 환전 신청 | 신청 / 완료 | 상태 컬럼 관리 여부 |
| 신고 | 접수 / 인정 / 반려 | 자동 블라인드 임계값 |
| 게시물 노출 | 정상 / 블라인드 / 삭제 | — |
