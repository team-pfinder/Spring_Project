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
<title>LocationBasicInfo.jsp</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<!-- 헤더 -->
<div>
	<h1>기본 정보</h1>	<!-- 가운데 정렬 -->
</div>
<%-- <img src="<!-- 뒤로가는 화살표 버튼 -->"> --%>
<br><br>

<div>
	<h1>등록할 공간의 기본 정보를 입력하세요.</h1>
	<hr>	
</div>

<br><br>

<!-- 1. 공간명 -->

<div id="LocationName">

	공간명 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationName" name="LocationName" style="width: 300pt;">
	<br><br>
	<span>최소 2자, 최대 20자</span>
	<!-- 입력 전 default 내용 : 공간명을 입력하세요.  -->
	
	<span>사용 가능한 특수문자: ( , ) , [ , ] , - , .(마침표), ,(쉼표)</span> <!-- 글자크기 작게 -->

</div>

<br><br><br>


<!-- 2. 공간 유형 -->

<div id="LocationType">

	공간 유형 *<br><br>	<!-- bold, *은 빨간색 -->
	<select id="LocationType" name="LocationType" style="width: 200pt;">
		<option value="==[공간 유형을 선택하세요.]==">[==공간 유형을 선택하세요.]==</option>
		<option value="파티룸">파티룸</option>
		<option value="클럽">클럽</option>
		<option value="엠티장소">엠티장소</option>
		<option value="워크샵장소">워크샵장소</option>
		<option value="워크샵장소">루프탑</option>
		<option value="워크샵장소">브라이덜샤워</option>
	</select>
	<br><br>
	<span>※ 검수 후에는 유형변경이 불가합니다.</span> <!-- 붉은색 글자 -->

</div>


<br><br><br>

<!-- 4. 공간 한줄 소개 -->

<div id="LocationOneLineComment">

	공간 한줄 소개 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationOneLineComment" name="LocationOneLineComment" style="width: 300pt;">
	<br><br>
	<span>최소 4자, 최대 20자</span> <!-- 글자크기 작게 -->
	<!-- 입력 전 defalut 내용 : 공간을 한 문장으로 소개해주세요.  -->

</div>

<br><br><br>

<!-- 5. 공간 소개 -->

<div id="LocationComment">

	공간 소개 *<br><br> <!-- bold, font-size 1~2pt up -->
	<textarea name="LocationComment" id="LocationComment" cols="40" rows="5"></textarea>
	<br><br><!-- 입력 전 default 내용 : 공간을 상세하게 소개해주세요.  -->
	
	<span>최소 20자, 최대 400자</span> <!-- 글자크기 작게 -->

</div>

<br><br><br>


<!-- 6. 시설안내 -->

<div id="LocationFacilityInfo">

	시설 안내 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationFacilityInfo" name="LocationFacilityInfo" style="width: 300pt;">
	<!-- 입력 전 default 내용 : 최대 10개까지 추가가능합니다.  -->
	
	<input type="button" id="LocationFacilityInfoInsert" name="LocationFacilityInfoInsert"
			 style="width: 70pt;" value="추가 +">
	<br><br>
	<span>최소 10자, 최대 50자, 최대 10개까지 등록가능</span> <!-- 글자크기 작게 -->
	
	<!-- 5-1. 추가한 시설안내 내용이 보여지도록 하는 공간
			, 리스트 형태로 추가할때마다 순번부여,
			, "x" 버튼클릭시 추가한 내용 삭제가능 
			, (순번).(내용)(x버튼) 형식		<- 순번은 뺼 수도 있을듯.
	-->
</div>

<br><br><br>


<!-- 7. 예약 시 주의사항 -->

<div id="LocationPrecautions">
	
	예약 시 주의사항 *<br><br> <!-- bold, font-size 1~2pt up -->
	<input type="text" id="LocationPrecautions" name="LocationPrecautions" style="width: 300pt;">
	<!-- 입력 전 default 내용 : 최대 10개까지 추가가능합니다.  -->
	
	<input type="button" id="LocationPrecautionsInsert" name="LocationPrecautionsInsert"
			 style="width: 70pt;" value="추가 +">
	<br><br>
	<span>최소 20자, 최대 100자, 최대 10개까지 등록가능</span> <!-- 글자크기 작게 -->
	<br><br>
	<span>※환불규정<br>	<!-- 모두 red color font -->
		7일전 - 100% 환불, 6~1일전 - 50% 환불, 예약당일 : 환불 및 취소 불가
		</span>
	<!-- 6-1. 추가한 예약 시 주의사항 내용이 보여지도록, 삭제 x 버튼클릭시 추가한 내용 삭제가능 -->

</div>

<br><br><br>


<!-- 8. 대표이미지 -->
<div id="LocationThumbnail">

	대표이미지 *<br><br> <!-- bold, font-size 1~2pt up -->
	<!-- 이미지추가시 들어갈 공간.. textarea인지 확인 필요-->
	<textarea name="LocationThunmbnail" id="LocationThumbnail" cols="40" rows="10"></textarea>
	<!-- 입력 전 default 내용 : 대표이미지 파일을 추가해 주세요(JPG, JPEG, PNG)  -->
	<br><br>
	<input type="button" id="LocationThumbnailInsert" name="LocationThumbnailInsert"
			 style="width: 70pt;" value="파일첨부">
	<br><br>
	<span>최소/최대 1장, 최대 5MB, 최대해상도 2048*1158
		, 이미지 확장자(JPG,JPEG,PNG)만 가능</span> <!-- 작은 글자 -->
	
	<!-- 7.1 추가 시 보여지는 default 이미지 해상도 xxx*xxx -->
	<!-- ※ 다시 첨부하여 등록할 시 기존 이미지를 대체 -->
</div>

<br><br><br>

<!-- 9. 주소 -->
	
<div id="LocationAddr">
	주소 *<br><br> <!-- bold, font-size 1~2pt up -->
	
	<input type="text" id="LocationAddr" name="LocationAddr" style="width: 300pt;" readonly="readonly">
	<!-- 입력 전 default 내용 : 주소를 입력해주세요.  -->
	
	<input type="button" id="LocationAddrInsert" name="LocationAddrInsert"
			 style="width: 70pt;" value="주소등록">
	<!-- 주소등록 버튼클릭 
		 → 주소등록창이라는 새 페이지 등장 
		 →  텍스트로 주소검색 → 검색버튼 클릭 
		 → 해당 검색어에 따른 데이터를 selectbox나 radio button으로 체크 
		 → 체크된 상태로 등록 버튼 클릭 
		 → 주소등록 페이지가 닫힘 
		 → 체크된 주소 및 우편번호 
			
			: 텍스트가 (우편번호)주소 텍스트박스(readonly)에 자동입력 
			{주소 API 사용} 
	-->
	<br><br>
	<input type="text" id="LocationDetailedAddr" name="LocationDetailedAddr" style="width: 300pt;">
	<!-- 입력 전 default 내용 : 상세 주소  -->

</div>


<br><br><br>

<!-- 다음 버튼(공통) : 다음 입력페이지로 넘어가고, DB에 저장된다.
					   (필수항목을 입력하지 않았을 경우,
						입력하지않은 항목 중 가장 첫번째 항목을 focus()하고
					    alert("필수항목을 입력해야합니다")된다.
						그리고 입력하는 textbox로 입력커서가 이동한다. 
						또한 다음페이지로 submit 되지 않는다.-->
<input type="button" id="LocationBasicInfoSave" style="width: 150pt;"
	value="다음"> <!-- onclick="function()" submit → LocationContact.jsp-->

<!-- 취소 버튼 -->
<input type="button" id="LocationBasicInfoCancel" style="width: 150pt;"
	value="취소"> <!-- onclick="function()" -->



<br><br><br><br><br><br><br><br><br>

</body>
</html>