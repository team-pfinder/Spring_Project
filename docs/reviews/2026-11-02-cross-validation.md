# 교차검증 기록 — 2026-11-02

분석 산출물(`docs/architecture.md`, `docs/screens.md`, `docs/spec/lookation/`, `docs/db/table-spec.md`, `AGENTS.md`, `GLOSSARY.md`)과 보안 이슈를 외부 에이전트 3개로 교차검증한 결과다.

| 검증자 | 실행 | 결과 |
| --- | --- | --- |
| Gemini CLI 0.46.0 | Orca 터미널, 읽기 전용 | 발견 11건 |
| Codex (GPT-6-Luna) | Orca 터미널, 읽기 전용 | 발견 12건 |
| OpenCode (GPT-6-Luna) | `opencode run --auto`, 읽기 전용 | 발견 8건 (이후 기본 모델: `opencode-go/muse-spark-1.3-contributor`) |

검증자는 코드·문서·이슈를 수정하지 않았다. 반영은 이 세션에서 수행했다.

## 반영한 지적

| 지적 | 판정 | 반영 |
| --- | --- | --- |
| 엔드포인트 180은 매핑 수이고 고유 URL은 179, `/actions/profile.action` 1건 중복 | 수용 | `architecture.md` §0-1, `screens.md` §1·말미 |
| 어노테이션 매핑 161이 아니라 활성 160(주석 `modifyform.action` 제외) | 수용 | `architecture.md` §3.2 표, 부록 A.2 |
| "상태 변경 GET 32건"에는 폼·팝업·목록 7건이 포함, 실제 후보 25건 | 수용 | `architecture.md` §6-4, `AGENTS.md`, 이슈 #6 코멘트 |
| Servlet 3.1 `cookie-config`에는 SameSite가 없음 | 수용 | 이슈 #6 코멘트, Tomcat CookieProcessor/필터 대안 |
| `ProfileFormController`는 소스가 없는 유령 클래스(미등록 컨트롤러 2개와 구분) | 수용 | `architecture.md` §6-10 |
| 메신저 전송 액션에 세션·소유권 검사 없음(IDOR) | 수용 | 이슈 #8, `architecture.md` §6-21 |
| 가입 인증 메일이 평문 비밀번호를 hidden 필드로 담고 HTTP로 전송 | 수용 | 이슈 #7, `architecture.md` §6-22 |
| 업로드 RCE는 WAS가 JSP를 실행하는 배포 전제 | 수용(조건 명시) | 이슈 #3 코멘트 |
| SQLi "저장소 전체 1건"은 특정 grep 기준 | 수용(범위 명시) | 이슈 #2 코멘트 |
| 테이블 76·컬럼 278·관계(FK) 78, EL JSP 114 중 c:out 9, 평문 비밀번호, BankInfoDAO:173 | 확인 | 변경 없음 |

## 기각한 지적

| 지적 | 기각 이유 |
| --- | --- |
| `FileManager.doFileDownload`/`doFileDelete` 경로 traversal이 치명 취약점 | `doFileDownload`는 호출자 0건(죽은 코드). `doFileDelete` 호출자는 DB에 저장된 파일명만 전달한다(`HelpManager.java:241`, `LocationReview.java:196`). 사용자 입력으로 경로를 조작하는 경로는 확인되지 않아 잠재 위험으로만 기록했다(`architecture.md` §4.4). |
| `adminlogout.action`이 끊긴 링크라는 문서가 틀렸다 | 배포본 `ROOT.war`에는 빈이 있고 저장소 사본에는 없다. `screens.md` §7.2가 이미 이 차이를 서술한다. |
| 주석 포함 161개가 맞다 | 활성 매핑 기준으로는 160개다. 문서는 활성 기준임을 명시했다. |

## 검증

- 링크 검사: `check_links.py .` → 상대 링크 2,906개, 깨진 링크 0
- 수치 재계산: 활성 어노테이션 160, XML 빈 20, 고유 URL 179, 테이블 76, 컬럼 278, FK 78, EL JSP 114, c:out 9, GET 패턴 32/상태 변경 후보 25
- 이슈 상태: #1~#8 OPEN, 각 `bug` + 상태 라벨 1개
