package com.lookation.mybatis;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.util.FileManager;

@Controller
public class FileTest
{
	@RequestMapping(value="/actions/imagetest.action", method=RequestMethod.GET)
	public String imageTest(HttpServletRequest request, Model model)
	{
		return "../WEB-INF/views/FormImage.jsp";
	}
	
	@RequestMapping(value="/actions/downloadtest.action", method=RequestMethod.GET)
	public String downloadTest(HttpServletRequest request, Model model)
	{
		return "../WEB-INF/views/FormDownload.jsp";
	}
	
	@RequestMapping(value="/actions/uploadimagetest.action", method=RequestMethod.POST)
	public String uploadImageTest(HttpServletRequest request, Model model)
	{
		ArrayList<String> imageList = FileManager.upload(request, "images");
		
		model.addAttribute("imageList", imageList);
		
		return "../WEB-INF/views/View.jsp";
	}
	
	@RequestMapping(value="/actions/uploaddownloadtest.action", method=RequestMethod.POST)
	public String uploadDownloadTest(HttpServletRequest request, Model model)
	{
		ArrayList<String> downList = FileManager.upload(request, "downloads");
		
		model.addAttribute("downList", downList);
		
		return "../WEB-INF/views/ViewDownload.jsp";
	}
}
