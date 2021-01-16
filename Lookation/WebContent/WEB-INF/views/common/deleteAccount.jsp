<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	// 세션 접속시 id확인
	//String id= (String)session.getAttribute("sessionID"); 

	String identify = (String)request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>탈퇴완료.jsp</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">
.withdrawBox
	{
	    width: 648px;
	    
	    margin: 0 auto; 
		float: none;
		padding: 32px;
	}

</style>
<script type="text/javascript">
	
	$(function()
	{
		// 삭제 버튼 클릭 시 액션 처리
		$("#deleteBtn").click(function()
		{	
			var address = unescape(location.href);
			var param = "";
			
			// url 파라미터값 추출
			param = address.substring(address.indexOf("identify", 0) + 9);
			// ?identify=member 혹은 ?identify=host로 넘어옴
			
			// 테스트
			//alert(param);
			//alert($(this).val());
			
			var code = $("#deleteBtn").val();
			
			// ※ 나중에 세션처리로 변경
			if(param=="member")
			{
				// 이용자일 경우 멤버코드 받아서
				$(location).attr("href", "deletemember.action?memCode=" + code);
			}
			else if(param=="host")
			{
				// 호스트일 경우 호스트코드 받아서 
				$(location).attr("href", "deletehost.action?hostCode=" + code);
			}
			else
			{
				$(location).attr("href", "404.html");
			}
			
	
		});
	});
</script>
</head>
<body>
	<div class="container my-5 py-5">
		<div class="row mb-5">
			<div class="col-md-12 mb-5">
				<div class="withdrawBox">
					<div class="dbox w-100 text-center">
						<!-- 체크그림 가운데정렬 -->
						<div class="icon d-flex align-items-center justify-content-center">
							<span class="fa fa-check"></span>
						</div>
						<div class="text mb-5">
							<h3 class="font-weight-bold">탈퇴하시겠습니까?</h3>
							
							<!-- 이용자 문구 -->
							<c:if test="${identify eq 'member'}">
								<h6>회원가입시 입력한 모든 개인정보(계좌정보, 연락처, 이메일 등)는
								서비스 부정이용을 막기 위해 탈퇴일로부터 최대 1년간 보관 후 파기되며,
								이는 재가입하더라도 복구되지
								않습니다.</h6>
							</c:if>
							
							<!-- 호스트 문구 -->
							<c:if test="${identify eq 'host'}">
							<h6>회원가입시 입력한 모든 개인정보(계좌정보, 사업자정보, 연락처, 이메일 등)는
								서비스 부정이용을 막기 위해 탈퇴일로부터 최대 1년간 보관 후 파기되며,
								이는 재가입하더라도 복구되지
								않습니다.</h6>
							</c:if>
							
						</div>
						
						<!-- <a  role="button">메인 화면으로 돌아가기</a> -->
						<div class="align-items-center">
							<button class="btn btn-primary mx-1" type="button">취소하기</button>
							<!-- 탈퇴 버튼 클릭시 탈퇴완료 alert창 호출,  -->
							<!-- 자동으로 메인 화면으로 돌아가기 -->
							
							<!-- 나중에 여기 value값에 회원번호 저장할것 -->
							<!-- 이용자 탈퇴버튼 -->
							<c:if test="${identify eq 'member'}">
								<button class="btn btn-danger mx-1" value="M000011" type="button" id="deleteBtn">탈퇴하기</button>
							</c:if>
							<!-- 호스트 탈퇴버튼 -->
							<c:if test="${identify eq 'host'}">
								<button class="btn btn-danger mx-1" value="H000011" type="button" id="deleteBtn">탈퇴하기</button>
							</c:if>
						</div>

					</div>
				
				</div><!-- End withdrawBox -->
				
			</div><!-- End .col-md-12 -->
		</div><!-- End .row  -->
		
	</div><!-- End .container -->
	

<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<!-- Footer -->
<%-- <%@ include file="../01.ksb/foot.jsp" %> --%>
</body>
</html>
