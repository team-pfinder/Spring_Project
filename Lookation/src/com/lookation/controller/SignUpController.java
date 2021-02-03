// CreateAccountController.java
// - 사용자 정의 컨트롤러 클래스


package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IAccountDAO;
import com.lookation.dto.AccountDTO;

public class SignUpController implements Controller
{
	private IAccountDAO memberDao, hostDao;
	
	public void setMemberDao(IAccountDAO memberDao)
	{
		this.memberDao = memberDao;
	}

	public void setHostDao(IAccountDAO hostDao)
	{
		this.hostDao = hostDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		String identify = request.getParameter("identify");
		String email = request.getParameter("email");
		String nick = request.getParameter("nick");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		
		if(email == null || nick == null || pw == null || name == null || tel == null)
		{
			mav.setViewName("../WEB-INF/views/common/wrongAccess.jsp?identify=" + identify);
			return mav;
		}
		
		AccountDTO account = new AccountDTO();
		account.setEmail(email);
		account.setNick(nick);
		account.setPw(pw);
		account.setName(name);
		account.setTel(tel);
		
		try
		{	
			boolean createSuccess = true;
			
			// 이메일, 닉네임 중복 검사 
			if(memberDao.countEmail(email) > 0 || memberDao.countNick(nick) > 0 ||
			   hostDao.countEmail(email) > 0 || hostDao.countNick(nick) > 0)
			{
				createSuccess = false;
			}

			request.setAttribute("identify", identify);
			
			// 계정 생성 성공 여부에 따라 보여질 페이지가 다르므로
			if(createSuccess)
			{
				if(identify.equals("member"))
					memberDao.add(account);
				else if(identify.equals("host"))
					hostDao.add(account);
				
				mav.setViewName("../WEB-INF/views/common/createAccountNotice.jsp");
			}
			else
			{
				mav.setViewName("../WEB-INF/views/common/createAccountFailNotice.jsp");
			}
				
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
