package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.INoticeManagerDAO;
import com.lookation.dto.NoticeDTO;

//※ Spring 이 제공하는 『Controller』 인터페이스를 구현함으로써
//사용자 정의 컨트롤러 클래스를 구성한다.

@Controller
public class NoticeManager
{
	@Autowired
	private SqlSession sqlSession;
	
	//관리자가 작성한 도움말 정보 <<조회>>
	@RequestMapping(value="/actions/noticemanager.action", method = RequestMethod.GET)
	public String noticeList(ModelMap model)
	{
		INoticeManagerDAO dao = sqlSession.getMapper(INoticeManagerDAO.class);
		
		model.addAttribute("A_noticeList", dao.A_noticeList());
		
		return "/WEB-INF/views/admin/noticeManager.jsp";
	}
	
	//관리자가 공지사항 <<작성>>
	@RequestMapping(value="/actions/noticeinsertform.action", method= RequestMethod.GET)
	public String noticeInsertform()
	{	
		String result = null;
		
		//INoticeManagerDAO dao = sqlSession.getMapper(INoticeManagerDAO.class);
		//있어도 없어도 됨
		
		result = "/WEB-INF/views/admin/inputNoticeInfo.jsp";
		
		return result;
		
	}
	
	//인서트 같은 경우 폼 안에서 서브밋할거기때문에 post방식
	@RequestMapping(value="/actions/noticeinsert.action", method = RequestMethod.POST)
	public String noticeInsert(NoticeDTO dto)
	{
		INoticeManagerDAO dao = sqlSession.getMapper(INoticeManagerDAO.class);
		
		dao.add(dto);
		
		System.out.println(dto.getNotice_content());
		
		return "redirect:noticemanager.action";
		
	}
	
	//관리자가 작성한 도움말 <<삭제>>
	@RequestMapping(value="/actions/noticedelete.action", method=RequestMethod.GET)
	public String noticeDelete(NoticeDTO dto) 
	{ 
		INoticeManagerDAO dao = sqlSession.getMapper(INoticeManagerDAO.class);
	  
	    dao.remove(dto);
	  
	    return "redirect:noticemanager.action"; 
	}
	
	//관리자가 작성한 도움말 <<수정>>
	@RequestMapping(value="/actions/noticeupdateform.action", method=RequestMethod.GET)
	public String noticeUpdateForm(ModelMap model, String notice_code) 
	{ 
		INoticeManagerDAO dao = sqlSession.getMapper(INoticeManagerDAO.class);
		
		dao.search(notice_code);
	
		model.addAttribute("search", dao.search(notice_code));
		
		
		
		return "/WEB-INF/views/admin/modifyNoticeInfo.jsp"; 
	}
	

	@RequestMapping(value="/actions/noticeupdate.action", method=RequestMethod.POST)
	public String noticeUpdate(NoticeDTO dto) 
	{ 
		INoticeManagerDAO dao = sqlSession.getMapper(INoticeManagerDAO.class);
		
		dao.update(dto);
		
		//System.out.println(dto.getNotice_code());
		
		return "redirect:noticemanager.action"; 
	}

		

}
