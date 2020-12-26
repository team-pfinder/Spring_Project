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
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	$(document).ready(function()
	{
		$('p.a').click(function()
		{
			$('p.b').toggle('');
		});
	});
</script>
</head>
<body>
<!-- 로고 및 검색 -->
	<!-- 
      #css

    -->
	<!--
      #참고 소스
      jqueryApp01/jqTest01.html
   -->
	<div>
		<table>
			<tr>
				<th><img src="<%=cp%>/images/logo.png" height="80px" /></th>
				<th><input type="text"
					style="width: 700px; height: 50px; font-size: 30px" /></th>
				<th><img src="<%=cp%>/images/search.png" height="40px" /></th>
			</tr>
		</table>
		<hr>
	</div>


	<div style="display: inline-block; float: left; margin-left: 15px;" >
		<!-- <select style="width: 180px; height: 26px;">
			<option value="default">전체</option>
			<option>회원</option>
			<option>취소 및 환불</option>
			<option>공간이용 및 후기</option>
			<option>기타</option>
		</select>  -->
		<input type="text" style="width: 250px; height: 26px;">
		<button>검색</button>
	</div>



	<div>
		<table width="100%" cellspacing="0">
			<thead>
				<tr>
					<th></th>
					<th>제목</th>
					<!-- <th>내용</th> -->
				</tr>
			</thead>


			<tbody>
				<tr align="center">

					<td>공지사항</td>

					<!-- <td>아무제목</td> -->
					<td>
						<!-- <p class="a">공지사항이요</p>
						<p class="b">진짜 하기 싫어 디지겠어요</p>  -->
						<details>
							<summary>[업데이트] 2020.12.25 (금) 온라인 cs 운영안내 </summary>
							<p>아우 하기 싫어요</p>
						</details>

					</td>
					
					<tr align="center">
					
					<td>공지사항</td>

					<td>
						
						<details>
							<summary>[공지사항] 2020.12.26 (토) 룩케이션 운영안내 </summary>
							<p>싫어요!</p>
						</details>

					</td>
					</tr>
					

					<!-- <td>아무말 아 하기 싫어</td> -->

				</tr>

			</tbody>
		</table>
	</div>








</body>
</html>