# 패키지는 레이어 우선으로 나눈다

기존 `com.lookation.<feature>.{api, application, domain, infrastructure}`를 레이어 최상위 구조로 바꾼다.

```
com.lookation
├── controller
├── service
├── repository
├── domain
├── dto
└── global/{security, error, mail, batch, config, crypto, file}
```

- 기능별 하위 패키지는 두지 않는다. 대부분의 기능이 단일 파일이라 계층 안에서 평면으로 두고 클래스 이름으로 구분한다.
- 요청은 `controller`의 `XxxForm`, 응답은 `dto`의 `XxxResponse`.
- Repository는 인터페이스 없이 jOOQ 구현 클래스 하나(`repository`).
- 계층을 가로지르는 통합 테스트는 `com.lookation.integration`에 둔다. 단위 테스트가 생기면 해당 레이어 패키지를 미러링한다.
- 대가: `service`가 `controller`의 Form을 인자로 받는다(계층 역방향 의존). 웹 외 어댑터가 생기면 Form을 `dto`로 옮기고 command로 변환한다.
- 기존 feature-first 결정(ADR 0002)의 패키지 문구는 이 결정으로 대체한다.
