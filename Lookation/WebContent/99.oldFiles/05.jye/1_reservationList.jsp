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
<title>예약리스트확인페이지.jsp</title>
<%@ include file="../01.ksb/head(user).jsp" %>
<%@ include file="../includes/includes_home.jsp" %>
<style type="text/css">
	/* btn */
	.btn-gon {
		color: #FFF;
		background-color: #00043c;
	}
	
	.btn-gon:hover {
		color: #FFF;
		background-color: #00043cdb;
	}
	
	.text-gon {
		color: #00043c;
	}
</style>
<script type="text/javascript">

	function popCancel()
	{
		var url = "1-2_cancelReservation.jsp";
		var option = "width=450, height=400, resizable=no, scrollbars=yes, status=no";
		window.open(url, "", option);
	}
	
	// 예약 상세보기 팝업
	function popDetails(){
       /*  var url = "1-1_reservationDetails.jsp"; */
        var url = "1-1_reservationDetails.jsp";
        var name = "popup test";
        var option = "width = 850, height = 400, top = 100, left = 200, location = no"
        window.open(url, name, option);
    }

</script>

</head>
<body>

<section class="hero-wrap hero-wrap-2" style="background-image: url('<%=cp%>/images/bg_3.jpg');" data-stellar-background-ratio="0.5">
  	<div class="overlay"></div>
  	<div class="container">
    	<div class="row no-gutters slider-text align-items-end">
      		<div class="col-md-9 ftco-animate pb-5">
      			<p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.html">Home <i class="ion-ios-arrow-forward"></i></a></span> <span> My Page <i class="ion-ios-arrow-forward"></i></span></p>
        		<h1 class="mb-0 bread">예약 리스트</h1>
      		</div>
    	</div>
  	</div>
</section>

<!-- container 시작 -->
<!-- 마이페이지에서 예약내역 들어온 모습 -->
<!-- ※ 수정해야할 부분 : 검색창, 버튼크기때문에 열 높이 달라지는 부분 -->


<div class="container my-5">
	<div class="row">
		<!-- include mypage_Sidebar.jsp -->
		<%@ include file="../includes/mypage_Sidebar(user).jsp"%>
	
		<div class="col-lg-10 col-md-10">
			<!-- Page Heading -->
			<h1 class="mb-2 text-gray-800">예약 리스트</h1>
			<p class="my-1 ml-2"> <strong class="text-primary">진영은</strong>님의 예약 내역입니다. <a target="_blank" href="../04.cys/1.mypageMain(user).jsp">이전으로</a>.</p>
			<p class="mb-4 mt-1 ml-2">예약현황 클릭시 예약 상세내역을 확인할 수 있습니다.</p>
				
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-default">예약 리스트</h6>
				</div>
				<div class="card-body">
					<!-- 검색이나 정렬 관련 내용 -->
					<!-- <form action="#" class="subscribe-form">
						<div class="form-group d-flex my-2 pt-0 float-right">
							<input type="text" class="form-control" placeholder="검색어를 입력하세요.">
						    <input type="button" value="검색" class="submit">
						</div>
						<br>
					</form> -->
					
					<div class="table-flex">
						<table class="table table-bordered" id="dataTable">
							
							<!-- 예약정보 조회 및 검색 -->
							<thead>
								<tr class="text-center">
									<!-- 정렬 : 예약일자 최근순으로 -->
									<!-- <th>예약코드</th> -->
									<th>예약내용</th>
									<th>공간명</th>
									<!-- <th>예약자명</th> -->
									<th>예약현황</th>
									<th>취소/상세보기</th> 
								</tr>
							</thead>
							<tbody class="text-center">
								<tr>
									<!-- <td>BC000001</td> -->
									<td>2021-01-08 14시 ~ 20시, 6시간</td>
									<td>공간명은최소2자</td>
									<td class="text-gon">예약완료</td>
									<td>
										<!-- 전일 23:59 전까지는 예약취소 버튼 출력 -->
										<!-- 버튼 -->
										<button type="button" class="btn py-1 px-1 mb-0 btn-danger border-0 rounded"
										onclick="popCancel()">
										취소
										</button>
										<button type="button" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded" 
										onclick="popDetails()" >
										상세보기
										</button>
									</td>
								</tr>
								<tr>
									<!-- <td>BC000002</td> -->
									<td>2021-01-01 14시 ~ 20시, 6시간</td>
									<td>최대20자</td>
									<td class="text-danger">예약취소</td>
									<td>
										<!-- 이미 예약취소된 예약/
										     예약취소기간이 지난 예약/이용완료된 예약
										     은 버튼 disabled 속성 부여 -->
										<button type="button" class="btn py-1 px-1 mb-0 btn-danger border-0 rounded"
										onclick="popCancel()" disabled="disabled">
										취소</button>
										<button type="button" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded" 
										onclick="popDetails()" >
										상세보기
										</button>
									</td>
								</tr>
								<tr>
									<!-- <td>BC000003</td> -->
									<td>2020-12-08 11시 ~ 14시, 3시간</td>
									<td>공간명이 이렇게 길다면 어떻게 되나</td>
									<td>이용완료</td>
									<td>
										<button type="button" class="btn py-1 px-1 mb-0 btn-danger border-0 rounded"
										onclick="popCancel()" disabled="disabled">
										취소</button>
										<button type="button" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded" 
										onclick="popDetails()" >
										상세보기
										</button>
									</td>
								</tr>
								<tr>
									<!-- <td>BC000004</td> -->
									<td>2020-11-17 17시 ~ 20시, 3시간</td>
									<td>일이삼사오육칠팔구십일이삼사오육칠팔구십</td>
									<td>이용완료</td>
									<td>
										<button type="button" class="btn py-1 px-1 mb-0 btn-danger border-0 rounded"
										onclick="popCancel()" disabled="disabled">
										취소</button>
										<button type="button" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded" 
										onclick="popDetails()" >
										상세보기
										</button>
									</td>
								</tr>
								<tr>
									<!-- <td>BC000005</td> -->
									<td>2020-11-15 18시 ~ 24시, 6시간</td>
									<td>공간명 대충 이렇게 해도 정렬이 잘</td>
									<td>이용완료</td>
									<td>
										<button type="button" class="btn py-1 px-1 mb-0 btn-danger border-0 rounded"
										onclick="popCancel()" disabled="disabled">
										취소</button>
										<button type="button" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded" 
										onclick="popDetails()" >
										상세보기
										</button>
									</td>
								</tr>
								<tr>
									<!-- <td>BC000006</td> -->
									<td>2020-11-10 12시 ~ 16시, 4시간</td>
									<td>광천김따는곳</td>
									<td>이용완료</td>
									<td>
										<button type="button" class="btn py-1 px-1 mb-0 btn-danger border-0 rounded"
										onclick="popCancel()" disabled="disabled">
										취소</button>
										<button type="button" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded" 
										onclick="popDetails()" >
										상세보기
										</button>
									</td>
								</tr>
								<tr>
									<!-- <td>BC000007</td> -->
									<td>2020-10-22 14시 ~ 22시, 8시간</td>
									<td>공간명이라요</td>
									<td>이용완료</td>
									<td>
										<button type="button" class="btn py-1 px-1 mb-0 btn-danger border-0 rounded"
										onclick="popCancel()" disabled="disabled">
										취소</button>
										<button type="button" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded" 
										onclick="popDetails()" >
										상세보기
										</button>
									</td>
								</tr>
								<tr>
									<!-- <td>BC000008</td> -->
									<td>2020-09-27 12시 ~ 18시, 6시간</td>
									<td>공간명은최소2자</td>
									<td class="">이용완료</td>
									<td>
										<button type="button" class="btn py-1 px-1 mb-0 btn-danger border-0 rounded"
										onclick="popCancel()" disabled="disabled">
										취소</button>
										<button type="button" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded" 
										onclick="popDetails()" >
										상세보기
										</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div><!-- End .table-flex -->
				</div><!-- End .card-body -->
			</div><!-- End .card shadow mb-4 -->
			
			
			<!-- 페이징 처리할 부분 -->
	        <div class="row mt-5">
	          <div class="col text-center">
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
		</div><!-- End .col-md-10 -->
	
	<!-- Scroll to Top Button -->
	<!-- <a class="scroll-to-top rounded" href="#page-top">
	  <i class="fas fa-angle-up"></i>
	</a> -->
	
	</div><!-- End .row -->
	
	

</div><!-- End .container -->

<%@ include file="../includes/includes_home_end.jsp" %>
<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>
<div>
	<c:import url="${cp}/includes/footer_user.jsp"></c:import>
</div>
</body>
</html>