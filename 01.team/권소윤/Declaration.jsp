<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	// 받아온 정보를 넘겨주는 구문
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Declaration.jsp</title>

<script type="text/javascript">

	function declaration()
	{
		alert("호출 확인");
	}
	
	function resetForm()
	{
		alert("호출 확인");
	}
	
</script>
</head>
<body>

<div>
	<table>
		<tr>
			<th><img src="<%=cp %>/images/logo.png" height="80px" /></th>
			<th>신고</th>	<!-- align : center -->
		</tr>
	</table>
	<hr>
</div>

<div>
	<!-- 신고버튼을 눌렀을 때 초기화면 -->
	<!--
		#jsp
	 	textbox, textarea, button의 id, class 통일하여 재작성 필요
	 	form의 id, action, method 작성 필요
	 	리뷰 및 QNA 상세정보를 받아온 후, Declaration_host.jsp 파일로 넘겨준다.
	 	필요한 정보 : 컨텐츠정보, 신고자정보, 신고일자, 피신고자정보 + 신고 사유
	 	
	 	#js
	 	신고하기 / 다시작성 클릭 시 동작하는 함수 편집 필요
	 	이용자 닉네임은 받아와야 하는 값이기 때문에 reset 되어도 남아있어야 하기 때문에
	 	함수로 처리..해야겠죠? 아닌가...
	 	
	 -->
	<form>	<!-- Declaration_host.jsp로 데이터가 전송 -->
		이용자 닉네임<br>	<!-- 볼드체, 글씨색 : 보라색 -->
		<input type="text"><br><br>
		신고 사유 <br>		<!-- 볼드체, 글씨색 : 보라색 -->
		<textarea rows="3" cols="30"></textarea><br>
		<br>
		<button type="button" onclick="declaration()">신고하기</button>	<!-- 보라색버튼, 흰글씨 -->
		<button type="button" onclick="resetForm()">다시작성</button>	<!-- 노란색버튼, 흰글씨 -->
	</form>
</div>

</body>
</html>