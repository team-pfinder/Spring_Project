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
<title>LocationPackageUpdate.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>


<!-- LocationPackageInsert.jsp 양식 그대로 사용 -->
<!-- 선택한 패키지의 저장된 컬럼정보를 모두 불러온다(javascript) -->



<br><br><br>

<!-- 수정 버튼 -->
<input type="button" id="LocationPacakgeUpdateSave" style="width: 150pt;"
	value="수정"> <!-- onclick="function()"
					   → LocationPacakgeForm.jsp 에서 
					   선택했던 패키지의 수정내용을 적용시킴 -->

<!-- 취소 버튼 -->
<input type="button" id="LocationPacakgeUpdateCancel" style="width: 150pt;"
	value="취소"> <!-- onclick="function()" -->


<br><br><br><br><br><br>

</body>
</html>