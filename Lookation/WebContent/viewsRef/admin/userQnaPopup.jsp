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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>qnaPopup.jsp</title>
<c:import url="${cp }/includes/includes_admin.jsp"></c:import>
<style>
	table
	{
		border-collapse: separate;
		border-spacing: 0 50px;
	}
	table tr
	{
		border-bottom: 1px solid gray;
		/* border-collapse: separate;로 하면 tr 스타일이 안먹음..ㅠ */
	}
	table th
	{
		width: 100px;
		color: #4E73DF;
	}
	a
	{
		text-decoration: none;
	}
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
</style>
</head>
<body>
<div class="outer">
	<div class="inner">
		<form>
		<!-- userReview.jsp에서 공간코드, 작성일자, 이용자이름, 리뷰전문을 받아온다. -->
			<table>
				<tr>
					<th>공간코드</th>
					<td>
						<a href="#">이전페이지에서 받아온 공간코드</a>
					</td>
				</tr>
				<tr>
					<th>작성일자</th>
					<td>이전페이지에서 받아온 작성일자</td>
				</tr>
				<tr>
					<th>이용자</th>
					<td>이전페이지에서 받아온 이용자 이름</td>
				</tr>
				<tr>
					<th>작성된 Q&A</th>
					<td>이전페이지에서 받아온 큐엔에이 전문 표시</td>
				</tr>
			</table>
		</form>
	</div>
</div>


</body>
</html>