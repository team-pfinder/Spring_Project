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
<title>RefundPolicy.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<!-- include 스크립릿 으로 불러와서 사용하기 -->
<div>
	<table>
		<tr>
			<th><img src="<%=cp %>/images/logo.png" height="80px" /></th>
			<th><input type="text" style="width: 700px; height: 50px; font-size: 30px"/></th>
            <th><img src="<%=cp %>/images/search.png" height="40px" /></th>
		</tr>
	</table>
	<hr>
</div>

<!-- 
	#jsp
		정책 내용 추가 필요...
	
 -->


<div>

	<span>정산 및 환불 정책</span>	<!-- span 태그 모두 볼드체, 보라색 -->
	<pre>					<!-- pre 태그 모두 글씨체 통일 필요 -->
근데 pre 태그
써
도
되
는
걸
까 ㅎㅎ
	</pre>
	<br>
	
	<span>이전 정산 및 환불정책</span>
	<pre>
	</pre>
</div>

</body>
</html>