<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String identify = (String)request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>
<title>회원가입 성공</title>

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
		width:100%;
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
	                  <span class="fa fa-check"></span>
	            </div>
            </div>
			<%-- <img src="<%=cp%>/images/check.png" style="width:15%;"/> --%>
			<h2>회원가입이 <span>완료</span> 되었습니다.</h2>
			<br>
			
			<c:if test="${identify eq 'host'}">
				<p>호스트님의 회원가입을 축하합니다!</p> 
			</c:if>
			<c:if test="${identify eq 'member'}">
				<p>이용자님의 회원가입을 축하합니다!</p> 
			</c:if>
			
			<p>알차고 실속있는 서비스로 찾아뵙겠습니다.</p> 
			<hr style="border: solid thin silver;
			           margin: 50px 0px 35px 0px;">
			
			<div class="buttonForm">
				<c:if test="${identify eq 'host'}">
					<button style="background: #ffffff"
							onclick="location.href='hostmain.action'">홈으로</button> 
				</c:if>
				<c:if test="${identify eq 'member'}">
					<button style="background: #ffffff"
					        onclick="location.href='membermain.action'">홈으로</button> 
				</c:if> 
			</div>
		</div>
	</div>

</body>
</html>