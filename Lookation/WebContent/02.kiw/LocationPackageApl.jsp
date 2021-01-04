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
<title>LocationPackageApl.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>


<!-- 헤더 -->
<div>
	<h1>패키지 적용 페이지</h1>	<!-- 가운데 정렬 -->
</div>


<!-- ※ LocationPackage.jsp에서  -->
<!-- 캘린더 디자인 (Javascript code)-->
<!-- https://jerryjerryjerry.tistory.com/26 -->

<br><br>

<!-- 달력 특정 일자에 커서를 갖다댈 때 
→ background-color:yellow -->

<!-- 달력 특정 일자 클릭 시 
→ onclick="function();"
(체크박스?형태로 checked된 상태) -->


<!-- ※ 현재 특정패키지의 적용할 날짜들을 선택한 상태 -->


<!-- 1. 패키지 현황 
	 (○(라디오버튼) (패키지명) 형태 
	  → LocationPackageForm.jsp 에서 현재 패키지 정보를 리스트 형태로 받아옴 -->

<div>
	<form>
		<table id="InsertedLocationPackage" border="2">
			<tr>
				<th>-</th>
				<th>패키지명</th>
				<th>패키지 시작시간</th>
				<th>패키지 종료시간</th>
				<th>패키지 가격</th>
			</tr>
			<tr>
				<td>
					<input type="radio" name="LocationPackageApl" value="<%-- <%=현재 패키지정보%> --%>">
				</td>							
				<td>올나잇(18시~익일 3시)</td>  <!-- 패키지명 -->
				<td>18시</td>					<!-- 패키지 시작시간 -->
				<td>익일 3시</td>				<!-- 패키지 종료시간 -->
				<td>100,000원</td>				<!-- 패키지 가격 -->
			</tr>
			<tr>
				<td>
					<input type="radio" name="LocationPackageApl" value="<%-- <%=현재 패키지정보%> --%>">
				</td>							<!-- 패키지가 추가될때마다 tr,td노드가 추가 -->
				<td>저녁시간</td>				<!--  -->
				<td>10시</td>
				<td>23시</td>
				<td>100,000원</td>
			</tr>
			<!--	 :	 	-->
		</table>
	</form>
</div>


<!-- 저장 버튼 -->
<!-- 주소등록처럼 LocationPacakageInsert.jsp 에서 저장버튼 클릭시 
	 입력한 정보가 추가됨.  -->
<br><br>
<input type="button" id="LocationPackageAplSave" name="LocationPackageAplSave"
	   style="width: 150pt;" value="저장">

<!-- 취소 버튼 -->
<input type="button" id="LocationPacakgeAplCancel" name="LocationPackageAplCancel" 
	   style="width: 150pt;" value="취소"> <!-- onclick="function()" -->


</body>
</html>