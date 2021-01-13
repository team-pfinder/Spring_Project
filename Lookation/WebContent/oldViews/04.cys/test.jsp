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
<title>5.mypageMileageLoadAndWithdraw.jsp</title>
<!-- Bootstrap cdn 설정 -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css"> -->
<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"> -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

</head>
<body>

	<!-- 테스트 -->
	<!-- Tab을 구성할 영역 설정-->
	<div style="margin:10px;">
	<!-- Tab 영역 태그는 ul이고 클래스는 nav와 nav-tabs를 설정한다. -->
	<ul class="nav nav-tabs">
	<!-- Tab 아이템이다. 태그는 li과 li > a이다. li태그에 active는 현재 선택되어 있는 탭 메뉴이다. -->
	<li class="active"><a href="#home" data-toggle="tab">Home</a></li>
	<!-- a 태그의 href는 아래의 tab-content 영역의 id를 설정하고 data-toggle 속성을 tab으로 설정한다. -->
	<li><a href="#profile" data-toggle="tab">Profile</a></li>
	<li><a href="#messages" data-toggle="tab">Messages</a></li>
	<li><a href="#settings" data-toggle="tab">Settings</a></li>
	</ul>
	<!-- Tab이 선택되면 내용이 보여지는 영역이다. -->
	<!-- 태그는 div이고 class는 tab-content로 설정한다. -->
	<div class="tab-content">
	<!-- 각 탭이 선택되면 보여지는 내용이다. 태그는 div이고 클래스는 tab-pane이다. -->
	<!-- active 클래스는 현재 선택되어 있는 탭 영역이다. -->
	<div class="tab-pane fade in active" id="home">Home 메뉴</div>
	<!-- id는 고유한 이름으로 설정하고 tab의 href와 연결되어야 한다. -->
	<div class="tab-pane fade" id="profile">Profile 메뉴</div>
	<!-- fade 클래스는 선택적인 사항으로 트랜지션(transition)효과가 있다.
	<!-- in 클래스는 fade 클래스를 선언하여 트랜지션효과를 사용할 때 in은 active와 선택되어 있는 탭 영역의 설정이다. -->
	<div class="tab-pane fade" id="messages">Messages 메뉴</div>
	<div class="tab-pane fade" id="settings">Settings 메뉴</div>
	</div>
	</div>


</body>
</html>