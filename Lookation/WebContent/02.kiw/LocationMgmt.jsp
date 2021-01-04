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
<title>LocationMgmt.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<!-- 헤더 -->
<div>
	<h1>내 공간정보 관리</h1>	<!-- 가운데 정렬 -->
</div>
<%-- <img src="<!-- 뒤로가는 화살표 버튼 -->"> --%>

<!-- 공간 관리 -->
<!-- 공간이 추가/삭제될때마다 공간관리 페이지 내에서 td가 추가/삭제되고,
	 td의 개수가 2개보다 크면 다음 tr의 td노드에 추가된다. -->
<div>

	<form action="">
		<table id="LocationMgmtTable">
			<tr>
				<td>
					<table id="LocationTable">
						<tr>
							<td>
								<!-- 1. 해당 공간의 대표이미지 -->
								<!-- 해당 공간에대한 모든 공간 정보는 해당 게시물의 코드를 참조하여 받아온다 -->
								<!-- <img src="/images/img_01.jpg"> -->
							</td>	
						</tr>
						<tr>
							<td>
								<!-- 2. 공간명 : bold, 텍스트박스형태 안보이도록 -->
								<input type="text" name="LocationName" value="공간명" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td>
								<!-- 3. 공간등록날짜 : 회색폰트, 텍스트박스형태 안보이도록 -->
								<input type="text" name="LocationInsertDate" value="공간등록날짜 <%-- <%=%> --%>" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td>
								<!-- 4. 검수상태 : 회색폰트, 텍스트박스형태 안보이도록 -->
								<input type="text" name="LocationInspection" value="검수상태 <%-- <%=%> --%>" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td>
								<!-- 4. 공간정보 수정 -->
								<input type="button" name="LocationUpdateBtn" value="공간정보수정"> <!-- onclick="func()" -->
								<!-- 5. 공간 삭제 -->
								<input type="button" name="LocationDeleteBtn" value="공간삭제"><!-- onclick="func()" -->
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>

<br><br>
<!-- 새 공간 등록하기 -->
<!-- 클릭 시 공간등록페이지(LocationBasicInfo.jsp)로 이동 -->
<input type="button" style="width: 100pt" value="새 공간 등록하기"> 
<!-- onclick="func()" submit → LocationBasicInfo.jsp-->


<!-- 페이지 -->		<!-- ←논의 필요 --> 
<!-- 일정개수(약 6개) 이상이면 등록한 공간등록날짜보다 더 이전것들을 보여준다. -->
<!-- 한 페이지에서만 보여지는 경우는 비활성화 상태 -->


</div>
</body>
</html>