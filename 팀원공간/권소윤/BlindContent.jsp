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
<title>BlindContent.jsp</title>
	
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

	function back()
	{
		alert("상세페이지로 돌아가기");
	}

</script>
</head>
<body>
	<!-- 이용자/호스트가 블라인드 게시글에 접근하였을 때... -->
	<!-- 
		#js
		리스트로 되돌아가는 함수 정의 필요
		button id, class 정의 필요
		
	-->

<div class="container pt-5">
		<div class="row justify-content-between">
			<div class="col">
				<a class="navbar-brand" href="user/index.html">LOOK<span>ATION</span></a>
			</div>
		</div>
	</div>

<div>
	<span>접근 제한된 게시물입니다.</span><br>	<!-- align : center -->
	<br>
	<button type="button" onclick="back()" class="btn-primary">되돌아가기</button> <!-- align : center -->
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