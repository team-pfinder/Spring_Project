<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약리스트확인페이지.jsp</title>
<c:import url="${cp}/includes/header_user.jsp"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
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

	/* function popCancel()
	{
		var url = "1-2_cancelReservation.jsp";
		var option = "width=450, height=400, resizable=no, scrollbars=yes, status=no";
		window.open(url, "", option);
	}
	
	// 예약 상세보기 팝업
	function popDetails(){
         var url = "1-1_reservationDetails.jsp"; 
        var url = "1-1_reservationDetails.jsp";
        var name = "popup test";
        var option = "width = 850, height = 400, top = 100, left = 200, location = no"
        window.open(url, name, option);
    } */
	
    $(document).ready(function()
	{
    	// 예약 취소 팝업
    	$(".popCancel").click(function()
    	{
    		var url = "bookcancel.action?book_code=" + $(this).val();
    		var option = "width=450, height=500, resizable=no, scrollbars=yes, status=no";
    		window.open(url, "", option);
    	}); 
    	
    	
    	// 예약 상세보기 팝업
    	$(".popDetails").click(function()
    	{
    		var url = "bookdetails.action?book_code=" + $(this).val();
    		var option = "width=850, height=500, resizable=no, scrollbars=yes, status=no";
    		window.open(url, "", option);
    	}); 
    	
	})
	
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
		<c:import url="${cp}/includes/mypage_Sidebar(user).jsp"></c:import>
		<%-- <%@ include file="../../../includes/mypage_Sidebar(user).jsp"%> --%>
	
		<div class="col-lg-10 col-md-10">
			<!-- Page Heading -->
			<h1 class="mb-2 text-gray-800">예약 리스트</h1>
			<p class="my-1 ml-2"> <strong class="text-primary">진영은</strong>님의 예약 내역입니다. <a target="_blank" href="#">이전으로</a>.</p>
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
							<thead>
								<tr class="text-center">
									<th>예약내용</th>
									<th>공간명</th>
									<th>예약현황</th>
									<th>취소/상세보기</th> 
								</tr>
							</thead>
							<tbody class="text-center">
								<c:forEach var="book" items="${bookList }">
									<fmt:parseDate var="apply_date" value="${book.apply_date }" pattern="yyyy-MM-dd"/>
									<fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/>
										
									<tr>
										<td>${fn:substring(book.apply_date, 0, 10)} ${book.package_start}시 ~
										
										<c:choose>
										<c:when test="${book.package_end > 24}">
										익일 ${book.package_end-24 }시
										</c:when>
										<c:otherwise>
										${book.package_end }시
										</c:otherwise>
										</c:choose>
										, ${book.book_hour }시간</td>
										<td>${book.loc_name }</td>
										
										
										<c:choose>
											<c:when test="${apply_date < now} ">
												<td>이용완료</td>
												<td>
													<button type="button" value="${book.book_code}"
													class="btn py-1 px-1 mb-0 btn-danger border-0 rounded popCancel"
													disabled="disabled">
													취소
													</button>
													
											</c:when>
											
											<c:when test="${book.member_cancel eq 1 || book.host_cancel eq 1}">
												<td class="text-danger">예약취소</td>
												<td>
													<button type="button" value="${book.book_code}"
													class="btn py-1 px-1 mb-0 btn-danger border-0 rounded popCancel"
													disabled="disabled">
													취소
													</button>
													<button type="button" value="${book.book_code}" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded popDetails" >
													상세보기
													</button>
												</td>
											</c:when>
											
											<c:otherwise>
												<td class="text-gon">예약완료</td>
												<td>
													<button type="button" value="${book.book_code}"
													class="btn py-1 px-1 mb-0 btn-danger border-0 rounded popCancel"
													>
													취소
													</button>
													<button type="button" value="${book.book_code}" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded popDetails" >
													상세보기
													</button>
												</td>
											</c:otherwise>
											
										</c:choose>
										
									</tr>
								</c:forEach>
								<tr>
									<!-- <td>BC000002</td> -->
									<td>2021-01-01 14시 ~ 20시, 6시간</td>
									<td>최대20자</td>
									<td class="text-danger">예약취소</td>
									<td>
										<!-- 이미 예약취소된 예약/
										     예약취소기간이 지난 예약/이용완료된 예약
										     은 버튼 disabled 속성 부여 -->
										<button type="button" class="btn py-1 px-1 mb-0 btn-danger border-0 rounded popCancel"
										>
										취소</button>
										<button type="button" class="btn py-1 px-1 mb-0 btn-gon border-0 popDetails" 
										>
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
<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>
<div>
	<c:import url="${cp}/includes/footer_user.jsp"></c:import>
</div>
</body>
</html>