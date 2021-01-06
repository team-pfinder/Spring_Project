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

	<!-- include NAV -->
	<%@ include file="../01.ksb/head(user).jsp"%>
	<%@ include file="../includes/includes_home.jsp"%>
<title>7.mypageMyReviewAndQ&A.jsp</title>
</head>
<body>
	<!-- 타이틀 -->
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2">
							<a href="index.html">Home <i class="ion-ios-arrow-forward"></i></a>
						</span> 
						<span class="mr-2"><a href="1.mypageMain.jsp">Mypage <i class="ion-ios-arrow-forward"></i></a></span>
							<span>My Review & Q&A <i class="ion-ios-arrow-forward"></i>
						</span>
					</p>
					<h1 class="mb-0 bread">내가 작성한 글</h1>
				</div>
			</div>
		</div>
	</section>
	<!-- END 타이틀 -->

	<!-- 본문 -->
	<section class="ftco-section ftco-degree-bg">
		<div class="container">
			<div class="row">
				<!-- 사이드바 -->
				<!-- .col-md-2 -->
				<!-- include mypage_Sidebar.jsp -->
				<%@ include file="../includes/mypage_Sidebar.jsp"%>

				<div class="col-lg-10 ftco-animate">
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
	<%@ include file="../includes/includes_loader.jsp"%>

	<!-- includes_home_end -->
	<%@ include file="../includes/includes_home_end.jsp"%>

</body>
</html>