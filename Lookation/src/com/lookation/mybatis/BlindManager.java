package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IBankInfoDAO;
import com.lookation.dao.IBlindManagerDAO;

@Controller
public class BlindManager
{
	@Autowired
	private SqlSession sqlSession;
	
	// blindManagerList 전체 조회
	@RequestMapping(value = "/actions/blindmanager.action", method = RequestMethod.GET)
	public String list(Model model)
	{
		IBlindManagerDAO dao = sqlSession.getMapper(IBlindManagerDAO.class);
		
		model.addAttribute("list", dao.list());
		
		return "/WEB-INF/views/admin/blindManager.jsp";
	}
	
	// 블라인드 해제
	@RequestMapping(value = "/actions/blindremove.action", method = RequestMethod.GET)
	public String remove(String loc_blind_code)
	{
		IBlindManagerDAO dao = sqlSession.getMapper(IBlindManagerDAO.class);
		
		dao.remove(loc_blind_code);
		
		return "redirect:blindmanager.action";
	}
}
