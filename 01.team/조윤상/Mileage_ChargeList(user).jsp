<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp1 = request.getContextPath();
%>    

<!-- 마일리지 충전 내역 페이지 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mileage_ChargeList(user).jsp</title>
</head>
<body>

<!-- 검색창, 메뉴창 -->
<%@ include file="Include.jsp" %>


<div><!-- css 가로배치 -->	
	
	<!-- 서브메뉴 -->
	<table>
		<tr>
			<td>서브메뉴1</td>
		</tr>
		<tr>
			<td>서브메뉴2</td>
		</tr>
		<tr>
			<td>서브메뉴3</td>
		</tr>
		<tr>
			<td>서브메뉴4</td>
		</tr>
	</table>
</div>


<div><!-- css 가로배치 -->
	
	<!-- 타이틀 -->
	<div>
		<h3>마일리지 충전신청 리스트</h3>
		<hr>
	</div>
	
	<!-- 설명 -->
	<div>
		<p>
		마일리지 충전 신청 내역 입니다...
		...
		..
		
		</p>
	</div>
	
	<!-- 리스트 -->
	<div>
		<table>
			<tr>
				<th>신청날짜</th><th>금액</th><th>처리상태</th><th>처리일</th>
			</tr>
			<tr>
				<td>2020-01-01 15:18:12</td>
				<td>50000원</td>
				<td>충전완료</td>
				<td>2020-01-01 16:16:15</td>
			</tr>
			<tr>
				<td>2020-07-05 18:18:18</td>
				<td>50000원</td>
				<td>충전실패</td>
				<td>2020-07-05 19:19:19</td>
			</tr>
			<tr>
				<td>2020-12-25 18:18:18</td>
				<td>50000원</td>
				<td>충전대기</td>
				<th style="text-align: center"> - </th>
			</tr>
		</table>
	</div>
	
</div>

참고이미지<a href="<%=cp %>/images/Mileage_ChargeList.png">그림</a>

</body>
</html>