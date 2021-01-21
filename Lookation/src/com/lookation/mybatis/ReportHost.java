package com.lookation.mybatis;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IReportHostDAO;
import com.lookation.dto.reportHostDTO;
import com.lookation.util.FileManager;


@Controller
public class ReportHost
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
	public String add(reportHostDTO dto, HttpServletRequest request, Model model)
	{
		IReportHostDAO dao = sqlSession.getMapper(IReportHostDAO.class);
		
		dao.add(dto);
		
		ArrayList<String> imageList = FileManager.upload(request, "images");
		
		model.addAttribute("imageList", imageList);
		
		return "redirect:신고하던페이지";
	}
}
