<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Lookation_admin</title>
<c:import url="${cp}/includes/includes_admin.jsp"></c:import>
<style type="text/css">

td {
	max-width: 300px;
}

.twobutton {
	width: 100px;
}
</style>
<script type="text/javascript">
	
	$(document).ready(function()
	{	
		// 신고내역 확인
		$(".popCheck").click(function()
		{
			var url = "reportdetails.action?host_code=" + $(this).val();
			var option = "width=630, height=300, resizable=no, scrollbars=yes, status=no";
			window.open(url, "", option);
		});
		
		// 블랙리스트 등록
		$(".popBlack").click(function()
		{
			var url = "setblackhostform.action?host_code=" + $(this).val();
			var option = "width=450, height=300, resizable=no, scrollbars=yes, status=no";
			window.open(url, "", option);
		});
		
		// 블랙리스트 해제 
		$(".release").click(function()
		{
			// 블랙리스트 해제하는 알림창
			var url = "delblackhost.action?host_code=" + $(this).val();
			
			if(confirm("해당 회원을 블랙리스트 목록에서 해제하시겠습니까?"))
			{
				$(location).attr("href", url);
			}
		});
	});

</script>
</head>
<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<div>
			<c:import url="${cp}/includes/Admin_Sidebar.jsp"></c:import>
		</div>
		<!-- End of Sidebar -->

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- header -->
				<div>
					<c:import url="${cp}/includes/header.jsp"></c:import>
				</div>

				<!-- Begin Page Content -->
				<div class="container-fluid">


					<!-- Page Heading -->
					<h1 class="h3 mb-2 text-gray-800">회원관리(호스트)</h1>
					<p class="mb-4">모든 호스트를 관리할 수 있습니다. 신고내역 버튼을 누르면 해당 회원의 신고내역이
						보입니다.</p>

					<!-- DataTales Example -->
					<div class="card shadow mb-4">
						<div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-primary">신고내역 확인 /
								블랙리스트 설정</h6>
						</div>


						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable">
									<thead>
										<tr align="center">
											<th>아이디</th>
											<th>닉네임</th>
											<th>블랙여부</th>
											<th>처리사유</th>
											<th class="twobutton">처리</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="list" items="${blacklist}">
											<tr align="center">
												<td>${list.host_email }</td>
												<td>${list.host_nickname }</td>
												<c:choose>
													<c:when test="${list.yesblack eq 1}">
														<td>Y</td>
														<td>${list.host_blacklist_reason }</td>
														<td>
															<button type="button"
																class="btn btn-primary btn-icon-split popCheck"
																value="${list.host_code }">신고내역</button>
															<button type="button" class="btn btn-info btn-icon-split release"
																value="${list.host_code }">해제</button>
														</td>
													</c:when>

													<c:when test="${list.yesblack eq 0 }">
														<td>N</td>
														<td>${list.host_blacklist_reason }</td>
														<td>
															<button type="button"
																class="btn btn-primary btn-icon-split popCheck"
																value="${list.host_code }">신고내역</button>
															<button type="button"
																class="btn btn-danger btn-icon-split popBlack"
																value="${list.host_code }">등록</button>
														</td>
													</c:when>
												</c:choose>
											</tr>
										</c:forEach>
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
						<span>Copyright &copy; Pfinder 2021</span>
					</div>
				</div>
			</footer>
			<!-- End of Footer -->

		</div>
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

</body>

</html>
