package com.lookation.mybatis;


 import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IhostQnaManagerDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

@Controller
public class hostQnaManager
{
	@Autowired
	private SqlSession sqlSession;
	
	// hostQnaList 테이블 정보 조회
	@RequestMapping(value="/actions/hostqnamanager.action", method = RequestMethod.GET)
	public String firstList(ModelMap model)
	{
		IhostQnaManagerDAO dao = sqlSession.getMapper(IhostQnaManagerDAO.class);
		
		model.addAttribute("firstList", dao.firstList());
		
		return "/WEB-INF/views/admin/hostQnaManager.jsp";
	}
	
	// 상세정보 조회
	@RequestMapping(value = "/actions/hostqnapopup.action", method = RequestMethod.GET)
	public String secondList(String qna_reply_code, ModelMap model)
	{
		IhostQnaManagerDAO dao = sqlSession.getMapper(IhostQnaManagerDAO.class);

		model.addAttribute("secondList", dao.secondList(qna_reply_code));
		
		return "/WEB-INF/views/admin/hostQnaPopup.jsp";
	}

	// qnareply 삭제
	@RequestMapping(value = "/actions/hostqnareplydelete.action", method = RequestMethod.GET)
	public String remove(String qna_reply_code)
	{
		IhostQnaManagerDAO dao = sqlSession.getMapper(IhostQnaManagerDAO.class);
		
		dao.remove(qna_reply_code);
		
		return "redirect:hostqnamanager.action";
	}

	
}
