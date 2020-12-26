<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp1 = request.getContextPath();
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mileage_ChargeList(admin).jsp</title>
<link rel="stylesheet" type="text/css" href="<%=cp1 %>/css/list.css">
</head>
<body>

<!-- css 가로 배치 -->
<div>
	<h1>마일리지 충전 리스트</h1>
	<hr>
</div>

<!-- css 가로 배치 -->	
<div>
	<a href="Mileage_ExchangeList(admin).jsp"><button>마일리지 환전 관리</button></a>
	<a href="Mileage_....jsp"><button>마일리지 정산 관리</button></a>
</div>
<!-- 충전신청 리스트 검색 창 -->
<div>
	<form action="">
		<select name="" id="">
			<option value="">검색선택</option>
			<option value="">이메일검색</option>
			<option value="">계좌번호검색</option>
			<option value="">예금주검색</option>
		</select>
		<input type="text" /> <button type="submit">검색</button>
		<select name="" id="">
			<option value="">정렬선택</option>
			<option value="">최근순</option>
			<option value="">오래된순</option>
			<option value="">대기중인 신청</option>
			<option value="">완료처리된 신청</option>
			<option value="">실패처리된 신청</option>
		</select>
	</form>
</div>
<br>
<!-- 리스트 -->
<div id='bbsList_list'>
	<div id="title">
		<dl>
			<dt class="num">번호</dt>
			<dt class="email">E-Mail</dt>
			<dt class="name">예금주</dt>
			<dt class="bankaccount">계좌번호</dt>
			<dt class="amount">금액</dt>
			<dt class="date_apply">충전신청일자</dt>
			<dt class="processedOrNot">충전처리</dt>
			<dt class="date_processed">처리일</dt>
		</dl>
	</div><!-- #title -->
	
	<div id="lists"> 
		<dl>
			<dd class="num">1</dd>
			<dd class="email">kiw@test.com</dd>
			<dd class="name">김일웅</dd>
			<dd class="bankaccount">123484-4545-7879451</dd>
			<dd class="amount">10000원</dd>
			<dd class="date_apply">2020-11-11 11:22</dd>
			<dd class="processedOrNot">완료</dd>
			<dd class="date_processed">2020-11-11</dd>
		</dl>
		<dl>
			<dd class="num">2</dd>
			<dd class="email">ksy@test.com</dd>
			<dd class="name">권소윤</dd>
			<dd class="bankaccount">154544-1545-7845441</dd>
			<dd class="amount">20000원</dd>
			<dd class="date_apply">2020-11-11 12:22</dd>
			<dd class="processedOrNot">실패</dd>
			<dd class="date_processed">2020-11-11</dd>
		</dl>
		<dl>
			<dd class="num">3</dd>
			<dd class="email">ksb@test.com</dd>
			<dd class="name">김승법</dd>
			<dd class="bankaccount">1541561-45844-5489451</dd>
			<dd class="amount">10000원</dd>
			<dd class="date_apply">2020-12-26 11:22</dd>
			<dd class="processedOrNot">대기</dd>
			<dd class="date_processed"><button>처리</button><button>반려</button></dd>
		</dl>
	</div><!-- #lists -->
	
	<div id="footer">
		<p> 1 prev 21 22 23 24 25 26 27 28 29 30 Next 90</p>
		<p>조회 내역이 없습니다.</p>
	</div><!-- #footer -->
</div><!-- #bbsList_list -->


참고이미지<a href="<%=cp1 %>/images/Mileage_ChargeList(admin).png">그림</a>

</body>
</html>