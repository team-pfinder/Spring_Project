package com.lookation.mybatis;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IQnaDAO;
import com.lookation.dto.QnaDTO;

@Controller
public class Qna
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value="/actions/qnainsert.action", method = RequestMethod.POST)
	public String insertQna(QnaDTO dto)
	{
		IQnaDAO dao = sqlSession.getMapper(IQnaDAO.class);
		
		dao.insertMemQna(dto);
		
		return "redirect:locationdetail.action";
		
	}
	
	// 수정 폼 못받아옴... 
	@RequestMapping(value="/actions/modifyformqna.action", method = RequestMethod.GET)
	public String modifyFormQna(QnaDTO dto)
	{
		String result = null;
		
		IQnaDAO dao = sqlSession.getMapper(IQnaDAO.class);
		
		dao.updateForm(dto);
		
		result = "/WEB-INF/views/common/modifyQnaPopup.jsp";
		
		return result;
	}
	
	//삭제 
	@RequestMapping(value="/actions/deleteqna.action", method = RequestMethod.GET)
	public String deleteQna(QnaDTO dto)
	{
		IQnaDAO dao = sqlSession.getMapper(IQnaDAO.class);
		
		dao.deleteMemQna(dto);
		
		return "redirect:locationdetail.action";
	}
	
}
