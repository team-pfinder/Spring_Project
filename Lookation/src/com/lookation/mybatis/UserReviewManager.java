package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IuserReviewManagerDAO;

@Controller
public class UserReviewManager
{
	@Autowired
	private SqlSession sqlSession;
	
	// userReviewList 테이블 정보 조회
	@RequestMapping(value="/actions/userreviewmanager.action", method = RequestMethod.GET)
	public String basicList(Model model, HttpServletRequest request)
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
		
		IuserReviewManagerDAO dao = sqlSession.getMapper(IuserReviewManagerDAO.class);
		
		model.addAttribute("basicList", dao.basicList());
		
		return "/WEB-INF/views/admin/userReviewManager.jsp";
	}
	
	// 상세정보 조회
	@RequestMapping(value="/actions/userreviewpopup.action", method = RequestMethod.GET)
	public String detailList(String review_code, Model model, HttpServletRequest request)
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
		
		IuserReviewManagerDAO dao = sqlSession.getMapper(IuserReviewManagerDAO.class);
		
		model.addAttribute("detailList", dao.detailList(review_code));
		
		return "/WEB-INF/views/admin/userReviewPopup.jsp";
	}
	
	// review 삭제 (단일)
	@RequestMapping(value ="/actions/reviewdelete.action", method = RequestMethod.GET)
	public String remove(String review_code, HttpServletRequest request)
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
		
		IuserReviewManagerDAO dao = sqlSession.getMapper(IuserReviewManagerDAO.class);
		
		dao.remove(review_code);
		
		return "redirect:userreviewmanager.action";
	}
	
	// review 삭제 (다중)
	@RequestMapping(value = "/actions/selectremove.action", method = RequestMethod.GET)
	public String multiRemove(HttpServletRequest request)
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
		
		String sidParameter = request.getParameter("sid");
		
		String [] review_code = sidParameter.split(",");
		
		IuserReviewManagerDAO dao = sqlSession.getMapper(IuserReviewManagerDAO.class);
		
		for (int i = 0; i < review_code.length; i++)
		{
			dao.remove(review_code[i]);
		}
		
		return "redirect:userreviewmanager.action";
	}
	
}
