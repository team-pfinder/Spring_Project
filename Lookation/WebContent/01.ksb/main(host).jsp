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
<title>main(host).jsp</title>
</head>
<body>
	
	<!-- 공간 등록 -->
	<div>
		<img src="<%=cp %>/images/logo.png" height="100px"/>
		<p>지금 바로 공간을 공유해보세요!</p>	<!-- 큰폰트 -->
		<p>파티룸, 브라이덜샤워, 엠티장소 등 다양한 모임공간을 룩케이션을 통해 공유해보세요!</p> <!-- 중간폰트, 회색폰트 -->
		<button>공간 등록하기</button> <!-- 보라색버튼, 흰색폰트 -->
		<p>궁금한 점이 있으신가요? <a href="faq">FAQ 바로가기</a></p> <!-- 작은폰트, 회색폰트 -->
	</div>
	
</body>
</html>