<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>

<head>

<%@ include file="../includes/includes_admin.jsp" %>
<title>도움말 수정하기</title>

</head>

<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<%@ include file="../includes/Admin_Sidebar.jsp" %>

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<%@include file="../includes/header.jsp"%>


				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- 상품등록 폼 -->
					<div class="row">
						<!-- left column -->
						<div class="col-md-12">
							<!-- general form elements -->
							<div class="box box-primary">
								<div class="box-header">
									<h3 class="box-title">도움말 수정하기</h3>
								</div>
								<br>

								<!-- /.box-header -->
								<form id='insertForm' role="form" action="/admin/product/insert" method="post" enctype="multipart/form-data">
									<div class="box-body">
										<label for="exampleInputEmail1">카테고리</label>
										<div class="form-group">
											<select class="form-control" id="mainCategory" name="cate_prtcode"
												style="width: 30%; margin-right: 10px; display: inline-block;">
												<option value="default">전체</option>
												<option>회원</option>
												<option>공간이용 및 후기</option>
												<option>호스트되기</option> 
												<option>공간조회</option>
												<option>공간등록</option>
												<option>공간정보관리</option> 
												<option>가격 및 일정</option>
												<option>예약 및 결제</option>
												<option>취소 및 환불</option> 
												<option>기타</option>
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
											<label for="exampleInputEmail1">Upload Image</label> 
											<p>이자리에기존에있던파일이있어야겠죠?</p>
											<input type="file" id="file1" name="file1" class="form-control" />
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

                           <button id="btn_submit" type="button" class="btn btn-primary">수정하기</button>
                           <button id="btn_submit" type="button" class="btn btn-primary">목록으로</button>
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
