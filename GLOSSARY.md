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
| 관리자 | `admin` | 관리자 역할을 가진 계정. 회원가입 불가, 초기 계정은 시드로 만든다 | 운영자 |
| 회원 | `account` | 이용자·호스트·관리자를 포괄하는 단일 로그인 계정 | 계정(로그인 계정과 혼동) |
| 역할 | `role` | 계정이 보유한 자격. `MEMBER`, `HOST`, `ADMIN`을 함께 가질 수 있다 | 등급, 권한 |
| 회원식별자 | `accountId` | 계정과 소유 데이터를 식별하는 내부 식별자 | 회원번호, 회원코드 |
| 호스트 프로필 | `host_profile` | 호스트 역할에 딸린 사업자·정산 계좌 정보. 검수 대상 | 호스트 정보 |
| 블랙리스트 | `sanction` | 관리자 판단으로 서비스 이용을 제한하는 상태 | 블랙 |
| 경고 | `warning` | 신고 인정 시 누적되는 제재 카운트 | 벌점 |
| 탈퇴회원 | `accounts.withdrawn_at` | 탈퇴 후 식별정보를 분리 보관하는 계정. 게시물에는 "알 수 없는 회원"으로 표시 | 탈퇴자 |

확정: 블랙리스트는 기간제 누적(1차 7일, 2차 30일, 3차 영구)이며 로그인·예약 조회·환전·탈퇴만 허용한다 ([ADR 0005](docs/adr/0005-unified-account-model.md)). 남은 쟁점: 탈퇴 데이터 보존 [V-DATA-RETENTION-01](docs/spec/lookation/validation.md#v-data-retention-01).

## 공간

| 용어 | 코드 표기 | 정의 | 쓰지 않는 말 |
| --- | --- | --- | --- |
| 공간 | `space`, `spaces` | 호스트가 등록해 대여하는 장소 단위 | 매물, 룸, 공간 게시물, loc |
| 공간유형 | `SpaceType` | 파티룸·엠티공간·루프탑·브라이덜샤워·클럽·워크샵장소 6종. 추가는 마이그레이션으로만 | 카테고리, LOC_TYPE |
| 검수 | `inspection` | 관리자가 공간 등록의 적법성·필수 정보를 확인하는 절차 | 심사, 승인 검토 |
| 검수 대기 | `PENDING_INSPECTION` | 검수 신청 후 관리자 처리 전 | 인증 대기, 등록 대기 |
| 검수 승인 | `PUBLISHED` | 관리자가 공간 게시를 승인한 상태 | 검수 완료 |
| 검수 반려 | `INSPECTION_REJECTED` | 관리자가 등록을 거부한 상태 | 검수 기각 |
| 검수 중단 | `SUSPENDED` | 게시 후 관리자가 노출을 중단한 상태 | 블라인드(공간) |
| 블라인드 | `blind`, `loc_blind_code` | 게시물을 목록·상세에서 숨기되 데이터는 남기는 처리 | 숨김, 비공개 |
| 가격 규칙 | `price_rule` | 공간에서 판매하는 시간대별 가격표(시작·종료 시각, 가격) | 패키지, 상품, 요금제 |

확정: 검수 상태는 `DRAFT`, `PENDING_INSPECTION`, `INSPECTION_REJECTED`, `PUBLISHED`, `SUSPENDED`, `CLOSED` 6개다. 반려 후 재신청은 허용하고, 재신청마다 `inspections` 새 행을 남긴다. 공간유형은 6종으로 고정한다.

## 예약·결제

| 용어 | 코드 표기 | 정의 | 쓰지 않는 말 |
| --- | --- | --- | --- |
| 예약 | `reservation` | 이용자가 공간의 시간대를 확보하는 거래 | booking, 주문 |
| 예약 대기 | `PENDING_APPROVAL`, `PENDING_PAYMENT` | 호스트 승인 또는 결제를 기다리는 예약 | 신청 |
| 예약완료 | `CONFIRMED` | 결제까지 끝나 확정된 예약 | 결제완료 |
| 이용완료 | `COMPLETED` | 체크아웃 이후 이용이 끝난 상태 | 사용완료 |
| 예약취소 | `CANCELLED` | 취소된 예약. 환불은 별도 상태·원장 항목으로 남는다 | 환불완료 |
| 예약만료 | `EXPIRED` | 승인·결제 시한을 넘긴 예약 | 자동취소 |
| 에스크로 | `HOLD`, `RELEASE` | 이용완료 전까지 결제대금을 사이트가 보관하는 방식 | 예치 |
| 바로결제 | `direct payment` | 예약 시점에 마일리지로 즉시 결제 | 즉시결제 |
| 마일리지 | `mileage` | 사이트 안에서만 쓰는 결제 수단. 잔액과 거래 이력을 가진다 | 포인트, 캐시 |
| 지갑 | `wallet` | 계정별 마일리지 잔액 | 캐시, 포인트 |
| 원장 항목 | `wallet_entry` | 지갑 잔액을 바꾸는 append-only 거래 기록 | 거래내역 |
| 충전 | `charge` | 계좌 입금을 관리자가 확인해 마일리지를 늘리는 처리 | 입금, 데포짓 |
| 출금 | `withdrawal` | 보유 마일리지를 계좌로 지급 신청하는 처리 | 환전, 정산 |
| 정산 | `settlement` | 이용완료 예약 금액에서 수수료를 뺀 몫을 호스트 지갑에 자동 입금하는 처리 | 환전(호스트) |
| 수수료 | `fee` | 정산 시 차감하는 비율. 10%, 원 단위 내림, 부가세 포함 | 중개료 |

확정: 예약 상태 집합과 승인예약 포함 ([ADR 0006](docs/adr/0006-reservation-state-machine.md)), 자동 정산과 원장 ([ADR 0007](docs/adr/0007-wallet-ledger-settlement.md)), 수수료 10%·원 단위 내림·부가세 포함.

## 게시물·소통

| 용어 | 코드 표기 | 정의 | 쓰지 않는 말 |
| --- | --- | --- | --- |
| 리뷰 | `REVIEW` | 이용완료 후 남기는 별점·내용·사진 게시물 | 후기 |
| 답글 | `REVIEW_REPLY`, `QNA_REPLY` | 호스트가 리뷰·Q&A에 남기는 공개 답변 | 댓글 |
| Q&A | `QNA` | 공간에 대한 이용자 질문과 호스트 답글 | 문의 |
| 신고 | `LOC_REPORT`, `BOOK_REPORT` | 운영정책 위반을 관리자에게 알리는 행위 | 제보 |
| 신고 처리 | `REPORT_PROC_TYPE` | 관리자가 신고를 인정 또는 반려로 판정. 자동 블라인드는 없다 | 신고 심사 |
| PIRI | `message`, `messages` | 예약 당사자(이용자·호스트)의 1:1 메시지 스레드. 표시명은 PIRI | DM, 메신저, 1:1문의 |
| 공지사항 | `NOTICE` | 관리자가 등록하는 서비스 공지. 상단 고정 가능 | 공지 |
| 도움말 | `HELP` | 카테고리별 서비스 안내 문서 | FAQ |

확정: PIRI 표시명·`message` 코드, 신고 자동 블라인드 없음(관리자 판단), 리뷰는 `COMPLETED` 예약 1건당 1개·30일 이내.

## 상태 값

| 대상 | 상태 |
| --- | --- |
| 계정 | `ACTIVE`, `WITHDRAWN` (활성 제재는 `sanctions`로 표현) |
| 검수 | `DRAFT`, `PENDING_INSPECTION`, `INSPECTION_REJECTED`, `PUBLISHED`, `SUSPENDED`, `CLOSED` |
| 예약 | `PENDING_APPROVAL`, `PENDING_PAYMENT`, `CONFIRMED`, `COMPLETED`, `CANCELLED`, `REFUNDED`, `REJECTED`, `EXPIRED` |
| 제재 | `active`, `expired`, `revoked` (`ends_at IS NULL` = 영구) |
| 충전 | `REQUESTED`, `APPROVED`, `REJECTED` |
| 출금 | `REQUESTED`, `APPROVED`, `PAID`, `REJECTED` |
| 신고 | `RECEIVED`, `ACCEPTED`, `REJECTED` |

미정: 게시물 노출 상태. 확정 전에는 구현하지 않는다.
