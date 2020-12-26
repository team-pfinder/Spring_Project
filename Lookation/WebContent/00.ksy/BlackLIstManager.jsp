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
<title>BlackListManager.jsp</title>
<script type="text/javascript">

	function blackList()
	{
		alert("블랙리스트 해제 함수 호출 근데 이거 자스로 하는건가 ㅎ");
	}

</script>
</head>
<body>

<!--
	# jsp
	동적 테이블로 변경
	
	# js
	블랙리스트 해제 버튼 함수 기능 추가
	form, table, button 등의 id, class 추가 필요
	블랙리스트 해제일이 지나거나, 블랙리스트 해제 버튼 클릭 시, 리스트에서 보이지 않아야함
	selectbox에서 기간조정하면 자동으로 해제일이 늘거나 줄어야함~
	영구정지는..정확하게 몇개월인지 모르겠지만 아무튼 얘도 기간 졸라 길게 늘어나는것임
	
	※ 제 의견입니다..
	이용자 블리 테이블 | 호스트 블리 테이블 이렇게 옆으로 두고 테이블 놔두면
	넘 복잡할까유???ㅇㅅㅇ..
	아니면 테이블 전부 textarea 처럼 몇 줄 이상되면 스크롤 생기게 하고싶은데..ㅎ
-->



<div>
	<!-- 이용자 블랙리스트 테이블 -->
	<form>
		블랙리스트 : 이용자
		<table>
			<tr>
				<th>ID</th>
				<th>전환일</th>
				<th>해제일</th>
				<th>기간설정</th>
			</tr>
			
			<tr>
				<td>test1@test.com</td>
				<td>2020-12-26</td>
				<td>2021-03-26</td>
				<td>
					<select>
						<option>==[기간설정]==</option>
						<option>3개월</option>
						<option>6개월</option>
						<option>9개월</option>
						<option>12개월</option>
						<option>영구정지</option>
					</select>
				</td>
				<td>
					<button type="button" onclick="blackList()">블랙리스트 해제</button>
				</td>
			</tr>
			
			<tr>
				<td>test2@test.com</td>
				<td>2020-12-26</td>
				<td>2021-03-26</td>
				<td>
					<select>
						<option>==[기간설정]==</option>
						<option>3개월</option>
						<option>6개월</option>
						<option>9개월</option>
						<option>12개월</option>
						<option>영구정지</option>
					</select>
				</td>
				<td>
					<button type="button" onclick="blackList()">블랙리스트 해제</button>
				</td>
			</tr>
			
			<tr>
				<td>test3@test.com</td>
				<td>2020-12-26</td>
				<td>2021-03-26</td>
				<td>
					<select>
						<option>==[기간설정]==</option>
						<option>3개월</option>
						<option>6개월</option>
						<option>9개월</option>
						<option>12개월</option>
						<option>영구정지</option>
					</select>
				</td>
				<td>
					<button type="button" onclick="blackList()">블랙리스트 해제</button>
				</td>
			</tr>
		</table>
	</form>
	
	
	<!-- 호스트 블랙리스트 테이블 -->
	<br><br>
	<form>
		블랙리스트 : 호스트
		<table>
			<tr>
				<th>ID</th>
				<th>전환일</th>
				<th>해제일</th>
				<th>기간설정</th>
			</tr>
			
			<tr>
				<td>test4@test.com</td>
				<td>2020-12-26</td>
				<td>2021-03-26</td>
				<td>
					<select>
						<option>==[기간설정]==</option>
						<option>3개월</option>
						<option>6개월</option>
						<option>9개월</option>
						<option>12개월</option>
						<option>영구정지</option>
					</select>
				</td>
				<td>
					<button type="button" onclick="blackList()">블랙리스트 해제</button>
				</td>
			</tr>
			
			<tr>
				<td>test5@test.com</td>
				<td>2020-12-26</td>
				<td>2021-03-26</td>
				<td>
					<select>
						<option>==[기간설정]==</option>
						<option>3개월</option>
						<option>6개월</option>
						<option>9개월</option>
						<option>12개월</option>
						<option>영구정지</option>
					</select>
				</td>
				<td>
					<button type="button" onclick="blackList()">블랙리스트 해제</button>
				</td>
			</tr>
			
			<tr>
				<td>test6@test.com</td>
				<td>2020-12-26</td>
				<td>2021-03-26</td>
				<td>
					<select>
						<option>==[기간설정]==</option>
						<option>3개월</option>
						<option>6개월</option>
						<option>9개월</option>
						<option>12개월</option>
						<option>영구정지</option>
					</select>
				</td>
				<td>
					<button type="button" onclick="blackList()">블랙리스트 해제</button>
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>