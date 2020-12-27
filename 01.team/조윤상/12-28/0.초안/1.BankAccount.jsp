<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp1 = request.getContextPath();
%>    
<!-- 계좌 등록페이지 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1.BankAccount.jsp</title>
</head>
<body>

<!-- 검색창, 메뉴창 -->
<%@ include file="0.Include.jsp" %>

	
<div>
	<table>
		<tr>
			<td>
				<!-- 타이틀 -->
				<div>
					<h2>계좌등록/삭제</h2>
					<hr>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<!-- 설명 -->
				<div>
					<p>
					계좌 등록 설명....
					충전 및 환불시 필요한 계좌를 등록해라 씨부렁~
					등록한 계좌에서 돈 빠져나간다 싸부렁~
					계좌는 최대 세개 등록할수 있따 쑈부렁~
					...
					...
					</p>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<form action="">
						<h3>계좌등록</h3>
						<hr>
						<table border="1">
							<tr>
								<th>은행선택</th>
								<td>
									<select name="" id="">
										<option value="">국민</option>
										<option value="">우리</option>
										<option value="">농협</option>
										<option value="">신협</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>계좌번호</th>
								<td><input type="text" id="bankaccount" name="bankaccount"/></td>
							</tr>
						</table>
						<button>계좌등록</button><!-- 등록된 계좌가 3개일 때 클릭시, alert("계좌는 3개까지 등록할 수 있습니다");  -->
					</form>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<!-- 입출금계좌 등록현황 -->
				<div>
					<h3>계좌등록현황</h3>
					<hr>
					<form action="">
						<table border=1>
							<tr>
								<th></th>
								<th>번호</th>
								<th>계좌번호</th>
								<th>은행명</th>
							</tr>
							<tr>
								<!-- <td colspan="3">현재 등록된 계좌가 없습니다.</td> -->
								<th><input type="checkbox" name="account1" value="xxx"/></th>
								<td>1</td>
								<td>432985-432-6542</td>
								<td>국민은행</td>
							</tr>
						</table>
						<!-- 삭제 버튼 -->
						<button>계좌삭제</button>
					</form>
				</div>			
			</td>
		</tr>
	</table>
</div>

<a href="<%=cp %>/images/BankAccount.png">그림</a>

</body>
</html>