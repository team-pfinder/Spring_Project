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
<title>sendConfirmAccount(user).jsp</title>
</head>
<body>

<div>
	<p>이용자 가입 이메일 인증</p>	<!-- 큰글씨 -->
</div>

<div>
	<p>
		xxxx@xxxx.xxx 로 가입 인증 이메일을 보내드렸습니다.<br>
		이메일의 링크를 클릭하시면 회원가입이 완료 됩니다.
	</p>
	<p><button>홈으로 가기</button></p>
</div>

</body>
</html>