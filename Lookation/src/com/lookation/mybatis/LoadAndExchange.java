
package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.ILoadAndExchangeDAO;
import com.lookation.dto.LoadAndExchangeDTO;


@Controller
public class LoadAndExchange
{
    @Autowired
    private SqlSession sqlSession;
	
    // 이용자, 호스트 잔액 및 등록 계좌 조회
    @RequestMapping(value="/actions/loadandexchange.action", method = RequestMethod.GET)
    public String bankInfoAndBalance(ModelMap model)//, HttpServletRequest request)
    {
    	ILoadAndExchangeDAO dao = sqlSession.getMapper(ILoadAndExchangeDAO.class);
    	LoadAndExchangeDTO dto = new LoadAndExchangeDTO();
    	
    	String identifyCode = "H000003";//request.getParameter("identifyCode");
    	dto.setIdentifyCode(identifyCode);
    	
    	// 이용자일 경우
    	if(identifyCode.startsWith("M"))
    	{
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

            return "../WEB-INF/views/user/loadAndExchangeUser.jsp";
    	}
    	// 호스트일 경우
    	else if(identifyCode.startsWith("H"))
    	{
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
    		return "../WEB-INF/views/host/exchangeHost.jsp";
    	}
    	
    	return null;

    }
    
    // 이용자 충전 신청
    @RequestMapping(value="/actions/memberload.action", method = RequestMethod.POST)
    public String loadReg(ModelMap model, HttpServletRequest request)
    {
    	ILoadAndExchangeDAO dao = sqlSession.getMapper(ILoadAndExchangeDAO.class);
    	LoadAndExchangeDTO dto = new LoadAndExchangeDTO();
    
    	//----------------------------------------------------------------
    	String identifyCode = "M000001"; //request.getParameter("identifyCode");
    	//----------------------------------------------------------------
    	//String memberCode = identifyCode;
    	dto.setIdentifyCode(identifyCode);//--
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
    			dto = dao.memberLoadRegisterNotice(identifyCode);
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

    	return "../WEB-INF/views/user/loadRegisterNotice.jsp";

    	
    }
   
    // 이용자, 호스트 환전 신청
    @RequestMapping(value="/actions/exchange.action", method = RequestMethod.POST)
    public String exchangeReg(ModelMap model, HttpServletRequest request)
    {
    	ILoadAndExchangeDAO dao = sqlSession.getMapper(ILoadAndExchangeDAO.class);
    	LoadAndExchangeDTO dto = new LoadAndExchangeDTO();
    	
    	//----------------------------------------------------------------
    	String identifyCode = "H000003"; //request.getParameter("identifyCode");
    	//----------------------------------------------------------------
    	dto.setIdentifyCode(identifyCode);
    	dto.setBankNumber(request.getParameter("bankAccount"));
    	dto.setAmount(Integer.parseInt(request.getParameter("exchange")));
    	
    	// 테스트
    	System.out.println(dto.getIdentifyCode());
    	System.out.println(dto.getBankNumber());
    	System.out.println(dto.getAmount());   
    	try
		{
    		// 환전 쿼리
    		if(identifyCode.startsWith("M"))
    		{
    			dao.memberExchangeRegister(dto);
    		}
    		else if(identifyCode.startsWith("H"))
    		{
    			dao.hostExchangeRegister(dto);
    		}
    		
    		System.out.println("환전성공");

    		System.out.println("신청정보 가져오는중");
    		try
			{
        		// 등록된 충전신청 정보 가져오기
        		if(identifyCode.startsWith("M"))
        		{
        			dto = dao.memberExchangeNotice(identifyCode);
        		}
        		else if(identifyCode.startsWith("H"))
        		{
        			dto = dao.hostExchangeNotice(identifyCode);
        		}
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
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
    	
    	return "../WEB-INF/views/common/exchangeNotice.jsp";
    }

}



















