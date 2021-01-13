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
<title>Lookation 예약확인</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">

	.back-default
	{
	background-color: #f6f6f6;
	}
	
	.details-box
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
	max-width: 200px;
	}
	
	
</style>

</head>
<body class="back-default">
<!-- 
이용자는 예약리스트에서 예약공간명, 예약일자, 가격, 예약현황을 조회할 수 있다.
이용자는 예약리스트 상세정보조회 시 (클릭했을 때)  예약자명, 예약공간명,
 예약일자, 가격, 요청사항, 예약현황, 취소사유, 호스트 정보를( 상호명(개인/법인)
 , 대표자명, 사업자 등록번호, 사업자 등록증, 사업자 유형, 주업태, 주종목, 사업장 주소) 
 조회할 수 있다.
-->
	<div class="container">
		<div class="row">
			<!-- 팝업 제목 -->
			<div class="w-100 text-center">
				<div class="text">
					<h4 class="title font-weight-bold mt-3">예약 상세보기</h4>
				</div>
			</div>
		</div><!-- . End row -->
		<div class="row">
			<div class="col-md-12 px-3 pb-3 pt-4 mb-4 my-0 details-box">
				<!-- 예약현황 출력 -->
				<span class="text-primary ml-4">예약완료</span>
				<div class="float-right">
					<!-- 공간 예약코드 -->
					<span class="text-dark">예약번호 : BC000001</span>
				</div>
			<!-- 예약현황 -->
			<hr>
			<div class="col-md-12">
				<!-- 이전 페이지에서 해당 예약에 대한 데이터 받아와야 함. -->
				<div class="div-table">
					<div class="div-table-body">
						<div class="div-row">
							<div class="div-col font-weight-bold">예약공간명</div>
							<div class="div-col">공간명은최소2자</div>
							<div class="div-col font-weight-bold">예약자명</div>
							<div class="div-col">진영은</div>
						</div>
						
						<div class="div-row">
							<!-- 시간은 패키지정보에서 시작, 종료시각 -->
							<div class="div-col font-weight-bold">예약내용</div>
							<div class="div-col">2021-01-08 13시 ~ 18시, 5시간</div>
							<div class="div-col font-weight-bold">예약일자</div>
							<div class="div-col">2021-01-02 11:00:27</div>
						</div>
						
						<div class="div-row">
							<div class="div-col font-weight-bold">연락처</div>
							<div class="div-col">010-1234-1234</div>
							<div class="div-col font-weight-bold">이메일</div>
							<div class="div-col">nbsp@daum.net</div>
						</div>
						
						<div class="div-row">
							<div class="div-col font-weight-bold">예약인원</div>
							<div class="div-col">3명</div>
							<div class="div-col font-weight-bold">결제금액</div>
							<div class="div-col">200,000</div>
						</div>
						
						<div class="div-row">
							<div class="div-col font-weight-bold">요청사항</div>
							<div class="div-col">요청사항은 최대 100자입니다. 글자수 세기로 한글 100자를 세어보았습니다. 요청사항은 최대 100자입니다. 글자수 세기로 한글 100자를 세어보았습니다. 요청사항은 최대 100</div>
							<!-- 취소사유는 취소되었을 경우에만 보여줌 -->
							<div class="div-col font-weight-bold">취소사유</div>
							<div class="div-col">취소사유도 최대 100자입니다. </div>
						</div>
						
						<!-- 사업자정보 -->
						<div class="div-row">
							<div class="div-col font-weight-bold">상호명</div>
							<!-- 상호명 (개인/법인) -->
							<div class="div-col">호호파티룸(개인)</div>
							<div class="div-col font-weight-bold">사업자번호</div>
							<div class="div-col">123-45-67890</div>
						</div>
						<div class="div-row">
							<div class="div-col font-weight-bold">대표자명</div>
							<div class="div-col">호호아저씨</div>
							<div class="div-col font-weight-bold">사업자유형</div>
							<div class="div-col">일반/간이</div>
						</div>
						<div class="div-row">
							<div class="div-col font-weight-bold">주업태</div>
							<div class="div-col">도/소매</div>
							<div class="div-col font-weight-bold">주종목</div>
							<div class="div-col">숙박업</div>
						</div>
						<div class="div-row">
							<div class="div-col font-weight-bold">사업장주소</div>
							<div class="div-col">서울특별시 마포구 월드컵북로56길 19(상암동)</div>
						</div>
					</div>
				</div><!-- End .div-table -->
				</div><!-- End .col-md-12 -->
			</div><!-- End .row  -->
			<hr>
			<div class="div-table">
				<div class="div-table-body">
					<div class="div-row">
						<button type="button" class="btn btn-primary btn-block">확인</button></div>
					</div>
				</div><!-- End .div-table-body-->
			</div><!-- End .div-table -->
			
		</div><!-- End .row -->
	</div>
	
<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</body>
</html>