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
<title>profile.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>
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
			telAjaxRequest();
			
			$("div#displayTel").attr("class", "display");
			$("div#modifyTel").attr("class", "hide");
		});
		
		$("#change").click(function()
		{
			var tel = $("span#tel").html();
			$("input#tel").val(tel);
		});
		
		
		
	 });
	
	 // 수정 후 수정된 연락처를 다시 가져옴
	 function telAjaxRequest()
	 {
	 	 $.post("ajaxmodifytel.action?identify=${identify}" 
		         , {tel : $("input#tel").val()}
		         , function(data) 
		           {
		               $("span#tel").html(data);
		           });
	 }
</script>

</head>
<body>
	<div>
		<c:if test="${identify eq 'host' }">
			 <c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=${info.nick }"></c:import>
		</c:if>
		<c:if test="${identify eq 'member' }">
			 <c:import url="${cp}/includes/header_user.jsp?result=${result }&nick=${info.nick }"></c:import>
		</c:if>
    </div>
	
	<div class="head">
		<c:if test="${identify eq 'host' }">
			<h1 style="font-weight:1000;">호스트 프로필관리</h1> <!-- 큰폰트 -->
		</c:if>
		<c:if test="${identify eq 'member' }">
			<h1 style="font-weight:1000;">이용자 프로필관리</h1> <!-- 큰폰트 -->
		</c:if>
	</div>


	<!-- 프로필관리 양식 -->
	<div class="container">
		<c:if test="${identify eq 'host' }">
		<c:import url="${cp}/includes/mypage_Sidebar(host).jsp"></c:import>
		</c:if>
		<c:if test="${identify eq 'member' }">
			<c:import url="${cp}/includes/mypage_Sidebar(user).jsp"></c:import>
		</c:if>
		<div class="profileBox">
			<hr>
			<table>
				<tr>
					<th>닉네임</th>	<!-- 닉네임 변경창으로 변경되어야 함 -->
					<!-- 닉네임 변경창으로 변경되어야 함 -->
					<td width="400">
						${info.nick }<br>	
					</td>
				</tr>
				
				<tr>
					<th>이메일</th>
					<td>${info.email }</td>
				</tr>
				
				<tr>
					<th>연락처</th> <!-- 연락처 변경창으로 변경되어야 함 -->
					<td>
						<div class="display" id="displayTel">
					    	<span id="tel">${info.tel }</span><br>
					    	<a class="clickAble" href="#" id="change">변경하기</a>
					    </div>
						<div class="hide" id="modifyTel">
							<div>
								<input type="text" class="form-control full"  id="tel"
								 style="float:left; width:80%;">
								<button id="modify" style="float:right; width:20%;
								 height:52px; border-radius: 5px">확인</button>	
							</div>
							<p class="pass" id="tel">연락처 형식이 알맞지 않습니다.</p>
							<a class="clickAble" href="#" id="cancel">취소하기</a>
						</div>
					</td>
				</tr>
				
				<tr>
					<th>이름</th> <!-- 연락처 변경창으로 변경되어야 함 -->
					<td>${info.name} </td>
				</tr>
				
				<tr>
					<th>비밀번호</th>
					<td>
						<div>
							<a class="clickAble" href="confirmpasswordform.action?identify=${identify }&requestUrl=changepasswordform.action">
								변경하기
							</a>
						</div>
					</td>
				</tr>
				
				<tr>
					<th>계좌정보</th>
					<td>
						<div>
							<a class="clickAble" href="bankinfomanage.action?identify=${identify} ">관리</a>
						</div>
					</td>
				</tr>
				
				<tr>
					<th>가입일자</th>
					<td>${info.createDate }</td>
				</tr>
				
				<tr>
					<th>블랙리스트 전환일</th>
					<td>${blackListDate }</td>
				</tr>
				
			</table>
			<hr>
			<!-- 서비스 탈퇴 -->
			<div class="centerClick">
			   <a class="clickAble" href="deleteform.action?identify=${identify }" 
			style="color:red; font-weight:bold">회원 탈퇴</a>
			</div>
		</div>
	</div>
	
	<div>
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