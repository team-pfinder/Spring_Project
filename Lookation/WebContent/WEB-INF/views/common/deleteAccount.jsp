<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String identify = (String)request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
	
	// 세션 접속시 아이디 확인
	if(identify.equals("host"))
	{
		String hostCode = (String)session.getAttribute("hostCode");
		pageContext.setAttribute("Code", hostCode);
	}
	else
	{
		String memberCode = (String)session.getAttribute("memberCode");
		pageContext.setAttribute("Code", memberCode);
	}
	
	
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
		//var address = unescape(location.href);
		//var param = "";
		var Code = "";
		
		// url 파라미터값 추출
		//param = address.substring(address.indexOf("identify", 0) + 9);

		var identify = '<%=(String)request.getParameter("identify")%>'
		
		if(identify=="host")
		{
			Code = '<%=(String)session.getAttribute("hostCode")%>';
		}
		else
		{
			Code = '<%=(String)session.getAttribute("memberCode")%>';
		}
		
		// 취소 버튼 클릭시 액션처리
		$("#cancelBtn").click(function()
		{
			if(identify=="member")
			{
				// 이용자일 경우 멤버코드 받아서
				$(location).attr("href", "profile.action?identify=member");
			}
			else if(identify=="host")
			{
				// 호스트일 경우 호스트코드 받아서 
				$(location).attr("href", "profile.action?identify=host");
			}
		});
		
		// 삭제 버튼 클릭 시 액션 처리
		$("#deleteBtn").click(function()
		{	
			if(identify=="host")
			{
				Code = '<%=(String)session.getAttribute("hostCode")%>';
			}
			else
			{
				Code = '<%=(String)session.getAttribute("memberCode")%>';
			}
			
			if(identify=="member")
			{
				$(location).attr("href", "deletemember.action?identify=member");
			}
			else if(identify=="host")
			{
				$(location).attr("href", "deletehost.action?identify=host");
			}
			else
			{
				alert("유효하지 않은 접근입니다.");
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
							<button class="btn btn-primary mx-1" type="button" id="cancelBtn">취소하기</button>
							<!-- 탈퇴 버튼 클릭시 탈퇴완료 alert창 호출,  -->
							<!-- 자동으로 메인 화면으로 돌아가기 -->
							
							<!-- 나중에 여기 value값에 회원번호 저장할것 -->
							<!-- 이용자 탈퇴버튼 -->
							<c:if test="${identify eq 'member'}">
								<button class="btn btn-danger mx-1" type="button" id="deleteBtn">탈퇴하기</button>
							</c:if>
							<!-- 호스트 탈퇴버튼 -->
							<c:if test="${identify eq 'host'}">
								<button class="btn btn-danger mx-1" type="button" id="deleteBtn">탈퇴하기</button>
							</c:if>
						</div>

					</div>
				
				</div><!-- End withdrawBox -->
				
			</div><!-- End .col-md-12 -->
		</div><!-- End .row  -->
		
	</div><!-- End .container -->
	

<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</body>
</html>
