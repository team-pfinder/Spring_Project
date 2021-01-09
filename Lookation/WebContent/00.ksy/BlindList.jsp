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
<%@ include file="../includes/includes_admin.jsp" %>
<title>BlindList.jsp</title>

<script type="text/javascript">

	function blindCancel()
	{
		alert("블라인드 해제 처리");
	}

</script>

<style type="text/css">

	table
	{
		text-align: center;
	}

</style>

</head>
<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

   <!-- Sidebar -->
   <%@ include file="../includes/Admin_Sidebar.jsp" %>

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">
					<!-- Header --> <!-- 경로 변경 필요 -->
					<%@ include file="../includes/header.jsp" %>

                 <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">블라인드 관리</h1>
                    <p class="mb-4">블라인드 관리 근데 해제 시켜줄 일이 있으려나...?</p>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">블라인드 리스트</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
	                                    <tr>
											<th>분류</th>
											<th>회원번호</th>
											<th>게시물번호</th>
											<th>게시물</th>
											<th>블라인드처리일자</th>
											<th>블라인드처리사유</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>공간게시글</td>
											<td>786</td>
											<td>3939</td>
											<td>홍대파티룸</td>
											<td>2020-12-26</td>
											<td>탈퇴</td>
											<td>
												<button type="button" onclick="blindCancel()" class="btn btn-primary">블라인드 해제</button>
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