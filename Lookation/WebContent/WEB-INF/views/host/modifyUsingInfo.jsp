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
<title>locationUsingInfoUpdate.jsp</title>


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
               <h1 class="mb-0 bread">이용안내 수정</h1>
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
   <h1 class="mb-2 text-gray-800">이용안내 수정</h1>
   <p class="mb-4"> 이용안내를 수정하세요. <a target="_blank" href="#">이전으로</a>.</p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">이용안내 수정</h6>
         </div>



<form style="width: 800px; margin: 120px;">
	
	<!-- 1. 이용시간 -->
	
	<div id="LocationUsingTime">
		
		<span style="font-size: 14pt; font-weight: bold;">이용시간 <span style="color: red">*</span></span>
		<br><br>
		<select class="form-control" name="locationStartTime" required="required">
			<option>[==시간을 선택하세요.==]</option>
			<option value="0시">0시</option>
			<option value="1시">1시</option>
			<option value="2시">2시</option>
			<option value="3시">3시</option>
			<option value="4시">4시</option>
			<option value="5시">5시</option>
			<option value="6시">6시</option>
			<option value="7시">7시</option>
			<option value="8시">8시</option>
			<option value="9시">9시</option>
			<option value="10시">10시</option>
			<option value="11시">11시</option>
			<option value="12시">12시</option>
			<option value="13시">13시</option>
			<option value="14시">14시</option>
			<option value="15시">15시</option>
			<option value="16시">16시</option>
			<option value="17시">17시</option>
			<option value="18시">18시</option>
			<option value="19시">19시</option>
			<option value="20시">20시</option>
			<option value="21시">21시</option>
			<option value="22시">22시</option>
			<option value="23시">23시</option>
			<option value="24시">24시</option>
		</select>
		~ <select class="form-control" name="LocationEndTime" required="required">
			<option>[==시간을 선택하세요.==]</option>
			<option value="0시">0시</option>
			<option value="1시">1시</option>
			<option value="2시">2시</option>
			<option value="3시">3시</option>
			<option value="4시">4시</option>
			<option value="5시">5시</option>
			<option value="6시">6시</option>
			<option value="7시">7시</option>
			<option value="8시">8시</option>
			<option value="9시">9시</option>
			<option value="10시">10시</option>
			<option value="11시">11시</option>
			<option value="12시">12시</option>
			<option value="13시">13시</option>
			<option value="14시">14시</option>
			<option value="15시">15시</option>
			<option value="16시">16시</option>
			<option value="17시">17시</option>
			<option value="18시">18시</option>
			<option value="19시">19시</option>
			<option value="20시">20시</option>
			<option value="21시">21시</option>
			<option value="22시">22시</option>
			<option value="23시">23시</option>
			<option value="24시">24시</option>
			<option value="익일 1시">익일 1시</option>
			<option value="익일 2시">익일 2시</option>
			<option value="익일 3시">익일 3시</option>
			<option value="익일 4시">익일 4시</option>
			<option value="익일 5시">익일 5시</option>
			<option value="익일 6시">익일 6시</option>
			<option value="익일 7시">익일 7시</option>
			<option value="익일 8시">익일 8시</option>
			<option value="익일 9시">익일 9시</option>
			<option value="익일 10시">익일 10시</option>
		</select>
		<!-- 종료시간 : 시작시간보다 이후부터 
		(최대 : 시작시간 + 34시까지)  -->
		<br><br>
		<span style="color: red;">※ 실제 이용 가능한 시간으로 설정해야 합니다.</span> <!-- 붉은색 글자 -->
		
	</div>
	
	<br><br><br>
	
	<!-- 2. 정기 휴무 -->
	
	<div id="locationRegularDayOff">
	
		<span style="font-size: 14pt; font-weight: bold;">정기 휴무일</span>
		<br><br>
		<input type="text" class="form-control" 
				placeholder="정기 휴무일을 수정하세요. [최소 2자, 최대 20자]"
				name="locationRegularDayOff">
		<br>
		
	</div>
	
	<br><br><br>
	
	<!-- 3. 지정 휴무일 -->
	
	<div id="locationRegularDayOff">
	
		<span style="font-size: 14pt; font-weight: bold;">지정 휴무일</span>
		<br><br>
		<input type="text" name="LocationDayOff" class="form-control"
			   placeholder="지정 휴무일을 수정하세요.[최소 2자, 최대 20자]">
		<br>
		<span></span> <!-- 글자 크기 작게 -->
		
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
			id="LocationBasicInfoCancel" value="취소">
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