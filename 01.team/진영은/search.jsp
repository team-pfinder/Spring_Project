<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<title>Lookation</title>
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
	
	<!-- header 자리 -->
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

    <section class="hero-wrap hero-wrap-2" style="background-image: url('images/bg_3.jpg');" data-stellar-background-ratio="0.5">
      <div class="overlay"></div>
      <div class="container">
        <div class="row no-gutters slider-text align-items-end">
          <div class="col-md-9 ftco-animate pb-5">
          	<p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.html">Home <i class="ion-ios-arrow-forward"></i></a></span> <span>Search <i class="ion-ios-arrow-forward"></i></span></p>
            <h1 class="mb-0 bread">Search Space</h1>
          </div>
        </div>
      </div>
    </section>
    
    <section class="ftco-section">
      <div class="container">
      
      	<div class="row d-flex">
      		<!-- 나중에 검색어 태그 a 안에 받아야 함 -->
      		<h3><a>인천</a>에 대한 검색 결과입니다.</h3><br>
      	</div>
      
      	<form>
      		<div class="row">
      			<div class="col-md-4">
					<dl>
						<dt>공간유형</dt>
						<dd>
							<select class="form-select" aria-label="Default select example"
								style="width: 200px;">
								<option selected>모든 공간</option>
								<option value="1">파티룸</option>
								<option value="2">엠티장소</option>
								<option value="3">루프탑</option>
								<option value="4">클럽</option>
							</select>
						</dd>
					</dl>
				</div>
				<div class="col-md-4">
					<dl>
						<dt>날짜</dt>
						<dd>캘린더 선택</dd>
					</dl>
				</div>
				<div class="col-md-4">
					<dl>
						<dt>지역</dt>
						<dd>
							<select class="form-select" aria-label="Default select example2"
								style="width: 200px;">
								<option selected>전체</option>
								<option value="1">서울</option>
								<option value="2">경기</option>
								<option value="3">충북</option>
								<option value="4">충남</option>
								<option value="5">전북</option>
								<option value="6">전남</option>
								<option value="7">경북</option>
								<option value="8">경남</option>
							</select>
							<!-- 선택시 지역명 텍스트 입력 가능-->
						</dd>
						<dd>
           					<input type="text" class="form-control pl-3" placeholder="Search">
         				</dd>
					</dl>
				</div>
				
				
      		</div><br><br>
		</form><!-- 상세검색창 form end -->
		
		<!-- 정렬 start -->
		<div class="row">
    		<div class="col-md-12 text-right">
	    		<select class="form-select" aria-label="Default select example3"
				style="width: 200px; float: right;">
	
					<option selected>정렬방법선택</option>
					<!-- 우측정렬 -->
					<option value="1">가격 낮은 순</option>
					<option value="2">가격 높은 순</option>
					<option value="3">이용후기 많은 순</option>
					<option value="4">베스트 공간 순</option>
					<!-- 선택시 해당 순서대로 정렬한 페이지 요청-->
				</select>
    		</div>
  		</div><br><br>
  		<!-- 정렬 end  -->

      	
        <div class="row d-flex">
          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('images/image_1.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">동명동</a></div>
                  <div><a href="#"><span class="icon-star"></span> 4.1</a></div>
                  <div><a href="#"><span class="icon-people"></span> 4</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 15</a></div>
                  <div><a href="#"><span class="icon-won"></span> 60,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">개구리랑 놀이동산 파티룸</a></h3>
              </div>
            </div>
          </div>
          
          
          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('images/image_2.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">죽전동</a></div>
                  <div><a href="#"><span class="icon-star"></span> 4.3</a></div>
                  <div><a href="#"><span class="icon-people"></span> 6</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 99</a></div>
                  <div><a href="#"><span class="icon-won"></span> 90,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">우후죽순</a></h3>
              </div>
            </div>
          </div>
          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('images/image_3.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">응암동</a></div>
                  <div><a href="#"><span class="icon-star"></span> 3.8</a></div>
                  <div><a href="#"><span class="icon-people"></span> 8</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 20</a></div>
                  <div><a href="#"><span class="icon-won"></span> 190,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">길이가 들쭉날쭉하면요</a></h3>
              </div>
            </div>
          </div>

          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('images/image_4.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">상속자들</a></div>
                  <div><a href="#"><span class="icon-star"></span> 1.8</a></div>
                  <div><a href="#"><span class="icon-people"></span> 100</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 123</a></div>
                  <div><a href="#"><span class="icon-won"></span> 1,200,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">사탄들의 학교에 루시퍼의 등장이라... 재밌어지겠네</a></h3>
              </div>
            </div>
          </div>
          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('images/image_5.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">율전동</a></div>
                  <div><a href="#"><span class="icon-star"></span> 4.9</a></div>
                  <div><a href="#"><span class="icon-people"></span> 1</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 5</a></div>
                  <div><a href="#"><span class="icon-won"></span> 10,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">개구리 일하러오세요~</a></h3>
              </div>
            </div>
          </div>
          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('images/image_6.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">공릉동</a></div>
                  <div><a href="#"><span class="icon-star"></span> 4.4</a></div>
                  <div><a href="#"><span class="icon-people"></span> 5</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 27</a></div>
                  <div><a href="#"><span class="icon-won"></span> 150,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">개구리랑 놀이동산 파티룸 하나를 길쭉하게 쓰면 어떻게 되려나 이렇게 이렇게 이렇게 이렇게 길어져도?</a></h3>
              </div>
            </div>
          </div>
        </div>

        <div class="row mt-5">
          <div class="col text-center">
            <div class="block-27">
              <ul>
                <li><a href="#">&lt;</a></li>
                <li class="active"><span>1</span></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">&gt;</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </section>
    
    
    
    <!-- footer 자리 -->
    
    
    
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