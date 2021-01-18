package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IBankInfoDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class BankInfoAddPopupController implements Controller
{
	private IBankInfoDAO dao;
	
	public void setDao(IBankInfoDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		String identify = request.getParameter("identify");
		String identifyCode = request.getParameter("identifyCode");
		//String identify = "host";
		//String identifyCode = "H000002";
		
		try
		{
			// 세션 설정
			mav.addObject("identify", identify);
			mav.addObject("identifyCode", identifyCode);
			System.out.println("popup");
			mav.setViewName("../WEB-INF/views/common/bankAccountAddPopup.jsp");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
