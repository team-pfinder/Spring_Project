<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	String identify = request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- include NAV -->
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<title>환전신청 완료</title>
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
				<p style="font-size: 25px; text-align:left">
				선택하신 계좌로 환전처리가 완료되었습니다. 환전내역은 마이페이지 
				마일리지 내역에서 확인하실 수 있습니다.   
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</p>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12">	
				<span style="text-align:left; font-size:23px">
				<b>환전 신청일</b> : ${exchangeInfo.regdate}<br>
				
				<b>환전 받으실 계좌</b> : ${exchangeInfo.bankNumber} (${exchangeInfo.bank})<br>
				<b>예금주</b> : ${exchangeInfo.bankHolder }<br>
				<b>예정 금액</b> : ${exchangeInfo.amount}원<br>
				</span>
				<hr><br>
				<c:if test="${identify eq 'member' }">
				<div class="buttonForm">
					<button style="float: left; background: #ffffff" onclick="location.href='membermain.action' ">홈으로</button>
					<button style="float: right; border-width: 0px" onclick="location.href='mypage.action?identify=member'">
						<span style="font-color: black">마이페이지로</span>
					</button>
					<br><br><br><br><br><br>
				</div>
				</c:if>
				<c:if test="${identify eq 'host' }">
				<div class="buttonForm">
					<button style="float: left; background: #ffffff" onclick="location.href='hostmain.action' ">홈으로</button>
					<button style="float: right; border-width: 0px" onclick="location.href='mypage.action?identify=host'">
						<span style="font-color: black">마이페이지로</span>
					</button>
					<br><br><br><br><br><br>
				</div>
				</c:if>
				
			</div>
		</div>
	</div>
			
	<!-- footer 출력부분 -->
	<div>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
	</div>

</body>
</html>