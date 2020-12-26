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
<title>confirmPassword(user).jsp</title>
</head>
<body>

	<div>
		<p>이용자 비밀번호 확인</p>	<!-- 큰폰트 -->
	</div>
	
	<div>
		<table>
			<tr>
				<td>인증을 위해 비밀번호를 입력하세요.</td>	<!-- 중간폰트, 회색폰트 -->
			</tr>
			<tr>
				<td><input type="password" placeholder="비밀번호"></td>
			</tr>
			<tr>
				<td><button>확인</button></td>	<!-- text박스와 같은 크기로 -->
			</tr>
		</table>
	</div>
	
</body>
</html>