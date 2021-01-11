<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<title>main(host).jsp</title>

<style type="text/css">

	.brand
	{
		color: #000000;
		font-weight: 800;
		font-family: inherit;
	}
	
	.brand span
	{
	    color: #fdbe34
	}

	h2
	{
		font-weight: bold;
	}
	
	.padding-vertical
	{
		padding-top: 40px;
		padding-bottom: 70px;
	}

</style>

</head>
<body>
	<div>
        <c:import url="${cp}/includes/header_host.jsp"></c:import>
    </div>
	
	<div class="container padding-vertical" style="text-align: center">
		<h1 class="brand" style="font-size: 500%;">Look<span>ation.</span></h1>
		<h1>지금 바로 공간을 공유해보세요!</h1>	
		<h1>파티룸, 브라이덜샤워, 엠티장소 등 다양한 모임공간을 룩케이션을 통해 공유해보세요!</h1> 
		<br>
		<button class="btn btn-primary" style="width: 30%;height: 80px;">
			<span style="font-size: 150%; font-family: inherit; 
			font-weight: bold; color: black;">공간 등록하기</span>
		</button> 
		<p>궁금한 점이 있으신가요? <a href="faq">FAQ 바로가기</a></p> <!-- 작은폰트, 회색폰트 -->
	</div>
	
	<!-- 슬라이드쇼 -->
	<div class="hero-wrap">
		<div class="home-slider owl-carousel">
			<div class="slider-item"
				style="background-image: url(<%=cp%>/images/main-host-1.jpg);">
				<div class="overlay"></div>
				<div class="container">
					<div
						class="row no-gutters slider-text align-items-center justify-content-center">
						<div class="col-md-12 ftco-animate">
							<div class="text w-100 text-center">
								<h2>공간 운영에 대한 솔루션이 필요하신가요?</h2>
								<h1 class="mb-3">이제 룩케이션과 함께 하세요!</h1>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="slider-item"
				style="background-image: url(<%=cp%>/images/main-host-2.jpg);">
				<div class="overlay"></div>
				<div class="container">
					<div
						class="row no-gutters slider-text align-items-center justify-content-center">
						<div class="col-md-12 ftco-animate">
							<div class="text w-100 text-center">
								<h2>수년간 쌓아온 노하우</h2>
								<h1 class="mb-3">이제 룩케이션을 통해 공간 운영하세요!</h1>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="slider-item"
				style="background-image: url(<%=cp%>/images/main-host-3.jpg);">
				<div class="overlay"></div>
				<div class="container">
					<div
						class="row no-gutters slider-text align-items-center justify-content-center">
						<div class="col-md-12 ftco-animate">
							<div class="text w-100 text-center">
								<h2>공간공유를 통한 당신의 열정을</h2>
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
                <strong class="number" data-number="5">0</strong>
              </div>
              <div class="text-2">
              	<span>년 동안 지켜온<br>운영 노하우</span>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
            <div class="block-18 d-flex">
              <div class="text d-flex align-items-center">
                <strong class="number" data-number="1000">0</strong>
              </div>
              <div class="text-2">
              	<span>건 예약거래 <br>성사</span>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
            <div class="block-18 d-flex">
              <div class="text d-flex align-items-center">
                <strong class="number" data-number="378">0</strong>
              </div>
              <div class="text-2">
              	<span>명의 <br>호스트</span>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3 d-flex justify-content-center counter-wrap ftco-animate">
            <div class="block-18 d-flex">
              <div class="text d-flex align-items-center">
                <strong class="number" data-number="1200">0</strong>
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
	
	<!-- footer.jsp -->
	<div>

        <c:import url="${cp}/includes/footer_host.jsp"></c:import>
        <c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
    </div>
</body>
</html>