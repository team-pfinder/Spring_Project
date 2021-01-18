package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IReportHostDAO;
import com.lookation.dto.reportHostDTO;


@Controller
public class reportHostController
{
	@Autowired
	private SqlSession sqlSession;
	
	// 신고 접수 폼 호출
	@RequestMapping(value = "/actions/reporthostform.action", method = RequestMethod.GET)
	public String addForm()
	{
		String result = "/WEB-INF/views/host/reportHost.jsp";
		
		return result;
	}
	// 신고 접수
	@RequestMapping(value = "/actions/reporthost.action", method = RequestMethod.POST)
	public String add(reportHostDTO dto)
	{
		IReportHostDAO dao = sqlSession.getMapper(IReportHostDAO.class);
		
		dao.add(dto);
		
		return "redirect:신고하던페이지";
	}
}
