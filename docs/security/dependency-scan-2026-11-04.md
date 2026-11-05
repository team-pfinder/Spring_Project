# 의존성 CVE 스캔 — 2026-11-04

`server/`(Kotlin 재작성본) 기준. 레거시 `Lookation/`은 범위 밖이다.

## 결론

**취약점 0건.** OSV·Aqua(trivy)·Anchore(grype) 3개 DB 교차 확인. 즉시 수정·다음 릴리스·모니터링 대상 없음.

|구분|건수|
|---|---|
|Critical|0|
|High|0|
|Medium|0|
|Low|0|
|직접 의존성 취약|0|

스캔한 Maven 컴포넌트 154개(배포 산출물 `lookation-server-0.0.1-SNAPSHOT.jar` 전체 클래스패스). 동일 `group:artifact` 다중 버전 충돌 0건.

## 대상

|항목|값|
|---|---|
|모듈|`server/`|
|스택|Kotlin 2.3.21, Spring Boot 4.1.2, Spring Security 7, jOOQ 3.21.9, Flyway 12.4.0, JDK 25|
|스캔 입력|`server/build/libs/lookation-server-0.0.1-SNAPSHOT.jar` (70 MB boot jar)|
|jar SHA-256 앞 16자|`4b0fc37bb27d26c1`|
|jar 빌드 시각|2026-11-04 15:02|
|컴포넌트|154 (`pkg:maven/*`). SBOM의 루트 jar 항목 1개는 스캔 대상 아님|

**Gradle lockfile이 없어서** 소스 디렉터리 스캔은 의존성을 하나도 못 본다. 실제 배포 클래스패스를 담은 boot jar에서 SBOM을 뽑아 스캔했다.

## 도구 실행 결과

|도구|버전|입력|결과|비고|
|---|---|---|---|---|
|syft|1.51.1|boot jar|CycloneDX 154 컴포넌트|SBOM 생성|
|osv-scanner|2.7.0|SBOM|취약점 0|OSV 온라인 조회|
|trivy|0.76.1|SBOM|취약점 0|Aqua DB 2026-11-04 10:43|
|grype|0.121.0|SBOM|취약점 0|Anchore DB v6.1.10, 2026-11-03|

실패로 처리한 실행:

|도구|명령|이유|
|---|---|---|
|osv-scanner|`scan source -r server/`|`No package sources found` — lockfile 없음|
|trivy|`trivy fs server/`|`Number of language-specific files num=0`|
|trivy|`trivy fs <boot.jar>`|boot jar 내부 `BOOT-INF/lib/*.jar`을 읽지 못함(`META-INF/maven` 부재)|

위 세 실행의 0건은 "취약점 없음"이 아니라 **"스캔 대상 0개"**다. 결과는 SBOM 경유 실행만 신뢰한다.

## 검증 (양성 대조)

0건이 스캐너 오작동이 아님을 확인하려고, 알려진 취약 패키지 2개(`log4j-core@2.14.1`, `spring-core@5.3.0`)로 만든 SBOM을 같은 경로로 스캔했다.

|도구|검출 건수|
|---|---|
|osv-scanner|12|
|grype|12|
|trivy|12|

세 도구 모두 검출한다. 따라서 위 0건은 실제 0건이다.

## 한계와 리스크

1. **lockfile 부재** — Gradle dependency locking이 꺼져 있어(`build.gradle.kts`에 `dependencyLocking` 없음) 소스 기반 스캐너가 무력하다. CI에서 스캔하려면 `gradle.lockfile`을 커밋하거나 boot jar를 산출물로 넘겨야 한다.
2. **trivy의 boot jar 미지원** — trivy로 돌리려면 반드시 SBOM을 경유해야 한다. 소스만 넘기면 0건이 나오고 그건 오탐이다.
3. **테스트 의존성 제외** — `testImplementation`(Testcontainers 2.0.5 등)은 배포 산출물에 없어 스캔에서 빠졌다. 배포 취약점 판정에는 영향 없음.
4. **레거시 미포함** — `Lookation/WebContent/WEB-INF/lib` jar 26개와 vendor JS는 이 문서 범위 밖이다.

## 재현

```bash
# 1. 배포 산출물 기준 SBOM
syft server/build/libs/lookation-server-0.0.1-SNAPSHOT.jar \
  -o cyclonedx-json=.security/sbom.cdx.json

# 2. 3개 DB 교차 스캔
osv-scanner scan -L .security/sbom.cdx.json --format json \
  --output-file .security/osv-sbom.json
trivy sbom --scanners vuln --format json \
  --output .security/trivy-sbom.json .security/sbom.cdx.json
grype sbom:.security/sbom.cdx.json -o json --file .security/grype.json
```

원본 산출물은 `.security/`(gitignore 대상)에 남는다. 요약은 `.security/merged.json`.

## 다음 점검

`build.gradle.kts`의 의존성 버전이나 Spring Boot BOM이 바뀌면 재실행한다. CI에 넣을 경우 boot jar 빌드 이후 단계에서 위 3개 명령을 돌리고, exit code가 아니라 JSON 결과의 findings 수로 판정한다(도구별 exit code 정책이 다르다).
