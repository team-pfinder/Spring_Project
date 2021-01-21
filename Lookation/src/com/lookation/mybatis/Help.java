package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;

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
	public String helpList(ModelMap model, HttpServletRequest request)
	{
		IHelpDAO dao = sqlSession.getMapper(IHelpDAO.class);
		
		model.addAttribute("U_helpList", dao.U_helpList());
		/*
		String help_code = request.getParameter("help_code");
		
	
		model.addAttribute("help_code", help_code);
		System.out.println("여기는 조회중 : " + help_code);
		*/
		
		return "/WEB-INF/views/common/help.jsp";
	}
	
	//제목 클릭 후 자세한 내용 
	@RequestMapping(value = "/actions/helpdetail.action", method = RequestMethod.GET)
	public String U_detailList(String help_code, ModelMap model)
	{
		IHelpDAO dao = sqlSession.getMapper(IHelpDAO.class);

		model.addAttribute("U_detailList", dao.U_detailList(help_code));
		
		//System.out.println(help_code);
		
		return "/WEB-INF/views/common/helpDetail.jsp";
	}

}
