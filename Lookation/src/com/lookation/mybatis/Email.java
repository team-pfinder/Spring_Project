package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IPasswordResetDAO;
import com.lookation.dto.PasswordResetDTO;
import com.lookation.util.EmailManager;
import com.lookation.util.TokenGenerator;


@Controller
public class Email
{
	@Autowired
	private SqlSession sqlSession;


	@RequestMapping(value="/actions/sendconfirmemail.action", method= {RequestMethod.POST, RequestMethod.GET})
	public String sendPasswordEmail(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");
		String email = request.getParameter("email");
		
		if(email == null)
		{
			return "../WEB-INF/views/common/wrongAccess.jsp?identify=" + identify;
		}
		
		String token = TokenGenerator.newToken();
		PasswordResetDTO reset = new PasswordResetDTO();
		reset.setTokenHash(TokenGenerator.sha256(token));
		reset.setIdentify(identify);
		reset.setEmail(email);
		reset.setExpiresAt(new java.util.Date(System.currentTimeMillis() + 30L * 60 * 1000));
		IPasswordResetDAO resetDao = sqlSession.getMapper(IPasswordResetDAO.class);
		resetDao.insert(reset);
		String siteUrl = System.getProperty("lookation.site.url", System.getenv("LOOKATION_SITE_URL"));
		if (siteUrl == null || siteUrl.isEmpty()) { siteUrl = "https://sb92120.cafe24.com"; }
		String resetLink = siteUrl + "/actions/changepasswordnologinform.action?identify=" + identify + "&token=" + token;

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
				"			<p>아래 링크는 30분 동안 한 번만 사용할 수 있습니다.</p>" + 
				"			<p><a href=\"" + resetLink + "\">비밀번호 변경하기</a></p>" + 
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


		EmailManager.sendHtml(email, "lookation", title, html);

		
		model.addAttribute("email", email);
		model.addAttribute("result", "noSigned");
		
		return "../WEB-INF/views/common/sendFindPassword.jsp?identify" + identify;
	}
}
