<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String basicUrl = cp + "/Download.jsp";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View.jsp</title>
</head>
<body>
<c:forEach var="down" items="${downList }">
	<a href="<%=basicUrl%>?saveFileName=${down}&originalFileName=${down}">${down }파일 다운로드</a>
</c:forEach>
</body>
</html>