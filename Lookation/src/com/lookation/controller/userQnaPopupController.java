// SampleController.java
// - 사용자 정의 컨트롤러 클래스


package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IuserQnaManager;
import com.lookation.dto.adminUserDTO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class userQnaPopupController implements Controller
{
	private IuserQnaManager dao;
	
	public void setDao(IuserQnaManager dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		try
		{
			// userQnaManager.jsp 로부터 qnaCode 수신
			String qnaCode = request.getParameter("qnaCode");
			
			adminUserDTO dto = new adminUserDTO();
			dto = dao.detailList(qnaCode);
			
			mav.addObject("dto", dto);
			
			mav.setViewName("/WEB-INF/views/admin/userQnaPopup.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
