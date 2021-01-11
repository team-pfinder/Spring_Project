<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Lookation_admin</title>
<c:import url="${cp}/includes/includes_admin.jsp"></c:import>
<script type="text/javascript">
	
	// 신고내역 확인하는 팝업창
	function popCheck()
	{
		var url = "8-1_checkReportDetails.jsp";
		var option = "width=600, height=450, resizable=no, scrollbars=yes, status=no";
		window.open(url, "", option);
	}
	
	// 블랙리스트 해제하는 알림창
	function release()
	{
		confirm("해당 회원을 블랙리스트 목록에서 해제하시겠습니까?");
	}
	
	// 블랙리스트 등록하는 팝업창
	function popSetBlack()
	{
		var url = "8-2_setBlacklist.jsp";
		var option = "width=450, height=300, resizable=no, scrollbars=yes, status=no";
		window.open(url, "", option);
	}

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
          <h1 class="h3 mb-2 text-gray-800">회원관리(호스트)</h1>
          <p class="mb-4"> 모든 호스트를 관리할 수 있습니다. 신고내역 버튼을 누르면 해당 회원의 신고내역이 보입니다.</p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">신고내역 확인 / 블랙리스트 설정</h6>
            </div>
            
            
          <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable">
                  <thead>
				<tr align="center">
					<th>회원코드</th>
					<th>아이디</th>
					<th>닉네임</th>
					<th>블랙여부</th>
					<th>처리사유</th>
					<th>내역 확인/블랙리스트 등록</th>
				</tr>
			</thead>
               <tbody>
				<tr align="center">
					<td>H0001</td>
					<td>ul@naver.com</td>
					<td>신규</td>
					<td>Y</td>
					<td>그냥 맘에안들어요</td>
					<td>
						<!-- 신고내역 확인 -->
						<!-- 클릭시 해당회원 신고내역리스트 출력 -->
						<button type="button" class="btn btn-primary btn-icon-split" onclick="popCheck()">신고내역</button> 
						<!-- 블랙리스트 설정 혹은 해제? -->
						<button type="button" class="btn btn-info btn-icon-split" onclick="release()">해제</button>
					</td>
				</tr>
				<tr align="center">
					<td>H0002</td>
					<td>al@naver.com</td>
					<td>안녕</td>
					<td>N</td>
					<td></td>
					<td>
						<button type="button" class="btn btn-primary btn-icon-split">신고내역</button> 
						<!-- 블랙리스트 설정하고 싶으면 이거 클릭 -->
						<!-- ※ 블랙리스트 등록 페이지는? -->
						<button type="button" class="btn btn-danger btn-icon-split" onclick="popSetBlack()">설정</button>
					</td>
				</tr>
				<tr align="center">
					<td>H0003</td>
					<td>b3@naver.com</td>
					<td>잘래</td>
					<td>N</td>
					<td></td>
					<td>
						<button type="button" class="btn btn-primary btn-icon-split">신고내역</button> 
						<button type="button" class="btn btn-danger btn-icon-split">설정</button>
					</td>
				</tr>
				<tr align="center">
					<td>H0004</td>
					<td>vd@naver.com</td>
					<td>그만</td>
					<td>N</td>
					<td></td>
					<td>
						<button type="button" class="btn btn-primary btn-icon-split">신고내역</button> 
						<button type="button" class="btn btn-danger btn-icon-split">설정</button>
					</td>
				</tr>
				<tr align="center">
					<td>H0005</td>
					<td>ol@naver.com</td>
					<td>왜끝이없지</td>
					<td>Y</td>
					<td>욕했음</td>
					<td>
						<button type="button" class="btn btn-primary btn-icon-split">신고내역</button> 
						<button type="button" class="btn btn-info btn-icon-split">해제</button>
					</td>
				</tr>
				<tr align="center">
					<td>H0006</td>
					<td>bbb@naver.com</td>
					<td>신규</td>
					<td>Y</td>
					<td>그냥 맘에안듦</td>
					<td>
						<button type="button" class="btn btn-primary btn-icon-split">신고내역</button> 
						<button type="button" class="btn btn-info btn-icon-split">해제</button>
					</td>
				</tr>
				<tr align="center">
					<td>H0007</td>
					<td>dt@daum.net</td>
					<td>거북이</td>
					<td>Y</td>
					<td>자꾸 할인해달래</td>
					<td>
						<button type="button" class="btn btn-primary btn-icon-split">신고내역</button> 
						<button type="button" class="btn btn-info btn-icon-split">해제</button>
					</td>
				</tr>
				<tr align="center">
					<td>H0008</td>
					<td>dd@dd.com</td>
					<td>토끼</td>
					<td>N</td>
					<td></td>
					<td>
						<button type="button" class="btn btn-primary btn-icon-split">신고내역</button> 
						<button type="button" class="btn btn-danger btn-icon-split">설정</button>
					</td>
				</tr>
				<tr align="center">
					<td>H0009</td>
					<td>abc@abc.com</td>
					<td>하암</td>
					<td>N</td>
					<td></td>
					<td>
						<button type="button" class="btn btn-primary btn-icon-split">신고내역</button> 
						<button type="button" class="btn btn-danger btn-icon-split">설정</button>
					</td>
				</tr>
				<tr align="center">
					<td>H0010</td>
					<td>vc@daum.net</td>
					<td>라스트</td>
					<td>N</td>
					<td></td>
					<td>
						<button type="button" class="btn btn-primary btn-icon-split">신고내역</button> 
						<button type="button" class="btn btn-danger btn-icon-split">설정</button>
					</td>
				</tr>
				<tr align="center">
					<td>H0011</td>
					<td>ll@abcde.com</td>
					<td>원</td>
					<td></td>
					<td>N</td>
					<td>
						<button type="button" class="btn btn-primary btn-icon-split">신고내역</button> 
						<button type="button" class="btn btn-danger btn-icon-split">설정</button>
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

</body>

</html>
