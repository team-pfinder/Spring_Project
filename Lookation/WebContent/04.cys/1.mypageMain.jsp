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
<%@ include file="../includes/includes_home.jsp"%>
<title>1.mypageMain.jsp</title>
</head>
<body>
	<div class="container pt-5">
		<div class="row justify-content-between">
			<div class="col">
				<a class="navbar-brand" href="index.html">Home<span>builder.</span></a>
			</div>
			<div class="col d-flex justify-content-end">
				<div class="social-media">
					<p class="mb-0 d-flex">
						<a href="#"
							class="d-flex align-items-center justify-content-center"><span
							class="fa fa-facebook"><i class="sr-only">Facebook</i></span></a> <a
							href="#" class="d-flex align-items-center justify-content-center"><span
							class="fa fa-twitter"><i class="sr-only">Twitter</i></span></a> <a
							href="#" class="d-flex align-items-center justify-content-center"><span
							class="fa fa-instagram"><i class="sr-only">Instagram</i></span></a> <a
							href="#" class="d-flex align-items-center justify-content-center"><span
							class="fa fa-dribbble"><i class="sr-only">Dribbble</i></span></a>
					</p>
				</div>
			</div>
		</div>
	</div>
	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light"
		id="ftco-navbar">
		<div class="container">

			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#ftco-nav" aria-controls="ftco-nav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="fa fa-bars"></span> Menu
			</button>
			<form action="#" class="searchform order-lg-last">
				<div class="form-group d-flex">
					<input type="text" class="form-control pl-3" placeholder="Search">
					<button type="submit" placeholder="" class="form-control search">
						<span class="fa fa-search"></span>
					</button>
				</div>
			</form>
			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item"><a href="index.html" class="nav-link">Home</a></li>
					<li class="nav-item"><a href="about.html" class="nav-link">About</a></li>
					<li class="nav-item"><a href="team.html" class="nav-link">Our
							team</a></li>
					<li class="nav-item"><a href="project.html" class="nav-link">Project</a></li>
					<li class="nav-item active"><a href="blog.html"
						class="nav-link">Blog</a></li>
					<li class="nav-item"><a href="contact.html" class="nav-link">Contact</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- END nav -->

	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('<%=cp%>/images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"><a href="index.html">Home <i
								class="ion-ios-arrow-forward"></i></a></span> <span>Mypage <i
							class="ion-ios-arrow-forward"></i></span>
					</p>
					<h1 class="mb-0 bread">마이페이지</h1>
				</div>
			</div>
		</div>
	</section>

	<section class="ftco-section">
		<div class="container">
			<div class="row d-flex">
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="2.mypageProfile.jsp" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/Mypage_BankAccount.png');">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="2.mypageProfile.jsp">프로필관리</a>
							</h3>
						</div>
					</div>
				</div>
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="3.mypageBankAccount.jsp" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/Mypage_Mileage_Charge.png');">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="3.mypageBankAccount.jsp">계좌관리</a>
							</h3>
						</div>
					</div>
				</div>
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="4.mypageReservaionList.jsp" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/Mypage_Mileage_Exchange.png');">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="4.mypageReservaionList.jsp">예약리스트</a>
							</h3>
						</div>
					</div>
				</div>

				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="5.mypageMileageLoadAndWithdraw.jsp" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/image_4.jpg');">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="5.mypageMileageLoadAndWithdraw.jsp">충전/환전 신청</a>
							</h3>
						</div>
					</div>
				</div>
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="6.mypageMileageHistory.jsp" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/image_5.jpg');">
						</a>
						<div class="text mt-3 text-center">
							<div class="meta mb-2"></div>
							<h3 class="heading">
								<a href="6.mypageMileageHistory.jsp">마일리지 내역</a>
							</h3>
						</div>
					</div>
				</div>
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="7.mypageMyReviewAndQ&A.jsp" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/image_6.jpg');">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="7.mypageMyReviewAndQ&A.jsp">내가 쓴 글</a>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>


	<!-- loader -->
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
			<circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
			<circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>

	<%@ include file="../includes/includes_home_end.jsp"%>

</body>
</html>