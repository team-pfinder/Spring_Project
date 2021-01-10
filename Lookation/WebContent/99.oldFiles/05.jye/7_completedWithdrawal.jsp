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
<title>탈퇴완료 공통.jsp</title>
<%-- <%@ include file="../01.ksb/head(user).jsp" %> --%>
<%-- <%@ include file="../includes/includes_home.jsp" %> --%>
<c:import url="${cp}/01.ksb/head(user).jsp"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">
.withdrawBox
	{
	    width: 648px;
	    margin: 0 auto; 
		float: none;
		padding: 32px;
	}
	
	
	.ybox .icon {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    background: #fdbe34;
    margin: 0 auto;
    margin-bottom: 20px;
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
					<div class="ybox w-100 text-center">
						<!-- 체크그림 가운데정렬 -->
						<div class="icon d-flex align-items-center justify-content-center">
							<span class="icon-check" style="color: white; font-size: 32px;"></span>
						</div>
						<div class="text mb-5">
							<h3 class="font-weight-bold">성공적으로 탈퇴되었습니다.</h3>
							<h6>그동안 이용해주셔서 감사합니다.</h6>
						</div>

						<div class="align-items-center">
							<button class="btn btn-dark" type="button">메인으로 돌아가기</button>
						</div>
					</div>
				
				</div><!-- End withdrawBox -->
				
			</div><!-- End .col-md-12 -->
		</div><!-- End .row  -->
		
	</div><!-- End .container -->
	
<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>	
<!-- Footer -->
<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>
<c:import url="${cp}/01.ksb/foot.jsp"></c:import>
</body>
</html>