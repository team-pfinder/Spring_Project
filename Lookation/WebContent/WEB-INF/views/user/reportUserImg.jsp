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
<title>reportUserImg.jsp</title>
	
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
		<!-- 이용자 → 호스트 : 공간게시물 신고 폼 -->
		<p> ※ 파일명이 한글로 되어있을 경우 제대로 업로드 되지 않을 수 있습니다.
		영문명으로 변경하시고 업로드 해주시길 바랍니다.</p>
		<form id="reportForm" method="post" enctype="multipart/form-data">
		<input type="text" id="loc_report_code" name="loc_report_code" value="${list.loc_report_code }" style="display: none;">
			<input type="file" class="form-control" id="loc_report_img_url" name="loc_report_img_url"><br>
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
		$(".report").click(function(event)
		{
			// submit을 못하게 막는 구문
			event.preventDefault();
			
			var form = $("#reportForm")[0];
			var data = new FormData(form);
			
			$.ajax({
				type : "post"
				, enctype : "multipart/form-data"
				, url : "reportuserimg.action"
				, data : data
				, processData : false
				, contentType : false
				, complete : function()
				{
					window.opener.parent.location.reload();
					window.close();
				}
				
			});
		});
	});
	
</script>
</html>