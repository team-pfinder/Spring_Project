<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약리스트확인페이지.jsp</title>
<c:import url="${cp}/includes/header_user.jsp?result=${result }&nick=${info.nick }"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">
	/* btn */
	.btn-gon {
		color: #FFF;
		background-color: #00043c;
	}
	
	.btn-gon:hover {
		color: #FFF;
		background-color: #00043cdb;
	}
	
	.text-gon {
		color: #00043c;
	}
	
	
	   form {
  width: 100%;
}
table {
  border-collapse:collapse;
  /*margin-bottom: 10px;*/
}
th, td {
  padding: 3px 10px;
}

.table th, .table td {
vertical-align: middle;
}

.off-screen {
  display: none;
}
#li1 {
  width: 100%;
  text-align: center;
}

#li1 a {
	color: gray;
    text-align: center;
    display: inline-block;
    width: 40px;
    height: 40px;
    line-height: 40px;
    border-radius: 50%;
    border: 1px solid #e6e6e6;
    margin-right: 10px;
}
#li1 a.active {
    background: #fdbe34;
    color: #fff;
    border: 1px solid transparent;
}

.empty {
height: 300px;
}

.book_date {
	font-size: 0.8em;
	color : #6c757d73;
}

p {
margin-bottom: 0;
}
</style>
<script type="text/javascript">

    $(document).ready(function()
	{
    	// 예약 취소 팝업
    	$(".popCancel").click(function()
    	{
    		var url = "bookcancel.action?book_code=" + $(this).val();
    		var option = "width=450, height=500, resizable=no, scrollbars=yes, status=no";
    		window.open(url, "", option);
    	}); 
    	
    	
    	// 예약 상세보기 팝업
    	$(".popDetails").click(function()
    	{
    		var url = "bookdetails.action?book_code=" + $(this).val();
    		var option = "width=850, height=500, resizable=no, scrollbars=yes, status=no";
    		window.open(url, "", option);
    	}); 
    	
	})
	
</script>

</head>
<body>
<section class="hero-wrap hero-wrap-2" style="background-image: url('<%=cp%>/images/bg_3.jpg');" data-stellar-background-ratio="0.5">
  	<div class="overlay"></div>
  	<div class="container">
    	<div class="row no-gutters slider-text align-items-end">
      		<div class="col-md-9 ftco-animate pb-5">
      			<p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.html">Home <i class="ion-ios-arrow-forward"></i></a></span> <span> My Page <i class="ion-ios-arrow-forward"></i></span></p>
        		<h1 class="mb-0 bread">예약 리스트</h1>
      		</div>
    	</div>
  	</div>
</section>

<div class="container my-5">
	<div class="row">
		<!-- include mypage_Sidebar.jsp -->
		<c:import url="${cp}/includes/mypage_Sidebar(user).jsp"></c:import>
		<%-- <%@ include file="../../../includes/mypage_Sidebar(user).jsp"%> --%>
	
		<div class="col-lg-10 col-md-10">
			<!-- Page Heading -->
			<h1 class="mb-2 text-gray-800">예약 리스트</h1>
			<p class="my-1 ml-2"> <strong class="text-primary">${nick}</strong>님의 예약 내역입니다.</p>
			<p class="mb-4 mt-1 ml-2">예약현황 클릭시 예약 상세내역을 확인할 수 있습니다.</p>
				
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-default">예약 리스트</h6>
				</div>
				<div class="card-body">
					<div class="table-flex">
						<form action="" id="setRows1">
						    <input type="hidden" name="rowPerPage1" value="10">
						</form>
						<table class="table" id="products1" style="word-break:break-all;">
							<thead>
								<tr class="text-center">
									<th>예약일자</th>
									<th>예약내용/결제일자</th>
									<th>공간명</th>
									<th>예약현황</th>
									<th>예약관리</th> 
								</tr>
							</thead>
							<tbody class="text-center">
								<!-- 날짜 변환 -->
								<jsp:useBean id="now" class="java.util.Date" scope="page"/>
								<fmt:formatDate var="today" value="${now}" scope="page" pattern="yyyy-MM-dd"/>  
								
								<c:if test="${not empty bookList }">
									<c:forEach var="book" items="${bookList }">
										<fmt:parseDate var="pdate" value="${book.apply_date}" pattern="yy-MM-dd HH:mm:ss" />
										<fmt:formatDate var="fdate" value="${pdate}" pattern="yyyy-MM-dd" />
										<%-- <span>today : ${today } <br> fdate : ${fdate }<br></span> --%>
										<tr>
											<td>
											<p>
											${fdate}</p>
											</td>
											
											<c:choose>
												<c:when test="${book.package_end >= 24}">
													<td>
														${book.package_start }:00 ~ 익일 ${book.package_end - 24}:00
													 (${book.book_hour}시간)
													<p class="book_date">${book.book_date }</p></td>
												</c:when>
												
												<c:otherwise>
													<td>
														${book.package_start }:00 ~ ${book.package_end }:00
													 (${book.book_hour}시간)
													<p class="book_date">${book.book_date }</p></td>
												</c:otherwise>
											</c:choose>
											
											<td>${book.loc_name }</td>
											
											
											
<%-- 											<c:choose>
											<c:when test="${fdate <= today} ">
												<td>이용완료</td>
												<td>
													<button type="button" value="${book.book_code}"
													class="btn py-1 px-1 mb-0 btn-danger border-0 rounded popCancel"
													disabled="disabled">
													취소
													</button>
													
											</c:when>
											
											<c:when test="${fdate > today} ">
												<td>예약완료</td>
												<td>
													<button type="button" value="${book.book_code}"
													class="btn py-1 px-1 mb-0 btn-danger border-0 rounded popCancel"
													disabled="disabled">
													취소
													</button>
											</c:when>
											
											<c:when test="${book.member_cancel eq 1 || book.host_cancel eq 1}">
												<td class="text-danger">예약취소</td>
												<td>
													<button type="button" value="${book.book_code}"
													class="btn py-1 px-1 mb-0 btn-danger border-0 rounded popCancel"
													disabled="disabled">
													취소
													</button>
													<button type="button" value="${book.book_code}" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded popDetails" >
													상세보기
													</button>
												</td>
											</c:when>
											
										 	<c:otherwise>
												<td class="text-gon">그외</td>
												<td>
													<button type="button" value="${book.book_code}"
													class="btn py-1 px-1 mb-0 btn-danger border-0 rounded popCancel"
													>
													취소
													</button>
													<button type="button" value="${book.book_code}" class="btn py-1 px-1 mb-0 btn-gon border-0 rounded popDetails" >
													상세보기
													</button>
												</td>
											</c:otherwise> 
											
										</c:choose> --%>
											<c:if test="${book.checkbook == -1}">
												<td class="text-danger">취소완료${book.checkbook}</td>	<!-- -1 취소완료 -->
												<td>
													<button type="button" value="${book.book_code}"
													class="btn py-1 px-1 mb-0 btn-warning border-0 rounded">
													신고</button>
													<button type="button" value="${book.book_code}"
														class="btn py-1 px-1 mb-0 btn-danger border-0 rounded popCancel"
														disabled="disabled">
														취소</button>
													<button type="button" value="${book.book_code}"
														class="btn py-1 px-1 mb-0 btn-gon border-0 rounded popDetails">
														상세보기</button>
												</td>
											</c:if>
											
											<c:if
												test="${book.checkbook == 0 }">			<!-- 0 : 이용완료 -->
												<td class="text-dark">이용완료${book.checkbook}</td>
												<td>
													<button type="button" value="${book.book_code}"
													class="btn py-1 px-1 mb-0 btn-warning border-0 rounded">
													신고</button>
													<button type="button" value="${book.book_code}"
														class="btn py-1 px-1 mb-0 btn-danger border-0 rounded popCancel"
														>
														취소</button>
													<button type="button" value="${book.book_code}"
														class="btn py-1 px-1 mb-0 btn-gon border-0 rounded popDetails">
														상세보기</button>
												</td>
											</c:if>
											
											<c:if test="${book.checkbook == 1 }">		<!-- 1 : 예약완료 -->
												<td class="text-gon">예약완료${book.checkbook}</td>
												<td>
													<button type="button" value="${book.book_code}"
													class="btn py-1 px-1 mb-0 btn-warning border-0 rounded">
													신고</button>
													<button type="button" value="${book.book_code}"
														class="btn py-1 px-1 mb-0 btn-danger border-0 rounded popCancel"
														>취소</button>
													<button type="button" value="${book.book_code}"
														class="btn py-1 px-1 mb-0 btn-gon border-0 rounded popDetails">
														상세보기</button>
												</td>
											</c:if>
											
											</tr>
										</c:forEach><!-- .c:forEach 끝 -->
									</c:if>
									
									<c:if test="${empty bookList }">
										<tr class="py-5 my-5">
											<td colspan="5" class="py-5 my-5"> 예약이 없습니다. </td>
										</tr>
										
									</c:if>
							</tbody>
						</table>
					</div><!-- End .table-flex -->
				</div><!-- End .card-body -->
			</div><!-- End .card shadow mb-4 -->
			<div class="empty"></div>
		</div><!-- End .col-md-10 -->
	</div><!-- End .row -->
	
	

</div><!-- End .container -->

<script type="text/javascript">

var $setRows = $('#setRows1');

$setRows.submit(function (e) {
  e.preventDefault();
  var rowPerPage = $('[name="rowPerPage1"]').val() * 1;// 1 을  곱하여 문자열을 숫자형로 변환

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


<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<div>
	<c:import url="${cp}/includes/footer_user.jsp"></c:import>
</div>
</body>
</html>