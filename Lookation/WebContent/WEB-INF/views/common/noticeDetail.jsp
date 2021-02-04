<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();

%>
<%
	String identify = request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
	System.out.println(identify);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
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



<section class="ftco-section ftco-degree-bg">
      <div class="container">
        <div class="row">
          <div class="col-lg-8 ftco-animate">
          <input type="hidden" value="${U_ndetailList.notice_code }" name="notice_code">
            <h1 class="mb-3">${U_ndetailList.notice_title }</h1>
            <div><a href="#"><span class="icon-person"></span> Admin</a></div>
            <div class="meta">${U_ndetailList.notice_date }</div>
            <hr>
            <br>
            <div style="white-space:pre;"><c:out value="${U_ndetailList.notice_content }" /></div>

         
            
            
            <div class="tag-widget post-tag-container mb-5 mt-5">
              <div class="tagcloud">
                <a href="#" class="tag-cloud-link">#${U_ndetailList.important_notice }</a>
                <!-- <a href="#" class="tag-cloud-link">Sport</a>
                <a href="#" class="tag-cloud-link">Tech</a>
                <a href="#" class="tag-cloud-link">Travel</a>
                 -->
              </div>
            </div>
              
        <!-- 버튼 -->  
            <div class="form-group">
               <button type="button" class="btn py-3 px-4 btn-primary" style="float: right;" onclick="location.href='notice.action?identify=${identify}'">목록으로</button>
            </div>

          

          </div> <!-- end class="col-lg-8 ftco-animate" -->
          
			</div>
		</div>
	</section>



	
<!-- footer 출력부분 -->
<div>
		<c:if test="${identify eq 'host' }">
			 <c:import url="${cp}/includes/footer_host.jsp"></c:import>
		</c:if>
		<c:if test="${identify eq 'member' }">
			 <c:import url="${cp}/includes/footer_user.jsp"></c:import>
		</c:if>
</div>

<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>

	<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>

</body>
</html>