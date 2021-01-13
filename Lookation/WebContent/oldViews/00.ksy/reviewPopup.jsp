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
<title>reviewPopup.jsp</title>
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
					<th>작성된 리뷰</th>
					<td>이전페이지에서 받아온 리뷰 전문 표시 이거 길게 쓰면
					어떻게 될지 궁금해서 한 번 실험해봅니다. 진짜 궁금쓰~
					아 여행가고 싶다...해외여행..가고싶어..근데 갈 수 있을가...
					코로나 진정되기 기다릴려면 몇 년 더 기다려야 할텐데
					사실 해외는 더 노답이라ㅋㅋ...개노답이다 진짜 인생 아!!</td>
				</tr>
			</table>
		</form>
	</div>
</div>

</body>
</html>