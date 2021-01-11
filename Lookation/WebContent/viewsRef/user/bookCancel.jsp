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
<title>Lookation 예약취소</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">
	.back-default
	{
	background-color: #f6f6f6;
	}
	
	.cancel-box
	{
	border-radius: 30px;
	padding-top: 0;
	font-size: 0.8em;
	background-color: white;
	}
	
	.div-table{
	display: table;
	width: 100%;
	}
	
	.div-table-body {
	display: table-row-group;
	}
	

	.div-row {
	display: table-row;
	}

	.div-col {
	display: table-cell;
	padding: 3px 10px;
	max-width: 300px;
	}
	
	.div-col-half {
	display: table-cell;
	padding: 3px 10px;
	width: 50%;
	}
	
	.vertical-top{
	vertical-align: top;
	}
	
	.cancel-reason{
	font-size: 1em;
	}
	
</style>
</head>
<body class="back-default">
<!-- 예약취소시 취소일자, 취소사유 받기
 취소 완료창은? -->

	<div class="container">
		<div class="row">
			<!-- 팝업 제목 -->
			<div class="w-100 text-center">
				<div class="text">
					<h4 class="title font-weight-bold mt-3">예약 취소하기</h4>
				</div>
			</div>
		</div><!-- . End row -->
		
		<div class="row">
			<div class="col-md-12 px-4 pb-3 pt-4 mx-4 cancel-box">
				<span class="font-weight-bold text-danger">예약 취소</span>
				<div class="float-right">
					<!-- 공간 예약코드 -->
					<span class="text-dark">예약번호 : BC000001</span>
				</div>
				<!-- 이전 페이지(reservationList.jsp)에서 해당 예약에 대한 데이터 받아와야 함. -->
				<hr>
				<div class="div-table">
					<div class="div-table-body">
						<div class="div-row">
							<div class="div-col font-weight-bold">예약공간명</div>
							<div class="div-col">공간명은최소2자</div>
						</div>
						<!-- 시간은 패키지정보에서 시작시간, 종료시각 -->
						<div class="div-row">
							<div class="div-col font-weight-bold">예약내용</div>
							<div class="div-col">2021-01-08 13시 ~ 18시, 5시간</div>
						</div>
						<div class="div-row">
							<div class="div-col font-weight-bold">결제금액</div>
							<div class="div-col">200,000</div>
						</div>
						<div class="div-row">
							<div class="div-col font-weight-bold">결제일자</div>
							<div class="div-col">2021-01-02 11:00:27</div>
						</div>
						<div class="div-row">
							<div class="div-col font-weight-bold vertical-top"><label for="cancelReason">취소사유</label></div>
							<div class="div-col">
								<textarea rows="5" cols="10" class="form-control form-control-sm cancel-reason" placeholder="취소 사유를 입력하세요.(최대 300byte)" maxlength="100"></textarea>
							</div>
						</div>
					</div>
				</div><!-- End .div-table -->
				<hr>
				<div class="w-100 text-center p-2">
					<!-- 환불 가능여부 확인 -->
					<!-- 환불 정책에 맞춘 환불 가격 자동 계산 -->
					<div class="text-small">
						<div>이용시작일자 <span class="text-danger">4</span>일 전이므로</div>
						<div>예약 가격의 <span class="text-danger text-weight-bold">50%</span>인 <span
						class="text-primary">100,000</span>원이 마일리지로 환불됩니다.</div>
						<div>취소한 예약은 되돌릴 수 없습니다.</div>
					</div>
					
					<span class="text-center">정말 취소하시겠습니까?</span>
				</div>
		</div><!-- End .row -->
		<hr>
		<div class="button-group div-table mt-3 px-4">
		<div class="div-table-body">
			<div class="div-row">
				<div class="div-col-half"><button type="button" class="btn btn-secondary btn-block">닫기</button></div>
				<!-- 확인 버튼 클릭시 예약취소테이블 insert -->
				<div class="div-col-half"><button type="button" class="btn btn-danger btn-block">예약 취소하기</button></div>
			</div>
		</div><!-- End .div-table-body-->
	</div><!-- End .div-table -->
	</div><!-- End .col-md-12  -->
		
		
		
</div>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
</body>
</html>