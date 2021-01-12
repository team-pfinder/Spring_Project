package com.lookation.controller;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class SendEmailController implements Controller
{
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		
		final MimeMessagePreparator preparator = new MimeMessagePreparator()
		{
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception
			{
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom("lookation@gmail.com");
				helper.setTo("sb921204@naver.com");
				helper.setSubject("테스트 제목입니다.");
				helper.setText("테스트 내용입니다.", true);
			}
		};
		
		mailSender.send(preparator);
		
		/*
		// 인증 url 생성
		String createAccountUrl= "http://localhost:8090/Lookation/actions/createaccount.action";
		String identify = "?identify=" + request.getParameter("identify");
		String type = "&type=" + request.getParameter("type");
		
		String id = "&email=" + request.getParameter("email");
		String pw = "&pw=" + request.getParameter("pw");
		String nick = "&nick=" + request.getParameter("nick");
		String name = "&name=" + request.getParameter("name");
		String tel = "&tel=" + request.getParameter("tel");
		String urlSignUpInfo = id + pw + nick + name + tel;
		
		createAccountUrl += identify + type + urlSignUpInfo;
		System.out.println(createAccountUrl);
		
		// 이메일 전송 
		
		
		// 메인으로 돌아가기
		// action으로 바꿀 예정
		if(identify.equals("user"))
			mav.setViewName("redirect:user/mainUser.jsp");
		else if(identify.equals("host"))
			mav.setViewName("redirect:host/mainHost.jsp");
		*/
		
		mav.setViewName("user/mainUser");
		
		return mav;
	}
}
