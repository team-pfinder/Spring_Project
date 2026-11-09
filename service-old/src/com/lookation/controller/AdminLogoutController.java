package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// ※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
// 사용자 정의 컨트롤러 클래스를 구성한다.

public class AdminLogoutController implements Controller
{
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();

		// 로그아웃 뷰 페이지(로그아웃 처리되었다는 안내 페이지) 없이
		// 바로 로그인 페이지를 다시 요청할 수 있도록 처리 
		
		HttpSession session = request.getSession();
		
		session.removeAttribute("admin_id");
		
		mav.setViewName("redirect:adminloginform.action");
		
		return mav;
	}
}
