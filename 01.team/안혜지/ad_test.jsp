<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();
%>
<!DOCTYPE html>

<head>

  <%@ include file="../includes/includes_admin.jsp" %>
  <title>공간검수 목록</title>

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
          <h1 class="h3 mb-2 text-gray-800">공간 검수</h1>
          <p class="mb-4"> 요기는 공간 검수 목록이지롱 요기 게시물 누르면 페이지로 기기</p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">공간 검수 목록</h6>
            </div>
            
            
          <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
				<tr align="center">
					<!-- <th><input type="checkbox" /></th> -->
					<th>공간 번호</th>
					<th>공간 사진</th>
					<th>공간 명</th>
					<th>공간 유형</th>
					<th>검수상태</th>
					<th>검수처리</th>
				</tr>
			</thead>
               <tbody>
				<tr align="center">
					<!-- <td><input type="checkbox" /></td> -->
					<td>1</td>

					<td><img src="<%=cp%>/images/image_1.jpg" style="width: 100px; height: 40px"></td>

					<td>친구덜과오랜만</td>
					
					<td>파티룸</td>

					<td>승인</td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-primary btn-icon-split">검수처리</button> 
					</td>
				</tr>
				
				<tr align="center">
					<!-- <td><input type="checkbox" /></td> -->
					<td>2</td>

					<td><img src="<%=cp%>/images/image_2.jpg" style="width: 100px; height: 40px"></td>

					<td>윌유메리미?</td>
					
					<td>브라이덜샤워</td>

					<td>거절 </td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-primary btn-icon-split">검수처리</button> 
					</td>
				</tr>
				
				<tr align="center">
					<!-- <td><input type="checkbox" /></td> -->
					<td>3</td>

					<td><img src="<%=cp%>/images/image_3.jpg" style="width: 100px; height: 40px"></td>

					<td>두유라잌파티?</td>
					
					<td>클럽</td>

					<td>승인</td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-primary btn-icon-split">검수처리</button> 
					</td>
				</tr>
				
				<tr align="center">
					<!-- <td><input type="checkbox" /></td> -->
					<td>4</td>

					<td><img src="<%=cp%>/images/image_4.jpg" style="width: 100px; height: 40px"></td>

					<td>나랑별보러갈래?</td>
					
					<td>루프탑</td>

					<td>승인</td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-primary btn-icon-split">검수처리</button> 
					</td>
				</tr>
				
				<tr align="center">
					<!-- <td><input type="checkbox" /></td> -->
					<td>5</td>

					<td><img src="<%=cp%>/images/image_5.jpg" style="width: 100px; height: 40px"></td>

					<td>쌍용 파티</td>
					
					<td>파티룸</td>

					<td>거절</td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-primary btn-icon-split">검수처리</button> 
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
