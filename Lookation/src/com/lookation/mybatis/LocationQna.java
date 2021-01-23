package com.lookation.mybatis;


import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.ILocationQnaDAO;
import com.lookation.dto.LocationQnaDTO;

@Controller
public class LocationQna
{
	@Autowired
	private SqlSession sqlSession;
	
	
	
	// QnA 작성 팝업으로 이동
	@RequestMapping(value="/actions/writeqna.action", method = RequestMethod.GET)
	public String QnaForm(Model model, HttpServletRequest request)
	{	
		String locCode = request.getParameter("locCode");
		
		// 호스트인지 이용자인지 담을 변수
		String check = request.getParameter("identify");

		if(check.equals("member"))	// 이용자라면
		{
			String memCode = request.getParameter("memCode");
			model.addAttribute("memCode", memCode);		
		}
		else						// 호스트라면
		{
			String hostCode = request.getParameter("hostCode");
			model.addAttribute("hostCode", hostCode);
		}
		
		model.addAttribute("locCode", locCode);
		
		return "../WEB-INF/views/common/writeQnaPopup.jsp";
	}
	
	// QnA 수정 팝업으로 이동
	@RequestMapping(value="/actions/modifyformqna.action", method = RequestMethod.GET)
	public String modifyFormQna(Model model, HttpServletRequest request)
	{	
		// 호스트인지 멤버인지 담을 변수
		String check = request.getParameter("identify");
		ILocationQnaDAO dao = sqlSession.getMapper(ILocationQnaDAO.class);

		if(check.equals("member"))
		{
			String qna_code = request.getParameter("qna_code");
			model.addAttribute("modify", dao.updateQnaForm(qna_code));
			
		}
		else
		{
			String qna_reply_code = request.getParameter("qna_reply_code");
			model.addAttribute("modifyQnaReply", dao.updateQnaReplyForm(qna_reply_code));
		}
		return "/WEB-INF/views/common/modifyQnaPopup.jsp";
		
	}
	
	/*=== 이용자 ===*/
	
	// 이용자 : QnA 작성
	@RequestMapping(value="/actions/qnainsert.action", method = RequestMethod.POST)
	public String insertQna(LocationQnaDTO dto)
	{
		ILocationQnaDAO dao = sqlSession.getMapper(ILocationQnaDAO.class);
		
		dao.insertMemQna(dto);
		
		return "redirect:locationdetail.action";
		// 이것도 ajax로 팝업창 닫는데 어떻게해야할지... 
	}
	
	
	// 이용자 : QnA 수정
	@RequestMapping(value="/actions/modifyqna.action", method = RequestMethod.POST)
	public void modifyQna(LocationQnaDTO dto)
	{
		ILocationQnaDAO dao = sqlSession.getMapper(ILocationQnaDAO.class);
		
		dao.updateMemQna(dto);
	}
	
	
	// 이용자 : QnA 삭제 
	@RequestMapping(value="/actions/deleteqna.action", method = RequestMethod.GET)
	public String deleteQna(LocationQnaDTO dto)
	{
		ILocationQnaDAO dao = sqlSession.getMapper(ILocationQnaDAO.class);
		
		dao.deleteMemQna(dto);
		
		return "redirect:locationdetail.action";
	}
	
	/*=== 호스트 ===*/
	
	// 호스트 : QnA 답글 작성
	@RequestMapping(value = "/actions/qnareplyinsert.action", method = RequestMethod.POST)
	public String insertQnaReply(LocationQnaDTO dto)
	{
		ILocationQnaDAO dao = sqlSession.getMapper(ILocationQnaDAO.class);

		dao.insertHostQna(dto);

		return "redirect:locationdetailhost.action";
		// ajax 쓰면 void로 돌려줘야 하는줄 알았는데 이렇게 해도 먹어서
		// 냅둬야 할지 어쩔지 모르겠다.
	}
	
	
	// 호스트 : Qna 답글 수정
	@RequestMapping(value="/actions/modifyrqnareply.action", method=RequestMethod.POST)
	public void modifyQnaReply(LocationQnaDTO dto)
	{
		ILocationQnaDAO dao = sqlSession.getMapper(ILocationQnaDAO.class);
		
		dao.updateHostQna(dto);
	}
	
	// 호스트 : QnA 답글 삭제
	@RequestMapping(value = "/actions/deleteqnareply.action", method = RequestMethod.GET)
	public String deleteQnaReply(LocationQnaDTO dto)
	{
		ILocationQnaDAO dao = sqlSession.getMapper(ILocationQnaDAO.class);
		
		dao.deleteHostQna(dto);
		
		return "redirect:locationdetailhost.action";
		
	}

}
