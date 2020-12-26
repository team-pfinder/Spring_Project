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
<title>LocationPackageInsert.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<!-- 헤더 -->
<div>
	<h1>패키지 추가 페이지</h1>	<!-- 가운데 정렬 -->
</div>

<br><br>


<!-- 1. 패키지명 -->

<div id="LocationPackageName">

	패키지명 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationPackageName" name="LocationPackageName" style="width: 300pt;">
	<br><br>
	<span>최소 2자, 최대 20자</span> <!-- 글자 크기 작게 -->
	<!-- 입력 전 default 내용 : 패키지명을 입력하세요.  -->

</div>

<br><br><br>

<!-- 2. 패키지 시작시간, 패키지 종료시간 -->

<div id="LocationPackageTime">
	
	이용시간 *<br><br> <!-- bold, font-size 1~2pt up -->
	<select id="LocationPackageStart" name="LocationPackageStart" style="width: 150pt;">
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
	부터 <select id="LocationPacakgeEnd" name="LocationPacakgeEnd" style="width: 150pt;">
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
		<option value="익일 1시">익일 1시</option>
		<option value="익일 2시">익일 2시</option>
		<option value="익일 3시">익일 3시</option>
		<option value="익일 4시">익일 4시</option>
		<option value="익일 5시">익일 5시</option>
		<option value="익일 6시">익일 6시</option>
		<option value="익일 7시">익일 7시</option>
		<option value="익일 8시">익일 8시</option>
		<option value="익일 9시">익일 9시</option>
		<option value="익일 10시">익일 10시</option>
	</select>
	까지
	<!-- 종료시간 : 시작시간보다 이후부터 
	(최대 : 시작시간 + 34시까지)  -->
	<br><br>
	<span>※ 실제 이용 가능한 시간으로 설정해야 합니다.</span> <!-- 붉은색 글자 -->
	
</div>

<br><br><br>

<!-- 3. 패키지가격 -->

<div id="LocationPackagePrice">

	패키지가격 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationPackagePrice" name="LocationPackage" style="width: 300pt;">
	<br><br>
	<span>패키지가격의 최소가격은 1,000원, 최대가격은 1,000,000원입니다.(부가세포함)</span>
	<br>
	<!-- 글자 크기 작게, 붉은색 -->
	<!-- 입력 전 default 내용 : 패키지가격을 입력하세요.  -->
	
</div>

<br><br><br>

<!-- 저장 버튼 -->
<input type="button" id="LocationPacakgeInsertSave" style="width: 150pt;"
	value="저장"> <!-- onclick="function()" → LocationPacakgeForm.jsp 테이블의 리스트형태로 저장 -->

<!-- 취소 버튼 -->
<input type="button" id="LocationPacakgeInsertCancel" style="width: 150pt;"
	value="취소"> <!-- onclick="function()" -->


<br><br><br><br><br><br>


</body>
</html>