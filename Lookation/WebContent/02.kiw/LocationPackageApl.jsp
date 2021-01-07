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
<title>LocationPackageApl.jsp</title>
<%@ include file="../includes/includes_home.jsp" %>
<%@ include file="../includes/header_host.jsp" %>


<link rel="stylesheet" href="/css/jquery-ui.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>

<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script type='text/javascript' src='//code.jquery.com/jquery-1.8.3.js'></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
<script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
<script src="/js/bootstrap-datepicker.kr.js" charset="UTF-8"></script>

<script type='text/javascript'>

   /*  $(function(){
        $('.input-group.date').datepicker({
            calendarWeeks: false,
            todayHighlight: true,
            autoclose: true,
            format: "yyyy/mm/dd",
            language: "kr"
        });
    });
 */
    $( function() {
        var dateFormat = "mm/dd/yy",
          from = $( "#sproid-bookingcondition-datefrom" )
            .datepicker({
              defaultDate: "+1w",
              changeMonth: true,
              numberOfMonths: 1
            })
            .on( "change", function() {
              to.datepicker( "option", "minDate", getDate( this ) );
            }),
          to = $( "#sproid-bookingcondition-dateto" ).datepicker({
            defaultDate: "+1w",
            changeMonth: true,
            numberOfMonths: 1
          })
          .on( "change", function() {
            from.datepicker( "option", "maxDate", getDate( this ) );
          });
        function getDate( element ) {
          var date;
          try {
            date = $.datepicker.parseDate( dateFormat, element.value );
          } catch( error ) {
            date = null;
          }
          return date;
        }
      } );
    
    </script>

</head>


</head>
<body>



   <!-- 타이틀 -->
   <section class="hero-wrap hero-wrap-2"
      style="background-image: url('images/bg_3.jpg');"
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
                   
                  <span>공간 등록 
                  	<i class="ion-ios-arrow-forward"></i>
                  </span>
               </p>
               <h1 class="mb-0 bread">패키지 적용</h1>
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
   <h1 class="mb-2 text-gray-800">패키지 적용</h1>
   <p class="mb-4"> 패키지를 적용하세요. <a target="_blank" href="#">이전으로</a>.</p>
      
      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->
      
      
      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">패키지 적용</h6>
         </div>



<form style="width: 800px; margin: 120px;">

	
	<!-- 0. 달력 -->
	
	<span style="font-size: 14pt; font-weight: bold;">날짜 선택 <span style="color: red">*</span></span>
	
	<br><br>
	
	<!-- 달력 특정 일자에 커서를 갖다댈 때 
	→ background-color:yellow -->
	
	<!-- 달력 특정 일자 클릭 시 
	→ onclick="function();"
	(체크박스?형태로 checked된 상태) -->
	
	<!--   <div class="container">
	
	        <div class="input-group date">
				<input type="checkbox" id="checked">
				
		            <input type="text" class="form-control">
		            <span class="input-group-addon">
			            <i class="glyphicon glyphicon-calendar">
			            </i>
		            </span>
	 			
	        </div>
	
	  </div> -->
	
	  <p>from</p>
	<input type="text" class="spromotion-input-inbody spromotion-input-datepick" id="sproid-bookingcondition-datefrom">
	<p>to</p>
	<input type="text" class="spromotion-input-inbody spromotion-input-datepick" id="sproid-bookingcondition-dateto">
	
	
	
	
	<!-- <input type="button" value="선택한 날짜 적용" /> -->
	

<br><br><br>

<!-- ※ 현재 특정패키지의 적용할 날짜들을 선택한 상태-------------------------------------------------------------- -->



	<!-- 1. 현재 패키지 (추가된 패키지 현황) 
		 (○(라디오버튼) (패키지명) 형태 
		  → LocationPackageForm.jsp 에서 현재 패키지 정보를 리스트 형태로 받아옴 -->
	
	
	<div id="currentPackage">
		<span style="font-size: 14pt; font-weight: bold;">현재패키지 <span style="color: red">*</span></span>
			<br><br>
			<table class="table table-bordered table-hover" id="insertedPacakge">
				<tr>
					<th>-</th>
					<th>패키지명</th>
					<th>패키지 시작시간</th>
					<th>패키지 종료시간</th>
					<th>패키지 가격</th>
				</tr>
				<tr>
					<td>
						<input type="radio" name="selectPackage">
					</td>							<!-- 패키지번호 -->
					<td>올나잇(18시~익일 3시)</td>  <!-- 패키지명 -->
					<td>18시</td>					<!-- 패키지 시작시간 -->
					<td>익일 3시</td>				<!-- 패키지 종료시간 -->
					<td>100,000원</td>				<!-- 패키지 가격 -->
				</tr>
				<tr>
					<td>
						<input type="radio" name="selectPackage">
					</td>							<!-- LocationPackageInsert.jsp 에서 -->
					<td>저녁시간</td>				<!-- 패키지가 추가될때마다 tr,td노드가 추가 -->
					<td>10시</td>
					<td>23시</td>
					<td>100,000원</td>
				</tr>
				
				<!--	 :	 	-->
			</table>
		
	</div>	

<br><br><br>
<div class="container">
<!-- 저장 버튼 -->
<!-- 주소등록처럼 LocationPacakageInsert.jsp 에서 저장버튼 클릭시 
	 입력한 정보가 추가됨.  -->

<input type="submit" value="저장" class="btn btn-warning" 
		   id="LocationPackageAplSave" style="width:300px;">

<!-- 취소 버튼 -->
<input type="button" class="btn btn-default" 
	id="LocationPacakgeAplCancel" style="width: 300px;"
	value="취소"> <!-- onclick="function()" -->



</div>

	

<br><br><br><br>

</form>
</div>
</div>
<%@ include file="../includes/footer.jsp"%>
<%@ include file="../includes/includes_home_end.jsp"%>
</body>
</html>