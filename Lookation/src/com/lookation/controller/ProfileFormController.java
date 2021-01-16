// * ProfileFormController

package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ProfileFormController implements Controller
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
			mav.setViewName("../WEB-INF/views/common/profile_test.jsp");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
		
	}
	
}
