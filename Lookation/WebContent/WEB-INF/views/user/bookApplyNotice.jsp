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
<title>예약신청 완료</title>
<c:import url="${cp}/includes/header_user.jsp?result=${result }&nick=${info.nick }"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<script type="text/javascript">


	$(function()
	{
		$("#main").click(function()
		{
			$(location).attr("href","membermain.action?identify=member"+ $(this).val());
		});

	});
	
	// 새로고침 방지
	function noRefresh()
	{
	    /* CTRL + N키 막음. */
	    if ((event.keyCode == 78) && (event.ctrlKey == true))
	    {
	        event.keyCode = 0;
	        return false;
	    }
	    /* F5 번키 막음. */
	    if(event.keyCode == 116)
	    {
	        event.keyCode = 0;
	        return false;
	    }
	}
	document.onkeydown = noRefresh ;
	
</script>
</head>
<body>

	<div>
		<!-- Header -->
		<br><br><br><br><br><!-- 임시 개행 -->
	</div>

	<div class="container">
		<div class="row">
			<div class="col-md-12 py-5">
				<!-- 체크그림 가운데정렬 -->
				<div class="dbox w-100 text-center">
					<div class="icon d-flex align-items-center justify-content-center">
						<span class="fa fa-check"></span>
					</div>
					<div class="text">
						<h3 class="font-weight-bold">성공적으로 결제완료되었습니다.</h3>
					</div>
				</div>
			</div>
		</div><!-- End .row  -->
		
		<br><br>
		
		<div class="row pb-5">
			<div class="col-md-12 pb-5 mb-5">
				<h5 class="font-weight-bold">결제내역 확인</h5>
				<hr>
	
					<div class="form-inline form-group">
						<span class="col-md-2 font-weight-bold">예약자명</span>
						<span class="col-md-10">${notice.actual_booker}</span>
					</div>
	
					<div class="form-inline form-group">
						<span class="col-md-2 font-weight-bold">연락처</span>
						<span class="col-md-10">${notice.actual_booker_tel }</span>
					</div>
	
					<div class="form-inline form-group">
						<span class="col-md-2 font-weight-bold">선택한 패키지</span>
						<span class="col-md-10">${notice.package_name }</span>
					</div>
					
					<div class="form-inline form-group">
						<span class="col-md-2 font-weight-bold">예약시간</span>
						<span class="col-md-10">${notice.apply_date } ${notice.package_start }시 ~ ${notice.package_end }시</span>
					</div>
					
					<div class="form-inline form-group">
						<span class="col-md-2 font-weight-bold">인원수</span>
						<span class="col-md-10">${notice.book_people }</span>
					</div>
					
					<div class="form-inline form-group">
						<span class="col-md-2 font-weight-bold">결제금액</span>
						<span class="col-md-10">${notice.package_price }</span>
					</div>
					
					<div class="form-inline form-group">
						<span class="col-md-2 align-self-start font-weight-bold">요청사항</span>
						<span class="col-md-10" style="white-space:pre-line;"><c:out value="${notice.book_req }" /></span>
					</div>
				
				<!-- <a  role="button">메인 화면으로 돌아가기</a> -->
				<div class="text-center">
					<button class="btn btn-primary" type="button" id="main">메인 화면으로 돌아가기</button>
				</div>
			
			</div><!-- End .col-md-12  -->

		</div><!-- End .row  -->
		
		
	</div><!-- End .container -->

<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<c:import url="${cp}/includes/footer_user.jsp"></c:import>
</body>
</html>