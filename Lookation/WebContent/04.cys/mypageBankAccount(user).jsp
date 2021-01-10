<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>3.mypageBankAccount.jsp</title>
<script type="text/javascript">

	function showPopup()
	{
        var obj = document.frm;
        url = "bankAccountAddPopup.jsp";
        option = "width=600, height=670, toolbar=no, location=no, status=no, memubar=no, scrollbars=no, resizable=no, left=150, top=150";/* 크롬은 resizable 옵션 안먹음 */
		window.open(url, "계좌 등록 팝업", option);
	}

</script>
</head>
<body>
	<!-- include header_user.jsp -->
	<div>
		<c:import url="${cp}/includes/header_user.jsp"></c:import>
	</div>
	
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
				<%@ include file="../includes/mypage_Sidebar(user).jsp"%>

				<div class="col-lg-10 col-md-10">
					<!-- Page Heading -->
					<p class="mb-4">
						마일리지 충전 및 환전을 위한 계좌를 등록하고 관리하세요.<br>
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
											<tr>
												<th>선택</th>
												<th>번호</th>
												<th>계좌번호</th>
												<th>은행명</th>
												<th>예금주</th>
											</tr>
										</thead>
										<tbody>
											<tr align="center">
												<th colspan="4">등록된 계좌가 존재하지 않습니다.</th>
											</tr>
											<tr align="center">
												<th><input type="checkbox"></th>
												<td>1</td>
												<td>554602-04-076182</td>
												<td>국민은행</td>
												<td>조윤상</td>
											</tr>
											<tr align="center">
												<th><input type="checkbox"></th>
												<td>2</td>
												<td>879412-487-454561</td>
												<td>우리은행</td>
												<td>조윤상</td>
											</tr>
											<tr align="center">
												<th><input type="checkbox"></th>
												<td>3</td>
												<td>484814-58-1512124</td>
												<td>농협</td>
												<td>조윤상</td>
											</tr>
	
										</tbody>
									</table>
									<div>
										<button type="button" class="btn btn-warning" style="width:260px;" onclick="showPopup()">계좌등록</button>
										<button type="button" class="btn btn-warning" style="width:260px;" onclick="deleteAccount()">계좌삭제</button>
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

	<!-- footer.jsp -->
	<div>
		<c:import url="${cp}/includes/footer.jsp"></c:import>
	</div>

	<div>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
	</div>

</body>
</html>