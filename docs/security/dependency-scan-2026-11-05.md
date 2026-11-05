# 의존성 CVE 스캔 — 2026-11-05

`server/`(Kotlin 재작성본) 기준. 레거시 `Lookation/`은 범위 밖이다. AWS SDK BOM `2.57.8` 적용 후 boot JAR을 검사했다.

## 결론

**취약점 0건.** OSV, Aqua(Trivy), Anchore(Grype) 데이터베이스에서 교차 확인했다.

| 구분 | 건수 |
|---|---:|
| Critical | 0 |
| High | 0 |
| Medium | 0 |
| Low | 0 |
| 직접 의존성 취약 | 0 |

Syft SBOM은 155개 항목을 담고, 그중 Maven 구성요소는 154개다. OSV는 154개 패키지를 검사했다.

## 대상

| 항목 | 값 |
|---|---|
| 모듈 | `server/` |
| 스택 | Kotlin 2.3.21, Spring Boot 4.1.2, Spring Security 7.1.2, jOOQ 3.21.9, Flyway 12.4.0, AWS SDK 2.57.8, JDK 25 |
| 입력 | `server/build/libs/lookation-server-0.0.1-SNAPSHOT.jar` (67 MB) |
| SHA-256 | `427c8f604daa3786ffae50b50373ed25edff260ed6c52c447a73eb9f6ddabf59` |
| 검증 빌드 | `env -u SPRING_PROFILES_ACTIVE ./gradlew build` — 성공, 전체 테스트 포함 |

## 도구 실행 결과

| 도구 | 버전 | 입력 | 결과 | 데이터베이스 |
|---|---|---|---|---|
| Syft | 1.51.1 | boot JAR | SBOM 154 Maven 구성요소 | 해당 없음 |
| OSV-Scanner | 2.7.0 | SBOM | 취약점 0 | OSV 온라인 조회 |
| Trivy | 0.76.1 | SBOM | 취약점 0 | Aqua DB 갱신: 2026-11-05 01:19 UTC |
| Grype | 0.121.0 | SBOM | 취약점 0 | Anchore DB v6.1.10, 생성: 2026-11-04 06:20 UTC, 유효 |

Grype DB 갱신 확인 결과 새 데이터베이스가 없었고, 기존 DB는 유효했다. `.security/2026-11-05/`에 원본 SBOM과 각 도구 JSON을 보관했다. VEX 산출물은 생성하지 않았다.

## 범위와 한계

1. Gradle lockfile이 없어 소스 디렉터리 스캔은 정확한 클래스패스를 표현하지 못한다. 배포 산출물에서 SBOM을 만들고 검사했다.
2. Testcontainers 등 테스트 의존성은 boot JAR에 포함되지 않아 이번 배포 의존성 검사에서 제외했다.
3. 레거시 `Lookation/WebContent/WEB-INF/lib` JAR과 vendor JS는 범위 밖이다.

## 재현

```bash
syft server/build/libs/lookation-server-0.0.1-SNAPSHOT.jar \
  -o cyclonedx-json=.security/2026-11-05/sbom.cdx.json
osv-scanner scan -L .security/2026-11-05/sbom.cdx.json --format json \
  --output-file .security/2026-11-05/osv.json
trivy sbom --scanners vuln --format json \
  --output .security/2026-11-05/trivy.json .security/2026-11-05/sbom.cdx.json
grype sbom:.security/2026-11-05/sbom.cdx.json -o json \
  --file .security/2026-11-05/grype.json
```

다음 `server/` 의존성 또는 Spring Boot BOM 변경 때 다시 검사한다. 결과 JSON의 findings 수로 판정한다.
