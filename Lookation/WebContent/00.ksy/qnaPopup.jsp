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
		<p id="p2">2021/01/04</p>
		<hr>
	</div>
	
	<div id="content">
		<span>qna 전문보기...근데 이거 게시판번호 들고와야하는데..
		가능한감</span>
	</div>
</div>


</body>
</html>