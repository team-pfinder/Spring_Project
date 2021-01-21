package com.lookation.mybatis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BlindContent
{
	@RequestMapping(value = "/actions/blindContent.action", method = RequestMethod.GET)
	public String blind()
	{
		return "/WEB-INF/views/common/blindContent.jsp";
	}
}
