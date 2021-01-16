package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.util.IEmailService;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class SendSignUpEmailController implements Controller
{
	private IEmailService util;

	public void setUtil(IEmailService util)
	{
		this.util = util;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		//util.sendSimple("sb921204@naver.com", "Lookation", "이건 테스트양", "응 데스틍");
		
		/*
		 String filePath = "C:/Users/Bum/Desktop/GitHub/Spring_Project" +
		 "/Lookation/WebContent/includes/test.html";
		 util.sendAttach("sb921204@naver.com", "Lookation", "이건 테스트양", "응 데스틍",
		 filePath);
		 */
		
		// 인증 url 생성
		String identify = request.getParameter("identify");
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String nick = request.getParameter("nick");
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		
		// 이메일 전송
		
		// html 폼 준비
		String html = "<!DOCTYPE html>" + 
				"<html>" + 
				"<head>" + 
				"<meta charset=\"UTF-8\">" + 
				"<title>email.html</title>" + 
				"</head>" + 
				"<body class=\"back-default\" style=\"background-color: #f6f6f6;\">" + 
				"	<div class=\"email-form\" style=\"font-family : 'Poppins', Arial, sans-serif; width : 800px; margin:0 auto; text-align: center;\">" + 
				"		<div style=\"text-align: center; padding-top: 40px;\">" + 
				"			<h1 class=\"brand\" style=\"font-size: 64px; font-size: 400%; font-weight: 800;\">Look<span style=\"color: #fdbe34\">ation.</span></h1>" + 
				"		</div>" + 
				"		<hr>	" + 
				"		<div class=\"content-form\" style=\"width : 100%; background: #ffffff; padding: 50px 0px 50px 0px;\">" + 
				"			<!-- <h1>내용이 입력될 곳</h1> -->" + 
				"			<h1>계정 인증 안내</h1>" + 
				"			<br>" + 
				"			<form action=\"http://localhost:8090/Lookation/actions/signup.action?identify="+ identify + "\" method=\"post\">" + 
				"				<table style=\"margin:0 auto;\">" + 
				"					<tr>" + 
				"						<th>이메일 : </th>" + 
				"						<td>" + email + "</td>" + 
				"					</tr>" + 
				"					<tr>" + 
				"						<th>연락처 : </th>" + 
				"						<td>" + tel + "</td>" + 
				"					</tr>" + 
				"					<tr>" + 
				"						<th>이름 : </th>" + 
				"						<td>"+ name +"</td>" + 
				"					</tr>" + 
				"					<tr>" + 
				"						<th>닉네임 : </th>" + 
				"						<td>"+ nick +"</td>" + 
				"					</tr>" + 
				"				</table>" + 
				"				<input type=\"hidden\" name=\"email\" value=\"" + email + "\">" + 
				"				<input type=\"hidden\" name=\"pw\" value=\""+ pw +"\">" + 
				"				<input type=\"hidden\" name=\"nick\" value=\"" + nick + "\">" + 
				"				<input type=\"hidden\" name=\"name\" value=\"" + name + "\">" + 
				"				<input type=\"hidden\" name=\"tel\" value=\"" + tel + "\">" + 
				"				<h2>계정 인증을 위해서 아래 버튼을 클릭해주세요.</h2>" + 
				"				<button type=\"submit\">인증하기</button>" + 
				"			</form>" + 
				"		</div>" + 
				"		<hr>" + 
				"		<div>" + 
				"			<p>본 메일은 발신 전용 입니다. 이용 관련 제안사항은 <a class=\"clickAble\" href=\"#\">FAQ</a>를 이용해주세요.</p>" + 
				"			<p> <a class=\"clickAble\" href=\"#\">이용약관</a> | <a class=\"clickAble\" href=\"#\">개인정보처리방침</a></p>" + 
				"			<p>Copyright ⓒ Pfinder Corp. All Rights Reserved.</p>" + 
				"		</div>" + 
				"	</div>" + 
				"</body>" + 
				"</html>";
		
		String titleType = (identify.equals("host") ? "호스트" : "이용자");
		String title = "■■■■■ " + titleType + " 계정생성 인증 메일입니다.  ■■■■■";
		
		util.sendHtml(email, "Lookation", title, html);
		
		/*
		 * 	
		// 메인으로 돌아가기
		// action으로 바꿀 예정
		if(identify.equals("user"))
			mav.setViewName("redirect:user/mainUser.jsp");
		else if(identify.equals("host"))
			mav.setViewName("redirect:host/mainHost.jsp");
		*/
		
		// for test
		mav.setViewName("../WEB-INF/views/user/mainUser.jsp");
		
		return mav;
	}
}
