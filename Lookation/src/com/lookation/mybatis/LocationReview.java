package com.lookation.mybatis;


import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lookation.dao.ILocationReviewDAO;
import com.lookation.dto.LocationReviewDTO;

@Controller
public class LocationReview
{
	@Autowired
	private SqlSession sqlSession;
	
	// 리뷰팝업으로 이동
	@RequestMapping(value="/actions/writereview.action", method = RequestMethod.GET)
	public String reviewForm(Model model, HttpServletRequest request)
	{	
		String locCode = request.getParameter("locCode");
		
		// 호스트인지 멤버인지 담을 변수
		String check = request.getParameter("identify");

		if(check.equals("member"))
		{
			String memCode = request.getParameter("memCode");
			model.addAttribute("memCode", memCode);
		}
		else
		{
			String hostCode = request.getParameter("hostCode");
			model.addAttribute("hostCode", hostCode);
		}
		
		model.addAttribute("locCode", locCode);
		
		return "../WEB-INF/views/common/writeReviewPopup.jsp";
	}
	
	@RequestMapping(value="/actions/modifyformreview.action", method = RequestMethod.GET)
	public String reviewModifyForm(Model model, HttpServletRequest request)
	{	
		// 호스트인지 멤버인지 담을 변수
		String check = request.getParameter("identify");
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		
		// 수정 폼의 경우 각각 다른 메소드를 수행해야 한다.
		// 이용자 → updateReviewForm
		// 호스트 → updateReviewReplyForm
		if(check.equals("member"))
		{
			String review_code = request.getParameter("review_code");
			model.addAttribute("modifyReview", dao.updateReviewForm(review_code));
		}
		else
		{
			String review_reply_code = request.getParameter("review_reply_code");
			model.addAttribute("modifyReviewReply", dao.updateReviewReplyForm(review_reply_code));
		}
		
		return "/WEB-INF/views/common/modifyReviewPopup.jsp"; 
	} 
	
	
	
	/*=== 이용자 ===*/
	
	// 이용자 : 리뷰 작성
	@RequestMapping(value="/actions/reviewinsert.action", method = RequestMethod.POST)
	public String insertReview(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		dao.insertMemReview(dto);
		
		return "redirect:locationdetail.action";
		
	}
	
	// 이용자 : 리뷰 수정
	@RequestMapping(value="/actions/modifyreview.action", method = RequestMethod.POST)
	public String modifyReview(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		dao.updateMemReview(dto);
		
		return "redirect:locationdetail.action";
	}
		
	
	
	// 이용자 : 리뷰 삭제 
	@RequestMapping(value="/actions/deletereview.action", method = RequestMethod.GET)
	public String deleteReview(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		dao.deleteMemReview(dto);
		
		return "redirect:locationdetail.action";
	}
	
	// 호스트 : 리뷰 답글 작성
	@RequestMapping(value="/actions/reviewreplyinsert.action", method = RequestMethod.POST)
	public String insertReviewReply(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		dao.insertHostReview(dto);
		return "redirect:locationdetailhost.action";
	}
	
	
	// 호스트 : 리뷰 답글 수정
	@RequestMapping(value="/actions/modifyreviewreply.action", method = RequestMethod.POST)
	public String modifyReviewReply(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		dao.updateHostReview(dto);
		
		return "redirect:locationdetailhost.action";
	}
	
	
	// 호스트 : 리뷰 답글 삭제
	@RequestMapping(value="/actions/deletereviewreply.action", method = RequestMethod.GET)
	public String deleteReviewReply(LocationReviewDTO dto)
	{
		ILocationReviewDAO dao = sqlSession.getMapper(ILocationReviewDAO.class);
		
		dao.deleteHostReview(dto);
		
		return "redirect:locationdetailhost.action";
	}
}
