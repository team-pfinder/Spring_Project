// SignUpFormController.java
// - 사용자 정의 컨트롤러 클래스


package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class SignUpFormController implements Controller
{
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		// 로그인 확인 필요
		// 로그인 한 경우에는 들어오면 안됨
		String identify = request.getParameter("identify");
		
		try
		{
			mav.setViewName("../WEB-INF/views/common/signUp.jsp?identify=" + identify);
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
