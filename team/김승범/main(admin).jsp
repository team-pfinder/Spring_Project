<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main(admin).jsp</title>
</head>
<body>
	
	<div>
		<p>관리자계정으로 접속하였습니다.</p> <!-- 큰폰트 -->
		<p>~건의 마일리지 신청, ~건의 신고가 접수되었습니다.</p> <!-- 중간폰트, 회색폰트 -->
		
		<table>
			<tr>
				<!-- 두 버튼 크기는 동일하게 구성 -->
				<td>
					<!-- 보라색버튼, 흰색폰트 -->
					<button>
						마일리지 내역<br>
						바로가기
					</button>
				</td>
				<td>
					<!-- 주황색버튼, 흰색폰트 -->
					<button>
						신고 내역<br>
						바로가기
					</button>
				</td>
			</tr>
		</table>
	</div>
	
</body>
</html>