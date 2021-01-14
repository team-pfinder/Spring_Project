package com.lookation.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.lookation.dao.IBankInfoDAO;
import com.lookation.dto.BankInfoDTO;

public class BankInfoManageController implements Controller
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
		
		ArrayList<BankInfoDTO> bankInfoList = new ArrayList<BankInfoDTO>();
		/*
		String identify = request.getParameter(identify);
		
		if(identify.equals("member"))
		{
			
		}
		*/
		
		/*
		try
		{
			mav.addObject("identify", "member");	// 분기 테스트용
			System.out.println("member1");
			bankInfoList = dao.memberBankInfoLists();
			  
			mav.addObject("memberBankInfoList", bankInfoList );
			
			mav.setViewName("common/bankAccount");
			System.out.println("member2");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		*/
		
		try
		{
			mav.addObject("identify", "host");	// 분기 테스트용
			System.out.println("host1");
			bankInfoList = dao.hostBankInfoLists();
			  
			mav.addObject("hostBankInfoList", bankInfoList );
			
			mav.setViewName("../WEB-INF/views/common/bankAccount.jsp");
			System.out.println("host2");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());	
		}

		return mav;
	}
}
