package com.lookation.mybatis;



import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.lookation.dao.IReviewDAO;
import com.lookation.dto.ReviewDTO;

@Controller
public class Review
{
	@Autowired
	private SqlSession sqlSession;
	
	// 리뷰팝업으로 이동
	@RequestMapping(value="/actions/writereview.action", method = RequestMethod.GET)
	public String reviewForm(Model model, HttpServletRequest request)
	{	
		String locCode = request.getParameter("locCode");
		String memCode = request.getParameter("memCode");
		
		model.addAttribute("locCode", locCode);
		model.addAttribute("memCode", memCode);
		
		return "../WEB-INF/views/common/writeReviewPopup.jsp";
	}
	
	// 리뷰 작성
	@RequestMapping(value="/actions/reviewinsert.action", method = RequestMethod.POST)
	public String insertReview(ReviewDTO dto)
	{
		IReviewDAO dao = sqlSession.getMapper(IReviewDAO.class);
		
		dao.insertMemReview(dto);
		
		return "redirect:locationdetail.action";
		
	}
	
	// 수정 폼 못받아옴... 
	/*
	 * @RequestMapping(value="/actions/modifyformqna.action", method =
	 * RequestMethod.GET) public String modifyFormQna(QnaDTO dto) { String result =
	 * null;
	 * 
	 * IQnaDAO dao = sqlSession.getMapper(IQnaDAO.class);
	 * 
	 * dao.updateForm(dto);
	 * 
	 * result = "/WEB-INF/views/common/modifyQnaPopup.jsp";
	 * 
	 * return result; }
	 */
	
	// 삭제 
	@RequestMapping(value="/actions/deletereview.action", method = RequestMethod.GET)
	public String deleteReview(ReviewDTO dto)
	{
		IReviewDAO dao = sqlSession.getMapper(IReviewDAO.class);
		
		dao.deleteMemReview(dto);
		
		return "redirect:locationdetail.action";
	}
	
}
