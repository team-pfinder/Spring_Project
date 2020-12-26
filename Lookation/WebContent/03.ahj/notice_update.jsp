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
<title>Insert title here</title>
</head>
	<div>
		<h3>공지사항 수정하기</h3>
	</div>
	<br>
		<!-- <div>
			
				<label style="width: 30%; margin-right: 20px;">카테고리</label> 
				<select style="width: 30%; margin-right: 10px; display: inline-block;">
					<option value="default">전체</option>
					<option>회원</option>
					<option>취소 및 환불</option>
					<option>공간이용 및 후기</option>
					<option>기타</option>
				</select>

			</div> -->

			<div>
				<label >제목</label> 
				<input type="text" >
			</div>

			<div>
				<label>내용입력</label>
				<textarea rows="8"></textarea>
			</div>

			<div>
				<label >file Image</label> 
				<input type="file" />
			</div>

		<button type="button">글 수정</button>
		<button type="button">글 목록</button>




</body>
</html>