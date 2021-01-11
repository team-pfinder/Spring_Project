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
<title>3.1.bankAccountAddPopup.jsp</title>
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

				<form action="">
					<div class="form-group has-success">
						<label class="control-label" for="inputSuccess1">은행 선택</label> <select
							class="form-control" required>
							<option value="" disabled selected hidden>등록할 계좌의 은행을
								선택하세요...</option>
							<option value="1">경남은행</option>
							<option value="2">광주은행</option>
							<option value="3">국민은행</option>
							<option value="4">기업은행</option>
							<option value="5">농협중앙회</option>
							<option value="6">농협회원조합</option>
							<option value="7">대구은행</option>
							<option value="8">도이치은행</option>
							<option value="9">부산은행</option>
							<option value="10">산업은행</option>
							<option value="11">상호저축은행</option>
							<option value="12">새마을금고</option>
							<option value="13">수협중앙회</option>
							<option value="14">신한금융투자</option>
							<option value="15">신한은행</option>
							<option value="16">신협중앙회</option>
							<option value="17">외환은행</option>
							<option value="18">우리은행</option>
							<option value="19">우체국</option>
							<option value="20">전북은행</option>
							<option value="21">제주은행</option>
							<option value="22">카카오뱅크</option>
							<option value="23">케이뱅크</option>
							<option value="24">하나은행</option>
							<option value="25">한국씨티은행</option>
							<option value="26">HSBC은행</option>
							<option value="27">SC제일은행</option>
						</select>
					</div>
					<div class="form-group has-warning">
						<label class="control-label" for="inputWarning1">계좌 번호</label> <input
							type="text" class="form-control" id=""
							placeholder=" (-) 포함 번호 입력">
					</div>

					<div class="form-group has-warning">
						<label class="control-label" for="inputWarning1">예금주</label> <input
							type="text" class="form-control" id="">
					</div>
					<div>
						<div class="col-6">
							<div class="checkbox">
								<label> <input type="checkbox" id="" value="option1">
									계좌등록 <a href="javascript:showPopup()"><b
										style="color: black">약관</b></a> 동의
								</label>
							</div>
						</div>
						<div class="col-sm-12">
							<button type="submit" class="btn btn-warning" style="width:384px">계좌등록</button>
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