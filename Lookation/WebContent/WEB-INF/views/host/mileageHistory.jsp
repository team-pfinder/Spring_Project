<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마일리지 내역</title>
<style type="text/css">
   .nav-tabs .pill-1 .nav-link:not(.active)
   {
      background-color:#00043C !important;
      color:white !important;
   }

   /* active (faded) */
   .nav-tabs .pill-1 .nav-link {
      background-color: #FDBE34 !important;
      color: black !important;
      font-weight: bold !important;
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
   
#li1, #li2 {
  width: 100%;
  text-align: center;
}

#li1 a, #li2 a{
	color: gray;
    text-align: center;
    display: inline-block;
    width: 40px;
    height: 40px;
    line-height: 40px;
    border-radius: 50%;
    border: 1px solid #e6e6e6;
}
#li1 a.active, #li2 a.active{
    background: #fdbe34;
    color: #fff;
    border: 1px solid transparent;
}   
</style>

</head>
<body>
	<!-- header 출력부분 -->
	<div>
		<c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=${info.nick }"></c:import>
	</div>
	
	<!-- 타이틀 -->
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('<%=cp%>/images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"> <a href="hostmain.action">Home <i
								class="ion-ios-arrow-forward"></i></a>
						</span> <span class="mr-2"><a href="mypage.actionidentify=host">Mypage <i
								class="ion-ios-arrow-forward"></i></a></span> <span>Mileage History
							<i class="ion-ios-arrow-forward"></i>
						</span>
					</p>
					<h1 class="mb-0 bread">마일리지 내역</h1>
				</div>
			</div>
		</div>
	</section>
	<!-- END 타이틀 -->

	<!-- 본문 -->
	<section class="ftco-section ftco-degree-bg">
		<div class="container" style="margin-top: -80px">
			<div class="row">
				<!-- 사이드바 -->
				<!-- .col-md-2 -->
				<!-- include mypage_Sidebar(host).jsp -->
				<c:import url="${cp}/includes/mypage_Sidebar(host).jsp"></c:import>
				<!-- .col-md-10 -->
				<div class="col-lg-10 ftco-animate">
					<nav class="navbar navbar-default navbar-fixed-top">
						<div>
							<ul class="nav nav-tabs">
								<li class="nav-item pill-1"><a class="nav-link active" data-toggle="tab"
									href="#first">정산내역</a></li>
								<li class="nav-item pill-1"><a class="nav-link" data-toggle="tab"
									href="#second">환전내역</a></li>
							</ul>
						</div>
					</nav>

					<!-- 탭 대가리 -->
					<div class="tab-content" style="margin-top: 10px;">
						<!-- 첫번째 탭 -->
						<div class="tab-pane in active" id="first">
							<p class="my-1 ml-2"> <strong class="text-primary">${info.nick}</strong>님의 마일리지 충전 내역입니다.</p>
							<!-- 첫번째 카드 -->
							<div class="card shadow mb-4">						
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-default">정산내역</h6>
								</div>
								<!-- 카드바디 -->
								<div class="card-body">
									<table id="products1" class="table table-condensed table-hover" style="text-align:center">
										<form action="" id="setRows1">
										    <input type="hidden" name="rowPerPage1" value="5">
										</form>
										
										<thead>
											<tr>
												<th>번호</th>
												<th>공간명</th>
												<th>이용자</th>
												<th>예약일</th>
												<th>결제마일리지</th>
												<th>정산마일리지</th>
												<th>상태</th>
												<th>취소일</th>
											</tr>
										</thead>											
										
										<tbody>
											<c:if test="${fn:length(calHistory) == 0}">
											<tr>
												<th colspan="9">정산 내역이 존재하지 않습니다.</th>
											</tr>
											</c:if>											

											<c:forEach var="item" items="${calHistory}" varStatus="status">
											<tr>
												<th>${status.count }</th>
												<td>${item.lname }</td>
												<td>${item.nickname }</td>
												<td>${item.applyDate }</td>
												<td>${item.price }</td>
												<td>${item.calAmount }</td>
												<td>${item.status }</td>
												<c:if test="${item.refundDate eq null }">
													<td>${item.hostCancelDate }</td>
												</c:if>
												<c:if test="${item.hostCancelDate eq null }">
													<td>${item.refundDate }</td>
												</c:if>
											</tr>	
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>	

						<!-- 두번째 탭 -->
						<div class="tab-pane" id="second">
							<p class="my-1 ml-2"> <strong class="text-primary">${info.nick}</strong>님의 마일리지 충전 내역입니다.</p>
							<!-- 세번째 카드 -->
							<div class="card shadow mb-4">						
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-default">환전내역</h6>
								</div>
								<!-- 카드바디 -->
								<div class="card-body">
									<table id="products2" class="table table-condensed table-hover" style="text-align:center">
									<form action="" id="setRows2">
									    <input type="hidden" name="rowPerPage2" value="5">
									</form>	
									<thead>
										<tr>
											<th>번호</th>
											<th>계좌번호</th>
											<th>은행명</th>
											<th>환전금액</th>
											<th>환전처리일</th>
										</tr>
									</thead>
										<tbody>	
										
										<c:if test="${fn:length(exchangeHistory) == 0}">
										<tr>
											<th colspan="9">환전 내역이 존재하지 않습니다.</th>
										</tr>
										</c:if>											

										<c:forEach var="item" items="${exchangeHistory}" varStatus="status">
										<tr>
											<th>${status.count }</th>
											<td>${item.bankNumber }</td>
											<td>${item.bank }</td>
											<td>${item.amount }</td>
											<td>${item.exchangeDate }</td>
										</tr>	
										</c:forEach>
										</tbody>	
									</table>
								</div>
							</div>
						</div>	
					</div>

				</div>
				<!-- END <div class="col-lg-10 ftco-animate"> -->

			</div>
			<!-- END <div class="row"> -->
		</div>
		<!-- END container -->
	</section>
	<!-- .section -->

	<!-- footer.jsp -->
	<div>
		<c:import url="${cp}/includes/footer_host.jsp"></c:import>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
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

<script type="text/javascript">

var $setRows = $('#setRows2');

$setRows.submit(function (e) {
  e.preventDefault();
  var rowPerPage = $('[name="rowPerPage2"]').val() * 1;// 1 을  곱하여 문자열을 숫자형로 변환

//    console.log(typeof rowPerPage);

  var zeroWarning = 'Sorry, but we cat\'t display "0" rows page. + \nPlease try again.'
  if (!rowPerPage) {
    alert(zeroWarning);
    return;
  }
  $('#li2').remove();
  var $products = $('#products2');

  $products.after("<div class='text-center' id='li2'>");


  var $tr = $($products).find('tbody tr');
  var rowTotals = $tr.length;
//  console.log(rowTotals);

  var pageTotal = Math.ceil(rowTotals/ rowPerPage);
  var i = 0;

  for (; i < pageTotal; i++) {
    $('<a href="#"></a>')
        .attr('rel', i)
        .html(i + 1)
        .appendTo('#li2');
  }

  $tr.addClass('off-screen')
      .slice(0, rowPerPage)
      .removeClass('off-screen');

  var $pagingLink = $('#li2 a');
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