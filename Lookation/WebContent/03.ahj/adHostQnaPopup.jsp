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
<%@ include file="../includes/includes_admin.jsp" %>
<title>호스트답글상세보기</title>
<style type="text/css">
   .font-default
   {
   font-size: 0.8em;
   }
   
   .div-table{
   display: table;
   width: 100%;
   }
   
   .div-table-body {
   display: table-row-group;
   }
   

   .div-row {
   display: table-row;
   }

   .div-col {
   display: table-cell;
   padding: 3px 10px;
   }
   
   .div-col-half {
   display: table-cell;
   padding: 3px 10px;
   width: 50%;
   }
   
   .vertical-top{
   vertical-align: top;
   }
   
   #regReason{
   font-size: 1em;
   }
   
</style>
</head>
<body class="font-default">
<!-- 관리자용 호스트QnA답글 상세보기 팝업 -->

   <div class="container">
      <div class="row">
         <!-- 팝업 제목 -->
         <div class="w-100 text-center">
            <div class="text">
               <h4 class="title font-weight-bold mt-3">상세보기</h4>
            </div>
         </div>
      </div><!-- . End row -->
      
      <div class="row">
         <div class="col-md-12">
            <hr>
            <div class="div-table">
               <div class="div-table-body">
                  <div class="div-row">
                     <div class="div-col font-weight-bold">공간코드</div>
                     <div class="div-col"><a href="#">LOC000001</a></div>
                  </div>
                  
                  <div class="div-row">
                     <div class="div-col font-weight-bold">작성일자</div>
                     <div class="div-col">2020-12-25</div>
                  </div>
                  
                  <div class="div-row">
                     <div class="div-col font-weight-bold">이용자</div>
                     <div class="div-col">차은우</div>
                  </div>
                  
                  <div class="div-row">
                     <div class="div-col font-weight-bold">이용자리뷰</div>
                     <div class="div-col">불친절이컨셉인가요?진짜너무어이가없고기분이나쁩니다</div>
                  </div>
                  
                  <div class="div-row">
                     <div class="div-col font-weight-bold">호스트</div>
                     <div class="div-col">붐따호스트</div>
                  </div>
                  
                  <div class="div-row">
                     <div class="div-col font-weight-bold">호스트답글</div>
                     <div class="div-col">꼬우면오지마세용!</div>
                  </div>
             
               </div> 
            </div><!-- End .div-table -->
            <hr>
      </div><!-- End .row -->
      <hr>
      <div class="button-group div-table px-2">
      <div class="div-table-body">
         <div class="div-row">
            <div class="div-col-half"><button type="button" class="btn btn-secondary btn-block">닫기</button></div>
   
         </div>
      </div><!-- End .div-table-body-->
   </div><!-- End .div-table -->
   </div><!-- End .col-md-12  -->
      
      
      
</div>
</body>
</html>