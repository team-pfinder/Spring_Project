<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main(user).jsp</title>
</head>
<body>
	<!-- 오늘의 공간 -->
	<div>
		<table>
			<caption>오늘의 공간</caption>
			<!-- 공간사진 -->
			<tr>
				<th colspan="2"><img src="<%=cp%>/images/todaysPlace01.png"/></th>
				<th colspan="2"><img src="<%=cp%>/images/todaysPlace02.png"/></th>
				<th colspan="2"><img src="<%=cp%>/images/todaysPlace03.png"/></th>
			</tr>
			<!-- 공간제목 -->
			<!-- 왼쪽정렬 중간크기 -->
			<tr>
				<th colspan="2">Mango_Studio</th>	
				<th colspan="2">성수로운공간</th>
				<th colspan="2">홍대 호리존 스튜디오 비주얼하이브</th>
			</tr>
			<!-- 공간위치 -->
			<!-- 왼쪽정렬 작은크기 -->
			<tr>
				<td colspan="2">[서교동]</td>
				<td colspan="2">[송정동]</td>
				<td colspan="2">[창천동]</td>
			</tr>
			<!-- 공간가격, 정보 -->
			<tr>
				<td>80,000 원/패키지</td><td>[최대 8인] [0]</td>
				<td>100,000 원/패키지</td><td>[최대 30인] [0]</td>
				<td>30,000 원/패키지</td><td>[최대 10인] [0]</td>
			</tr>
		</table>
	</div>
	<hr>
	
	<!-- 이용자 리뷰 -->
	<!-- 
		#jquery
		더보기 탭 => 클릭시 무한 스크롤 추가 
	-->
	<!--
		#참고 소스
		jqueryApp01/jqTest06.html
	 -->
	<div>
		<table>
			<caption>
				이용자 리뷰<br>
				<!-- 큰폰트 -->
				생생한 후기를 만나보세요.
				<!-- 작은폰트, 회색폰트 -->
			</caption>

			<tr>
				<th colspan="2"><img src="<%=cp%>/images/reviewPlace01.png" /></th>
				<th colspan="2"><img src="<%=cp%>/images/reviewPlace02.png" /></th>
				<th colspan="2"><img src="<%=cp%>/images/reviewPlace03.png" /></th>
			</tr>

		</table>
	</div>

</body>
</html>
















