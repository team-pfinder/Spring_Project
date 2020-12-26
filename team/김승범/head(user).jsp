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
<title>head(user).jsp</title>
</head>
<body>

	<!-- 로고 및 검색 -->
	<!--
		#참고 소스
		jqueryApp01/jqTest01.html
	-->
	<div>
		<table>
			<tr>
				<th><img src="<%=cp %>/images/logo.png" height="80px" /></th>
				<th><input type="text" style="width: 700px; height: 50px; font-size: 30px"/></th>
				<th><img src="<%=cp %>/images/search.png" height="40px" /></th>
			</tr>
		</table>
		<hr>
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
						<li>바로가기
							<ol type="a">
								<li>오늘의 추천공간</li>
								<li>이용자 리뷰</li>
								<li>선택검색</li>
								<li></li>
							</ol>
						</li>
					</ol>
				</td>
				<td>
					<ol start="2" type="1" style="float:left;">
						<li>마이페이지
							<ol type="a">
								<li>예약 리스트</li>
								<li>이용후기·Q&amp;A관리</li>
								<li></li>
								<li></li>
							</ol>
						</li>
					</ol>
				</td>
				<td>			
					<ol start="3" type="1" style="float:left;">
						<li>고객지원
							<ol type="a">
								<li>도움말</li>
								<li>FAQ</li>
								<li></li>
								<li></li>
							</ol>
						</li>
					</ol>	
				</td>
				<td>
					<ol start="4" type="1" style="float:left;">
						<li>서비스 정보
							<ol type="a">
								<li>서비스 소개</li>
								<li>이용약관</li>
								<li>개인정보처리방침</li>
								<li>운영정책</li>
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
					<button>회원가입</button>
				</td>
				<!-- 로그인 후 -->
				<td>
				    <p>로그인 후</p>
					<p>xxxx 님 환영합니다~!</p>
					<button>프로필관리</button>
					<button>로그아웃</button>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>