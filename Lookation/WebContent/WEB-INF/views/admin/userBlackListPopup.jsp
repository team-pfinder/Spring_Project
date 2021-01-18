<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String member_email = request.getParameter("member_email");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>hostBlackListPopup.jsp</title>

<c:import url="${cp }/includes/includes_admin.jsp"></c:import>
<script type="text/javascript">

	

	$(document).ready(function()
	{
		$("#btn").click(function()
		{
			if(confirm("정말로 이 계정을 블랙리스트 처리하시겠습니까?"))
			{
				$(location).attr("href", "userblack.action?member_email=<%=member_email%>" + "&member_blacklist_reason=" + $("#member_blacklist_reason").val());
			}
			
		});
	});

</script>


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
		color: #4E73DF;
	}

</style>
</head>
<body>
<%
	String context = request.getContextPath();
	System.out.println(context);
%>

<div class="outer">
	<div class="inner">
		<span>블랙리스트에 추가하려는 이용자 또는 호스트</span><br>
		<input type="text" class="form-control" id="member_email"
		value="<%=member_email %>" readonly="readonly"> <br>
		<span>블랙리스트 사유</span><br>
		<input type="text" class="form-control" id="member_blacklist_reason"
		placeholder="블랙리스트 사유 입력"><br>
		<button type="button" class="btn btn-primary" id="btn">블랙리스트 등록</button>
	</div>
</div>

</body>
</html>