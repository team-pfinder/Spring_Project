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

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ include file="../includes/includes_admin.jsp"%>
<title>userReview.jsp</title>
<style type="text/css">
	table 
	{
		text-align: center;
	}
	
	

</style>

<script type="text/javascript">
	
	function popupOpen()
	{
		var popUrl = "reviewPopup.jsp";
		var popOption = "width=500, height=700, resizable=no, scrollbars=yes, status=no";
		window.open(popUrl, "", popOption);
	}
	
	function deleteReview()
	{
		alert("게시글 삭제 function 작동 부릉부릉 =3");
	}
        
    </script>

</head>
<body id="page-top">


	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<%@ include file="../includes/Admin_Sidebar.jsp"%>

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<!-- Header -->
					<%@ include file="../includes/header.jsp" %>

				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- Page Heading -->
					<h1 class="h3 mb-2 text-gray-800">리뷰조회</h1>
					<p class="mb-4">온 리뷰를 조회할 수 있읍니다. 검색을 통해 특정 이용자리뷰를 조회할 수 있읍니다.</p>

					<!-- DataTales Example -->
					<div class="card shadow mb-4">
						<div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-primary">Review</h6>
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<thead>
										<tr>
											<th>리뷰번호</th>
											<th>닉네임</th>
											<th>이메일</th>
											<th>공간코드</th>
											<th>리뷰내용</th>
											<th>처리</th>
										</tr>
									</thead>

									<tbody>
										<tr>
											<td><input type="checkbox"> 5</td>
											<td>닉네임5</td>
											<td>test5@test.com</td>
											<td>P05005</td>
											<td>분위기 짱짱입니다. 이용 잘하고 갑니다.</td>
											<td>
												<button type="button" class="btn btn-primary" onclick="popupOpen()">상세보기</button>
												<button type="button" class="btn btn-warning" onclick="deleteReview()">삭제</button>
											</td>
										</tr>
										<tr>
											<td><input type="checkbox"> 4</td>
											<td>닉네임4</td>
											<td>test4@test.com</td>
											<td>P03003</td>
											<td>사진이랑 좀 다르네여. 직원분도 불친절...</td>
											<td>
												<button type="button" class="btn btn-primary" onclick="popupOpen()">상세보기</button>
												<button type="button" class="btn btn-warning" onclick="deleteReview()">삭제</button>
											</td>
										</tr>
										<tr>
											<td><input type="checkbox"> 3</td>
											<td>닉네임3</td>
											<td>test3@test.com</td>
											<td>P00123</td>
											<td>찾기 힘든곳에 있었는데 호스트분이 친절...</td>
											<td>
												<button type="button" class="btn btn-primary" onclick="popupOpen()">상세보기</button>
												<button type="button" class="btn btn-warning" onclick="deleteReview()">삭제</button>
											</td>
										</tr>
										<tr>
											<td><input type="checkbox"> 2</td>
											<td>닉네임2</td>
											<td>test2@test.com</td>
											<td>P03005</td>
											<td>파티하기 되게 좋았어요. 뷰도 좋고...</td>
											<td>
												<button type="button" class="btn btn-primary" onclick="popupOpen()">상세보기</button>
												<button type="button" class="btn btn-warning" onclick="deleteReview()">삭제</button>
											</td>
										</tr>
										<tr>
											<td><input type="checkbox"> 1</td>
											<td>닉네임1</td>
											<td>test1@test.com</td>
											<td>P05004</td>
											<td>분위기 짱짱입니다. 이용 잘하고 갑니다.</td>
											<td>
												<button type="button" class="btn btn-primary" onclick="popupOpen()">상세보기</button>
												<button type="button" class="btn btn-warning" onclick="deleteReview()">삭제</button>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- End of Main Content -->

			<!-- Footer -->
			<footer class="sticky-footer bg-white">
				<div class="container my-auto">
					<div class="copyright text-center my-auto">
						<span>Copyright &copy; Your Website 2020</span>
					</div>
				</div>
			</footer>
			<!-- End of Footer -->

		</div>
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>

	<!-- Logout Modal-->
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready
					to end your current session.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="login.html">Logout</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>