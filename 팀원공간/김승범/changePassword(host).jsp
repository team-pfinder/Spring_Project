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
<title>changePassword(host).jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
	<div>
		<p>호스트 비밀번호 변경하기</p>				<!-- 큰폰트 -->
	</div>
	
	<div>
		<table>
			<tr>
				<td colspan="2">
					새 비밀번호<br>				<!-- 작은폰트 -->
					<input type="password" />	<!-- 세로로 큰 크기 -->
				</td>
			</tr>
			<tr>
				<td colspan="2">
					새 비밀번호 재입력<br>		<!-- 작은폰트 -->
					<input type="password" />	<!-- 세로로 큰 크기 -->
				</td>
			</tr>
			<tr><td></td></tr>		
			<tr>
				<!-- 두 버튼은 서로 같은 크기 -->
				<td><button>취소</button></td>		<!-- 작은폰트, 회색폰트, 흰버튼 -->
				<td><button>변경하기</button></td>	<!-- 작은폰트, 흰색폰트, 보라색버튼 -->
			</tr>	
		</table>
	</div>
</body>
</html>