package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IBlindManagerDAO;

@Controller
public class BlindManager
{
	@Autowired
	private SqlSession sqlSession;
	
	// blindManagerList 전체 조회
	@RequestMapping(value = "/actions/blindmanager.action", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		IBlindManagerDAO dao = sqlSession.getMapper(IBlindManagerDAO.class);
		
		model.addAttribute("list", dao.list());
		
		return "/WEB-INF/views/admin/blindManager.jsp";
	}
	
	// 블라인드 해제
	@RequestMapping(value = "/actions/blindremove.action", method = RequestMethod.GET)
	public String remove(String loc_blind_code, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		IBlindManagerDAO dao = sqlSession.getMapper(IBlindManagerDAO.class);
		
		dao.remove(loc_blind_code);
		
		return "redirect:blindmanager.action";
	}
}
