<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp_include = request.getContextPath();
%>


<!-- 관리자 페이지 부트스트랩, 스타일 파일들의 집합 -->
<!-- #00. Admin_sb -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<!-- Custom fonts for this template -->
<link href="<%=cp_include%>/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<%=cp_include%>/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="<%=cp_include%>/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">



<!-- Bootstrap core JavaScript-->
<script src="<%=cp_include %>/vendor/jquery/jquery.min.js"></script>
<script src="<%=cp_include %>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="<%=cp_include %>/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="<%=cp_include %>/js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script src="<%=cp_include %>/vendor/datatables/jquery.dataTables.min.js"></script>
<script src="<%=cp_include %>/vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="<%=cp_include %>/js/demo/datatables-demo.js"></script>


