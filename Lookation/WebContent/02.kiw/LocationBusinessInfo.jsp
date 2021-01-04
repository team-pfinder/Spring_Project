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
<title>LocationBusinessInfo.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>


<!-- 헤더 -->
<div>
	<h1>사업자 정보</h1>	<!-- 가운데 정렬 -->
</div>
<%-- <img src="<!-- 뒤로가는 화살표 버튼 -->"> --%>
<br><br>

<div>
	<h1>등록할 공간의 사업자 정보를 입력하세요.</h1>
	<hr>	
</div>

<br><br>

<!-- 1. 상호명 -->

<div id="LocationBusinessName">

	상호명 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationBusinessName" name="LocationBusinessName" style="width: 300pt;">
	<span>최소 2자, 최대 20자</span> <!-- 글자 크기 작게 -->
	<!-- 입력 전 default 내용 : 상호명을 입력하세요.  -->

</div>

<br><br><br>

<!-- 2. 대표자명 -->

<div id="LocationRepresentative">

	대표자명 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationRepresentative" name="LocationRepresentative" style="width: 300pt;">
	<span>최소 2자, 최대 10자</span> <!-- 글자 크기 작게 -->
	<!-- 입력 전 default 내용 : 대표자명을 입력하세요.  -->

</div>

<br><br><br>

<!-- 3. 사업자등록번호 -->

<div id="LocationBusinessRegNum">

	사업자등록번호 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationBusinessRegNum1" name="LocationBusinessRegNum1" style="width: 30pt;">
	- <input type="text" id="LocationBusinessRegNum2" name="LocationBusinessRegNum2" style="width: 30pt;">
	- <input type="text" id="LocationBusinessRegNum3" name="LocationBusinessRegNum3" style="width: 30pt;">

</div>

<br><br><br>

<!-- 4. 사업자 등록증 -->
<div id="LocationBusinessReg">

	사업자 등록증 *<br><br> <!-- bold, font-size 1~2pt up -->
	<!-- 이미지추가시 들어갈 공간.. textarea인지 확인 필요-->
	<textarea name="LocationBusinessReg" id="LocationBusinessReg" cols="40" rows="10"></textarea>
	<!-- 입력 전 default 내용 : 사업자등록증을 첨부해 주세요(JPG, JPEG, PNG)  -->
	<br><br>
	<input type="button" id="LocationBusinessRegInsert" name="LocationBusinessRegInsert"
			 style="width: 70pt;" value="파일첨부">
			 <br><br>
	<span>최소/최대 1장, 최대 5MB, 최대해상도 2048*1158
		, 이미지 확장자(JPG,JPEG,PNG)만 가능</span> <!-- 작은 글자 -->
	
	<!-- 7.1 추가 시 보여지는 default 이미지 해상도 xxx*xxx -->
	<!-- ※ 다시 첨부하여 등록할 시 기존 이미지를 대체 -->
</div>

<br><br><br>

<!-- 5. 사업자 유형 -->

<div id="LocationBusinessType">
	<form>
		사업자 유형 *<br><br> <!-- bold, font-size 1~2pt up -->
		<input type="radio" name="LocationBusinessType" value="간이과세자">간이과세자
		<input type="radio" name="LocationBusinessType" value="일반과세자">일반과세자
		<input type="radio" name="LocationBusinessType" value="법인사업자">법인사업자
		<input type="radio" name="LocationBusinessType" value="면세사업자">면세사업자
		<input type="radio" name="LocationBusinessType" value="기타">기타
	</form>
</div>

<br><br><br>

<!-- 6. 주종목 -->

<div id="LocationBusinessMainEvent">

	주종목 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationBusinessMainEvent" name="LocationBusinessMainEvent" style="width: 300pt;">
	<span>최소 5자, 최대 30자</span> <!-- 글자 크기 작게 -->
	<!-- 입력 전 default 내용 : 주종목을 입력하세요.  -->

</div>

<br><br><br>

<!-- 7. 주업태 -->

<div id="LocationBusinessMain">

	주종목 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationBusinessMain" name="LocationBusinessMain" style="width: 300pt;">
	<span>최소 5자, 최대 30자</span> <!-- 글자 크기 작게 -->
	<!-- 입력 전 default 내용 : 주업태를 입력하세요.  -->

</div>

<br><br><br>

<!-- 8. 주소 -->
	
<div id="LocationBusinessAddr">
	주소 *<br><br> <!-- bold, font-size 1~2pt up -->
	
	<input type="text" id="LocationBusinessAddr" name="LocationBusinessAddr" style="width: 300pt;" readonly="readonly">
	<!-- 입력 전 default 내용 : 주소를 입력해주세요.  -->
	
	<input type="button" id="LocationBusinessAddrInsert" name="LocationBusinessAddrInsert"
			 style="width: 70pt;" value="주소등록">
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
	<input type="text" id="LocationBusinessDetailedAddr" name="LocationBusinessDetailedAddr" style="width: 300pt;">
	<!-- 입력 전 default 내용 : 상세 주소  -->

</div>


<br><br><br>

<!-- 다음 버튼 (자세한 설명은 LocationBasicInfo.jsp 참조) -->
<input type="button" id="LocationBusinessInfoSave" style="width: 150pt;"
	value="다음"> <!-- onclick="function()" submit → LocationUsingInfo.jsp -->

<!-- 취소 버튼 -->
<input type="button" id="LocationBusinessInfoCancel" style="width: 150pt;"
	value="취소"> <!-- onclick="function()" -->


</body>
</html>