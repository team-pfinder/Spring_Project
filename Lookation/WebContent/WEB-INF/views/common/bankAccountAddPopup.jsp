<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	String identify = request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>3.1.bankAccountAddPopup.jsp</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
	function showPopup()
	{
		var obj = document.frm;
		url = "bankAccountTerms.jsp";
		option = "width=600, height=670, toolbar=no, location=no, status=no, memubar=no, scrollbars=no, resizable=no, left=150, top=150";/* 크롬은 resizable 옵션 안먹음 */
		window.open(url, "계좌 등록 약관", option);
	}
	

</script>

<c:import url="${cp}/includes/includes_home.jsp"></c:import>
</head>
<body>
	<!-- nav -->
	<div class="container pt-5">
		<div class="row justify-content-between">
			<div class="col">
				<a class="navbar-brand">계좌<span>등록</span></a>
			</div>
		</div>
		<hr />
	</div>
	<!-- 	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light"
		id="ftco-navbar">
		<div class="container">
			<form action="#" class="searchform order-lg-last">
				<div class="form-group ">
				</div>
			</form>
		</div>
	</nav>	
 -->

	<div class="container">
		<div class="row">
			<div class="col-1"></div>
			<div class="col-10">
				
				<form action="bankinfoadd.action" method="post" id="bankinfoform">
					<div class="form-group has-success">
						<!-- ------------------------------------------------------------ -->
						<input type="hidden" name="identify" value="<%=identify%>">
						<!-- ------------------------------------------------------------ -->
						<label class="control-label" for="inputSuccess1">은행 선택</label>
						<select class="form-control" id="bank" name="bank"  required >
							<option value="" disabled selected hidden>등록할 계좌의 은행을 선택하세요...</option>
							<option value="경남은행">경남은행</option>
							<option value="광주은행">광주은행</option>
							<option value="국민은행">국민은행</option>
							<option value="기업은행">기업은행</option>
							<option value="농협중앙회">농협중앙회</option>
							<option value="농협회원조합">농협회원조합</option>
							<option value="대구은행">대구은행</option>
							<option value="도이치은행">도이치은행</option>
							<option value="부산은행">부산은행</option>
							<option value="산업은행">산업은행</option>
							<option value="상호저축은행">상호저축은행</option>
							<option value="새마을금고">새마을금고</option>
							<option value="수협중앙회">수협중앙회</option>
							<option value="신한금융투자">신한금융투자</option>
							<option value="신한은행">신한은행</option>
							<option value="신협중앙회">신협중앙회</option>
							<option value="외환은행">외환은행</option>
							<option value="우리은행">우리은행</option>
							<option value="우체국">우체국</option>
							<option value="전북은행">전북은행</option>
							<option value="제주은행">제주은행</option>
							<option value="카카오뱅크">카카오뱅크</option>
							<option value="케이뱅크">케이뱅크</option>
							<option value="하나은행">하나은행</option>
							<option value="한국씨티은행">한국씨티은행</option>
							<option value="HSBC">HSBC은행</option>
							<option value="제일은행">SC제일은행</option>
						</select>
					</div>
					<div class="form-group has-warning">
						<label class="control-label" for="inputWarning1">계좌 번호</label> <input
							type="text" class="form-control" id="bankNum" name="bankNum"
							placeholder=" (-) 포함 번호 입력">
					</div>

					<div class="form-group has-warning">
						<label class="control-label" for="inputWarning1">예금주</label> <input
							type="text" class="form-control" id="bankHol" name="bankHol">
					</div>
					<div>
						<div class="col-6">
							<div class="checkbox">
								<label> <input type="checkbox" id="" value="option1" required>
									계좌등록 <a href="javascript:showPopup()"><b
										style="color: black">약관</b></a> 동의
								</label>
							</div>
						</div>
						<div class="col-sm-12">
							<!-- <input type="button" id="submit" class="btn btn-warning" value="계좌등록" style="width:384px" onclick="popupSubmit();"> -->
							<button type="submit" id="submit" class="btn btn-warning" style="width:384px">계좌등록</button>
							<button type="button" class="btn btn-warning" style="width:384px"
								onclick="javascript:window.close()">취소</button>
						</div>
					</div>
				</form>
			</div>
			<div class="col-1"></div>

		</div>
	</div>


</body>
</html>