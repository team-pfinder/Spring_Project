package com.lookation.mybatis;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Service
{
	// 이용약관
	@RequestMapping(value = "/actions/termsOfService.action", method = RequestMethod.GET)
	public String termsOfService()
	{
		return "/WEB-INF/views/common/termsOfServicePolicy.jsp";
	}
	
	// 공간 정책
	@RequestMapping(value = "/actions/locManagement.action", method = RequestMethod.GET)
	public String locManagement()
	{
		return "/WEB-INF/views/common/locationManagementPolicy.jsp";
	}
	
	// 개인정보보호정책
	@RequestMapping(value = "/actions/privacy.action", method = RequestMethod.GET)
	public String privacy()
	{
		return "/WEB-INF/views/common/privacyPolicy.jsp";
	}
	
	// 환불 정책
	@RequestMapping(value = "/actions/refund.action", method = RequestMethod.GET)
	public String refund()
	{
		return "/WEB-INF/views/common/refundPolicy.jsp";
	}
	
}
