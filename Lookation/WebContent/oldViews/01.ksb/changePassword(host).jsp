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

<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<title>changePassword(host).jsp</title>

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
	
	.changeBox
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

<script type="text/javascript">
	$(function()
	{
		// 최소 8자 최대 12자, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자
		var reg = /^.*(?=^.{8,12}$)(?=.*\d)(?=.*[a-z])(?=.*[!@#$]).*$/;
		
		// 새 비밀번호 정규식 검사(입력시마다)
		$("#pw_new").keyup(function()
		{
			if(!reg.test($(this).val()))
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
		
		// submit
		$("#submit").click( function()
		{
			// 형식이 맞지 않거나
			// 새로운 비밀번호 재입력과 동일하지 않는 경우
			if(!reg.test($("#pw_new").val())
			     || $("#pw_new_certain").val() != $("#pw_new").val() )
			{
				alert("새 비밀번호의 형식이 맞지 않거나, 서로 일치하지 않습니다.");
				return;
			}
			
			$("#changePasswordForm").submit();
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
		<h1 style="font-weight:1000;">호스트 비밀번호 변경하기</h1> 
	</div>

	<form action="#.jsp" method="post" id="changePasswordForm">
		<div class="container">
			<div class="changeBox">
				<ul class="pw_info">
					<li>
						<p>새 비밀번호</p> <input type="password" class="form-control full"
						id="pw_new">
						<p class="pass" id="pw_new">
							비밀번호 형식에 알맞지 않습니다.<br> 비밀번호는 숫자, 특수문자(!,@,#,$) 포함 최소 8자, 최대
							12자 사이입니다.
						</p>
					</li>
					<li>
						<p>새 비밀번호 재입력</p> <input type="password" class="form-control full"
						id="pw_new_certain">
						<p class="pass" id="pw_new_certain">새 비밀번호와 같지 않습니다.</p>
					</li>
				</ul>


				<div style="margin-top: 30px;">
					<button type="button" class="full" style="background: #ffffff;" id="cancel">
						<span style="color: black;">취소</span>
					</button>
					<button type="button" class="full" id="submit"
						style="float: right; border-width: 0px;">
						<span style="color: black;">변경하기</span>
					</button>
				</div>
			</div>
		</div>
	</form>

</body>
</html>