package com.lookation.controller;

import java.util.ArrayList;

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
import com.lookation.dao.MemberAccountDAO;
import com.lookation.dto.BankInfoDTO;

public class BankInfoManageController implements Controller
{
	private IBankInfoDAO dao;
	
	public void setDao(IBankInfoDAO dao)
	{
		this.dao = dao;
	}
	
	private IAccountDAO memberDao;
	
	public void setMemberDao(IAccountDAO memberDao)
	{
		this.memberDao = memberDao;
	}	
	
	
	@Autowired
	private SqlSession sqlSession;
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		ArrayList<BankInfoDTO> bankInfoList = new ArrayList<BankInfoDTO>();

		// 공통 측면 뷰일 경우 사용자가 누구인지 알기 위해 
		// identify를 GET 받아야한다.
		String identify = request.getParameter("identify");
		
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                                                  
		session.setAttribute("memberCode", "M000001");
		session.setAttribute("hostCode", "H000003");
		String accountCode = (String)session.getAttribute(identify + "Code"); 
		
		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned"; 
		
		if(accountCode != null)
		{
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
	        // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.
			
			// 이용자일 경우                                                                            
			if(identify.equals("member"))                                                   
			{                                                                               
				// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.
				IMemberAccountDAO memberDao = sqlSession.getMapper(IMemberAccountDAO.class);
				mav.addObject("info", memberDao.getInfo(accountCode));
				
				try
				{
					System.out.println("member1");
					bankInfoList = dao.memberBankInfoLists(accountCode);

					mav.addObject("memberBankInfoList", bankInfoList );
					
					mav.setViewName("../WEB-INF/views/common/bankAccount.jsp?identify=" + identify);
					System.out.println("member2");
					
				} catch (Exception e)
				{
					System.out.println(e.toString());
				}
		                                                                                 
			}
			// 호스트일 경우
			else if(identify.equals("host"))                                                   
			{                                                                               
				// 호스트측면 추가로 해야할 작업은 이 밑에 쓴다.
				IHostAccountDAO hostDao = sqlSession.getMapper(IHostAccountDAO.class);	    
				mav.addObject("info", hostDao.getInfo(accountCode));
				
				try
				{
					// 분기 테스트용
					// 세션 설정----------------------------------------
					//mav.addObject("identify", identify);
					//mav.addObject("identifyCode", identifyCode);
					//--------------------------------------------------
					
					// 계좌 리스트 받아오기
					bankInfoList = dao.hostBankInfoLists(accountCode);
					
					// 계좌 리스트 지정
					mav.addObject("hostBankInfoList", bankInfoList );
					
					// 뷰 페이지 지정
					mav.setViewName("../WEB-INF/views/common/bankAccount.jsp?identify=" + identify);
					
				} catch (Exception e)
				{
					System.out.println(e.toString());	
				}

		                                                  
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
		// *********************************************************************************
		                                                                                                                                                                                                                                                                                                                            
		return mav;
	}

}
