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
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>locationManagementPolicy.jsp</title>

<style>
	
	div.container div
	{
		font-size: 10pt;
	}
	
	div.container  p
	{
		color: #fdbe34;
		font-weight: bold;
		font-size: 15pt;
	}

</style>

<!-- css 등 공통적으로 들어가는 요소 include -->
<c:import url="${cp }/includes/includes_home.jsp"></c:import>

</head>
<body>
	
	<c:choose>
		<c:when test = "${identify eq 'member'}">
			<!-- include header_user.jsp -->
			<div>
				<c:import url="${cp}/includes/header_user.jsp"></c:import>
			</div>
		</c:when>
		<c:when test = "${identify eq 'host'}">
			<!-- include header_host.jsp -->
			<div>
				<c:import url="${cp}/includes/header_host.jsp"></c:import>
			</div>
		</c:when>
	</c:choose>	
	
	
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"><a href="index.html">Services
						<i class="ion-ios-arrow-forward"></i></a></span>
					</p>
					<h1 class="mb-0 bread">공간관리정책</h1>
				</div>
			</div>
		</div>
	</section>

	<!-- 정책 본문 -->
	<div class="container" style="margin: 10% 10% 10% 10%;">
		<div>
			본 정책은 Lookation 회원 이용약관에 따라 작성되었으며 본 정책에서
			사용되는 용어의 정의는 Lookation 회원 이용약관과 동일합니다.
		</div>
		<br>
		
		<div>
			<p>1. 공간등록</p>
			- 사이트가 정한 양식에 맞게 작성하면 공간이 등록됩니다. 단, 검수과정을 거쳐야 하기 때문에
			바로 예약을 받을 수 있는 것은 아니며, 검수가 승인이 완료되면 최종적으로 사이트에서 이용이 가능합니다. <br>
			<br>
			- 공간등록 입력 정보<br>
			(i) 기본 정보<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 공간 유형 (파티룸, 브라이덜샤워, 루프탑, 클럽, 엠티(워크샵) 중 선택) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 공간 명 (최소 2자, 최대 20자. '(', ')', '[', ']', '{', '}', ',', '.' 만 사용가능 ) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 공간 한줄 소개 (최소 4자, 최대 100자)<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 공간 소개 (최소 20자, 최대 1333자)<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 시설 안내 (최소 10자, 최대 50자, 추가 버튼으로 최대 10개까지 추가 가능)<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 주의 사항 (죄소 20자, 최대 50자, 추가 버튼으로 최대 10개까지 추가 가능)<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 대표이미지(썸네일) (2장 이상 불가능, 최대 5mb, 최대 해상도 2048*1158, .jpg, .jpeg, .png 확장자만 가능)<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 주소<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 상세 주소<br>
			<br>
			(ii) 상세 정보<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 이미지 (최소 1장, 최대 10장, 최대 3mb, 최대해상도 2048*1158, .jpg, .jpeg, .png 확장자만 가능) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 최소수용인원 (최소1, 최대 10) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 최대수용인원 (최소수용인원 이상, 최대 30) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 공간 관련 웹사이트 (url 형태) <br>
			<br>
			(iii) 패키지<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 패키지명 (최소2자, 최대 20자) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 시작시각 <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 종료시각 <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;→ 0시 ~ 익일 10시 까지 입력 가능, 패키지당 최소 이용시간 3시간 <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 패키지 적용 날짜 (캘린더에서 패키지 추가 및 삭제 가능, 패키지가 등록되지 않은 날짜는 자동 휴무처리) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;→ 당일 날짜를 기준으로 3개월 이후 스케줄까지 적용이 가능
			<br>
			(iv) 가격 정보<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 패키지별 가격 정보 (부가세 포함가 필수입력 안내, 최소 1,000원 최대 1,000,000원) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 환불정책 (Lookation에서 제공하는 환불정책 사용)<br>
			<br>
			
			(v) 연락처<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 이메일 (xxxx@xxx.xxx 형태로 작성) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 휴대전화 <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 대표전화<br>
			<br>
			
			(vi) 이용 안내<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 이용시간 (시작시각 ~ 종료시각 선택 가능) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 정기휴무 <br>
			<br>
			
			(vii) 사업자 정보<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 상호명 (최소 2자, 최대 20자) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 대표자명 (최소 2자 최대 10자) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 사업자등록번호 <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 사업자등록증 첨부 (1장, 이미지 등록과 동일)<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 사업자 유형 (간이과세자, 일반과세자, 법인사업자, 면세사업자, 기타사업자 선택 가능)<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 주업태 (최소 5자, 최대 30자) <br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 주종목 (최소 5자, 최대 30자)<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 사업장 주소<br>
			<br>
			
			(viii) 결제정보<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 계좌선택<br>
			<br>
			
			- 모든 정보를 다 입력했다면 검수 신청을 할 수 있습니다. <br>
			- 검수 대기상태에서는 이용자에게 공간이 조회되지 않습니다. 검수 완료 상태가 되면 공간이 조회됩니다.<br>
			- 검수가 완료되면 메일을 통해 알림을 받으실 수 있습니다.<br>
			- 검수 신청대기 상태에서는 해당 공간을 삭제하실 수 없습니다.<br>
			- 검수가 기각되면 재신청을 하실 수 없으며, 삭제처리만 가능합니다. <br>
		</div>
		<br>
		
		<div>
			<p>2. 공간수정</p>
			- 공간 수정 페이지에서는 작성한 공간 정보에 대해 내용 확인 및 수정이 가능합니다. <br>
			- 공간 수정 페이지에서 수정이 불가능한 항목<br>
			→ 기본정보, 사업자 정보, 가격 정보(단, 예약이 존재하지 않는 경우에는 수정이 가능)<br>
			- 패키지 정보의 경우, 해당 일자를 기준으로 12개월 이후까지의 스케줄을 추가하실 수 있습니다.<br>
			- 해당 패키지에 예약이 존재하는 경우에는 패키지의 내용을 수정하실 수 없습니다. 예약이 존재하지 않아야만 수정이 가능합니다.<br>
		</div>
		<br>
		
		<div>
			<p>3. 공간삭제</p>
			- 공간을 삭제하는 날짜를 기준으로 이후에 예약이 잡혀있지 않을 경우에만 삭제가 가능합니다.<br>
			- 삭제된 공간 게시물의 개인정보 (사업자정보, 연락처)는 1년 보관 후 파기됩니다.<br>
		</div>
		<br>
		
		
	</div>
	
	<c:choose>
		<c:when test="${identify eq 'member'}">
			<c:import url="${cp}/includes/footer_user.jsp"></c:import>
		</c:when>
		<c:when test="${identify eq 'host'}">
			<c:import url="${cp}/includes/footer_host.jsp"></c:import>
		</c:when>
	</c:choose>
	
	
	<!-- 자바스크립트 include -->
	<c:import url="${cp }/includes/includes_home_end.jsp"></c:import>

</body>
</html>