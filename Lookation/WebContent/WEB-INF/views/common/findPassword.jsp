<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String identify = request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>
<title>findPassword.jsp</title>

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
		padding-bottom: 2%;
	}
	
	.inputBox
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
</style>

<script type="text/javascript">
	$(function()
	{
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
			
			findEmailAjax();
		});
		
		// submit
		$("#submit").click( function()
		{
			// 형식이 맞지 않을경우
			if(!regEmail.test($("#email").val()))
			{
				alert("이메일 형식이 알맞지 않습니다. 다시 입력해주세요.");
				return;
			}
			
			// (이메일이 데이터베이스에 없는 경우 발송 불가)
			if(str.trim() == "존재하지 않는 이메일")
			{
				alert("존재하지 않는 이메일 입니다.");
				return;
			}
			
			$("#sendFindPasswordForm").submit();
		});
	});
	
	var str;
	function findEmailAjax()
	{
		var vIdentify = "<c:out value='${identify}'/>";
		$.post("ajaxfindemail.action"
				, {
			         identify : vIdentify
			       , email : $("input#email").val()
			      }
			    , function(data)
			      {
					  str = data;
			      });
	}
</script>

</head>

<body class="back-default">

	<div>
		<c:if test="${identify eq 'host' }">
       		<c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=noNick.jsp"></c:import>
        </c:if>
        <c:if test="${identify eq 'member' }">
       		<c:import url="${cp}/includes/header_user.jsp?result=${result }&nick=noNick.jsp"></c:import>
        </c:if>
    </div>

	<div class="head">
		<c:if test="${identify eq 'host' }">
			<h1 style="font-weight:1000;">호스트 비밀번호 찾기</h1>
		</c:if>
		<c:if test="${identify eq 'member' }">
			<h1 style="font-weight:1000;">이용자 비밀번호 찾기</h1>
		</c:if>
	</div>
	
	<div class="inputBox" >
		<div class="loginBox">	
			<form action="sendconfirmemail.action?identify=${identify }" 
			method="post" id = "sendFindPasswordForm">
				<ul class="login_info">
					<li>
						<!-- err시 빨간 textbox border 테두리 필요 -->
						<input type="text" class="form-control full" id="email" name="email"
						placeholder="이메일">
						<p class="pass" id="email">이메일 형식이 알맞지 않습니다.</p>
					</li>
					<li>
						<p style="font-size:12px">
							가입시 사용한 이메일 주소를 입력해주시면 비밀번호 재설정 링크를 보내드립니다.<br>
							이메일 주소를 모르시는 경우 비밀번호 찾기가 불가합니다.
						</p>
					</li>
				</ul>
			</form>	
			
				<div style="margin-top:15px;">
					<button type="button" class="btn btn-primary full" id="submit"
					style="margin-bottom: 0px;">
						<span style="color:black; font-weight: bold;">비밀번호 재설정 링크 보내기</span>
					</button>
				</div>
		</div>
	</div>
	
	<div class="fixed-bottom">
		<c:if test="${identify eq 'host' }">
			<c:import url="${cp}/includes/footer_host.jsp"></c:import>
		</c:if>
		<c:if test="${identify eq 'member' }">
			<c:import url="${cp}/includes/footer_user.jsp"></c:import>
		</c:if>
        
        <c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
    </div>
</body>
</html>

