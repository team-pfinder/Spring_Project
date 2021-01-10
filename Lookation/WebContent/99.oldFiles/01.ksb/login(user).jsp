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
<title>login(user).jsp</title>

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
	
	.loginBox
	{
	    width: 648px;
	    background-color: #ffffff;
	    
	    margin: 0 auto; 
		float: none;
		padding: 32px;
	}
	
	.login_info
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
		margin-bottom: 15px;
	}
	
	input.err
	{
		width:100%;
		border-color: red;
	}
	
	.err 
	{
	    display: display;
		font-size: 14px;
		text-align: left;
		color: red;
		margin-top: 5px;
		margin-bottom: 5px;
	}
	
	.pass
	{
		display: none;
	}
	
	button
	{
		width:100%;
		height: 52px;
	}
	
</style>

<script type="text/javascript">
	$(function()
	{
		// 최소 8자 최대 12자, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자
		var regPw = /^.*(?=^.{8,12}$)(?=.*\d)(?=.*[a-z])(?=.*[!@#$]).*$/;
		// 이메일 정규식
		var regEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		
		// 이메일 정규식 검사(입력시마다)
		$("#email").keyup(function()
		{
			if(!regEmail.test($(this).val()))
			{	
				$(this).removeClass("full");
				$(this).addClass("err");
				$("p#email").attr("class", "err");
			}
			else
			{
				$(this).removeClass("err");
				$(this).addClass("full");
				$("p#email").attr("class", "pass");	
			}
		});
		
		// 비밀번호 정규식 검사(입력시마다)
		$("#pw").keyup(function()
		{
			if(!regPw.test($(this).val()))
			{	
				$(this).removeClass("full");
				$(this).addClass("err");
				$("p#pw").attr("class", "err");
			}
			else
			{
				$(this).removeClass("err");
				$(this).addClass("full");
				$("p#pw").attr("class", "pass");	
			}
		});
		
		// submit
		$("#submit").click( function()
		{
			// 형식이 맞지 않을경우
			if(!regEmail.test($("#email").val()) || !regPw.test($("#pw").val()))
			{
				alert("입력된 형식이 알맞지 않습니다. 다시 입력해주세요.");
				return;
			}
			
			$("#").submit();
		});
		
		// cancel
		$("#cancel").click( function()
		{
			alert("cancel");
		});
	});
</script>

</head>

<body class="back-default">

	<div class="head">
		<h1 style="font-weight:1000;">이용자 로그인</h1>
	</div>
	
	<div class="container" >
		<div class="loginBox">		
				<ul class="login_info">
					<li>
						<!-- err시 빨간 textbox border 테두리 필요 -->
						<input type="text" class="form-control full" id="email" placeholder="이메일">
						<p class="pass" id="email">이메일 형식이 알맞지 않습니다.</p>
					</li>
					<li>
						<input type="password" class="form-control full" id="pw" placeholder="비밀번호">
						<p class="pass" id="pw">
							비밀번호 형식에 알맞지 않습니다.<br> 비밀번호는 숫자, 특수문자(!,@,#,$) 포함 최소 8자, 최대
							12자 사이입니다.
						</p>
					</li>
				</ul>
				<div class="memory_box">
					<input type="checkbox" id="memory"/>
					<label for="memory">로그인 기억하기</label>
					<a href="findPassword" style="float:right; font-weight:bold;"> 비밀번호 찾기</a>
				</div>
				
				<div style="margin-top:15px;">
					<button type="button" class="btn btn-primary full" id="submit">
						<span style="color:black; font-weight: bold;">이메일로 로그인</span>
					</button>
					<p style="text-align:center; padding-top: 15px;">
						아직 룩케이션 회원이 아니신가요? <a href="createAccount" style="font-weight:0">회원가입</a>
					</p>
				</div>
		</div>
	</div>

</body>
</html>