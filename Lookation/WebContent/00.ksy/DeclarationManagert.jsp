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
<title>DeclarationManager.jsp</title>

<script type="text/javascript">

	function process1()
	{
		alert("신고 처리 프로세스");
		
		// 신고 처리 사유를 작성하는 페이지 또는 텍스트박스 필요
	}
	
	function process2()
	{
		alert("신고 반려 프로세스");
		
		// 신고 처리 사유를 작성하는 페이지 또는 텍스트박스 필요
	}
	
	function process3()
	{
		alert("허위 신고 처리 프로세스");
		// 신고 처리 사유를 작성하는 페이지 또는 텍스트박스 필요
	}

</script>

</head>
<body>

<div>
	<table>
		<tr>
			<th><img src="<%=cp %>/images/logo.png" height="80px" /></th>
			<th>신고 처리 리스트</th> <!-- align : center -->
		</tr>
	</table>
	<hr>
</div>

<div>
	<!-- Declaration.jsp에서 넘어온 신고가 모여 처리되는 곳 -->
	<!-- 이곳에서 처리된 신고는 DeclarationList.jsp에 로그로 저장됨 -->
	<!--
		#jsp
			Declaration.jsp에서 받아온 정보를 토대로 데이터 작성 필요
			동적 테이블로 교체
			신고를 처리한 경우 리스트에서 제거되어야 함...(보이지 않아야 함)
		#js
			button이 눌려졌을 때 각각 처리되는 거 함수 작성 필요 / id, class 재작성 필요
	-->
	<form>
		<table>	<!-- 테이블 크기 조정, 테이블 선 정리 -->
			<tr>
				<th>컨텐츠정보</th>
				<th>신고사유</th>
				<th>피신고자정보</th>
				<th>신고자정보</th>
				<th>신고일자</th>
			</tr>
			<tr>
				<td>컨텐츠정보1</td>
				<td>신고사유1</td>
				<td>피신고자정보1</td>
				<td>신고자정보1</td>
				<td>신고일자1</td>
				<td>
					<button type="button" onclick="process1()">신고처리</button>
					<button type="button" onclick="process2()">신고반려</button>
					<button type="button" onclick="process3()">허위신고</button>
				</td>
			</tr>
			<tr>
				<td>컨텐츠정보2</td>
				<td>신고사유2</td>
				<td>피신고자정보2</td>
				<td>신고자정보2</td>
				<td>신고일자2</td>
				<td>
					<button type="button" onclick="process1()">신고처리</button>
					<button type="button" onclick="process2()">신고반려</button>
					<button type="button" onclick="process3()">허위신고</button>
				</td>
			</tr>
			<tr>
				<td>컨텐츠정보3</td>
				<td>신고사유3</td>
				<td>피신고자정보3</td>
				<td>신고자정보3</td>
				<td>신고일자3</td>
				<td>
					<button type="button" onclick="process1()">신고처리</button>
					<button type="button" onclick="process2()">신고반려</button>
					<button type="button" onclick="process3()">허위신고</button>
				</td>
			</tr>
			<tr>
				<td>컨텐츠정보4</td>
				<td>신고사유4</td>
				<td>피신고자정보4</td>
				<td>신고자정보4</td>
				<td>신고일자4</td>
				<td>
					<button type="button" onclick="process1()">신고처리</button>
					<button type="button" onclick="process2()">신고반려</button>
					<button type="button" onclick="process3()">허위신고</button>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>