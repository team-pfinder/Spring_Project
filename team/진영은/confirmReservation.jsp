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
<title>예약신청완료.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

	<div>
		<!-- 상단에 로고, 검색창 -->
		로고, 검색창
	</div>

	<div>
		<!-- 체크그림 가운데정렬 -->
		<img alt="" src="">
		<h3>성공적으로 결제완료되었습니다.</h3>
		<h4>결제내역은 회원정보에 등록한 이메일로 전송되었습니다.</h4>
	</div>
	
	
	<div>
		<h3>결제내역 확인</h3>
		<hr>
	</div>

	<div>
		<ul>
			<li>예약자명</li>
			<!-- 예약자정보 예약내역에서 입력한 내용 받아오기-->
			<li>진영은</li>
			<li>연락처</li>
			<li>010-1234-1234</li>
			<li>이메일</li>
			<li>papajon@lookation.com</li>
			<li>인원수</li>
			<li>3명</li>
			<li>요청사항</li>
			<li>고통을 멈춰주세요</li>
			<li>결제금액</li>
			<li>200,000</li>
		</ul>
	</div>

	<a class="btn btn-primary" href="#" role="button">메인 화면으로 돌아가기</a>
</body>
</html>