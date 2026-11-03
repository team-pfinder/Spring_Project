package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IAccountDAO;
import com.lookation.dao.ISignupVerificationDAO;
import com.lookation.dto.AccountDTO;
import com.lookation.dto.SignupPendingDTO;
import com.lookation.util.TokenGenerator;

@Controller
public class VerifyEmail
{
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	@Qualifier("memberAccountDAO")
	private IAccountDAO memberDao;

	@Autowired
	@Qualifier("hostAccountDAO")
	private IAccountDAO hostDao;

	@RequestMapping(value = "/actions/verifyemail.action", method = RequestMethod.GET)
	public String confirm(HttpServletRequest request, Model model)
	{
		model.addAttribute("token", request.getParameter("token"));
		return "../WEB-INF/views/common/emailVerifyConfirm.jsp";
	}

	@RequestMapping(value = "/actions/verifyemail.action", method = RequestMethod.POST)
	public String verify(HttpServletRequest request, Model model) throws Exception
	{
		String token = request.getParameter("token");
		if (token == null || token.isEmpty())
		{
			return "../WEB-INF/views/common/emailVerifyFail.jsp";
		}

		ISignupVerificationDAO verificationDao = sqlSession.getMapper(ISignupVerificationDAO.class);
		String token_hash = TokenGenerator.sha256(token);
		SignupPendingDTO pending = verificationDao.findValid(token_hash);
		if (pending == null)
		{
			return "../WEB-INF/views/common/emailVerifyFail.jsp";
		}

		if (memberDao.countEmail(pending.getEmail()) > 0 || hostDao.countEmail(pending.getEmail()) > 0)
		{
			return "../WEB-INF/views/common/emailVerifyFail.jsp";
		}
		if (verificationDao.markUsed(token_hash) == 0)
		{
			return "../WEB-INF/views/common/emailVerifyFail.jsp";
		}

		AccountDTO account = new AccountDTO();
		account.setEmail(pending.getEmail());
		account.setPw(pending.get_pw_hash());
		account.setNick(pending.getNick());
		account.setName(pending.getName());
		account.setTel(pending.getTel());

		if ("host".equals(pending.getIdentify()))
		{
			hostDao.add(account);
		}
		else
		{
			memberDao.add(account);
		}
		return "../WEB-INF/views/common/emailVerified.jsp";
	}
}
