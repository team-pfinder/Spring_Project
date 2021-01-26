<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String delete = request.getParameter("delete");
	pageContext.setAttribute("delete", delete);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>locationList.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<script type="text/javascript">
	$(function() {
		
		var del = "<c:out value='${delete}'/>";
		
		if(del=='fail')
			alert("공간 삭제 실패!\n이미 예약된 내역이 존재합니다.");

	});

	function del(code)
	{
		if(confirm("삭제하면 다시 복구가 불가능합니다. 정말 이 공간을 삭제하시겠습니까? "))
		{
			location.href = "deletelocation.action?loc_code=" + code;
		}
	}
	
	function mod(code)
	{
		location.href = "#";
	}
</script>

</head>
<body>


</head>
<body>
	<div>
        <c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=${info.nick }"></c:import>
	</div>
	
   <!-- 타이틀 -->
   <section class="hero-wrap hero-wrap-2"
      style="background-image: url(<%=cp%>/images/bg_3.jpg);"
      data-stellar-background-ratio="0.5">
      <div class="overlay"></div>
      
      <!-- 타이틀 내용 -->
      <div class="container">
         <div class="row no-gutters slider-text align-items-end">
            <div class="col-md-9 ftco-animate pb-5">
               <p class="breadcrumbs mb-2">
                  <span class="mr-2"> 
                  	<a href="index.html">Home 
                  		<i class="ion-ios-arrow-forward"></i>
                  	</a>
                  </span> 
                  <span>공간 관리 
                  	<i class="ion-ios-arrow-forward"></i>
                  </span>
               </p>
               <h1 class="mb-0 bread">공간 관리</h1>
            </div>
         </div>
      </div>
   </section>
   <!-- END 타이틀 -->

   <!-- 본문 -->
<!-- container 시작 -->

<div class="container">

   <br><br>
   
   <!-- Page Heading -->
   <h1 class="mb-2 text-gray-800">공간 관리</h1>
   <p class="mb-4"> 공간 관리페이지에서 내 공간을 관리해보세요. <a target="_blank" href="#">이전으로</a>.</p>
      
	<section class="ftco-section">
		<div class="container">
			<div class="row d-flex">
				
				
				<c:forEach var="location" items="${locList }">
						<div class="col-md-4 ftco-animate">
							<div class="blog-entry align-self-stretch">
								<a href="locationdetailhost.action?loc_code=${location.loc_code }" class="block-20 rounded"
									style="background-image: url('<%=cp%>/images/${location.thumbnail_url }');">
								</a>
								<!-- 이미지 클릭시 해당 공간상세 게시글페이지로..-->
								<div class="text mt-3 text-center">
									<h3 class="heading">
										<a href="#">${location.loc_name }</a>
										<!-- 공간명 클릭시 해당 공간상세 게시글페이지로..-->
									</h3>
									<hr>
									<div class="text mt-3 text-left">
										<span style="size: 10pt;">등록일</span><span> ${location.loc_reg_date }</span> 
										<c:if test="${location.inspect_type eq '검수승인'}">
											<span class="btn btn-success" style="float: right;">검수승인</span>
										</c:if>
										<c:if test="${location.inspect_type eq '검수반려'}">
											<span class="btn btn-danger" style="float: right;">검수반려</span>
										</c:if>
										<c:if test="${location.inspect_type eq '검수대기'}">
											<span class="btn btn-secondary" style="float: right;">검수대기</span>
										</c:if>
									</div>
									<br><br>
									<div class="text mt-3">
										<c:if test="${location.inspect_type eq '검수승인'}">
											<a href="packagemanager.action?loc_code=${location.loc_code}" 
											class="btn btn-primary" style="width:35%;">패키지 등록</a>
											<a href="packageapplyform.action?loc_code=${location.loc_code }" 
											class="btn btn-dark" style="width:35%;">예약 등록</a>
											<a href="javascript:void(0);" onclick="del('${location.loc_code}')" 
											class="btn btn-danger" style="width:20%;">삭제</a>
										</c:if>
										<c:if test="${location.inspect_type eq '검수반려'}">
										    <a href="javascript:void(0);" onclick="del(${location.loc_code})" 
											class="btn btn-danger" style="width:100%;">삭제</a>
										</c:if>
									</div>
									<hr>
								</div>
							</div>
						</div>
					</c:forEach>	
			</div>
		</div>
		<br><br>
		
		<!-- 새 공간 등록하기 -->
		<!-- 클릭 시 공간등록페이지(LocationBasicInfo.jsp)로 이동 -->
		<!-- <input type="submit" class="btn btn-info" value="새 공간 등록하기" 
			   style="width:100%; height: 100px; font-size: 14pt; font-weight: bold;">  -->
		<!-- onclick="func()" submit → LocationBasicInfo.jsp-->
		
		<a href="basicform.action" class="btn btn-info"
				style="width:100%; font-size: 16pt; font-family: 맑은 고딕; font-weight: bold;">
				새 공간 등록하기</a>
		
		
		<!-- 페이지 -->		<!-- ←논의 필요 --> 
		<!-- 일정개수(약 6개) 이상이면 등록한 공간등록날짜보다 더 이전것들을 보여준다. -->
		<!-- 한 페이지에서만 보여지는 경우는 비활성화 상태 -->
		
		<br><br><br><br>
	</section>
</div> <!-- container end -->

	<div>
		<c:import url="${cp}/includes/footer_host.jsp"></c:import>
		<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
	</div>
</body>
</html>