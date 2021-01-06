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
<title>예약취소하겠습니다.jsp</title>
<%@ include file="../includes/includes_home.jsp"%>
</head>
<body>

	<!-- 헤더자리 -->

	<div class="container">

		<!-- 버튼 -->
		<!-- 
		<button type="button" class="btn btn-primary btn-xs"
			data-toggle="modal" data-target="#myModal">취소</button> 
		-->
		<!-- 해당 버튼 클릭시 id가 myModal인 팝업 실행시킴. -->
		<a type="submit" class="btn btn-primary" data-toggle="modal" href="#myModal">
		취소
		</a>
		
		<!-- 모달 팝업 -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">예약 취소하기</h4>
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
					</div>
					<div class="modal-body">
						<!-- 이전 페이지에서 해당 예약에 대한 데이터 받아와야 함. -->
						<table class="table">
							<tr>
								<th>예약코드</th>
								<td>NNSJ1532</td>
							</tr>
							<tr>
								<th>예약 공간명</th>
								<td>산</td>
							</tr>
							<tr>
								<th>예약자명</th>
								<td>진영은</td>
							</tr>

							<tr>
								<th>예약일자</th>
								<td>2021-01-01</td>
							</tr>
							<tr>
								<th>예약인원수</th>
								<td>4명</td>
							</tr>
							<tr>
								<th>예약가격</th>
								<td>200,000</td>
							</tr>

						</table>

						<!-- 환불 정책에 맞춘 환불 가격 자동 계산 -->
						<div class="row">
							<div class="col-md-12 form-group form-inline">
								<br> <small>이용시작일자 <span class="text-danger">4</span>일
									전이므로 예약 가격의 <span class="text-danger text-weight-bold">50%</span>인
									<span class="text-primary">100,000</span>원이 환불됩니다.
								</small> <br>
								<br> <span class="text-center">취소하시겠습니까?</span>
							</div>

						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">취소하고
							닫기</button>
						<button type="button" class="btn btn-primary">확인</button>
					</div>
				</div>
			</div>
		</div>


	</div>

	<!-- Button trigger modal -->
	<button type="button" class="btn btn-primary btn-lg"
		data-toggle="modal" data-target="#myModal">Launch demo modal
	</button>

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Modal title</h4>
				</div>
				<div class="modal-body">...</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
		</div>
	</div>






		<%@ include file="../includes/includes_home_end.jsp"%>

		<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>
</body>
</html>