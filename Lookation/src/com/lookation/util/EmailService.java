package com.lookation.util;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class EmailService implements IEmailService
{
	private JavaMailSenderImpl mailSender;
	
	public void setMailSender(JavaMailSenderImpl mailSender)
	{
		this.mailSender = mailSender;
	}

	@Override
	public void sendSimple(String to, String from, String subject, String content)
	{
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

	@Override
	public void sendAttach(String to, String from, String subject, String content, String filePath)
	{
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
				
				FileSystemResource file = new FileSystemResource(new File(filePath));
				
				// [파일명].[확장자명] 만 자르기
				String[] subArr = filePath.split("/");
				helper.addAttachment(subArr[subArr.length - 1], file);
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

	@Override
	public void sendHtml(String to, String from, String subject, String html)
	{
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
