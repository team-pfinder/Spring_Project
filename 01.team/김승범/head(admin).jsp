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
<title>head(admin).jsp</title>
</head>
<body>

	<!-- 로고 -->
	<div>
		<img src="<%=cp %>/images/logoHost.png" />
		<hr>
	</div>
	<!--
		#참고 소스
		jqueryApp01/jqTest01.html
	-->
	<div>
		<p>Lookation 관리자페이지</p>	<!-- 보라색뒷배경, 중간크기폰트, Lookation(흰색폰트), 호스트센터(노란색폰트) -->
	</div>

	<!-- 메뉴창 및 로그인 -->
	<!-- 
		#jquery
		메뉴 hover로 숨기기, 보이기
	--> 
	<div>
		<table>
			<tr>
				<!-- 메뉴창 -->
				<td>
					<ol start="1" type="1" style="float:left;">
						<li>회원 관리
							<ol type="a">
								<li>이용자 회원 내역</li>
								<li>호스트 회원 내역</li>
								<li>블랙리스트 회원 내역</li>
								<li></li>
							</ol>
						</li>
					</ol>
				</td>
				<td>
					<ol start="2" type="1" style="float:left;">
						<li>마일리지 관리
							<ol type="a">
								<li>충전 신청 내역</li>
								<li>충전 처리 내역</li>
								<li>환전 신청 내역</li>
								<li>환전 처리 내역</li>
							</ol>
						</li>
					</ol>
				</td>
				<td>			
					<ol start="3" type="1" style="float:left;">
						<li>컨텐츠 관리
							<ol type="a">
								<li>신고 내역</li>
								<li>신고 처리 내역</li>
								<li>블라인드 처리 내역</li>
								<li>블라인드 관리</li>
							</ol>
						</li>
					</ol>	
				</td>
				<td>
					<ol start="4" type="1" style="float:left;">
						<li>dummy
							<ol type="a">
								<li>dummy</li>
								<li>dummy</li>
								<li>dummy</li>
								<li>dummy</li>
							</ol>
						</li>
					</ol>
				</td>
				<td></td>	
			    
			    <!-- 로그인 창 -->	
			    <!-- 가운데 정렬 필요 -->
			    <!-- 로그인 전 -->
				<td>
				    <p>로그인 전</p>
					<button>로그인</button>
				</td>
				<!-- 로그인 후 -->
				<td>
				    <p>로그인 후</p>
					<p>xxxx 관리자</p>
					<button>프로필관리</button>
					<button>로그아웃</button>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>