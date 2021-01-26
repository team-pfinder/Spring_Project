package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IReportDAO;

@Controller
public class ReportList
{
	@Autowired
	private SqlSession sqlSession;
	
	
	// reportList 테이블 정보 조회
	@RequestMapping(value = "/actions/bookreportlist.action", method = RequestMethod.GET)
	public String basicList(Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("bookBasicList", dao.bookBasicList());	// 예약신고 정보 조회
		
		return "/WEB-INF/views/admin/bookReportList.jsp";
	}
	
	// 예약신고 part
	
	// 신고처리 전 상세정보 조회
	@RequestMapping(value = "/actions/bookpopup0.action", method = RequestMethod.GET)
	public String bookDetailList0(String book_report_code, Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("bookDetailList0", dao.bookDetailList0(book_report_code));
		
		return "/WEB-INF/views/admin/bookReportPopup0.jsp";
	}
	
	// 신고처리 후 상세정보 조회
	@RequestMapping(value = "/actions/bookpopup1.action", method = RequestMethod.GET)
	public String bookDetailList1(String book_report_code, Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("bookDetailList1", dao.bookDetailList1(book_report_code));
		
		return "/WEB-INF/views/admin/bookReportPopup1.jsp";
	}
	
	// 신고처리 버튼 클릭
	@RequestMapping(value = "/actions/bookreportproc.action", method = RequestMethod.GET)
	public String bookReportProc(String book_report_code, String report_proc_type_code, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		dao.bookReportProc(book_report_code, report_proc_type_code);
		
		return "redirect:bookreportlist.action";
	}
	
	// 블랙리스트 추가 버튼 클릭
	@RequestMapping(value = "/actions/userblackform.action", method = RequestMethod.GET)
	public String userBlackForm(String member_email, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		String result = null;
		
		result = "/WEB-INF/views/admin/userBlackListPopup.jsp";
		
		return result;
	}
	
	// 블랙리스트 처리
	@RequestMapping(value = "/actions/userblack.action", method = RequestMethod.GET)
	public String userBlack(String member_email, String member_blacklist_reason, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		dao.userBlackList(member_email, member_blacklist_reason);
		
		return "";
		
	}
	
	
	// 공간 신고 part
	@RequestMapping(value = "/actions/reportlist.action", method = RequestMethod.GET)
	public String locBasicList(Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("locBasicList", dao.locBasicList());		// 공간신고 정보 조회
		
		return "/WEB-INF/views/admin/reportList.jsp";
	}
	
	// 신고처리 전 상세정보 조회
	@RequestMapping(value = "/actions/locpopup0.action", method = RequestMethod.GET)
	public String locDetailList0(String loc_report_code, Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("locDetailList0", dao.locDetailList0(loc_report_code));
		
		return "/WEB-INF/views/admin/locReportPopup0.jsp";
	}
	
	// 신고처리 후 상세정보 조회
	@RequestMapping(value = "/actions/locpopup1.action", method = RequestMethod.GET)
	public String locDetailList1(String loc_report_code, Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("locDetailList1", dao.locDetailList1(loc_report_code));
		
		return "/WEB-INF/views/admin/locReportPopup1.jsp";
	}
	
	// 신고처리 버튼 클릭
	@RequestMapping(value = "/actions/locreportproc.action", method = RequestMethod.GET)
	public String locReportProc(String loc_report_code, String report_proc_type_code, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		dao.locReportProc(loc_report_code, report_proc_type_code);
		
		return "redirect:reportlist.action";
	}
	
	// 블랙리스트 추가 버튼 클릭
	@RequestMapping(value = "/actions/hostblackform.action", method = RequestMethod.GET)
	public String hostBlackForm(String host_email, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		String result = null;
		
		result = "/WEB-INF/views/admin/hostBlackListPopup.jsp";
		
		return result;
	}
	
	// 블랙리스트 처리
	@RequestMapping(value = "/actions/hostblack.action", method = RequestMethod.GET)
	public String hostBlack(String host_email, String host_blacklist_reason, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String admin_id = (String)session.getAttribute("admin_id");

		// 로그인이 안된경우                                                                   
		if(admin_id == null)                                                      
		{                                                                            
			// 로그인 실패. 다시 로그인창으로                                                     
			return "redirect:adminloginform.action";
		}
		
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		dao.hostBlackList(host_email, host_blacklist_reason);
		
		return "";
		
	}
	
	
}
