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
import com.lookation.dao.IHostHistoryListDAO;
import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dao.IMemberHistoryListDAO;

@Controller
public class MileageHistoryList
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value="/actions/mileagehistory.action", method = RequestMethod.GET)
	public String historyList(Model model, HttpServletRequest request)
	{
		// 공통 측면 뷰일 경우 사용자가 누구인지 알기 위해 
		// identify를 GET 받아야한다.
		String identify = request.getParameter("identify");
		
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute(identify + "Code"); 
		
		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";       
		
		// return url 저장
		String returnUrl = "";
		
		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 이용자일 경우                                                                            
			if(identify.equals("member"))                                                   
			{
				// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
		        // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.
				IMemberAccountDAO memDao = sqlSession.getMapper(IMemberAccountDAO.class);	    
				model.addAttribute("info", memDao.getInfo(accountCode));

				// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.
				IMemberHistoryListDAO dao = sqlSession.getMapper(IMemberHistoryListDAO.class);
				
				model.addAttribute("payHistory", dao.payHistory(accountCode));
				model.addAttribute("loadHistory", dao.loadHistory(accountCode));
				model.addAttribute("exchangeHistory", dao.exchangeHistory(accountCode));
				model.addAttribute("refundHistory", dao.refundHistory(accountCode));

				returnUrl = "../WEB-INF/views/user/mileageHistory.jsp?identify=" + identify;
                                                             
			}
			// 호스트일 경우
			else if(identify.equals("host"))                                                   
			{   
				// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
		        // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.
				IHostAccountDAO hostDao = sqlSession.getMapper(IHostAccountDAO.class);	    
				model.addAttribute("info", hostDao.getInfo(accountCode));

				// 호스트측면 추가로 해야할 작업은 이 밑에 쓴다.
				IHostHistoryListDAO dao = sqlSession.getMapper(IHostHistoryListDAO.class);

				model.addAttribute("calHistory", dao.calHistory(accountCode));
				model.addAttribute("exchangeHistory", dao.exchangeHistory(accountCode));
				
				returnUrl =  "../WEB-INF/views/host/mileageHistory.jsp?identify=" + identify;
		                                                  
			}
			// 로그인이 되었음을 기록한다.
			result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);    

		return returnUrl;

	}
	
}
