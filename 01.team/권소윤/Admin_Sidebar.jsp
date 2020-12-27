<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- Sidebar -->
	<ul
		class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
		id="accordionSidebar">

		<!-- Sidebar - Brand -->
		<a
			class="sidebar-brand d-flex align-items-center justify-content-center"
			href="index.html">
			<div class="sidebar-brand-icon rotate-n-15">
				<i class="fas fa-laugh-wink"></i>
			</div>
			<div class="sidebar-brand-text mx-3">
				Pfinder Admin <sup></sup>
			</div>
		</a>

		<!-- Divider -->
		<hr class="sidebar-divider my-0">

		<!-- Nav Item - Dashboard -->
		<li class="nav-item"><a class="nav-link" href="index.html"> <i
				class="fas fa-fw fa-tachometer-alt"></i> <span>Dashboard</span></a></li>

		<!-- Divider -->
		<hr class="sidebar-divider">

		<!-- Heading -->
		<div class="sidebar-heading">Interface</div>

		<!-- Nav Item - Pages Collapse Menu -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#collapseTwo"
			aria-expanded="true" aria-controls="collapseTwo"> <i
				class="fas fa-fw fa-cog"></i> <span>Components</span>
		</a>
			<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
				data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">Custom Components:</h6>
					<a class="collapse-item" href="buttons.html">Buttons</a> <a
						class="collapse-item" href="cards.html">Cards</a>
				</div>
			</div></li>

		<!-- Nav Item - Utilities Collapse Menu -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#collapseUtilities"
			aria-expanded="true" aria-controls="collapseUtilities"> <i
				class="fas fa-fw fa-wrench"></i> <span>Utilities</span>
		</a>
			<div id="collapseUtilities" class="collapse"
				aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">Custom Utilities:</h6>
					<a class="collapse-item" href="utilities-color.html">Colors</a> <a
						class="collapse-item" href="utilities-border.html">Borders</a> <a
						class="collapse-item" href="utilities-animation.html">Animations</a>
					<a class="collapse-item" href="utilities-other.html">Other</a>
				</div>
			</div></li>

		<!-- Divider -->
		<hr class="sidebar-divider">



		<!-- Heading -->
		<div class="sidebar-heading">관리</div>


		<!-- 공지 관리 -->
		<!-- Nav Item - Pages Collapse Menu -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#collapsePages1"
			aria-expanded="true" aria-controls="collapsePages"> <i
				class="fas fa-fw fa-folder"></i> <span>공지관리</span>
		</a>
			<div id="collapsePages1" class="collapse"
				aria-labelledby="headingPages" data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">도움말:</h6>
					<a class="collapse-item" href="login.html">도움말 목록</a> <a
						class="collapse-item" href="register.html">도움말 등록</a>
					<div class="collapse-divider"></div>
					<h6 class="collapse-header">공지사항:</h6>
					<a class="collapse-item" href="404.html">공지사항 목록</a> <a
						class="collapse-item" href="blank.html">공지사항 등록</a>
				</div>
			</div></li>

		<!-- 신고관리 -->
		<!-- Nav Item - Pages Collapse Menu -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#collapsePages2"
			aria-expanded="true" aria-controls="collapsePages"> <i
				class="fas fa-fw fa-folder"></i> <span>신고관리</span>
		</a>
			<div id="collapsePages2" class="collapse"
				aria-labelledby="headingPages" data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">신고:</h6>
					<a class="collapse-item" href="DeclarationManager.jsp">신고처리</a> <a
						class="collapse-item" href="DeclarationList.jsp">신고내역</a> <a
						class="collapse-item" href="BlindList.jsp">블라인드 관리</a> <a
						class="collapse-item" href="BlackListManager.jsp">블랙리스트 관리</a>
				</div>
			</div></li>

		<!-- 마일리지 관리 -->
		<li class="nav-item"><a class="nav-link collapsed" href="#"
			data-toggle="collapse" data-target="#collapsePages4"
			aria-expanded="true" aria-controls="collapsePages"> <i
				class="fas fa-fw fa-folder"></i> <span>마일리지 관리</span>
		</a>
			<div id="collapsePages4" class="collapse"
				aria-labelledby="headingPages" data-parent="#accordionSidebar">
				<div class="bg-white py-2 collapse-inner rounded">
					<h6 class="collapse-header">마일리지관리:</h6>
					<a class="collapse-item" href="Mileage_ChargeList(admin).jsp">충전관리</a> <a
						class="collapse-item" href="Mileage_ExchangeList(admin).jsp">환전관리</a> <a
						class="collapse-item" href="forgot-password.html">정산관리</a>
				</div>
			</div></li>

		<!-- Nav Item - Tables -->
		<li class="nav-item active"><a class="nav-link"
			href="tables.html"> <i class="fas fa-fw fa-table"></i> <span>Tables</span></a>
		</li>

		<!-- Divider -->
		<hr class="sidebar-divider d-none d-md-block">

		<!-- Sidebar Toggler (Sidebar) -->
		<div class="text-center d-none d-md-inline">
			<button class="rounded-circle border-0" id="sidebarToggle"></button>
		</div>

	</ul>
	<!-- End of Sidebar -->


</body>
</html>