package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IReportDAO;

@Controller
public class reportListController
{
	@Autowired
	private SqlSession sqlSession;
	
	
	// reportList 테이블 정보 조회
	@RequestMapping(value = "/actions/bookreportlist.action", method = RequestMethod.GET)
	public String basicList(Model model)
	{
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("bookBasicList", dao.bookBasicList());	// 예약신고 정보 조회
		
		return "/WEB-INF/views/admin/bookReportList.jsp";
	}
	
	// 예약신고 part
	
	// 신고처리 전 상세정보 조회
	@RequestMapping(value = "/actions/bookpopup0.action", method = RequestMethod.GET)
	public String bookDetailList0(String book_report_code, Model model)
	{
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("bookDetailList0", dao.bookDetailList0(book_report_code));
		
		return "/WEB-INF/views/admin/bookReportPopup0.jsp";
	}
	
	// 신고처리 후 상세정보 조회
	@RequestMapping(value = "/actions/bookpopup1.action", method = RequestMethod.GET)
	public String bookDetailList1(String book_report_code, Model model)
	{
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("bookDetailList1", dao.bookDetailList1(book_report_code));
		
		return "/WEB-INF/views/admin/bookReportPopup1.jsp";
	}
	
	// 신고처리 버튼 클릭
	@RequestMapping(value = "/actions/bookreportproc.action", method = RequestMethod.GET)
	public String bookReportProc(String book_report_code, String report_proc_type_code)
	{
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		dao.bookReportProc(book_report_code, report_proc_type_code);
		
		return "redirect:bookreportlist.action";
	}
	
	// 블랙리스트 추가 버튼 클릭
	@RequestMapping(value = "/actions/userblackform.action", method = RequestMethod.GET)
	public String userBlackForm(String member_email)
	{
		String result = null;
		
		result = "/WEB-INF/views/admin/userBlackListPopup.jsp";
		
		return result;
	}
	
	// 블랙리스트 처리
	// → 에이젝스 처리로 변경 필요
	@RequestMapping(value = "/actions/userblack.action", method = RequestMethod.GET)
	public String userBlack(String member_email, String member_blacklist_reason)
	{
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		dao.userBlackList(member_email, member_blacklist_reason);
		
		return "redirect:bookreportlist.action";
	}
	
	
	// 공간 신고 part
	@RequestMapping(value = "/actions/reportlist.action", method = RequestMethod.GET)
	public String locBasicList(Model model)
	{
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("locBasicList", dao.locBasicList());		// 공간신고 정보 조회
		
		return "/WEB-INF/views/admin/reportList.jsp";
	}
	
	// 신고처리 전 상세정보 조회
	@RequestMapping(value = "/actions/locpopup0.action", method = RequestMethod.GET)
	public String locDetailList0(String loc_report_code, Model model)
	{
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("locDetailList0", dao.locDetailList0(loc_report_code));
		
		return "/WEB-INF/views/admin/locReportPopup0.jsp";
	}
	
	// 신고처리 후 상세정보 조회
	@RequestMapping(value = "/actions/locpopup1.action", method = RequestMethod.GET)
	public String locDetailList1(String loc_report_code, Model model)
	{
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		model.addAttribute("locDetailList1", dao.locDetailList1(loc_report_code));
		
		return "/WEB-INF/views/admin/locReportPopup1.jsp";
	}
	
	// 신고처리 버튼 클릭
	@RequestMapping(value = "/actions/locreportproc.action", method = RequestMethod.GET)
	public String locReportProc(String loc_report_code, String report_proc_type_code)
	{
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		dao.locReportProc(loc_report_code, report_proc_type_code);
		
		return "redirect:reportlist.action";
	}
	
	// 블랙리스트 추가 버튼 클릭
	@RequestMapping(value = "/actions/hostblackform.action", method = RequestMethod.GET)
	public String hostBlackForm(String host_email)
	{
		String result = null;
		
		result = "/WEB-INF/views/admin/hostBlackListPopup.jsp";
		
		return result;
	}
	
	// 블랙리스트 처리
	// → 에이젝스 처리로 변경 필요
	@RequestMapping(value = "/actions/hostblack.action", method = RequestMethod.GET)
	public String hostBlack(String host_email, String host_blacklist_reason)
	{
		IReportDAO dao = sqlSession.getMapper(IReportDAO.class);
		
		dao.hostBlackList(host_email, host_blacklist_reason);
		
		return "redirect:reportlist.action";
	}
	
	
}
