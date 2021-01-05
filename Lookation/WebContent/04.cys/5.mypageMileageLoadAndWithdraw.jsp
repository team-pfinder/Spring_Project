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
<%-- <%@ include file="../includes/defaults.jsp"%> --%>

<title>5.mypageMileageLoadAndWithdraw.jsp</title>
<style type="text/css">
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
</head>
<body>
	<!-- nav -->
	<div class="container pt-5">
		<div class="row justify-content-between">
			<div class="col">
				<a class="navbar-brand" href="index.html">Home<span>builder.</span></a>
			</div>
			<div class="col d-flex justify-content-end">
				<div class="social-media">
					<p class="mb-0 d-flex">
						<a href="#"
							class="d-flex align-items-center justify-content-center"><span
							class="fa fa-facebook"><i class="sr-only">Facebook</i></span></a> <a
							href="#" class="d-flex align-items-center justify-content-center"><span
							class="fa fa-twitter"><i class="sr-only">Twitter</i></span></a> <a
							href="#" class="d-flex align-items-center justify-content-center"><span
							class="fa fa-instagram"><i class="sr-only">Instagram</i></span></a> <a
							href="#" class="d-flex align-items-center justify-content-center"><span
							class="fa fa-dribbble"><i class="sr-only">Dribbble</i></span></a>
					</p>
				</div>
			</div>
		</div>
	</div>
	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light"
		id="ftco-navbar">
		<div class="container">
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#ftco-nav" aria-controls="ftco-nav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="fa fa-bars"></span> Menu
			</button>
			<form action="#" class="searchform order-lg-last">
				<div class="form-group d-flex">
					<input type="text" class="form-control pl-3" placeholder="Search">
					<button type="submit" placeholder="" class="form-control search">
						<span class="fa fa-search"></span>
					</button>
				</div>
			</form>
			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item"><a href="index.html" class="nav-link">Home</a></li>
					<li class="nav-item"><a href="about.html" class="nav-link">About</a></li>
					<li class="nav-item"><a href="team.html" class="nav-link">Our
							team</a></li>
					<li class="nav-item"><a href="project.html" class="nav-link">Project</a></li>
					<li class="nav-item active"><a href="blog.html"
						class="nav-link">Blog</a></li>
					<li class="nav-item"><a href="contact.html" class="nav-link">Contact</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- END nav -->

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
				<!-- .col-md-8 -->
				<div class="col-lg-2 sidebar pl-lg-5 ftco-animate">
					<div class="sidebar-box ftco-animate">
						<div class="categories">
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
				
				<!-- 본문 -->
				<div class="col-lg-10 ftco-animate">
					<!-- NAV 바 -->
					<nav class="navbar navbar-default navbar-fixed-top">
						<div>
							<ul class="nav nav-tabs">
								<li class="nav-item pill-1 active"><a
									class="nav-link active" data-toggle="tab" href="#first">마일리지
										충전하기</a></li>
								<li class="nav-item pill-1"><a class="nav-link"
									data-toggle="tab" href="#second">마일리지 환전하기</a></li>
							</ul>
						</div>
					</nav>

					<!-- 탭 구성 -->
					<div class="tab-content" style="margin-top: 0px; margin-left: 10px;">
						<!-- 첫번째 탭 -->
						<div class="tab-pane in active" id="first">
							<!-- 첫번째 카드 -->
							<div class="card shadow mb-4">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-default">마일리지 충전</h6>
								</div>
								<!-- 카드바디 -->
								<div class="card-body">
									<form action="">
										<!-- 설명 -->
										<div>
											<p>마일리지는 최소 10,000원부터 충전이 가능합니다.</p>
										</div>
										<!-- 금액 선택 버튼 -->
										<div class="container">
											<div class="row">
												<div class="col-md-6">
													<table class="table">
														<tr>
															<th>
																<p>버튼을 눌러 금액을 입력해주세요</p> <input type="button"
																class="btn btn-primary" onclick="add10000()"
																value="10000원" /> <input type="button"
																class="btn btn-primary" onclick="add50000()"
																value="50000원" />
															</th>
															<th>충전할 마일리지<input type="text" class="form-control"
																value=0 id="chargeAmount" name="chargeAmount" disabled="disabled"/>
															</th>
														</tr>
														<tr>

														</tr>
														<!-- 이용자 계좌 선택 -->
														<tr>
															<th>계좌 선택</th>
															<td><select class="form-control" name="" id="">
																	<option value="">계좌선택</option>
																	<option value="">계좌1</option>
																	<option value="">계좌2</option>
																	<option value="">계좌3</option>
															</select></td>
														</tr>
														<tr>
															<th>예금주</th>
															<td><input type="text" class="form-control" name="accountName" /></td>
														</tr>
														<tr>
															<th colspan="2">
																<input type="checkbox" /> 이용약관 동의 <a href="약관...">[약관보기]</a>
																<div><br>
																	<button type="submit" class="btn btn-primary form-control">충전하기</button>
																</div>
															</th>
														</tr>
													</table>
													<br>

												</div>
												<!-- 오른쪽 요소 -->
												<div class="col-md-4">
												<div class="col-12 input-group">
														<table class="table">
															<tr>
																<th>충전 전 마일리지<input type="text" class="form-control"
																	value=0 id="chargeAmount" name="chargeAmount" />
																</th>
															</tr>
															<tr>
																<th>충전 후 마일리지<input type="text" class="form-control"
																	value=0 id="chargeAmount" name="chargeAmount" />
																</th>
															</tr>
														</table>
													</div>
												</div>
											</div>
										</div>	
									</form>
								</div>
								<!-- END 카드바디 -->
							</div>
							<!-- END 카드 -->
						</div>
						<!-- END 첫번째 탭 -->
						
						<!-- 두번째 탭 -->
						<div class="tab-pane" id="second">
							<!-- 두번째 카드 -->
							<div class="card shadow mb-4">
							<div class="card-header py-3">
								<h6 class="m-0 font-weight-bold text-default">마일리지 환전</h6>
							</div>
							<!-- 카드 바디 -->
							<div class="card-body">
								<!-- 설명 -->
								<div>
									<p>마일리지는 최소 1000원부터 환전이 가능합니다.</p>
								</div>

								<!-- 환전 금액 입력 -->
								<div>
									<p></p>
								</div>
	
								<div>
									<form action="">
										<table class="table">
											<tr>
												<th>
													<div class="row">
														<b class="col-12">환전하실 금액을 입력하세요.</b>
														<input type="text" class="form-control col-6 ml-3" id="ex_amount1" onkeyup="calc()" />
														<button type="button" class="btn btn-primary col-4 ml-3" onclick="setTotal()">전액 입력</button>
													</div>
												</th>
											</tr>
											<tr>
												<th>현재보유한 마일리지<input type="text" class="form-control" id="cr_mileage" value="54000" disabled="disabled" /></th>
											</tr>
											<tr>
												<th>계좌 선택
													<select class="form-control" name="" id="">
														<option value="">쿸민은행</option>
														<option value="">우리은행</option>
														<option value="">롱협</option>
													</select>
												</th>
											</tr>
											<tr>
												<th>환전후 잔여 마일리지<input type="text" class="form-control" id="ex_remained" disabled="disabled"/></th>
											</tr>
										</table>
										<button type="submit" class="btn btn-primary form-control">환전신청하기</button>
									</form>
								</div>
							</div>
							<!-- END 카드 바디 -->	
						</div>
						<!-- END 두번째 카드 -->
					</div>
					<!-- END 두번째 탭 -->
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
	
	
	<table class="table">
		<tr>
			<td>f</td>
			<td>f</td>
			<td>f</td>
		</tr>
		<tr>
			<td>f</td>
			<td>f</td>
			<td>f</td>
		</tr>
		<tr>
			<td>f</td>
			<td>f</td>
			<td>f</td>
		</tr>
	</table>

	<!-- loader -->
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
			<circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
			<circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>

	<!-- includes_home_end -->
	<%@ include file="../includes/includes_home_end.jsp"%>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

</body>
</html>