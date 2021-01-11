<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>

  <%@ include file="../includes/includes_admin.jsp" %>
  <title>호스트 QnA 조회</title>
  
    <script type="text/javascript">
  
  	//팝업창
	function popupOpen()
	{
		var popUrl = "adHostQnaPopup.jsp";
		var popOption = "width=500, height=500, resizable=no, scrollbars=yes, status=no";
		window.open(popUrl, "", popOption);
	}
	
  
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
      
  </script>
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
          <h1 class="h3 mb-2 text-gray-800">호스트 QnA답글</h1>
          <p class="mb-4">호스트 QnA답글 목록입니다.</p>

         <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
              <h6 class="m-0 font-weight-bold text-primary">QnA답글목록</h6>
          
              <button type="button" class="btn btn-danger " data-toggle="modal" data-target="#myModal2">전체삭제</button>
              </div>
            
            
          <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
				<tr align="center">
					<th><input type="checkbox" /></th>
					<th>번호</th>
					<th>공간코드</th>
					<th>이용자QnA</th>
					<th>답글</th>
					<th>호스트</th>
					<th>작성일자</th>
					<th>처리</th>
				</tr>
			</thead>
               <tbody>
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>1</td>

					<td>QRE000001</td>
					
					<td>여기보일러가따숩나요?</td>

					<td>넹:) 귀뚜라미입니다</td>
					
					<td>김호스트</td>
					
					<td>2021.01.01</td>

					<td>
						<!-- 상세보기/삭제기능 -->
						<button type="button" class="btn btn-warning"onclick="popupOpen()">상세보기</button>  
						<button type="button" class="btn btn-danger " data-toggle="modal" data-target="#myModal2">삭제</button>
					</td>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>2</td>
		
					<td>QRE000002</td>
					
					<td>에어컨청소는언제했나요?</td>

					<td>신년맞이대청소했습니다</td>
					
					<td>권호스트</td>
					
					<td>2020.12.31</td>

					<td>
						<!-- 상세보기/삭제기능 -->
						<button type="button" class="btn btn-warning"onclick="popupOpen()">상세보기</button>  
						<button type="button" class="btn btn-danger " data-toggle="modal" data-target="#myModal2">삭제</button>
					</td>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>3</td>
					
					<td>QRE000003</td>
					
					<td>$바@다!이&야%기#</td>

					<td>$꺼@지!세&용%웩#</td>
					
					<td>벧호스트</td>
					
					<td>2020.12.23</td>

					<td>
						<!-- 상세보기/삭제기능 -->
						<button type="button" class="btn btn-warning"onclick="popupOpen()">상세보기</button>  
						<button type="button" class="btn btn-danger " data-toggle="modal" data-target="#myModal2">삭제</button>
					</td>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>4</td>

					<td>QRE000004</td>
					
					<td>홍대역몇번출구인가요?</td>

					<td>2번출구입니다</td>
					
					<td>조호스트</td>
					
					<td>2020.12.24</td>

					<td>
						<!-- 상세보기/삭제기능 -->
						<button type="button" class="btn btn-warning"onclick="popupOpen()">상세보기</button>  
						<button type="button" class="btn btn-danger " data-toggle="modal" data-target="#myModal2">삭제</button>
					</td>
				</tr>
				
				<tr align="center">
				<td><input type="checkbox" /></td>
					<td>5</td>
					
					<td>QRE000005</td>
					
					<td>불친절이컨셉인가요?</td>

					<td>싫으면오지말던가</td>
					
					<td>붐따호스트</td>
					
					<td>2020.12.25</td>

					<td>
						<!-- 상세보기/삭제기능 -->
						<button type="button" class="btn btn-warning"onclick="popupOpen()">상세보기</button>  
						<button type="button" class="btn btn-danger " data-toggle="modal" data-target="#myModal2">삭제</button>
					</td>
				</tr>
			</tbody>
                  
                </table>
              </div>
            </div>
          </div>

        </div>
        

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
                     <th align="center">이용자QnA</th>
                     <td>불친절이컨셉인가요?진짜너무어이가없고기분이나쁩니다</td>
                  </tr>
                  <tr>
                     <th align="center">호스트</th>
                     <td>붐따호스트</td>
                  </tr>
                  <tr>
                     <th>호스트 답글</th>
                     <td>꼬우면오지마시던가요</td>
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
