package com.lookation.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.lookation.dao.IBankAccountDAO;
import com.lookation.dto.HostBankAccountInfoDTO;
import com.lookation.dto.MemberBankAccountInfoDTO;

public class BankAccountInfoController implements Controller
{
	private IBankAccountDAO dao;
	
	public void setDao(IBankAccountDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		ArrayList<MemberBankAccountInfoDTO> memberBankAccountInfoList = new ArrayList<MemberBankAccountInfoDTO>();
		ArrayList<HostBankAccountInfoDTO> hostBankAccountInfoList = new ArrayList<HostBankAccountInfoDTO>();
		/*
		try
		{
			System.out.println("member1");
			memberBankAccountInfoList = dao.memberBankInfoLists();
			  
			mav.addObject("memberBankAccountInfoList", memberBankAccountInfoList );
			
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
			hostBankAccountInfoList = dao.hostBankInfoLists();
			  
			mav.addObject("hostBankAccountInfoList", hostBankAccountInfoList );
			
			mav.setViewName("common/bankAccount");
			System.out.println("host2");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());	
		}
		
		/*
		String identify = request.getParameter(identify);
		
		if(identify.equals("member"))
		{
			ArrayList<MemberBankAccountInfoDTO> memberBankAccountInfoList = new ArrayList<MemberBankAccountInfoDTO>();
			try
			{
				System.out.println("member1");
				memberBankAccountInfoList = dao.memberBankInfoLists();
				  
				mav.addObject("memberBankAccountInfoList", memberBankAccountInfoList );
				
				mav.setViewName("common/bankAccount");
				System.out.println("member2");
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		else if(identify.equals("host"))
		{
			ArrayList<HostBankAccountInfoDTO> hostBankAccountInfoList = new ArrayList<HostBankAccountInfoDTO>();
			try
			{
				System.out.println("host1");
				hostBankAccountInfoList = dao.hostBankInfoLists();
				  
				mav.addObject("hostBankAccountInfoList", hostBankAccountInfoList );
				
				mav.setViewName("common/bankAccount");
				System.out.println("host2");
				
			} catch (Exception e)
			{
				System.out.println(e.toString());	
			}
		}
		*/

		return mav;
	}
}
