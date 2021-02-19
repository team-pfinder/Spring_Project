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
<title>상세정보 추가</title>

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

<script type="text/javascript">
	
	$(function() {

		// 함수 호출
		
		setMinPeople($('#inputMinPeople'), 1, 9, '최소 수용인원');
		setMaxPeople($('#inputMaxPeople'), 1, '최대 수용인원');
		
		// submit 제어
		var f = $('#inputDetailInfo');
		
		$('#submitButton').click(function () 
		{	
			
			if ($('#inputMinPeople').val() == '' 
				|| $('#inputMaxPeople').val() == '' 
				|| $('#inputWebUrl').val() == ''
				|| ($('input[name=detailImage1]').val() == '' 
						&& $('input[name=detailImage2]').val() == '' 
							&& $('input[name=detailImage3]').val() == '' 
								&& $('input[name=detailImage4]').val() == '' 
									&& $('input[name=detailImage5]').val() == '' 
										&& $('input[name=detailImage6]').val() == '' 
											&& $('input[name=detailImage7]').val() == '' 
												&& $('input[name=detailImage8]').val() == '' 
													&& $('input[name=detailImage9]').val() == '' 
														&& $('input[name=detailImage10]').val() == '') ) {
				
				alert("필수 입력사항을 모두 입력해 주세요.");
				
			}
			else if (parseInt($('#inputMinPeople').val()) < 1
					|| parseInt($('#inputMinPeople').val()) > 9)
			{
				alert("최소 수용인원은 최소 1명 ~ 최대 9명으로 입력해야합니다.");
				$('#inputMinPeople').focus();
			}
			else if (parseInt($('#inputMaxPeople').val()) > 30)
			{
				alert("최대 수용인원은 최대 30명까지 입력가능합니다.");
				$('#inputMaxPeople').focus();
			}
			else if (parseInt($('#inputMinPeople').val()) > $('#inputMaxPeople').val())
			{
				alert("최소 수용인원이 최대 수용인원 보다 큽니다.")
				$('#inputMinPeople').focus();
			}
			else
			{
				f.submit();				
			}
			
		});
		
	});
		
	// 함수 정의 ---------------------------------------------------------	
	
	
	// 최소 수용인원 수 제약 function
	function setMinPeople(target, minNum, maxNum, name){
		
		target.on("keyup", function() {
			
			var err = $(this).next();
			err.css("display", "none");
			
			if (target.val()=='') {
				
				err.html.hide();
				return false;
			}
			
			// 글자 수 제한, 색 변경
			else if (parseInt(target.val()) < minNum 
					|| parseInt(target.val()) > maxNum ) {
				
				err.html("" + name + "은 " + minNum + " 이상 ~ " + maxNum + " 이하로 설정해야합니다.").css("display","inline");
				err.css("color", "red");
				return;
				
			} 
			else {
				
				err.html("사용 가능한 " + name + "입니다.").css("display","inline");
				err.css("color","green");
				return target;
			}
		});
	}
	
	 
	
	// 최대 수용인원 수 제약 function
	function setMaxPeople(target, minNum, name) {
		
		target.on("keyup", function ()
		{
			var err = $(this).next();
			err.css("display", "none");
			
			// 최대 수용인원 x
			if (target.val()=='') {

				err.html.hide();
				return;
			}
			// 글자 수 제한, 색 변경
			else if (parseInt(target.val()) > 30 
					|| parseInt(target.val()) < minNum) {
				
				err.html("" + name + "은 " + "30 이하로 설정해야합니다.").css("display","inline");
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
	<!-- header 출력부분 -->
	<div>
		<c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=${info.nick }"></c:import>
	</div>
	
   <!-- 타이틀 -->
   <section class="hero-wrap hero-wrap-2"
      style="background-image: url('<%=cp%>/images/bg_3.jpg');"
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
   <p class="mb-4"> 상세정보를 입력하세요. <a target="_blank" href="#"></a></p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">상세정보 입력</h6>
         </div>


	<!-- form start --------------------------------------------->
	<form style="width: 80%; margin: 120px;" id="inputDetailInfo"  enctype="multipart/form-data"
		  action="inputdetailinfo.action" method="POST">	
		
		<!-- 1. 이미지 --><!-- ※ 보류 -->
		
		<div id="locDetailImg">
		
			<span style="font-size: 14pt; font-weight: bold;">이미지 <span style="color: red">*</span></span>
			<br><br>
			
		<c:forEach var="i" begin="1" end="10">
				<div class="filebox${i }">
					<input type="file" name="detailImage${i }" id="ex_filename" class="upload-hidden">
				</div>
		</c:forEach>
				<br><br>
				<span style="color: gray">각 1장, 최대 10MB 제한</span>
		</div>
	
	<br><br><br>
	
		<!-- 2. 최소 수용인원 -->
		
		<div id="minPeople">
		
			<span style="font-size: 14pt; font-weight: bold;">최소 수용인원 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control"
					placeholder="최소 수용인원을 입력하세요.[최소 1명 이상 ~ 최대 9명 이하]"
					id="inputMinPeople" name="inputMinPeople"
					onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
					<!-- onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" -->
					<!-- oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"--> 
			<span id="err" style="font-weight: bold;"></span>
			
		</div>
	
	<br><br><br>
		
		
		<!-- 3. 최대 수용인원 -->
			
		<div id="maxPeople">
		
			<span style="font-size: 14pt; font-weight: bold;">최대 수용인원 <span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control"
					placeholder="최대 수용인원을 입력하세요.[최소 수용인원 이상, 최대 30명 이하]"
					id="inputMaxPeople" name="inputMaxPeople"
					onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
					<!-- onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" -->
					<!-- oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1'); -->
			<span style="font-weight: bold;"></span>
		</div>
	
	<br><br><br>
	
		<!-- 4. 웹 url -->
		
		<div id="webUrl">
			
			<span style="font-size: 14pt; font-weight: bold;">웹 사이트<span style="color: red">*</span></span>
			<br><br>
			<input type="text" class="form-control"
					placeholder="웹사이트 url 을 입력하세요."
					id="inputWebUrl" name="inputWebUrl">
			
			<span style="font-weight: bold;"></span>
		</div>
	
	<br><br><br>
	
	<!-- 다음 버튼 -->
	<div class="container" style="text-align: center;">
		<button type="button" class="btn btn-warning" id="submitButton"
		style="width:45%; border-color: gray;">다음 </button>
	
	<!-- 취소 버튼 -->
		<input type="button" class="btn btn-default" style="align-content:center; width:45%; border-color: gray;" 
				id="detailInfo" value="취소" onclick="cancel()">		
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