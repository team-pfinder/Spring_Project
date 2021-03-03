<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();
%>
<!DOCTYPE html>

<head>

  <%@ include file="../includes/includes_admin.jsp" %>
  <title>호스트 리뷰 조회</title>

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
          <h1 class="h3 mb-2 text-gray-800">호스트 리뷰답글</h1>
          <p class="mb-4"> 요기는 호스트 리뷰답글 목록이지롱 요기 게시물 누르면 페이지로 기기</p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">호스트 리뷰답글 목록</h6>
            </div>
            
            
          <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
				<tr align="center">
					<th><input type="checkbox" /></th>
					<th>번호</th>
					<th>공간코드</th>
					<th>답글</th>
					<th>답글코드</th>
					<th>호스트</th>
					<th>작성일자</th>
					<th>상태</th>
					<th>처리</th>
				</tr>
			</thead>
               <tbody>
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>1</td>


					<td>PL010101</td>
					
					<td>네 감사합니다 :)</td>

					<td>RE010101</td>
					
					<td>김호스트</td>
					
					<td>2021.01.01</td>
					<td></td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-warning btn-icon-split">상세보기</button> 
						<button type="button" class="btn btn-secondary btn-icon-split">블라인드</button> 
					</td>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>2</td>

					

					<td>PL020202</td>
					
					<td>마음에들어하시니기분이좋습니다 :)</td>

					<td>RE020202</td>
					
					<td>권호스트</td>
					
					<td>2020.12.31</td>
					<td></td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-warning btn-icon-split">상세보기</button> 
						<button type="button" class="btn btn-secondary btn-icon-split">블라인드</button> 
					</td>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>3</td>

					

					<td>PL030303</td>
					
					<td>장난하나오지마셈 ㅗㅗ</td>

					<td>RE030303</td>
					
					<td>벧호스트</td>
					
					<td>2020.12.23</td>
					<td>블라인드</td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-warning btn-icon-split">상세보기</button> 
						<button type="button" class="btn btn-secondary btn-icon-split">블라인드</button> 
					</td>
				</tr>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>4</td>

					

					<td>PL040404</td>
					
					<td>또 오세용 :)</td>

					<td>RE040404</td>
					
					<td>조호스트</td>
					
					<td>2020.12.24</td>
					<td></td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-warning btn-icon-split">상세보기</button> 
						<button type="button" class="btn btn-secondary btn-icon-split">블라인드</button> 
					</td>
				</tr>
				</tr>
				
				<tr align="center">
				<td><input type="checkbox" /></td>
					<td>5</td>

					
					<td>PL050505</td>
					
					<td>덕분에기분이잡쳤습니다</td>

					<td>RE050505</td>
					
					<td>붐따호스트</td>
					
					<td>2020.12.25</td>
					<td>블라인드</td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-warning btn-icon-split">상세보기</button> 
						<button type="button" class="btn btn-secondary btn-icon-split">블라인드</button> 
					</td>
				</tr>
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
