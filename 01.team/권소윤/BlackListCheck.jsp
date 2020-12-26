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
<title>BlackListCheck.jsp</title>
<script type="text/javascript">

	function refund()
	{
		alert("자동환불처리하러갑니다 슝슝");
	}
	
	function close()
	{
		alert("창닫기");
	}

</script>
</head>
<body>

<!-- 
	#jsp
	회원 이름(또는 닉네임), 해제되는 날짜를 로그인 시, 회원 정보를 조회하여
	받아와 처리하는 것으로 수정 필요
	button id, class 정의 필요
	
	#js
	버튼을 눌렀을 대 작동하는 함수 작성
	refund() 의 경우, 자동으로 환불하기로 했는데 계좌가 없으면 계좌를 등록하러,
	계좌가 여러개면 계좌 선택하러 가야함...
 -->

<div>
	<table>
		<tr>
			<th><img src="<%=cp %>/images/logo.png" height="80px" /></th>
		</tr>
	</table>
	<hr>
</div>

<div> 
	권소윤님은 경고 3회 누적되어 한시적으로 사이트를 이용하실 수 없습니다.<br>
	2021년 3월 26일 23:59 이후 다시 이용하실 수 있습니다.
	<br><br>
	<button type="button" onclick="refund()">마일리지환불</button>
	<button type="button" onclick="close()">창닫기</button>
</div>

</body>
</html>