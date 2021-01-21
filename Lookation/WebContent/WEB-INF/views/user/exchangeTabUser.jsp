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
	<div class="tab-pane" id="second">
		<!-- 두번째 카드 -->
		<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-default">마일리지 환전</h6>
		</div>
		<!-- 카드 바디 -->
		<div class="card-body">
			<!-- 설명 -->
			<div>
				<p>마일리지는 최소 1000원부터 환전이 가능합니다.</p>
			</div>

			<!-- 환전 금액 입력 -->
			<div>
				<p></p>
			</div>

			<div>
				<form action="exchange.action" method="post">
					<table class="table">
						<tr>
							<th>
								<div class="row">
									<b class="col-12">환전하실 금액을 입력하세요.</b>
									<input type="text" class="form-control col-6 ml-3" id="ex_amount1" name="exchange" onkeyup="calc()"  oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
									<button type="button" class="btn btn-primary col-4 ml-3" onclick="setTotal()">전액 입력</button>
								</div>
							</th>
						</tr>
						<tr>
							<th>현재보유한 마일리지<input type="text" class="form-control" id="cr_mileage" value=${balance} disabled="disabled" /></th>
						</tr>
						<tr>
							<th>계좌 선택
								<select class="form-control" name="bankAccount" id="">
									<c:forEach var="list" items="${bankInfoList}">	
										<option value="${list.bankNumber}">${list.bankNumber} (${list.bank}) </option>
									</c:forEach>
								</select>
							</th>
						</tr>
						<tr>
							<th>환전후 잔여 마일리지<input type="text" class="form-control" id="ex_remained" disabled="disabled"/></th>
						</tr>
					</table>
					<button type="submit" class="btn btn-primary form-control">환전신청하기</button><!-- 1000보다 작으면 submit 불가능 -->
				</form>
			</div>
		</div>
		<!-- END 카드 바디 -->	
	</div>
	<!-- END 두번째 카드 -->
</div>
<!-- END 두번째 탭 -->

</body>
</html>