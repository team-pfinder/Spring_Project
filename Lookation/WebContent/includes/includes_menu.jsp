<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>includes_menu.jsp</title>
</head>
<body>

<!-- 맨위 로고, 검색창 -->
<!-- Header -->

<!-- nav -->
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

<!-- Menu -->
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


</body>
</html>