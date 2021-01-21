<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String member_code = request.getParameter("member_code");
	pageContext.setAttribute("member_code", member_code);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lookation</title>
<c:import url="${cp}/includes/includes_admin.jsp"></c:import>
<style type="text/css">

	.bd-box
	{
	padding : 0.8em;
	font-size: 0.8em;
	}
	
	.div-table{
	display: table;
	width: 100%;
	}
	
	.div-table-body {
	display: table-row-group;
	}
	
	.div-table-header {
	padding: 30px;
    margin-bottom: 0;
    background-color: #f8f9fc;
    border-bottom: 1px solid #e3e6f0;
    color: #4e73df;
    height: 2em;
    font-weight: bold;
    font-size: 1.1em;
	}

	.div-row {
	display: table-row;
	}

	.div-col {
	display: table-cell;
	max-width: 150px;
	padding: 15px 8px 15px 8px;
	}
	
	
	.div-col-half {
	display: table-cell;
	padding: 3px;
	width: 50%;
	}
	
	.vertical-center{
	vertical-align: middle;
	}
	
	.cancel-reason{
	font-size: 1em;
	
	}
	
	
</style>
</head>
<body class="">
<!-- 회원 신고내역 확인하려고 할 경우 팝업 -->

	<div class="">
		<div class="row">
			<div class="col-md-12 px-4 pb-3 pt-4 mx-4 bd-box">
				<div class="float-right">
					<span class="text-dark">회원코드 : ${member_code }</span>
				</div>
				<!-- 이전 페이지(reservationList.jsp)에서 해당 예약에 대한 데이터 받아와야 함. -->
				<div class="div-table mt-4 card shadow">
					<div class="div-table-body">
						<div class="div-row div-table-header vertical-center">
							<div class="div-col">신고코드</div>
							<div class="div-col">신고유형</div>
							<div class="div-col">신고사유</div>
							<div class="div-col">신고일자</div>
						</div>
						<c:choose>
							<c:when test="${not empty reportlist }">
								<c:forEach var="rep" items="${reportlist}" varStatus="status">
									<div class="card-body div-row">
										<div class="div-col">${rep.book_report_code }</div>
										<div class="div-col">${rep.book_report_type}</div>
										<div class="div-col">${rep.book_report_reason}</div>
										<div class="div-col">${fn:substring(rep.book_report_date, 0, 10)}</div>	
										<div class="div-col">${rep.report_proc_type}</div>
									</div>
								</c:forEach>
							</c:when>
							
							<c:otherwise>
								<div class="div-row">
									<div class= "div-col"></div>
									<div class= "div-col"></div>
									<div class= "div-col" style="max-width: 200px;">신고 내역이 존재하지 않습니다.</div>
									<div class= "div-col"></div>
									<div class= "div-col"></div>
								</div>
							</c:otherwise>
						</c:choose>
					</div> 
				</div><!-- End .div-table -->
			</div><!-- End .row -->
		<div class="button-group div-table mt-3 px-4">
			<div class="div-table-body">
				<div class="div-row">
					<div class="div-col">
						<button type="button" class="btn btn-secondary btn-block" 
						onclick="window.close();">닫기</button>
					</div>
				</div>
			</div><!-- End .div-table-body-->
		</div><!-- End .div-table -->
	</div><!-- End .row -->
		
		
		
	</div>
</body>
</html>