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
<title>5.mypageMileageLoadAndWithdraw.jsp</title>

<script type="text/javascript">
	function add10000()
	{
		document.getElementById("chargeAmount").value = parseInt(document
				.getElementById("chargeAmount").value) + 10000;
	}
	function add50000()
	{
		document.getElementById("chargeAmount").value = parseInt(document
				.getElementById("chargeAmount").value) + 50000;
	}
	
	function calc()
	{
		var cr_mileage = document.getElementById("cr_mileage").value;
		var ex_amount1 = document.getElementById("ex_amount1").value;
		var ex_remained = document.getElementById("ex_remained").value;

		ex_remained = cr_mileage - ex_amount1;
		
		if(ex_remained < 0)
		{
			alert("너 돈 없 음");
			return false;
		}
		document.getElementById("ex_remained").value = ex_remained;
		document.getElementById("ex_amount2").value = ex_amount1;
		
	}
	
	function setTotal()
	{
		document.getElementById("ex_amount1").value = document.getElementById("cr_mileage").value;
		document.getElementById("ex_remained").value = 0;
	}
	
</script>

<style type="text/css">
   .nav-tabs .pill-1 .nav-link:not(.active)
   {
      background-color:#00043C !important;
      color:white !important;
   }

   /* active (faded) */
   .nav-tabs .pill-1 .nav-link {
      background-color: #FDBE34 !important;
      color: black !important;
      font-weight: bold !important;
   }
</style>
</head>
<body>
	<!-- include header_user.jsp -->
	<div>
		<c:import url="${cp}/includes/header_user.jsp"></c:import>
	</div>
	
	<!-- 타이틀 -->
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"> <a href="index.html">Home <i
								class="ion-ios-arrow-forward"></i></a>
						</span> <span class="mr-2"><a href="1.mypageMain.jsp">Mypage <i
								class="ion-ios-arrow-forward"></i></a></span> <span>Mileage Load &
							Withdraw <i class="ion-ios-arrow-forward"></i>
						</span>
					</p>
					<h1 class="mb-0 bread">마일리지 충전 & 환전</h1>
				</div>
			</div>
		</div>
	</section>
	<!-- END 타이틀 -->	

	<!-- 컨테이너 -->
	<section class="ftco-section ftco-degree-bg">
		<div class="container" style="margin-top: -80px">
			<div class="row">
				<!-- 사이드바 -->
				<!-- .col-md-2 -->
				<!-- include mypage_Sidebar.jsp -->
				<%@ include file="../includes/mypage_Sidebar(user).jsp"%>
				
				<!-- 본문 -->
				<div class="col-lg-10 ftco-animate">
					<!-- NAV 바 -->
					<nav class="navbar navbar-default navbar-fixed-top">
						<div>
							<ul class="nav nav-tabs">
								<li class="nav-item pill-1 active"><a class="nav-link active" data-toggle="tab" href="#first">충전하기</a></li>
								<li class="nav-item pill-1 "><a class="nav-link " data-toggle="tab" href="#second">환전하기</a></li>
							</ul>
						</div>
					</nav>
					
					<!-- 탭 구성 -->
					<div class="tab-content" style="margin-top: 0px; margin-left: 10px;">
						<!-- 첫번째 탭 -->
						<%@ include file="loadTabUser.jsp"%>
						
						<!-- 두번째 탭 -->
						<%@ include file="../common/exchangeTab.jsp"%>
					</div>
					<!-- END 탭구성 -->
				</div>
				<!-- END 본문 -->
			</div>
			<!-- END row -->
		</div>
		<!-- END 컨테이너 -->	
	</section>
	<!-- .section -->

	<!-- footer.jsp -->
	<div>
		<c:import url="${cp}/includes/footer_user.jsp"></c:import>
	</div>

	<div>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
	</div>

</body>
</html>