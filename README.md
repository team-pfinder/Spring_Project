# Lookation

Lookation은 파티룸 등 공간을 등록하고 예약하는 공간 대여 서비스다. 2020년 팀 프로젝트로 만든 레거시 웹 앱은 동결했고, 현재 `server/`에서 백엔드를 재작성하고 있다.

## 프로젝트 상태

| 경로 | 상태 | 기술 |
| --- | --- | --- |
| [`Lookation/`](Lookation/) | 레거시 앱, 동결 | Java 8, Spring MVC 3.0.2, JSP, MyBatis, Oracle |
| [`server/`](server/) | 신규 백엔드, 개발 중 | Kotlin 2.3, JDK 25, Spring Boot 4.1.2, Spring Security 7, PostgreSQL, Flyway, jOOQ, JWT |

레거시 앱의 구조와 실행 조건은 [아키텍처 문서](docs/architecture.md)를 참고한다. 신규 백엔드가 레거시 기능을 어떻게 대체하는지는 [패리티 체크리스트](docs/rewrite/parity-checklist.md)에서 확인할 수 있다.

## 빠른 시작

필요 항목: JDK 25, Docker

저장소 루트에서 로컬 인프라를 시작한다.

```bash
docker compose up -d --wait
```

새 터미널에서 백엔드를 실행한다.

```bash
cd server
./gradlew bootRun
```

Swagger UI: <http://localhost:8080/swagger-ui/index.html>

빌드와 통합 테스트를 실행하려면 `server/`에서 다음 명령을 실행한다. Docker가 필요하다.

```bash
./gradlew build
```

프로필, 환경변수, 로컬 서비스, API 사용법은 [서버 개발 안내](server/README.md)에 있다.

## 저장소 안내

- `Lookation/`: 동결된 Eclipse 기반 레거시 웹 앱
- `server/`: Kotlin·Spring Boot 신규 백엔드
- `docs/adr/`: 아키텍처 결정 기록
- `docs/architecture.md`: 레거시 구조와 요청 흐름
- `docs/rewrite/parity-checklist.md`: 레거시 기능과 신규 서버 대응 현황

## 기여와 문의

질문과 작업 제안은 [GitHub Issues](https://github.com/team-pfinder/Spring_Project/issues)에 등록한다.

## 2020년 팀 프로젝트 기록

[시연 영상](https://youtu.be/v0N15BSgZGU)

| 구성원 | 주요 기여 |
| --- | --- |
| 김승범 (NoPainNoDev) | 팀장, 일정·업무 관리, Spring·MyBatis 구성, 회원·공통 기능, 예약 패키지 |
| 김일웅 (ILWOONGKIM) | 문서 정리, 공간 등록·수정 |
| 권소윤 (helloSyun) | 신고·콘텐츠 관리, 정책, 검색 |
| 조윤상 (YunsandCho) | 마일리지·마이페이지·예약, 지도 검색 |
| 안혜지 (Ahn-hj) | 공지·도움말, 공간 검수, 리뷰·Q&A 관리 |
| 진영은 (yyeejj) | 예약·신고 처리, 공간 상세, 메신저 |
