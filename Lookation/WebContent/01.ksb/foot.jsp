<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link
	href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=cp %>/css/animate.css">
<link rel="stylesheet" href="<%=cp %>/css/owl.carousel.min.css">
<link rel="stylesheet" href="<%=cp %>/css/owl.theme.default.min.css">
<link rel="stylesheet" href="<%=cp %>/css/magnific-popup.css">
<link rel="stylesheet" href="<%=cp %>/css/ionicons.min.css">
<link rel="stylesheet" href="<%=cp %>/css/flaticon.css">
<link rel="stylesheet" href="<%=cp %>/css/icomoon.css">
<link rel="stylesheet" href="<%=cp %>/css/style.css">
<title>foot.jsp</title>
</head>
<body>

	<!-- 오른쪽 정렬 필요 -->
	<!-- <hr>
	<div>
		<button>이용약관</button>
		<button>개인정보처리방침</button>
		<button>운영정책</button>
	</div> -->
	<div class="footer">
		<div class="container-fluid px-lg-5">
			<div class="col-md-9 py-5">
				<div class="row">
					<div class="col-md-4 mb-md-0 mb-4">
						<h2 class="footer-heading">About us</h2>
						<p>우리는 사람입니다. 잡아먹지 않으니 겁먹지마세요.</p>
					</div>
					
					<div class="col-md-8">
						<div class="row justify-content-center">
							<div class="col-md-12 col-lg-10">
								<div class="row">
									<div class="col-md-4 mb-md-0 mb-4">
										<h2 class="footer-heading">Services</h2>
										<ul class="list-unstyled">
											<li><a href="#" class="py-1 d-block">서비스 소개</a></li>
											<li><a href="#" class="py-1 d-block">이용약관</a></li>
											<li><a href="#" class="py-1 d-block">개인정보 처리<br>방침</a></li>
											<li><a href="#" class="py-1 d-block">운영정책</a></li>
										</ul>
									</div>
									<div class="col-md-4 mb-md-0 mb-4">
										<h2 class="footer-heading">About</h2>
										<ul class="list-unstyled">
											<li><a href="#" class="py-1 d-block">Staff</a></li>
											<li><a href="#" class="py-1 d-block">Team</a></li>
											<li><a href="#" class="py-1 d-block">Careers</a></li>
											<li><a href="#" class="py-1 d-block">Blog</a></li>
										</ul>
									</div>
									<div class="col-md-4 mb-md-0 mb-4">
										<h2 class="footer-heading">Resources</h2>
										<ul class="list-unstyled">
											<li><a href="#" class="py-1 d-block">Security</a></li>
											<li><a href="#" class="py-1 d-block">Global</a></li>
											<li><a href="#" class="py-1 d-block">Charts</a></li>
											<li><a href="#" class="py-1 d-block">Privacy</a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row mt-md-5">
					<div class="col-md-12">
						<p class="copyright">
							대표 : 김승범 | 대표전화 : 010-3441-0260
							| Copyright ©
							<script>document.write(new Date().getFullYear());</script>
							2020 All rights reserved
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>