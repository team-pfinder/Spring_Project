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
<title>2.mypageProfile.jsp</title>
</head>
<body>
	<!-- nav -->
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

	<!-- 타이틀 -->
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"> <a href="index.html">Home <i
								class="ion-ios-arrow-forward"></i></a>
						</span> <span class="mr-2"><a href="1.mypageMain.jsp">Mypage <i
								class="ion-ios-arrow-forward"></i></a></span> <span>Profile <i
							class="ion-ios-arrow-forward"></i>
						</span>
					</p>
					<h1 class="mb-0 bread">프로필 관리</h1>
				</div>
			</div>
		</div>
	</section>
	<!-- END 타이틀 -->

	<!-- 본문 -->
	<section class="ftco-section ftco-degree-bg">
		<div class="container" style="margin-top: -80px";>
			<div class="row">
				<!-- 사이드바 -->
				<!-- .col-md-8 -->
				<div class="col-lg-3 sidebar pl-lg-5 ftco-animate">
					<div class="sidebar-box ftco-animate">
						<div class="categories">
							<h3><a href="1.mypageMain.jsp">마이페이지</a></h3><hr>
							<li><a href="2.mypageProfile.jsp">프로필 관리 <span
									class="ion-ios-arrow-forward"></span></a></li>
							<li><a href="3.mypageBankAccount.jsp">계좌 관리 <span
									class="ion-ios-arrow-forward"></span></a></li>
							<li><a href="4.mypageReservaionList.jsp">예약 리스트 <span
									class="ion-ios-arrow-forward"></span></a></li>
							<li><a href="5.mypageMileageLoadAndWithdraw.jsp">충전 및 환전
									신청 <span class="ion-ios-arrow-forward"></span>
							</a></li>
							<li><a href="6.mypageMileageHistory.jsp">마일리지 내역 <span
									class="ion-ios-arrow-forward"></span></a></li>
							<li><a href="7.mypageMyReviewAndQ&A.jsp">내가 작성한 글 <span
									class="ion-ios-arrow-forward"></span></a></li>
						</div>
					</div>

				</div>
				<!-- END 사이드바 -->

				<div class="col-lg-9 ftco-animate">
					<h2 class="mb-3">It is a long established fact a reader be
						distracted</h2>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
						Reiciendis, eius mollitia suscipit, quisquam doloremque distinctio
						perferendis et doloribus unde architecto optio laboriosam porro
						adipisci sapiente officiis nemo accusamus ad praesentium? Esse
						minima nisi et. Dolore perferendis, enim praesentium omnis, iste
						doloremque quia officia optio deserunt molestiae voluptates soluta
						architecto tempora.</p>
					<p>Molestiae cupiditate inventore animi, maxime sapiente optio,
						illo est nemo veritatis repellat sunt doloribus nesciunt! Minima
						laborum magni reiciendis qui voluptate quisquam voluptatem soluta
						illo eum ullam incidunt rem assumenda eveniet eaque sequi deleniti
						tenetur dolore amet fugit perspiciatis ipsa, odit. Nesciunt dolor
						minima esse vero ut ea, repudiandae suscipit!</p>
					<h2 class="mb-3 mt-5">#2. Creative WordPress Themes</h2>
					<p>Temporibus ad error suscipit exercitationem hic molestiae
						totam obcaecati rerum, eius aut, in. Exercitationem atque quidem
						tempora maiores ex architecto voluptatum aut officia doloremque.
						Error dolore voluptas, omnis molestias odio dignissimos culpa ex
						earum nisi consequatur quos odit quasi repellat qui officiis
						reiciendis incidunt hic non? Debitis commodi aut, adipisci.</p>
					<p>
						<img src="images/image_2.jpg" alt="" class="img-fluid">
					</p>


				</div>
				<!-- END <div class="col-lg-8 ftco-animate"> -->

			</div>
			<!-- END <div class="row"> -->


		</div>
		<!-- END container -->
	</section>
	<!-- .section -->

	<!-- loader -->
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
			<circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
			<circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>

	<!-- includes_home_end -->
	<%@ include file="../includes/includes_home_end.jsp"%>

</body>
</html>