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
<title>디엠(호스트).jsp</title>
<style type="text/css">
/* Chat containers */
.container {
	width: 500px;
	padding: 10px;
}

/* Header */
.chatHeader {
	background-color: #333;
	font-size: 18pt;
	color: white;
	font-weight: bold;
	width: 500px;
	height: 60px;
	padding: 20px;
	text-align: center;
	font-weight: bold;
	line-height: 0px;
}

/* 왼쪽 메시지 */
.message-left {
	position: relative;
	margin-left: 20px;
	margin-bottom: 10px;
	padding: 10px;
	background-color: #F5F5F5;
	width: 200px;
	height: auto;
	text-align: left;
	border-radius: 10px;
	resize: both;
	display: inline-block;
	max-width: 400px;
	max-height: 400px;
}

/* 오른쪽 메시지 */
.message-right {
	position: relative;
	margin-bottom: 10px;
	margin-left: calc(100% - 180px);
	padding: 10px;
	background-color: black;
	color: white;
	width: 200px;
	height: auto;
	text-align: left;
	border-radius: 10px;
	resize: both;
	display: inline-block;
	max-width: 400px;
	max-height: 400px;
}

.message-content {
	padding: 0;
	margin: 0;
}

/* le*/
.message-timestamp-right {
	position: absolute;
	font-size: .85em;
	font-weight: 300;
	bottom: 5px;
	right: 5px;
}

.message-timestamp-left {
	position: absolute;
	font-size: .85em;
	font-weight: 300;
	bottom: 5px;
	left: 5px;
}

#chatInputForm {
	width: 380px;
	height: 40px;
	padding: 5px;
	box-shadow: 0px -0.5px 10px 0px #dedede;
}
</style>
</head>
<body>

	<div class="container">
		<div class="chatHeader">
			<div>
				<h5>진영은</h5>
			</div>
		</div>

		<br> <br>


		<div class="chatBody">
			<!-- 채팅 내용 동적 생성 -->
			<!-- 자바스크립트로 채팅 내용 하단에 입력될 때마다 -->
			<!-- 스크롤바 자동으로 움직이는 기능 추가  -->
			<div class="message-right">
				<p class="message-content">안녕하세요. 몇시쯤 도착 예정이신지요...?</p>
				<br>
				<div class="message-timestamp-right">11:00</div>
			</div>

			<div class="message-left">
				<p class="message-content">9999시요</p>
				<br>
				<div class="message-timestamp-left">11:01</div>
			</div>

			<div class="message-right">
				<p class="message-content">네, 비밀번호는 486입니다.</p>
				<br>
				<div class="message-timestamp-right">11:02</div>
			</div>

			<div class="message-left">
				<p class="message-content">네네네</p>
				<br>
				<div class="message-timestamp-left">11:05</div>
			</div>

			<!-- 갑자기 사진 보냄 -->
			<div class="message-left">
				<p class="message-content">
					<img alt="" src="<%=cp%>/images/1.jpg" style="width: 200px;">
				</p>
				<br>
				<div class="message-timestamp-left">11:05</div>
			</div>
			<br> <br>
		</div>
		<!-- .chatBody end -->

		<!-- 채팅 입력창은 가만히 있고 내용만 움직이는 스크롤 -->
		<!-- 처음 로딩시 제일 마지막 메시지로 가야 함 -->
		<form>
			<input type="text" id="chatInputForm" placeholder="여기에 입력하세요.">

			<!-- 사진 첨부하기 -->
			<input type="button" value="첨부">

			<!-- 전송하기 -->
			<input type="button" value="전송">

		</form>

	</div>


</body>
</html>