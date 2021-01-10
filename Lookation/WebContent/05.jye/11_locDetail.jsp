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
<title>Lookation</title>
<c:import url="${cp}/01.ksb/head(user).jsp"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">
.navbar-expand-sm {
	border-bottom: solid 2px #ffd014;
	border-top: solid 2px #ffd014
}

.info-div {
	padding-bottom: 8em;
}

.sidebar-box {
	border-radius: 20px;
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
.nav-pick {
	width: 80px;
	margin: 10px;
}

.nav-pick > a{
	color: black;
	font-weight: bold;
}

.nav-pick > a:hover {
	color: #ebebeb;
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


/*=== 호스트 정보 ===*/
.host-box {
	font-size: 0.8em;
	background-color: #FFF;
	border-radius: 10px;
	margin-top: 4em;
	line-height: 130%;
}


/*=== 리뷰, Q&A 시간 표시 ===*/
.meta {
	font-size: 13px;
    letter-spacing: .1em;
    color: #ccc;
}

/*=== 별점 ===*/
.set-star{
	color: #fdbe34;
}

/*=== 답글 버튼 ===*/
.reply {
	padding: 5px 10px;
    background: #e6e6e6;
    color: #000000;
    font-size: 11px;
    letter-spacing: .1em;
    font-weight: 400;
    border-radius: 4px;
}


/*=== 리뷰 이미지 리사이징 ===*/
.review-img {

	display: inline-block;
	resize: both;
	max-width: 100%;
	
}

</style>
</head>
<!-- scroll할 영역을 body로 지정해줌 -->
<!-- navbar 클래스대로 클림하면 움직일 예정 -->
<body data-spy="scroll" data-target=".navbar" data-offset="50">


<section class="ftco-section ftco-degree-bg">
	<div class="container">
		<div class="row">	
			<div class="col-md-12">
			
				<h2 class="mb-1 font-weight-bold">파파존스 호러존 스튜디오</h2>
				<h4 class="">주택개조 카페입니다</h4>
				<!-- 태그모양으로 카테고리 표시해줌  -->
				<div class="tagcloud mb-5">
	                <a href="#" class="tag-cloud-link">파티룸</a>
	                <a href="#" class="tag-cloud-link">루프탑</a>
             	</div>
             	
             	
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
			</div>
		</div>
			
		<div class="row">
			<div class="col-lg-8 p-md-3">


				<!-- .navbar scrollspy로 이동할 부분 -->
				<nav class="navbar navbar-expand-sm my-5">
					<ul class="navbar-nav text-center">
						<li class="nav-pick"><a href="#section1">공간소개</a>
						<li>
						<li class="nav-pick"><a href="#section2">시설안내</a>
						<li>
						<li class="nav-pick"><a href="#section3">주의사항</a>
						<li>
						<li class="nav-pick"><a href="#section4">환불정책</a>
						<li>
						<li class="nav-pick"><a href="#section5">Q&A</a>
						<li>
						<li class="nav-pick"><a href="#section6">이용후기</a>
						<li>
					</ul>
				</nav>

				<!-- Section 1 -->
				<div id="section1" class="info-div">
					<h4 class="mb-4 info-sub">공간소개</h4>
					<p>30평정도 되면 테이블은 단체석 포함 12개입니다. 상인동 술집 골목과 상인롯데시네마 인근이며 지하철
						상인역에서 도보 7분이면 오실 수 있습니다.</p>

					<ol class="info-list">
						<li class="list-divider"><span class="sub">영업시간</span> <span
							class="det">12~22시</span></li>
						<li class="list-divider"><span class="sub">정기휴무일</span> <span
							class="det">없음</span></li>
						<li class="list-divider"><span class="sub">지정휴무일</span> <span
							class="det">언제</span>
						<li>
					</ol>
				</div>
				<!-- End .Section 1  -->

				<!-- Section 2 -->
				<div id="section2" class="info-div">
					<h4 class="info-sub">시설 안내</h4>

					<ol class="number-list">
						<li class="list-divider"><span class="sub-num">1</span> <span
							class="det-num">실내화장실</span></li>
						<li class="list-divider"><span class="sub-num">2</span> <span
							class="det">테이블 12개</span></li>
						<li class="list-divider"><span class="sub-num">3</span> <span
							class="det-num">최대 30명 수용</span>
						<li>
						<li class="list-divider"><span class="sub-num">4</span> <span
							class="det-num">카페</span>
						<li>
						<li class="list-divider"><span class="sub-num">5</span> <span
							class="det-num">디저트</span>
						<li>
					</ol>
				</div>
				<!-- End .Section 2  -->

				<!-- Section 3 -->
				<div id="section3" class="info-div">
					<h4 class="info-sub">예약시 주의사항</h4>
					<ol class="number-list">
						<li class="list-divider"><span class="sub-num">1</span> <span
							class="det-num">미리 오시기 전 전화 부탁드려요</span></li>
						<li class="list-divider"><span class="sub-num">2</span> <span
							class="det">카페 안 물품 파손 주의</span></li>
						<li class="list-divider"><span class="sub-num">3</span> <span
							class="det-num">최대 30명 수용</span>
						<li>
						<li class="list-divider"><span class="sub-num">4</span> <span
							class="det-num">그냥그냥그냥</span>
						<li>
						<li class="list-divider"><span class="sub-num">5</span> <span
							class="det-num">아무튼간에 주의하시라고용~~~~~~~~~</span>
						<li>
					</ol>
				</div>
				<!-- End .Section 3  -->

				<!-- Section 4 -->
				<div id="section4" class="info-div">
					<h4 class="info-sub">환불규정 안내</h4>
					<ol class="number-list">
						<li class="list-divider"><span class="sub mr-2">이용
								7일전</span> <span class="det ml-5">총 금액의 100% 환불</span></li>
						<li class="list-divider"><span class="sub">이용 6일전 ~
								1일전</span> <span class="det">총 금액의 50% 환불</span></li>
						<li class="list-divider"><span class="sub mr-3">예약 당일</span>
							<span class="det ml-5">환불 불가</span>
						<li>
					</ol>
				</div>
				<!-- End .Section 4  -->

				<div id="map mb-4" class="info-div">
				
					<!-- 지도 위치 -->
					<!-- 주소 및 -->
					<!-- 전화번호 -->
					<!-- 길찾기 버튼 -->
				
					<h4 class="info-sub">위치 안내</h4>
					<h6>지도자리~~~~~~~</h6>

					<!-- 호스트 정보 -->
					<div class="d-flex p-4 host-box">
						<div class="host-info">
							<h3 class="mb-4">슈미</h3>
							<p>
								<a href="2_newDirectMessage.jsp" class="reply">호스트에게 DM</a>
							</p>
						</div>
														
							
						<div class="desc ml-5">
							<!-- 상호명(개인/법인)
 								, 대표자명, 사업자 등록번호
 								, 사업자 유형, 주업태, 주종목, 사업장 주소)  -->
							<p>사업자번호 : 123-45-67890</p>
							<p>상호명(개인/법인) : 슈미의 동전찾기</p>
							<p>대표자명 : 슈미</p>
							<p>사업자유형 : 간이과세자</p>
							<p>주업태 : 도,소매 주종목 : 숙박</p>
							<p>사업장 주소 : 서울특별시 마포구 무슨다리로 89 1층</p>
							<!-- 예약 완료한 이용자에게 DM버튼 출력 -->
							
						</div>
					</div>
				</div><!-- End .map -->




				<!-- Section 5 -->
				<div id="section5" class="info-div">
					<h4 class="info-sub">
						Q&A<span class="set-star ml-3">2개</span>
						<button class="btn btn-primary float-right">질문 작성하기</button>
					</h4>
					<br>
					<br>
					<ul class="comment-list">
						<li class="comment">
							<h3>돈조반니</h3>
							<div class="meta">2021년 1월 1일 2:21pm</div>
							<p>식기류가 준비되어 있는지 궁금합니다~!!!</p>
							<p>
								<a href="#" class="reply">답글</a>
							</p>
						</li>

						<li class="comment">
							<h3>장로스탄</h3>
							<div class="meta">2020년 12월 31일 2:11pm</div>
							<p>궁금한게 있는데 호박도 나오나요?</p>
							<p>
								<a href="#" class="reply">답글</a>
							</p>

							<ul class="children">
								<li class="comment">
									<h3>슈미</h3>
									<div class="meta">2020년 1월 4일 7:19pm</div>
									<p>해당 공간 게시글을 작성한 호스트가 맞을 경우 수정/삭제 버튼이 나타납니다.</p>
									<p>
										<a href="#" class="reply">수정</a> <a href="#" class="reply">삭제</a>
									</p>
								</li>
							</ul>
						</li>

						<li class="comment">
							<h3>제이</h3>
							<div class="meta">2020년 12월 21일 5:14pm</div>
							<p>6명 이상 가면 추가요금 지불하고 묵을 수 있나요?</p>
							<p>
								<a href="#" class="reply">답글</a>
							</p>
						</li>
					</ul>
					<!-- .comment-list -->

				</div>
				<!-- End .section5 -->


				<!-- section 6 -->
				<div id="section6" class="info-div">
					<h4 class="info-sub">
						이용후기<span class="set-star ml-3">2개</span>

						<!-- 이용후기 작성권한이 있을 경우(이용완료, 후기작성 안했는지 확인) -->
						<!-- 에만 이용후기 작성버튼 표시  -->
						<button class="btn btn-primary float-right">후기 작성하기</button>
					</h4>
					<br>

					<!-- 사진 후기만 보기 클릭시 사진있는 후기만 보여주기-->
					<div class="custom-control custom-switch float-right">
						<input type="checkbox" class="custom-control-input" id="switch1">
						<label class="custom-control-label" for="switch1">사진 후기만
							보기</label>
					</div>


					<ul class="comment-list">
						<li class="comment">
							<h3>돈조반니</h3>
							<div class="float-right set-star">
								<span class="icon-star mr-1"></span><span
									class="icon-star mr-1"></span><span class="icon-star mr-1"></span><span
									class="icon-star mr-1"></span><span class="icon-star mr-1"></span>
							</div>
							<div class="meta">2021년 1월 1일 2:21pm</div>
							<p>커닝시티 파티룸은 무척이나 깨끗하고 쾌적했습니다. 다들 한번 이용해보세요</p>
							<p>
								<img class="review-img" src="<%=cp%>/images/bg_1.jpg"
									alt="리뷰사진">
							</p>
							<p>
								<a href="#" class="reply">답글</a>
							</p>
						</li>

						<li class="comment">
							<h3>장로스탄</h3>
							<div class="float-right set-star">
								<span class="icon-star mr-1"></span><span
									class="icon-star mr-1"></span><span class="icon-star mr-1"></span><span
									class="icon-star mr-1"></span>
							</div>
							<div class="meta">2020년 12월 31일 2:11pm</div>
							<p>리뷰 내용을 엄청 길게 적을 수도 있기 때문에요/// 1000자까지 작성이 가능합니다. 작성한 이용자가
								맞을 경우 수정/삭제 버튼이 나타나요.</p>
							<p>
								<a href="#" class="reply">답글</a>
							</p>

							<ul class="children">
								<li class="comment">
									<h3>슈미</h3>
									<div class="meta">2020년 1월 4일 7:19pm</div>
									<p>해당 공간 게시글을 작성한 호스트가 맞을 경우 수정/삭제 버튼이 나타납니다.</p>
									<p>
										<a href="#" class="reply">수정</a> <a href="#" class="reply">삭제</a>
									</p>
								</li>
							</ul>
						</li>

						<li class="comment">
							<h3>제이</h3>
							<div class="float-right set-star">
								<span class="icon-star mr-1"></span><span
									class="icon-star mr-1"></span><span class="icon-star mr-1"></span>
							</div>
							<div class="meta">2020년 12월 21일 5:14pm</div>
							<p>아주 쾌적하고 좋은 방이었습니다.</p>
							<p>
								<a href="#" class="reply">답글</a>
							</p>
						</li>
					</ul>
					<!-- .comment-list -->

				</div>
				<!-- End .section6 -->
			</div>
			<!-- .col-lg-8 -->


			<!-- 오른쪽 사이드바 -->
	<div class="col-lg-4 col-xs-12 sidebar pl-lg-5 ftco-animate">
		<div class="sidebar-box ftco-animate p-3 mt-5">
			<form action="">
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
							<p class="text mb-0 py-3">
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
					
					<h4>예약선택</h4>
						<hr>
					<!-- 예약하고 싶은 날짜를 선택하면 해당 날짜에 적용된 -->
					<!-- 패키지정보가 나타남. -->
					<div class="py-2 calendar">
						<!-- ※ 원래는 datePicker 이용해야 함. -->
						<input type="date" class="form-control">
					</div>
					
					
					<div class="content">
						
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
						 
					
					<div class="heading my-3">
						<h3>총 예약인원</h3> 
                     	</div>

					<div class="btn-group" style="width: 200px;">
						<button type="button" class="btn btn-primary py-3 my-0 btn-group-addon">+</button>
						<input type="number" id="txtCount" placeholder="인원수"
							required="required" class="form-control border-0">
						<button type="button" class="btn btn-primary py-0 mb-0 btn-group-addon">-</button>
					</div>
					<div class="text-right">
							<h3><span class="icon-won"></span>  60,000 원</h3>
					</div>
					<br>
					
					<!-- 결제하기 누르면 인원수 검증 -->
					<button type="button" class="btn btn-primary btn-lg btn-block">결제하기</button>
			
				</div>
					</form><!-- End Form -->
				</div>
			</div><!-- 오른쪽 사이드바 끝 -->
		</div><!-- End .row -->
	</div><!-- End .container -->
</section>



<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<!-- Footer -->
<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>
<div>
	<c:import url="${cp}/includes/footer.jsp"></c:import>
</div>
</body>
</html>