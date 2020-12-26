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
<title>LocationContact.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<!-- 헤더 -->
<div>
	<h1>연락처 정보</h1>	<!-- 가운데 정렬 -->
</div>
<%-- <img src="<!-- 뒤로가는 화살표 버튼 -->"> --%>
<br><br>

<div>
	<h1>연락처 정보를 입력하세요.</h1>
	<hr>	
</div>

<br><br>

<!-- 1. 이메일 -->

<div id="LocationContactMail">
	
	연락처 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" name="LocationContactMail1" style="width: 100pt;">
	@ <input type="text" name="LocationContactMail2" style="width: 100pt;">
	<!-- 대소문자 구별 x -->
</div>

<br><br><br>

<!-- 2. 휴대폰 -->

<div id="LocationContactTel">
	
	휴대폰 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" name="LocationTel1" style="width: 30pt;">
	- <input type="text" name="LocationTel2" style="width: 30pt;">
	- <input type="text" name="LocationTel3" style="width: 30pt;">
</div>

<br><br><br>

<!-- 3. 대표전화 -->

<div id="LocationContactMainTel">
	
	대표전화 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" name="LocationContactMainTel" style="width: 30pt;">
	- <input type="text" name="LocationContactMainTel" style="width: 30pt;">
	- <input type="text" name="LocationContactMainTel" style="width: 30pt;">
</div>

<br><br><br>

<!-- 다음 버튼 : (자세한 설명은 LocationBasicInfo.jsp 참조) -->
<input type="button" id="LocationContactSave" style="width: 150pt;"
	value="다음"> <!-- onclick="function()" submit → LocationBusinessInfo.jsp -->

<!-- 취소 버튼 -->
<input type="button" id="LocationContactCancel" style="width: 150pt;"
	value="취소"> <!-- onclick="function()" -->



<br><br><br>

<br><br><br><br><br><br><br><br><br><br><br><br>
</body>
</html>