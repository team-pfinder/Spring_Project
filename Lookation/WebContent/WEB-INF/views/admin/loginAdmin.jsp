<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>

<c:import url="${cp}/includes/includes_admin.jsp"></c:import>
<title>관리자 로그인</title>

<script type="text/javascript">
$(document).ready(function()
		{
			$("#submitBtn").click(function()
			{
				$("#err").css("display", "none");

				if ($("#id").val() == "" || $("#pw").val() == "")
				{
					$("#err").html("This field is required.").css("display", "inline");
					return; //-- submit 액션 처리 중단
				}

				$("#adminloginForm").submit();
			});

		});
</script>

</head>

<body class="bg-gradient-primary">

	<div class="container">

		<!-- Outer Row -->
		<div class="row justify-content-center">

			<div class="col-xl--10 col-lg-6 col-md-19">

				<div class="card o-hidden border-0 shadow-lg my-5">
					<div class="card-body p-0">
					
						<!-- Nested Row within Card Body -->
						<div class="row">

							<div class="col-lg-12">
								<div class="p-5">
									<div class="text-center">
										<h1 class="h4 text-gray-900 mb-4">Welcome Admin!</h1>
									</div>
									<form action="adminlogin.action" method="post" id="adminloginForm">
										<div class="form-group">
											<input type="email" class="form-control form-control-user"
												id="id" aria-describedby="emailHelp"
												placeholder="ID">
										</div>
										<div class="form-group">
											<input type="password" class="form-control form-control-user"
												id="pw" placeholder="Password">
										</div>
										<div class="form-group">
											<div class="custom-control custom-checkbox small">
												<input type="checkbox" class="custom-control-input"
													id="customCheck"> <label
													class="custom-control-label" for="customCheck">Remember
													Me</label>
											</div>
										</div>
										<!-- <a href="index.html" -->
											<div id="submitBtn" class="btn btn-primary btn-user btn-block"> Login</div> <!-- </a> -->
											 <br><span id="err" style="color: red; display: none;"></span>
									</form>
									<hr>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>

	</div>
	<!-- end class container -->

</body>

</html>
