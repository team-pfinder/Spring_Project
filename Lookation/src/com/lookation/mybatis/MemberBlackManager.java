package com.lookation.mybatis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.IMemberBlackManagerDAO;
import com.lookation.dto.MemberBlackManagerDTO;

@Controller
public class MemberBlackManager
{
	@Autowired
	private SqlSession sqlSession;

	// 이용자 리스트 조회
	@RequestMapping(value = "/actions/memberblackmanager.action", method = RequestMethod.GET)
	public String memberBlackManager(Model model, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String accountCode = (String) session.getAttribute("adminCode");

		// 로그인이 안된경우
		if (accountCode == null)
		{
			// 로그인 실패. 다시 로그인창으로
			return "redirect:adminloginform.action";
		}

		// 다음 페이지로 이동
		IMemberBlackManagerDAO dao = sqlSession.getMapper(IMemberBlackManagerDAO.class);

		model.addAttribute("blacklist", dao.listBlackMem());

		return "../WEB-INF/views/admin/userAccountManager.jsp";
	}

	// 이용자 신고내역 조회 팝업
	@RequestMapping(value = "/actions/reportmemdetails.action", method = RequestMethod.GET)
	public String viewReportDetailsmember(Model model, String member_code, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String accountCode = (String) session.getAttribute("adminCode");

		// 로그인이 안된경우
		if (accountCode == null)
		{
			// 로그인 실패. 다시 로그인창으로
			return "redirect:adminloginform.action";
		}

		// 다음 페이지로 이동

		IMemberBlackManagerDAO dao = sqlSession.getMapper(IMemberBlackManagerDAO.class);

		model.addAttribute("reportlist", dao.viewReportMem(member_code));

		return "../WEB-INF/views/admin/reportDetailPopupUser.jsp";
	}

	// 이용자 블랙리스트 등록 팝업
	@RequestMapping(value = "/actions/setblackmemform.action", method = RequestMethod.GET)
	public String setBlackmember(Model model, String member_code, HttpServletRequest request)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();

		String accountCode = (String) session.getAttribute("adminCode");

		// 로그인이 안된경우
		if (accountCode == null)
		{
			// 로그인 실패. 다시 로그인창으로
			return "redirect:adminloginform.action";
		}

		// 다음 페이지로 이동

		IMemberBlackManagerDAO dao = sqlSession.getMapper(IMemberBlackManagerDAO.class);

		// 이용자 이메일찾기시 이용할 member_code 및 이용자 정보 전달
		model.addAttribute("member_code", member_code);
		model.addAttribute("member_email", dao.searchMemEmail(member_code));

		return "../WEB-INF/views/admin/blacklistPopupUser.jsp";
	}

	// 이용자 블랙리스트 등록 작업
	@RequestMapping(value = "/actions/setblackmem.action", method = RequestMethod.POST)
	public void regBlackmember(Model model, MemberBlackManagerDTO dto)
	{
		IMemberBlackManagerDAO dao = sqlSession.getMapper(IMemberBlackManagerDAO.class);

		// 블랙리스트 등록
		dao.regBlackMem(dto);

		// return "redirect:memberblackmanager.action";
	}

	// 이용자 블랙리스트 해제 작업
	@RequestMapping(value = "/actions/delblackmem.action", method = RequestMethod.GET)
	public String delBlackmember(Model model, HttpServletRequest request)
	{
		IMemberBlackManagerDAO dao = sqlSession.getMapper(IMemberBlackManagerDAO.class);

		String member_code = request.getParameter("member_code");
		String member_email = dao.searchMemEmail(member_code);

		// 블랙리스트 등록
		dao.delBlackMem(member_email);

		return "redirect:memberblackmanager.action";
	}
}
