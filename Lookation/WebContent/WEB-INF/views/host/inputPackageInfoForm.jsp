<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String loc_code = request.getParameter("loc_code");
	pageContext.setAttribute("loc_code", loc_code);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>패키지정보 추가</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<script type="text/javascript">
	
	
	// 패키지 추가 버튼 클릭시 inputPackageInfo.jsp 팝업
	function input()
	{
		location.href = "inputPackageInfo.jsp";
	}
	/* 
	// 패키지 수정 버튼 클릭시 modifyPacakgeInfo.jsp 팝업
	// 선택한 라디오버튼의 값을 받아와 해당 패키지의 정보가 입력된 상태로 보여짐
	function mod()
	{
		location.href = "modifyPackageInfo.jsp";
	}
	 */
	// 패키지 삭제 버튼 클릭 시 
	// 선택한 라디오버튼의 값을 받아와 해당 패키지의 정보를 모두 삭제
	// (DB 상에서는 폐기된 패키지로 insert)
	function del()
	{
		// 라디오 박스 선택 여부 확인
		if(!$('input:radio[name=selectPackage]').is(':checked'))
		{
			alert("삭제할 패키지를 먼저 선택하세요.");
			return;
		}
		
		//
		if(confirm("정말 해당 패키지를 삭제하겠습니까?"))
		{
			$('#inputPackageInfoForm').submit();
		}
		
		/* location.href = "modifyPacakgeInfo.jsp"; */
	}
	 
	/* 
	// 공간 등록 및 검수 신청 버튼 클릭시
	// 여태까지의 공간 입력 정보를 DB에 insert 하고
	// 공간 검수 신청 confirm 처리 후 공간 관리페이지로 이동하는 function
	function locConfirm() {
		
		if(confirm("작성을 취소하고 메인 페이지로 돌아가시겠습니까?                        "
				+ "(기존 작성 내용은 저장되지 않습니다.)"))
		{
			location.href = "locationlist.action";
		}	
		
	}
	*/
	
	// 취소 버튼 클릭시 기존 작성내용을 저장하지 않고 메인 홈페이지로 이동하는 function
	function cancel() {
		
		if(confirm("작성을 취소하고 메인 페이지로 돌아가시겠습니까?                        "
				+ "(기존 작성 내용은 저장되지 않습니다.)"))
		{
			location.href = "locationlist.action";
		}	
		
	}
	
</script>
</head>
<body>
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
   <p class="mb-4"> 패키지정보를 입력하세요. <a target="_blank" href="#"></a></p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">패키지정보 입력</h6>
         </div>


	<!-- form start --------------------------------------------->
	<form style="width: 80%; margin: 120px;" id="inputPackageInfoForm" 
		  action="deletepackageform.action?loc_code=${loc_code }" method="POST"><!--onsubmit="handOver()" -->
			
	
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
					
					<c:forEach var="form" items="${formList }">
						<tr>
							<td>
								<input type="radio" name="selectPackage" value="${form.code }">
							</td>						
							<td>${form.name }</td>  
							<td>${form.time_start }시</td>					
							<c:if test="${form.time_end <= 24 }">
								<td> ${form.time_end}시</td>
							</c:if>
							<c:if test="${form.time_end > 24 }">
								<td>익일 ${form.time_end - 24}시</td>
							</c:if>			
							<td>${form.price }</td>		
						</tr>		
					</c:forEach>
				</table>
		</div>
	
	<br><br>
	
		<!-- 2. 패키지 추가하기 -->
		<!--    → inputPackageInfo.jsp로 이동 -->
		<input type="button" class="form-control" value="패키지 추가"
			   onclick="location.href='packageform.action?loc_code=${loc_code}'">
		
		<!-- 3. 패키지 수정하기 
				→ 현재 패키지 중 하나를 선택후 수정버튼 클릭, 
				   선택하지않으면 alert("패키지를 선택하세요."), onsubmit="false"
				→ modifyPackageInfo.jsp로 이동 -->
		<!-- <input type="button" class="form-control" value="패키지 수정"
			   onclick="mod()"> --> <!-- onclick="function()" -->
		
		<!-- 4. 패키지 삭제하기
				→ 현재 패키지 중 하나를 선택후 삭제버튼 클릭, onsubmit="false"
				   선택하지않으면 alert("패키지를 선택하세요.") -->
		<input type="button" class="form-control" value="패키지 삭제"
		       onclick="del()"> 
		<!-- onclick="function()" 
			 → 삭제시 선택된 패키지의 현재패키지 리스트에서 
			 	모든 컬럼의 내용을 지운다(view에서만) -->
	
	
	<br><br><br><br>
	
	<div class="container" style="text-align: center;">
		<!-- <input type="submit" class="btn btn-warning"
				id="inputPackageInfoFormConfirm" style="width:45%; border-color: gray;"
				onclick="locConfirm()" value="공간 등록 및 검수 신청"> -->
		 <!-- onclick="function()" submit → LocationPacakgeMgmt.jsp -->
		
		<!-- 취소 버튼 -->
		<input type="button" id="inputPackageInfoFormCancel"
				   class="btn btn-warning" style="align-content:center; width: 100%; border-color: gray;"  
				   onclick="cancel()" value="돌아가기"> <!-- onclick="function()" -->
	
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