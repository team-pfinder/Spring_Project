<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();
%>
<!DOCTYPE html>

<head>

  <%@ include file="../includes/includes_admin.jsp" %>
  <title>호스트 QnA 조회</title>
 
<!-- 블라인드 클릭 시 팝업 창 -->
<script type="text/javascript">
  function showPopup(hasFilter) 
  {
	  const popup = document.querySelector('#popup');
	  
	  if (hasFilter) 
	  {
	  	popup.classList.add('has-filter');
	  }
	  popup.classList.remove('hide');
  }

	function closePopup() 
	{
		const popup = document.querySelector('#popup');
	    
		popup.classList.add('hide');
	}
  </script>

<!-- 블라인드 클릭 시 팝업 창 -->  
<style type="text/css">

	#popup 
	{
	  display: flex;
	  justify-content: center;
	  align-items: center;
	  position: fixed;
	  top: 0;
	  left: 0;
	  width: 100%;
	  height: 100%;
	  background: rgba(0, 0, 0, .7);
	  z-index: 1;
	}
	
	#popup.hide 
	{
	  display: none;
	}
	
	#popup.has-filter 
	{
	  backdrop-filter: blur(4px);
	  -webkit-backdrop-filter: blur(4px);
	}
	
	#popup .content 
	{
	  padding: 20px;
	  background: #fff;
	  border-radius: 5px;
	  box-shadow: 1px 1px 3px rgba(0, 0, 0, .3);
	}
</style>
 

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
          <h1 class="h3 mb-2 text-gray-800">호스트 QnA</h1>
          <p class="mb-4"> 요기는 호스트 QnA 목록이지롱 요기 게시물 누르면 상세페이지로 기기</p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">호스트 QnA 목록</h6>
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
					<th>상태</th>
					<th>처리</th>
				</tr>
			</thead>
               <tbody>
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>1</td>


					<td>PL010101</td>
					
					<td>여기보일러가따숩나요?</td>

					<td>넹:) 귀뚜라미입니다</td>
					
					<td>김호스트</td>
					
					<td>2021.01.01</td>
					<td></td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-warning btn-icon-split" >상세보기</button> 
						<button type="button" class="btn btn-secondary btn-icon-split" onclick="showPopup(true)">블라인드</button> 
					</td>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>2</td>

					

					<td>PL020202</td>
					
					<td>에어컨청소는언제했나요?</td>

					<td>신년맞이대청소했습니다</td>
					
					<td>권호스트</td>
					
					<td>2020.12.31</td>
					<td></td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-warning btn-icon-split">상세보기</button> 
						<button type="button" class="btn btn-secondary btn-icon-split" onclick="showPopup(true)">블라인드</button> 
					</td>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>3</td>

					

					<td>PL030303</td>
					
					<td>$바@다!이&야%기#</td>

					<td>$꺼@지!세&용%웩#</td>
					
					<td>벧호스트</td>
					
					<td>2020.12.23</td>
					<td>삭제</td>

					<td>
						<!-- 처리기능 -->
						<button type="button" class="btn btn-warning btn-icon-split">상세보기</button> 
						<button type="button" class="btn btn-secondary btn-icon-split" onclick="showPopup(true)">블라인드</button> 
					</td>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>4</td>

					

					<td>PL040404</td>
					
					<td>홍대역몇번출구인가요?</td>

					<td>2번출구입니다</td>
					
					<td>조호스트</td>
					
					<td>2020.12.24</td>
					<td></td>

					<td>
						<!-- 처리기능 -->
						<button type="button" class="btn btn-warning btn-icon-split">상세보기</button> 
						<button type="button" class="btn btn-secondary btn-icon-split" onclick="showPopup(true)">블라인드</button>
					</td>
				</tr>
				
				<tr align="center">
				<td><input type="checkbox" /></td>
					<td>5</td>

					
					<td>PL050505</td>
					
					<td>불친절이컨셉인가요?</td>

					<td>싫으면오지말던가</td>
					
					<td>붐따호스트</td>
					
					<td>2020.12.25</td>
					<td>블라인드</td>

					<td>
						<!-- 처리기능 -->
						<button type="button" class="btn btn-warning btn-icon-split">상세보기</button> 
						<button type="button" class="btn btn-secondary btn-icon-split" onclick="showPopup(true)">블라인드</button>
					</td>
				</tr>
			</tbody>
                  
                </table>
              </div>
            </div>
          </div>

        </div>
        
<div id="popup" class="hide">
  <div class="content">
    <p>
      블라인드 처리를 하시겠습니까?
    </p>
    <!-- 버튼 디자인 수정 필요  -->
     <button  type="button" class="btn btn-success btn-icon-split">예</button>
     <button  type="button"  class="btn btn-secondary btn-icon-split" onclick="closePopup()">닫기</button>
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
