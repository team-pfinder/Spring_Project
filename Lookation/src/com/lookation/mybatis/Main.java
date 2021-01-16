package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Main
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value="/actions/membermain.action", method = RequestMethod.GET)
	public String memberMain(Model model)
	{
		return "../WEB-INF/views/user/mainUser.jsp";
	}
	
	@RequestMapping(value="/actions/hostmain.action", method = RequestMethod.GET)
	public String hostMain(Model model)
	{
		return "../WEB-INF/views/host/mainHost.jsp";
	}
	
	@RequestMapping(value="/actions/adminmain.action", method = RequestMethod.GET)
	public String adminMain(Model model)
	{
		return "../WEB-INF/views/admin/mainAdmin.jsp";
	}
}
