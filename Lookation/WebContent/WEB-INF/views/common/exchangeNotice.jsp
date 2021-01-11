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
<!-- include NAV -->
<div>
	<c:import url="${cp}/includes/includes_home.jsp"></c:import>
</div>

<title>5-1.withdrawGuide.jsp</title>
<style type="text/css">
button
    {
        border-style: ridge;
        border-width: thin;
        border-color: silver;
        font-size: 17px;
        font-weight: bold;
        background: #fdbe34;
        width:49%;
        height: 75px;
    }
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12">
				<div class="dbox" style="margin-top: 150px;">
                <div class="icon d-flex align-items-center justify-content-center">
                      <span class="fa fa-check"></span>
                </div>
            	</div>
            </div>
            <div style="overflow:hidden; text-align:center;margin-top:-140px">
				<h1><br><br><br>환전신청이 <b>완료</b> 되었습니다.</h1>
				<hr>
				<p style="font-size: 25px">
				<span>환전신청 후 48시간 이내 관리자가 입금</span>하지 않으면 
				<span>결제 시스템 상 취소</span>로 진행됩니다. 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</p>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12">	
				<span style="text-align:left; font-size:23px">
				<b>환전 신청일</b> : 2021-01-06 18:21<br>
				<b>기한일</b> : 2021-01-08 18:21<br>
				
				<b>환전 하실 계좌</b> : 554602-04-076182(국민은행)<br>
				<b>예금주</b> : 김손님<br>
				<b>예정 금액</b> : 450,000원<br>
				</span>
				<hr><br>
				
				<div class="buttonForm">
					<button style="float: left; background: #ffffff" onclick="location.href='#' ">홈으로</button>
					<button style="float: right; border-width: 0px" onclick="location.href='1.mypageMain(user).jsp' ">
						<span style="font-color: black">마이페이지로</span>
					</button>
					<br><br><br><br><br><br>
				</div>
			</div>
		</div>
	</div>
			
	<!-- footer.jsp -->
	<div>
		<c:import url="${cp}/includes/footer.jsp"></c:import>
	</div>

	<!-- includes_home_end -->
	<div>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
	</div>

</body>
</html>