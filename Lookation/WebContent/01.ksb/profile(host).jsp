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
<title>profile(user).jsp</title>
<%@ include file="../includes/includes_home.jsp"%>
<%@ include file="../includes/defaults.jsp"%>
<style type="text/css">
	.head
	{
		text-align:center;
		padding-top: 5%;
		padding-bottom: 2%;
	}
	
	.profileBox
	{
	    width: 648px;
	    background-color: #ffffff;
	    
	    margin: 0 auto; 
		float: none;
		padding: 32px;
		margin-bottom: 100px;
	}
	
	div.centerClick
	{
		text-align: center;
	}
	
	tr
	{
		height: 50%;
	}
	
	th
	{
		padding: 12px 0;
		vertical-align: top;
		color: black;
	}
	
	td
	{	
		padding: 12px 0;
		padding-left: 50px;
		text-align: left;
	}
	
	a.clickAble
	{
		text-decoration: underline;
		font-weight: bold;
	}
	
	.modifyBox
	{
		width : 80%;
		height: 40px;
	}
	
	button
	{
		width : 20%;
		height: 40px;
		background: #fdbe34; 
		color: #000000;
		font-weight: bold;
		border: 0;
	}
	
</style>
</head>
<body>
	<div>
        <c:import url="${cp}/includes/header_host.jsp"></c:import>
    </div>
	
	<div class="head">
		<h1 style="font-weight:1000;">호스트 프로필관리</h1> <!-- 큰폰트 -->
	</div>
	
	<!-- 프로필관리 양식 -->
	<div class="container">
		<div class="profileBox">
			<hr>
			<table>
				<tr>
					<th>닉네임</th>	<!-- 닉네임 변경창으로 변경되어야 함 -->
					<!-- 닉네임 변경창으로 변경되어야 함 -->
					<td>
						<div>
							<input class="modifyBox" type="text" value="입코딩"><button>확인</button>	
						</div>
						<!-- 입코딩<br> -->
						
						<div>
							<!-- <a class="clickAble" href="#">변경하기</a> -->
							<a class="clickAble" href="#">취소하기</a>
						</div>
					</td>
				</tr>
				
				<tr>
					<th>이메일</th>
					<td>sb92120@gmail.com</td>
				</tr>
				
				<tr>
					<th>연락처</th> <!-- 연락처 변경창으로 변경되어야 함 -->
					<td>
						<div>
							<input class="modifyBox" type="text" value="010-3441-0260"><button>확인</button>	
						</div>
						<!-- 010-3441-0260<br> -->
						<div>
							<!-- <a class="clickAble" href="#">변경하기</a> -->
							<a class="clickAble" href="#">취소하기</a>
						</div>
					</td>
				</tr>
				
				<tr>
					<th>비밀번호</th>
					<td>
						<div>
							<a class="clickAble" href="changePassword(host).jsp">변경하기</a>
						</div>
					</td>
				</tr>
				
				<tr>
					<th>계좌정보</th>
					<td>
						<div>
							<a class="clickAble" href="#">관리</a>
						</div>
					</td>
				</tr>
				
				<tr>
					<th>가입일자</th>
					<td>2020.12.04</td>
				</tr>
				
				<tr>
					<th>블랙리스트 전환일</th>
					<td>2021.01.05</td>
				</tr>
			</table>
			<hr>
			<!-- 서비스 탈퇴 -->
			<div class="centerClick">
				<a class="clickAble" href="#" 
				style="color:red; font-weight:bold">회원 탈퇴</a>
			</div>
		</div>
	</div>
	
	<div>
        <c:import url="${cp}/includes/footer.jsp"></c:import>
    </div>
	<%@ include file="../includes/includes_home_end.jsp"%>
</body>
</html>