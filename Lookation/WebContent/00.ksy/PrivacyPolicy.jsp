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
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>PrivacyPolicy.jsp</title>

<style>
	
	div.container  p
	{
		color: #fdbe34;
		font-weight: bold;
	}

</style>

<!-- css 등 공통적으로 들어가는 요소 include -->
<%@ include file="../includes/includes_home.jsp"%>

</head>
<body>

	<!-- 경로변경 필요 -->
	<!-- Lookation 로고 및 메뉴 include -->
	<%@ include file="../includes/header_user.jsp" %>

	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"><a href="index.html">Services
						<i class="ion-ios-arrow-forward"></i></a></span>
					</p>
					<h1 class="mb-0 bread">개인정보처리방침</h1>
				</div>
			</div>
		</div>
	</section>

	<!-- 방침 본문 -->
	<div class="container" style="margin: 10% 10% 10% 10%;">
		<div>
			Lookation(이하 "회사")는 정보통신망이용촉진 및 정보보호 등에 관한 법률(이하 '정보통신망법') 등
			에 따라 회원의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여
			다음과 같이 개인정보처리방침을 수립하고, 공개한다.
		</div>
		<br>
		<div>
			<p>제 1 조 (개인정보 수집 항목 및 방법)</p>
			1. 회사는 이용자/호스트 회원 가입 시 아래와 같은 개인정보를 수집한다.<br>
				- 이용자는 회원가입하는 과정에서 성명, 이메일 주소, 휴대폰 번호 (필수적 수집정보)를 수집한다.<br>
				- 마일리지를 충전/환전하고, 예약하는 과정에서 예약정보(성명, 이메일주소, 휴대폰번호), 결제정보
				(계좌번호)를 수집한다.<br>
				- 호스트 회원의 경우, 회원가입 직후 공간 등록 과정에서 필수항목으로 어쩌고...<br>
		</div>
		
		후략...
		
	</div>

	<!-- 경로변경 필요 -->
	<%@ include file="../includes/footer.jsp" %>
	
	
	<!-- 자바스크립트 include -->
	<%@ include file="../includes/includes_home_end.jsp"%>

</body>
</html>