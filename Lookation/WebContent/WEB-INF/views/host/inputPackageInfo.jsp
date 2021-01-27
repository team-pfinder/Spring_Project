<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
   request.setCharacterEncoding("UTF-8");
   String cp = request.getContextPath();
   
   String loc_code = request.getParameter("loc_code");
   pageContext.setAttribute("loc_code", loc_code);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>inputPackageInfo.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<script type="text/javascript">


   $(function() {

      // enter submit 방지
      document.addEventListener('keydown', function(event) {
         
         if (event.keyCode === 13) {
             event.preventDefault();
           };
              
      }, true);
      
      // 함수 호출
      setInputLength($('#inputPackageName'), '패키지명', 2, 20);
      setInputPrice($('#locationPackagePrice'), '패키지가격', 1000, 1000000);
      
      $('#inputPackageInfoSave').click(function() {
         
         //alert(set($('#locationPackagePrice')));
         
         var f = $('#inputPackageInfo');
         
         var tName = $('#inputPackageName').val();
         var tStart = $('#locationPackageStart').val();
         var tEnd = $('#locationPackageEnd').val();
         var tPrice = $('#locationPackagePrice').val();
         
         if (tName == '' || tStart == '' || tEnd == '' || tPrice == '') 
         {
            alert("필수 입력사항을 모두 입력해 주세요.");
         }
         else if (tName.length < 2 || tName.length > 20) {
            
            alert("패키지명은 2자~20자로 입력해야합니다.");
            $("#inputPackageName").focus();
         }
         else if (parseInt(tEnd) <= parseInt(tStart))
         {
            alert("패키지 이용시간 설정을 확인하세요.");
            $("#locationPackageStart").focus();
         }
         else if (parseInt(tEnd) > parseInt(tStart)
               && parseInt(tEnd) - parseInt(tStart) < 3)
         {
            alert("패키지 이용시간은 최소 3시간 이상이어야합니다.")
            $("#locationPackageStart").focus();
         }
         else if (parseInt(tPrice) > 1000000 || parseInt(tPrice) < 1000)
         {
            alert("패키지가격은 최소 1,000원 이상 ~ 최대 1,000,000원으로 입력해야 합니다.");
            $('#locationPackagePrice').focus();
         }
         else{
            confirm("이대로 패키지를 작성하시겠습니까?");
            f.submit();
         }
         
         
      });
         
   });

   // 함수 정의 ----------------------------------------------------------
   /* function set(target)
   {
      alert(parseInt(target.val())+1);
      return;
   }  */
   // setInputLength()
   //-- 입력값, 입력대상, 최소 인원, 최대 인원 및 유효성 검사 결과 알림
   function setInputLength(target, name, minLength, maxLength) {
      target.on("keyup", function() {
         var err = $(this).next();
         err.css("display", "none");
         
         if (target.val()=='') {
            
            err.html.hide();
            return false;
         }
         
         // 글자 수 제한, 색 변경
         else if (target.val().length > maxLength || target.val().length < minLength) {
            
            err.html("" + name + "은(는) " + minLength + "자~" + maxLength + "자로 입력해야합니다.").css("display","inline");
            err.css("color", "red");
            return;
         }
         else {
            err.html("사용 가능한 " + name + "입니다.").css("display","inline");
            err.css("color","green");
            return;
         }
      });
   }
   
   // 패키지 가격 제한 function
   function setInputPrice(target, name, minPrice, maxPrice) {
      target.on("keyup", function() {
         var err = $(this).next();
         err.css("display", "none");
         
         if (target.val()=='') {
            
            err.html.hide();
            return false;
         }
         // 글자 수 제한, 색 변경
         else if (parseInt($('#locationPackagePrice').val()) < minPrice 
               || parseInt($('#locationPackagePrice').val()) > maxPrice) {
            
            err.html("" + name + "은(는) 최소 " + minPrice + "원 이상~" + maxPrice + "원 으로 입력해야합니다.").css("display","inline");
            err.css("color", "red");
            return;
         }
         else {
            err.html("사용 가능한 " + name + "입니다.").css("display","inline");
            err.css("color","green");
            return;
         }
      });
   }
   
   // 취소 버튼 클릭시 기존 작성내용을 저장하지 않고 메인 홈페이지로 이동하는 function
   function cancel() {

      if(confirm("작성을 취소하고 메인 페이지로 돌아가시겠습니까?                        "
                  + "(기존 작성 내용은 저장되지 않습니다.)"))
      {
         location.href = "packagemanager.action?loc_code=${loc_code}";
      }   
   }

</script>

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
                  <span class="mr-2"> <a href="index.html">Home <i
                        class="ion-ios-arrow-forward"></i>
                  </a>
                  </span> <span>공간 등록 <i class="ion-ios-arrow-forward"></i>
                  </span>
               </p>
               <h1 class="mb-0 bread">패키지정보 입력</h1>
            </div>
         </div>
      </div>
   </section>
   <!-- END 타이틀 -->

   <!-- 본문 -->
   <!-- container 시작 -->

   <div class="container">

      <br>
      <br>

      <!-- Page Heading -->
      <h1 class="mb-2 text-gray-800">패키지정보 입력</h1>
      <p class="mb-4">
         패키지정보를 입력하세요. <a target="_blank" href="#"></a>
      </p>

      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->


      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">패키지정보 입력</h6>
         </div>



         <!-- form start --------------------------------------------->
         <form style="width: 80%; margin: 120px;" id="inputPackageInfo"
              action="inputpackageform.action?loc_code=${loc_code }" method="POST">
            <!-- action="inputpackageform.action?loc_code=${loc_code }" -->
            
            <!-- 컨트롤러 구성, 매핑 후 → action="inputxxxInfo.action" 로 변경 -->

            <!-- 1. 패키지명 -->

            <div id="packageName">

               <span style="font-size: 14pt; font-weight: bold;">패키지명 <span
                  style="color: red">*</span></span> <br>
               <br> <input type="text" name="inputPackageName" id="inputPackageName"
                  class="form-control" placeholder="패키지명을 입력하세요.(,제외) [최소 2자 ~ 최대 20자]">
               <span id="err" style="font-weight: bold;"></span>
            </div>

            <br>
            <br>
            <br>

            <!-- 2. 패키지 시작시간, 패키지 종료시간 -->

            <div id="packageStart">

               <span style="font-size: 14pt; font-weight: bold;">이용시간 <span
                  style="color: red">*</span></span> <br>
               <br> <select id="locationPackageStart" class="form-control"
                  name="locationPackageStart">
                  <option value=''>[==시간을 선택하세요.==]</option>
                  <c:forEach var="i" begin="0" end="24">
                     <option value="${i }">${i }시</option>
                  </c:forEach>
               </select><br> ~ <select id="locationPackageEnd" class="form-control"
                  name="locationPackageEnd">
                  <option value=''>[==시간을 선택하세요.==]</option>
                  <c:forEach var="i" begin="0" end="24">
                     <option value="${i }">${i }시</option>
                  </c:forEach>
                  
                  <c:forEach var="i" begin="1" end="10">
                     <option value="${i + 24 }">익일 ${i }시</option>
                  </c:forEach>
               </select><br>

               <!-- 종료시간 : 시작시간보다 이후부터 
               (최대 : 시작시간 + 34시까지)  -->
               <br> <span style="color: red;">※ 실제 이용 가능한 시간으로 설정해야
                  합니다.</span>
               <!-- 붉은색 글자 -->

            </div>

            <br>
            <br>
            <br>

            <!-- 3. 패키지 가격 -->

            <div id="packagePrice">

               <span style="font-size: 14pt; font-weight: bold;">패키지 가격 <span
                  style="color: red">*</span></span> <br>
               <br> <input type="text" class="form-control"
                  placeholder="패키지가격을 입력하세요. [최소 1,000원 ~ 최대 1,000,000원(부가세포함)]"
                  id="locationPackagePrice" name="locationPackagePrice" maxlength="50"
                  onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"> <br>
               <br>

            </div>

            <div class="container">
               <br>
               <br>
               <br>

               <!-- 저장 버튼 -->
               <!-- <input type="submit" value="저장" class="btn btn-warning"
                  id="inputPackageInfoSave" style="width: 45%; border-color: gray;"
                  onclick="inputInfoSave()"> -->
               <button type="button" class="btn btn-warning"
                  id="inputPackageInfoSave" style="width: 45%; border-color: gray;">저장</button>
               
               <%-- onclick="function()" → LocationPacakgeForm.jsp 테이블의 리스트형태로 저장 --%>

               <!-- 취소 버튼 -->
               <input type="button" class="btn btn-default"
                  id="inputPackageInfoCancel"
                  style="align-content: center; width: 45%; border-color: gray;"
                  onclick="cancel()" value="취소">
               <!-- onclick="function()" -->

            </div>



            <br>
            <br>
            <br>
            <br>

         </form>
      </div>
   </div>
   <div>
      <c:import url="${cp}/includes/footer_host.jsp"></c:import>
      <c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
   </div>

</body>
</html>