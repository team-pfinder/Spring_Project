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
<title>locationContact.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>


<script type="text/javascript">

	$(function() {
		
		// 함수 호출
		emailReg($('#inputEmail'));
		contactReg($('#inputContact'));	
		mainContactReg($('#inputMainContact'));
		
	});
	

	
	
	
	// 함수 정의 -----------------------------------------------------------------
	
	
	// 이메일 정규식 함수 정의
	function emailReg(target) {
		target.on("keyup", function(){
		
			// 입력창 다음에 표시할 $(#err) 메세지의 위치를 err 변수에 담음
			var err = $(this).next();
			err.css("display", "none");

			// 검증에 사용할 정규식 변수 regExp에 저장
			var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

			// target의 value와 정규식과 같은지 match() 함수로 검증
			if ($('#inputEmail').val().match(regExp) != null)
			{
				err.html("사용 가능한 이메일 입니다.").css("color", "green");
				err.css("display","inline");
				return;
				
			} else {
				
				// 정규식이 제대로 적용되지 않은 상태
				err.html("이메일 을 올바르게 입력해주세요.").css("color", "red");
				err.css("display","inline");
				return;
			}
			
		});
	}
	
	// 휴대폰 번호 정규식 함수 정의
	function contactReg(target) {
		target.on("keyup", function()
		{
			// 입력창 다음에 표시할 $(#err) 메세지의 위치를 err 변수에 담음
			var err = $(this).next();
			err.css("display", "none");
			
			// 검증에 사용할 정규식 변수 regExp에 저장
			var regExp = /^\d{3}-\d{3,4}-\d{4}$/;

			// target의 value와 정규식과 같은지 match() 함수로 검증
			if ($('#inputContact').val().match(regExp) != null)
			{
				err.html("사용 가능한 휴대폰 번호입니다.").css("color", "green");
				err.css("display","inline");
				return;
				
			} else {
				
				// 이메일 정규식이 제대로 적용되지 않은 상태
				err.html("휴대폰 번호를 올바르게 입력해주세요.").css("color", "red");
				err.css("display","inline");
				return;
			}
			
		});
			
	}
	
	
	// 대표전화 정규식 함수 정의
	function mainContactReg(target) {
		target.on("keyup", function()
		{
			// 입력창 다음에 표시할 $(#err) 메세지의 위치를 err 변수에 담음
			var err = $(this).next();
			err.css("display", "none");
			
			// 검증에 사용할 정규식 변수 regExp에 저장
			var regExp = /^\d{2,3}-\d{3,4}-\d{4}$/;

			// target의 value와 정규식과 같은지 match() 함수로 검증
			if ($('#inputMainContact').val().match(regExp) != null)
			{
				err.html("사용 가능한 대표전화 번호입니다.").css("color", "green");
				err.css("display","inline");
				return;
				
			} else {
				
				// 이메일 정규식이 제대로 적용되지 않은 상태
				err.html("대표 전화번호를 올바르게 입력해주세요.").css("color", "red");
				err.css("display","inline");
				return;
			}
			
		});
			
	}

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
	
	// ---------------------------------------------------------------- 함수 정의
	
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
               <h1 class="mb-0 bread">연락처정보 입력</h1>
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
   <h1 class="mb-2 text-gray-800">연락처정보 입력</h1>
   <p class="mb-4"> 연락처정보를 입력하세요. <a target="_blank" href="#">이전으로</a>.</p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">연락처정보 입력</h6>
         </div>
	
	
	<form style="width: 80%; margin: 120px;" id="inputContactInfo" 
		  action="inputcontactinfo.action" method="POST"><!--onsubmit="handOver()" -->
		
	
	
		<!-- 1. 이메일 -->
		
		<div id="email">
		 	<span style="font-size: 14pt; font-weight: bold;">이메일 <span style="color: red">*</span></span>
		 	<br><br>
			<input type="text" class="form-control" id="inputEmail" name="inputEmail"
				   required="required" placeholder="이메일을 입력하세요.(@, . 포함) (ex) text@lookation.com)">
			<span id="err" style="font-weight: bold;"></span>
		</div>
	
	<br><br><br>
	
		<!-- 2. 휴대폰 -->
		
		<div id="contact">
			
			<span style="font-size: 14pt; font-weight: bold;">휴대폰 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control" id="inputContact" name="inputContact"
				   required="required" placeholder="휴대폰 번호를 입력하세요.(-포함) (ex) 010-1234-5678)">
			<span style="font-weight: bold;"></span>
		</div>
	
	<br><br><br>
	
	
		<!-- 3. 대표전화 -->
		
		<div id="mainContact">
			
			<span style="font-size: 14pt; font-weight: bold;">대표전화 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control" id="inputMainContact" name="inputMainContact"
				   required="required" placeholder="대표 전화번호를 입력하세요.(-포함) (ex) 02-123-4567)">
			<span style="font-weight: bold;"></span>
		</div>
	
	<br><br><br>
	
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
	<div class="container" style="text-align: center;">
		<input type="submit" value="다음" class="btn btn-warning" 
			   style="width:45%; border-color: gray;">
		
	<!-- 취소 버튼 -->
		<input type="button" class="btn btn-default" style="align-content:center; width:45%; border-color: gray;" 
				id="contactInfoCancel" value="취소" onclick="cancel()">		
	</div>	
	
	</form>
</div>

</div>
	
	<div>
		<c:import url="${cp}/includes/footer_host.jsp"></c:import>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
	</div>
</body>
</html>