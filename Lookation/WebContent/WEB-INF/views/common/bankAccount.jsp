<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>BankAccount.jsp</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	
    $(document).ready(function()
	{
		$("#regBtn").click(function()
		{
	        url = "bankinfoaddpopup.action";
	        option = "width=600, height=670, toolbar=no, location=no, status=no, memubar=no, scrollbars=no, resizable=no, left=150, top=150";/* 크롬은 resizable 옵션 안먹음 */
			window.open(url, "계좌 등록 팝업", option);
		});
	});

</script>
</head>
<body>
<c:choose>
	<c:when test = "${identify eq 'member'}">
	<!-- include header_user.jsp -->
	<div>
		<c:import url="${cp}/includes/header_user.jsp"></c:import>
	</div>
	</c:when>
	<c:when test = "${identify eq 'host'}">
	<!-- include header_host.jsp -->
	<div>
		<c:import url="${cp}/includes/header_host.jsp"></c:import>
	</div>
	</c:when>
</c:choose>	
	<!-- 타이틀 -->
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"> <a href="index.html">Home <i
								class="ion-ios-arrow-forward"></i></a>
						</span> <span class="mr-2"><a href="1.mypageMain.jsp">Mypage <i
								class="ion-ios-arrow-forward"></i></a></span> <span>Bank Account <i
							class="ion-ios-arrow-forward"></i>
						</span>
					</p>
					<h1 class="mb-0 bread">계좌 관리</h1>
				</div>
			</div>
		</div>
	</section>
	<!-- END 타이틀 -->

	<!-- 본문 -->
	<section class="ftco-section ftco-degree-bg">
		<div class="container" style="margin-top: -80px">
			<div class="row">
				<!-- 사이드바 -->
				<!-- .col-md-2 -->
				<!-- include mypage_Sidebar.jsp -->
				<%-- <c:import url="${cp}/includes/mypage_Sidebar(host).jsp"></c:import>
				<c:import url="${cp}/includes/mypage_Sidebar(user).jsp"></c:import> --%>
				<c:choose>
					<c:when test = "${identify eq 'member'}">
					<!-- include mypage_Sidebar(user).jsp -->
					<c:import url="${cp}/includes/mypage_Sidebar(user).jsp"></c:import>
					</c:when>
					<c:when test = "${identify eq 'host'}">
					<!-- include mypage_Sidebar(host).jsp -->
					<c:import url="${cp}/includes/mypage_Sidebar(host).jsp"></c:import>
					</c:when>
				</c:choose>					

				<div class="col-lg-10 col-md-10">
					<!-- Page Heading -->
					<p class="mb-4">
						마일리지 관리를 위한 계좌를 등록하고 관리하세요.<br>
					    입금하시는 계좌 정보의 계좌번호와 이곳에 등록하신 계좌번호가<br>
					    완전히 동일해야(띄어쓰기 및 특수문자 금지, 숫자만 허용) 
					    자동입금확인 처리가 가능합니다.
					</p>
			
					<div class="card shadow mb-4">
						<div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-default">계좌 등록 / 삭제</h6>
						</div>
						<div class="card-body">			
							<div class="table-responsive">
								<form action="">
									<table class="table table-bordered" id="dataTable" width="100%"
										cellspacing="0">	
										<thead>
											<tr align="center">
												<th>선택</th>
												<th>번호</th>
												<th>계좌번호</th>
												<th>은행명</th>
												<th>예금주</th>
											</tr>
										</thead>
										<tbody>
											
											<c:choose>
												<c:when test="${identify eq 'member'}">
													<c:if test="${fn:length(bankAccountInfoList) == 0}">
													<tr>
														<th colspan="5">등록된 계좌가 존재하지 않습니다. <br> 계좌를 등록해주세요.</th>
													</tr>
													</c:if>
		 											<c:forEach var="memberBankAccountInfo" items="${memberBankAccountInfoList }" varStatus="status">
													<tr align="center">
														<th><input type="checkbox"></th>
														<th>${status.count}</th>
														<td>${memberBankAccountInfo.memberBankNumber}</td>
														<td>${memberBankAccountInfo.memberBank}</td>
														<td>${memberBankAccountInfo.memberBankHolder}</td>
													</tr>
													</c:forEach>
												</c:when>
												<c:when test="${identify eq 'host'}">
													<c:if test="${fn:length(hostBankAccountInfoList) == 0}">
													<tr>
														<th colspan="5">등록된 계좌가 존재하지 않습니다. <br> 계좌를 등록해주세요.</th>
													</tr>
													</c:if>
													<c:forEach var="hostBankAccountInfo" items="${hostBankAccountInfoList }" varStatus="status">
													<tr align="center">
														<th><input type="checkbox"></th>
														<th>${status.count}</th>
														<td>${hostBankAccountInfo.hostBankNumber}</td>
														<td>${hostBankAccountInfo.hostBank}</td>
														<td>${hostBankAccountInfo.hostBankHolder}</td>
													</tr>
													</c:forEach>
												</c:when>
											</c:choose>
										</tbody>
									</table>
									<div>
										<button type="button" id="regBtn" class=" btn btn-warning" style="width:430px;" value="계좌등록"
										${fn:length(hostBankAccountInfoList) < 3 ? "" : "disabled=\"disabled\""}								
										">계좌등록</button>
										<button type="button" id="delBtn" class=" btn btn-warning" style="width:430px;" 
										${fn:length(hostBankAccountInfoList) != 0 ? "" : "disabled=\"disabled\""}
										onclick="deleteAccount()">계좌삭제</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				
				</div>
				<!-- END <div class="col-lg-8 ftco-animate"> -->

			</div>
			<!-- END <div class="row"> -->
			
		</div>
		<!-- END container -->
	</section>
	<!-- .section -->

	<div>
		<!-- footer.jsp -->
		<c:choose>
			<c:when test="${identify eq 'member'}">
			<c:import url="${cp}/includes/footer_user.jsp"></c:import>
			</c:when>
			<c:when test="${identify eq 'host'}">
			<c:import url="${cp}/includes/footer_host.jsp"></c:import>
			</c:when>
		</c:choose>

		<!-- includes_home_end -->
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
	</div>
</body>
</html>
