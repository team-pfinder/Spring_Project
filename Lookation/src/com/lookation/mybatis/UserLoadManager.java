package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IUserLoadManagerDAO;

@Controller
public class UserLoadManager
{
	@Autowired
	private SqlSession sqlSession;
	
	// 충전 신청 리스트 조회 
	@RequestMapping(value ="/actions/loadmgr.action", method = RequestMethod.GET)
	public String adminLoadManager(Model model)
	{
		IUserLoadManagerDAO dao = sqlSession.getMapper(IUserLoadManagerDAO.class);
		model.addAttribute("list", dao.loadRegList());
		
		return "../WEB-INF/views/admin/loadManager.jsp";
	}
	
	// 충전 신청 승인 처리
	@RequestMapping(value ="/actions/loadmgrapprove.action", method = RequestMethod.POST)
	public String adminLoadManagerApprove()
	{
		return null;
	}

	// 충전 신청 반려 처리
	@RequestMapping(value = "/actions/loadmgrdeny.action", method = RequestMethod.POST)
	public String adminLoadManagerDeny()
	{
		return null;
	}
}
