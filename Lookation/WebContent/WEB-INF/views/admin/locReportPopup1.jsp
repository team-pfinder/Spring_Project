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
<title>신고상세</title>
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
		width: 150px;
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
		<!-- reportList.jsp에서 loc_report_code를 받아옴 -->
			<table>
				<tr>
					<th>신고접수코드</th>
					<td>${locDetailList1.loc_report_code }</td>
				</tr>
				<tr>
					<th>공간코드</th>
					<td>
						<a href="#">${locDetailList1.loc_code }</a>
					</td>
				</tr>
				<tr>
					<th>신고유형</th>
					<td>${locDetailList1.loc_report_type }</td>
				</tr>
				<tr>
					<th>피신고자정보</th>
					<td>${locDetailList1.host_email }</td>
				</tr>
				<tr>
					<th>신고자정보</th>
					<td>${locDetailList1.member_email }</td>
				</tr>
				<tr>
					<th>신고일자</th>
					<td>${locDetailList1.loc_report_date }</td>
				</tr>
				<tr>
					<th>신고사유</th>
					<td>${locDetailList1.loc_report_reason }</td>
				</tr>
				<tr>
					<th>이미지</th>
					<td><img src="<%=cp %>/images/${locDetailList1.loc_report_img_url }" width="350"></img></td>
				</tr>
				<tr>
					<th>신고처리결과</th>
					<td>${locDetailList1.report_proc_type }</td>
				</tr>
				<tr>
					<th>신고처리일자</th>
					<td>${locDetailList1.loc_report_proc_date }</td>
				</tr>
				<tr>
					<th>블랙리스트<br>처리여부</th>
					<c:choose>
						<c:when test="${locDetailList1.black_count ==0 }">
							<td>-</td>
						</c:when>
						<c:otherwise>
							<td>
								블랙리스트 처리
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</table>
		</form>
	</div>
</div>


</body>
</html>