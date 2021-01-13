package com.lookation.util;


public interface IEmailService
{
	public void sendSimple(String to, String from, String subject, String content);
	public void sendAttach(String to, String from, String subject, String content, String filePath);
	public void sendHtml(String to, String from, String subject, String html);
}
