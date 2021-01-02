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
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Declaration.jsp</title>
	
	<link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="user/css/animate.css">
    <link rel="stylesheet" href="user/css/owl.carousel.min.css">
    <link rel="stylesheet" href="user/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="user/css/magnific-popup.css">
    <link rel="stylesheet" href="user/css/ionicons.min.css">
    <link rel="stylesheet" href="user/css/flaticon.css">
    <link rel="stylesheet" href="user/css/icomoon.css">
    <link rel="stylesheet" href="user/css/style.css">

<script type="text/javascript">
</script>
</head>
<body>

	<div class="container pt-5">
		<div class="row justify-content-between">
			<div class="col">
				<a class="navbar-brand" href="user/index.html">LOOK<span>ATION</span></a>
			</div>
		</div>
	</div>

<div class="container">
	<div class="box">
		<!-- 이용자 → 호스트 : 공간게시물 신고 폼 -->
		<form>
			호스트 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="text" id="hostName" class="txt"><br>
			신고하려는 공간 <input id="placeCode" value="이전페이지에서 받아온 공간제목"><br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<select>
				<option>==[신고유형]==</option>
				<option>부적절한 언행 사용</option>
				<option>허위매물</option>
				<option>광고</option>
				<option>호스트 서비스 미충족</option>
				<option>강압적 추가 결제 유도</option>
				<option>기타</option>
			</select><br>
			신고사유<br>
			<textarea rows="3" cols="40" placeholder="자세한 신고사유를 작성하세요."></textarea><br>
			<p>※ 신고 내용을 허위로 작성할 경우, 해당 신고는 반려처리됩니다.<br>
			허위 신고의 경우 횟수가 잦거나, 정도가 심한 경우에는 블랙리스트 처리될 수 있으며,<br>
			법적 제재를 받으실 수 있습니다.</p>
			<button type="button" class="btn btn-primary">신고하기</button>
			<button type="button" class="btn btn-secondary">다시작성</button>
		</form>
		
		<!-- 호스트 → 이용자 : 예약 신고 폼 -->
		<form>
			이용자 &nbsp;&nbsp;&nbsp;
			<input type="text" id="userName" class="txt"><br>
			예약번호 <input type="text" id="reservationCode" value="이전페이지에서 받아온 예약번호"><br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<select>
				<option>==[신고유형]==</option>
				<option>부적절한 언행 사용</option>
				<option>기물파손</option>
				<option>광고</option>
				<option>기타</option>
			</select><br>
			신고사유<br>
			<textarea rows="3" cols="40" placeholder="자세한 신고사유를 작성하세요."></textarea><br>
			<p>※ 신고 내용을 허위로 작성할 경우, 해당 신고는 반려처리됩니다.<br>
			허위 신고의 경우 횟수가 잦거나, 정도가 심한 경우에는 블랙리스트 처리될 수  있으며,<br>
			법적 제재를 받으실 수 있습니다.</p>
			<button type="button" class="btn btn-primary">신고하기</button>
			<button type="button" class="btn btn-secondary">다시작성</button>
		</form>
	</div>
</div>

	<script src="user/js/jquery.min.js"></script>
	<script src="user/js/jquery-migrate-3.0.1.min.js"></script>
	<script src="user/js/popper.min.js"></script>
	<script src="user/js/bootstrap.min.js"></script>
	<script src="user/js/jquery.easing.1.3.js"></script>
	<script src="user/js/jquery.waypoints.min.js"></script>
	<script src="user/js/jquery.stellar.min.js"></script>
	<script src="user/js/jquery.animateNumber.min.js"></script>
	<script src="user/js/owl.carousel.min.js"></script>
	<script src="user/js/jquery.magnific-popup.min.js"></script>
	<script src="user/js/scrollax.min.js"></script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
	<script src="user/js/google-map.js"></script>
	<script src="user/js/main.js"></script>

</body>
</html>