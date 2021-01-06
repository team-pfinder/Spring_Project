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
<title>Insert title here</title>
<style>
	#title #p1
	{
		font-weight: bold;
		font-size: 25pt;
	}
	#title #p2
	{
		text-align: right;
		font-size: 5pt;
		margin-top: -25px;
	}
</style>
</head>
<body>
<div>
	<div id="title">
		<p id="p1">전문보기</p>
		<p id="p2">2021/01/06</p>
		<hr>
	</div>
	
	<div id="content">
		<span>리뷰 전문, 사진도 함께 보이면 좋을거같은데...ㅎ</span>
	</div>
</div>


</body>
</html>