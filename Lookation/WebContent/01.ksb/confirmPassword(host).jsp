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
<title>confirmPassword(host).jsp</title>

<style type="text/css">
	*
	{
		font-family : inherit;
		font-size: 15px;
	}
	
	.back-default 
	{
		background-color: #f6f6f6;
	}
	
	.head
	{
		text-align:center;
		padding-top: 5%;
		padding-bottom: 3%;
	}
	
	.container
	{
		padding: 150px 50px 100px;
		padding-top: 10px;
	}
	
	.confirmBox
	{
	    width: 648px;
	    background-color: #ffffff;
	    
	    margin: 0 auto; 
		float: none;
		padding: 32px;
	}
	
	.pw_info
	{
		padding-left: 0;
		padding-right: 0;
		list-style: none;
	}
	
	.memory_box
	{
	    text-align: left;
	    list-style: none;
	}
	
	input.full
	{
		width:100%;
	}

	.err 
	{
		font-size: 14px;
		text-align: left;
		color: red;
		margin-top: 5px;
		margin-bottom: 15px;
	}
	
	button
	{
		border-style: ridge;
		border-width: thin;
		border-color: silver;
		background: #fdbe34;
		width:49%;
		height: 50px;
	}
	
	.form-control
	{
	    font-size: 18px;
	}
	
</style>

</head>
<body class="back-default">

	<div class="head">
		<h1 style="font-weight:1000;">호스트 비밀번호 확인</h1> 
	</div>
	
	<div class="container" >
		<div class="confirmBox">		
				<ul class="pw_info">
					<li>
						<p style="font-size: 16px; font-weight: bold;">인증을 위해 비밀번호를 입력하세요.</p>
						<input type="password" class="form-control full" id="pw_new">
						<p class="err">비밀번호가 너무 짧습니다.</p>
					</li>
				</ul>
				
				
				<div style="margin-top:30px;">
					<button type="submit" class="full" style="background: #ffffff;">
						<span style="color:black;">취소</span>
					</button>
					<button type="submit" class="full" style="float: right; border-width: 0px;">
						<span style="color:black;">확인</span>
					</button>
				</div>
		</div>
	</div>
	
</body>
</html>