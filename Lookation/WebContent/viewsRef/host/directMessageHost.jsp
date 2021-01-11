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
<title>Direct Message.jsp</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">
body {
	padding: 5%;
	background-color: #F6F6F6;
	font-family: "나눔스퀘어";
}

.container {
	padding: 0;
	background-color: #FFF;
	width: 600px;
}

/* header-chat */
.header-chat {
	background-color: #FFF;
	height: 90px;
	box-shadow: 0px 3px 2px rgba(0, 0, 0, 0.100);
	border-bottom: 2px solid #EEE;
	display: flex;
	align-items: center;
}

.chat .header-chat .icon {
	margin-left: 30px;
	color: #515151;
	font-size: 14pt;
}

.header-chat .name {
	margin: 0 0 0 20px;
	font-family: "나눔스퀘어";
	font-weight: bold; font-size : 13pt;
	color: #515151;
	font-size: 13pt;
}

.header-chat .right {
	position: absolute;
	right: 40px;
}
/*=== End header-chat ===*/

/*=== messages-chat ===*/
.messages-chat {
	padding: 25px 0px;
	overflow-y: scroll;
	overflow-x: hidden;
	width: 100%;
	height: 500px;
}

/*=== 왼쪽(응답측) 말풍선 공간 ===*/
.messages-chat .message {
	display: flex;
	align-items: center;
	margin-bottom: 8px;
	clear: both; /* float-right인 시간표시와 분리하기 위함 */
}

/*=== 왼쪽(응답측) 말풍선 ===*/
.messages-chat .text {
	margin: 0 35px;
	background-color: #00043c;
	color: white;
	padding: 12px;
	border-radius: 20px;
	max-width: 380px;
}

/*=== 오른쪽(전송측) 말풍선 공간 ===*/
.send {
	display: block;
	margin-right: 0px; /* !important */
	margin-left: auto; /* flexbox alignment rule */
	resize: both;
	display: block;
}

/*=== 오른쪽(전송측) 말풍선 ===*/
.send .text {
	background-color: #fdbe34;
	color: black;
	max-width: 380px;
	display: block;
}

/*=== 모든 사진 ===*/
.message-img {
	max-width: 150px;
	resize: both;
}

/*=== 시간 ===*/
.response-timestamp {
	font-size: 12px;
	color: lightgrey;
	margin-bottom: 10px;
	margin-left: 40px;
}

.send-timestamp {
	font-size: 12px;
	color: lightgrey;
	margin-right: 40px;
}

/* footer-chat */
.footer-chat {
	width: 600px;
	height: 80px;
	display: flex;
	align-items: flex-end;
	position: absolute;
	bottom: calc(auto-20px);
	background-color: white;
	border-top: 2px solid #EEE;
	align-items: center;
}

.footer-chat .icon {
	margin-left: 30px;
	color: #00043c;
	font-size: 14pt;
}

.footer-chat .send {
	color: #FFF;
	background-color: #00043c;
	position: absolute;
	right: 15px;
	padding: 12px 12px 12px 12px;
	border-radius: 50px;
	font-size: 14pt;
}

.footer-chat .right {
	position: absolute;
	right: 40px;
}

.write-message {
	border: none;
	width: 60%;
	height: 50px;
	margin-left: 20px;
	padding: 10px;
}

.clickable {
	cursor: pointer;
}


/* 사진첨부 기존버튼 지우기 */
.filebox input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
}
</style>
<script type="text/javascript">
	
	/* 이미지 팝업으로 원본크기 띄우기 */
	/* 추후 제이쿼리 수정 */
	function popImg(url)
	{
		var img = new Image();
		img.src = url;
		var w_img = img.width;
		var h_img = img.height;
		var OpenWindow = window.open('', '_blank', 'width=' + w_img
				+ ', height=' + h_img + ', menubars=no, scrollbars=auto');
		OpenWindow.document
				.write("<style>body{margin:0px;}</style><img src='"+url+"'>");
	}
	
</script>

</head>
<body>
	<div class="container">

		<div class="message-box shadow">
			<div class="row">
				<div class="col-md-12">
					<!-- .header-chat 시작-->
					<div class="header-chat">
						<!-- <i class="icon fa fa-user-o" aria-hidden="true"></i>-->
						<p class="name">호스트영은</p>
					</div>
					<div class="messages-chat">

						<!-- 시간 위치, 사진 클릭하면 확대 -->
						<!-- 이미지 첨부하는 기능 -->
						<!-- 가로 스크롤바 숨기기  -->

						<div class="message">
							<p class="text">안녕하세요. 몇시쯤 도착 예정이신지요...?</p>
						</div>
						<div class="message">
							<p class="response-timestamp">21-01-11 03:45:27</p>
						</div>

						<div class="message">
							<div class="send">
								<p class="text">9999시요</p>
							</div>
						</div>
						<!-- 오른쪽 시간표시하는 timestamp는 : send-timestamp -->
						<!-- 부모 class에 float-right 해야 우측정렬됨. -->
						<div class="message float-right">
							<p class="send-timestamp">21-01-11 03:45:27</p>
						</div>
						
						<div class="message">
							<p class="text">네, 비밀번호는 486입니다.</p>
						</div>
						<div class="message">
							<p class="response-timestamp">21-01-11 03:45:27</p>
						</div>
						
						
						<div class="message">
							<div class="send">
								<p class="text">네네네</p>
							</div>
						</div>
						<div class="message float-right">
							<p class="send-timestamp">21-01-11 08:45:27</p>
						</div>
						
						
						<!-- 보내는 쪽에서 갑자기 사진 보냄 -->
						<div class="message">
							<div class="send">
								<p class="text">
									<img class="message-img" alt="" src="<%=cp%>/images/1.jpg" onclick="popImg(this.src)">
								</p>
							</div>
						</div>
						<div class="message float-right">
							<p class="send-timestamp">21-01-11 08:45:27</p>
						</div>
						
						
						<!-- 장문 테스트 -->
						<div class="message">
							<p class="text">최대 입력가능한 글자수는 몇바이트였더라 dm만 언제까지 해야하는지 궁금해요...
								최대 입력가능한 글자수는 몇바이트였더라 dm만 언제까지 해야하는지 궁금해요...최대 입력가능한 글자수는
								몇바이트였더라 dm만 언제까지 해야하는지 궁금해요...최대 입력가능한 글자수는 몇바이트였더라 dm만 언제까지
								해야하는지 궁금해요...최대 입력가능한 글자수는 몇바이트였더라 dm만 언제까지 해야하는지 궁금해요...최대
								입력가능한 글자수는 몇바이트였더라 dm만 언제까지 해야하는지 궁금해요...최대 입력가능한 글자수는 몇바이트였더라
								dm만 언제까지 해야하는지 궁금해요...</p>
						</div>
						<div class="message">
							<p class="response-timestamp">21-01-11 03:45:27</p>
						</div>
						
						
						<!-- 응답 측에서도 갑자기 사진 보냄 -->
						<div class="message">
							<p class="text">
								<img class="message-img" alt="" src="<%=cp%>/images/1.jpg" onclick="popImg(this.src)">
							</p>
						</div>
						<div class="message">
							<p class="response-timestamp">21-01-11 08:45:27</p>
						</div>
						
						
						<!-- 장문 테스트 -->
						<div class="message">
							<div class="send">
								<p class="text">최대 입력가능한 글자수는 몇바이트였더라 dm만 언제까지 해야하는지 궁금해요...
									최대 입력가능한 글자수는 몇바이트였더라 dm만 언제까지 해야하는지 궁금해요...최대 입력가능한 글자수는
									몇바이트였더라 dm만 언제까지 해야하는지 궁금해요...최대 입력가능한 글자수는 몇바이트였더라 dm만 언제까지
									해야하는지 궁금해요...최대 입력가능한 글자수는 몇바이트였더라 dm만 언제까지 해야하는지 궁금해요...최대
									입력가능한 글자수는 몇바이트였더라 dm만 언제까지 해야하는지 궁금해요...최대 입력가능한 글자수는 몇바이트였더라
									dm만 언제까지 해야하는지 궁금해요...</p>
							</div>
						</div>
						<div class="message float-right">
							<p class="send-timestamp">21-01-11 09:45:27</p>
						</div>
						
						
						<!-- 응답 측에서 메시지 연결해서 보내는 상황 -->
						<div class="message">
							<p class="text">이용하시는 데 불편함 없으시지요?</p>
						</div>
						<div class="message">
							<p class="text">불편한 경우가 생기신다면 바로 말씀해 주세</p>
						</div>
						<div class="message">
							<p class="text">요.</p>
						</div>
						<!-- 시간을 제일 마지막에 한번만 출력할 수 있게 한다 -->
						<div class="message">
							<p class="response-timestamp">21-01-11 11:45:27</p>
						</div>
						
						
						<!-- 전송 측에서 메시지 연결해서 보내는 상황 -->
						<div class="message">
							<div class="send">
								<p class="text">
									<img class="message-img" alt="" src="<%=cp%>/images/about.jpg" onclick="popImg(this.src)">
								</p>
							</div>
						</div>
						<div class="message">
							<div class="send">
								<p class="text">예 그럼요.</p>
							</div>
						</div>
						<div class="message float-right">
							<p class="send-timestamp">21-01-11 12:12:31</p>
						</div>



					</div><!-- End .message-chat  -->
					
					
					
					
					<!-- .footer-chat -->
					<div class="footer-chat">
						<!-- 사진첨부버튼 -->
						<div class="filebox">
							<label for="ex_file">
								<i class="icon fa fa-picture-o clickable"
									style="font-size: 20pt; align-self: center;" aria-hidden="true"></i>
							</label>
							<input type="file" id="ex_file" multiple="multiple">
						</div>
						
						<input type="text" class="write-message" placeholder="메시지를 입력하세요"></input>
						<i class="icon send fa fa-paper-plane-o clickable"
							aria-hidden="true"></i>
					</div>
					<!-- End .footer-chat -->

				</div>
			</div>
		</div>

	</div>


<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</body>
</html>