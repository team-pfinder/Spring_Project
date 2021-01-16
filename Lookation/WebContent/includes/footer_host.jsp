<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- head에서 include 하므로 X -->
<%-- <%@ include file="../includes/includes_home.jsp"%> --%>

<title>footer_host.jsp</title>

</head>
<body>

	<!-- 오른쪽 정렬 필요 -->
	<!-- <hr>
	<div>
		<button>이용약관</button>
		<button>개인정보처리방침</button>
		<button>운영정책</button>
	</div> -->
	<div class="footer">
		<div class="container-fluid px-lg-5" >
			<div class="col-md-9 py-5" style="float: none; margin:0 auto;">
				<div class="row">
					<div class="col-md-6 mb-md-0 mb-6">
						<h2 class="footer-heading"></h2>
						<p>룩케이션은 통신판매중개자이며 통신판매 당사자가 아닙니다. 따라서 스페이스클라우드는 공간 거래정보 및 거래에 대해 책임지지 않습니다.</p>
					</div>
					
					<div class="col-md-8">
						<div class="row justify-content-center">
							<div class="col-md-12 col-lg-10">
								<div class="row">
									<div class="col-md-4 mb-md-0 mb-4">
										<h2 class="footer-heading">About</h2>
										<ul class="list-unstyled">
											<li><a href="#" class="py-1 d-block">회사 소개</a></li>
											<li><a href="#" class="py-1 d-block">대표이사 소개</a></li>
										</ul>
									</div>
									<div class="col-md-4 mb-md-0 mb-4">
										<h2 class="footer-heading">Services</h2>
										<ul class="list-unstyled">
											<li><a href="#" class="py-1 d-block">서비스 소개</a></li>
											<li><a href="#" class="py-1 d-block">이용약관</a></li>
											<li><a href="#" class="py-1 d-block">개인정보 처리<br>방침</a></li>
											<li><a href="#" class="py-1 d-block">운영정책</a></li>
										</ul>
									</div>
									<div class="col-md-4 mb-md-0 mb-4">
										<h2 class="footer-heading">SNS</h2>
										<ul class="list-unstyled">
											<li><a href="#" class="py-1 d-block">네이버블로그</a></li>
											<li><a href="#" class="py-1 d-block">페이스북</a></li>
											<li><a href="#" class="py-1 d-block">트위터</a></li>
											<li><a href="#" class="py-1 d-block">인스타그램</a></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row mt-md-5">
					<div class="col-md-12">
						<p class="copyright">
							Team : pFinder | 문의 : lookation.notice@gmail.com
							| Copyright ©
							<script>document.write(new Date().getFullYear());</script>
							2020 All rights reserved
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>