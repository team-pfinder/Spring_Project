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

	<!-- 맨위 로고, 검색창 -->
	<!-- Header -->
	
	<br><br><br><br>
	
	
	<!-- 이전 페이지 : userQuestion.jsp-->
	<!-- 수정 버튼 클릭해 들어온 모습 -->
	<!-- Q&A 내용 수정 -->
	
	<!-- container 시작 -->
	<div class="container">
		<!-- 리뷰내용수정 -->
		<div class="row">
			<div class="col-md-12">
				<!-- 제목 -->
				<div class="header">
					<h3 class="title">Q&A 수정하기</h3>
				</div><!-- End .header -->
				
				<br>

				<form action="">
					<div class="body">
						<div class="form-group">
							<label for="content">내용</label>
							<textarea class="form-control" id="content" name="pro_detail"
								rows="8"></textarea>
						</div>
					</div><!-- End .body -->
				</form>
			</div><!-- End .col-md-12 -->
		</div><!-- End .row -->
		
		<div class="row">
			<div class="col-md-12">
				<hr>

				<div class="text-center">
					<button type="button" class="btn btn-primary">수정하기</button>
					<!-- 수정하기 버튼 클릭시 update문 돌아가야 -->
					<button type="button" class="btn btn-dark">목록으로</button>
					<!-- 목록으로 버튼 클릭시 userReview.jsp로 돌아감 -->
				</div>

			</div><!-- End .col-md-12 -->
		</div><!-- End .row -->

	</div>
	<!-- .container-fluid End -->


<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</body>
</html>