<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp1 = request.getContextPath();
%>    
<!-- 마일리지 충전 페이지 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mileage_Charge(user).jsp</title>
</head>
<body>

<!-- 검색창, 메뉴창 -->
<%@ include file="Include.jsp" %>	


<div><!-- css 가로배치 -->
	
	<!-- 서브메뉴 -->
	<table>
		<tr>
			<td>서브메뉴1</td>
		</tr>
		<tr>
			<td>서브메뉴2</td>
		</tr>
		<tr>
			<td>서브메뉴3</td>
		</tr>
		<tr>
			<td>서브메뉴4</td>
		</tr>
	</table>

</div>


<div><!-- css 가로배치 -->	
	<!-- 타이틀 -->
	<div>
		<h3>마일리지 충전</h3>
		<hr>
	</div>
	
	<!-- 설명 -->
	<div>
		<p>
		마일리지는 최소 10000원부터 충전이 가능합니다.
		...
		...
		</p>
	</div>
	
	<!-- 충전 -->
	<div>
		<input type="text" /> 원
		<form action="">	
			
			<!-- 금액 선택 버튼 -->
			<div>
				<button>1000원</button>
				<button>5000원</button>
				<button>10000원</button>
				<button>50000원</button>	
			</div>
			
			<!-- 이용자 계좌 선택 -->
			<div>
				계좌선택
				<select name="" id="">
					<option value="">계좌1</option>
					<option value="">계좌2</option>
					<option value="">계좌3</option>
				</select>
			</div>
			
			<input type="checkbox"/> 이용약관 동의 <a href="약관...">[약관보기]</a>
			
			<div>
				<button type="submit">충전하기</button>
			</div>
		</form>
	</div>
	
</div>


참고이미지<a href="images/Mileage_Charge.png">그림</a>

</body>
</html>