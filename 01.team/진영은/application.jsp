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
<title>예약 신청화면</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900&display=swap" rel="stylesheet">

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="css/animate.css">

<link rel="stylesheet" href="css/owl.carousel.min.css">
<link rel="stylesheet" href="css/owl.theme.default.min.css">
<link rel="stylesheet" href="css/magnific-popup.css">

<link rel="stylesheet" href="css/ionicons.min.css">

<link rel="stylesheet" href="css/flaticon.css">
<link rel="stylesheet" href="css/icomoon.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>

<!-- Header -->
<div class="container pt-5">
	<div class="row justify-content-between">
		<div class="col">
			<a class="navbar-brand" href="index.html">Look<span>ation.</span></a>
		</div>
		<div class="col d-flex justify-content-end">
			<div class="social-media">
	    		<p class="mb-0 d-flex">
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-facebook"><i class="sr-only">Facebook</i></span></a>
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-twitter"><i class="sr-only">Twitter</i></span></a>
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-instagram"><i class="sr-only">Instagram</i></span></a>
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-dribbble"><i class="sr-only">Dribbble</i></span></a>
	    		</p>
       		</div>
       </div>
	</div>
</div>

<nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
	<div class="container">
    	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="fa fa-bars"></span> Menu
      	</button>
		
		<form action="#" class="searchform order-lg-last">
        	<div class="form-group d-flex">
           		<input type="text" class="form-control pl-3" placeholder="Search">
           		<button type="submit" placeholder="" class="form-control search"><span class="fa fa-search"></span></button>
         	</div>
       	</form>
      	<div class="collapse navbar-collapse" id="ftco-nav">
        	<ul class="navbar-nav mr-auto">
        		<li class="nav-item"><a href="index.html" class="nav-link">Home</a></li>
        		<li class="nav-item"><a href="about.html" class="nav-link">About</a></li>
        		<li class="nav-item"><a href="team.html" class="nav-link">Our team</a></li>
        		<li class="nav-item"><a href="project.html" class="nav-link">Project</a></li>
        		<li class="nav-item active"><a href="blog.html" class="nav-link">Blog</a></li>
          		<li class="nav-item"><a href="contact.html" class="nav-link">Contact</a></li>
        	</ul>
      	</div>
    </div>
</nav>
<!-- END nav -->

<!-- <section class="ftco-section bg-primary">
  	<div class="container">
  		<div class="row">
      		<div class="col-md-9 ftco-animate pb-5">
        		<h1 class="mb-4">예약 신청</h1>더 굵고 크게 / 밑으로 정렬
      		</div>
    	</div>
    </div>
</section> -->

<section class="hero-wrap hero-wrap-2" style="background-image: url('images/bg_3.jpg');" data-stellar-background-ratio="0.5">
  	<div class="overlay"></div>
  	<div class="container">
    	<div class="row no-gutters slider-text align-items-end">
      		<div class="col-md-9 ftco-animate pb-5">
      			<p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.html">Home <i class="ion-ios-arrow-forward"></i></a></span> <span>Space <i class="ion-ios-arrow-forward"></i></span></p>
        		<h1 class="mb-0 bread">선택한 공간 예약하기</h1>
      		</div>
    	</div>
  	</div>
</section>


<section class="ftco-section ftco-degree-bg">
	<div class="container">
		<div class="row">
			<div class="col-lg-8 ftco-animate">
				<p>
					<img src="images/image_1.jpg" alt="" class="img-fluid">
				</p>


				<h2 class="mb-3 font-weight-bold">파파존스 호러존 스튜디오</h2><hr>
				<p>공간설명이 들어갈 자리입니다.</p>
				<p>공간유형 : 파티룸</p>
				<p>예약 인원 : 최소 1명 ~ 최대 8명</p>


				<h2 class="mb-3 mt-5"># 예약 정보</h2><hr>
				<p>예약날짜 : 2020. 12. 31. (목) 12시 ~ 15시</p>
				<p>예약인원 : 1명</p>



				<h2 class="mb-3 mt-5"># 예약자 정보</h2><hr>
				<!-- 여기서 3:7로 나누고 싶다-->
				<form>
					<div class="row">
						<div class="col-lg-2 ftco-animate">
							<p>예약자명 (*)</p>
							<p>연락처 (*)</p>
							<p>이메일 (*)</p>
							<p>인원수 (*)</p>
							<p>요청사항</p>
						</div>


						<!-- 예약자 정보 입력란에 기존 회원정보 자동으로 불러옴 -->
						<!-- 수정 가능 -->
						<div class="col-lg-10 ftco-animate">
							<p>
								<input type="text" placeholder="예약자명" class="form-control-sm">
							</p>
							<p>
								<input type="tel" name="phone1" placeholder="010" class="form-control-sm"
									style="width: 50px;">-<input type="tel" name="phone2" class="form-control-sm"
									style="width: 50px;">-<input type="tel" name="phone3" class="form-control-sm"
									style="width: 50px;">
							</p>
							<p>
								<input type="text" class="form-control-sm">
							</p>
							<p>
								<input type="text" class="form-control-sm">
							</p>
							<p>
								<textarea cols="40" rows="5" class="form-control"
									placeholder="남기고 싶은 말을 적어주세요.(최대 100자)"></textarea>
							</p>
						</div>
					</div>
				</form>

				<h2 class="mb-3 mt-5"># 호스트 정보</h2><hr>
				<!-- 해당 공간의 호스트 사업자 정보 출력-->
				<p>공간상호 : 파파존스 파티룸</p>
				<p>대표자명 : 진영은</p>
				<p>소재지 : 주소주소 알아서뭐하게</p>
				<p>사업자번호 : 1234567890</p>
				<p>연락처 : 010-1234-1234, papajon@lookation.com</p>
				
				
				<h2 class="mb-3 mt-5"># 예약시 주의사항</h2><hr>
				<div class="row">
					<div class="col-lg-1 ftco-animate text-center flex">
						<p>1</p>
						<p>2</p>
						<p>3</p>
						<p>4</p>
						<p>5</p>
					</div>


					<div class="col-lg-10 ftco-animate">
						<!-- 해당 공간의 주의사항 출력-->
						<p>그냥 조심하세요. 이렇게 길게 쓴 주의사항도 담을 수 있는지 확인하는 중입니다. 이렇게 길게 쓴 주의사항도 담을 수 있는지 확인하는 중입니다.</p>
						<p>깨트리면 2배 보상</p>
						<p>마음의 상처도 보상하세요</p>
						<p>최대 10개까지 입력가능</p>
						<p>출력은 DB에 있는 만큼만!</p>
					</div>
				</div>
				
				<h2 class="mb-3 mt-5"># 환불 규정</h2><hr>
				<span>이용 1일전 23:59까지만 취소 가능</span>
				<span>이용당일(첫 날) 환불은 불가능합니다. 관련 사항은 호스트에게 직접 문의하세요.</span><br><br>
				<div class="row">
					<div class="col-lg-3 ftco-animate">
						<p>이용 7일전</p>
						<p>이용 6일전 ~ 1일전</p>
						<p>예약 당일</p>
					</div>


					<!-- 예약자 정보 입력란에 기존 회원정보 자동으로 불러옴 -->
					<!-- 수정 가능 -->
					<div class="col-lg-8 ftco-animate">
						<p>총 금액의 100% 환불</p>
						<p>총 금액의 50% 환불</p>
						<p>환불 불가</p>
					</div>
				</div>
				
				<h2 class="mb-3 mt-5"># 서비스 동의 </h2> <hr>
				
				
				
				<span>이용 1일전 23:59까지만 취소 가능</span>
				<span>이용당일(첫 날) 환불은 불가능합니다. 관련 사항은 호스트에게 직접 문의하세요.</span><br>
				<div class="text-right"><input type="checkbox"> 전체 동의</div><br>
				
				<div>
				<!-- 전부 다 체크했는지 확인 -->
				<p><input type="checkbox"> <span>(필수)</span> 위 공간의 예약조건 확인 및 결제진행 동의</p>
				<p><input type="checkbox"> <span>(필수)</span> 환불규정 안내에 대한 동의</p>
				<p><input type="checkbox"> <span>(필수)</span> 개인정보 제3자 제공 동의</p>
				<p><input type="checkbox"> <span>(필수)</span> 개인정보 수집 및 이용 동의</p>
				<span>서비스 이용약관 동의는 필수입니다.</span>
				<!-- 빨간색, 작은글씨 -->
				</div>
				<br><br>
				
				
				
				
				<!-- 버튼 클릭시 폼 내용 검증 -->
				<!-- 검증 완료시 submit 후 DB 예약내역, 결제내역 테이블에 추가 -->
				<div class="d-grid gap-2 mx-auto">
					<button class="btn btn-primary" type="button">동의하고 결제하기</button>
				</div>
				
				<!-- 맨 위로 올라가는 버튼 -->
				
			</div>



			<!-- 오른쪽 사이드바 -->
			<div class="col-lg-4 sidebar pl-lg-5 ftco-animate">
				<div class="sidebar-box ftco-animate">
					<div class="categories">
						<h3>결제 예정금액</h3>
						
						<div class="row">
							<div class="col-lg-4 ftco-animate">
								<p>예약날짜</p>
								<p>예약시간</p>
								<p>예약인원</p>
							</div>
							
							<div class="col-lg-8 ftco-animate text-right">
								<p>2020.12.31(목)</p>
								<p>12시 ~ 15시(3시간)</p>
								<p>5명</p>
							</div>
							<br>
						</div>
						
						<hr>
						<div class="text-right">
							<h3><span class="icon-won"></span>  60,000 원</h3>
						</div>
						<br>
						
						<!-- 거대버튼 만들고싶은데 못 만듦... -->
						<button type="button" class="btn btn-primary btn-lg btn-block">결제하기</button>

					</div>
				</div>
			</div>
		</div>
	</div>
</section>



	<!-- Footer -->



<!-- loader -->
<div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>


<script src="js/jquery.min.js"></script>
<script src="js/jquery-migrate-3.0.1.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.easing.1.3.js"></script>
<script src="js/jquery.waypoints.min.js"></script>
<script src="js/jquery.stellar.min.js"></script>
<script src="js/jquery.animateNumber.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/jquery.magnific-popup.min.js"></script>
<script src="js/scrollax.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
<script src="js/google-map.js"></script>
<script src="js/main.js"></script>

</body>
</html>