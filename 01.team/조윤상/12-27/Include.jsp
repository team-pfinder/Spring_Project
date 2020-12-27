<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
   request.setCharacterEncoding("UTF-8");
   String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main(User).jsp</title>

</head>
<body>

   <!-- 로고 및 검색 -->
   <!-- 
      #css

    -->
   <!--
      #참고 소스
      jqueryApp01/jqTest01.html
   -->
   <div>
      <table>
         <tr>
            <th><img src="<%=cp %>/images/logo.png" height="80px" /></th>
            <th><input type="text" style="width: 700px; height: 50px; font-size: 30px"/></th>
            <th><img src="<%=cp %>/images/search.png" height="40px" /></th>
         </tr>
      </table>
      <hr>
   </div>

   <!-- 메뉴창 및 로그인 -->
   <!-- 
      #css 
      메뉴창, 로그인창 디자인
   -->
   <!-- 
      #jquery
      메뉴 hover로 숨기기, 보이기
   --> 
   <div>
      <table>
         <tr>
            <td>
               <ol start="1" type="1" style="float:left;">
                  <li>메뉴
                     <ol type="a">
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                     </ol>
                  </li>
               </ol>
               <ol start="2" type="1" style="float:left;">
                  <li>메뉴
                     <ol type="a">
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                     </ol>
                  </li>
               </ol>         
               <ol start="3" type="1" style="float:left;">
                  <li>메뉴
                     <ol type="a">
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                     </ol>
                  </li>
               </ol>   
               <ol start="4" type="1" style="float:left;">
                  <li>메뉴
                     <ol type="a">
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                        <li>보조메뉴</li>
                     </ol>
                  </li>
               </ol>
            </td>
            <td></td>      
         </tr>
      </table>
   </div>
   
</body>
</html>