<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	// 세션 접속시 id확인
	//String id= (String)session.getAttribute("sessionID"); 

	String identify = (String)request.getParameter("identify");
	pageContext.setAttribute("identify", identify);
	
	// 부모창 주소 받기
	String reqpage = (String)request.getParameter("reqpage");
	pageContext.setAttribute("reqpage", reqpage);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Q&A수정</title>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<script type="text/javascript">

	/* function submitBtn()
	{
		$.ajax({
			type : "post",
			url : "modifyqnainlist.action",
			complete : function(xh)
			{					
				window.opener.parent.location.reload();				
				window.close();
			}
		});
	} */
	
	function submitBtn_m()
	{
		$.ajax({
			type : "post",
			url : "modifyqna.action",
			complete : function(xh)
			{					
				window.opener.parent.location.reload();				
				window.close();
			}
		});
	}
	
	function submitBtn_h()
	{
		$.ajax({
			type : "post",
			url : "modifyqnareply.action",
			complete : function(xh)
			{					
				window.opener.parent.location.reload();				
				window.close();
			}
		});
	}

</script>

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<c:choose>
				<c:when test="${identify eq 'member'}"> 	
					<form action="modifyqna.action" method="post" class="modifyForm">
						<div class="header">
							<h3 class="title my-2">Q&A 수정하기</h3>
						</div><!-- End .header -->
						
						
						<div class="body">
							<div class="form-group">
								<input type="hidden" value="${modify.qna_code }" name="qna_code">
								<label for="content">내용</label>
								<textarea class="form-control" id="content" name="qna_content"
									rows="8" maxlength="3000">${modify.qna_content }</textarea>
							</div>
						</div><!-- End .body -->
						<hr>
		
						<div class="text-center">
							<button type="button" class="btn btn-dark" onClick="self.close();">닫기</button>
							<button type="submit" class="btn btn-primary" id="submitBtn" onClick="submitBtn_m()">작성하기</button>
						</div>
					</form>
				</c:when>
				
				<c:when test="${identify eq 'host'}"> 
					<form action="modifyqnareply.action" method="post">
						<div class="header">
							<h3 class="title my-2">Q&A 답글 수정하기</h3>
						</div><!-- End .header -->
						
						
						<div class="body">
							<div class="form-group">
								<input type="hidden" value="${modifyQnaReply.qna_reply_code }" name="qna_reply_code">
								<label for="content">내용</label>
								<textarea class="form-control" id="content" name="qna_reply_content"
									rows="8" maxlength="3000">${modifyQnaReply.qna_reply_content }</textarea>
							</div>
						</div><!-- End .body -->
						<hr>
		
						<div class="text-center">
							<button type="button" class="btn btn-dark" onClick="self.close();">닫기</button>
							<button type="submit" class="btn btn-primary" onClick="submitBtn_h()">작성하기</button>
						</div>
					</form>
				</c:when>
				</c:choose>
			</div><!-- End .col-md-12 -->
		</div><!-- End .row -->

	</div>
	<!-- .container-fluid End -->


<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
</body>
</html>