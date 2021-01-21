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
<title>Lookation</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<c:import url="${cp}/includes/header_user.jsp"></c:import>
<style type="text/css">
body, html {
width: 100%;
height: 100%;
}

section {
height: auto;
}

.info-div {
	padding-bottom: 8em;
}

.sidebar-box {
	border-radius: 20px;
}

/*=== carousel ===*/
.slider-item img {
	display: block;
	width: 100%;
	height: auto;
	max-width: 500px;
	resize: both;
}

/* Scrollspy 수정중 */
.myScrollspy {
	position: fixed;
    z-index: 999;
    top: 55%;
    left: 20px;
    width: 30px;
    padding: inherit;
    transform: translateY(-50%);
    color:black;
}

.myScrollspy .nav-stacked li {
    position: relative;
    min-width: 100px;
    font-size: 10px;
    text-align: center;
}

.myScrollspy .nav-stacked li .dot {
    display: block;
    color: #fff;
    padding: 5px 0;
}

.myScrollspy .nav-stacked li .dot::before,
.myScrollspy .nav-stacked li .dot::after {
    display: block;
    position: absolute;
    content: '';
    border-radius: 50%;
    top: 50%;
    transition: all .3s ease;
}

.myScrollspy .nav-stacked li .dot::before {
    width: 5px;
    height: 5px;
    left: 0;
    border: 2px solid #888;
    transform: translateY(-50%);
}

.myScrollspy .nav-stacked li .dot::after {
    width: 15px;
    height: 15px;
    border: 2px solid #444;
    left: -5px;
    transform: translateY(-50%) scale(0);
}

.myScrollspy .nav-stacked li .dot.active::before,
.myScrollspy .nav-stacked li:hover .dot::before {
    background: #00043c;
    border-color: #00043c;
}

.myScrollspy .nav-stacked li .dot.active::after,
.myScrollspy .nav-stacked li:hover .dot::after {
    transform: translateY(-50%) scale(1);
}

.myScrollspy .nav-stacked li .dot span {
    display: inline-block;
    opacity: 0;
    font-weight: 700;
    letter-spacing: .5px;
    background-color: #00043c;
    padding: 5px 10px;
    border-radius: 20px;
    margin-left: 20px;
    transform: translateX(20px);
    transition: all .3s ease;
}

.myScrollspy .nav-stacked li .dot.active span,
.myScrollspy .nav-stacked li:hover .dot span {
    transform: translateX(0px);
    opacity: 1;
}

/*=== sub 제목 밑줄 ===*/
.info-sub:after {
	content: "";
	display: block;
	font-weight: bold;
	width: 25px;
	border-bottom: 4px solid #ffd014;
	margin: 10px 0px;
}

/*=== 공간소개, 환불규정안내 ===*/
.info-list {
	list-style: none;
	display: inline;
}

/*=== list 구분 ===*/
.list-divider {
	border-bottom: 1px solid #ebebeb;
	padding: 10px 0 8px 3px;
}

.sub {
	/*font-weight: bold;*/
	vertical-align: top;
}

.det {
	padding-left: 30px;
	color: #656565;
	text-align: justify;
}

/*=== 시설 안내, 주의사항 리스트 ===*/
.number-list {
	list-style: none;
	padding: 0px;
}

.number-list .sub-num {
	padding-top: 1px;
	font-weight: bold;
	padding-left: 3px;
}

.number-list .det-num {
	padding-left: 30px;
	color: #656565;
}

/*=== 호스트 정보 ===*/
.host-box {
	font-size: 0.8em;
	background-color: #FFF;
	border-radius: 10px;
	margin-top: 4em;
	line-height: 130%;
}

/*=== 리뷰, Q&A 시간 표시 ===*/
.meta {
	font-size: 13px;
	letter-spacing: .1em;
	color: #ccc;
}

/*=== 답댓글 ===*/
.comment-list .children-reply {
    padding: 0px 0px 60px 40px;
    margin: 0;
    float: left;
    width: 100%;
}



/*=== 별점 ===*/
.set-star {
	color: #fdbe34;
}

/*=== 답글 버튼 ===*/
.reply {
	padding: 5px 10px;
	background: #e6e6e6;
	color: #000000;
	font-size: 11px;
	letter-spacing: .1em;
	font-weight: 400;
	border-radius: 4px;
}

/*=== 리뷰 이미지 리사이징 ===*/
.review-img {
	display: inline-block;
	resize: both;
	max-width: 100%;
}	
	
/*=== 패키지 가격 줄 정리 ===*/
.package-bundle {
max-width: 50%;
font-size: 10pt;
color: rgba(0, 0, 0, 0.8);
}

.vertical-down{
vertical-align: bottom;
margin-top: 1.9em;
}

#section4
{
	min-height: 600px;
	padding: 0;
	margin: 0;
}

/*=== 글자 튀어나가는것 ===*/
p {
	word-break:break-all;
}

</style>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=bbdc5d69c0be5fc4d930f65664018993&libraries=services,clusterer,drawing"></script>
<script type="text/javascript">

	$(function(){
		$('body').scrollspy({target: ".navbar"});
		/* scrollspy */
	    var link = $('#myScrollspy a.dot');
	    link.on('click',function(e){
	        
	        //href 속성을 통해, section id 타겟을 잡음
	        var target = $($(this).attr('href')); 
	        
	        //target section의 좌표를 통해 꼭대기로 이동
	        $('html, body').animate({
	            scrollTop: target.offset().top
	        },600);
	        
	        //active 클래스 부여
	        $(this).addClass('active');
	
	        // 앵커를 통해 이동할때, URL에 #id가 붙지 않도록 함.
	        e.preventDefault();
	    });
	    
	    $(window).on('scroll',function(){
	        findPosition();
	    });
	
	    function findPosition(){
	        $('section').each(function(){
	            if( ($(this).offset().top - $(window).scrollTop() ) < 20){
	                link.removeClass('active');
	                $('#myScrollspy').find('[data-scroll="'+ $(this).attr('id') +'"]').addClass('active');
	            }
	        });
	    }
	
	    findPosition();
	    /* 여기까지 scrollspy */
	    
	    /* 날짜 기본값 오늘로 */
	    var now = new Date();
		var day = ("0" + now.getDate()).slice(-2);
		var month = ("0" + (now.getMonth() + 1)).slice(-2);
		var today = now.getFullYear()+"-"+(month)+"-"+(day);
		
		// 최대 3개월까지만으로 제한하기
		var maxmonth = ("0" + (now.getMonth() + 3)).slice(-2);
		var maxdate = now.getFullYear()+"-"+(maxmonth)+"-"+(day);
		
		$("#selectDate").val(today);
		$("#selectDate").attr("min", today);
		$("#selectDate").attr("max", maxdate);
		
	    /* 숫자 버튼 증가 및 최소, 최댓값 설정 */
	    $("#increase").on("click", function(){
			
		   	if($("#amount").val() >= 29)
		    {
		   		$("#amount").val(30);
		    }
		   	else
		   	{
		   		$("#amount").val(parseInt($("#amount").val()) + 1);
		   	}
	    });
	    
	    $("#decrease").on("click", function(){
	    	
	    	 if($("#amount").val() <= 0)
	         {
	         	$("#amount").val(0);
	         }
	    	 else
	    	 {
	    		$("#amount").val(parseInt($('#amount').val()) - 1);
	    	 }
	       
	    });
	    
	    // 패키지 정보 가져오기
		// 날짜가 변경되었을 경우 수행할 코드 처리
		$("#selectDate").change(function()
		{
			var htmlText="";
			
			$.ajax({
				url : "locdetailajax.action"
				, type : "get"
				, data : {selectDate : $("#selectDate").val(), locCode : $("#hiddenCode").val()}
			    , dataType : "json"
				, success : function(args) {
					
					/* $.each(data, function() {
					     alert(this["packageName"]);
					     alert(this.packageName);
					}); */

					for(var idx=0; idx<args.test.length; idx++){
						$("#pacakgeDiv").append("<input type='radio'>"+ args.test[idx].packageName);
					}
				}
			    , error:function(e){
			    	alert(e.responseText);
			    	
			    }
				});
		});
		
		// 이용자 QnA 수정하는 팝업
		$(".modifyQna").click(function()
		{
			var url = "modifyformqna.action?identify=member&qna_code=" + $(this).val();
			var option = "width=450, height=400, resizable=no, scrollbars=yes, status=no";
			window.open(url, "", option);
		}); 
		
		// 이용자 Qna 삭제하는 팝업
		$(".deleteQna").click(function()
		{
			if(confirm("삭제하시겠습니까?"))
			{
				$(location).attr("href", "deleteqna.action?identify=member&qna_code=" + $(this).val());
			}
		}); 
		
		// 이용자 리뷰 삭제하는 팝업 
		$(".deleteReview").click(function()
		{
			if(confirm("삭제하시겠습니까?"))
			{
				$(location).attr("href", "deletereview.action?identify=member&review_code=" + $(this).val());
			}
		}); 
		
		// 이용자 리뷰 수정하는 팝업
		$(".modifyReview").click(function()
		{
			var url = "modifyformreview.action?identify=member&review_code=" + $(this).val();
			var option = "width=450, height=400, resizable=no, scrollbars=yes, status=no";
			window.open(url, "", option);
		}); 
	
	});

	// 질문 작성하는 팝업
	function writeQna()
	{	
		var locCode = document.getElementById("hiddenCode").value;
		var url = "writeqna.action?identify=member&locCode="+ locCode + "&memCode=M000003";
		// memCode 임시
		var option = "width=450, height=400, resizable=no, scrollbars=yes, status=no";
		window.open(url, "", option);
	}
	
	// 후기 작성하는 팝업
	function writeReview()
	{	
		var locCode = document.getElementById("hiddenCode").value;
		var url = "writereview.action?identify=member&locCode="+ locCode + "&memCode=M000004";
		// memCode 임시
		var option = "width=450, height=400, resizable=no, scrollbars=yes, status=no";
		window.open(url, "", option);
	}
	
</script>
</head>
<body data-spy="scroll" data-target="#myScrollspy" data-offset="15">

<div class="ftco-section ftco-degree-bg">
	<div class="container">
		<div class="row">	
			<div class="col-md-12">
				<input type="hidden" id="hiddenCode" name="loc_code" value="${basicInfo.locationCode }">
				<h2 class="mb-1 font-weight-bold">${basicInfo.locName }</h2>
				<h4 class="mb-3">${basicInfo.shortIntro }</h4>
				<!-- 태그모양으로 카테고리 표시해줌  -->
			 	<div class="tagcloud mb-5">
	                <a href="#" class="tag-cloud-link">${basicInfo.locType }</a>
             	</div>
             	<div id="print"></div>
             	
				<!--- carousel --->
				<!-- 상세이미지 슬라이더 -->
				<div class="home-slider owl-carousel">
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_1.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_2.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_3.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_4.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_5.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_6.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/location1.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/location4.jpg);">
					</div>
					<div class="slider-item"
						style="background-image: url(<%=cp%>/images/image_2.jpg);">
					</div>
				</div>
				<!-- 이미지 클릭하면 확대  -->
			</div>
		</div>
			
		<div class="row">
			<div class="col-lg-8 p-md-3">


			<!-- .navbar scrollspy로 이동할 부분 -->
			<nav class="myScrollspy" id="myScrollspy">
				<ul class="nav nav-pills nav-stacked" data-spy="affix" data-offset-top="205">
					<li><a data-scroll="section1" href="#section1" class="dot active"><span>공간소개</span></a><li>
					<li><a data-scroll="section2" href="#section2" class="dot"><span>이용후기</span></a><li>
					<li><a data-scroll="section3" href="#section3" class="dot"><span>위치안내</span></a><li>
					<li><a data-scroll="section4" href="#section4" class="dot"><span>Q&A</span></a><li>
				</ul>
			</nav>

			<!-- Section 1 -->
			<section id="section1" class="mb-5">
				
				<div class="info-div mb-5">
					<h4 class="my-4 info-sub">공간소개</h4>
					<p>${basicInfo.intro }</p>
				</div>

			
				<div class="info-div mt-5">
					<h4 class="info-sub">시설 안내</h4>
					<ol class="number-list">
						<c:forEach var="facility" items="${facilityInfo}" varStatus="status">
							<li class="list-divider">
								<span class="sub-num">${status.count }</span>
								<span class="det-num">${facility.facility }</span>
							</li>
						</c:forEach>
					</ol>
				</div>
			</section>
			<!-- End .Section 1  -->

			<section id="" class="info-div">
				<h4 class="info-sub">예약시 주의사항</h4>
				<ol class="number-list">
					<c:forEach var="caution" items="${cautionInfo}" varStatus="status">
						<li class="list-divider">
							<span class="sub-num">${status.count }</span>
							<span class="det-num">${caution.caution }</span>
						</li>
					</c:forEach>
				</ol>
			</section>
				
			<!-- section 2 -->
			<section id="section2" class="info-div">
				<h4 class="info-sub">
					이용후기<span class="set-star ml-3">${countReview }</span>
					<span class="ml-2" style="font-size: 10pt;">(평균별점 <span class="set-star icon-star mr-1"></span>${avgReviewRate }점)</span>

					<!-- 이용후기 작성권한이 있을 경우(이용완료, 후기작성 안했는지 확인) -->
					<!-- 에만 이용후기 작성버튼 표시  -->
					<button class="btn btn-primary float-right" onclick="writeReview()">후기 작성하기</button>
				</h4>
				<br>

				<!-- 사진 후기만 보기 클릭시 사진있는 후기만 보여주기-->
				<div class="custom-control custom-switch float-right">
					<input type="checkbox" class="custom-control-input" id="switch1">
					<label class="custom-control-label" for="switch1">사진 후기만
						보기</label>
				</div>
					
				<c:forEach var="rv" items="${review }">
					<ul class="comment-list">	
							<li class="comment">
								<h4>${rv.memberNickName }</h4>
								<h6 class="float-right">
									<c:forEach var="star" begin="1" end="${rv.reviewRate }" step="1">
										<span class="set-star icon-star mr-1"></span>
									</c:forEach>
								</h6>
								
								<div class="meta mb-2">${rv.date }</div>
								
								<c:if test="${rv.removeCount eq 1}">
									<p>삭제된 리뷰입니다.</p>
								</c:if>
								
								<c:if test="${rv.removeCount eq 0}">
									<p>${rv.content }</p>
									
									<c:if test="${rv.rvimgCount ne 0 }">
										<p>
											<img class="review-img" src="<%=cp%>${rv.url}"
												alt="리뷰사진">
										</p>
									</c:if>
									
									<c:if test="${rv.memCode == 'M000004'}">
										<button type="button" class="reply border-0 modifyReview" value="${rv.boardCode }">수정</button> 
										<button type="button" class="reply border-0 deleteReview" value="${rv.boardCode }">삭제</button>
									</c:if>
								</c:if>
								
								<c:if test="${rv.count eq 1 && rv.replyRemove eq 0 }">
									<li class="children children-reply">
										<h4>${basicInfo.hostNickName }</h4>
										<span class="meta mb-2">${rv.replyDate }</span>
										<p class="">${rv.replyContent }</p>
									</li>
								</c:if>
							</li>
					</ul>
				</c:forEach><!-- .comment-list -->
			
			</section><!-- End .section2 -->
				
			<!-- Section 3 -->
			<section id="section3" class="">
				<!-- 호스트 정보 -->
				<h4 class="mt-4 info-sub">호스트정보</h4>
				<div class="d-flex p-4 host-box mb-5">
					<div class="host-info">
						<h3 class="mb-4">${basicInfo.hostNickName}</h3>
						<p>
							<a href="2_newDirectMessage.jsp" class="reply">호스트에게 DM</a>
						</p>
					</div>
													
						
					<div class="desc ml-5">
						<!-- 상호명(개인/법인)
								, 대표자명, 사업자 등록번호
								, 사업자 유형, 주업태, 주종목, 사업장 주소)  -->
						<p>사업자번호 : ${bizInfo.bizLicenseNum}</p>
						<p>상호명 : ${bizInfo.bizName}</p>
						<p>대표자명 : ${bizInfo.bizCeo}</p>
						<p>사업자유형 : ${bizInfo.bizCeoType}</p>
						<p>주업태 : ${bizInfo.bizMainType} 주종목 : ${bizInfo.bizSubType}</p>
						<p>주소 : <span id="addr">${basicInfo.addr}</span> ${basicInfo.detailAddr}</p>
						<!-- 예약 완료한 이용자에게 DM버튼 출력 -->
						
					</div>
				</div>
				
				<!-- 지도 위치 -->
			
				<h4 class="info-sub">위치 안내</h4>
				<div id="map" style="width:100% ;height:350px;"></div>
				<script>
				var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
			    mapOption = {
			        center: new kakao.maps.LatLng(33.450701, 126.570667), // 중심좌표
			        level: 3 // 지도의 확대 레벨
			    };  

				var map = new kakao.maps.Map(mapContainer, mapOption); 

				// 주소-좌표 변환 객체 생성
				var geocoder = new kakao.maps.services.Geocoder();
				
				// 해당 공간의 주소를 저장
				var addr = document.getElementById('addr').innerHTML;
				
				// 주소로 좌표를 검색합니다
				geocoder.addressSearch(addr, function(result, status) {
					
				     if (status === kakao.maps.services.Status.OK) {	// 검색완료시
				        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

				        // 결과값으로 받은 위치를 마커로 표시
				        var marker = new kakao.maps.Marker({
				            map: map,
				            position: coords
				        });

				        // 인포윈도우로 장소에 대한 설명을 표시합니다
				        var infowindow = new kakao.maps.InfoWindow({
				            content: '<div style="width:150px;text-align:center;padding:3px 0;color:black;font-size:0.8em;">${basicInfo.locName }</div>'
				        });
				        infowindow.open(map, marker);

				        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
				        map.setCenter(coords);
				    } 
				});
				</script>
				<sub>※ 호스트가 입력한 주소가 정확하지 않을 경우 실제 위치와 다를 수 있습니다.</sub>
			</section>
			<!-- End .Section 3  -->

			<section id="" class="info-div mb-5">
				<h4 class="info-sub mt-5">환불규정 안내</h4>
				<ol class="number-list">
					<li class="list-divider"><span class="sub mr-2">이용
							7일전</span> <span class="det ml-5">총 금액의 100% 환불</span></li>
					<li class="list-divider"><span class="sub">이용 6일전 ~
							1일전</span> <span class="det">총 금액의 50% 환불</span></li>
					<li class="list-divider"><span class="sub mr-3">예약 당일</span>
						<span class="det ml-5">환불 불가</span>
					<li>
				</ol>
			</section>
			
			<!-- Section 4 -->
			<section id="section4" class="mt-4">
				<h4 class="info-sub">
					Q&A<span class="set-star ml-3">${countQna }</span>
					<!-- 로그인 했는지 확인 -->
					<button class="btn btn-primary float-right" type="button" onclick="writeQna()">질문 작성하기</button>
				</h4>
				
				<c:forEach var="qna" items="${qna }">
					<ul class="comment-list">
					
						<li class="comment mb-5">
							<h4>${qna.memberNickName }</h4>
							<span class="meta">${qna.date }</span>
							<c:if test="${qna.removeCount==1}">
								<p class="">이용자가 삭제한 게시글입니다.</p>
							</c:if>
							<c:if test="${qna.removeCount==0}">
								<p class="">${qna.qna_content }</p>
								<c:if test="${qna.memCode == 'M000003'}">
									<button type="button" class="reply border-0 modifyQna" value="${qna.boardCode }">수정</button> 
									<button type="button" class="reply border-0 deleteQna" value="${qna.boardCode }">삭제</button>
								</c:if>
							</c:if>
							
						</li>
					
						<c:if test="${qna.count eq 1 && qna.replyRemove eq 0 }">
							<li class="children-reply">
								<h4>${basicInfo.hostNickName }</h4>
								<span class="meta">${qna.replyDate }</span>
								<p class="">${qna.replyContent }</p>
							</li>
						</c:if>
					</ul>
				</c:forEach>
			</section><!-- End .section4 -->
		</div><!-- .col-lg-8 -->

			<!-- 오른쪽 사이드바 -->
		<div class="col-lg-4 col-xs-12 sidebar pl-lg-5 ftco-animate">
			<div class="sidebar-box ftco-animate p-3 mt-5">
				
				<form action="bookapply.action" id="reserveForm" method="GET">
					<div class="categories">
						<h3>공간예약하기</h3>
						<hr>
						
						<div class="memo">
							<p class="text-center text-dark">호스트의 승인을 기다릴 필요 없이<br>지금 바로 예약하세요!</p>
							<hr>
						</div>
						
						<!-- 공간정보 -->
						<div class="info">
							<div class="spaceInfo">
								<ol class="info-list">
									<li class="list-divider">
										<span class="sub">공간유형</span>
										<span class="det">${basicInfo.locType }</span>
									</li>
									<li class="list-divider">
										<span class="sub">영업시간</span>
										<span class="det">${usingInfo.usingHour }</span>
									</li>
									<li class="list-divider">
										<span class="sub">정기휴무일</span>
										<span class="det">${usingInfo.dayOff }</span>
									</li>
									<li class="list-divider">
										<span class="sub">지정휴무일</span>
										<span class="det">${usingInfo.appointDayOff }</span>
									</li>
								</ol>
							</div>
						</div>
						
						
						
						<h4 class="mt-5">예약선택</h4>
							<hr>
						<!-- 예약하고 싶은 날짜를 선택하면 해당 날짜에 적용된 -->
						<!-- 패키지정보가 나타남. -->
						<!-- 선택한 날짜를 date 변수에 담고, 해당 날짜에 존재하는 패키지정보를 불러와서 출력한다... ? -->
						
						
						<div class="py-2 calendar">
							<input type="date" class="form-control" id="selectDate" min="2020-12">
						</div>
						
						
						<div class="content mt-3">
							<div class="packageDiv">
<%-- 								<c:forEach var="i" items="${test }" varStatus="radioNum">
									
										<label><input type="radio" name="packageRadio" id="${radioNum.count}"value="${i.packageCode }">
										${i.packageName }
										<span class="ml-3 package-bundle">
										<c:choose>
											<c:when test="${i.packStart >= 13 && i.packEnd >= 24}">
												<c:set var="startpm" value="${i.packStart - 12}" scope="session" />
												<c:set var="nextam" value="${i.packEnd - 24}" scope="session" />
												
												오후 ${startpm }시 ~ 익일 오전 ${nextam }시
											</c:when>
										
											<c:when test="${i.packStart <= 11 && i.packEnd >= 13 }">
												<c:set var="endpm" value="${i.packEnd - 12}" scope="session" />
												
												오전 ${i.packStart }시 ~ 오후 ${endpm }시
											</c:when>
											
											<c:otherwise>
												오전 ${i.packStart }시 ~ 오후 ${i.packEnd }시
											</c:otherwise>
										</c:choose>
										</span>
										</label>
										<div class="flex float-right vertical-down">
											<strong>${i.packPrice }(원)</strong>
										</div>
								</c:forEach> 
								 --%>
								<input type="radio" name="apply_package_code" id="1" value="AP000001">
							    <label><span class="ml-3 package-bundle" > 오후 10시 ~ 익일 오전 10시</span></label>
							    <div class="flex float-right vertical-down">
									<strong>60000(원)</strong>
								</div>
							</div>
						</div>
							 
						
						<div class="heading mt-3 mb-2">
							<h3>총 예약인원</h3>
							<hr> 
	                    </div>
						<span class="pull-right mb-3">
						최소인원
						<span class="text-primary" id="minPeople">${basicInfo.minPeople }</span>명
						최대인원 
						<span class="text-danger" id="minPeople">${basicInfo.maxPeople }</span>명</span>
						
						<div class="text-center my-2 mt-2">
							<div class="btn-group" style="width: 230px;">
								<button type="button" class="btn btn-primary py-3 my-0 btn-group-addon"
								id="decrease" >-</button>
								<input type="text" id="amount" required="required"
								class="form-control border-0" name="book_people" value="${basicInfo.minPeople }" style="text-align:center;">
								<button type="button" class="btn btn-primary py-0 mb-0 btn-group-addon"
								id="increase">+</button>
							</div>
						</div>
							
						<div class="text-right my-4">
							<!-- 가격 선택시 바뀌어야함 -->
							<h3><span class="icon-won"></span>  60,000 원</h3>
						</div>
						
						<!-- 결제하기 누르면 인원수 검증 -->
						<button type="submit" class="btn btn-primary btn-lg btn-block">결제하기</button>
						</div><!-- End .categories -->
					</form><!-- End form -->
				</div><!-- End .sidebar-box ftco-animate -->
			</div><!-- End .col-md-4 -->
		</div><!-- End .row -->
	</div><!-- End .container -->
</div>



<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<div>
	<c:import url="${cp}/includes/footer_user.jsp"></c:import>
</div>
</body>
</html>