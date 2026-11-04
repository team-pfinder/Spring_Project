# MyBatis 대신 jOOQ를 쓴다

원 요구는 "MyBatis를 효율적으로"였으나, 재작성 시점에서 SQL을 컴파일 타임에 검증하는 편이 런타임 오류 위험을 크게 줄인다. jOOQ는 Kotlin DSL과 잘 맞고, XML·resultMap·`selectKey`가 모두 필요 없어진다.

- 버전: Spring Boot 4.1이 관리하는 jOOQ 3.20을 고정한다(별도 최신 3.21.x를 쓰지 않는다).
- 코드 생성: Flyway 마이그레이션 SQL을 jOOQ `DDLDatabase`로 읽어 빌드 시 생성한다. 도커 없이 컴파일된다.
- 구조: `Controller → Service(@Transactional) → Repository(jOOQ)`. Repository 밖으로 jOOQ Record를 노출하지 않는다.
- 대안: MyBatis 유지(원 요구), JPA. MyBatis는 SQL 오류가 런타임에만 드러나고, JPA는 이 스키마의 조회 패턴에 맞지 않아 기각.
- 결과: jOOQ OSS 에디션은 PostgreSQL을 지원하므로 라이선스 문제가 없다.
