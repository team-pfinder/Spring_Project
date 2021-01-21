package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHelpDAO;

@Controller
public class Help
{
		@Autowired
		private SqlSession sqlSession;
		
		//관리자가 작성한 도움말 정보 <<조회>>
		@RequestMapping(value="/actions/help.action", method = RequestMethod.GET)
		public String helpList(ModelMap model)
		{
			IHelpDAO dao = sqlSession.getMapper(IHelpDAO.class);
			
			model.addAttribute("U_helpList", dao.U_helpList());
			
			return "/WEB-INF/views/common/help.jsp";
		}
}
