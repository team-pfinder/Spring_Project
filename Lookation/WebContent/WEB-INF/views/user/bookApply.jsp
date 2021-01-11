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
<title>예약 신청화면.jsp</title>
<c:import url="${cp}/01.ksb/head(user).jsp"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">

	.res-form-box
	{
	background-color: #FFF;
	}
	
	.div-table{
	display: table;
	width: 100%;
	}
	
	.div-table-body {
	display: table-row-group;
	}
	

	.div-row {
	display: table-row;
	}
	
	.div-col{
	display: table-cell;
	padding: 3px 10px;
	}
	
	.notice{
	padding: 3px 17px 13px;
	}
	
	.det{
	max-width: 150px;
	}
	
	.btn-gon {
    color: #FFF;
    background-color: #00043c;
    }
    
    .btn-gon:hover {
    color: #FFF;
    background-color: #00043cdb;
    }
    
    /*=== 제목 밑줄 ===*/
	.divider:after {
    content: "";
    display: block;
    border-bottom: 4px solid #00043c;
    margin: 10px 0px;
	}
	
</style>
</head>
<body>

<section class="hero-wrap hero-wrap-2" style="background-image: url('<%=cp%>/images/bg_3.jpg');" data-stellar-background-ratio="0.5">
  	<div class="overlay"></div>
  	<div class="container">
    	<div class="row no-gutters slider-text align-items-end">
      		<div class="col-md-9 ftco-animate pb-5">
      			<p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.html">Home <i class="ion-ios-arrow-forward"></i></a></span> <span><a href="#">Space <i class="ion-ios-arrow-forward"></i></a></span></p>
        		<h1 class="mb-0 bread">선택한 공간 예약하기</h1>
      		</div>
    	</div>
  	</div>
</section>


<section class="ftco-section ftco-degree-bg">
	<div class="container">
		<div class="row">
			<div class="col-lg-8 w-100 p-md-5 res-form-box">
				
				<p class="ftco-animate">
					<img src="<%=cp%>/images/image_1.jpg" alt="템플릿 이미지" class="img-fluid">
				</p>
				
				<h2 class="mb-3 mt-5 font-weight-bold"># 공간 정보</h2><hr>
				<div class="form-inline form-group">
					<p class="col-md-2">공간설명</p>
					<p class="col-md-10">공간설명 들어갈 자리</p>
				</div>
				
				<div class="form-inline form-group">
					<p class="col-md-2">공간유형</p>
					<p class="col-md-10">파티룸</p>
				</div>
				
				<div class="form-inline form-group">
					<p class="col-md-2">예약인원</p>
					<p class="col-md-10">최소 1명 ~ 최대 8명</p>
				</div>

				<h2 class="mb-3 mt-5 font-weight-bold"># 예약 정보</h2><hr>
				
				<div class="form-inline form-group">
					<p class="col-md-2">패키지명</p>
					<p class="col-md-10">알록달록머시기패키지</p>
				</div>
				
				<div class="form-inline form-group">
					<p class="col-md-2">예약날짜</p>
					<p class="col-md-10">2020. 12. 31. (목) 12시 ~ 15시</p>
				</div>
				
				<div class="form-inline form-group">
					<p class="col-md-2">예약인원</p>
					<p class="col-md-10">1명</p>
				</div>

				<h2 class="mb-3 mt-5 font-weight-bold"># 예약자 정보</h2><hr>
				<form class="ftco-animate">
					<!-- 체크박스 클릭시 예약자 정보 입력란에 기존 회원정보 자동으로 불러옴
						 수정 가능 -->
					<div class="text-right mr-3">
						<input type="checkbox" id="loadUserInfo" class="form-control-m">
						<label for="loadUserInfo">입력한 회원정보와 동일</label><br>
					</div>
						 
					<div class="div-table">
						<div class="div-table-body">
							<div class="div-row">
								<div class="div-col"><label for="1" class="control-label"><small class="text-danger">(*)</small>예약자명</label></div>
								<div class="div-col">
									<input type="text" id="1" class="form-control" placeholder="예약자명"
										   required="required" maxlength="10">
								</div>
							</div>
							
							<div class="div-row">
								<div class="div-col"><label for="2" class="control-label"><small class="text-danger">(*)</small>이메일</label></div>
								<div class="div-col">
									<div class="form-inline form-group">
										<input type="text" id="2" placeholder="이메일" class="form-control"
												required="required" style="width: 170px;">@
										<input type="text" placeholder="@xxx.com" class="form-control"
												required="required" style="width: 200px;">
									</div>
								</div>
							</div>
							
							<div class="div-row">
								<div class="div-col"><label for="3" class="control-label"><small class="text-danger">(*)</small>연락처</label></div>
								<div class="div-col">
									<div class="form-inline form-group">
										<!-- 숫자만 입력하도록 정규식 검증 -->
										<input type="tel" id="3" class="form-control mr-2" style="width: 60px;" maxlength="3">
										 - <input type="tel" class="form-control mx-2" style="width: 80px;" maxlength="4">
										 - <input type="tel" class="form-control mx-2" style="width: 80px;" maxlength="4">
									</div>
								</div>
							</div>
							
							<div class="div-row">
								<div class="div-col"><label for="4" class="control-label"><small class="text-danger">(*)</small>요청사항</label></div>
								<div class="div-col">
									<textarea class="form-control col-md-9" name="message"
							placeholder="남기고 싶은 말을 적어주세요.(최대 100자)" maxlength="100"></textarea>
								</div>
							</div>
							
						</div><!-- End .div-table-body -->
					</div><!-- End .div-table -->
						
				<h2 class="mb-3 mt-5 font-weight-bold"># 호스트 정보</h2><hr>
				<!-- 해당 공간의 호스트 사업자 정보 출력-->
				
				
				<div class="hostInfo ftco-animate">
					<div class="form-inline form-group">
						<p class="col-md-2">공간상호</p>
						<p class="col-md-10">파파존스 파티룸</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2">대표자명</p>
						<p class="col-md-10">진영은</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2">소재지</p>
						<p class="col-md-10">서울특별시 은평구 땡땡동 주소주소</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2">사업자번호</p>
						<p class="col-md-10">123-45-67890</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2 align-self-start">연락처</p>
						<div class="col-md-10">
							<p>010-1234-1234</p>
							<p>papajon@lookation.com</p>
						</div>
					</div>
				</div>
				
				
				<h2 class="mb-3 mt-5 font-weight-bold"># 예약시 주의사항</h2><hr>
				<div class="ftco-animate">
					<div class="div-table">
						<div class="div-table-body">
							<div class="div-row">
								<div class="div-col font-weight-bold notice">1</div>
								<div class="div-col">주의사항은 최대 50자까지 작성할 수 있습니다. 주의사항 50자가 얼마나 가는지 봐야 합니.</div>
							</div>
							
							<div class="div-row">
								<div class="div-col font-weight-bold notice">2</div>
								<div class="div-col">물건을 깨트리면 보상하세요!</div>
							</div>
							
							<div class="div-row">
								<div class="div-col font-weight-bold notice">3</div>
								<div class="div-col">출력시에는 DB에 있는 만큼만 출력되도록</div>
							</div>
							
							<div class="div-row">
								<div class="div-col font-weight-bold notice">4</div>
								<div class="div-col">숫자는 자동으로 증가... 중간 번호의 주의사항이 이렇게 줄을 넘어가도 정렬이 유지되는지 확인중</div>
							</div>
							
							<div class="div-row">
								<div class="div-col font-weight-bold notice">5</div>
								<div class="div-col">안녕하세요</div>
							</div>
							
						</div><!-- End .div-table-body -->
					</div><!-- End .div-table -->
				</div>
				
				<h2 class="mb-3 mt-5 font-weight-bold"># 환불 규정</h2><hr>
				
				
				<div class="ftco-animate">
					<div class="memo">
						<span class="text-body">이용 1일전 23:59까지만 취소 가능.</span>
						<span class="text-danger">이용당일(첫 날) 환불은 불가능합니다. 관련 사항은 호스트에게 직접 문의하세요.</span><br><br>
					</div>
			
					
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
				</div>
				
				
				<h2 class="mb-3 mt-5 font-weight-bold"># 서비스 동의 </h2> <hr>
				
				
				<div class="ftco-animate">
					<span>약관 블라블라~</span>
					
					<!-- 체크박스 크기 조절  -->
					<!-- 전체동의 클릭시 아래의 체크박스 4개 동시 체크 -->
					<div class="text-right">
						<input type="checkbox" id="allConfirm" class="checkbox">
						<label for="allConfirm">전체 동의</label><br></div>
					<div>
					
					<!-- 전부 다 체크했는지 확인 -->
					<p><input type="checkbox"> <small class="text-danger">(필수)</small> 위 공간의 예약조건 확인 및 결제진행 동의</p>
					<p><input type="checkbox"> <small class="text-danger">(필수)</small> 환불규정 안내에 대한 동의</p>
					<p><input type="checkbox"> <small class="text-danger">(필수)</small> 개인정보 제3자 제공 동의</p>
					<p><input type="checkbox"> <small class="text-danger">(필수)</small> 개인정보 수집 및 이용 동의</p>
					<small class="text-danger"><span class="fa fa-check mr-2"></span> 서비스 이용약관 동의는 필수입니다.</small>
					</div>
					<br><br>
						
					<!-- 버튼 클릭시 폼 내용 검증 -->
					<!-- 검증 완료시 submit 후 DB 예약내역, 결제내역 테이블에 추가 -->
					<div class="row">
						<button class="btn btn-primary btn-block" type="button">동의하고 결제하기</button>
					</div>
					
					<!-- 맨 위로 올라가는 버튼 만들기 -->
				
				</div>
			</div>

			<!-- 오른쪽 사이드바 -->
			<div class="col-lg-4 sidebar pl-lg-5 ftco-animate">
				<div class="sidebar-box p-4 ftco-animate">
					<div class="categories">
						<h3>결제 예정금액</h3>
						<div class="divider" style="width: 120px;"></div>
						<!-- 이전 페이지(locDetail.jsp)에서 정보 받아옴 -->
						<div class="div-table">
							<div class="div-table-body">
								<div class="div-row">
									<div class="div-col font-weight-bold">예약날짜</div>
									<div class="div-col text-right det">2020.12.31(목)</div>
								</div>
								
								<div class="div-row">
									<div class="div-col font-weight-bold">패키지명</div>
									<div class="div-col text-right det">최대 20자까지 작성할 수 있는 패키지</div>
								</div>
								
								<div class="div-row">
									<div class="div-col font-weight-bold">예약시간</div>
									<div class="div-col text-right det">12시 ~ 15시(3시간)</div>
								</div>
								
								<div class="div-row">
									<div class="div-col font-weight-bold">예약인원</div>
									<div class="div-col text-right det">5명</div>
								</div>
								
							</div><!-- End .div-table-body -->
						</div><!-- End .div-table -->
						<div class="divider my-3"></div>
						<div class="text-right">
							<h3><span class="icon-won"></span>  60,000 원</h3>
						</div>
						<br>
						

					</div>
				</div>
			</div><!-- 오른쪽 사이드바 끝 -->
		</div>
	</div>	
</section>





	
<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<div>
	<c:import url="${cp}/includes/footer_user.jsp"></c:import>
</div>
</body>
</html>