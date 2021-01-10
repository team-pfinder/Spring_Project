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
<title>예약신청완료.jsp</title>
<%-- <%@ include file="../01.ksb/head(user).jsp" %> --%>
<c:import url="${cp}/01.ksb/head(user).jsp"></c:import>
<%@ include file="../includes/includes_home.jsp" %>
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
						<h6>결제내역은 회원정보에 등록된 이메일로 전송되었습니다.</h6>
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
						<span class="col-md-10">진영은</span>
					</div>
	
					<div class="form-inline form-group">
						<span class="col-md-2 font-weight-bold">연락처</span>
						<span class="col-md-10">010-1234-1234</span>
					</div>
	
					<div class="form-inline form-group">
						<span class="col-md-2 font-weight-bold">이메일</span>
						<span class="col-md-10">papajon@lookation.com</span>
					</div>
	
					<div class="form-inline form-group">
						<span class="col-md-2 font-weight-bold">인원수</span>
						<span class="col-md-10">3명</span>
					</div>
	
					<div class="form-inline form-group">
						<span class="col-md-2 align-self-start font-weight-bold">요청사항</span>
						<span class="col-md-10">고통을 멈춰주세요</span>
					</div>
					
					<div class="form-inline form-group">
						<span class="col-md-2 font-weight-bold">결제금액</span>
						<span class="col-md-10">200,000</span>
					</div>
				
				<!-- <a  role="button">메인 화면으로 돌아가기</a> -->
				<div class="text-center">
					<button class="btn btn-primary" type="button">메인 화면으로 돌아가기</button>
				</div>
			
			</div><!-- End .col-md-12  -->

		</div><!-- End .row  -->
		
		
	</div><!-- End .container -->

<%@ include file="../includes/includes_home_end.jsp" %>


<!-- Footer -->
<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>
<div>
	<c:import url="${cp}/includes/footer.jsp"></c:import>
</div>

</body>
</html>