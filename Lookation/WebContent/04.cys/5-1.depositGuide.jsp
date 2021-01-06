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
<!-- include NAV -->
<%@ include file="../01.ksb/head(user).jsp"%>

<title>5-1.dopositGuide</title>
<script type="text/javascript">

	function showPopup()
	{
        var obj = document.frm;
        url = "3.1.bankAccountAddPopup.jsp";
        option = "width=600, height=670, toolbar=no, location=no, status=no, memubar=no, scrollbars=no, resizable=no, left=150, top=150";/* 크롬은 resizable 옵션 안먹음 */
		window.open(url, "계좌 등록 팝업", option);
	}

</script>
</head>
<body>
	<!-- 본문 -->
	<section class="ftco-section ftco-degree-bg">
		<div class="container" style="margin-top: -80px">
			<div class="row">
				
				<!-- END <div class="col-lg-8 ftco-animate"> -->

			</div>
			<!-- END <div class="row"> -->
			
		</div>
		<!-- END container -->
	</section>
	<!-- .section -->

	<!-- loader -->
	<%@ include file="../includes/includes_loader.jsp"%>

	<!-- includes_home_end -->
	<%@ include file="../includes/includes_home_end.jsp"%>

</body>
</html>