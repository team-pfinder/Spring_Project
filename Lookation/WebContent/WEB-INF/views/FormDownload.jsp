<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

	<form action="uploaddownloadtest.action" method="post" enctype="multipart/form-data" >
		
		<input type="file" name="file1" size="30"><br>
		<input type="file" name="file2" size="30"><br>
		<input type="submit" value="업로드">
	
	</form>

</body>
</html>