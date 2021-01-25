<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String loc_code = request.getParameter("loc_code");
	String member_code = request.getParameter("member_code");
	String loc_name = request.getParameter("loc_name");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>reportUser.jsp</title>
	
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
				<a class="navbar-brand" href="user/index.html">Look<span>ation.</span></a>
			</div>
		</div>
	</div>


<div class="outer">
	<div class="inner">
		<!-- 이용자 → 호스트 : 공간게시물 신고 폼 -->
		<form action="reportuser.action" method="post">
			<span>신고자정보</span>
			<input type="text" name="member_code" class="form-control" readonly="readonly"
			value="<%=member_code %>"><br>
			<span>신고하려는 공간</span>
			<input type="text" name="loc_code" value="<%=loc_code %>" style="display: none;">
			<input readonly="readonly" value="<%=loc_name %>" class="form-control"><br>
			<select class="form-control" name="loc_report_type_code" id="loc_report_type_code">
				<option value="typeselect">==[신고유형]==</option>
				<option value="LRPP000001">서비스미충족</option>
				<option value="LRPP000002">추가결제 유도</option>
				<option value="LRPP000003">공유규정위반</option>
				<option value="LRPP000004">허위매물</option>
				<option value="LRPP000005">기타</option>
			</select><br>
			<span>신고사유</span><br>
			<textarea rows="3" cols="40" placeholder="자세한 신고사유를 작성하세요."
			class="form-control reason" name="loc_report_reason"></textarea><br>
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

<script type="text/javascript">

	$(document).ready(function()
	{
		$(".report").click(function()
		{
			if($("#loc_report_type_code").val() == 'typeselect')
			{
				alert("신고유형을 선택하지 않았습니다.");
				return false;
			}
			if($(".reason").val() == "")
			{
				alert("신고사유를 작성하지 않았습니다.");
				return false;
			}
			
			
		});
	});	

</script>

</html>