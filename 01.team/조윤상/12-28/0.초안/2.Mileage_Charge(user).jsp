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
<title>2.Mileage_Charge(user).jsp</title>
<script type="text/javascript">

	function add10000()
	{
		document.getElementById("chargeAmount").value = parseInt(document.getElementById("chargeAmount").value) + 10000;
	}
	function add50000()
	{
		document.getElementById("chargeAmount").value = parseInt(document.getElementById("chargeAmount").value) + 50000;
	}
	
</script>
</head>
<body>

<!-- 검색창, 메뉴창 -->
<%@ include file="0.Include.jsp" %>	

<div>
	<!-- 타이틀 -->
	<div>
		<h3>마일리지 충전신청</h3>
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
		<input type="text" value=0 id="chargeAmount" name="chargeAmount" /> 원
		<form action="">	
			
			<!-- 금액 선택 버튼 -->
			<div>
				<input type="button" onclick="add10000()" value="10000원"/>
				<input type="button" onclick="add50000()" value="50000원"/>	
			</div>
			<br>
			<!-- 이용자 계좌 선택 -->
			<div>
				<table>
					<tr>
						<th>계좌선택</th>
						<td>
							<select name="" id="">
								<option value="">계좌1</option>
								<option value="">계좌2</option>
								<option value="">계좌3</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							예금주
						</th>
						<td>
							<input type="text" name="accountName"/>
						</td>
					</tr>
				</table>
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