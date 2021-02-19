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
<title>블라인드 목록</title>

<script type="text/javascript">

	$(document).ready(function()
	{
		$(".blindRemove").click(function()
		{
			if(confirm("정말로 블라인드 해제하시겠습니까?"))
			{
				$(location).attr("href", "blindremove.action?loc_blind_code=" + $(this).val());
			}
		});
	});

</script>

<style type="text/css">

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
                    <h1 class="h3 mb-2 text-gray-800">블라인드 관리</h1>
                    <p class="mb-4">블라인드 처리된 공간 게시물들을 조회할 수 있다. 블라인드 해제 처리도 가능하다.
                    하지만, 권장하지 않는 기능이므로 왠만하면 사용하지 않는 것이 좋다.</p>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">블라인드 리스트</h6>
                        </div>
                       <div class="card-body">
				             <div class="table-responsive">
				            <table class="table table-bordered text-center" id="products1">
				               
				               <form action="" id="setRows1">
				                   <input type="hidden" name="rowPerPage1" value="5">
				               </form>
				                  <colgroup>
				                     <col style="width: 10%">
				                     <col style="width: 10%">
				                     <col style="width: 20%">
				                     <col style="width: 15%">
				                     <col style="width: 20%">
				                     <col style="width: 20%">
				                     <col style="width: 20%">
				                     <col style="width: 20%">
				                  </colgroup>
                                    <thead>
	                                    <tr>
											<th>공간코드</th>
											<th>호스트</th>
											<th>호스트이메일</th>
											<th>공간분류</th>
											<th>공간명</th>
											<th>블라인드처리일자</th>
											<th>블라인드처리사유</th>
											<th>블라인드 해제</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="dto" items="${list }">
											<tr>
												<td>${dto.loc_code }</td>
												<td>${dto.host_nickname }</td>
												<td>${dto.host_email }</td>
												<td>${dto.loc_type }</td>
												<td>${dto.loc_name }</td>
												<td>${dto.loc_blind_date }</td>
												<td>${dto.loc_blind_reason }</td>
												<td>
													<button type="button" class="btn btn-danger blindRemove"
													value="${dto.loc_blind_code }">블라인드해제</button>
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