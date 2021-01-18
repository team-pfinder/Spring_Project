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
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>reportHost.jsp</title>
	
<!-- css 등 공통적으로 들어가는 요소 include -->
<c:import url="${cp }/includes/includes_home.jsp"></c:import>
<style type="text/css">

	.outer
	{
		margin: 3% auto;
		height: 100%;
		width: 100%;
	}
	.inner
	{
		margin: auto;
		height: 200px;
		width: 500px;
		align-content: center;
		padding: 0px 5%;
	}
	.inner .btn
	{
		width: 100%;
	}
	
	.inner span
	{
		font-weight: bold;
		color: #fdbe34;
	}

</style>

</head>
<body>

	<div class="container pt-5">
		<div class="row justify-content-between">
			<div class="col">
				<a class="navbar-brand" href="user/index.html">Look<span>ation.</span>  <span style="color: black; font-size: 18px;">호스트센터</span></a>
			</div>
		</div>
	</div>

<div class="outer form-group">
	<div class="inner">
		<!-- 호스트 → 이용자 : 예약 신고 폼 -->
		<form action="" method="post" enctype="multipart/form-data">
			<span>이용자</span>
			<input type="text" id="userName" class="form-control" value="이전페이지에서 받아온 이용자이름"
			readonly="readonly"><br>
			<span>예약번호</span>
			<input type="text" id="reservationCode" value="이전페이지에서 받아온 예약번호"
			class="form-control" readonly="readonly"><br>
			<select class="form-control">
				<option>==[신고유형]==</option>
				<option value="BRPT000001">부적절한 언행 사용</option>
				<option value="BRPT000002">기물파손</option>
				<option value="BRPT000003">광고</option>
				<option value="BTPT000004">기타</option>
			</select><br>
			<span>신고사유</span><br>
			<textarea rows="3" cols="40" placeholder="자세한 신고사유를 작성하세요."
			class="form-control reason"></textarea><br>
			<!-- 파일 여러개 선택가능 -->
			<input type="file" multiple="multiple" class="form-control"><br>
			<p>※ 신고 내용을 허위로 작성할 경우, 해당 신고는 반려처리됩니다.<br>
			허위 신고의 경우 횟수가 잦거나, 정도가 심한 경우에는 블랙리스트 처리될 수  있으며,<br>
			법적 제재를 받으실 수 있습니다.</p>
			<button type="submit" class="btn btn-primary report">신고하기</button>
			<button type="reset" class="btn btn-secondary">다시작성</button>
		</form>
	</div>
</div>
	
	<!-- 자바스크립트 include -->
	<c:import url="${cp }/includes/includes_home_end.jsp"></c:import>

</body>
</html>