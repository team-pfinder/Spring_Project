
package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IBankInfoDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써 //사용자 정의 컨트롤러 클래스를 구성한다.

public class BankInfoRemoveController implements Controller
{
	private IBankInfoDAO dao;

	public void setDao(IBankInfoDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();

		String identify = request.getParameter("identify");
		//String identify = "host";
		
		System.out.println(identify);
		
		String sidParameter = request.getParameter("sid");

		System.out.println(sidParameter);
		
		String [] bankNumber = sidParameter.split(",");
		
		for (int i = 0; i < bankNumber.length; i++)
		{
			System.out.println(bankNumber[i]);
		}
		
		if (identify.equals("member"))
		{
			for (int i = 0; i < bankNumber.length; i++)
			{
				dao.bankInfoRemove(identify, bankNumber[i]);
			}
			//
			mav.setViewName("redirect:/actions/bankinfomanage.action");

		
		} else if (identify.equals("host"))
		{
			for (int i = 0; i < bankNumber.length; i++)
			{
				dao.bankInfoRemove(identify, bankNumber[i]);
			}
			//
			mav.setViewName("redirect:/actions/bankinfomanage.action");
		}
		
		return mav;
	}
}




