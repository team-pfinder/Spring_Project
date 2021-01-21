<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<head>

  <c:import url="${cp}/includes/includes_admin.jsp"></c:import>
  <title>도움말 목록</title>
  <script type="text/javascript">
  /*
	//체크박스 전체선택 전체 해제
	$(document).ready(function()
	{
		$("#allCheck").click(function()
		{
			//전체 선택 체크
			if($("#allCheck").prop("checked"))
			{
				// 해당화면 전체 checkbox 체크하는 구문
				$("input[type=checkbox]").prop("checked", true);
			}
			// 전체 선택 해제
			else
			{
				// 해당화면 전체 checkbox 체크 해제 하는 구문
				$("input[type=checkbox]").prop("checked", false);
			}
		});
	});
  */
      
  </script>
  
</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">
  
  	<!-- Sidebar -->
  	<div>
	<c:import url="${cp}/includes/Admin_Sidebar.jsp"></c:import>
	</div>
<%--  	<%@include file="../includes/Admin_Sidebar.jsp" %> --%>
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
          <h1 class="h3 mb-2 text-gray-800">도움말</h1>
          <p class="mb-4"> 여기는 도움말 목록입니다</p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
              <h6 class="m-0 font-weight-bold text-primary">도움말 목록</h6>
          
              <button type="button" class="btn btn-secondary" onclick="location.href='helpinsertform.action'" >글 작성</button>
              </div>
            
            
          <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable">
                  <thead>
				<tr align="center">
					<th>도움말 코드</th>
					<th>카테고리</th>
					<th>제목</th>
					<th>내용</th>
					<th>작성일자</th>
					<th>수정/삭제</th>
				</tr>

			</thead>
               <tbody>
               <c:forEach var="HelpDTO" items="${A_helpList }">
				<tr align="center">

					 <td>${HelpDTO.help_code }</td>
					
					<td>${HelpDTO.board_type}</td>

					<td>${HelpDTO.help_title }</td>

					<td>${HelpDTO.help_content }</td>
					
					<td>${HelpDTO.help_date }</td>

					<td>
						<!-- 수정기능 -->
						<button type="button" class="btn btn-warning" value="${HelpDTO.help_code}" 
						onclick="location.href='../actions/helpupdateform.action?help_code=' + this.value;">수정</button>			 											 
						<!-- 삭제기능 -->
						<button type="button" class="btn btn-danger " value="${HelpDTO.help_code}" 
						onclick="location.href='../actions/helpdelete.action?help_code=' + this.value;">삭제</button>
					</td>
				</tr>

			</c:forEach>
			</tbody>
                  
                </table>
              </div>
            </div>
          </div>

        </div>
        <!-- /.container-fluid -->
   
   <!-- 삭제 -->

    <div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
      aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
         <div class="modal-content">
            <div class="modal-header">
            	<h4 class="modal-title" id="myModalLabel">삭제하기</h4>
               <button type="button" class="close" data-dismiss="modal">
                  <span aria-hidden="true">×</span><span class="sr-only">Close</span>
               </button>
            </div>
            <div class="modal-body">
            <!-- 해당 리뷰, 리뷰답글 받아와야함 -->
               <p>삭제하시겠습니까?</p>
               
            </div>
            <div class="modal-footer">
            	<button type="button" class="btn btn-primary">확인</button>
               <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
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
