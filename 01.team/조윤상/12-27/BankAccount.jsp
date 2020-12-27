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
<title>BankAccount.jsp</title>
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
	<table>
		<tr>
			<td>
				<!-- 타이틀 -->
				<div>
					<h3>계좌 관리</h3>
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
				<!-- 계좌 리스트 -->
				<div>
					<form action="">
						<table border=1>
							<tr>
								<th>번호</th>
								<th>계좌번호</th>
								<th>은행명</th>
							</tr>
							<tr>
								<td colspan="3">현재 등록된 계좌가 없습니다.</td>
							</tr>
						</table>
					</form>
				</div>			
			</td>
		</tr>
		<tr>
			<td>
				<!-- 등록, 수정, 삭제 버튼 -->
				<div>
					<button>계좌등록</button>
					<button>계좌수정</button>
					<button>계좌삭제</button>	
				</div>
			</td>
		</tr>
	</table>

</div>

<a href="<%=cp %>/images/BankAccount.png">그림</a>

</body>
</html>