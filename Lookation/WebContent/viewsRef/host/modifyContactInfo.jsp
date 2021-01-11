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
<title>locationContactUpdate.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>


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
                   
                  <span>공간 등록 
                  	<i class="ion-ios-arrow-forward"></i>
                  </span>
               </p>
               <h1 class="mb-0 bread">연락처정보 수정</h1>
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
   <h1 class="mb-2 text-gray-800">연락처정보 수정</h1>
   <p class="mb-4"> 연락처정보를 수정하세요. <a target="_blank" href="#">이전으로</a>.</p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">연락처정보 수정</h6>
         </div>



<form style="width: 800px; margin: 120px;">

	<!-- 1. 이메일 -->

	<div id="locationContactMail">
		
		<span style="font-size: 14pt; font-weight: bold;">이메일 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" required="required" id="locationContactMail1"
					style="width:100px;">
		@ 
		<input type="text" required="required" id="locationContactMail2"
					style="width:100px;">
	</div>

<br><br><br>

	<!-- 2. 휴대폰 -->
	
	<div id="locationContactTel">
		
		<span style="font-size: 14pt; font-weight: bold;">휴대폰 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" name="locationTel1" required="required" style="width: 70pt;">
		- <input type="text" name="locationTel2" required="required" style="width: 70pt;">
		- <input type="text" name="locationTel3" required="required" style="width: 70pt;">
	</div>


<br><br><br>


	<!-- 3. 대표전화 -->
	
	<div id="locationContactMainTel">
		
		<span style="font-size: 14pt; font-weight: bold;">대표전화 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" required="required" name="locationContactMainTel" style="width: 70pt;">
		- <input type="text" required="required" name="locationContactMainTel" style="width: 70pt;">
		- <input type="text" required="required" name="locationContactMainTel" style="width: 70pt;">
	</div>


<br><br><br>
<div class="container">
<!-- 다음 버튼(공통) : 다음 입력페이지로 넘어가고, DB에 저장된다.
					   (필수항목을 입력하지 않았을 경우,
						입력하지않은 항목 중 가장 첫번째 항목을 focus()하고
					    alert("필수항목을 입력해야합니다")된다.
						그리고 입력하는 textbox로 입력커서가 이동한다. 
						또한 다음페이지로 submit 되지 않는다.
						
						※ 다음 버튼 이동 순서
						※ xxxUpdate.jsp 다음버튼 이동 순서 
						   기본정보 → 연락처정보 → 사업자정보
						   → 이용정보  → 상세정보  → 패키지정보 -->

	<input type="submit" value="다음" class="btn btn-warning" style="width:300px;">
			
<!-- 취소 버튼 -->
	<input type="button" class="btn btn-default" style="width:300px;" 
			 value="취소">
	<!-- onclick="function()" -->
</div>

	

<br><br><br><br>

</form>
</div>
</div>

<div>
		<c:import url="${cp}/includes/footer_host.jsp"></c:import>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</div>
</body>
</html>