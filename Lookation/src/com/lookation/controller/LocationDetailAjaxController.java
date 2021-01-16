// LocationDetailAjaxController.java
// - 상세공간페이지에서 날짜 선택시 해당하는 날짜에 있는 패키지
//   가져옴


package com.lookation.controller;



import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.ILocationDetailDAO;
import com.lookation.dto.LocationDetailDTO;

public class LocationDetailAjaxController implements Controller
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
		
		// 데이터 수신(Location.jsp 페이지로부터... 로그인한 hostCode 수신)
		String selectDate = request.getParameter("selectDate");
		String locCode = request.getParameter("locCode");
		
		//System.out.println(selectDate);
		
		ArrayList<LocationDetailDTO> packageInfo = new ArrayList<LocationDetailDTO>();
		
		try 
		{
			packageInfo = dao.packageInfo(locCode, selectDate);
			
			/* 파라미터 제대로 받으면 다시 수정 */
			//mav.setViewName("redirect:locationdetail.action?locCode"+locCode);
			mav.addObject("packageInfo", packageInfo);
			
			
			// 테스트
			System.out.println(packageInfo);
			mav.setViewName("../WEB-INF/views/user/locationDetail.jsp");
			
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return mav;
	}
}
