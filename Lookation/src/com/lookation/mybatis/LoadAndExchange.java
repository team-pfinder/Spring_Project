
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
	
    // 잔액 및 등록 계좌 조회
    @RequestMapping(value="/actions/memberloadandexchange.action", method = RequestMethod.GET)
    public String loadForm(ModelMap model)//, HttpServletRequest request)
    {
    	ILoadAndExchangeDAO dao = sqlSession.getMapper(ILoadAndExchangeDAO.class);
    	LoadAndExchangeDTO dto = new LoadAndExchangeDTO();
    	//String identifyCode = "M000002";//request.getParameter("identifyCode");
    	dto.setMemberCode("M000001");
    	
    	try
		{
    		model.addAttribute("bankInfoList", dao.bankInfoList(dto));
    		System.out.println("bankInfoList");
    		model.addAttribute("balance", dao.getBalance(dto));
    		System.out.println("balance");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

        return "../WEB-INF/views/user/loadAndExchangeUser.jsp";
    }
    
    // 충전 신청
    @RequestMapping(value="/actions/memberload.action", method = RequestMethod.POST)
    public String loadReg(ModelMap model, HttpServletRequest request)
    {
    	ILoadAndExchangeDAO dao = sqlSession.getMapper(ILoadAndExchangeDAO.class);
    	LoadAndExchangeDTO dto = new LoadAndExchangeDTO();
    
    	//----------------------------------------------------------------
    	String identifyCode = "M000001"; //request.getParameter("identifyCode");
    	//----------------------------------------------------------------
    	//String memberCode = identifyCode;
    	dto.setMemberCode(identifyCode);//--
    	dto.setBankNumber(request.getParameter("bankAccount"));
    	dto.setAmount(Integer.parseInt(request.getParameter("charge")));
    	
    	// 테스트
    	System.out.println(dto.getBankNumber());
    	System.out.println(dto.getAmount());    	

    	// 충전신청 정보 DB 등록
    	try
		{
    		dao.loadRegister(dto);	
    		System.out.println("충전신청 성공");
        	
    		// 등록된 충전신청 정보 가져오기
        	try
    		{
    			dto = dao.loadRegisterNotice(identifyCode);
    			System.out.println(dto.getAmount());
    			System.out.println(dto.getBank());
    			System.out.println(dto.getBankNumber());
    			System.out.println(dto.getRegdate());
    			
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
   
    // 환전 신청
    @RequestMapping(value="/actions/memberexchange.action", method = RequestMethod.POST)
    public String exchangeReg(ModelMap model, HttpServletRequest request)
    {
    	ILoadAndExchangeDAO dao = sqlSession.getMapper(ILoadAndExchangeDAO.class);
    	LoadAndExchangeDTO dto = new LoadAndExchangeDTO();
    	
    	//----------------------------------------------------------------
    	String identifyCode = "M000001"; //request.getParameter("identifyCode");
    	//----------------------------------------------------------------
    	//String memberCode = identifyCode;
    	dto.setMemberCode(identifyCode);
    	dto.setBankNumber(request.getParameter("bankAccount"));
    	dto.setAmount(Integer.parseInt(request.getParameter("exchange")));
    	
    	// 테스트
    	System.out.println(dto.getMemberCode());
    	System.out.println(dto.getBankNumber());
    	System.out.println(dto.getAmount());   
    	try
		{
    		// 환전 쿼리
    		dao.exchangeRegister(dto);
    		System.out.println("환전성공");

    		System.out.println("신청정보 가져오는중");
    		try
			{
        		// 등록된 충전신청 정보 가져오기
    			dto = dao.exchangeNotice(identifyCode);
    			// 테스트
    			System.out.println(dto.getAmount());
    			System.out.println(dto.getBank());
    			System.out.println(dto.getBankNumber());
    			System.out.println(dto.getBankHolder());
    			System.out.println(dto.getRegdate());
    			
    			model.addAttribute("exchangeInfo", dto);
			} catch (Exception e2)
			{
				System.out.println(e2.toString());
			}
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
    	
    	return "../WEB-INF/views/user/exchangeNotice.jsp";
    }
  
}



















