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
<title>locationBusinessInfo.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<style type="text/css">

	
	/* 대표이미지 추가 css */
	.filebox input[type="file"] { 
	
		position: absolute; width: 70%; 
		padding: 0; margin: -1px; overflow: hidden; clip:rect(0,0,0,0); border: 0; 
	}
	.filebox label {
	
		display: inline-block; padding: .5em .75em; color: #999; 
		font-size: inherit; line-height: normal; vertical-align: middle; 
		background-color: #fdfdfd; cursor: pointer; border: 1px solid #ebebeb; 
		border-bottom-color: #e2e2e2; border-radius: .25em; 
	}
	
	/* named upload */ 
	.filebox .upload-name { 
	
		display: inline-block; padding: .5em .75em; 
	
		/* label의 패딩값과 일치 */ 
		font-size: inherit; font-family: inherit; line-height: normal; 
		vertical-align: middle; background-color: #f5f5f5; 
		border: 1px solid #ebebeb; border-bottom-color: #e2e2e2; 
		border-radius: .25em; -webkit-appearance: none; 
		
		/* 네이티브 외형 감추기 */ 
	
		-moz-appearance: none; appearance: none; 
	
	}

</style>

<!-- 주소 API -->		
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script type="text/javascript">


	$(function() {
		
		// enter submit 방지
		document.addEventListener('keydown', function(event) {
			
			if (event.keyCode === 13) {
			    event.preventDefault();
		  	};
		  	   
		}, true);
		
		
		// 함수 호출
		setInputLength($('#inputBizName'), '상호명', 2, 20);
		setInputLength($('#inputBizCeo'), '대표자명', 2, 10);
		bizNumReg($('#inputBizNum'));
		setSelectFilter($('#inputBizCeoType'), '사업자 유형');
		setInputLength($('#inputBizMainType'), '주업태', 2, 30);
		setInputLength($('#inputBizSubType'), '주종목', 2, 30);
		
		
		// 사업자등록증 이미지 등록
		
		var fileTarget = $('.filebox .upload-hidden');
		
		fileTarget.on('change', function(){ // 값이 변경되면 
			
			// modern browser
			
			if(window.FileReader){ 
				var filename = $(this)[0].files[0].name; 
			} 
		
			// old IE
			
				else { 
			
				var filename = $(this).val().split('/').pop().split('\\').pop(); // 파일명만 추출 
			} 
			
		
			// 추출한 파일명 삽입 
			$(this).siblings('.upload-name').val(filename); 
				
		}); 
		
		
		// submit 제어
		
		$('#submitButton').click(function() {
			
			var f = $("#inputBusinessInfo");
			var bizNumRegExp = /^\d{3}-\d{2}-\d{5}$/;
			
			var tBizName = $("#inputBizName").val();
			var tBizCeo = $("#inputBizCeo").val();
			var tBizNum = $('#inputBizNum').val();
			var tBizLicense = $("#ex_filename").val();
			var tBizCeoType = $("#inputBizCeoType").val();
			var tBizMainType = $("#inputBizMainType").val();
			var tBizSubType = $("#inputBizSubType").val();
			var tLocAddr = $("#inputAddr").val();
			
			
			if (tBizName == "" || tBizCeo == "" || tBizNum == "" 
				|| tBizLicense == "" || tBizCeoType == "" 
				|| tBizMainType == "" || tBizSubType == "" || tLocAddr == "") {
				
				alert("필수 입력사항을 모두 입력해 주세요.");
			}
			else if (tBizName.length < 2 || tBizName.length > 20) {
				
				alert("상호명은 2자~20자로 입력해야합니다.");
				$("#inputBizName").focus();
				
			}
			else if (tBizCeo.length < 2 || tBizCeo.length > 10) {
				
				alert("대표자명은 2자~10자로 입력해야합니다.");
				$("#inputBizCeo").focus();
				
			}
			else if (tBizNum.match(bizNumRegExp) == null) {
				
				alert("사업자등록번호를 올바르게 입력하세요.");
				$("#inputBizNum").focus();
				
			} 
			else if (tBizMainType.length < 2 || tBizMainType.length > 30) {
				
				alert("주업태는 2자~30자로 입력해야합니다.");
				$("#inputBizMainType").focus();
				
			}
			else if (tBizSubType.length < 2 || tBizSubType.length > 30) {
				
				alert("주종목은 2자~30자로 입력해야합니다.");
				$("#inputBizSubType").focus();
				
			}
			else {
				
				f.submit();
			}
			
		
		});
	});
	
	// 함수 정의 --------------------------------------------------------------------
	
	// setInputLength()
	//-- 입력값, 입력대상, 최소 인원, 최대 인원 및 유효성 검사 결과 알림
	function setInputLength(target, name, minLength, maxLength) {
		
		target.on("keyup", function() {
		
			var err = $(this).next();
			err.css("display", "none");
			
			
			if (target.val()=='') {
				
				err.html.hide();
				return false;
			}
			
			// 글자 수 제한, 색 변경
			else if (target.val().length > maxLength || target.val().length < minLength) {
				
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
	
	
	// 사업자등록번호 정규식 함수 정의
	function bizNumReg(target) {
		target.on("keyup", function()
		{
			
			// 입력창 다음에 표시할 $(#err) 메세지의 위치를 err 변수에 담음
			var err = $(this).next();
			err.css("display", "none");
			
			// 검증에 사용할 정규식 변수 regExp에 저장
			var regExp = /^\d{3}-\d{2}-\d{5}$/;

			
			if ($('#inputBizNum').val()=='') {
				
				err.html.hide();
				return false;
			}
			
			// target의 value와 정규식과 같은지 match() 함수로 검증
			else if ($('#inputBizNum').val().match(regExp) != null)
			{
				err.html("사용 가능한 사업자등록번호 입니다.").css("color", "green");
				err.css("display","inline");
				return;
				
			} else {
				
				// 이메일 정규식이 제대로 적용되지 않은 상태
				err.html("사업자등록번호를 올바르게 입력해주세요.").css("color","red");
				err.css("display","inline");
				return;
			}
			
		});
			
	}
	
	// 사업자 유형 빈 값 선택 유효성 검사
	function setSelectFilter(target, name) {
		target.on('change', function () {
			var val = $(this).val();
			var err = $(this).next();
			
			if (val.trim() == "") {
				
				// 빈 값을 선택한 경우
				err.show();
				err.text(name + "을(를) 선택해야 합니다.");
				
			} else {
				err.hide();
			}
		})
	}
	

	// 주소 등록 클릭 시 다음 주소 API 실행
	function showDaumAddPop() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	        	
	            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
	            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
	            console.log(data);
	            var jibunAddress = data.jibunAddress;
	            $('#inputAddr').val(jibunAddress);
	            
	        }
	    }).open();
	}
	
	// 취소 버튼 클릭시 기존 작성내용을 저장하지 않고 메인 홈페이지로 이동하는 function
	function cancel() {
		
		var con = confirm("작성을 취소하고 메인 페이지로 돌아가시겠습니까?                        "
						+ "(기존 작성 내용은 저장되지 않습니다.)");
		if (con == true) {
			location.href = "hostmain.action";
			return;
		} else {
			return;
		}
		
	}
	
</script>
</head>
<body>

	<!-- header 출력부분 -->
	<div>
		<c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=${info.nick }"></c:import>
	</div>

   <!-- 타이틀 -->
   <section class="hero-wrap hero-wrap-2"
      style="background-image: url(<%=cp%>/images/bg_3.jpg);"
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
               <h1 class="mb-0 bread">사업자정보 입력</h1>
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
   <h1 class="mb-2 text-gray-800">사업자정보 입력</h1>
   <p class="mb-4"> 사업자정보를 입력하세요. <a target="_blank" href="#">이전으로</a>.</p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">사업자정보 입력</h6>
         </div>

	<!-- form start --------------------------------------------->
	<form style="width: 80%; margin: 120px;" id="inputBusinessInfo" enctype="multipart/form-data"
	 	  action="inputbusinessinfo.action" method="POST"><!--onsubmit="handOver()" -->
	
		<!-- 1. 상호명 -->
		
		<div id="bizName">
		
			<span style="font-size: 14pt; font-weight: bold;">상호명 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control" 
				   placeholder="상호명을 입력하세요. [최소 2자 ~ 최대 20자]"
		 		   id="inputBizName" name="inputBizName">
		 	<span id="err" style="font-weight: bold;"></span>
		</div>
		
		
	<br><br><br>
	
		
		<!-- 2. 대표자명 -->
	
		<div id="bizCeo">
	
			<span style="font-size: 14pt; font-weight: bold;">대표자명 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control" 
				   placeholder="대표자명을 입력하세요. [최소 2자 ~ 최대 10자]" 
				   id="inputBizCeo" name="inputBizCeo">
			<span style="font-weight: bold;"></span>	
		</div>
		
		
	<br><br><br>
	
	
		<!-- 3. 사업자등록번호 -->
		
		<div id="bizNum">
		
			<span style="font-size: 14pt; font-weight: bold;">사업자등록번호 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control" 
				    id="inputBizNum" name="inputBizNum" 
				   placeholder="사업자등록번호를 입력하세요.(-포함) (ex) 123-45-67890)">
			<span style="font-weight: bold;"></span>
	
		</div>
		

	<br><br><br>
	
		<!-- 4. 사업자등록증 -->
		<div>
			<span style="font-size: 14pt; font-weight: bold;">사업자등록증<span style="color: red">*</span></span>
			<br><br>
			<div class="filebox"> 
				<input class="upload-name" name="inputBizLicense"
					   value="사업자등록증을 첨부해 주세요(JPG, JPEG, PNG)" disabled="disabled" style="width: 70%">
				<label for="ex_filename"><span class="glyphicon fa fa-upload"></span></label> 
				<input type="file" name="inputBizLicense" id="ex_filename" class="upload-hidden">
			</div>
			<br><br>
		</div>
		<!-- <div id="locationBusinessReg">
		
			<span style="font-size: 14pt; font-weight: bold;">사업자등록증 <span style="color: red">*</span></span>
			<br><br>
			이미지추가시 들어갈 공간.. textarea인지 확인 필요
			<textarea class="form-control" placeholder="사업자등록증을 첨부해 주세요(JPG, JPEG, PNG)"
						name="locationBusinessReg"
						cols="40" rows="10" required="required"></textarea>
			<br>
			<input type="button" class="form-control"
					name="locationBusinessRegInsert" value="이미지 추가 +">
			
			7.1 추가 시 보여지는 default 이미지 해상도 xxx*xxx
			※ 다시 첨부하여 등록할 시 기존 이미지를 대체
		</div> -->
		<!-- 	
		
		<div>
		
			<span style="font-size: 14pt; font-weight: bold;">시설 안내 <span style="color: red">*</span></span>
			<br><br>
			<ul class="facility-list">
				<li>
					<textarea class="form-control" id="inputFacility" required="required"
						placeholder="시설은 [최소 10자 ~ 최대 50자] 로 입력하여 10개까지 추가 가능합니다."></textarea>
					<span style="font-weight: bold; display: none; color: red;"></span>
				</li>
			</ul>
			<br>
			<input type="button" class="form-control" id="inputFacilityBtn"
					name="inputFacilityBtn" value="시설 추가 +">
			<br><br>
			
			5-1. 추가한 시설안내 내용이 보여지도록 하는 공간
					, 리스트 형태로 추가할때마다 순번부여,
					, "x" 버튼클릭시 추가한 내용 삭제가능 
					, (순번).(내용)(x버튼) 형식		<- 순번은 뺼 수도 있을듯.
			
		</div>
		-->
		
		
	
	<br><br><br>
	
		<!-- 5. 사업자 유형 -->
		
		<div id="bizCeoType">
			<span style="font-size: 14pt; font-weight: bold;">사업자 유형 <span style="color: red">*</span></span>
			<br><br>
			<select id="inputBizCeoType" name="inputBizCeoType" class="form-control" >
				<option value="">[==사업자 유형을 선택하세요.==]</option>
				<option value="간이과세자">간이과세자</option>
				<option value="일반과세자">일반과세자</option>
				<option value="법인사업자">법인사업자</option>
				<option value="면세사업자">면세사업자</option>
				<option value="기타">기타</option>
			</select>
			<span style="font-weight: bold; display: block; color: red;">사업자 유형을(를) 선택해야 합니다.</span>
		</div>
		

		<br><br><br>
		
		<!-- 6. 주업태 -->
		
		<div id="bizMainType">
		
			<span style="font-size: 14pt; font-weight: bold;">주업태 <span style="color: red">*</span></span>
			<br><br>
			<input type="text"  class="form-control"
				   placeholder="주업태를 입력하세요. [최소 5자 ~ 최대 30자]"
				   id="inputBizMainType" name="inputBizMainType">
			<span style="font-weight: bold; "></span>
		</div>
		
		
		<br><br><br>
	
	
		<!-- 7. 주종목 -->
		
		<div id="bizSubType">
		
			<span style="font-size: 14pt; font-weight: bold;">주종목 <span style="color: red">*</span></span>
			<br><br>
			<input type="text"  class="form-control"
				   placeholder="주종목을 입력하세요. [최소 5자 ~ 최대 30자]"
				   id="inputBizSubType" name="inputBizSubType">
			<span style="font-weight: bold; "></span>
		</div>
	
	
	<br><br><br>
	
		<!-- 8. 사업지 주소 -->
		
		<div>
			<span style="font-size: 14pt; font-weight: bold;">주소 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control"  id="inputAddr"
				   placeholder="주소를 입력해주세요." name="inputAddr" readonly>
			
			<input onclick="showDaumAddPop()" type="button" class="form-control"
				   value="주소 등록">
			<br><br>
		
		</div>
	
	<br><br><br>
	
		<!-- 다음 버튼 -->
		<div class="container" style="text-align: center;">
		<button type="button" class="btn btn-warning" id="submitButton"
			style="width:45%; border-color: gray;">다음 </button>
		
		<!-- 취소 버튼 -->
			<input type="button" class="btn btn-default" style="align-content:center; width:45%; border-color: gray;" 
					id="BusinessInfoCancel" value="취소" onclick="cancel()">		
		</div>	
		




<br><br><br><br>

</form>

</div>
</div>

<!-- footer 출력부분 -->
<div>
	<c:import url="${cp}/includes/footer_host.jsp"></c:import>
	<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</div>

</body>
</html>