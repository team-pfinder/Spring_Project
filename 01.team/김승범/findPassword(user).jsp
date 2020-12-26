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
<title>findPassword(user).jsp</title>
</head>
<body>

	<div>
		<p>이용자 비밀번호 찾기</p> <!-- 큰폰트 -->
	</div>

	<table>
		<tr>
			<td><input type="text" placeholder="이메일"/></td>
		</tr>
		
		<tr>
			<td>
				가입시 사용한 이메일 주소를 입력해주시면 비밀번호 재설정 링크를 보내드립니다. <!-- 매우작은폰트 -->
			</td>
		</tr>
		
		<tr>
			<td>
				<button>비밀번호 재설정 링크 보내기</button> <!-- 작은폰트, 노란버튼 -->
			</td>
		</tr>
	</table>

</body>
</html>