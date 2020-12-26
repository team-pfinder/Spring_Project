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
<title>BlindContent.jsp</title>
<script type="text/javascript">

	function back()
	{
		alert("상세페이지로 돌아가기");
	}

</script>
</head>
<body>
	<!-- 이용자/호스트가 블라인드 게시글에 접근하였을 때... -->
	<!-- 
		#js
		리스트로 되돌아가는 함수 정의 필요
		button id, class 정의 필요
		
	-->

<!-- include 스크립릿 으로 불러와서 사용하기 -->
<div>
	<table>
		<tr>
			<th><img src="<%=cp %>/images/logo.png" height="80px" /></th>
		</tr>
	</table>
	<hr>
</div>

<div>
	<span>접근 제한된 게시물입니다.</span><br>	<!-- align : center -->
	<br>
	<button type="button" onclick="back()">되돌아가기</button> <!-- align : center -->
</div>
</body>
</html>