<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script type="text/javascript">

	$(function()
	{	
		// 이용자 블랙리스트 등록하기
		$("#setBlack").click(function()
		{
			var reason = $("#regReason").val();
		 	
			if(reason.length == 0)
			{
				alert("등록사유를 입력해주세요.");
				$("#regReason").focus();
				return false;
			}
			
			$.ajax({
				type : "post",
				url : "setblackmem.action",
				complete : function(xh)
				{			
					window.opener.parent.location.reload();				
					window.close();
				}
			});
			
		});
	});

</script>
</head>
<body class="font-default">
	<div class="container">
		<div class="row">
			<!-- 팝업 제목 -->
			<div class="w-100 text-center">
				<div class="text">
					<h4 class="title font-weight-bold mt-3">블랙리스트 등록</h4>
				</div>
			</div>
		</div><!-- . End row -->
		
		<form action="setblackmem.action" method="post">
			<div class="row">
				<div class="col-md-12">
					<hr>
					<div class="div-table">
						<div class="div-table-body">
							<div class="div-row">
								<div class="div-col font-weight-bold">회원ID</div>
								<div class="div-col">${member_code }</div>
								<input type="hidden" value=${member_email } name="member_email">
							</div>
							<!-- 시간은 패키지정보에서 시작시간, 종료시각 -->
							<div class="div-row">
								<div class="div-col font-weight-bold vertical-top">
									<label for="regReason">등록사유</label>
								</div>
								<div class="div-col">
									<textarea rows="5" cols="10"
										class="form-control form-control-sm" id="regReason"
										name="member_blacklist_reason"
										placeholder="등록 사유를 입력하세요.(최대 300byte)" maxlength="100"></textarea>
								</div>
							</div>
						</div>
					</div>
					<!-- End .div-table -->
					<hr>
				</div>
				<!-- End .col-md-12 -->
				<hr>
				<div class="button-group div-table px-2">
					<div class="div-table-body">
						<div class="div-row">
							<div class="div-col-half">
								<button type="button" class="btn btn-secondary btn-block">닫기</button>
							</div>

							<div class="div-col-half">
								<button type="submit" class="btn btn-primary btn-block" id="setBlack">블랙리스트 등록</button>
							</div>

						</div>
					</div>
					<!-- End .div-table-body-->
				</div>
				<!-- End .div-table -->
			</div>
			<!-- End .row  -->
		</form>
		
		
		
</div>
</body>
</html>