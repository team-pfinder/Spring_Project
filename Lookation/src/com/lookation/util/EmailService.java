package com.lookation.util;

import javax.servlet.http.HttpSession;

import org.springframework.mail.SimpleMailMessage;

public class EmailService implements IEmailService
{

	@Override
	public void sendSimple(String emailTo, String emailFrom, String subject, String content)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailTo); // 수신자 설정
		message.setFrom(emailFrom); // 발신자 설정
		message.setSubject(subject); // 제목 설정
		message.setText(content); // 내용 설정
		
	}

	@Override
	public void sendAttach(String email, String name)
	{
		
	}

	@Override
	public void sendHtml(String email, String name, HttpSession session)
	{
		
	}
	
}
