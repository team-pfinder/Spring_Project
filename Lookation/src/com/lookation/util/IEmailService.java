package com.lookation.util;

import javax.servlet.http.HttpSession;

public interface IEmailService
{
	public void sendSimple(String emailTo, String emailFrom, String subject, String content);
	public void sendAttach(String email, String name);
	public void sendHtml(String email, String name, HttpSession session);
}
