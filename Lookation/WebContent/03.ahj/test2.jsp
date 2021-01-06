<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();
%>
<!DOCTYPE html>

<head>

  <%@ include file="../includes/includes_admin.jsp" %>
  <title>검수처리2</title>
  <style type="text/css">
  
.blog .carousel-indicators {
	left: 0;
	top: auto;
    bottom: -40px;

}

/* The colour of the indicators */
.blog .carousel-indicators li {
    background: #a3a3a3;
    border-radius: 50%;
    width: 8px;
    height: 8px;
}

.blog .carousel-indicators .active {
background: #707070;
}
  </style>
  <script type="text/javascript">
	$('#blogCarousel').carousel({
		interval: 5000
});
  
  </script>
 

</head>

<body>
<div class="container">
            <div class="row blog">
                <div class="col-md-12">
                    <div id="blogCarousel" class="carousel slide" data-ride="carousel">

                        <ol class="carousel-indicators">
                            <li data-target="#blogCarousel" data-slide-to="0" class="active"></li>
                            <li data-target="#blogCarousel" data-slide-to="1"></li>
                        </ol>

                        <!-- Carousel items -->
                        <div class="carousel-inner">

                            <div class="carousel-item active">
                                <div class="row">
                                    <div class="col-md-3">
                                        <a href="#">
                                            <img src="http://placehold.it/250x250" alt="Image" style="max-width:100%;">
                                        </a>
                                    </div>
                                    <div class="col-md-3">
                                        <a href="#">
                                            <img src="http://placehold.it/250x250" alt="Image" style="max-width:100%;">
                                        </a>
                                    </div>
                                    <div class="col-md-3">
                                        <a href="#">
                                            <img src="http://placehold.it/250x250" alt="Image" style="max-width:100%;">
                                        </a>
                                    </div>
                                    <div class="col-md-3">
                                        <a href="#">
                                            <img src="http://placehold.it/250x250" alt="Image" style="max-width:100%;">
                                        </a>
                                    </div>
                                </div>
                                <!--.row-->
                            </div>
                            <!--.item-->

                            <div class="carousel-item">
                                <div class="row">
                                    <div class="col-md-3">
                                        <a href="#">
                                            <img src="http://placehold.it/250x250" alt="Image" style="max-width:100%;">
                                        </a>
                                    </div>
                                    <div class="col-md-3">
                                        <a href="#">
                                            <img src="http://placehold.it/250x250" alt="Image" style="max-width:100%;">
                                        </a>
                                    </div>
                                    <div class="col-md-3">
                                        <a href="#">
                                            <img src="http://placehold.it/250x250" alt="Image" style="max-width:100%;">
                                        </a>
                                    </div>
                                    <div class="col-md-3">
                                        <a href="#">
                                            <img src="http://placehold.it/250x250" alt="Image" style="max-width:100%;">
                                        </a>
                                    </div>
                                </div>
                                <!--.row-->
                            </div>
                            <!--.item-->

                        </div>
                        <!--.carousel-inner-->
                    </div>
                    <!--.Carousel-->

                </div>
            </div>
</div>

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Your Website 2020</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- 메인 페이지 -->
  </div>
  <!-- 전체 페이지 -->
  
  

 
</body>

</html>
