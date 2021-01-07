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
<title>findPassword(user).jsp</title>

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
		width:100%;
		height: 52px;
	}
	
</style>

</head>

<body class="back-default">

	<div>
        <c:import url="${cp}/includes/header_user.jsp"></c:import>
    </div>

	<div class="head">
		<h1 style="font-weight:1000;">이용자 비밀번호 찾기</h1>
	</div>
	
	<div class="inputBox" >
		<div class="loginBox">		
				<ul class="login_info">
					<li>
						<!-- err시 빨간 textbox border 테두리 필요 -->
						<input type="text" class="form-control full" id="email" placeholder="이메일">
						<p class="err">이메일 형식이 유효하지 않습니다.</p>
					</li>
					<li>
						<p style="font-size:12px">
							가입시 사용한 이메일 주소를 입력해주시면 비밀번호 재설정 링크를 보내드립니다.<br>
							이메일 주소를 모르시는 경우 비밀번호 찾기가 불가합니다.
						</p>
					</li>
				</ul>

				<div style="margin-top:15px;">
					<button type="submit" class="btn btn-primary full" style="margin-bottom: 0px;">
						<span style="color:black; font-weight: bold;">비밀번호 재설정 링크 보내기</span>
					</button>
				</div>
		</div>
	</div>
	
	<div>
        <c:import url="${cp}/includes/footer.jsp"></c:import>
    </div>
	<%@ include file="../includes/includes_home_end.jsp"%>
</body>
</html>

