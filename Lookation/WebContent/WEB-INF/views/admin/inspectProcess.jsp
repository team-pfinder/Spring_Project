<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>

<c:import url="${cp}/includes/includes_admin.jsp"></c:import>

<!-- 커러셀 사용-->
<link rel="stylesheet" href="../css/owl.carousel.min.css">
<link rel="stylesheet" href="../css/owl.theme.default.min.css">
<script src="../js/jquery.min.js"></script>
<script src="../js/owl.carousel.min.js"></script>



<title>검수처리</title>



<script type="text/javascript">
$(document).ready(function()
	{
		var owl = $('.owl-carousel');

		owl.owlCarousel(
		{
			items : 1, // 한번에 보여줄 아이템 수
			loop : true, // 반복여부
			margin : 35, // 오른쪽 간격
			autoplay : true, // 자동재생 여부
			autoplayTimeout : 1800, // 재생간격
			autoplayHoverPause : true //마우스오버시 멈출지 여부
		});

	/* 	$('.customNextBtn').click(function()
		{
			owl.trigger('next.owl.carousel');
		})

		$('.customPrevBtn').click(function()
		{
			owl.trigger('prev.owl.carousel',
			[ 300 ]);
		}) */
	});
</script>

<style type="text/css">
.owl-carousel 
{
	display: inline-block;
	overflow: hidden;
	object-fit: cover;
	border-radius: 5px;
}
</style>

</head>

<body id="page-top">
	<!-- 전체 페이지 -->
	<div id="wrapper">

		<!-- 사이드 메뉴 -->
<c:import url="${cp}/includes/Admin_Sidebar.jsp"></c:import>

		<!-- 메인 페이지 -->
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">

				<!-- Header-->
<c:import url="${cp}/includes/header.jsp"></c:import>

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

									<h4 class="mb-3 font-weight-bold">#검수신청코드 ${dto1.inspect_reg_code }</h4>

									<h2 class="mb-3 mt-2"># 기본정보</h2>
									<hr>
									<p>공간명 : ${dto1.loc_name}</p>
									<p>공간한줄소개 : ${dto1.loc_short_intro}</p>
									<p>공간유형 : ${dto1.loc_type}</p>
									<p>공간주소 : ${dto1.loc_addr}</p>
									<p>공간상세주소: ${dto1.loc_detail_addr}</p>
									<p>공간소개: ${dto1.loc_intro} </p>
									<p>시설안내: ${dto3.facility_content}</p>
									<p>주의사항: ${dto4.caution_content}</p>

									<h2 class="mb-3 mt-5"># 상세정보</h2>
									<hr>
									<p>최대 인원 : ${dto1.max_people}</p>
									<p>최소 인원 : ${dto1.min_people}</p>
									<p>공간 웹사이트 : ${dto5.loc_web_url}</p>

									<h2 class="mb-3 mt-5"># 패키지</h2>
									<hr>
									<p>패키지명 : ${dto2.package_name} </p>
									<p>패키지 시작시간 : ${dto2.package_start} </p>
									<p>패키지 종료시간 : ${dto2.package_end} </p>

									<h2 class="mb-3 mt-5"># 연락처</h2>
									<hr>
									<p>휴대폰 : ${dto1.loc_tel}</p>
									<p>대표 전화 : ${dto1.loc_main_tel}</p>
									<p>이메일 : ${loc_mail} <!-- 이메일을 불러오면 백지상태 -->
									
									<h2 class="mb-3 mt-5"># 사업자정보</h2>
									<hr>
									<p>상호명 : ${dto1.biz_name}</p>
									<p>대표자명 : ${dto1.biz_ceo}</p>
									<p>사업자유형 : ${dto1.biz_ceo_type}</p>
									<p>주업태 : ${dto1.biz_main_type}</p>
									<p>주종목 : ${dto1.biz_sub_type}</p>
									<p>사업장 주소 : ${dto1.biz_addr}</p>
									<p>사업자등록번호 : ${dto1.biz_license_number}</p>
									<p>사업자등록증 : 사진</p>

									<h2 class="mb-3 mt-5"># 이용안내</h2>
									<hr>
									<p>이용시간 : ${dto1.loc_use_hour}</p>
									<p>정기휴무: ${dto1.loc_use_day_off}</p>
									<p>지정 휴무일 : ${dto1.loc_use_appoint_day_off}</p>

								</div>
								<!-- card-body end -->
							</div>
							<!-- card shadow -->



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

									<div class="owl-carousel">
										<div>
											<img src="<%=cp%>/images/image_1.jpg">
										</div>
										<div>
											<img src="<%=cp%>/images/image_2.jpg">
										</div>
										<div>
											<img src="<%=cp%>/images/image_3.jpg">
										</div>
										<div>
											<img src="<%=cp%>/images/image_5.jpg">
										</div>
										<div>
											<img src="<%=cp%>/images/image_6.jpg">
										</div>
									</div>



									<!-- 커러셀 test end -->



								</div>
								<!-- card-body end -->


							</div>
							<!-- 공간검수이미지 end -->
							<div>
								<hr>
							</div>

							<button id="btn_submit" type="button"
								class="btn btn-success btn-block">완료</button>
							<br>
							<button id="btn_submit" type="button"
								class="btn btn-danger btn-block"">반려</button>
						</div>
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
