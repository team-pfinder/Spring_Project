/*=========================
DeleteFormController.java
==========================*/


package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IHostAccountDAO;
import com.lookation.dao.IMemberAccountDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

public class DeleteFormController implements Controller
{

	@Autowired
	private SqlSession sqlSession;
	

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html; charset=UTF-8");
		try
		{
			// 공통 측면 뷰일 경우 사용자가 누구인지 알기 위해 
			// identify를 GET 받아야한다.
			String identify = request.getParameter("identify");
			
			// 세션을 통한 로그인 확인    
			HttpSession session = request.getSession();
			//session.setAttribute("hostCode", "H000002");
			session.setAttribute("memberCode", "M000001");
			
			String accountCode = (String)session.getAttribute(identify + "Code"); 

			// 로그인 확인을 기록하기 위함                  
			String result = "noSigned";                                                         

			// 회원 코드가 세션에 세팅되어 있다면                                                                                   
			if(accountCode != null)                                         
			{       
				// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
			    // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.

				// 이용자일 경우                                                                            
				if(identify.equals("member"))                                                   
				{
					IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);
					
					mav.addObject("info", dao.getInfo(accountCode));
				}
				// 호스트일 경우
				else if(identify.equals("host"))                                                   
				{                                
					IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);
					mav.addObject("info", dao.getInfo(accountCode));
				}
				// 로그인이 되었음을 기록한다.
			    result = "signed";                                                                                
			}

			// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
			mav.addObject("result", result);                                               

			// 로그인이 안되어 있다면 
			if(result.equals("noSigned"))
			{
			    // 로그인 창으로 이동한다.
				mav.setViewName("redirect:loginform.action?identify=" + identify);
			}
			// *********************************************************************************
			                                                                                                                                                                                                                                                                                                                            
			// 뒤에 identify를 GET 해준다.  
			mav.setViewName("../WEB-INF/views/common/deleteAccount.jsp?identify=" + identify);
			                                      
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}
}
