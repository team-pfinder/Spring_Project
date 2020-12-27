<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp1 = request.getContextPath();
%>       

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mileage_ExchangeList(host).jsp</title>
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
		<h3>마일리지 충전신청 리스트</h3>
		<hr>
	</div>
	
	<!-- 설명 -->
	<div>
		<p>
		마일리지 충전 신청 내역 입니다...
		...
		..
		
		</p>
	</div>
	
	<!-- 리스트 -->
	<div>
		<table>
			<tr>
				<th>번호</th><th>환전신청일</th><th>환전금액</th><th>입금요청계좌</th><th>은행명</th><th>처리상태</th><th>처리일자</th>
			</tr>
			
			<!-- 
			<tr>
				<th>환전 신청내역이 존재하지 않습니다.</th>
			</tr>
			 -->
			 
			<tr>
				<td>1</td>
				<td>2020-11-10</td>
				<td>20000</td>
				<td>54-454-45815</td>
				<td>국민은행</td>
				<td>완료</td>
				<td>2020-11-11</td>
			</tr>
			<tr>
				<td>2</td>
				<td>2020-12-26</td>
				<td>24000</td>
				<td>54-454-45815</td>
				<td>국민은행</td>
				<td>대기</td>
				<td style="text-align: center"> - </td>
			</tr>
		</table>
	</div>
	
</div>


참고이미지<a href="<%=cp1 %>/images/Mileage_ExchangeList(user).png"><button>그림</button></a>
</body>
</html>