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
			var popUrl = "userqnapopup.action?qnaCode=" + $(this).val();
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
				$(location).attr("href", "qnadelete.action?qnaCode=" + $(this).val());
			}
		});
	});
	
	/* QNA 테이블과 QNA_REMOVE 테이블 데이터 비교해서 뷰에서 안보이게 */
	$(document).ready(function()
	{
		if(count>0)
		{
			css("display", "none");
		}
	});
	
	
	/* 체크박스 전체선택 전체 해제 */
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
	
	/* 선택삭제 눌렀을 때 */
	$(document).ready(function()
	{
		$("#selectDelete").click(function()
		{
			/* 체크가 선택된 모든 리뷰 삭제 구문 */
			if($("input[type=checkbox]"))	// 체크가 되어있는 것들..근데 이게 맞나?
			{
				confirm("정말로 삭제하시겠습니까?");
				/* 분기하여 확인 누르면 다중삭제 되게 처리할 것 */
			}
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
                    <p class="mb-4">온 QNA를 조회할 수 있읍니다.
                     검색을 통해 특정 이용자QNA를 조회할 수 있읍니다.</p>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Q&A</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0" >
                                    <thead>
                                    	<tr>
                                    		<th><input type="checkbox" id="allCheck">
                                    		<button type="button" id="selectDelete" class="btn">선택삭제</button></th>
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
                                    	<tr>
                                    		<td><input type="checkbox"></td>
                                    		<td>${adminUserDTO.qnaCode }</td>
                                    		<td>${adminUserDTO.memberNickname }</td>
                                    		<td>${adminUserDTO.memberEmail }</td>
                                    		<td>${adminUserDTO.locCode }</td>
                                    		<td>${adminUserDTO.qnaContent }</td>
                                    		<td>
												<button type="button" class="btn btn-primary detailBtn"
												value="${adminUserDTO.qnaCode }">상세보기</button>
												<button type="button" class="btn btn-danger deleteBtn"
												value="${adminUserDTO.qnaCode }">삭제</button>
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