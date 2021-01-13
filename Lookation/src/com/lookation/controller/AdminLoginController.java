
 // # AdminLoginController.java

package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IAdminDAO;

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
		
		// 로그인 여부로 닉네임 반환하게함.
		String nickname = null;

		try
		{
			// 관리자 로그인
			nickname = dao.loginAdmin(admin_id, admin_pw);

		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		// 로그인 성공 여부에 따른 분기(구분)
		if(nickname==null)
		{
			// 로그인 실패 → 로그인 폼을 다시 요청할 수 있도록 안내
			mav.setViewName("redirect:adminloginform.action");
		}
		else
		{
			// 로그인 성공
				mav.setViewName("redirect:adminloginform.action");
		}
			
			

		return mav;
	}
}
