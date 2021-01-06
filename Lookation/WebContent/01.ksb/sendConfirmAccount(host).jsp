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
<%@ include file="../includes/includes_home.jsp" %>
<%@ include file="../includes/defaults.jsp" %>
<%@ include file="head(host).jsp"%>
<title>createAccountSuccess(host).jsp</title>

<style type="text/css">
	*
	{
		font-family : inherit;
	}
	
	.back-default 
	{
		background-color: #f6f6f6;
	}
	
	.head
	{
		text-align:center;
		padding-top: 5%;
		padding-bottom: 1%;
	}
	
	.confirmBox
	{
	    width: 900px;
	    
	    margin: 0 auto; 
		float: none;
		padding: 32px;
		text-align: center;
	}
	
	.buttonForm
	{
		width:70%;
		margin: 0 auto; 
		float: none;
		margin-bottom: 60px; 
	}
	
	button
	{
		border-style: ridge;
		border-width: thin;
		border-color: silver;
		font-size: 17px;
		font-weight: bold;
		color: black;
		background: #fdbe34;
		width:100%;
		height: 60px;
	}
	
	p
	{
		font-size: 120%;
		font-weight: 500;
	}
	
</style>
</head>

<body class="back-default">

	<div class="head">
		<h1 style="font-weight:1000;">호스트 가입 이메일 인증</h1>
	</div>

	<div class = "container">
		<div class = "confirmBox">			
			<p><span>sb92120@gmail.com</span> 로 가입 인증 이메일을 보내드렸습니다.</p> 
			<p>이메일의 링크를 클릭하시면 회원가입이 완료 됩니다.</p> 
			<hr style="border: solid thin silver;
			           margin: 50px 0px 35px 0px;">
			
			<div class="buttonForm">
				<button style="border-width: 0px;">홈으로</button> 
			</div>
		</div>
	</div>

	<%@ include file="foot.jsp"%>
	<%@ include file="../includes/includes_home_end.jsp"%>
</body>
</html>