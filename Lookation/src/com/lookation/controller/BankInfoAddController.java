
package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IBankInfoDAO;
import com.lookation.dto.BankInfoDTO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써 //사용자 정의 컨트롤러 클래스를 구성한다.

public class BankInfoAddController implements Controller
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

		
		
		//String identify = request.getParameter("identify");
		String identify = "host";
		System.out.println(identify);
		 
		System.out.println("dsd"); 
		BankInfoDTO dto = new BankInfoDTO(); 
		dto.setBank(request.getParameter("bank"));
		dto.setBankNumber(request.getParameter("bankNum"));
		dto.setBankHolder(request.getParameter("bankHol"));
				
		if (identify.equals("member"))
		{
			try
			{
				//if(dao.memberBankInfoCount(identifyCode) >= 3)
				if(dao.memberBankInfoCount("M000002") >= 3)
				{
					System.out.println("이용자 계좌 이미 3개");
				}
				else
				{
					dao.memberBankInfoAdd(dto);
					mav.setViewName("../WEB-INF/views/common/timeout.jsp");
				}

			} catch (Exception e)
			{
				System.out.println(e.toString());
			}

		} else if (identify.equals("host"))
		{
			try
			{
				System.out.println("계좌 갯수 체크전");
				//if(dao.memberBankInfoCount(identifyCode) >= 3)
				if(dao.hostBankInfoCount("H000002") >= 3)
				{
					System.out.println("호스트 계좌 이미 3개");
				}
				else 
				{
					System.out.println("인서트 하기 전");
					dao.hostBankInfoAdd(dto);
					mav.setViewName("../WEB-INF/views/common/timeout.jsp");
					System.out.println("인서트 후");
				}
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}

		}
		
		return mav;
	}
}
