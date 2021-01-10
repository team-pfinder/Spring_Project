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
<title>LocationUpdate.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<!-- LocationMgmt.jsp 에서 공간정보수정 버튼을 눌렀을 경우 -->
<!-- 해당 공간에 대한 모든 정보를 받아온 상태에서 -->
<!-- 기본정보부터 패키지정보까지 처음부터 끝까지 변경해야한다. -->
<!-- (변경사항이 없는 정보의 경우는 바로 다음버튼으로 넘어가는 것) -->

<!-- 즉, LocationUpdate.jsp는 모든 공간의 정보를 받아오고,
	 LocationBusinessInfoUpdate.jsp 페이지로 요청하는 것을 시작으로
	 다음버튼을 누를때 xxxUpdate.jsp 로 이동할 수 있도록 구성한다.
	 (마지막 패키지의 버튼은 수정완료/취소 버튼)  -->

<!-- redirect? → LocationBasicInfoUpdate.jsp -->

메롱


</body>
</html>