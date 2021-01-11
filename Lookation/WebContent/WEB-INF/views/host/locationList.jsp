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
<title>locationMgmt.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>


</head>
<body>


</head>
<body>
	<div>
	<c:import url="${cp}/includes/header_host.jsp"></c:import>
	</div>
	
   <!-- 타이틀 -->
   <section class="hero-wrap hero-wrap-2"
      style="background-image: url('images/bg_3.jpg');"
      data-stellar-background-ratio="0.5">
      <div class="overlay"></div>
      
      <!-- 타이틀 내용 -->
      <div class="container">
         <div class="row no-gutters slider-text align-items-end">
            <div class="col-md-9 ftco-animate pb-5">
               <p class="breadcrumbs mb-2">
                  <span class="mr-2"> 
                  	<a href="index.html">Home 
                  		<i class="ion-ios-arrow-forward"></i>
                  	</a>
                  </span> 
                  <span>공간 관리 
                  	<i class="ion-ios-arrow-forward"></i>
                  </span>
               </p>
               <h1 class="mb-0 bread">공간 관리</h1>
            </div>
         </div>
      </div>
   </section>
   <!-- END 타이틀 -->

   <!-- 본문 -->
<!-- container 시작 -->

<div class="container">

   <br><br>
   
   <!-- Page Heading -->
   <h1 class="mb-2 text-gray-800">공간 관리</h1>
   <p class="mb-4"> 공간 관리페이지에서 내 공간을 관리해보세요. <a target="_blank" href="#">이전으로</a>.</p>
      
	<section class="ftco-section">
		<div class="container">
			<div class="row d-flex">
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="#" class="block-20 rounded"	
							style="background-image: url('<%=cp%>/images/location1.jpg');">
						</a>	<!-- 이미지 클릭시 해당 공간상세 게시글페이지로..-->
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="#">강남 라마다 호텔 트윈 1811호</a>	<!-- 공간명 클릭시 해당 공간상세 게시글페이지로..-->
							</h3>
							<hr>
							<div class="text mt-3 text-left">
								<span style="size: 10pt;">등록일</span><span> 2020.11.22</span>
								<span class="btn btn-success" style="float: right;">검수완료</span>									
							</div>
							<br>
							<div class="text mt-3">
								<a href="locationUpdate.jsp" class="btn btn-primary">공간정보수정</a>
								<a href="locationDelete.jsp" class="btn btn-dark">공간정보삭제</a>
							</div>
							<hr>
						</div>
					</div>
				</div><!-- location1 end -->
				
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="#" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/location2.png');">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="#">융프라우 밑 호수 3번구역</a>	<!-- 공간명 클릭시 해당 공간상세 게시글페이지로..-->
							</h3>
							<hr>
							<div class="text mt-3 text-left">
								<span style="size: 10pt;">등록일</span><span> 2020.12.21</span>
								<span class="btn btn-danger" style="float: right;">검수반려</span>									
							</div>
							<br>
							<div class="text mt-3">
								<a href="locationUpdate.jsp" class="btn btn-primary">공간정보수정</a>
								<a href="locationDelete.jsp" class="btn btn-dark">공간정보삭제</a>
							</div>
							<hr>
						</div>
					</div>
				</div><!-- location2 end -->
				
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="#" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/location3.png');">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="#">Salar de Uyuni, BOLIVIA</a>	<!-- 공간명 클릭시 해당 공간상세 게시글페이지로..-->
							</h3>
							<hr>
							<div class="text mt-3 text-left">
								<span style="size: 10pt;">등록일</span><span> 2020.01.06</span>
								<span class="btn btn-secondary" style="float: right;">검수대기</span>									
							</div>
							<br>
							<div class="text mt-3">
								<a href="locationUpdate.jsp" class="btn btn-primary">공간정보수정</a>
								<a href="locationDelete.jsp" class="btn btn-dark">공간정보삭제</a>
							</div>
							<hr>
						</div>
					</div>
				</div><!-- location3 end -->

				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="#" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/location4.jpg');">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="#">히말라야 정상</a>	<!-- 공간명 클릭시 해당 공간상세 게시글페이지로..-->
							</h3>
							<hr>
							<div class="text mt-3 text-left">
								<span style="size: 10pt;">등록일</span><span> 2020.12.21</span>
								<span class="btn btn-danger" style="float: right;">검수반려</span>									
							</div>
							<br>
							<div class="text mt-3">
								<a href="locationUpdate.jsp" class="btn btn-primary">공간정보수정</a>
								<a href="locationDelete.jsp" class="btn btn-dark">공간정보삭제</a>
							</div>
							<hr>
						</div>
					</div>
				</div><!-- location4 end -->
				
				<div class="col-md-4 ftco-animate">
					<div class="blog-entry align-self-stretch">
						<a href="#" class="block-20 rounded"
							style="background-image: url('<%=cp%>/images/location5.png');">
						</a>
						<div class="text mt-3 text-center">
							<h3 class="heading">
								<a href="#">오키나와 해변</a>	<!-- 공간명 클릭시 해당 공간상세 게시글페이지로..-->
							</h3>
							<hr>
							<div class="text mt-3 text-left">
								<span style="size: 10pt;">등록일</span><span> 2020.12.21</span>
								<span class="btn btn-secondary" style="float: right;">검수대기</span>									
							</div>
							<br>
							<div class="text mt-3">
								<a href="locationUpdate.jsp" class="btn btn-primary">공간정보수정</a>
								<a href="locationDelete.jsp" class="btn btn-dark">공간정보삭제</a>
							</div>
							<hr>
						</div>
					</div>
				</div><!-- location5 end -->
				
			</div>
		</div>
		<br><br>
		
		<!-- 새 공간 등록하기 -->
		<!-- 클릭 시 공간등록페이지(LocationBasicInfo.jsp)로 이동 -->
		<!-- <input type="submit" class="btn btn-info" value="새 공간 등록하기" 
			   style="width:100%; height: 100px; font-size: 14pt; font-weight: bold;">  -->
		<!-- onclick="func()" submit → LocationBasicInfo.jsp-->
		
		<a href="locationBasicInfo.jsp" class="btn btn-info"
				style="width:100%; font-size: 16pt; font-family: 맑은 고딕; font-weight: bold;">
				새 공간 등록하기</a>
		
		
		<!-- 페이지 -->		<!-- ←논의 필요 --> 
		<!-- 일정개수(약 6개) 이상이면 등록한 공간등록날짜보다 더 이전것들을 보여준다. -->
		<!-- 한 페이지에서만 보여지는 경우는 비활성화 상태 -->
		
		<br><br><br><br>
	</section>
</div> <!-- container end -->

	<div>
	<c:import url="${cp}/includes/footer_host.jsp"></c:import>
	<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
	</div>
</body>
</html>