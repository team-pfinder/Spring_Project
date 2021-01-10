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
<title>profile(host).jsp</title>
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
	
	input.full
	{
		height: 40px;
	}
	
	input.err
	{
		border-color: red;
	}
	
	p.err 
	{
	    display: display;
		font-size: 14px;
		text-align: left;
		color: red;
	}
	
	p.pass
	{
		display: none;
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


<script type="text/javascript">

	$(function()
	 {
		$("#change").click(function()
		{
			$("div#displayTel").attr("class", "hide");
			$("div#modifyTel").attr("class", "display");
		});
		
		$("#cancel").click(function()
		{
			$("div#displayTel").attr("class", "display");
			$("div#modifyTel").attr("class", "hide");
		});
		
		// 전화번호 정규식
		var regTel = /^\d{2,3}-\d{3,4}-\d{4}$/;
		
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
		
		$("#modify").click(function()
		{
			if(!regTel.test($("input#tel").val()))
			{
				alert("연락처 형식이 알맞지 않습니다. 다시 입력해주세요.");
				return;
			}
			
			/* 적용이 되는 자리 */
			
			$("div#displayTel").attr("class", "display");
			$("div#modifyTel").attr("class", "hide");
		});
	 });
</script>

</head>
<body>
	<div>
        <c:import url="${cp}/includes/header_user.jsp"></c:import>
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
					<td width="400">
						입코딩<br>	
					</td>
				</tr>
				
				<tr>
					<th>이메일</th>
					<td>sb92120@gmail.com</td>
				</tr>
				
				<tr>
					<th>연락처</th> <!-- 연락처 변경창으로 변경되어야 함 -->
					<td>
						<div class="display" id="displayTel">
					    	010-3441-0260<br>
					    	<a class="clickAble" href="#" id="change">변경하기</a>
					    </div>
						<div class="hide" id="modifyTel">
							<div>
								<input type="text" class="form-control full"  id="tel"
								 value="010-3441-0260" style="float:left; width:80%;">
								<button id="modify" style="float:right; width:20%;
								 height:52px; border-radius: 5px">확인</button>	
							</div>
							<p class="pass" id="tel">연락처 형식이 알맞지 않습니다.</p>
							<a class="clickAble" href="#" id="cancel">취소하기</a>
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
        <c:import url="${cp}/includes/footer_host.jsp"></c:import>
        <c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
    </div>
</body>
</html>