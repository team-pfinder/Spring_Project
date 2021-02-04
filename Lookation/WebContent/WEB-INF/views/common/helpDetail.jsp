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
<title>도움말</title>
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
              <h1 class="mb-0 bread">도움말</h1>
            </div>
       </div>
     </div>
</section>



<section class="ftco-section ftco-degree-bg">
      <div class="container">
        <div class="row">
          <div class="col-lg-8 ftco-animate">
          <input type="hidden" value="${U_detailList.help_code }" name="help_code">
            <h1 class="mb-3">${U_detailList.help_title }</h1>
            <div><a href="#"><span class="icon-person"></span> Admin</a></div>
            <div class="meta">${U_detailList.help_date }</div>
            <hr>
            <br>
             <div style="white-space:pre;"><c:out value="${U_detailList.help_content }" /></div>
       		
       		<!-- 이미지 있을 때만 이미지가 나오게 -->
       		<c:if test="${!empty Help_img.help_img_url}">
            <p><img src="<%=cp %>/images/${Help_img.help_img_url }" width="300px"></p>
            </c:if>

            
            <div class="tag-widget post-tag-container mb-5 mt-5">
              <div class="tagcloud">
                <a href="#" class="tag-cloud-link">#${U_detailList.board_type }</a>
              <!--  <a href="#" class="tag-cloud-link">Sport</a>
                <a href="#" class="tag-cloud-link">Tech</a>
                <a href="#" class="tag-cloud-link">Travel</a>
                 -->
              </div>
            </div>
              
            <!-- 버튼 -->  
            <div class="form-group">
               <button type="button" class="btn py-3 px-4 btn-primary" style="float: right;" onclick="location.href='help.action?identify=${identify}'">목록으로</button>
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
      <c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</div>

<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>

   <%-- <%@ include file="../01.ksb/foot.jsp" %> --%>

</body>
</html>