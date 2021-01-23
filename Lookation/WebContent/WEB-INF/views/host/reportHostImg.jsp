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
<title>reportHostImg.jsp</title>
	
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
		<p> 신고가 접수되었습니다. 더 자세하고 확실한 신고를 원하신다면 아래 파일첨부를 통해
		신고할 내용의 캡쳐본을 첨부해주시길 바랍니다. 신고할 내용을 캡쳐하여 첨부해주시면
		더 빠르고 정확한 조취가 취해질 수 있습니다. 파일첨부를 원하시지 않으시다면, 닫기버튼을 눌러주세요.</p>
		<form action="reporthostimg.action" method="post" enctype="multipart/form-data">
		<input type="text" id="book_report_code" name="book_report_code" value="${list.book_report_code }">
			<input type="file" class="form-control" id="book_report_img_url" name="book_report_img_url"><br>
			<button type="submit" class="btn btn-primary report">신고하기</button>
			<button type="reset" class="btn btn-secondary">다시작성</button>
			<button type="button" class="btn btn-default close">닫기</button>
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
			$.ajax({
				
				type : "post"
				, url : "reporthostimg.action"
				, complete : function()
				{
					window.opener.parent.location.reload();
					window.close();
				}
				
			});
		});
		
		$(".close").click(function()
		{
			window.self.close();
		});
	});
	
</script>
</html>