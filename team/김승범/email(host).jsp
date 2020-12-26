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
<title>email(user).jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

	<!-- 로고 -->
	<div>
		<img src="<%=cp %>/images/logoHost.png" />
		<hr>
	</div>
	<!--
		#참고 소스
		jqueryApp01/jqTest01.html
	-->
	<div>
		<p>Lookation 호스트센터</p>	<!-- 보라색뒷배경, 중간크기폰트, Lookation(흰색폰트), 호스트센터(노란색폰트) -->
	</div>

	<!-- 분류에 따른 문서 양식이 오는 자리 -->
	<div>
		<p>분류에 따른 문서 양식이 오는 자리</p>
	</div>
	
	<!-- 끝 -->
	<hr>
	<div>
		<p>본 메일은 발신 전용 입니다. 이용 관련 제안사항은 <a href="faq">FAQ</a>를 이용해주세요.</p>
		<p><button>이용약관</button> | <button>개인정보처리방침</button></p>
		<p>Copyright ⓒ Pfinder Corp. All Rights Reserved.</p>
	</div>

</body>
</html>