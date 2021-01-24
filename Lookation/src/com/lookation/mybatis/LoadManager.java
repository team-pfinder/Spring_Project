package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IUserLoadManagerDAO;

@Controller
public class LoadManager
{
	@Autowired
	private SqlSession sqlSession;
	
	// 충전 신청 리스트 조회 
	@RequestMapping(value ="/actions/loadmgr.action", method = RequestMethod.GET)
	public String adminLoadManager(Model model, HttpServletRequest request)
	{
		IUserLoadManagerDAO dao = sqlSession.getMapper(IUserLoadManagerDAO.class);
		model.addAttribute("list", dao.loadRegList());
		
		HttpSession session = request.getSession();

		String accountCode = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(accountCode == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		                                                                                  
		// 다음 페이지로 이동                                                                                                                                                                                                                                                                                                                                             
		return "../WEB-INF/views/admin/loadManager.jsp";
	}
	
	// 충전 신청 승인 처리
	@RequestMapping(value ="/actions/loadmgrapprove.action", method = RequestMethod.GET)
	public String adminLoadManagerApprove(Model model, HttpServletRequest request)
	{
		IUserLoadManagerDAO dao = sqlSession.getMapper(IUserLoadManagerDAO.class);
		dao.loadRegApprove(request.getParameter("regCode"));
		
		HttpSession session = request.getSession();

		String accountCode = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(accountCode == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		                                                                                  
		// 다음 페이지로 이동                                                                                                                                                                                                                                                                                                                                             
		return "redirect:loadmgr.action";
	}

	// 충전 신청 반려 처리
	@RequestMapping(value = "/actions/loadmgrdeny.action", method = RequestMethod.GET)
	public String adminLoadManagerDeny(Model model, HttpServletRequest request)
	{
		IUserLoadManagerDAO dao = sqlSession.getMapper(IUserLoadManagerDAO.class);
		dao.loadRegDeny(request.getParameter("regCode"));
		
		HttpSession session = request.getSession();

		String accountCode = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(accountCode == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		                                                                                  
		// 다음 페이지로 이동                                                                                                                                                                                                                                                                                                                                             
		
		return "redirect:loadmgr.action";
	}
}
