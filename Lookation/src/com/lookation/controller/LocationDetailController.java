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
		
		String loc_code = request.getParameter("loc_code");
		//String loc_code = "L000003";
	
		//String selectDate = request.getParameter("selectDate");
		
		try
		{
			mav.addObject("basicInfo", dao.basicInfo(loc_code));
			mav.addObject("usingInfo", dao.usingInfo(loc_code));
			//mav.addObject("packageInfo", dao.packageInfo(locCode, selectDate));
			mav.addObject("facilityInfo", dao.facilityInfo(loc_code));
			mav.addObject("cautionInfo", dao.cautionInfo(loc_code));
			mav.addObject("bizInfo", dao.bizInfo(loc_code));
			mav.addObject("countQna", dao.countQna(loc_code));
			mav.addObject("countReview", dao.countReview(loc_code));
			mav.addObject("qna", dao.qna(loc_code));
			mav.addObject("review", dao.review(loc_code));
			mav.addObject("avgReviewRate", dao.avgReviewRate(loc_code));
			
			mav.setViewName("../WEB-INF/views/user/locationDetail.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
