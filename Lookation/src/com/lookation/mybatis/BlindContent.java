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

@Controller
public class BlindContent
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/actions/blindcontent.action", method = RequestMethod.GET)
	public String blind(HttpServletRequest request, Model model)
	{
		String identify = request.getParameter("identify");

		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();
		
		// 임시 로그인 세팅
		// session.setAttribute("hostCode", "H000001");
		// session.setAttribute("memberCode", "M000001");
		
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
				model.addAttribute("info", dao.getInfo(accountCode));

				// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.

		                                                                                 
			}
			// 호스트일 경우
			else if(identify.equals("host"))                                                   
			{                                                                               
				IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
				model.addAttribute("info", dao.getInfo(accountCode));

				// 호스트측면 추가로 해야할 작업은 이 밑에 쓴다.


		                                                  
			}
			// 로그인이 되었음을 기록한다.
		        result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);
		
		System.out.println(result);
		
		
		return "/WEB-INF/views/common/blindContent.jsp?identify=" + identify;
	}
}
