// AccountAjaxController.java
// - 사용자 정의 컨트롤러 클래스


package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IAccountDAO;

public class AccountAjaxController implements Controller
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
		
		String email = request.getParameter("email");
		String nick = request.getParameter("nick"); 
		
		String str = "";
		try
		{
			if(memberDao.countNick(nick) > 0 || hostDao.countNick(nick) > 0)
			{
				str = "닉네임이 이미 사용중입니다. 다른 닉네임을 입력해주세요.";
			}	
			
			// 이메일, 닉네임 중복 검사 
			if(memberDao.countEmail(email) > 0 || hostDao.countEmail(email) > 0 )
			{
				str = "이메일이 이미 사용중입니다. 다른 이메일을 입력해주세요.";
			}	
			
			mav.addObject("result", str);
			mav.setViewName("");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
