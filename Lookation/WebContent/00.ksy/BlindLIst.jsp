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
<title>BlindList.jsp</title>
<script type="text/javascript">

	function blindCancel()
	{
		alert("블라인드 해제 처리");
	}

</script>
</head>
<body>

<!-- 
	#jsp
		분류 selectbox에 따라서 리스트 변화 필요 
		(리뷰/리뷰답글/QnA/QnA답글/공간게시글 별로 조회 가능하게)
		블라인드 해제 버튼을 누르거나 블랙리스트가 해제된 경우에는
		리스트에서 가려져야 함
		동적테이블로 수정
		select, form, talbe의 id, class 정의 필요
	
	#js
		블라인드 해제 버튼을 눌렀을 때 작용하는 함수 작성
 -->


<div>
	<form>
		<select>
			<option>==[분류]==</option>
			<option>리뷰</option>
			<option>리뷰답글</option>
			<option>QnA</option>
			<option>QnA답글</option>
			<option>공간게시글</option>
		</select>
	</form>
</div>

<div>
	<hr>
	<form>
		<table>
			<tr>
				<th>분류</th>
				<th>회원번호</th>
				<th>게시물번호</th>
				<th>게시물</th>
				<th>블라인드처리일자</th>
				<th>블라인드처리사유</th>
			</tr>
			
			<tr>
				<td>리뷰</td>
				<td>1</td>
				<td>14</td>
				<td>창의력이 바닥나서 뭐라 적어야할지...</td>
				<td>2019-12-31</td>
				<td>블랙리스트</td>
				<td>
					<button type="button" onclick="blindCancel()">블라인드 해제</button>
				</td>
			</tr>
			
			<tr>
				<td>리뷰답글</td>
				<td>30</td>
				<td>112</td>
				<td>우웩~</td>
				<td>2020-01-11</td>
				<td>신고누적</td>
				<td>
					<button type="button" onclick="blindCancel()">블라인드 해제</button>
				</td>
			</tr>
			
			<tr>
				<td>QnA</td>
				<td>190</td>
				<td>2922</td>
				<td>하기싫어 ㅠㅠ</td>
				<td>2020-02-19</td>
				<td>신고누적</td>
				<td>
					<button type="button" onclick="blindCancel()">블라인드 해제</button>
				</td>
			</tr>
			
			<tr>
				<td>QnA답글</td>
				<td>20</td>
				<td>20</td>
				<td>살려주세요</td>
				<td>2020-09-12</td>
				<td>탈퇴</td>
				<td>
					<button type="button" onclick="blindCancel()">블라인드 해제</button>
				</td>
			</tr>
			
			<tr>
				<td>공간게시글</td>
				<td>786</td>
				<td>3939</td>
				<td>홍대파티룸</td>
				<td>2020-12-26</td>
				<td>탈퇴</td>
				<td>
					<button type="button" onclick="blindCancel()">블라인드 해제</button>
				</td>
			</tr>
		</table>
	</form>
</div>
<br><br>

</body>
</html>