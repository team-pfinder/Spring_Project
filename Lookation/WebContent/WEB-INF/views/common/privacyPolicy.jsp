<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	String identify = request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>개인정보처리방침</title>

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
	
	.inner
	{
		float: none;
		margin: 100px auto;
	}

</style>


<!-- css 등 공통적으로 들어가는 요소 include -->
<c:import url="${cp }/includes/includes_home.jsp"></c:import>

</head>
<body>

	<!-- header 출력부분 -->
<div>
		<c:if test="${identify eq 'host' }">
			 <c:import url="${cp}/includes/header_host.jsp?result=${result }&nick=${info.nick }"></c:import>
		</c:if>
		<c:if test="${identify eq 'member' }">
			 <c:import url="${cp}/includes/header_user.jsp?result=${result }&nick=${info.nick }"></c:import>
		</c:if>
</div>

	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('<%=cp%>/images/bg_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div class="row no-gutters slider-text align-items-end">
				<div class="col-md-9 ftco-animate pb-5">
					<p class="breadcrumbs mb-2">
						<span class="mr-2"><a href="termsofservice.action?identify=<%=identify %>">Services
						<i class="ion-ios-arrow-forward"></i></a></span>
					</p>
					<h1 class="mb-0 bread">개인정보처리방침</h1>
				</div>
			</div>
		</div>
	</section>

	<!-- 방침 본문 -->
	<div class="container">
		<div class="inner">
			<div>
				<p>개인정보의 처리목적</p>
				Lookation(‘http://www.lookation.com’이하 ‘회사’) 은 다음의 목적을 위하여 개인정보를 처리하고 있으며,
				 다음의 목적 이외의 용도로는 이용하지 않습니다.
				<br>
				- 고객 가입의사 확인, 고객에 대한 서비스 제공에 따른 본인 식별.인증,
				 회원자격 유지.관리, 물품 또는 서비스 공급에 따른 금액 결제,
				  물품 또는 서비스의 공급.배송 등
			</div>
			<br>
			
			<div>
				<p>개인정보의 처리 및 보유 기간</p>
				① 회사는 정보주체로부터 개인정보를 수집할 때 동의 받은
				   개인정보 보유․이용기간 또는 법령에 따른 개인정보 보유․이용기간 내에서 
				   개인정보를 처리․보유합니다. <br>
				② 구체적인 개인정보 처리 및 보유 기간은 다음과 같습니다. <br>
				- 이메일(로그인 ID), 연락처, 비밀번호, 이름, 은행계좌정보, 사업자 정보 : 회원 탈퇴 후 1년 보관 후 폐기 <br>
				- 단, 블랙리스트로 지정된 회원의 경우 재가입 방지를 위하여 이메일(로그인 ID)는 5년간 보관 후 폐기 <br>
				  그 외 다른 정보는 동일하게 1년간 보관 후 폐기
			</div>
			<br>
			
			<div>
				<p>개인정보의 제 3자 제공에 관한 사항</p>
				① 회사는 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조 및 제18조에 해당하는 경우에만
				   개인정보를 제3자에게 제공합니다. <br>
				② 회사는 다음과 같이 개인정보를 제3자에게 제공하고 있습니다. <br><br>
				1. <span style="font-weight: bold;">호스트</span><br>
				개인정보를 제공받는 자 : 호스트 <br>
				제공받는 자의 개인정보 이용목적 : 이메일, 휴대전화번호, 로그인ID, 이름, 은행계좌정보 <br>
				제공받는 자의 보유.이용기간: 공간 이용완료 전까지<br>
				2. <span style="font-weight: bold;">이용자</span><br>
				개인정보를 제공받는 자 : 이용자<br>
				제공받는 자의 개인정보 이용목적 : 이메일, 휴대전화번호, 이름, 은행계좌정보, 공유공간정보(주소, 전화번호, 사업자번호)<br>
				제공받는 자의 보유.이용기간: 이용완료 전까지<br>
				3. <span style="font-weight: bold;">관리자</span><br>
				개인정보를 제공받는 자 : 관리자<br>
				제공받는 자의 개인정보 이용목적 : 이메일, 휴대전화번호, 비밀번호, 로그인ID, 이름, 은행계좌정보, 결제기록, 공유공간정보(주소, 전화번호, 사업자정보)<br>
				제공받는 자의 보유.이용기간: 탈퇴 후 1년~5년(블랙리스트로 지정시)<br>
			</div>
			<br>
			
			<div>
				<p>정보주체와 법정대리인의 권리·의무 및 그 행사방법</p>
				회원은 개인정보주체로써 다음과 같은 권리를 행사할 수 있습니다.<br>
				1. 개인정보 열람요구<br>
				2. 오류 등이 있을 경우 정정 요구<br>
				3. 삭제요구<br>
				4. 처리정지 요구<br>
			</div>
			<br>
			
			<div>
				<p>처리하는 개인정보의 항목</p>
				회사는 다음의 개인정보 항목을 처리하고 있습니다.<br>
				- 이메일(로그인 ID), 휴대전화번호, 비밀번호, 이름, 은행계좌정보, 공유공간정보(주소, 전화번호), 사업자번호
			</div>
			<br>
			
			<div>
				<p>개인정보 파기</p>
				회사는 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체없이 해당 개인정보를 파기합니다.<br>
				파기의 절차, 기한 및 방법은 다음과 같습니다.<br><br>
				-파기절차<br>
				 이용자가 입력한 정보는 목적 달성 후 별도의 DB에 옮겨져 내부 방침 및 기타 관련 법령에 따라
				 일정기간 저장된 후 혹은 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.<br>
				<br>
				-파기기한<br>
				 이용자의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 1일 이내에
				 , 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는
				 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 3일 이내에 그 개인정보를 파기합니다.
			</div>
			<br>
			
			<div>
				<p>개인정보 자동 수집 장치의 설치·운영 및 거부에 관한 사항</p>
				본 회사는 정보주체의 이용정보를 저장하고 수시로 불러오는 '쿠키'를 사용하지 않습니다.
			</div>
			<br>
			
			<div>
				<p>개인정보 보호책임자 작성</p>
				① 회사는 개인정보 처리에 관한 업무를 총괄해서 책임지고
				 , 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.
				<br><br>
				▶ 개인정보 보호책임자<br>
				성명 : 김승범<br>
				직급 : 대표<br>
				연락처 : 010-3441-0260<br>
				이메일 : sb1204@pfinder.com<br>
				<br>
				▶ 개인정보 보호 담당부서<br>
				부서명 : DM<br>
				담당자 : 조윤상<br>
				연락처 : 010-7383-1339
				이메일 : mr_cho@pfinder.com<br>
				<br>
				② 정보주체께서는 회사의의 서비스(또는 사업)을 이용하시면서
				 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다.<br>
				 회사는 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.
			</div>
			<br>
			
			<div>
				<p> 개인정보 처리방침 변경</p>
				이 개인정보처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이
				있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.
			</div>
			<br>
			
			<div>
				<p>개인정보의 안전성 확보조치</p>
				회사는 개인정보보호법 제 29조에 따라 다음과 같이 안전성 확보에 필요한 기술적/관리적 및 물리적 조치를 하고 있습니다.
				<br><br>
				1. 정기적인 자체 감사 실시<br>
				개인정보 취급 관련 안정성 확보를 위해 정기적(분기 1회)으로 자체 감사를 실시하고 있습니다.<br><br>
				2. 개인정보 취급 직원의 최소화 및 교육<br>
				개인정보를 취급하는 직원을 지정하고 담당자에 한정시켜 최소화 하여 개인정보를 관리하는 대책을 시행하고 있습니다.<br><br>
				3. 내부관리계획의 수립 및 시행<br>
				개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.<br><br>
				4. 개인정보의 암호화<br>
				이용자의 개인정보는 비밀번호는 암호화 되어 저장 및 관리되고 있어
				, 본인만이 알 수 있으며 중요한 데이터는 파일 및 전송 데이터를 암호화 하거나
				 파일 잠금 기능을 사용하는 등의 별도 보안기능을 사용하고 있습니다.<br><br>
				5. 개인정보에 대한 접근 제한<br>
				개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여
				,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며
				 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.<br><br>
				6. 비인가자에 대한 출입 통제<br>
				개인정보를 보관하고 있는 물리적 보관 장소를 별도로 두고 이에 대해 출입통제 절차를 수립, 운영하고 있습니다.
			</div>
		</div>
	</div>

	<!-- footer 출력부분 -->
<div>
		<c:if test="${identify eq 'host' }">
			 <c:import url="${cp}/includes/footer_host.jsp"></c:import>
		</c:if>
		<c:if test="${identify eq 'member' }">
			 <c:import url="${cp}/includes/footer_user.jsp"></c:import>
		</c:if>
</div>
	
	
	<!-- 자바스크립트 include -->
	<c:import url="${cp }/includes/includes_home_end.jsp"></c:import>

</body>
</html>