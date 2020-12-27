<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp1 = request.getContextPath();
%>    
<!-- 마일리지 환전 페이지 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mileage_Exchange.jsp</title>
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
		<h3>마일리지 환전</h3>
		<hr>
	</div>
	
	<!-- 설명 -->
	<div>
		<p>
		마일리지는 최소 1000원부터 환전이 가능합니다.
		...
		...
		</p>
	</div>
	
	<!-- 환전 금액 입력 -->
	<div>
		<p>환전하실 금액을 입력하세요.</p>
		<input type="text" />원
	</div>

	<div>
		<form action="">
			<table>
				<tr>
					<th>현재보유한 마일리지</th>
					<td>54000</td>
				</tr>
				<tr>
					<th>환전할 마일리지</th>
					<td>30000</td>
				</tr>
				<tr>
					<th>환전후 잔여 마일리지</th>
					<td>24000</td>
				</tr>
				<tr>
					<th>계좌 선택</th>
					<td>
						<select name="" id="">
							<option value="">쿸민</option>
							<option value="">우리</option>
							<option value="">롱협</option>
						</select>
					</td>
				</tr>
			</table>
			<button type="submit">환전신청하기</button>
		</form>
	</div>

</div>

참고이미지<a href="<%=cp1 %>/images/Mileage_Exchange(user).png">그림</a>

</body>
</html>