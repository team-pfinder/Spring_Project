<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	/* identify 들고오는게 필요할거같은데 */
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>BlindContent.jsp</title>
	
<!-- css 등 공통적으로 들어가는 요소 include -->
<c:import url="${cp }/includes/includes_home.jsp"></c:import>

<style type="text/css">
	
	.outer
	{
		margin: 10% auto;
		height: 200px;
		width: 100%;
	}
	.inner
	{
		margin: auto;
		height: 200px;
		width: 1000px;
		text-align: center;
	}
	.inner .btn
	{
		width: 500px;
	}

</style>

<script type="text/javascript">

	function back()
	{
		window.history.back();
	}

</script>
</head>
<body>
	<!-- 이용자/호스트가 블라인드 게시글에 접근하였을 때... -->
	<!-- 
		#js
		리스트로 되돌아가는 함수 정의 필요
		button id, class 정의 필요
		
	-->

	<c:choose>
		<c:when test = "${identify eq 'member'}">
			<!-- include header_user.jsp -->
			<div>
				<c:import url="${cp}/includes/header_user.jsp"></c:import>
			</div>
		</c:when>
		<c:when test = "${identify eq 'host'}">
			<!-- include header_host.jsp -->
			<div>
				<c:import url="${cp}/includes/header_host.jsp"></c:import>
			</div>
		</c:when>
	</c:choose>	
	
	

<div class="outer">
	<div class="inner">
		운영정책을 위반하여 블라인드 처리된 공간게시글 입니다.
		<br><br>
		<button type="button" onclick="back()" class="btn btn-primary">되돌아가기</button> <!-- align : center -->
	</div>
</div>


	<c:choose>
		<c:when test="${identify eq 'member'}">
			<c:import url="${cp}/includes/footer_user.jsp"></c:import>
		</c:when>
		<c:when test="${identify eq 'host'}">
			<c:import url="${cp}/includes/footer_host.jsp"></c:import>
		</c:when>
	</c:choose>
	
	<!-- 자바스크립트 include -->
	<c:import url="${cp }/includes/includes_home_end.jsp"></c:import>

</body>
</html>