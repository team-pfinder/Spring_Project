package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 이용자 마이페이지 메뉴

@Controller
public class MemberMypage
{
    @Autowired
    private SqlSession sqlSession;
    
	// 메인 이동
	@RequestMapping(value ="/actions/membermypage.action", method = RequestMethod.GET)
	public String mypageProfileHost(Model model)
	{
		return "../WEB-INF/views/user/mypageMember.jsp";
	}
	
	
	// 프로필 관리 이동
//	@RequestMapping(value ="/actions/hostprofile.action", method = RequestMethod.GET)
//	public String mypageProfileHost(Model model)
//	{
//		return "../WEB-INF/views/host/s.jsp";
//	}
//	
	// 계좌관리 이동 (이용자, 호스트 여기서 둘다 처리)
	@RequestMapping(value ="/actions/commonbankinfo.action", method = RequestMethod.GET)
	public String mypageBankInfoHost(Model model)
	{
		return "redirect:/actions/bankinfomanage.action";
		//return "../WEB-INF/views/common/bankAccount.jsp";
	}
	
	// 예약 리스트
//	@RequestMapping(value ="/actions/commonmypagemainbankinfo.action", method = RequestMethod.GET)
//	public String mypageBankInfoHost(Model model)
//	{
//		return "../WEB-INF/views/common/bankAccount.jsp";
//	}
	
	
	// 충전 및 환전 신청
//	@RequestMapping(value ="/actions/memberloadandexchange.action", method = RequestMethod.GET)
//	public String mypageExchangeHost(Model model)
//	{
//		return "../WEB-INF/views/user/loadAndExchangeUser.jsp";
//	}

	// 마일리지 내역
	@RequestMapping(value ="/actions/membermileagehistory.action", method = RequestMethod.GET)
	public String mypageHistoryHost(Model model)
	{
		return "../WEB-INF/views/user/mileageHistory.jsp";
	}
	
	
//	// 공간 관리
//	@RequestMapping(value ="/actions/hostmypagemainmilealgehistory.action", method = RequestMethod.GET)
//	public String mypageHistoryHost(Model model)
//	{
//		return "../WEB-INF/views/host/mileageHistory.jsp";
//	}
//	
//
//	
//	// 내가 작성한 글
//	@RequestMapping(value ="/actions/hostmypagemainmilealgehistory.action", method = RequestMethod.GET)
//	public String mypageHistoryHost(Model model)
//	{
//		return "../WEB-INF/views/host/mileageHistory.jsp";
//	}

	
}
