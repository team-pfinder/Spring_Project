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

<c:import url="${cp }/includes/includes_admin.jsp"></c:import>
<title>reportList.jsp</title>

<style type="text/css">

	
	ul.tabs
	{
		margin: 0px;
		padding: 0px;
		list-style: none;
	}
	ul.tabs li
	{
		background: none;
		color: #222;
		display: inline-block;
		padding: 10px 15px;
		cursor: pointer;
	}
	
	ul.tabs li.current
	{
		background: #ededed;
		color: #222;
	}
	
	.tab-content
	{
		display: none;
		background: #ededed;
		padding: 15px;
	}
	
	.tab-content.current
	{
		display: inherit;
	}
	
	table{
	width: 100%;
	table-layout: fixed;
	}
	
	table thead tr{
	width: 100%;
	}
	
	
	td {
	   text-overflow : ellipsis; 
	   overflow : hidden; 
	   white-space : nowrap;
	   block: inline-block;
	}
	
	tr > td > a {
	   color: gray;
	}
	
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

<script type="text/javascript">

	$(document).ready(function()
	{
		// 공간 신고 --------------------------------------------
		
		// 신고처리 전 상세보기 버튼을 클릭했을 때
		$(".locDetailBtn0").click(function()
		{
			// alert($(this).val());
			var popUrl = "locpopup0.action?loc_report_code=" + $(this).val();
			var popOption = "width=500, height=700, resizable=no, scrollbars=yes, status=no";
			window.open(popUrl, "", popOption);
		});
		
		// 신고처리 후 상세보기 버튼을 클릭했을 때
		$(".locDetailBtn1").click(function()
		{
			// alert($(this).val());
			var popUrl = "locpopup1.action?loc_report_code=" + $(this).val();
			var popOption = "width=500, height=700, resizable=no, scrollbars=yes, status=no";
			window.open(popUrl, "", popOption);
		})
		
		// 신고처리 버튼 클릭 (인정 / 반려)
		$(".locReportOK").click(function()	//-- 신고인정
		{
			// alert($(this).val());
			$(location).attr("href", "locreportproc.action?loc_report_code=" + $(this).val() + "&report_proc_type_code=RPPT000001");
		});
		$(".locReportNO").click(function()	//-- 신고반려
		{
			// alert($(this).val());
			$(location).attr("href", "locreportproc.action?loc_report_code=" + $(this).val() + "&report_proc_type_code=RPPT000002");
		})
		
		// 블랙리스트 추가 버튼 클릭
		$(".hostBlack").click(function()
		{
			// alert($(this).val());
			var popUrl = "hostblackform.action?host_email=" + $(this).val();
			var popOption = "width=500, height=500, resizable=no, scrollbars=yes, status=no";
			window.open(popUrl, "", popOption);
		})
		
		// -------------------------------------------- 공간 신고
		
		// 예약신고 관리 페이지로
		$(".bookReport").click(function()
		{
			$(location).attr("href", "bookreportlist.action");
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

                <!-- Header --> <!-- 경로 변경 필요 -->
				<c:import url="${cp }/includes/header.jsp"></c:import>

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">신고처리</h1>
                    <p class="mb-4">이용자 혹은 호스트가 신고한 내역을 확인하고 처리할 수 있다.</p>
					
		                    <!-- DataTales Example -->
		                    <div class="card shadow mb-4">
		                        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					              <h6 class="m-0 font-weight-bold text-primary">공간신고처리</h6>
					          
					              <button type="button" class="btn btn-secondary bookReport">예약신고관리</button>
					            </div>
		                       <div class="card-body">
				             <div class="table-responsive">
				            <table class="table table-bordered text-center" id="products1">
				               
				               <form action="" id="setRows1">
				                   <input type="hidden" name="rowPerPage1" value="5">
				               </form>
				                  <colgroup>
				                     <col style="width: 10%">
				                     <col style="width: 15%">
				                     <col style="width: 10%">
				                     <col style="width: 10%">
				                     <col style="width: 20%">
				                     <col style="width: 20%">
				                     <col style="width: 20%">
				                  </colgroup>
		                                	<thead>
			                                    <tr>
													<th>공간코드</th>
													<th>신고유형</th>
													<th>피신고자정보</th>
													<th>신고자정보</th>
													<th>신고일자</th>
													<th>상세보기</th>
													<th>신고처리</th>
												</tr>
											</thead>
											
											<tbody>
												<c:forEach var="reportDTO" items="${locBasicList }">
													<tr>
														<td>${reportDTO.loc_code }</td>
														<td>${reportDTO.loc_report_type }</td>
														<td>${reportDTO.host_nickname }</td>
														<td>${reportDTO.member_nickname }</td>
														<td>${reportDTO.loc_report_date }</td>
														<c:choose>
															<c:when test="${reportDTO.result_count == 0 }">
																<td>
																	<button type="button" class="btn btn-primary locDetailBtn0"
																	value="${reportDTO.loc_report_code }">상세보기</button>
																</td>
																<td>
																	<button type="button" class="btn btn-danger locReportOK"
																	value="${reportDTO.loc_report_code }">인정</button>
																	<button type="button" class="btn btn-secondary locReportNO"
																	value="${reportDTO.loc_report_code }">반려</button>
																</td>
															</c:when>
															<c:otherwise>
																<td>
																	<button type="button" class="btn btn-primary locDetailBtn1"
																	value="${reportDTO.loc_report_code }">처리결과보기</button>
																</td>
																<c:choose>
																	<c:when test="${reportDTO.black_count ==0 }">
																		<td>
																			<button type="button" class="btn btn-danger hostBlack"
																			value="${reportDTO.host_email }">블랙리스트 추가</button>
																		</td>
																	</c:when>
																	<c:otherwise>
																	<td>
																		블랙리스트처리됨
																	</td>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</tr>
												</c:forEach>
											</tbody>
		                                </table>
		                            </div>
		                        </div>
		                </div>
		                <!-- /.container-fluid -->
                </div>

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