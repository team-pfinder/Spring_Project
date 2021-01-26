// DeleteUserController.java
// - 이용자 데이터 삭제 작업


package com.lookation.controller;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IDeleteMemberDAO;
import com.lookation.dao.IMemberAccountDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class DeleteMemberController implements Controller
{
	
	@Autowired
	private SqlSession sqlSession;
	
	private IDeleteMemberDAO dao;
	
	public void setDao(IDeleteMemberDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try
		{	
			// 세션을 통한 로그인 확인                                                                    
			HttpSession session = request.getSession();                    
			String accountCode = (String)session.getAttribute("memberCode"); 

			// 로그인 확인을 기록하기 위함                  
			String result = "noSigned";                                                         

			// 회원 코드가 세션에 세팅되어 있다면                                                                                   
			if(accountCode != null)                                         
			{       
				// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
			    // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.
				IMemberAccountDAO sessionInfo = sqlSession.getMapper(IMemberAccountDAO.class);	    
				mav.addObject("info", sessionInfo.getInfo(accountCode));
				
				// 로그인이 되었음을 기록한다.
		        result = "signed";
				
				// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.
				if(dao.checkMemCode(accountCode)==0)	// 회원프로필 존재하지 않는다면... 
				{
					out.println("<script>alert('가입정보가 유효하지 않거나 이미 탈퇴가 완료된 회원입니다.');</script>");
					out.flush();
					
					mav.setViewName("loginform.action?identify=member");
					return mav;
				}
				else if(dao.checkMileage(accountCode)!=0)	// 마일리지 남아있으면
				{
					out.println("<script>alert('마일리지가 남아있어 마일리지 환전 페이지로 이동합니다.');</script>");
					out.flush();
					
					// 마이페이지로 보냄
					mav.setViewName("loadandexchange.action?identify=member");
					
					return mav;
				}
				else if(dao.checkBook(accountCode)!=0)	// 예약내역 남아있으면
				{	
					out.println("<script>alert('예약내역이 남아있어 예약관리 페이지로 이동합니다.');</script>");
					out.flush();
					
					// 마이페이지로 보냄
					mav.setViewName("mypage.action?identify=member");
					
					return mav;
				}
				else						
				{	
					dao.delExchangeInfo(accountCode);
					dao.delLoadReg(accountCode);
					dao.delBankInfo(accountCode);
					dao.delProfile(accountCode);
					dao.insertDelTbl(accountCode);
				}
				
				out.println("<script>alert('탈퇴가 완료되었습니다.');</script>");
				out.flush();
			}

			// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
			mav.addObject("result", result);                                               

			// 로그인이 안되어 있다면 
			if(result.equals("noSigned"))
			{
			    // 로그인 창으로 이동한다.
				mav.setViewName("redirect:loginform.action?identify=member");
			}
			                                                                                                                                                                                                                                                                                                                                                                                                                             
			mav.setViewName("redirect:loginform.action?identify=member");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return mav;
	}
}
