<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<head>

  <%@ include file="../includes/includes_admin.jsp" %>
  <title>도움말 목록</title>

</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">
  
  	<!-- Sidebar -->
 	<%@include file="../includes/Admin_Sidebar.jsp" %>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">
      
      <!-- header -->
      
      <%@include file="../includes/header.jsp" %>

        <!-- Begin Page Content -->
        <div class="container-fluid">
        

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">도움말</h1>
          <p class="mb-4"> 요기는 도움말 목록이지롱 요기 게시물 누르면 수정페이지로 기기</p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">도움말 목록</h6>
            </div>
            
            
          <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
				<tr align="center">
					<th><input type="checkbox" /></th>
					<th>글 번호</th>
					<th>제목</th>
					<th>내용</th>
					<th>수정/삭제</th>
				</tr>
			</thead>
               <tbody>
				<tr align="center">
					<td><input type="checkbox" /></td>

					<td>1</td>

					<td>아무제목</td>

					<td>아무말 아 하기 싫어</td>

					<td>
						<!-- 수정기능 -->
						<button type="button" class="btn btn-warning btn-icon-split">수정</button> 
						<!-- 삭제기능 -->
						<button type="button" class="btn btn-danger btn-icon-split">삭제</button>
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
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="login.html">Logout</a>
        </div>
      </div>
    </div>
  </div>

</body>

</html>
