package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.util.IEmailService;

@Controller
public class Email
{
	@Autowired
	private IEmailService emailService;
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value="/actions/sendconfirmemail.action", method=RequestMethod.POST)
	public String sendPasswordEmail(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		String email = request.getParameter("email");
		
		String html = 
				"<!DOCTYPE html>" + 
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
				"		<hr>" + 
				"		<div class=\"content-form\" style=\"width : 100%; background: #ffffff; padding: 50px 0px 50px 0px;\">" + 
				"			<!-- <h1>내용이 입력될 곳</h1> -->" + 
				"			<h1>비밀번호 변경 안내</h1>" + 
				"			<br>" + 
				"			<form action=\"http://localhost:8090/Lookation/actions/changepasswordnologinform.action?identify=" + identify + "\" method=\"post\">" + 
				"				<p>변경할 계정의 이메일을 다시 한번 확인해주세요.</p>" + 
				"				<table style=\"margin:0 auto;\">" + 
				"					<tr>" + 
				"						<th>이메일 : </th>" + 
				"						<td>"+ email +"</td>" + 
				"					</tr>		" + 
				"				</table>" + 
				"				<input type=\"hidden\" name=\"email\" value=\""+ email +"\">" + 
				"				<h2>비밀번호 변경을 위해 아래 버튼을 클릭해주세요.</h2>" + 
				"				<button type=\"submit\">변경하기</button>" + 
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
		String title = "■■■■■ " + titleType + " 비밀번호 변경 인증 메일입니다.  ■■■■■";
		
		emailService.sendHtml(email, "lookation", title, html);
		
		model.addAttribute("email", email);
		model.addAttribute("result", "noSigned");
		
		return "../WEB-INF/views/common/sendConfirmAccount.jsp?identify" + identify;
	}
}
