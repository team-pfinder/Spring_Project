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
<title>TermsofUse.jsp</title>

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
					<h1 class="mb-0 bread">이용약관</h1>
				</div>
			</div>
		</div>
	</section>

	<!-- 약관 본문 -->
	<div class="container" style="margin: 10% 10% 10% 10%;">
		<div>
			<p>제 1 조 (목적)</p>
			이 약관은 주식회사 Lookation가 제공하는 Lookation(http://www.lookation.com)온라인 서비스
			(이하 "서비스")의 이용과 관련하여 회사와 회원과의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을
			목적으로 한다.
		</div>
		<br>
		<div>
			<p>제 2 조 (정의)</p>
			이 약관에서 사용하는 용어의 정의는 다음과 같다.
				1. "서비스"라 함은 유휴 공간(이하 "공간") 정보의 공유 및 예약을 위하여 "회사"가 Lookation을 통해
				제공하는 온라인 서비스를 일컫는다.
				2. "사이트"라 함은 "회사"가 "서비스"를 운영하는 웹사이트를 의미한다.
				현재는 https://www.lookation.com 이다.
		</div>
		
		후략...
		
	</div>
	
	<!-- 경로변경 필요 -->
	<%@ include file="../includes/footer.jsp" %>
	
	
	<!-- 자바스크립트 include -->
	<%@ include file="../includes/includes_home_end.jsp"%>

</body>
</html>