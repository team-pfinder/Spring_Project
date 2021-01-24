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
<title>도움말 목록</title>
<script type="text/javascript">
  /*
	//체크박스 전체선택 전체 해제
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
  */
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
          <h1 class="h3 mb-2 text-gray-800">도움말</h1>
          <p class="mb-4"> 여기는 도움말 목록입니다</p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
              <h6 class="m-0 font-weight-bold text-primary">도움말 목록</h6>
          
              <button type="button" class="btn btn-secondary" onclick="location.href='helpinsertform.action'" >글 작성</button>
              </div>
            
            
          <div class="card-body">
             <div class="table-responsive">
				<table class="table table-bordered text-center" id="products1">
					
					<form action="" id="setRows1">
					    <input type="hidden" name="rowPerPage1" value="5">
					</form>
						<colgroup>
							<col style="width: 15%">
							<col style="width: 15%">
							<col style="width: 30%">
							<col style="width: 30%">
							<col style="width: 20%">
							<col style="width: 20%">
						</colgroup>
               	<thead>
					<tr align="center">
						<th>도움말 코드</th>
						<th>카테고리</th>
						<th>제목</th>
						<th>내용</th>
						<th>작성일자</th>
						<th>수정/삭제</th>
					</tr>
				</thead>
				
              <tbody>
               <c:forEach var="HelpDTO" items="${A_helpList }">
				<tr align="center">

					 <td>${HelpDTO.help_code }</td>
					
					<td>${HelpDTO.board_type}</td>

					<td>${HelpDTO.help_title }</td>

					<td>${HelpDTO.help_content }</td>
					
					<td>${HelpDTO.help_date }</td>

					<td>
						<!-- 수정기능 -->
						<button type="button" class="btn btn-warning" value="${HelpDTO.help_code}" 
						onclick="location.href='../actions/helpupdateform.action?help_code=' + this.value;">수정</button>			 											 
						<!-- 삭제기능 -->
						<button type="button" class="btn btn-danger " value="${HelpDTO.help_code}" 
						onclick="location.href='../actions/helpdelete.action?help_code=' + this.value;">삭제</button>
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
