
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
import com.lookation.dao.IHostAccountDAO;
import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dto.BankInfoDTO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써 //사용자 정의 컨트롤러 클래스를 구성한다.

public class BankInfoAddController implements Controller
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

		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)              
		{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
	        // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.
			
			// 입력받은 계좌정보를 DTO에 저장
			BankInfoDTO dto = new BankInfoDTO(); 
			dto.setBank(request.getParameter("bank"));
			dto.setIdentifyCode(accountCode);			
			dto.setBankNumber(request.getParameter("bankNum"));
			dto.setBankHolder(request.getParameter("bankHol"));
			
			// 이용자일 경우                                                                            
			if(identify.equals("member"))                                                   
			{                                                                               
				try
				{
					if(dao.memberBankInfoCount(accountCode) >= 3)
					{
						
					}
					else
					{
						dao.memberBankInfoAdd(dto);
						mav.setViewName("../WEB-INF/views/common/timeout.jsp");
					}
				} catch (Exception e)
				{
					System.out.println(e.toString());
				}

		                                                                                 
			}
			// 호스트일 경우
			else if(identify.equals("host"))                                                   
			{                                                                               
				try
				{
					if(dao.hostBankInfoCount(accountCode) >= 3)
					{
						
					}
					else 
					{
						dao.hostBankInfoAdd(dto);
						mav.setViewName("../WEB-INF/views/common/timeout.jsp");
					}
				} catch (Exception e)
				{
					System.out.println(e.toString());
				}
		                                                  
			}
			// 로그인이 되었음을 기록한다.
        	result = "signed";                                                                                
		}

		return mav;
	}
}
