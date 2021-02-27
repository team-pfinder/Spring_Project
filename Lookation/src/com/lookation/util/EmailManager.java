package com.lookation.util;


import java.util.Properties;

//import java.io.File;

import javax.mail.internet.MimeMessage;

//import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;


public class EmailManager
{
	private static JavaMailSenderImpl mailSender;
	
	private static void getMailSender()
	{
		if(mailSender == null)
		{
			// <!-- smtp를 사용한 메일 서비스 설정 -->
			mailSender = new JavaMailSenderImpl();
			mailSender.setHost("smtp.gmail.com");
			mailSender.setPort(587);
			mailSender.setUsername("lookation.notice@gmail.com");
			mailSender.setPassword("java006$");
			
			Properties prop = new Properties();
			prop.setProperty("mail.smtp.auth", "true");
			prop.setProperty("mail.smtp.starttls.enable", "true");
			mailSender.setJavaMailProperties(prop);
		}
	}
	
	// 간단한 내용을 메일로 보내기
	public static void sendSimple(String to, String from, String subject, String content)
	{
		getMailSender();
		
		final MimeMessagePreparator preparator = new MimeMessagePreparator()
		{
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception
			{
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				// true : 멀티파트 메세지를 사용하겠다
				
				helper.setFrom(from);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(content);
			}
		};
		
		try
		{
			mailSender.send(preparator);
		} catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	// 첨부파일과 함께 메일로 보내기
	public static void sendAttach(String to, String from, String subject, String content, String filePath)
	{
		getMailSender();
		
		final MimeMessagePreparator preparator = new MimeMessagePreparator()
		{
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception
			{
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.setFrom(from);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(content); 
				
				//FileSystemResource file = new FileSystemResource(new File(filePath));
				
				// [파일명].[확장자명] 만 자르기
				//String[] subArr = filePath.split("/");
				//helper.addAttachment(subArr[subArr.length - 1], file);
			}
		};
		
		try
		{
			mailSender.send(preparator);
		} catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	// HTML을 메일로 보내기
	public static void sendHtml(String to, String from, String subject, String html)
	{
		getMailSender();
		
		final MimeMessagePreparator preparator = new MimeMessagePreparator()
		{
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception
			{
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				// true : 멀티파트 메세지를 사용하겠다
				
				helper.setFrom(from);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(html, true); // true : html을 사용하겠다.
			}
		};
		
		try
		{
			mailSender.send(preparator);
		} catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
}
