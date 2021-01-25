package com.lookation.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IHostAccountDAO;
import com.lookation.dao.ILocationDetailDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class LocationDetailHostController implements Controller
{
	@Autowired
	private SqlSession sqlSession;
	
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
		
		HttpSession session = request.getSession();
		String accountCode = (String)session.getAttribute("hostCode"); 
		String result = "noSigned";  
		
		try
		{
			// 회원 코드가 세션에 세팅되어 있다면
			if (accountCode != null)
			{
				IHostAccountDAO hoDao = sqlSession.getMapper(IHostAccountDAO.class);	    
				mav.addObject("info", hoDao.getInfo(accountCode));
				
				mav.addObject("result", result);
				mav.addObject("loc_code", loc_code);
				
				mav.addObject("basicInfo", dao.basicInfo(loc_code));
				mav.addObject("usingInfo", dao.usingInfo(loc_code));
				mav.addObject("facilityInfo", dao.facilityInfo(loc_code));
				mav.addObject("cautionInfo", dao.cautionInfo(loc_code));
				mav.addObject("bizInfo", dao.bizInfo(loc_code));
				mav.addObject("countQna", dao.countQna(loc_code));
				mav.addObject("countReview", dao.countReview(loc_code));
				mav.addObject("qna", dao.qna(loc_code));
				mav.addObject("review", dao.review(loc_code));
				mav.addObject("avgReviewRate", dao.avgReviewRate(loc_code));

				mav.addObject("detailPhotoUrl", dao.detailPhoto(loc_code));
				
				// 로그인이 되었음을 기록한다.
				result = "signed";
			}
			
			// 로그인 여부 데이터를 뷰에 넘겨준다.
			mav.addObject("result", result);
			
			if(result.equals("noSigned"))
			{
			    // 로그인 창으로 이동한다.
			    mav.setViewName("redirect:loginform.action?identify=host");
			}
			
			mav.setViewName("../WEB-INF/views/host/locationDetailHost.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
