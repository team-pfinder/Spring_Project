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
<title>LocationPackageFormUpdate.jsp</title>
<%@ include file="../includes/includes_home.jsp" %>
<%@ include file="../includes/includes_menu.jsp" %>

</head>
<body>


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
               <h1 class="mb-0 bread">패키지정보 수정</h1>
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
   <h1 class="mb-2 text-gray-800">패키지정보 수정</h1>
   <p class="mb-4"> 패키지정보를 수정하세요. <a target="_blank" href="#">이전으로</a>.</p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">패키지정보 수정</h6>
         </div>



<form style="width: 800px; margin: 120px;">

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
	<!--    → LocationPackageInsert.jsp로 이동 -->
	<input type="button" class="form-control" value="패키지 추가"> <!-- onclick="function()" -->
	
	<!-- 3. 패키지 수정하기 
			→ 현재 패키지 중 하나를 선택후 수정버튼 클릭, 
			   선택하지않으면 alert("패키지를 선택하세요."), onsubmit="false"
			→ LocationPackageUpdate.jsp로 이동 -->
	<input type="button" class="form-control" value="패키지 수정"> <!-- onclick="function()" -->
	
	<!-- 4. 패키지 삭제하기
			→ 현재 패키지 중 하나를 선택후 삭제버튼 클릭, onsubmit="false"
			   선택하지않으면 alert("패키지를 선택하세요.") -->
	<input type="button" class="form-control" value="패키지 삭제"> 
	<!-- onclick="function()" 
		 → 삭제시 선택된 패키지의 현재패키지 리스트에서 
		 	모든 컬럼의 내용을 지운다(view에서만) -->


<br><br><br><br>

<div class="container">
<!-- 수정 확인 버튼
	(※ 패키지 적용까지 최종 완료, 
		현재 모든 현재패키지의 정보(적용된 날짜 포함)를 참조하여 
		DB에 적용시킨다. 
	(※ 그리고 LocationMgmt.jsp 에 현 공간코드를 참조하는
		모든 정보를 DB에 저장, LocationMgmt.jsp 에 등록된 공간이 보여질 수 있도록 한다.
	(※ 클릭 시 alert("현재까지의 수정 내용을 저장하시겠습니까?") → <확인> / <취소>) 
 -->
 
<input type="submit" class="btn btn-warning"
		id="LocationPacakgeFormSave" style="width:300px;"
		value="수정 확인">
 <!-- onclick="function()" submit → LocationPacakgeMgmt.jsp -->

<!-- 취소 버튼 -->
<input type="button" id="LocationPacakgeFormCancel"
		   class="btn btn-default" style="width:300px;" 
		   value="취소"> <!-- onclick="function()" -->

</div>

	<%@ include file="../includes/includes_home_end.jsp"%>
	

<br><br><br><br>

</form>
</div>
</div>

</body>
</html>