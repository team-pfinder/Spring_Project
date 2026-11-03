# 테이블 명세서

- 프로젝트: Lookation
- 생성 시각: 2026-11-02T17:14:31+09:00
- SQL 방언: oracle
- 원본: DDL 파일 1개
- 테이블: 76
- 컬럼: 278
- 관계: 78
- 인덱스: 0

> 이 문서는 SQL DDL을 기준으로 생성했습니다. 런타임 DB 상태와 다를 수 있습니다.

## 테이블 목록

| No | 스키마 | 테이블 | 설명 | 컬럼 | PK | FK | 인덱스 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | - | ACTUAL_BOOKER | 실예약자 AB | 4 | ACTUAL_BOOKER_CODE | 1 | 0 |
| 2 | - | ADMIN | 관리자 | 3 | ADMIN_ID | 0 | 0 |
| 3 | - | APPLY_PACKAGE | 적용된 패키지 AP | 3 | APPLY_PACKAGE_CODE | 1 | 0 |
| 4 | - | ATTACHMENT | 첨부파일(공지사항) AT | 3 | ATTACHMENT_CODE | 1 | 0 |
| 5 | - | BIZ_INFO | 사업자정보 BZ | 10 | BIZ_INFO_CODE | 1 | 0 |
| 6 | - | BOARD_TYPE | 게시물 분류 BT | 2 | BOARD_TYPE_CODE | 0 | 0 |
| 7 | - | BOOK_LIST | 예약내역 BC | 6 | BOOK_CODE | 2 | 0 |
| 8 | - | BOOK_REFUND_LIST | 예약 환불 내역(이용자) BRF | 3 | BOOK_REFUND_CODE | 1 | 0 |
| 9 | - | BOOK_REPORT | 신고접수(이용자)(예약내역) BRP | 6 | BOOK_REPORT_CODE | 3 | 0 |
| 10 | - | BOOK_REPORT_IMG | 이미지(신고) BRPIMG | 3 | BOOK_REPORT_IMG_CODE | 1 | 0 |
| 11 | - | BOOK_REPORT_PROC | 신고처리(이용자)(예약내역) BRPP | 4 | BOOK_REPORT_PROC_CODE | 2 | 0 |
| 12 | - | BOOK_REPORT_TYPE | 신고유형분류(예약내역) BRPT | 2 | BOOK_REPORT_TYPE_CODE | 0 | 0 |
| 13 | - | Book_Pay_List | 예약 결제 내역(이용자) BP | 3 | BOOK_PAY_CODE | 1 | 0 |
| 14 | - | CAUTION | 주의사항(기본정보)  CAU | 3 | CAUTION_CODE | 1 | 0 |
| 15 | - | FACILITY_INFO | 시설안내(기본정보) FIF | 3 | FACILITY_CODE | 1 | 0 |
| 16 | - | HELP | 도움말 HLP | 5 | HELP_CODE | 1 | 0 |
| 17 | - | HELP_IMG | 이미지(도움말) HLPIMG | 3 | HELP_IMG_CODE | 1 | 0 |
| 18 | - | HOST | 회원(호스트) H | 2 | HOST_CODE | 0 | 0 |
| 19 | - | HOST_BANK_INFO | 계좌정보(호스트) | 4 | HOST_BANK_NUMBER | 1 | 0 |
| 20 | - | HOST_BLACKLIST | 블랙리스트(호스트) | 3 | HOST_EMAIL | 0 | 0 |
| 21 | - | HOST_CANCEL_LIST | 예약취소내역(호스트) HC0000 | 4 | HOST_CANCEL_CODE | 1 | 0 |
| 22 | - | HOST_EXCHANGE_BANK_INFO | 환전계좌정보(호스트) HEBI | 3 | HOST_EXCHANGE_BANK_INFO_CODE | 2 | 0 |
| 23 | - | HOST_EXCHANGE_LIST | 마일리지 환전 내역(호스트) HE | 4 | HOST_EXCHANGE_CODE | 1 | 0 |
| 24 | - | HOST_MSG_IMG | 이미지(메신저정보(호스트)) HMIMG | 3 | HOST_MSG_IMG_CODE | 1 | 0 |
| 25 | - | HOST_MSG_INFO | 메신저정보(호스트) HMIF | 4 | HOST_MSG_INFO_CODE | 1 | 0 |
| 26 | - | HOST_PROFILE | 프로필정보(호스트) | 6 | HOST_EMAIL | 1 | 0 |
| 27 | - | HOST_WITHDRAW | 회원탈퇴(호스트) HW | 3 | HOST_WITHDRAW_CODE | 1 | 0 |
| 28 | - | IMPORTANT_NOTICE | 중요공지 분류 IN | 2 | IMPORTANT_NOTICE_CODE | 0 | 0 |
| 29 | - | INSPECT_PROC_LIST | 검수처리내역 IP | 5 | INSPECT_PROC_CODE | 2 | 0 |
| 30 | - | INSPECT_REG_LIST | 검수신청내역 IR | 3 | INSPECT_REG_CODE | 1 | 0 |
| 31 | - | INSPECT_TYPE | 검수분류 IT | 2 | INSPECT_TYPE_CODE | 0 | 0 |
| 32 | - | LOAD_PROC | 충전처리내역(이용자) LP | 4 | LOAD_PROC_CODE | 2 | 0 |
| 33 | - | LOAD_REG | 마일리지 충전신청 내역(이용자) LR | 4 | LOAD_REG_CODE | 1 | 0 |
| 34 | - | LOAD_REG_BANK_INFO | 충전신청계좌정보 LRBI | 3 | LOAD_REG_BANK_INFO_CODE | 2 | 0 |
| 35 | - | LOAD_TYPE | 마일리지 충전 분류 LT | 2 | LOAD_TYPE_CODE | 0 | 0 |
| 36 | - | LOC | 공간 L | 3 | LOC_CODE | 1 | 0 |
| 37 | - | LOC_BASIC_INFO | 기본정보 LBIF | 8 | LOC_BASIC_INFO_CODE | 2 | 0 |
| 38 | - | LOC_BLIND | 블라인드내역(공간게시물) LB | 4 | LOC_BLIND_CODE | 1 | 0 |
| 39 | - | LOC_CONTACT | 연락처 LC | 5 | LOC_CONTACT_CODE | 1 | 0 |
| 40 | - | LOC_DETAIL_IMG | 이미지(상세정보) LDIMG | 3 | LOC_DETAIL_IMG_CODE | 1 | 0 |
| 41 | - | LOC_DETAIL_INFO | 상세정보 LDIF | 4 | LOC_DETAIL_INFO_CODE | 1 | 0 |
| 42 | - | LOC_REMOVE | 삭제내역(공간) LRM | 3 | LOC_REMOVE_CODE | 1 | 0 |
| 43 | - | LOC_REPORT | 신고접수(이용자)(공간게시물) LRP | 6 | LOC_REPORT_CODE | 3 | 0 |
| 44 | - | LOC_REPORT_IMG | 이미지(신고) LRPIMG | 3 | LOC_REPORT_IMG_CODE | 1 | 0 |
| 45 | - | LOC_REPORT_PROC | 신고처리(이용자)(공간게시물) LRPP | 4 | LOC_REPORT_PROC_CODE | 2 | 0 |
| 46 | - | LOC_REPORT_TYPE | 신고유형분류(공간) LRPT | 2 | LOC_REPORT_TYPE_CODE | 0 | 0 |
| 47 | - | LOC_TYPE | 공간분류 LOCT | 2 | LOC_TYPE_CODE | 0 | 0 |
| 48 | - | LOC_USE_INFO | 이용안내 LUI | 5 | LOC_USE_INFO_CODE | 1 | 0 |
| 49 | - | LOC_WEB | 공간웹사이트(상세정보)  LW | 3 | LOC_WEB_CODE | 1 | 0 |
| 50 | - | MEMBER | 회원(이용자) M | 2 | MEMBER_CODE | 0 | 0 |
| 51 | - | MEMBER_BANK_INFO | 계좌정보(이용자) | 4 | MEMBER_BANK_NUMBER | 1 | 0 |
| 52 | - | MEMBER_BLACKLIST | 블랙리스트(이용자) | 3 | MEMBER_EMAIL | 0 | 0 |
| 53 | - | MEMBER_CANCEL_LIST | 예약취소내역(이용자) MC | 4 | MEMBER_CANCEL_CODE | 1 | 0 |
| 54 | - | MEMBER_EXCHANGE_BANK_INFO | 환전계좌정보(이용자) MEBI | 3 | MEMBER_EXCHANGE_BANK_INFO_CODE | 2 | 0 |
| 55 | - | MEMBER_EXCHANGE_LIST | 마일리지 환전 내역(이용자) ME | 4 | MEMBER_EXCHANGE_CODE | 1 | 0 |
| 56 | - | MEMBER_MSG_IMG | 이미지(메신저정보(이용자)) MMIMG | 3 | MEMBER_MSG_IMG_CODE | 1 | 0 |
| 57 | - | MEMBER_MSG_INFO | 메신저정보(이용자) MMIF | 4 | MEMBER_MSG_INFO_CODE | 1 | 0 |
| 58 | - | MEMBER_PROFILE | 프로필정보(이용자) | 6 | MEMBER_EMAIL | 1 | 0 |
| 59 | - | MEMBER_WITHDRAW | 탈퇴(이용자) MW | 3 | MEMBER_WITHDRAW_CODE | 1 | 0 |
| 60 | - | MSG | 메신저 MSG | 2 | MSG_CODE | 1 | 0 |
| 61 | - | NOTICE | 공지사항 N | 5 | NOTICE_CODE | 1 | 0 |
| 62 | - | NOTICE_IMG | 이미지(공지사항) NIMG | 3 | NOTICE_IMG_CODE | 1 | 0 |
| 63 | - | PACKAGE | 패키지 P | 6 | PACKAGE_CODE | 1 | 0 |
| 64 | - | PACKAGE_FORM | 패키지 양식 PF | 2 | PACKAGE_FORM_CODE | 1 | 0 |
| 65 | - | PACKAGE_REMOVE | 폐기 패키지 PRM | 3 | PACKAGE_REMOVE_CODE | 1 | 0 |
| 66 | - | QNA | Q_AND_A(이용자) Q | 5 | QNA_CODE | 2 | 0 |
| 67 | - | QNA_REMOVE | 삭제내역(Q_AND_A) QRM | 3 | QNA_REMOVE_CODE | 1 | 0 |
| 68 | - | QNA_REPLY | Q_AND_A답글(호스트) QRE | 4 | QNA_REPLY_CODE | 1 | 0 |
| 69 | - | QNA_REPLY_REMOVE | 삭제내역(Q_AND_A답글) QRERM | 3 | QNA_REPLY_REMOVE_CODE | 1 | 0 |
| 70 | - | REPORT_PROC_TYPE | 신고처리분류 RPPT | 2 | REPORT_PROC_TYPE_CODE | 0 | 0 |
| 71 | - | REVIEW | 리뷰(이용자) RV | 6 | REVIEW_CODE | 2 | 0 |
| 72 | - | REVIEW_IMG | 이미지(리뷰) RVIMG | 3 | REVIEW_IMG_CODE | 1 | 0 |
| 73 | - | REVIEW_REMOVE | 삭제내역(리뷰)  RVRM | 3 | REVIEW_REMOVE_CODE | 1 | 0 |
| 74 | - | REVIEW_REPLY | 리뷰답글(호스트) RVRE | 4 | REVIEW_REPLY_CODE | 1 | 0 |
| 75 | - | REVIEW_REPLY_REMOVE | 삭제내역(리뷰답글) RVRERM | 3 | REVIEW_REPLY_REMOVE_CODE | 1 | 0 |
| 76 | - | THUMBNAIL | 썸네일(기본정보) TH | 3 | THUMBNAIL_CODE | 1 | 0 |

## ACTUAL_BOOKER — 실예약자 AB

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | ACTUAL_BOOKER_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 실예약자 코드 | - |
| 2 | BOOK_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 예약코드 | - |
| 3 | ACTUAL_BOOKER | VARCHAR2 | 30 | - | - | - | - | - | 예약자명 | - |
| 4 | ACTUAL_BOOKER_TEL | VARCHAR2 | 13 | - | - | - | - | - | 예약자 연락처 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_ACTUAL_BOOKER_BOOK_CODE_BOO | BOOK_CODE | BOOK_LIST | BOOK_CODE | - | - |

## ADMIN — 관리자

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | ADMIN_ID | VARCHAR2 | 18 | Y | - | - | Y | - | 아이디 | - |
| 2 | ADMIN_PW | VARCHAR2 | 15 | - | - | - | - | - | 비밀번호 | - |
| 3 | ADMIN_NICKNAME | VARCHAR2 | 18 | - | - | - | - | - | 닉네임 | - |

## APPLY_PACKAGE — 적용된 패키지 AP

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | APPLY_PACKAGE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 적용된패키지코드 | - |
| 2 | PACKAGE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 패키지코드 | - |
| 3 | APPLY_DATE | DATE | - | - | - | - | - | - | 적용일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_APPLY_PACKAGE_PACKAGE_CODE_ | PACKAGE_CODE | PACKAGE | PACKAGE_CODE | - | - |

## ATTACHMENT — 첨부파일(공지사항) AT

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | ATTACHMENT_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 첨부파일코드 | - |
| 2 | NOTICE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공지사항 코드 | - |
| 3 | ATTACHMENT_URL | VARCHAR2 | 500 | - | - | - | - | - | 첨부파일 링크(url) | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_ATTACHMENT_NOTICE_CODE_NOTI | NOTICE_CODE | NOTICE | NOTICE_CODE | - | - |

## BIZ_INFO — 사업자정보 BZ

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | BIZ_INFO_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 사업자정보코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간코드 | - |
| 3 | BIZ_NAME | VARCHAR2 | 90 | - | - | - | - | - | 상호명 | - |
| 4 | BIZ_CEO | VARCHAR2 | 30 | - | - | - | - | - | 대표자명 | - |
| 5 | BIZ_LICENSE_URL | VARCHAR2 | 500 | - | - | - | - | - | 사업자등록증(url) | - |
| 6 | BIZ_CEO_TYPE | VARCHAR2 | 30 | - | - | - | - | - | 사업자유형 | - |
| 7 | BIZ_MAIN_TYPE | VARCHAR2 | 90 | - | - | - | - | - | 주업태 | - |
| 8 | BIZ_SUB_TYPE | VARCHAR2 | 90 | - | - | - | - | - | 주종목 | - |
| 9 | BIZ_ADDR | VARCHAR2 | 200 | - | - | - | - | - | 사업장 주소 | - |
| 10 | BIZ_LICENSE_NUMBER | CHAR | 12 | - | - | - | - | - | 사업자등록번호 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_BIZ_INFO_LOC_CODE_LOC_LOC_C | LOC_CODE | LOC | LOC_CODE | - | - |

## BOARD_TYPE — 게시물 분류 BT

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | BOARD_TYPE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 게시물분류코드 | - |
| 2 | BOARD_TYPE | VARCHAR2 | 30 | - | - | - | - | - | 게시물 유형 | - |

## BOOK_LIST — 예약내역 BC

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | BOOK_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 예약코드 | - |
| 2 | MEMBER_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드(이용자) | - |
| 3 | APPLY_PACKAGE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 적용된 패키지 코드 | - |
| 4 | BOOK_PEOPLE | NUMBER | 2 | - | - | - | - | - | 인원수 | - |
| 5 | BOOK_DATE | DATE | - | - | - | - | - | - | 예약 신청시간 | - |
| 6 | BOOK_REQ | VARCHAR2 | 300 | - | - | - | - | - | 요청사항 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_BOOK_LIST_MEMBER_CODE_MEMBE | MEMBER_CODE | MEMBER | MEMBER_CODE | - | - |
| FK_BOOK_LIST_APPLY_PACKAGE_COD | APPLY_PACKAGE_CODE | APPLY_PACKAGE | APPLY_PACKAGE_CODE | - | - |

## BOOK_REFUND_LIST — 예약 환불 내역(이용자) BRF

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | BOOK_REFUND_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 환불코드 | - |
| 2 | BOOK_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 예약코드 | - |
| 3 | BOOK_REFUND_DATE | DATE | - | - | - | - | - | - | 환불일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_BOOK_REFUND_LIST_BOOK_CODE_ | BOOK_CODE | BOOK_LIST | BOOK_CODE | - | - |

## BOOK_REPORT — 신고접수(이용자)(예약내역) BRP

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | BOOK_REPORT_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 신고접수코드 | - |
| 2 | BOOK_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 예약코드 | - |
| 3 | BOOK_REPORT_TYPE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 신고유형분류코드 | - |
| 4 | HOST_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드(신고자)(호스트) | - |
| 5 | BOOK_REPORT_REASON | VARCHAR2 | 300 | - | - | - | - | - | 신고사유 | - |
| 6 | BOOK_REPORT_DATE | DATE | - | - | - | - | - | - | 신고일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_BOOK_REPORT_BOOK_REPORT_TYP | BOOK_REPORT_TYPE_CODE | BOOK_REPORT_TYPE | BOOK_REPORT_TYPE_CODE | - | - |
| FK_BOOK_REPORT_BOOK_CODE_BOOK_ | BOOK_CODE | BOOK_LIST | BOOK_CODE | - | - |
| FK_BOOK_REPORT_HOST_CODE_HOST_ | HOST_CODE | HOST | HOST_CODE | - | - |

## BOOK_REPORT_IMG — 이미지(신고) BRPIMG

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | BOOK_REPORT_IMG_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 이미지코드 | - |
| 2 | BOOK_REPORT_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 신고접수코드 | - |
| 3 | BOOK_REPORT_IMG_URL | VARCHAR2 | 500 | - | - | - | - | - | 이미지 링크(url) | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_BOOK_REPORT_IMG_BOOK_REPORT | BOOK_REPORT_CODE | BOOK_REPORT | BOOK_REPORT_CODE | - | - |

## BOOK_REPORT_PROC — 신고처리(이용자)(예약내역) BRPP

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | BOOK_REPORT_PROC_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 신고처리코드 | - |
| 2 | BOOK_REPORT_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 신고접수코드 | - |
| 3 | REPORT_PROC_TYPE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 신고처리분류코드 | - |
| 4 | BOOK_REPORT_PROC_DATE | DATE | - | - | - | - | - | - | 처리일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_BOOK_REPORT_PROC_BOOK_REPOR | BOOK_REPORT_CODE | BOOK_REPORT | BOOK_REPORT_CODE | - | - |
| FK_BOOK_REPORT_PROC_REPORT_PRO | REPORT_PROC_TYPE_CODE | REPORT_PROC_TYPE | REPORT_PROC_TYPE_CODE | - | - |

## BOOK_REPORT_TYPE — 신고유형분류(예약내역) BRPT

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | BOOK_REPORT_TYPE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 신고유형분류코드 | - |
| 2 | BOOK_REPORT_TYPE | VARCHAR2 | 50 | - | - | - | - | - | 신고 유형 | - |

## Book_Pay_List — 예약 결제 내역(이용자) BP

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | BOOK_PAY_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 결제코드 | - |
| 2 | BOOK_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 예약코드 | - |
| 3 | BOOK_PAY_DATE | DATE | - | - | - | - | - | - | 결제일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_Book_Pay_List_BOOK_CODE_BOO | BOOK_CODE | BOOK_LIST | BOOK_CODE | - | - |

## CAUTION — 주의사항(기본정보)  CAU

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | CAUTION_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 주의사항 코드 | - |
| 2 | LOC_BASIC_INFO_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 기존정보코드 | - |
| 3 | CAUTION_CONTENT | VARCHAR2 | 450 | - | - | - | - | - | 주의사항 내용 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_CAUTION_LOC_BASIC_INFO_CODE | LOC_BASIC_INFO_CODE | LOC_BASIC_INFO | LOC_BASIC_INFO_CODE | - | - |

## FACILITY_INFO — 시설안내(기본정보) FIF

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | FACILITY_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 시설안내 코드 | - |
| 2 | LOC_BASIC_INFO_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 기존정보코드 | - |
| 3 | FACILITY_CONTENT | VARCHAR2 | 450 | - | - | - | - | - | 시설안내내용 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_FACILITY_INFO_LOC_BASIC_INF | LOC_BASIC_INFO_CODE | LOC_BASIC_INFO | LOC_BASIC_INFO_CODE | - | - |

## HELP — 도움말 HLP

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HELP_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 도움말 코드 | - |
| 2 | BOARD_TYPE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 게시물분류코드 | - |
| 3 | HELP_TITLE | VARCHAR2 | 60 | - | - | - | - | - | 제목 | - |
| 4 | HELP_CONTENT | VARCHAR2 | 3000 | - | - | - | - | - | 내용 | - |
| 5 | HELP_DATE | DATE | - | - | - | - | - | - | 작성일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_HELP_BOARD_TYPE_CODE_BOARD_ | BOARD_TYPE_CODE | BOARD_TYPE | BOARD_TYPE_CODE | - | - |

## HELP_IMG — 이미지(도움말) HLPIMG

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HELP_IMG_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 이미지코드 | - |
| 2 | HELP_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 도움말 코드 | - |
| 3 | HELP_IMG_URL | VARCHAR2 | 500 | - | - | - | - | - | 이미지 링크(url) | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_HELP_IMG_HELP_CODE_HELP_HEL | HELP_CODE | HELP | HELP_CODE | - | - |

## HOST — 회원(호스트) H

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HOST_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 회원코드(호스트) | - |
| 2 | HOST_SIGN_UP_DATE | DATE | - | - | - | - | - | - | 가입일자 | - |

## HOST_BANK_INFO — 계좌정보(호스트)

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HOST_BANK_NUMBER | VARCHAR2 | 20 | Y | - | - | Y | - | 계좌번호 | - |
| 2 | HOST_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드(호스트) | - |
| 3 | HOST_BANK | VARCHAR2 | 40 | - | - | - | - | - | 은행명 | - |
| 4 | HOST_BANK_HOLDER | VARCHAR2 | 30 | - | - | - | - | - | 예금주 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_HOST_BANK_INFO_HOST_CODE_HO | HOST_CODE | HOST | HOST_CODE | - | - |

## HOST_BLACKLIST — 블랙리스트(호스트)

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HOST_EMAIL | VARCHAR2 | 320 | Y | - | - | Y | - | 가입 이메일 | - |
| 2 | HOST_BLACKLIST_REASON | VARCHAR2 | 300 | - | - | - | - | - | 블랙리스트 사유 | - |
| 3 | HOST_BLACKLIST_DATE | DATE | - | - | - | - | - | - | 블랙리스트 전환일 | - |

## HOST_CANCEL_LIST — 예약취소내역(호스트) HC0000

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HOST_CANCEL_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 예약취소코드 | - |
| 2 | BOOK_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 예약코드 | - |
| 3 | HOST_CANCEL_REASON | VARCHAR2 | 300 | - | - | - | - | - | 취소사유 | - |
| 4 | HOST_CANCEL_DATE | DATE | - | - | - | - | - | - | 취소일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_HOST_CANCEL_LIST_BOOK_CODE_ | BOOK_CODE | BOOK_LIST | BOOK_CODE | - | - |

## HOST_EXCHANGE_BANK_INFO — 환전계좌정보(호스트) HEBI

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HOST_EXCHANGE_BANK_INFO_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 환전계좌코드 | - |
| 2 | HOST_EXCHANGE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 환전내역코드 | - |
| 3 | HOST_BANK_NUMBER | VARCHAR2 | 20 | - | Y | - | - | - | 계좌번호 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_HOST_EXCHANGE_BANK_INFO_HOS | HOST_EXCHANGE_CODE | HOST_EXCHANGE_LIST | HOST_EXCHANGE_CODE | - | - |
| FK_HOST_EXCHANGE_BANK_NUMBER | HOST_BANK_NUMBER | HOST_BANK_INFO | HOST_BANK_NUMBER | - | - |

## HOST_EXCHANGE_LIST — 마일리지 환전 내역(호스트) HE

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HOST_EXCHANGE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 환전내역코드 | - |
| 2 | HOST_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드 | - |
| 3 | HOST_EXCHANGE_AMOUNT | NUMBER | 18 | - | - | - | - | - | 환전액수 | - |
| 4 | HOST_EXCHANGE_DATE | DATE | - | - | - | - | - | - | 환전일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_HOST_EXCHANGE_LIST_HOST_COD | HOST_CODE | HOST | HOST_CODE | - | - |

## HOST_MSG_IMG — 이미지(메신저정보(호스트)) HMIMG

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HOST_MSG_IMG_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 이미지코드 | - |
| 2 | HOST_MSG_INFO_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 메신저정보코드 | - |
| 3 | HOST_MSG_IMG_URL | VARCHAR2 | 500 | - | - | - | - | - | 이미지 링크(url) | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_HOST_MSG_IMG_HOST_MSG_INFO_ | HOST_MSG_INFO_CODE | HOST_MSG_INFO | HOST_MSG_INFO_CODE | - | - |

## HOST_MSG_INFO — 메신저정보(호스트) HMIF

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HOST_MSG_INFO_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 메신저정보코드 | - |
| 2 | MSG_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 메신저코드 | - |
| 3 | HOST_MSG_CONTENT | VARCHAR2 | 1500 | - | - | - | - | - | 내용 | - |
| 4 | HOST_MSG_DATE | DATE | - | - | - | - | - | - | 메신저일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_HOST_MSG_INFO_MSG_CODE_MSG_ | MSG_CODE | MSG | MSG_CODE | - | - |

## HOST_PROFILE — 프로필정보(호스트)

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HOST_EMAIL | VARCHAR2 | 320 | Y | - | - | Y | - | 가입 이메일 | - |
| 2 | HOST_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드(호스트) | - |
| 3 | HOST_PW | VARCHAR2 | 30 | - | - | - | - | - | 비밀번호 | - |
| 4 | HOST_NICKNAME | VARCHAR2 | 18 | - | - | - | - | - | 닉네임 | - |
| 5 | HOST_NAME | VARCHAR2 | 30 | - | - | - | - | - | 이름 | - |
| 6 | HOST_TEL | VARCHAR2 | 13 | - | - | - | - | - | 연락처 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_HOST_PROFILE_HOST_CODE_HOST | HOST_CODE | HOST | HOST_CODE | - | - |

## HOST_WITHDRAW — 회원탈퇴(호스트) HW

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | HOST_WITHDRAW_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 회원탈퇴코드 | - |
| 2 | HOST_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드(호스트) | - |
| 3 | HOST_WITHDRAW_DATE | DATE | - | - | - | - | - | - | 탈퇴일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_HOST_WITHDRAW_HOST_CODE_HOS | HOST_CODE | HOST | HOST_CODE | - | - |

## IMPORTANT_NOTICE — 중요공지 분류 IN

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | IMPORTANT_NOTICE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 중요분류코드 | - |
| 2 | IMPORTANT_NOTICE | VARCHAR2 | 20 | - | - | - | - | - | 공지 유형 | - |

## INSPECT_PROC_LIST — 검수처리내역 IP

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | INSPECT_PROC_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 검수처리코드 | - |
| 2 | INSPECT_REG_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 검수신청코드 | - |
| 3 | INSPECT_TYPE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 검수분류코드 | - |
| 4 | INSPECT_PROC_REASON | VARCHAR2 | 300 | - | - | - | - | - | 검수처리사유 | - |
| 5 | INSPECT_PROC_DATE | DATE | - | - | - | - | - | - | 검수처리일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_INSPECT_PROC_LIST_INSPECT_R | INSPECT_REG_CODE | INSPECT_REG_LIST | INSPECT_REG_CODE | - | - |
| FK_INSPECT_PROC_LIST_INSPECT_T | INSPECT_TYPE_CODE | INSPECT_TYPE | INSPECT_TYPE_CODE | - | - |

## INSPECT_REG_LIST — 검수신청내역 IR

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | INSPECT_REG_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 검수신청코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간코드 | - |
| 3 | INSPECT_REG_DATE | DATE | - | - | - | - | - | - | 검수신청일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_INSPECT_REG_LIST_LOC_CODE_L | LOC_CODE | LOC | LOC_CODE | - | - |

## INSPECT_TYPE — 검수분류 IT

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | INSPECT_TYPE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 검수분류코드 | - |
| 2 | INSPECT_TYPE | VARCHAR2 | 20 | - | - | - | - | - | 검수 유형 | - |

## LOAD_PROC — 충전처리내역(이용자) LP

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOAD_PROC_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 충전처리코드 | - |
| 2 | LOAD_REG_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 충전신청코드 | - |
| 3 | LOAD_TYPE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 마일리지 충전 분류 코드 | - |
| 4 | LOAD_PROC_DATE | DATE | - | - | - | - | - | - | 충전처리일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOAD_PROC_LOAD_REG_CODE_LOA | LOAD_REG_CODE | LOAD_REG | LOAD_REG_CODE | - | - |
| FK_LOAD_PROC_LOAD_TYPE_CODE_LO | LOAD_TYPE_CODE | LOAD_TYPE | LOAD_TYPE_CODE | - | - |

## LOAD_REG — 마일리지 충전신청 내역(이용자) LR

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOAD_REG_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 충전신청코드 | - |
| 2 | MEMBER_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드 | - |
| 3 | LOAD_AMOUNT | NUMBER | 7 | - | - | - | - | - | 충전신청액수 | - |
| 4 | LOAD_REG_DATE | DATE | - | - | - | - | - | - | 충전신청일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOAD_REG_MEMBER_CODE_MEMBER | MEMBER_CODE | MEMBER | MEMBER_CODE | - | - |

## LOAD_REG_BANK_INFO — 충전신청계좌정보 LRBI

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOAD_REG_BANK_INFO_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 충전신청계좌코드 | - |
| 2 | LOAD_REG_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 충전신청코드 | - |
| 3 | MEMBER_BANK_NUMBER | VARCHAR2 | 20 | - | Y | - | - | - | 계좌번호 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOAD_REG_BANK_INFO_LOAD_REG | LOAD_REG_CODE | LOAD_REG | LOAD_REG_CODE | - | - |
| FK_LOAD_REG_BANK_INFO_MEMBER_B | MEMBER_BANK_NUMBER | MEMBER_BANK_INFO | MEMBER_BANK_NUMBER | - | - |

## LOAD_TYPE — 마일리지 충전 분류 LT

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOAD_TYPE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 마일리지 충전 분류 코드 | - |
| 2 | LOAD_TYPE | VARCHAR2 | 20 | - | - | - | - | - | 유형 | - |

## LOC — 공간 L

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 공간코드 | - |
| 2 | HOST_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드(호스트) | - |
| 3 | LOC_REG_DATE | DATE | - | - | - | - | - | - | 공간등록일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_HOST_CODE_HOST_HOST_COD | HOST_CODE | HOST | HOST_CODE | - | - |

## LOC_BASIC_INFO — 기본정보 LBIF

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_BASIC_INFO_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 기본정보코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간코드 | - |
| 3 | LOC_TYPE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간분류코드 | - |
| 4 | LOC_NAME | VARCHAR2 | 180 | - | - | - | - | - | 공간명 | - |
| 5 | LOC_SHORT_INTRO | VARCHAR2 | 300 | - | - | - | - | - | 공간한줄소개 | - |
| 6 | LOC_INTRO | VARCHAR2 | 4000 | - | - | - | - | - | 공간소개 | - |
| 7 | LOC_ADDR | VARCHAR2 | 200 | - | - | - | - | - | 주소 | - |
| 8 | LOC_DETAIL_ADDR | VARCHAR2 | 180 | - | - | - | - | - | 상세주소 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_BASIC_INFO_LOC_CODE_LOC | LOC_CODE | LOC | LOC_CODE | - | - |
| FK_LOC_BASIC_INFO_LOC_TYPE_COD | LOC_TYPE_CODE | LOC_TYPE | LOC_TYPE_CODE | - | - |

## LOC_BLIND — 블라인드내역(공간게시물) LB

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_BLIND_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 블라인드코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간 코드 | - |
| 3 | LOC_BLIND_REASON | VARCHAR2 | 300 | - | - | - | - | - | 처리사유 | - |
| 4 | LOC_BLIND_DATE | DATE | - | - | - | - | - | - | 처리일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_BLIND_LOC_CODE_LOC_LOC_ | LOC_CODE | LOC | LOC_CODE | - | - |

## LOC_CONTACT — 연락처 LC

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_CONTACT_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 연락처코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간코드 | - |
| 3 | LOC_EMAIL | VARCHAR2 | 320 | - | - | - | - | - | 연락이메일 | - |
| 4 | LOC_TEL | VARCHAR2 | 13 | - | - | - | - | - | 휴대폰 | - |
| 5 | LOC_MAIN_TEL | VARCHAR2 | 13 | - | - | - | - | - | 대표 전화 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_CONTACT_LOC_CODE_LOC_LO | LOC_CODE | LOC | LOC_CODE | - | - |

## LOC_DETAIL_IMG — 이미지(상세정보) LDIMG

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_DETAIL_IMG_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 이미지코드(상세정보) | - |
| 2 | LOC_DETAIL_INFO_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 상세정보코드 | - |
| 3 | LOC_DETAIL_IMG_URL | VARCHAR2 | 500 | - | - | - | - | - | 이미지 링크(url) | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_DETAIL_IMG_LOC_DETAIL_I | LOC_DETAIL_INFO_CODE | LOC_DETAIL_INFO | LOC_DETAIL_INFO_CODE | - | - |

## LOC_DETAIL_INFO — 상세정보 LDIF

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_DETAIL_INFO_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 상세정보코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간코드 | - |
| 3 | MIN_PEOPLE | NUMBER | 2 | - | - | - | - | - | 최소 수용인원 | - |
| 4 | MAX_PEOPLE | NUMBER | 2 | - | - | - | - | - | 최대 수용인원 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_DETAIL_INFO_LOC_CODE_LO | LOC_CODE | LOC | LOC_CODE | - | - |

## LOC_REMOVE — 삭제내역(공간) LRM

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_REMOVE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 삭제내역코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간코드 | - |
| 3 | LOC_REMOVE_DATE | DATE | - | - | - | - | - | - | 삭제일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_REMOVE_LOC_CODE_LOC_LOC | LOC_CODE | LOC | LOC_CODE | - | - |

## LOC_REPORT — 신고접수(이용자)(공간게시물) LRP

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_REPORT_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 신고접수코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간코드 | - |
| 3 | LOC_REPORT_TYPE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 신고유형분류코드 | - |
| 4 | MEMBER_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드(신고자)(이용자) | - |
| 5 | LOC_REPORT_REASON | VARCHAR2 | 300 | - | - | - | - | - | 신고사유 | - |
| 6 | LOC_REPORT_DATE | DATE | - | - | - | - | - | - | 신고일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_REPORT_LOC_REPORT_TYPE_ | LOC_REPORT_TYPE_CODE | LOC_REPORT_TYPE | LOC_REPORT_TYPE_CODE | - | - |
| FK_LOC_REPORT_MEMBER_CODE_MEMB | MEMBER_CODE | MEMBER | MEMBER_CODE | - | - |
| FK_LOC_REPORT_LOC_CODE_LOC_LOC | LOC_CODE | LOC | LOC_CODE | - | - |

## LOC_REPORT_IMG — 이미지(신고) LRPIMG

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_REPORT_IMG_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 이미지코드 | - |
| 2 | LOC_REPORT_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 신고접수코드 | - |
| 3 | LOC_REPORT_IMG_URL | VARCHAR2 | 500 | - | - | - | - | - | 이미지 링크(url) | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_REPORT_IMG_LOC_REPORT_C | LOC_REPORT_CODE | LOC_REPORT | LOC_REPORT_CODE | - | - |

## LOC_REPORT_PROC — 신고처리(이용자)(공간게시물) LRPP

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_REPORT_PROC_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 신고처리코드 | - |
| 2 | LOC_REPORT_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 신고접수코드 | - |
| 3 | REPORT_PROC_TYPE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 신고처리분류코드 | - |
| 4 | LOC_REPORT_PROC_DATE | DATE | - | - | - | - | - | - | 처리일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_REPORT_PROC_REPORT_PROC | REPORT_PROC_TYPE_CODE | REPORT_PROC_TYPE | REPORT_PROC_TYPE_CODE | - | - |
| FK_LOC_REPORT_PROC_LOC_REPORT_ | LOC_REPORT_CODE | LOC_REPORT | LOC_REPORT_CODE | - | - |

## LOC_REPORT_TYPE — 신고유형분류(공간) LRPT

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_REPORT_TYPE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 신고유형분류코드 | - |
| 2 | LOC_REPORT_TYPE | VARCHAR2 | 50 | - | - | - | - | - | 신고 유형 | - |

## LOC_TYPE — 공간분류 LOCT

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_TYPE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 공간분류코드 | - |
| 2 | LOC_TYPE | VARCHAR2 | 30 | - | - | - | - | - | 공간 유형 | - |

## LOC_USE_INFO — 이용안내 LUI

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_USE_INFO_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 이용안내코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간코드 | - |
| 3 | LOC_USE_HOUR | VARCHAR2 | 300 | - | - | - | - | - | 이용시간 | - |
| 4 | LOC_USE_DAY_OFF | VARCHAR2 | 300 | - | - | - | - | - | 정기 휴무 | - |
| 5 | LOC_USE_APPOINT_DAY_OFF | VARCHAR2 | 300 | - | - | - | - | - | 지정 휴무일 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_USE_INFO_LOC_CODE_LOC_L | LOC_CODE | LOC | LOC_CODE | - | - |

## LOC_WEB — 공간웹사이트(상세정보)  LW

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | LOC_WEB_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 공간웹사이트코드 | - |
| 2 | LOC_DETAIL_INFO_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 상세정보코드 | - |
| 3 | LOC_WEB_URL | VARCHAR2 | 2000 | - | - | - | - | - | 공간 관련 웹사이트(url) | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_LOC_WEB_LOC_DETAIL_INFO_COD | LOC_DETAIL_INFO_CODE | LOC_DETAIL_INFO | LOC_DETAIL_INFO_CODE | - | - |

## MEMBER — 회원(이용자) M

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | MEMBER_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 회원코드(이용자) | - |
| 2 | MEMBER_SIGN_UP_DATE | DATE | - | - | - | - | - | - | 가입일자 | - |

## MEMBER_BANK_INFO — 계좌정보(이용자)

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | MEMBER_BANK_NUMBER | VARCHAR2 | 20 | Y | - | - | Y | - | 계좌번호 | - |
| 2 | MEMBER_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드(이용자) | - |
| 3 | MEMBER_BANK | VARCHAR2 | 40 | - | - | - | - | - | 은행명 | - |
| 4 | MEMBER_BANK_HOLDER | VARCHAR2 | 30 | - | - | - | - | - | 예금주 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_MEMBER_BANK_INFO_MEMBER_COD | MEMBER_CODE | MEMBER | MEMBER_CODE | - | - |

## MEMBER_BLACKLIST — 블랙리스트(이용자)

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | MEMBER_EMAIL | VARCHAR2 | 320 | Y | - | - | Y | - | 가입 이메일 | - |
| 2 | MEMBER_BLACKLIST_REASON | VARCHAR2 | 300 | - | - | - | - | - | 블랙리스트 전환 사유 | - |
| 3 | MEMBER_BLACKLIST_DATE | DATE | - | - | - | - | - | - | 블랙리스트 전환일 | - |

## MEMBER_CANCEL_LIST — 예약취소내역(이용자) MC

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | MEMBER_CANCEL_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 예약취소코드 | - |
| 2 | BOOK_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 예약코드 | - |
| 3 | MEMBER_CANCEL_REASON | VARCHAR2 | 300 | - | - | - | - | - | 취소사유 | - |
| 4 | MEMBER_CANCEL_DATE | DATE | - | - | - | - | - | - | 취소일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_MEMBER_CANCEL_LIST_BOOK_COD | BOOK_CODE | BOOK_LIST | BOOK_CODE | - | - |

## MEMBER_EXCHANGE_BANK_INFO — 환전계좌정보(이용자) MEBI

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | MEMBER_EXCHANGE_BANK_INFO_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 환전계좌코드 | - |
| 2 | MEMBER_EXCHANGE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 환전내역코드 | - |
| 3 | MEMBER_BANK_NUMBER | VARCHAR2 | 20 | - | Y | - | - | - | 계좌번호 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_MEMBER_EXCHANGE_BANK_INFO_M | MEMBER_EXCHANGE_CODE | MEMBER_EXCHANGE_LIST | MEMBER_EXCHANGE_CODE | - | - |
| FK_MEMBER_EXCHANGE_BANK_NUMBER | MEMBER_BANK_NUMBER | MEMBER_BANK_INFO | MEMBER_BANK_NUMBER | - | - |

## MEMBER_EXCHANGE_LIST — 마일리지 환전 내역(이용자) ME

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | MEMBER_EXCHANGE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 환전내역코드 | - |
| 2 | MEMBER_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드 | - |
| 3 | MEMBER_EXCHANGE_AMOUNT | NUMBER | 18 | - | - | - | - | - | 환전액수 | - |
| 4 | MEMBER_EXCHANGE_DATE | DATE | - | - | - | - | - | - | 환전일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_MEMBER_EXCHANGE_LIST_MEMBER | MEMBER_CODE | MEMBER | MEMBER_CODE | - | - |

## MEMBER_MSG_IMG — 이미지(메신저정보(이용자)) MMIMG

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | MEMBER_MSG_IMG_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 이미지코드 | - |
| 2 | MEMBER_MSG_INFO_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 메신저정보코드 | - |
| 3 | MEMBER_MSG_IMG_URL | VARCHAR2 | 500 | - | - | - | - | - | 이미지 링크(url) | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_MEMBER_MSG_IMG_MEMBER_MSG_I | MEMBER_MSG_INFO_CODE | MEMBER_MSG_INFO | MEMBER_MSG_INFO_CODE | - | - |

## MEMBER_MSG_INFO — 메신저정보(이용자) MMIF

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | MEMBER_MSG_INFO_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 메신저정보코드 | - |
| 2 | MSG_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 메신저코드 | - |
| 3 | MEMBER_MSG_CONTENT | VARCHAR2 | 1500 | - | - | - | - | - | 내용 | - |
| 4 | MEMBER_MSG_DATE | DATE | - | - | - | - | - | - | 메신저일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_MEMBER_MSG_INFO_MSG_CODE_MS | MSG_CODE | MSG | MSG_CODE | - | - |

## MEMBER_PROFILE — 프로필정보(이용자)

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | MEMBER_EMAIL | VARCHAR2 | 320 | Y | - | - | Y | - | 가입 이메일 | - |
| 2 | MEMBER_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드(이용자) | - |
| 3 | MEMBER_PW | VARCHAR2 | 30 | - | - | - | - | - | 비밀번호 | - |
| 4 | MEMBER_NICKNAME | VARCHAR2 | 18 | - | - | - | - | - | 닉네임 | - |
| 5 | MEMBER_NAME | VARCHAR2 | 30 | - | - | - | - | - | 이름 | - |
| 6 | MEMBER_TEL | VARCHAR2 | 13 | - | - | - | - | - | 연락처 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_MEMBER_PROFILE_MEMBER_CODE_ | MEMBER_CODE | MEMBER | MEMBER_CODE | - | - |

## MEMBER_WITHDRAW — 탈퇴(이용자) MW

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | MEMBER_WITHDRAW_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 탈퇴코드 | - |
| 2 | MEMBER_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드(이용자) | - |
| 3 | MEMBER_WITHDRAW_DATE | DATE | - | - | - | - | - | - | 탈퇴일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_MEMBER_WITHDRAW_MEMBER_CODE | MEMBER_CODE | MEMBER | MEMBER_CODE | - | - |

## MSG — 메신저 MSG

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | MSG_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 메신저코드 | - |
| 2 | BOOK_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 예약코드 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_MSG_BOOK_CODE_BOOK_LIST_BOO | BOOK_CODE | BOOK_LIST | BOOK_CODE | - | - |

## NOTICE — 공지사항 N

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | NOTICE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 공지사항 코드 | - |
| 2 | IMPORTANT_NOTICE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 중요분류코드 | - |
| 3 | NOTICE_TITLE | VARCHAR2 | 120 | - | - | - | - | - | 제목 | - |
| 4 | NOTICE_CONTENT | VARCHAR2 | 3000 | - | - | - | - | - | 내용 | - |
| 5 | NOTICE_DATE | DATE | - | - | - | - | - | - | 작성일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_NOTICE_IMPORTANT_NOTICE_COD | IMPORTANT_NOTICE_CODE | IMPORTANT_NOTICE | IMPORTANT_NOTICE_CODE | - | - |

## NOTICE_IMG — 이미지(공지사항) NIMG

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | NOTICE_IMG_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 이미지코드 | - |
| 2 | NOTICE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공지사항 코드 | - |
| 3 | NOTICE_IMG_URL | VARCHAR2 | 500 | - | - | - | - | - | 이미지 링크(url) | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_NOTICE_IMG_NOTICE_CODE_NOTI | NOTICE_CODE | NOTICE | NOTICE_CODE | - | - |

## PACKAGE — 패키지 P

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | PACKAGE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 패키지코드 | - |
| 2 | PACKAGE_FORM_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 패키지양식코드 | - |
| 3 | PACKAGE_NAME | VARCHAR2 | 180 | - | - | - | - | - | 패키지명 | - |
| 4 | PACKAGE_START | NUMBER | 2 | - | - | - | - | - | 패키지시작시간 | - |
| 5 | PACKAGE_END | NUMBER | 2 | - | - | - | - | - | 패키지종료시간 | - |
| 6 | PACKAGE_PRICE | NUMBER | 14 | - | - | - | - | - | 패키지가격 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_PACKAGE_PACKAGE_FORM_CODE_P | PACKAGE_FORM_CODE | PACKAGE_FORM | PACKAGE_FORM_CODE | - | - |

## PACKAGE_FORM — 패키지 양식 PF

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | PACKAGE_FORM_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 패키지양식코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간코드 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_PACKAGE_FORM_LOC_CODE_LOC_L | LOC_CODE | LOC | LOC_CODE | - | - |

## PACKAGE_REMOVE — 폐기 패키지 PRM

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | PACKAGE_REMOVE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 폐기 패키지 코드 | - |
| 2 | PACKAGE_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 패키지코드 | - |
| 3 | PACKAGE_REMOVE_DATE | DATE | - | - | - | - | - | - | 폐기일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_PACKAGE_REMOVE_PACKAGE_CODE | PACKAGE_CODE | PACKAGE | PACKAGE_CODE | - | - |

## QNA — Q_AND_A(이용자) Q

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | QNA_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | Q_AND_A코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간코드 | - |
| 3 | MEMBER_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드 | - |
| 4 | QNA_CONTENT | VARCHAR2 | 3000 | - | - | - | - | - | 내용 | - |
| 5 | QNA_DATE | DATE | - | - | - | - | - | - | Q_AND_A 작성일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_QNA_MEMBER_CODE_MEMBER_MEMB | MEMBER_CODE | MEMBER | MEMBER_CODE | - | - |
| FK_QNA_LOC_CODE_LOC_LOC_CODE | LOC_CODE | LOC | LOC_CODE | - | - |

## QNA_REMOVE — 삭제내역(Q_AND_A) QRM

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | QNA_REMOVE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 삭제내역코드 | - |
| 2 | QNA_CODE | VARCHAR2 | 15 | - | Y | - | - | - | Q_AND_A코드 | - |
| 3 | QNA_REMOVE_DATE | DATE | - | - | - | - | - | - | 삭제일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_QNA_REMOVE_QNA_CODE_QNA_QNA | QNA_CODE | QNA | QNA_CODE | - | - |

## QNA_REPLY — Q_AND_A답글(호스트) QRE

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | QNA_REPLY_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | Q_AND_A답글코드 | - |
| 2 | QNA_CODE | VARCHAR2 | 15 | - | Y | - | - | - | Q_AND_A코드 | - |
| 3 | QNA_REPLY_CONTENT | VARCHAR2 | 3000 | - | - | - | - | - | Q_AND_A 답글내용 | - |
| 4 | QNA_REPLY_DATE | DATE | - | - | - | - | - | - | Q_AND_A작성일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_QNA_REPLY_QNA_CODE_QNA_QNA_ | QNA_CODE | QNA | QNA_CODE | - | - |

## QNA_REPLY_REMOVE — 삭제내역(Q_AND_A답글) QRERM

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | QNA_REPLY_REMOVE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 삭제내역코드 | - |
| 2 | QNA_REPLY_CODE | VARCHAR2 | 15 | - | Y | - | - | - | Q_AND_A답글코드 | - |
| 3 | QNA_REPLY_REMOVE_DATE | DATE | - | - | - | - | - | - | 삭제일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_QNA_REPLY_REMOVE_QNA_REPLY_ | QNA_REPLY_CODE | QNA_REPLY | QNA_REPLY_CODE | - | - |

## REPORT_PROC_TYPE — 신고처리분류 RPPT

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | REPORT_PROC_TYPE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 신고처리분류코드 | - |
| 2 | REPORT_PROC_TYPE | VARCHAR2 | 20 | - | - | - | - | - | 처리 유형 | - |

## REVIEW — 리뷰(이용자) RV

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | REVIEW_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 리뷰코드 | - |
| 2 | LOC_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 공간코드 | - |
| 3 | MEMBER_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 회원코드 | - |
| 4 | REVIEW_RATE | NUMBER | 1 | - | - | - | - | - | 평점 | - |
| 5 | REVIEW_CONTENT | VARCHAR2 | 3000 | - | - | - | - | - | 리뷰내용 | - |
| 6 | REVIEW_DATE | DATE | - | - | - | - | - | - | 리뷰 작성 일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_REVIEW_MEMBER_CODE_MEMBER_M | MEMBER_CODE | MEMBER | MEMBER_CODE | - | - |
| FK_REVIEW_LOC_CODE_LOC_LOC_COD | LOC_CODE | LOC | LOC_CODE | - | - |

## REVIEW_IMG — 이미지(리뷰) RVIMG

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | REVIEW_IMG_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 이미지코드 | - |
| 2 | REVIEW_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 리뷰코드 | - |
| 3 | REVIEW_IMG_URL | VARCHAR2 | 500 | - | - | - | - | - | 이미지 링크(url) | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_REVIEW_IMG_REVIEW_CODE_REVI | REVIEW_CODE | REVIEW | REVIEW_CODE | - | - |

## REVIEW_REMOVE — 삭제내역(리뷰)  RVRM

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | REVIEW_REMOVE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 삭제내역코드 | - |
| 2 | REVIEW_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 리뷰코드 | - |
| 3 | REVIEW_REMOVE_DATE | DATE | - | - | - | - | - | - | 삭제일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_REVIEW_REMOVE_REVIEW_CODE_R | REVIEW_CODE | REVIEW | REVIEW_CODE | - | - |

## REVIEW_REPLY — 리뷰답글(호스트) RVRE

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | REVIEW_REPLY_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 리뷰답글코드 | - |
| 2 | REVIEW_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 리뷰코드 | - |
| 3 | REVIEW_REPLY_CONTENT | VARCHAR2 | 3000 | - | - | - | - | - | 리뷰답글내용 | - |
| 4 | REVIEW_REPLY_DATE | DATE | - | - | - | - | - | - | 리뷰답글작성일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_REVIEW_REPLY_REVIEW_CODE_RE | REVIEW_CODE | REVIEW | REVIEW_CODE | - | - |

## REVIEW_REPLY_REMOVE — 삭제내역(리뷰답글) RVRERM

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | REVIEW_REPLY_REMOVE_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 삭제내역코드 | - |
| 2 | REVIEW_REPLY_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 리뷰답글코드 | - |
| 3 | REVIEW_REPLY_REMOVE_DATE | DATE | - | - | - | - | - | - | 삭제일자 | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_REVIEW_REPLY_REMOVE_REVIEW_ | REVIEW_REPLY_CODE | REVIEW_REPLY | REVIEW_REPLY_CODE | - | - |

## THUMBNAIL — 썸네일(기본정보) TH

- 스키마: -
- 원본: docs/db/source/lookation_create_table.utf8.sql

| No | 컬럼 | 데이터 타입 | 길이 | PK | FK | UNIQUE | NOT NULL | DEFAULT | 설명 | 비고 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | THUMBNAIL_CODE | VARCHAR2 | 15 | Y | - | - | Y | - | 썸네일 코드 | - |
| 2 | LOC_BASIC_INFO_CODE | VARCHAR2 | 15 | - | Y | - | - | - | 기존정보코드 | - |
| 3 | THUMBNAIL_URL | VARCHAR2 | 500 | - | - | - | - | - | 썸네일 링크(url) | - |

### 관계

| FK | 컬럼 | 참조 테이블 | 참조 컬럼 | ON DELETE | ON UPDATE |
| --- | --- | --- | --- | --- | --- |
| FK_THUMBNAIL_LOC_BASIC_INFO_CO | LOC_BASIC_INFO_CODE | LOC_BASIC_INFO | LOC_BASIC_INFO_CODE | - | - |

## 파싱 메모

- 무시한 SQL 문입니다: SELECT COUNT(*) FROM TABS
