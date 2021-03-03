<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>공지사항 글쓰기</title>



<!-- Custom fonts for this template -->
<link href="sb/vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="sb/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="sb/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">
	
	<script src="/ckeditor/ckeditor.js"></script>

</head>

<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<%@include file="/WEB-INF/views/include/sidebar.jsp"%>

		<!-- End of Sidebar -->

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<%@include file="/WEB-INF/views/include/header.jsp"%>


				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- 상품등록 폼 -->
					<div class="row">
						<!-- left column -->
						<div class="col-md-12">
							<!-- general form elements -->
							<div class="box box-primary">
								<div class="box-header">
									<h3 class="box-title">공지사항 등록하기</h3>
								</div>
								<br>

								<!-- /.box-header -->
								<form id='insertForm' role="form" action="/admin/product/insert" method="post" enctype="multipart/form-data">
									<div class="box-body">
										<div class="form-group">
											<select class="form-control" id="mainCategory" name="cate_prtcode"
												style="width: 30%; margin-right: 10px; display: inline-block;">
												<option value="default">전체</option>
												<option>기능</option>
												<option>업데이트</option>
												<option>긴급 공지</option>
												<option>사과문</option>
											</select>
											

										</div>

										<div class="form-group">
											<label for="exampleInputEmail1">제목</label> <input
												type="text" id="pro_name" name="pro_name"
												class="form-control">
										</div>


										<div class="form-group">
											<label for="exampleInputPassword1">글쓰기</label>
											<textarea class="form-control" id="pro_detail"
												name="pro_detail" rows="8"></textarea>
										</div>

										<div class="form-group">
											<label for="exampleInputEmail1">Upload Image</label> <input
												type="file" id="file1" name="file1" class="form-control" />
										</div>
									</div>
							</div>
							<!-- /.container-fluid -->
							
							<div class="box-footer">
                           <div>
                              <hr>
                           </div>

                           <ul class="mailbox-attachments clearfix uploadedList">
                           </ul>

                           <button id="btn_submit" type="button" class="btn btn-primary">글 등록</button>
                        </div>
                     </form>
                  </div>

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
								<h5 class="modal-title" id="exampleModalLabel">Ready to
									Leave?</h5>
								<button class="close" type="button" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
							</div>
							<div class="modal-body">Select "Logout" below if you are
								ready to end your current session.</div>
							<div class="modal-footer">
								<button class="btn btn-secondary" type="button"
									data-dismiss="modal">Cancel</button>
								<a class="btn btn-primary" href="login.html">Logout</a>
							</div>
						</div>
					</div>
				</div>

				<!-- Bootstrap core JavaScript-->
				<script src="sb/vendor/jquery/jquery.min.js"></script>
				<script src="sb/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

				<!-- Core plugin JavaScript-->
				<script src="sb/vendor/jquery-easing/jquery.easing.min.js"></script>

				<!-- Custom scripts for all pages-->
				<script src="sb/js/sb-admin-2.min.js"></script>

				<!-- Page level plugins -->
				<script src="sb/vendor/datatables/jquery.dataTables.min.js"></script>
				<script src="sb/vendor/datatables/dataTables.bootstrap4.min.js"></script>

				<!-- Page level custom scripts -->
				<script src="sb/js/demo/datatables-demo.js"></script>
				
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
