# 레거시 → 신규 API 파리티 체크리스트

레거시 소스(`Lookation/src`)에서 추출한 모든 요청 처리 지점과 신규 Kotlin/Spring Boot 4.1 REST 엔드포인트의 1:1 대응표다. 신규 엔드포인트는 고정된 매핑 규약을 그대로 사용한다.

## 근거

- 어노테이션 핸들러: `Lookation/src/com/lookation/mybatis/*.java`의 `@RequestMapping` **164개** (클래스 레벨 prefix 없음, 전부 메서드 레벨).
- XML 컨트롤러: `implements Controller` 클래스 **20개** / `handleRequest()` 20개. URL은 `중요파일/02. dispatcher-servlet/dispatcher-servlet.xml`의 `<bean name="...">`에서 그대로 옮겼다.
- `dispatcher-servlet.xml`은 `com.lookation.mybatis` 컴포넌트 스캔 + XML 빈 18개를 함께 등록한다. XML 빈 18개 중 `ProfileFormController`는 소스에 클래스가 없어(빈 정의만 존재) 핸들러 행을 만들지 않고 아래 주석에 남겼다.
- 총 행 수: 164 + 20 = **184**.

## 상태 정의

| 상태 | 의미 |
|---|---|
| `MAP` | 레거시 동작 1개가 신규 엔드포인트 1개로 그대로 이관 |
| `MERGE` | 여러 레거시 URL/중복 핸들러/화면 보조 요청이 하나의 신규 엔드포인트로 통합 |
| `DROP` | 이관하지 않음 — JSP 화면, 테스트/샘플, 빈 미등록(도달 불가), 중복 |
| `DROP ⚠️ 확인필요` | 의도가 불명확해 판단을 보류 — 기획 확인 필요 |

`신규 엔드포인트`의 `{id}`는 규약의 리소스 하위 경로 표기(`/api/v1/spaces/{id}`)와 동일한 관례다. `—`는 대응 엔드포인트 없음(DROP).

## 요약

| 도메인 | 레거시 URL 수 | MAP | DROP | MERGE | 합계 |
|---|---|---|---|---|---|
| `auth` | 19 | 7 | 7 | 5 | 19 |
| `account` | 9 | 4 | 3 | 2 | 9 |
| `host` | 2 | 2 | 0 | 0 | 2 |
| `space` | 23 | 6 | 2 | 15 | 23 |
| `price` | 6 | 5 | 1 | 0 | 6 |
| `reservation` | 9 | 5 | 0 | 4 | 9 |
| `payment` | 1 | 1 | 0 | 0 | 1 |
| `wallet` | 8 | 7 | 1 | 0 | 8 |
| `settlement` | 0 | 0 | 0 | 0 | 0 |
| `inspection` | 3 | 3 | 0 | 0 | 3 |
| `review` | 18 | 10 | 0 | 8 | 18 |
| `qna` | 18 | 10 | 0 | 8 | 18 |
| `message` | 4 | 2 | 0 | 2 | 4 |
| `report` | 14 | 6 | 0 | 8 | 14 |
| `sanction` | 20 | 9 | 1 | 10 | 20 |
| `content` | 21 | 13 | 2 | 6 | 21 |
| `admin` | 4 | 2 | 1 | 1 | 4 |
| `deadcode` | 5 | 0 | 5 | 0 | 5 |
| **합계** | **184** | **92** | **23** | **69** | **184** |

`settlement`는 레거시에 정산 기능이 없어 0건이다(신규 기능). `deadcode`는 도메인 패키지가 아니라 DROP 전용 분류다.

## 인증/세션 — `auth`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 1 | `/actions/accountajax.action` | XML bean | `AccountAjaxController.java:30 handleRequest()` | 이용자/호스트 회원가입 처리(identify 분기) | `/api/v1/auth/signup` | MAP |
| 2 | `/actions/adminlogin.action` | XML bean | `AdminLoginController.java:26 handleRequest()` | 관리자 로그인(비밀번호 검증·구형 해시 재해시) | `/api/v1/auth/login` | MERGE |
| 3 | `/actions/adminloginform.action` | XML bean | `AdminLoginFormController.java:18 handleRequest()` | 관리자 로그인 화면 | `—` | DROP |
| 4 | `/actions/ajaxfindemail.action` | POST | `Account.java:432 ajaxFindEmail()` | 가입 이메일 중복 확인 AJAX | `/api/v1/auth/signup` | MERGE |
| 5 | `/actions/changepasswordnologin.action` | POST | `Account.java:494 changePasswordNoLogin()` | 메일 토큰으로 비밀번호 재설정 | `/api/v1/auth/password-reset` | MAP |
| 6 | `/actions/changepasswordnologinform.action` | POST+GET | `Account.java:465 changePasswordNoLoginForm()` | 비밀번호 재설정 폼(메일 토큰) | `—` | DROP |
| 7 | `/actions/confirmpassword.action` | POST+GET | `Account.java:175 confirmPassword()` | 현재 비밀번호 검증 후 민감 작업 허용 | `/api/v1/me` | MAP |
| 8 | `/actions/confirmpasswordform.action` | GET | `Account.java:149 confirmPasswordForm()` | 비밀번호 재확인 화면(민감 작업 진입) | `—` | DROP |
| 9 | `/actions/findpasswordform.action` | GET | `Account.java:422 findPasswordForm()` | 비밀번호 찾기 화면 | `—` | DROP |
| 10 | `/actions/hostlogout.action` | GET | `Main.java:115 hostLogout()` | 호스트 로그아웃(세션 무효화) | `/api/v1/auth/logout` | MERGE |
| 11 | `/actions/login.action` | POST+GET | `Account.java:66 login()` | 이메일·비밀번호 로그인, 구형 해시 자동 재해시 | `/api/v1/auth/login` | MAP |
| 12 | `/actions/loginform.action` | GET | `Account.java:28 loginForm()` | 로그인 화면(JSP) | `—` | DROP |
| 13 | `/actions/memberlogout.action` | GET | `Main.java:80 memberLogout()` | 이용자 로그아웃(세션 무효화) | `/api/v1/auth/logout` | MAP |
| 14 | `/actions/sendconfirmemail.action` | POST | `Email.java:25 sendPasswordEmail()` | 비밀번호 재설정 메일 발송 | `/api/v1/auth/password-reset` | MAP |
| 15 | `/actions/sendsignupemail.action` | POST | `SignupEmail.java:30 send()` | 가입 인증 메일 발송·중복 검사·pending 저장 | `/api/v1/auth/signup` | MERGE |
| 16 | `/actions/signupform.action` | XML bean | `SignUpFormController.java:22 handleRequest()` | 회원가입 화면 | `—` | DROP |
| 17 | `/actions/verifyemail.action` | GET | `VerifyEmail.java:33 confirm()` | 이메일 인증 링크 확인 화면 | `/api/v1/auth/verify-email` | MERGE |
| 18 | `/actions/verifyemail.action` | POST | `VerifyEmail.java:40 verify()` | 인증 코드 검증·계정 활성화 | `/api/v1/auth/verify-email` | MAP |
| 19 | `—` | XML bean | `AdminLogoutController.java:16 handleRequest()` | 관리자 로그아웃 — dispatcher-servlet.xml에 빈 미등록 | `—` | DROP |

## 계정/프로필 — `account`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 20 | `/actions/ajaxmodifytel.action` | POST+GET | `Account.java:293 ajaxModifyTel()` | 전화번호 변경 AJAX | `/api/v1/me` | MAP |
| 21 | `/actions/changepassword.action` | POST | `Account.java:376 changePassword()` | 로그인 상태 비밀번호 변경 | `/api/v1/me` | MAP |
| 22 | `/actions/changepasswordform.action` | GET | `Account.java:333 changePasswordForm()` | 비밀번호 변경 화면 | `—` | DROP |
| 23 | `/actions/deleteform.action` | XML bean | `DeleteFormController.java:31 handleRequest()` | 회원 탈퇴 확인 화면 | `—` | DROP |
| 24 | `/actions/deletemember.action` | XML bean | `DeleteMemberController.java:39 handleRequest()` | 이용자 탈퇴(잔액·예약 확인 후 계정/프로필/계좌 삭제) | `/api/v1/me` | MAP |
| 25 | `/actions/membermain.action` | GET | `Main.java:28 memberMain()` | 이용자 메인(통계·오늘의 공간·오늘의 리뷰) | `/api/v1/me` | MERGE |
| 26 | `/actions/mypage.action` | GET | `MypageMain.java:25 mypage()` | 마이페이지 메인(프로필 요약) | `/api/v1/me` | MERGE |
| 27 | `/actions/profile.action` | GET | `Account.java:239 profile()` | 프로필 조회(블랙리스트 해제일 포함) | `/api/v1/me` | MAP |
| 28 | `—` | XML bean | `MypageFormController.java:28 handleRequest()` | 마이페이지 화면 — 빈 미등록, ISampleDAO만 주입 | `—` | DROP |

## 호스트 — `host`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 29 | `/actions/deletehost.action` | XML bean | `DeleteHostController.java:38 handleRequest()` | 호스트 탈퇴(잔액·예약 확인 후 사업/연락/환전 정보 삭제) | `/api/v1/me/host-profile` | MAP |
| 30 | `/actions/hostmain.action` | GET | `Main.java:90 hostMain()` | 호스트 메인(통계 요약) | `/api/v1/me/host-profile` | MAP |

## 공간 — `space`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 31 | `/actions/basicajax.action` | POST | `Location.java:165 basicAjax()` | 등록 1단계 임시 저장(편의시설·주의사항 AJAX) | `/api/v1/spaces` | MERGE |
| 32 | `/actions/basicform.action` | GET | `Location.java:120 basicForm()` | 공간 등록 1단계 화면(기본정보 입력) | `/api/v1/spaces` | MERGE |
| 33 | `/actions/deletelocation.action` | POST | `Location.java:66 deleteAction()` | 공간 삭제(예약 존재 시 차단) | `/api/v1/spaces/{id}` | MAP |
| 34 | `/actions/inputbasicinfo.action` | POST+GET | `Location.java:222 inputBasicInfo()` | 기본정보·시설·주의사항·썸네일 저장 | `/api/v1/spaces` | MAP |
| 35 | `/actions/inputbusinessinfo.action` | POST+GET | `Location.java:374 inputBusinessInfo()` | 사업자 정보 저장 | `/api/v1/spaces` | MERGE |
| 36 | `/actions/inputcontactinfo.action` | POST+GET | `Location.java:321 inputContactInfo()` | 연락처 정보 저장 | `/api/v1/spaces` | MERGE |
| 37 | `/actions/inputdetailinfo.action` | POST+GET | `Location.java:454 inputDetailInfo()` | 상세정보·상세이미지·웹링크 저장 | `/api/v1/spaces` | MERGE |
| 38 | `/actions/inputusinginfo.action` | POST+GET | `Location.java:536 inputUsingInfo()` | 이용정보·점검일정·패키지 저장 | `/api/v1/spaces` | MERGE |
| 39 | `/actions/insertinfotest.action` | GET | `Location.java:598 insertinfotest()` | 미구현 테스트 엔드포인트(수정 DAO 일괄 호출) | `—` | DROP ⚠️ 확인필요 |
| 40 | `/actions/locationdetail.action` | XML bean | `LocationDetailController.java:31 handleRequest()` | 공간 상세(이용자) — 기본/이용/시설/사업자 정보 | `/api/v1/spaces/{id}` | MAP |
| 41 | `/actions/locationdetailhost.action` | XML bean | `LocationDetailHostController.java:32 handleRequest()` | 공간 상세(호스트) — 소유권 확인 후 조회 | `/api/v1/spaces/{id}` | MERGE |
| 42 | `/actions/locationlist.action` | GET | `Location.java:29 locationList()` | 공간 목록 조회(지역/카테고리) | `/api/v1/spaces` | MAP |
| 43 | `/actions/locdetailajax.action` | XML bean | `LocationDetailAjaxController.java:29 handleRequest()` | 날짜별 패키지·예약 가능 여부 JSON | `/api/v1/spaces/{id}` | MERGE |
| 44 | `/actions/modifybasicform.action` | GET | `Location.java:694 modifyBasicForm()` | 기본정보 수정 화면 | `/api/v1/spaces/{id}` | MERGE |
| 45 | `/actions/modifybasicinfo.action` | POST+GET | `Location.java:865 modifyBasicInfo()` | 기본정보·시설·주의사항 수정 저장 | `/api/v1/spaces/{id}` | MAP |
| 46 | `/actions/modifycontactinfo.action` | POST+GET | `Location.java:996 modifyContactInfo()` | 연락처 정보 수정 | `/api/v1/spaces/{id}` | MERGE |
| 47 | `/actions/modifydetailinfo.action` | POST+GET | `Location.java:1054 modifyDetailInfo()` | 상세정보·웹링크 수정 | `/api/v1/spaces/{id}` | MERGE |
| 48 | `/actions/modifyform.action` | GET | `Location.java:750 basicModifyForm()` | 전체 정보 수정 화면 | `/api/v1/spaces/{id}` | MERGE |
| 49 | `/actions/modifylocation.action` | GET | `Location.java:639 modifyAction()` | 수정 진입 전 예약 존재 여부 검사 | `/api/v1/spaces/{id}` | MERGE |
| 50 | `/actions/modifyusinginfo.action` | POST+GET | `Location.java:1124 modifyUsingInfo()` | 이용정보 수정 | `/api/v1/spaces/{id}` | MERGE |
| 51 | `/actions/search.action` | GET+POST | `Search.java:32 searchKeyword()` | 키워드/지역 검색 | `/api/v1/spaces` | MAP |
| 52 | `/actions/searchkeyword.action` | GET+POST | `Search.java:177 searchKeywordOther()` | 키워드 검색(동일 검색 로직 재노출) | `/api/v1/spaces` | MERGE |
| 53 | `—` | XML bean | `LocationPackageAjaxController.java:26 handleRequest()` | 패키지 상세 JSON — 빈 미등록 | `—` | DROP |

## 가격/패키지 — `price`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 54 | `/actions/deletepackageform.action` | POST+GET | `Package.java:140 deletePackageForm()` | 패키지 삭제(예약 존재 시 차단) | `/api/v1/spaces/{id}/prices` | MAP |
| 55 | `/actions/inputpackageform.action` | POST+GET | `Package.java:92 inputPackageForm()` | 패키지 추가 | `/api/v1/spaces/{id}/prices` | MAP |
| 56 | `/actions/packageapplyajax.action` | POST+GET | `Package.java:244 packageApplyAjax()` | 패키지 적용/해제 저장 | `/api/v1/spaces/{id}/prices` | MAP |
| 57 | `/actions/packageapplyform.action` | GET | `Package.java:194 packageApplyForm()` | 패키지 적용 현황 조회 | `/api/v1/spaces/{id}/prices` | MAP |
| 58 | `/actions/packageform.action` | GET | `Package.java:65 packageForm()` | 패키지 추가 화면 | `—` | DROP |
| 59 | `/actions/packagemanager.action` | GET | `Package.java:28 packageManager()` | 공간 패키지 목록 | `/api/v1/spaces/{id}/prices` | MAP |

## 예약 — `reservation`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 60 | `/actions/bookapply.action` | POST | `BookApply.java:41 bookapplyBasic()` | 예약 생성(패키지·사업자정보 검증) | `/api/v1/reservations` | MAP |
| 61 | `/actions/bookcancel.action` | GET | `BookList.java:115 bookCancelPop()` | 취소 팝업 — 환불 예정 금액 계산 | `/api/v1/reservations/{id}` | MERGE |
| 62 | `/actions/bookcancelhost.action` | GET | `BookList.java:248 bookCancelPopHost()` | 호스트 취소 팝업 — 환불 예정 금액 | `/api/v1/reservations/{id}` | MERGE |
| 63 | `/actions/bookdetails.action` | GET | `BookList.java:74 bookDetails()` | 이용자 예약 상세 | `/api/v1/reservations/{id}` | MAP |
| 64 | `/actions/bookdetailshost.action` | GET | `BookList.java:207 bookDetailsHost()` | 호스트 예약 상세 | `/api/v1/reservations/{id}` | MERGE |
| 65 | `/actions/booklist.action` | GET | `BookList.java:33 bookList()` | 이용자 예약 목록 | `/api/v1/reservations` | MAP |
| 66 | `/actions/booklisthost.action` | GET | `BookList.java:166 bookListHost()` | 호스트 예약 목록 | `/api/v1/reservations` | MERGE |
| 67 | `/actions/hostcancel.action` | POST | `BookList.java:289 hostCancel()` | 호스트 예약 취소·환불 처리 | `/api/v1/reservations/{id}` | MAP |
| 68 | `/actions/membercancel.action` | POST | `BookList.java:156 memberCancel()` | 이용자 예약 취소·환불 처리 | `/api/v1/reservations/{id}` | MAP |

## 결제 — `payment`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 69 | `/actions/bookpay.action` | POST | `BookApply.java:95 MileageCheck()` | 마일리지 결제·예약 확정 | `/api/v1/payments` | MAP |

## 지갑/입출금 — `wallet`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 70 | `/actions/bankinfoadd.action` | XML bean | `BankInfoAddController.java:33 handleRequest()` | 계좌 등록(최대 3개 제한) | `/api/v1/wallets` | MAP |
| 71 | `/actions/bankinfoaddpopup.action` | XML bean | `BankInfoAddPopupController.java:29 handleRequest()` | 계좌 등록 화면 | `—` | DROP |
| 72 | `/actions/bankinfomanage.action` | XML bean | `BankInfoManageController.java:37 handleRequest()` | 등록 계좌 목록(이용자/호스트) | `/api/v1/wallets` | MAP |
| 73 | `/actions/bankinforemove.action` | XML bean | `BankInfoRemoveController.java:31 handleRequest()` | 등록 계좌 삭제 | `/api/v1/wallets` | MAP |
| 74 | `/actions/exchange.action` | POST | `LoadAndExchange.java:195 exchangeReg()` | 환전(출금) 신청 등록 | `/api/v1/withdrawals` | MAP |
| 75 | `/actions/loadandexchange.action` | GET+POST | `LoadAndExchange.java:27 bankInfoAndBalance()` | 이용자/호스트 잔액·등록 계좌 조회 | `/api/v1/wallets` | MAP |
| 76 | `/actions/memberload.action` | POST | `LoadAndExchange.java:114 loadReg()` | 마일리지 충전 신청 등록 | `/api/v1/wallets` | MAP |
| 77 | `/actions/mileagehistory.action` | GET | `MileageHistoryList.java:24 historyList()` | 마일리지 충전·결제·환불·환전 내역 | `/api/v1/me/wallet/entries` | MAP |

## 정산 — `settlement`

레거시 대응 URL 없음 — 신규 구현 대상.

## 입점 심사 — `inspection`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 78 | `/actions/insepectinsert.action` | XML bean | `InspectInsertController.java:25 handleRequest()` | 심사 결과 등록(승인/반려) | `/api/v1/inspections` | MAP |
| 79 | `/actions/inspectmanager.action` | XML bean | `InspectManagerController.java:26 handleRequest()` | 입점 신청 목록(관리자) | `/api/v1/inspections` | MAP |
| 80 | `/actions/inspectprocess.action` | XML bean | `InspectProcessController.java:28 handleRequest()` | 입점 신청 상세 심사(사업자·시설·이미지) | `/api/v1/inspections/{id}` | MAP |

## 리뷰 — `review`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 81 | `/actions/deletereview.action` | POST | `LocationReview.java:213 deleteReview()` | 리뷰 삭제 | `/api/v1/reviews/{id}` | MAP |
| 82 | `/actions/deletereviewinlist.action` | POST | `MemberReviewList.java:67 deleteReview()` | 목록에서 리뷰 삭제 | `/api/v1/reviews/{id}` | MERGE |
| 83 | `/actions/deletereviewreply.action` | POST | `LocationReview.java:244 deleteReviewReply()` | 호스트 리뷰 답글 삭제 | `/api/v1/reviews/{id}` | MERGE |
| 84 | `/actions/hostreviewdelete.action` | POST | `hostReviewManager.java:75 r_remove()` | 호스트 리뷰 답글 삭제 | `/api/v1/admin/reviews/{id}` | MAP |
| 85 | `/actions/hostreviewmanager.action` | GET | `hostReviewManager.java:26 r_firstList()` | 호스트 리뷰 목록(관리자) | `/api/v1/admin/reviews` | MAP |
| 86 | `/actions/hostreviewpopup.action` | GET | `hostReviewManager.java:50 r_secondList()` | 호스트 리뷰 상세 팝업 | `/api/v1/admin/reviews/{id}` | MERGE |
| 87 | `/actions/modifyformreview.action` | GET | `LocationReview.java:74 reviewModifyForm()` | 리뷰 수정 폼(리뷰·이미지·답글) | `/api/v1/reviews/{id}` | MERGE |
| 88 | `/actions/modifyreview.action` | POST+GET | `LocationReview.java:173 modifyReview()` | 리뷰 수정(이미지 교체) | `/api/v1/reviews/{id}` | MAP |
| 89 | `/actions/modifyreviewinlist.action` | POST | `MemberReviewList.java:58 modifyReview()` | 목록에서 리뷰 수정 | `/api/v1/reviews/{id}` | MAP |
| 90 | `/actions/modifyreviewreply.action` | POST | `LocationReview.java:235 modifyReviewReply()` | 호스트 리뷰 답글 수정 | `/api/v1/reviews/{id}` | MERGE |
| 91 | `/actions/reviewdelete.action` | POST | `UserReviewManager.java:68 remove()` | 리뷰 단건 삭제 | `/api/v1/admin/reviews/{id}` | MAP |
| 92 | `/actions/reviewinsert.action` | POST+GET | `LocationReview.java:135 insertReview()` | 리뷰 등록(이미지 포함) | `/api/v1/spaces/{id}/reviews` | MAP |
| 93 | `/actions/reviewreplyinsert.action` | POST | `LocationReview.java:225 insertReviewReply()` | 호스트 리뷰 답글 작성 | `/api/v1/reviews/{id}` | MAP |
| 94 | `/actions/selectremove.action` | POST | `UserReviewManager.java:91 multiRemove()` | 리뷰 다건 삭제 | `/api/v1/admin/reviews` | MERGE |
| 95 | `/actions/userreviewlist.action` | GET | `MemberReviewList.java:25 userReviewList()` | 내 리뷰 목록 | `/api/v1/reviews` | MAP |
| 96 | `/actions/userreviewmanager.action` | GET | `UserReviewManager.java:22 basicList()` | 전체 리뷰 목록(관리자) | `/api/v1/admin/reviews` | MAP |
| 97 | `/actions/userreviewpopup.action` | GET | `UserReviewManager.java:45 detailList()` | 리뷰 상세 팝업 | `/api/v1/admin/reviews/{id}` | MERGE |
| 98 | `/actions/writereview.action` | GET | `LocationReview.java:30 reviewForm()` | 리뷰 작성 팝업 데이터 | `/api/v1/spaces/{id}/reviews` | MERGE |

## QnA — `qna`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 99 | `/actions/deleteqna.action` | POST | `LocationQna.java:148 deleteQna()` | 이용자 QnA 삭제 | `/api/v1/qnas/{id}` | MAP |
| 100 | `/actions/deleteqnainlist.action` | POST | `MemberQnaList.java:70 deleteQna()` | 목록에서 QnA 삭제 | `/api/v1/qnas/{id}` | MERGE |
| 101 | `/actions/deleteqnareply.action` | POST | `LocationQna.java:183 deleteQnaReply()` | 호스트 QnA 답글 삭제 | `/api/v1/qnas/{id}` | MERGE |
| 102 | `/actions/hostqnamanager.action` | GET | `hostQnaManager.java:26 firstList()` | 호스트 QnA 목록(관리자) | `/api/v1/admin/qnas` | MAP |
| 103 | `/actions/hostqnapopup.action` | GET | `hostQnaManager.java:49 secondList()` | 호스트 QnA 상세 팝업 | `/api/v1/admin/qnas/{id}` | MERGE |
| 104 | `/actions/hostqnareplydelete.action` | POST | `hostQnaManager.java:74 remove()` | 호스트 QnA 답글 삭제 | `/api/v1/admin/qnas/{id}` | MAP |
| 105 | `/actions/modifyformqna.action` | GET | `LocationQna.java:75 modifyFormQna()` | QnA/답글 수정 폼 | `/api/v1/qnas/{id}` | MERGE |
| 106 | `/actions/modifyqna.action` | POST | `LocationQna.java:138 modifyQna()` | 이용자 QnA 수정 | `/api/v1/qnas/{id}` | MAP |
| 107 | `/actions/modifyqnainlist.action` | POST | `MemberQnaList.java:61 modifyQna()` | 목록에서 QnA 수정 | `/api/v1/qnas/{id}` | MAP |
| 108 | `/actions/modifyqnareply.action` | POST | `LocationQna.java:174 modifyQnaReply()` | 호스트 QnA 답글 수정 | `/api/v1/qnas/{id}` | MERGE |
| 109 | `/actions/qnadelete.action` | POST | `UserQnaManager.java:72 remove()` | QnA 단건 삭제 | `/api/v1/admin/qnas/{id}` | MAP |
| 110 | `/actions/qnainsert.action` | POST | `LocationQna.java:127 insertQna()` | 이용자 QnA 작성 | `/api/v1/spaces/{id}/qnas` | MAP |
| 111 | `/actions/qnareplyinsert.action` | POST | `LocationQna.java:163 insertQnaReply()` | 호스트 QnA 답글 작성 | `/api/v1/qnas/{id}` | MAP |
| 112 | `/actions/selectdelete.action` | POST | `UserQnaManager.java:95 multiRemove()` | QnA 다건 삭제 | `/api/v1/admin/qnas` | MERGE |
| 113 | `/actions/userqnalist.action` | GET | `MemberQnaList.java:26 memberQnaList()` | 내 QnA 목록 | `/api/v1/qnas` | MAP |
| 114 | `/actions/userqnamanager.action` | GET | `UserQnaManager.java:26 basicList()` | 전체 QnA 목록(관리자) | `/api/v1/admin/qnas` | MAP |
| 115 | `/actions/userqnapopup.action` | GET | `UserQnaManager.java:49 detailList()` | QnA 상세 팝업 | `/api/v1/admin/qnas/{id}` | MERGE |
| 116 | `/actions/writeqna.action` | GET | `LocationQna.java:25 QnaForm()` | QnA 작성 팝업 데이터 | `/api/v1/spaces/{id}/qnas` | MERGE |

## 메시지 — `message`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 117 | `/actions/hmessenger.action` | GET | `HostMessenger.java:29 messageList()` | 호스트 메시지 목록·검색(예약 단위) | `/api/v1/reservations/{id}/messages` | MAP |
| 118 | `/actions/hmsgsend.action` | POST | `HostMessenger.java:85 imgSend()` | 호스트 메시지/이미지 전송 | `/api/v1/reservations/{id}/messages` | MAP |
| 119 | `/actions/mmessenger.action` | GET | `MemberMessenger.java:29 messageList()` | 이용자 메시지 목록·검색 | `/api/v1/reservations/{id}/messages` | MERGE |
| 120 | `/actions/mmsgsend.action` | POST | `MemberMessenger.java:91 imgSend()` | 이용자 메시지/이미지 전송 | `/api/v1/reservations/{id}/messages` | MERGE |

## 신고 — `report`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 121 | `/actions/bookpopup0.action` | GET | `ReportList.java:49 bookDetailList0()` | 예약 신고 상세(처리 전) | `/api/v1/admin/reports/{id}` | MERGE |
| 122 | `/actions/bookpopup1.action` | GET | `ReportList.java:73 bookDetailList1()` | 예약 신고 상세(처리 후) | `/api/v1/admin/reports/{id}` | MERGE |
| 123 | `/actions/bookreportlist.action` | GET | `ReportList.java:24 basicList()` | 예약 신고 목록(관리자) | `/api/v1/admin/reports` | MAP |
| 124 | `/actions/bookreportproc.action` | POST | `ReportList.java:97 bookReportProc()` | 예약 신고 처리 | `/api/v1/admin/reports/{id}` | MAP |
| 125 | `/actions/locpopup0.action` | GET | `ReportList.java:231 locDetailList0()` | 공간 신고 상세(처리 전) | `/api/v1/admin/reports/{id}` | MERGE |
| 126 | `/actions/locpopup1.action` | GET | `ReportList.java:254 locDetailList1()` | 공간 신고 상세(처리 후) | `/api/v1/admin/reports/{id}` | MERGE |
| 127 | `/actions/locreportproc.action` | POST | `ReportList.java:277 locReportProc()` | 공간 신고 처리 | `/api/v1/admin/reports/{id}` | MAP |
| 128 | `/actions/reporthost.action` | POST+GET | `ReportHost.java:30 add()` | 호스트 신고 접수 | `/api/v1/reports` | MAP |
| 129 | `/actions/reporthostform.action` | GET | `BookList.java:339 addhostForm()` | 호스트 신고 접수 폼(예약 기준) | `/api/v1/reports` | MERGE |
| 130 | `/actions/reporthostimg.action` | GET+POST | `ReportHost.java:79 addImg()` | 호스트 신고 이미지 업로드 | `/api/v1/reports` | MERGE |
| 131 | `/actions/reportlist.action` | GET | `ReportList.java:208 locBasicList()` | 공간 신고 목록(관리자) | `/api/v1/admin/reports` | MAP |
| 132 | `/actions/reportuser.action` | POST+GET | `ReportUser.java:29 add()` | 이용자 신고 접수 | `/api/v1/reports` | MAP |
| 133 | `/actions/reportuserform.action` | GET | `BookList.java:299 addForm()` | 이용자 신고 접수 폼(예약 기준) | `/api/v1/reports` | MERGE |
| 134 | `/actions/reportuserimg.action` | GET+POST | `ReportUser.java:80 addImg()` | 이용자 신고 이미지 업로드 | `/api/v1/reports` | MERGE |

## 제재/블라인드 — `sanction`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 135 | `/actions/` | POST+GET | `blackListEmail.java:19 sendPasswordEmail()` | 블랙리스트 전환 안내 메일 | `—` | DROP ⚠️ 확인필요 |
| 136 | `/actions/blind.action` | POST | `ReportList.java:370 addBlind()` | 공간 블라인드 처리 | `/api/v1/sanctions` | MAP |
| 137 | `/actions/blindcontent.action` | GET | `BlindContent.java:22 blind()` | 블라인드된 콘텐츠 열람 | `/api/v1/sanctions` | MERGE |
| 138 | `/actions/blindform.action` | GET | `ReportList.java:347 blindForm()` | 블라인드 처리 팝업 | `/api/v1/sanctions` | MERGE |
| 139 | `/actions/blindmanager.action` | GET | `BlindManager.java:22 list()` | 블라인드 목록(관리자) | `/api/v1/sanctions` | MERGE |
| 140 | `/actions/blindremove.action` | POST | `BlindManager.java:45 remove()` | 블라인드 해제 | `/api/v1/sanctions/{id}` | MAP |
| 141 | `/actions/delblackhost.action` | POST | `HostBlackManager.java:107 delBlackHost()` | 호스트 블랙리스트 해제 | `/api/v1/sanctions/{id}` | MAP |
| 142 | `/actions/delblackmem.action` | POST | `MemberBlackManager.java:111 delBlackmember()` | 이용자 블랙리스트 해제 | `/api/v1/sanctions/{id}` | MAP |
| 143 | `/actions/hostblack.action` | POST | `ReportList.java:323 hostBlack()` | 호스트 블랙리스트 처리 | `/api/v1/sanctions` | MAP |
| 144 | `/actions/hostblackform.action` | GET | `ReportList.java:300 hostBlackForm()` | 호스트 블랙리스트 처리 팝업 | `/api/v1/sanctions` | MERGE |
| 145 | `/actions/hostblackmanager.action` | GET | `HostBlackManager.java:23 hostBlackManager()` | 호스트 블랙리스트 목록 | `/api/v1/sanctions` | MERGE |
| 146 | `/actions/memberblackmanager.action` | GET | `MemberBlackManager.java:23 memberBlackManager()` | 이용자 블랙리스트 목록 | `/api/v1/sanctions` | MAP |
| 147 | `/actions/reportdetails.action` | GET | `HostBlackManager.java:46 viewReportDetailsHost()` | 호스트 신고내역 팝업 | `/api/v1/sanctions` | MERGE |
| 148 | `/actions/reportmemdetails.action` | GET | `MemberBlackManager.java:47 viewReportDetailsmember()` | 이용자 신고내역 팝업 | `/api/v1/sanctions` | MERGE |
| 149 | `/actions/setblackhost.action` | POST | `HostBlackManager.java:95 regBlackHost()` | 호스트 블랙리스트 등록 | `/api/v1/sanctions` | MAP |
| 150 | `/actions/setblackhostform.action` | GET | `HostBlackManager.java:70 setBlackHost()` | 호스트 블랙리스트 등록 팝업(이메일 검색) | `/api/v1/sanctions` | MERGE |
| 151 | `/actions/setblackmem.action` | POST | `MemberBlackManager.java:99 regBlackmember()` | 이용자 블랙리스트 등록 | `/api/v1/sanctions` | MAP |
| 152 | `/actions/setblackmemform.action` | GET | `MemberBlackManager.java:72 setBlackmember()` | 이용자 블랙리스트 등록 팝업(이메일 검색) | `/api/v1/sanctions` | MERGE |
| 153 | `/actions/userblack.action` | POST | `ReportList.java:143 userBlack()` | 이용자 블랙리스트 처리 | `/api/v1/sanctions` | MAP |
| 154 | `/actions/userblackform.action` | GET | `ReportList.java:120 userBlackForm()` | 이용자 블랙리스트 처리 팝업 | `/api/v1/sanctions` | MERGE |

## 콘텐츠(공지/도움말/약관) — `content`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 155 | `/actions/help.action` | GET | `Help.java:24 helpList()` | 도움말 목록 | `/api/v1/helps` | MAP |
| 156 | `/actions/helpdelete.action` | POST | `HelpManager.java:147 helpDelete()` | 도움말 삭제(이미지 포함) | `/api/v1/admin/helps/{id}` | MAP |
| 157 | `/actions/helpdetail.action` | GET | `Help.java:111 U_detailList()` | 도움말 상세(이미지 포함) | `/api/v1/helps/{id}` | MAP |
| 158 | `/actions/helpinsert.action` | POST | `HelpManager.java:86 helpInsert()` | 도움말 작성 | `/api/v1/admin/helps` | MAP |
| 159 | `/actions/helpinsertform.action` | GET | `HelpManager.java:58 helpInsertform()` | 도움말 작성 화면 | `—` | DROP |
| 160 | `/actions/helpmanager.action` | GET | `HelpManager.java:34 helpList()` | 도움말 관리 목록(관리자) | `/api/v1/admin/helps` | MAP |
| 161 | `/actions/helpupdate.action` | POST | `HelpManager.java:207 helpUpdate()` | 도움말 수정(이미지 포함) | `/api/v1/admin/helps/{id}` | MAP |
| 162 | `/actions/helpupdateform.action` | GET | `HelpManager.java:181 helpUpdateForm()` | 도움말 수정 화면 | `/api/v1/admin/helps/{id}` | MERGE |
| 163 | `/actions/locmanagement.action` | GET | `Service.java:78 locManagement()` | 공간 운영 정책 문서 | `/api/v1/helps` | MERGE |
| 164 | `/actions/notice.action` | GET | `Notice.java:24 U_noticeList()` | 공지사항 목록 | `/api/v1/notices` | MAP |
| 165 | `/actions/noticedelete.action` | POST | `NoticeManager.java:105 noticeDelete()` | 공지사항 삭제 | `/api/v1/admin/notices/{id}` | MAP |
| 166 | `/actions/noticedetail.action` | GET | `Notice.java:99 U_ndetailList()` | 공지사항 상세 | `/api/v1/notices/{id}` | MAP |
| 167 | `/actions/noticeinsert.action` | POST | `NoticeManager.java:78 noticeInsert()` | 공지사항 작성 | `/api/v1/admin/notices` | MAP |
| 168 | `/actions/noticeinsertform.action` | GET | `NoticeManager.java:50 noticeInsertform()` | 공지사항 작성 화면 | `—` | DROP |
| 169 | `/actions/noticemanager.action` | GET | `NoticeManager.java:26 noticeList()` | 공지사항 관리 목록(관리자) | `/api/v1/admin/notices` | MAP |
| 170 | `/actions/noticeupdate.action` | POST | `NoticeManager.java:156 noticeUpdate()` | 공지사항 수정 | `/api/v1/admin/notices/{id}` | MAP |
| 171 | `/actions/noticeupdateform.action` | GET | `NoticeManager.java:129 noticeUpdateForm()` | 공지사항 수정 화면 | `/api/v1/admin/notices/{id}` | MERGE |
| 172 | `/actions/privacy.action` | GET | `Service.java:131 privacy()` | 개인정보처리방침 문서 | `/api/v1/helps` | MERGE |
| 173 | `/actions/refund.action` | GET | `Service.java:184 refund()` | 환불 정책 문서 | `/api/v1/helps` | MERGE |
| 174 | `/actions/terms.action` | GET | `LoadAndExchange.java:294 loadAndExchangeTerms()` | 충전/환전 약관 문서 | `/api/v1/helps` | MERGE |
| 175 | `/actions/termsofservice.action` | GET | `Service.java:24 termsOfService()` | 이용약관 문서 | `/api/v1/helps` | MAP |

## 운영자 — `admin`

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 176 | `/actions/adminmain.action` | GET | `Main.java:124 adminMain()` | 관리자 메인 화면(정적) | `—` | DROP |
| 177 | `/actions/loadmgr.action` | GET | `LoadManager.java:22 adminLoadManager()` | 충전 신청 목록 | `/api/v1/admin/loads` | MAP |
| 178 | `/actions/loadmgrapprove.action` | POST | `LoadManager.java:44 adminLoadManagerApprove()` | 충전 신청 승인 | `/api/v1/admin/loads/{id}` | MAP |
| 179 | `/actions/loadmgrdeny.action` | POST | `LoadManager.java:66 adminLoadManagerDeny()` | 충전 신청 반려 | `/api/v1/admin/loads/{id}` | MERGE |

## 레거시 잔재 — `deadcode` (DROP 전용)

| # | 레거시 URL | 메서드 | 레거시 처리 | 기능 | 신규 엔드포인트 | 상태 |
|---|---|---|---|---|---|---|
| 180 | `/actions/batissample.action` | GET | `BatisSample.java:18 sample()` | MyBatis 연동 샘플 조회 — 죽은 코드 | `—` | DROP |
| 181 | `/actions/downloadtest.action` | GET | `FileTest.java:25 downloadTest()` | 파일 다운로드 테스트 — 죽은 코드 | `—` | DROP |
| 182 | `/actions/imagetest.action` | GET | `FileTest.java:19 imageTest()` | 이미지 출력 테스트 — 죽은 코드 | `—` | DROP |
| 183 | `/actions/uploaddownloadtest.action` | POST | `FileTest.java:49 uploadDownloadTest()` | 업로드/다운로드 왕복 테스트 — 죽은 코드 | `—` | DROP |
| 184 | `/actions/uploadimagetest.action` | POST | `FileTest.java:31 uploadImageTest()` | 이미지 업로드 테스트 — 죽은 코드 | `—` | DROP |

## 주석

1. `/actions/profile.action`은 어노테이션 핸들러(`Account.java:239`)와 XML 빈 정의(`ProfileFormController`)가 겹친다. 클래스가 없어 실제로는 어노테이션 핸들러만 동작하므로 XML 정의는 죽은 설정이다.
2. `/actions/verifyemail.action`은 GET/POST 두 개의 `@RequestMapping`이 같은 URL에 걸려 있어 2행으로 집계했다(합계 184에 포함).
3. `/actions/insepectinsert.action`은 철자 오타가 있으나 `dispatcher-servlet.xml`에 그대로 바인딩되어 동작한다. URL을 임의로 고치지 않고 원문 유지.
4. `/actions/`(`blackListEmail.java:19`)는 경로가 `actions` 디렉터리로 끝나 특정 액션에 도달하지 못한다. 의도한 URL이 무엇인지 소스만으로 확정할 수 없어 `⚠️ 확인필요`.
5. 코드 상 미사용: `com.lookation.dao.ISampleDAO` / `mapper/SampleDAO.xml` / `BatisSample`, `FileTest`, `TempLocationDTO`, `insertinfotest.action`, `WebContent/actions/*.action` 56개(전부 0바이트, 실행 코드 없음), 미바인딩 컨트롤러 3종(`AdminLogoutController`, `LocationPackageAjaxController`, `MypageFormController`).
6. `MypageMain`/`Account.profile`/`ProfileFormController`는 모두 마이페이지 진입점이라 `/api/v1/me` 하나로 합친다. `Main.membermain`도 같은 화면 데이터라 `MERGE`.
