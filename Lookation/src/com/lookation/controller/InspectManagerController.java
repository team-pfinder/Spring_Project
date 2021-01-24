package com.lookation.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IInspectManagerDAO;

import com.lookation.dto.InspectLocationDTO;

public class InspectManagerController implements Controller
{
	private IInspectManagerDAO dao;
		
		public void setDao(IInspectManagerDAO dao)
		{
			this.dao = dao;
		}
	
		@Override
		public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			ModelAndView mav = new ModelAndView();
			
			// 세션을 통한 로그인 확인
			HttpSession session = request.getSession();

			String admin_id = (String)session.getAttribute("admin_id");

			// 로그인이 안된경우                                                                   
			if(admin_id == null)                                                      
			{                                                                            
				// 로그인 실패. 다시 로그인창으로                                                     
				mav.setViewName("redirect:adminloginform.action");
				return mav;
			}
			
			ArrayList<InspectLocationDTO> list = new ArrayList<InspectLocationDTO>();
			
			try
			{
				list = dao.list();
				
				mav.addObject("list", list);
				
				
				mav.setViewName("../WEB-INF/views/admin/inspectManager.jsp");
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			
			return mav;
		}

}
