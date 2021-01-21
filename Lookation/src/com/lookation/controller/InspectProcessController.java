package com.lookation.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IInspectManagerDAO;
import com.lookation.dto.InspectLocationDTO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class InspectProcessController implements Controller
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
		
		try
		{
			// inspectManager.jsp --> inspect_reg_code 수신
			String inspect_reg_code = request.getParameter("inspect_reg_code");
			// 즉 ,  여기서 inspect_reg_code 값이 null 
			
			InspectLocationDTO dto1 = new InspectLocationDTO();
			InspectLocationDTO dto2 = new InspectLocationDTO();
			InspectLocationDTO dto3 = new InspectLocationDTO();
			InspectLocationDTO dto4 = new InspectLocationDTO();
			InspectLocationDTO dto5 = new InspectLocationDTO();
			

			//inspect_reg_code = "IR000001"; //이고
			// 이거 getParameter로 받아오려면
			/*
			 insepetManager.jsp 에서 get Method로 inspectprocess.action 요청해서
			 url 끝이 .action?inspect_reg_code='IR000001' 일때만 
			 request.getParameter 이용해서 파라미터 값 받아올 수 있는데,
			 우린 아직 페이지 연결이 되지 않아서 임시로 입력
			 */
			
			
			dto1 = dao.prlist(inspect_reg_code);
			dto2 = dao.pakageInfo(inspect_reg_code);
			dto3 = dao.facilityInfo(inspect_reg_code);
			dto4 = dao.cautionInfo(inspect_reg_code);
			dto5 = dao.urlInfo(inspect_reg_code);

			mav.addObject("dto1", dto1);	// 여기 다른 객체인데 
			mav.addObject("dto2", dto2);	// 이름은 똑같음
			mav.addObject("dto3", dto3);
			mav.addObject("dto4", dto4);
			mav.addObject("dto5", dto5);
			
			
			mav.setViewName("../WEB-INF/views/admin/inspectProcess.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}

