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
