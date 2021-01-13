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
<title>공지사항</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
</head>
<body>

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
           		<button type="submit" placeholder="" class="form-control search"><span class="fa fa-search"></span></button>
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
      			<p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.html">Home <i class="ion-ios-arrow-forward"></i></a></span> <span> My Page <i class="ion-ios-arrow-forward"></i></span></p>
        		<h1 class="mb-0 bread">공지사항</h1>
      		</div>
    	</div>
  	</div>
</section>

	<section class="ftco-section">
		<div class="container">
<!-- 		     <form action="#" class="search-form">
                <div class="form-group d-flex my-2 pt-0 float-right">
                  <span class="icon icon-search"></span>
                  <input type="text" class="form-control" placeholder="Type a keyword and hit enter">
                </div>
              </form> -->
	<!-- 검색창 ---------------------------------------->
			<%-- <div class="row" style="text-align: right;">
				<div class="col-lg-12">
					<form id="searchForm">
						<input type="text" name="keyword" value="${pageMaker.cri.keyword}"/>
						<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
						<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
						<button class="btn btn-primary btn-sm">Search</button>
					</form>
				</div>
			</div> --%>
		<!-- 검색창 -->
		<!-- 검색이나 정렬 관련 내용 -->
               <form action="#" class="subscribe-form">
                  <div class="form-group d-flex my-2 pt-0 float-right">
                     <input type="text" class="form-control" placeholder="검색어를 입력하세요.">
                      <input type="button" value="검색" class="submit">
                  </div>
                  <br>
               </form>
		
			<div class="row justify-content-center">
				<div class="col-md-12">
					<div class="wrapper">
						<div class="row no-gutters mb-5">
						
							<div class="card-body">
								<div align="center">
										
								</div>
								<div class="table-responsive">
									<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
										<thead>
											<tr align="center">
												<th>글 번호</th>
												<th>제목</th>
												<th>작성일자</th>
											</tr>
										</thead>
										<tbody>
											
											<tr align="center">
												<td>1</td>
												<td><a href="#">공지사항 다른 느낌으로 하기</a></td>
												<td>2021-01-10</td>
											</tr>
											
											<tr align="center">
												<td>2</td>
												<td><a href="#">공지사항 다른 느낌으로 하기</a></td>
												<td>2021-01-10</td>
											</tr>
											
											<tr align="center">
												<td>3</td>
												<td><a href="#">공지사항 다른 느낌으로 하기</a></td>
												<td>2021-01-10</td>
											</tr>
											
											<tr align="center">
												<td>4</td>
												<td><a href="#">공지사항 다른 느낌으로 하기</a></td>
												<td>2021-01-10</td>
											</tr>
											
											<tr align="center">
												<td>5</td>
												<td><a href="#">공지사항 다른 느낌으로 하기</a></td>
												<td>2021-01-10</td>
											</tr>
											
											<tr align="center">
												<td>6</td>
												<td><a href="#">공지사항 다른 느낌으로 하기</a></td>
												<td>2021-01-10</td>
											</tr>
											
	


										</tbody>

									</table>
																		
											<!-- 페이징 처리할 부분 -->

						
								</div>
									        <div class="row mt-2">
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
		</div><!-- End .col-md-10 -->
							</div>
						</div>
	
						
						
						
						<div class="row">
							<div class="col-md-3">
								<div class="dbox w-100 text-center">
									<div
										class="icon d-flex align-items-center justify-content-center">
										<span class="fa fa-map-marker"></span>
									</div>
									<div class="text">
										<p>
											<span>Address:</span> 198 West 21th Street, Suite 721 New
											York NY 10016
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="dbox w-100 text-center">
									<div
										class="icon d-flex align-items-center justify-content-center">
										<span class="fa fa-phone"></span>
									</div>
									<div class="text">
										<p>
											<span>Phone:</span> <a href="tel://1234567920">+ 1235
												2355 98</a>
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="dbox w-100 text-center">
									<div
										class="icon d-flex align-items-center justify-content-center">
										<span class="fa fa-paper-plane"></span>
									</div>
									<div class="text">
										<p>
											<span>Email:</span> <a href="mailto:info@yoursite.com">info@yoursite.com</a>
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="dbox w-100 text-center">
									<div
										class="icon d-flex align-items-center justify-content-center">
										<span class="fa fa-globe"></span>
									</div>
									<div class="text">
										<p>
											<span>Website</span> <a href="#">yoursite.com</a>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<footer class="footer">
		<div class="container-fluid px-lg-5">
			<div class="row">
				<div class="col-md-9 py-5">
					<div class="row">
						<div class="col-md-4 mb-md-0 mb-4">
							<h2 class="footer-heading">About us</h2>
							<p>A small river named Duden flows by their place and
								supplies it with the necessary regelialia.</p>
							<ul class="ftco-footer-social p-0">
								<li class="ftco-animate"><a href="#" data-toggle="tooltip"
									data-placement="top" title="Twitter"><span
										class="ion-logo-twitter"></span></a></li>
								<li class="ftco-animate"><a href="#" data-toggle="tooltip"
									data-placement="top" title="Facebook"><span
										class="ion-logo-facebook"></span></a></li>
								<li class="ftco-animate"><a href="#" data-toggle="tooltip"
									data-placement="top" title="Instagram"><span
										class="ion-logo-instagram"></span></a></li>
							</ul>
						</div>
						<div class="col-md-8">
							<div class="row justify-content-center">
								<div class="col-md-12 col-lg-10">
									<div class="row">
										<div class="col-md-4 mb-md-0 mb-4">
											<h2 class="footer-heading">Services</h2>
											<ul class="list-unstyled">
												<li><a href="#" class="py-1 d-block">Construction</a></li>
												<li><a href="#" class="py-1 d-block">House
														Renovation</a></li>
												<li><a href="#" class="py-1 d-block">Painting</a></li>
												<li><a href="#" class="py-1 d-block">Arhictecture
														Design</a></li>
											</ul>
										</div>
										<div class="col-md-4 mb-md-0 mb-4">
											<h2 class="footer-heading">About</h2>
											<ul class="list-unstyled">
												<li><a href="#" class="py-1 d-block">Staff</a></li>
												<li><a href="#" class="py-1 d-block">Team</a></li>
												<li><a href="#" class="py-1 d-block">Careers</a></li>
												<li><a href="#" class="py-1 d-block">Blog</a></li>
											</ul>
										</div>
										<div class="col-md-4 mb-md-0 mb-4">
											<h2 class="footer-heading">Resources</h2>
											<ul class="list-unstyled">
												<li><a href="#" class="py-1 d-block">Security</a></li>
												<li><a href="#" class="py-1 d-block">Global</a></li>
												<li><a href="#" class="py-1 d-block">Charts</a></li>
												<li><a href="#" class="py-1 d-block">Privacy</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row mt-md-5">
						<div class="col-md-12">
							<p class="copyright">
								<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
								Copyright &copy;
								<script>
									document.write(new Date().getFullYear());
								</script>
								All rights reserved | This template is made with <i
									class="ion-ios-heart" aria-hidden="true"></i> by <a
									href="https://colorlib.com" target="_blank">Colorlib.com</a>
								<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-3 py-md-5 py-4 aside-stretch-right pl-lg-5">
					<h2 class="footer-heading">Request A Quote</h2>
					<form action="#" class="contact-form">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Your Name">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Your Email">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Subject">
						</div>
						<div class="form-group">
							<textarea name="" id="" cols="30" rows="3" class="form-control"
								placeholder="Message"></textarea>
						</div>
						<div class="form-group">
							<button type="submit" class="form-control submit px-3">Send</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</footer>



	<!-- loader -->
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
			<circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
			<circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>

<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>

	<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>

</body>
</html>