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
<title>locationBasicInfo.jsp</title>
<%@ include file="../includes/includes_home.jsp" %>
<%@ include file="../includes/header_host.jsp" %>

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



<form style="width: 800px; margin: 120px;">

	<!-- 1. 공간명 -->
	
	<div id="locationName">
		<!-- <label for="locName" style="font-weight: bold;">공간명 *</label> -->
		<span style="font-size: 14pt; font-weight: bold;">공간명  <span style="color: red">*</span></span>
		<br><br>
		<input type="text" class="form-control"  
			   name="locName" id="locName"
			   placeholder="공간명을 입력하세요. [최소 2자 ~ 최대 20자]" required="required">
		<br>
		<span style="color: gray">* 사용 가능한 특수문자: ( , ) , [ , ] , - , .(마침표), ,(쉼표)</span> <!-- 글자크기 작게 -->
	</div>


<br><br><br>

	
	<!-- 2. 공간 유형 -->
	
	<div id="locationType">
		<span style="font-size: 14pt; font-weight: bold;">공간 유형  <span style="color: red">*</span></span>
		<br><br>
		<select id="locType" class="form-control" required="required">
			<option>[==공간 유형을 선택하세요.]==</option>
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
	
		<span style="font-size: 14pt; font-weight: bold;">공간 한줄 소개  <span style="color: red">*</span></span>
		<br><br>
		<input type="text" class="form-control" required="required" 
			   placeholder="공간을 한 문장으로 소개해주세요. [최소 4자 ~ 최대 20자]" 
			   id="locationOneLineComment" name="locationOneLineComment">
	</div>
	
	
<br><br><br>


	<!-- 4. 공간 소개 -->
	
	<div id="locationComment">
	
		<span style="font-size: 14pt; font-weight: bold;">공간 소개  <span style="color: red">*</span></span>
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
		<br><br>
		
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
	
	<!-- 8. 주소 -->
		
	<div id="locationAddr">
		<span style="font-size: 14pt; font-weight: bold;">주소 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" class="form-control" required="required"
			   placeholder="주소를 입력해주세요." name="LocationAddr">
		
		<input type="button" class="form-control" name="LocationAddrInsert" value="주소 등록">
		<!-- 주소등록 버튼클릭 
			 → 주소등록창이라는 새 페이지 등장 
			 →  텍스트로 주소검색 → 검색버튼 클릭 
			 → 해당 검색어에 따른 데이터를 selectbox나 radio button으로 체크 
			 → 체크된 상태로 등록 버튼 클릭 
			 → 주소등록 페이지가 닫힘 
			 → 체크된 주소 및 우편번호 
				
				: 텍스트가 (우편번호)주소 텍스트박스(readonly)에 자동입력 
				{주소 API 사용} 
		-->
		<br><br>
		<span style="font-size: 13pt; font-weight: bold;">상세 주소 <span style="color: red">*</span></span>
		<br><br>
		<input type="text" class="form-control" required="required" 
				id="LocationDetailedAddr" name="LocationDetailedAddr">
		<!-- 입력 전 default 내용 : 상세 주소  -->
	
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
	<input type="submit" value="다음" class="btn btn-warning" style="width:350px;">
	
<!-- 취소 버튼 -->
	<input type="button" class="btn btn-default" style="width:350px;" 
			id="locationBasicInfoCancel" value="취소">
	<!-- onclick="function()" -->
<!-- 
    <div>
       <button type="button" class="btn-warning" style="width:260px;" onclick="showPopup()">계좌등록</button>
       <button type="button" class="btn-warning" style="width:260px;" onclick="deleteAccount()">계좌삭제</button>
    </div>
 -->

</div>


</form>
</div>
</div>
	

<br><br><br><br>


	<%@ include file="../includes/footer.jsp"%>
	<%@ include file="../includes/includes_home_end.jsp"%>
</body>
</html>