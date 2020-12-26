<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약리스트확인페이지.jsp</title>
<!-- 부트스트랩 CDN -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
	crossorigin="anonymous">
</head>
<body>

	<!-- 맨위 로고, 검색창 -->
	<br><br>
	<div>로고, 검색창, 로그인상태</div><br><br>
	<span>진영은님의 예약 내역입니다.</span><br><br>

	<!-- 예약정보 조회 및 검색 -->
		<!-- 검색이나 정렬 관련 내용 -->
	
	<!-- Table -->
	<table class="table">
		<thead class="table-dark">
			<tr>
				<th>#</th><!-- 번호순 -->
				<th>예약자명</th>
				<th>예약공간명</th>
				<th>예약일자</th>
				<th>예약인원수</th>
				<th>예약가격</th>
				<th>요청사항</th>
				<th>예약현황</th> 
				<!-- 예약완료: 파란색
				     이용완료: 검정색
				     환불신청: 빨간색
					 환불완료: 검정색 -->
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>NNSJ1532</td>
				<td>진영은</td>
				<td>바다</td>
				<td>2021-01-01</td>
				<td>6</td>
				<td>200,000</td>
				<td>-</td>
				<td>예약완료</td>
			</tr>
			<tr>
				<td>BBRD0428</td>
				<td>진영은</td>
				<td>산</td>
				<td>2021-03-14</td>
				<td>5</td>
				<td>100,000</td>
				<td>-</td>
				<td>예약취소</td>
			</tr>
			<tr>
				<td>JLDY0019</td>
				<td>진영은</td>
				<td>들</td>
				<td>2021-07-12</td>
				<td>4</td>
				<td>380,000</td>
				<td>-</td>
				<td>이용완료</td>
			</tr>
		</tbody>
	</table>
	
	
	<!-- 페이징 -->
	<p>1 2 3 4 5 6 7 8 </p>
	
</body>
</html>