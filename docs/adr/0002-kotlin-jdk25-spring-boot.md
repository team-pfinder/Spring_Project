# Kotlin 100% · JDK 25 LTS · Spring Boot 4.1

신규 백엔드는 Kotlin 100%로 작성하고 JDK 25 LTS, Spring Boot 4.1.x, Gradle Kotlin DSL을 쓴다. Spring Boot 4.1은 Kotlin 2.3을 지원하고, JDK 25는 JEP 491로 가상 스레드의 `synchronized` pinning 문제를 해소한다.

- 가상 스레드: `spring.threads.virtual.enabled=true`. 블로킹 I/O 위주(JDBC) 애플리케이션이라 플랫폼 스레드 풀 크기 조정 없이 처리량을 얻는다.
- 대안: Java 25 단독, Kotlin/Java 혼용. 혼용은 Java 8→25 문법 차이 때문에 리뷰 비용만 늘어 기각.
- 빌드: 단일 Gradle 모듈, package-by-feature. 도메인 경계가 안정화되면 멀티모듈 분리를 재검토한다.
