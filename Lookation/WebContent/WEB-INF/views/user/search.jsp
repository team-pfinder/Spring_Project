<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Lookation</title>
<c:import url="${cp}/01.ksb/head(user).jsp"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
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
      		<h3><a href="">인천</a>에 대한 검색 결과입니다.</h3><br>
      	</div>
      	
      	
      	<!-- 검색 폼 -->
      	<form>
      		<div class="row ml-1 my-1 ftco-animate">
      			<div class="checkbox">
			       <label>
			           <input type="checkbox" id="cancelled" checked="checked">
			           해당 검색 결과 내에서 다시 검색
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
								<!-- ※ 원래는 datePicker 이용해야 함. -->
								<input type="date" class="form-control">
							</dd>
							<dt>종료일</dt>
							<dd>
								<!-- ※ 원래는 datePicker 이용해야 함. -->
								<input type="date" class="form-control">
							</dd>
						</dl>
					</div>
				<div class="col-md-3">
					<dl>
						<!-- ※ 슬라이더 못해먹겠다 -->
						<dt><label for="customRange">가격</label></dt>
						<dd>
							<div class="form-group">
								<span class="d-flex"> 
									<!-- 처음 입력한 숫자가 뒤 숫자보다 작아야 검색가능. -->
									<input type="number" class="form-control"
									value="10000" min="0" max="400000" step="10000"> <span class="mt-2 mx-1">-</span> 
									<input type="number" class="form-control"
									value="60000" min="0" max="400000" step="10000">  <span class="mt-2 mx-1">(원)</span>
								</span> 
								</div>
						</dd>
						<!-- <dd>
							<div class="form-group">
								<input value="10000" min="0" max="400000" step="500" type="range">
							</div>
						</dd>
						<dd>
							<div class="form-group">
								<input value="10000" min="0" max="400000" step="500" type="range">
							</div>
						</dd> -->
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
								<input type="text" class="form-control border" placeholder="동 이름을 입력하세요."> 
								<div class="input-group-append">
									<button type="button" class="btn btn-primary pb-3"><span class="fa fa-search"></span></button>
								</div>
							</div>
						</dd>
					
						<dd class="float-right">
							<!-- 지도 버튼 클릭시 핀 찍은 지도 창 열림 -->
							<button type="button" class="btn btn-outline-dark rounded-pill border">
							<span class="icon-map-marker" style="font-size:1.2em;">  지도</span></button>
						</dd>
					</dl>
				</div>
				
				
      		</div><br><br>
		</form><!-- 상세검색창 form end -->
		
		<!-- 정렬 start -->
		<div class="row">
			<div class="col-md-9">
				<!-- 빈공간 -->
			</div>
    		<div class="col-md-3 m-0 text-right">
    			<!-- 우측정렬 -->
	    		<select class="form-control">
					<option selected="selected">정렬방법선택</option>
					<option value="1">가격 낮은 순</option>
					<option value="2">가격 높은 순</option>
					<option value="3">이용후기 많은 순</option>
					<option value="4">평균 별점 순</option>
					<!-- 선택시 해당 순서대로 정렬한 페이지 요청-->
				</select>
    		</div>
  		</div><br><br>
  		<!-- 정렬 end  -->

      	
        <div class="row d-flex">
          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('<%=cp%>/images/image_1.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">동명동</a></div>
                  <div><a href="#"><span class="icon-star"></span> 4.1</a></div>
                  <div><a href="#"><span class="icon-people"></span> 4</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 15</a></div>
                  <div><a href="#"><span class="icon-won"></span> 60,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">개구리랑 놀이동산 파티룸</a></h3>
              </div>
            </div>
          </div>
          
          
          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('<%=cp%>/images/image_2.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">죽전동</a></div>
                  <div><a href="#"><span class="icon-star"></span> 4.3</a></div>
                  <div><a href="#"><span class="icon-people"></span> 6</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 99</a></div>
                  <div><a href="#"><span class="icon-won"></span> 90,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">우후죽순</a></h3>
              </div>
            </div>
          </div>
          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('<%=cp%>/images/image_3.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">응암동</a></div>
                  <div><a href="#"><span class="icon-star"></span> 3.8</a></div>
                  <div><a href="#"><span class="icon-people"></span> 8</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 20</a></div>
                  <div><a href="#"><span class="icon-won"></span> 190,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">길이가 들쭉날쭉하면요</a></h3>
              </div>
            </div>
          </div>

          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('<%=cp%>/images/image_4.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">상속자들</a></div>
                  <div><a href="#"><span class="icon-star"></span> 1.8</a></div>
                  <div><a href="#"><span class="icon-people"></span> 100</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 123</a></div>
                  <div><a href="#"><span class="icon-won"></span> 1,200,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">사탄들의 학교에 루시퍼의 등장이라... 재밌어지겠네</a></h3>
              </div>
            </div>
          </div>
          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('<%=cp%>/images/image_5.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">율전동</a></div>
                  <div><a href="#"><span class="icon-star"></span> 4.9</a></div>
                  <div><a href="#"><span class="icon-people"></span> 1</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 5</a></div>
                  <div><a href="#"><span class="icon-won"></span> 10,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">개구리 일하러오세요~</a></h3>
              </div>
            </div>
          </div>
          <div class="col-md-4 ftco-animate">
            <div class="blog-entry align-self-stretch">
              <a href="blog-single.html" class="block-20 rounded" style="background-image: url('<%=cp%>/images/image_6.jpg');">
              </a>
              <div class="text mt-3 text-center">
              	<div class="meta mb-2">
                  <div><a href="#">공릉동</a></div>
                  <div><a href="#"><span class="icon-star"></span> 4.4</a></div>
                  <div><a href="#"><span class="icon-people"></span> 5</a></div>
                  <div><a href="#" class="meta-chat"><span class="icon-chat"></span> 27</a></div>
                  <div><a href="#"><span class="icon-won"></span> 150,000~</a></div>
                </div>
                <h3 class="heading"><a href="#">개구리랑 놀이동산 파티룸 하나를 길쭉하게 쓰면 어떻게 되려나 이렇게 이렇게 이렇게 이렇게 길어져도?</a></h3>
              </div>
            </div>
          </div>
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
      </div>
    </section>
    
    
    
    
<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<!-- Footer -->
<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>
<div>
	<c:import url="${cp}/includes/footer_user.jsp"></c:import>
</div>
</body>
</html>