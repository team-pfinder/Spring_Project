<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();
%>
<!DOCTYPE html>

<head>

<%@ include file="../includes/includes_admin.jsp"%>
<title>검수처리2</title>
 <script type="text/javascript">
 const carouselSlide = document.querySelector('.carousel-slide');
 const carouselImages = document.querySelectorAll('.carousel-slide img');

 const prevBtn = document.querySelector('#prevBtn');
 const nextBtn = document.querySelector('#nextBtn');

 let counter = 1;
 const size = carouselImages[0].clientWidth;
 carouselSlide.style.transform = 'translateX(' + (-size * counter) + 'px)';

 // Buttons
 nextBtn.addEventListener('click', ()=> {
   if(counter >= carouselImages.length -1) return;
   carouselSlide.style.transition = "transform 0.4s ease-in-out";
   counter++;
   carouselSlide.style.transform = 'translateX(' + (-size * counter) + 'px)';
 });

 prevBtn.addEventListener('click', ()=> {
   if(counter <= 0) return;
   carouselSlide.style.transition = "transform 0.4s ease-in-out";
   counter--;
   carouselSlide.style.transform = 'translateX(' + (-size * counter) + 'px)';
 });

 // Jump to First/Last Slide
 carouselSlide.addEventListener('transitionend', () => {
   console.log(carouselImages[counter]);
   if (carouselImages[counter].id === 'lastClone'){
     carouselSlide.style.transition = 'none'; // 트랜지션 효과 없애기
     counter = carouselImages.length -2; // couter 초기화
     carouselSlide.style.transform = 'translateX(' + (-size * counter) + 'px)'; // 실제 마지막 이미지로 이동.
   } else if (carouselImages[counter].id === 'firstClone') {
     carouselSlide.style.transition = 'none';
     counter = carouselImages.length - counter; // couter 초기화
     carouselSlide.style.transform = 'translateX(' + (-size * counter) + 'px)';
   }
 });
 </script>
 
 <style type="text/css">
.carousel-container{
  width: 900px;
  margin: 30px auto;
  border: 0px solid #000;
  overflow: hidden;
  position: relative;
}
.carousel-slide{
  display: flex;
  width: 100%;
  height: 450px;
}
#prevBtn{
  position:absolute;
  top: 50%;
  left: 0;
  transform: translate(0%, -50%);
  width: 20px;
  height: 36px;
  background: url(img/carousel_prevBtn.png) no-repeat;
  text-indent: -9999px;
}
#nextBtn{
  position:absolute;
  top: 50%;
  right: 0;
  transform: translate(0%, -50%);
  width: 20px;
  height: 36px;
  background: url(img/carousel_nextBtn.png) no-repeat;
  text-indent: -9999px;
}
 </style>

</head>

<body id="page-top">
	<!-- 전체 페이지 -->
	<div id="wrapper">

		<!-- 사이드 메뉴 -->
		<%@include file="../includes/Admin_Sidebar.jsp"%>

		<!-- 메인 페이지 -->
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">

				<!-- Header-->
				<%@include file="../includes/header.jsp"%>

				<!-- 주요 내용 -->
				<div class="container-fluid">
				<div class="row">
					<!-- 공간 검수 카드 -->
					<div class="col-lg-6">
						<!-- 검수처리하기 글씨쪽 -->
						<div class="card shadow mb-4">
							<div class="card-header py-3">
								<h6 class="m-0 font-weight-bold text-primary">검수처리하기</h6>
							</div>
							<div class="card-body">

								<h4 class="mb-3 font-weight-bold">#검수신청코드 :TP123456</h4>

								<h2 class="mb-3 mt-2"># 기본정보</h2>
								<hr>
								<p>공간명 : 나랑 별 보러 갈래</p>
								<p>공간한줄소개 : 감성사진을원한다면여기로</p>
								<p>공간유형 : 파티룸</p>
								<p>공간주소 : 서울시 서대문구 창천동</p>
								<p>공간상세주소: 공간 상세주소를 쓰세요</p>
								<p>시설안내: 과도한 음주가무는 이웃에게 민폐입니다</p>
								<p>주의사항: 과한 파티는 철컹철컹</p>

								<h2 class="mb-3 mt-5"># 상세정보</h2>
								<hr>
								<p>최대 인원 : 6명</p>
								<p>최소 인원 : 2명</p>
								<p>공간 웹사이트 : www.lala.com</p>

								<h2 class="mb-3 mt-5"># 패키지</h2>
								<hr>
								<p>패키지명 : 패키지명을 입력하세요</p>
								<p>패키지 시작시간 : 9시</p>
								<p>패키지 종료시간 : 10시</p>

								<h2 class="mb-3 mt-5"># 연락처</h2>
								<hr>
								<p>휴대폰 : 010-1234-1234</p>
								<p>대표 전화 : 070-1234-1234</p>
								<p>이메일 : sist0106@naver.com
								<h2 class="mb-3 mt-5"># 사업자정보</h2>
								<hr>
								<p>상호명 :</p>
								<p>대표자명 :</p>
								<p>사업자유형 :</p>
								<p>주업태 :</p>
								<p>주종목 :</p>
								<p>사업장 주소 :</p>
								<p>사업자등록번호 :</p>
								<p>사업자등록증 : 사진</p>

								<h2 class="mb-3 mt-5"># 이용안내</h2>
								<hr>
								<p>이용시간 :</p>
								<p>정기휴무:</p>
								<p>지정 휴무일 :</p>

							</div>
							<!-- card-body end -->
						</div>
						<!-- card shadow -->

						<div>
							<hr>
						</div>

						<button id="btn_submit" type="button" class="btn btn-primary">완료</button>
						<button id="btn_submit" type="button" class="btn btn-danger">반려</button>

					</div>
					<!-- 공간검수 카드 end -->
					
					<!-- 공간검수 이미지 -->
					<div class="col-lg-6">

              <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">공간검수 이미지</h6>
                </div>
                <div class="card-body">
                
                <!-- 커러셀 test -->
                
                <div class="carousel-container">
  <div class="carousel-slide">
    <img src="images/image_1.jpg" id="lastClone">
    <img src="images/image_1.jpg">
    <img src="images/image_2.jpg">
    <img src="images/image_3.jpg">
    <img src="images/image_4.jpg">
    <img src="images/image_5.jpg">
    <img src="images/image_6.jpg" id="firstClone">
  </div>
  <button id="prevBtn">Prev</button>
  <button id="nextBtn">Next</button>
</div>

    <!-- 커러셀 test end -->
 
                 
                <!-- card-body end -->
              </div>

            </div>
            <!-- 공간검수이미지 end -->

          </div>
          <!-- row end -->

				</div>
				<!-- 주요 내용 container-fluid end -->
			</div>
			<!-- content end -->

	<br>

	<!-- Footer -->
	<footer class="sticky-footer bg-white">
		<div class="container my-auto">
			<div class="copyright text-center my-auto">
				<span>Copyright &copy; Your Website 2020</span>
			</div>
		</div>
	</footer>
	<!-- End of Footer -->

	</div>
	<!-- 메인페이지 content-wrapper end -->
	</div>
	<!-- 전체 페이지 end -->


</body>

</html>
