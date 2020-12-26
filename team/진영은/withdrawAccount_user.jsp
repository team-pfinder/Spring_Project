<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴완료(이용자).jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
	
	<!-- 작은 화면 -->
	
	<div>
		<!-- 체크그림 가운데정렬 -->
		<img alt="" src="">
		<h3>탈퇴하시겠습니까?</h3>
		<span>회원가입시 입력한 모든 개인정보와 예약내역, 작성글이 삭제되며, 이는 재가입하더라도 복구되지 않습니다.</span>
	</div>
	
	<div>
		<input type="button" value="예">
		<input type="button" value="아니오">
	</div>
	
	
</body>
</html>