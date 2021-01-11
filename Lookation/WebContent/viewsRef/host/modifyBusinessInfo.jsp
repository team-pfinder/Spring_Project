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
<title>locationBusinessInfoUpdate.jsp</title>
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
               <h1 class="mb-0 bread">사업자정보 수정</h1>
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
   <h1 class="mb-2 text-gray-800">사업자정보 수정</h1>
   <p class="mb-4"> 사업자정보를 수정하세요. <a target="_blank" href="#">이전으로</a>.</p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">사업자정보 수정</h6>
         </div>



<form style="width: 800px; margin: 120px;">

	<!-- 1. 상호명 --><!-- ※ 변경불가 항목 -->
	
	<div id="locationBusinessName">
		<span style="font-size: 14pt; font-weight: bold;">상호명 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" class="form-control" readonly="readonly"
			   placeholder="상호명을 입력하세요. [최소 2자 ~ 최대 20자]"
	 		   name="locationBusinessName">
	</div>

<br><br><br>

	<!-- 2. 대표자명 --><!-- ※ 변경불가 항목 -->

	<div id="locationRepresentative">

	<span style="font-size: 14pt; font-weight: bold;">대표자명 <span style="color: red">*</span></span>
	<br><br>
	<input type="text" class="form-control" readonly="readonly"
		   placeholder="대표자명을 입력하세요. [최소 2자 ~ 최대 10자]" 
		   name="locationRepresentative">	
	</div>
	
	
<br><br><br>

	<!-- 3. 사업자등록번호 --><!-- ※ 변경불가 항목 -->
	
	<div id="locationBusinessRegNum">
	
		<span style="font-size: 14pt; font-weight: bold;">사업자등록번호 <span style="color: red">*</span></span>
		<br><br>
			<input type="text" readonly="readonly" id="locationBusinessRegNum1"
				style="width:100px; background-color: gray">
			-
			<input type="text" readonly="readonly" id="locationBusinessRegNum2"
				style="width:100px; background-color: gray">
			-
			<input type="text" readonly="readonly" id="locationBusinessRegNum3"
				style="width:100px; background-color: gray">

	</div>
	
	
<br><br><br>


	<!-- 4. 사업자 등록증 --><!-- ※ 변경불가 항목 -->
	<div id="locationBusinessReg">
	
		<span style="font-size: 14pt; font-weight: bold;">사업자등록증 <span style="color: red">*</span></span>
		<br><br>
		<!-- 이미지추가시 들어갈 공간.. textarea인지 확인 필요-->
		<textarea class="form-control" placeholder="사업자등록증을 첨부해 주세요(JPG, JPEG, PNG)"
					name="locationBusinessReg"
					cols="40" rows="10" readonly="readonly"></textarea>
		<br><br>
		<span style="color: gray">* 최소/최대 1장, 최대 5MB, 최대해상도 2048*1158</span>
		
		<!-- 7.1 추가 시 보여지는 default 이미지 해상도 xxx*xxx -->
		<!-- ※ 다시 첨부하여 등록할 시 기존 이미지를 대체 -->
	</div>

	

<br><br><br>

	<!-- 5. 사업자 유형 --><!-- ※ 변경불가 항목 -->
	
	<div id="locationBusinessType">
		<span style="font-size: 14pt; font-weight: bold;">사업자유형 <span style="color: red">*</span></span>
		<br><br>
			<input disabled="disabled" type="radio" name="locationBusinessType" value="간이과세자">간이과세자
			<input disabled="disabled" type="radio" name="locationBusinessType" value="일반과세자">일반과세자
			<input disabled="disabled" type="radio" name="locationBusinessType" value="법인사업자">법인사업자
			<input disabled="disabled" type="radio" name="locationBusinessType" value="면세사업자">면세사업자
			<input disabled="disabled" type="radio" name="locationBusinessType" value="기타">기타
	</div>

	

	<br><br><br>
	
	<!-- 6. 주종목 --><!-- ※ 변경불가 항목 -->
	
	<div id="locationBusinessMainEvent">
	
		<span style="font-size: 14pt; font-weight: bold;">주종목 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" readonly="readonly" class="form-control"
			   placeholder="주종목을 입력하세요. [최소 5자 ~ 최대 30자]"
			   name="locationBusinessMainEvent">
	</div>
	
	<br><br><br>


	<!-- 7. 주업태 --><!-- ※ 변경불가 항목 -->
	
	<div id="locationBusinessMain">
	
		<span style="font-size: 14pt; font-weight: bold;">주업태 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" readonly="readonly" class="form-control"
			   placeholder="주업태을 입력하세요. [최소 5자 ~ 최대 30자]"
			   name="locationBusinessMainEvent">
	</div>


<br><br><br>

	<!-- 8. 사업지 주소 --><!-- ※ 변경불가 항목 -->
		
	<div id="locationBusinessAddr">
		<span style="font-size: 14pt; font-weight: bold;">주소 <span style="color: red">*</span></span>
		
		<br><br>
		<input type="text" class="form-control" readonly="readonly"
			   placeholder="주소를 입력해주세요." name="locationBusinessAddr">
		<br><br>
		<span style="font-size: 13pt; font-weight: bold;">상세 주소 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" class="form-control" readonly="readonly" name="locationBusinessDetailedAddr">
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