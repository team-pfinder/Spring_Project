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
<title>PrivacyPolicy.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

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

	<span>개인정보처리방침</span><!-- span 태그 모두 볼드체, 보라색 -->
	<pre><!-- pre 태그 모두 글씨체 통일 필요 -->
	회원탈퇴 시, 개인정보는 즉시 파기된다.
	파기되는 개인정보 : 이메일, 비밀번호, 이름, 연락처, 계좌번호
	어쩌고
	저
	쩌
	고
	구
	시
	렁
	</pre>
	<br>

	<span>이전 처리방침</span>
	<pre>
	
	</pre>
	<br>
</div>

</body>
</html>