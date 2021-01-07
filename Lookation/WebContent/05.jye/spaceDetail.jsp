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

<style type="text/css">
.navbar-expand-sm {
	border-bottom: solid 2px #ffd014;
	border-top: solid 2px #ffd014
}

.info-div {
	padding-bottom: 8em;
}

/*=== carousel ===*/
.slider-item img{
    display: block;
    width: 100%;
    height: auto;
    max-width: 500px;
    resize: both;
}


/*=== scrollspy ===*/
a {
	color: black;
	font-weight: bold;
}

a:hover {
	color: orange;
	text-decoration: none;
}

/*=== sub 제목 밑줄 ===*/
.info-sub:after {
    content: "";
    display: block;
    font-weight: bold;
    width: 25px;
    border-bottom: 4px solid #ffd014;
    margin: 10px 0px;
}

/*=== 공간소개, 환불규정안내 ===*/
.info-list {
	list-style: none;
	display: inline;
}

/*=== list 구분 ===*/
.list-divider {
	border-bottom: 1px solid #ebebeb;
	padding: 10px 0 8px 3px;
}

.sub {
	/*font-weight: bold;*/
	
}

.det {
	padding-left: 30px;
	color: #656565;
	text-align:justify;
}

/*=== 시설 안내, 주의사항 리스트 ===*/
.number-list {
	list-style: none;
	padding: 0px;
}

.number-list .sub-num {
	padding-top: 1px;
	font-weight: bold;
	padding-left: 3px;
}

.number-list .det-num {
	padding-left: 30px;
	color: #656565;
}
</style>
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

<section class="ftco-section ftco-degree-bg">
	<div class="container">
		<div class="row">	
			<div class="col-lg-8 w-100 p-md-5 p-4">
				
				
				<!--- carousel --->
				<!-- 상세이미지 슬라이더 -->
				<div class="home-slider owl-carousel">
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_1.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_2.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_3.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_4.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_5.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_6.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/location1.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/location4.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_2.jpg);">
					</div>
				</div>
				<!-- 이미지 클릭하면 확대  -->
				
				<div class="row">
					<div class="col-lg-12 p-md-5 p-4">
						<h2 class="mb-3 font-weight-bold">파파존스 호러존 스튜디오</h2>
						<h4 class="">주택개조 카페입니다</h4>
						<!-- 태그모양으로 카테고리를...?  -->
						<div class="tagcloud">
			                <a href="#" class="tag-cloud-link">파티룸</a>
			                <a href="#" class="tag-cloud-link">루프탑</a>
		             	</div>
					</div>
				</div>
				

				<!-- .navbar scrollspy로 이동할 부분 -->
				<nav class="navbar navbar-expand-sm my-5">
				
					<ul class="navbar-nav">
						<li class="nav-item "><a href="#section1">공간소개</a><li>
						<li class="nav-item pl-5"><a href="#section2">시설안내</a><li>
						<li class="nav-item pl-5"><a href="#section3">주의사항</a><li>
						<li class="nav-item pl-5"><a href="#section4">환불정책</a><li>
						<li class="nav-item pl-5"><a href="#section5">Q&A</a><li>
						<li class="nav-item pl-5"><a href="#section6">이용후기</a><li>
					</ul>
					
				</nav>

				<!-- Section 1 -->
				<div id="section1" class="info-div">
					<h4 class="mb-4 info-sub">공간소개</h4>
					<p>30평정도 되면 테이블은 단체석 포함 12개입니다. 상인동 술집 골목과 상인롯데시네마 인근이며 지하철
						상인역에서 도보 7분이면 오실 수 있습니다.</p>

					<ol class="info-list">
						<li class="list-divider">
							<span class="sub">영업시간</span>
							<span class="det">12~22시</span>
						</li>
						<li class="list-divider">
							<span class="sub">정기휴무일</span>
							<span class="det">없음</span>
						</li>
						<li class="list-divider">
							<span class="sub">지정휴무일</span>
							<span class="det">언제</span>
						<li>
					</ol>
				</div>
				
				<!-- Section 2 -->
				<div id="section2" class="info-div">
					<h4 class="info-sub">시설 안내</h4>
					<p>Try to scroll this page and look at the navigation bar
						while scrolling!</p>
						
						
					<ol class="number-list">
						<li class="list-divider">
							<span class="sub-num">1</span>
							<span class="det-num">실내화장실</span>
						</li>
						<li class="list-divider">
							<span class="sub-num">2</span>
							<span class="det">테이블 12개</span>
						</li>
						<li class="list-divider">
							<span class="sub-num">3</span>
							<span class="det-num">최대 30명 수용</span>
						<li>
						<li class="list-divider">
							<span class="sub-num">4</span>
							<span class="det-num">카페</span>
						<li>
						<li class="list-divider">
							<span class="sub-num">5</span>
							<span class="det-num">디저트</span>
						<li>
					</ol>
					
				</div>
				
				<!-- Section 3 -->
				<div id="section3" class="info-div">
					<h4 class="info-sub">예약시 주의사항</h4>
					<ol class="number-list">
						<li class="list-divider">
							<span class="sub-num">1</span>
							<span class="det-num">미리 오시기 전 전화 부탁드려요</span>
						</li>
						<li class="list-divider">
							<span class="sub-num">2</span>
							<span class="det">카페 안 물품 파손 주의</span>
						</li>
						<li class="list-divider">
							<span class="sub-num">3</span>
							<span class="det-num">최대 30명 수용</span>
						<li>
						<li class="list-divider">
							<span class="sub-num">4</span>
							<span class="det-num">그냥그냥그냥</span>
						<li>
						<li class="list-divider">
							<span class="sub-num">5</span>
							<span class="det-num">아무튼간에 주의하시라고용~~~~~~~~~</span>
						<li>
					</ol>
				</div>
				
				<!-- Section 2 -->
				<div id="section4" class="info-div">
					<h4 class="info-sub">환불규정 안내</h4>
					<ol class="info-list">
						<li class="list-divider">
							<span class="sub">이용 7일전</span>
							<span class="det">총 금액의 100% 환불</span>
						</li>
						<li class="list-divider">
							<span class="sub">이용 6일전 ~ 1일전</span>
							<span class="det">총 금액의 50% 환불</span>
						</li>
						<li class="list-divider">
							<span class="sub">예약 당일</span>
							<span class="det">환불 불가</span>
						<li>
					</ol>
				</div>
				
				<!-- 지도 위치 -->
				<!-- 주소 및 -->
				<!-- 전화번호 -->
				<!-- 길찾기 버튼 -->
				<div class="info-div">
					<h1>지도~~~~~</h1>
				</div>
			
				
				
				<!-- Section 5 -->
				<div id="section5" class="info-div">
					<h4 class="info-sub">Q&A<span class="text-primary ml-3">2개</span> <button class="btn btn-primary float-right">질문 작성하기</button></h4>
					<br><br>
					<p>Try to scroll this page and look at the navigation bar
						while scrolling!</p>
					<!-- 질문 내용 게시판마냥 -->
				</div>
					
				
				<!-- Section 6 -->
				<div id="section6" class="info-div">
					<h4 class="info-sub">이용후기<span class="text-primary ml-3">2개</span>
					
					<!-- 이용후기 작성권한이 있을 경우(이용완료, 후기작성 안했는지 확인) -->
					<!-- 에만 이용후기 작성버튼 표시  -->
					<button class="btn btn-primary float-right">후기 작성하기</button></h4>
					<br>
					
					<!-- 사진 후기만 보기 클릭시 사진있는 후기만 보여주기-->
					<div class="custom-control custom-switch float-right">
						<input type="checkbox" class="custom-control-input"
							id="switch1"> <label class="custom-control-label"
							for="switch1">사진 후기만 보기</label>
					</div>
					<span>Try to scroll this page and look at the navigation bar
						while scrolling!</span>
				</div>
				
				<!--  -->
				<div id="host-info" class="info-div">
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
								<p class="text mb-0 py-5">
								TRYGROUND는 아마추어들의 다양한 시도를 지원하는 특별한 아지트입니다.<br>
								추천용도: 드로잉, 공방 각종  클래스 / 컨설팅 / 각종모임 / 스터디 / 조모임 / 새로운 시도를 위한 공간<br>
								※ 독립된 공간이며 다양한 음료를 저렴하게 주문가능합니다.
								</p>
								<ol class="info-list">
									<li class="list-divider">
										<span class="sub">공간유형</span>
										<span class="det">파티룸 루프탑</span>
									</li>
									<li class="list-divider">
										<span class="sub">예약시간</span>
										<span class="det">최소 1시간부터</span>
									</li>
									<li class="list-divider">
										<span class="sub">수용인원</span>
										<span class="det">최소 3명 ~ 최대 4명</span>
									<li>
								</ol>
							</div>
						</div>
						
						<br>
						
						<div class="content">
							<h4>예약선택</h4>
							<hr>
							<div class="form-check">
								<input type="radio" class="form-check-input" id="radio1">
								<label class="form-check-label" for="radio1">알뜰오후(12시 ~ 18시)</label>
								<div class="flex float-right">
									<strong>\54,000</strong>
								</div>
							</div>
							<div class="form-check">
								<input type="radio" class="form-check-input" id="radio2">
								<label class="form-check-label" for="radio2">알뜰저녁(19시 ~ 24시)</label>
									<div class="flex float-right">
									<strong>\89,000</strong>
								</div>
							</div>
							<div class="form-check">
								<input type="radio" class="form-check-input" id="radio3">
								<label class="form-check-label" for="radio3">올나잇(19시~ 익일10시)</label>
								<div class="flex float-right">
									<strong>\104,000</strong>
								</div>
							</div>
						</div>
							 
						<div class="py-3 calendar">
							<!-- ※ 원래는 datePicker 이용해야 함. -->
							<input type="date" class="form-control">
						</div>
						<div class="heading"><h3>총 예약인원</h3> <span>
                        (최대 8명
                        , 4명 초과시 10,000원/인)
                      	</span></div>
						
						<div class="box_setting">
							<span class="input">
							 <a class="btn_minus">-</a> 
								<input type="number"
								class="number_set"></span><a class="btn_plus">+</a>
						</div>
						<br><br><br>
						<div class="text-right">
								<h3><span class="icon-won"></span>  60,000 원</h3>
						</div>
						<br>
						
						<!-- 결제하기 누르면 인원수 검증 -->
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