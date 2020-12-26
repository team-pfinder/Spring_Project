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
<title>공지사항</title>

</head>
<body>
	<div style="display: inline-block; float: left; margin-left: 15px;">
		<!-- <select style="width: 180px; height: 26px;">
			<option value="default">전체</option>
			<option>회원</option>
			<option>취소 및 환불</option>
			<option>공간이용 및 후기</option>
			<option>기타</option>
		</select>  -->
		<input type="text" style="width: 250px; height: 26px;">
		<button>검색</button>
	</div>
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;

	<div style="display: inline-block; float: right; margin-right: 15px;">
		<button>선택 삭제</button>
		<button>글 등록</button>
	</div>
	<br>

	<div>
		<h6>공지사항 목록</h6>
	</div>
	<hr>


	<div>
		<table width="100%" cellspacing="0">
			<thead>
				<tr>
					<th><input type="checkbox" /></th>
					<th>글 번호</th>
					<th>제목</th>
					<th>내용</th>
					<th>수정/삭제</th>
				</tr>
			</thead>


			<tbody>
				<tr align="center">
					<td></td>

					<td>1</td>

					<td>아무제목</td>

					<td>아무말 아 하기 싫어</td>

					<td>
						<!-- 수정기능 -->
						<button type="button">수정</button> 
						<!-- 삭제기능 -->
						<button type="button">삭제</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	</div>







</body>
</html>