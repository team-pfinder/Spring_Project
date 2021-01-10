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
<title>mypageReservaionList(host).jsp</title>
<style type="text/css">
</style>
</head>
<body>
	<!-- include header_host.jsp -->
	<div>
		<c:import url="${cp}/includes/header_host.jsp"></c:import>
	</div>
	
	<!-- 타이틀 -->
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('<%=cp%>/images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"><a href="index.html">Home <i
								class="ion-ios-arrow-forward"></i></a></span> <span> My Page <i
							class="ion-ios-arrow-forward"></i></span>
					</p>
					<h1 class="mb-0 bread">예약 리스트</h1>
				</div>
			</div>
		</div>
	</section>

	<!-- container 시작 -->
	<!-- 마이페이지에서 예약내역 들어온 모습 -->
	<!-- ※ 수정해야할 부분 : 검색창, 버튼크기때문에 열 높이 달라지는 부분 -->


	<div class="container" style="margin-top:50px" >
		<div class="row">
			<!-- include mypage_Sidebar.jsp -->
			<%@ include file="../includes/mypage_Sidebar(host).jsp"%>
			<br>
		
			<div class="col-lg-10 col-md-10">
				<!-- Page Heading -->
				<p class="mb-4">
					진영은님의 예약 내역입니다. <a target="_blank" href="#">이전으로</a>.
				</p>
	
				<div class="card shadow mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-default">예약 리스트</h6>
					</div>
					<div class="card-body">
						<!-- 검색이나 정렬 관련 내용 -->
						<div class="row">
							<div class="col-md-4 d-flex align-items-right">
								<form action="#" class="subscribe-form">
									<div class="form-group d-flex align-items-right">
										<input type="text" class="form-control"
											placeholder="검색어를 입력하세요."> <input type="button"
											value="검색" class="submit px-3">
									</div>
									<br>
								</form>
							</div>
		
						</div>
						<!-- End .row -->
		
						<div class="table-responsive">
							<table class="table table-bordered" id="dataTable" width="100%"
								cellspacing="0">
		
								<!-- 예약정보 조회 및 검색 -->
								<thead>
									<tr>
										<th>#</th>
										<!-- 번호순 -->
										<th>예약자명</th>
										<th>예약공간명</th>
										<th>예약일자</th>
										<th>예약인원수</th>
										<th>예약가격</th>
										<th>요청사항</th>
										<th>예약현황</th>
										<th>예약취소</th>
									</tr>
								</thead>
								<tbody>
									<tr align="center">
										<td>NNSJ1532</td>
										<td>진영은</td>
										<td>바다</td>
										<td>2021-01-01</td>
										<td>6</td>
										<td>200,000</td>
										<td>-</td>
										<td class="text-primary">예약완료</td>
										<td>
											<!-- 전일 23:59 전까지는 예약취소 버튼 출력 --> <!-- 버튼 -->
											<button type="button" class="btn-xs btn-danger border-0 round"
												data-toggle="modal" data-target="#myModal">취소</button> <!-- 해당 버튼 클릭시 id가 myModal인 팝업 실행시킴. -->
										</td>
									</tr>
									<tr align="center">
										<td>BBRD0428</td>
										<td>진영은</td>
										<td>산</td>
										<td>2021-03-14</td>
										<td>5</td>
										<td>100,000</td>
										<td>-</td>
										<td class="text-danger">예약취소</td>
										<td>
											<!-- 이미 예약취소된 예약에는 버튼 출력 X -->
										</td>
									</tr>
									<tr align="center">
										<td>JLDY0019</td>
										<td>진영은</td>
										<td>공간명이 이렇게 길다면 어떻게 되나</td>
										<td>2021-07-12</td>
										<td>4</td>
										<td>380,000</td>
										<td>-</td>
										<td>이용완료</td>
										<td>
											<!-- 이미 이용완료된 예약에는 버튼 출력 X -->
										</td>
									</tr>
									<tr align="center">
										<td>AAAA0128</td>
										<td>진영은</td>
										<td>산</td>
										<td>2021-04-05</td>
										<td>5</td>
										<td>100,000</td>
										<td>-</td>
										<td>이용완료</td>
										<td>
											<!-- 이미 예약취소된 예약에는 버튼 출력 X -->
										</td>
									</tr>
									<tr align="center">
										<td>AARD0128</td>
										<td>진영은</td>
										<td>어디가지</td>
										<td>2021-03-14</td>
										<td>3</td>
										<td>100,000</td>
										<td>-</td>
										<td>이용완료</td>
										<td>
											<!-- 이미 예약취소된 예약에는 버튼 출력 X -->
										</td>
									</tr>
									<tr align="center">
										<td>BARD1193</td>
										<td>광천김</td>
										<td>어디로가</td>
										<td>2021-02-14</td>
										<td>5</td>
										<td>120,000</td>
										<td>-</td>
										<td>이용완료</td>
										<td>
											<!-- 이미 예약취소된 예약에는 버튼 출력 X -->
										</td>
									</tr>
									<tr align="center">
										<td>VRTD1234</td>
										<td>진영은</td>
										<td>어디론가</td>
										<td>2021-01-01</td>
										<td>4</td>
										<td>150,000</td>
										<td>-</td>
										<td>이용완료</td>
										<td>
											<!-- 이미 예약취소된 예약에는 버튼 출력 X -->
										</td>
									</tr>
								</tbody>
							</table>
						</div>
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
		
				<!-- Scroll to Top Button -->
				<a class="scroll-to-top rounded" href="#page-top"> <i
					class="fas fa-angle-up"></i>
				</a>
			</div><!-- END col-10 -->
		</div>
	</div>
	<!-- .container End -->

	<!-- 모달창 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">예약 취소하기</h4>
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">Close</span>
					</button>
				</div>
				<div class="modal-body">
					<!-- 이전 페이지에서 해당 예약에 대한 데이터 받아와야 함. -->
					<form>
						<table class="table table-condensed">
							<tr>
								<th>예약코드</th>
								<td>NNSJ1532</td>
							</tr>
							<tr>
								<th>예약 공간명</th>
								<td>산</td>
							</tr>
							<tr>
								<th>예약자명</th>
								<td>진영은</td>
							</tr>

							<tr>
								<th>예약일자</th>
								<td>2021-01-01</td>
							</tr>
							<tr>
								<th>예약인원수</th>
								<td>4명</td>
							</tr>
							<tr>
								<th>예약가격</th>
								<td>200,000</td>
							</tr>
							<tr>
								<th>취소사유</th>
								<td><textarea class="form-control" required="required"
										maxlength="40"></textarea></td>
							</tr>
						</table>
					</form>

					<div class="row">
						<div class="col-md-12 form-group form-inline">
							<!-- 환불 정책에 맞춘 환불 가격 자동 계산 -->
							<small>이용시작일자 <span class="text-danger">4</span>일 전이므로 예약
								가격의 <span class="text-danger text-weight-bold">50%</span>인 <span
								class="text-primary">100,000</span>원이 마일리지로 환불됩니다. 취소한 예약은 되돌릴 수
								없습니다.
							</small> <br> <span class="text-center">정말 취소하시겠습니까?</span>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">취소하고 닫기</button>

					<!-- 확인 버튼 클릭시 예약취소테이블 insert -->
					<button type="button" class="btn btn-primary">확인</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- footer.jsp -->
	<div>
		<c:import url="${cp}/includes/footer_host.jsp"></c:import>
	</div>

	<!-- includes_home_end -->
	<%@ include file="../includes/includes_home_end.jsp"%>
	
</body>
</html>