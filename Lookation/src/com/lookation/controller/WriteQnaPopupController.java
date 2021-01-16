// WriteQnaPopupController.java
// - 사용자 정의 컨트롤러 클래스


package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;




public class WriteQnaPopupController implements Controller
{
	/*
	 * private ILocationDetailDAO dao;
	 * 
	 * public void setDao(ILocationDetailDAO dao) { this.dao = dao; }
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		try
		{
			// locationDatail.jsp 로부터 locCode 수신
			// 회원코드 수신
			String locCode = request.getParameter("locCode");
			String memCode = request.getParameter("memCode");
			
			//LocationDetailDTO dto = new LocationDetailDTO();
			
			mav.addObject("locCode", locCode);
			mav.addObject("memCode", memCode);
			
			mav.setViewName("/WEB-INF/views/common/writeQnaPopup.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
