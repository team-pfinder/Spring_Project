<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:import url="${cp }/includes/includes_admin.jsp"></c:import>
<title>userQna.jsp</title>

<style type="text/css">
	table 
	{
		text-align: center;
	}
	table tr td
	{
		text-overflow: ellipsis;
		overflow: hidden;
		white-space: nowrap;
		max-width: 300px;
	}

</style>

<script type="text/javascript">
	
	/* 상세보기 버튼을 클릭했을 때 상세보기 팝업을 띄우는 구문 */
	$(document).ready(function()
	{
		$(".detailBtn").click(function()
		{
			// alert($(this).val());
			var popUrl = "userqnapopup.action?qna_code=" + $(this).val();
			var popOption = "width=500, height=700, resizable=no, scrollbars=yes, status=no";
			window.open(popUrl, "", popOption);
		});
	});
	
	/* 삭제버튼 누르면 삭제가 되게 */
	$(document).ready(function()
	{
		$(".deleteBtn").click(function()
		{
			// alert($(this).val());
			
			if (confirm("해당 게시글을 삭제하시겠습니까?"))
			{
				$(location).attr("href", "qnadelete.action?qna_code=" + $(this).val());
			}
		});
	});
	
	
	/* 선택삭제 */
	$(document).ready(function()
	{
		var chkObj = document.getElementsByName("rowCheck");
		var rowCnt = chkObj.length;
		
		/* 체크박스 전체 선택 */
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
		
		/* 손수 전체 선택을 해도 전체선택 체크박스에 체크 되게 */
		$("input[name='rowCheck']").click(function()
		{
			if($("input[name='rowCheck']:checked").length == rowCnt)
			{
				$("input[name='allCheck']")[0].checked = true;
			}
			else
			{
				$("input[name='allCheck']")[0].checked = false;
			}
		});
		
		/* 선택삭제 버튼 클릭 */
		$(".modal-footer .removeBtn").click(function()
		{
			// 추후 추가
		});
	});
        
    </script>

</head>
<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

   <!-- Sidebar -->
   <div>
   		<c:import url="${cp}/includes/Admin_Sidebar.jsp"></c:import>
   </div>

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <!-- Header -->
				<c:import url="${cp }/includes/header.jsp"></c:import>

                 <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Q&A조회</h1>
                    <p class="mb-4">이용자가 작성한 Q&A를 조회하고 관리할 수 있습니다.</p>

                    <!-- DataTales Example -->
		          <div class="card shadow mb-4">
		            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
		              <h6 class="m-0 font-weight-bold text-primary">QnA목록</h6>
		          
		              <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal2">선택삭제</button>
		              </div>
		              
		              
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="table" width="100%" cellspacing="0" >
                                    <thead>
                                    	<tr>
                                    		<th><input type="checkbox" id="allCheck" name="allCheck"></th>
                                    		<th>Q&A코드</th>
                                    		<th>닉네임</th>
                                    		<th>이메일</th>
                                    		<th>공간코드</th>
                                    		<th>Q&A내용</th>
                                    		<th>처리</th>
                                    	</tr>
                                    </thead>
                                    
                                    <tbody>
                                    	<c:forEach var="adminUserDTO" items="${basicList }">
	                                    	<c:choose>
	                                    		<c:when test="${adminUserDTO.count == 0 }">
			                                    	<tr>
			                                    		<td><input type="checkbox" name="rowCheck" value="${adminUserDTO.qna_code }"></td>
			                                    		<td>${adminUserDTO.qna_code }</td>
			                                    		<td>${adminUserDTO.member_nickname }</td>
			                                    		<td>${adminUserDTO.member_email }</td>
			                                    		<td>${adminUserDTO.loc_code }</td>
			                                    		<td>${adminUserDTO.qna_content }</td>
			                                    		<td>
															<button type="button" class="btn btn-primary detailBtn"
															value="${adminUserDTO.qna_code }">상세보기</button>
															<button type="button" class="btn btn-danger deleteBtn"
															value="${adminUserDTO.qna_code }">삭제</button>
														</td>
			                                    	</tr>
		                                    	</c:when>
		                                    	<c:otherwise>
		                                    		<tr style="display: none;">
			                                    		<td><input type="checkbox"></td>
			                                    		<td>${adminUserDTO.qna_code }</td>
			                                    		<td>${adminUserDTO.member_nickname }</td>
			                                    		<td>${adminUserDTO.member_email }</td>
			                                    		<td>${adminUserDTO.loc_code }</td>
			                                    		<td>${adminUserDTO.qna_content }</td>
			                                    		<td>
															<button type="button" class="btn btn-primary detailBtn"
															value="${adminUsertDTO.qna_code }">상세보기</button>
															<button type="button" class="btn btn-danger deleteBtn"
															value="${adminUserDTO.qna_code }">삭제</button>
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
				            	<button type="button" class="btn btn-primary removeBtn">확인</button>
				               <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
				            </div>
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
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
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