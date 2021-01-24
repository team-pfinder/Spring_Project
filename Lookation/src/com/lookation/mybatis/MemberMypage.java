package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHostAccountDAO;
import com.lookation.dao.IMemberAccountDAO;

// 이용자 마이페이지 메뉴

@Controller
public class MemberMypage
{
    @Autowired
    private SqlSession sqlSession;
    
	// 메인 이동
	@RequestMapping(value ="/actions/mypage.action", method = RequestMethod.GET)
	public String mypage(Model model, HttpServletRequest request)
	{
		// 공통 측면 뷰일 경우 사용자가 누구인지 알기 위해 
		// identify를 GET 받아야한다.
		String identify = request.getParameter("identify");

		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute(identify + "Code"); 

		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";                                                         

		// 
		String returnUrl = "";
		
		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
			// db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.

			// 이용자일 경우                                                                            
			if(identify.equals("member"))                                                   
			{                                                                               
				IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	    
				model.addAttribute("info", dao.getInfo(accountCode));

				// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.
				returnUrl = "../WEB-INF/views/user/mypageMember.jsp";                                                                                 
			}
			// 호스트일 경우
			else if(identify.equals("host"))                                                   
			{                                                                               
				IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
				model.addAttribute("info", dao.getInfo(accountCode));

				// 호스트측면 추가로 해야할 작업은 이 밑에 쓴다.
				returnUrl = "../WEB-INF/views/host/mypageHost.jsp";	                                                  
			}
			// 로그인이 되었음을 기록한다.
		        result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               


		// ********* 만약 로그인기능이 사용되는 뷰페이지의 경우 이 코드를 추가한다. ********* 
		// 로그인이 안되어 있다면 
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
			returnUrl = "redirect:loginform.action?identify=" + identify;
		}
		// *********************************************************************************
		                                                                                                                                                                                                                                                                                                                            
		// 뒤에 identify를 GET 해준다.                                                                                              
		return returnUrl;                    
	
	}
	

	// 마일리지 내역
	@RequestMapping(value ="/actions/membermileagehistory.action", method = RequestMethod.GET)
	public String mypageHistoryHost(Model model)
	{
		return "redirect:/actions/membermileagehistorylist.action";
	}

	
}
