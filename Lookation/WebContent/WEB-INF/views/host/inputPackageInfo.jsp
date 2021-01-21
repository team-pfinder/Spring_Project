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
<title>inputPackageInfo.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<script type="text/javascript">


	$(document).ready(function() {

		// 함수 호출
		setInputLength($('#inputPackageName'), '공간명', 2, 20);

	
	});

	// 함수 정의 ----------------------------------------------------------
	
	// setInputLength()
	//-- 입력값, 입력대상, 최소 인원, 최대 인원 및 유효성 검사 결과 알림
	function setInputLength(target, name, minLength, maxLength) {
		target.on("keyup", function() {
			var err = $(this).next();
			err.css("display", "none");
			
			// 글자 수 제한, 색 변경
			if (target.val().length > maxLength || target.val().length < minLength) {
				
				err.html("" + name + "은(는) " + minLength + "자~" + maxLength + "자로 입력해야합니다.").css("display","inline");
				err.css("color", "red");
				return;
			}
			else {
				err.html("사용 가능한 " + name + "입니다.").css("display","inline");
				err.css("color","green");
				return;
			}
		});
	}
/* 
	// 저장 버튼 클릭 시 팝업 창이 닫히고, 
	// inputPackageForm.jsp 로 이동하여 현재패키지에 입력한 사항을 input
	function inputInfoSave()
	{
		
	}
 */
	
	// 취소 버튼 클릭시 기존 작성내용을 저장하지 않고 메인 홈페이지로 이동하는 function
	function cancel() {
		
		var con = confirm("작성을 취소하고 메인 페이지로 돌아가시겠습니까?                        "
						+ "(기존 작성 내용은 저장되지 않습니다.)");
		
		if (con == true) {
			location.href = "mainHost.jsp";
			return;
		} else {
			return;
		}
		
	}

</script>

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
               <h1 class="mb-0 bread">패키지정보 입력</h1>
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
   <h1 class="mb-2 text-gray-800">패키지정보 입력</h1>
   <p class="mb-4"> 패키지정보를 입력하세요. <a target="_blank" href="#">이전으로</a>.</p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">패키지정보 입력</h6>
         </div>



	<!-- form start --------------------------------------------->
	<form style="width: 80%; margin: 120px;" id="inputPackageInfoForm" 
		  action="inputPackageInfoForm.jsp" method="POST"><!--onsubmit="handOver()" -->
		  <!-- 컨트롤러 구성, 매핑 후 → action="inputxxxInfo.action" 로 변경 -->
		
		<!-- 1. 패키지명 -->
		
		<div id="packageName">
		
			<span style="font-size: 14pt; font-weight: bold;">패키지명 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" name="inputPackageName"
				   class="form-control"
				   placeholder="패키지명을 입력하세요. [최소 2자 ~ 최대 20자]">
			<span id="err" style="font-weight: bold;"></span>
		</div>
		
		<br><br><br>
		
		<!-- 2. 패키지 시작시간, 패키지 종료시간 -->
		
		<div id="packageStart">
			
			<span style="font-size: 14pt; font-weight: bold;">이용시간 <span style="color: red">*</span></span>
			<br><br>
			<select id="locationPackageStart" class="form-control" name="locationPackageStart">
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
			</select><br>
			~ 
			<select id="locationPacakgeEnd" class="form-control" name="locationPacakgeEnd">
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
			</select><br>
			
			<!-- 종료시간 : 시작시간보다 이후부터 
			(최대 : 시작시간 + 34시까지)  -->
			<br>
			<span style="color: red;">※ 실제 이용 가능한 시간으로 설정해야 합니다.</span> <!-- 붉은색 글자 -->
			
		</div>
		
		<br><br><br>
		
		<!-- 3. 패키지 가격 -->
		
		<div id="locationPackagePrice">
		
			<span style="font-size: 14pt; font-weight: bold;">패키지 가격 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control"
				   placeholder="패키지가격을 입력하세요. [최소 1,000원 ~ 최대 1,000,000원(부가세포함)]"
				   name="locationPackagePrice">
			<br><br>
			
		</div>
	
	<div class="container">
	<br><br><br>
	
		<!-- 저장 버튼 -->
		<input type="submit" value="저장" class="btn btn-warning" 
			   id="inputPackageInfoSave" style="width:45%; border-color: gray;"
			   onclick="inputInfoSave()">
		<!-- onclick="function()" → LocationPacakgeForm.jsp 테이블의 리스트형태로 저장 -->
	
		<!-- 취소 버튼 -->
		<input type="button" class="btn btn-default" 
			   id="inputPackageInfoCancel" style="align-content:center; width:45%; border-color: gray;"
			   onclick="cancel()" value="취소"> <!-- onclick="function()" -->

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