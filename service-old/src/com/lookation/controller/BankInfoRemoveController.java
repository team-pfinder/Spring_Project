
package com.lookation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.lookation.dao.IAccountDAO;
import com.lookation.dao.IBankInfoDAO;
import com.lookation.dao.IMemberAccountDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써 //사용자 정의 컨트롤러 클래스를 구성한다.

public class BankInfoRemoveController implements Controller
{
	private IBankInfoDAO dao;

	public void setDao(IBankInfoDAO dao)
	{
		this.dao = dao;
	}
	
	@Autowired
	private SqlSession sqlSession;
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		// 공통 측면 뷰일 경우 사용자가 누구인지 알기 위해 
		// identify를 GET 받아야한다.
		String identify = request.getParameter("identify");
		
		
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute(identify + "Code"); 

		
		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";                                                         

		String sidParameter = request.getParameter("sid");
		
		String [] bankNumber = sidParameter.split(",");
		
		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
			// db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.

			// 이용자일 경우                                                                            
			if(identify.equals("member"))                                                   
			{                                      
				IMemberAccountDAO memberDao = sqlSession.getMapper(IMemberAccountDAO.class);
				mav.addObject("info", memberDao.getInfo(accountCode));

				// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.
				for (int i = 0; i < bankNumber.length; i++)
				{
					dao.bankInfoRemove(identify, bankNumber[i]);
				}
				mav.setViewName("redirect:/actions/bankinfomanage.action?identify=" + identify);
			}
			// 호스트일 경우
			else if(identify.equals("host"))                                                   
			{                                        
				// 호스트측면 추가로 해야할 작업은 이 밑에 쓴다.
				IMemberAccountDAO hostDao = sqlSession.getMapper(IMemberAccountDAO.class);	    
				mav.addObject("info", hostDao.getInfo(accountCode));
				
				// 호스트측면 추가로 해야할 작업은 이 밑에 쓴다.
				for (int i = 0; i < bankNumber.length; i++)
				{
					dao.bankInfoRemove(identify, bankNumber[i]);
				}
				mav.setViewName("redirect:/actions/bankinfomanage.action?identify=" + identify);
			}
			// 로그인이 되었음을 기록한다.
		    result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		mav.addObject("result", result);                                               

		// ********* 만약 로그인기능이 사용되는 뷰페이지의 경우 이 코드를 추가한다. ********* 
		// 로그인이 안되어 있다면 
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    mav.setViewName("redirect:loginform.action?identify=" + identify);
		}
		return mav;
	}
}




