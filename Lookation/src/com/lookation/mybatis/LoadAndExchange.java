
package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IAccountDAO;
import com.lookation.dao.IHostAccountDAO;
import com.lookation.dao.ILoadAndExchangeDAO;
import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dto.LoadAndExchangeDTO;


@Controller
public class LoadAndExchange
{
    @Autowired
    private SqlSession sqlSession;
    
    // 이용자, 호스트 잔액 및 등록 계좌 조회
    @RequestMapping(value="/actions/loadandexchange.action", method = RequestMethod.GET)
    public String bankInfoAndBalance(ModelMap model, HttpServletRequest request)
    {
    	ILoadAndExchangeDAO dao = sqlSession.getMapper(ILoadAndExchangeDAO.class);
    	LoadAndExchangeDTO dto = new LoadAndExchangeDTO();
    	String identify = request.getParameter("identify");
    	String returnUrl = ""; 
    	
    	
    	//-------------------------------------------------------------------------------
    	// 세션을 통한 로그인 확인                                                                    
    	HttpSession session = request.getSession();                                                                  
    	String accountCode = (String)session.getAttribute(identify + "Code"); 
    	
    	dto.setIdentifyCode(accountCode);

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
				IMemberAccountDAO memberDao = sqlSession.getMapper(IMemberAccountDAO.class);
    			model.addAttribute("info", memberDao.getInfo(accountCode));
    			model.addAttribute("identify", accountCode);
	        	try
	    		{
	        		model.addAttribute("bankInfoList", dao.memberBankInfoList(dto));
	        		System.out.println("bankInfoList : memberBankInfoList");
	        		model.addAttribute("balance", dao.memberGetBalance(dto));
	        		System.out.println("balance : memberGetBalance");
	    		} catch (Exception e)
	    		{
	    			System.out.println(e.toString());
	    		}

	        	returnUrl = "../WEB-INF/views/user/loadAndExchangeUser.jsp?identify=" + identify;
    	                                                                                 
    		}
    		// 호스트일 경우
    		else if(identify.equals("host"))                                                   
    		{                                                                               
    			IHostAccountDAO hostDao = sqlSession.getMapper(IHostAccountDAO.class);	    
    			model.addAttribute("info", hostDao.getInfo(accountCode));
    			model.addAttribute("identify", accountCode);

    			System.out.println(hostDao.getInfo(accountCode).getNick());

            	try
        		{
            		model.addAttribute("bankInfoList", dao.hostBankInfoList(dto));
            		System.out.println("bankInfoList : hostBankInfoList");
            		model.addAttribute("balance", dao.hostGetBalance(dto));
            		System.out.println("balance : hostGetBalance");
        		} catch (Exception e)
        		{
        			System.out.println(e.toString());
        		}
            	returnUrl = "../WEB-INF/views/host/exchangeHost.jsp?identify" + identify;
    	                                                  
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

    	return returnUrl;
    }
    
    // 이용자 충전 신청
    @RequestMapping(value="/actions/memberload.action", method = RequestMethod.POST)
    public String loadReg(ModelMap model, HttpServletRequest request)
    {
    	ILoadAndExchangeDAO dao = sqlSession.getMapper(ILoadAndExchangeDAO.class);
    	LoadAndExchangeDTO dto = new LoadAndExchangeDTO();
    
    	//-----------------------------------------------------------------------------

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

    		// 이용자일 경우                                                                            
			IMemberAccountDAO memberDao = sqlSession.getMapper(IMemberAccountDAO.class);	    
			model.addAttribute("info", memberDao.getInfo(accountCode));

			// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.

	    	dto.setIdentifyCode(accountCode);
	    	dto.setBankNumber(request.getParameter("bankAccount"));
	    	dto.setAmount(Integer.parseInt(request.getParameter("charge")));
	    	
	    	// 테스트
	    	System.out.println(dto.getBankNumber());
	    	System.out.println(dto.getAmount());    	
	    	
	    	// 충전신청 정보 DB 등록
	    	try
			{
	    		dao.memberLoadRegister(dto);	
	    		System.out.println("충전신청 성공");
	        	
	    		// 등록된 충전신청 정보 가져오기
	        	try
	    		{
	    			dto = dao.memberLoadRegisterNotice(accountCode);
	    			//--------------------------------------------
	    			System.out.println(dto.getAmount());
	    			System.out.println(dto.getBank());
	    			System.out.println(dto.getBankNumber());
	    			System.out.println(dto.getRegdate());
	    			//--------------------------------------------
	    			model.addAttribute("loadInfo", dto);

	    		} catch (Exception e2)
	    		{
	    			System.out.println(e2.toString());
	    		}
	        	
			} catch (Exception e)
			{
				System.out.println(e.toString());
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
    	    return "redirect:loginform.action?identify=member";
    	}
    	// *********************************************************************************

    	return "../WEB-INF/views/user/loadRegisterNotice.jsp";
    }

    
    // 이용자, 호스트 환전 신청
    @RequestMapping(value="/actions/exchange.action", method = RequestMethod.POST)
    public String exchangeReg(ModelMap model, HttpServletRequest request)
    {
    	ILoadAndExchangeDAO dao = sqlSession.getMapper(ILoadAndExchangeDAO.class);
    	LoadAndExchangeDTO dto = new LoadAndExchangeDTO();
    	
    	//----------------------------------------------------------------
    	// 공통 측면 뷰일 경우 사용자가 누구인지 알기 위해 
    	// identify를 GET 받아야한다.
    	String identify = request.getParameter("identify");
    	System.out.println(identify);
    	
    	// 세션을 통한 로그인 확인                                                                    
    	HttpSession session = request.getSession();                                                                  
    	String accountCode = (String)session.getAttribute(identify + "Code"); 

    	System.out.println("identify : " + identify + "identifyCode : " + accountCode);
    	
    	
    	// 로그인 확인을 기록하기 위함                  
    	String result = "noSigned";                                                         

    	// 회원 코드가 세션에 세팅되어 있다면                                                                                   
    	if(accountCode != null)                                         
    	{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
			// db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.

			// 입력받은 데이터들 대입
    		dto.setIdentifyCode(accountCode);
        	dto.setBankNumber(request.getParameter("bankAccount"));
        	dto.setAmount(Integer.parseInt(request.getParameter("exchange")));
        	
        	// 테스트
        	System.out.println(dto.getIdentifyCode());
        	System.out.println(dto.getBankNumber());
        	System.out.println(dto.getAmount()); 
    		
    		// 이용자일 경우                                                                            
    		if(identify.equals("member"))                                                   
    		{                                                                               
        		try
    			{
        			// 환전 신청
        			dao.memberExchangeRegister(dto);
            		System.out.println("환전신청성공");
            		try
					{
            			System.out.println("신청정보 가져오는중");
                		// 등록된 충전신청 정보 가져오기
            			dto = dao.memberExchangeNotice(accountCode);
            			// 가져온 충전신청 정보 확인
            			//-----------------------------------------------
            			System.out.println(dto.getAmount());
            			System.out.println(dto.getBank());
            			System.out.println(dto.getBankNumber());
            			System.out.println(dto.getBankHolder());
            			System.out.println(dto.getRegdate());
            			//-----------------------------------------------
            			model.addAttribute("exchangeInfo", dto);

					} catch (Exception e)
					{
						System.out.println(e.toString());
					}
    			} catch (Exception e2)
    			{
    				System.out.println(e2.toString());
    			}
    		}
    		
    		// 호스트일 경우
    		else if(identify.equals("host"))                                                   
    		{                                                                               
        		try
        		{
        			// 환전 신청하기
        			dao.hostExchangeRegister(dto);
            		System.out.println("환전신청성공");
        			try
					{
                		System.out.println("신청정보 가져오는중");
        				// 환전 신청 정보 가져오기
            			dto = dao.hostExchangeNotice(accountCode);
            			//-----------------------------------------------
            			System.out.println(dto.getAmount());
            			System.out.println(dto.getBank());
            			System.out.println(dto.getBankNumber());
            			System.out.println(dto.getBankHolder());
            			System.out.println(dto.getRegdate());
            			//-----------------------------------------------
            			model.addAttribute("exchangeInfo", dto);
        				
					} catch (Exception e2)
					{
						System.out.println(e2.toString());
					}
        			
        		} catch(Exception e)
        		{
        			System.out.println(e.toString());       
        		}
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
    	    return "redirect:loginform.action?identify=" + identify;
    	}
    	
    	return "../WEB-INF/views/common/exchangeNotice.jsp";
    }
    
    @RequestMapping(value="/actions/terms.action", method = RequestMethod.GET)
    public String loadAndExchangeTerms(ModelMap model, HttpServletRequest request)
    {
    	
	    return "../WEB-INF/view/common/bankAccountTerms.jsp";
    }
}



















