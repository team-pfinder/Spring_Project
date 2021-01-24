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
		pageContext.setAttribute("hostCode", hostCode);
	}
	
	if(identify.equals("member"))
	{
		String memberCode = (String)session.getAttribute("memberCode");
		pageContext.setAttribute("memberCode", memberCode);
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lookation</title>
<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">

#reviewForm
{
	width: auto;
	height: auto;
	resize: auto;
}

/* radio 지우기 */
#radio input.star-check[type="radio"] {
  opacity: 0;
  z-index: 99;
}
.star-rate#radio,
.star-rate#radio > .stars {
  display: flex;
  justify-content: space-between;
}
.star-rate#radio > .stars {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  width: 100%;
  height: 100%;
  z-index: 9;
}

.star-rate {
  display: inline-block;
  position: relative;
  width: 100px;
}

.stars [data-star-value] {
  color: #868686;
  position: relative;
  cursor: pointer;
  display: grid;
  align-items: center;
  width: fit-content;
  transition: color 0.3s ease;
}
.stars [data-star-value]::after {
  content: "";
  position: absolute;
  min-width: 8px;
  min-height: 8px;
  width: 50%;
  height: 50%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 50%;
}

.star-check:nth-of-type(1):checked ~ .stars [data-star-value="1"],
.star-check:nth-of-type(2):checked ~ .stars [data-star-value="1"],
.star-check:nth-of-type(2):checked ~ .stars [data-star-value="2"],
.star-check:nth-of-type(3):checked ~ .stars [data-star-value="1"],
.star-check:nth-of-type(3):checked ~ .stars [data-star-value="2"],
.star-check:nth-of-type(3):checked ~ .stars [data-star-value="3"],
.star-check:nth-of-type(4):checked ~ .stars [data-star-value="1"],
.star-check:nth-of-type(4):checked ~ .stars [data-star-value="2"],
.star-check:nth-of-type(4):checked ~ .stars [data-star-value="3"],
.star-check:nth-of-type(4):checked ~ .stars [data-star-value="4"],
.star-check:nth-of-type(5):checked ~ .stars [data-star-value="1"],
.star-check:nth-of-type(5):checked ~ .stars [data-star-value="2"],
.star-check:nth-of-type(5):checked ~ .stars [data-star-value="3"],
.star-check:nth-of-type(5):checked ~ .stars [data-star-value="4"],
.star-check:nth-of-type(5):checked ~ .stars [data-star-value="5"] {
  color: var(--warning);
}
</style>

<script type="text/javascript">

	$(function()
	{	
		// 이용자 리뷰 작성
		$("#submitBtn_m").click(function()
		{
			e.preventDefault();
			
			var form = $("#reviewForm")[0];
			var data = new FormData(form);
			
			if($("input[name=review_rate]:radio:checked").length == 0)
			{
				alert("별점을 선택해주세요"); 
				return false;
			}
			
			//var formData = $("#reviewForm").serialize();
			
			$.ajax({
				type : "post",
				url : "reviewinsert.action",
				enctype: "multipart/form-data",
				data : data,
				processData : false,
				contentType : fales,
				complete : function(xh)
				{	
					//$("#reviewForm").submit();
					window.opener.parent.location.reload();				
					window.close();
				}
			});
			
		});
		
		//$("input[@name=review_rate]").filter('input[@value='+sValue+']').attr("checked", "checked"); 
	});

	// 호스트 리뷰 답글 작성
	function submitBtn_h()
	{
		$.ajax({
			type : "post",
			url : "reviewreplyinsert.action",
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
	<!-- container 시작 -->
	<div class="container">
		<div class="row">
			<div class="col-md-12">
			<!-- 이용자 -->
			<c:if test="${identify eq 'member'}"> 
				<form action="reviewinsert.action" id="reviewForm" enctype="multipart/form-data" method="post">
					<div class="header">
						<h3 class="title my-2">리뷰 작성하기</h3>
						<hr>
						<label class="label-name"> 별점 </label>
	
						<div id="radio" class="star-rate float-right">
							<input type="radio" class="star-check" value="1" name="review_rate"/>
							<input type="radio" class="star-check" value="2" name="review_rate"/>
							<input type="radio" class="star-check" value="3" name="review_rate"/>
							<input type="radio" class="star-check" value="4" name="review_rate"/>
							<input type="radio" class="star-check" value="5" name="review_rate"/>
							
							<div class="stars">
								<span><i data-star-value="1" class="fa fa-star"></i></span>
								<span><i data-star-value="2" class="fa fa-star"></i></span>
								<span><i data-star-value="3" class="fa fa-star"></i></span>
								<span><i data-star-value="4" class="fa fa-star"></i></span>
								<span><i data-star-value="5" class="fa fa-star"></i></span>
							</div>
						</div>
					</div><!-- End .header -->
					
					<div class="body">
						<div class="form-group">
							<%
								String member_code = (String)request.getParameter("member_code");
								pageContext.setAttribute("member_code", member_code);
							%>
							<input type="hidden" value="${loc_code}" name="loc_code">
							<input type="hidden" value="${member_code }" name="member_code">
							<label for="content">내용${memberCode }</label>
							<textarea class="form-control" id="content" name="review_content"
								rows="8" maxlength="3000" required="required"
								placeholder="내용을 입력해주세요."></textarea>
						</div>
						
						<div class="form-group">
								<label for="image">사진 첨부</label>
								<input type="file" id="image" class="form-control" accept="image/*"/>
						</div>
	
					</div><!-- End .body -->
					<hr>
	
					<div class="text-center">
						<button type="button" class="btn btn-dark" onClick="self.close();">닫기</button>
						<button type="submit" class="btn btn-primary" id="submitBtn_m">작성하기</button>
					</div>
				</form>
			</c:if>
			
			<!-- 호스트 -->
			<c:if test="${identify eq 'host'}"> 
				<form action="reviewreplyinsert.action" id="reviewForm" method="post">
					<div class="header">
						<h3 class="title my-2">리뷰 답글 작성하기</h3>
						<hr>
					</div><!-- End .header -->
					
					<!-- 리뷰 답글 작성시 필요한 review_code 받음-->
					<%
						String reviewCode = (String)request.getParameter("reviewCode");
						pageContext.setAttribute("reviewCode", reviewCode);
					%>
					<div class="body">
						<div class="form-group">
							<input type="hidden" value="${reviewCode}" name="review_code">
							<label for="content">내용</label>
							<textarea class="form-control" id="content" name="review_reply_content"
								rows="8" maxlength="3000" required="required"
								placeholder="내용을 입력해주세요."></textarea>
						</div>
	
					</div><!-- End .body -->
					<hr>
	
					<div class="text-center">
						<button type="button" class="btn btn-dark" onClick="self.close();">닫기</button>
						<button type="submit" class="btn btn-primary" onClick="submitBtn_h()">작성하기</button>
					</div>
				</form>
			</c:if>
			
			</div><!-- End .col-md-12 -->
		</div><!-- End .row -->

	</div>
	<!-- .container-fluid End -->


</body>
</html>