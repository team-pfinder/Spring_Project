<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도움말 글쓰기</title>
<c:import url="${cp}/includes/includes_admin.jsp"></c:import>
<script type="text/javascript">
$(document).ready(function()
		{
			$(".resethelp").click(function()
			{
				
				if(confirm("도움말 입력을 취소하시겠습니까?"))
				{
					$(location).attr("href","helpmanager.action");
				}
			});
			
			$("#btn_submit").click(function()
			{
				//alert("나야");
				$("#insertForm").submit();
			});
		});
</script>
</head>
<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

<c:import url="${cp}/includes/Admin_Sidebar.jsp"></c:import>

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

<c:import url="${cp}/includes/header.jsp"></c:import>

				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- 글등록 폼 -->
					<div class="row">
						<!-- left column -->
						<div class="col-md-12">
							<!-- general form elements -->
							<div class="box box-primary">
								<div class="box-header">
									<h3 class="box-title">도움말 등록하기</h3>
								</div>
								<br>

								<!-- /.box-header -->
								<form id="insertForm" role="form" action="helpinsert.action" method="post" >
									<div class="box-body">
									<label for="exampleInputEmail1">카테고리</label>
										<div class="form-group">
											<select class="form-control" id="mainCategory" name="board_type_code"
												style="width: 30%; margin-right: 10px; display: inline-block;">
												<option value="default">전체</option>
												<option value="BT000010">회원</option> 
												<option value="BT000001">호스트되기</option>
												<option value="BT000002">공간등록</option>
												<option value="BT000003">공간조회</option> 
												<option value="BT000004">공간정보관리</option>
												<option value="BT000005">예약및결제</option>
												<option value="BT000006">취소및환불</option> 
												<option value="BT000007">공간이용및후기</option>
												<option value="BT000008">공간정보관리</option>
												<option value="BT000009">기타</option> 
												
												
											</select>

										</div>

										<div class="form-group">
											<label for="exampleInputEmail1">제목</label> 
											<input type="text" id="help_title" name="help_title" class="form-control">
										</div>


										<div class="form-group">
											<label for="exampleInputPassword1">글쓰기</label>
											<textarea class="form-control" id="help_content" name="help_content" rows="8"></textarea>
										</div>

										<div class="form-group">
											<label for="exampleInputEmail1">Upload Image</label> 
											<input type="file" id="file1" name="file1" class="form-control" />
										</div>
									</div><!-- .box-body -->
								</form>
							</div>
						</div>
					</div><!-- /.row	 -->			
				</div><!-- /.container-fluid -->
							
							<div class="box-footer">
                           <div>
                              <hr>
                           </div>

                           <ul class="mailbox-attachments clearfix uploadedList">
                           </ul>

                           <button id="btn_submit" type="button" class="btn btn-primary">작성하기</button>
                           <button id="btn_list" type="button" class="btn btn-primary" onclick="location.href='helpmanager.action'">목록으로</button>
                        </div>
							
                 </div>
                 </div>
 
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
				
				
</body>

</html>
