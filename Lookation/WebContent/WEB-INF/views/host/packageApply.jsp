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
<title>LocationPackageApl.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/defaults.jsp"></c:import>

<!-- FullCanlendar 라이브러리 -->
<link href='${pageContext.request.contextPath }/lib/calendar/main.css'
   rel='stylesheet' />
<script src='${pageContext.request.contextPath }/lib/calendar/main.js'></script>

<script>

   // 적용한 패키지 구조체
   function structApply()
   {
      var apply_code = '';
      var code = '';
      var start = '';
      var end = '';
      var date = '';
      
      var state = '';   // 패키지의 상태(추가or삭제or수정)
   }
   

   // 기존에 있던 패키지를 담는 구조체
   var arrPackageApply = new Array();
   
   // 새롭게 추가된 패키지를 담는 구조체
   var arrPackageInsert = new Array();
  
   var date = '';
   var id_index = 1;
   
  document.addEventListener('DOMContentLoaded', function() {

   var index = 0;
   // 예약이 없는 적용된 패키지
   <c:forEach var="apply" items="${applyList}">   
      arrPackageApply[index] = new structApply();
      arrPackageApply[index].apply_code = '${apply.apply_code}';
      arrPackageApply[index].code = '${apply.code}';
      arrPackageApply[index].start = '${apply.time_start}';
      arrPackageApply[index].end = '${apply.time_end}';
      arrPackageApply[index].date = '${apply.apply_date}';
      arrPackageApply[index].state = 'none';
      index++;
   </c:forEach>  
   
   // 이미 예약된 적용된 패키지
   <c:forEach var="applyBook" items="${applyBookList}">   
	   arrPackageApply[index] = new structApply();
	   arrPackageApply[index].apply_code = '${applyBook.apply_code}';
	   arrPackageApply[index].code = '${applyBook.code}';
	   arrPackageApply[index].start = '${applyBook.time_start}';
	   arrPackageApply[index].end = '${applyBook.time_end}';
	   arrPackageApply[index].date = '${applyBook.apply_date}';
	   arrPackageApply[index].state = 'none';
	   index++;
   </c:forEach>  
   
    /* initialize the external events
    -----------------------------------------------------------------*/
   
    var containerEl = document.getElementById('external-events-list');
    new FullCalendar.Draggable(containerEl, {
      itemSelector: '.fc-event',
      eventData: function(eventEl) {
           return {
             id: "new" + id_index++,
             title: eventEl.innerText.trim()
           }
      }
    });

    /* initialize the calendar
    -----------------------------------------------------------------*/
   
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
      headerToolbar: {
        left: 'today',
        center: 'title',
        right: 'prev,next'
      },
      editable: true,
      droppable: true,
      dragRevertDuration: 0,
      
      // 추가하는 이벤트
      drop: function(arg) {
         date = arg.dateStr;
      },
      
      // 옮기는 이벤트
      eventDrop: function(arg) 
      {
         var id = arg.event.id;      
         var title = arg.event.title;   
          date = arg.event.startStr;
          
          var now = new Date();
         var todayDate = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 24);
         var selectDate = new Date(date);
         
         if(selectDate.getTime() <= todayDate.getTime())
         {
            alert("오늘과 이전 날짜는 패키지 등록이 불가능합니다.");
            arg.revert();
            return;
         }
         
         // 이미 예약된 적용된 패키지를 옮기려고 할 경우
         if(id == 'booked')
         {
        	 alert("이미 예약된 패키지는 수정할 수 없습니다.");
        	 arg.revert();
        	 return;
         }
          
         // title 문자열을 쪼개서 start, end를 가져온다.
         var arr = title.split('~');
          var start = parseInt(arr[0].split(':')[0]);
          var end = parseInt(arr[1].split(':')[0]);
          var result = checkApply(id, start, end, date);
         
          if(result == true)   
          {
             // 수정
             modifyApply(id, date);
          }
          else
          {
              alert("겹치는 시간대의 패키지가 있습니다. 다른 날짜를 선택해주세요.");
              arg.revert();
           }  
      },
      
      // 패키지가 추가되는 이벤트
      eventReceive: function(info)
      {
         var now = new Date();
         var todayDate = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 24);
         var selectDate = new Date(date);
         
         if(selectDate.getTime() <= todayDate.getTime())
         {
            alert("오늘과 이전 날짜는 패키지 등록이 불가능합니다.");
            info.revert();
            return;
         }
              
         var tags = info.draggedEl.getElementsByTagName('span');
         
         var code, start, end, name;
         code = tags[0].innerHTML;
         start = tags[1].innerHTML;
         
         if(tags[2].className == "nextDay")
            end = String(parseInt(tags[2].innerHTML) + 24);
         else
          end = tags[2].innerHTML; 
         
         name = tags[3].innerHTML;
         
         var result = checkApply(info.event.id, start, end, date);
         
          if(result == true)   
          {
             var ap = new structApply();
             ap.apply_code = info.event.id;
             ap.code = code;
             ap.start = start;
             ap.end = end;
             ap.name = name;
             ap.date = date;
             ap.state = 'insert';
             arrPackageInsert.push(ap);
          }
          else
          {
              alert("겹치는 시간대의 패키지가 있습니다. 다른 날짜를 선택해주세요.");
              info.revert();
           }
      },
      
      // 삭제
      eventDragStop: function(info) {

          if(isEventOverDiv(info.jsEvent.clientX, info.jsEvent.clientY)) 
          {
        	  // 이미 예약된 적용된 패키지를 삭제하려고 할 경우
              if(info.event.id == 'booked')
              {
             	 alert("이미 예약된 패키지는 삭제할 수 없습니다.");
             	 info.revert();
             	 return;
              }
        	  
             removeApply(info.event.id);
             info.event.remove();
          }
      },

      // 이벤트
      events : [
         
           <c:forEach var="apply" items="${applyList}">
          {
             id : '${apply.apply_code }',
             title: '${apply.time_start }:00 ~ <c:if test="${apply.time_end <= 24}">${apply.time_end }</c:if><c:if test="${apply.time_end > 24}">익일 ${apply.time_end - 24}</c:if>:00 ${apply.name}',
             start: '${apply.apply_date}'
          },
           </c:forEach>
          
          <c:forEach var="applyBook" items="${applyBookList}">
          {
             id : 'booked',
             title: '${applyBook.time_start }:00 ~ <c:if test="${applyBook.time_end <= 24}">${applyBook.time_end }</c:if><c:if test="${applyBook.time_end > 24}">익일 ${applyBook.time_end - 24}</c:if>:00 ${applyBook.name}',
             start: '${applyBook.apply_date}',
             color : '#FDBE34',
             textColor : '#000000'
          },
           </c:forEach>
      ]
    });
    calendar.render();
   
    
    var isEventOverDiv = function(x, y) {
        
       var trashEl = $("#trash")[0];
        const rect = trashEl.getBoundingClientRect();
         
       // Compare
       if (x >= rect.left
            && y >= rect.top
            && x <= rect.right
            && y <= rect .bottom) 
       { return true; }
        
       
       return false; 

    }
    
  });
  
 
     // 적용 불가능한 상황인지 체크
   function checkApply(apply_code, start, end, date)
   {
        var today = date;
        
        // 익일 계산을 위해 이전 날짜와 이후 날짜도 가져온다.
        var yesterday = makeDate(date, -1);
        var tomorrow = makeDate(date, 1);   
   
        start = parseInt(start);
        end = parseInt(end);
        
      // 이미 적용된 패키지, 추가된 패키지에서 겹치는 시간대가 있는지 확인한다.
      for (var i = 0; i < arrPackageApply.length; i++) 
      {
         // 이미 삭제 처리된 적용 패키지는 검사하지 않는다.
         if(arrPackageApply[i].state == 'delete' 
               // 자기 자신은 패키지는 검사하지 않는다.
               || arrPackageApply[i].apply_code == apply_code)
            continue;
         
         var otherStart = parseInt(arrPackageApply[i].start);
         var otherEnd = parseInt(arrPackageApply[i].end);
         
         if(arrPackageApply[i].date == yesterday)
         {
            if(otherEnd > 24 && otherEnd - 24 > start)
               return false;
         }
         
         if(arrPackageApply[i].date == today)
         {
            // 패키지 범위 내에 이미 패키지가 존재
            if(otherStart >= start && otherEnd <= end)
               return false;
            
            // 패키지 범위에 걸쳐서 패키지가 존재
            if(otherStart < start && otherEnd > start ||
                  otherStart < end && otherEnd > end   )
               return false;
               
            // 이미 존재하는 패키지 안 영역에 있음
            if(otherStart <= start && otherEnd >= end)
               return false;
         }
            
         if(arrPackageApply[i].date == tomorrow)
         {
            if(end > 24 && end - 24 > otherStart)
               return false;
         }
      }
      
      for (var i = 0; i < arrPackageInsert.length; i++) 
      {
         // 자기 자신은 패키지는 검사하지 않는다.
         if(arrPackageInsert[i].apply_code == apply_code)
            continue;
         
         var otherStart = parseInt(arrPackageInsert[i].start);
         var otherEnd = parseInt(arrPackageInsert[i].end);
         
         if(arrPackageInsert[i].date == yesterday)
         {
            if(otherEnd > 24 && otherEnd - 24 > start)
               return false;
            
         }
         
         if(arrPackageInsert[i].date == today)
         {
            // 패키지 범위 내에 이미 패키지가 존재
            if(otherStart >= start && otherEnd <= end)
               return false;
               
            // 패키지 범위에 걸쳐서 패키지가 존재
            if( (otherStart < start && otherEnd > start) || (otherStart < end && otherEnd > end) )
               return false;
               
               
            // 이미 존재하는 패키지 안 영역에 있음
            if(otherStart <= start && otherEnd >= end)
               return false;
               
         }
            
         if(arrPackageInsert[i].date == tomorrow)
         {
            if(end > 24 && end - 24 > otherStart)
               return false;
               
         }
      }
   
      return true;
   }
        
     function makeDate(date, val)
     {
        var tempDate = new Date(date);
        tempDate.setDate(tempDate.getDate() + val);
        var y = tempDate.getFullYear();
        var m = tempDate.getMonth() + 1;
        var d = tempDate.getDate();
      return ( y + '-' + (m >= 10 ? m : '0' + m) + '-' + (d >= 10 ? d : '0' + d) );   
     }
     
     // 적용된 패키지를 변경한 경우
     function modifyApply(apply_code, newDate)
     {
        if(apply_code != '')
      {
         for (var i = 0; i < arrPackageApply.length; i++) 
           {
              if(arrPackageApply[i].apply_code == apply_code)
              {
                  // 이미 추가되어 있는 것들은 수정 상태값을 주어
                 // 나중에 db상에서 수정할 수 있게
                 arrPackageApply[i].date = newDate;
                 arrPackageApply[i].state = 'update';
                 break;
              }
           }
         
         for (var i = 0; i < arrPackageInsert.length; i++) 
           {
              if(arrPackageInsert[i].apply_code == apply_code)
              {
                  // 새로 추가된 것들은 아직 db상에 존재하지 않으므로
                 // 그냥 날짜만 갱신해주면 된다.
                 arrPackageInsert[i].date = newDate;
                 break;
              }
           }
      }
     }
     
     // 적용된 패키지를 지우는 경우
     function removeApply(apply_code)
     {
        if(apply_code != '')
      {
         for (var i = 0; i < arrPackageApply.length; i++) 
           {
              if(arrPackageApply[i].apply_code == apply_code)
              {
                 // 이미 추가되어 있는 것들은 삭제 상태값을 주어
                 // 나중에 db상에서 지울 수 있게
                 arrPackageApply[i].state = 'delete';
                 break;
              }
           }
         
         for (var i = 0; i < arrPackageInsert.length; i++) 
           {
              if(arrPackageInsert[i].apply_code == apply_code)
              {
                 // 새로 추가된 것들은 아직 db상에 존재하지 않으므로
                 // 그냥 배열에서 삭제하면 된다.
                 arrPackageInsert.splice(i, 1);
                 break;
              }
           }
      }
     }
     
</script>

<script type="text/javascript">
   

   $(function()
   {
      var result = '';
      
      $("#LocationPackageAplSave").click(function()
      {
         if(confirm("이 패키지 스케쥴로 저장하시겠습니까?"))
         {
            applyAjaxRequest();
            alert("성공적으로 저장되었습니다.");
            $("#applyForm").submit();
         }
      });
      
      $("#LocationPacakgeAplCancel").click(function()
      {
         if(confirm("정말 처음으로 돌아시겠습니까?"))
         {
            location.href="locationlist.action?loc_code=${loc_code}";
         }
      });
   }); 
   
   // JSON을 이용해 적용된 패키지 정보를 컨트롤러로 전송한다.
   function applyAjaxRequest()
   {
       var arrApply_code = new Array();
      var arrCode = new Array();
      var arrDate = new Array();
      var arrState = new Array();
      
      for (var i = 0; i < arrPackageApply.length; i++) {
         arrApply_code.push(arrPackageApply[i].apply_code);
         arrCode.push(arrPackageApply[i].code);
         arrDate.push(arrPackageApply[i].date);
         arrState.push(arrPackageApply[i].state);
      }
      
      for (var i = 0; i < arrPackageInsert.length; i++) {
         arrApply_code.push(arrPackageInsert[i].apply_code);
         arrCode.push(arrPackageInsert[i].code);
         arrDate.push(arrPackageInsert[i].date);
         arrState.push(arrPackageInsert[i].state);
      }
      
      var formData = {
            'apply_code' : arrApply_code,
            'package_code' : arrCode,
            'date' : arrDate,
            'state' : arrState
      }; 

      $.ajax({
         type : 'post',
         url : 'packageapplyajax.action?loc_code=${loc_code}',
         dataType: 'json',
         data        : formData,
         success      : function(data) {result = data;}
      }); 
   }

</script>

<style type="text/css">
body {
   margin-top: 40px;
   font-size: 14px;
   font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
}

#external-events {
   position: fixed;
   left: 20px;
   top: 20px;
   width: 150px;
   padding: 0 10px;
   border: 1px solid #ccc;
   background: #eee;
   text-align: left;
}

#external-events h4 {
   font-size: 16px;
   margin-top: 0;
   padding-top: 1em;
}

#external-events .fc-event {
   margin: 3px 0;
   cursor: pointer;
}

#external-events p {
   margin: 1.5em 0;
   font-size: 11px;
   color: #666;
}

#external-events p input {
   margin: 0;
   vertical-align: middle;
}

#calendar-wrap {
   margin-left: 200px;
}

#calendar {
   max-width: 1100px;
   margin: 0 auto;
}

a.fc-event {
  -webkit-transition: 0s;
  transition: 0s;
}
</style>

</head>
<body>F
   <div>
        <c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=${info.nick }"></c:import>
   </div>


   <!-- 타이틀 -->
   <section class="hero-wrap hero-wrap-2"
      style="background-image: url('<%=cp%>/images/bg_3.jpg');"
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
               <h1 class="mb-0 bread">패키지 적용</h1>
            </div>
         </div>
      </div>
   </section>
   <!-- END 타이틀 -->

   <!-- 본문 -->
   <!-- container 시작 -->

   <div class="container" style="padding: 30px">

      <!-- Page Heading -->
      <h1 class="mb-2 text-gray-800">패키지 적용</h1>
      <p class="mb-4">
         드래그-앤-드롭으로 편하게 패키지를 적용하세요. <a target="_blank" href="#">이전으로</a>.
      </p>

      <!-- 필요하다면 마이페이지로 돌아가는 왼쪽 사이드바 -->


      <div class="card shadow mb-4">
         <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-default">패키지 적용</h6>
         </div>



         <form action="packageapplyform.action?loc_code=${loc_code }" 
            style="padding: 50px; text-align: center;" id="applyForm">

            <input type="hidden" name="loc_code" value="${loc_code }"/>
            <!-- 0. 달력 -->

            <span style="float:left; font-size: 14pt; font-weight: bold;">날짜 선택 <span
               style="color: red">*</span></span>

            <div id='wrap' style="padding: 50px;">

               <div id='external-events' style="width: 15%; margin-top: 20%;">
                  <h4 style="font-weight:bold;">등록된 패키지</h4>

                  <div id='external-events-list'>
                     
                     <c:forEach var="form" items="${formList }">
                        <div class='fc-event fc-h-event fc-daygrid-event fc-daygrid-block-event'>
                           <div class='fc-event-main'>
                              <span id="code" style="display:none;">${form.code }</span>
                              <span id="time_start">${form.time_start }</span>:00 ~
                              <c:if test="${form.time_end <= 24}">
                                 <span id="time_end" class="today">${form.time_end }</span>:00
                              </c:if>
                              <c:if test="${form.time_end > 24}">
                                 익일 <span id="time_end" class="nextDay">${form.time_end - 24}</span>:00 
                              </c:if>
                              <span id="name">${form.name }</span>
                              <span id="price">₩ ${form.price }</span>
                           </div>
                        </div>
                     </c:forEach>   
                                          
                  </div>
                  <br>
               </div>

               <div id='calendar-wrap' style="margin: auto 0px;">
                  <div id='calendar'></div>
               </div>
               
               <p style="margin-top: 5px;"> 패키지를 삭제하려면 이곳으로 끌어놓으세요.</p>
               <div id="trash" style="margin:auto;width:40%; height: 50px; background: #FFC107; text-align: center;">
                  <img src="${pageContext.request.contextPath}/images/cal-trash.png" width="40px"
                  style="margin-top:5px;"/> 
               </div>
            </div>
            
            

            <div class="container">
               <!-- 저장 버튼 -->
               <!-- 주소등록처럼 LocationPacakageInsert.jsp 에서 저장버튼 클릭시 
                   입력한 정보가 추가됨.  -->

            <!--    <input type="submit" value="저장" class="btn btn-warning"
                  id="LocationPackageAplSave" style="width: 300px;"> -->

               <button type="button" class="btn btn-warning"
               id="LocationPackageAplSave" style="width: 300px;">저장</button>

               <!-- 취소 버튼 -->
               <input type="button" class="btn btn-default"
                  id="LocationPacakgeAplCancel"
                  style="width: 300px; border-color: black;" value="취소">
               
            </div>
            
         </form>

      </div>
   </div>
   <div>
      <c:import url="${cp}/includes/footer_host.jsp"></c:import>
      <c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
   </div>
</body>
</html>