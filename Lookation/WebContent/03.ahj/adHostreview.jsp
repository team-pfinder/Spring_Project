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
          <h1 class="h3 mb-2 text-gray-800">호스트 리뷰</h1>
          <p class="mb-4">호스트 리뷰 목록입니다.</p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">호스트 리뷰 목록</h6>
            </div>
            
            
          <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
				<tr align="center">
					<th><input type="checkbox" /></th>
					<th>번호</th>
					<th>공간코드</th>
					<th>이용자리뷰</th>
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

					<!-- 해당공간 하이퍼링크 넣기 -->
					<td><a href="#">PL010101</a></td>
					
					<td>친구들과너무즐거운시간이었습니다</td>

					<td>넹:) 너무감사합니다!</td>
					
					<td>김호스트</td>
					
					<td>2021.01.01</td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-warning " data-toggle="modal" data-target="#myModal">상세보기</button>  
						<button type="button" class="btn btn-danger " onclick="showPopup(true)">삭제</button> 
					</td>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>2</td>
		
					<!-- 해당공간 하이퍼링크 넣기 -->
					<td><a href="#">PL020202</a></td>
					
					<td>인생샷을너무마음에들어요</td>

					<td>다른테마도이용해보세요!</td>
					
					<td>권호스트</td>
					
					<td>2020.12.31</td>

					<td>
						<!-- 검수처리기능 -->
						<button type="button" class="btn btn-warning " data-toggle="modal" data-target="#myModal">상세보기</button>  
						<button type="button" class="btn btn-danger " onclick="showPopup(true)">삭제</button>
					</td>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>3</td>

					
					<!-- 해당공간 하이퍼링크 넣기 -->
					<td><a href="#">PL030303</a></td>
					
					<td>너무너무별로였어요!붐따</td>

					<td>오지마세용</td>
					
					<td>벧호스트</td>
					
					<td>2020.12.23</td>

					<td>
						<!-- 처리기능 -->
						<button type="button" class="btn btn-warning " data-toggle="modal" data-target="#myModal">상세보기</button>  
						<button type="button" class="btn btn-danger " onclick="showPopup(true)">삭제</button> 
					</td>
				</tr>
				
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>4</td>

					
					<!-- 해당공간 하이퍼링크 넣기 -->
					<td><a href="#">PL040404</a></td>
					
					<td>잘~~놀다갑니다</td>

					<td>또놀러오세요</td>
					
					<td>조호스트</td>
					
					<td>2020.12.24</td>

					<td>
						<!-- 처리기능 -->
						<button type="button" class="btn btn-warning " data-toggle="modal" data-target="#myModal">상세보기</button>  
						<button type="button" class="btn btn-danger " onclick="showPopup(true)">삭제</button>
					</td>
				</tr>
				
				<tr align="center">
				<td><input type="checkbox" /></td>
					<td>5</td>

					<!-- 해당공간 하이퍼링크 넣기 -->					
					<td><a href="#">PL050505</a></td>
					
					<td>어쩔수없이이용하는데</td>

					<td>싫으면오지말던가</td>
					
					<td>붐따호스트</td>
					
					<td>2020.12.25</td>

					<td>
						<!-- 처리기능 -->
						<button type="button" class="btn btn-warning " data-toggle="modal" data-target="#myModal">상세보기</button> 
						<button type="button" class="btn btn-danger " data-toggle="modal" data-target="#myModal2">삭제</button>
					</td>
				</tr>
			</tbody>
                  
                </table>
              </div>
            </div>
          </div>

        </div>
        
        
        

   <!-- 해당 버튼 클릭시 id가 myModal인 팝업 실행시킴. -->


   <!-- 모달 팝업 -->
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
            <!-- 해당 리뷰, 리뷰답글 받아와야함 -->
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
        
<div id="popup" class="hide">
  <div class="content">
    <p>
      삭제 하시겠습니까?
    </p>
    <!-- 버튼 디자인 수정 필요  -->
     <button  type="button" class="btn btn-success">예</button>
     <button  type="button"  class="btn btn-secondary" onclick="closePopup()">닫기</button>
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
