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
		
		//String identify = request.getParameter(identify);
		//String identifyCode = request.getParameter(identifyCode);
		String identify = "member";
		String identifyCode = "M000002";
		
		if(identify.equals("member"))
		{
			try
			{
				// 분기 테스트용
				// 세션 설정----------------------------------------
				mav.addObject("identify", identify);
				mav.addObject("identifyCode", identifyCode);
				//--------------------------------------------------
				
				System.out.println("member1");
				bankInfoList = dao.memberBankInfoLists();

				mav.addObject("memberBankInfoList", bankInfoList );
				
				mav.setViewName("../WEB-INF/views/common/bankAccount.jsp");
				System.out.println("member2");
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}	
		else if(identify.equals("host"))
		{	
			try
			{
				// 분기 테스트용
				// 세션 설정----------------------------------------
				mav.addObject("identify", identify);
				mav.addObject("identifyCode", identifyCode);
				//--------------------------------------------------
				
				// 계좌 리스트 받아오기
				bankInfoList = dao.hostBankInfoLists();
				
				// 계좌 리스트 지정
				mav.addObject("hostBankInfoList", bankInfoList );
				
				// 뷰 페이지 지정
				mav.setViewName("../WEB-INF/views/common/bankAccount.jsp");
				
			} catch (Exception e)
			{
				System.out.println(e.toString());	
			}
	
		}
		return mav;
		
	}
}
