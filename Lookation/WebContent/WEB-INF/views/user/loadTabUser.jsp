<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- 첫번째 탭 -->
	<div class="tab-pane in active" id="first">
		<!-- 첫번째 카드 -->
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h6 class="m-0 font-weight-bold text-default">마일리지 충전</h6>
			</div>
			<!-- 카드바디 -->
			<div class="card-body">
				<form name="sub1" action="memberload.action" method="post">
					<!-- 설명 -->
					<div>
						<p>마일리지는 최소 10,000원부터 충전이 가능합니다.</p>
					</div>
					<!-- 금액 선택 버튼 -->
					<div class="container">
						<div class="row">
							<div class="col-md-7">
								<table class="table">
									<tr>
										<th >
											<p>버튼으로 금액 입력</p> 
											<input type="button" class="btn-sm btn-warning" style="width:48%" onclick="add10000()" value="10000원" />
											<input type="button" class="btn-sm btn-warning" style="width:48%" onclick="add50000()" value="50000원" />
										</th>
										<th id="addhidden">충전할 마일리지
											<input type="text" class="form-control" value=0 id="chargeAmount" name="chargeAmount" disabled="disabled"/>
										</th>
										
									</tr>
									<tr>

									</tr>
									<!-- 이용자 계좌 선택 -->
									<tr>
										<th>계좌 선택</th>
										<td><select class="form-control" name="bankAccount" id="" style="width:50;">
											<c:forEach var="list" items="${bankInfoList}">	
												<option value="${list.bankNumber}">${list.bankNumber} (${list.bank}) </option>
											</c:forEach>
										</select></td>
									</tr>
									<tr>
										<th colspan="2">
											<label><input type="checkbox" required/> 이용약관 동의 <a 
											href="bankAccountTerms.jsp" onclick="window.open(this.href,'약관','width=600, height=670');return false;">[약관보기]</a></label>
										</th>
									</tr>
								</table>
								<br>

							</div>
							<!-- 오른쪽 요소 -->
							<div class="col-5">
							<div class="col-12 input-group">
								<table class="table">
									<tr>
										<th>충전 전 마일리지<input type="text" class="form-control"
											value="${balance}" id="balance" name="balance" disabled="disabled"/>
										</th>
									</tr>
									<tr>
										<th>충전 후 마일리지<input type="text" class="form-control"
											value="${balance}" id="after" name="after" />
										</th>
									</tr>
								</table>
							</div>
							</div>
							<div class="col-12">
								<br>
								<button type="button"  id="cSubmit" class="btn btn-primary form-control" onclick="cccSubmit()">충전하기
								</button>
							</div>
						</div>
					</div>	
				</form>
			</div>
			<!-- END 카드바디 -->
		</div>
		<!-- END 카드 -->
	</div>
	<!-- END 첫번째 탭 -->

</body>
</html>