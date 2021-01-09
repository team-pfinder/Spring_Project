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
<title>signUp(user).jsp</title>

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
		padding-top: 3%;
		padding-bottom: 1%;
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
		margin-top: -10px;
		margin-bottom: 15px;
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
	
	p.normal
	{
		font-weight: bold;
	}
	
</style>

<script type="text/javascript">
	$(function()
	{
		// 최소 8자 최대 12자, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자
		var regPw = /^.*(?=^.{8,12}$)(?=.*\d)(?=.*[a-z])(?=.*[!@#$]).*$/;
		// 이메일 정규식
		var regEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		// 전화번호 정규식
		var regTel = /^\d{2,3}-\d{3,4}-\d{4}$/;
		// 닉네임 정규식
		var regNick = /^[가-힣]{2,6}$/;
		
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
		
		// 새 비밀번호 정규식 검사(입력시마다)
		$("#pw_new").keyup(function()
		{
			if(!regPw.test($(this).val()))
			{	
				$(this).removeClass("full");
				$(this).addClass("err");
				$("p#pw_new").attr("class", "err");
			}
			else
			{
				$(this).removeClass("err");
				$(this).addClass("full");
				$("p#pw_new").attr("class", "pass");	
			}
		});
		
		// 재입력 비밀번호 새 비밀번호와 일치여부 검사(입력시마다)
		$("#pw_new_certain").keyup(function()
		{
			if($(this).val() != $("#pw_new").val())
			{
				$(this).removeClass("full");
				$(this).addClass("err");
				$("p#pw_new_certain").attr("class", "err");
			}
			else
			{
				$(this).removeClass("err");
				$(this).addClass("full");
				$("p#pw_new_certain").attr("class", "pass");	
			}
		});
		
		// 닉네임 정규식 검사(입력시마다)
		$("#nick").keyup(function()
		{
			if(!regNick.test($(this).val()))
			{	
				$(this).removeClass("full");
				$(this).addClass("err");
				$("p#nick").attr("class", "err");
			}
			else
			{
				$(this).removeClass("err");
				$(this).addClass("full");
				$("p#nick").attr("class", "pass");	
			}
		});
		
		// 전화번호 정규식 검사(입력시마다)
		$("#tel").keyup(function()
		{
			if(!regTel.test($(this).val()))
			{	
				$(this).removeClass("full");
				$(this).addClass("err");
				$("p#tel").attr("class", "err");
			}
			else
			{
				$(this).removeClass("err");
				$(this).addClass("full");
				$("p#tel").attr("class", "pass");	
			}
		});
		
		
		
		
		// submit
		$("#submit").click( function()
		{
			// 이메일 형식이 맞지 않을경우
			if(!regEmail.test($("#email").val()))
			{
				alert("이메일 형식이 알맞지 않습니다. 다시 입력해주세요.");
				return;
			}
			
			// 비밀번호 형식이 맞지 않거나
			// 새로운 비밀번호 재입력과 동일하지 않는 경우
			if(!regPw.test($("#pw_new").val())
			     || $("#pw_new_certain").val() != $("#pw_new").val() )
			{
				alert("새 비밀번호의 형식이 맞지 않거나, 서로 일치하지 않습니다.");
				return;
			}
			
			// 닉네임 형식이 맞지 않을경우
			if(!regNick.test($("#nick").val()))
			{
				alert("닉네임 형식이 알맞지 않습니다. 다시 입력해주세요.");
				return;
			}
			
			// 연락처 형식이 맞지 않을경우
			if(!regTel.test($("#nick").val()))
			{
				alert("연락처 형식이 알맞지 않습니다. 다시 입력해주세요.");
				return;
			}
			
			/* 이메일 중복검사 */
			/* 닉네임 중복검사 */
			
			
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
		<h1 style="font-weight:1000;">이용자 회원가입</h1>
	</div>
	
	<div class="container" >
		<div class="loginBox">		
				<ul class="login_info">
					<li>
						<!-- err시 빨간 textbox border 테두리 필요 -->
						<p class="normal">이메일</p> 
						<input type="text" class="form-control full" id="email">
						<p class="pass" id="email">이메일 형식이 알맞지 않습니다.</p>
					</li>
					<li>
						<p class="normal">비밀번호</p> 
						<input type="password" class="form-control full"
						id="pw_new">
						<p class="pass" id="pw_new">
							비밀번호 형식에 알맞지 않습니다.<br>
							비밀번호는 숫자, 특수문자(!,@,#,$) 포함 최소 8자, 최대 12자 사이입니다.
						</p>
					</li>
					<li>
						<p class="normal">비밀번호 재입력</p> 
						<input type="password" class="form-control full"
						id="pw_new_certain">
						<p class="pass" id="pw_new_certain">새 비밀번호와 같지 않습니다.</p>
					</li>
					<li><hr></li>
					<li>
						<p class="normal">닉네임</p> 
						<input type="text" class="form-control full"
						id="nick">
						<p class="pass" id="nick">
							닉네임 형식이 알맞지 않습니다.<br>
							닉네임은 한글로 최소 2자, 최대 6자 사이입니다.
						</p>
					</li>
					<li>
						<p class="normal">이름</p> 
						<input type="text" class="form-control full"
						id="name">
					</li>
					<li>
						<p class="normal">연락처</p> 
						<input type="text" class="form-control full"
						id="tel">
						<p class="pass" id="tel">연락처 형식이 알맞지 않습니다.</p>
					</li>
				</ul>
				
				<div style="margin-top:35px;">
					<button type="button" class="btn btn-primary full" id="submit">
						<span style="color:black; font-weight: bold;">회원가입</span>
					</button>
				</div>
		</div>
	</div>

</body>
</html>