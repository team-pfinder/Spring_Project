<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>

<c:import url="${cp}/includes/includes_admin.jsp"></c:import>
  <title>호스트 리뷰 조회</title>
  <script type="text/javascript">
  
  	//상세보기 버튼을 클릭 시 팝업창 
	$(document).ready(function()
	{
		$(".detailBtn").click(function()
		{
			// alert($(this).val());
			var popUrl = "hostreviewpopup.action?review_reply_code=" + $(this).val();
			var popOption = "width=500, height=700, resizable=no, scrollbars=yes, status=no";
			window.open(popUrl, "", popOption);
		});
	});
	
	// 삭제버튼 클릭 시 삭제
	$(document).ready(function()
	{
		$(".deleteBtn").click(function()
		{
			// alert($(this).val());
			
			if (confirm("해당 게시글을 삭제하시겠습니까?"))
			{
				$(location).attr("href", "hostreviewdelete.action?reivew_reply_code=" + $(this).val());
			}
		});
	});

/*  
	//체크박스
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
<c:import url="${cp}/includes/Admin_Sidebar.jsp"></c:import>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">
      
      <!-- header -->
      
     <c:import url="${cp}/includes/header.jsp"></c:import>
        <!-- Begin Page Content -->
        <div class="container-fluid">
        

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">호스트 리뷰답글</h1>
          <p class="mb-4">호스트 리뷰답글 목록입니다.</p>

         <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
              <h6 class="m-0 font-weight-bold text-primary">리뷰답글목록</h6>
          
              <button type="button" class="btn btn-danger " data-toggle="modal" data-target="#myModal2">전체삭제</button>
              </div>
            
            
          <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
				<tr align="center">
					<th>리뷰답글코드</th>
					<th>이용자리뷰</th>
					<th>답글</th>
					<th>호스트</th>
					<th>작성일자</th>
					<th>처리</th>
				</tr>
			</thead>
               <tbody>
               <c:forEach var="AdminHostDTO" items="${r_firstList }">
               	<c:choose>
                	<c:when test="${AdminHostDTO.count == 0 }">
				<tr align="center">
					<td>${AdminHostDTO.review_reply_code } </td>
								
								<td>${AdminHostDTO.review_content }</td>
			
								<td>${AdminHostDTO.review_reply_content }</td>
								
								<td>${AdminHostDTO.host_nickname }</td>
								
								<td>${AdminHostDTO.review_reply_date }</td>
			
								<td>
									<!-- 상세보기/삭제기능 -->
									<button type="button" class="btn btn-warning detailBtn"
									value="${AdminHostDTO.review_reply_code }">상세보기</button>  
									<button type="button" class="btn btn-danger deleteBtn" 
									value="${AdminHostDTO.review_reply_code }">삭제</button>
								</td>
				</tr>
				
			</c:when>
							<c:otherwise>
	                     		<tr style="display: none;">
		                      		<td>${AdminHostDTO.review_reply_code } </td>
									
									<td>${AdminHostDTO.review_content }</td>
				
									<td>${AdminHostDTO.review_reply_content }</td>
									
									<td>${AdminHostDTO.host_nickname }</td>
									
									<td>${AdminHostDTO.review_reply_date }</td>
				
									<td>
										<!-- 상세보기/삭제기능 -->
										<button type="button" class="btn btn-warning detailBtn"
										value="${AdminHostDTO.review_reply_code }">상세보기</button>  
										<button type="button" class="btn btn-danger deleteBtn" 
										value="${AdminHostDTO.review_reply_code }">삭제</button>
								   </td>
								</tr>
                        	</c:otherwise>
							</c:choose>
							</c:forEach>
						</tbody>
                  
                </table>
              </div>
            </div>
          </div>

        </div>
        
        
        

<!-- 해당 버튼 클릭시 id가 myModal인 팝업 실행시킴. -->
<!-- 
   모달 팝업
   <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
      aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
         <div class="modal-content">
            <div class="modal-header">
               <h4 class="modal-title" id="myModalLabel">상세보기</h4>
               <button type="button" class="close" data-dismiss="modal">
                  <span aria-hidden="true">×</span><span class="sr-only">Close</span>
               </button>
            </div>
            <div class="modal-body">
            해당 리뷰, 리뷰답글 받아와야함
               <table class="table">
                  <tr>
                     <th align="center">공간코드</th>
                     <td>PL050505</td>
                  </tr>
                  <tr>
                     <th align="center">작성일자</th>
                     <td>2020.12.25</td>
                  </tr>
                  <tr>
                     <th align="center">이용자</th>
                     <td>박보검</td>
                  </tr>
                  
                  <tr>
                     <th align="center">이용자리뷰</th>
                     <td>덕분에너무마음에드는하루였습니다</td>
                  </tr>
                  <tr>
                     <th align="center">호스트</th>
                     <td>조호스트</td>
                  </tr>
                  <tr>
                     <th>호스트 답글</th>
                     <td>너무감사드립니다:)</td>
                  </tr>
                  
               </table>
               
            </div>
            <div class="modal-footer">
               <button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
            </div>
         </div>
      </div>
   </div> 
  -->
   
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
