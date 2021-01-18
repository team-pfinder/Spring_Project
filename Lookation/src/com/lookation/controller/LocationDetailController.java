package com.lookation.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.ILocationDetailDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class LocationDetailController implements Controller
{
	private ILocationDetailDAO dao;
	
	public void setDao(ILocationDetailDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		String locCode = request.getParameter("locCode");
		//String locCode = "L000003";
	
		String selectDate = request.getParameter("selectDate");
		
		try
		{
			mav.addObject("basicInfo", dao.basicInfo(locCode));
			mav.addObject("usingInfo", dao.usingInfo(locCode));
			mav.addObject("packageInfo", dao.packageInfo(locCode, selectDate));
			mav.addObject("facilityInfo", dao.facilityInfo(locCode));
			mav.addObject("cautionInfo", dao.cautionInfo(locCode));
			mav.addObject("bizInfo", dao.bizInfo(locCode));
			mav.addObject("countQna", dao.countQna(locCode));
			mav.addObject("countReview", dao.countReview(locCode));
			mav.addObject("qna", dao.qna(locCode));
			mav.addObject("review", dao.review(locCode));
			mav.addObject("avgReviewRate", dao.avgReviewRate(locCode));
			
			mav.setViewName("../WEB-INF/views/user/locationDetail.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
