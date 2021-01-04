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
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<!-- 헤더 -->
<div>
	<h1>상세 정보</h1>	<!-- 가운데 정렬 -->
</div>
<%-- <img src="<!-- 뒤로가는 화살표 버튼 -->"> --%>
<br><br>

<div>
	<h1>공간의 상세 정보를 입력하세요.</h1>
	<hr>	
</div>

<br><br>

<!-- 1. 이미지 -->

<div id="LocationImages">

	이미지 *<br><br> <!-- bold, font-size 1~2pt up -->
	<!-- 이미지추가시 들어갈 공간.. textarea인지 확인 필요-->
	<textarea name="LocationImages" id="LocationImages" cols="40" rows="10"></textarea>
	<!-- 입력 전 default 내용 : 이미지 파일을 추가해 주세요(JPG, JPEG, PNG)  -->
	
	<input type="button" id="LocationImagesInsert" name="LocationImagesInsert"
			 style="width: 70pt;" value="파일첨부">
			 <br><br>
			 <!-- 이미지가 추가될 때 마다 
			 추가한 이미지를 썸네일 형태로 조회할 수 있음.
			 그 썸네일 형태로 조회된 이미지를 클릭시 
			 체크박스형태로 복수선택하여 x버튼으로 삭제가능
			  -->
	<br><br>
	<span>최소 1장 ~ 최대 10장, 최대 3MB, 최대해상도 2048*1158
		, 이미지 확장자(JPG,JPEG,PNG)만 가능</span> <!-- 작은 글자 -->

</div>

<br><br><br>

<!-- 2. 최소 수용인원 -->

<div id="MinAvailablePeople">

	최소 수용인원 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="MinAvailablePeople" name="MinAvailablePeople" style="width: 300pt;">
	<br><br>
	<span>최소 1명 이상, 최대 10명 이하</span> <!-- 작은 글자 -->
	<!-- 입력 전 default 내용 : 최소 수용인원을 입력하세요.  -->
	
</div>

<br><br><br>

<!-- 3. 최대 수용인원 -->

<div id="MaxAvailablePeople">

	최대 수용인원 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="MaxAvailablePeople" name="MaxAvailablePeople" style="width: 300pt;">
	<br><br>
	<span>최소 수용인원 이상, 최대 30명 이하</span> <!-- 작은 글자 -->
	<!-- 입력 전 default 내용 : 최대 수용인원을 입력하세요.  -->
	
</div>

<br><br><br>

<!-- 4. 공간 관련 웹사이트 -->

<div id="LocationUrl">

	공간 관련 웹사이트<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationUrl" name="LocationUrl" style="width: 300pt;">
	<br><br>
	<!-- 입력 전 default 내용 : 공간 관련 웹사이트를 URL 형태로 입력하세요.  -->
	
</div>
<br><br><br>


<br><br><br>

<!-- 다음 버튼 (자세한 설명은 LocationBasicInfo.jsp 참조) -->
<input type="button" id="LocationDetailedInfoSave" style="width: 150pt;"
	value="다음"> <!-- onclick="function()" submit → LocationPacakgeForm.jsp -->

<!-- 취소 버튼 -->
<input type="button" id="LocationDetailedInfoCancel" style="width: 150pt;"
	value="취소"> <!-- onclick="function()" -->




<br><br><br><br><br><br><br><br><br>

</body>
</html>