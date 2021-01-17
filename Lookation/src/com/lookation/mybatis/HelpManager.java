package com.lookation.mybatis;


 import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHelpManagerDAO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

@Controller
public class HelpManager
{
	@Autowired
	private SqlSession sqlSession;
	
	// hostQnaList 테이블 정보 조회
	@RequestMapping(value="/actions/helpmanager.action", method = RequestMethod.GET)
	public String firstList(ModelMap model)
	{
		IHelpManagerDAO dao = sqlSession.getMapper(IHelpManagerDAO.class);
		
		model.addAttribute("A_helpList", dao.A_helpList());
		
		return "/WEB-INF/views/admin/helpManager.jsp";
	}
	


	
}
