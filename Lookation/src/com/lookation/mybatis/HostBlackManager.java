package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IHostBlackManagerDAO;
import com.lookation.dto.HostBlackManagerDTO;

@Controller
public class HostBlackManager
{
	@Autowired
	private SqlSession sqlSession;

	// 호스트 리스트 조회
	@RequestMapping(value = "/actions/hostblackmanager.action", method = RequestMethod.GET)
	public String hostBlackManager(Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();
		/* ============================================= */
		session.setAttribute("adminCode", "admin_lookation01");
		/* ============================================= */

		String accountCode = (String) session.getAttribute("adminCode");

		// 로그인이 안된경우
		if (accountCode == null)
		{
			// 로그인 실패. 다시 로그인창으로
			return "redirect:adminloginform.action";
		}

		// 다음 페이지로 이동
		IHostBlackManagerDAO dao = sqlSession.getMapper(IHostBlackManagerDAO.class);

		model.addAttribute("blacklist", dao.listBlackHost());

		return "../WEB-INF/views/admin/hostAccountManager.jsp";
	}

	// 호스트 신고내역 조회 팝업
	@RequestMapping(value = "/actions/reportdetails.action", method = RequestMethod.GET)
	public String viewReportDetailsHost(Model model, String host_code, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();
		/* ============================================= */
		session.setAttribute("adminCode", "admin_lookation01");
		/* ============================================= */

		String accountCode = (String) session.getAttribute("adminCode");

		// 로그인이 안된경우
		if (accountCode == null)
		{
			// 로그인 실패. 다시 로그인창으로
			return "redirect:adminloginform.action";
		}

		// 다음 페이지로 이동
		IHostBlackManagerDAO dao = sqlSession.getMapper(IHostBlackManagerDAO.class);

		model.addAttribute("reportlist", dao.viewReportHost(host_code));

		return "../WEB-INF/views/admin/reportDetailPopupHost.jsp";
	}

	// 호스트 블랙리스트 등록 팝업
	@RequestMapping(value = "/actions/setblackhostform.action", method = RequestMethod.GET)
	public String setBlackHost(Model model, String host_code, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();
		/* ============================================= */
		session.setAttribute("adminCode", "admin_lookation01");
		/* ============================================= */

		String accountCode = (String) session.getAttribute("adminCode");

		// 로그인이 안된경우
		if (accountCode == null)
		{
			// 로그인 실패. 다시 로그인창으로
			return "redirect:adminloginform.action";
		}

		// 다음 페이지로 이동
		IHostBlackManagerDAO dao = sqlSession.getMapper(IHostBlackManagerDAO.class);

		// 호스트 이메일찾기시 이용할 host_code 및 호스트 정보 전달
		model.addAttribute("host_code", host_code);
		model.addAttribute("host_email", dao.searchEmail(host_code));

		return "../WEB-INF/views/admin/blacklistPopupHost.jsp";
	}

	// 호스트 블랙리스트 등록 작업
	@RequestMapping(value = "/actions/setblackhost.action", method = RequestMethod.POST)
	public String regBlackHost(Model model, HostBlackManagerDTO dto)
	{
		IHostBlackManagerDAO dao = sqlSession.getMapper(IHostBlackManagerDAO.class);

		// 블랙리스트 등록
		dao.regBlackHost(dto);

		return "redirect:hostblackmanager.action";
	}

	// 호스트 블랙리스트 해제 작업
	@RequestMapping(value = "/actions/delblackhost.action", method = RequestMethod.GET)
	public String delBlackHost(Model model, HttpServletRequest request)
	{
		IHostBlackManagerDAO dao = sqlSession.getMapper(IHostBlackManagerDAO.class);

		String host_code = request.getParameter("host_code");
		String host_email = dao.searchEmail(host_code);

		// 블랙리스트 등록
		dao.delBlackHost(host_email);

		return "redirect:hostblackmanager.action";
	}
}
