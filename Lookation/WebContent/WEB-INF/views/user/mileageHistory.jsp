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
#li1, #li2, #li3, #li4 {
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


</style>


</head>
<body>
<!-- header 출력부분 -->
<div>
	<c:import url="${cp}/includes/header_user.jsp?result=${result }&nick=${info.nick }"></c:import>
</div>

	
	<!-- 타이틀 -->
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('${pageContext.request.contextPath}/images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"> <a href="membermain.action">Home <i
								class="ion-ios-arrow-forward"></i></a>
						</span> <span class="mr-2"><a href="mypage.actionidentify=member">Mypage <i
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
				<!-- include mypage_Sidebar.jsp -->
				<c:import url="${cp}/includes/mypage_Sidebar(user).jsp"></c:import>
				<!-- END 사이드바 -->

				<div class="col-lg-10 ftco-animate">
					<nav class="navbar navbar-default navbar-fixed-top">
						<div>
							<ul class="nav nav-tabs">
								<li class="nav-item pill-1"><a class="nav-link active"
									data-toggle="tab" href="#first">사용내역</a></li>
								<li class="nav-item pill-1"><a class="nav-link"
									data-toggle="tab" href="#second">충전내역</a></li>
								<li class="nav-item pill-1"><a class="nav-link"
									data-toggle="tab" href="#third">환전내역</a></li>
								<li class="nav-item pill-1"><a class="nav-link"
									data-toggle="tab" href="#fourth">환불내역</a></li>
							</ul>
						</div>
					</nav>

					<!-- 탭 대가리 -->
					<div class="tab-content" style="margin-top: 10px;">
						<!-- 첫번째 탭 -->
						<div class="tab-pane in active" id="first">
							<p>조윤상님의 마일리지 사용 내역입니다.</p>
							<!-- 첫번째 카드 -->
							<div class="card shadow mb-4">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-default">사용내역</h6>
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
												<th>사용 마일리지</th>
												<th>사용일</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${fn:length(payHistory) == 0}">
											<tr>
												<th colspan="5">사용 내역이 존재하지 않습니다.</th>
											</tr>
											</c:if>
											<c:forEach var="item" items="${payHistory}" varStatus="status">
											<tr>
												<th>${status.count}</th>
												<td>${item.name}</td>
												<td>${item.price}</td>
												<td>${item.payDate}</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<!-- 두번째 탭 -->
						<div class="tab-pane" id="second">
							<p>조윤상님의 마일리지 충전 내역입니다.</p>
							<!-- 두번째 카드 -->
							<div class="card shadow mb-4">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-default">충전내역</h6>
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
												<th>금액</th>
												<th>충전신청일</th>
												<th>상태</th>
												<th>충전처리일</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${fn:length(loadHistory) == 0}">
											<tr>
												<th colspan="7">충전 내역이 존재하지 않습니다.</th>
											</tr>
											</c:if>
											<c:forEach var="item" items="${loadHistory}" varStatus="status">
											<tr>
												<th>${status.count}</th>
												<td>${item.bankNumber}</td>
												<td>${item.bank}</td>
												<td>${item.amount}</td>
												<td>${item.regDate}</td>
												<c:if test="${empty item.loadType}">
													<td>충전대기</td>
												</c:if>
												<c:if test="${not empty item.loadType}">
													<td>${item.loadType}</td>
												</c:if>
												<c:if test="${empty item.procDate}">
													<td>-</td>
												</c:if>
												<c:if test="${not empty item.procDate}">
													<td>${item.procDate}</td>
												</c:if>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>

						</div>
						<!-- 세번째 탭 -->
						<div class="tab-pane" id="third">
							<p>조윤상님의 마일리지 환전 내역입니다.</p>
							<!-- 세번째 카드 -->
							<div class="card shadow mb-4">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-default">환전내역</h6>
								</div>
								<!-- 카드바디 -->
								<div class="card-body">
									<table id="products3" class="table table-condensed table-hover" style="text-align:center">
										<form action="" id="setRows3">
										    <input type="hidden" name="rowPerPage3" value="5">
										</form>
										
										<thead>
											<tr>
												<th>번호</th>
												<th>계좌번호</th>
												<th>은행명</th>
												<th>금액</th>
												<th>환전처리일</th>
											</tr>
										</thead>
										
										<tbody>	
											<c:if test="${fn:length(exchangeHistory) == 0}">
											<tr>
												<th colspan="7">환전 내역이 존재하지 않습니다.</th>
											</tr>
											</c:if>
											<c:forEach var="item" items="${exchangeHistory}" varStatus="status">
											<tr>
												<th>${status.count}</th>
												<td>${item.bankNumber}</td>
												<td>${item.bank}</td>
												<td>${item.amount}</td>
												<td>${item.exchangeDate}</td>
											</tr>
											</c:forEach>
										</tbody>	
									</table>
								</div>
							</div>
							
						</div>
						<!-- 네번째 탭 -->
						<div class="tab-pane" id="fourth">
							<p>조윤상님의 환불 내역입니다.</p>
							<!-- 네번째 카드 -->
							<div class="card shadow mb-4">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-default">환불내역</h6>
								</div>
								<!-- 카드바디 -->
								<div class="card-body">
									<table id="products4" class="table table-condensed table-hover" style="text-align:center">
										<form action="" id="setRows4">
									        <input type="hidden" name="rowPerPage4" value="5">
									    </form>		
									    									
									    <thead>
											<tr>
												<th>번호</th>
												<th>취소유형</th>
												<th>공간</th>
												<th>예약일</th>
												<th>결제금액</th>
												<th>환불금액</th>
												<th>환불처리일</th>
											</tr>
										</thead>
										
										<tbody>	
											<c:if test="${fn:length(refundHistory) == 0}">
											<tr>
												<th colspan="7">환불 내역이 존재하지 않습니다.</th>
											</tr>
											</c:if>
											<c:forEach var="item" items="${refundHistory}" varStatus="status">
											<tr>
												<th>${status.count}</th>
												<td>${item.type}</td>
												<td>${item.lname}</td>
												<td>${item.applyDate}</td>
												<td>${item.price}</td>
												<td>${item.cancelDate}</td>
												<td>${item.refundAmount}</td>
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
		<c:import url="${cp}/includes/footer_user.jsp"></c:import>
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
<script type="text/javascript">

var $setRows = $('#setRows3');

$setRows.submit(function (e) {
  e.preventDefault();
  var rowPerPage = $('[name="rowPerPage3"]').val() * 1;// 1 을  곱하여 문자열을 숫자형로 변환

//    console.log(typeof rowPerPage);

  var zeroWarning = 'Sorry, but we cat\'t display "0" rows page. + \nPlease try again.'
  if (!rowPerPage) {
    alert(zeroWarning);
    return;
  }
  $('#li3').remove();
  var $products = $('#products3');

  $products.after("<div class='text-center' id='li3'>");


  var $tr = $($products).find('tbody tr');
  var rowTotals = $tr.length;
//  console.log(rowTotals);

  var pageTotal = Math.ceil(rowTotals/ rowPerPage);
  var i = 0;

  for (; i < pageTotal; i++) {
    $('<a href="#"></a>')
        .attr('rel', i)
        .html(i + 1)
        .appendTo('#li3');
  }

  $tr.addClass('off-screen')
      .slice(0, rowPerPage)
      .removeClass('off-screen');

  var $pagingLink = $('#li3 a');
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

var $setRows = $('#setRows4');

$setRows.submit(function (e) {
  e.preventDefault();
  var rowPerPage = $('[name="rowPerPage4"]').val() * 1;// 1 을  곱하여 문자열을 숫자형로 변환

//    console.log(typeof rowPerPage);

  var zeroWarning = 'Sorry, but we cat\'t display "0" rows page. + \nPlease try again.'
  if (!rowPerPage) {
    alert(zeroWarning);
    return;
  }
  $('#li4').remove();
  var $products = $('#products4');

  $products.after("<div class='text-center' id='li4'>");


  var $tr = $($products).find('tbody tr');
  var rowTotals = $tr.length;
//  console.log(rowTotals);

  var pageTotal = Math.ceil(rowTotals/ rowPerPage);
  var i = 0;

  for (; i < pageTotal; i++) {
    $('<a href="#"></a>')
        .attr('rel', i)
        .html(i + 1)
        .appendTo('#li4');
  }

  $tr.addClass('off-screen')
      .slice(0, rowPerPage)
      .removeClass('off-screen');

  var $pagingLink = $('#li4 a');
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