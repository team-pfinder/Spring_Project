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
<title>LocationDetailedInfo.jsp</title>

<link rel="stylesheet" href="css/bootstrap.css">
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<script type="text/javascript">
	
	$(function() {

		$('#inputMinPeople').on("keyup", function ()
		{
			var inputMin = setMinPeople($('#inputMinPeople'), 1, 10, '최소 수용인원');

			var err = $(this).next();
			err.css("display", "none");
			
				var inputMax = setMaxPeople($('#inputMaxPeople'), inputMin, 30, '최대 수용인원');
				
				if (inputMax != null && inputMin > inputMax) {
						
					err.html("최대 수용인원이 최소 수용인원 보다 작습니다.").css("display", "inline");
					err.css("color", "red");
				
			}
		});
				
	});
				
	
	
	// 최소 수용인원 수 제약 functiond
	function setMinPeople(target, minNum, maxNum, name){
		
		target.on("keyup", function() {
			
			var err = $(this).next();
			err.css("display", "none");
			
			// 글자 수 제한, 색 변경
			if (target.val()=="" || parseInt(target.val()) < minNum || parseInt(target.val()) > maxNum ) {
				
				err.html("" + name + "은 " + minNum + " 이상 ~ " + maxNum + " 이하로 설정해야합니다.").css("display","inline");
				err.css("color", "red");
				return;
				
			} else {
				
				err.html("사용 가능한 " + name + "입니다.").css("display","inline");
				err.css("color","green");
				return target;
			}
		});
	}
	
	// 최대 수용인원 수 제약 function
	function setMaxPeople(target, minNum, maxNum, name){
		
		target.on("keyup", function() {
			
			var err = $(this).next();
			err.css("display", "none");
			
			// 글자 수 제한, 색 변경
			if (target.val()=="" || parseInt(target.val()) < minNum || parseInt(target.val()) > maxNum ) {
				
				err.html("" + name + "은 " + minNum + " 이상 ~ " + maxNum + " 이하로 설정해야합니다.").css("display","inline");
				err.css("color", "red");
				return;
				
			} else {
				
				err.html("사용 가능한 " + name + "입니다.").css("display","inline");
				err.css("color","green");
				return target;
			}
		});
	}
	  
	// 입력한 최대 수용인원보다 최소 수용인원이 더 큰 경우 처리 function
	/* function maxTest() {
		
		var inputMin = parseInt($.trim($('#inputMinPeople').val()));

		return inputMin;
	} */
	
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
               <h1 class="mb-0 bread">상세정보 입력</h1>
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
   <h1 class="mb-2 text-gray-800">상세정보 입력</h1>
   <p class="mb-4"> 상세정보를 입력하세요. <a target="_blank" href="#">이전으로</a>.</p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">상세정보 입력</h6>
         </div>


	<!-- form start --------------------------------------------->
	<form style="width: 80%; margin: 120px;" id="inputDetailInfo" 
		  action="${pageContext.request.contextPath}/inputdetailinfo.action" method="POST"><!--onsubmit="handOver()" -->
		
		<input type="hidden" name="inputLocName" value="${basicInfoDTO.inputLocName }" />
		<input type="hidden" name="inputLocType" value="${basicInfoDTO.inputLocType }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputShortIntro" value="${basicInfoDTO.inputShortIntro }" />
		<input type="hidden" name="inputIntro" value="${basicInfoDTO.inputIntro }" />
		<input type="hidden" name="inputFacility" value="${basicInfoDTO.inputFacility }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputPrecautions" value="${basicInfoDTO.inputPrecautions }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputThumbnail" value="${basicInfoDTO.inputThumbnail }" />
		<input type="hidden" name="inputAddr" value="${basicInfoDTO.inputAddr }" />
		<input type="hidden" name="inputDetailAddr" value="${basicInfoDTO.inputDetailAddr }" />
		
		<input type="hidden" name="inputEmail" value="${contactDTO.inputEmail }" />
		<input type="hidden" name="inputContact" value="${contactDTO.inputContact }" />
		<input type="hidden" name="inputMainContact" value="${contactDTO.inputMainContact }" />
		
		<input type="hidden" name="inputBizName" value="${businessInfoDTO.inputBizName }" />
		<input type="hidden" name="inputBizCeo" value="${businessInfoDTO.inputBizCeo }" />
		<input type="hidden" name="inputBizNum" value="${businessInfoDTO.inputBizNum }" />
		<input type="hidden" name="inputBizLicense" value="${businessInfoDTO.inputBizLicense }" />
		<input type="hidden" name="inputBizCeoType" value="${businessInfoDTO.inputBizCeoType }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputBizMainType" value="${businessInfoDTO.inputBizMainType }" />
		<input type="hidden" name="inputBizSubType" value="${businessInfoDTO.inputBizSubType }" />
		<input type="hidden" name="inputAddr" value="${businessInfoDTO.inputAddr }" />
		<input type="hidden" name="inputDetailAddr" value="${businessInfoDTO.inputDetailAddr }" />
		
		<input type="hidden" name="inputUsingHour" value="${usingInfoDTO.inputUsingHour }">
		<input type="hidden" name="inputDayOff" value="${usingInfoDTO.inputDayOff }">
		<input type="hidden" name="inputAppointDayoff" value="${usingInfoDTO.inputAppointDayoff }">
		
		
		
		<!-- 1. 이미지 --><!-- ※ 보류 -->
		
		<div id="locDetailImg">
		
			<span style="font-size: 14pt; font-weight: bold;">이미지 <span style="color: red">*</span></span>
			<!-- 이미지추가시 들어갈 공간.. textarea인지 확인 필요-->
			<br><br>
				<textarea class="form-control" placeholder="상세이미지를 추가하세요.(JPG, JPEG, PNG)"
							name="inputLocDetailImg"
							cols="40" rows="10" required="required"></textarea>
				<br>
				<input type="button" class="form-control"
						name="inputLocDetailImgBtn" value="상세이미지 추가 +">
				<br><br>
				<span style="color: gray">* 최소/최대 1장, 최대 5MB, 최대해상도 2048*1158</span>
					 <!-- 
					 이미지가 추가될 때 마다 
					 추가한 이미지를 썸네일 형태로 조회할 수 있음.
					 그 썸네일 형태로 조회된 이미지를 클릭시 
					 체크박스형태로 복수선택하여 x버튼으로 삭제가능
					  -->
		</div>
	
	<br><br><br>
	
		<!-- 2. 최소 수용인원 -->
		
		<div id="minPeople">
		
			<span style="font-size: 14pt; font-weight: bold;">최소 수용인원 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" required="required" class="form-control"
					placeholder="최소 수용인원을 입력하세요.[최소 1명 이상 ~ 최대 10명 이하]"
					id="inputMinPeople" name="inputMinPeople">
					<!-- oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" --> 
			<span id="err" style="font-weight: bold;"></span>
			
		</div>
	
	<br><br><br>
		
		
		<!-- 3. 최대 수용인원 -->
			
		<div id="maxPeople">
		
			<span style="font-size: 14pt; font-weight: bold;">최대 수용인원 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" required="required" class="form-control"
					placeholder="최대 수용인원을 입력하세요.[최소 수용인원 이상, 최대 30명 이하]"
					id="inputMaxPeople" name="inputMaxPeople">
					<!-- oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1'); -->
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
					id="detailInfo" value="취소" onclick="cancel()">		
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