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
<title>공간 검색한 페이지</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
	crossorigin="anonymous">
	
<style type="text/css">

dl {
	float: left;
}

.thumbnail {
	background-color: gray;
	margin: 10px;
	width: 150px;
	height: 150px;
	background-size: cover;
	background-repeat: no-repeat;
	background-position: center center;
	float: left;
}
</style>


</head>
<body>

	<!-- 맨위 로고, 검색창 -->
	<br>
	<br>
	<div>로고, 검색창, 로그인상태</div>
	<br>
	<br>

	<!-- 검색어 표시 -->
	<span>인천</span>
	<span>(으)로 검색한 결과입니다. </span>
	<br>
	<br>

	<form>
		<dl>
			<dt>공간유형</dt>
			<dd>
				<select class="form-select" aria-label="Default select example"
					style="width: 200px;">
					<option selected>모든 공간</option>
					<option value="1">파티룸</option>
					<option value="2">엠티장소</option>
					<option value="3">루프탑</option>
					<option value="4">클럽</option>
				</select>
			</dd>
		</dl>
		<dl>
			<dt>지역</dt>
			<dd>
				<select class="form-select" aria-label="Default select example2"
					style="width: 200px;">
					<option selected>전체</option>
					<option value="1">서울</option>
					<option value="2">경기</option>
					<option value="3">충북</option>
					<option value="4">충남</option>
					<option value="5">전북</option>
					<option value="6">전남</option>
					<option value="7">경북</option>
					<option value="8">경남</option>
				</select>
				<!-- 선택시 지역명 텍스트 입력 가능-->
			</dd>
			<dd>
				<input type="text" placeholder="지역명을 입력하세요">

			</dd>
		</dl>
		<dl>
			<dt>날짜</dt>
			<dd>캘린더 선택</dd>
		</dl>
	</form>

	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<!-- 위치 : 정렬방법 옆에 -->
	<div>
		<!-- 지도상 조회 -->
		<!-- 지도API 이용 -->
		<button type="button">지도</button>
	</div>
	<div>
		<select class="form-select" aria-label="Default select example3"
			style="width: 200px; float: right;">

			<option selected>정렬방법선택</option>
			<!-- 우측정렬 -->
			<option value="1">가격 낮은 순</option>
			<option value="2">가격 높은 순</option>
			<option value="3">이용후기 많은 순</option>
			<option value="4">베스트 공간 순</option>
			<!-- 선택시 해당 순서대로 정렬한 페이지 요청-->
		</select>
	</div>

	<br>
	<br>
	<br>

	<br>
	<br>
	<div>
		<!-- 검색결과 받아오는 부분 -->

		<!-- 무한스크롤 방식 -->
		<!-- 대표사진, 공간명, 위치, 대표가격, 최대인원, 평균별점, 리뷰갯수 -->
		<div class="thumbnail"
			style="background-image: url('image/mountain.jpg')"></div>
		<p>공간명</p>
		<p>동명동</p>
		<p>60,000</p>
		<p>4인</p>
		<p>4.1</p>
		<p>리뷰 4개</p>
		
		<div class="thumbnail"
			style="background-image: url('image/mountain.jpg')"></div>
		<p>공간명</p>
		<p>동1동</p>
		<p>40,000</p>
		<p>5인</p>
		<p>3.4</p>
		<p>리뷰 15개</p>
		
		<div class="thumbnail"
			style="background-image: url('image/mountain.jpg')"></div>
		<p>공간명</p>
		<p>동2동</p>
		<p>80,000</p>
		<p>2인</p>
		<p>3.8</p>
		<p>리뷰 4개</p>
		
		
		<!-- 검색 결과 없을시 -->
		검색결과가 없습니다.
	</div>
</head>
<body>

</body>
</html>