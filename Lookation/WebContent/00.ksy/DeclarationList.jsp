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
<title>DeclarationList.jsp</title>
</head>
<body>

<!-- 관리자가 신고처리한 내역을 모두 볼 수 있음 쉽게 이야기하면 로그조회 페이지... -->
<!-- 
	#jsp
		동적 테이블로 수정
 -->

<div>
	<form> <!-- 테이블 크기 및 위지 조정 -->
		<table> <!-- 테이블 글자, 테이블 선 조정 -->
			<tr>
				<th>컨텐츠정보</th>
				<th>신고사유</th>
				<th>피신고자정보</th>
				<th>신고자정보</th>
				<th>신고일자</th>
				<th>처리결과</th>
			</tr>
			
			<tr>
				<td>컨텐츠정보2</td>
				<td>신고사유2</td>
				<td>피신고자정보2</td>
				<td>신고자정보2</td>
				<td>신고일자2</td>
				<td>처리결과2</td>
			</tr>
			
			<tr>
				<td>컨텐츠정보3</td>
				<td>신고사유3</td>
				<td>피신고자정보3</td>
				<td>신고자정보3</td>
				<td>신고일자3</td>
				<td>처리결과3</td>
			</tr>
			
			<tr>
				<td>컨텐츠정보4</td>
				<td>신고사유4</td>
				<td>피신고자정보4</td>
				<td>신고자정보4</td>
				<td>신고일자4</td>
				<td>처리결과4</td>
			</tr>
			
			<tr>
				<td>컨텐츠정보5</td>
				<td>신고사유5</td>
				<td>피신고자정보5</td>
				<td>신고자정보5</td>
				<td>신고일자5</td>
				<td>처리결과5</td>
			</tr>
			
		</table>
	</form>
</div>

</body>
</html>