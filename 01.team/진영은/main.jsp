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
            <td>
               <button>로그인</button>
               <button>회원가입</button>
            </td>
         </tr>
      </table>
   </div>
   
   <!-- 오늘의 공간 -->
   <!-- 
      #css 
   -->
   <div>
      <table>
         <caption>오늘의 공간</caption>
         <tr>
            <th>1번 게시물</th>
            <th>2번 게시물</th>
            <th>3번 게시물</th>
         </tr>
         <tr>
            <td>test</td>
            <td>test</td>
            <td>test</td>
         </tr>
      </table>
   </div>
   
   <!-- 이용자 리뷰 -->
   <!-- 
      #css 
   
   -->
   <!-- 
      #jquery
      더보기 탭 => 클릭시 무한 스크롤 추가 
   -->
   <!--
      #참고 소스
      jqueryApp01/jqTest06.html
    -->
   <div>
      <table>
         <caption>이용자 리뷰</caption>
         <tr>
            <th>1번 리뷰</th>
            <th>2번 리뷰</th>
            <th>3번 리뷰</th>
         </tr>
         <tr>
            <td>test</td>
            <td>test</td>
            <td>test</td>
         </tr>
         
      </table>
   </div>
   
   <!-- 나머지 -->
   <!-- 
      #css
      
    -->
   <div>
      <hr>
      <button>이용약관</button>
      <button>개인정보처리방침</button>
      <button>운영정책</button>

   </div>
</body>
</html>















