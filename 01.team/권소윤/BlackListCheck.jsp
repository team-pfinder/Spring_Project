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
<title>BlackListCheck.jsp</title>

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
	
	function refund()
	{
		alert("자동환불처리하러갑니다 슝슝");
	}

	function closeWindow()
	{
		alert("창닫기");
	}
	
</script>

</head>
<body>

	<!-- 
	#jsp
	회원 이름(또는 닉네임), 해제되는 날짜를 로그인 시, 회원 정보를 조회하여
	받아와 처리하는 것으로 수정 필요
	button id, class 정의 필요
	
	#js
	버튼을 눌렀을 대 작동하는 함수 작성
	refund() 의 경우, 자동으로 환불하기로 했는데 계좌가 없으면 계좌를 등록하러,
	계좌가 여러개면 계좌 선택하러 가야함...
 -->

	<div class="container pt-5">
		<div class="row justify-content-between">
			<div class="col">
				<a class="navbar-brand" href="user/index.html">LOOK<span>ATION</span></a>
			</div>
			
		</div>
	</div>

	<div>
		권소윤님은 경고 3회 누적되어 한시적으로 사이트를 이용하실 수 없습니다.<br> 2021년 3월 26일 23:59
		이후 다시 이용하실 수 있습니다. <br>
		<br>
		<button type="button" onclick="refund()" class="btn-primary">마일리지환불</button>
		<button type="button" onclick="closeWindow()" class="btn-secondary">창닫기</button>
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