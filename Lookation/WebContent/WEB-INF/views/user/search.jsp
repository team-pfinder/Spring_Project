<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
   request.setCharacterEncoding("UTF-8");
   String cp = request.getContextPath();
   
   String keyword = (String)request.getParameter("keyword");
   pageContext.setAttribute("keyword", keyword);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Lookation</title>
<c:import url="${cp}/includes/header_user.jsp"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=bbdc5d69c0be5fc4d930f65664018993&libraries=services,clusterer,drawing"></script>
<style>
   .b { 
   height: 500px; 
   background-color: #FFF; 
   display: none;
   }
    
    .form-control { 
    font-size : 1.0em;
    }  
    
    .empty {
    
    padding-bottom: 100px;
    }
    
</style>
<script type="text/javascript">

   $(function(){
      
      /* 날짜 기본값 */
       var now = new Date();
      
      // 시작날짜 오늘
       var day = ("0" + now.getDate()).slice(-2);
      var month = ("0" + (now.getMonth() + 1)).slice(-2);
      var today = now.getFullYear()+"-"+(month)+"-"+(day);
      
      // 최대 3개월까지만으로 제한하기
      var maxmonth = ("0" + (now.getMonth() + 3)).slice(-2);
      
      var maxdate = now.getFullYear()+"-"+(maxmonth)+"-"+(day);
      
      $("#startDate").val(today);
      $("#startDate").attr("min", today);
      $("#startDate").attr("max", maxdate);
      
      $("#endDate").attr("min", today);
      $("#endDate").attr("max", maxdate);
      
      
      
      // 지도 버튼 클릭시 내려오게
       $("button.map").click( function() {
             $(".mapbar").slideToggle();
        });
   });


</script>

<script type="text/javascript">

$(function()
{
	// 수정 버튼 클릭 시 액션 처리
	$("#order").change(function()
	{
		$(location).attr("href", "searchorder.action?order=" + $(this).val());
	});
	
});
	/*
	function sbmOrd()
	{
		alert("s");
		var val = document.getElementById("order").value;
		alert(val);
		alert('${keyword}');
		alert("searchorder.action?keyword=${keyword}&order="+val);
		var form = document.createElement('form');
		form.name = 'newForm';
		form.method ='get';
		form.action = "searchorder.action?order=rwe";
		document.body.appendChild(form);
		form.submit();
	}
	*/


</script>

</head>
<body>
    <section class="hero-wrap hero-wrap-2" style="background-image: url('<%=cp%>/images/bg_3.jpg');" data-stellar-background-ratio="0.5">
      <div class="overlay"></div>
      <div class="container">
        <div class="row no-gutters slider-text align-items-end">
          <div class="col-md-9 ftco-animate pb-5">
             <p class="breadcrumbs mb-2"><span class="mr-2"><a href="#">Home <i class="ion-ios-arrow-forward"></i></a></span> <a href="#"><span>Search <i class="ion-ios-arrow-forward"></i></span></a></p>
            <h1 class="mb-0 bread">공간 검색하기</h1>
          </div>
        </div>
      </div>
    </section>

   <section class="ftco-section">
      <div class="container">
         <div class="row ml-1 mb-3 d-flex ftco-animate ">
            <!-- 나중에 검색어 태그 a 안에 받아야 함 -->
            <h3>
               <a href="">${keyword }</a>에 대한 검색 결과입니다.
            </h3>
            <br>
         </div>


         <!-- 검색 폼 -->
         <form>
            <div class="row ml-1 my-1 ftco-animate">
               <div class="checkbox">
                  <label> <input type="checkbox" id="research"> 해당
                     검색 결과 내에서 다시 검색
                  </label>
               </div>
            </div>



            <div class="row ftco-animate">
               <div class="col-md-3">
                  <dl>
                     <dt class="ml-1">공간유형</dt>
                     <dd>
                        <select class="form-control">
                           <option selected>모든 공간</option>
                           <option value="1">파티룸</option>
                           <option value="2">엠티장소</option>
                           <option value="3">루프탑</option>
                           <option value="4">클럽</option>
                        </select>
                     </dd>
                  </dl>
               </div>
               <div class="col-md-3">
                  <dl>
                     <dt>시작일</dt>
                     <dd>
                        <input type="date" class="form-control" id="startDate"
                           name="start_date">
                     </dd>
                     <dt>종료일</dt>
                     <dd>
                        <input type="date" class="form-control" id="endDate"
                           name="end_Date">
                     </dd>
                  </dl>
               </div>
               <div class="col-md-3">
                  <dl>
                     <dt>
                        <label for="customRange">가격</label>
                     </dt>
                     <dd>
                        <div class="form-group">
                           <span class="d-flex"> <!-- 처음 입력한 숫자가 뒤 숫자보다 작아야 검색가능. -->
                              <input type="number" class="form-control" value="10000"
                              min="0" max="400000" step="10000"> <span
                              class="mt-2 mx-1">-</span> <input type="number"
                              class="form-control" value="60000" min="0" max="400000"
                              step="10000"> <span class="mt-2 mx-1">(원)</span>
                           </span>
                        </div>
                     </dd>
                  </dl>
               </div>
               <div class="col-md-3">
                  <dl>

                     <dt>지역</dt>
                     <dd>
                        <select class="form-control px-10">
                           <option selected="selected">전체</option>
                           <option value="1">서울</option>
                           <option value="2">경기</option>
                           <option value="3">충북</option>
                           <option value="4">충남</option>
                           <option value="5">전북</option>
                           <option value="6">전남</option>
                           <option value="7">경북</option>
                           <option value="8">경남</option>
                        </select>
                        <!-- 선택시 지역명 텍스트 입력 가능-->
                        <div class="input-group d-flex mt-2">
                           <input type="text" class="form-control border"
                              placeholder="동 이름을 입력하세요.">
                           <div class="input-group-append">
                              <button type="button" class="btn btn-primary pb-3">
                                 <span class="fa fa-search"></span>
                              </button>
                           </div>
                        </div>
                     </dd>

                     <dd class="float-right">
                        <!-- 지도 버튼 클릭시 핀 찍은 지도 창 열림 -->
                        <button type="button"
                           class="btn btn-outline-dark rounded-pill border map">
                           <span class="icon-map-marker" style="font-size: 1.2em;">
                              지도</span>
                        </button>
                     </dd>
                  </dl>
               </div>
               <div class="col-md-12 mb-3">
                  <div class="mapbar border">
                     <h1>나는 지도 자리요</h1>
                  </div>
               </div>
            </div>
            <!-- End .row ftco animate -->
         </form>
         <!-- 상세검색창 form end -->

         <!-- 정렬 start -->
         <div class="row">
            <div class="col-md-9">
               <!-- 빈공간 -->
            </div>
            <div class="col-md-3 m-0 text-right">
               <!-- 우측정렬 -->
               <select class="form-control" id="order" onchange="sbmOrd();">
                  <option selected="selected">정렬방법선택</option>
                  <option value="sortLtH">가격 낮은 순</option>
                  <option value="sortHtL">가격 높은 순</option>
                  <option value="sortReview">이용후기 많은 순</option>
                  <option value="sortRateAvg">평균 별점 순</option>
                  <!-- 선택시 해당 순서대로 정렬한 페이지 요청-->
               </select>
            </div>
         </div>
         <br>
         <br>
         <!-- 정렬 end  -->

         <!-- 키워드 있을 경우 -->

         <c:choose>
            <c:when test="${empty keyword}">
               <div class="col-md-12 ftco-animate">
                  <h3 class="text-center empty">검색 결과가 존재하지 않습니다.</h3>
               </div>
            </c:when>
				<c:otherwise>
					<div class="row d-flex">
						<c:forEach var="item" items="${view}">
						<div class="col-md-4 ftco-animate">
							<div class="blog-entry align-self-stretch">
								<a href="blog-single.html" class="block-20 rounded"
									style="background-image: url('<%=cp%>/images/image_1.jpg');">
								</a>
								<div class="text mt-3 text-center">
									<div class="meta mb-2">
										<div>
											<a href="#">${item.loc_addr }</a>
										</div>
										<div>
											<a href="#"><span class="icon-star"></span> ${item.avg_rate }</a>
										</div>
										<div>
											<a href="#"><span class="icon-people"></span> ${item.max_people}</a>
										</div>
										<div>
											<a href="#" class="meta-chat"><span class="icon-chat"></span>
												${item.review_count }</a>
										</div>
										<div>
											<a href="#"><span class="icon-won"></span>${item.price}</a>
										</div>
									</div>
									<h3 class="heading">
										<a href="#">${item.loc_name }</a>
									</h3>
								</div>
							</div>
						</div>
					</c:forEach>               
				</div>
               
               <!-- 페이징 아니면 무한스크롤 방식 -->
               <!-- 페이징 처리할 부분 -->
               <div class="row mt-5">
                  <div class="col text-center">
                     <div class="block-27">
                        <ul>
                           <li><a href="#">&lt;</a></li>
                           <li class="active"><span>1</span></li>
                           <li><a href="#">2</a></li>
                           <li><a href="#">3</a></li>
                           <li><a href="#">4</a></li>
                           <li><a href="#">5</a></li>
                           <li><a href="#">&gt;</a></li>
                        </ul>
                     </div>
                  </div>
               </div>
               </c:otherwise>
          </c:choose>
      </div>
   </section>




   <c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<div>
   <c:import url="${cp}/includes/footer_user.jsp"></c:import>
</div>
</body>
</html>