// LocationDetailAjaxController.java
// - 상세공간페이지에서 날짜 선택시 해당하는 날짜에 있는 패키지
//   가져옴

package com.lookation.controller;

import java.io.PrintWriter;
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

		// 데이터 수신(locationDetail.jsp 페이지로부터... 로그인한 memberCode 수신)
		String selectDate = request.getParameter("selectDate");
		String locCode = request.getParameter("locCode");

		System.out.println(selectDate);
		System.out.println(locCode);
		String parseJson = "";

		ArrayList<LocationDetailDTO> packageInfo = new ArrayList<LocationDetailDTO>();

		try
		{
			packageInfo = dao.packageInfo(locCode, selectDate);
			
			String tmp = "";
			
			// AJAX용 JSON 만들기
			for (int i = 0; i < packageInfo.size(); i++)
			{
				tmp +="{\"packageName\":"+ "\"" + packageInfo.get(i).getPackageName() + "\""
				+",\"packCode\":"+ "\"" + packageInfo.get(i).getApply_package_code() + "\""
				+",\"packStart\":"+ "\"" + packageInfo.get(i).getPackStart() + "\""
				+",\"packPrice\":"+ "\"" + packageInfo.get(i).getPackPrice() + "\""
				+",\"applyDate\":"+ "\"" + packageInfo.get(i).getApplyDate() + "\""
				+",\"packEnd\":" + "\"" + packageInfo.get(i).getPackEnd()+ "\"";
				
				if(i==packageInfo.size()-1){ tmp += "}"; }else{ tmp += "},"; }
			}
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			parseJson = "[" + tmp + "]";
			out.print(parseJson.toString()); 

			mav.addObject("data", parseJson);

			/* System.out.println("tmp 출력 =======" + parseJson); */
			mav.setViewName("../WEB-INF/views/ajax/LocationDetailAjax.jsp");

		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return mav;
	}
	
}
