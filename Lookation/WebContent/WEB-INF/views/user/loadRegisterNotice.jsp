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
<title>LoadRegisterNotice.jsp</title>
<c:import url="${cp }/includes/includes_home.jsp"></c:import>
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
				<h1><br><br><br>충전신청이 <b>완료</b> 되었습니다.</h1>
				<hr>
				<p style="font-size: 25px; text-align: left;">
				계좌이체로 결제시 <span>입금대기 후 48시간 이내 
				입금</span>해 주시지 않으면 <span style="color:red">결제 시스템 상 취소</span>로 진행됩니다.
				충전내역은 마이페이지 마일리지 내역에서 확인하실 수 있습니다.
				</p>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12">	
				<span style="text-align:left; font-size:23px">
				<b>결제 신청일</b> : ${loadInfo.regdate}<br>
				<b>결제 기한일</b> : ${loadInfo.expiredate}<br>
				<b>선택하신 결제 수단</b> : ${loadInfo.bankNumber} (${loadInfo.bank})<br>
				
				<b>룩케이션 계좌</b> : 554602-04-076182(국민은행)<br>
				<b>예금주</b> : 룩케이션조윤상<br>
				<b>결제 예정 금액</b> : ${loadInfo.amount}원 <br>
				</span>
				<hr><br>
				
				<div class="buttonForm">
					<button style="float: left; background: #ffffff" onclick="location.href='#' ">홈으로</button>
					<button style="float: right; border-width: 0px" onclick="location.href='mypageMain(user).jsp' ">
						<span style="font-color: black">마이페이지로</span>
					</button>
					<br><br><br><br><br><br>
				</div>
			</div>
		</div>
	</div>
	
	<!-- footer.jsp -->
	<div>
		<c:import url="${cp}/includes/footer_user.jsp"></c:import>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
	</div>
<script>

</script>

</body>
</html>