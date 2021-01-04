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
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<!-- ※ 썸네일형태로 들어갈 수 있도록 하는 템플릿 추가 -->

<%@ include file="/includes/includes_home.jsp" %>
<%@ include file="/includes/includes_menu.jsp" %>

</head>
<body>

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
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">공간 관리</h6>
         </div>



<form style="width: 800px; margin: 120px;">
		<table id="LocationMgmtTable" class="table table-bordered table-hover">
			<tr>
				<td>
					<table id="LocationTable" class="table table-bordered table-hover">
						<tr>
							<td>
								<!-- 1. 해당 공간의 대표이미지 -->
								<!-- 해당 공간에대한 모든 공간 정보는 해당 게시물의 코드를 참조하여 받아온다 -->
								<!-- <img src="/images/img_01.jpg"> -->
							</td>	
						</tr>
						<tr>
							<td>
								<!-- 2. 공간명 : bold, 텍스트박스형태 안보이도록 -->
								<input type="text" class="form-control" name="LocationName" value="공간명" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td>
								<!-- 3. 공간등록날짜 : 회색폰트, 텍스트박스형태 안보이도록 -->
								<input type="text" class="form-control" name="LocationInsertDate" value="공간등록날짜 <%-- <%=%> --%>" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td>
								<!-- 4. 검수상태 : 회색폰트, 텍스트박스형태 안보이도록 -->
								<input type="text" class="form-control" name="LocationInspection" value="검수상태 <%-- <%=%> --%>" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td>
								<div class="container">
									<!-- 4. 공간정보 수정 -->
									<input type="button" class="btn btn-warning" name="LocationUpdateBtn" value="공간정보수정"> <!-- onclick="func()" -->
									<!-- 5. 공간 삭제 -->
									<input type="button" class="btn btn-default" name="LocationDeleteBtn" value="공간삭제"><!-- onclick="func()" -->
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</form>
<!-- 새 공간 등록하기 -->
<!-- 클릭 시 공간등록페이지(LocationBasicInfo.jsp)로 이동 -->
<input type="button" class="btn btn-info" value="새 공간 등록하기"> 
<!-- onclick="func()" submit → LocationBasicInfo.jsp-->


   <%@ include file="../includes/includes_home_end.jsp"%>

<!-- 페이지 -->		<!-- ←논의 필요 --> 
<!-- 일정개수(약 6개) 이상이면 등록한 공간등록날짜보다 더 이전것들을 보여준다. -->
<!-- 한 페이지에서만 보여지는 경우는 비활성화 상태 -->

<br><br><br><br>
</div>
</div>
</body>
</html>