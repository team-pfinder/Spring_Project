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
<%-- <%@ include file="../01.ksb/head(user).jsp" %> --%>
<%@ include file="../includes/includes_home.jsp" %>
</head>
<body>

<!-- 맨위 로고, 검색창 -->
<!-- Header -->
<div class="container pt-5">
	<div class="row justify-content-between">
		<div class="col">
			<a class="navbar-brand" href="index.html">Look<span>ation.</span></a>
		</div>
		<div class="col d-flex justify-content-end">
			<div class="social-media">
	    		<p class="mb-0 d-flex">
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-facebook"><i class="sr-only">Facebook</i></span></a>
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-twitter"><i class="sr-only">Twitter</i></span></a>
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-instagram"><i class="sr-only">Instagram</i></span></a>
	    			<a href="#" class="d-flex align-items-center justify-content-center"><span class="fa fa-dribbble"><i class="sr-only">Dribbble</i></span></a>
	    		</p>
       		</div>
       </div>
	</div>
</div>

<nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
	<div class="container">
    	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="fa fa-bars"></span> Menu
      	</button>
      	
		<form action="#" class="searchform order-lg-last">
        	<div class="form-group d-flex">
           		<input type="text" class="form-control pl-3" placeholder="Search">
           		<button type="submit" class="form-control search"><span class="fa fa-search"></span></button>
         	</div>
       	</form>
      	<div class="collapse navbar-collapse" id="ftco-nav">
        	<ul class="navbar-nav mr-auto">
        		<li class="nav-item"><a href="index.html" class="nav-link">Home</a></li>
        		<li class="nav-item"><a href="about.html" class="nav-link">About</a></li>
        		<li class="nav-item"><a href="team.html" class="nav-link">Our team</a></li>
        		<li class="nav-item"><a href="project.html" class="nav-link">Project</a></li>
        		<li class="nav-item active"><a href="blog.html" class="nav-link">Blog</a></li>
          		<li class="nav-item"><a href="contact.html" class="nav-link">Contact</a></li>
        	</ul>
      	</div>
      	
    </div>
</nav>
<!-- END nav -->

<section class="hero-wrap hero-wrap-2" style="background-image: url('<%=cp%>/images/bg_3.jpg');" data-stellar-background-ratio="0.5">
  	<div class="overlay"></div>
  	<div class="container">
    	<div class="row no-gutters slider-text align-items-end">
      		<div class="col-md-9 ftco-animate pb-5">
      			<p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.html">Home <i class="ion-ios-arrow-forward"></i></a></span> <span><a href="myPage.html"> My Page <i class="ion-ios-arrow-forward"></i></a></span>
      			<span><a href="index.html"> 작성글 리스트 <i class="ion-ios-arrow-forward"></i></a></span></p>
        		<h1 class="mb-0 bread">리뷰</h1>
      		</div>
    	</div>
  	</div>
</section>


<!-- 마이페이지에서 작성글 리스트 > 리뷰 선택해 들어온 모습 -->
<!-- ※ 수정해야할 부분 : 검색창 -->
<!--  -->

<!-- container 시작 -->
<div class="container">
	<br>
	<div class="row">
		<!-- Page Heading -->
		<div class="memo">
			<h1 class="mb-2 text-gray-800">내가 작성한 글</h1>
			<p class="mb-4">
				<!-- 작성자 닉네임 출력 -->
				<span class="text-primary">거북이</span>님의 작성글입니다. <span>행 클릭시
					해당 게시글이 있는 위치로 이동합니다 </span><a target="_blank" href="#"> 이전으로</a>.
			</p>
		</div><!-- End .memo -->

			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-dark">작성글보기(리뷰)</h6>
				</div>
				<div class="card-body">
					<!-- 검색이나 정렬 관련 내용 -->
					<!-- ※ 여기 세개가 안붙음 -->
					<div class="col-md-5 d-flex float-right">
						<form action="" class="subscribe-form">
							<div class="input-group">
								<select class="custom-select border-0 my-2">
									<option selected="selected">전체</option>
									<option>공간명</option>
									<option>내용</option>
								</select>
								<div class="form-group d-flex">
									<input type="text" class="form-control"
										placeholder="검색어를 입력하세요.">
								</div>
								<div class="input-group-append">
									<button class="btn btn-primary">검색</button>
								</div>
							</div><!-- End .input-group -->
							
							
						</form>
						<br><br>
					</div><!-- End .col-md-5 -->


					<div class="table-responsive align-self-center">
						<table class="table table-bordered table-hover" id="dataTable"
							width="100%" cellspacing="0">

							<!-- 예약정보 조회 및 검색 -->
							<thead>
								<tr>
									<th>#</th>
									<th>공간명</th>
									<th>내용</th>
									<th>작성일</th>
									<th></th>
								</tr>
							</thead>
							<tbody data-link="row" class="rowlink">
								<tr align="center">
									<td>189</td>
									<td>바다</td>
									<!-- 리뷰내용은 첫줄만 표시 -->
									<td>웃풍이 많이 불어 추웠습니다...</td>
									<!-- 글 수정시 작성일은 수정일로 변경(update)된다. -->
									<td>2021-01-01</td>
									<td>
										<!-- 글수정버튼 -->
										<button type="button" class="btn-xs btn-warning rounded border-0">수정</button>
										<!-- 글삭제버튼 -->
										<button type="button" class="btn-xs btn-dark rounded border-0">삭제</button>
										<!-- ※ 이동방법 결정 -->
										<!-- 1. 해당 행 클릭시 해당 글로 이동 -->
										<!-- 2. 수정, 삭제버튼 클릭시 각각 수정창, 삭제창 뜨게 -->
									</td>
								</tr>
								<tr align="center">
									<td>123</td>
									<td>공간이이렇게길면</td>
									<td>난방이 잘 되어 있어 좋았습니다... 더보기</td>
									<td>2021-07-14</td>
									<td>
										<button type="button" class="btn-xs btn-warning rounded border-0">수정</button>
										<button type="button" class="btn-xs btn-dark rounded border-0">삭제</button>
									</td>
								</tr>
								<tr align="center">
									<td>111</td>
									<td>파파존스 호러 어쩌구 저쩌구</td>
									<td>역시 제일 무서웠습니다. 다들 가세요</td>
									<td>2021-07-12</td>
									<td>
										<button type="button" class="btn-xs btn-warning rounded border-0">수정</button>
										<button type="button" class="btn-xs btn-dark rounded border-0">삭제</button>
									</td>
								</tr>
								<tr align="center">
									<td>109</td>
									<td>후기가 너무 많은 공간</td>
									<td>별점 5점 만점에 5점입니다...더보기</td>
									<td>2021-03-14</td>
									<td>
										<button type="button" class="btn-xs btn-warning rounded border-0">수정</button>
										<button type="button" class="btn-xs btn-dark rounded border-0">삭제</button>
									</td>
								</tr>
								<tr align="center">
									<td>88</td>
									<td>브라이덜샤워</td>
									<td>브라이덜 샤워를 하기 쾌적한 ... 더보기 </td>
									<td>2021-02-14</td>
									<td>
										<button type="button" class="btn-xs btn-warning rounded border-0">수정</button>
										<button type="button" class="btn-xs btn-dark rounded border-0">삭제</button>
									</td>
								</tr>
								<tr align="center">
									<td>72</td>
									<td>루프탑 빌려주는 곳</td>
									<td>냉장고가 있었거든요 ... 더보기</td>
									<td>2021-02-14</td>
									<td>
										<button type="button" class="btn-xs btn-warning rounded border-0">수정</button>
										<button type="button" class="btn-xs btn-dark rounded border-0">삭제</button>
									</td>
								</tr>
								<tr align="center">
									<td>52</td>
									<td>새해맞이공간</td>
									<td>새해 맞이로 다녀왔습니다.</td>
									<td>2021-01-01</td>
									<td>
										<button type="button" class="btn-xs btn-warning rounded border-0">수정</button>
										<button type="button" class="btn-xs btn-dark rounded border-0">삭제</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div><!-- End .cardBody -->
			</div><!-- End .card -->
		</div><!-- End .row -->
		
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
	<br><br><br><br>
	
	
</div><!-- .container End -->
	
<!-- loader -->
<div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>

<%@ include file="../includes/includes_home_end.jsp" %>

<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>

</body>
</html>
</body>
</html>