<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15)
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

#fileName {
width: 100px;
overflow: hidden;

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
	
	
	function msgSend()
	{
		var f = document.getElementById("msgForm");
		
		f.submit();
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
						<p class="name">${nick}</p>
					</div>
					<div class="messages-chat">
						
						<!-- 호스트가 보내는 경우 : horm 0 -->
						<!-- 이용자가 보내는 경우 : horm 1 -->
						
						<!-- 1. sender_code(horm) 보고 이용자인지 호스트인지 나눠줌. -->
						<!-- 2. imgcount 보고 이미지이면 1 -->
						<!--    			  텍스트 메시지이면 0 -->
						
						<!-- 3. 연속해서 메시지 보내는 경우 말풍선 연결해서 뜨도록 처리(보류) -->
						
						
						
						<c:forEach var="m" items="${msg}">
							<c:choose>
								<c:when test="${not empty msg}">
									<c:if test="${m.horm eq 1}">
										<c:choose>
											<c:when test="${m.imgcount eq 0}">
												<div class="message">
													<div class="send">
														<p class="text">${m.msg_content}</p>
													</div>
												</div>
												<div class="message float-right">
													<p class="send-timestamp">${fn:substring(m.msg_date,5,16)}</p>
												</div>
											</c:when>
											
											<c:when test="${m.imgcount eq 1}">
												<div class="message">
													<div class="send">
														<p class="text">${m.msg_img_url }
															<img class="message-img" alt="" src="<%=cp%>/images/${m.msg_img_url }" onclick="popImg(this.src)">
														</p>
													</div>
												</div>
												<div class="message float-right">
													<p class="send-timestamp">${fn:substring(m.msg_date,5,16)}</p>
												</div>
											</c:when>
										</c:choose>
									</c:if>
										
									<c:if test="${m.horm eq 0}">
										<c:choose>
											<c:when test="${m.imgcount eq 0}">
												<div class="message">
													<p class="text">${m.msg_content }</p>
												</div>
												<div class="message">
													<p class="response-timestamp">${fn:substring(m.msg_date,5,16)}</p>
												</div>
											</c:when>
											
											<c:when test="${m.imgcount eq 1}">
												<div class="message">
													<p class="text">
														<img class="message-img" alt="" src="<%=cp%>/images/${m.msg_img_url }" onclick="popImg(this.src)">
													</p>
												</div>
												<div class="message">
													<p class="response-timestamp">${fn:substring(m.msg_date,5,16)}</p>
												</div>
											</c:when>
										</c:choose>
									</c:if>
								</c:when>
							
								<c:otherwise>
									메시지가 없습니다.
								</c:otherwise>
							
							</c:choose>
						</c:forEach>
					</div><!-- End .message-chat  -->
					
					
					
					<form action="mimgsend.action" method="post" enctype="multipart/form-data" id="msgForm">
					<!-- .footer-chat -->
						<div class="footer-chat" id="footer">
								<!-- 사진첨부버튼 -->
								
									<div class="filebox">
										<label for="ex_file">
											<i class="icon fa fa-picture-o clickable"
												style="font-size: 20pt; align-self: center;" aria-hidden="true"></i>
										</label>
										<input type="file" id="ex_file" name="msg_img_url" onchange="javascript:document.getElementById('file').value = this.value"
										accept=".jpg,.jpeg,.png,.gif,.bmp">
									</div>
									<input type="text" class="write-message" placeholder="메시지를 입력하세요" name="member_msg_content" id="file"></input>
									<i class="icon send fa fa-paper-plane-o clickable"
										aria-hidden="true" onclick="msgSend();"></i>
								
						</div>
						<input type="hidden" name="msg_code" value="${msg_code }">
						<input type="hidden" name="book_code" value="${book_code }">
					<!-- End .footer-chat -->
					</form>
					
				</div>
			</div>
		</div>
		
	</div>

<div style="height: 150px;"></div>
<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</body>
</html>