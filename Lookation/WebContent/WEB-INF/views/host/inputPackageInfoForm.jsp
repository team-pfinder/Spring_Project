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
<title>inputPackageInfoForm.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<script type="text/javascript">
	
	
	// 패키지 추가 버튼 클릭시 inputPackageInfo.jsp 팝업
	function input()
	{
		location.href = "inputPackageInfo.jsp";
	}
	
	// 패키지 수정 버튼 클릭시 modifyPacakgeInfo.jsp 팝업
	// 선택한 라디오버튼의 값을 받아와 해당 패키지의 정보가 입력된 상태로 보여짐
	function mod()
	{
		location.href = "modifyPackageInfo.jsp";
	}
	
	// 패키지 삭제 버튼 클릭 시 
	// 선택한 라디오버튼의 값을 받아와 해당 패키지의 정보를 모두 삭제
	// (DB 상에서는 폐기된 패키지로 insert)
	function del()
	{
		/* location.href = "modifyPacakgeInfo.jsp"; */
	}
	
	
	// 공간 등록 및 검수 신청 버튼 클릭시
	// 여태까지의 공간 입력 정보를 DB에 insert 하고
	// 공간 검수 신청 confirm 처리 후 공간 관리페이지로 이동하는 function
	function locConfirm() {
		
		var conf = confirm("현재까지의 공간 입력정보를 저장하고 검수 신청을 하시겠습니까?");
		
		if (conf == true) {
			location.href = "locationList.jsp";
			return;
		} else {
			return;
		}
		
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
		  action="${pageContext.request.contextPath}/inputpackageinfoform.action" method="POST"><!--onsubmit="handOver()" -->
		
		<input type="hidden" name="inputLocName" value="${basicInfoDTO.inputLocName }" />
		<input type="hidden" name="inputLocType" value="${basicInfoDTO.inputLocType }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputShortIntro" value="${basicInfoDTO.inputShortIntro }" />
		<input type="hidden" name="inputIntro" value="${basicInfoDTO.inputIntro }" />
		<input type="hidden" name="inputFacility" value="${basicInfoDTO.inputFacility }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputPrecautions" value="${basicInfoDTO.inputPrecautions }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputThumbnail" value="${basicInfoDTO.inputThumbnail }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputAddr" value="${basicInfoDTO.inputAddr }" />
		<input type="hidden" name="inputDetailAddr" value="${basicInfoDTO.inputDetailAddr }" />
		
		<input type="hidden" name="inputEmail" value="${contactDTO.inputEmail }" />
		<input type="hidden" name="inputContact" value="${contactDTO.inputContact }" />
		<input type="hidden" name="inputMainContact" value="${contactDTO.inputMainContact }" />
		
		<input type="hidden" name="inputBizName" value="${businessInfoDTO.inputBizName }" />
		<input type="hidden" name="inputBizCeo" value="${businessInfoDTO.inputBizCeo }" />
		<input type="hidden" name="inputBizNum" value="${businessInfoDTO.inputBizNum }" />
		<input type="hidden" name="inputBizLicense" value="${businessInfoDTO.inputBizLicense }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputBizCeoType" value="${businessInfoDTO.inputBizCeoType }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputBizMainType" value="${businessInfoDTO.inputBizMainType }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputBizSubType" value="${businessInfoDTO.inputBizSubType }" /><!-- ※ 체크 -->
		<input type="hidden" name="inputAddr" value="${businessInfoDTO.inputAddr }" />
		<input type="hidden" name="inputDetailAddr" value="${businessInfoDTO.inputDetailAddr }" />
		
		<input type="hidden" name="inputUsingHour" value="${usingInfoDTO.inputUsingHour }">
		<input type="hidden" name="inputDayOff" value="${usingInfoDTO.inputDayOff }">
		<input type="hidden" name="inputAppointDayoff" value="${usingInfoDTO.inputAppointDayoff }">
			
		<input type="hidden" name="inputLocDetailImg" value="${detailInfoDTO.inputLocDetailImg }"><!-- ※ 체크 -->
		<input type="hidden" name="inputMinPeople" value="${detailInfoDTO.inputMinPeople }">
		<input type="hidden" name="inputMaxPeople" value="${detailInfoDTO.inputMaxPeople }">
		
			
	
		<!-- 1. 현재 패키지 (추가된 패키지 현황) --> 
		<div id="currentPackage">
			<span style="font-size: 14pt; font-weight: bold;">현재패키지 <span style="color: red">*</span></span>
				<br><br>
				<table class="table table-bordered table-hover" id="InsertedPacakge">
					<tr>
						<th>-</th>
						<th>패키지명</th>
						<th>패키지 시작시간</th>
						<th>패키지 종료시간</th>
						<th>패키지 가격</th>
					</tr>
					<tr>
						<td>
							<input type="radio" name="selectPackage">
						</td>						
						<td>올나잇(18시~익일 3시)</td>  <!-- 패키지명 -->
						<td>18시</td>					<!-- 패키지 시작시간 -->
						<td>익일 3시</td>				<!-- 패키지 종료시간 -->
						<td>100,000원</td>				<!-- 패키지 가격 -->
					</tr>
					<tr>
						<td>
							<input type="radio" name="selectPackage">
						</td>							<!-- LocationPackageInsert.jsp 에서 -->
						<td>저녁시간</td>				<!-- 패키지가 추가될때마다 tr,td노드가 추가 -->
						<td>10시</td>
						<td>23시</td>
						<td>100,000원</td>
					</tr>
					
					<!--	 :	 	-->
				</table>
		</div>
	
	<br><br>
	
		<!-- 2. 패키지 추가하기 -->
		<!--    → inputPackageInfo.jsp로 이동 -->
		<input type="button" class="form-control" value="패키지 추가"
			   onclick="input()">
		
		<!-- 3. 패키지 수정하기 
				→ 현재 패키지 중 하나를 선택후 수정버튼 클릭, 
				   선택하지않으면 alert("패키지를 선택하세요."), onsubmit="false"
				→ modifyPackageInfo.jsp로 이동 -->
		<input type="button" class="form-control" value="패키지 수정"
			   onclick="mod()"> <!-- onclick="function()" -->
		
		<!-- 4. 패키지 삭제하기
				→ 현재 패키지 중 하나를 선택후 삭제버튼 클릭, onsubmit="false"
				   선택하지않으면 alert("패키지를 선택하세요.") -->
		<input type="button" class="form-control" value="패키지 삭제"> 
		<!-- onclick="function()" 
			 → 삭제시 선택된 패키지의 현재패키지 리스트에서 
			 	모든 컬럼의 내용을 지운다(view에서만) -->
	
	
	<br><br><br><br>
	
	<div class="container" style="text-align: center;">
		<input type="submit" class="btn btn-warning"
				id="inputPackageInfoFormConfirm" style="width:45%; border-color: gray;"
				onclick="locConfirm()" value="공간 등록 및 검수 신청">
		 <!-- onclick="function()" submit → LocationPacakgeMgmt.jsp -->
		
		<!-- 취소 버튼 -->
		<input type="button" id="inputPackageInfoFormCancel"
				   class="btn btn-default" style="align-content:center; width:45%; border-color: gray;"  
				   onclick="cancel()" value="취소"> <!-- onclick="function()" -->
	
	</div>

	
	<br><br><br><br>

	</form>
</div>

</div>
	<br><br><br><br>
	
<div>
	<c:import url="${cp}/includes/footer_host.jsp"></c:import>
	<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</div>
</body>
</html>