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
import com.lookation.dao.IMainLocationInfo;
import com.lookation.dao.IMemberAccountDAO;
import com.lookation.dao.IStatDAO;
import com.lookation.dto.AccountDTO;
import com.lookation.dto.MainLocationDTO;

@Controller
public class Main
{
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value="/actions/membermain.action", method = RequestMethod.GET)
	public String memberMain(HttpServletRequest request, Model model)
	{
		// 세션을 통한 로그인 확인
		HttpSession session = request.getSession();
		String identify = (String)session.getAttribute("identify");
		String accountCode = (String)session.getAttribute("accountCode");
		String result = "noSigned";
		
		if(identify != null && accountCode != null)
		{
			if(identify.equals("member"))
			{
				IMemberAccountDAO dao = sqlSession.getMapper(IMemberAccountDAO.class);	
				model.addAttribute("info", dao.getInfo(accountCode));
				result = "signed";
			}
		}
		model.addAttribute("result", result);
		
		// 통계값 확인
		IStatDAO statDao = sqlSession.getMapper(IStatDAO.class);
		model.addAttribute("countMember", statDao.countMember());
		model.addAttribute("countHost", statDao.countHost());
		model.addAttribute("countBookDeal", statDao.countBookDeal());
		model.addAttribute("countLocation", statDao.countLocation());
		
		// 오늘의 공간
		IMainLocationInfo mainLocationDao = sqlSession.getMapper(IMainLocationInfo.class);
		ArrayList<MainLocationDTO> todayLocationList = mainLocationDao.todayLocationList();

		// 주소에서 동만 남긴다.
		for (MainLocationDTO mainLocationDTO : todayLocationList)
		{
			String addr = mainLocationDTO.getAddr();
			String[] subAddr = addr.split(" ");
			
			for(String sub : subAddr)
			{
				// 마지막 글자가 동인 경우
				if(sub.charAt(sub.length()-1) == '동')
				{
					mainLocationDTO.setAddr(sub);
					break;
				}
			}
		}
		
		model.addAttribute("todayLocationList", todayLocationList);
		
		// 오늘의 리뷰
		model.addAttribute("todayReviewList", mainLocationDao.todayReviewList());
		
		return "../WEB-INF/views/user/mainUser.jsp";
	}
	
	@RequestMapping(value="/actions/memberlogout.action", method = RequestMethod.GET)
	public String memberLogout(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();
		session.removeAttribute("identify");
		session.removeAttribute("accountCode");
		
		return "redirect:hostmain.action";
	}
	
	
	@RequestMapping(value="/actions/hostmain.action", method = RequestMethod.GET)
	public String hostMain(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();
		String identify = (String)session.getAttribute("identify");
		String accountCode = (String)session.getAttribute("accountCode");
		String result = "noSigned";
		
		if(identify != null && accountCode != null)
		{
			if(identify.equals("host"))
			{
				IHostAccountDAO dao = sqlSession.getMapper(IHostAccountDAO.class);	
				model.addAttribute("info", dao.getInfo(accountCode));
				result = "signed";
			}
		}		
		model.addAttribute("result", result);
		
		// 통계값 확인
		IStatDAO statDao = sqlSession.getMapper(IStatDAO.class);
		model.addAttribute("countMember", statDao.countMember());
		model.addAttribute("countHost", statDao.countHost());
		model.addAttribute("countBookDeal", statDao.countBookDeal());
		model.addAttribute("countLocation", statDao.countLocation());
		
		return "../WEB-INF/views/host/mainHost.jsp";
	}
	
	@RequestMapping(value="/actions/hostlogout.action", method = RequestMethod.GET)
	public String hostLogout(HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession();
		session.removeAttribute("identify");
		session.removeAttribute("accountCode");
		
		return "redirect:membermain.action";
	}
	
	@RequestMapping(value="/actions/adminmain.action", method = RequestMethod.GET)
	public String adminMain(Model model)
	{
		return "../WEB-INF/views/admin/mainAdmin.jsp";
	}
}
