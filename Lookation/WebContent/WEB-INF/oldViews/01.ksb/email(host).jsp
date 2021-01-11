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
<title>email(host).jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>

<style type="text/css">
	*
	{
		font-family : inherit;
	}
	
	.back-default 
	{
		background-color: #f6f6f6;
	}
	
	.email-form
	{
		width : 800px;
		float: none;
	    margin:  auto; 
	    text-align: center;
	}
	
	.content-form
	{
		width : 100%;
		background: #ffffff;
		padding-top: 50px;
		padding-bottom: 50px;
	}
	
	.brand
	{
		color: #000000;
		font-weight: 800;
		font-family: inherit;
	}
	
	.brand span
	{
	    color: #fdbe34
	}

	h2
	{
		font-weight: bold;
	}
	
	.padding-vertical
	{
		padding-top: 40px;
	}
	
	a.clickAble
	{
		text-decoration: underline;
		font-weight: bold;
		color: #fdbe34;
	}


</style>
</head>


<body class="back-default">

	<div class="email-form">

		<!-- 로고 -->
		<div class="padding-vertical" style="text-align: center">
			<h1 class="brand" style="font-size: 400%;">Look<span>ation.</span> <span style="color: black; font-size: 50%;">호스트센터</span></h1>
		</div>
		<hr>
		
		<!-- 내용이 입력되는 곳 -->
		<div class="content-form">
			<h1>내용이 입력될 곳</h1>
		</div>
		
		<hr>
		<div>
			<p>본 메일은 발신 전용 입니다. 이용 관련 제안사항은 <a class="clickAble" href="faq">FAQ</a>를 이용해주세요.</p>
			<p> <a class="clickAble" href="#">이용약관</a> | <a class="clickAble" href="#">개인정보처리방침</a></p>
			<p>Copyright ⓒ Pfinder Corp. All Rights Reserved.</p>
		</div>
	
	</div>
	
	<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</body>
</html>