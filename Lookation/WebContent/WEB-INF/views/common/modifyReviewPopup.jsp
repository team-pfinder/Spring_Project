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
		$(".submitBtn").click(function()
		{
			if($("input[name=review_rate]:radio:checked").length == 0)
			{
				alert("별점을 선택해주세요"); 
				return;
			}
			$("#reviewForm").submit();
			window.close();
			
		});
		//$("input[@name=review_rate]").filter('input[@value='+sValue+']').attr("checked", "checked"); 
	});
	
</script>

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<!-- 이용자 -->
				<c:if test="${identify eq 'member'}"> 
				
					<c:choose>
						<c:when test="${reqpage eq 'list'}">
							<form action="modifyreviewinlist.action" method="post" id="reviewForm" target="redirect:userreviewlist.action">
								<div class="header">
									<h3 class="title">리뷰 수정하기</h3>
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
								</div>
							
								<div class="body">
									<div class="form-group">
										<input type="hidden" value="${modifyReview.review_code }" name="review_code">
										<label for="content">내용</label>
										<textarea class="form-control" id="content" name="review_content"
											rows="8" maxlength="3000">${modifyReview.review_content }</textarea>
									</div>
			
									<div class="form-group">
										<label for="image">사진 첨부</label>
										<input type="file" id="image"
											class="form-control">
									</div>
									
									<div class="text-center">
										<button type="button" class="btn btn-dark" onClick="window.close();">목록으로</button>
										<button type="button" class="btn btn-primary submitBtn">수정하기</button>
									</div>
								</div><!-- End .body -->
							</form>
						</c:when>
					<c:otherwise>
						<form action="modifyreview.action" method="post" id="reviewForm" target="redirect:locationdetail.action">
							<div class="header">
								<h3 class="title">리뷰 수정하기</h3>
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
							</div>
						
							<div class="body">
								<div class="form-group">
									<input type="hidden" value="${modifyReview.review_code }" name="review_code">
									<label for="content">내용</label>
									<textarea class="form-control" id="content" name="review_content"
										rows="8" maxlength="3000">${modifyReview.review_content }</textarea>
								</div>
		
								<div class="form-group">
									<label for="image">사진 첨부</label>
									<input type="file" id="image"
										class="form-control">
								</div>
								
								<div class="text-center">
									<button type="button" class="btn btn-dark" onClick="window.close();">목록으로</button>
									<button type="button" class="btn btn-primary submitBtn">수정하기</button>
								</div>
							</div><!-- End .body -->
						</form>
					</c:otherwise>
					</c:choose>
				</c:if>	
				
					
				<!-- 호스트 -->
				
				<c:if test="${identify eq 'host'}"> 
					<form action="modifyreviewreply.action" method="post" target="redirect:locationdetailhost.action">
						<div class="header">
							<h3 class="title">리뷰 수정하기</h3>
						</div>
						
					
						<div class="body">
							<div class="form-group">
								<input type="hidden" value="${modifyReviewReply.review_reply_code }" name="review_reply_code">
								<label for="content">내용</label>
								<textarea class="form-control" id="content" required="required" name="review_reply_content"
									rows="8" maxlength="3000">${modifyReviewReply.review_reply_content }</textarea>
							</div>
	
							<div class="text-center">
								<button type="button" class="btn btn-dark" onClick="window.close();">목록으로</button>
								<button type="submit" class="btn btn-primary" onClick="self.close();">수정하기</button>
							</div>
						</div><!-- End .body -->
					</form>
				</c:if>
					
				
			</div><!-- End .col-md-12 -->
		</div><!-- End .row -->
	</div>
	<!-- .container End -->

</body>
</html>