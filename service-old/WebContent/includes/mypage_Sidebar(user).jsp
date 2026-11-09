<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mypage_Sidebar.jsp(member)</title>
</head>
<body>

	<div class="col-lg-2 sidebar pl-lg-5 ftco-animate">
		<div class="sidebar-box ftco-animate">
			<div class="categories back-default">
				<h3><a href="mypage.action?identify=member">마이페이지</a></h3><hr>
				<li><a href="profile.action?identify=member">프로필 관리 <span
						class="ion-ios-arrow-forward"></span></a></li>
				<li><a href="bankinfomanage.action?identify=member">계좌 관리 <span
						class="ion-ios-arrow-forward"></span></a></li>
				<li><a href="booklist.action">예약 리스트 <span
						class="ion-ios-arrow-forward"></span></a></li>
				<li id="check"><a href="loadandexchange.action?identify=member">충전 및 환전 <span class="ion-ios-arrow-forward"></span>
				</a></li>
				<li><a href="mileagehistory.action?identify=member">마일리지 내역 <span
						class="ion-ios-arrow-forward"></span></a></li>
				<li><a href="userreviewlist.action">리뷰 관리 <span
						class="ion-ios-arrow-forward"></span></a></li>
				<li><a href="userqnalist.action">Q&A 관리 <span
						class="ion-ios-arrow-forward"></span></a></li>
			</div>
		</div>

	</div>

<script type="text/javascript">
/*
	// 충전 환전 페이지 접근시 계좌 유무 확인 후 계좌관리 페이지로 이동
	$(function()
	{	
		$("#check").click(function()
		{
			alert("check");
			var count = 0;
			$.ajax({
				type : "post",
				url : "checkifhavebankinfo.action",
				async: true,
				complete : function(xh)
				{		

					count++;

					if(xh == "OK")
					{
						confirm("등록된 계좌가 존재하지 않습니다. 계좌를 등록하러 가시겠습니까?")
						{
							location.href = "bankinfomanage.action?identify=member";
						}
					}


				}
			});
			if(count == 0)
			{
				confirm("등록된 계좌가 존재하지 않습니다. 계좌를 등록해주세요.")
				{
					location.href="loadandexchange.action?identify=member";
				}
			}
	
		});
	});
*/				
</script>


</body>
</html>