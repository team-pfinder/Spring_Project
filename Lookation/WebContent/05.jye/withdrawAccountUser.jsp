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
<title>이용자탈퇴완료.jsp</title>
<%-- <%@ include file="../01.ksb/head(user).jsp" %> --%>
<%@ include file="../includes/includes_home.jsp" %>
</head>
<body>

	<div>
		<!-- Header -->
		<br><br><br><br><br><!-- 임시 개행 -->
	</div>

	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<!-- 체크그림 가운데정렬 -->
				<div class="dbox w-100 text-center">
					<!-- 체크그림 가운데정렬 -->
					<div class="icon d-flex align-items-center justify-content-center">
						<span class="fa fa-check"></span>
					</div>
					<div class="text">
						<h3 class="font-weight-bold">탈퇴하시겠습니까?</h3>
						<h6>회원가입시 입력한 모든 개인정보는
						서비스 부정이용을 막기 위해 탈퇴일로부터 최대 1년간 보관 후 파기되며,
						이는 재가입하더라도 복구되지
						않습니다.</h6>
					</div>
				</div>
			</div>
		</div><!-- End .row  -->
		
		<br><br>
		
		<div class="row">
			<div class="col-md-12">
				<hr>
				<!-- <a  role="button">메인 화면으로 돌아가기</a> -->
				<div class="text-center">
					<button class="btn btn-dark" type="button">취소하기</button>
					<button class="btn btn-primary" type="button">탈퇴하기</button>
				</div>

			</div><!-- End .col-md-12  -->
		</div><!-- End .row  -->
		
		
	</div><!-- End .container -->
	
	<!-- Footer -->

<!-- loader -->
<div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>

<%@ include file="../includes/includes_home_end.jsp" %>

<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>
</body>
</html>