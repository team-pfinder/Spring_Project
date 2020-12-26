<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>profile(user).jsp</title>
</head>
<body>
	<div>
		<p>이용자 프로필관리</p> <!-- 큰폰트 -->
	</div>
	
	<!-- 프로필관리 양식 -->
	<div>
		<table>
			<tr>
				<td colspan="2"><hr></td>
			</tr>
			
			<tr>
				<td>닉네임</td>		<!-- 작은크기폰트, 회색폰트 -->
				<td>xxxxx</td>
			</tr>
			
			<tr>
				<td></td>
				<td><a href="changeNickname">변경하기</a></td>
			</tr>
			
			<tr>
				<td>이메일</td>		<!-- 작은크기폰트, 회색폰트 -->
				<td>xxxxx@xxxxx.xxx</td>
			</tr>
			
			<tr>
				<td>연락처</td>		<!-- 작은크기폰트, 회색폰트 -->
				<td>010-0000-0000</td>
			</tr>
			
			<tr>
				<td></td>
				<td><a href="changeTel">변경하기</a></td>
			</tr>
			
			<tr>
				<td>비밀번호</td>	<!-- 작은크기폰트, 회색폰트 -->
				<td><a href="changeTel">변경하기</a></td>
			</tr>
			
			<tr>
				<td>계좌정보</td>			<!-- 작은크기폰트, 회색폰트 -->
				<td><a href="manageBankAccount">관리</a></td>
			</tr>
			
			<tr>
				<td>가입일자</td>	<!-- 작은크기폰트, 회색폰트 -->
				<td>xxxx.xx.xx</td>
			</tr>
			
			<tr>
				<td>블랙리스트 전환일 </td>	<!-- 작은크기폰트, 회색폰트 -->
				<td>xxxx.xx.xx</td>			<!-- 현재 블랙리스트가 아닌경우, 블랙리스트가 아니라고 나온다. -->
			</tr>
			
			<tr>
				<td>경고내역</td>			<!-- 작은크기폰트, 회색폰트 -->
				<td><a href="comfirmWarning">확인하기</a></td>
			</tr>
			
			<tr>
				<td colspan="2"><hr></td>
			</tr>
			
			<tr>
				<td colspan="2"><a href="dropOutAccount">서비스 탈퇴하기</a></td> <!-- 가운데 정렬 -->
			</tr>
		
		</table>
	</div>
</body>
</html>