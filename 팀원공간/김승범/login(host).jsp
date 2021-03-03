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
<title>login(host).jsp</title>
</head>
<body>

	<div>
		<p>호스트 로그인</p> <!-- 큰폰트 -->
	</div>
	
	<div>
		<table>
			<tr>
				<th colspan="2"><input type="text" placeholder="이메일"/></th>
			</tr>
			<tr>
				<th colspan="2"><input type="password" placeholder="비밀번호"/></th>
			</tr>
			
			<tr>
				<th>
					<input type="checkbox"/><label>로그인 기억하기</label>	<!-- 왼쪽정렬 -->
				</th>
				<th><a href="findPassword"> 비밀번호 찾기</a></th> 			<!-- 오른쪽정렬 -->
			</tr>
			
			<tr>
				<th colspan="2"><button>로그인</button></th> <!-- textbox 크기에 맞게, 보라색버튼, 흰색폰트 -->
			</tr>
			
			<tr>
				<!-- 작은크기폰트, 회색폰트 -->
				<td colspan="2">
					아직 룩케이션 회원이 아니신가요? <a href="createAccount">회원가입</a>
				</td> 
			</tr>
		</table>
	</div>
</body>
</html>