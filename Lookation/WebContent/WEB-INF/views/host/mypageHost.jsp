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
<!-- include head -->

<title>마이페이지</title>
</head>
<body>
	<!-- header 출력부분 -->
	<div>
		<c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=${info.nick }"></c:import>
	</div>

	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('<%=cp%>/images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"><a href="index.html">Home <i
								class="ion-ios-arrow-forward"></i></a></span> <span>Mypage <i
							class="ion-ios-arrow-forward"></i></span>
					</p>
					<h1 class="mb-0 bread">마이페이지</h1>
				</div>
			</div>
		</div>
	</section>

	<section class="ftco-section">
		<div class="container">
			<div class="row d-flex">
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="profile.action?identify=host" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/mypage/Profile.png'); background-size: auto 100%;">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="profile.action?identify=host">프로필관리</a>
							</h3>
						</div>
					</div>
				</div>
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="bankinfomanage.action?identify=host" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/mypage/BankAccount.png'); background-size: auto 100%;">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="bankinfomanage.action?identify=host">계좌관리</a>
							</h3>
						</div>
					</div>
				</div>
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="booklisthost.action" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/mypage/BookList.png'); background-size: auto 100%;">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="booklisthost.action">예약리스트</a>
							</h3>
						</div>
					</div>
				</div>

				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="loadandexchange.action?identify=host" class="block-20 rounded"
							style="background-image:url('<%=cp%>/images/mypage/MileageLoadAndExchange.png'); background-size: auto 100%;">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="loadandexchange.action?identify=host">환전 신청</a>
							</h3>
						</div>
					</div>
				</div>
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="mileagehistory.action?identify=host" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/mypage/MileageHistory.png'); background-size: auto 100%;">
						</a>
						<div class="text mt-3 text-center">
							<div class="meta mb-2"></div>
							<h3 class="heading">
								<a href="mileagehistory.action?identify=host">마일리지 내역(정산/환전)</a>
							</h3>
						</div>
					</div>
				</div>
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="locationlist.action" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/mypage/Location.png'); background-size: auto 100%;">
						</a>
						<div class="text mt-3 text-center">
							<div class="meta mb-2"></div>
							<h3 class="heading">
								<a href="locationlist.action">공간관리</a>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- footer.jsp -->
	<div>
		<c:import url="${cp}/includes/footer_host.jsp"></c:import>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
	</div>


</body>
</html>