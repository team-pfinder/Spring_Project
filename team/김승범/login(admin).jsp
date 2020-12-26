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
<title>login(admin).jsp</title>
</head>
<body>

	<div>
		<p>관리자 로그인</p> <!-- 큰폰트 -->
	</div>

	<div>
		<table>
			<tr>
				<th colspan="2"><input type="text" placeholder="관리자 아이디"/></th>
			</tr>
			<tr>
				<th colspan="2"><input type="password" placeholder="관리자 비밀번호"/></th>
			</tr>
			
			<tr>
				<th colspan="2"><button>로그인</button></th> <!-- textbox 크기에 맞게, 보라색버튼, 흰색폰트 -->
			</tr>
			
		</table>
	</div>
</body>
</html>