package com.lookation.mybatis;


 import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IhostReviewManagerDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

@Controller
public class hostReviewManager
{
	@Autowired
	private SqlSession sqlSession;
	
	// hostreviewList 테이블 정보 조회
	@RequestMapping(value="/actions/hostreviewmanager.action", method = RequestMethod.GET)
	public String r_firstList(ModelMap model,HttpServletRequest request)
	{
		IhostReviewManagerDAO dao = sqlSession.getMapper(IhostReviewManagerDAO.class);
		
		model.addAttribute("r_firstList", dao.r_firstList());
		
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		                                                                                  
		// 다음 페이지로 이동                           
		return "/WEB-INF/views/admin/hostReviewManager.jsp";
	}
	
	// 상세정보 조회
	@RequestMapping(value = "/actions/hostreviewpopup.action", method = RequestMethod.GET)
	public String r_secondList(String review_reply_code, ModelMap model,HttpServletRequest request)
	{
		
		IhostReviewManagerDAO dao = sqlSession.getMapper(IhostReviewManagerDAO.class);

		model.addAttribute("r_secondList", dao.r_secondList(review_reply_code));
		
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		                                                                                  
		// 다음 페이지로 이동                           
		return "/WEB-INF/views/admin/hostReviewPopup.jsp";
	}

	// reviewreply 삭제
	@RequestMapping(value = "/actions/hostreviewdelete.action", method = RequestMethod.GET)
	public String r_remove(String review_reply_code,HttpServletRequest request)
	{
		IhostReviewManagerDAO dao = sqlSession.getMapper(IhostReviewManagerDAO.class);
		
		dao.r_remove(review_reply_code);
		
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		                                                                                  
		// 다음 페이지로 이동 
		return "redirect:hostreviewmanager.action";
	}

	
}
