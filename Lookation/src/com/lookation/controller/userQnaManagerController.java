package com.lookation.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IuserQnaManager;
import com.lookation.dto.adminUserDTO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class userQnaManagerController implements Controller
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
		
		// 세션 처리 과정 추가 (관리자 로그인 확인 과정)
		
		ArrayList<adminUserDTO> basicList = new ArrayList<adminUserDTO>();
		
		try
		{
			basicList = dao.basicList();
			
			mav.addObject("basicList", basicList);
			
			mav.setViewName("/WEB-INF/views/admin/userQnaManager.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
