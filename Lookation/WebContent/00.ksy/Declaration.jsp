<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	// 받아온 정보를 넘겨주는 구문
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

	function declaration()
	{
		alert("호출 확인");
	}
	
	function resetForm()
	{
		alert("호출 확인");
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

<div style="margin: 0 auto;">
	<!-- 신고버튼을 눌렀을 때 초기화면 -->
	<!--
		#jsp
	 	textbox, textarea, button의 id, class 통일하여 재작성 필요
	 	form의 id, action, method 작성 필요
	 	리뷰 및 QNA 상세정보를 받아온 후, Declaration_host.jsp 파일로 넘겨준다.
	 	필요한 정보 : 컨텐츠정보, 신고자정보, 신고일자, 피신고자정보 + 신고 사유
	 	
	 	#js
	 	신고하기 / 다시작성 클릭 시 동작하는 함수 편집 필요
	 	이용자 닉네임은 받아와야 하는 값이기 때문에 reset 되어도 남아있어야 하기 때문에
	 	함수로 처리..해야겠죠? 아닌가...
	 	
	 -->
	<form>	<!-- Declaration_host.jsp로 데이터가 전송 -->
		<span>이용자 닉네임</span><br>
		<input type="text"><br><br>
		<span>신고 사유</span> <br>
		<textarea rows="3" cols="30"></textarea><br>
		<br>
		<button type="button" onclick="declaration()" class="btn-primary">신고하기</button>
		<button type="button" onclick="resetForm()" class="btn-warning">다시작성</button>
	</form>
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