<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>
<title>main.jsp</title>
<style type="text/css">

.box-up 
{
	height: 220px;
	width: 102%;
	background-color: #ffffff;
}

.sumnail 
{
	background-size: cover;
	background-position: center;
}

.box-down 
{
	height: 120px;
	width: 102%;
	background-color: #ffffff;
}

.font-default 
{
	font-weight: bold;
}

.padding-side 
{
	padding-left: 5%;
	padding-right: 5%;
}

.padding-upside 
{
	padding-top: 3%;
}

span.price 
{
	font-size: 140%;
	color: #fdbe34;
}

.info-price, .info-number 
{
	font-size: 90%;
}

.info-number 
{
	position: absolute;
	right: 20px;
	bottom: 12px;
}

#img
{
	transition: background-size ease-out 200ms;
	background-size: auto 100%;
	cursor: default;
}

#img:hover
{
	transition: background-size ease-out 200ms;
	background-size: auto 120%;
	cursor: pointer;
}

#review
{
	cursor: default;
}


#review:hover
{
	cursor: pointer;
}

</style>

<script type="text/javascript">
</script>
</head>

<body>
	<div>
		<c:import url="${cp}/includes/header_user.jsp?result=${result }&nick=${info.nick }">
		</c:import>
	</div>

	<!-- 슬라이드쇼 -->
	<div class="hero-wrap">
		<div class="home-slider owl-carousel">
			<div class="slider-item"
				style="background-image: url(<%=cp%>/images/main-1.jpg);">
				<div class="overlay"></div>
				<div class="container">
					<div
						class="row no-gutters slider-text align-items-center justify-content-center">
						<div class="col-md-12 ftco-animate">
							<div class="text w-100 text-center">
								<h2>즐거운 파티를 위한 공간을 찾고 있나요?</h2>
								<h1 class="mb-3">룩케이션과 함께 하세요!</h1>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="slider-item"
				style="background-image: url(<%=cp%>/images/main-2.jpg);">
				<div class="overlay"></div>
				<div class="container">
					<div
						class="row no-gutters slider-text align-items-center justify-content-center">
						<div class="col-md-12 ftco-animate">
							<div class="text w-100 text-center">
								<h2>브라이들 샤워, 파티룸, 엠티장소 등</h2>
								<h1 class="mb-3">다양한 놀이공간을 룩케이션에서 찾아 보세요!</h1>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="slider-item"
				style="background-image: url(<%=cp%>/images/main-3.jpg);">
				<div class="overlay"></div>
				<div class="container">
					<div
						class="row no-gutters slider-text align-items-center justify-content-center">
						<div class="col-md-12 ftco-animate">
							<div class="text w-100 text-center">
								<h2>당신의 놀이문화를 통한 삶의 지향</h2>
								<h1 class="mb-3">룩케이션이 응원합니다!</h1>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END 슬라이드쇼 -->
	
	<!-- 통계 -->
	<section class="ftco-counter" id="section-counter">
    	<div class="container">
				<div class="row">
          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
            <div class="block-18 d-flex">
              <div class="text d-flex align-items-center">
                <strong class="number" data-number="${countLocation }">0</strong>
              </div>
              <div class="text-2">
              	<span>개의 <br>공유공간</span>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
            <div class="block-18 d-flex">
              <div class="text d-flex align-items-center">
                <strong class="number" data-number="${countBookDeal }">0</strong>
              </div>
              <div class="text-2">
              	<span>건의 <br>예약거래 <br></span>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
            <div class="block-18 d-flex">
              <div class="text d-flex align-items-center">
                <strong class="number" data-number="${countHost }">0</strong>
              </div>
              <div class="text-2">
              	<span>명의 <br>호스트</span>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
            <div class="block-18 d-flex">
              <div class="text d-flex align-items-center">
                <strong class="number" data-number="${countMember }">0</strong>
              </div>
              <div class="text-2">
              	<span>명의 <br>이용자</span>
              </div>
            </div>
          </div>
        </div>
    	</div>
    </section>
	<!-- 통계 END -->

	<!-- 오늘의 공간 -->
	<section class="ftco-section">
		<div class="container" style="max-width: 1300px;">
			<div class="row justify-content-center mb-5 pb-3">
				<div class="col-md-7 heading-section text-center ftco-animate">
					<h2 class="mb-4">오늘의 추천공간</h2>
				</div>
			</div>
			<div class="row mb-4 mx-5 mt-4">
				
				<c:forEach var="todayLocation" items="${todayLocationList }">
					<div class="col-md-4 mb-4 ftco-animate">
						<div onclick="location.href='locationdetail.action?loc_code=${todayLocation.code}'" 
						    class="box-up z-depth-5 flex-center sumnail" id="img"
							style="background-image: url('<%=cp %>/images/image_1.jpg');" ></div>
						<div class="box-down z-depth-5 flex-center">
							<h2 class="font-default padding-side padding-upside">${todayLocation.name }</h2>
							<a class="padding-side"> <span
								class="glyphicon glyphicon-map-marker font-default"></span> ${todayLocation.addr }
							</a>
							<div class="info-price padding-side">
								<span class="price">${todayLocation.min }</span>원/패키지
							</div>
							<div class="info-number padding-side">
								<span class="glyphicon glyphicon-user font-default"></span><a>
									최대 ${todayLocation.max }인</a> &nbsp; <span class="glyphicon glyphicon-star font-default"></span><a>
									${todayLocation.avg }</a>
							</div>
						</div>
					</div>
				</c:forEach>
				
				<%-- 
				<div class="col-md-4 mb-4 ftco-animate">
					<div class="box-up z-depth-5 flex-center sumnail" id="img"
						style="background-image: url('<%=cp%>/images/todaysPlace01.png');" ></div>
					<div class="box-down z-depth-5 flex-center">
						<h2 class="font-default padding-side padding-upside">Mango_Studio</h2>
						<a class="padding-side"> <span
							class="glyphicon glyphicon-map-marker font-default"></span> 서교동
						</a>
						<div class="info-price padding-side">
							<span class="price">80,000</span>원/패키지
						</div>
						<div class="info-number padding-side">
							<span class="glyphicon glyphicon-user font-default"></span><a>
								최대 8인</a> &nbsp; <span class="glyphicon glyphicon-star font-default"></span><a>
								4.0</a>
						</div>
					</div>
				</div>

				<div class="col-md-4 mb-4 ftco-animate">
					<div class="box-up sumnail z-depth-5 flex-center" id="img"
						style="background-image: url('<%=cp%>/images/todaysPlace02.png');"></div>
					<div class="box-down z-depth-5 flex-center">
						<h2 class="font-default padding-side padding-upside">성수로운공간</h2>
						<a class="padding-side"> <span
							class="glyphicon glyphicon-map-marker font-default"></span> 송정동
						</a>
						<div class="info-price padding-side">
							<span class="price">100,000</span>원/패키지
						</div>
						<div class="info-number padding-side">
							<span class="glyphicon glyphicon-user font-default"></span><a>
								최대 30인</a> &nbsp; <span
								class="glyphicon glyphicon-star font-default"></span><a> 4.5</a>
						</div>
					</div>
				</div>

				<div class="col-md-4 mb-4 ftco-animate">
					<div class="box-up sumnail z-depth-5 flex-center" id="img"
						style="background-image: url('<%=cp%>/images/todaysPlace03.png');"></div>
					<div class="box-down z-depth-5 flex-center">
						<h2 class="font-default padding-side padding-upside">홍대 호리존
							스튜디오 비주얼..</h2>
						<a class="padding-side"> <span
							class="glyphicon glyphicon-map-marker font-default"></span> 창천동
						</a>
						<div class="info-price padding-side">
							<span class="price">30,000</span>원/패키지
						</div>
						<div class="info-number padding-side">
							<span class="glyphicon glyphicon-user font-default"></span><a>
								최대 10인</a> &nbsp; <span
								class="glyphicon glyphicon-star font-default"></span><a> 3.0</a>
						</div>
					</div>
				</div> 
				--%>
				
			</div>
		</div>
	</section>
	<!-- 오늘의 공간 END -->
	
	<!-- 이용자 리뷰 -->
	<section class="ftco-section testimony-section bg-primary">
      <div class="container">
        <div class="row justify-content-center mb-5">
          <div class="col-md-7 text-center heading-section heading-section-white ftco-animate">
          	<span class="subheading">생생한 후기를 만나보세요!</span>
            <h2 class="mb-4">이용자 리뷰</h2>
          </div>
        </div>
        <div class="row ftco-animate">
          <div class="col-md-12">
            <div class="carousel-testimony owl-carousel ftco-owl">

			<c:forEach var="todayReview" items="${todayReviewList }">
				<div class="item">
					<div class="testimony-wrap py-4"style="background-image: url('${todayReview.url}');" id="review"
					     onclick="location.href='locationdetail.action?loc_code=${todayReview.code}'">
						<div class="icon d-flex align-items-center justify-content-center">
							<span class="fa fa-quote-left"></span>
						</div>
						<div class="text">
							<br>
							<h2>${todayReview.name}</h2>
							<p class="mb-4" style="color: #000000;">${todayReview.min } 원/패키지</p>
							<hr>
							<div class="align-items-center">
								<img alt="" src="<%=cp%>/images/star/star_rate_${todayReview.rate }.png"
									style="width: 40%;">
								<p style="color: #000000; margin-top: 3%;">${todayReview.content }</p>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>

						<%-- 
              <div class="item">
                <div class="testimony-wrap py-4" style="background-image: url('<%=cp%>/images/reviewPlace02.png');">
                	<div class="icon d-flex align-items-center justify-content-center"><span class="fa fa-quote-left"></span></div>
                  <div class="text">
                    <p class="mb-4" style="color:#000000;">Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                    <div class="d-flex align-items-center">
                    	<div class="user-img" style="background-image: url(<%=cp%>/images/person_2.jpg)"></div>
                    	<div class="pl-3">
		                    <p class="name">Roger Scott</p>
		                    <span class="position">Marketing Manager</span>
		                  </div>
	                  </div>
                  </div>
                </div>
              </div> 
              --%>
              
              
              
            </div>
          </div>
        </div>
      </div>
    </section>
    
    <div>
		<c:import url="${cp}/includes/footer_user.jsp"></c:import>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
	</div>
</body>
</html>















