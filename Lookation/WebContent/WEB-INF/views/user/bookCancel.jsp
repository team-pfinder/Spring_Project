<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	
	textarea.form-control{
	font-size: 1em;
	}
	
</style>

</head>
<body class="back-default">
	<div class="container">
		<div class="row">
			<!-- 팝업 제목 -->
			<div class="w-100 text-center">
				<div class="text">
					<h4 class="title font-weight-bold">예약 취소하기</h4>
					
				</div>
			</div>
		</div><!-- . End row -->
		<form action="membercancel.action" method="post" target="redirect:booklist.action" id="cancelForm">
		<div class="row">
			<div class="col-md-12 px-4 pb-3 pt-4 mx-4 cancel-box">
				<div class="float-right">
					<!-- 공간 예약코드 -->
					<input type="hidden" value="${cancel.book_code }" name="book_code"> 
					<span class="text-dark">예약번호 : ${cancel.book_code }</span>
				</div>
				<!-- 이전 페이지(reservationList.jsp)에서 해당 예약에 대한 데이터 받아와야 함. -->
				<hr class="mt-4">
				<div class="div-table">
					
					<div class="div-table-body">
						<div class="div-row">
							<div class="div-col font-weight-bold">예약공간명</div>
							<div class="div-col">${cancel.loc_name }</div>
						</div>
						<!-- 시간은 패키지정보에서 시작시간, 종료시각 -->
						<div class="div-row">
							<div class="div-col font-weight-bold">예약내용</div>
							<div class="div-col">${cancel.apply_date } ${cancel.package_start }시 ~ ${cancel.package_end }시, ${cancel.book_hour }시간</div>
						</div>
						<div class="div-row">
							<div class="div-col font-weight-bold">결제금액</div>
							<div class="div-col">${cancel.package_price }</div>
						</div>
						<div class="div-row">
							<div class="div-col font-weight-bold">결제일자</div>
							<div class="div-col">${cancel.book_date }</div>
						</div>
						<div class="div-row">
							<div class="div-col font-weight-bold vertical-top"><label for="cancelReason">취소사유</label></div>
							<div class="div-col">
								<textarea rows="5" cols="10" class="form-control form-control-sm cancel-reason" placeholder="취소 사유를 입력하세요.(최대 300byte)"
								maxlength="100" required="required" name="member_cancel_reason"></textarea>
							</div>
						</div>
					</div>
					
				</div><!-- End .div-table -->
				<hr>
				<div class="w-100 text-center p-2">
					<!-- 환불 가능여부 확인 -->
					<!-- 환불 정책에 맞춘 환불 가격 자동 계산 -->
					<div class="text-small">
						<c:if test="${cancel.days >= 7}">
							<div>이용시작일자 <span class="text-danger">${cancel.days }</span>일 전이므로</div>
							<div>예약 가격의 전액인 <span
							class="text-primary">${cancel.package_price }</span>원이 마일리지로 환불됩니다.</div>
							
						</c:if>
						
						<!-- 패키지 가격 정수형 변환 -->	
						
						<c:if test="${cancel.days < 7 && cancel.days >= 1}">
							<fmt:parseNumber var="int_package_price" value="${cancel.package_price / 2 }" integerOnly="true" />
							<div>이용시작일자 <span class="text-danger">${cancel.days }</span>일 전이므로</div>
							<div>예약 가격의 <span class="text-danger text-weight-bold">50%</span>인 <span
							class="text-primary">${int_package_price}</span>원이 마일리지로 환불됩니다.</div>
						</c:if>
						
						<div>취소한 예약은 되돌릴 수 없습니다.</div>
					</div>
					
					<span class="text-center">정말 취소하시겠습니까?</span>
				</div>
		
				<hr>
		
		
				<div class="button-group div-table mt-3 px-4">
					<div class="div-table-body">
						<div class="div-row">
							<div class="div-col-half"><button type="button" class="btn btn-secondary btn-block" onclick="window.close()">닫기</button></div>
							<!-- 확인 버튼 클릭시 예약취소테이블 insert -->
							<div class="div-col-half"><button type="submit" class="btn btn-danger btn-block submitBtn" value="${cancel.book_code}" onClick="window.close();">예약 취소하기</button></div>
						</div>
					</div><!-- End .div-table-body-->
				</div><!-- End .div-table -->
			</div><!-- End .col-md-12 -->
		</div><!-- End .row  -->
		</form>
	</div><!-- End .container -->	
		
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
</body>
</html>