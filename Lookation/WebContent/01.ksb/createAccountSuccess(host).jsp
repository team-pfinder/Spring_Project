<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>createAccountSuccess(host).jsp</title>


</head>
<body>


	<div>
		<p>호스트 회원가입이 완료되었습니다!</p> <!-- 큰폰트 -->
		<p>서비스 이용을 위해 다시 로그인 해주세요.</p> <!-- 중간폰트 -->
		<button>홈으로 가기</button> <!-- 보라색 흰색폰트 -->
		<button type="button" class="btn btn-primary btn-lg">Large button</button>
	</div>

</body>
</html>