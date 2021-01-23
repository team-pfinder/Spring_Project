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
<title>공간검수 목록</title>
<script type="text/javascript">
 
//팝업창
$(document).ready(function()
{
      $(".detailBtn").click(function()
      {
         // alert($(this).val());
         var popUrl = "inspectprocess.action?inspect_reg_code=" + $(this).val();
         var popOption = "width=500, height=700, resizable=no, scrollbars=yes, status=no";
         window.open(popUrl, "", popOption);
      });
});
</script>
<style type="text/css">
  form {
  width: 100%;
}
table {
  border-collapse:collapse;
  margin-bottom: 10px;
}
th, td {
  padding: 3px 10px;
}
.off-screen {
  display: none;
}

#li1, #li2, #li3, #li2 {
  width: 100%;
  text-align: center;
}

#li1 a, #li2 a, #li3 a, #li4 a {
	color: gray;
    text-align: center;
    display: inline-block;
    width: 40px;
    height: 40px;
    line-height: 40px;
    border-radius: 50%;
    border: 1px solid #e6e6e6;
}
#li1 a.active, #li2 a.active, #li3 a.active, #li4 a.active {
    background: #4e73df;
    color: #fff;
    border: 1px solid transparent;
} 

.empty {
height: 200px;

}

</style>


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
          <h1 class="h3 mb-2 text-gray-800">공간 검수</h1>
          <p class="mb-4"> 여기는 공간 검수 대기 중인 상태 리스트입니다</p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">공간 검수 목록</h6>
            </div>
            
          <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="products1">
                <form action="" id="setRows1">
					<input type="hidden" name="rowPerPage1" value="5">
				</form>
                  <thead>
				<tr align="center">
					<th>검수신청코드</th>
					<th>공간 명</th>
					<th>공간 유형</th>
					<th>검수처리</th>
				</tr>
			</thead>
               <tbody>
               	<c:forEach var="InspectLocationDTO" items="${list }">
               		<tr align="center">
								<td>${InspectLocationDTO.inspect_reg_code }</td>
				                <td>${InspectLocationDTO.loc_name }</td>
				                <td>${InspectLocationDTO.loc_type }</td>
			
								<td>
									<!-- 검수처리기능 -->
									<button type="button" class="btn btn-primary detailBtn"
											value="${InspectLocationDTO.inspect_reg_code }">검수처리</button> 
								</td>
							</tr>
               	
               	<%--
               		<c:choose>
                		<c:when test="${InspectLocationDTO.count == 0 }">  
		               		<tr align="center">
										<td>${InspectLocationDTO.inspect_reg_code }</td>
						                <td>${InspectLocationDTO.loc_name }</td>
						                <td>${InspectLocationDTO.loc_type }</td>
					
										<td>
											<!-- 검수처리기능 
											<button type="button" class="btn btn-primary detailBtn"
													value="${InspectLocationDTO.inspect_reg_code }">검수처리</button> 
										</td>
									</tr>
						</c:when> 	
						
						
						
               		
						
						<c:otherwise>
	                    	<tr style="display: none;">
	                    		<td>${InspectLocationDTO.inspect_reg_code }</td>
				                <td>${InspectLocationDTO.loc_name }</td>
				                <td>${InspectLocationDTO.loc_type }</td>
			
								<td>
									<!-- 검수처리기능 -->
									<button type="button" class="btn btn-primary detailBtn"
											value="${InspectLocationDTO.inspect_reg_code }">검수처리</button> 
								</td>
	                     		
					 -->
				
				<!-- ex)데이터 입력전 예시
				<tr align="center">
					<td><input type="checkbox" /></td>
					<td>2</td>

					<td>TP001235</td>

					<td>윌유메리미?</td>
					
					<td>브라이덜샤워</td>

					<td>대기</td>

					<td>
						검수처리기능
						<button type="button" class="btn btn-primary">검수처리</button> 
					</td>
				</tr> -->
				<%--
			
				</tr>
			 	</c:otherwise>
				</c:choose> --%>
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

  <!-- 관리자 로그아웃 모달  -->
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">로그아웃</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close" style="float: right;">
						<span aria-hidden="true" style="float: right;">×</span>
					</button>
				</div>
				<div class="modal-body">로그아웃 시 메인 페이지로 이동합니다.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="adminlogout.action">Logout</a>
				</div>
			</div>
		</div>
	</div>


<script type="text/javascript">

var $setRows = $('#setRows1');

$setRows.submit(function (e) {
  e.preventDefault();
  var rowPerPage = $('[name="rowPerPage1"]').val() * 1;// 1 을  곱하여 문자열을 숫자형로 변환

//    console.log(typeof rowPerPage);

  var zeroWarning = 'Sorry, but we cat\'t display "0" rows page. + \nPlease try again.'
  if (!rowPerPage) {
    alert(zeroWarning);
    return;
  }
  $('#li1').remove();
  var $products = $('#products1');

  $products.after("<div class='text-center' id='li1'>");


  var $tr = $($products).find('tbody tr');
  var rowTotals = $tr.length;
//  console.log(rowTotals);

  var pageTotal = Math.ceil(rowTotals/ rowPerPage);
  var i = 0;

  for (; i < pageTotal; i++) {
    $('<a href="#"></a>')
        .attr('rel', i)
        .html(i + 1)
        .appendTo('#li1');
  }

  $tr.addClass('off-screen')
      .slice(0, rowPerPage)
      .removeClass('off-screen');

  var $pagingLink = $('#li1 a');
  $pagingLink.on('click', function (evt) {
    evt.preventDefault();
    var $this = $(this);
    if ($this.hasClass('active')) {
      return;
    }
    $pagingLink.removeClass('active');
    $this.addClass('active');

    // 0 => 0(0*4), 4(0*4+4)
    // 1 => 4(1*4), 8(1*4+4)
    // 2 => 8(2*4), 12(2*4+4)
    // 시작 행 = 페이지 번호 * 페이지당 행수
    // 끝 행 = 시작 행 + 페이지당 행수

    var currPage = $this.attr('rel');
    var startItem = currPage * rowPerPage;
    var endItem = startItem + rowPerPage;

    $tr.css('opacity', '0.0')
        .addClass('off-screen')
        .slice(startItem, endItem)
        .removeClass('off-screen')
        .animate({opacity: 1}, 300);

  });

  $pagingLink.filter(':first').addClass('active');

});

$setRows.submit();	



</script>

</body>
</html>
