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
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">
.withdrawBox
	{
	    width: 648px;
	    margin: 0 auto; 
		float: none;
		padding: 32px;
	}

</style>
</head>
<body>

	<div>
		<!-- Header -->
		<br><br><br><br><br><!-- 임시 개행 -->
	</div>

	<div class="container mb-5">
		<div class="row mb-5">
			<div class="col-md-12 mb-5">
				<div class="withdrawBox">
					<!-- 체크그림 가운데정렬 -->
					<div class="dbox w-100 text-center">
						<!-- 체크그림 가운데정렬 -->
						<div class="icon d-flex align-items-center justify-content-center">
							<span class="fa fa-check"></span>
						</div>
						<div class="text mb-5">
							<h3 class="font-weight-bold">탈퇴하시겠습니까?</h3>
							<h6>회원가입시 입력한 모든 개인정보는
							서비스 부정이용을 막기 위해 탈퇴일로부터 최대 1년간 보관 후 파기되며,
							이는 재가입하더라도 복구되지
							않습니다.</h6>
						</div>

						<div class="align-items-center">
							<button class="btn btn-primary mx-1" type="button">취소하기</button>
							<!-- 탈퇴 버튼 클릭시 탈퇴완료 alert창 호출,  -->
							<!-- 자동으로 메인 화면으로 돌아가기 -->
							<button class="btn btn-danger mx-1" type="button">탈퇴하기</button>
						</div>
					</div>
				
				</div><!-- End withdrawBox -->
				
			</div><!-- End .col-md-12 -->
		</div><!-- End .row  -->
		
	</div><!-- End .container -->
	
	

<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>	
	
</body>
</html>