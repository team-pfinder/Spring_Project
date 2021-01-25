package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.ILocationDetailDAO;
import com.lookation.dao.IMemberAccountDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class LocationDetailController implements Controller
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

		try
		{
			String accountCode = (String)session.getAttribute("memberCode"); 
			
			// 로그인 확인을 기록하기 위함
			String result = "noSigned";
			
			// 회원 코드가 세션에 세팅되어 있다면                                                                                   
			if(accountCode != null)                                         
			{       
				// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
			        // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.

				IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	    
				mav.addObject("info", dao.getInfo(accountCode));

				// 로그인이 되었음을 기록한다.
			    result = "signed";                                                                                
			}
			
			
			// 로그인 여부 데이터를 뷰에 넘겨준다.
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
			
			
			
			mav.setViewName("../WEB-INF/views/user/locationDetail.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return mav;
	}
}
