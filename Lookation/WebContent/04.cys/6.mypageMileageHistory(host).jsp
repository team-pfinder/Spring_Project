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
<%@ include file="../includes/includes_home.jsp"%>
<!-- include NAV -->
<%@ include file="../01.ksb/head(host).jsp"%>
<title>6.mypageMileageHistory(host).jsp</title>
<style type="text/css">
	/*
	.nav-pills .pill-1 .nav-link:not(.active) 
	{
	    background-color: #00043C;
	}
	
	// active (faded)
	.nav-pills .pill-1 .nav-link {
	    background-color: #FDBE34;
	    color: white;
	}
	.row
	{
		width:130%;
	}
	 */
	.nav-tabs .pill-1 .nav-link:not(.active)
	{
		background-color:#00043C;
		color:white;
	}
	
	/* active (faded) */
	.nav-tabs .pill-1 .nav-link {
		background-color: #FDBE34;
		color: black;
		font-weight: bold;
	}
	
	.row {
		width: 120%;
	}
</style>

</head>
<body>
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
								class="ion-ios-arrow-forward"></i></a></span> <span>Mileage History
							<i class="ion-ios-arrow-forward"></i>
						</span>
					</p>
					<h1 class="mb-0 bread">마일리지 내역</h1>
				</div>
			</div>
		</div>
	</section>
	<!-- END 타이틀 -->

	<!-- 본문 -->
	<section class="ftco-section ftco-degree-bg">
		<div class="container" style="margin-top: -80px">
			<div class="row">
				<!-- 사이드바 -->
				<!-- .col-md-8 -->
				<div class="col-lg-2 sidebar pl-lg-5 ftco-animate">
					<div class="sidebar-box ftco-animate">
						<div class="categories back-default">
							<h3>
								<a href="1.mypageMain.jsp">마이페이지</a>
							</h3>
							<hr>
							<li><a href="2.mypageProfile.jsp">프로필 관리 <span
									class="ion-ios-arrow-forward"></span></a></li>
							<li><a href="3.mypageBankAccount.jsp">계좌 관리 <span
									class="ion-ios-arrow-forward"></span></a></li>
							<li><a href="4.mypageReservaionList.jsp">예약 리스트 <span
									class="ion-ios-arrow-forward"></span></a></li>
							<li><a href="5.mypageMileageLoadAndWithdraw.jsp">충전 및 환전
									신청 <span class="ion-ios-arrow-forward"></span>
							</a></li>
							<li><a href="6.mypageMileageHistory.jsp">마일리지 내역 <span
									class="ion-ios-arrow-forward"></span></a></li>
							<li><a href="7.mypageMyReviewAndQ&A.jsp">내가 작성한 글 <span
									class="ion-ios-arrow-forward"></span></a></li>
						</div>
					</div>

				</div>
				<!-- END 사이드바 -->

				<div class="col-lg-10 ftco-animate">
					<nav class="navbar navbar-default navbar-fixed-top">
						<div>
							<ul class="nav nav-tabs">
								<li class="nav-item pill-1"><a class="nav-link active" data-toggle="tab"
									href="#first">정산내역</a></li>
								<li class="nav-item pill-1"><a class="nav-link" data-toggle="tab"
									href="#second">환전내역</a></li>
							</ul>
						</div>
					</nav>

					<!-- 탭 대가리 -->
					<div class="tab-content" style="margin-top: 10px;">
						<!-- 첫번째 탭 -->
						<div class="tab-pane in active" id="first">
							<p>조윤상님의 마일리지 정산 내역입니다.</p>
							<!-- 첫번째 카드 -->
							<div class="card shadow mb-4">						
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-default">정산내역</h6>
								</div>
								<!-- 카드바디 -->
								<div class="card-body">
									<form action="">
										<table class="table table-bordered">
											<tr>
												<th>번호</th>
												<th>공간명</th>
												<th>사용 마일리지</th>
												<th>잔여 마일리지</th>
												<th>사용일</th>
											</tr>
											<tr>
												<th colspan="5">정산 내역이 존재하지 않습니다.</th>
											</tr>
											<tr>
												<td>1</td>
												<td>어승승의 어승승승</td>
												<td>20000</td>
												<td>40000</td>
												<td>2020-12-31</td>
											</tr>
										</table>
									</form>
								</div>
							</div>
							<!-- 페이징 처리할 부분 -->
							<div class="row mt-5">
								<div class="col-3"></div>
								<div class="col-4 text-center">
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

						<!-- 두번째 탭 -->
						<div class="tab-pane" id="second">
							<p>조윤상님의 마일리지 환전 내역입니다.</p>
							<!-- 세번째 카드 -->
							<div class="card shadow mb-4">						
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-default">환전내역</h6>
								</div>
								<!-- 카드바디 -->
								<div class="card-body">
									<form action="">
										<table class="table table-bordered">
											<tr>
												<th>번호</th>
												<th>공간명</th>
												<th>사용 마일리지</th>
												<th>잔여 마일리지</th>
												<th>사용일</th>
											</tr>
											<tr>
												<th colspan="5">환전 내역이 존재하지 않습니다.</th>
											</tr>
											<tr>
												<td>1</td>
												<td>어승승의 어승승승</td>
												<td>20000</td>
												<td>40000</td>
												<td>2020-12-31</td>
											</tr>
										</table>
									</form>
								</div>
							</div>
							<!-- 페이징 처리할 부분 -->
							<div class="row mt-5">
								<div class="col-3"></div>
								<div class="col-4 text-center">
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
					</div>

				</div>
				<!-- END <div class="col-lg-10 ftco-animate"> -->

			</div>
			<!-- END <div class="row"> -->
		</div>
		<!-- END container -->
	</section>
	<!-- .section -->

	<!-- loader -->
	<%@ include file="../includes/includes_loader.jsp"%>

	<!-- includes_home_end -->
	<%@ include file="../includes/includes_home_end.jsp"%>

</body>
</html>