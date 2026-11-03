# Lookation — AI 개발 컨텍스트

> 2020년 팀 프로젝트(PFINDER)로 만든 공간 대여·예약 사이트. **레거시 스택 + 테스트 없음 + 빌드 도구 없음**을 전제로 작업한다.
> 이 파일은 진입점이고, 상세는 아래 문서로 위임한다. 코드를 고치기 전에 해당 문서를 먼저 읽는다.

## Agent skills

### Issue tracker

Issues live in GitHub Issues (`team-pfinder/Spring_Project`) via the `gh` CLI. See `docs/agents/issue-tracker.md`.

### Triage labels

Default five-role vocabulary: `needs-triage`, `needs-info`, `ready-for-agent`, `ready-for-human`, `wontfix`. See `docs/agents/triage-labels.md`.

### Domain docs

Single-context: `GLOSSARY.md` + `docs/adr/` at the repo root. See `docs/agents/domain.md`.

## 먼저 읽을 문서

| 문서 | 내용 | 언제 |
| --- | --- | --- |
| [`GLOSSARY.md`](GLOSSARY.md) | 확정 용어와 코드 표기, 상태 값 쟁점 | 항상. 이슈·테스트·커밋 이름에 같은 표기를 쓴다 |
| [`docs/architecture.md`](docs/architecture.md) | 스택, 요청 흐름, 명명 규칙, 빌드·기동, 리스크 20건 | 코드를 처음 만질 때 |
| [`docs/screens.md`](docs/screens.md) | URL 180개 ↔ 컨트롤러 ↔ JSP ↔ 역할, AJAX, 갭 | 화면·URL을 바꿀 때 |
| [`docs/spec/lookation/index.md`](docs/spec/lookation/index.md) | 기획서 분석 인덱스, finding 35건, **blocker 6건** | 기능 요구를 해석할 때 |
| [`docs/spec/lookation/validation.md`](docs/spec/lookation/validation.md) | 요구사항 충돌·누락·질문 | 구현 전 결정 확인 |
| [`docs/db/table-spec.md`](docs/db/table-spec.md) | 테이블 78개, 컬럼 294, DDL 기준 FK 78 | SQL·DTO를 만질 때 |
| [`docs/reviews/2026-11-02-cross-validation.md`](docs/reviews/2026-11-02-cross-validation.md) | 외부 에이전트 3개 교차검증 결과와 반영·기각 내역 | 문서 수치를 신뢰하기 전 |
| `docs/adr/` (아직 없음) | 구조 결정 기록 | 설계를 바꿀 때 |

## 스택

| 항목 | 값 |
| --- | --- |
| 언어 | Java 8 (JDK 1.8) |
| 웹 | Spring MVC 3.0.2 (XML 설정 중심) |
| SQL | MyBatis 3.5.1 + mybatis-spring 1.3.2, 손으로 쓴 JDBC DAO 8개 |
| DB | Oracle XE (`LOOKATION` 스키마) |
| WAS | Tomcat 8.0+ (Servlet 3.1) |
| 뷰 | JSP + JSTL/EL, 레이아웃 엔진 없음 |
| 빌드 | **없음** — Eclipse Dynamic Web Project, `WEB-INF/lib` jar 26개, WAR export |

## 실행

1. Eclipse에서 Dynamic Web Project `Lookation` 생성. 소스 `Lookation/src`, 웹 루트 `Lookation/WebContent`, Target runtime Tomcat 8+.
2. `중요파일/02. dispatcher-servlet/dispatcher-servlet.xml`을 `Lookation/WebContent/WEB-INF/`로 복사한다. **그대로는 기동 실패**: `:190`의 존재하지 않는 `com.lookation.controller.ProfileFormController` 빈을 지운다.
3. Oracle에 `중요파일/01. sql/※sql 실행순서.txt` 순서로 스키마를 만든다. SQL 파일은 EUC-KR이다.
4. Tomcat publish 후 `http://localhost:8080/Lookation/actions/membermain.action`.
5. `/`는 404다(welcome-file 전부 없음). 진입은 항상 `/actions/*.action`.

배포본은 `중요파일/00. export/호스팅용/ROOT.war`(컨텍스트 `/`), 소스 배포본은 `소스용/Lookation.war`다. 두 WAR의 `dispatcher-servlet.xml`은 저장소 사본과 내용이 다르다.

## 코드 규칙

### 라우팅 — 두 체계

| 체계 | 위치 | 등록 방법 |
| --- | --- | --- |
| XML | `com.lookation.controller.*` (`implements Controller`) | `dispatcher-servlet.xml`의 `<bean name="/actions/x.action">` **필수** |
| 어노테이션 | `com.lookation.mybatis.*` (`@Controller`) | `@RequestMapping("/actions/x.action")`, 컴포넌트 스캔 |

- URL은 전부 `/actions/<이름>.action`이다. **`/user`, `/host`, `/admin` URL 접두사는 없다** — 그 이름은 JSP 폴더다.
- 역할 구분은 세션 키 + `identify=member|host` 파라미터다.
- `com.lookation.controller`에 클래스를 추가하고 XML 빈을 빠뜨리면 404다.

### 뷰

- ViewResolver 빈이 없어 prefix/suffix가 빈 문자열이다. 뷰 이름은 `"../WEB-INF/views/<역할>/<파일>.jsp"` 상대경로로 쓴다.
- 쿼리스트링을 뷰 이름에 붙이는 관례가 있다(`return "../WEB-INF/views/common/login.jsp?identify=" + identify;`).
- `redirect:`는 상대(`redirect:loginform.action`)와 절대(`redirect:/actions/...`)가 혼용된다. 기존 파일의 방식을 따른다.

### 인증

- 필터·인터셉터가 없다. 각 액션 첫머리의 세션 검사를 **복사해서** 넣는다.
- 키: `memberCode`, `hostCode`, `admin_id`. 회원 키는 `identify + "Code"`로 동적 생성된다.
- 새 액션에서 검사를 빠뜨리면 즉시 권한 우회다.

### SQL

- 같은 테이블에 MyBatis 매퍼 XML과 JDBC DAO 두 벌이 있다. 어느 쪽을 고치는지 먼저 확인한다.
- 매퍼 XML 4개는 파일명과 인터페이스명이 다르다(`HostMileageHistoryDAO.xml`→`IHostHistoryListDAO` 등). 파일명으로 추정하지 않는다.
- 트랜잭션 설정이 없다(`@Transactional` 0건). 여러 테이블을 쓰는 기능은 원자성을 직접 설계하거나 Oracle 프로시저를 쓴다.
- 페이징이 없다. 목록은 전체 조회 또는 `ROWNUM <= N`이다.
- JDBC DAO는 `finally`가 없어 예외 시 커넥션이 샌다. 수정하는 김에 try-with-resources로 바꾼다.

### 기타

- CSRF: 상태 변경은 POST이며 `_csrf` 파라미터 또는 `X-CSRF-Token` 헤더가 필수다. 폼은 `/includes/csrf.jsp`를 include하고 AJAX는 공통 헤더 스크립트가 토큰을 붙인다.
- 설정: `LOOKATION_MAIL_USERNAME`, `LOOKATION_MAIL_PASSWORD`, `LOOKATION_DB_PASSWORD`, 선택 `LOOKATION_UPLOAD_DIR`·`LOOKATION_SITE_URL`. 없으면 기동 실패.
- AJAX JSON은 문자열 조립이다(`@ResponseBody` 0건). 값에 따옴표가 들어가면 깨진다.
- 파일 업로드는 `util/FileManager`(cos.jar), 메일은 `util/EmailManager`(Gmail SMTP)다. 운영 도메인·로컬 경로가 하드코딩되어 있다.
- DTO 필드는 DB 컬럼과 같은 snake_case다(`getLoc_code()`).
- 삭제·차단·취소 계열 GET 매핑이 32건(폼·팝업·목록 7건 포함)이고, 실제 상태 변경 후보는 25건이다(메서드명 `delete|remove|blind|black|withdraw|cancel` 기준). 새 기능은 POST를 쓴다.

## 검증

- 단위 테스트: `./Lookation/test/run-tests.sh` (JUnit 4 + Mockito, `Lookation/test/lib`).
  - 메인 소스를 `--release 8`로 컴파일한 뒤 컨트롤러 경계 테스트를 실행한다. Oracle·Tomcat 불필요.
  - 테스트 의존성은 `Lookation/test/lib`에만 두고 `WEB-INF/lib`에 넣지 않는다.
  - 새 테스트는 컨트롤러 요청/응답·DAO 상호작용 같은 외부 행위만 검증한다.
- 수동 스모크: 해당 `.action` 호출 → JSP 렌더 확인 → DB 반영 확인. 세션 키가 필요한 화면은 로그인 후 확인한다.
- 기동 확인 없이 "동작한다"고 보고하지 않는다. Oracle·Tomcat이 없으면 그 한계를 명시한다.

## 교차검증

외부 에이전트 교차검증은 Orca에서 실행하고 결과를 `docs/reviews/YYYY-MM-DD-cross-validation.md`에 남긴다.

| 검증자 | 실행 |
| --- | --- |
| Gemini | `orca agent launch --agent gemini --worktree path:<repo> --prompt "<brief>"` |
| Codex | `orca agent launch --agent codex --worktree path:<repo> --prompt "<brief>"` |
| OpenCode | `opencode run --auto --model opencode-go/muse-spark-1.3-contributor "<brief>"` |

- OpenCode는 `orca agent launch --agent opencode`가 `agent_session_operation_unknown`으로 실패하므로 Orca 터미널에서 `opencode run`을 직접 실행한다.
- 검증자는 읽기 전용으로 두고, 결과 파일은 `/tmp/lookation-review-<agent>.md`에 쓰게 한다.
- 수치 지적은 반영 전에 직접 재계산한다.

## 하지 말 것

- `WEB-INF/dispatcher-servlet.xml`을 커밋하지 않는다(`.gitignore` 대상, 자격증명 포함).
- DB 비밀번호·운영 도메인을 새로 하드코딩하지 않는다.
- `@Transactional`을 붙이고 동작한다고 가정하지 않는다. 트랜잭션 매니저 빈이 없다.
- 역할별 URL 접두사를 가정한 문서·이슈를 만들지 않는다.
- 테스트 없이 대규모 리팩터링을 하지 않는다. 먼저 회귀 시나리오를 적는다.

## 미해결 결정 (구현 전)

기획서 분석에서 blocker 6건이 나왔다. 결정 전에는 해당 기능을 구현하지 않는다.

| ID | 쟁점 |
| --- | --- |
| [V-ACCOUNT-BLACKLIST-01](docs/spec/lookation/validation.md#v-account-blacklist-01) | 블랙리스트 영구 vs 기간제 |
| [V-PERM-BLACKLIST-01](docs/spec/lookation/validation.md#v-perm-blacklist-01) | 블랙리스트 허용 기능 |
| [V-STATUS-RESERVATION-01](docs/spec/lookation/validation.md#v-status-reservation-01) | 예약 상태 3단계 vs 4단계 |
| [V-STATUS-INSPECT-01](docs/spec/lookation/validation.md#v-status-inspect-01) | 검수 상태 코드표 |
| [V-PROC-SETTLE-01](docs/spec/lookation/validation.md#v-proc-settle-01) | 정산 자동 vs 호스트 신청 |
| [V-INT-PAYMENT-01](docs/spec/lookation/validation.md#v-int-payment-01) | 승인예약 포함 여부 |

보안 취약점은 GitHub 이슈 #1~#8로 등록되어 있다. 구현 전에 #1·#4·#7·#8을 먼저 확인한다.

## 문서 갱신

| 바꾸는 것 | 같이 갱신 |
| --- | --- |
| DDL·테이블 | `docs/db/table-spec.*` 재생성 |
| URL·JSP·컨트롤러 매핑 | `docs/screens.md` |
| 패키지·요청 흐름·규약 | `docs/architecture.md` |
| 도메인 용어·상태 값 | `GLOSSARY.md`, 필요 시 `docs/spec/lookation/glossary.md`·`domain.md` |
| 기획 해석·정책 결정 | `docs/spec/lookation/validation.md`의 finding 상태 |

테이블 명세 재생성(원본은 EUC-KR이라 UTF-8 변환 후 실행):

```
iconv -f EUC-KR -t UTF-8 "중요파일/01. sql/lookation_create_table.sql" > /tmp/lookation_create_table.utf8.sql
python3 ~/.agents/skills/gorani-table-spec/scripts/table_spec.py \
  --ddl /tmp/lookation_create_table.utf8.sql --dialect oracle \
  --project Lookation --outdir docs/db --name table-spec \
  --formats md,html --sources "중요파일/01. sql/*.sql"
```

`xlsx`는 이 스킬 버전(3.17.0)의 저장 버그로 생성되지 않는다. `md`/`html`만 재생성한다.
