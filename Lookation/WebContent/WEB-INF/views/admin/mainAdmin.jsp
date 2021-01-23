<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>

<c:import url="${cp}/includes/includes_admin.jsp"></c:import>
<title>main.jsp</title>

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

				<div>
					<c:import url="${cp }/includes/header.jsp"></c:import>
				</div>


				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- 상품등록 폼 -->
					<div class="row"></div>
					<!-- End of Main Content -->

					<!-- Footer -->
					<footer class="sticky-footer bg-white">

						<div class="container my-auto">
							<div class="copyright text-center my-auto">
								<h1>관리자 계정으로 접속하였습니다.</h1>
							</div>
						</div>
					</footer>
					<!-- End of Footer -->
				</div>
			</div>
		</div>
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

			<!-- Scroll to Top Button-->
			<a class="scroll-to-top rounded" href="#page-top"> <i
				class="fas fa-angle-up"></i>
			</a>
			
			<!-- 관리자 로그아웃 모달  -->
			<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">로그아웃</h5>
							<button class="close" type="button" data-dismiss="modal"
								aria-label="Close" style="float: right;">
								<span aria-hidden="true" style="float: right;">×</span>
							</button>
						</div>
						<div class="modal-body">로그아웃 시 메인 페이지로 이동합니다.</div>
						<div class="modal-footer">
							<button class="btn btn-secondary" type="button"
								data-dismiss="modal">Cancel</button>
							<a class="btn btn-primary" href="adminlogout.action">Logout</a>
						</div>
					</div>
				</div>
			</div>

			<script>
				$(document).ready(function(){
			         
			         /* CkEditor 설정 */
			         // config.js 외 개별설정. JSON문법 스타일 사용한 설정 구문
			         var ckeditor_config = {
			            resize_enable: false,
			            enterMode: CKEDITOR.ENTER_BR,
			            shiftEnterMode: CKEDITOR.ENTER_P,
			            toolbarCanCollapse: true,
			            removePlugins : "elementspath", 
			            /* 파일 업로드 기능
			               CkEditor를 이용해 업로드 할 때 아래 주소에 업로드   
			            */
			            
			            
			         };
			         CKEDITOR.replace("pro_detail", ckeditor_config);
				</script>
</body>

</html>
