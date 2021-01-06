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
<title>Insert title here</title>
<%-- <%@ include file="../01.ksb/head(user).jsp" %> --%>
<%@ include file="../includes/includes_home.jsp" %>
</head>
<!-- scroll할 영역을 body로 지정해줌 -->
<!-- navbar 클래스대로 클림하면 움직일 예정 -->
<body data-spy="scroll" data-target=".navbar" data-offset="50">

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

<%-- <section class="hero-wrap hero-wrap-2" style="background-image: url('<%=cp%>/images/bg_3.jpg');" data-stellar-background-ratio="0.5">
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
 --%>

<section class="ftco-section ftco-degree-bg">
	<div class="container">
		<div class="row">
			<div class="col-lg-8 w-100 p-md-5 p-4">
				<p>
					<img src="<%=cp%>/images/image_1.jpg" alt="템플릿 이미지" class="img-fluid">
				</p>


				<h2 class="mb-3 font-weight-bold">파파존스 호러존 스튜디오</h2><hr>
				
				<!-- 상세이미지 슬라이더 -->
				<!-- 클릭하면,,  -->

				<!-- .navbar scrollspy로 이동할 부분 -->
				<nav class="navbar navbar-expand-sm text-dark">
				
					<ul class="navbar-nav">
						<li class="pl-2"><a href="#section1">공간소개</a><li>
						<li class="pl-2"><a href="#section2">시설안내</a><li>
						<li class="pl-2"><a href="#section3">유의사항</a><li>
						<li class="pl-2"><a href="#section4">환불정책</a><li>
						<li class="pl-2"><a href="#section5">Q&A</a><li>
						<li class="pl-2"><a href="#section6">이용후기</a><li>
					</ul>
					
				</nav>

					<!-- Section 1 -->
					<div id="section1">
						<h1>공간소개</h1>
						<p>30평정도 되면 테이블은 단체석 포함 12개입니다. 상인동 술집 골목과 상인롯데시네마 인근이며 지하철
							상인역에서 도보 7분이면 오실수있습니다</p>
						<ul>
							<li>영업시간</li>
							<li>12~22시</li>
							<li>정기휴무일</li>
							<li>없음</li>
							<li>지정휴무일</li>
							<li>언제</li>
						</ul>
					</div>
					
					<!-- Section 2 -->
					<div id="section2">
						<h1>시설 안내</h1>
						<p>Try to scroll this page and look at the navigation bar
							while scrolling!</p>
					</div>
					
					<!-- Section 3 -->
					<div id="section3">
						<h1>예약시 주의사항</h1>
						<p>Try to scroll this page and look at the navigation bar
							while scrolling!</p>
					</div>
					
					<!-- Section 2 -->
					<div id="section4">
						<h1>환불규정 안내</h1>
						<p>Try to scroll this page and look at the navigation bar
							while scrolling!</p>
					</div>
					
					<!-- 지도 위치 -->
					<!-- 주소 및 -->
					<!-- 전화번호 -->
					<!-- 길찾기 버튼 -->
					
					<!-- Section 5 -->
					<div id="section5">
						<h1>Q&A<span class="text-primary">2개</span></h1>
						<!-- 오른쪽 질문 작성하기 버튼 -->
						<p>Try to scroll this page and look at the navigation bar
							while scrolling!</p>
						<!-- 질문 내용 게시판마냥 -->
					</div>
					
					<!-- Section 6 -->
					<div id="section6">
						<h1>이용후기<span class="text-primary">2개</span></h1>
						<!-- 오른쪽 사진 후기만 보기 버튼 -->
						<p>Try to scroll this page and look at the navigation bar
							while scrolling!</p>
					</div>
					
					<!--  -->
					<div id="host-info">
					<!-- 호스트 정보 -->
					</div>
				</div>



			<!-- 오른쪽 사이드바 -->
			<div class="col-lg-4 col-xs-12 sidebar pl-lg-5 ftco-animate">
				<div class="sidebar-box ftco-animate">
					<div class="categories">
						<h3>공간예약하기</h3>
						<hr>
						
						<div class="memo">
							<p class="text-center text-dark">호스트의 승인을 기다릴 필요 없이<br>지금 바로 예약하세요!</p>
							<hr>
						</div>
						
						<!-- 공간정보 -->
						<div class="info">
							<div class="spaceInfo">
								<p class="text">
								TRYGROUND는 아마추어들의 다양한 시도를 지원하는 특별한 아지트입니다.<br>
								추천용도: 드로잉, 공방 각종  클래스 / 컨설팅 / 각종모임 / 스터디 / 조모임 / 새로운 시도를 위한 공간<br>
								※ 독립된 공간이며 다양한 음료를 저렴하게 주문가능합니다.
								</p>
								<ol class="info-list">
									<li>
										<strong>공간유형</strong>
										<span class="det">파티룸 루프탑</span>
									</li>
									<li>
										<span class="sub">예약시간</span>
										<span class="det">최소 1시간부터</span>
									</li>
									<li>
										<span class="sub">수용인원</span>
										<span class="det">최소 3명 ~ 최대 4명</span>
									<li>
								</ol>
							</div>
						</div>
						
						<hr>
						
						<div class="content">
							<h4>예약선택</h4>
							<hr>
							<div class="custom-radio">
								<input type="radio" class="custom-control-input" id="radio1">
								<label class="custom-control-label" for="radio1">예약 선택하기</label>
							</div>
						</div>
						
						
						
						<div class="text-right">
							<h3><span class="icon-won"></span>  60,000 원</h3>
						</div>
						<br>
						
						<!-- 거대버튼 만들고싶은데 못 만듦... -->
						<button type="button" class="btn btn-primary btn-lg btn-block">결제하기</button>

					</div>
				</div>
			</div><!-- 오른쪽 사이드바 끝 -->
		</div>
	</div>	
</section>









<!-- loader -->
<div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>
<%@ include file="../includes/includes_home_end.jsp" %>


<!-- Footer -->
<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>

</body>
</html>