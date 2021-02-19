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
<title>이용정보 수정</title>

<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<script type="text/javascript">


	$(function ()
	{
		// enter submit 방지
		document.addEventListener('keydown', function(event) {
			
			if (event.keyCode === 13) {
			    event.preventDefault();
		  	};
		  	   
		}, true);
		
		
		// submit 제어
		
		$('#submitButton').click(function() {
			
			var f = $("#inputUsingInfo");
			
			var tUsingHour = $("#inputUsingHour").val(); 
			var tDayOff = $("#inputDayOff").val();
			var tAppointDayoff = $("#inputAppointDayoff").val(); 
			
			var con;
			
			if (tUsingHour == "" || tDayOff == "" || tAppointDayoff == "" ) 
			{
				 
				alert("필수 입력사항을 모두 입력해 주세요.");
			}
			else 
			{
				f.submit();
				
				con = confirm("해당 공간의 모든 정보 수정 내용을 저장하시겠습니까?");
				
				if (con == true) 
				{
					
					f.submit();
					alert("해당 공간의 모든 정보 수정이 완료되었습니다.");
					return;
					
				} 
				else 
				{
					return;
				}
				
				
			}
				
			
		});
			
	});
	

	// 취소 버튼 클릭시 기존 작성내용을 저장하지 않고 메인 홈페이지로 이동하는 function
	function cancel() {
		
		var con = confirm("작성을 취소하고 메인 페이지로 돌아가시겠습니까?                        "
						+ "(이전 페이지까지 작성한 내용은 저장됩니다.)");
		
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
                   
                  <span>공간 수정 
                  	<i class="ion-ios-arrow-forward"></i>
                  </span>
               </p>
               <h1 class="mb-0 bread">이용안내 수정</h1>
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
   <h1 class="mb-2 text-gray-800">이용안내 수정</h1>
   <p class="mb-4"> 이용안내를 수정하세요. <a target="_blank" href="#"></a></p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">이용안내 수정</h6>
         </div>

	
	<form style="width: 80%; margin: 120px;" id="inputUsingInfo" 
		  action="modifyusinginfo.action?loc_code=${loc_code }" method="post">
		  
		<!-- 1. 이용시간 -->
		
		<div id="usingHour">
			
			<span style="font-size: 14pt; font-weight: bold;">이용시간 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control" id="inputUsingHour" name="inputUsingHour"
				   placeholder="이용시간을 입력하세요(최대 34 시간, ex)오전 11시 ~ 오후 10시)"
				   maxlength="50"
				   value="${usingInfoList.loc_use_hour }">
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
			<input type="text" class="form-control"
				   id="inputDayOff" name="inputDayOff"
				   placeholder="정기 휴무일을 입력하세요. (ex) 매월 둘째주 일요일, 월요일)"
				   value="${usingInfoList.loc_use_day_off }" maxlength="50">
			<br>
			
		</div>
		
		<br><br><br>
		
		<!-- 3. 지정 휴무일 -->
		
		<div id="appointDayoff">
		
			<span style="font-size: 14pt; font-weight: bold;">지정 휴무일 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" id="inputAppointDayoff" name="inputAppointDayoff"
				   class="form-control"
				   placeholder="지정 휴무일을 입력하세요. (ex) 4월 5일, 8월 9일, ...)"
				   value="${usingInfoList.loc_use_appoint_day_off }" maxlength="50">
			<br>
			<span></span> <!-- 글자 크기 작게 -->
			
		</div>
	
	
	
	<br><br><br>
	
	<!-- 다음 버튼 -->
	<div class="container" style="text-align: center;">

		<button type="button" class="btn btn-warning" id="submitButton"
			style="width:45%; border-color: gray;">수정 완료 </button>
	
	<!-- 취소 버튼 -->
		<input type="button" class="btn btn-default" style="align-content:center; width:45%; border-color: gray;" 
				id="UsingInfoCancel" value="취소" onclick="cancel()">		
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
