# 구현 리뷰 기록 — 2026-11-03

보안 수정 커밋(97e4e88..HEAD)을 외부 리뷰어(Codex 계열)가 읽기 전용으로 검토한 결과와 반영 내역이다.

## 반영한 지적

| 심각도 | 지적 | 반영 |
| --- | --- | --- |
| P1 | `SignupPendingDTO`가 MyBatis 언더스코어 매핑 없이 조회되어 비밀번호가 NULL로 저장됨 | `SignupVerificationDAO.xml`에 `resultMap` 추가 |
| P1 | AJAX CSRF 헤더가 jQuery 재로딩 이후 인스턴스에 적용되지 않음 | `includes_home_end.jsp`와 `includes_admin.jsp`의 jQuery 로드 이후로 설정 이동 |
| P1 | 관리자 페이지에 `postAction` 미정의 | `includes_admin.jsp`에 함수 추가 |
| P1 | 공지·도움말 삭제 버튼의 `onclick` 문자열이 깨짐 | 닫는 `);"` 수정 |
| P0 | 비밀번호 찾기가 이메일만으로 임의 변경 가능 | `PASSWORD_RESET` 토큰 테이블·검증·1회 사용·POST 전용으로 교체 |
| P1 | `changepassword.action`이 GET으로 상태 변경 | POST 전용 매핑으로 변경 |
| P1 | DB 비밀번호 환경변수가 실제 DataSource에 연결되지 않음 | `#{systemEnvironment['LOOKATION_DB_PASSWORD']}`로 통일 |
| P2 | 인증 토큰 검증이 비원자적 | `markUsed` 선점 후 계정 생성, 이메일 중복 재검사 |
| P3 | `.bmp`가 accept에는 있으나 정책에서 거부 | BMP 시그니처·확장자 허용 |
| P3 | 기존 웹루트 이미지 삭제가 동작하지 않음 | 삭제 시 레거시 경로도 시도 |

## 검증

- 단위 테스트: `./Lookation/test/run-tests.sh` → 33 tests, OK
- 정적 검증: 자격증명 리터럴 0건, 문자열 결합 SQL 0건, 상태 변경 GET 0건(전환 대상 기준)
- 런타임 검증은 미수행: Oracle XE·Tomcat 부재. 수동 회귀는 `docs/screens.md` 기준으로 남아 있다.

## 2차 리뷰(Standards·Spec 축)

| 축 | 지적 | 반영 |
| --- | --- | --- |
| Spec | 비밀번호 재설정 메일 링크에 `email` 누락 | 링크에 이메일 추가, 폼 검증과 일치 |
| Spec | 관리자 신고처리·공지/도움말 등록·수정이 GET | POST 전용 매핑 + `postAction` 호출로 전환 |
| Spec | `<c:out>`과 `fn:escapeXml` 이중 이스케이프 | `c:out` 내부 `fn:escapeXml` 제거 |
| Spec | 메신저 업로드가 소유권 검사보다 먼저 저장 | 거부 시 업로드 파일 삭제 |
| Spec | 신고 사유가 이스케이프되지 않음 | 관리자 팝업 6곳에 `c:out` 적용 |
| Standards | DDL 78개 테이블 vs 문서 77 | 명세 재생성(78/294), AGENTS·architecture 수치 갱신 |
| Standards | `sendsignupemail`/`signup` 컨트롤러가 문서에 잔존 | `screens.md`를 `SignupEmail`/`VerifyEmail`로 갱신 |
| Standards | 필터·서블릿·리스너 미문서화 | `architecture.md` 요청 흐름 서술 갱신 |
| Standards | 운영 도메인 기본값 하드코딩 | `LOOKATION_SITE_URL` 필수화 |
| Standards | 이메일 인증 GET이 상태 변경 | GET은 확인 폼, POST가 계정 생성 |
| Standards | JDBC DAO 수정부 커넥션 누수 | `hostBankInfoCount` try-with-resources |
| Standards | 신규 DTO camelCase | snake_case 필드·접근자로 변경 |

미반영(의도적): `/uploads/*`는 공간 썸네일 공개를 위해 익명 조회를 허용한다. 비밀번호 변경은 현재 세션만 무효화하며, 전역 세션 레지스트리는 이번 범위에 없다.
