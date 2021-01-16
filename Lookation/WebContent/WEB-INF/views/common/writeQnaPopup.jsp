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
<c:import url="${cp}/includes/includes_home.jsp"></c:import>

</head>
<body>
	<!-- container 시작 -->
	<div class="container">
		<div class="row">
			<div class="col-md-12">
			<form action="qnainsert.action" method="post" target="redirect:locationdetail.action">
				<div class="header">
					<h3 class="title my-2">Q&A 작성하기</h3>
				</div><!-- End .header -->
				
				
				<div class="body">
					<div class="form-group">
						<input type="hidden" value="${locCode}" name="loc_code">
						<input type="hidden" value="${memCode }" name="member_code">
						<label for="content">내용</label>
						<textarea class="form-control" id="content" name="qna_content"
							rows="8" maxlength="3000"></textarea>
					</div>
				</div><!-- End .body -->
				<hr>

				<div class="text-center">
					<button type="button" class="btn btn-dark" onClick="self.close();">닫기</button>
					<button type="submit" class="btn btn-primary" id="submitBtn" onClick="window.close();">작성하기</button>
					<!-- 작성하기 버튼 클릭시 insert문 돌아가야 -->
				</div>
			</form>
			</div><!-- End .col-md-12 -->
		</div><!-- End .row -->

	</div>
	<!-- .container-fluid End -->


<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</body>
</html>