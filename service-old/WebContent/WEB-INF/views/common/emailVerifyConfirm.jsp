<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>계정 인증</title></head>
<body>
<h1>계정 인증</h1>
<p>아래 버튼을 누르면 계정 생성이 완료됩니다.</p>
<form action="verifyemail.action" method="post">
<jsp:include page="/includes/csrf.jsp" />
<input type="hidden" name="token" value="${token}">
<button type="submit">인증 완료</button>
</form>
</body>
</html>
