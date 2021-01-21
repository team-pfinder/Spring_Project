package com.lookation.mybatis;


 import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHelpManagerDAO;
import com.lookation.dto.HelpDTO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

@Controller
public class HelpManager
{
	@Autowired
	private SqlSession sqlSession;
	
	//관리자가 작성한 도움말 정보 <<조회>>
	@RequestMapping(value="/actions/helpmanager.action", method = RequestMethod.GET)
	public String helpList(ModelMap model)
	{
		IHelpManagerDAO dao = sqlSession.getMapper(IHelpManagerDAO.class);
		
		model.addAttribute("A_helpList", dao.A_helpList());
		
		return "/WEB-INF/views/admin/helpManager.jsp";
	}
	
	//관리자가 도움말 <<작성>>
	@RequestMapping(value="/actions/helpinsertform.action", method= RequestMethod.GET)
	public String helpInsertform()
	{	
		String result = null;
		
		//IHelpManagerDAO dao = sqlSession.getMapper(IHelpManagerDAO.class);
		//있어도 없어도 됨
		
		result = "/WEB-INF/views/admin/inputHelpInfo.jsp";
		
		return result;
		
	}
	
	//인서트 같은 경우 폼 안에서 서브밋할거기때문에 post방식
	@RequestMapping(value="/actions/helpinsert.action", method = RequestMethod.POST)
	public String helpInsert(HelpDTO dto)
	{
		IHelpManagerDAO dao = sqlSession.getMapper(IHelpManagerDAO.class);
		
		
		dao.add(dto);
		
		System.out.println(dto.getHelp_content());
		
		return "redirect:helpmanager.action";
		
	}

	//관리자가 작성한 도움말 <<삭제>>
	@RequestMapping(value="/actions/helpdelete.action", method=RequestMethod.GET)
	public String helpDelete(HelpDTO dto) 
	{ 
		IHelpManagerDAO dao = sqlSession.getMapper(IHelpManagerDAO.class);
	  
	    dao.remove(dto);
	  
	    return "redirect:helpmanager.action"; 
	}
	
	//관리자가 작성한 도움말 <<수정>>
	@RequestMapping(value="/actions/helpupdateform.action", method=RequestMethod.GET)
	public String helpUpdateForm(ModelMap model, String help_code) 
	{ 
		IHelpManagerDAO dao = sqlSession.getMapper(IHelpManagerDAO.class);
		
		dao.search(help_code);
	
		model.addAttribute("search", dao.search(help_code));
		
		
		
		return "/WEB-INF/views/admin/modifyHelpInfo.jsp"; 
	}
	

	@RequestMapping(value="/actions/helpupdate.action", method=RequestMethod.POST)
	public String helpUpdate(HelpDTO dto) 
	{ 
		IHelpManagerDAO dao = sqlSession.getMapper(IHelpManagerDAO.class);
		
		dao.update(dto);
		
		//System.out.println(dto.getHelp_code());
		
		return "redirect:helpmanager.action"; 
	}

	


	
}
