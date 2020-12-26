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
<title>예약신청화면.jsp</title>
<!-- 부트스트랩 CDN -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
	crossorigin="anonymous">
</head>
<body>

	<div>로고, 검색창</div>
	<br>

	<div>
		<h2>예약하기</h2>
	</div>

	<div>
		예약 공간 가격<br>
		<hr>
	</div>

	<div>
		<h4>파파존스 호러존 스튜디오</h4>
	</div>

	<div>
		<ul>
			<li><img alt="" src=""></li>
			<!-- 썸네일사진 들어갈 자리 -->
			<li>공간설명이 들어갈 자리입니다.</li>
			<li>공간유형</li>
			<li>파티룸</li>
			<li>예약인원</li>
			<li>최소 1명 ~ 최대 8명</li>
		</ul>

	</div>

	<div>
		예약 정보
		<hr>
	</div>

	<div>
		<ul>
			<li>예약날짜</li>
			<li>2020.12.31 (목) 12시~ 15시</li>
			<li>예약인원</li>
			<li>1명</li>
		</ul>
	</div>

	<div>
		예약자 정보
		<hr>
	</div>

	<form>
		<ul>
			<li>예약자</li>
			<li><input type="text" placeholder="예약자명"></li>
			<li>연락처</li>
			<!-- 연락처 정규식 검증 -->
			<li><input type="tel" name="phone1"> -<input type="tel"
				name="phone2"> -<input type="tel" name="phone3"></li>
			<!-- 회원정보의 이메일 주소 불러와서 자동으로 입력되게 -->
			<!-- 이메일 정규식 검증 -->
			<li>이메일</li>
			<li><input type="text"></li>
			<li>인원수</li>
			<li><input type="text"></li>
			<li>요청사항</li>
			<li><input type="text" placeholder="남기고 싶은 말을 적어주세요.(최대 100자)"></li>
		</ul>
	</form>

	<div>
		결제 예정금액
		<hr>
	</div>

	<ul>
		<!-- 공간상세 페이지에서 선택한 날짜, 패키지정보 받아옴 -->
		<li>2020.12.31(목)</li>
		<li>12시~ 15시, 3시간</li>
		<li>예약인원</li>
		<li>1명</li>
		<hr>
		<li><h4>60,000</h4></li>
	</ul>

	<div>
		호스트 정보
		<hr>
	</div>

	<ul>
		<!-- 해당 공간의 호스트 사업자 정보 출력-->
		<li>공간상호</li>
		<li>파파존스 파티룸</li>
		<li>대표자명</li>
		<li>진영은</li>
		<li>소재지</li>
		<li>주소주소알아서뭐하게</li>
		<li>사업자번호</li>
		<li>1234567890</li>
		<li>연락처</li>
		<li><p>010-1234-1234</p>
			<p>papajon@lookation.com</p></li>
	</ul>


	<div>
		예약시 주의사항
		<hr>
	</div>

	<ul>
		<!-- 해당 공간의 주의사항 출력-->
		<li>1</li>
		<li>그냥 조심하세요</li>
		<li>2</li>
		<li>깨트리면 2배 보상</li>
		<li>3</li>
		<li>마음의 상처도 보상하세요</li>
		<li>4</li>
		<li>최대 10개까지 입력가능</li>
		<li>5</li>
		<li>출력은 DB에 있는 만큼만!</li>
	</ul>

	<div>
		환불규정 안내
		<hr>
	</div>

	<ul>
		<!-- 서비스 환불규정 정책 출력-->
		<span>이용 1일전 23:59까지만 취소 가능</span>
		<span>이용당일(첫 날) 환불은 불가능합니다. 관련 사항은 호스트에게 직접 문의하세요.</span>
		<li>이용 7일전</li>
		<li>총 금액의 100% 환불</li>
		<li>이용 6일전 ~ 1일전</li>
		<li>총 금액의 50% 환불</li>
		<li>예약 당일</li>
		<li>환불 불가</li>
	</ul>

	<div>
		서비스 동의 <input type="checkbox"> 전체 동의
	</div>


	<div>
		<!-- 전부 다 체크했는지 확인 -->
		<input type="checkbox"> 위 공간의 예약조건 확인 및 결제진행 동의
		<p>필수</p>
		<input type="checkbox"> 환불규정 안내에 대한 동의
		<p>필수</p>
		<input type="checkbox"> 개인정보 제3자 제공 동의
		<p>필수</p>
		<input type="checkbox"> 개인정보 수집 및 이용 동의
		<p>필수</p>
		<span>서비스 이용약관 동의는 필수입니다.</span><!-- 빨간색, 작은글씨 -->
		<!-- 빨간색, 작은글씨 -->
	</div>
	<br><br>

	<!-- 버튼 클릭시 폼 내용 검증 -->
	<!-- 검증 완료시 submit 후 DB 예약내역, 결제내역 테이블에 추가 -->
	<div class="d-grid gap-2">
		<button class="btn btn-primary" type="button">동의하고 결제하기</button>
	</div>
	
	<!-- 맨 위로 올라가는 버튼 -->


</body>
</html>