// DeleteUserController.java
// - 이용자 데이터 삭제 작업


package com.lookation.controller;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IDeleteMemberDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class DeleteMemberController implements Controller
{
	private IDeleteMemberDAO dao;
	
	public void setDao(IDeleteMemberDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		// [임시]
		// 데이터 수신(deleteAccount.jsp 페이지로부터... 로그인한 memCode 수신)
		String memCode = request.getParameter("memCode");
		//String nickName = request.getParameter("nickName");
		
		try
		{	
			
			if(dao.searchMemCode(memCode)!=0)	// 이미 탈퇴이용자 테이블에 있다면... 
			{
				response.setContentType("text/html; charset=UTF-8");
				 
				PrintWriter out = response.getWriter();
				 
				out.println("<script>alert('이미 탈퇴가 완료되었습니다.');</script>");
				 
				out.flush();
				
				mav.setViewName("common/login");
				return mav;
			}
			else if(dao.checkBook(memCode)!=0)	// 예약내역 남아있으면
			{	
				response.setContentType("text/html; charset=UTF-8");
				 
				PrintWriter out = response.getWriter();
				 
				out.println("<script>alert('예약내역이 남아있어 예약관리 페이지로 이동합니다.');</script>");
				 
				out.flush();
				
				// 마이페이지로 보냄
				mav.setViewName("user/mypageUser");
				
				return mav;
			}
			else						
			{
				dao.delExchangeInfo(memCode);
				dao.delLoadReg(memCode);
				dao.delBankInfo(memCode);
				dao.delProfile(memCode);
				
				dao.insertDelTbl(memCode);
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
