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
<title>modifyBasicInfo.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript" src="<%=cp%>/js/jquery-ui.js"></script>

<script type="text/javascript">

		/* <input type="text" class="form-control"  
	   name="locName" id="locName"
	   placeholder="공간명을 입력하세요. [최소 2자 ~ 최대 20자]" required="required"> */
	
	   
	// 1-1. 공간명 글자수 제한
	$(document).ready(function()
	{
		$("locName").on("keyup", function()
		{
			if ($(this).val().length > 20)
			{
				// $(this).val($(this).val().substring(0,19));
				
			}
		});
	});
	   
	   

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
</style>


</head>
<body>
	<div>
		<c:import url="${cp}/includes/header_host.jsp"></c:import>
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
                   
                  <span>공간 관리 
                  	<i class="ion-ios-arrow-forward"></i>
                  </span>
                  
                  <a href="locationMgmt.jsp">공간 수정 
                  		<i class="ion-ios-arrow-forward"></i>
                  	</a>
               </p>
               <h1 class="mb-0 bread">기본 정보 수정</h1>
            </div>
         </div>
      </div>
   </section>
   <!-- END 타이틀 -->

   <!-- 본문 -->
<!-- container 시작 -->
<!-- 마이페이지에서 예약내역 들어온 모습 -->
<!-- ※ 수정해야할 부분 : 검색창, 버튼크기때문에 열 높이 달라지는 부분 -->

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



<form style="width: 70%; margin: 120px;">

	<!-- 1. 공간명 -->
	
	<div id="locationName">
		<!-- <label for="locName" style="font-weight: bold;">공간명 *</label> -->
		<span style="font-size: 14pt; font-weight: bold;">공간명 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" class="form-control"  
			   name="locName" id="locName"
			   placeholder="공간명을 입력하세요. [최소 2자 ~ 최대 20자]" required="required">
		<br>
		<span style="color: gray">* 사용 가능한 특수문자: ( , ) , [ , ] , - , .(마침표), ,(쉼표)</span> <!-- 글자크기 작게 -->
	</div>


<br><br><br>


	<!-- 2. 공간 유형 --><!-- ※ 변경불가 항목 : 공간 유형, 주소, 상세주소 -->
	
	<!-- 선택했던 공간유형을 불러와 해당 옵션을 selected 된 상태로 
		 default 값을 두고, disabled 시킨다. -->

	<div id="locationType">
		<span style="font-size: 14pt; font-weight: bold;">공간 유형 <span style="color: red">*</span></span>
		<br><br>
		<select id="locType" class="form-control" disabled="disabled">
			<option value="">[==공간 유형을 선택하세요.]==</option>
			<option value="파티룸">파티룸</option>
			<option value="클럽">클럽</option>
			<option value="엠티장소">엠티장소</option>
			<option value="워크샵장소">워크샵장소</option>
			<option value="루프탑">루프탑</option>
			<option value="브라이덜샤워">브라이덜샤워</option>
		</select>
		<br>
		<span style="color: red;">* 검수 후에는 유형변경이 불가합니다.</span>
	</div>
	
	
<br><br><br>

	<!-- 3. 공간 한줄 소개 -->
	
	<div id="locationOneLineComment">
	
		<span style="font-size: 14pt; font-weight: bold;">공간 한줄 소개 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" class="form-control" required="required" 
			   placeholder="공간을 한 문장으로 소개해주세요. [최소 4자 ~ 최대 20자]" 
			   id="locationOneLineComment" name="locationOneLineComment">
	</div>
	
	
<br><br><br>


	<!-- 4. 공간 소개 -->
	
	<div id="locationComment">
	
		<span style="font-size: 14pt; font-weight: bold;">공간 소개 <span style="color: red">*</span></span>
		<br><br>
		<textarea class="form-control" required="required" 
				  placeholder="공간을 상세하게 소개해주세요. [최소 20자 ~ 최대 400자]"
				  name="LocationComment" id="LocationComment" cols="40" rows="5"></textarea>
	</div>
	
	
<br><br><br>

		
	<!-- 5. 시설안내 -->
	
	<div id="locationFacilityInfo">
	
		<span style="font-size: 14pt; font-weight: bold;">시설 안내 <span style="color: red">*</span></span>
		<br><br>
		<input class="form-control" required="required"
			   placeholder="시설은 [최소 10자 ~ 최대 50자] 로 입력하여 10개까지 추가 가능합니다."
			   type="text" name="LocationFacilityInfo">
		<br>
		<input type="button" class="form-control"
				name="LocationFacilityInfoInsert" value="시설 추가 +">

		<!-- 5-1. 추가한 시설안내 내용이 보여지도록 하는 공간
				, 리스트 형태로 추가할때마다 순번부여,
				, "x" 버튼클릭시 추가한 내용 삭제가능 
				, (순번).(내용)(x버튼) 형식		<- 순번은 뺼 수도 있을듯.
		-->
	</div>


<br><br><br>

	
	<!-- 6. 예약 시 주의사항 -->
	
	<div id="locationPrecautions">
		
		<span style="font-size: 14pt; font-weight: bold;">예약 시 주의사항 <span style="color: red">*</span></span>
		<br><br>
		<input class="form-control" required="required"
			   placeholder="[최소 20자 ~ 최대 100자] 로 입력하여 10개까지 추가 가능합니다."
		type="text" name="locationPrecautions">
		<br>
		<input type="button" class="form-control"
			   name="locationPrecautionsInsert" value="예약 시 주의사항 추가 +">
		<br><br>
		
		<div id="refund">
			<span style="font-weight: bold; font-size: 12pt">※ 환불규정</span>
				<br><br>
				- 7일 전 : 100% 환불<br>
				- 6~1일 전 : 50% 환불<br><br>
				<span style="color: red;">-<ins>당일 환불 및 예약취소 불가</ins></span>
			<!-- 6-1. 추가한 예약 시 주의사항 내용이 보여지도록, 삭제 x 버튼클릭시 추가한 내용 삭제가능 -->
		</div>
		
	</div>

	
<br><br><br>
	
	
	<!-- 7. 대표이미지 -->
	<div id="locationThumbnail">
	
		<span style="font-size: 14pt; font-weight: bold;">대표이미지 <span style="color: red">*</span></span>
		<br><br>
		<!-- 이미지추가시 들어갈 공간.. textarea인지 확인 필요-->
		<textarea class="form-control" required="required"
			      placeholder="대표이미지 파일을 추가해 주세요(JPG, JPEG, PNG)"
				  name="locationThunmbnail" cols="40" rows="10"></textarea>
		<br>
		<input type="button" class="form-control" 
				name="locationThumbnailInsert"
				 value="이미지 추가 +">
		<br><br>
		<span>* 최소/최대 1장, 최대 5MB, 최대해상도 2048*1158 까지</span> <!-- 작은 글자 -->
		
		<!-- 7.1 추가 시 보여지는 default 이미지 해상도 xxx*xxx -->
		<!-- ※ 다시 첨부하여 등록할 시 기존 이미지를 대체 -->
		
	</div>
		
	
<br><br><br>
	
	
<!-- 8. 주소 --><!-- ※ 변경불가 항목 : 공간 유형, 주소, 상세주소 -->

<!-- 등록했던 주소, 상세주소 정보를 불러와 
	 해당 텍스트를 disabled 시킨상태를 default로 둔다.-->

	
	<div id="locationAddr">
		<span style="font-size: 14pt; font-weight: bold;">주소 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" class="form-control" readonly="readonly"
			   placeholder="주소를 입력해주세요." name="LocationAddr">
		
		<br><br>
		<span style="font-size: 13pt; font-weight: bold;">상세 주소 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" class="form-control" readonly="readonly" 
				id="LocationDetailedAddr" name="LocationDetailedAddr">
	
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
<div class="container">

	<input type="submit" value="다음" class="btn btn-warning" style="width:300px;">
			
<!-- 취소 버튼 -->
	<input type="button" class="btn btn-default" style="width:300px;" 
			value="취소">
	<!-- onclick="function()" -->
	
</div>

<br><br><br><br>

</form>

</div>
</div>

	<div>
		<c:import url="${cp}/includes/footer_host.jsp"></c:import>
	</div>

</body>
</html>