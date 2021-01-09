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

<%@ include file="../includes/includes_admin.jsp" %>
<title>DeclarationManager.jsp</title>

<style type="text/css">

	table
	{
		text-align: center;
	}

</style>

<script type="text/javascript">

	function process1()
	{
		alert("신고 처리 프로세스");
	}
	
	function process2()
	{
		alert("신고 반려 프로세스");
		
		// 신고 처리 사유를 작성하는 페이지 또는 텍스트박스 필요
	}
	
	function blacklist()
	{
		var popUrl = "blackListPopup.jsp";
		var popOption = "width=500, height=300, resizable=no, scrollbars=yes, status=no";
		window.open(popUrl, "", popOption);
	}

</script>

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
                    <h1 class="h3 mb-2 text-gray-800">신고처리</h1>
                    <p class="mb-4">이용자와 호스트가 신고한 내용들을 볼 수 있는 페이지.
                    신고처리가 되면...ajax 처리를 통해서...바로...볼 수 있다...신고완료 리스트...</p>
					
					
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">신고처리리스트</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                	<thead>
	                                    <tr>
											<th>컨텐츠정보</th>
											<th>신고사유</th>
											<th>피신고자정보</th>
											<th>신고자정보</th>
											<th>신고일자</th>
											<th>신고처리결과</th>	<!-- 처리 했으면 처리결과를 보여주고 처리안했으면 버튼이 노출되게..할 수 있나? -->
										</tr>
									</thead>
									
									<tbody>
										<tr>
											<td>컨텐츠정보1</td>
											<td>신고사유1</td>
											<td>피신고자정보1</td>
											<td>신고자정보1</td>
											<td>신고일자1</td>
											<td>
												<button type="button" onclick="process1()" class="btn btn-primary">인정</button>
												<button type="button" onclick="process2()" class="btn btn-secondary">반려</button>
												<button type="button" onclick="blacklist()" class="btn btn-danger">블랙리스트추가</button>
											</td>
										</tr>
										<tr>
											<td>컨텐츠정보2</td>
											<td>신고사유2</td>
											<td>피신고자정보2</td>
											<td>신고자정보2</td>
											<td>신고일자2</td>
											<td>
												<button type="button" onclick="process1()" class="btn btn-primary">인정</button>
												<button type="button" onclick="process2()" class="btn btn-secondary">반려</button>
												<button type="button" onclick="blacklist()" class="btn btn-danger">블랙리스트추가</button>
											</td>
										</tr>
										<tr>
											<td>컨텐츠정보3</td>
											<td>신고사유3</td>
											<td>피신고자정보3</td>
											<td>신고자정보3</td>
											<td>신고일자3</td>
											<td>
												신고반려
											</td>
										</tr>
										<tr>
											<td>컨텐츠정보4</td>
											<td>신고사유4</td>
											<td>피신고자정보4</td>
											<td>신고자정보4</td>
											<td>신고일자4</td>
											<td>
												신고인정
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




 <!-- Bootstrap core JavaScript-->
    <script src="admin/vendor/jquery/jquery.min.js"></script>
    <script src="admin/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="admin/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="admin/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="admin/vendor/chart.js/Chart.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="admin/js/demo/chart-area-demo.js"></script>
    <script src="admin/js/demo/chart-pie-demo.js"></script>

</body>
</html>