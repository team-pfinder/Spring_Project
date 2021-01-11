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
<title>DeclarationUser.jsp</title>
	
<!-- css 등 공통적으로 들어가는 요소 include -->
<%@ include file="../includes/includes_home.jsp"%>
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
				<a class="navbar-brand" href="user/index.html">Look<span>ation.</span></a>
			</div>
		</div>
	</div>


<div class="outer">
	<div class="inner">
		<!-- 이용자 → 호스트 : 공간게시물 신고 폼 -->
		<form>
			<span>호스트</span>
			<input type="text" id="hostName" class="form-control" readonly="readonly"
			value="이전페이지에서 받아온 호스트이름"><br>
			<span>신고하려는 공간</span> <input id="placeCode" readonly="readonly"
			value="이전페이지에서 받아온 공간제목" class="form-control"><br>
			<select class="form-control">
				<option>==[신고유형]==</option>
				<option>부적절한 언행 사용</option>
				<option>허위매물</option>
				<option>광고</option>
				<option>호스트 서비스 미충족</option>
				<option>강압적 추가 결제 유도</option>
				<option>기타</option>
			</select><br>
			<span>신고사유</span><br>
			<textarea rows="3" cols="40" placeholder="자세한 신고사유를 작성하세요."
			class="form-control"></textarea><br>
			<!-- 파일 여러개 업로드 가능, 근데 파일만 초기화는 아직... -->
			<input type="file" multiple="multiple" class="form-control"><br>
			<p>※ 신고 내용을 허위로 작성할 경우, 해당 신고는 반려처리됩니다.<br>
			허위 신고의 경우 횟수가 잦거나, <br>
			정도가 심한 경우에는 블랙리스트 처리될 수 있으며,<br>
			법적 제재를 받으실 수 있습니다.</p>
			<button type="button" class="btn btn-primary">신고하기</button>
			<button type="reset" class="btn btn-secondary">다시작성</button>
		</form>
	</div>
</div>
	
	<!-- 자바스크립트 include -->
	<%@ include file="../includes/includes_home_end.jsp"%>

</body>
</html>