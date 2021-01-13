/*=========================
DeleteFormController.java
==========================*/


package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class DeleteFormController implements Controller
{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		try
		{
			// jdbc 처리
			// dao.lists();
			//...
			
			// 불러올 view 세팅 
			// (jsp이름 or redirect:[액션명].action
			mav.setViewName("common/deleteAccount");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
