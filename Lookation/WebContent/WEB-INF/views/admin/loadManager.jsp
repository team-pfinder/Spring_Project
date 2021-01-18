<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>

<!DOCTYPE html>
<head>

<meta charset="UTF-8">
<c:import url="${cp}/includes/includes_admin.jsp"></c:import>

<title></title>
</head>

<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<c:import url="${cp}/includes/Admin_Sidebar.jsp"></c:import>

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<nav
					class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

					<!-- Sidebar Toggle (Topbar) -->
					<form class="form-inline">
						<button id="sidebarToggleTop"
							class="btn btn-link d-md-none rounded-circle mr-3">
							<i class="fa fa-bars"></i>
						</button>
					</form>

					<!-- Topbar Search -->
					<form
						class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
						<div class="input-group">
							<input type="text" class="form-control bg-light border-0 small"
								placeholder="Search for..." aria-label="Search"
								aria-describedby="basic-addon2">
							<div class="input-group-append">
								<button class="btn btn-primary" type="button">
									<i class="fas fa-search fa-sm"></i>
								</button>
							</div>
						</div>
					</form>

					<!-- Topbar Navbar -->
					<ul class="navbar-nav ml-auto">

						<!-- Nav Item - Search Dropdown (Visible Only XS) -->
						<li class="nav-item dropdown no-arrow d-sm-none"><a
							class="nav-link dropdown-toggle" href="#" id="searchDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-search fa-fw"></i>
						</a> <!-- Dropdown - Messages -->
							<div
								class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
								aria-labelledby="searchDropdown">
								<form class="form-inline mr-auto w-100 navbar-search">
									<div class="input-group">
										<input type="text"
											class="form-control bg-light border-0 small"
											placeholder="Search for..." aria-label="Search"
											aria-describedby="basic-addon2">
										<div class="input-group-append">
											<button class="btn btn-primary" type="button">
												<i class="fas fa-search fa-sm"></i>
											</button>
										</div>
									</div>
								</form>
							</div></li>

						<!-- Nav Item - Alerts -->
						<li class="nav-item dropdown no-arrow mx-1"><a
							class="nav-link dropdown-toggle" href="#" id="alertsDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-bell fa-fw"></i> <!-- Counter - Alerts -->
								<span class="badge badge-danger badge-counter">3+</span>
						</a> <!-- Dropdown - Alerts -->
							<div
								class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="alertsDropdown">
								<h6 class="dropdown-header">Alerts Center</h6>
								<a class="dropdown-item d-flex align-items-center" href="#">
									<div class="mr-3">
										<div class="icon-circle bg-primary">
											<i class="fas fa-file-alt text-white"></i>
										</div>
									</div>
									<div>
										<div class="small text-gray-500">December 12, 2019</div>
										<span class="font-weight-bold">A new monthly report is
											ready to download!</span>
									</div>
								</a> <a class="dropdown-item d-flex align-items-center" href="#">
									<div class="mr-3">
										<div class="icon-circle bg-success">
											<i class="fas fa-donate text-white"></i>
										</div>
									</div>
									<div>
										<div class="small text-gray-500">December 7, 2019</div>
										$290.29 has been deposited into your account!
									</div>
								</a> <a class="dropdown-item d-flex align-items-center" href="#">
									<div class="mr-3">
										<div class="icon-circle bg-warning">
											<i class="fas fa-exclamation-triangle text-white"></i>
										</div>
									</div>
									<div>
										<div class="small text-gray-500">December 2, 2019</div>
										Spending Alert: We've noticed unusually high spending for your
										account.
									</div>
								</a> <a class="dropdown-item text-center small text-gray-500"
									href="#">Show All Alerts</a>
							</div></li>

						<!-- Nav Item - Messages -->
						<li class="nav-item dropdown no-arrow mx-1"><a
							class="nav-link dropdown-toggle" href="#" id="messagesDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-envelope fa-fw"></i>
								<!-- Counter - Messages --> <span
								class="badge badge-danger badge-counter">7</span>
						</a> <!-- Dropdown - Messages -->
							<div
								class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="messagesDropdown">
								<h6 class="dropdown-header">Message Center</h6>
								<a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle"
											src="<%=cp %>/img/undraw_profile_1.svg" alt="">
										<div class="status-indicator bg-success"></div>
									</div>
									<div class="font-weight-bold">
										<div class="text-truncate">Hi there! I am wondering if
											you can help me with a problem I've been having.</div>
										<div class="small text-gray-500">Emily Fowler · 58m</div>
									</div>
								</a> <a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle"
											src="<%=cp %>/img/undraw_profile_2.svg" alt="">
										<div class="status-indicator"></div>
									</div>
									<div>
										<div class="text-truncate">I have the photos that you
											ordered last month, how would you like them sent to you?</div>
										<div class="small text-gray-500">Jae Chun · 1d</div>
									</div>
								</a> <a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle"
											src="<%=cp %>/img/undraw_profile_3.svg" alt="">
										<div class="status-indicator bg-warning"></div>
									</div>
									<div>
										<div class="text-truncate">Last month's report looks
											great, I am very happy with the progress so far, keep up the
											good work!</div>
										<div class="small text-gray-500">Morgan Alvarez · 2d</div>
									</div>
								</a> <a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle"
											src="https://source.unsplash.com/Mv9hjnEUHR4/60x60" alt="">
										<div class="status-indicator bg-success"></div>
									</div>
									<div>
										<div class="text-truncate">Am I a good boy? The reason I
											ask is because someone told me that people say this to all
											dogs, even if they aren't good...</div>
										<div class="small text-gray-500">Chicken the Dog · 2w</div>
									</div>
								</a> <a class="dropdown-item text-center small text-gray-500"
									href="#">Read More Messages</a>
							</div></li>

						<div class="topbar-divider d-none d-sm-block"></div>

						<!-- Nav Item - User Information -->
						<li class="nav-item dropdown no-arrow"><a
							class="nav-link dropdown-toggle" href="#" id="userDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <span
								class="mr-2 d-none d-lg-inline text-gray-600 small">Douglas
									McGee</span> <img class="img-profile rounded-circle"
								src="<%=cp %>/img/undraw_profile.svg">
						</a> <!-- Dropdown - User Information -->
							<div
								class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="userDropdown">
								<a class="dropdown-item" href="#"> <i
									class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> Profile
								</a> <a class="dropdown-item" href="#"> <i
									class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
									Settings
								</a> <a class="dropdown-item" href="#"> <i
									class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
									Activity Log
								</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#" data-toggle="modal"
									data-target="#logoutModal"> <i
									class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
									Logout
								</a>
							</div></li>

					</ul>

				</nav>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- Page Heading -->
					<h1 class="h3 mb-2 text-gray-800">충전신청 관리</h1>
					<p class="mb-4">이것은 관리자의 충전신청 관리 페이지이다.충전블라블라 블라블라충전씨부렁 충전 싸부렁</p>

					<!-- DataTales Example -->
					<div class="card shadow mb-4">
						<div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-primary">충전신청 리스트</h6>
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<thead>
										<tr align="center">
											<th>번호</th>
											<th>E-Mail</th>
											<th>계좌번호</th>
											<th>은행명</th>
											<th>예금주</th>
											<th>금액</th>
											<th>충전신청일</th>
											<th>처리상태</th>
											<th>처리일</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="item" items="${list }" varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td>${item.email}</td>
											<td>${item.bankNumber}</td>
											<td>${item.bank }</td>
											<td>${item.bankHolder }</td>
											<td>${item.amount }</td>
											<td>${item.regDate }</td>
											<td>${item.loadType }</td>
											<c:if></c:if>
											<td>${item.procDate }</td>
											<!-- <td>
												<button class="btn btn-primary" id="" value="">처리</button>
												<button class="btn btn-danger">반려</button>
											</td>-->
										</tr>
										</c:forEach>
									<!-- 	<tr align="center">
											<td>1</td>
											<td>dasd@test.com</td>
											<td>안혜지</td>
											<td>4564-4548-45151</td>
											<td>20000</td>
											<td>2020-11-12</td>
											<td>완료</td>
											<td>2020-11-12</td>
										</tr>
										<tr align="center">
											<td>2</td>
											<td>gdjsd@test.com</td>
											<td>김일웅</td>
											<td>2344-44557-100681</td>
											<td>40000</td>
											<td>2020-11-12</td>
											<td>완료</td>
											<td>2020-11-12</td>
										</tr>
										<tr align="center">
											<td>3</td>
											<td>dasd@test.com</td>
											<td>홍길동</td>
											<td>12314-64245-45245</td>
											<td>60000</td>
											<td>2020-11-12</td>
											<td>완료</td>
											<td>2020-11-12</td>
										</tr>
										<tr align="center">
											<td>4</td>
											<td>gfdkjno@test.com</td>
											<td>조윤상</td>
											<td>546723-11218-45151</td>
											<td>50000</td>
											<td>2020-11-12</td>
											<td>완료</td>
											<td>2020-11-12</td>
										</tr>
										<tr align="center">
											<td>5</td>
											<td>tgrry@test.com</td>
											<td>박길동</td>
											<td>487487-82585-4515</td>
											<td>80000</td>
											<td>2020-11-12</td>
											<td>완료</td>
											<td>2020-11-12</td>
										</tr>
										<tr align="center">
											<td>6</td>
											<td>nbvcn@test.com</td>
											<td>김길동</td>
											<td>345765-6348-145251</td>
											<td>90000</td>
											<td>2020-11-12</td>
											<td>완료</td>
											<td>2020-11-12</td>
										</tr>
										<tr align="center">
											<td>7</td>
											<td>dasd@test.com</td>
											<td>김길태</td>
											<td>73487-42218-1151151</td>
											<td>40000</td>
											<td>2020-11-12</td>
											<td>완료</td>
											<td>2020-11-12</td>
										</tr>
										<tr align="center">
											<td>8</td>
											<td>ktyj@test.com</td>
											<td>진영은</td>
											<td>566514-63628-08683</td>
											<td>80000</td>
											<td>2020-11-12</td>
											<td>완료</td>
											<td>2020-11-12</td>
										</tr>
										<tr align="center">
											<td>9</td>
											<td>yturty@test.com</td>
											<td>권소윤</td>
											<td>52624-24148-741151</td>
											<td>60000</td>
											<td>2020-11-12</td>
											<td>완료</td>
											<td>2020-11-12</td>
										</tr>
										<tr align="center">
											<td>10</td>
											<td>lgjsk@test.com</td>
											<td>마동탁</td>
											<td>95611-4551148-1115151</td>
											<td>70000</td>
											<td>2020-12-25</td>
											<td>대기</td>
											<td><button class="btn btn-primary" id="" value="">처리</button>
												<button class="btn btn-danger">반려</button></td>
										</tr>
										<tr align="center">
											<td>11</td>
											<td>quied@test.com</td>
											<td>김호진</td>
											<td>125485-87851-016574</td>
											<td>10000</td>
											<td>2020-12-25</td>
											<td>대기</td>
											<td><button class="btn btn-primary" id="" value="">처리</button>
												<button class="btn btn-danger">반려</button></td>
										</tr>
										<tr align="center">
											<td>12</td>
											<td>hswe@test.com</td>
											<td>김후진</td>
											<td>78944-16545-872357</td>
											<td>20000</td>
											<td>2020-12-26</td>
											<td>대기</td>
											<td><button class="btn btn-primary" id="" value="">처리</button>
												<button class="btn btn-danger">반려</button></td>
										</tr>
										<tr align="center">
											<td>13</td>
											<td>qqtedasd@test.com</td>
											<td>김휴진</td>
											<td>897489-7854-23844</td>
											<td>50000</td>
											<td>2020-12-26</td>
											<td>대기</td>
											<td><button class="btn btn-primary" id="" value="">처리</button>
												<button class="btn btn-danger">반려</button></td>
										</tr> -->
									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- End of Main Content -->

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
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>

	<!-- Logout Modal-->
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready
					to end your current session.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="login.html">Logout</a>
				</div>
			</div>
		</div>
	</div>

</body>

</html>