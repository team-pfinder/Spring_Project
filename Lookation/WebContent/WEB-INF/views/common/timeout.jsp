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
<title>Lookation</title>
<script type="text/javascript">
	var wintimer;
	clearTimeout(wintimer);
	wintimer= setTimeout("closewin()",1000);  //천분의 1초 단위로 초를 지정합니다..
	opener.parent.location.reload();
	function closewin() {
	   window.close();
	}
	window.onload=showWindow;
	
</script>
</head>
<body>

</body>
</html>