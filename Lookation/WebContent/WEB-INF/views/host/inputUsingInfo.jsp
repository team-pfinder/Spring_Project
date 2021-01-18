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
<title>locationUsingInfo.jsp</title>

<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<script type="text/javascript">

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
               <h1 class="mb-0 bread">이용안내 입력</h1>
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
   <h1 class="mb-2 text-gray-800">이용안내 입력</h1>
   <p class="mb-4"> 이용안내를 입력하세요. <a target="_blank" href="#">이전으로</a>.</p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">이용안내 입력</h6>
         </div>



	<!-- form start --------------------------------------------->
	<form style="width: 80%; margin: 120px;" id="inputUsingInfo" 
		  action="inputDetailInfo.jsp" method="POST"><!--onsubmit="handOver()" -->
		  <!-- 컨트롤러 구성, 매핑 후 → action="inputxxxInfo.action" 로 변경 -->
	
		
		<!-- 1. 이용시간 -->
		
		<div id="usingHour">
			
			<span style="font-size: 14pt; font-weight: bold;">이용시간 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control" id="inputUsingHour" required="required"
				   placeholder="이용시간을 입력하세요(최대 34 시간, ex)오전 11시 ~ 오후 10시)">
			<!-- 종료시간 : 시작시간보다 이후부터 
			(최대 : 시작시간 + 34시까지)  -->
			<br><br>
			<span style="color: red;">※ 실제 이용 가능한 시간으로 설정해야 합니다.</span>
			
		</div>
		
		<br><br><br>
		
		<!-- 2. 정기 휴무일 -->
		
		<div id="dayOff">
		
			<span style="font-size: 14pt; font-weight: bold;">정기 휴무일 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control" required="required"
				   id="inputDayOff" name="inputDayOff"
				   placeholder="정기 휴무일을 입력하세요. (ex) 매월 둘째주 일요일, 월요일)">
			<br>
			
		</div>
		
		<br><br><br>
		
		<!-- 3. 지정 휴무일 -->
		
		<div id="appointDayoff">
		
			<span style="font-size: 14pt; font-weight: bold;">지정 휴무일 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" id="inputAppointDayoff" name="inputAppointDayoff"
				   class="form-control" required="required"
				   placeholder="지정 휴무일을 입력하세요. (ex) 4월 5일, 8월 9일, ...)">
			<br>
			<span></span> <!-- 글자 크기 작게 -->
			
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
					id="UsingInfoCancel" value="취소" onclick="cancel()">		
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
