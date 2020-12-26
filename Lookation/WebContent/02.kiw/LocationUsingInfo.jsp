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
<title>LocationUsingInfo.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<!-- 헤더 -->
<div>
	<h1>이용 안내</h1>	<!-- 가운데 정렬 -->
</div>
<%-- <img src="<!-- 뒤로가는 화살표 버튼 -->"> --%>
<br><br>

<div>
	<h1>이용 안내 정보를 입력하세요.</h1>
	<hr>	
</div>

<br><br>

<!-- 1. 이용시간 -->

<div id="LocationUsingTime">
	
	이용시간 *<br><br> <!-- bold, font-size 1~2pt up -->
	<select id="LocationStartTime" name="LocationStartTime" style="width: 150pt;">
		<option value="[==시간을 선택하세요.==]">[==시간을 선택하세요.==]</option>
		<option value="0시">0시</option>
		<option value="1시">1시</option>
		<option value="2시">2시</option>
		<option value="3시">3시</option>
		<option value="4시">4시</option>
		<option value="5시">5시</option>
		<option value="6시">6시</option>
		<option value="7시">7시</option>
		<option value="8시">8시</option>
		<option value="9시">9시</option>
		<option value="10시">10시</option>
		<option value="11시">11시</option>
		<option value="12시">12시</option>
		<option value="13시">13시</option>
		<option value="14시">14시</option>
		<option value="15시">15시</option>
		<option value="16시">16시</option>
		<option value="17시">17시</option>
		<option value="18시">18시</option>
		<option value="19시">19시</option>
		<option value="20시">20시</option>
		<option value="21시">21시</option>
		<option value="22시">22시</option>
		<option value="23시">23시</option>
		<option value="24시">24시</option>
	</select>
	부터 <select id="LocationEndTime" name="LocationEndTime" style="width: 150pt;">
		<option value="[==시간을 선택하세요.==]">[==시간을 선택하세요.==]</option>
		<option value="0시">0시</option>
		<option value="1시">1시</option>
		<option value="2시">2시</option>
		<option value="3시">3시</option>
		<option value="4시">4시</option>
		<option value="5시">5시</option>
		<option value="6시">6시</option>
		<option value="7시">7시</option>
		<option value="8시">8시</option>
		<option value="9시">9시</option>
		<option value="10시">10시</option>
		<option value="11시">11시</option>
		<option value="12시">12시</option>
		<option value="13시">13시</option>
		<option value="14시">14시</option>
		<option value="15시">15시</option>
		<option value="16시">16시</option>
		<option value="17시">17시</option>
		<option value="18시">18시</option>
		<option value="19시">19시</option>
		<option value="20시">20시</option>
		<option value="21시">21시</option>
		<option value="22시">22시</option>
		<option value="23시">23시</option>
		<option value="24시">24시</option>
	</select>
	까지
	<!-- 종료시간 : 시작시간보다 이후부터 
	(최대 : 시작시간 + 34시까지)  -->
	<br><br>
	<span>※ 실제 이용 가능한 시간으로 설정해야 합니다.</span> <!-- 붉은색 글자 -->
	
</div>

<br><br><br>

<!-- 2. 정기휴무 -->

<div id="LocationRegularDayOff">

	정기휴무 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationRegularDayOff" name="LocationRegularDayOff" style="width: 300pt;">
	<br>
	<span>최소 2자, 최대 20자</span> <!-- 글자 크기 작게 -->
	
</div>

<br><br><br>

<!-- 3. 지정휴무일 -->

<div id="LocationRegularDayOff">

	지정휴무일<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationDayOff" name="LocationDayOff" style="width: 300pt;">
	<br>
	<span>최소 2자, 최대 20자</span> <!-- 글자 크기 작게 -->
	
</div>


<br><br><br>

<!-- 저장 버튼 -->
<input type="button" id="LocationUsingInfoSave" style="width: 150pt;"
	value="저장"> <!-- onclick="function()" -->

<!-- 취소 버튼 -->
<input type="button" id="LocationUsingInfoCancel" style="width: 150pt;"
	value="취소"> <!-- onclick="function()" -->




<br><br><br><br><br><br>



</body>
</html>