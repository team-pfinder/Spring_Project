package com.lookation.mybatis;


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
	public String r_firstList(ModelMap model)
	{
		IhostReviewManagerDAO dao = sqlSession.getMapper(IhostReviewManagerDAO.class);
		
		model.addAttribute("r_firstList", dao.r_firstList());
		
		return "/WEB-INF/views/admin/hostReviewManager.jsp";
	}
	
	// 상세정보 조회
	@RequestMapping(value = "/actions/hostreviewpopup.action", method = RequestMethod.GET)
	public String r_secondList(String review_reply_code, ModelMap model)
	{
		IhostReviewManagerDAO dao = sqlSession.getMapper(IhostReviewManagerDAO.class);

		model.addAttribute("r_secondList", dao.r_secondList(review_reply_code));
		
		return "/WEB-INF/views/admin/hostReviewPopup.jsp";
	}

	// reviewreply 삭제
	@RequestMapping(value = "/actions/hostreviewdelete.action", method = RequestMethod.GET)
	public String r_remove(String review_reply_code)
	{
		IhostReviewManagerDAO dao = sqlSession.getMapper(IhostReviewManagerDAO.class);
		
		dao.r_remove(review_reply_code);
		
		return "redirect:hostreviewmanager.action";
	}

	
}
