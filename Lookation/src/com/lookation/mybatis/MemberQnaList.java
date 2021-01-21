package com.lookation.mybatis;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IMemberQnaListDAO;
import com.lookation.dto.MemberQnaListDTO;
import com.lookation.util.Pager;

@Controller
public class MemberQnaList
{
	@Autowired
	private SqlSession sqlSession;
	
	// QnA 리스트 불러오기
	@RequestMapping(value = "/actions/userqnalist.action", method = RequestMethod.GET)
	public String memberQnaList(String member_code, Model model)
	{
		/* 임시 --------------------------- */
		member_code= "M000001";
		/* -------------------------------- */
		IMemberQnaListDAO dao = sqlSession.getMapper(IMemberQnaListDAO.class);
		
		model.addAttribute("qnaList", dao.memberQnaList(member_code));
		model.addAttribute("nickName", dao.memberNickName(member_code));
		
		return "/WEB-INF/views/user/userQnaList.jsp";
	}
	
	
	// QnA 리스트에서 QnA 수정
	@RequestMapping(value="/actions/modifyqnainlist.action", method = RequestMethod.POST)
	public String modifyQna(MemberQnaListDTO dto)
	{
		IMemberQnaListDAO dao = sqlSession.getMapper(IMemberQnaListDAO.class);
		
		dao.modifyqnalist(dto);
		
		return "redirect:userqnalist.action";
	}
	
	// QnA 리스트에서 QnA 삭제
	@RequestMapping(value="/actions/deleteqnainlist.action", method = RequestMethod.GET)
	public String deleteQna(MemberQnaListDTO dto)
	{
		IMemberQnaListDAO dao =sqlSession.getMapper(IMemberQnaListDAO.class);
		
		dao.deleteqnalist(dto);
		return "redirect:userqnalist.action";
	}
	
}
