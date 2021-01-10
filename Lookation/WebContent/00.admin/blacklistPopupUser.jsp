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
<title>Lookation</title>
<c:import url="${cp}/includes/includes_admin.jsp"></c:import>
<style type="text/css">
	.font-default
	{
	font-size: 0.8em;
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
	}
	
	.div-col-half {
	display: table-cell;
	padding: 3px 10px;
	width: 50%;
	}
	
	.vertical-top{
	vertical-align: top;
	}
	
	#regReason{
	font-size: 1em;
	}
	
</style>
</head>
<body class="font-default">
<!-- 블랙리스트 설정하려고 할 경우 팝업 -->

	<div class="container">
		<div class="row">
			<!-- 팝업 제목 -->
			<div class="w-100 text-center">
				<div class="text">
					<h4 class="title font-weight-bold mt-3">블랙리스트 등록</h4>
				</div>
			</div>
		</div><!-- . End row -->
		
		<div class="row">
			<div class="col-md-12">
				<hr>
				<div class="div-table">
					<div class="div-table-body">
						<div class="div-row">
							<div class="div-col font-weight-bold">회원ID</div>
							<!-- 이용자의 경우 U00000x -->
							<!-- 회원 ID 받아옴 -->
							<div class="div-col">H000001</div>
						</div>
						<!-- 시간은 패키지정보에서 시작시간, 종료시각 -->
						<div class="div-row">
							<div class="div-col font-weight-bold vertical-top"><label for="regReason">등록사유</label></div>
							<div class="div-col">
								<textarea rows="5" cols="10" class="form-control form-control-sm" id="regReason" placeholder="등록 사유를 입력하세요.(최대 300byte)" maxlength="100"></textarea>
							</div>
						</div>
					</div> 
				</div><!-- End .div-table -->
				<hr>
		</div><!-- End .row -->
		<hr>
		<div class="button-group div-table px-2">
		<div class="div-table-body">
			<div class="div-row">
				<div class="div-col-half"><button type="button" class="btn btn-secondary btn-block">닫기</button></div>
				<div class="div-col-half"><button type="button" class="btn btn-primary btn-block">블랙리스트 등록</button></div>
	
			</div>
		</div><!-- End .div-table-body-->
	</div><!-- End .div-table -->
	</div><!-- End .col-md-12  -->
		
		
		
</div>
</body>
</html>