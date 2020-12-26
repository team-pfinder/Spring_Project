<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main(user).jsp</title>
</head>
<body>
	<!-- 오늘의 공간 -->
	<div>
		<table>
			<caption>오늘의 공간</caption>
			<tr>
				<th>1번 게시물</th>
				<th>2번 게시물</th>
				<th>3번 게시물</th>
			</tr>
			<tr>
				<td>test</td>
				<td>test</td>
				<td>test</td>
			</tr>
		</table>
	</div>
	<hr>
	
	<!-- 이용자 리뷰 -->
	<!-- 
		#jquery
		더보기 탭 => 클릭시 무한 스크롤 추가 
	-->
	<!--
		#참고 소스
		jqueryApp01/jqTest06.html
	 -->
	<div>
		<table>
			<caption>
				이용자 리뷰<br>				<!-- 큰폰트 -->
				생생한 후기를 만나보세요.	<!-- 작은폰트, 회색폰트 -->
			</caption>
			<tr>
				<th>1번 리뷰</th>
				<th>2번 리뷰</th>
				<th>3번 리뷰</th>
			</tr>
			<tr>
				<td>test</td>
				<td>test</td>
				<td>test</td>
			</tr>
			
		</table>
	</div>
	
</body>
</html>
















