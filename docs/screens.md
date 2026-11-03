# Lookation 화면·URL 지도

> AI 에이전트가 **"이 URL을 열면 어떤 JSP가 렌더되고, 어떤 권한이 필요한가"** 를 재탐색 없이 조회하기 위한 라우팅 맵이다.
> 모든 행은 컨트롤러 파일과 JSP 경로로 근거를 남겼고, 코드만으로 단정할 수 없는 역할은 `(추론)`으로 표시했다.
> 범위는 URL ↔ JSP ↔ 역할 ↔ 목적과 그 갭이다. 컨트롤러→DAO→매퍼 구조는 `docs/architecture.md`를 본다.

## 0. 근거 파일

| 근거 | 경로 | 내용 |
| --- | --- | --- |
| 서블릿 매핑 | `Lookation/WebContent/WEB-INF/web.xml:26` | `DispatcherServlet` ← `*.action`. 라우팅은 전부 `.action` 확장자 |
| 인코딩 필터 | `Lookation/WebContent/WEB-INF/web.xml:38-45` | `CharacterEncodingFilter` UTF-8, `/*` |
| URL→빈 매핑(저장소 사본) | `중요파일/02. dispatcher-servlet/dispatcher-servlet.xml` | XML 방식 20개. 웹루트 사본은 `.gitignore:60`으로 제외 |
| URL→빈 매핑(배포본) | `중요파일/00. export/호스팅용/ROOT.war` 내 `WEB-INF/dispatcher-servlet.xml` | 같은 20개지만 `adminlogout`/`profile` 구성이 다르다(§7.2) |
| 애노테이션 컨트롤러 | `Lookation/src/com/lookation/mybatis/*.java` | 37파일 / `@RequestMapping` 160개. `component-scan base-package="com.lookation.mybatis"`로 스캔 |
| XML 컨트롤러 | `Lookation/src/com/lookation/controller/*.java` | 22파일 / 등록 20개(`Controller.handleRequest`) |
| 뷰 | `Lookation/WebContent/WEB-INF/views/**/*.jsp` | 117개 (admin 35, common 32, host 28, user 20, ajax 2) |
| 공통 조각 | `Lookation/WebContent/includes/*.jsp` | 13개, 뷰 110개가 `<c:import>`로 사용 |

## 1. 30초 요약

| # | 사실 |
| --- | --- |
| 1 | **모든 URL은 `/actions/<이름>.action`** 이다. `/user/**`·`/host/**`·`/admin/**`·`/ajax/**` 같은 **역할별 URL 접두사는 없다** — 그 이름은 JSP 폴더다(§2.1). |
| 2 | 라우팅 체계가 **두 개 공존**한다. ① XML 빈 + `implements Controller` **20개**(`controller` 패키지, 애노테이션만 봐서는 발견 불가), ② `@Controller` + `@RequestMapping` **160개**(`mybatis` 패키지). 합계 **180개 매핑 / 고유 URL 179개**(`/actions/profile.action` 1개 중복). |
| 3 | JSP/JS가 링크하는 URL 이름은 **172개**이고, 그중 **168개**가 매핑되어 있다. 매핑 없는 4개는 §7.1. |
| 4 | GET/POST 구분은 애노테이션 쪽에만 있다. XML 20개는 `handleRequest` 하나라 **모든 메서드를 받는다**(표의 `GET/POST`). |
| 5 | 역할 구분은 세션 키 `memberCode`(이용자) / `hostCode`(호스트) / `admin_id`(관리자)이고, 공통 화면은 `identify=member\|host` 파라미터로 분기한다. |
| 6 | 뷰는 JSP forward다. `@ResponseBody` **0건** — AJAX도 `ajax/*.jsp`가 `${result}`/`${data}`를 찍는 방식(§6). |
| 7 | CSRF 도입 후 상태 변경 21개 엔드포인트와 메신저 전송 2개가 POST다. 토큰은 `_csrf` 파라미터 또는 `X-CSRF-Token` 헤더로 전달한다. |
| 8 | 180개 매핑 중 **123개**가 JSP 뷰를 반환하고, **57개**는 리다이렉트만 하거나 본문이 없다(중복 경로 포함 매핑 기준, §4). |

## 2. URL 접두사와 역할

### 2.1 실제 URL 공간

| 접두사 | 실체 | 근거 |
| --- | --- | --- |
| `/actions/` | **유일한 라우팅 공간**. DispatcherServlet이 `*.action`을 잡는다 | `web.xml:26` |
| `/user/`, `/host/`, `/admin/`, `/ajax/` | URL이 아니라 **JSP 폴더**(`WEB-INF/views/<폴더>`). `WEB-INF` 아래라 직접 접근 불가 | `views/` 트리 |
| `/includes/` | 정적 JSP 조각 13개. `WEB-INF` 밖이라 URL 직접 접근이 가능하다 | `WebContent/includes/` |
| `/css`, `/js`, `/images`, `/img`, `/vendor`, `/fonts`, `/lib`, `/ckeditor` | 정적 자원 | `WebContent/` |
| `/` (root) | welcome-file 6종(`index.html`~`default.jsp`)이 모두 없다 → **404**. 진입은 항상 `/actions/*.action` | `web.xml:4-11` |

### 2.2 역할별 진입 URL

| 역할 | 세션 키 | 진입 URL | 대표 JSP |
| --- | --- | --- | --- |
| 비회원 | — | `/actions/loginform.action`, `/actions/signupform.action?identify=member\|host`, `/actions/findpasswordform.action` | `common/login.jsp`, `common/signUp.jsp` |
| 이용자 | `memberCode` | `/actions/membermain.action`, `/actions/mypage.action?identify=member`, `/actions/locationdetail.action?loc_code=...` | `user/mainUser.jsp`, `user/mypageMember.jsp` |
| 호스트 | `hostCode` | `/actions/hostmain.action`, `/actions/mypage.action?identify=host`, `/actions/basicform.action` | `host/mainHost.jsp`, `host/mypageHost.jsp` |
| 관리자 | `admin_id` | `/actions/adminloginform.action` → `/actions/adminmain.action` | `admin/loginFormAdmin.jsp`, `admin/mainAdmin.jsp` |
| 공통(비회원 포함) | 없음 | `/actions/notice.action`, `/actions/help.action`, `/actions/termsofservice.action` | `common/notice.jsp`, `common/help.jsp` |

행 수 기준 분포(180): 관리자 55, 이용자/호스트(identify 분기) 36, 호스트 32, 공통 28, 이용자 18, 비회원 5, 테스트 5, 비회원→관리자 1.
역할 판정 순서: ① 세션 `memberCode`/`hostCode`/`admin_id` 검사 → ② `identify` 파라미터 분기 → ③ 뷰 폴더. ②·③으로 판정한 행은 `(추론)`을 붙였다.

## 3. URL → 컨트롤러 → JSP 전체 표 (180개)

`등록` 열: `anno` = `@RequestMapping` 자동 스캔, `xml` = `중요파일/02. dispatcher-servlet/dispatcher-servlet.xml`의 `<bean name="/actions/...">`.
**반환 문자열 원문**: 실제 반환값은 `"../WEB-INF/views/user/locationDetail.jsp"`(상대형, 대부분) 또는 `"/WEB-INF/views/user/search.jsp"`(절대형 — `Search.java:173`, `Notice.java:106`, `Help.java`). ViewResolver에 prefix/suffix가 없어 이 문자열이 곧 forward 경로다. 표의 `뷰(JSP)`는 읽기 쉽게 `WEB-INF/views/` 접두사를 뗀 상대 경로이며, 접두사를 붙이면 원문이 된다.
`—` 는 JSP로 forward하지 않는 엔드포인트(리다이렉트 전용 또는 본문 없음)다. 대상은 §4.

| URL | HTTP | 등록 | 처리 코드 (파일: 메서드) | 뷰(JSP) | 역할 | 목적 |
| --- | --- | --- | --- | --- | --- | --- |
| `/actions/` | GET/POST | anno | `blackListEmail.java: sendPasswordEmail` | `common/sendFindPassword.jsp`<br>`common/wrongAccess.jsp` | 이용자/호스트(identify 분기) (추론) | 인증이메일 발송 성공(JSP 제목) |
| `/actions/accountajax.action` | GET/POST | xml | `controller/AccountAjaxController.handleRequest` | `ajax/AccountAjax.jsp` | 비회원 | 이메일/닉네임 중복검사(AJAX) |
| `/actions/adminlogin.action` | GET/POST | xml | `controller/AdminLoginController.handleRequest` | — | 비회원→관리자 | — |
| `/actions/adminloginform.action` | GET/POST | xml | `controller/AdminLoginFormController.handleRequest` | `admin/loginFormAdmin.jsp` | 비회원 | 관리자 로그인(JSP 제목) |
| `/actions/adminmain.action` | GET | anno | `Main.java: adminMain` | `admin/mainAdmin.jsp` | 관리자 (추론) | admin(JSP 제목) |
| `/actions/ajaxfindemail.action` | GET/POST | anno | `Account.java: ajaxFindEmail` | `ajax/AccountAjax.jsp` | 이용자/호스트(identify 분기) (추론) | — |
| `/actions/ajaxmodifytel.action` | GET/POST | anno | `Account.java: ajaxModifyTel` | `ajax/AccountAjax.jsp` | 이용자/호스트(identify 분기) (추론) | — |
| `/actions/bankinfoadd.action` | GET/POST | xml | `controller/BankInfoAddController.handleRequest` | `common/timeout.jsp` | 이용자/호스트(identify 분기) | 계좌 등록 처리 |
| `/actions/bankinfoaddpopup.action` | GET/POST | xml | `controller/BankInfoAddPopupController.handleRequest` | `common/bankAccountAddPopup.jsp` | 이용자/호스트(identify 분기) | 계좌 등록 팝업 |
| `/actions/bankinfomanage.action` | GET/POST | xml | `controller/BankInfoManageController.handleRequest` | `common/bankAccount.jsp` | 이용자/호스트(identify 분기) | 계좌 관리 |
| `/actions/bankinforemove.action` | GET/POST | xml | `controller/BankInfoRemoveController.handleRequest` | — | 이용자/호스트(identify 분기) | 계좌 삭제 처리 |
| `/actions/basicajax.action` | POST | anno | `Location.java: basicAjax` | `common/wrongAccess.jsp` | 호스트 | — |
| `/actions/basicform.action` | GET | anno | `Location.java: basicForm` | `host/inputBasicInfo.jsp` | 호스트 | 기본정보 추가(JSP 제목) |
| `/actions/batissample.action` | GET | anno | `BatisSample.java: sample` | `sample.jsp` | 테스트 | — |
| `/actions/blind.action` | POST | anno | `ReportList.java: addBlind` | — | 관리자 | — |
| `/actions/blindcontent.action` | GET | anno | `BlindContent.java: blind` | `common/blindContent.jsp` | 이용자/호스트(identify 분기) (추론) | 블라인드 알림(JSP 제목) |
| `/actions/blindform.action` | GET | anno | `ReportList.java: blindForm` | `admin/addBlind.jsp` | 관리자 | 블랙리스트 등록(JSP 제목) |
| `/actions/blindmanager.action` | GET | anno | `BlindManager.java: list` | `admin/blindManager.jsp` | 관리자 | 블라인드 목록(JSP 제목) |
| `/actions/blindremove.action` | POST | anno | `BlindManager.java: remove` | — | 관리자 | — |
| `/actions/bookapply.action` | POST | anno | `BookApply.java: bookapplyBasic` | `user/bookApply.jsp` | 이용자 | 예약신청(JSP 제목) |
| `/actions/bookcancel.action` | GET | anno | `BookList.java: bookCancelPop` | `user/bookCancel.jsp` | 이용자 | 예약취소(JSP 제목) |
| `/actions/bookcancelhost.action` | GET | anno | `BookList.java: bookCancelPopHost` | `host/bookCancel.jsp` | 호스트 | 예약취소(JSP 제목) |
| `/actions/bookdetails.action` | GET | anno | `BookList.java: bookDetails` | `user/bookDetails.jsp` | 이용자 | 예약상세(JSP 제목) |
| `/actions/bookdetailshost.action` | GET | anno | `BookList.java: bookDetailsHost` | `user/bookDetails.jsp` | 호스트 | 예약상세(JSP 제목) |
| `/actions/booklist.action` | GET | anno | `BookList.java: bookList` | `user/bookList.jsp` | 이용자 | 예약리스트 확인(JSP 제목) |
| `/actions/booklisthost.action` | GET | anno | `BookList.java: bookListHost` | `host/bookList.jsp` | 호스트 | 예약리스트 확인(JSP 제목) |
| `/actions/bookpay.action` | POST | anno | `BookApply.java: MileageCheck` | `user/bookApplyNotice.jsp`<br>`user/mypageMember.jsp` | 이용자 | 선택한 패키지의 |
| `/actions/bookpopup0.action` | GET | anno | `ReportList.java: bookDetailList0` | `admin/bookReportPopup0.jsp` | 관리자 | 신고상세(JSP 제목) |
| `/actions/bookpopup1.action` | GET | anno | `ReportList.java: bookDetailList1` | `admin/bookReportPopup1.jsp` | 관리자 | 신고상세(JSP 제목) |
| `/actions/bookreportlist.action` | GET | anno | `ReportList.java: basicList` | `admin/bookReportList.jsp` | 관리자 | 신고목록(JSP 제목) |
| `/actions/bookreportproc.action` | GET | anno | `ReportList.java: bookReportProc` | — | 관리자 | — |
| `/actions/changepassword.action` | GET/POST | anno | `Account.java: changePassword` | `common/wrongAccess.jsp` | 이용자/호스트(identify 분기) (추론) | — |
| `/actions/changepasswordform.action` | GET | anno | `Account.java: changePasswordForm` | `common/changePassword.jsp` | 이용자/호스트(identify 분기) (추론) | 비밀번호 변경(JSP 제목) |
| `/actions/changepasswordnologin.action` | GET/POST | anno | `Account.java: changePasswordNoLogin` | `common/wrongAccess.jsp` | 이용자/호스트(identify 분기) (추론) | — |
| `/actions/changepasswordnologinform.action` | GET/POST | anno | `Account.java: changePasswordNoLoginForm` | `common/changePasswordNoLogin.jsp`<br>`common/wrongAccess.jsp` | 이용자/호스트(identify 분기) (추론) | 비밀번호 변경(JSP 제목) |
| `/actions/confirmpassword.action` | GET/POST | anno | `Account.java: confirmPassword` | `common/wrongAccess.jsp` | 이용자/호스트(identify 분기) (추론) | — |
| `/actions/confirmpasswordform.action` | GET | anno | `Account.java: confirmPasswordForm` | `common/confirmPassword.jsp` | 이용자/호스트(identify 분기) (추론) | 비밀번호 확인(JSP 제목) |
| `/actions/delblackhost.action` | POST | anno | `HostBlackManager.java: delBlackHost` | — | 공통 (추론) | — |
| `/actions/delblackmem.action` | POST | anno | `MemberBlackManager.java: delBlackmember` | — | 공통 (추론) | 이용자 |
| `/actions/deleteform.action` | GET/POST | xml | `controller/DeleteFormController.handleRequest` | `common/deleteAccount.jsp` | 이용자/호스트(identify 분기) | 탈퇴완료(JSP 제목) |
| `/actions/deletehost.action` | GET/POST | xml | `controller/DeleteHostController.handleRequest` | — | 호스트 | 호스트 탈퇴 처리 |
| `/actions/deletelocation.action` | POST | anno | `Location.java: deleteAction` | — | 호스트 | — |
| `/actions/deletemember.action` | GET/POST | xml | `controller/DeleteMemberController.handleRequest` | — | 이용자 | 이용자 탈퇴 처리 |
| `/actions/deletepackageform.action` | GET/POST | anno | `Package.java: deletePackageForm` | `common/wrongAccess.jsp` | 호스트 | — |
| `/actions/deleteqna.action` | POST | anno | `LocationQna.java: deleteQna` | — | 공통 (추론) | — |
| `/actions/deleteqnainlist.action` | POST | anno | `MemberQnaList.java: deleteQna` | — | 공통 (추론) | — |
| `/actions/deleteqnareply.action` | POST | anno | `LocationQna.java: deleteQnaReply` | — | 공통 (추론) | — |
| `/actions/deletereview.action` | POST | anno | `LocationReview.java: deleteReview` | — | 공통 (추론) | — |
| `/actions/deletereviewinlist.action` | POST | anno | `MemberReviewList.java: deleteReview` | — | 공통 (추론) | — |
| `/actions/deletereviewreply.action` | POST | anno | `LocationReview.java: deleteReviewReply` | — | 공통 (추론) | — |
| `/actions/downloadtest.action` | GET | anno | `FileTest.java: downloadTest` | `FormDownload.jsp` | 테스트 | — |
| `/actions/exchange.action` | POST | anno | `LoadAndExchange.java: exchangeReg` | `common/exchangeNotice.jsp` | 이용자/호스트(identify 분기) (추론) | 환전신청 완료(JSP 제목) |
| `/actions/findpasswordform.action` | GET | anno | `Account.java: findPasswordForm` | `common/findPassword.jsp` | 이용자/호스트(identify 분기) (추론) | 비밀번호 찾기(JSP 제목) |
| `/actions/help.action` | GET | anno | `Help.java: helpList` | `common/help.jsp` | 이용자/호스트(identify 분기) (추론) | 도움말(JSP 제목) |
| `/actions/helpdelete.action` | POST | anno | `HelpManager.java: helpDelete` | — | 관리자 | — |
| `/actions/helpdetail.action` | GET | anno | `Help.java: U_detailList` | `common/helpDetail.jsp` | 이용자/호스트(identify 분기) (추론) | 도움말(JSP 제목) |
| `/actions/helpinsert.action` | GET/POST | anno | `HelpManager.java: helpInsert` | — | 관리자 | — |
| `/actions/helpinsertform.action` | GET | anno | `HelpManager.java: helpInsertform` | `admin/inputHelpInfo.jsp` | 관리자 | 도움말 글쓰기(JSP 제목) |
| `/actions/helpmanager.action` | GET | anno | `HelpManager.java: helpList` | `admin/helpManager.jsp` | 관리자 | 도움말 목록(JSP 제목) |
| `/actions/helpupdate.action` | GET/POST | anno | `HelpManager.java: helpUpdate` | — | 관리자 | — |
| `/actions/helpupdateform.action` | GET | anno | `HelpManager.java: helpUpdateForm` | `admin/modifyHelpInfo.jsp` | 관리자 | 다음 페이지로 이동 |
| `/actions/hmessenger.action` | GET | anno | `HostMessenger.java: messageList` | `common/wrongAccess.jsp`<br>`host/hostMessenger.jsp` | 호스트 | 메신저(JSP 제목) |
| `/actions/hmsgsend.action` | GET/POST | anno | `HostMessenger.java: imgSend` | — | 공통 (추론) | — |
| `/actions/hostblack.action` | POST | anno | `ReportList.java: hostBlack` | — | 관리자 | — |
| `/actions/hostblackform.action` | GET | anno | `ReportList.java: hostBlackForm` | `admin/hostBlackListPopup.jsp` | 관리자 | 블랙리스트 추가(JSP 제목) |
| `/actions/hostblackmanager.action` | GET | anno | `HostBlackManager.java: hostBlackManager` | `admin/hostAccountManager.jsp` | 관리자 | 계정관리(JSP 제목) |
| `/actions/hostcancel.action` | POST | anno | `BookList.java: hostCancel` | — | 공통 (추론) | — |
| `/actions/hostlogout.action` | GET | anno | `Main.java: hostLogout` | — | 공통 (추론) | — |
| `/actions/hostmain.action` | GET | anno | `Main.java: hostMain` | `host/mainHost.jsp` | 호스트 | Lookation(JSP 제목) |
| `/actions/hostqnamanager.action` | GET | anno | `hostQnaManager.java: firstList` | `admin/hostQnaManager.jsp` | 관리자 | Q&A조회(JSP 제목) |
| `/actions/hostqnapopup.action` | GET | anno | `hostQnaManager.java: secondList` | `admin/hostQnaPopup.jsp` | 관리자 | 답글상세(JSP 제목) |
| `/actions/hostqnareplydelete.action` | POST | anno | `hostQnaManager.java: remove` | — | 관리자 | — |
| `/actions/hostreviewdelete.action` | POST | anno | `hostReviewManager.java: r_remove` | — | 관리자 | — |
| `/actions/hostreviewmanager.action` | GET | anno | `hostReviewManager.java: r_firstList` | `admin/hostReviewManager.jsp` | 관리자 | 리뷰조회(JSP 제목) |
| `/actions/hostreviewpopup.action` | GET | anno | `hostReviewManager.java: r_secondList` | `admin/hostReviewPopup.jsp` | 관리자 | 리뷰상세보기(JSP 제목) |
| `/actions/imagetest.action` | GET | anno | `FileTest.java: imageTest` | `FormImage.jsp` | 테스트 | — |
| `/actions/inputbasicinfo.action` | GET/POST | anno | `Location.java: inputBasicInfo` | `common/wrongAccess.jsp`<br>`host/inputContactInfo.jsp` | 호스트 | 연락처 추가(JSP 제목) |
| `/actions/inputbusinessinfo.action` | GET/POST | anno | `Location.java: inputBusinessInfo` | `common/wrongAccess.jsp`<br>`host/inputDetailInfo.jsp` | 호스트 | 상세정보 추가(JSP 제목) |
| `/actions/inputcontactinfo.action` | GET/POST | anno | `Location.java: inputContactInfo` | `common/wrongAccess.jsp`<br>`host/inputBusinessInfo.jsp` | 호스트 | 사업정보 추가(JSP 제목) |
| `/actions/inputdetailinfo.action` | GET/POST | anno | `Location.java: inputDetailInfo` | `common/wrongAccess.jsp`<br>`host/inputUsingInfo.jsp` | 호스트 | 이용정보 추가(JSP 제목) |
| `/actions/inputpackageform.action` | GET/POST | anno | `Package.java: inputPackageForm` | `common/wrongAccess.jsp` | 호스트 | — |
| `/actions/inputusinginfo.action` | GET/POST | anno | `Location.java: inputUsingInfo` | `common/wrongAccess.jsp` | 호스트 | — |
| `/actions/insepectinsert.action` | GET/POST | xml | `controller/InspectInsertController.handleRequest` | — | 관리자 | 검수 처리 등록(오타 URL) |
| `/actions/insertinfotest.action` | GET | anno | `Location.java: insertinfotest` | `host/locationList.jsp` | 호스트 (추론) | 공간관리(JSP 제목) |
| `/actions/inspectmanager.action` | GET/POST | xml | `controller/InspectManagerController.handleRequest` | `admin/inspectManager.jsp` | 관리자 | 공간 검수 목록 |
| `/actions/inspectprocess.action` | GET/POST | xml | `controller/InspectProcessController.handleRequest` | `admin/inspectProcess.jsp` | 관리자 | 검수 상세/처리 |
| `/actions/loadandexchange.action` | GET/POST | anno | `LoadAndExchange.java: bankInfoAndBalance` | `host/exchangeHost.jsp`<br>`user/loadAndExchangeUser.jsp` | 이용자/호스트(identify 분기) (추론) | 이용 |
| `/actions/loadmgr.action` | GET | anno | `LoadManager.java: adminLoadManager` | `admin/loadManager.jsp` | 관리자 | 충전신청 관리(JSP 제목) |
| `/actions/loadmgrapprove.action` | GET | anno | `LoadManager.java: adminLoadManagerApprove` | — | 관리자 | — |
| `/actions/loadmgrdeny.action` | GET | anno | `LoadManager.java: adminLoadManagerDeny` | — | 관리자 | — |
| `/actions/locationdetail.action` | GET/POST | xml | `controller/LocationDetailController.handleRequest` | `user/locationDetail.jsp`<br>`user/wrongLocation.jsp` | 이용자 | 공간 상세 |
| `/actions/locationdetailhost.action` | GET/POST | xml | `controller/LocationDetailHostController.handleRequest` | `host/locationDetailHost.jsp`<br>`common/wrongAccess.jsp` | 호스트 | 공간상세(JSP 제목) |
| `/actions/locationlist.action` | GET | anno | `Location.java: locationList` | `host/locationList.jsp` | 호스트 | 공간관리(JSP 제목) |
| `/actions/locdetailajax.action` | GET/POST | xml | `controller/LocationDetailAjaxController.handleRequest` | `ajax/LocationDetailAjax.jsp` | 이용자 | 날짜별 패키지 JSON(AJAX) |
| `/actions/locmanagement.action` | GET | anno | `Service.java: locManagement` | `common/locationManagementPolicy.jsp` | 이용자/호스트(identify 분기) (추론) | 공간관리정책(JSP 제목) |
| `/actions/locpopup0.action` | GET | anno | `ReportList.java: locDetailList0` | `admin/locReportPopup0.jsp` | 관리자 | 신고상세(JSP 제목) |
| `/actions/locpopup1.action` | GET | anno | `ReportList.java: locDetailList1` | `admin/locReportPopup1.jsp` | 관리자 | 신고상세(JSP 제목) |
| `/actions/locreportproc.action` | GET | anno | `ReportList.java: locReportProc` | — | 관리자 | — |
| `/actions/login.action` | GET/POST | anno | `Account.java: login` | `common/wrongAccess.jsp` | 이용자/호스트(identify 분기) (추론) | — |
| `/actions/loginform.action` | GET | anno | `Account.java: loginForm` | `common/login.jsp` | 이용자/호스트(identify 분기) (추론) | 로그인(JSP 제목) |
| `/actions/memberblackmanager.action` | GET | anno | `MemberBlackManager.java: memberBlackManager` | `admin/userAccountManager.jsp` | 관리자 | 계정관리(JSP 제목) |
| `/actions/membercancel.action` | POST | anno | `BookList.java: memberCancel` | — | 공통 (추론) | — |
| `/actions/memberload.action` | POST | anno | `LoadAndExchange.java: loadReg` | `user/loadRegisterNotice.jsp` | 이용자 | 충전신청 완료(JSP 제목) |
| `/actions/memberlogout.action` | GET | anno | `Main.java: memberLogout` | — | 공통 (추론) | — |
| `/actions/membermain.action` | GET | anno | `Main.java: memberMain` | `user/mainUser.jsp` | 이용자 | Lookation(JSP 제목) |
| `/actions/mileagehistory.action` | GET | anno | `MileageHistoryList.java: historyList` | `common/login.jsp`<br>`host/mileageHistory.jsp`<br>`user/mileageHistory.jsp` | 이용자/호스트(identify 분기) (추론) | 로그인(JSP 제목) |
| `/actions/mmessenger.action` | GET | anno | `MemberMessenger.java: messageList` | `common/wrongAccess.jsp`<br>`user/userMessenger.jsp` | 이용자 | 메신저(JSP 제목) |
| `/actions/mmsgsend.action` | GET/POST | anno | `MemberMessenger.java: imgSend` | — | 공통 (추론) | — |
| `/actions/modifybasicform.action` | GET | anno | `Location.java: modifyBasicForm` | `host/modifyBasicInfo.jsp` | 호스트 | 기본정보 수정(JSP 제목) |
| `/actions/modifybasicinfo.action` | GET/POST | anno | `Location.java: modifyBasicInfo` | `common/wrongAccess.jsp`<br>`host/modifyContactInfo.jsp` | 호스트 | 연락처 수정(JSP 제목) |
| `/actions/modifycontactinfo.action` | GET/POST | anno | `Location.java: modifyContactInfo` | `common/wrongAccess.jsp`<br>`host/modifyDetailInfo.jsp` | 호스트 | 상세정보 수정(JSP 제목) |
| `/actions/modifydetailinfo.action` | GET/POST | anno | `Location.java: modifyDetailInfo` | `common/wrongAccess.jsp`<br>`host/modifyUsingInfo.jsp` | 호스트 | 이용정보 수정(JSP 제목) |
| `/actions/modifyformqna.action` | GET | anno | `LocationQna.java: modifyFormQna` | `common/modifyQnaPopup.jsp` | 이용자/호스트(identify 분기) (추론) | Q&A수정(JSP 제목) |
| `/actions/modifyformreview.action` | GET | anno | `LocationReview.java: reviewModifyForm` | `common/modifyReviewPopup.jsp` | 이용자/호스트(identify 분기) (추론) | 리뷰수정(JSP 제목) |
| `/actions/modifylocation.action` | GET | anno | `Location.java: modifyAction` | — | 호스트 | — |
| `/actions/modifyqna.action` | POST | anno | `LocationQna.java: modifyQna` | — | 공통 (추론) | — |
| `/actions/modifyqnainlist.action` | POST | anno | `MemberQnaList.java: modifyQna` | — | 공통 (추론) | — |
| `/actions/modifyqnareply.action` | POST | anno | `LocationQna.java: modifyQnaReply` | — | 공통 (추론) | — |
| `/actions/modifyreview.action` | GET/POST | anno | `LocationReview.java: modifyReview` | — | 공통 (추론) | — |
| `/actions/modifyreviewinlist.action` | POST | anno | `MemberReviewList.java: modifyReview` | — | 공통 (추론) | — |
| `/actions/modifyreviewreply.action` | POST | anno | `LocationReview.java: modifyReviewReply` | — | 공통 (추론) | — |
| `/actions/modifyusinginfo.action` | GET/POST | anno | `Location.java: modifyUsingInfo` | `common/wrongAccess.jsp` | 호스트 | — |
| `/actions/mypage.action` | GET | anno | `MypageMain.java: mypage` | `host/mypageHost.jsp`<br>`user/mypageMember.jsp` | 이용자/호스트(identify 분기) (추론) | 마이페이지(JSP 제목) |
| `/actions/notice.action` | GET | anno | `Notice.java: U_noticeList` | `common/notice.jsp` | 이용자/호스트(identify 분기) (추론) | 공지사항(JSP 제목) |
| `/actions/noticedelete.action` | POST | anno | `NoticeManager.java: noticeDelete` | — | 관리자 | — |
| `/actions/noticedetail.action` | GET | anno | `Notice.java: U_ndetailList` | `common/noticeDetail.jsp` | 공통 (추론) | 공지사항(JSP 제목) |
| `/actions/noticeinsert.action` | POST | anno | `NoticeManager.java: noticeInsert` | — | 관리자 | — |
| `/actions/noticeinsertform.action` | GET | anno | `NoticeManager.java: noticeInsertform` | `admin/inputNoticeInfo.jsp` | 관리자 | 공지사항 글쓰기(JSP 제목) |
| `/actions/noticemanager.action` | GET | anno | `NoticeManager.java: noticeList` | `admin/noticeManager.jsp` | 관리자 | 공지사항 목록(JSP 제목) |
| `/actions/noticeupdate.action` | GET/POST | anno | `NoticeManager.java: noticeUpdate` | — | 관리자 | — |
| `/actions/noticeupdateform.action` | GET | anno | `NoticeManager.java: noticeUpdateForm` | `admin/modifyNoticeInfo.jsp` | 관리자 | 공지사항 수정(JSP 제목) |
| `/actions/packageapplyajax.action` | GET/POST | anno | `Package.java: packageApplyAjax` | — | 호스트 | — |
| `/actions/packageapplyform.action` | GET | anno | `Package.java: packageApplyForm` | `host/packageApply.jsp` | 호스트 | 예약등록(JSP 제목) |
| `/actions/packageform.action` | GET | anno | `Package.java: packageForm` | `host/inputPackageInfo.jsp` | 호스트 | 패키지정보 추가(JSP 제목) |
| `/actions/packagemanager.action` | GET | anno | `Package.java: packageManager` | `host/inputPackageInfoForm.jsp` | 호스트 | 패키지정보 추가(JSP 제목) |
| `/actions/privacy.action` | GET | anno | `Service.java: privacy` | `common/privacyPolicy.jsp` | 이용자/호스트(identify 분기) (추론) | 개인정보처리방침(JSP 제목) |
| `/actions/profile.action` | GET | anno | `Account.java: profile` | `common/profile.jsp` | 이용자/호스트(identify 분기) (추론) | 프로필(JSP 제목) |
| `/actions/profile.action` | GET/POST | xml | `controller/ProfileFormController(클래스 없음).handleRequest` | `common/profile.jsp` | 이용자/호스트(identify 분기) | 프로필(JSP 제목) |
| `/actions/qnadelete.action` | POST | anno | `UserQnaManager.java: remove` | — | 관리자 | — |
| `/actions/qnainsert.action` | POST | anno | `LocationQna.java: insertQna` | — | 공통 (추론) | — |
| `/actions/qnareplyinsert.action` | POST | anno | `LocationQna.java: insertQnaReply` | — | 공통 (추론) | — |
| `/actions/refund.action` | GET | anno | `Service.java: refund` | `common/refundPolicy.jsp` | 이용자/호스트(identify 분기) (추론) | 환불정책(JSP 제목) |
| `/actions/reportdetails.action` | GET | anno | `HostBlackManager.java: viewReportDetailsHost` | `admin/reportDetailPopupHost.jsp` | 관리자 | 신고상세(JSP 제목) |
| `/actions/reporthost.action` | GET/POST | anno | `ReportHost.java: add` | `host/reportHostImg.jsp` | 호스트 | 신고(JSP 제목) |
| `/actions/reporthostform.action` | GET | anno | `BookList.java: addhostForm` | `host/reportHost.jsp` | 호스트 | 신고(JSP 제목) |
| `/actions/reporthostimg.action` | GET/POST | anno | `ReportHost.java: addImg` | — | 호스트 | — |
| `/actions/reportlist.action` | GET | anno | `ReportList.java: locBasicList` | `admin/reportList.jsp` | 관리자 | 신고관리(JSP 제목) |
| `/actions/reportmemdetails.action` | GET | anno | `MemberBlackManager.java: viewReportDetailsmember` | `admin/reportDetailPopupUser.jsp` | 관리자 | 신고상세(JSP 제목) |
| `/actions/reportuser.action` | GET/POST | anno | `ReportUser.java: add` | `user/reportUserImg.jsp` | 이용자 | 신고(JSP 제목) |
| `/actions/reportuserform.action` | GET | anno | `BookList.java: addForm` | `user/reportUser.jsp` | 이용자 | 신고(JSP 제목) |
| `/actions/reportuserimg.action` | GET/POST | anno | `ReportUser.java: addImg` | — | 이용자 | — |
| `/actions/reviewdelete.action` | POST | anno | `UserReviewManager.java: remove` | — | 관리자 | — |
| `/actions/reviewinsert.action` | GET/POST | anno | `LocationReview.java: insertReview` | — | 공통 (추론) | — |
| `/actions/reviewreplyinsert.action` | POST | anno | `LocationReview.java: insertReviewReply` | — | 공통 (추론) | — |
| `/actions/search.action` | GET/POST | anno | `Search.java: searchKeyword` | `user/search.jsp` | 이용자 | 검색결과(JSP 제목) |
| `/actions/searchkeyword.action` | GET/POST | anno | `Search.java: searchKeywordOther` | `user/search.jsp` | 이용자 | 검색결과(JSP 제목) |
| `/actions/selectdelete.action` | POST | anno | `UserQnaManager.java: multiRemove` | — | 관리자 | — |
| `/actions/selectremove.action` | POST | anno | `UserReviewManager.java: multiRemove` | — | 관리자 | — |
| `/actions/sendconfirmemail.action` | GET/POST | anno | `Email.java: sendPasswordEmail` | `common/sendFindPassword.jsp`<br>`common/wrongAccess.jsp` | 이용자/호스트(identify 분기) (추론) | 인증이메일 발송 성공(JSP 제목) |
| `/actions/sendsignupemail.action` | POST | anno | `mybatis/SignupEmail.send` | 인증 메일 링크 | 비회원 | 가입 인증 메일 발송 |
| `/actions/setblackhost.action` | POST | anno | `HostBlackManager.java: regBlackHost` | — | 공통 (추론) | — |
| `/actions/setblackhostform.action` | GET | anno | `HostBlackManager.java: setBlackHost` | `admin/blacklistPopupHost.jsp` | 관리자 | 블랙리스트 등록(JSP 제목) |
| `/actions/setblackmem.action` | POST | anno | `MemberBlackManager.java: regBlackmember` | — | 공통 (추론) | — |
| `/actions/setblackmemform.action` | GET | anno | `MemberBlackManager.java: setBlackmember` | `admin/blacklistPopupUser.jsp` | 관리자 | 블랙리스트 등록(JSP 제목) |
| `/actions/verifyemail.action` | GET | anno | `mybatis/VerifyEmail.confirm` | `common/emailVerifyConfirm.jsp` | 비회원 | 인증 확인 폼 |
| `/actions/verifyemail.action` | POST | anno | `mybatis/VerifyEmail.verify` | `common/emailVerified.jsp`<br>`common/emailVerifyFail.jsp` | 비회원 | 토큰 검증 후 계정 생성 |
| `/actions/signupform.action` | GET/POST | xml | `controller/SignUpFormController.handleRequest` | `common/signUp.jsp` | 비회원 | 회원가입 폼(이용자/호스트) |
| `/actions/terms.action` | GET | anno | `LoadAndExchange.java: loadAndExchangeTerms` | `common/bankAccountTerms.jsp` | 공통 (추론) | Insert title here(JSP 제목) |
| `/actions/termsofservice.action` | GET | anno | `Service.java: termsOfService` | `common/termsOfServicePolicy.jsp` | 이용자/호스트(identify 분기) (추론) | 이용약관(JSP 제목) |
| `/actions/uploaddownloadtest.action` | POST | anno | `FileTest.java: uploadDownloadTest` | `ViewDownload.jsp` | 테스트 | — |
| `/actions/uploadimagetest.action` | POST | anno | `FileTest.java: uploadImageTest` | `View.jsp` | 테스트 | — |
| `/actions/userblack.action` | POST | anno | `ReportList.java: userBlack` | — | 관리자 | — |
| `/actions/userblackform.action` | GET | anno | `ReportList.java: userBlackForm` | `admin/userBlackListPopup.jsp` | 관리자 | 블랙리스트 추가(JSP 제목) |
| `/actions/userqnalist.action` | GET | anno | `MemberQnaList.java: memberQnaList` | `user/userQnaList.jsp` | 이용자 | Q&A 목록(JSP 제목) |
| `/actions/userqnamanager.action` | GET | anno | `UserQnaManager.java: basicList` | `admin/userQnaManager.jsp` | 관리자 | Q&A관리(JSP 제목) |
| `/actions/userqnapopup.action` | GET | anno | `UserQnaManager.java: detailList` | `admin/userQnaPopup.jsp` | 관리자 | Q&A상세(JSP 제목) |
| `/actions/userreviewlist.action` | GET | anno | `MemberReviewList.java: userReviewList` | `user/userReviewList.jsp` | 이용자 | 리뷰 목록(JSP 제목) |
| `/actions/userreviewmanager.action` | GET | anno | `UserReviewManager.java: basicList` | `admin/userReviewManager.jsp` | 관리자 | 리뷰관리(JSP 제목) |
| `/actions/userreviewpopup.action` | GET | anno | `UserReviewManager.java: detailList` | `admin/userReviewPopup.jsp` | 관리자 | 리뷰상세(JSP 제목) |
| `/actions/writeqna.action` | GET | anno | `LocationQna.java: QnaForm` | `common/writeQnaPopup.jsp` | 이용자/호스트(identify 분기) (추론) | Q&A작성(JSP 제목) |
| `/actions/writereview.action` | GET | anno | `LocationReview.java: reviewForm` | `common/writeReviewPopup.jsp` | 이용자/호스트(identify 분기) (추론) | 리뷰작성(JSP 제목) |

## 4. 리다이렉트 지도

133개 엔드포인트가 `redirect:`를 반환한다. 리다이렉트 대상은 대부분 로그인 가드가 보내는 곳이므로, 대상이 매핑되지 않으면 인증 실패가 404로 이어진다(§7.1).

| 리다이렉트 대상 | 출발 엔드포인트 수 | 출발 예시 |
| --- | --- | --- |
| `loginform.action?identify={member\|host}` | 59 | `/actions/bankinfoaddpopup.action`, `/actions/bankinfomanage.action`, `/actions/bankinforemove.action` 외 56 |
| `adminloginform.action` | 55 | `/actions/adminlogin.action`, `/actions/blind.action`, `/actions/blindform.action` 외 52 |
| `locationdetail.action` | 4 | `/actions/deleteqna.action`, `/actions/deletereview.action`, `/actions/modifyqna.action` 외 1 |
| `locationdetailhost.action` | 4 | `/actions/deleteqnareply.action`, `/actions/deletereviewreply.action`, `/actions/qnareplyinsert.action` 외 1 |
| `locationlist.action` | 4 | `/actions/deletelocation.action`, `/actions/inputusinginfo.action`, `/actions/modifylocation.action` 외 1 |
| `(동적)` | 3 | `/actions/changepasswordnologin.action`, `/actions/confirmpassword.action`, `/actions/login.action` |
| `helpmanager.action` | 3 | `/actions/helpdelete.action`, `/actions/helpinsert.action`, `/actions/helpupdate.action` |
| `noticemanager.action` | 3 | `/actions/noticedelete.action`, `/actions/noticeinsert.action`, `/actions/noticeupdate.action` |
| `hostblackmanager.action` | 2 | `/actions/delblackhost.action`, `/actions/setblackhost.action` |
| `hostmain.action` | 2 | `/actions/hostlogout.action`, `/actions/sendsignupemail.action` |
| `loadmgr.action` | 2 | `/actions/loadmgrapprove.action`, `/actions/loadmgrdeny.action` |
| `membermain.action` | 2 | `/actions/memberlogout.action`, `/actions/sendsignupemail.action` |
| `mypage.action?identify={member\|host}` | 2 | `/actions/deletehost.action`, `/actions/deletemember.action` |
| `packagemanager.action` | 2 | `/actions/deletepackageform.action`, `/actions/inputpackageform.action` |
| `userqnamanager.action` | 2 | `/actions/qnadelete.action`, `/actions/selectdelete.action` |
| `userreviewmanager.action` | 2 | `/actions/reviewdelete.action`, `/actions/selectremove.action` |
| `/actions/bankinfomanage.action?identify={member\|host}` | 1 | `/actions/bankinforemove.action` |
| `/actions/inspectmanager.action` | 1 | `/actions/insepectinsert.action` |
| `adminmain.action` | 1 | `/actions/adminlogin.action` |
| `blindmanager.action` | 1 | `/actions/blindremove.action` |
| `bookreportlist.action` | 1 | `/actions/bookreportproc.action` |
| `confirmpasswordform.action?identify={member\|host}` | 1 | `/actions/confirmpassword.action` |
| `hmessenger.action` | 1 | `/actions/hmsgsend.action` |
| `hostqnamanager.action` | 1 | `/actions/hostqnareplydelete.action` |
| `hostreviewmanager.action` | 1 | `/actions/hostreviewdelete.action` |
| `memberblackmanager.action` | 1 | `/actions/delblackmem.action` |
| `mmessenger.action` | 1 | `/actions/mmsgsend.action` |
| `modifybasicform.action?identify={member\|host}` | 1 | `/actions/modifylocation.action` |
| `profile.action?identify={member\|host}` | 1 | `/actions/changepassword.action` |
| `reportlist.action` | 1 | `/actions/locreportproc.action` |
| `userqnalist.action` | 1 | `/actions/deleteqnainlist.action` |
| `userreviewlist.action` | 1 | `/actions/deletereviewinlist.action` |

## 5. JSP 폴더 규약과 공통 include

### 5.1 폴더

| 폴더 | JSP 수 | 용도 | 주로 쓰는 공통 include | 대표 파일 |
| --- | --- | --- | --- | --- |
| `admin/` | 35 | 관리자 콘솔(회원·신고·검수·공지·블라인드 관리) | `includes_admin.jsp`, `header.jsp`, `Admin_Sidebar.jsp` | `admin/mainAdmin.jsp`, `admin/reportList.jsp` |
| `common/` | 32 | 비회원·공통(로그인, 가입, 정책, 공지, 도움말, 계좌, 탈퇴) | `header_user.jsp`/`header_host.jsp`, `footer_*`, `includes_home*.jsp` | `common/login.jsp`, `common/signUp.jsp`, `common/profile.jsp` |
| `host/` | 28 | 호스트 콘솔(공간 등록·수정, 예약, 마일리지) | `header_host.jsp`, `footer_host.jsp`, `mypage_Sidebar(host).jsp` | `host/mainHost.jsp`, `host/locationList.jsp` |
| `user/` | 20 | 이용자(검색, 공간 상세, 예약, 마이페이지) | `header_user.jsp`, `footer_user.jsp`, `mypage_Sidebar(user).jsp` | `user/mainUser.jsp`, `user/locationDetail.jsp` |
| `ajax/` | 2 | AJAX 응답 본문만 출력(`${result}` / `${data}`) | 없음 | `ajax/AccountAjax.jsp`, `ajax/LocationDetailAjax.jsp` |

### 5.2 공통 include (`Lookation/WebContent/includes/`)

뷰는 정적 include가 아니라 **JSTL `<c:import url="${cp}/includes/...">`** 로 조각을 끼워 넣는다. `<%@ include file=... %>` 형태는 9곳에 남아 있으나 **전부 `<%-- --%>` 주석 안**이라 실행되지 않는다(예: `views/common/notice.jsp:19`).

| 파일 | 내용 | 사용(뷰 수) |
| --- | --- | --- |
| `includes/includes_home.jsp` | 부트스트랩·폰트·`css/style.css` 스타일 묶음 | 69 |
| `includes/includes_home_end.jsp` | jQuery·bootstrap·owl 스크립트 묶음 | 68 |
| `includes/header_host.jsp` | 호스트 상단 GNB + 로그인 상태(`result`, `nick`) | 39 |
| `includes/footer_host.jsp` | 호스트 푸터(정책 링크 `locmanagement`, `privacy`, `refund`) | 37 |
| `includes/includes_admin.jsp` | 관리자용 sb-admin-2 CSS/폰트 | 35 |
| `includes/defaults.jsp` | `css/bootstrap.css`, `js/jquery-3.2.1.min.js` (구형 묶음) | 31 |
| `includes/footer_user.jsp` | 이용자 푸터(정책 링크) | 28 |
| `includes/header_user.jsp` | 이용자 상단 GNB + 검색 폼(`search`, `searchkeyword`) | 27 |
| `includes/Admin_Sidebar.jsp` | 관리자 좌측 메뉴 — 관리자 URL 목록의 원본 | 18 |
| `includes/header.jsp` | 구형 헤더(관리자 페이지 일부) | 18 |
| `includes/mypage_Sidebar(user).jsp` | 이용자 마이페이지 사이드바 | 9 |
| `includes/mypage_Sidebar(host).jsp` | 호스트 마이페이지 사이드바 | 7 |
| `includes/includes_loader.jsp`, `includes/header test.jsp` | 미사용/실험 파일 | 0 |

`Admin_Sidebar.jsp`와 `mypage_Sidebar(*).jsp`가 **역할별 URL 목록의 원본**이다. 새 화면은 여기에도 링크를 넣어야 화면에서 도달할 수 있다.

### 5.3 뷰 이름 규약

| 방식 | 표기 | 예 |
| --- | --- | --- |
| 애노테이션 컨트롤러 | `return "../WEB-INF/views/<폴더>/<파일>.jsp"` (일부 `/WEB-INF/views/...`) | `Account.java:35`, `Notice.java:106` |
| XML 컨트롤러 | `mav.setViewName("../WEB-INF/views/...")` | `AdminLoginFormController.java:22` |
| 리다이렉트 | `"redirect:<name>.action?..."` | `AdminLoginController.java:56` |

`dispatcher-servlet.xml`에 `ViewResolver` 빈이 없어 뷰 이름이 곧 forward 경로다(`/actions/x.action` 기준 상대경로라 `../WEB-INF/...`가 성립).
주의: 여러 컨트롤러가 뷰 이름 뒤에 `?identify=member`를 붙이지만(`BankInfoManageController.java:72`), **forward 대상의 쿼리스트링은 요청 파라미터로 전달되지 않는다**(추론: 서블릿 forward 규약). 따라서 그 JSP의 `request.getParameter("identify")`는 null이 된다.

### 5.4 세션·권한 규약

| 세션 키 | 설정 주체 | 검사 위치 |
| --- | --- | --- |
| `memberCode` | 로그인 처리(`mybatis/Account.java: login`) | `LocationDetailController`, `DeleteMemberController`, `Search.java`, `BookList.java`, JSP 5곳 |
| `hostCode` | 로그인 처리(`mybatis/Account.java: login`) | `LocationDetailHostController`, `DeleteHostController`, `Location.java`, `Package.java` |
| `admin_id` | `AdminLoginController.java:59` | `Inspect*Controller`, 관리자 애노테이션 컨트롤러 전부 |
| `beforeUrl` / `requestUrl` | `Account.java: loginForm` / `confirmPasswordForm` | 로그인 후 복귀 URL |

`sessionID`는 `common/writeQnaPopup.jsp:8` 등 3개 팝업 JSP에 **주석으로만** 남아 있고 세팅 코드가 없다 — 세션 키가 아니라 잔재다.

### 5.5 함정: URL 이름과 JSP 이름이 한 칸씩 어긋난다

공간 등록/수정은 단계별 위저드라 **직전 단계의 액션이 다음 단계의 폼을 렌더**한다. 이름만 보고 짝을 추정하면 틀린다(`Location.java` 기준).

| URL | 실제 반환 JSP |
| --- | --- |
| `/actions/basicform.action` | `host/inputBasicInfo.jsp` |
| `/actions/inputbasicinfo.action` | `host/inputContactInfo.jsp` |
| `/actions/inputcontactinfo.action` | `host/inputBusinessInfo.jsp` |
| `/actions/inputbusinessinfo.action` | `host/inputDetailInfo.jsp` |
| `/actions/inputdetailinfo.action` | `host/inputUsingInfo.jsp` |
| `/actions/modifybasicinfo.action` | `host/modifyContactInfo.jsp` |
| `/actions/insertinfotest.action` | `host/locationList.jsp` |

## 6. AJAX 엔드포인트

`@ResponseBody`도 `MappingJackson2HttpMessageConverter`도 쓰지 않는다. **응답도 JSP forward**이고 `ajax/*.jsp`가 `${result}` 또는 `${data}`만 출력한다. Content-Type은 JSP page 지시자에 따라 `text/html; charset=UTF-8`이라 JSON도 `application/json`이 아니다.

| URL | 처리 코드 | 요청 파라미터 | 응답 | 응답 뷰 |
| --- | --- | --- | --- | --- |
| `/actions/accountajax.action` | `controller/AccountAjaxController.java: handleRequest` (xml) | `type=email\|nick`, `email`, `nick` | 중복 시 안내 문구, 정상이면 빈 문자열 | `ajax/AccountAjax.jsp` |
| `/actions/ajaxfindemail.action` | `mybatis/Account.java: ajaxFindEmail` (anno) | `identify`, `email` | `"존재하지 않는 이메일"` / `"이미 존재하는 이메일"` | `ajax/AccountAjax.jsp` |
| `/actions/ajaxmodifytel.action` | `mybatis/Account.java: ajaxModifyTel` (anno) | `identify`, `tel` | 처리 결과 문자열 | `ajax/AccountAjax.jsp` |
| `/actions/locdetailajax.action` | `controller/LocationDetailAjaxController.java: handleRequest` (xml) | `selectDate`, `loc_code` | `[{"packageName":..,"packCode":..,"packStart":..,"packPrice":..,"applyDate":..,"packEnd":..}]` (문자열 결합 생성) | `ajax/LocationDetailAjax.jsp` |
| `/actions/packageapplyajax.action` | `mybatis/Package.java: packageApplyAjax` (anno) | `loc_code` | 비로그인·타인 공간이면 빈 문자열 | — (경로에 따라 뷰 없음) |
| `/actions/basicajax.action` | `mybatis/Location.java: basicAjax` (anno) | `facility[]`, `precaution[]` | 시설/주의사항 등록 처리 | `common/wrongAccess.jsp`(검증 실패 시) |

`LocationPackageAjaxController`(패키지 날짜 JSON)는 클래스만 있고 빈 등록이 없어 호출 경로가 없다(§7.2).

## 7. 갭

### 7.1 참조되지만 매핑이 없는 URL (4개)

JSP/JS가 링크하지만 `@RequestMapping`도 XML 빈도 없다. 클릭하면 DispatcherServlet이 핸들러를 찾지 못한다(404).

**architecture.md §6-12와의 차이**: 그 문서는 `checkifhavebankinfo`, `home` **2건**을 예시로 적었다. 전수 스캔 결과는 **4건**이고 차이는 `adminlogout`, `inputxxxInfo`다. `adminlogout`은 **배포본 `ROOT.war`의 `dispatcher-servlet.xml`에는 빈이 있고 저장소 사본에는 없다**(§7.2). 즉 저장소 사본 기준 4건, 배포본 기준 3건이다.

| URL | 참조하는 JSP | 비고 |
| --- | --- | --- |
| `/actions/adminlogout.action` | `admin/blindManager.jsp`, `admin/bookReportList.jsp`, `admin/helpManager.jsp`, `admin/hostQnaManager.jsp` 외 11 | 관리자 로그아웃. `AdminLogoutController.java`는 존재하지만 저장소 사본 `dispatcher-servlet.xml`에 빈이 없다 |
| `/actions/checkifhavebankinfo.action` | `includes/mypage_Sidebar(user).jsp` | 계좌 보유 여부 확인 AJAX |
| `/actions/home.action` | `user/bookApply.jsp` | 메인으로 이동 |
| `/actions/inputxxxInfo.action` | `host/inputPackageInfo.jsp`, `host/modifyPackageInfo.jsp` | 공간 정보 입력 공통 진입(파라미터로 분기하는 의도, 추론) |

### 7.2 등록이 어긋난 컨트롤러

| 문제 | 대상 | 근거 |
| --- | --- | --- |
| 빈이 **없는 클래스**를 참조 | `/actions/profile.action` → `com.lookation.controller.ProfileFormController` | `중요파일/02. dispatcher-servlet/dispatcher-servlet.xml:190`. `src/com/lookation/controller/`에 해당 클래스 없음 → 이 사본으로 기동하면 컨텍스트 로딩 실패(추론). 같은 URL을 `mybatis/Account.java: profile`이 애노테이션으로도 잡아 **두 체계의 URL 교집합 1건**이 생긴다. architecture.md §0-1도 같은 기준(180 매핑 / 고유 179)으로 정리했다. |
| 저장소 사본과 배포본이 다름 | `adminlogout.action`, `profile.action` | 배포본 `ROOT.war`의 `dispatcher-servlet.xml`에는 `adminlogout.action` 빈이 있고 `profile.action` 빈은 없다. 저장소 사본은 반대다. §3의 XML 20건은 **저장소 사본 기준** |
| 클래스는 있는데 **빈 미등록** | `AdminLogoutController.java`(`adminlogout`), `MypageFormController.java`, `LocationPackageAjaxController.java` | 저장소 사본 `dispatcher-servlet.xml`에 세 클래스의 `<bean>` 없음. `MypageFormController`가 반환하는 `user/mypageUser.jsp`도 존재하지 않는다(실제 파일은 `user/mypageMember.jsp`) |
| 주석 처리된 옛 뷰 이름 | `AdminLoginController.java:49`, `InspectInsertController.java:58` | `loginAdmin.jsp`, `inspectmanager.jsp`(대소문자 불일치)가 주석에 남아 있음 |
| 주석 처리된 액션 | `mybatis/Location.java:749-753` | `modifyform.action` 매핑·메서드가 `/* */`로 통째로 주석 처리됨. JSP에서도 링크하지 않아 미사용 |

### 7.3 컨트롤러가 반환하지만 존재하지 않는 JSP (5개)

| 처리 코드 | 반환 뷰 | 상태 |
| --- | --- | --- |
| `mybatis/FileTest.java: imageTest`·`downloadTest`·`uploadImageTest`·`uploadDownloadTest` | `FormImage.jsp`, `FormDownload.jsp`, `View.jsp`, `ViewDownload.jsp` | `WEB-INF/views/` 어디에도 없음 |
| `mybatis/BatisSample.java: sample` | `sample.jsp` | 없음 |

학습/테스트용 컨트롤러다(`/actions/imagetest.action`, `/actions/batissample.action` 등). 실사용 화면에는 영향이 없지만 호출하면 404다.

### 7.4 어떤 URL도 렌더하지 않는 JSP (9개) — dead view

| JSP | 제목 | 상태 |
| --- | --- | --- |
| `common/email.jsp` | email(ex).jsp | 메일 HTML 샘플. 코드 참조 없음 |
| `common/sendConfirmAccount.jsp` | 인증이메일 작성 | 참조 없음. 인증메일 HTML은 컨트롤러 문자열로 생성 |
| `host/deleteLocation.jsp` | 공간삭제 | 참조 없음(`deletelocation.action`은 `locationlist.action`으로 리다이렉트) |
| `host/modifyBusinessInfo.jsp` | 사업정보 수정 | 참조 없음. 대응 URL 매핑도 없음 |
| `host/modifyPackageInfo.jsp` | 패키지정보 수정 | `host/inputPackageInfoForm.jsp:31`이 `location.href="modifyPackageInfo.jsp"`로 직접 이동 — `WEB-INF` 경로라 브라우저에서 열리지 않는다 |
| `host/modifyPackageInfoForm.jsp` | 패키지정보 수정 | 참조 없음 |
| `host/mypageBankAccount(host).jsp` | mypageBankAccount(host).jsp | 자기 자신 외 참조 없음. 계좌 화면은 `common/bankAccount.jsp`로 통합됨 |
| `user/exchangeTabUser.jsp` | Lookation | `user/loadAndExchangeUser.jsp:165`가 `<c:import>`로 포함(뷰가 아닌 조각) |
| `user/loadTabUser.jsp` | Lookation | `user/loadAndExchangeUser.jsp:161`이 `<c:import>`로 포함(뷰가 아닌 조각) |

### 7.5 스캐폴딩·설정 갭

| 항목 | 내용 | 근거 |
| --- | --- | --- |
| 빈 `.action` 파일 56개 | `Lookation/WebContent/actions/*.action`이 전부 0바이트. DispatcherServlet이 `*.action`을 선점하므로 정적 파일로 서빙되지 않는 잔재 | `WebContent/actions/` |
| 매핑 없는 자리표시자 6개 | `adminform`, `basicmodifyform`, `hostmypage`, `membermypage`, `sample`, `sendsignupmail` — 파일만 있고 URL 매핑 없음 | `WebContent/actions/` vs §3 |
| 웹루트에 `dispatcher-servlet.xml` 없음 | `.gitignore:60`이 `WEB-INF/dispatcher-servlet.xml`을 제외해 저장소에 없다. 복사하지 않으면 기동 불가(추론) | `.gitignore:59-60` |
| root 진입점 없음 | welcome-file 6종이 모두 없어 `/Lookation/`은 404. 진입은 `/actions/loginform.action` 등 직접 URL | `web.xml:4-11` |
| 스캔 잔재 | `component-scan base-package="org.springframework.samples.petclinic.web"`가 남아 있음 | `dispatcher-servlet.xml` |
| 자격증명 하드코딩 | `localDataSource`/`remoteDataSource`에 Oracle 계정·비밀번호 평문, 원격 IP 노출 | `dispatcher-servlet.xml` |

### 7.6 동작 결함

| # | 결함 | 근거 |
| --- | --- | --- |
| 1 | `noSigned` 리다이렉트를 뒤따르는 `setViewName(jsp)`가 덮어써 로그인 가드가 무력화 | `DeleteFormController.java:79-84` |
| 2 | 탈퇴 불가 시 `mypage`로 보내겠다고 알리지만 마지막 `redirect:loginform`이 덮어씀 | `DeleteMemberController.java:71-117` |
| 3 | JSON을 `PrintWriter`로 먼저 쓰고 같은 응답을 JSP로 forward → 본문 중복 가능(추론) | `LocationDetailAjaxController.java:57-67` |
| 4 | 계좌가 3개 이상이면 뷰를 세팅하지 않고 종료 → 빈 뷰 이름 | `BankInfoAddController.java:69-96` |
| 5 | 뷰 이름에 붙인 `?identify=...`는 forward 파라미터로 전달되지 않음 | `BankInfoManageController.java:72`, `SignUpFormController.java:30` |

### 7.7 매핑은 있지만 어떤 JSP도 링크하지 않는 URL (11개)

직접 URL 입력이나 외부(메일 링크·테스트)로만 호출된다.

| URL | 처리 코드 | 비고 |
| --- | --- | --- |
| `/actions/` | `blackListEmail.java: sendPasswordEmail` | — |
| `/actions/adminloginform.action` | `controller/AdminLoginFormController.handleRequest` | — |
| `/actions/batissample.action` | `BatisSample.java: sample` | 테스트/샘플 컨트롤러 |
| `/actions/blindcontent.action` | `BlindContent.java: blind` | — |
| `/actions/changepasswordnologinform.action` | `Account.java: changePasswordNoLoginForm` | — |
| `/actions/downloadtest.action` | `FileTest.java: downloadTest` | 테스트/샘플 컨트롤러 |
| `/actions/imagetest.action` | `FileTest.java: imageTest` | 테스트/샘플 컨트롤러 |
| `/actions/insertinfotest.action` | `Location.java: insertinfotest` | — |
| `/actions/verifyemail.action` | `mybatis/VerifyEmail.verify` | 인증 메일 링크 |
| `/actions/uploaddownloadtest.action` | `FileTest.java: uploadDownloadTest` | 테스트/샘플 컨트롤러 |
| `/actions/uploadimagetest.action` | `FileTest.java: uploadImageTest` | 테스트/샘플 컨트롤러 |

## 8. JSP 인벤토리 (117개)

`렌더하는 URL`이 `—`면 §7.4의 dead view다. `— (c:import 조각)`은 컨트롤러 뷰가 아니라 다른 JSP가 포함하는 조각이다.

| JSP | 제목 | 렌더하는 URL |
| --- | --- | --- |
| `admin/addBlind.jsp` | 블랙리스트 등록 | `/actions/blindform.action` |
| `admin/blacklistPopupHost.jsp` | 블랙리스트 등록 | `/actions/setblackhostform.action` |
| `admin/blacklistPopupUser.jsp` | 블랙리스트 등록 | `/actions/setblackmemform.action` |
| `admin/blindManager.jsp` | 블라인드 목록 | `/actions/blindmanager.action` |
| `admin/bookReportList.jsp` | 신고목록 | `/actions/bookreportlist.action` |
| `admin/bookReportPopup0.jsp` | 신고상세 | `/actions/bookpopup0.action` |
| `admin/bookReportPopup1.jsp` | 신고상세 | `/actions/bookpopup1.action` |
| `admin/helpManager.jsp` | 도움말 목록 | `/actions/helpmanager.action` |
| `admin/hostAccountManager.jsp` | 계정관리 | `/actions/hostblackmanager.action` |
| `admin/hostBlackListPopup.jsp` | 블랙리스트 추가 | `/actions/hostblackform.action` |
| `admin/hostQnaManager.jsp` | Q&A조회 | `/actions/hostqnamanager.action` |
| `admin/hostQnaPopup.jsp` | 답글상세 | `/actions/hostqnapopup.action` |
| `admin/hostReviewManager.jsp` | 리뷰조회 | `/actions/hostreviewmanager.action` |
| `admin/hostReviewPopup.jsp` | 리뷰상세보기 | `/actions/hostreviewpopup.action` |
| `admin/inputHelpInfo.jsp` | 도움말 글쓰기 | `/actions/helpinsertform.action` |
| `admin/inputNoticeInfo.jsp` | 공지사항 글쓰기 | `/actions/noticeinsertform.action` |
| `admin/inspectManager.jsp` | 공간검수 목록 | `/actions/inspectmanager.action` |
| `admin/inspectProcess.jsp` | 검수처리 | `/actions/inspectprocess.action` |
| `admin/loadManager.jsp` | 충전신청 관리 | `/actions/loadmgr.action` |
| `admin/locReportPopup0.jsp` | 신고상세 | `/actions/locpopup0.action` |
| `admin/locReportPopup1.jsp` | 신고상세 | `/actions/locpopup1.action` |
| `admin/loginFormAdmin.jsp` | 관리자 로그인 | `/actions/adminloginform.action` |
| `admin/mainAdmin.jsp` | admin | `/actions/adminmain.action` |
| `admin/modifyHelpInfo.jsp` | 도움말 수정 | `/actions/helpupdateform.action` |
| `admin/modifyNoticeInfo.jsp` | 공지사항 수정 | `/actions/noticeupdateform.action` |
| `admin/noticeManager.jsp` | 공지사항 목록 | `/actions/noticemanager.action` |
| `admin/reportDetailPopupHost.jsp` | 신고상세 | `/actions/reportdetails.action` |
| `admin/reportDetailPopupUser.jsp` | 신고상세 | `/actions/reportmemdetails.action` |
| `admin/reportList.jsp` | 신고관리 | `/actions/reportlist.action` |
| `admin/userAccountManager.jsp` | 계정관리 | `/actions/memberblackmanager.action` |
| `admin/userBlackListPopup.jsp` | 블랙리스트 추가 | `/actions/userblackform.action` |
| `admin/userQnaManager.jsp` | Q&A관리 | `/actions/userqnamanager.action` |
| `admin/userQnaPopup.jsp` | Q&A상세 | `/actions/userqnapopup.action` |
| `admin/userReviewManager.jsp` | 리뷰관리 | `/actions/userreviewmanager.action` |
| `admin/userReviewPopup.jsp` | 리뷰상세 | `/actions/userreviewpopup.action` |
| `ajax/AccountAjax.jsp` |  | `/actions/accountajax.action`<br>`/actions/ajaxfindemail.action`<br>`/actions/ajaxmodifytel.action` |
| `ajax/LocationDetailAjax.jsp` |  | `/actions/locdetailajax.action` |
| `common/bankAccount.jsp` | 계좌관리 | `/actions/bankinfomanage.action` |
| `common/bankAccountAddPopup.jsp` | 계좌추가 | `/actions/bankinfoaddpopup.action` |
| `common/bankAccountTerms.jsp` | Insert title here | `/actions/terms.action` |
| `common/blindContent.jsp` | 블라인드 알림 | `/actions/blindcontent.action` |
| `common/changePassword.jsp` | 비밀번호 변경 | `/actions/changepasswordform.action` |
| `common/changePasswordNoLogin.jsp` | 비밀번호 변경 | `/actions/changepasswordnologinform.action` |
| `common/confirmPassword.jsp` | 비밀번호 확인 | `/actions/confirmpasswordform.action` |
| `common/createAccountFailNotice.jsp` | 회원가입 실패 | `/actions/sendsignupemail.action` |
| `common/createAccountNotice.jsp` | 회원가입 성공 | `/actions/sendsignupemail.action` |
| `common/deleteAccount.jsp` | 탈퇴완료 | `/actions/deleteform.action` |
| `common/email.jsp` | email(ex).jsp | — |
| `common/exchangeNotice.jsp` | 환전신청 완료 | `/actions/exchange.action` |
| `common/findPassword.jsp` | 비밀번호 찾기 | `/actions/findpasswordform.action` |
| `common/help.jsp` | 도움말 | `/actions/help.action` |
| `common/helpDetail.jsp` | 도움말 | `/actions/helpdetail.action` |
| `common/locationManagementPolicy.jsp` | 공간관리정책 | `/actions/locmanagement.action` |
| `common/login.jsp` | 로그인 | `/actions/loginform.action`<br>`/actions/mileagehistory.action` |
| `common/modifyQnaPopup.jsp` | Q&A수정 | `/actions/modifyformqna.action` |
| `common/modifyReviewPopup.jsp` | 리뷰수정 | `/actions/modifyformreview.action` |
| `common/notice.jsp` | 공지사항 | `/actions/notice.action` |
| `common/noticeDetail.jsp` | 공지사항 | `/actions/noticedetail.action` |
| `common/privacyPolicy.jsp` | 개인정보처리방침 | `/actions/privacy.action` |
| `common/profile.jsp` | 프로필 | `/actions/profile.action` |
| `common/refundPolicy.jsp` | 환불정책 | `/actions/refund.action` |
| `common/sendConfirmAccount.jsp` | 인증이메일 작성 | — |
| `common/sendFindPassword.jsp` | 인증이메일 발송 성공 | `/actions/`<br>`/actions/sendconfirmemail.action` |
| `common/signUp.jsp` | 회원가입 | `/actions/signupform.action` |
| `common/termsOfServicePolicy.jsp` | 이용약관 | `/actions/termsofservice.action` |
| `common/timeout.jsp` | Lookation | `/actions/bankinfoadd.action` |
| `common/writeQnaPopup.jsp` | Q&A작성 | `/actions/writeqna.action` |
| `common/writeReviewPopup.jsp` | 리뷰작성 | `/actions/writereview.action` |
| `common/wrongAccess.jsp` | 잘못된 접근 | `/actions/`<br>`/actions/basicajax.action`<br>`/actions/changepassword.action`<br>`/actions/changepasswordnologin.action` 외 19 |
| `host/bookCancel.jsp` | 예약취소 | `/actions/bookcancelhost.action` |
| `host/bookList.jsp` | 예약리스트 확인 | `/actions/booklisthost.action` |
| `host/deleteLocation.jsp` | 공간삭제 | — |
| `host/exchangeHost.jsp` | 마일리지 관리 | `/actions/loadandexchange.action` |
| `host/hostMessenger.jsp` | 메신저 | `/actions/hmessenger.action` |
| `host/inputBasicInfo.jsp` | 기본정보 추가 | `/actions/basicform.action` |
| `host/inputBusinessInfo.jsp` | 사업정보 추가 | `/actions/inputcontactinfo.action` |
| `host/inputContactInfo.jsp` | 연락처 추가 | `/actions/inputbasicinfo.action` |
| `host/inputDetailInfo.jsp` | 상세정보 추가 | `/actions/inputbusinessinfo.action` |
| `host/inputPackageInfo.jsp` | 패키지정보 추가 | `/actions/packageform.action` |
| `host/inputPackageInfoForm.jsp` | 패키지정보 추가 | `/actions/packagemanager.action` |
| `host/inputUsingInfo.jsp` | 이용정보 추가 | `/actions/inputdetailinfo.action` |
| `host/locationDetailHost.jsp` | 공간상세 | `/actions/locationdetailhost.action` |
| `host/locationList.jsp` | 공간관리 | `/actions/insertinfotest.action`<br>`/actions/locationlist.action` |
| `host/mainHost.jsp` | Lookation | `/actions/hostmain.action` |
| `host/mileageHistory.jsp` | 마일리지 내역 | `/actions/mileagehistory.action` |
| `host/modifyBasicInfo.jsp` | 기본정보 수정 | `/actions/modifybasicform.action` |
| `host/modifyBusinessInfo.jsp` | 사업정보 수정 | — |
| `host/modifyContactInfo.jsp` | 연락처 수정 | `/actions/modifybasicinfo.action` |
| `host/modifyDetailInfo.jsp` | 상세정보 수정 | `/actions/modifycontactinfo.action` |
| `host/modifyPackageInfo.jsp` | 패키지정보 수정 | — |
| `host/modifyPackageInfoForm.jsp` | 패키지정보 수정 | — |
| `host/modifyUsingInfo.jsp` | 이용정보 수정 | `/actions/modifydetailinfo.action` |
| `host/mypageBankAccount(host).jsp` | mypageBankAccount(host).jsp | — |
| `host/mypageHost.jsp` | 마이페이지 | `/actions/mypage.action` |
| `host/packageApply.jsp` | 예약등록 | `/actions/packageapplyform.action` |
| `host/reportHost.jsp` | 신고 | `/actions/reporthostform.action` |
| `host/reportHostImg.jsp` | 신고 | `/actions/reporthost.action` |
| `user/bookApply.jsp` | 예약신청 | `/actions/bookapply.action` |
| `user/bookApplyNotice.jsp` | 예약신청 완료 | `/actions/bookpay.action` |
| `user/bookCancel.jsp` | 예약취소 | `/actions/bookcancel.action` |
| `user/bookDetails.jsp` | 예약상세 | `/actions/bookdetails.action`<br>`/actions/bookdetailshost.action` |
| `user/bookList.jsp` | 예약리스트 확인 | `/actions/booklist.action` |
| `user/exchangeTabUser.jsp` | Lookation | — (c:import 조각) |
| `user/loadAndExchangeUser.jsp` | 마일리지 충전, 환전 | `/actions/loadandexchange.action` |
| `user/loadRegisterNotice.jsp` | 충전신청 완료 | `/actions/memberload.action` |
| `user/loadTabUser.jsp` | Lookation | — (c:import 조각) |
| `user/locationDetail.jsp` | 공간 상세 | `/actions/locationdetail.action` |
| `user/mainUser.jsp` | Lookation | `/actions/membermain.action` |
| `user/mileageHistory.jsp` | 마일리지 내역 | `/actions/mileagehistory.action` |
| `user/mypageMember.jsp` | 마이페이지 | `/actions/bookpay.action`<br>`/actions/mypage.action` |
| `user/reportUser.jsp` | 신고 | `/actions/reportuserform.action` |
| `user/reportUserImg.jsp` | 신고 | `/actions/reportuser.action` |
| `user/search.jsp` | 검색결과 | `/actions/search.action`<br>`/actions/searchkeyword.action` |
| `user/userMessenger.jsp` | 메신저 | `/actions/mmessenger.action` |
| `user/userQnaList.jsp` | Q&A 목록 | `/actions/userqnalist.action` |
| `user/userReviewList.jsp` | 리뷰 목록 | `/actions/userreviewlist.action` |
| `user/wrongLocation.jsp` | 잘못된 접근 | `/actions/locationdetail.action` |

---

합계: 엔드포인트 180개 매핑(애노테이션 160 + XML 빈 20, 고유 URL 179개), JSP 117개, 공통 include 13개, 매핑 없는 참조 URL 4개, dead view 9개, 링크 없는 매핑 11개.
