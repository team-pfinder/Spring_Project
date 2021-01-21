package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IReportUserDAO;
import com.lookation.dto.reportUserDTO;

@Controller
public class ReportUser
{
	@Autowired
	private SqlSession sqlSession;
	
	// 신고 접수 폼 호출
	@RequestMapping(value = "/actions/reportuserform.action", method = RequestMethod.GET)
	public String addForm()
	{
		return "/WEB-INF/views/user/reportUser.jsp";
	}
	
	// 신고 접수
	
	public String add(reportUserDTO dto)
	{
		IReportUserDAO dao = sqlSession.getMapper(IReportUserDAO.class);
		
		dao.add(dto);
		
		return "redirect:신고하던페이지";
	}
}
