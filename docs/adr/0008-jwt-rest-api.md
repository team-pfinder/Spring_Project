# REST API + JWT, 세션 제거

레거시는 액션 46곳에 세션 검사를 복붙했고 필터·인터셉터가 없어 권한 우회가 가능하다. 신규 백엔드는 `/api/v1` REST API만 제공하고 JWT로 인증한다.

- access 15분, refresh 14일. refresh 토큰은 DB에 저장하고 rotation하며, 재사용 탐지 시 해당 계정의 토큰을 전부 폐기한다.
- Spring Security 7 메서드 보안으로 역할을 검사한다. 세션 기반 뷰(JSP)는 없앤다.
- 요청은 `XxxForm`, 응답은 `XxxDto`로 분리한다. 오류는 RFC 9457 `ProblemDetail`, 문서는 springdoc-openapi.
- 목록은 keyset 페이징을 기본으로 한다.
- 대안: 세션 유지 + 외부 API만 JWT. 동일 출처 앱에는 세션이 더 단순하지만, 프론트를 나중에 별도로 붙이는 계획과 맞지 않아 기각.
