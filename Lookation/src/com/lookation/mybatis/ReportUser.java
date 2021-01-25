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

import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dao.IReportUserDAO;
import com.lookation.dto.reportUserDTO;
import com.lookation.util.FileManager;
import com.oreilly.servlet.MultipartRequest;

@Controller
public class ReportUser
{
	@Autowired
	private SqlSession sqlSession;
	
	
	// 신고 접수 후 이미지 업로드 폼 호출
	@RequestMapping(value = "/actions/reportuser.action", method = RequestMethod.POST)
	public String add(reportUserDTO dto, String loc_code, String member_code, Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute("memberCode"); 

		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";                                                         

		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
		        // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.

			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.



			
			// 로그인이 되었음을 기록한다.
		        result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               


		// ********* 만약 로그인기능이 사용되는 뷰페이지의 경우 이 코드를 추가한다. ********* 
		// 로그인이 안되어 있다면 
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=member";
		}
		
		
		IReportUserDAO dao = sqlSession.getMapper(IReportUserDAO.class);
		
		dao.add(dto);
		
		model.addAttribute("list", dao.list(loc_code, member_code));
		
		return "/WEB-INF/views/user/reportUserImg.jsp";
	}
	
	// 이미지 업로드
	@RequestMapping(value = "/actions/reportuserimg.action", method = {RequestMethod.GET, RequestMethod.POST})
	public String addImg(HttpServletRequest request, Model model, String loc_report_code, String loc_report_img_url)
	{
		// 세션을 통한 로그인 확인                                                                    
		HttpSession session = request.getSession();                                                                  
		String accountCode = (String)session.getAttribute("memberCode"); 

		// 로그인 확인을 기록하기 위함                  
		String result = "noSigned";                                                         

		// 회원 코드가 세션에 세팅되어 있다면                                                                                   
		if(accountCode != null)                                         
		{       
			// 다음 사이트 header에 이용자 정보를 보여줄 수 있게
		        // db에서 회원 정보를 받아 뷰에 데이터를 넘겨준다.

			IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	    
			model.addAttribute("info", dao.getInfo(accountCode));

			// 이용자측면 추가로 해야할 작업은 이 밑에 쓴다.



			
			// 로그인이 되었음을 기록한다.
		        result = "signed";                                                                                
		}

		// 로그인 여부 데이터를 뷰에 넘겨준다.                                                                                   
		model.addAttribute("result", result);                                               


		// ********* 만약 로그인기능이 사용되는 뷰페이지의 경우 이 코드를 추가한다. ********* 
		// 로그인이 안되어 있다면 
		if(result.equals("noSigned"))
		{
		    // 로그인 창으로 이동한다.
		    return "redirect:loginform.action?identify=member";
		}
		
		IReportUserDAO dao = sqlSession.getMapper(IReportUserDAO.class);
		
		
		try
		{
			MultipartRequest m = FileManager.upload(request, "images");
			ArrayList<String> imageList = FileManager.getFileNames(m);
			loc_report_img_url = imageList.get(0);
			loc_report_code = m.getParameter("loc_report_code");
		} catch (Exception e)
		{
			e.toString();
		}
		
		dao.addImg(loc_report_code, loc_report_img_url);
		
		return "";
	}
}
