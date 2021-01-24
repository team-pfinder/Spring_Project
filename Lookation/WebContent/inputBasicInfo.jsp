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
<title>inputBasicInfo.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<%-- <%@ include file="../includes/header_host.jsp" %> --%>

<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>

<style>

/* 시설안내 추가 css */
ul.facility-list {
    list-style: none;
    padding: 0;
}

ul.facility-list > li {
    margin-bottom: 10px;
}

ul.precautions-list {
    list-style: none;
    padding: 0;
}

ul.precautions-list > li {
    margin-bottom: 10px;
}

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

<!-- 이미지 thumbnail -->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->


<!-- 주소 API -->		
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script type="text/javascript">


	$(document).ready(function() {

		// enter submit 방지
		document.addEventListener('keydown', function(event) {
			
			if (event.keyCode === 13) {
			    event.preventDefault();
		  	};
		  	   
		}, true);
		
		
		// 함수 호출
		setInputFilter($('#inputLocName'));
		setInputLength($('#inputLocName'), '공간명', 2, 20);
		setSelectFilter($('#inputLocType'), '공간 유형');
		setInputLength($('#inputShortIntro'), '공간 한줄 소개', 4, 20);
		setInputLength($('#inputIntro'), '공간 소개', 20, 400);
		setInputLength($('#inputFacility'), '시설 안내', 10, 50);
		setInputLength($('#inputPrecaution'), '예약 시 주의사항', 20, 100);

		
		// 썸네일 이미지 등록
		
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
			
			return true;
		}); 
		
		
		// submit 제어
		
		var result = '';
		  
		$('#submitButton').click(function() {
			
			var f = $("#inputBasicInfo");
		
			var tLocName = $("#inputLocName").val();
			var tLocType = $("#inputLocType").val();
			var tFacility = $("#facilityInfo_1").children().first().html();
			var tPrecaution = $("#precautionInfo_1").children().first().html();
			var tShortIntro = $("#inputShortIntro").val();
			var tIntro = $("#inputIntro").val();
			var tThumbnail = $("#ex_filename").val();
			var tLocAddr = $("#inputAddr").val();
			var tLocDetailAddr = $("#inputDetailAddr").val();
			
			
			if (tLocName == "" || tLocType == "" || tShortIntro == "" || tIntro == "" 
			|| tThumbnail == "" || tLocAddr == "" || tLocDetailAddr == "") {
			 
				alert("필수 입력사항을 모두 입력해 주세요.");
				
			}
			else if (tLocName.length < 2 || tLocName.length > 20) {
				
				alert("공간명은 2자~20자로 입력해야합니다.");
				$("#inputLocName").focus();
				
			}
			else if (tShortIntro.length < 4 || tShortIntro.length > 20) {
				
				alert("공간 한줄 소개는 4자~20자로 입력해야합니다.");
				$("#inputShortIntro").focus();
				
			}
			else if (tIntro.length < 20 || tIntro.length > 400) {
				
				alert("공간 소개는 20자~400자로 입력해야합니다.");
				$("#inputIntro").focus();
				
			}
			else if (tFacility == null) {
				
				alert("시설안내를 추가해주세요.");
			}
			else if (tPrecaution == null)
			{
				alert("예약 시 주의사항을 추가해주세요.")
			}
			else {
				
				f.submit();
			}
			
			//alert(tFacility.length);
			
			ajaxInfo();
			
		}); 
		
	});
		
	   
	
	// 함수 정의 ----------------------------------------------------------
	
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
				return false;
			}
			
			else {
				err.html("사용 가능한 " + name + "입니다.").css("display","inline");
				err.css("color","green");
				return true;
			}
		});
	}

	// 입력값에 대한 정규식 검사 function(한/영, 숫자, 특수문자 (  ) [ ] - , . )
	function setInputFilter(target) {
		target.on('keyup', function () {
		    var originVal = $(target).val();
		    var val = regExp($(target).val());

		    if (originVal != val) {
		    	
		        // 같지 않은 경우
		        $(target).val(val);
		        return false;
		        
		    } else {
		        $(target).next().hide();
		        return true;
		    }
		});
	}

	// 공간유형 빈 값 선택 유효성 검사
	function setSelectFilter(target, name) {
		target.on('change', function () {
			var val = $(this).val();
			var err = $(this).next();

			if (val.trim() == "") {
				
				// 빈 값을 선택한 경우
				err.show();
				err.text(name + "을(를) 선택해야 합니다.");
				
				return false;
				
			} else {
				err.hide();
				
				return true;
			}
		})
	}
	
	// 이용할 수 없는 특수문자 입력 시 자동 제거 처리 function
	function regExp(str){  
		
	    var reg = /[\{\}\/?;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi
	  
	 	// 특수문자 검증
	    if(reg.test(str)){
		  
	   		// 특수문자 제거후 리턴
	   		return str.replace(reg, "");
	    
	    } else {
		  
		    // 특수문자가 없으므로 본래 문자 리턴
		    return str;
	    
		}  
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
	            $('#inputDetailAddr').focus();
	        }
	    }).open();
	}
	
	
	// 시설안내 추가 및 삭제 function
	
	var facilityNum = 1;
	
	function addFacilityInfo()
	{
		if(facilityNum > 10)
		{
			alert("더이상 추가할 수 없습니다.");
			return;
		}
		
		var obj = document.getElementById("locationFacilityInfo");
		var newDiv = document.createElement("div");
		
		var content = $('#inputFacility').val();
		
		
		if(content == '')
		{
			alert("내용을 먼저 추가하세요.");
			return;
		}

		if (content.length < 10 || content.length > 50 ) {
			
			alert("시설 안내는 10자 이상 ~ 50자 이하로 입력해야합니다.")
			return;
		}
		
		newDiv.innerHTML = "<p>" + facilityNum + ". " + content + "</p>";
		newDiv.setAttribute("id" , "facilityInfo_" + facilityNum);
		obj.appendChild(newDiv);
		
		$('#inputFacility').val('');
		$('#inputFacility').next().hide();
		
		facilityNum++;
	}

	function removeFacilityInfo()
	{
		if(facilityNum <= 1)
		{
			alert("더이상 삭제할 수 없습니다.");
			return;
		}
		
		var obj = document.getElementById("facilityInfo_" + (facilityNum - 1));
		var parent = obj.parentElement;
		parent.removeChild(obj);

		facilityNum--;
	}

	// 주의사항 추가, 삭제 function
	
	var precautionNum = 1;

	function addPrecautionInfo()
	{
		if(precautionNum > 10)
		{
			alert("더이상 추가할 수 없습니다.");
			return;
		}
		
		var obj = document.getElementById("locationPrecautionInfo");
		var newDiv = document.createElement("div");
		
		var content = $('#inputPrecaution').val();
		
		if (content.length < 20 || content.length > 100 )
		{
			alert("예약 시 주의사항은 20자 이상 ~ 100자 이하로 입력해야합니다.")
			return;
		}
		
		if(content == '')
		{
			alert("내용을 먼저 추가하세요.");
			return;
		}
		
		newDiv.innerHTML = "<p>" + precautionNum + ". " + content + "</p>";
		newDiv.setAttribute("id" , "precautionInfo_" + precautionNum);
		obj.appendChild(newDiv);
		
		$('#inputPrecaution').val('');
		$('#inputPrecaution').next().hide();
		
		precautionNum++;
	}

	function removePrecautionInfo()
	{
		if(precautionNum <= 1)
		{
			alert("더이상 삭제할 수 없습니다.");
			return;
		}
		
		var obj = document.getElementById("precautionInfo_" + (precautionNum - 1));
		var parent = obj.parentElement;
		parent.removeChild(obj);

		precautionNum--;
	}
	
	// 시설안내, 주의사항 ajax 처리 function
	
	function ajaxInfo()
	{
		var arrFacility = new Array();
		var arrPrecaution = new Array();
		
		for (var i = 1; i < facilityNum; i++) {
			arrFacility.push($("#facilityInfo_" + i).children().first().html());
		}
		
		for (var i = 1; i < precautionNum; i++) {
			arrPrecaution.push($("#precautionInfo_" + i).children().first().html());
		}

		var formData = {
				'facility' : arrFacility,
				'precaution' : arrPrecaution
		};
		
		$.ajax({
			type : 'post',
			url : 'basicajax.action',
			dataType: 'json',
			data        : formData,
			success		: function(data) {result = data;}
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
	
	// ---------------------------------------------------------- 함수 정의
	
</script>


<style type="text/css">
	#refund
	{
		border: 1px solid #ccc;
		border-radius: 5px;
		width: 300px;
		padding: 15px;
		margin: 30px;
	}
	.back-default
	{
		background: #f6f6f6;
	}
</style>

</head>


<body class="back-default">
		
	<!-- header 출력부분 -->
	<div>
		<c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=${info.nick }"></c:import>
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
               <h1 class="mb-0 bread">기본 정보 입력</h1>
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
   <h1 class="mb-2 text-gray-800">기본정보 입력</h1>
   <p class="mb-4"> 기본정보를 입력하세요. <a target="_blank" href="#">이전으로</a>.</p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">기본정보 입력</h6>
         </div>


	
	<!-- form start --------------------------------------------->
	<form style="width: 80%; margin: 120px;" id="inputBasicInfo" enctype="multipart/form-data"
		  action="inputContactInfo.jsp" method="post">
			<!-- action="inputbasicinfo.action" -->
		<!-- 1. 공간명 -->

		<div>
			<!-- <label for="locName" style="font-weight: bold;">공간명 *</label> -->
			<span style="font-size: 14pt; font-weight: bold;">공간명  <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control" id="inputLocName" name="inputLocName"
				   placeholder="공간명을 입력하세요. [최소 2자 ~ 최대 20자]">
			<span id="err" style="font-weight: bold;"></span>

			<div style="padding-top: 40px;">
				<span style="color: gray">* 사용 가능한 특수문자: ( , ) , [ , ] , - , .(마침표), ,(쉼표)</span> <!-- 글자크기 작게 -->
			</div>
		</div>
	
	
	<br><br><br>
	
		
		<!-- 2. 공간 유형 -->
		
		<div>
			<span style="font-size: 14pt; font-weight: bold;">공간 유형  <span style="color: red">*</span></span>
			<br><br>
			<select id="inputLocType" name="inputLocType" class="form-control">
				<option value="">[==공간 유형을 선택하세요.==]</option>
				<option value="파티룸">파티룸</option>
				<option value="클럽">클럽</option>
				<option value="엠티장소">엠티장소</option>
				<option value="워크샵장소">워크샵장소</option>
				<option value="루프탑">루프탑</option>
				<option value="브라이덜샤워">브라이덜샤워</option>
			</select>
			<span style="font-weight: bold; display: block; color: red;">공간 유형을(를) 선택해야 합니다.</span>
			<br>
			<span style="color: red;">* 검수 후에는 유형변경이 불가합니다.</span>
		</div>
		
		
	<br><br><br>
	
		<!-- 3. 공간 한줄 소개 -->
		
		<div>
		
			<span style="font-size: 14pt; font-weight: bold;">공간 한줄 소개  <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control"
				   placeholder="공간을 한 문장으로 소개해주세요. [최소 4자 ~ 최대 20자]" 
				   id="inputShortIntro" name="inputShortIntro">
			<span style="font-weight: bold;"></span>
   		
		</div>
		
		
	<br><br><br>
	
	
		<!-- 4. 공간 소개 -->
		
		<div>
		
			<span style="font-size: 14pt; font-weight: bold;">공간 소개  <span style="color: red">*</span></span>
			<br><br>
			<textarea class="form-control" 
					  placeholder="공간을 상세하게 소개해주세요. [최소 20자 ~ 최대 400자]"
					  id="inputIntro" name="inputIntro" cols="40" rows="5"></textarea>
		  	<span style="font-weight: bold;"></span>
		</div>
		
	<br><br><br>
	
			
		<!-- 5. 시설안내 -->
		
		<div id="locationFacilityInfo">
			<span style="font-size: 14pt; font-weight: bold;">시설 안내 <span style="color: red">*</span></span>
			<br><br>
			<input class="form-control" 
				   placeholder="시설은 [최소 10자 ~ 최대 50자] 로 입력하여 10개까지 추가 가능합니다."
				   type="text" id="inputFacility" name="inputFacility">
				   <span style="font-weight: bold; display: none; color: red;"></span>
			<br>
			
			<input type="button" class="form-control" onclick="addFacilityInfo()"
					id="inputFacilityBtn" name="inputFacilityBtn" value="시설 추가 +">
			<input type="button" class="form-control" onclick="removeFacilityInfo()"
					id="inputFacilityBtn" name="inputFacilityBtn" value="시설 삭제 -">
	
			<!-- 5-1. 추가한 시설안내 내용이 보여지도록 하는 공간
					, 리스트 형태로 추가할때마다 순번부여,
					, "x" 버튼클릭시 추가한 내용 삭제가능 
					, (순번).(내용)(x버튼) 형식		<- 순번은 뺼 수도 있을듯.
			-->
		</div>
		
	
	
	<br><br><br>
	
		
		<!-- 6. 예약 시 주의사항 -->
		
		<div id="locationPrecautionInfo">
			
			<span style="font-size: 14pt; font-weight: bold;">예약 시 주의사항 <span style="color: red">*</span></span>
			<br><br>
			<ul class="precautions-list">
				<li>
				    <input class="form-control" id="inputPrecaution" 
						placeholder="예약 시 주의사항은 [최소 20자 ~ 최대 100자] 로 입력하여 10개까지 추가 가능합니다."/>
					<span style="font-weight: bold; display: none; color: red;"></span>
				</li>
			</ul>
			<br>
			<input type="button" class="form-control" id="inputPrecautionsBtn" onclick="addPrecautionInfo()"
				   name="inputPrecautionsBtn" value="예약 시 주의사항 추가 +">
				   <input type="button" class="form-control" id="inputPrecautionsBtn" onclick="removePrecautionInfo()"
				   name="inputPrecautionsBtn" value="예약 시 주의사항 삭제 -">
			
		</div>
		
		<br><br>
		<div id="refund">
				<span style="font-weight: bold; font-size: 12pt">※ 환불규정</span>
					<br><br>
					- 7일 전 : 100% 환불<br>
					- 6~1일 전 : 50% 환불<br><br>
					<span style="color: red;">-<ins>당일 환불 및 예약취소 불가</ins></span>
				<!-- 6-1. 추가한 예약 시 주의사항 내용이 보여지도록, 삭제 x 버튼클릭시 추가한 내용 삭제가능 -->
		</div>
		
	<br><br><br>
		
		
		<!-- 7. 대표이미지 -->
		<div>
			<span style="font-size: 14pt; font-weight: bold;">대표이미지 <span style="color: red">*</span></span>
			<br><br>
			<div class="filebox"> 
				<input class="upload-name" name="inputThumbnail" 
					   placeholder="이미지 등록" disabled="disabled" style="width: 70%">
				<label for="ex_filename"><span class="glyphicon fa fa-upload"></span></label> 
				<input type="file" name="thumbnail" id="ex_filename" class="upload-hidden">
			</div>
		</div>
		
		<!-- 
		<div>
		
			<span style="font-size: 14pt; font-weight: bold;">대표이미지 <span style="color: red">*</span></span>
			<br><br>
			이미지추가시 들어갈 공간.. textarea인지 확인 필요
			<textarea class="form-control" 
				      placeholder="대표이미지 파일을 등록해 주세요(JPG, JPEG, PNG)"
					  name="inputThumbnail" cols="40" rows="10"></textarea>
			<br>
			<input type="button" class="form-control" role="file"
					name="inputThumbnailBtn" value="이미지 등록">
			<br><br>
			<span>* 최소/최대 1장, 최대 5MB, 최대해상도 2048*1158 까지</span> 작은 글자
			
			7.1 추가 시 보여지는 default 이미지 해상도 xxx*xxx
			※ 다시 첨부하여 등록할 시 기존 이미지를 대체
			
		</div>
		 -->	
		<!-- 
		<div>
			<label for="inputThumbnail">이미지 등록</label>
			<input id="fileInput" type="file" 
				   data-class-button="btn btn-default" data-class-input="form-control" 
				   data-button-text="" data-icon-name="fa fa-upload" class="form-control"
				   tabindex="-1" style="position: absolute; clip:rect(0px 0px 0px 0px);">
				   
				<div class="bootstrapfilestyle input-group">
					<input type="text" id="userfile" class="form-control" name="userfile" disabled="disabled">
					<span class="group-span-filestyle input-group-btn" tabindex="0">
						<label for="fileInput" class="btn btn-default">
							<span class="glyphicon fa fa-upload"></span>
						</label>
					</span>
				</div>				   
		</div>	
			 -->
		
		

	<br><br><br>
		
		<!-- 8. 주소 -->
			
		<div>
			<span style="font-size: 14pt; font-weight: bold;">주소 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control"  id="inputAddr"
				   placeholder="주소를 입력해주세요." name="inputAddr" readonly>
			
			<input onclick="showDaumAddPop()" type="button" class="form-control"
				   value="주소 등록">
			<br><br>
			<span style="font-size: 13pt; font-weight: bold;">상세 주소 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control"  
					id="inputDetailAddr" name="inputDetailAddr">
			<!-- 입력 전 default 내용 : 상세 주소  -->
		
		</div>
	
	
	<br><br><br>
	
	<!-- 다음 버튼 -->
	<div class="container" style="text-align: center;">

		<!-- <input type="submit" value="다음" class="btn btn-warning" 
			   style="width:45%; border-color: gray;"> --> <!-- type="button" onsubmit="next()"
		style="display: block; margin: 0px auto" -->
		<button type="button" class="btn btn-warning" id="submitButton"
			style="width:45%; border-color: gray;">다음 </button>
		
	<!-- 취소 버튼 -->
		<input type="button" class="btn btn-default" style="align-content:center; width:45%; border-color: gray;" 
				id="BasicInfoCancel" value="취소" onclick="cancel();">		
		
	</div>	
	
	</form>
</div>

</div>
	

<!-- ※ 추가 구현해야하는 기능 -->

<!-- 1. 시설, 주의사항 삭제버튼 클릭 시 최근에 추가한 시설, 주의사항 삭제 기능 -->
<!-- 2. 시설, 주의사항 추가된 것이 1개이상이면 
	    입력창에서 입력하지않아도 넘어갈 수 있도록 -->
<!-- 3. 이미지 업로드 서버단에 저장 방법 -->
<!-- 4. 이미지 업로드 시 입력창에 자동입력되는 파일명의 형태를 img 링크로 받아오는 방법 -->
<!-- 5. jQuery hasClass 사용하여 제대로 입력되지않으면 submit 되지않도록 처리 -->	
<!--    → 입력한 내용을 받아와서 다시 함수들을 호출하여 검사, 잘못 입력했을시 false  -->
	
<br><br><br><br>
	
<!-- footer 출력부분 -->
<div>
       <c:import url="${cp}/includes/footer_host.jsp"></c:import>
       <c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</div>

</body>
</html>