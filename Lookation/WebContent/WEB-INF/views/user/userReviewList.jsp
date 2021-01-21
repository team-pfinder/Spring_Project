<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lookation</title>
<c:import url="${cp}/includes/header_user.jsp"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">


table{
width: 100%;
table-layout: fixed;
}

table thead tr{
width: 100%;
}


td {
	text-overflow : ellipsis; 
	overflow : hidden; 
	white-space : nowrap;
	block: inline-block;
}

tr > td > a {
	color: gray;
}

</style>
<script type="text/javascript">


$(function(){

	// 이용자 리뷰 수정하는 팝업
	$(".modifyreview").click(function()
	{
		var url = "modifyformreview.action?identify=member&review_code=" + $(this).val() + "&reqpage=list";
		var option = "width=450, height=400, resizable=no, scrollbars=yes, status=no";
		window.open(url, "", option);
	}); 

	// 이용자 리뷰 삭제하는 팝업 
	$(".deletereview").click(function()
	{
		if(confirm("삭제하시겠습니까?"))
		{
			$(location).attr("href", "deletereviewinlist.action?review_code=" + $(this).val());
		}
	}); 
	
});
</script>

</head>
<body>

<section class="hero-wrap hero-wrap-2" style="background-image: url('<%=cp%>/images/bg_3.jpg');" data-stellar-background-ratio="0.5">
  	<div class="overlay"></div>
  	<div class="container">
    	<div class="row no-gutters slider-text align-items-end">
      		<div class="col-md-9 ftco-animate pb-5">
      			<p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.html">Home <i class="ion-ios-arrow-forward"></i></a></span> <span><a href="myPage.html"> My Page <i class="ion-ios-arrow-forward"></i></a></span>
      			</p>
        		<h1 class="mb-0 bread">리뷰</h1>
      		</div>
    	</div>
  	</div>
</section>

<!-- container 시작 -->
<!-- 마이페이지에서 작성글 리스트 > Q&A 선택해 들어온 모습 -->
<!-- ※ 수정해야할 부분 : 검색창, 버튼크기때문에 열 높이 달라지는 부분 -->
<!--  -->


<div class="container">
	<div class="row mt-4">
		<c:import url="${cp}/includes/mypage_Sidebar(user).jsp"></c:import>
		<div class="col-md-10">
			<!-- Page Heading -->
		<div class="memo">
			<h1 class="mb-2 text-gray-800">리뷰</h1>
			<p class="mb-4">
				<!-- 작성자 닉네임 출력 -->
				<span class="text-primary font-weight-bold">${nickName.member_nickname }</span>님의 작성글입니다.공간명 클릭시
					해당 게시글이 있는 위치로 이동합니다.
			</p>
		</div><!-- End .memo -->

			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-default">작성글보기(리뷰)</h6>
				</div>
				<div class="card-body">
					<div class="table-responsive align-self-center">
						<table class="table" id="dataTable">
							<colgroup>
								<col style="width: 25%">
								<col style="width: 10%">
								<col style="width: 35%">
								<col style="width: 15%">
								<col style="width: 15%">
							</colgroup>
							
							<!-- 예약정보 조회 및 검색 -->
							<thead class="text-center">
								<tr>
									<th>공간명</th>
									<th>별점</th>
									<th>내용</th>
									<th>작성일</th>
									<th></th>
								</tr>
							</thead>
							<tbody data-link="row" class="rowlink text-center">
								<c:forEach var="review" items="${reviewList }">
								
									<c:if test="${review.removecount ne 1 }">
										<tr>
											<!-- 공간이름 클릭시 이동 -->
											<td><a href="#">${review.loc_name }</a></td>
											<td><span class="icon-star mr-1 text-warning"></span>${review.review_rate }점</td>
											<td title="${review.review_content }">${review.review_content }</td>
											<!-- 날짜 YYYY-MM-DD 형태로 자름 -->
											<td>${fn:substring(review.review_date, 0, 10)}</td>
											<td>
												<button type="button" class="btn-xs btn-warning rounded border-0 modifyreview" value="${review.review_code }">수정</button> 
												<button type="button" class="btn-xs btn-danger rounded border-0 deletereview" value="${review.review_code }">삭제</button>
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div><!-- End .cardBody -->
			</div><!-- End .card -->
		
		</div><!-- End .col-md-10 -->
		
	</div><!-- End .row -->
		
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
	<br><br><br><br>
	
	
</div><!-- .container End -->	

<c:import url="${cp}/includes/footer_user.jsp"></c:import>
<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</body>
</html>