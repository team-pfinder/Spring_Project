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
<title>예약 신청화면.jsp</title>
<%-- <%@ include file="../01.ksb/head(user).jsp" %> --%>
<%@ include file="../includes/includes_home.jsp" %>
</head>
<body>

<!-- Header -->
<div class="container pt-5">
	<div class="row justify-content-between">
		<div class="col">
			<a class="navbar-brand" href="index.html">Look<span>ation.</span></a>
		</div>
		<div class="col d-flex justify-content-end">
			<div class="social-media">
	    		<p class="mb-0 d-flex">
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-facebook"><i class="sr-only">Facebook</i></span></a>
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-twitter"><i class="sr-only">Twitter</i></span></a>
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-instagram"><i class="sr-only">Instagram</i></span></a>
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-dribbble"><i class="sr-only">Dribbble</i></span></a>
	    		</p>
       		</div>
       </div>
	</div>
</div>

<nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
	<div class="container">
    	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="fa fa-bars"></span> Menu
      	</button>
		
		<form action="#" class="searchform order-lg-last">
        	<div class="form-group d-flex">
           		<input type="text" class="form-control pl-3" placeholder="Search">
           		<button type="submit" placeholder="" class="form-control search"><span class="fa fa-search"></span></button>
         	</div>
       	</form>
      	<div class="collapse navbar-collapse" id="ftco-nav">
        	<ul class="navbar-nav mr-auto">
        		<li class="nav-item"><a href="index.html" class="nav-link">Home</a></li>
        		<li class="nav-item"><a href="about.html" class="nav-link">About</a></li>
        		<li class="nav-item"><a href="team.html" class="nav-link">Our team</a></li>
        		<li class="nav-item"><a href="project.html" class="nav-link">Project</a></li>
        		<li class="nav-item active"><a href="blog.html" class="nav-link">Blog</a></li>
          		<li class="nav-item"><a href="contact.html" class="nav-link">Contact</a></li>
        	</ul>
      	</div>
    </div>
</nav>
<!-- END nav -->

<section class="hero-wrap hero-wrap-2" style="background-image: url('<%=cp%>/images/bg_3.jpg');" data-stellar-background-ratio="0.5">
  	<div class="overlay"></div>
  	<div class="container">
    	<div class="row no-gutters slider-text align-items-end">
      		<div class="col-md-9 ftco-animate pb-5">
      			<p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.html">Home <i class="ion-ios-arrow-forward"></i></a></span> <span>Space <i class="ion-ios-arrow-forward"></i></span></p>
        		<h1 class="mb-0 bread">선택한 공간 예약하기</h1>
      		</div>
    	</div>
  	</div>
</section>


<section class="ftco-section ftco-degree-bg">
	<div class="container">
		<div class="row">
			<div class="col-lg-8 contact-wrap w-100 p-md-5 p-4">
				<p>
					<img src="<%=cp%>/images/image_1.jpg" alt="템플릿 이미지" class="img-fluid">
				</p>


				<h2 class="mb-3 font-weight-bold">파파존스 호러존 스튜디오</h2><hr>
				
				<div class="form-inline form-group">
					<p class="col-md-2">공간설명</p>
					<p class="col-md-10">공간설명 들어갈 자리</p>
				</div>
				
				<div class="form-inline form-group">
					<p class="col-md-2">공간유형</p>
					<p class="col-md-10">파티룸</p>
				</div>
				
				<div class="form-inline form-group">
					<p class="col-md-2">예약인원</p>
					<p class="col-md-10">최소 1명 ~ 최대 8명</p>
				</div>

				<h2 class="mb-3 mt-5 font-weight-bold"># 예약 정보</h2><hr>
				
				<div class="form-inline form-group">
					<p class="col-md-2">예약날짜</p>
					<p class="col-md-10">2020. 12. 31. (목) 12시 ~ 15시</p>
				</div>
				
				<div class="form-inline form-group">
					<p class="col-md-2">예약인원</p>
					<p class="col-md-10">1명</p>
				</div>

				<h2 class="mb-3 mt-5 font-weight-bold"># 예약자 정보</h2><hr>
				<!-- .contactForm 설정시 form-control 선 사라지는 css 있어서 이렇게 함 -->
				<form class="contactForm ftco-animate">
					<div class="form-inline form-group">
						<label for="1" class="col-md-2 control-label text-left">예약자명(*)</label>
						<input type="text" id="1" class="form-control" placeholder="예약자명"
						required="required" >
					</div>
					
					
					<!-- 예약자 정보 입력란에 기존 회원정보 자동으로 불러옴
						 수정 가능 -->
					<div class="form-inline form-group">
						<label for="2" class="col-md-2 control-label">이메일</label>
						<input type="text" id="2" placeholder="이메일" class="form-control"
						required="required">@
						<input type="text" placeholder="xxx.com" class="form-control"
						required="required">
					</div>
					
					<div class="form-inline form-group">
						<label for="3" class="col-md-2 control-label">인원수(*)</label>
						<input type="text" id="3" class="form-control col-md-1"
						required="required"> 명
					</div>
					
					<div class="form-inline form-group">
						<label for="2" class="col-md-2 control-label">요청사항</label>
						<textarea class="form-control col-md-9" name="message"
							placeholder="남기고 싶은 말을 적어주세요.(최대 50자)" maxlength="100"></textarea>
					</div>
				</form>

				<h2 class="mb-3 mt-5 font-weight-bold"># 호스트 정보</h2><hr>
				<!-- 해당 공간의 호스트 사업자 정보 출력-->
				
				
				<div class="hostInfo ftco-animate">
					<div class="form-inline form-group">
						<p class="col-md-2">공간상호</p>
						<p class="col-md-10">파파존스 파티룸</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2">대표자명</p>
						<p class="col-md-10">진영은</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2">소재시</p>
						<p class="col-md-10">서울특별시 은평구 땡땡동 주소주소</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2">사업자번호</p>
						<p class="col-md-10">2020. 12. 31. (목) 12시 ~ 15시</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2 align-self-start">연락처</p>
						<div class="col-md-10">
							<p>010-1234-1234</p>
							<p>papajon@lookation.com</p>
						</div>
					</div>
				</div>
				
				
				<h2 class="mb-3 mt-5 font-weight-bold"># 예약시 주의사항</h2><hr>
				<div class="ftco-animate">
					<div class="form-inline form-group">
						<p class="col-md-1 text-center align-self-start">1.</p>
						<p class="col-md-11">그냥 조심하세요. 이렇게 길게 쓴 주의사항도 담을 수 있는지 확인하는 중입니다. 이렇게 길게 쓴 주의사항도 담을 수 있는지 확인하는 중입니다.</p>
					</div>
					
					
					<div class="form-inline form-group">
						<p class="col-md-1 text-center align-self-start">2.</p>
						<p class="col-md-11">깨트리면 2배 보상</p>
					</div>
			
					<div class="form-inline form-group">
						<p class="col-md-1 text-center align-self-start">3.</p>
						<p class="col-md-11">마음의 상처도 보상하세요</p>
					</div>
			
					<div class="form-inline form-group">
						<p class="col-md-1 text-center align-self-start">4.</p>
						<p class="col-md-11">최대 10개까지 입력가능</p>
					</div>
			
					<div class="form-inline form-group">
						<p class="col-md-1 text-center align-self-start">5.</p>
						<p class="col-md-11">출력은 DB에 있는 만큼만!</p>
					</div>
				</div>
				
				<h2 class="mb-3 mt-5 font-weight-bold"># 환불 규정</h2><hr>
				
				
				<div class="ftco-animate">
					<div class="memo">
						<span class="text-body">이용 1일전 23:59까지만 취소 가능.</span>
						<span class="text-danger">이용당일(첫 날) 환불은 불가능합니다. 관련 사항은 호스트에게 직접 문의하세요.</span><br><br>
					</div>
			
					
					<div class="row">
						<div class="col-lg-3 ftco-animate">
							<p>이용 7일전</p>
							<p>이용 6일전 ~ 1일전</p>
							<p>예약 당일</p>
						</div>
	
	
						<!-- 예약자 정보 입력란에 기존 회원정보 자동으로 불러옴 -->
						<!-- 수정 가능 -->
						<div class="col-lg-8 ftco-animate">
							<p>총 금액의 100% 환불</p>
							<p>총 금액의 50% 환불</p>
							<p>환불 불가</p>
						</div>
					</div>
				</div>
				
				
				<h2 class="mb-3 mt-5 font-weight-bold"># 서비스 동의 </h2> <hr>
				
				
				<div class="ftco-animate">
					<span>약관 블라블라~</span>
					
					<!-- 체크박스 크기 조절  -->
					<!-- 전체동의 클릭시 아래의 체크박스 4개 동시 체크 -->
					<div class="text-right">
						<input type="checkbox" id="allConfirm" class="checkbox">
						<label for="allConfirm">전체 동의</label><br></div>
					<div>
					
					<!-- 전부 다 체크했는지 확인 -->
					<p><input type="checkbox"> <small class="text-danger">(필수)</small> 위 공간의 예약조건 확인 및 결제진행 동의</p>
					<p><input type="checkbox"> <small class="text-danger">(필수)</small> 환불규정 안내에 대한 동의</p>
					<p><input type="checkbox"> <small class="text-danger">(필수)</small> 개인정보 제3자 제공 동의</p>
					<p><input type="checkbox"> <small class="text-danger">(필수)</small> 개인정보 수집 및 이용 동의</p>
					<span class="text-danger">- 서비스 이용약관 동의는 필수입니다.</span>
					</div>
					<br><br>
						
					<!-- 버튼 클릭시 폼 내용 검증 -->
					<!-- 검증 완료시 submit 후 DB 예약내역, 결제내역 테이블에 추가 -->
					<div class="row">
						<button class="btn btn-primary btn-block" type="button">동의하고 결제하기</button>
					</div>
					
					<!-- 맨 위로 올라가는 버튼 만들기 -->
				
				</div>

				
			</div>



			<!-- 오른쪽 사이드바 -->
			<div class="col-lg-4 sidebar pl-lg-5 ftco-animate">
				<div class="sidebar-box ftco-animate">
					<div class="categories">
						<h3>결제 예정금액</h3>
						<hr>
						
						<div class="row">
							<div class="col-lg-4 ftco-animate">
								<p>예약날짜</p>
								<p>예약시간</p>
								<p>예약인원</p>
							</div>
							
							<div class="col-lg-8 ftco-animate text-right">
								<p>2020.12.31(목)</p>
								<p>12시 ~ 15시(3시간)</p>
								<p>5명</p>
							</div>
							<br>
						</div>
						
						<hr>
						<div class="text-right">
							<h3><span class="icon-won"></span>  60,000 원</h3>
						</div>
						<br>
						
						<button type="button" class="btn btn-primary btn-lg btn-block">결제하기</button>

					</div>
				</div>
			</div><!-- 오른쪽 사이드바 끝 -->
		</div>
	</div>	
</section>





	<!-- Footer -->



<!-- loader -->
<div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>

<%@ include file="../includes/includes_home_end.jsp" %>

<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>

</body>
</html>