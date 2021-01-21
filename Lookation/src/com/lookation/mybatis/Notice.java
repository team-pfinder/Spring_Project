package com.lookation.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.INoticeDAO;

@Controller
public class Notice
{
	@Autowired
	private SqlSession sqlSession;
	
	//유저 측 공지사항 <<조회>>
	@RequestMapping(value="/actions/notice.action", method = RequestMethod.GET)
	public String U_noticeList(ModelMap model)
	{
		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);
		
		model.addAttribute("U_noticeList", dao.U_noticeList());

		//String notice_code = request.getParameter("notice_code");
		
	
		//model.addAttribute("notice_code", notice_code);
		//System.out.println("여기는 조회중 : " + notice_code);
	
		
		return "/WEB-INF/views/common/notice.jsp";
	}
	
	// 공지사항 상세보기
	@RequestMapping(value = "/actions/noticedetail.action", method = RequestMethod.GET)
	public String U_ndetailList(String notice_code, ModelMap model)
	{
		INoticeDAO dao = sqlSession.getMapper(INoticeDAO.class);

		model.addAttribute("U_ndetailList", dao.U_ndetailList(notice_code));
		
		System.out.println(notice_code);
		
		return "/WEB-INF/views/common/noticeDetail.jsp";
	}

}
