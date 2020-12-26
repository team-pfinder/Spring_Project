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
<title>LocationPackageForm.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>


<!-- 헤더 -->
<div>
	<h1>패키지 정보</h1>	<!-- 가운데 정렬 -->
</div>
<%-- <img src="<!-- 뒤로가는 화살표 버튼 -->"> --%>
<br><br>

<div>
	<h1>패키지 정보를 입력하세요.</h1>
	<hr>
</div>

<br><br>


<!-- 1. 현재 패키지 (추가된 패키지 현황) --> 
<div>
	<h4>현재패키지</h4><br>		<!-- bold,  -->
	<form>
		<table id="InsertedLocationPacakge" border="2">
			<tr>
				<th>-</th>
				<th>패키지명</th>
				<th>패키지 시작시간</th>
				<th>패키지 종료시간</th>
				<th>패키지 가격</th>
			</tr>
			<tr>
				<td>1</td>						<!-- 패키지번호 -->
				<td>올나잇(18시~익일 3시)</td>  <!-- 패키지명 -->
				<td>18시</td>					<!-- 패키지 시작시간 -->
				<td>익일 3시</td>				<!-- 패키지 종료시간 -->
				<td>100,000원</td>				<!-- 패키지 가격 -->
			</tr>
			<tr>
				<td>2</td>						<!-- LocationPackageInsert.jsp 에서 -->
				<td>저녁시간</td>				<!-- 패키지가 추가될때마다 tr,td노드가 추가 -->
				<td>10시</td>
				<td>23시</td>
				<td>100,000원</td>
			</tr>
			
			<!--	 :	 	-->
		</table>
	</form>
</div>

<br><br>

<!-- 2. 패키지 추가하기 -->
<!--    → LocationPackageInsert.jsp로 이동 -->
<input type="button" style="width:120pt;" value="패키지 추가"> <!-- onclick="function()" -->

<!-- 3. 패키지 수정하기 
		→ 현재 패키지 중 하나를 선택후 수정버튼 클릭, 
		   선택하지않으면 alert("패키지를 선택하세요."), onsubmit="false"
		→ LocationPackageUpdate.jsp로 이동 -->
<input type="button" style="width:120pt;" value="패키지 수정"> <!-- onclick="function()" -->

<!-- 4. 패키지 삭제하기
		→ 현재 패키지 중 하나를 선택후 삭제버튼 클릭, onsubmit="false"
		   선택하지않으면 alert("패키지를 선택하세요.") -->
<input type="button" style="width:120pt;" value="패키지 삭제"> 
<!-- onclick="function()" 
	 → 삭제시 선택된 패키지의 현재패키지 리스트에서 
	 	모든 컬럼의 내용을 지운다(view에서만) -->


<br><br><br><br>


<!-- 저장 및 검수 신청 버튼
	(※ 패키지 적용까지 최종 완료, 
		현재 모든 현재패키지의 정보(적용된 날짜 포함)를 참조하여 
		DB에 적용시킨다. 
	(※ 그리고 LocationMgmt.jsp 에 현 공간코드를 참조하는
		모든 정보를 DB에 저장, LocationMgmt.jsp 에 등록된 공간이 보여질 수 있도록 한다.
	(※ 클릭 시 alert("검수신청하시겠습니까?") → <확인> / <취소>) 
 -->
<input type="button" id="LocationPacakgeFormSave" style="width: 150pt;"
	value="저장 및 검수신청"> <!-- onclick="function()" 
					   		   	   submit → LocationPacakgeMgmt.jsp -->


<!-- 취소 버튼 -->
<input type="button" id="LocationPacakgeFormCancel" style="width: 150pt;"
	value="취소"> <!-- onclick="function()" -->



</body>
</html>