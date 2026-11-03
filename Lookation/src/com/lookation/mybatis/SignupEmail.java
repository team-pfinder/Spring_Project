package com.lookation.mybatis;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHostAccountDAO;
import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dao.ISignupVerificationDAO;
import com.lookation.dto.SignupPendingDTO;
import com.lookation.util.EmailManager;
import com.lookation.util.PasswordHasher;
import com.lookation.util.TokenGenerator;

@Controller
public class SignupEmail
{
	private static final long TOKEN_VALID_MILLIS = 24L * 60 * 60 * 1000;

	@Autowired
	private SqlSession sqlSession;

	@RequestMapping(value = "/actions/sendsignupemail.action", method = RequestMethod.POST)
	public String send(HttpServletRequest request, Model model) throws Exception
	{
		String identify = request.getParameter("identify");
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String nick = request.getParameter("nick");
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");

		if (email == null || nick == null || pw == null || name == null || tel == null)
		{
			return "../WEB-INF/views/common/wrongAccess.jsp?identify=" + identify;
		}

		IMemberAccountDAO memberDao = sqlSession.getMapper(IMemberAccountDAO.class);
		IHostAccountDAO hostDao = sqlSession.getMapper(IHostAccountDAO.class);
		com.lookation.dto.AccountDTO probe = new com.lookation.dto.AccountDTO();
		probe.setEmail(email);
		probe.setNick(nick);
		if (memberDao.countEmail(probe) > 0 || memberDao.countNick(probe) > 0
				|| hostDao.countEmail(probe) > 0 || hostDao.countNick(probe) > 0)
		{
			return "../WEB-INF/views/common/createAccountFailNotice.jsp";
		}

		String token = TokenGenerator.newToken();
		SignupPendingDTO pending = new SignupPendingDTO();
		pending.setToken_hash(TokenGenerator.sha256(token));
		pending.setIdentify(identify);
		pending.setEmail(email);
		pending.setPw_hash(PasswordHasher.hash(pw));
		pending.setNick(nick);
		pending.setName(name);
		pending.setTel(tel);
		pending.setExpires_at(new Date(System.currentTimeMillis() + TOKEN_VALID_MILLIS));

		ISignupVerificationDAO verificationDao = sqlSession.getMapper(ISignupVerificationDAO.class);
		verificationDao.insertPending(pending);

		String link = siteUrl() + "/actions/verifyemail.action?token=" + token;
		String html = "<html><body>"
				+ "<h1>Lookation 계정 인증</h1>"
				+ "<p>아래 링크를 눌러 계정 생성을 완료해 주세요. 링크는 24시간 동안 한 번만 사용할 수 있습니다.</p>"
				+ "<p><a href=\"" + link + "\">계정 인증하기</a></p>"
				+ "</body></html>";
		EmailManager.sendHtml(email, "Lookation", "Lookation 계정 인증 안내", html);

		if ("host".equals(identify))
		{
			return "redirect:hostmain.action";
		}
		return "redirect:membermain.action";
	}

	private String siteUrl()
	{
		return com.lookation.util.AppConfig.require("LOOKATION_SITE_URL");
	}
}
