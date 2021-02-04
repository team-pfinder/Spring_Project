<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
   request.setCharacterEncoding("UTF-8");
   String cp = request.getContextPath();
   
   String keyword = (String)request.getParameter("keyword");
   pageContext.setAttribute("keyword", keyword);

   String order = (String)request.getParameter("order");
   pageContext.setAttribute("order", order);

   String loc_type = (String)request.getParameter("loc_type");
   pageContext.setAttribute("loc_type", loc_type);

   String start_date = (String)request.getParameter("start_date");
   pageContext.setAttribute("start_date", start_date);

   String end_date = (String)request.getParameter("end_date");
   pageContext.setAttribute("end_date", end_date);
   
   String start_price = (String)request.getParameter("start_price");
   pageContext.setAttribute("start_price", start_price);
   
   String end_price = (String)request.getParameter("end_price");
   pageContext.setAttribute("end_price", end_price);

   String loc_addr_city = (String)request.getParameter("loc_addr_city");
   pageContext.setAttribute("loc_addr_city", loc_addr_city);

   String loc_addr_dong = (String)request.getParameter("loc_addr_dong");
   pageContext.setAttribute("loc_addr_dong", loc_addr_dong);


%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Lookation</title>
<c:import url="${cp}/includes/header_user.jsp?result=${result }&nick=${info.nick }"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>


<style>
.mapbar{
	height: 500px;
	background-color: #FFF;
	display: none;
}

.form-control {
	font-size: 1.0em;
}

.empty {
	padding-bottom: 100px;
}
</style>
<style type="text/css">


#img
{
	transition: background-size ease-out 200ms;
	background-size: auto 100%;
	cursor: default;
}

#img:hover
{
	transition: background-size ease-out 200ms;
	background-size: auto 120%;
	cursor: pointer;
}

</style>

<style>
.overlay_info {
	border-radius: 6px;
	margin-bottom: 12px;
	float: left;
	position: relative;
	border: 1px solid #ccc;
	border-bottom: 2px solid #ddd;
	background-color: #fff;
	width: 210px;
	height: 170px;
}

.overlay_info:nth-of-type(n) {
	border: 0;
	box-shadow: 0px 1px 2px #888;
}

.overlay_info a {
	display: block;
	background: #d95050;
	background: #d95050
		url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
		no-repeat right 14px center;
	text-decoration: none;
	color: #fff;
	padding: 12px 36px 12px 14px;
	font-size: 14px;
	border-radius: 6px 6px 0 0
}

.overlay_info a strong {
	display: block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 190px;
	height: 20px;
}

.overlay_info .desc {
	padding: 7px;
	position: relative;
	width: 190px;
	height: 75px;
}

.overlay_info img {
	/* vertical-align: top; */
	width: 195px;
	height: 110px;
}

.overlay_info .address {
	font-size: 12px;
	color: #333;
	position: absolute;
	left: 80px;
	right: 14px;
	top: 24px;
	white-space: normal
}

.overlay_info:after {
	content: '';
	position: absolute;
	margin-left: -11px;
	left: 50%;
	bottom: -12px;
	width: 22px;
	height: 12px;
	background:
		url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png)
		no-repeat 0 bottom;
}
</style>

</head>
<body>
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('<%=cp%>/images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"><a href="#">Home <i
								class="ion-ios-arrow-forward"></i></a></span> <a href="#"><span>Search
								<i class="ion-ios-arrow-forward"></i>
						</span></a>
					</p>
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
			<form action="search.action" method="get">

				<div class="row ftco-animate">
					<div class="col-md-3">
						<dl>
							<dt class="ml-1">공간유형</dt>
							<dd>
								<select class="form-control" name="loc_type">
									<option ${loc_type == null ? "selected=\"selected\"":""}>모든공간</option>
									<option ${loc_type eq "브라이덜샤워" ? "selected=\"selected\"":"" } value="브라이덜샤워">브라이덜샤워</option>
									<option ${loc_type eq "파티룸" ? "selected=\"selected\"":"" } value="파티룸">파티룸</option>
									<option ${loc_type eq "엠티장소" ? "selected=\"selected\"":"" } value="엠티장소">엠티장소</option>
									<option ${loc_type eq "루프탑" ? "selected=\"selected\"":"" } value="루프탑">루프탑</option>
									<option ${loc_type eq "클럽" ? "selected=\"selected\"":"" } value="클럽">클럽</option>
								</select>
							</dd>
						</dl>
					</div>
					<div class="col-md-3">
						<dl>
							<dt>날짜</dt>
							<dd>
								<input type="date" class="form-control" id="start_date"	name="start_date" >
							</dd>
						</dl>
					</div>
					<div class="col-md-6">
						<dl>
							<dt>가격</dt>
							<dd>
								<div class="form-group">
									<span class="d-flex"> <!-- 처음 입력한 숫자가 뒤 숫자보다 작아야 검색가능. -->
										<input type="number" class="form-control" name="start_price" value = ${start_price eq null ? "0" : start_price} min="0" max="400000" step="10000"> 
										<span class="mt-2 mx-1">-</span> 
										<input type="number" class="form-control" name="end_price" value = ${end_price eq null ? "400000" : end_price} min="0" max="400000" step="10000">
										<span class="mt-2 mx-1">(원)</span>
									</span>
								</div>
							</dd>
						</dl>
					</div>
					<div class="col-md-3">
						<dl>
							<dt>지역</dt>
							<dd>
								<select class="form-control px-10" name="loc_addr_city">
									<option ${loc_addr_city == null ? "selected=\"selected\"":""}>전체</option>
									<option ${loc_addr_city eq "서울" ? "selected=\"selected\"":""} value="서울">서울</option>
									<option ${loc_addr_city eq "경기" ? "selected=\"selected\"":""} value="경기">경기</option>
									<option ${loc_addr_city eq "충북" ? "selected=\"selected\"":""} value="충북">충북</option>
									<option ${loc_addr_city eq "충남" ? "selected=\"selected\"":""} value="충남">충남</option>
									<option ${loc_addr_city eq "전북" ? "selected=\"selected\"":""} value="전북">전북</option>
									<option ${loc_addr_city eq "전남" ? "selected=\"selected\"":""} value="전남">전남</option>
									<option ${loc_addr_city eq "경북" ? "selected=\"selected\"":""} value="경북">경북</option>
									<option ${loc_addr_city eq "경남" ? "selected=\"selected\"":""} value="경남">경남</option>
								</select>
							</dd>
						</dl>	
					</div>
					<div class="col-md-3">
						<dl>					
							<dt>상세지역</dt>
							<dd>
								<input type="text" class="form-control border float-right" name="loc_addr_dong" placeholder="동 이름을 입력하세요." value=${loc_addr_dong eq null ? null : loc_addr_dong}>
							</dd>
						</dl>
					</div>
					<div class="col-md-4">
						<br />
						<button type="submit" class="btn btn-primary pb-3" style="width:100%">
							상세검색 <span class="fa fa-search"></span>
						</button>
					</div>				
					<div class="col-md-12">	
						<dl>
							<dd class="float-right">
								<!-- 지도 버튼 클릭시 핀 찍은 지도 창 열림 -->
								<button type="button" class="btn btn-outline-dark rounded-pill border map">
									<span class="icon-map-marker" style="font-size: 1.2em;">지도</span>
								</button>
							</dd>
						</dl>
					</div>
					<input type="hidden" value="${keyword }" name="keyword"/>
					<div class="col-md-12 mb-3">
						<div id="map" class="mapbar border" style="width:100%;height:500px;"></div>
					</div>
				</div>
				<!-- End .row ftco animate -->
			</form>
			<!-- 상세검색창 form end -->
			<hr />
			<!-- 정렬 start -->
			<div class="row">
				<div class="col-md-9">
					<!-- 빈공간 -->
				</div>
				<div class="col-md-3 m-0 text-right">
					<!-- 우측정렬 -->
					<select class="form-control" id="order">
						<option ${order == null ? "selected=\"selected\"":""}>정렬방법선택</option>
						<option ${order == "sortLtH" ? "selected=\"selected\"":""} value="sortLtH">가격 낮은 순</option>
						<option ${order == "sortHtL" ? "selected=\"selected\"":""} value="sortHtL">가격 높은 순</option>
						<option ${order == "sortReview" ? "selected=\"selected\"":""} value="sortReview">이용후기 많은 순</option>
						<option ${order == "sortRateAvg" ? "selected=\"selected\"":""} value="sortRateAvg">평균 별점 순</option>
						<!-- 선택시 해당 순서대로 정렬한 페이지 요청-->
					</select>
				</div>
			</div>
			<br> <br>
			<!-- 정렬 end  -->

			<!-- 결과 리스트 -->

			<c:choose>
				<c:when test="${empty view}">
					<div class="col-md-12 ftco-animate">
						<h3 class="text-center empty">검색 결과가 존재하지 않습니다.</h3>
					</div>
				</c:when>
				<c:otherwise>
					<div class="row d-flex">
						<c:forEach var="item" items="${view}">
							<div class="col-md-4 ftco-animate">
								<div class="blog-entry align-self-stretch">
									<a href="locationdetail.action?loc_code=${item.loc_code}"
										class="block-20 rounded" id="img"
										style="background-image: url('<%=cp%>/images/${item.thumbnail_url}');">
									</a>
									<div class="text mt-3 text-center">
										<div class="meta mb-2">
											<div>
												<a href="#">${item.loc_addr }</a>
											</div>
											<br>
											<div>
												<a href="#"><span class="icon-star"></span>
													${item.avg_rate }</a>
											</div>
											<div>
												<a href="#"><span class="icon-people"></span>
													${item.max_people}</a>
											</div>
											<div>
												<a href="#" class="meta-chat"><span class="icon-chat">${item.review_count }</span></a>
											</div>
											<div>
												<a href="#"><span class="icon-won"></span>${item.price}</a>
											</div>
										</div>
										<h3 class="heading">
											<a href="locationdetail.action?loc_code=${item.loc_code}">${item.loc_name }</a>
										</h3>
										<!-- 맵정보 -->
										<form name="mapInfo">
											<input type="hidden" name="loc_code" value="${item.loc_code}"/>
											<input type="hidden" name="loc_name" value="${item.loc_name}"/>
											<input type="hidden" name="loc_addr" value="${item.loc_addr}"/>
											<input type="hidden" name="thumbnail_url" value="${item.thumbnail_url}"/>
										</form>			
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:otherwise>
			</c:choose>

		</div>
	</section>

<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<div>
	<c:import url="${cp}/includes/footer_user.jsp"></c:import>
</div>

<script type="text/javascript">

	function getParam(sname) {
	
	    var params = location.search.substr(location.search.indexOf("?") + 1);
	
	    var sval = "";
	
	    params = params.split("&");
	
	    for (var i = 0; i < params.length; i++) {
	
	        temp = params[i].split("=");
	
	        if ([temp[0]] == sname) { sval = temp[1]; }
	
	    }
	
	    return sval;
	}
	
	$(function()
	{

		/* 날짜 기본값 */
		var now = new Date();

		// 시작날짜 오늘
		var day = ("0" + now.getDate()).slice(-2);
		var month = ("0" + (now.getMonth() + 1)).slice(-2);
		var today = now.getFullYear() + "-" + (month) + "-" + (day);

		// 최대 3개월까지만으로 제한하기
		var maxmonth = ("0" + (now.getMonth() + 3)).slice(-2);

		var maxdate = now.getFullYear() + "-" + (maxmonth) + "-" + (day);
		
		var start_date_param = getParam("start_date");

		if(start_date_param == null)
		{
			$("#start_date").val(today);
		}
		else if(start_date_param == "")
		{
			$("#start_date").val(today);
		}
		else if(start_date_param != null)
		{
			$("#start_date").val(start_date_param);
		}


		$("#start_date").attr("min", today);
		$("#start_date").attr("max", maxdate);

		$("#end_date").attr("min", today);
		$("#end_date").attr("max", maxdate);

		// 지도 버튼 클릭시 내려오게
		$("button.map").click(function()
		{
			setTimeout(function(){ map.relayout(); }, 1000);
			$(".mapbar").slideToggle();
		});
	});
</script>

	<!-- 검색 -->
<script type="text/javascript">

	$(function()
	{
		$("#order").change(function()
		{
			$(location).attr("href", 
							"search.action?keyword=${keyword}&loc_type=${loc_type}&start_date=${start_date}&end_date=${end_date}&start_price=${start_price}&end_price=${end_price}&loc_addr_city=${loc_addr_city}&loc_addr_dong=${loc_addr_dong}&order=" + $(this).val());
		});
	});

</script>

<!-- 지도API -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=bbdc5d69c0be5fc4d930f65664018993&libraries=services"></script>
<script type="text/javascript">
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
	    center : new daum.maps.LatLng(36.633535, 127.425882), // 지도의 중심좌표
	    level : 3
	// 지도의 확대 레벨
	};
	
	// 지도를 생성합니다    
	var map = new daum.maps.Map(mapContainer, mapOption);
	
	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new daum.maps.services.Geocoder();
	
	//배열 사이즈 지정
	var num_of_result = $("input[name=loc_code]").length;
	
	//배열 생성
	var loc_code_arr = new Array(num_of_result);
	var loc_name_arr = new Array(num_of_result);
	var loc_addr_arr = new Array(num_of_result);
	var thumbnail_url_arr = new Array(num_of_result);
	
	console.log("--------------obj result ----------");
	
	//배열에 값 주입
	for(var i=0; i<num_of_result; i++)
	{
		loc_code_arr[i] = $("input[name=loc_code]").eq(i).val();
		loc_name_arr[i] = $("input[name=loc_name]").eq(i).val();
		loc_addr_arr[i] = $("input[name=loc_addr]").eq(i).val();
		thumbnail_url_arr[i] = $("input[name=thumbnail_url]").eq(i).val();
		console.log(loc_code_arr[i]);
		console.log(loc_name_arr[i]);
		console.log(loc_addr_arr[i]);
		console.log(thumbnail_url_arr[i]);
		console.log("----------------------------------");

	}

	// 마커 생성
	for (i = 0; i < num_of_result; i++)
	{
		myMarker(i + 1, loc_addr_arr[i], loc_name_arr[i], loc_code_arr[i], thumbnail_url_arr[i]);
	}
	
	function myMarker(number, address, name, code, img)
	{
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch(
						//'주소',
						address,
						function(result, status)
						{
							// 정상적으로 검색이 완료됐으면 
							if (status === daum.maps.services.Status.OK)
							{
								var coords = new daum.maps.LatLng(
										result[0].y,
										result[0].x);

								// 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
								var content = '<div class="overlay_info">';
								content += '    <a href="locationdetail.action?loc_code='+code+'"><strong style="text-overflow: ellipsis";>'+name+'</strong></a>';
								content += '    <div class="desc">';
								content += '        <img src="<%=cp%>/images/'+ img +'" alt="">';
								content += '    </div>';
								content += '</div>';
									
								// 커스텀 오버레이가 표시될 위치입니다 
								var position = new daum.maps.LatLng(
										result[0].y, result[0].x);

								// 커스텀 오버레이를 생성합니다
								var customOverlay = new daum.maps.CustomOverlay(
								{
									map : map,
									position : position,
									content : content,
								    xAnchor: 0.5, // 커스텀 오버레이의 x축 위치입니다. 1에 가까울수록 왼쪽에 위치합니다. 기본값은 0.5 입니다
								    yAnchor: 1.1 // 커스텀 오버레이의 y축 위치입니다. 1에 가까울수록 위쪽에 위치합니다. 기본값은 0.5 입니다
								});

								// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
								map.setCenter(coords);
							}
						});
	}
</script>

</body>
</html>