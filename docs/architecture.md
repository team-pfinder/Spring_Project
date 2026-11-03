# Lookation 아키텍처

> AI 에이전트가 이 저장소를 처음 열었을 때 **재탐색 없이** 올바른 위치를 고치기 위한 지도.
> 모든 경로는 저장소 루트(`/Users/npnl/Documents/work/Spring_Project`) 기준이며, `파일:줄` 형식으로 근거를 남긴다.
> 애플리케이션 루트는 `Lookation/` (Eclipse Dynamic Web Project). 웹 컨텍스트는 배포 방식에 따라 `Lookation` 또는 `/`(ROOT).

---

## 0. 30초 요약

1. **라우팅 체계가 두 개 공존한다.**
   - ① XML 방식: `dispatcher-servlet.xml`의 `<bean name="/actions/xxx.action" class="com.lookation.controller.XxxController">` + `implements org.springframework.web.servlet.mvc.Controller` (22개 클래스).
   - ② 어노테이션 방식: `@Controller` + `@RequestMapping` (37개 클래스, 패키지 이름은 `com.lookation.mybatis` — **이름과 달리 컨트롤러 패키지다**).
   - 총 **180개 매핑**(활성 어노테이션 160 + XML 빈 20), **고유 URL 경로 179개**. `/actions/profile.action` 1개가 양쪽에 겹치며, XML 쪽 빈은 존재하지 않는 클래스를 참조해 실제 핸들러가 없다(§6-1, `docs/screens.md` §7.2). 주석 처리된 `modifyform.action`은 활성 매핑에서 제외했다.
2. **데이터 접근도 두 체계다.** MyBatis 매퍼 인터페이스(`com.lookation.dao.I*DAO` + `src/com/lookation/mybatis/mapper/*.xml`, 34개)와 손으로 쓴 JDBC DAO 8개(같은 `dao` 패키지의 클래스)가 공존한다. 서로 다른 인터페이스를 구현하므로 충돌은 없지만, 같은 테이블을 두 경로로 만진다.
3. **`dispatcher-servlet.xml`은 저장소에 없다.** `.gitignore:60`이 의도적으로 제외한다. 기동하려면 `중요파일/02. dispatcher-servlet/dispatcher-servlet.xml`을 `Lookation/WebContent/WEB-INF/`로 복사해야 한다. 단, 이 사본은 배포본(`중요파일/00. export/호스팅용/ROOT.war` 내부)과 내용이 다르고 **존재하지 않는 클래스를 참조**한다(§6-1).
4. **빌드 도구가 없다.** `pom.xml`/`build.gradle` 없음. Eclipse + `WEB-INF/lib` 수동 jar + WAR export가 빌드다.
5. **인증/인가는 필터도 인터셉터도 아니다.** 유일한 필터는 인코딩 필터(`web.xml:31-46`)이고, 로그인 검사는 각 액션 메서드 첫머리에 복붙된 세션 확인 코드다.

### 코드 수정 전 5가지 체크

| # | 확인 | 이유 |
|---|---|---|
| 1 | 새 액션을 추가할 때 **어느 라우팅 체계**에 넣을지 먼저 정한다 | XML에 `<bean>`을 추가하지 않으면 `implements Controller` 클래스는 404다(§3.2) |
| 2 | 뷰 반환 문자열은 `../WEB-INF/views/<역할>/<파일>.jsp` 형식 | ViewResolver가 기본값이라 prefix/suffix가 없고, 현재 요청 URI 기준 상대경로로 해석된다(§3.5) |
| 3 | 세션 키는 `"memberCode"`, `"hostCode"`, `"admin_id"` | `memberCode`/`hostCode`는 `identify + "Code"`로 **동적 생성**된다(`mybatis/Account.java:109`) |
| 4 | SQL을 고칠 때 **MyBatis XML과 JDBC DAO 중 어느 쪽**인지 확인 | 같은 테이블에 두 벌의 SQL이 존재한다(§6-5) |
| 5 | 테스트는 없다 | 저장소 전체에 JUnit 테스트 0개. 검증은 수동 실행뿐 |

---

## 1. 스택·런타임

| 항목 | 값 | 근거 |
|---|---|---|
| 언어/타깃 | **Java 8 (JDK 1.8)** | `README.md` "JDK 1.8"; 배포 클래스 파일 major version `0x34`(52 = Java 8) — `중요파일/00. export/호스팅용/ROOT.war` 내 `WEB-INF/classes/.../AdminLoginController.class` |
| 웹 프레임워크 | **Spring MVC 3.0.2.RELEASE** | `Lookation/WebContent/WEB-INF/lib/org.springframework.web.servlet-3.0.2.RELEASE.jar` |
| 설정 방식 | XML 중심. `applicationContext.xml`은 **빈 파일**(루트 컨텍스트에 빈 없음), 모든 빈은 `dispatcher-servlet.xml`에 | `Lookation/WebContent/WEB-INF/applicationContext.xml:1-11` |
| ORM/SQL 매퍼 | **MyBatis 3.5.1** + **mybatis-spring 1.3.2** | `WEB-INF/lib/mybatis-3.5.1.jar`, `mybatis-spring-1.3.2.jar` |
| DB | **Oracle** (JDBC thin, SID `xe`) | `dispatcher-servlet.xml` 의 `jdbc:oracle:thin:@localhost:1521:xe`, `@34.64.215.203:1521:xe` |
| JDBC 드라이버 | **ojdbc6** (11g용) | `WEB-INF/lib/ojdbc6.jar`; `중요파일/01. sql/lookation_sys_init.sql` 주석의 `11.2.0` 경로 |
| WAS | **Tomcat** (Servlet 3.1 필요 → Tomcat 8.0+) | `Lookation/WebContent/WEB-INF/web.xml:3` `version="3.1"`; `README.md` "WAS : Apache Tomcat" |
| 뷰 | **JSP + JSTL**(`c`, `fmt`, `fn`) + EL. 템플릿 엔진 없음 | `views/**` 117개 JSP에서 `taglib prefix="c"` 117회 |
| 프론트 | jQuery 3.2.1, Bootstrap 4 계열, Owl Carousel, Magnific Popup, CKEditor 4, SB Admin 2 | `Lookation/WebContent/js/`, `css/`, `ckeditor/`, `vendor/` |
| 파일 업로드 | **cos.jar** (`com.oreilly.servlet.MultipartRequest`) | `src/com/lookation/util/FileManager.java:20` |
| 메일 | **javax.mail 1.5.6** + Spring `JavaMailSenderImpl`(spring-context-support) | `WEB-INF/lib/javax.mail-1.5.6.jar`, `spring-context-support-3.0.2.RELEASE.jar` |
| 빌드 | **없음** (Eclipse 수동 빌드) | `pom.xml`/`build.gradle` 부재; `.gitignore:53` "무빌드 서블릿 프로젝트" |

### 의존 JAR 전체 목록 (`Lookation/WebContent/WEB-INF/lib`, 26개)

| 용도 | 파일 |
|---|---|
| Spring 3.0.2 | `org.springframework.aop-3.0.2.RELEASE.jar`, `org.springframework.asm-…`, `org.springframework.beans-…`, `org.springframework.context-…`, `org.springframework.core-…`, `org.springframework.expression-…`, `org.springframework.jdbc-…`, `org.springframework.transaction-…`, `org.springframework.web-…`, `org.springframework.web.servlet-…`, `spring-context-support-3.0.2.RELEASE.jar` |
| MyBatis | `mybatis-3.5.1.jar`, `mybatis-spring-1.3.2.jar` |
| Oracle | `ojdbc6.jar` |
| 메일 | `javax.mail-1.5.6.jar` |
| 업로드 | `cos.jar` (파일명에 버전 없음, 2008년 빌드) |
| JSTL | `jstl.jar`, `standard.jar` (파일명에 버전 없음) |
| JSON(사실상 미사용) | `json-lib-2.4-jdk15.jar`, `ezmorph-1.0.6.jar`, `commons-beanutils-1.9.4.jar`, `commons-collections-3.2.2.jar`, `commons-lang-2.6.jar` |
| 로깅 | `commons-logging-1.2.jar`, `com.springsource.org.apache.commons.logging-1.1.1.jar` |

> 서블릿 API jar는 없다(Tomcat이 제공). `spring-tx` jar는 있으나 트랜잭션 설정은 없다(§4.7).
> `json-lib` 계열은 Java 코드에서 한 번도 import되지 않는다(JSON은 문자열 조립 — §4.3).

---

## 2. 디렉터리 지도

```
Spring_Project/
├── Lookation/                        ← 애플리케이션 루트 (Eclipse 프로젝트)
│   ├── src/com/lookation/            ← Java 소스 148개
│   │   ├── controller/   (22)        ← XML 방식 컨트롤러 (implements Controller)
│   │   ├── dao/          (49)        ← I*DAO 인터페이스 41 + JDBC DAO 클래스 8
│   │   ├── dto/          (38)        ← POJO (snake_case 필드 + getXxx/setXxx)
│   │   ├── mybatis/      (37)        ← @Controller 클래스 37 (패키지명과 무관)
│   │   │   └── mapper/   (34 XML)    ← MyBatis 매퍼 XML
│   │   └── util/         (2)         ← FileManager, EmailManager
│   └── WebContent/
│       ├── WEB-INF/
│       │   ├── web.xml               ← DispatcherServlet(*.action) + 인코딩 필터
│       │   ├── applicationContext.xml← 빈 파일
│       │   ├── lib/                  ← jar 26개
│       │   └── views/                ← JSP 117개 (admin/host/user/common/ajax)
│       ├── includes/                 ← 헤더/푸터/사이드바 조각 14개
│       ├── actions/                  ← 빈 *.action 파일 56개 (레거시 껍데기, §6-12)
│       ├── css/ js/ images/ img/ fonts/ vendor/ scss/ ckeditor/
│       └── lib/calendar/             ← 캘린더 위젯(정적 리소스)
├── 중요파일/
│   ├── 00. export/소스용/Lookation.war
│   ├── 00. export/호스팅용/ROOT.war   ← 실제 배포본(.class 포함)
│   ├── 01. sql/                      ← DDL/뷰/프로시저/더미데이터 + 실행순서
│   └── 02. dispatcher-servlet/dispatcher-servlet.xml  ← 설정 원본(복사해서 사용)
├── 최종문서/                          ← 기획/설계 PDF
├── 공용공간/, 팀원공간/               ← 산출물
└── docs/                             ← 본 문서
```

### 2.1 명명 규칙

| 대상 | 규칙 | 예 |
|---|---|---|
| XML 방식 컨트롤러 | `XxxController`, `com.lookation.controller`, `implements Controller`, `handleRequest()`가 `ModelAndView` 반환 | `AdminLoginController` |
| 어노테이션 컨트롤러 | 도메인 명사 클래스명(`Location`, `BookList`), `com.lookation.mybatis`, 메서드가 `String` 뷰 이름 반환 | `mybatis/Location.java:24` |
| 매퍼 인터페이스 | `IXxxDAO` (예외: `IMainLocationInfo`, 소문자 시작 `IhostQnaManagerDAO`, `IuserQnaManagerDAO`) | `dao/ILocationDAO.java` |
| 매퍼 XML | `src/com/lookation/mybatis/mapper/XxxDAO.xml`, `namespace="com.lookation.dao.IXxxDAO"` | `LocationDAO.xml:5` |
| **XML 파일명 ≠ 인터페이스명인 경우** | `HostMileageHistoryDAO.xml`→`IHostHistoryListDAO`, `MemberMileageHistoryDAO.xml`→`IMemberHistoryListDAO`, `reportListDAO.xml`→`IReportDAO`, `blindManagerDAO.xml`→`IBlindManagerDAO` | 파일명으로 인터페이스를 추정하면 틀린다 |
| JDBC DAO | `XxxDAO` 클래스 + `setDataSource(DataSource)` 수동 주입, `IAccountDAO`류의 소형 인터페이스 구현 | `dao/MemberAccountDAO.java:13` |
| DTO | `XxxDTO`, 필드는 DB 컬럼과 같은 snake_case(`loc_code`), 접근자는 `getLoc_code()` | `dto/LocationDTO.java:82-87` |
| 뷰 | 역할 폴더 `views/{admin,host,user,common,ajax}` + camelCase 파일명 | `views/host/locationList.jsp` |

### 2.2 뷰 폴더 = 역할

| 폴더 | 역할 | JSP 수(대표) |
|---|---|---|
| `views/user/` | 이용자(member) 화면 | `mainUser.jsp`, `search.jsp`, `bookApply.jsp`, `mypageMember.jsp` |
| `views/host/` | 호스트 화면 | `mainHost.jsp`, `locationList.jsp`, `inputBasicInfo.jsp`, `mypageHost.jsp` |
| `views/admin/` | 관리자 화면 | `loginFormAdmin.jsp`, `mainAdmin.jsp`, `inspectManager.jsp`, `*Manager.jsp` |
| `views/common/` | 공통/정책/계정 | `login.jsp`, `signUp.jsp`, `profile.jsp`, `notice.jsp`, `termsOfServicePolicy.jsp` |
| `views/ajax/` | AJAX 응답용 JSP 조각 | `AccountAjax.jsp`, `LocationDetailAjax.jsp` |
| `WebContent/includes/` | `c:import`로 삽입되는 헤더/푸터/사이드바 | `header_user.jsp`, `header_host.jsp`, `includes_admin.jsp`, `mypage_Sidebar(user).jsp` |

JSP 레이아웃 관례: 각 JSP가 `<c:import url="${cp}/includes/...">`로 조각을 직접 삽입한다(`views/user/mainUser.jsp:13-14`, `cp = request.getContextPath()`). 공통 레이아웃 엔진(Tiles/sitemesh)은 없다.

---

## 3. 요청 흐름

### 3.1 기동 순서

1. Tomcat이 `web.xml`을 읽는다 → `ContextLoaderListener`가 루트 컨텍스트(`applicationContext.xml`, **빈 파일**)를 만든다 (`web.xml:13-15`).
2. `DispatcherServlet`(servlet-name `dispatcher`, `load-on-startup=1`)이 `WEB-INF/dispatcher-servlet.xml`을 로드한다 (`web.xml:18-21`). 설정 파일이 없으면 DispatcherServlet이 정상 초기화되지 않아 `*.action` 요청을 처리할 수 없다(§6-1).
3. `dispatcher-servlet.xml`이 `SimpleDriverDataSource` 2개(local/remote) → `SqlSessionFactoryBean` → `SqlSessionTemplate`(`id="sqlSession"`) → 컨트롤러/DAO 빈을 등록한다 (`dispatcher-servlet.xml:28-67`).
4. `<context:component-scan base-package="com.lookation.mybatis"/>`가 `@Controller` 37개를 등록한다 (`dispatcher-servlet.xml:14-15`). `org.springframework.samples.petclinic.web` 스캔은 샘플 잔재로 아무 것도 찾지 못한다 (`:10-11`).

### 3.2 두 라우팅 체계

`ViewResolver`·`HandlerMapping` 빈을 하나도 정의하지 않았으므로 Spring 3.0.2의 기본 전략이 쓰인다. 기본값은 jar 내부 `org/springframework/web/servlet/DispatcherServlet.properties`에 있다.

- `HandlerMapping` = `BeanNameUrlHandlerMapping` + `DefaultAnnotationHandlerMapping`
- `ViewResolver` = `InternalResourceViewResolver` (prefix/suffix **빈 문자열**)
- `HandlerAdapter` = `HttpRequestHandlerAdapter`, `SimpleControllerHandlerAdapter`, `AnnotationMethodHandlerAdapter`

| 체계 | 등록 위치 | 클래스 | 반환 | 등록 개수 |
|---|---|---|---|---|
| XML | `dispatcher-servlet.xml`의 `<bean name="/actions/…action">` | `com.lookation.controller.*` (`implements Controller`) | `ModelAndView` | 20 |
| 어노테이션 | `@Controller` + `@RequestMapping(value="/actions/…action")` | `com.lookation.mybatis.*` | `String`(뷰 이름) 또는 `Model`+`String` | 160 |

**함정**: `com.lookation.controller` 패키지에는 컴포넌트 스캔이 걸려 있지 않다. 따라서 XML에 `<bean>`을 추가하지 않은 `implements Controller` 클래스는 절대 매핑되지 않는다(실제로 3개가 미등록 — §6-10).

### 3.3 시퀀스 다이어그램

```
[Browser]
   │  GET /Lookation/actions/booklist.action?identify=member
   ▼
┌──────────────────────────────────────────────────────────────────────────┐
│ Tomcat                                                                   │
│  CharacterEncodingFilter (UTF-8, url-pattern=/*)   web.xml:31-46         │
│        │                                                                 │
│        ▼                                                                 │
│  DispatcherServlet (servlet-mapping *.action)      web.xml:25-26         │
│        │                                                                 │
│        ├─ HandlerMapping ──▶ BeanNameUrlHandlerMapping                   │
│        │      · bean name "/actions/booklist.action" 이 있으면 매칭       │
│        │      · 없으면 DefaultAnnotationHandlerMapping → @RequestMapping  │
│        │                                                                 │
│        ▼                                                                 │
│  Controller                                                              │
│   ① XML 방식이면 com.lookation.controller.XxxController#handleRequest()  │
│        └ ModelAndView + 주입된 DAO (setter injection)                    │
│   ② 어노테이션 방식이면 com.lookation.mybatis.BookList#bookList(...)     │
│        ├ HttpSession 검사 (memberCode/hostCode/admin_id)  ← 인증은 여기  │
│        └ sqlSession.getMapper(IBookListDAO.class)                        │
│                                                                          │
│        ┌──────────────────────────┴───────────────────────────┐          │
│        ▼ (JDBC 경로)                                          ▼ (MyBatis)│
│  dao.XxxDAO (DataSource.getConnection)              SqlSessionTemplate    │
│   PreparedStatement/CallableStatement                       │             │
│        │                                            mapper XML            │
│        │                                    mybatis/mapper/XxxDAO.xml     │
│        │                                     (namespace = I*DAO)          │
│        └──────────────────┬───────────────────────────────┘              │
│                           ▼                                              │
│                   Oracle (LOOKATION 스키마)                              │
│                   테이블 76 / 뷰 66 / 프로시저 12 / F_CODE 함수          │
│                           │                                              │
│                           ▼                                              │
│  ViewResolver = InternalResourceViewResolver (prefix="", suffix="")      │
│    · "redirect:xxx.action"  → RedirectView (현재 URL 기준 상대)          │
│    · "redirect:/actions/…"  → 컨텍스트 기준 절대                         │
│    · "../WEB-INF/views/user/bookList.jsp?x=y" → RequestDispatcher forward│
└──────────────────────────────────────────────────────────────────────────┘
                           │
                           ▼
                     JSP 렌더링 (JSTL/EL) → HTML
```

### 3.4 세션·인증 (필터/인터셉터 없음)

- **필터는 인코딩 하나뿐**이다. `web.xml`에는 보안 필터도, `<error-page>`도, `<security-constraint>`도 없다. `dispatcher-servlet.xml`에도 `HandlerInterceptor` 빈이 없다.
- 로그인 성공 시 세션 키를 **동적으로** 만든다: `session.setAttribute(identify + "Code", accountCode)` — `mybatis/Account.java:109`. 즉 `identify=member` → `memberCode`, `identify=host` → `hostCode`.
- 관리자는 `admin_id` (`controller/AdminLoginController.java:61`, 해제는 `AdminLogoutController.java:25`).
- 세션 키 사용 빈도(정적 grep): `admin_id` 54회, `hostCode` 31회, `memberCode` 17회, `TempLocation` 7회, `beforeUrl`/`requestUrl`/`identify` 각 1~3회.
- 각 액션의 표준 패턴:

```java
String accountCode = (String) session.getAttribute("hostCode");
if (accountCode == null) return "redirect:loginform.action?identify=host";
```

- 로그인 후 복귀용으로 `beforeUrl`(referer 마지막 세그먼트)과 `requestUrl`을 세션에 저장한다 (`mybatis/Account.java:33-56`).
- 세션 만료 안내는 뷰로만 존재한다(`views/common/timeout.jsp`, `views/common/wrongAccess.jsp`). 서버 측 강제 리다이렉트는 없다.

### 3.5 뷰 해석 규칙 (에이전트가 가장 자주 틀리는 부분)

1. `ViewResolver` 빈이 없으므로 `InternalResourceViewResolver`가 기본값으로 쓰이고 prefix/suffix가 **빈 문자열**이다.
2. 따라서 뷰 이름은 **현재 요청 URI(`/…/actions/…`) 기준 상대경로**여야 한다. 그래서 모두 `"../WEB-INF/views/…"`로 시작한다.
3. 쿼리스트링을 뷰 이름에 붙이는 관례가 있다: `return "../WEB-INF/views/common/login.jsp?identify=" + identify;` (`mybatis/Account.java:59`). 뷰 이름에 `?`를 붙이는 방식이므로 리팩터링 시 주의.
4. `redirect:`는 두 가지 형태가 혼용된다 — 상대(`redirect:loginform.action`)와 절대(`redirect:/actions/bankinfomanage.action?identify=…`, `controller/BankInfoRemoveController.java:69`).
5. `WEB-INF` 아래에 있으므로 JSP 직접 접근은 차단된다. 모든 화면은 컨트롤러를 거친다.

### 3.6 요청 파라미터 규약

- `identify`(`member` | `host` | `admin`)가 역할 구분자다. 로그인/마이페이지/공통 화면이 이 값을 받아 분기한다 (`mybatis/MypageMain.java:25-64`).
- 폼 필드는 camelCase 또는 snake_case가 섞여 있다(`inputLocName`, `loc_code`, `selectDate`). DTO 세터 이름과 맞춰야 한다.

---

## 4. 공통 규약

### 4.1 URL 규약

**역할별 URL prefix는 없다.** 모든 엔드포인트가 `/actions/*.action` 단일 네임스페이스에 있고, 역할은 `identify` 파라미터 + 세션 키 + JSP 폴더로 구분된다. (요구사항에 있던 `/user`, `/host`, `/admin` prefix는 존재하지 않는다.)

- 확장자 `.action`이 DispatcherServlet 매핑이다 (`web.xml:26`).
- 역할별 진입점: `/actions/membermain.action`, `/actions/hostmain.action`, `/actions/adminloginform.action` (`최종문서/13.관련 URL.txt`).
- 루트(`/`)에는 `index.jsp`가 없다. `web.xml:4-11`의 welcome-file은 모두 존재하지 않는 파일을 가리키므로 `/` 요청은 404다.

### 4.2 반환 규약

| 목적 | 반환 | 예 |
|---|---|---|
| 화면 | `"../WEB-INF/views/<역할>/<파일>.jsp"` (필요 시 `?파라미터`) | `mybatis/MypageMain.java:55` |
| 처리 후 이동 | `"redirect:<상대>.action"` 또는 `"redirect:/actions/<절대>.action"` | `mybatis/Location.java:60` |
| AJAX(JSON) | `response.setContentType("text/html;charset=utf-8")` + `PrintWriter`로 **손수 조립한 JSON 문자열** | `controller/LocationDetailAjaxController.java:45-61` |
| AJAX(HTML 조각) | `views/ajax/*.jsp` 뷰 반환 | `controller/AccountAjaxController.java` |

XML 방식 컨트롤러는 `ModelAndView.setViewName(...)`으로 같은 문자열을 쓴다(`controller/BankInfoManageController.java:72`).

### 4.3 AJAX/JSON

- `json-lib` jar가 있으나 코드에서 쓰지 않는다. JSON은 문자열 연결로 만든다 — `controller/LocationDetailAjaxController.java:50-57`. 값에 `"`가 들어가면 JSON이 깨지고, 이스케이프가 없어 XSS 위험이 있다.
- 응답 Content-Type이 `text/html`이므로 클라이언트가 `JSON.parse(data)`를 직접 호출한다 — `WebContent/WEB-INF/views/user/locationDetail.jsp:478`.

### 4.4 파일 업로드/다운로드 — `src/com/lookation/util/FileManager.java`

| 기능 | 메서드 | 동작 |
|---|---|---|
| 업로드 | `upload(request, saveFolderName)` (`:22-35`) | `ServletContext.getRealPath(saveFolderName)`에 저장. 최대 100MB(`:27`), UTF-8, `DefaultFileRenamePolicy` |
| 파일명 추출 | `getFileNames(multi)` (`:37-52`) | `MultipartRequest`의 파일명 목록 |
| 다운로드 | `doFileDownload(saveFileName, originalFileName, path, response)` (`:55-105`) | 4KB 스트림 복사. 파일명을 `EUC-KR→8859_1`로 재인코딩(`:65`), `Content-disposition` 직접 설정(`:80`) |
| 삭제 | `doFileDelete(request, folderName, fileName)` (`:108-`) | `getRealPath` 기준 파일 삭제 |

호출 측은 `FileManager.upload(request, "images")` 형태로 `images`/`downloads` 폴더에 저장한다(`mybatis/Location.java:238`, `mybatis/HelpManager.java:92`, `mybatis/FileTest.java:36`).

### 4.5 메일 — `src/com/lookation/util/EmailManager.java`

- 정적 유틸. `JavaMailSenderImpl`을 **lazy static**으로 1회 생성(`:18-31`), Gmail SMTP 587 + STARTTLS.
- API: `sendSimple(to, from, subject, content)`(`:39`), `sendAttach(...)`(`:67`), `sendHtml(...)`(`:99`).
- 본문 HTML은 유틸이 아니라 **컨트롤러에 문자열로 박혀 있다**: `mybatis/Email.java:30-…`, `controller/SendSignUpEmailController.java:44-…`, `mybatis/ReportList.java:162-…`. 메일 안의 링크는 운영 도메인이 하드코딩되어 있다(`mybatis/Email.java:47`).

### 4.6 페이징

- **구현된 페이징이 없다.** page/offset 파라미터를 받는 코드도, `LIMIT`/`OFFSET`도 없다.
- 목록은 전체 조회이거나, Oracle `ROWNUM`으로 "상위 N개"만 자른다 — `mybatis/mapper/MainLocationInfoDAO.xml:11`, `:25` (`<![CDATA[ ROWNUM <= 3 ]]>`), `BookApplyDAO.xml`, `MemberReviewListDAO.xml` 동일 패턴.
- 목록 화면을 페이지네이션으로 바꾸려면 쿼리·컨트롤러·JSP를 모두 새로 만들어야 한다.

### 4.7 트랜잭션

- **없다.** `@Transactional` 0건, `TransactionTemplate` 0건, `setAutoCommit(false)` 0건, `tx:advice`/`DataSourceTransactionManager` 빈 0건.
- `SqlSessionTemplate`은 autocommit으로 동작하고, JDBC DAO는 `Connection`을 열고 바로 `close()` 한다(`dao/MemberAccountDAO.java:22-45`).
- 여러 테이블을 쓰는 액션(공간 등록 `mybatis/Location.java`의 `inputBasicInfo`→`inputThumbnail`→`inputFacilityInfo` 연쇄)은 중간 실패 시 부분 반영된다. 일부 원자성은 Oracle 프로시저에 위임되어 있다(`CREATE_MEMBER_ACCOUNT`, `CREATE_HOST_ACCOUNT` 등 12개 — `중요파일/01. sql/lookation_procedure.sql`).

### 4.8 예외 처리

- `@ExceptionHandler`, `HandlerExceptionResolver` 빈, `<error-page>` 모두 없다.
- 관례는 `try { … } catch (Exception e) { System.out.println(e.toString()); }` — 저장소 전체 `System.out` 62회/29개 파일. `printStackTrace`는 0회.
- 따라서 예외는 Tomcat 기본 500 페이지로 떨어지고, 사용자에게는 흰 화면이 보인다.

---

## 5. 빌드·실행

### 5.1 빌드 방식: Eclipse Dynamic Web Project (Maven/Gradle 없음)

- `pom.xml`, `build.gradle`, `Makefile` **없음** (저장소 전체 검색 결과 0).
- `.classpath`, `.project`, `.settings/`도 **저장소에 없다** — `.gitignore:24-26`이 제외한다. 즉 이 저장소만 clone해서는 Eclipse 프로젝트 메타데이터가 없다.
- 의존성은 `Lookation/WebContent/WEB-INF/lib/*.jar` 26개를 Eclipse의 `Web App Libraries`로 그대로 쓰는 방식이다 (`.gitignore:53` 주석: "무빌드 서블릿 프로젝트라 수동 관리").
- 컴파일 산출물은 `Lookation/WebContent/WEB-INF/classes/`로 나가고(`.gitignore:34`), `src` 아래의 `*.xml`(매퍼)도 같은 곳으로 복사된다. 그래서 `mapperLocations="classpath:com/lookation/mybatis/mapper/*.xml"`이 동작한다(`dispatcher-servlet.xml:60`).

### 5.2 로컬 기동 절차

1. Eclipse에서 **File → New → Dynamic Web Project**, 이름 `Lookation`, 컨텍스트 루트는 `Lookation` 또는 `/`.
2. `Lookation/src`를 소스 폴더로, `Lookation/WebContent`를 웹 콘텐츠 루트로 지정. Target runtime은 Tomcat 8.0 이상(Servlet 3.1).
3. `중요파일/02. dispatcher-servlet/dispatcher-servlet.xml`을 `Lookation/WebContent/WEB-INF/`로 **복사**한다(저장소에 없음).
   - 이 사본은 `localDataSource`(`localhost:1521:xe`, `lookation_local`)를 쓰고, `sqlSessionFactory`도 local을 본다(`:33-35`, `:57-60`).
   - **그대로는 기동 실패한다**: `:190-192`가 존재하지 않는 `com.lookation.controller.ProfileFormController`를 참조한다. 해당 `<bean>`을 지우거나 `mybatis/Account.java`의 `@RequestMapping("/actions/profile.action")`과 중복되지 않게 정리해야 한다.
4. Oracle XE에 §5.4 순서로 스키마를 만든다.
5. Tomcat에 publish → `http://localhost:8080/Lookation/actions/membermain.action`.

### 5.3 WAR export

| 파일 | 내용 | 용도 |
|---|---|---|
| `중요파일/00. export/소스용/Lookation.war` | `WEB-INF/classes`에 **.class 151개 + .java 149개**, `WEB-INF/lib` 26개, `WEB-INF/views` 123항목, `dispatcher-servlet.xml`(9,076B) | 소스 배포용(코드 열람). 실행도 가능하지만 소스가 함께 들어 있다 |
| `중요파일/00. export/호스팅용/ROOT.war` | `WEB-INF/classes`에 .class 151개, `WEB-INF/lib` 26개, `dispatcher-servlet.xml` | **실제 운영 배포본**. 컨텍스트 루트 `/` |

- 배포본의 `dispatcher-servlet.xml`은 `remoteDataSource`(`34.64.215.203`, `lookation_main`)를 `sqlSessionFactory`에 물리고(`WAR 내 WEB-INF/dispatcher-servlet.xml:56`), DAO 빈도 전부 remote를 쓴다.
- 배포본에는 `/actions/adminlogout.action` 빈이 있고(`:146`), `/actions/profile.action` 빈은 없다. **저장소의 `중요파일` 사본과 정확히 반대**다.
- WAR 루트에 `index.jsp`가 없으므로 `/`는 404이고, 진입은 항상 `/actions/*.action`이다.
- JSP의 링크/폼은 컨텍스트 경로 없이 상대 URL(`membermain.action`)을 쓴다. 현재 URL이 `/…/actions/…`일 때만 맞으므로, **컨텍스트 이름을 바꿔도 동작하지만 `/actions/` 밖에서 렌더링되는 화면은 깨진다**.

### 5.4 DB 초기화 순서

`중요파일/01. sql/※sql 실행순서.txt`:

```
## sys (SYS 계정)
1. lookation_sys_init.sql          -- CREATE USER LOOKATION IDENTIFIED BY <REDACTED>; GRANT CONNECT,RESOURCE,DBA

## lookation (LOOKATION 계정)
1. lookation_function.sql (F_CODE)
2. lookation_create_table.sql      -- 테이블 76개
3. lookation_sequence.sql
4. lookation_static_data.sql
5. lookation_view.sql              -- 뷰 66개
6. lookation_procedure.sql         -- 프로시저 12개
7. lookation_function.sql (나머지) -- F_HOST_BALANCE 등
8. lookation_dummy_data.sql        -- (선택) 더미 데이터
```

- `lookation_sys_init.sql`은 DBA 권한을 부여하므로 SYS/SYSTEM으로 실행한다.
- SQL 파일은 **UTF-8이 아니다**(`file -I`가 `iso-8859-1`로 판별 — `lookation_function.sql`, `lookation_create_table.sql`). 한글 주석 바이트로 보아 EUC-KR 계열로 [INFERENCE]. UTF-8로 열면 주석이 깨지므로 실행 도구 인코딩을 맞출 것.
- 코드가 기대하는 스키마는 `LOOKATION`이며, `dispatcher-servlet.xml`의 계정(`lookation_local`, `lookation`, `lookation_main`)은 이 스키마의 복제본이다. **세 이름이 서로 다른 시점의 스냅샷일 수 있다.**

---

## 6. 리스크·함정

| # | 리스크 | 근거 | 대응 |
|---|---|---|---|
| 1 | **설정 파일이 저장소에 없고, 있는 사본은 기동 불가** | `.gitignore:60`; `중요파일/02. dispatcher-servlet/dispatcher-servlet.xml:190-192`가 미존재 클래스 `ProfileFormController` 참조 | 복사 후 해당 `<bean>` 제거. 배포본(`ROOT.war`) 내용과 다르다는 점을 항상 확인 |
| 2 | **DB 자격증명 평문 + 3개 스키마 혼재** | `dispatcher-servlet.xml:33-35`, `:44-46` (`<REDACTED>`), `lookation_sys_init.sql` | 값을 바꿀 때 세 계정을 모두 확인. 커밋에 비밀번호를 추가하지 말 것 |
| 3 | **인증이 각 메서드에 복붙** — 새 액션에서 세션 검사를 빠뜨리면 즉시 권한 우회 | `admin_id` 54회, `hostCode` 31회, `memberCode` 17회; 필터/인터셉터 0개(`web.xml:31-46`) | 새 액션 작성 시 기존 메서드의 검사 블록을 그대로 복사 |
| 4 | **상태 변경이 GET** — 메서드명 패턴(`delete|remove|blind|black|withdraw|cancel` + GET) 32건 중 폼·팝업·목록 7건을 제외하면 25건이 상태 변경 후보 | `mybatis/BlindManager.java:45`(`blindremove`), `mybatis/NoticeManager.java:105`(`noticedelete`), `mybatis/HelpManager.java:147`(`helpdelete`) | CSRF 방어 없음. 삭제 로직을 건드릴 때 POST 전환 여부를 별도 판단 |
| 5 | **같은 테이블에 SQL 두 벌** — MyBatis XML과 JDBC DAO | `mybatis/mapper/MemberAccountDAO.xml`(isLogin 등 8개) vs `dao/MemberAccountDAO.java`(add/countEmail/countNick, `:29` `{CALL CREATE_MEMBER_ACCOUNT}`) | 회원 생성은 **프로시저 경로만** 존재. 한쪽만 고치면 화면마다 동작이 달라진다 |
| 6 | **JDBC DAO의 커넥션 누수** — `finally` 0회, 예외 시 `close()` 미실행 | `dao/*.java` 전체 `finally` 0건, `dao/BankInfoDAO.java:167-180` | 수정 시 try-with-resources로 바꾸는 것이 안전(단, 동작 변경 최소화) |
| 7 | **SQL 문자열 결합(주입 위험)** | `dao/BankInfoDAO.java:173` `" WHERE HOST_CODE = '" + identifyCode +"'"` | 같은 파일의 다른 쿼리처럼 `?` 바인딩으로 통일 |
| 8 | **운영 도메인/경로 하드코딩** | `mybatis/Email.java:47`, `mybatis/ReportList.java:177`, `controller/SendSignUpEmailController.java:63` (`http://sb92120.cafe24.com/...`), `SendSignUpEmailController.java:24`(`C:/Users/Bum/...`) | 메일 링크는 설정값으로 빼야 한다 |
| 9 | **메일 본문 버그** — 문자열 안에 `\"`로 이스케이프된 채 `+ identify +`가 들어가 링크가 깨진다 | `mybatis/ReportList.java:177` | 수정 시 문자열 연결 위치를 밖으로 뺄 것 |
| 10 | **등록되지 않은 컨트롤러 2개 + 유령 클래스 참조 1개** | 소스는 있으나 미등록: `controller/MypageFormController.java`(뷰도 `mypageUser.jsp`로 미존재, `:40`), `controller/LocationPackageAjaxController.java`. 소스 자체가 없음: XML 사본의 `ProfileFormController` | 삭제하거나 매핑 추가. 유령 빈은 기동 전에 제거 |
| 11 | **존재하지 않는 뷰 참조 7건** | `../WEB-INF/views/FormImage.jsp`, `FormDownload.jsp`, `View.jsp`, `ViewDownload.jsp`, `admin/loginAdmin.jsp`, `sample.jsp`, `user/mypageUser.jsp` | 화면 작업 시 실제 파일명 확인. `admin`은 `loginFormAdmin.jsp`가 실제 파일 |
| 12 | **끊긴 링크** — 매핑 없는 `.action` 호출 4건 | `includes/mypage_Sidebar(user).jsp:46`(`checkifhavebankinfo.action`), `views/user/bookApply.jsp:251`(`home.action`) 외 2건(`adminlogout.action`, 배포본에만 있음) | AJAX는 404를 조용히 삼킨다. 화면 수정 시 함께 정리 |
| 13 | **빈 `*.action` 파일 56개** | `Lookation/WebContent/actions/*.action` (전부 0바이트) | 서블릿 매핑이 선점하므로 무해하지만, "여기에 파일을 만들면 매핑된다"는 오해를 유발한다 |
| 14 | **God class** — 한 파일에 17개 엔드포인트 | `mybatis/Location.java` 1,180줄 / 17 매핑 / `getMapper` 31회, `mybatis/Account.java` 481줄 | 수정 범위를 좁히고, 새 기능은 별도 클래스로 |
| 15 | **테스트 커버리지 없음** | `Lookation/test/run-tests.sh`로 JUnit4+Mockito 단위 테스트 계층을 추가했다. 현재는 계정 로그인 등 일부 경로만 덮는다 | 새 수정은 컨트롤러 경계 테스트를 함께 추가한다 |
| 16 | **뷰 이름에 쿼리스트링** | `mybatis/Account.java:59`, `controller/BankInfoManageController.java:72` | 뷰 이름을 파싱/검증하는 도구를 붙이면 깨진다 |
| 17 | **업로드 파일이 재배포 시 소실** | `util/FileManager.java:30` `getRealPath(saveFolderName)` — 호출측이 `"images"`를 넘겨 WAR 내부 경로에 저장(`mybatis/Location.java:238`) | 파일 저장 위치를 바꾸려면 `FileManager`만 고치면 되지만, 기존 경로 문자열(`"images"`)이 8개 컨트롤러에 흩어져 있다 |
| 18 | **파일 다운로드 헤더 인코딩 핵** | `util/FileManager.java:65` `EUC-KR → 8859_1` 재인코딩 | 한글 파일명이 깨지면 여기다. RFC 5987(`filename*=UTF-8''`)로 바꾸는 것이 정답 |
| 19 | **손수 조립한 JSON** | `controller/LocationDetailAjaxController.java:50-57` | 따옴표 포함 값에서 JSON이 깨진다. json-lib이 이미 lib에 있으므로 그걸 쓰는 편이 안전 |
| 20 | **역할 prefix가 없다** — `/user`, `/host`, `/admin` URL을 가정한 문서/이슈는 틀렸다 | 전체 매핑 180개가 `/actions/*.action` | 역할은 `identify` 파라미터와 JSP 폴더로만 구분 |
| 21 | **메신저 전송 액션에 인증·소유권 검사가 없다** — `hmsgsend`, `mmsgsend`가 세션 확인과 `book_code`/`msg_code` 소유권 검증 없이 DTO를 저장한다 | `mybatis/HostMessenger.java:84-107`, `mybatis/MemberMessenger.java:90-…`; 같은 파일의 조회 액션은 `checkInfo`로 검사한다(`HostMessenger.java:50-59`) | 세션 검사 + 예약·메시지 소유권 검증 추가. 이슈 #8 |
| 22 | **가입 인증 메일이 평문 비밀번호를 hidden 필드로 담아 HTTP로 전송** — 메일 본문에 `pw`가 그대로 들어가고 폼 action이 `http://`다 | `controller/SendSignUpEmailController.java:63-75` | 토큰 기반 인증으로 교체하고 HTTPS 사용. 이슈 #7 |

---

## 부록 A. 엔드포인트 인벤토리

### A.1 XML 방식 (`dispatcher-servlet.xml` `<bean name>`) — 20개

배포본(`ROOT.war` 내부 `WEB-INF/dispatcher-servlet.xml`) 기준. 저장소의 `중요파일` 사본은 `adminlogout` 대신 `profile`을 갖는다(§6-1).

| URL | 컨트롤러 | 주입 DAO |
|---|---|---|
| `/actions/signupform.action` | `SignUpFormController` | — |
| `/actions/sendsignupemail.action` | `SendSignUpEmailController` | — |
| `/actions/signup.action` | `SignUpController` | `MemberAccountDAO`, `HostAccountDAO` |
| `/actions/accountajax.action` | `AccountAjaxController` | `MemberAccountDAO`, `HostAccountDAO` |
| `/actions/adminloginform.action` | `AdminLoginFormController` | — |
| `/actions/adminlogin.action` | `AdminLoginController` | `AdminDAO` |
| `/actions/adminlogout.action` | `AdminLogoutController` | — |
| `/actions/inspectmanager.action` | `InspectManagerController` | `InspectManagerDAO` |
| `/actions/inspectprocess.action` | `InspectProcessController` | `InspectManagerDAO` |
| `/actions/insepectinsert.action` | `InspectInsertController` | `InspectManagerDAO` |
| `/actions/bankinfomanage.action` | `BankInfoManageController` | `BankInfoDAO`, `MemberAccountDAO` |
| `/actions/bankinfoaddpopup.action` | `BankInfoAddPopupController` | `BankInfoDAO` |
| `/actions/bankinfoadd.action` | `BankInfoAddController` | `BankInfoDAO` |
| `/actions/bankinforemove.action` | `BankInfoRemoveController` | `BankInfoDAO` |
| `/actions/deleteform.action` | `DeleteFormController` | — |
| `/actions/deletemember.action` | `DeleteMemberController` | `DeleteMemberDAO` |
| `/actions/deletehost.action` | `DeleteHostController` | `DeleteHostDAO` |
| `/actions/locationdetail.action` | `LocationDetailController` | `LocationDetailDAO` |
| `/actions/locdetailajax.action` | `LocationDetailAjaxController` | `LocationDetailDAO` |
| `/actions/locationdetailhost.action` | `LocationDetailHostController` | `LocationDetailDAO` |

> `insepectinsert`는 오타지만 **실제 URL**이다. 오타를 고치면 링크가 끊긴다.

JDBC DAO ↔ 인터페이스: `MemberAccountDAO`·`HostAccountDAO`→`IAccountDAO`, `AdminDAO`→`IAdminDAO`, `BankInfoDAO`→`IBankInfoDAO`, `DeleteMemberDAO`→`IDeleteMemberDAO`, `DeleteHostDAO`→`IDeleteHostDAO`, `InspectManagerDAO`→`IInspectManagerDAO`, `LocationDetailDAO`→`ILocationDetailDAO`. 모두 `DataSource` 주입형 수제 JDBC.

### A.2 어노테이션 방식 (`com.lookation.mybatis`, `@Controller`) — 37개 클래스 / 160 매핑

| 클래스 | 매핑 수 | 대표 URL | 사용 매퍼 |
|---|---|---|---|
| `Account` | 12 | `loginform.action`, `login.action`, `confirmpasswordform.action`, `confirmpassword.action` … | `IHostAccountDAO`, `IMemberAccountDAO` |
| `BatisSample` | 1 | `batissample.action` | `ISampleDAO` |
| `BlindContent` | 1 | `blindcontent.action` | `IHostAccountDAO`, `IMemberAccountDAO` |
| `BlindManager` | 2 | `blindmanager.action`, `blindremove.action` | `IBlindManagerDAO` |
| `BookApply` | 2 | `bookapply.action`, `bookpay.action` | `IBookApplyDAO`, `IMemberAccountDAO` |
| `BookList` | 10 | `booklist.action`, `bookdetails.action`, `bookcancel.action`, `membercancel.action` … | `IBookListDAO`, `IHostAccountDAO`, `IMemberAccountDAO` |
| `Email` | 1 | `sendconfirmemail.action` | — |
| `FileTest` | 4 | `imagetest.action`, `downloadtest.action`, `uploadimagetest.action`, `uploaddownloadtest.action` | — |
| `Help` | 2 | `help.action`, `helpdetail.action` | `IHelpDAO`, `IHostAccountDAO`, `IMemberAccountDAO` |
| `HelpManager` | 6 | `helpmanager.action`, `helpinsertform.action`, `helpinsert.action`, `helpdelete.action` … | `IHelpManagerDAO` |
| `HostBlackManager` | 5 | `hostblackmanager.action`, `reportdetails.action`, `setblackhostform.action`, `setblackhost.action` … | `IHostBlackManagerDAO` |
| `HostMessenger` | 2 | `hmessenger.action`, `hmsgsend.action` | `IHostAccountDAO`, `IHostMessengerDAO` |
| `LoadAndExchange` | 4 | `loadandexchange.action`, `memberload.action`, `exchange.action`, `terms.action` | `IHostAccountDAO`, `ILoadAndExchangeDAO`, `IMemberAccountDAO` |
| `LoadManager` | 3 | `loadmgr.action`, `loadmgrapprove.action`, `loadmgrdeny.action` | `IUserLoadManagerDAO` |
| `Location` | 17 | `locationlist.action`, `deletelocation.action`, `basicform.action`, `basicajax.action` … | `IHostAccountDAO`, `ILocationDAO` |
| `LocationQna` | 8 | `writeqna.action`, `modifyformqna.action`, `qnainsert.action`, `modifyqna.action` … | `IHostAccountDAO`, `ILocationQnaDAO`, `IMemberAccountDAO` |
| `LocationReview` | 8 | `writereview.action`, `modifyformreview.action`, `reviewinsert.action`, `modifyreview.action` … | `IHostAccountDAO`, `ILocationReviewDAO`, `IMemberAccountDAO` |
| `Main` | 5 | `membermain.action`, `memberlogout.action`, `hostmain.action`, `hostlogout.action`, `adminmain.action` | `IHostAccountDAO`, `IMainLocationInfo`, `IMemberAccountDAO`, `IStatDAO` |
| `MemberBlackManager` | 5 | `memberblackmanager.action`, `reportmemdetails.action`, `setblackmemform.action`, `setblackmem.action` … | `IMemberBlackManagerDAO` |
| `MemberMessenger` | 2 | `mmessenger.action`, `mmsgsend.action` | `IMemberAccountDAO`, `IMemberMessengerDAO` |
| `MemberQnaList` | 3 | `userqnalist.action`, `modifyqnainlist.action`, `deleteqnainlist.action` | `IMemberAccountDAO`, `IMemberQnaListDAO` |
| `MemberReviewList` | 3 | `userreviewlist.action`, `modifyreviewinlist.action`, `deletereviewinlist.action` | `IMemberAccountDAO`, `IMemberReviewListDAO` |
| `MileageHistoryList` | 1 | `mileagehistory.action` | `IHostAccountDAO`, `IHostHistoryListDAO`, `IMemberAccountDAO`, `IMemberHistoryListDAO` |
| `MypageMain` | 1 | `mypage.action` | `IHostAccountDAO`, `IMemberAccountDAO` |
| `Notice` | 2 | `notice.action`, `noticedetail.action` | `IHostAccountDAO`, `IMemberAccountDAO`, `INoticeDAO` |
| `NoticeManager` | 6 | `noticemanager.action`, `noticeinsertform.action`, `noticeinsert.action`, `noticedelete.action` … | `INoticeManagerDAO` |
| `Package` | 6 | `packagemanager.action`, `packageform.action`, `inputpackageform.action`, `deletepackageform.action` … | `IHostAccountDAO`, `ILocationDAO`, `IPackageDAO` |
| `ReportHost` | 2 | `reporthost.action`, `reporthostimg.action` | `IHostAccountDAO`, `IReportHostDAO` |
| `ReportList` | 14 | `bookreportlist.action`, `bookpopup0.action`, `bookpopup1.action`, `bookreportproc.action` … | `IReportDAO` |
| `ReportUser` | 2 | `reportuser.action`, `reportuserimg.action` | `IMemberAccountDAO`, `IReportUserDAO` |
| `Search` | 2 | `search.action`, `searchkeyword.action` | `IMemberAccountDAO`, `ISearchDAO` |
| `Service` | 4 | `termsofservice.action`, `locmanagement.action`, `privacy.action`, `refund.action` | `IHostAccountDAO`, `IMemberAccountDAO` |
| `UserQnaManager` | 4 | `userqnamanager.action`, `userqnapopup.action`, `qnadelete.action`, `selectdelete.action` | `IuserQnaManagerDAO` |
| `UserReviewManager` | 4 | `userreviewmanager.action`, `userreviewpopup.action`, `reviewdelete.action`, `selectremove.action` | `IuserReviewManagerDAO` |
| `blackListEmail` | 1 | `/actions/` (**경로 세그먼트 없음**) | — |
| `hostQnaManager` | 3 | `hostqnamanager.action`, `hostqnapopup.action`, `hostqnareplydelete.action` | `IhostQnaManagerDAO` |
| `hostReviewManager` | 3 | `hostreviewmanager.action`, `hostreviewpopup.action`, `hostreviewdelete.action` | `IhostReviewManagerDAO` |

### A.3 매퍼 XML ↔ 인터페이스 (34쌍, 전수 검증됨)

`src/com/lookation/mybatis/mapper/*.xml`의 `namespace`는 모두 `com.lookation.dao` 패키지의 실존 인터페이스와 일치한다. 이름이 다른 4쌍은 §2.1 표 참고. 나머지는 파일명과 인터페이스명이 동일하다.

### A.4 미등록/미사용

| 대상 | 상태 |
|---|---|
| `controller/MypageFormController.java` | XML `<bean>` 없음, `@Controller` 없음 → 도달 불가. 반환 뷰 `views/user/mypageUser.jsp`도 미존재(`:40`) |
| `controller/LocationPackageAjaxController.java` | 동일하게 미등록 |
| `controller/ProfileFormController` | **클래스 파일 자체가 없음**. 저장소 사본 `dispatcher-servlet.xml:190-192`만 참조 |
| `mybatis/BatisSample.java`, `mybatis/FileTest.java` | 학습용 데모. 매핑은 살아 있으나 참조 뷰(`sample.jsp`, `FormImage.jsp` 등)가 없다 |
| `json-lib` 계열 jar 5개 | 코드에서 미사용 |
