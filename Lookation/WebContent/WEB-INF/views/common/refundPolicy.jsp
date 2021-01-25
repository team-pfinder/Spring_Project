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
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>refundPolicy.jsp</title>

<style>
	
	div.container div
	{
		font-size: 10pt;
	}
	
	div.container  p
	{
		color: #fdbe34;
		font-weight: bold;
		font-size: 15pt;
	}
	
	.inner
	{
		float: none;
		margin: 100px auto;
	}

</style>

<!-- css 등 공통적으로 들어가는 요소 include -->
<c:import url="${cp }/includes/includes_home.jsp"></c:import>

</head>
<body>

	<!-- header 출력부분 -->
<div>
		<c:if test="${identify eq 'host' }">
			 <c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=${info.nick }"></c:import>
		</c:if>
		<c:if test="${identify eq 'member' }">
			 <c:import url="${cp}/includes/header_user.jsp?result=${result }&nick=${info.nick }"></c:import>
		</c:if>
</div>

	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('<%=cp%>/images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"><a href="termsofservice.action?identify=<%=identify %>">Services
						<i class="ion-ios-arrow-forward"></i></a></span>
					</p>
					<h1 class="mb-0 bread">정산 및 환불정책</h1>
				</div>
			</div>
		</div>
	</section>

	<!-- 정책 본문 -->
	<div class="container">
		<div class="inner">
			<div>
				본 정책은 Lookation 회원 이용약관에 따라 작성되었으며 본 정책에서
				사용되는 용어의 정의는 Lookation 회원 이용약관과 동일합니다.
			</div>
			<br>
			<div>
				<p>1. 정산</p>
				- 이용자는 Lookation을 이용하기 위해 충전한 마일리지를 다시 환전받을 수 있습니다.
				환전 가능 최소금액은 1,000원 부터 가능합니다. <br>
				- 호스트는 이용자가 공간을 이용완료 하였을 때 수수료 10%를 제외한 마일리지가
				자동으로 들어오게 되며, 이 마일리지를 환전받을 수 있습니다. 이용자와 마찬가지로
				환전 가능 최소금액은 1,000원입니다.
				- 환전 신청 시 미리 입력해둔 계좌번호를 선택할 수 있습니다. 계좌 정보가 입력이 되어있지
				않은 경우에는, 계좌를 추가한 후에 환전을 받을 수 있습니다. 최대 3개의 계좌를 입력할 수 있으며
				환전 받을 때 계좌를 선택할 수 있습니다.<br>
				- 마일리지가 남아있는 경우에는 회원 탈퇴를 진행할 수 없으므로, 정산을 받은 후 진행해야합니다.
				이는 블랙리스트로 지정된 회원도 적용되는 부분입니다.
			</div>
			<br>
			
			<div>
				<p>2. 환불</p>
				- 이용자는 예약한 공간을 이용 전날 23:59까지 취소를 할 수 있으며 아래의 환불 규정에
				따라 마일리지를 환불 받을 수 있게 됩니다. 환불 규정은 이용일을 기준으로 합니다.<br>
				<br>
				(i) ~7일 전 : 100% 환불 <br>
				(ii) 6 ~ 1일 전 : 50% 환불 <br>
				(iii) 예약 당일 : 환불 불가 (취소 불가) <br>
				(iv) 예외 : 호스트가 예약을 취소한 경우 : 100% 환불<br>
				<br>
				- 호스트는 이용자와는 다르게 당일 갑작스런 상황으로 공간을 이용할 수 없을 상황을 고려하여
				당일 이용시작 시각 전까지 취소를 할 수 있습니다.
				(단, 계속되는 당일 취소와 신고가 누적되면 블랙리스트로 지정될 수 있음)
				<br>
				-만일 이용자의 예약 취소를 한 경우, 호스트는 <br>
				(결제금액 * (1-환불비율))*0.9 만큼의 마일리지를 받을 수 있습니다.
			</div>
		</div>
	</div>

	<!-- footer 출력부분 -->
<div>
		<c:if test="${identify eq 'host' }">
			 <c:import url="${cp}/includes/footer_host.jsp"></c:import>
		</c:if>
		<c:if test="${identify eq 'member' }">
			 <c:import url="${cp}/includes/footer_user.jsp"></c:import>
		</c:if>
</div>
	
	
	<!-- 자바스크립트 include -->
	<c:import url="${cp }/includes/includes_home_end.jsp"></c:import>

</body>
</html>