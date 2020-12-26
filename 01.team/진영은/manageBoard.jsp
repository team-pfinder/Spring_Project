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
<title>작성글관리.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
	crossorigin="anonymous">
</head>
<body>

	<!-- 맨위 로고, 검색창 -->
	<br>
	<br>
	<div>로고, 검색창, 로그인상태</div>
	<br>
	<br>
	<span>진영은님의 작성글 내역입니다.</span>
	<br>
	<br>


	<!-- 작성글 내역 관리 radio button -->
	<div class="btn-group" role="group"
		aria-label="Basic radio toggle button group">
		
		<input type="radio" class="btn-check" name="btnradio" id="btnradio1"
			autocomplete="off" checked>
		<label class="btn btn-outline-primary" for="btnradio1">이용후기</label>
		<input type="radio" class="btn-check" name="btnradio" id="btnradio2"
			autocomplete="off"> 
		<label class="btn btn-outline-primary" for="btnradio2">Q&A관리</label>
	</div>
	
	<br><br>
	<!-- 1. 이용후기 선택시-->
	
	<!-- 예약정보(예약번호)로 검색 -->
	 <form class="d-flex">
        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">Search</button>
      </form>
	<br>
	
	<!-- 내용 리스트 출력  -->
	<!-- Table -->
	<table class="table">
		<thead class="table-dark">
			<tr>
				<th>공간명</th>
				<th>작성일자</th>
				<th>리뷰 첫줄</th>
			</tr>
		</thead>
		<tbody>
			<!-- 리뷰 내용 클릭시 해당 글주소로 이동 -->
			<tr>
				<td>바다</td>
				<td>2021-01-01</td>
				<td>사람들을 괴롭게 하는 방법 중 하나는 말을...더보기</td>
			</tr>
			<tr>
				<td>산</td>
				<td>2021-07-01</td>
				<td>정말 힘들었습니다...어쩌구 저쩌구</td>
			</tr>
			<tr>
				<td>들</td>
				<td>2021-07-14</td>
				<td>정말 좋았습니다. </td>
			</tr>
		</tbody>
	</table>
	
	<!-- 리스트 없을 경우 -->
	<!-- <span>현재 등록된 Q&A가 없습니다.</span> -->
	
	<!-- 2. Q&A 선택시 -->
	
	<!-- 내용 리스트 출력 -->
	
	
	<!-- 리스트 없을 경우 -->
	<!-- <span>현재 등록된 Q&A가 없습니다.</span> -->
	

	<!-- 페이징 -->
	<p>1 2 3 4 5 6 7 8 </p>



</body>
</html>