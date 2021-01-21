package com.lookation.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IInspectManagerDAO;
import com.lookation.dto.InspectLocationDTO;

public class InspectInsertController implements Controller
{
	private IInspectManagerDAO dao;
	
	public void setDao(IInspectManagerDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws SQLException
	{
		ModelAndView mav = new ModelAndView();
		
		String inspect_reg_code = request.getParameter("inspect_reg_code");
		String inspect_type_code = request.getParameter("inspect_type_code");
		String inspect_proc_reason =  request.getParameter("inspect_proc_reason");
		
		try
		{
			InspectLocationDTO dto = new InspectLocationDTO();
			
			dto.setInspect_reg_code(inspect_reg_code);
			dto.setInspect_type_code(inspect_type_code);
			dto.setInspect_proc_reason(inspect_proc_reason);
			
			
			dao.Inspect(dto); 
			
			mav.setViewName("redirect:/actions/inspectmanager.action");
			//mav.setViewName("../WEB-INF/views/admin/inspectmanager.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		return mav;
	}

}
