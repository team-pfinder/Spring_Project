
// # AdminLoginController.java

package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IAdminDAO;
import com.lookation.util.PasswordHasher;

public class AdminLoginController implements Controller
{
	private IAdminDAO dao;

	public void setDao(IAdminDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();

		// 데이터 수신(LoginForm.jsp) 로부터 id, pw,
		String admin_id = request.getParameter("admin_id");
		String admin_pw = request.getParameter("admin_pw");
		
		HttpSession session = request.getSession();      

		// 로그인 여부로 닉네임 반환하게함.
		String admin_nickname = null;

		try
		{
			String storedPassword = dao.findPassword(admin_id);

			if(storedPassword != null && PasswordHasher.matches(admin_pw, storedPassword))
			{
				admin_nickname = dao.findNickname(admin_id);

				if(admin_nickname != null && !PasswordHasher.isHashed(storedPassword))
				{
					dao.updatePassword(admin_id, PasswordHasher.hash(admin_pw));
				}
			}
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		/*
		mav.setViewName("../WEB-INF/views/admin/loginAdmin.jsp"); return mav;
		 */

	
		// 로그인 성공 여부에 따른 분기(구분) 
		if(admin_nickname==null) 
		{ // 로그인 실패 → 로그인 폼을 다시 요청할 수 있도록안내 
			mav.setViewName("redirect:adminloginform.action");
			//mav.setViewName("../WEB-INF/views/admin/loginAdmin.jsp"); 
		 } 
		else 
		{ // 로그인 성공
			session.setAttribute("admin_id", admin_id);
			mav.setViewName("redirect:adminmain.action");
		}
		return mav;
	}
}
