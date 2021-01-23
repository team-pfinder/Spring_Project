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
import com.oreilly.servlet.MultipartRequest;


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
	// 신고 접수 후 이미지 업로드 폼 호출
	@RequestMapping(value = "/actions/reporthost.action", method = RequestMethod.POST)
	public String add(reportHostDTO dto,String book_code,String host_code, Model model)
	{
		IReportHostDAO dao = sqlSession.getMapper(IReportHostDAO.class);
		
		dao.add(dto);
		
		model.addAttribute("list", dao.list(book_code, host_code));
		
		return "/WEB-INF/views/host/reportHostImg.jsp";
	}
	
	// 이미지 업로드
	@RequestMapping(value = "/actions/reporthostimg.action", method = {RequestMethod.GET, RequestMethod.POST})
	public void addImg(HttpServletRequest request, Model model, String book_report_code, String book_report_img_url)
	{
		IReportHostDAO dao = sqlSession.getMapper(IReportHostDAO.class);
		
		try
		{
			MultipartRequest m = FileManager.upload(request, "images");
			ArrayList<String> imageList = FileManager.getFileNames(m);
			model.addAttribute("imageList", imageList);
			
		} catch (Exception e)
		{
			e.toString();
		}
		
		dao.addImg(book_report_code, book_report_img_url);
		
		
	}
}
