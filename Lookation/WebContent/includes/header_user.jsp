<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<%@ include file="../includes/includes_home.jsp"%>
<title>header_host.jsp</title>

<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	
<script type="text/javascript">
	/* 서브메뉴 애니메이션 */
	$(function()
	{
		$("ul.sub").hide();
		$("#menu li").hover(function()
		{
			//$("ul:not(:animated)",this).slideDown("fast");
		    $("ul:not(:animated)",this).fadeIn("fast");
		},
		function()
		{
			//$("ul",this).slideUp("fast");
			$("ul",this).fadeOut("fast");
		});
	});
</script>

<style type="text/css">
/* 불릿 제거, 들여쓰기, 밑, 위 여유공간 */
ul.sub 
{
    width:130px;

	text-align: center;
	list-style: none;
	padding-left: 0px;
	padding-top: 10px;
	padding-bottom: 50px;
		
	position: absolute;
	display:none;
}

li.nav-item
{
	text-align: center;
    width:130px;
}

#menu 
{
	font-size: 14px;
	font-weight: 500;
	color: #fdbe34;
}

#menuWhite 
{
	font-size: 14px;
	font-weight: 500;
	color: #fdbe34;
	color: #ffffff;
}

.back-default
{
	background: #f6f6f6;
}

.navbar
{
	margin-bottom: 0px;
}
</style>

</head>

<body class="back-default">
	<div class="container pt-5">
		<div class="row justify-content-between">
			<!-- 좌상단 -->
			<div class="col">
				<!-- 로고 -->
				<a class="navbar-brand" href="index.html">Look<span>ation.</span></a>
			</div>
			<!-- 우상단 -->
			<div class="col d-flex justify-content-end">

				<!-- 검색 -->
				<div class="sidebar-box">
					<form action="#" class="search-form">
						<div class="form-group">
							<span class="icon icon-search"></span> 
							<input type="text" class="form-control" placeholder="지역 또는 공간을 검색해보세요!" style="font-size: 20px;">
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

	<!-- 메뉴바 -->
	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light"
		id="ftco-navbar">
		<div class="container">
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#ftco-nav" aria-controls="ftco-nav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="fa fa-bars"></span> Menu
			</button>

			<!-- 메뉴 -->
			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav mr-auto" id="menu">
					<li class="nav-item"><a href="#" class="nav-link">바로가기</a>
						<ul class="sub navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light">
							<li><a href="#">오늘의 추천공간</a></li>
							<li><a href="#">이용자 리뷰</a></li>
							<li><a href="#">선택검색</a></li>
						</ul></li>

					<li class="nav-item"><a href="#" class="nav-link">마이페이지</a>
						<ul class="sub navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light">
							<li><a href="#">마일리지 관리</a></li>
							<li><a href="#">예약 리스트</a></li>
							<li><a href="#">리뷰·Q&amp;A관리</a></li>
						</ul></li>

					<li class="nav-item"><a href="#" class="nav-link">고객지원</a>
						<ul class="sub navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light">
							<li><a href="#">도움말</a></li>
							<li><a href="#">FAQ</a></li>
						</ul></li>

					<li class="nav-item"><a href="#" class="nav-link">dummy</a>
						<ul class="sub navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light">
							<li><a href="#">dummy</a></li>
							<li><a href="#">dummy</a></li>
							<li><a href="#">dummy</a></li>
							<li><a href="#">dummy</a></li>
						</ul> 
					</li>
				
				</ul>
			</div>

			<!-- 로그인 상태 -->
			<div class="align-self-start"
				style="padding-top: 1.8rem; padding-right: 40px;">
				<form action="#">
					<!-- 로그인 전 -->
					<!--
					<a id="menuWhite">로그인 하세요.</a>
					<a href="login" id="menu">&emsp;로그인</a>
					<a href="sign_out" id="menu">&emsp;회원가입</a>
					-->

					<!-- 로그인 후 -->
					<a id="menuWhite"><span id="menu">xxxx</span>님 환영합니다.</a> 
					<a href="profile" id="menu">&emsp;프로필</a> 
					<a href="logout" id="menu">&emsp;로그아웃</a>
				</form>
			</div>

		</div>
	</nav>
	<!-- END 메뉴바 -->

</body>
</html>