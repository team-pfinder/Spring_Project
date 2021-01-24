<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String identify = (String)request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
	
	String requestUrl = (String)request.getSession().getAttribute("requestUrl");
	String beforeUrl = (String)request.getSession().getAttribute("beforeUrl");
	
	pageContext.setAttribute("requestUrl", requestUrl);
	pageContext.setAttribute("beforeUrl", beforeUrl);
	
	// 인증 결과 여부
	// 이전 페이지에서 로그인 실패시 GET을 통해 들어온다.
	String result = request.getParameter("result");
	pageContext.setAttribute("result", result);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>
<title>confirmPassword.jsp</title>

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
		var result = "<c:out value='${result}'/>";
		
		if(result=='fail')
			alert("인증 실패!\n잘못된 패스워드입니다.");
		
		// 최소 8자 최대 12자, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자
		var reg = /^.*(?=^.{8,12}$)(?=.*\d)(?=.*[a-z])(?=.*[!@#$]).*$/;
		
		// 비밀번호 정규식 검사(입력시마다)
		$("#pw").keyup(function()
		{
			if(!reg.test($(this).val()))
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
			if(!reg.test($("#pw").val()))
			{
				alert("비밀번호의 형식이 알맞지 않습니다.");
				return;
			}
			
			$("#confirmPwForm").submit();
		});
	});
</script>

</head>
<body class="back-default">

	<div class="head">
		<c:if test="${identify eq 'host'}">
			<h1 style="font-weight:1000;">호스트 비밀번호 확인</h1> 
		</c:if>
		<c:if test="${identify eq 'member'}">
			<h1 style="font-weight:1000;">이용자 비밀번호 확인</h1> 
		</c:if>
	</div>
	
	<div class="container" >
		<div class="confirmBox">
			<form action="confirmpassword.action?identify=${identify}" method="post" id="confirmPwForm">	
				<ul class="pw_info">
					<li>
						<p style="font-size: 16px; font-weight: bold;">인증을 위해 비밀번호를 입력하세요.</p>
						<input type="password" class="form-control full" id="pw" name="pw">
						<p class="pass" id="pw">
							비밀번호 형식에 알맞지 않습니다.<br> 비밀번호는 숫자, 특수문자(!,@,#,$) 포함 최소 8자, 최대
							12자 사이입니다.
						</p>
					</li>
				</ul>
				<input type="hidden" id="requestUrl" name="requestUrl" value="${requestUrl }"> 
			</form>		
				
				<div style="margin-top:30px;">
					<c:if test="${identify eq 'host'}">
						<button type="button" class="full" style="background: #ffffff;" id="cancel"
							onclick="location.href='${beforeUrl }'">
								<span style="color: black;">취소</span>
							</button> 
					</c:if>
					<c:if test="${identify eq 'member'}">
						<button type="button" class="full" style="background: #ffffff;" id="cancel"
					        onclick="location.href='${beforeUrl }'">
					        	<span style="color: black;">취소</span>
					        </button> 
					</c:if> 
					<button type="button" class="full" id="submit"
						style="float: right; border-width: 0px;">
						<span style="color: black;">확인</span>
					</button>
				</div>
		</div>
	</div>
	
</body>
</html>