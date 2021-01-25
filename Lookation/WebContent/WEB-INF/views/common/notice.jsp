<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	String identify = request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<%-- <c:import url="${cp}/includes/header_user.jsp"></c:import> --%>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>

<style type="text/css">

.table th, .table td {
vertical-align: middle;
padding : 10px;
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
    background: #fdbe34;
    color: #fff;
    border: 1px solid transparent;
}

.empty {
height: 200px;

}

</style>

<!-- <script type="text/javascript">
$(document).ready(function()
{	
	$(".showBtn").click(function()
	{
		$(location).attr("href", "noticedetail.action?notice_code=" + $(this).val());
	});
});
</script> -->
</head>
<body>

<!-- header 출력부분 -->
<div>
		<c:if test="${identify eq 'host' }">
			 <c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=${info.nick }"></c:import>
		</c:if>
		<c:if test="${identify eq 'member' }">
			 <c:import url="${cp}/includes/header_user.jsp?result=${result }&nick=${info.nick }"></c:import>
		</c:if>
</div>


<section class="hero-wrap hero-wrap-2" style="background-image: url('<%=cp%>/images/bg_3.jpg');" data-stellar-background-ratio="0.5">
  	<div class="overlay"></div>
  	<div class="container">
    	<div class="row no-gutters slider-text align-items-end">
      		<div class="col-md-9 ftco-animate pb-5">
      			<p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.html">Home <i class="ion-ios-arrow-forward"></i></a></span> <span> My Page <i class="ion-ios-arrow-forward"></i></span></p>
        		<h1 class="mb-0 bread">공지사항</h1>
      		</div>
    	</div>
  	</div>
</section>

 

	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-12">
					<div class="wrapper">
						<div class="row no-gutters mb-5">


							<div class="card-body">
								<div class="table-responsive">
									<table class="table table-bordered text-center" id="products1">
										
										<form action="" id="setRows1">
										    <input type="hidden" name="rowPerPage1" value="5">
										</form>
											<colgroup>
												<col style="width: 20%">
												<col style="width: 40%">
												<col style="width: 20%">
												<col style="width: 20%">
											</colgroup>
												
												<thead>
													<tr>
														<th>유형</th>
														<th>제목</th>
														<th>작성자</th>
														<th>작성일자</th>
													</tr>
												</thead>
												
												<tbody>	
													<c:forEach var="NoticeDTO" items="${U_noticeList }">
													
													<tr align="center">
													
														<td>${NoticeDTO.important_notice}</td>
														<td><a href="noticedetail.action?identify=${identify}&notice_code=${NoticeDTO.notice_code}">${NoticeDTO.notice_title}</a></td>
														<td><span class="icon-person"></span>Admin</td>
														<td><span class="icon-calendar mr-1"></span>${NoticeDTO.notice_date }</td>
													</tr>
													
													</c:forEach>
												</tbody>
		
											</table>
										</div>
									</div>
								</div>
						
						<div class="row">
							<div class="col-md-3">
								<div class="dbox w-100 text-center">
									<div
										class="icon d-flex align-items-center justify-content-center">
										<span class="fa fa-map-marker"></span>
									</div>
									<div class="text">
										<p>
											<span>Address:</span> 198 West 21th Street, Suite 721 New
											York NY 10016
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="dbox w-100 text-center">
									<div
										class="icon d-flex align-items-center justify-content-center">
										<span class="fa fa-phone"></span>
									</div>
									<div class="text">
										<p>
											<span>Phone:</span> <a href="tel://1234567920">+ 1235
												2355 98</a>
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="dbox w-100 text-center">
									<div
										class="icon d-flex align-items-center justify-content-center">
										<span class="fa fa-paper-plane"></span>
									</div>
									<div class="text">
										<p>
											<span>Email:</span> <a href="mailto:info@yoursite.com">info@yoursite.com</a>
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="dbox w-100 text-center">
									<div
										class="icon d-flex align-items-center justify-content-center">
										<span class="fa fa-globe"></span>
									</div>
									<div class="text">
										<p>
											<span>Website</span> <a href="#">yoursite.com</a>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
<script type="text/javascript">

$(document).ready(function()
		{	
			$(".showBtn").click(function()
			{
				$(location).attr("href", "noticedetail.action?notice_code=" + $(this).val());
			});
		});

var $setRows = $('#setRows1');

$setRows.submit(function (e) {
  e.preventDefault();
  var rowPerPage = $('[name="rowPerPage1"]').val() * 1;// 1 을  곱하여 문자열을 숫자형로 변환

//console.log(typeof rowPerPage);

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
<!-- footer 출력부분 -->
<div>
		<c:if test="${identify eq 'host' }">
			 <c:import url="${cp}/includes/footer_host.jsp"></c:import>
		</c:if>
		<c:if test="${identify eq 'member' }">
			 <c:import url="${cp}/includes/footer_user.jsp"></c:import>
		</c:if>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</div>


	<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>
<%-- <c:import url="${cp}/includes/footer_user.jsp"></c:import> --%>
<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</body>
</html>