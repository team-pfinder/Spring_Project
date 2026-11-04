# Oracle XE를 PostgreSQL로 교체한다

ERD를 새로 설계하고 이관할 운영 데이터가 없으므로 Oracle 유지 이유가 없다. PostgreSQL은 Testcontainers·Flyway·부분 인덱스·GiST 제약·`timestamptz`를 무료로 제공하고 로컬·CI 구동이 쉽다.

- 스키마는 Flyway 마이그레이션으로만 변경한다. 초기 스키마는 `V1__baseline.sql`부터 시작한다.
- 레거시의 PL/SQL 프로시저 25개, 함수(`F_CODE`, `F_USER_BALANCE`, `F_HOST_BALANCE`), 뷰 66개는 이관하지 않고 Kotlin Service로 재구현한다.
- 대안: Oracle XE 유지. ojdbc·구버전 XE 제약과 라이선스 부담을 그대로 안고 가야 해 기각.
- 결과: Oracle 전용 문법(`SYSDATE`, `NVL`, `ROWNUM`, `DUAL`, `selectKey`)은 전부 사라진다.
