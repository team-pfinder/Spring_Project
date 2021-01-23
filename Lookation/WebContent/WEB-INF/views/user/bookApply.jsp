<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약 신청화면.jsp</title>
<c:import url="${cp}/includes/header_user.jsp"></c:import>
<c:import url="${cp}/includes/includes_home.jsp"></c:import>
<style type="text/css">

	.res-form-box
	{
	background-color: #FFF;
	}
	
	.div-table{
	display: table;
	width: 100%;
	}
	
	.div-table-body {
	display: table-row-group;
	}
	

	.div-row {
	display: table-row;
	}
	
	.div-col{
	display: table-cell;
	padding: 3px 10px;
	}
	
	.notice{
	padding: 3px 17px 13px;
	}
	
	.det{
	max-width: 150px;
	}
	
	.btn-gon {
    color: #FFF;
    background-color: #00043c;
    }
    
    .btn-gon:hover {
    color: #FFF;
    background-color: #00043cdb;
    }
    
    /*=== 제목 밑줄 ===*/
	.divider:after {
    content: "";
    display: block;
    border-bottom: 4px solid #00043c;
    margin: 10px 0px;
	}
	
	.vertical-top {
	vertical-align: top;
	margin-bottom: auto;
	}
	
	/*=== 폼 입력 검증 ===*/
	input.err
	{
		border-color: red;
	}
	
	p.err 
	{
	    display: display;
		font-size: 14px;
		text-align: left;
		color: red;
	}
	
	
	p.pass
	{
		display: none;
	}
	
</style>
<script type="text/javascript">


	$(document).ready(function()
	{
		
		// 전화번호 정규식
		var regTel = /^\d{2,3}-\d{3,4}-\d{4}$/;
		// 이름 정규식
		var regName = /^[가-힣]{2,10}$/;

		// 전화번호 정규식 검사(입력시마다)
		$("#tel").keyup(function()
		{
			//alert("나와");
			if(!regTel.test($(this).val()))
			{	
				$(this).removeClass("full");
				$(this).addClass("err");
				$("p#tel").attr("class", "err");
			}
			else
			{
				$(this).removeClass("err");
				$(this).addClass("full");
				$("p#tel").attr("class", "pass");	
			}
		});
		
		// 이름 정규식 검사(입력시마다)
		$("#name").keyup(function()
		{
			if(!regName.test($(this).val()))
			{	
				$(this).removeClass("full");
				$(this).addClass("err");
				$("p#name").attr("class", "err");
			}
			else
			{
				$(this).removeClass("err");
				$(this).addClass("full");
				$("p#name").attr("class", "pass");	
			}
		});
		
		
		/* 기존 회원정보로 세팅 */
		$("#setProfile").change(function()
		{
			if($("#setProfile").is(":checked"))
			{
				$("#name").prop("value", $("#hiddenName").val());
				$("#tel").prop("value", $("#hiddenTel").val());
			}
			else
			{
				$("#name").prop("value", "");
				$("#tel").prop("value", "");
			}
		});
		
		/* 체크박스 전체 선택 */
		$("#allConfirm").click(function()
		{
			//전체 선택 체크
			if ($("#allConfirm").prop("checked"))
			{
				// 해당화면 전체 checkbox 체크하는 구문
				$("[name=checkOne]").prop("checked", true);
			}
			// 전체 선택 해제
			else
			{
				// 해당화면 전체 checkbox 체크 해제 하는 구문
				$("[name=checkOne]").prop("checked", false);
			}
			
			$("[name=checkOne]").each(function()
			{
				$(this).click(function()
				{
					oneCheckFunc( $(this) );
				});
			});
		});
		
		// 서브밋 전 폼 입력값 확인
		$(".submitBtn").click(function()
		{
			// 연락처 형식이 맞지 않을경우
			if(!regTel.test($("input#tel").val()))
			{
				alert("연락처 형식이 알맞지 않습니다. 다시 입력해주세요.");
				return;
			}
			
			// 예약자명이 누락되었을 경우
			if($("#name").val()=="")
			{
				alert("예약자명이 누락되었습니다. 다시 입력해주세요.");
				return;
			}
			
			// 체크박스 전체 선택되지 않았을 경우
			
			if($("input[type=checkbox]").filter(':checked').size()<4)
			{
				alert("모든 이용약관에 동의해 주세요.");
				return;
			};
			
			$("#bookForm").submit();
			
		});
		
		
		
	});
	
	// 체크박스 하나라도 해제시
	// □ 전체 동의 체크박스 해제
	function oneCheckFunc( obj )
	{
		var allObj = $("#allConfirm");
		var objName = $(obj).attr("name");

		if( $(obj).prop("checked") )
		{
			checkBoxLength = $("[name="+ objName +"]").length;
			checkedLength = $("[name="+ objName +"]:checked").length;

			if( checkBoxLength == checkedLength ) {
				allObj.prop("checked", true);
			} else {
				allObj.prop("checked", false);
			}
		}
		else
		{
			allObj.prop("checked", false);
		}
	}
	
	
	
</script>

</head>
<body>

<section class="hero-wrap hero-wrap-2" style="background-image: url('<%=cp%>/images/bg_3.jpg');" data-stellar-background-ratio="0.5">
  	<div class="overlay"></div>
  	<div class="container">
    	<div class="row no-gutters slider-text align-items-end">
      		<div class="col-md-9 ftco-animate pb-5">
      			<p class="breadcrumbs mb-2"><span class="mr-2"><a href="home.action">Home <i class="ion-ios-arrow-forward"></i></a></span> <span><a href="redirect:locationdetail.action">Space <i class="ion-ios-arrow-forward"></i></a></span></p>
        		<h1 class="mb-0 bread">선택한 공간 예약하기</h1>
      		</div>
    	</div>
  	</div>
</section>


<section class="ftco-section ftco-degree-bg">
	<div class="container">
		<div class="row">
			<div class="col-lg-8 w-100 p-md-5 res-form-box">
				<input type="hidden" value="${profile.member_name }" id="hiddenName"> 
				<input type="hidden" value="${profile.member_tel }" id="hiddenTel">
				<p class="ftco-animate">
					<img src="<%=cp%>/images/image_1.jpg" alt="템플릿 이미지" class="img-fluid">
				</p>
				<input hidden="hidden" value="${check.mileage }" name="mileage">
				<h2 class="mb-3 mt-5 font-weight-bold"># 공간 정보</h2><hr>
				<div class="form-inline form-group">
					<p class="col-md-2">공간명</p>
					<p class="col-md-10">${basic.loc_name }</p>
				</div>
				
				<div class="form-inline form-group">
					<p class="col-md-2">공간유형</p>
					<p class="col-md-10">${basic.loc_type }</p>
				</div>
				
				<div class="form-inline form-group">
					<p class="col-md-2">한줄소개</p>
					<p class="col-md-10">${basic.loc_short_intro }</p>
				</div>
				
			
				<div class="form-inline form-group">
					<p class="col-md-2">예약인원</p>
					<p class="col-md-10">최소 ${basic.min_people }명 ~ 최대 ${basic.max_people }명</p>
				</div>

				<h2 class="mb-3 mt-5 font-weight-bold"># 예약 정보</h2><hr>
				
				<!-- ※ 임시!!! 전페이지에서 받아와야 할 정보들 -->
				<div class="form-inline form-group">
					<p class="col-md-2 vertical-top">패키지명</p>
					<p class="col-md-10">${selectPack.package_name }</p>
				</div>
				
				<div class="form-inline form-group">
					<p class="col-md-2">예약일시</p>
					<p class="col-md-10">${fn:substring(selectPack.apply_date, 0, 10)} ${selectPack.package_start}시 ~ ${selectPack.package_end}시 (총 ${selectPack.hours} 시간)</p>
				</div>
				
				<div class="form-inline form-group">
					<p class="col-md-2">예약인원</p>
					<p class="col-md-10">${book_people }</p>
				</div>

				<h2 class="mb-3 mt-5 font-weight-bold"># 예약자 정보</h2><hr>
				<form class="ftco-animate" action="bookpay.action" method="post" id="bookForm">
					<!-- 체크박스 클릭시 예약자 정보 입력란에 기존 회원정보 자동으로 불러옴
						 수정 가능 -->
					<div class="text-right mr-3">
						<input type="checkbox" id="setProfile" class="form-control-m">
						<label for="loadUserInfo">입력한 회원정보와 동일</label><br>
					</div>
						 
					<div class="div-table">
						<div class="div-table-body">
							<div class="div-row">
								<div class="div-col">
									<label for="name" class="control-label">
									<small class="text-danger">(*)</small>예약자명
									</label>
								</div>
								<div class="div-col">
									<div class="form-inline form-group">
									<input type="text" id="name" name="actual_booker" class="form-control"
									 	placeholder="예약자명" required="required" maxlength="20" style="width: 250px;">
									</div>
									<p class="pass" id="name">예약자명의 형식이 잘못되었습니다.</p>
								</div>
								
							</div>
							
							<div class="div-row">
								<div class="div-col">
									<label for="3" class="control-label">
									<small class="text-danger">(*)</small>연락처</label>
								</div>
								<div class="div-col">
									<div class="form-inline form-group">
										<!-- 숫자만 입력하도록 정규식 검증 -->
										<input type="tel" id="tel" name="actual_booker_tel" class="form-control" style="width: 250px;"
										placeholder="'-' 포함하여 입력해주세요." required="required">
									</div>
									<p class="pass" id="tel">연락처 형식이 알맞지 않습니다.</p>
								</div>
							</div>
							
							<div class="div-row">
								<div class="div-col"><label for="4" class="control-label"><small class="text-danger">(*)</small>요청사항</label></div>
								<div class="div-col">
									<textarea class="form-control" rows="4" name="book_req" required="required"
									placeholder="남기고 싶은 말을 적어주세요. (최대 100자까지 입력 가능)"  maxlength="100" style="resize: none;"></textarea>
								</div>
							</div>
							
							<!-- 임시 코드 -->
							<input type="hidden" value="${profile.member_code }" name="member_code">
							<input type="hidden" value="${apply_package_code }" name="apply_package_code">
							<input type="hidden" value="${book_people }" name="book_people">
							<input type="hidden" value="${selectPack.package_price }" name="package_price">
						</div><!-- End .div-table-body -->
					</div><!-- End .div-table -->
				</form>
				
						
				<h2 class="mb-3 mt-5 font-weight-bold"># 호스트 정보</h2><hr>
				<!-- 해당 공간의 호스트 사업자 정보 출력-->
				
				
				<div class="hostInfo ftco-animate">
					<div class="form-inline form-group">
						<p class="col-md-2">공간상호</p>
						<p class="col-md-10">${bizinfo.biz_name}</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2">대표자명</p>
						<p class="col-md-10">${bizinfo.biz_ceo}</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2">소재지</p>
						<p class="col-md-10">${bizinfo.biz_addr}</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2">사업자번호</p>
						<p class="col-md-10">${bizinfo.biz_license_number}</p>
					</div>

					<div class="form-inline form-group">
						<p class="col-md-2 align-self-start">연락처</p>
						<div class="col-md-10">
							<p>${bizinfo.loc_tel}</p>
							<p>${bizinfo.loc_main_tel}</p>
							<p>${bizinfo.loc_email}</p>
						</div>
					</div>
				</div>
				
				<h2 class="mb-3 mt-5 font-weight-bold"># 환불 규정</h2><hr>
				
				
				<div class="ftco-animate">
					<div class="memo">
						<span class="text-body">이용 1일전 23:59까지만 취소 가능.</span>
						<span class="text-danger mb-4">이용당일(첫 날) 환불은 불가능합니다. 관련 사항은 호스트에게 직접 문의하세요.</span>
					</div>
			
					
					<div class="row">
						<div class="col-lg-3 ftco-animate">
							<p>이용 7일전</p>
							<p>이용 6일전 ~ 1일전</p>
							<p>예약 당일</p>
						</div>
	
	
						<!-- 예약자 정보 입력란에 기존 회원정보 자동으로 불러옴 -->
						<!-- 수정 가능 -->
						<div class="col-lg-8 ftco-animate">
							<p>총 금액의 100% 환불</p>
							<p>총 금액의 50% 환불</p>
							<p>환불 불가</p>
						</div>
					</div>
				</div>
				
				
				<h2 class="mb-3 mt-5 font-weight-bold"># 서비스 동의 </h2> <hr>
				
				
				<div class="ftco-animate">
					<strong>이용약관</strong>
					<div class="terms p-3" style="font-size: 0.8em; height: 100px; overflow-y: scroll;">
						<div class="">
							<p>제 1 조 (목적)</p>
							이 약관은 주식회사 Lookation가 제공하는
							Lookation(http://www.lookation.com)온라인 서비스 (이하 "서비스")의 이용과 관련하여
							회사와 회원과의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 한다.
						</div>
						<br>
						<div>
							<p>제 2 조 (정의)</p>
							이 약관에서 사용하는 용어의 정의는 다음과 같다. 1. "서비스"라 함은 유휴 공간(이하 "공간") 정보의 공유 및
							예약을 위하여 "회사"가 Lookation을 통해 제공하는 온라인 서비스를 일컫는다. 2. "사이트"라 함은
							"회사"가 "서비스"를 운영하는 웹사이트를 의미한다. 현재는 https://www.lookation.com 이다.
						</div>
					</div>
					
					<!-- 체크박스 크기 조절  -->
					<!-- 전체동의 클릭시 아래의 체크박스 4개 동시 체크 -->
					<div class="text-right mt-4">
						<input type="checkbox" id="allConfirm" class="checkbox">
						<label for="allConfirm">전체 동의</label><br>
					</div>
					
					<div class="my-2">
						<!-- 전부 다 체크했는지 확인 -->
						<p>
							<input type="checkbox" name="checkOne">
							<small class="text-danger">(필수)</small> 
							위 공간의 예약조건 확인 및 결제진행 동의
						</p>
						
						<p>
							<input type="checkbox" name="checkOne">
							<small class="text-danger">(필수)</small> 
							환불규정 안내에 대한 동의
						</p>
						
						<p>
							<input type="checkbox" name="checkOne">
							<small class="text-danger">(필수)</small> 
							개인정보 제3자 제공 동의
						</p>
						
						<p>
							<input type="checkbox" name="checkOne"> 
							<small class="text-danger">(필수)</small> 
							개인정보 수집 및 이용 동의
						</p>
						
						<small class="text-danger"><span class="fa fa-check mr-2"></span> 
							서비스 이용약관 동의는 필수입니다.</small>
					</div>
						
					<!-- 버튼 클릭시 폼 내용 검증 -->
					<!-- 검증 완료시 submit 후 DB 예약내역, 결제내역 테이블에 추가 -->
					<div class="row my-5">
						<button class="btn btn-primary btn-block submitBtn" type="button">
							동의하고 결제하기
						</button>
					</div>
					
					<!-- 맨 위로 올라가는 버튼 만들기 -->
				
				</div>
			</div>

			<!-- 오른쪽 사이드바 -->
			<div class="col-lg-4 sidebar pl-lg-5 ftco-animate">
				<div class="sidebar-box p-4 ftco-animate">
					<div class="categories">
						<h3>결제 예정금액</h3>
						<div class="divider" style="width: 120px;"></div>
						<!-- 이전 페이지(locDetail.jsp)에서 정보 받아옴 -->
						<div class="div-table">
							<div class="div-table-body">
								<div class="div-row">
									<div class="div-col font-weight-bold">예약날짜</div>
									<div class="div-col text-right det">${fn:substring(selectPack.apply_date, 0, 10)}</div>
								</div>
								
								<div class="div-row">
									<div class="div-col font-weight-bold">패키지명</div>
									<div class="div-col text-right det">
									${selectPack.package_name}</div>
								</div>
								
								<div class="div-row">
									<div class="div-col font-weight-bold">예약시간</div>
									<div class="div-col text-right det">
								${selectPack.package_start}시 ~ ${selectPack.package_end}시 총 ${selectPack.hours} 시간</div>
								</div>
								
								<div class="div-row">
									<div class="div-col font-weight-bold">예약인원</div>
									<div class="div-col text-right det">${book_people}</div>
								</div>
								
							</div><!-- End .div-table-body -->
						</div><!-- End .div-table -->
						<div class="divider my-3"></div>
						<div class="text-right">
							<h3><span class="icon-won mr-3"></span>${selectPack.package_price } 원</h3>
						</div>
						<br>
						

					</div>
				</div>
			</div><!-- 오른쪽 사이드바 끝 -->
		</div>
	</div>	
</section>





	
<c:import url="${cp}/includes/includes_home_end.jsp"></c:import>
<div>
	<c:import url="${cp}/includes/footer_user.jsp"></c:import>
</div>
</body>
</html>