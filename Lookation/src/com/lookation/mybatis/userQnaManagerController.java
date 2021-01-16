package com.lookation.mybatis;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IuserQnaManagerDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

@Controller
public class userQnaManagerController
{
	@Autowired
	private SqlSession sqlSession;
	
	// userQnaList 테이블 정보 조회
	@RequestMapping(value="/actions/userqnamanager.action", method = RequestMethod.GET)
	public String basicList(ModelMap model)
	{
		IuserQnaManagerDAO dao = sqlSession.getMapper(IuserQnaManagerDAO.class);
		
		model.addAttribute("basicList", dao.basicList());
		
		return "/WEB-INF/views/admin/userQnaManager.jsp";
	}
	
	// 상세정보 조회
	@RequestMapping(value = "/actions/userqnapopup.action", method = RequestMethod.GET)
	public String detailList(String qna_code, ModelMap model)
	{
		IuserQnaManagerDAO dao = sqlSession.getMapper(IuserQnaManagerDAO.class);
				
		model.addAttribute("detailList", dao.detailList(qna_code));
		
		return "/WEB-INF/views/admin/userQnaPopup.jsp";
	}
	
	// qna 삭제 (단일)
	@RequestMapping(value = "/actions/qnadelete.action", method = RequestMethod.GET)
	public String remove(String qna_code)
	{
		IuserQnaManagerDAO dao = sqlSession.getMapper(IuserQnaManagerDAO.class);
		
		dao.remove(qna_code);
		
		return "redirect:userqnamanager.action";
	}
	
	// qna 삭제 (다중)
	
}
