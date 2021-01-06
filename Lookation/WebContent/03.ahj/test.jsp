<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<head>

<%@ include file="../includes/includes_admin.jsp" %>
<title>검수처리1</title>

</head>

<body id="page-top">
  <!-- Page Wrapper -->
  <div id="wrapper">
  
  	<!-- Sidebar -->
 	<%@include file="../includes/Admin_Sidebar.jsp" %>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">
      
      <!-- header -->
      
      <%@include file="../includes/header.jsp" %>

				<!-- Begin Page Content -->
				<div class="container-fluid">
<div class="container">
		<div class="row">
			<div class=" ftco-animate">

				<h2 class="mb-3 font-weight-bold">파파존스 호러존 스튜디오</h2><hr>
				<p>공간설명이 들어갈 자리입니다.</p>
				<p>공간유형 : 파티룸</p>
				<p>예약 인원 : 최소 1명 ~ 최대 8명</p>


				<h2 class="mb-3 mt-5"># 예약 정보</h2><hr>
				<p>예약날짜 : 2020. 12. 31. (목) 12시 ~ 15시</p>
				<p>예약인원 : 1명</p>



				<h2 class="mb-3 mt-5"># 예약자 정보</h2><hr>
				<!-- 여기서 3:7로 나누고 싶다-->
				<form>
					<div class="row">
						<div class="col-lg-2 ftco-animate">
							<p>예약자명 (*)</p>
							<p>연락처 (*)</p>
							<p>이메일 (*)</p>
							<p>인원수 (*)</p>
							<p>요청사항</p>
						</div>


						<!-- 예약자 정보 입력란에 기존 회원정보 자동으로 불러옴 -->
						<!-- 수정 가능 -->
						<div class="col-lg-10 ftco-animate">
							<p>
								<input type="text" placeholder="예약자명" class="form-control-sm">
							</p>
							<p>
								<input type="tel" name="phone1" placeholder="010" class="form-control-sm"
									style="width: 50px;">-<input type="tel" name="phone2" class="form-control-sm"
									style="width: 50px;">-<input type="tel" name="phone3" class="form-control-sm"
									style="width: 50px;">
							</p>
							<p>
								<input type="text" class="form-control-sm">
							</p>
							<p>
								<input type="text" class="form-control-sm">
							</p>
							<p>
								<textarea cols="40" rows="5" class="form-control"
									placeholder="남기고 싶은 말을 적어주세요.(최대 100자)"></textarea>
							</p>
						</div>
					</div>
				</form>

				<h2 class="mb-3 mt-5"># 호스트 정보</h2><hr>
				<!-- 해당 공간의 호스트 사업자 정보 출력-->
				<p>공간상호 : 파파존스 파티룸</p>
				<p>대표자명 : 진영은</p>
				<p>소재지 : 주소주소 알아서뭐하게</p>
				<p>사업자번호 : 1234567890</p>
				<p>연락처 : 010-1234-1234, papajon@lookation.com</p>
				
				
				<h2 class="mb-3 mt-5"># 예약시 주의사항</h2><hr>
				<div class="row">
					<div class="col-lg-1 ftco-animate text-center flex">
						<p>1</p>
						<p>2</p>
						<p>3</p>
						<p>4</p>
						<p>5</p>
					</div>


					<div class="col-lg-10 ftco-animate">
						<!-- 해당 공간의 주의사항 출력-->
						<p>그냥 조심하세요. 이렇게 길게 쓴 주의사항도 담을 수 있는지 확인하는 중입니다. 이렇게 길게 쓴 주의사항도 담을 수 있는지 확인하는 중입니다.</p>
						<p>깨트리면 2배 보상</p>
						<p>마음의 상처도 보상하세요</p>
						<p>최대 10개까지 입력가능</p>
						<p>출력은 DB에 있는 만큼만!</p>
					</div>
				</div>
				
				<h2 class="mb-3 mt-5"># 환불 규정</h2><hr>
				<span>이용 1일전 23:59까지만 취소 가능</span>
				<span>이용당일(첫 날) 환불은 불가능합니다. 관련 사항은 호스트에게 직접 문의하세요.</span><br><br>
				<div class="row">
					<div class="col-lg-3 ftco-animate">
						<p>이용 7일전</p>
						<p>이용 6일전 ~ 1일전</p>
						<p>예약 당일</p>
					</div>


					<!-- 예약자 정보 입력란에 기존 회원정보 자동으로 불러옴 -->
					<!-- 수정 가능 -->
					<div class="col-lg-8 ftco-animate">
						<p>총 금액의 100% 환불</p>
						<p>총 금액의 50% 환불</p>
						<p>환불 불가</p>
					</div>
				</div>
				
				
				
				<!-- 버튼 클릭시 폼 내용 검증 -->
				<!-- 검증 완료시 submit 후 DB 예약내역, 결제내역 테이블에 추가 -->
				<div class="d-grid gap-2 mx-auto">
					<button class="btn btn-primary" type="button">동의하고 결제하기</button>
				</div>
				
				<!-- 맨 위로 올라가는 버튼 -->
								<!-- Scroll to Top Button-->
				<a class="scroll-to-top rounded" href="#page-top"> <i
					class="fas fa-angle-up"></i>
				</a>
			</div>

		</div>

	</div>
	<br><br>
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

						</div>
						<!-- End of Main Content -->


					</div>
					<!-- End of Content Wrapper -->



				<!-- Logout Modal-->
				<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
					aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">Ready to
									Leave?</h5>
								<button class="close" type="button" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
							</div>
							<div class="modal-body">Select "Logout" below if you are
								ready to end your current session.</div>
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
