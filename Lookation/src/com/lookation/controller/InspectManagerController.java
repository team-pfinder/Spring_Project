package com.lookation.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IInspectManagerDAO;

import com.lookation.dto.InspectLocationDTO;

//
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
