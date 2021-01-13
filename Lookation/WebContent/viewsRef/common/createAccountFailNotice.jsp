<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String identify = (String)request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
	
	pageContext.setAttribute("path", cp);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>
<title>createAccountSuccess(host).jsp</title>

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
		padding-top: 120px;
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
	}
	
	button
	{
		border-style: ridge;
		border-width: thin;
		border-color: silver;
		font-size: 17px;
		font-weight: bold;
		background: #fdbe34;
		width:49%;
		height: 75px;
	}
	
	span
	{
		font-size: 100%;
		font-weight: bold;
	}
	
	p
	{
		font-size: 120%;
		font-weight: 500;
	}
	
</style>
</head>

<body class="back-default">

	<div class = "container">
		<div class = "confirmBox">
			
			<div class="dbox" style="margin-bottom: 30px;">
				<div class="icon d-flex align-items-center justify-content-center">
	                  <span class="fa fa-close" style="font-size:400%"></span>
	            </div>
            </div>
			<%-- <img src="<%=cp%>/images/check.png" style="width:15%;"/> --%>
			<h2>회원가입에 <span>실패</span> 하였습니다.</h2>
			<br>
			
			<c:if test="${identify eq 'host'}">
				<p>호스트님의 계정 정보가 이미 생성되었거나, 잘못된 접근이 감지되었습니다. </p> 
			</c:if>
			<c:if test="${identify eq 'member'}">
				<p>이용자님의 계정 정보가 이미 생성되었거나, 잘못된 접근이 감지되었습니다. </p> 
			</c:if>
			
			<p>다시 가입해주세요. 불편을 끼쳐드려 죄송합니다.</p> 
			<hr style="border: solid thin silver;
			           margin: 50px 0px 35px 0px;">
			
			<div class="buttonForm">
				<c:if test="${identify eq 'host'}">
					<button style="float: left; background: #ffffff"
							onclick="location.href='#'">홈으로</button> 
				</c:if>
				<c:if test="${identify eq 'member'}">
					<button style="float: left; background: #ffffff"
					        onclick="location.href='#'">홈으로</button> 
				</c:if>
				
				<button style="float: right; border-width: 0px;"
				        onclick="location.href='${path}/actions/signupform.action?identify=${identify}'">
				        회원가입
				 </button>  
				
			</div>
		</div>
	</div>

</body>
</html>