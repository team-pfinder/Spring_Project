package com.lookation.mybatis;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHostAccountDAO;
import com.lookation.dao.IReportHostDAO;
import com.lookation.dto.reportHostDTO;
import com.lookation.util.FileManager;
import com.oreilly.servlet.MultipartRequest;


@Controller
public class ReportHost
{
	@Autowired
	private SqlSession sqlSession;
	
	
	// 신고 접수 후 이미지 업로드 폼 호출
	@RequestMapping(value = "/actions/reporthost.action", method = RequestMethod.POST)
	public String add(reportHostDTO dto,String book_code,String host_code, Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute("hostCode"); 

		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";                                                         

		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
		        // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.

			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
				model.addAttribute("info", dao.getInfo(accountCode));

			// 호스트측면 추가로 해야할 작업은 이 밑에 쓴다.



			
			// 로그인이 되었음을 기록한다.
		        result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               


		// 로그인이 안되어 있다면 
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=host";
		}
		
		IReportHostDAO dao = sqlSession.getMapper(IReportHostDAO.class);
		
		dao.add(dto);
		
		model.addAttribute("list", dao.list(book_code, host_code));
		
		return "/WEB-INF/views/host/reportHostImg.jsp";
	}
	
	// 이미지 업로드
	@RequestMapping(value = "/actions/reporthostimg.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String addImg(HttpServletRequest request, Model model, String book_report_code, String book_report_img_url)
	{
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute("hostCode"); 

		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";                                                         

		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
		        // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.

			IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	    
				model.addAttribute("info", dao.getInfo(accountCode));

			// 호스트측면 추가로 해야할 작업은 이 밑에 쓴다.



			
			// 로그인이 되었음을 기록한다.
		        result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               


		// 로그인이 안되어 있다면 
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=host";
		}
		
		IReportHostDAO dao = sqlSession.getMapper(IReportHostDAO.class);
		
		
		try
		{
			MultipartRequest m = FileManager.upload(request, "images");
			ArrayList<String> imageList = FileManager.getFileNames(m);
			book_report_img_url = imageList.get(0);
			book_report_code = m.getParameter("book_report_code");
		} catch (Exception e)
		{
			e.toString();
		}
		
		dao.addImg(book_report_code, book_report_img_url);
		
		return "";
		
	}
}
