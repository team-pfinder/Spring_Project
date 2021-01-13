// DeleteHostController.java
// - 호스트 데이터 삭제 작업


package com.lookation.controller;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IDeleteHostDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class DeleteHostController implements Controller
{
	private IDeleteHostDAO dao;
	
	public void setDao(IDeleteHostDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		// [임시]
		// 데이터 수신(deleteAccount.jsp 페이지로부터... 로그인한 hostCode 수신)
		String hostCode = request.getParameter("hostCode");
		//String nickName = request.getParameter("nickName");
		
		try
		{	
			
			if(dao.searchHostCode(hostCode)!=0)	// 이미 탈퇴호스트 테이블에 있다면...
			{
				response.setContentType("text/html; charset=UTF-8");
				 
				PrintWriter out = response.getWriter();
				 
				out.println("<script>alert('이미 탈퇴가 완료되었습니다.');</script>");
				 
				out.flush();
				
				mav.setViewName("common/login");
				return mav;
			}
			else if(dao.checkBook(hostCode)!=0)	// 예약내역 남아있으면
			{	
				response.setContentType("text/html; charset=UTF-8");
				 
				PrintWriter out = response.getWriter();
				 
				out.println("<script>alert('예약내역이 남아있어 예약관리 페이지로 이동합니다.');</script>");
				 
				out.flush();
				
				// 마이페이지로 보냄
				mav.setViewName("host/mypageHost");
				
				return mav;
			}	
			else						
			{
				dao.delContact(hostCode);
				dao.delBizInfo(hostCode);
				dao.insertLocRemove(hostCode);
				dao.delExchangeInfo(hostCode);
				dao.delBankInfo(hostCode);
				dao.delProfile(hostCode);
				dao.insertDelHost(hostCode);
			}
			
			response.setContentType("text/html; charset=UTF-8");
			 
			PrintWriter out = response.getWriter();
			 
			out.println("<script>alert('탈퇴가 완료되었습니다.');</script>");
			 
			out.flush();
			
			mav.setViewName("common/login");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return mav;
	}
}
